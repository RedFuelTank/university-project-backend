package com.example.main.config.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
public class JwtTokenProvider {
    @Autowired
    private JwtConfig jwtConfig;

    public String generateToken(String subject) {
        long currentTimeMs = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(subject)
                .addClaims(new HashMap<>())
                .setIssuedAt(new Date(currentTimeMs))
                .setExpiration(new Date(currentTimeMs + jwtConfig.getDurationInMs()))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();
    }

    public boolean isValid(String token, String user) {
        return getUsernameFromToken(token).equals(user) && hasExpired(token);
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean hasExpired(String token) {
        Date date = Jwts.parser()
                .setSigningKey(jwtConfig.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return date.after(new Date());

    }


    public static void main(String[] args) {
        long currentTimeMs = System.currentTimeMillis();
        String jwtToken = Jwts.builder()
                .setSubject("danila")
                .addClaims(new HashMap<>())
                .setIssuedAt(new Date(currentTimeMs))
                .setExpiration(new Date(currentTimeMs + 5 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, "c2VjcmV0")
                .compact();

        System.out.println(jwtToken);

    }
}
