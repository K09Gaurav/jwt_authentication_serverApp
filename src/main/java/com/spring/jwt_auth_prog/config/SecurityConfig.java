package com.spring.jwt_auth_prog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.spring.jwt_auth_prog.service.MyUserDetailsService;

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
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

    //     /*
    //      * Disable CSRF protection.
    //      * Useful for APIs or testing with Postman where CSRF tokens are not needed.
    //      * Without this, POST/PUT/DELETE requests require a CSRF token.
    //      *
    //      * remove login page and now ANYONE can access it
    //      */
    //     // http.csrf(customizer -> customizer.disable());

    //     /*
    //      * Require authentication for any request by default.
    //      *
    //      * Access to localhost was denied
    //      * Denies any request
    //      */
    //     // http.authorizeHttpRequests(requst -> requst.anyRequest().authenticated());

    //     /*
    //      * implements form login
    //      * buit when accesiing from postman
    //      * the response is in the form of html form lol
    //      */
    //     // http.formLogin(Customizer.withDefaults());

    //     /*
    //      * Enable HTTP Basic authentication.
    //      * Useful for api clients like postman
    //      */
    //     // http.httpBasic(Customizer.withDefaults());

    //     /*
    //      * now we cant login from browser cuz everry time i need to login again
    //      * since its is now stateless and every request needs a new reqqource
    //      * since it doesnt remember us giving a authentication credentials
    //      *
    //      * so if we are on login page and trying to login it will again redirect to login page
    //      * saying hey new guy new resource
    //      * cuz its stateless and doesnt remember you giving password
    //      *
    //      * its great for api clients like postman since they send credentials with requests
    //      *
    //      * but if we remove the login form page it wil now ask us for authentication using http basic
    //      * and treat us using http basic
    //      */
    //     // http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    //     return http
    //             .csrf(Customizer -> Customizer.disable())
    //             .authorizeHttpRequests(Customizer -> Customizer.anyRequest().authenticated())
    //             .formLogin(Customizer.withDefaults())
    //             .httpBasic(Customizer.withDefaults())
    //             // .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //             .build();

    // }

    /*
     * I want to verify the way i want and not use default login form
     * Create a bean -> it will be picked up by spring security
     *
     * UserDetailsService is used by DaoAuthenticationProvider
     * for retrieving a username, a password, and other attributes for authenticating with a username and password.
     * Spring Security provides in-memory, JDBC, and caching implementations of UserDetailsService.
     *
     * You can define custom authentication by exposing a custom UserDetailsService as a bean.
     */

     //----------------------------------------------------------------------------------------------------------------------------------

    // @Bean
    // public UserDetailsService userDetailsService(){
    //     UserDetails user1 = User
    //             .withDefaultPasswordEncoder()
    //             .username("Gaurav")
    //             .password("Kumar")
    //             .roles("ADMIN")
    //             .build();

    //     UserDetails user2 = User
    //             .withDefaultPasswordEncoder()
    //             .username("wow")
    //             .password("bro")
    //             .roles("USER")
    //             .build();

    //     return new InMemoryUserDetailsManager(user1,user2);    // InMemoryUserDetailsManager is a class that implements UserDetailsService
    // }


    /**
     * What normally happens
     * un-Authenticated Object (Authentication Object)
     *          |
     * Authentication Provider
     *          |
     * Authenticated Object (Authentication Object)
     *
     *
     * BUT I WANT TO USE MY own Authentication Provider
     * it can be used to connect with DB or some LDAP service (Lightweight Directory Access Protocol)
     *
     * For Databases there is DAO Authentication provider (which indirectly implements AuthenticationProvider)
     */

    /*
     * UserDetailsService is just an interface
     * UserDetailsService is used by DaoAuthenticationProvider for retrieving a username, a password,
     * and other attributes for authenticating with a username and password.
     * Spring Security provides in-memory, JDBC, and caching implementations of UserDetailsService.
     *
     * DaoAuthenticationProvider doesn’t fetch users itself — it delegates that job to UserDetailsService.
     *
     * User Details Service only has access to the username in order to retrieve the full user entity
     */
    private MyUserDetailsService userDetailsService;

    public SecurityConfig(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return provider;
    }




    @Bean // custom filterchain for enabling h2 console
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(req -> req
                    .requestMatchers("/h2-console/**").permitAll()
                    .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .headers(header -> header.frameOptions(fo -> fo.disable()))
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())

                .build();
    }

}
