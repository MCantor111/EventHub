package com.eventhub.dto.ai;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public class EventScheduleRequest {

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

    @NotNull(message = "Session count is required")
    @Min(value = 1, message = "Session count must be at least 1")
    @Max(value = 20, message = "Session count must be at most 20")
    private Integer sessionCount;

    @NotNull(message = "Duration in minutes is required")
    @Min(value = 30, message = "Duration must be at least 30 minutes")
    @Max(value = 1440, message = "Duration must be at most 1440 minutes")
    private Integer durationMinutes;

    @Size(max = 1000, message = "Special requirements must be at most 1000 characters")
    private String specialRequirements;

    @NotNull(message = "Keywords are required")
    @Size(min = 1, max = 10, message = "Keywords must contain between 1 and 10 items")
    private List<
            @NotBlank(message = "Keyword cannot be blank")
            @Size(max = 50, message = "Each keyword must be at most 50 characters")
                    String> keywords;

    public EventScheduleRequest() {
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

    public Integer getSessionCount() {
        return sessionCount;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public String getSpecialRequirements() {
        return specialRequirements;
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

    public void setSessionCount(Integer sessionCount) {
        this.sessionCount = sessionCount;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setSpecialRequirements(String specialRequirements) {
        this.specialRequirements = specialRequirements;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}