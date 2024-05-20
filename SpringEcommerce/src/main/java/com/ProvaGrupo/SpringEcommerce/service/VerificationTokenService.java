package com.ProvaGrupo.SpringEcommerce.service;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProvaGrupo.SpringEcommerce.model.VerificationToken;
import com.ProvaGrupo.SpringEcommerce.repository.VerificationTokenRepository;

@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    public VerificationToken createToken(Long userId) {
        String tokenValue = UUID.randomUUID().toString(); // Gera um token aleatório
        Instant expiryDate = Instant.now().plusSeconds(86400); // Expira em 24 horas (ajuste conforme necessário)

        VerificationToken token = new VerificationToken();
        token.setToken(tokenValue);
        token.setUserId(userId);
        token.setExpiryDate(expiryDate);

        return tokenRepository.save(token);
    }

    public Optional<VerificationToken> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void deleteToken(VerificationToken token) {
        tokenRepository.delete(token);
    }

    public void deleteExpiredTokens() {
        tokenRepository.deleteByExpiryDateBefore(Instant.now());
    }
    
    public boolean isTokenExpired(VerificationToken token) {
        return token.getExpiryDate().isBefore(Instant.now());
    }

    public void updateExpiryDate(VerificationToken token, Instant newExpiryDate) {
        token.setExpiryDate(newExpiryDate);
        tokenRepository.save(token);
    }

    public List<VerificationToken> getTokensByUserId(Long userId) {
        return tokenRepository.findByUserId(userId);
    }

}
