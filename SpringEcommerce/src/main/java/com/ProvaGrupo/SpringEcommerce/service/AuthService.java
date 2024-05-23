package com.ProvaGrupo.SpringEcommerce.service;

import com.ProvaGrupo.SpringEcommerce.auth.repository.UserRepository;
import com.ProvaGrupo.SpringEcommerce.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public Optional<Users> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.isAuthenticated())
            return Optional.empty();

        String username = authentication.getName();
        return Optional.ofNullable(loadUserByUsername(username));
    }

    private Users loadUserByUsername(String username){
        UserDetails user = userRepository.findByLogin(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");
        return null;
    }
}
