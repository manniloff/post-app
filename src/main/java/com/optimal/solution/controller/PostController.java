package com.optimal.solution.controller;

import com.optimal.solution.dto.PostDto;
import com.optimal.solution.model.Post;
import com.optimal.solution.service.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    @GetMapping(value = {"", "/"}, produces = "application/json")
    ResponseEntity<?> findAll() {
        try {
            LOGGER.info("Getting list of posts!");
            return ResponseEntity.ok(postService.findAll());
        } catch (Exception e) {
            LOGGER.error("Error with getting list of posts!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = {"/{id}"}, produces = "application/json")
    ResponseEntity<?> findById(@PathVariable int id) {
        try {
            LOGGER.info("Getting post by id");
            Optional<Post> post = postService.findById(id);
            if(post.isPresent()){
                return ResponseEntity.ok(post);
            } else {
                return ResponseEntity.ok("No post found with id " + id);
            }
        } catch (Exception e) {
            LOGGER.error("Error with getting post by id!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value = {"", "/"}, produces = "application/json")
    ResponseEntity<?> createOrUpdate(@RequestBody PostDto newPost) {
        try {
            LOGGER.info("Creating or updating a post");
            return ResponseEntity.ok(postService.createOrUpdate(newPost));
        } catch (Exception e) {
            LOGGER.error("Error with creating or updating a post!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping(value = {"/{id}"}, produces = "application/json")
    ResponseEntity<?> deleteById(@PathVariable int id) {
        try {
            LOGGER.info("Deleting post by id");
            if(postService.findById(id).isPresent()){
                return ResponseEntity.ok(postService.deleteById(id));
            } else {
                return ResponseEntity.ok("No post found with id " + id);
            }
        } catch (Exception e) {
            LOGGER.error("Error with deleting post by id!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
