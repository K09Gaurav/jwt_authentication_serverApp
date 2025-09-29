package com.spring.jwt_auth_prog.service;

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
        else{
            // return User.withUsername(username).build(); THIS IS USELESS -> builds an in-memory user with no password and no roles
            // we have to return object of UserDetails which we just implemented
            return new MyUserDetails(user);
        }
    }

}
