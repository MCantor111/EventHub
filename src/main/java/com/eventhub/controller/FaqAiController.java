package com.eventhub.controller;

import com.eventhub.dto.ai.FaqRagRequest;
import com.eventhub.dto.ai.FaqRagResponse;
import com.eventhub.dto.ai.FaqSearchRequest;
import com.eventhub.dto.ai.FaqSearchResponse;
import com.eventhub.service.FaqRagService;
import com.eventhub.service.FaqVectorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai/faq")
public class FaqAiController {

    private final FaqVectorService faqVectorService;
    private final FaqRagService faqRagService;

    public FaqAiController(FaqVectorService faqVectorService, FaqRagService faqRagService) {
        this.faqVectorService = faqVectorService;
        this.faqRagService = faqRagService;
    }

    @Operation(summary = "Load default FAQ entries into the vector store")
    @PostMapping("/load")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> loadFaqs() {
        int count = faqVectorService.loadDefaultFaqs();
        return Map.of(
                "message", "FAQ entries loaded successfully.",
                "count", count
        );
    }

    @Operation(summary = "Search FAQ entries using semantic similarity")
    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public FaqSearchResponse searchFaqs(@Valid @RequestBody FaqSearchRequest request) {
        return faqVectorService.searchFaqs(
                request.getQuery(),
                request.getTopK(),
                request.getMinSimilarity()
        );
    }

    @Operation(summary = "Ask a grounded FAQ question using RAG")
    @PostMapping("/ask")
    @ResponseStatus(HttpStatus.OK)
    public FaqRagResponse askFaq(@Valid @RequestBody FaqRagRequest request) {
        return faqRagService.answerQuestion(request);
    }
}