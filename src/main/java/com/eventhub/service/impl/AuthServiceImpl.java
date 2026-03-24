package com.eventhub.service.impl;

import com.eventhub.dto.AuthResponseDTO;
import com.eventhub.dto.LoginRequestDTO;
import com.eventhub.dto.PasswordResetConfirmDTO;
import com.eventhub.dto.PasswordResetRequestDTO;
import com.eventhub.dto.RegisterRequestDTO;
import com.eventhub.exception.AuthenticationException;
import com.eventhub.exception.InvalidTokenException;
import com.eventhub.model.Role;
import com.eventhub.model.User;
import com.eventhub.repository.RoleRepository;
import com.eventhub.repository.UserRepository;
import com.eventhub.security.CustomUserDetailsService;
import com.eventhub.security.JwtService;
import com.eventhub.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @Value("${app.password-reset.expiration-minutes}")
    private long passwordResetExpirationMinutes;

    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService,
                           CustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new AuthenticationException("A user with that email already exists.");
        }

        Role userRole = roleRepository.findByNameIgnoreCase("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Default role ROLE_USER not found."));

        User user = new User();
        user.setName(request.getName().trim());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        user.getRoles().add(userRole);

        User savedUser = userRepository.save(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(savedUser.getEmail());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponseDTO(
                token,
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRoles().stream().map(Role::getName).sorted().toList()
        );
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail().trim().toLowerCase(),
                            request.getPassword()
                    )
            );
        } catch (org.springframework.security.core.AuthenticationException ex) {
            throw new AuthenticationException("Invalid email or password.");
        }

        User user = userRepository.findByEmailIgnoreCase(request.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new AuthenticationException("Invalid email or password."));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponseDTO(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles().stream().map(Role::getName).sorted().toList()
        );
    }

    @Override
    public Map<String, String> requestPasswordReset(PasswordResetRequestDTO request) {
        User user = userRepository.findByEmailIgnoreCase(request.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new AuthenticationException("No user found with that email."));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(passwordResetExpirationMinutes));
        userRepository.save(user);

        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", "Password reset token generated successfully.");
        response.put("resetToken", token);
        response.put("expiresAt", user.getResetTokenExpiry().toString());
        return response;
    }

    @Override
    public Map<String, String> confirmPasswordReset(PasswordResetConfirmDTO request) {
        User user = userRepository.findByResetToken(request.getToken())
                .orElseThrow(() -> new InvalidTokenException("Invalid reset token."));

        if (user.getResetTokenExpiry() == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Reset token has expired.");
        }

        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);

        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", "Password reset successfully.");
        return response;
    }
}