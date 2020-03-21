package com.optimal.solution.service;

import com.optimal.solution.dto.UserDto;
import com.optimal.solution.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> findAll();

    Optional<UserDto> findById(int id);

    int create(User newUser);

    Optional<User> update(User newUser, int id);

    void deleteById(int id);
}
