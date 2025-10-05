package com.spring.jwt_auth_prog.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.jwt_auth_prog.model.Users;
import com.spring.jwt_auth_prog.service.UsersService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    /*
     * will use bcrypt to do hashing
     * b crypt = plain password -> hash1 -> hash2 -> ...... -> hashN -> store in DB
     * eg in online websites - give password give number of rounds
     * if rounds given is 10 -> it will run for 2^10 rounds
     *
     * Implement bcrypt when a user register and also when validating it
     *
     */

    private UsersService service;
    private BCryptPasswordEncoder encoder;

    public UserController(UsersService service) {
        this.service = service;
        this.encoder = new BCryptPasswordEncoder();
    }



    @PostMapping("/register")
    public Users registUser(@RequestBody Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        return service.registUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user){
        return service.verify(user);
    }




    /*
     * CSRF token proves “this request actually came from my own site, not from somewhere else.
     */
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest reqqHttpServletRequest){
        return (CsrfToken)reqqHttpServletRequest.getAttribute("_csrf");
    }
}
