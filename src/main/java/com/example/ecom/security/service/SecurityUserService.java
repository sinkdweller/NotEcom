package com.example.ecom.security.service;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.example.ecom.security.model.SecurityUser;
import com.example.ecom.repo.UserRepo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Spring has a way to retrieve users now 
@Service
public class SecurityUserService implements UserDetailsService {
    private final UserRepo userRepo;
    public SecurityUserService(UserRepo userRepo){
        this.userRepo=userRepo;
    }
    @Override
    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
        .map(SecurityUser::new)
        .orElseThrow(()-> new UsernameNotFoundException("USER NOT FOUND"));
    }
}
