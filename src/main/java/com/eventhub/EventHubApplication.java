package com.eventhub;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventHubApplication {

    @Value("${spring.ai.google.genai.api-key:}")
    private String geminiApiKey;

    public static void main(String[] args) {
        SpringApplication.run(EventHubApplication.class, args);
    }

    @PostConstruct
    public void logAiConfigurationStatus() {
        System.out.println("Gemini key present: " + !geminiApiKey.isBlank());
    }
}