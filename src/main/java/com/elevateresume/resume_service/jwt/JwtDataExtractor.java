package com.elevateresume.resume_service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtDataExtractor {

    private static final String USER_ID = "userId";
    private final JwtConfigurationProperties jwtConfigurationProperties;

    private Claims extractClaims(String token) {
        String secretKey = jwtConfigurationProperties.getSecretKey();

        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserId(String token) {
        return extractClaims(token).get(USER_ID, String.class);
    }
}
