package com.eventhub.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventDTO {

    private Long id;
    private String title;
    private String description;
    private BigDecimal ticketPrice;
    private LocalDate eventDate;
    private LocalDateTime createdAt;
    private CategoryDTO category;

    public EventDTO() {
    }

    public EventDTO(Long id, String title, String description, BigDecimal ticketPrice,
                    LocalDate eventDate, LocalDateTime createdAt, CategoryDTO category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ticketPrice = ticketPrice;
        this.eventDate = eventDate;
        this.createdAt = createdAt;
        this.category = category;
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }
}