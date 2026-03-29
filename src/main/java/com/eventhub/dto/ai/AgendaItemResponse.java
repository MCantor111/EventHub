package com.eventhub.dto.ai;

public class AgendaItemResponse {

    private String startTime;
    private String endTime;
    private String title;
    private String description;

    public AgendaItemResponse() {
    }

    public AgendaItemResponse(String startTime, String endTime, String title, String description) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}