package com.eventhub.service.impl;

import com.eventhub.dto.CreateRegistrationDTO;
import com.eventhub.dto.RegistrationDTO;
import com.eventhub.dto.RegistrationItemDTO;
import com.eventhub.dto.UserDTO;
import com.eventhub.exception.EventNotFoundException;
import com.eventhub.exception.RegistrationNotFoundException;
import com.eventhub.exception.UserNotFoundException;
import com.eventhub.model.Event;
import com.eventhub.model.Registration;
import com.eventhub.model.RegistrationItem;
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
    public RegistrationDTO createRegistration(CreateRegistrationDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + dto.getUserId()));

        Registration registration = new Registration();
        registration.setUser(user);
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
    public RegistrationDTO getRegistrationById(Long id) {
        Registration registration = registrationRepository.findDetailedById(id)
                .orElseThrow(() -> new RegistrationNotFoundException("Registration not found with id " + id));
        return toDTO(registration);
    }

    @Override
    public List<RegistrationDTO> getAllRegistrations() {
        return registrationRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public List<RegistrationDTO> getRegistrationsByUserId(Long userId) {
        return registrationRepository.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public List<RegistrationDTO> getRegistrationsBetween(LocalDateTime start, LocalDateTime end) {
        return registrationRepository.findByRegistrationDateBetween(start, end)
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
                user.getCreatedAt()
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