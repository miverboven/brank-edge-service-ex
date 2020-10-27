package com.example.brankedgeservice.controller;

import com.example.brankedgeservice.model.Book;
import com.example.brankedgeservice.model.FilledBookReview;
import com.example.brankedgeservice.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FilledBookReviewController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${reviewservice.baseurl}")
    private String reviewServiceBaseUrl;

    @Value("${bookinfoservice.baseurl}")
    private String bookInfoServiceBaseUrl;

    @GetMapping("/rankings/user/{userId}")
    public List<FilledBookReview> getRankingsByUserId(@PathVariable Integer userId){

        List<FilledBookReview> returnList= new ArrayList();

        ResponseEntity<List<Review>> responseEntityReviews =
                restTemplate.exchange("http://" + reviewServiceBaseUrl + "/reviews/user/{userId}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {
                        }, userId);

        List<Review> reviews = responseEntityReviews.getBody();

        for (Review review:
             reviews) {
            Book book =
                       restTemplate.getForObject("http://" + bookInfoServiceBaseUrl + "/books/{ISBN}",
                              Book.class, review.getISBN());

            returnList.add(new FilledBookReview(book, review));
        }

        return returnList;
    }

    @GetMapping("/rankings/book/title/{title}")
    public List<FilledBookReview> getRankingsByTitle(@PathVariable String title){

        List<FilledBookReview> returnList= new ArrayList();

        ResponseEntity<List<Book>> responseEntityBooks =
                restTemplate.exchange("http://" + bookInfoServiceBaseUrl + "/books/title/{title}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Book>>() {
                        }, title);

        List<Book> books = responseEntityBooks.getBody();

        for (Book book:
             books) {
            ResponseEntity<List<Review>> responseEntityReviews =
                    restTemplate.exchange("http://" + reviewServiceBaseUrl + "/reviews/{ISBN}",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {
                            }, book.getISBN());

            returnList.add(new FilledBookReview(book,responseEntityReviews.getBody()));
        }

        return returnList;
    }

    @GetMapping("/rankings/book/{ISBN}")
    public FilledBookReview getRankingsByISBN(@PathVariable String ISBN){

        Book book =
                restTemplate.getForObject("http://" + bookInfoServiceBaseUrl + "/books/{ISBN}",
                        Book.class, ISBN);

        ResponseEntity<List<Review>> responseEntityReviews =
                restTemplate.exchange("http://" + reviewServiceBaseUrl + "/reviews/{ISBN}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {
                        }, ISBN);

        return new FilledBookReview(book,responseEntityReviews.getBody());
    }

    @GetMapping("/rankings/{userId}/book/{ISBN}")
    public FilledBookReview getRankingByUserIdAndISBN(@PathVariable Integer userId, @PathVariable String ISBN){

        Book book =
                restTemplate.getForObject("http://" + bookInfoServiceBaseUrl + "/books/{ISBN}",
                        Book.class, ISBN);

        Review review =
                restTemplate.getForObject("http://" + reviewServiceBaseUrl + "/reviews/user/" + userId + "/book/" + ISBN,
                        Review.class);

        return new FilledBookReview(book, review);
    }

    @PostMapping("/rankings")
    public FilledBookReview addRanking(@RequestParam Integer userId, @RequestParam String ISBN, @RequestParam Integer score){

        Review review =
                restTemplate.postForObject("http://" + reviewServiceBaseUrl + "/reviews",
                        new Review(userId,ISBN,score),Review.class);

        Book book =
                restTemplate.getForObject("http://" + bookInfoServiceBaseUrl + "/books/{ISBN}",
                        Book.class,ISBN);

        return new FilledBookReview(book, review);
    }

    @PutMapping("/rankings")
    public FilledBookReview updateRanking(@RequestParam Integer userId, @RequestParam String ISBN, @RequestParam Integer score){

        Review review =
                restTemplate.getForObject("http://" + reviewServiceBaseUrl + "/reviews/user/" + userId + "/book/" + ISBN,
                        Review.class);
        review.setScoreNumber(score);

        ResponseEntity<Review> responseEntityReview =
                restTemplate.exchange("http://" + reviewServiceBaseUrl + "/reviews",
                        HttpMethod.PUT, new HttpEntity<>(review), Review.class);

        Review retrievedReview = responseEntityReview.getBody();

        Book book =
                restTemplate.getForObject("http://" + bookInfoServiceBaseUrl + "/books/{ISBN}",
                        Book.class,ISBN);

        return new FilledBookReview(book, retrievedReview);
    }

    @DeleteMapping("/rankings/{userId}/book/{ISBN}")
    public ResponseEntity deleteRanking(@PathVariable Integer userId, @PathVariable String ISBN){

        restTemplate.delete("http://" + reviewServiceBaseUrl + "/reviews/user/" + userId + "/book/" + ISBN);

        return ResponseEntity.ok().build();
    }

}
