package com.eventhub;

import com.eventhub.config.SecurityConfig;
import com.eventhub.controller.AuthController;
import com.eventhub.security.CustomUserDetailsService;
import com.eventhub.security.JwtAuthenticationFilter;
import com.eventhub.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class PasswordResetTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Test
    void passwordResetRequestShouldReturnToken() throws Exception {
        when(authService.requestPasswordReset(any()))
                .thenReturn(Map.of(
                        "message", "Password reset token generated successfully.",
                        "resetToken", "abc123",
                        "expiresAt", "2026-03-26T21:00:00"
                ));

        String body = """
                {
                  "email": "cantor@example.com"
                }
                """;

        mockMvc.perform(post("/api/auth/password-reset-request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resetToken").value("abc123"));
    }

    @Test
    void passwordResetConfirmShouldReturnSuccess() throws Exception {
        when(authService.confirmPasswordReset(any()))
                .thenReturn(Map.of("message", "Password reset successfully."));

        String body = """
                {
                  "token": "abc123",
                  "newPassword": "newpass123"
                }
                """;

        mockMvc.perform(post("/api/auth/password-reset-confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Password reset successfully."));
    }
}