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
    ResponseEntity<ResponseJsonDto> createUser(@RequestBody User newUser) {
        try {
            int id = userService.create(newUser);
            if (id != 0) {
                LOGGER.info("Registration new user");
                return ResponseEntity.ok(ResponseJsonDto.buildOk(id));
            }
            return ResponseEntity.ok(ResponseJsonDto.buildOk("User with login - " + newUser.getLogin() + " exists! Change login and try again."));
        } catch (Exception e) {
            LOGGER.error("Exception on registration an user!", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }
}
