package dev.hanlin.movies.controllers;

import dev.hanlin.movies.models.Review;
import dev.hanlin.movies.models.User;
import dev.hanlin.movies.services.ReviewService;
import dev.hanlin.movies.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return new ResponseEntity<>(reviewService.allReviews(), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Review>> getAllReviewsByUser(
            @PathVariable String email
    ) throws Exception {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            return new ResponseEntity<>(reviewService.allReviewsByUserEmail(user.get().getEmail()), HttpStatus.OK);
        }
        throw new Exception("User with email not found");
    }

    @PostMapping
    public ResponseEntity<Review> createReviewWithUser(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(reviewService.createReviewWithUser(payload.get("reviewBody"), payload.get("imdbId"), payload.get("email")), HttpStatus.CREATED);
    }
}
