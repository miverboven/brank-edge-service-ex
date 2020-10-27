package com.example.brankedgeservice.model;

public class Review {
    private String id;
    private Integer userId;
    private String ISBN;
    private Integer scoreNumber;

    public Review() {
    }

    public Review(Integer userId, String ISBN, Integer scoreNumber) {
        this.userId = userId;
        this.ISBN = ISBN;
        this.scoreNumber = scoreNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Integer getScoreNumber() {
        return scoreNumber;
    }

    public void setScoreNumber(Integer scoreNumber) {
        this.scoreNumber = scoreNumber;
    }
}
