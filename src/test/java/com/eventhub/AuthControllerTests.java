package com.eventhub;

import com.eventhub.config.SecurityConfig;
import com.eventhub.controller.AuthController;
import com.eventhub.dto.AuthResponseDTO;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTests {

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
    void loginShouldReturnToken() throws Exception {
        AuthResponseDTO response = new AuthResponseDTO(
                "fake-jwt-token",
                1L,
                "Cantor",
                "cantor@example.com",
                List.of("ROLE_USER")
        );

        when(authService.login(any())).thenReturn(response);

        String body = """
                {
                  "email": "cantor@example.com",
                  "password": "password"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"))
                .andExpect(jsonPath("$.email").value("cantor@example.com"));
    }

    @Test
    void registerShouldReturnCreated() throws Exception {
        AuthResponseDTO response = new AuthResponseDTO(
                "new-user-token",
                10L,
                "New User",
                "new@example.com",
                List.of("ROLE_USER")
        );

        when(authService.register(any())).thenReturn(response);

        String body = """
                {
                  "name": "New User",
                  "email": "new@example.com",
                  "password": "secret123"
                }
                """;

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("new-user-token"))
                .andExpect(jsonPath("$.userId").value(10));
    }
}