package com.eventhub.dto.ai;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FaqRagRequest {

    @NotBlank(message = "Query is required")
    @Size(max = 1000, message = "Query must be at most 1000 characters")
    private String query;

    @NotNull(message = "topK is required")
    @Min(value = 1, message = "topK must be at least 1")
    @Max(value = 10, message = "topK must be at most 10")
    private Integer topK;

    @NotNull(message = "minSimilarity is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "minSimilarity must be at least 0.0")
    @DecimalMax(value = "1.0", inclusive = true, message = "minSimilarity must be at most 1.0")
    private Double minSimilarity;

    public FaqRagRequest() {
    }

    public String getQuery() {
        return query;
    }

    public Integer getTopK() {
        return topK;
    }

    public Double getMinSimilarity() {
        return minSimilarity;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setTopK(Integer topK) {
        this.topK = topK;
    }

    public void setMinSimilarity(Double minSimilarity) {
        this.minSimilarity = minSimilarity;
    }
}