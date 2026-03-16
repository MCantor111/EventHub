package com.eventhub.service;

import com.eventhub.dto.CreateEventDTO;
import com.eventhub.dto.EventDTO;
import com.eventhub.dto.PagedResponse;

public interface EventService {
    EventDTO createEvent(CreateEventDTO dto);
    EventDTO getEventById(Long id);
    PagedResponse<EventDTO> getAllEvents(
            int page,
            int size,
            String sort,
            String category,
            Double minPrice,
            Double maxPrice,
            String startDate,
            String endDate
    );
}