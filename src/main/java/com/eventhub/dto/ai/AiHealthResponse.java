package com.eventhub.dto.ai;

public class AiHealthResponse {

    private boolean available;
    private String message;

    public AiHealthResponse() {
    }

    public AiHealthResponse(boolean available, String message) {
        this.available = available;
        this.message = message;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getMessage() {
        return message;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}