package com.eventhub.dto.ai;

import java.util.List;

public class TagSuggestionResponse {

    private List<String> tags;

    public TagSuggestionResponse() {
    }

    public TagSuggestionResponse(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}