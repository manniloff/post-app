package com.optimal.solution.controller;

import com.optimal.solution.dto.CommentDto;
import com.optimal.solution.model.Comment;
import com.optimal.solution.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;

    @GetMapping(value = {"", "/"}, produces = "application/json")
    List<CommentDto> findAll() {
        return commentService.findAll();
    }

    @GetMapping(value = {"/{id}"}, produces = "application/json")
    Optional<Comment> findById(@PathVariable int id) {
        return commentService.findById(id);
    }

    @PostMapping(value = {"", "/"}, produces = "application/json")
    int createOrUpdate(@RequestBody CommentDto newComment) {
        return commentService.createOrUpdate(newComment);
    }

    @DeleteMapping(value = {"/{id}"}, produces = "application/json")
    Optional<Comment> deleteById(@PathVariable int id) {
        return commentService.deleteById(id);
    }
}
