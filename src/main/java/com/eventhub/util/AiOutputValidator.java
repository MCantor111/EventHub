package com.eventhub.util;

import com.eventhub.dto.ai.AgendaItemResponse;
import com.eventhub.dto.ai.EventScheduleResponse;
import com.eventhub.dto.ai.StructuredEventDescriptionResponse;
import com.eventhub.dto.ai.TagSuggestionResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class AiOutputValidator {

    private static final List<String> BLOCKED_PATTERNS = List.of(
            "system prompt",
            "developer instruction",
            "hidden instruction",
            "api key",
            "environment variable",
            "secret key",
            "access token",
            "authorization header",
            "internal configuration",
            "ignore previous instructions"
    );

    public boolean isSafeText(String text) {
        if (text == null || text.isBlank()) {
            return false;
        }

        String normalized = text.toLowerCase(Locale.ROOT);
        for (String blockedPattern : BLOCKED_PATTERNS) {
            if (normalized.contains(blockedPattern)) {
                return false;
            }
        }

        return true;
    }

    public String safeFallbackText() {
        return "Sorry, a safe AI response could not be generated for this request.";
    }

    public boolean isValidStructuredDescription(StructuredEventDescriptionResponse response) {
        if (response == null) {
            return false;
        }

        if (!isSafeText(response.getTitle())) {
            return false;
        }

        if (!isSafeText(response.getDescription())) {
            return false;
        }

        if (!isSafeText(response.getTargetAudience())) {
            return false;
        }

        if (response.getHighlights() == null || response.getHighlights().isEmpty()) {
            return false;
        }

        for (String highlight : response.getHighlights()) {
            if (!isSafeText(highlight)) {
                return false;
            }
        }

        return response.getEstimatedAttendance() != null && response.getEstimatedAttendance() > 0;
    }

    public boolean isValidTagSuggestionResponse(TagSuggestionResponse response) {
        if (response == null || response.getTags() == null || response.getTags().isEmpty()) {
            return false;
        }

        for (String tag : response.getTags()) {
            if (!isSafeText(tag)) {
                return false;
            }
        }

        return true;
    }

    public boolean isValidScheduleResponse(EventScheduleResponse response) {
        if (response == null || response.getAgendaItems() == null || response.getAgendaItems().isEmpty()) {
            return false;
        }

        for (AgendaItemResponse item : response.getAgendaItems()) {
            if (item == null) {
                return false;
            }

            if (!isSafeText(item.getStartTime())) {
                return false;
            }

            if (!isSafeText(item.getEndTime())) {
                return false;
            }

            if (!isSafeText(item.getTitle())) {
                return false;
            }

            if (!isSafeText(item.getDescription())) {
                return false;
            }
        }

        return true;
    }
}