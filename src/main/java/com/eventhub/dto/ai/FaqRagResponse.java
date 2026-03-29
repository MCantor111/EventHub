package com.eventhub.dto.ai;

import java.util.List;

public class FaqRagResponse {

    private String answer;
    private String message;
    private List<FaqEntryResponse> sources;

    public FaqRagResponse() {
    }

    public FaqRagResponse(String answer, String message, List<FaqEntryResponse> sources) {
        this.answer = answer;
        this.message = message;
        this.sources = sources;
    }

    public String getAnswer() {
        return answer;
    }

    public String getMessage() {
        return message;
    }

    public List<FaqEntryResponse> getSources() {
        return sources;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSources(List<FaqEntryResponse> sources) {
        this.sources = sources;
    }
}