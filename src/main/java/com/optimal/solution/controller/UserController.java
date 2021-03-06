package com.optimal.solution.controller;

import com.optimal.solution.dto.ResponseJsonDto;
import com.optimal.solution.model.User;
import com.optimal.solution.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@   Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger("rest");
    private final UserService userService;

    @GetMapping(value = {"", "/"}, produces = "application/json")
    ResponseEntity<?> findAll() {
        try {
            LOGGER.info("Getting list of users!");
            return ResponseEntity.ok(userService.findAll());
        } catch (Exception e) {
            LOGGER.error("Exception on getting list of users: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<?> findById(@PathVariable int id) {
        try {
            LOGGER.info("Getting user by id");
            return ResponseEntity.ok(userService.findById(id));
        } catch (Exception e) {
            LOGGER.error("Exception on getting user by id: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value = {"", "/"}, produces = "application/json")
    ResponseEntity<?> create(@RequestBody User newUser) {
        try {
            int id = userService.create(newUser);
            if (id != 0) {
                LOGGER.info("Creating or updating a user");
                return new ResponseEntity<>("Created",HttpStatus.CREATED);
            }
            return new ResponseEntity<>("User with login - " + newUser.getLogin() + ", exists! Change login and try again.", HttpStatus.CONFLICT);
        } catch (Exception e) {
            LOGGER.error("Exception on creating or updating an user: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<?> update(@RequestBody User newUser,@PathVariable int id) {
        try {
            LOGGER.info("Creating or updating a user");
            Optional<User> result = userService.update(newUser,id);
            if(result.isPresent()){
                return ResponseEntity.ok(result);
            }
            return new ResponseEntity<>("Not Modified", HttpStatus.NOT_MODIFIED);
        } catch (Exception e) {
            LOGGER.error("Exception on creating or updating an user: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping(value = {"/{id}"}, produces = "application/json")
    ResponseEntity<?> deleteById(@PathVariable int id) {
        try {
            LOGGER.info("Deleting user by id");
            userService.deleteById(id);
            return ResponseEntity.ok("Deleted user with id - " + id);
        } catch (Exception e) {
            LOGGER.error("Exception on deleting user by id: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }
}
