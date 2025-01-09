package com.TP.Spring.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TokenGeneratorTest {

    @InjectMocks
    private TokenGenerator tokenGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Set test values using reflection
        ReflectionTestUtils.setField(tokenGenerator, "jwtSecret", "testSecretKey");
        ReflectionTestUtils.setField(tokenGenerator, "jwtExpirationMs", 3600000);
    }

    @Test
    void testGenerateJwtToken() {
        // Create mock user details
        UserDetails userDetails = new User("testuser", "password", Collections.emptyList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities()
        );

        // Generate token
        String token = tokenGenerator.generateJwtToken(authentication);

        // Assertions
        assertNotNull(token);
        assertTrue(token.length() > 0);
        System.out.println("Generated Token: " + token);
    }
}
