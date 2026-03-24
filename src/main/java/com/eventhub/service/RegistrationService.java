package com.eventhub.service;

import com.eventhub.dto.CreateRegistrationDTO;
import com.eventhub.dto.RegistrationDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface RegistrationService {

    RegistrationDTO createRegistration(CreateRegistrationDTO dto, String currentUserEmail, boolean isAdmin);

    RegistrationDTO getRegistrationById(Long id, String currentUserEmail, boolean isAdmin);

    List<RegistrationDTO> getAccessibleRegistrations(
            Long userId,
            LocalDateTime start,
            LocalDateTime end,
            String currentUserEmail,
            boolean isAdmin
    );
}