package com.ProvaGrupo.SpringEcommerce.service;

import com.ProvaGrupo.SpringEcommerce.model.VerificationToken;
import com.ProvaGrupo.SpringEcommerce.repository.VerificationTokenRepository;
import com.ProvaGrupo.SpringEcommerce.security.JwtProviderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerificationTokenServiceTest {

    @Mock
    private VerificationTokenRepository tokenRepository;

    @Mock
    private JwtProviderService jwtProviderService;

    @InjectMocks
    private VerificationTokenService tokenService;

    private Authentication authentication;

    @BeforeEach
    void setUp() {
        authentication = new UsernamePasswordAuthenticationToken("user", "password");
    }

    @Test
    void testCreateToken() {
        String tokenValue = "generatedToken";
        when(jwtProviderService.generateToken(any(Authentication.class), anyLong())).thenReturn(tokenValue);
        VerificationToken savedToken = new VerificationToken(1L, tokenValue, 1L, Instant.now().plusSeconds(VerificationTokenService.EXPIRATION_TIME_IN_SECONDS));
        when(tokenRepository.save(any(VerificationToken.class))).thenReturn(savedToken);

        VerificationToken result = tokenService.createToken(authentication, 1L);

        assertNotNull(result);
        assertEquals(tokenValue, result.getToken());
        assertEquals(1L, result.getUserId());
        assertNotNull(result.getExpiryDate());
        verify(jwtProviderService, times(1)).generateToken(authentication, VerificationTokenService.EXPIRATION_TIME_IN_SECONDS);
        verify(tokenRepository, times(1)).save(any(VerificationToken.class));
    }

    @Test
    void testGetToken() {
        VerificationToken token = new VerificationToken(1L, "tokenValue", 1L, Instant.now());
        when(tokenRepository.findByToken("tokenValue")).thenReturn(Optional.of(token));

        Optional<VerificationToken> result = tokenService.getToken("tokenValue");

        assertTrue(result.isPresent());
        assertEquals(token, result.get());
    }

    @Test
    void testDeleteToken() {
        VerificationToken token = new VerificationToken(1L, "tokenValue", 1L, Instant.now());

        tokenService.deleteToken(token);

        verify(tokenRepository, times(1)).delete(token);
    }
}