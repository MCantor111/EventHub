package com.eventhub.service;

import com.eventhub.dto.CreateEventDTO;
import com.eventhub.dto.EventDTO;
import com.eventhub.dto.PagedResponse;

import java.math.BigDecimal;

public interface EventService {

    EventDTO createEvent(CreateEventDTO dto);

    EventDTO updateEvent(Long id, CreateEventDTO dto);

    void deleteEvent(Long id);

    EventDTO getEventById(Long id);

    PagedResponse<EventDTO> getAllEvents(
            int page,
            int size,
            String sort,
            String category,
            Double minPrice,
            Double maxPrice,
            String startDate,
            String endDate,
            Boolean active,
            String keyword
    );

    Long countRegistrationsForEvent(Long eventId);

    BigDecimal totalRevenueForEvent(Long eventId);
}