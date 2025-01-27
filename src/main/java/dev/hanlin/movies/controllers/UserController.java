package dev.hanlin.movies.controllers;

import dev.hanlin.movies.exceptions.EmailAlreadyInUseException;
import dev.hanlin.movies.models.Review;
import dev.hanlin.movies.models.User;
import dev.hanlin.movies.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{email}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> payload) {
        try {
            User newUser = userService.createUser(payload.get("username"), payload.get("password"), payload.get("email"));
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (EmailAlreadyInUseException e) {
            return new ResponseEntity<>("Email " + payload.get("email") + " is already in use.", HttpStatus.CONFLICT);
        }
    }
}
