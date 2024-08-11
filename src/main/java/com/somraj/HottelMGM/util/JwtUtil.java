package com.somraj.HottelMGM.util;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    
    //First Stage he Karayach ahe 

    private final String SecreteKey = "7d14bcb025e0f87ce9e09c1b415a9bc8ad6f5655c0ec2cdcfdcf0b8e9a6aeb72";


    public String generateToken(UserDetails details){
        String token = Jwts.builder().subject(details.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000))
        .signWith(getSignKey())
        .compact();
        
        return token;
    }

    private SecretKey getSignKey(){
        byte[]  keyBytes = Decoders.BASE64URL.decode(SecreteKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(getSignKey())
                    .build().parseSignedClaims(token)
                    .getPayload();
    }

    public <T> T extractClaims(String token, Function<Claims, T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public boolean isValid(String token , UserDetails user){
        String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

}
