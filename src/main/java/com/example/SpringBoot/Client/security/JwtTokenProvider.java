package com.example.SpringBoot.Client.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration}")
    private long expiration;

    private Key getKey() {
        log.debug("Generating signing key for JWT");
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateToken(String username, String role) {
        log.info("Generating JWT token for user: {} with role: {}", username, role);
        String token = Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

        log.debug("JWT token generated successfully for user: {}", username);
        return token;
    }

    public String getUsername(String token) {

        String username = getClaims(token).getSubject();
        log.debug("Extracted username from JWT: {}", username);
        return username;
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            log.debug("JWT token validation successful");
            return true;
        } catch (JwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("JWT token is null or empty");
        }
        return false;
    }

    private Claims getClaims(String token) {
        log.debug("Parsing JWT token to extract claims");
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
