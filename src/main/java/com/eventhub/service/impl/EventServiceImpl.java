package com.eventhub.service.impl;

import com.eventhub.dto.CategoryDTO;
import com.eventhub.dto.CreateEventDTO;
import com.eventhub.dto.EventDTO;
import com.eventhub.dto.PagedResponse;
import com.eventhub.exception.CategoryNotFoundException;
import com.eventhub.exception.EventNotFoundException;
import com.eventhub.model.Category;
import com.eventhub.model.Event;
import com.eventhub.repository.CategoryRepository;
import com.eventhub.repository.EventRepository;
import com.eventhub.service.EventService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    public EventServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public EventDTO createEvent(CreateEventDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id " + dto.getCategoryId()));

        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setTicketPrice(dto.getTicketPrice());
        event.setEventDate(dto.getEventDate());
        event.setActive(dto.getActive() != null ? dto.getActive() : true);
        event.setCategory(category);

        Event saved = eventRepository.save(event);
        return toDTO(saved);
    }

    @Override
    @Cacheable(value = "events", key = "#id")
    public EventDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id " + id));
        return toDTO(event);
    }

    @Override
    @Cacheable(value = "eventsList", key = "#page + '-' + #size + '-' + #sort + '-' + #category + '-' + #minPrice + '-' + #maxPrice + '-' + #startDate + '-' + #endDate + '-' + #active + '-' + #keyword")
    public PagedResponse<EventDTO> getAllEvents(
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
    ) {
        String[] sortParts = sort.split(",");
        String sortField = sortParts[0];
        String direction = sortParts.length > 1 ? sortParts[1] : "asc";

        Sort sorting = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        Pageable pageable = PageRequest.of(page, size, sorting);

        BigDecimal min = minPrice != null ? BigDecimal.valueOf(minPrice) : BigDecimal.ZERO;
        BigDecimal max = maxPrice != null ? BigDecimal.valueOf(maxPrice) : new BigDecimal("999999999");
        LocalDate start = startDate != null ? LocalDate.parse(startDate) : LocalDate.of(1900, 1, 1);
        LocalDate end = endDate != null ? LocalDate.parse(endDate) : LocalDate.of(2999, 12, 31);

        Page<Event> eventPage;

        if (keyword != null && !keyword.isBlank()) {
            eventPage = eventRepository.searchEvents(keyword, blankToNull(category), pageable);
        } else if (category != null && !category.isBlank()) {
            eventPage = eventRepository.findByCategory_NameIgnoreCaseAndTicketPriceBetweenAndEventDateBetween(
                    category, min, max, start, end, pageable
            );
        } else if (Boolean.TRUE.equals(active)) {
            eventPage = eventRepository.findByActiveTrue(pageable);
        } else if (minPrice != null || maxPrice != null) {
            eventPage = eventRepository.findByTicketPriceBetween(min, max, pageable);
        } else if (startDate != null || endDate != null) {
            eventPage = eventRepository.findByEventDateBetween(start, end, pageable);
        } else {
            eventPage = eventRepository.findAll(pageable);
        }

        return new PagedResponse<>(
                eventPage.getContent().stream().map(this::toDTO).toList(),
                eventPage.getNumber(),
                eventPage.getSize(),
                eventPage.getTotalElements(),
                eventPage.getTotalPages(),
                eventPage.isLast()
        );
    }

    @Override
    public Long countRegistrationsForEvent(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new EventNotFoundException("Event not found with id " + eventId);
        }
        return eventRepository.countRegistrationsForEvent(eventId);
    }

    @Override
    public BigDecimal totalRevenueForEvent(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new EventNotFoundException("Event not found with id " + eventId);
        }
        return eventRepository.totalRevenueForEvent(eventId);
    }

    private EventDTO toDTO(Event event) {
        CategoryDTO categoryDTO = new CategoryDTO(
                event.getCategory().getId(),
                event.getCategory().getName()
        );

        return new EventDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getTicketPrice(),
                event.getEventDate(),
                event.getActive(),
                event.getCreatedAt(),
                categoryDTO
        );
    }

    private String blankToNull(String value) {
        return (value == null || value.isBlank()) ? null : value;
    }
}