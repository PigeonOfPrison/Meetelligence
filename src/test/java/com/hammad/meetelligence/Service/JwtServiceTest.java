package com.hammad.meetelligence.Service;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtServiceTest {

    private final JwtService jwtService;

    @Autowired
    public JwtServiceTest(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Test
    void shouldGenerateToken() {

        String token = jwtService.generateToken("test@example.com");

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void shouldExtractUsername() {

        String token = jwtService.generateToken("test@example.com");
        String username = jwtService.extractUsername(token);

        assertNotNull(username);
        assertEquals("test@example.com", username);
    }

    @Test
    void shouldValidateToken() {

        String token =
                jwtService.generateToken(
                        "test@example.com"
                );

        UserDetails userDetails = User
                .builder()
                .username("test@example.com")
                .password("password")
                .authorities("USER")
                .build();

        assertTrue(
                jwtService.isTokenValid(
                        token,
                        userDetails
                )
        );
    }

    @Test
    void shouldRejectTokenForDifferentUser() {

        String token =
                jwtService.generateToken(
                        "alice@example.com"
                );

        UserDetails userDetails =
                org.springframework.security.core.userdetails.User
                        .builder()
                        .username("bob@example.com")
                        .password("password")
                        .authorities("USER")
                        .build();

        assertFalse(
                jwtService.isTokenValid(
                        token,
                        userDetails
                )
        );
    }

    @Test
    void shouldContainExpirationClaim() {

        String token =
                jwtService.generateToken(
                        "test@example.com"
                );

        Claims claims =
                jwtService.extractAllClaims(token);

        assertNotNull(
                claims.getExpiration()
        );
    }
}
