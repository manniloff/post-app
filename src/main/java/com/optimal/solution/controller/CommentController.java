package com.optimal.solution.controller;

import com.optimal.solution.dto.CommentDto;
import com.optimal.solution.model.Comment;
import com.optimal.solution.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;

    @GetMapping(value = {"", "/"}, produces = "application/json")
    ResponseEntity<?> findAll() {
        try {
            LOGGER.info("Getting list of comments");
            return ResponseEntity.ok(commentService.findAll());
        } catch (Exception e) {
            LOGGER.error("Error with getting list of comments!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = {"/{id}"}, produces = "application/json")
    ResponseEntity<?> findById(@PathVariable int id) {
        try {
            Optional<Comment> comment = commentService.findById(id);
            if (comment.isPresent()) {
                LOGGER.info("Getting comment by id");
                return ResponseEntity.ok(comment);
            } else {
                return ResponseEntity.ok("No comment found with id " + id);
            }
        } catch (Exception e) {
            LOGGER.error("Error with getting comment by id!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
