package com.spring.jwt_auth_prog.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.jwt_auth_prog.model.MyUserDetails;
import com.spring.jwt_auth_prog.model.Users;
import com.spring.jwt_auth_prog.repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService{


    private UserRepo userRepo;

    public MyUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepo.findByUsername(username);
        if (user == null){
            System.out.println("USER NOT FOUND");
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
        else
        // we have to return object of UserDetails which we just implemented
        // If your Users entity has extra fields (like email, phone, active flag, etc.),
        // and you want to use or expose them in security logic later —
        // then you should make your own class implementing UserDetails.
            return new MyUserDetails(user);
            //if we dont have
            // return User.withUsername(user.getUsername()).password(user.getPassword()).roles(user.getRoles()).build();
    }



}
