package com.spring.jwt_auth_prog.service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
    // private String secretKey = "bG1hbyB3aGF0IGEgbm9vYmRhIHdoYXQgYXdvdyBkYW1uIGJybyBreWEga2FycmEgYWFqIGthbCBqPWt1Y2hnIG5haGkgYmhhaQ==";
    private String secretKey;


    public JWTService() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secret = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(secret.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

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
            .expiration(new Date(System.currentTimeMillis() + (100 *60*60*10))) //1hr
            .and()
            .signWith(getKey())
            .compact();

    }

    private SecretKey getKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String extractUsername(String token) {
        /*
         * not simple
         * decode it
         * fetch claims
         * fetch username then return it
         */
        return extractClaims(token, Claims::getSubject);// get subject is the name of function (methd referencing)
    }
    private <T> T extractClaims(String token, Function<Claims,T> claimmResolver){
        final Claims claims = extractAllClaims(token);
        return claimmResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        // return Jwts
        //         .parser().setSigningKey(getKey()).build().parseClaimsJws(token).getBody(); //old af need to update depreciated methods
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }





    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

}
