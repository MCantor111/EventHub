package com.eventhub.service;

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

public interface AiService {

    AiHealthResponse healthCheck();

    AiChatResponse chat(AiChatRequest request);

    EventDescriptionResponse generateEventDescription(EventDescriptionRequest request);

    StructuredEventDescriptionResponse generateStructuredEventDescription(StructuredEventDescriptionRequest request);

    TagSuggestionResponse suggestEventTags(EventTagSuggestionRequest request);

    EventScheduleResponse generateEventSchedule(EventScheduleRequest request);
}