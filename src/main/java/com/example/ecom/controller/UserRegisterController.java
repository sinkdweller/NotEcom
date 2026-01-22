package com.example.ecom.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecom.entity.User;
import com.example.ecom.service.UserRegisterService;

@RestController
public class UserRegisterController {

    private final UserRegisterService userRegisterService;

    public UserRegisterController(UserRegisterService userRegisterService){
        this.userRegisterService=userRegisterService;
    }
    @PostMapping("/register")
    public String postUser(@RequestBody User user){
        userRegisterService.createUser(user.getUsername(), user.getPassword(), user.getEmail());
        return "User " + user.getUsername() + " created successfully!";
    }
    @GetMapping("/test")
    public String test() {
    return "Controller is working!";
    }

}
