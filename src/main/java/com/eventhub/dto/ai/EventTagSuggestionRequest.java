package com.eventhub.dto.ai;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public class EventTagSuggestionRequest {

    @NotBlank(message = "Event name is required")
    @Size(max = 100, message = "Event name must be at most 100 characters")
    private String eventName;

    @NotBlank(message = "Category is required")
    @Size(max = 100, message = "Category must be at most 100 characters")
    private String category;

    @NotBlank(message = "Location is required")
    @Size(max = 150, message = "Location must be at most 150 characters")
    private String location;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @Size(max = 1500, message = "Description must be at most 1500 characters")
    private String description;

    @NotNull(message = "Keywords are required")
    @Size(min = 1, max = 10, message = "Keywords must contain between 1 and 10 items")
    private List<
            @NotBlank(message = "Keyword cannot be blank")
            @Size(max = 50, message = "Each keyword must be at most 50 characters")
                    String> keywords;

    public EventTagSuggestionRequest() {
    }

    public String getEventName() {
        return eventName;
    }

    public String getCategory() {
        return category;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}