package com.optimal.solution.controller;

import com.optimal.solution.auth.filter.JwtRequestFilter;
import com.optimal.solution.dto.CommentDto;
import com.optimal.solution.dto.PostDto;
import com.optimal.solution.dto.ResponseJsonDto;
import com.optimal.solution.model.Comment;
import com.optimal.solution.model.Post;
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
    ResponseEntity<ResponseJsonDto> findUserByIdUser() {
        try {
            LOGGER.info("Getting user by id");
            return ResponseEntity.ok(ResponseJsonDto.buildOk(userService.findById(JwtRequestFilter.id)));
        } catch (Exception e) {
            LOGGER.error("Exception on getting user by id: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value = {"", "/"}, produces = "application/json")
    ResponseEntity<ResponseJsonDto> createOrUpdateUser(@RequestBody User newUser) {
        try {
            LOGGER.info("Creating or updating a user");
            return ResponseEntity.ok(ResponseJsonDto.buildOk(userService.update(newUser)));
        } catch (Exception e) {
            LOGGER.error("Exception on creating or updating an user: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/posts", produces = "application/json")
    ResponseEntity<ResponseJsonDto> findAllPost() {
        try {
            LOGGER.info("Getting list of post for user - {} ", JwtRequestFilter.id);
            return ResponseEntity.ok(ResponseJsonDto.buildOk(postService.findAccountAll()));
        } catch (Exception e) {
            LOGGER.error("Exception on getting user by id: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/posts/{id}", produces = "application/json")
    ResponseEntity<ResponseJsonDto> findByIdPost(@PathVariable int id) {
        try {
            Optional<Post> post = postService.findByIdAccount(id);
            if (post.isPresent()) {
                LOGGER.info("Getting list of post for user - {} ", JwtRequestFilter.id);
                return ResponseEntity.ok(ResponseJsonDto.buildOk(post));
            } else {
                return ResponseEntity.ok(ResponseJsonDto.buildOk("No post found with id " + id));
            }
        } catch (Exception e) {
            LOGGER.error("Exception on getting user by id: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value = "/posts", produces = "application/json")
    ResponseEntity<ResponseJsonDto> createOrUpdatePost(@RequestBody PostDto newPost) {
        try {
            LOGGER.info("Creating or updating a post");
            return ResponseEntity.ok(ResponseJsonDto.buildOk(postService.createOrUpdate(newPost)));
        } catch (Exception e) {
            LOGGER.error("Exception on creating or updating a post: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping(value = {"/posts/{id}"}, produces = "application/json")
    ResponseEntity<ResponseJsonDto> deleteByIdPost(@PathVariable int id) {
        try {
            LOGGER.info("Deleting post by id");
            if (postService.findById(id).isPresent()) {
                return ResponseEntity.ok(ResponseJsonDto.buildOk(postService.deleteById(id)));
            } else {
                return ResponseEntity.ok(ResponseJsonDto.buildOk("No post found with id " + id));
            }
        } catch (Exception e) {
            LOGGER.error("Exception on deleting post by id: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/comments", produces = "application/json")
    ResponseEntity<ResponseJsonDto> findAllComment() {
        try {
            LOGGER.info("Getting list of comment for user - {} ", JwtRequestFilter.id);
            return ResponseEntity.ok(ResponseJsonDto.buildOk(commentService.findAccountAll()));
        } catch (Exception e) {
            LOGGER.error("Exception on getting comments: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/comments/{id}", produces = "application/json")
    ResponseEntity<ResponseJsonDto> findByIdComment(@PathVariable int id) {
        try {
            Optional<Comment> comment = commentService.findByIdAccount(id);
            if (comment.isPresent()) {
                LOGGER.info("Getting list of comment for user - {} ", JwtRequestFilter.id);
                return ResponseEntity.ok(ResponseJsonDto.buildOk(comment));
            } else {
                return ResponseEntity.ok(ResponseJsonDto.buildOk("No comment found with id " + id));
            }
        } catch (Exception e) {
            LOGGER.error("Exception on getting user by id: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value = "/comments", produces = "application/json")
    ResponseEntity<ResponseJsonDto> createOrUpdateComment(@RequestBody CommentDto newComment) {
        try {
            LOGGER.info("Creating or updating a comment");
            return ResponseEntity.ok(ResponseJsonDto.buildOk(commentService.createOrUpdate(newComment)));
        } catch (Exception e) {
            LOGGER.error("Exception on creating or updating a comment: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping(value = {"/comments/{id}"}, produces = "application/json")
    ResponseEntity<ResponseJsonDto> deleteByIdComment(@PathVariable int id) {
        try {
            LOGGER.info("Deleting comment by id");
            return ResponseEntity.ok(ResponseJsonDto.buildOk(commentService.deleteById(id)));
        } catch (Exception e) {
            LOGGER.error("Exception on deleting comment by id: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }
}
