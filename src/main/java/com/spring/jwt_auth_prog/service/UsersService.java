package com.spring.jwt_auth_prog.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.spring.jwt_auth_prog.model.Users;
import com.spring.jwt_auth_prog.repo.UserRepo;

@Service
public class UsersService {
    private UserRepo repo;
    private AuthenticationManager authenticationManager;



    public UsersService(UserRepo repo, AuthenticationManager authenticationManager) {
        this.repo = repo;
        this.authenticationManager = authenticationManager;
    }

    public Users registUser(Users user){
        if (user != null){
            return repo.save(user);
        }
        return user;

    }

    /*
     * Since authentication manager calls authentication Provider i just need to call it using the manager to verify
     * a Authentication object needs to be created to transfer it to
     *
     * service asks the AuthenticationManager to authenticate a UsernamePasswordAuthenticationToken(impl of Authentication interface).
     * The manager delegates to one of your AuthenticationProviders (e.g. DaoAuthenticationProvider)
     * which uses your UserDetailsService to load user data and validate the password.
     *
     * If valid, an authenticated Authentication object is returned.
     *
     * The manager (usually a ProviderManager) iterates available AuthenticationProviders
     * (e.g. DaoAuthenticationProvider, LDAP provider, etc.)
     * and asks each provider.supports(token) then provider.authenticate(token)
     * until one returns a non-null Authentication or throws an exception.
     */

    public String verify(Users user) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            return authentication.isAuthenticated() ? "Success" :"meh";
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT); //lol just because i cans
        }
    }
}
