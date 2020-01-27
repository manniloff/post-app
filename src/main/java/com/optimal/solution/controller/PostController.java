package com.optimal.solution.controller;

import com.optimal.solution.model.Post;
import com.optimal.solution.service.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    @GetMapping(value = {"", "/"}, produces = "application/json")
    List<Post> findAll() {
        return postService.findAll();
    }

    @GetMapping(value = {"/{id}"}, produces = "application/json")
    Optional<Post> findById(@PathParam("id") int id) {
        return postService.findById(id);
    }

    @PostMapping(value = {"", "/"}, produces = "application/json")
    int createOrUpdate(@RequestBody Post newPost) {
        return postService.createOrUpdate(newPost);
    }

    @DeleteMapping(value = {"/{id}"}, produces = "application/json")
    Optional<Post> deleteById(@PathParam("id") int id) {
        return postService.deleteById(id);
    }
}
