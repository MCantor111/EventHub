package com.eventhub.dto;

import java.math.BigDecimal;

public class RegistrationItemDTO {

    private Long id;
    private Long eventId;
    private String eventTitle;
    private Integer quantity;
    private BigDecimal ticketPrice;

    public RegistrationItemDTO() {
    }

    public RegistrationItemDTO(Long id, Long eventId, String eventTitle, Integer quantity, BigDecimal ticketPrice) {
        this.id = id;
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.quantity = quantity;
        this.ticketPrice = ticketPrice;
    }

    public Long getId() {
        return id;
    }

    public Long getEventId() {
        return eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}