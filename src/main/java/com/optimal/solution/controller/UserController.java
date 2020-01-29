package com.optimal.solution.controller;

import com.optimal.solution.model.User;
import com.optimal.solution.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping(value = {"", "/"}, produces = "application/json")
    List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    Optional<User> findById(@PathVariable int id) {
        return userService.findById(id);
    }

    @PostMapping(value = {"", "/"}, produces = "application/json")
    int createOrUpdate(@RequestBody User newUser) {
        return userService.createOrUpdate(newUser);
    }

    @DeleteMapping(value = {"/{id}"}, produces = "application/json")
    Optional<User> deleteById(@PathVariable int id) {
        return userService.deleteById(id);
    }
}
