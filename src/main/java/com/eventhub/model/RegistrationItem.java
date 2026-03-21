package com.eventhub.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "registration_items")
public class RegistrationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id")
    private Registration registration;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal ticketPrice;

    public RegistrationItem() {
    }

    public Long getId() {
        return id;
    }

    public Registration getRegistration() {
        return registration;
    }

    public Event getEvent() {
        return event;
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

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}