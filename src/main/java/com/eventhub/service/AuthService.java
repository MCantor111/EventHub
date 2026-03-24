package com.eventhub.service;

import com.eventhub.dto.AuthResponseDTO;
import com.eventhub.dto.LoginRequestDTO;
import com.eventhub.dto.PasswordResetConfirmDTO;
import com.eventhub.dto.PasswordResetRequestDTO;
import com.eventhub.dto.RegisterRequestDTO;

import java.util.Map;

public interface AuthService {

    AuthResponseDTO register(RegisterRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);

    Map<String, String> requestPasswordReset(PasswordResetRequestDTO request);

    Map<String, String> confirmPasswordReset(PasswordResetConfirmDTO request);
}