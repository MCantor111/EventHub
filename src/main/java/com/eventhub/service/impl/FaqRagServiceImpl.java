package com.eventhub.service.impl;

import com.eventhub.config.AiPromptConfig;
import com.eventhub.dto.ai.FaqEntryResponse;
import com.eventhub.dto.ai.FaqRagRequest;
import com.eventhub.dto.ai.FaqRagResponse;
import com.eventhub.service.FaqRagService;
import com.eventhub.service.FaqVectorService;
import com.eventhub.util.AiOutputValidator;
import com.eventhub.util.PromptSanitizer;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class FaqRagServiceImpl implements FaqRagService {

    private final ChatClient chatClient;
    private final FaqVectorService faqVectorService;
    private final AiPromptConfig aiPromptConfig;
    private final PromptSanitizer promptSanitizer;
    private final AiOutputValidator aiOutputValidator;

    public FaqRagServiceImpl(
            ChatClient.Builder chatClientBuilder,
            FaqVectorService faqVectorService,
            AiPromptConfig aiPromptConfig,
            PromptSanitizer promptSanitizer,
            AiOutputValidator aiOutputValidator
    ) {
        this.chatClient = chatClientBuilder.build();
        this.faqVectorService = faqVectorService;
        this.aiPromptConfig = aiPromptConfig;
        this.promptSanitizer = promptSanitizer;
        this.aiOutputValidator = aiOutputValidator;
    }

    @Override
    public FaqRagResponse answerQuestion(FaqRagRequest request) {
        List<FaqEntryResponse> sources = faqVectorService.findRelevantFaqs(
                request.getQuery(),
                request.getTopK(),
                request.getMinSimilarity()
        );

        if (sources.isEmpty()) {
            return new FaqRagResponse(
                    "I could not find relevant FAQ information for that question.",
                    "No relevant FAQ context was found. Please contact support for more help.",
                    List.of()
            );
        }

        String sanitizedQuestion = promptSanitizer.sanitize(request.getQuery());
        String faqContext = buildFaqContext(sources);

        Map<String, Object> variables = new LinkedHashMap<>();
        variables.put("question", sanitizedQuestion);
        variables.put("faqContext", faqContext);

        String userPrompt = renderTemplate(
                aiPromptConfig.getFaqRagPromptTemplate(),
                variables
        );

        String answer = chatClient.prompt()
                .system(aiPromptConfig.getSystemPrompt())
                .user(userPrompt)
                .call()
                .content();

        if (!aiOutputValidator.isSafeText(answer)) {
            return new FaqRagResponse(
                    aiOutputValidator.safeFallbackText(),
                    "A safe grounded FAQ answer could not be generated.",
                    sources
            );
        }

        return new FaqRagResponse(
                answer,
                "Answer generated using retrieved FAQ entries.",
                sources
        );
    }

    private String buildFaqContext(List<FaqEntryResponse> sources) {
        StringBuilder builder = new StringBuilder();

        for (FaqEntryResponse source : sources) {
            builder.append("FAQ ID: ").append(source.getId()).append("\n");
            builder.append("Category: ").append(source.getCategory()).append("\n");
            builder.append("Question: ").append(source.getQuestion()).append("\n");
            builder.append("Answer: ").append(source.getAnswer()).append("\n\n");
        }

        return builder.toString().trim();
    }

    private String renderTemplate(String template, Map<String, Object> variables) {
        PromptTemplate promptTemplate = new PromptTemplate(template);
        return promptTemplate.render(variables);
    }
}