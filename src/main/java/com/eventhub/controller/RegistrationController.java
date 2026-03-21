package com.eventhub.controller;

import com.eventhub.dto.CreateRegistrationDTO;
import com.eventhub.dto.RegistrationDTO;
import com.eventhub.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Operation(summary = "Create a registration")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RegistrationDTO createRegistration(@Valid @RequestBody CreateRegistrationDTO dto) {
        return registrationService.createRegistration(dto);
    }

    @Operation(summary = "Get all registrations")
    @GetMapping
    public List<RegistrationDTO> getAllRegistrations(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        if (userId != null) {
            return registrationService.getRegistrationsByUserId(userId);
        }

        if (start != null && end != null) {
            return registrationService.getRegistrationsBetween(start, end);
        }

        return registrationService.getAllRegistrations();
    }

    @Operation(summary = "Get registration by id")
    @GetMapping("/{id}")
    public RegistrationDTO getRegistrationById(@PathVariable Long id) {
        return registrationService.getRegistrationById(id);
    }
}