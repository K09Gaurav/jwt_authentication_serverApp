package com.spring.jwt_auth_prog.service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {


    /*
    * token should be based on few things like
    * username ,issue, date, expiry etc
    */
    public String generateToken(String username) {
        Map<String,Object> claims = new HashMap<>();

        return Jwts
            .builder()
            .claims()
            .add(claims)
            .subject(username)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() *60 *60 *10))
            .and()
            .signWith(getKey())
            .compact();

    }

    private String SecretKey = "bG1hbyB3aGF0IGEgbm9vYmRhIHdoYXQgYXdvdyBkYW1uIGJybyBreWEga2FycmEgYWFqIGthbCBqPWt1Y2hnIG5haGkgYmhhaQ==";
    private Key getKey() {
        byte[] bytes = Decoders.BASE64.decode(SecretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

}
