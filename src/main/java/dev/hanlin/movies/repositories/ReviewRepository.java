package dev.hanlin.movies.repositories;

import dev.hanlin.movies.models.Review;
import dev.hanlin.movies.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, ObjectId> {

    List<Review> findAllByUserEmail(String userEmail);
}
