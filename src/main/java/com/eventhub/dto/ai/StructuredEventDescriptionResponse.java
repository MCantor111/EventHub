package com.eventhub.dto.ai;

import java.util.List;

public class StructuredEventDescriptionResponse {

    private String title;
    private String description;
    private List<String> highlights;
    private String targetAudience;
    private Integer estimatedAttendance;

    public StructuredEventDescriptionResponse() {
    }

    public StructuredEventDescriptionResponse(
            String title,
            String description,
            List<String> highlights,
            String targetAudience,
            Integer estimatedAttendance
    ) {
        this.title = title;
        this.description = description;
        this.highlights = highlights;
        this.targetAudience = targetAudience;
        this.estimatedAttendance = estimatedAttendance;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getHighlights() {
        return highlights;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public Integer getEstimatedAttendance() {
        return estimatedAttendance;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHighlights(List<String> highlights) {
        this.highlights = highlights;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public void setEstimatedAttendance(Integer estimatedAttendance) {
        this.estimatedAttendance = estimatedAttendance;
    }
}