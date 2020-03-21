package com.optimal.solution.controller;

import com.optimal.solution.dto.ResponseJsonDto;
import com.optimal.solution.service.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private static final Logger LOGGER = LoggerFactory.getLogger("rest");
    private final PostService postService;

    @GetMapping(value = {"", "/"}, produces = "application/json")
    ResponseEntity<?> findAll() {
        try {
            LOGGER.info("Getting list of posts!");
            return ResponseEntity.ok(postService.findAll());
        } catch (Exception e) {
            LOGGER.error("Exception on getting posts: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = {"/{id}"}, produces = "application/json")
    ResponseEntity<?> findById(@PathVariable int id) {
        try {
            LOGGER.info("Getting post by id");
            return ResponseEntity.ok(postService.findById(id));
        } catch (Exception e) {
            LOGGER.error("Exception on getting post: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }
}
