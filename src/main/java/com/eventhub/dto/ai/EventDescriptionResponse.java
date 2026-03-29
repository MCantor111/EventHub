package com.eventhub.dto.ai;

public class EventDescriptionResponse {

    private String description;

    public EventDescriptionResponse() {
    }

    public EventDescriptionResponse(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}