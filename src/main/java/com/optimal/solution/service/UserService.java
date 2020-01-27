package com.optimal.solution.service;

import com.optimal.solution.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(int id);

    int createOrUpdate(User newUser);

    Optional<User> deleteById(int id);
}
