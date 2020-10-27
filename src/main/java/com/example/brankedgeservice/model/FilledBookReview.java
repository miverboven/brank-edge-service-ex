package com.example.brankedgeservice.model;

import java.util.ArrayList;
import java.util.List;

public class FilledBookReview {

    private String bookTitle;
    private String ISBN;
    private List<UserScore> userScores;

    public FilledBookReview(Book book, List<Review> reviews) {
        setBookTitle(book.getTitle());
        setISBN(book.getISBN());
        userScores = new ArrayList<>();
        reviews.forEach(review -> {
            userScores.add(new UserScore(review.getUserId(),
                    review.getScoreNumber()));
        });
        setUserScores(userScores);
    }

    public FilledBookReview(Book book, Review review) {
        setBookTitle(book.getTitle());
        setISBN(book.getISBN());
        userScores = new ArrayList<>();
        userScores.add(new UserScore(review.getUserId(),review.getScoreNumber()));
        setUserScores(userScores);
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String title) {
        this.bookTitle = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public List<UserScore> getUserScores() {
        return userScores;
    }

    public void setUserScores(List<UserScore> userScores) {
        this.userScores = userScores;
    }
}
