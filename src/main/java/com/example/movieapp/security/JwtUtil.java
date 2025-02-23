package com.example.movieapp.security;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
@Component
public class JwtUtil {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000;

    public String generateToken(String email,String role){
        return Jwts.builder()
                .setSubject(email)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }
    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
    public String extractRole(String token){
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token).getBody().get("role",String.class);
    }
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                    .parseClaimsJws(token);
            return true;
        } catch(JwtException e){
            return false;
        }
    }
}

