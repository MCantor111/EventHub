package com.eventhub.dto.ai;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AiChatRequest {

    @NotBlank(message = "Prompt is required")
    @Size(max = 2000, message = "Prompt must be at most 2000 characters")
    private String prompt;

    public AiChatRequest() {
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}