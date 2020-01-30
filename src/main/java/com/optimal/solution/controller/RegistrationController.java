package com.optimal.solution.controller;

import com.optimal.solution.model.User;
import com.optimal.solution.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    private final UserService userService;

    @PostMapping(value = {"", "/"}, produces = "application/json")
    ResponseEntity<?> createUser(@RequestBody User newUser) {
        try {
            LOGGER.info("Creating or updating a user");
            return ResponseEntity.ok(userService.createOrUpdate(newUser));
        } catch (Exception e) {
            LOGGER.error("Error with creating or updating an user!", e);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
