package com.eventhub.service.impl;

import com.eventhub.config.AiPromptConfig;
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
import com.eventhub.util.AiOutputValidator;
import com.eventhub.util.PromptSanitizer;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiServiceImpl implements AiService {

    private final ChatClient chatClient;
    private final AiPromptConfig aiPromptConfig;
    private final PromptSanitizer promptSanitizer;
    private final AiOutputValidator aiOutputValidator;

    public AiServiceImpl(
            ChatClient.Builder chatClientBuilder,
            AiPromptConfig aiPromptConfig,
            PromptSanitizer promptSanitizer,
            AiOutputValidator aiOutputValidator
    ) {
        this.chatClient = chatClientBuilder.build();
        this.aiPromptConfig = aiPromptConfig;
        this.promptSanitizer = promptSanitizer;
        this.aiOutputValidator = aiOutputValidator;
    }

    @Override
    public AiHealthResponse healthCheck() {
        try {
            String response = chatClient.prompt()
                    .system(aiPromptConfig.getSystemPrompt())
                    .user("Reply with exactly: AI service is available.")
                    .call()
                    .content();

            boolean safe = aiOutputValidator.isSafeText(response);
            if (!safe) {
                return new AiHealthResponse(false, "AI service responded, but the output was not considered safe.");
            }

            return new AiHealthResponse(true, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new AiHealthResponse(false, "AI service is unavailable: " + ex.getMessage());
        }
    }

    @Override
    public AiChatResponse chat(AiChatRequest request) {
        try {
            String sanitizedPrompt = promptSanitizer.sanitize(request.getPrompt());

            String userPrompt = renderTemplate(
                    aiPromptConfig.getGeneralChatUserPromptTemplate(),
                    Map.of("prompt", sanitizedPrompt)
            );

            String response = chatClient.prompt()
                    .system(aiPromptConfig.getSystemPrompt())
                    .user(userPrompt)
                    .call()
                    .content();

            if (!aiOutputValidator.isSafeText(response)) {
                return new AiChatResponse(aiOutputValidator.safeFallbackText());
            }

            return new AiChatResponse(response);
        } catch (Exception ex) {
            ex.printStackTrace();

            return new AiChatResponse(
                    "AI service is currently unavailable due to quota limits. Please try again later."
            );
        }
    }

    @Override
    public EventDescriptionResponse generateEventDescription(EventDescriptionRequest request) {
        Map<String, Object> variables = new LinkedHashMap<>();
        variables.put("eventName", promptSanitizer.sanitize(request.getEventName()));
        variables.put("category", promptSanitizer.sanitize(request.getCategory()));
        variables.put("location", promptSanitizer.sanitize(request.getLocation()));
        variables.put("date", request.getDate().toString());
        variables.put("keywords", promptSanitizer.sanitizeAndJoin(request.getKeywords()));

        String userPrompt = renderTemplate(
                aiPromptConfig.getEventDescriptionPromptTemplate(),
                variables
        );

        String response = chatClient.prompt()
                .system(aiPromptConfig.getSystemPrompt())
                .user(userPrompt)
                .call()
                .content();

        if (!aiOutputValidator.isSafeText(response)) {
            return new EventDescriptionResponse(aiOutputValidator.safeFallbackText());
        }

        return new EventDescriptionResponse(response);
    }

    @Override
    public StructuredEventDescriptionResponse generateStructuredEventDescription(
            StructuredEventDescriptionRequest request
    ) {
        Map<String, Object> variables = new LinkedHashMap<>();
        variables.put("eventName", promptSanitizer.sanitize(request.getEventName()));
        variables.put("category", promptSanitizer.sanitize(request.getCategory()));
        variables.put("location", promptSanitizer.sanitize(request.getLocation()));
        variables.put("date", request.getDate().toString());
        variables.put("keywords", promptSanitizer.sanitizeAndJoin(request.getKeywords()));

        String userPrompt = renderTemplate(
                aiPromptConfig.getStructuredEventDescriptionPromptTemplate(),
                variables
        );

        StructuredEventDescriptionResponse response = chatClient.prompt()
                .system(aiPromptConfig.getSystemPrompt())
                .user(userPrompt)
                .call()
                .entity(StructuredEventDescriptionResponse.class);

        if (!aiOutputValidator.isValidStructuredDescription(response)) {
            return buildStructuredDescriptionFallback();
        }

        return response;
    }

    @Override
    public TagSuggestionResponse suggestEventTags(EventTagSuggestionRequest request) {
        Map<String, Object> variables = new LinkedHashMap<>();
        variables.put("examples", aiPromptConfig.getTagSuggestionFewShotExamples());
        variables.put("eventName", promptSanitizer.sanitize(request.getEventName()));
        variables.put("category", promptSanitizer.sanitize(request.getCategory()));
        variables.put("location", promptSanitizer.sanitize(request.getLocation()));
        variables.put("date", request.getDate().toString());
        variables.put("description", promptSanitizer.sanitize(request.getDescription()));
        variables.put("keywords", promptSanitizer.sanitizeAndJoin(request.getKeywords()));

        String userPrompt = renderTemplate(
                aiPromptConfig.getTagSuggestionFewShotPromptTemplate(),
                variables
        );

        TagSuggestionResponse response = chatClient.prompt()
                .system(aiPromptConfig.getSystemPrompt())
                .user(userPrompt)
                .call()
                .entity(TagSuggestionResponse.class);

        if (!aiOutputValidator.isValidTagSuggestionResponse(response)) {
            return new TagSuggestionResponse(List.of("event", "community", "featured"));
        }

        return response;
    }

    @Override
    public EventScheduleResponse generateEventSchedule(EventScheduleRequest request) {
        Map<String, Object> variables = new LinkedHashMap<>();
        variables.put("eventName", promptSanitizer.sanitize(request.getEventName()));
        variables.put("category", promptSanitizer.sanitize(request.getCategory()));
        variables.put("location", promptSanitizer.sanitize(request.getLocation()));
        variables.put("date", request.getDate().toString());
        variables.put("sessionCount", request.getSessionCount());
        variables.put("durationMinutes", request.getDurationMinutes());
        variables.put("specialRequirements", promptSanitizer.sanitize(request.getSpecialRequirements()));
        variables.put("keywords", promptSanitizer.sanitizeAndJoin(request.getKeywords()));

        String userPrompt = renderTemplate(
                aiPromptConfig.getEventSchedulePromptTemplate(),
                variables
        );

        EventScheduleResponse response = chatClient.prompt()
                .system(aiPromptConfig.getSystemPrompt())
                .user(userPrompt)
                .call()
                .entity(EventScheduleResponse.class);

        if (!aiOutputValidator.isValidScheduleResponse(response)) {
            return new EventScheduleResponse(List.of());
        }

        return response;
    }

    private String renderTemplate(String template, Map<String, Object> variables) {
        PromptTemplate promptTemplate = new PromptTemplate(template);
        return promptTemplate.render(variables);
    }

    private StructuredEventDescriptionResponse buildStructuredDescriptionFallback() {
        StructuredEventDescriptionResponse fallback = new StructuredEventDescriptionResponse();
        fallback.setTitle("Event Information Unavailable");
        fallback.setDescription(aiOutputValidator.safeFallbackText());
        fallback.setHighlights(List.of("Please try again later"));
        fallback.setTargetAudience("General audience");
        fallback.setEstimatedAttendance(50);
        return fallback;
    }
}