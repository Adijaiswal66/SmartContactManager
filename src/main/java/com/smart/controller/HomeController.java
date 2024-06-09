package com.smart.controller;

import com.smart.entities.User;
import com.smart.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "/home")
@CrossOrigin
public class HomeController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;


    @GetMapping(value = "/getallusers")
    public List<User> getAllUsers(){
     return this.userService.getAllUsers();
    }

    @PostMapping(value = "/register")
    public User registerUser(@RequestBody User user){

            user.setRole("USER");
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
      return this.userService.registerUser(user);
    }


}

