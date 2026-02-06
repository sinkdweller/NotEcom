async function loadNavbar() {
    try {
        const response = await fetch('./navbar.html');
        const navHtml = await response.text();

        // Inject at the top of the body
        document.body.insertAdjacentHTML('afterbegin', navHtml);

        const path = window.location.pathname;
        const btn = document.getElementById('left-actions');

        if (path.includes('register-device')) {
            btn.innerHTML = `<button class="green-btn" onclick="window.location.href='/dashboard.html'">Dashboard</button>`;
        } 
        else if (path.includes('dashboard.html')) {
            btn.innerHTML = `<button id="nav-refresh-btn" class="green-btn" onclick="refresh()">Refresh</button>`;
        }
        } catch (error) {
                console.error("Error loading navbar:", error);
    }
}

function logout() {
    localStorage.removeItem("token");
    window.location.href = "/login.html";
}

loadNavbar();