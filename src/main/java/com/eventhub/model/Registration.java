package com.eventhub.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "registrations")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "registration", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistrationItem> registrationItems = new ArrayList<>();

    public Registration() {
    }

    @PrePersist
    public void onCreate() {
        if (this.registrationDate == null) {
            this.registrationDate = LocalDateTime.now();
        }
        if (this.totalAmount == null) {
            this.totalAmount = BigDecimal.ZERO;
        }
    }

    public void recalculateTotalAmount() {
        BigDecimal total = BigDecimal.ZERO;
        for (RegistrationItem item : registrationItems) {
            if (item.getTicketPrice() != null && item.getQuantity() != null) {
                total = total.add(item.getTicketPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
        }
        this.totalAmount = total;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public User getUser() {
        return user;
    }

    public List<RegistrationItem> getRegistrationItems() {
        return registrationItems;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRegistrationItems(List<RegistrationItem> registrationItems) {
        this.registrationItems = registrationItems;
    }
}