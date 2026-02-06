async function doLogin(user, pass){
        
        const response = await fetch("/auth/login", {
            method: "POST",
            headers: {'Content-type': 'Application/json'},
            body: JSON.stringify({
                username: user,
                password: pass
            }
            )
        })
        const data = await response.json();
        if(data.token){
            localStorage.setItem('token', data.token);
            alert("login success!");
            
            window.location.href = "/dashboard.html"
        }
        else{
            alert("login not successful");
        }
}
async function doSignup(username, password) {

    const response = await fetch("/auth/signup", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json' 
        },
        body: JSON.stringify({username: username, password: password})
    });
    if(response.ok){
        alert("user signup good!");
        doLogin(username, password);
    }else{
        const errorText = await response.text();
        alert("error: " + errorText);
    }
        
}