package com.eventhub.dto.ai;

public class FaqEntryResponse {

    private String id;
    private String category;
    private String question;
    private String answer;
    private Double similarity;

    public FaqEntryResponse() {
    }

    public FaqEntryResponse(String id, String category, String question, String answer, Double similarity) {
        this.id = id;
        this.category = category;
        this.question = question;
        this.answer = answer;
        this.similarity = similarity;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public Double getSimilarity() {
        return similarity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }
}