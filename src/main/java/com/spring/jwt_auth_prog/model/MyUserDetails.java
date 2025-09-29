package com.spring.jwt_auth_prog.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * If we used Spring's built-in User class, we wouldn’t need this.
 * But since we made our own Users entity, we need this class so
 * Spring Security knows how to read username, password, and roles.
 *
 * That’s why we implement UserDetails here.
 * The actual values (username, password, roles) come from the DB,
 * and MyUserDetailsService will pass them into this class.
 */
public class MyUserDetails implements UserDetails{

    private Users user;

    public MyUserDetails(Users user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return Collections.singleton( new SimpleGrantedAuthority(user.getRoles()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        // return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        // return UserDetails.super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        // return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        // return UserDetails.super.isEnabled();
        return true;
    }

}
