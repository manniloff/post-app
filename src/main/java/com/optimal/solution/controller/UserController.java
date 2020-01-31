package com.optimal.solution.controller;

import com.optimal.solution.model.User;
import com.optimal.solution.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping(value = {"", "/"}, produces = "application/json")
    ResponseEntity<?> findAll() {
        try {
            LOGGER.info("Getting list of users!");
            return ResponseEntity.ok(userService.findAll());
        } catch (Exception e) {
            LOGGER.error("Error with getting list of users!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<?> findById(@PathVariable int id) {
        try {
            LOGGER.info("Getting user by id");
            return ResponseEntity.ok(userService.findById(id));
        } catch (Exception e) {
            LOGGER.error("Error with getting user by id!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value = {"", "/"}, produces = "application/json")
    ResponseEntity<?> create(@RequestBody User newUser) {
        try {
            int id = userService.create(newUser);
            if (id != 0) {
                LOGGER.info("Creating or updating a user");
                return ResponseEntity.ok(id);
            }
            return ResponseEntity.ok("User with login - " + newUser.getLogin() + ", exists! Change login and try again.");
        } catch (Exception e) {
            LOGGER.error("Error with creating or updating an user!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping(value = {"", "/"}, produces = "application/json")
    ResponseEntity<?> update(@RequestBody User newUser) {
        try {
            LOGGER.info("Creating or updating a user");
            return ResponseEntity.ok(userService.update(newUser));
        } catch (Exception e) {
            LOGGER.error("Error with creating or updating an user!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping(value = {"/{id}"}, produces = "application/json")
    ResponseEntity<?> deleteById(@PathVariable int id) {
        try {
            LOGGER.info("Deleting user by id");
            return ResponseEntity.ok(userService.deleteById(id));
        } catch (Exception e) {
            LOGGER.error("Error with deleting user by id!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
