package com.eventhub.service;

import com.eventhub.dto.ai.FaqEntryResponse;
import com.eventhub.dto.ai.FaqSearchResponse;

import java.util.List;

public interface FaqVectorService {

    int loadDefaultFaqs();

    FaqSearchResponse searchFaqs(String query, int topK, double minSimilarity);

    List<FaqEntryResponse> findRelevantFaqs(String query, int topK, double minSimilarity);
}