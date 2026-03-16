package com.eventhub.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateEventDTO {

    @NotBlank(message = "Event title is required")
    @Size(min = 3, max = 100, message = "Event title must be between 3 and 100 characters")
    private String title;

    @Size(max = 1000, message = "Description must be at most 1000 characters")
    private String description;

    @NotNull(message = "Ticket price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Ticket price must be positive or zero")
    private BigDecimal ticketPrice;

    @NotNull(message = "Event date is required")
    private LocalDate eventDate;

    @NotNull(message = "Category is required")
    private Long categoryId;

    public CreateEventDTO() {
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}