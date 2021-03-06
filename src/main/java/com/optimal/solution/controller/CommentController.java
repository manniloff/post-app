package com.optimal.solution.controller;

import com.optimal.solution.dto.ResponseJsonDto;
import com.optimal.solution.service.CommentService;
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
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private static final Logger LOGGER = LoggerFactory.getLogger("rest");
    private final CommentService commentService;

    @GetMapping(value = {"", "/"}, produces = "application/json")
    ResponseEntity<?> findAll() {
        try {
            LOGGER.info("Getting list of comments");
            return ResponseEntity.ok(commentService.findAll());
        } catch (Exception e) {
            LOGGER.error("Exception on getting comments: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = {"/{id}"}, produces = "application/json")
    ResponseEntity<?> findById(@PathVariable int id) {
        try {
            LOGGER.info("Getting comment by id");
            return ResponseEntity.ok(commentService.findById(id));
        } catch (Exception e) {
            LOGGER.error("Exception on getting comment by id: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(produces = "application/json")
    ResponseEntity<?> badRequest(@PathVariable int id) {
        try {
            LOGGER.info("Getting comment by id");
            return ResponseEntity.ok("\"Bad Request\"");
        } catch (Exception e) {
            LOGGER.error("Exception on getting comment by id: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }
}
