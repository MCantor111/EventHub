package com.eventhub.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class RegistrationDTO {

    private Long id;
    private LocalDateTime registrationDate;
    private String status;
    private BigDecimal totalAmount;
    private UserDTO user;
    private List<RegistrationItemDTO> items;

    public RegistrationDTO() {
    }

    public RegistrationDTO(Long id, LocalDateTime registrationDate, String status,
                           BigDecimal totalAmount, UserDTO user, List<RegistrationItemDTO> items) {
        this.id = id;
        this.registrationDate = registrationDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.user = user;
        this.items = items;
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

    public UserDTO getUser() {
        return user;
    }

    public List<RegistrationItemDTO> getItems() {
        return items;
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

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public void setItems(List<RegistrationItemDTO> items) {
        this.items = items;
    }
}