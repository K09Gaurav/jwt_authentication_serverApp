package com.spring.jwt_auth_prog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/*
 * We are saying hey spring security
 * dont go for the default
 * this is the security filter chain you have to go for
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*
     *  Define the security filter chain.
     *
     * if i leave this method blank and just return httpsecurity .build()
     * there is no filter now !!!
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        /*
         * Disable CSRF protection.
         * Useful for APIs or testing with Postman where CSRF tokens are not needed.
         * Without this, POST/PUT/DELETE requests require a CSRF token.
         *
         * remove login page and now ANYONE can access it
         */
        http.csrf(customizer -> customizer.disable());

        /*
         * Require authentication for any request by default.
         *
         * Access to localhost was denied
         * Denies any request
         */
        http.authorizeHttpRequests(requst -> requst.anyRequest().authenticated());

        /*
         * implements form login
         * buit when accesiing from postman
         * the response is in the form of html form lol
         */
        http.formLogin(Customizer.withDefaults());

        /*
         * Enable HTTP Basic authentication.
         * Useful for api clients like postman
         */
        http.httpBasic(Customizer.withDefaults());

        /*
         * now we cant login from browser cuz everry time i need to login again
         * since its is now stateless and every request needs a new reqqource
         * since it doesnt remember us giving a authentication credentials
         *
         * so if we are on login page and trying to login it will again redirect to login page
         * saying hey new guy new resource
         * cuz its stateless and doesnt remember you giving password
         *
         * its great for api clients like postman since they send credentials with requests
         *
         * but if we remove the login form page it wil now ask us for authentication using http basic
         * and treat us using http basic
         */
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));




        return http.build();
    }

}
