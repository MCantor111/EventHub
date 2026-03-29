package com.eventhub.controller;

import com.eventhub.dto.ai.AiChatRequest;
import com.eventhub.dto.ai.AiChatResponse;
import com.eventhub.dto.ai.AiHealthResponse;
import com.eventhub.dto.ai.EventDescriptionRequest;
import com.eventhub.dto.ai.EventDescriptionResponse;
import com.eventhub.dto.ai.EventScheduleRequest;
import com.eventhub.dto.ai.EventScheduleResponse;
import com.eventhub.dto.ai.EventTagSuggestionRequest;
import com.eventhub.dto.ai.StructuredEventDescriptionRequest;
import com.eventhub.dto.ai.StructuredEventDescriptionResponse;
import com.eventhub.dto.ai.TagSuggestionResponse;
import com.eventhub.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @Operation(summary = "Check whether the AI service is available")
    @GetMapping("/health")
    public AiHealthResponse healthCheck() {
        return aiService.healthCheck();
    }

    @Operation(summary = "Send a general-purpose prompt to the AI service")
    @PostMapping("/chat")
    @ResponseStatus(HttpStatus.OK)
    public AiChatResponse chat(@Valid @RequestBody AiChatRequest request) {
        return aiService.chat(request);
    }

    @Operation(summary = "Generate a marketing-quality event description")
    @PostMapping("/event-description")
    @ResponseStatus(HttpStatus.OK)
    public EventDescriptionResponse generateEventDescription(
            @Valid @RequestBody EventDescriptionRequest request
    ) {
        return aiService.generateEventDescription(request);
    }

    @Operation(summary = "Generate a structured event description")
    @PostMapping("/event-description/structured")
    @ResponseStatus(HttpStatus.OK)
    public StructuredEventDescriptionResponse generateStructuredEventDescription(
            @Valid @RequestBody StructuredEventDescriptionRequest request
    ) {
        return aiService.generateStructuredEventDescription(request);
    }

    @Operation(summary = "Suggest structured event tags")
    @PostMapping("/event-tags")
    @ResponseStatus(HttpStatus.OK)
    public TagSuggestionResponse suggestEventTags(
            @Valid @RequestBody EventTagSuggestionRequest request
    ) {
        return aiService.suggestEventTags(request);
    }

    @Operation(summary = "Generate a suggested multi-session event schedule")
    @PostMapping("/event-schedule")
    @ResponseStatus(HttpStatus.OK)
    public EventScheduleResponse generateEventSchedule(
            @Valid @RequestBody EventScheduleRequest request
    ) {
        return aiService.generateEventSchedule(request);
    }
}