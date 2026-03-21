package com.eventhub.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateRegistrationItemDTO {

    @NotNull(message = "Event id is required")
    private Long eventId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    public CreateRegistrationItemDTO() {
    }

    public Long getEventId() {
        return eventId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}