package com.spring.jwt_auth_prog.config;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.jwt_auth_prog.service.JWTService;
import com.spring.jwt_auth_prog.service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
    private JWTService jwt;
    private ApplicationContext context;

    public JwtFilter(JWTService jwt,ApplicationContext context) {
        this.jwt = jwt;
        this.context = context;
    }

    /*
     * when rcieving request from client we get
     * "Bearer ajsdhbfashugfdblongassTokensljdhfb"
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                String authHeader = request.getHeader("Authorization");
                String token = null;
                String username = null;

                if (authHeader != null && authHeader.startsWith("Bearer ") ){
                    token = authHeader.substring(7);
                    username = jwt.extractUsername(token);
                }

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
                    if (jwt.validateToken(token, userDetails)){
                        UsernamePasswordAuthenticationToken UserPasstoken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        UserPasstoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(UserPasstoken);
                    }
                }
                filterChain.doFilter(request, response);
    }

}
