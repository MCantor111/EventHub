package com.eventhub.controller;

import com.eventhub.dto.CreateEventDTO;
import com.eventhub.dto.EventDTO;
import com.eventhub.dto.PagedResponse;
import com.eventhub.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Create an event")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EventDTO createEvent(@Valid @RequestBody CreateEventDTO dto) {
        return eventService.createEvent(dto);
    }

    @Operation(summary = "Get event by id")
    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @Operation(summary = "List events with pagination, filtering, and sorting")
    @GetMapping
    public PagedResponse<EventDTO> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "title,asc") String sort,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String keyword
    ) {
        return eventService.getAllEvents(
                page,
                size,
                sort,
                category,
                minPrice,
                maxPrice,
                startDate,
                endDate,
                active,
                keyword
        );
    }

    @Operation(summary = "Get registration count for an event")
    @GetMapping("/{id}/registration-count")
    public Map<String, Object> getRegistrationCount(@PathVariable Long id) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("eventId", id);
        response.put("registrationCount", eventService.countRegistrationsForEvent(id));
        return response;
    }

    @Operation(summary = "Get total revenue for an event")
    @GetMapping("/{id}/revenue")
    public Map<String, Object> getEventRevenue(@PathVariable Long id) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("eventId", id);
        response.put("totalRevenue", eventService.totalRevenueForEvent(id));
        return response;
    }
}