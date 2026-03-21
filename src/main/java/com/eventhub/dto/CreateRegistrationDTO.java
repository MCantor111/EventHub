package com.eventhub.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateRegistrationDTO {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotBlank(message = "Status is required")
    private String status;

    @Valid
    @NotEmpty(message = "At least one registration item is required")
    private List<CreateRegistrationItemDTO> items;

    public CreateRegistrationDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public List<CreateRegistrationItemDTO> getItems() {
        return items;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setItems(List<CreateRegistrationItemDTO> items) {
        this.items = items;
    }
}