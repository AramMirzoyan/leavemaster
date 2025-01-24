package com.leave.master.leavemaster.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import java.time.Instant;
import java.util.Base64;
import java.util.Map;

@TestConfiguration
public class TestJwtDecoder implements JwtDecoder {
    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new JwtException("Invalid token format");
            }
            String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

            Map<String, Object> headers = objectMapper.readValue(headerJson, new TypeReference<>() {
            });
            Map<String, Object> claims = objectMapper.readValue(payloadJson, new TypeReference<>() {
            });

            Instant issuedAt = convertToInstant(claims.get("iat"));
            Instant expiresAt = convertToInstant(claims.get("exp"));

            return Jwt.withTokenValue(token)
                    .headers(h -> h.putAll(headers))
                    .claims(c->c.putAll(claims))
                    .issuedAt(issuedAt)
                    .expiresAt(expiresAt)
                    .build();
        } catch (Exception e) {
            throw new JwtException("Failed to decode jwt token", e);
        }

    }


    private Instant convertToInstant(Object timestamp) {
        if (timestamp == null) {
            return null;
        }
        if (timestamp instanceof Integer) {
            return Instant.ofEpochSecond((Integer) timestamp);
        }
        if (timestamp instanceof Long) {
            return Instant.ofEpochSecond((Long) timestamp);
        }
        throw new JwtException("Invalid timestamp format: " + timestamp);
    }
}
