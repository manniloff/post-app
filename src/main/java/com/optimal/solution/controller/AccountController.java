package com.optimal.solution.controller;

import com.optimal.solution.auth.filter.JwtRequestFilter;
import com.optimal.solution.dto.CommentDto;
import com.optimal.solution.dto.PostDto;
import com.optimal.solution.model.User;
import com.optimal.solution.service.CommentService;
import com.optimal.solution.service.PostService;
import com.optimal.solution.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping(value = {"", "/"}, produces = "application/json")
    ResponseEntity<?> findUserByIdUser() {
        try {
            LOGGER.info("Getting user by id");
            return ResponseEntity.ok(userService.findById(JwtRequestFilter.id));
        } catch (Exception e) {
            LOGGER.error("Error with getting user by id!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping(value = {"", "/"}, produces = "application/json")
    ResponseEntity<?> createOrUpdateUser(@RequestBody User newUser) {
        try {
            LOGGER.info("Creating or updating a user");
            userService.update(newUser, newUser.getId());
            return new ResponseEntity<>("Created or Updated", HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error with creating or updating an user!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/posts", produces = "application/json")
    ResponseEntity<?> findAllPost() {
        try {
            LOGGER.info("Getting list of post for user - {} ", JwtRequestFilter.id);
            return ResponseEntity.ok(postService.findAccountAll());
        } catch (Exception e) {
            LOGGER.error("Error with getting user by id!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/posts/{id}", produces = "application/json")
    ResponseEntity<?> findByIdPost(@PathVariable int id) {
        try {
            LOGGER.info("Getting list of post for user - {} ", JwtRequestFilter.id);
            return ResponseEntity.ok(postService.findByIdAccount(id));
        } catch (Exception e) {
            LOGGER.error("Error with getting user by id!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value = "/posts", produces = "application/json")
    ResponseEntity<?> createOrUpdatePost(@RequestBody PostDto newPost) {
        try {
            LOGGER.info("Creating or updating a post");
            postService.createOrUpdate(newPost);
            return new ResponseEntity<>("Created or Updated", HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error with creating or updating a post!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping(value = {"/posts/{id}"}, produces = "application/json")
    ResponseEntity<?> deleteByIdPost(@PathVariable int id) {
        try {
            LOGGER.info("Deleting post by id");
            return ResponseEntity.ok(postService.deleteById(id));
        } catch (Exception e) {
            LOGGER.error("Error with deleting post by id!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/comments", produces = "application/json")
    ResponseEntity<?> findAllComment() {
        try {
            LOGGER.info("Getting list of comment for user - {} ", JwtRequestFilter.id);
            return ResponseEntity.ok(commentService.findAccountAll());
        } catch (Exception e) {
            LOGGER.error("Error with getting comments!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/comments/{id}", produces = "application/json")
    ResponseEntity<?> findByIdComment(@PathVariable int id) {
        try {
            Optional<CommentDto> comment = commentService.findByIdAccount(id);
            if (comment.isPresent()) {
                LOGGER.info("Getting list of comment for user - {} ", JwtRequestFilter.id);
                return ResponseEntity.ok(comment);
            } else {
                return ResponseEntity.ok("No comment found with id " + id);
            }
        } catch (Exception e) {
            LOGGER.error("Error with getting user by id!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value = "/comments", produces = "application/json")
    ResponseEntity<?> createOrUpdateComment(@RequestBody CommentDto newComment) {
        try {
            LOGGER.info("Creating or updating a comment");
            commentService.createOrUpdate(newComment);
            return new ResponseEntity<>("Created or Updated", HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error with creating or updating a comment!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping(value = {"/comments/{id}"}, produces = "application/json")
    ResponseEntity<?> deleteByIdComment(@PathVariable int id) {
        try {
            LOGGER.info("Deleting comment by id");
            return ResponseEntity.ok(commentService.deleteById(id));
        } catch (Exception e) {
            LOGGER.error("Error with deleting comment by id!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
