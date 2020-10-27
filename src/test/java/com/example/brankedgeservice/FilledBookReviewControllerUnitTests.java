package com.example.brankedgeservice;

import com.example.brankedgeservice.model.Book;
import com.example.brankedgeservice.model.Review;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FilledBookReviewControllerUnitTests {

    @Value("${reviewservice.baseurl}")
    private String reviewServiceBaseUrl;

    @Value("${bookinfoservice.baseurl}")
    private String bookInfoServiceBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    private Book book1 = new Book("Book1", "ISBN1");
    private Book book2 = new Book("Book2", "ISBN2");

    private Review reviewUser1Book1 = new Review(1, "ISBN1", 1);
    private Review reviewUser1Book2 = new Review(1, "ISBN2", 2);
    private Review reviewUser2Book1 = new Review(2, "ISBN1", 2);

    private List<Review> allReviewsFromUser1 = Arrays.asList(reviewUser1Book2, reviewUser1Book1);
    private List<Review> allReviewsForBook1 = Arrays.asList(reviewUser1Book1, reviewUser2Book1);
    private List<Review> allReviewsForBook2 = Arrays.asList(reviewUser1Book2);
    private List<Book> allBooks = Arrays.asList(book1, book2);

    @BeforeEach
    public void initializeMockserver() {
        mockServer = MockRestServiceServer.createServer(restTemplate);

//        // GET review from User 1 of Book 1
//        mockServer.expect(ExpectedCount.once(),
//                requestTo(new URI("http://" + reviewServiceBaseUrl + "/reviews/user/1/book/ISBN1")))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(reviewUser1Book1))
//                );

//        // GET all reviews from User 1
//        mockServer.expect(ExpectedCount.once(),
//                requestTo(new URI("http://" + reviewServiceBaseUrl + "/reviews/user/1")))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(allReviewsFromUser1))
//                );

//        // GET all reviews for Book 1
//        mockServer.expect(ExpectedCount.once(),
//                requestTo(new URI("http://" + reviewServiceBaseUrl + "/reviews/ISBN1")))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(allReviewsForBook1))
//                );

//        // GET all reviews for Book 2
//        mockServer.expect(ExpectedCount.once(),
//                requestTo(new URI("http://" + reviewServiceBaseUrl + "/reviews/ISBN2")))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(allReviewsForBook2))
//                );

//        // GET Book 1 info
//        mockServer.expect(ExpectedCount.once(),
//                requestTo(new URI("http://" + bookInfoServiceBaseUrl + "/books/ISBN1")))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(book1))
//                );

//        // GET Book 2 info
//        mockServer.expect(ExpectedCount.once(),
//                requestTo(new URI("http://" + bookInfoServiceBaseUrl + "/books/ISBN2")))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(book2))
//                );

//        // GET Books by Title 'Book'
//        mockServer.expect(ExpectedCount.once(),
//                requestTo(new URI("http://" + bookInfoServiceBaseUrl + "/books/title/Book")))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(allBooks))
//                );

//        // POST review for Book 1 from User 3
//        mockServer.expect(ExpectedCount.once(),
//                requestTo(new URI("http://" + reviewServiceBaseUrl + "/reviews")))
//                .andExpect(method(HttpMethod.POST))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(reviewUser3Book1))
//                );

//        // PUT review from User 1 for Book 1 with new score 5
//        mockServer.expect(ExpectedCount.once(),
//                requestTo(new URI("http://" + reviewServiceBaseUrl + "/reviews")))
//                .andExpect(method(HttpMethod.PUT))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(updatedReviewUser1Book1))
//                );

//        // DELETE review from User 999 of Book with ISBN9 as ISBN
//        mockServer.expect(ExpectedCount.once(),
//                requestTo(new URI("http://" + reviewServiceBaseUrl + "/reviews/user/999/book/ISBN9")))
//                .andExpect(method(HttpMethod.DELETE))
//                .andRespond(withStatus(HttpStatus.OK)
//                );
    }

    @Test
    public void whenGetRankingsByUserId_thenReturnFilledBookReviewsJson() throws Exception {

        // GET all reviews from User 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + reviewServiceBaseUrl + "/reviews/user/1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allReviewsFromUser1))
                );

        // GET Book 2 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + bookInfoServiceBaseUrl + "/books/ISBN2")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(book2))
                );

        // GET Book 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + bookInfoServiceBaseUrl + "/books/ISBN1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(book1))
                );

        mockMvc.perform(get("/rankings/user/{userId}", 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].bookTitle", is("Book2")))
                .andExpect(jsonPath("$[0].isbn", is("ISBN2")))
                .andExpect(jsonPath("$[0].userScores[0].userId", is(1)))
                .andExpect(jsonPath("$[0].userScores[0].scoreNumber", is(2)))
                .andExpect(jsonPath("$[1].bookTitle", is("Book1")))
                .andExpect(jsonPath("$[1].isbn", is("ISBN1")))
                .andExpect(jsonPath("$[1].userScores[0].userId", is(1)))
                .andExpect(jsonPath("$[1].userScores[0].scoreNumber", is(1)));
    }

    @Test
    public void whenGetRankingsByTitle_thenReturnFilledBookReviewsJson() throws Exception {

        // GET Books by Title 'Book'
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + bookInfoServiceBaseUrl + "/books/title/Book")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allBooks))
                );

        // GET all reviews for Book 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + reviewServiceBaseUrl + "/reviews/ISBN1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allReviewsForBook1))
                );

        // GET all reviews for Book 2
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + reviewServiceBaseUrl + "/reviews/ISBN2")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allReviewsForBook2))
                );

        mockMvc.perform(get("/rankings/book/title/{title}", "Book"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].bookTitle", is("Book1")))
                .andExpect(jsonPath("$[0].isbn", is("ISBN1")))
                .andExpect(jsonPath("$[0].userScores[0].userId", is(1)))
                .andExpect(jsonPath("$[0].userScores[0].scoreNumber", is(1)))
                .andExpect(jsonPath("$[0].userScores[1].userId", is(2)))
                .andExpect(jsonPath("$[0].userScores[1].scoreNumber", is(2)))
                .andExpect(jsonPath("$[1].bookTitle", is("Book2")))
                .andExpect(jsonPath("$[1].isbn", is("ISBN2")))
                .andExpect(jsonPath("$[1].userScores[0].userId", is(1)))
                .andExpect(jsonPath("$[1].userScores[0].scoreNumber", is(2)));

    }

    @Test
    public void whenGetRankingsByISBN_thenReturnFilledBookReviewsJson() throws Exception {

        // GET Book 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + bookInfoServiceBaseUrl + "/books/ISBN1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(book1))
                );


        // GET all reviews for Book 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + reviewServiceBaseUrl + "/reviews/ISBN1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allReviewsForBook1))
                );


        mockMvc.perform(get("/rankings/book/{ISBN}", "ISBN1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookTitle", is("Book1")))
                .andExpect(jsonPath("$.isbn", is("ISBN1")))
                .andExpect(jsonPath("$.userScores[0].userId", is(1)))
                .andExpect(jsonPath("$.userScores[0].scoreNumber", is(1)))
                .andExpect(jsonPath("$.userScores[1].userId", is(2)))
                .andExpect(jsonPath("$.userScores[1].scoreNumber", is(2)));
    }

    @Test
    public void whenGetRankingsByUserIdAndISBN_thenReturnFilledBookReviewsJson() throws Exception {

        // GET Book 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + bookInfoServiceBaseUrl + "/books/ISBN1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(book1))
                );

        // GET review from User 1 of Book 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + reviewServiceBaseUrl + "/reviews/user/1/book/ISBN1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(reviewUser1Book1))
                );

        mockMvc.perform(get("/rankings/{userId}/book/{ISBN}", 1, "ISBN1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookTitle", is("Book1")))
                .andExpect(jsonPath("$.isbn", is("ISBN1")))
                .andExpect(jsonPath("$.userScores[0].userId", is(1)))
                .andExpect(jsonPath("$.userScores[0].scoreNumber", is(1)));
    }

    @Test
    public void whenAddRanking_thenReturnFilledBookReviewJson() throws Exception {

        Review reviewUser3Book1 = new Review(3, "ISBN1", 3);

        // POST review for Book 1 from User 3
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + reviewServiceBaseUrl + "/reviews")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(reviewUser3Book1))
                );

        // GET Book 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + bookInfoServiceBaseUrl + "/books/ISBN1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(book1))
                );

        mockMvc.perform(post("/rankings")
                .param("userId", reviewUser3Book1.getUserId().toString())
                .param("ISBN", reviewUser3Book1.getISBN())
                .param("score", reviewUser3Book1.getScoreNumber().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookTitle", is("Book1")))
                .andExpect(jsonPath("$.isbn", is("ISBN1")))
                .andExpect(jsonPath("$.userScores[0].userId", is(3)))
                .andExpect(jsonPath("$.userScores[0].scoreNumber", is(3)));
    }

    @Test
    public void whenUpdateRanking_thenReturnFilledBookReviewJson() throws Exception {

        Review updatedReviewUser1Book1 = new Review(1, "ISBN1", 5);

        // GET review from User 1 of Book 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + reviewServiceBaseUrl + "/reviews/user/1/book/ISBN1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(reviewUser1Book1))
                );

        // PUT review from User 1 for Book 1 with new score 5
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + reviewServiceBaseUrl + "/reviews")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(updatedReviewUser1Book1))
                );

        // GET Book 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + bookInfoServiceBaseUrl + "/books/ISBN1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(book1))
                );

        mockMvc.perform(put("/rankings")
                .param("userId", updatedReviewUser1Book1.getUserId().toString())
                .param("ISBN", updatedReviewUser1Book1.getISBN())
                .param("score", updatedReviewUser1Book1.getScoreNumber().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookTitle", is("Book1")))
                .andExpect(jsonPath("$.isbn", is("ISBN1")))
                .andExpect(jsonPath("$.userScores[0].userId", is(1)))
                .andExpect(jsonPath("$.userScores[0].scoreNumber", is(5)));

    }

    @Test
    public void whenDeleteRanking_thenReturnStatusOk() throws Exception {

        // DELETE review from User 999 of Book with ISBN9 as ISBN
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + reviewServiceBaseUrl + "/reviews/user/999/book/ISBN9")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                );

        mockMvc.perform(delete("/rankings/{userId}/book/{ISBN}", 999, "ISBN9"))
                .andExpect(status().isOk());
    }

}
