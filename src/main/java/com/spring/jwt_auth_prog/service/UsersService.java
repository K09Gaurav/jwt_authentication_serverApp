package com.spring.jwt_auth_prog.service;

import org.springframework.stereotype.Service;

import com.spring.jwt_auth_prog.model.Users;
import com.spring.jwt_auth_prog.repo.UserRepo;

@Service
public class UsersService {
    private UserRepo repo;

    public UsersService(UserRepo repo) {
        this.repo = repo;
    }

    public Users registUser(Users user){
        if (user != null){
            return repo.save(user);
        }
        return user;

    }
}
