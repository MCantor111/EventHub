package com.eventhub.service.impl;

import com.eventhub.dto.CreateRegistrationDTO;
import com.eventhub.dto.RegistrationDTO;
import com.eventhub.dto.RegistrationItemDTO;
import com.eventhub.dto.UserDTO;
import com.eventhub.exception.AuthenticationException;
import com.eventhub.exception.EventNotFoundException;
import com.eventhub.exception.RegistrationNotFoundException;
import com.eventhub.exception.UserNotFoundException;
import com.eventhub.model.Event;
import com.eventhub.model.Registration;
import com.eventhub.model.RegistrationItem;
import com.eventhub.model.Role;
import com.eventhub.model.User;
import com.eventhub.repository.EventRepository;
import com.eventhub.repository.RegistrationRepository;
import com.eventhub.repository.UserRepository;
import com.eventhub.service.RegistrationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public RegistrationServiceImpl(
            RegistrationRepository registrationRepository,
            UserRepository userRepository,
            EventRepository eventRepository
    ) {
        this.registrationRepository = registrationRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public RegistrationDTO createRegistration(CreateRegistrationDTO dto, String currentUserEmail, boolean isAdmin) {
        User authenticatedUser = userRepository.findByEmailIgnoreCase(currentUserEmail)
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found with email " + currentUserEmail));

        Long effectiveUserId = isAdmin && dto.getUserId() != null
                ? dto.getUserId()
                : authenticatedUser.getId();

        User targetUser = userRepository.findById(effectiveUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + effectiveUserId));

        Registration registration = new Registration();
        registration.setUser(targetUser);
        registration.setStatus(dto.getStatus());

        List<RegistrationItem> items = dto.getItems().stream().map(itemDto -> {
            Event event = eventRepository.findById(itemDto.getEventId())
                    .orElseThrow(() -> new EventNotFoundException("Event not found with id " + itemDto.getEventId()));

            RegistrationItem item = new RegistrationItem();
            item.setRegistration(registration);
            item.setEvent(event);
            item.setQuantity(itemDto.getQuantity());
            item.setTicketPrice(event.getTicketPrice());
            return item;
        }).toList();

        registration.setRegistrationItems(items);
        registration.recalculateTotalAmount();

        Registration saved = registrationRepository.save(registration);
        return toDTO(saved);
    }

    @Override
    public RegistrationDTO getRegistrationById(Long id, String currentUserEmail, boolean isAdmin) {
        Registration registration = registrationRepository.findDetailedById(id)
                .orElseThrow(() -> new RegistrationNotFoundException("Registration not found with id " + id));

        if (!isAdmin && !registration.getUser().getEmail().equalsIgnoreCase(currentUserEmail)) {
            throw new AuthenticationException("You are not allowed to view this registration.");
        }

        return toDTO(registration);
    }

    @Override
    public List<RegistrationDTO> getAccessibleRegistrations(
            Long userId,
            LocalDateTime start,
            LocalDateTime end,
            String currentUserEmail,
            boolean isAdmin
    ) {
        if (isAdmin) {
            if (userId != null) {
                return registrationRepository.findByUserId(userId)
                        .stream()
                        .map(this::toDTO)
                        .toList();
            }

            if (start != null && end != null) {
                return registrationRepository.findByRegistrationDateBetween(start, end)
                        .stream()
                        .map(this::toDTO)
                        .toList();
            }

            return registrationRepository.findAll()
                    .stream()
                    .map(this::toDTO)
                    .toList();
        }

        User authenticatedUser = userRepository.findByEmailIgnoreCase(currentUserEmail)
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found with email " + currentUserEmail));

        return registrationRepository.findByUserId(authenticatedUser.getId())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private RegistrationDTO toDTO(Registration registration) {
        User user = registration.getUser();

        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getRoles().stream().map(Role::getName).sorted().toList()
        );

        List<RegistrationItemDTO> itemDTOs = registration.getRegistrationItems()
                .stream()
                .map(item -> new RegistrationItemDTO(
                        item.getId(),
                        item.getEvent().getId(),
                        item.getEvent().getTitle(),
                        item.getQuantity(),
                        item.getTicketPrice()
                ))
                .toList();

        return new RegistrationDTO(
                registration.getId(),
                registration.getRegistrationDate(),
                registration.getStatus(),
                registration.getTotalAmount(),
                userDTO,
                itemDTOs
        );
    }
}