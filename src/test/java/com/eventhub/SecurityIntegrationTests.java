package com.eventhub;

import com.eventhub.config.SecurityConfig;
import com.eventhub.controller.EventController;
import com.eventhub.controller.RegistrationController;
import com.eventhub.controller.UserController;
import com.eventhub.security.CustomUserDetailsService;
import com.eventhub.security.JwtAuthenticationFilter;
import com.eventhub.service.EventService;
import com.eventhub.service.RegistrationService;
import com.eventhub.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({
        EventController.class,
        RegistrationController.class,
        UserController.class
})
@Import(SecurityConfig.class)
class SecurityIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    @MockitoBean
    private RegistrationService registrationService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Test
    void publicEventListingShouldBeAccessibleWithoutAuth() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk());
    }

    @Test
    void registrationsShouldRequireAuthentication() throws Exception {
        mockMvc.perform(get("/api/registrations"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void userShouldNotBeAbleToCreateEvent() throws Exception {
        String body = """
                {
                  "title": "Sec Test Event",
                  "description": "test",
                  "ticketPrice": 25.00,
                  "eventDate": "2026-05-01",
                  "active": true,
                  "categoryId": 1
                }
                """;

        mockMvc.perform(post("/api/events")
                        .contentType(APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    void userShouldNotBeAbleToDeleteEvent() throws Exception {
        mockMvc.perform(delete("/api/events/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminShouldBeAbleToReachAdminUserEndpoints() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }
}