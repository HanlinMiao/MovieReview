package dev.hanlin.movies.services;

import dev.hanlin.movies.models.Movie;
import dev.hanlin.movies.models.Review;
import dev.hanlin.movies.models.User;
import dev.hanlin.movies.repositories.MovieRepository;
import dev.hanlin.movies.repositories.ReviewRepository;
import dev.hanlin.movies.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;

    public List<Review> allReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> allReviewsByUserEmail(String userEmail) {
        return reviewRepository.findAllByUserEmail(userEmail);
    }

    public Review createReviewWithUser(String reviewBody, String imdbId, String email) {
        Review review = reviewRepository.insert(new Review(email, reviewBody));
        // adding the review to the reviewIds field of the movie identified by the imdbId
        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review))
                .first();
        mongoTemplate.update(User.class)
                .matching(Criteria.where("email").is(email))
                .apply(new Update().push("reviews").value(review))
                .first();

        return review;
    }
}
