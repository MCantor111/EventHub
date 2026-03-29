package com.eventhub.dto.ai;

import java.util.List;

public class FaqSearchResponse {

    private String message;
    private List<FaqEntryResponse> results;

    public FaqSearchResponse() {
    }

    public FaqSearchResponse(String message, List<FaqEntryResponse> results) {
        this.message = message;
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public List<FaqEntryResponse> getResults() {
        return results;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResults(List<FaqEntryResponse> results) {
        this.results = results;
    }
}