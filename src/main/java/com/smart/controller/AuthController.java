package com.smart.controller;

import com.smart.entities.JwtRequest;
import com.smart.entities.JwtResponse;
import com.smart.security.JwtHelper;
import com.smart.services.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value ="/auth")
public class AuthController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(JwtHelper helper) {
        this.helper = helper;
    }


//    @PostMapping("/token")
//    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest) {
//        System.out.println(jwtRequest);
//
//        this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
//
//
//        UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtRequest.getEmail());
//        String token = this.helper.generateToken(userDetails);
//
//        JwtResponse response = JwtResponse.builder()
//                .jwtToken(token)
//                .username(userDetails.getUsername()).build();
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
        System.out.println(jwtRequest);
        try{
           this.manager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(),jwtRequest.getPassword()));
        }catch (UsernameNotFoundException e){
          e.printStackTrace();
          throw new Exception("Bad Credentials");
        }

       UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = this.helper.generateToken(userDetails);
        System.out.println("token " + token);

        return ResponseEntity.ok(new JwtResponse(token));

    }


    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

}
