package com.eventhub.service;

import com.eventhub.dto.CreateRegistrationDTO;
import com.eventhub.dto.RegistrationDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface RegistrationService {
    RegistrationDTO createRegistration(CreateRegistrationDTO dto);

    RegistrationDTO getRegistrationById(Long id);

    List<RegistrationDTO> getAllRegistrations();

    List<RegistrationDTO> getRegistrationsByUserId(Long userId);

    List<RegistrationDTO> getRegistrationsBetween(LocalDateTime start, LocalDateTime end);
}