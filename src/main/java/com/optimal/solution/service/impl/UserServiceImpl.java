package com.optimal.solution.service.impl;

import com.optimal.solution.model.User;
import com.optimal.solution.repository.UserRepository;
import com.optimal.solution.service.UserService;
import com.optimal.solution.util.Roles;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        LOGGER.info("Getting list of Users from db");
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(int id) {
        LOGGER.info("Getting User by Id - {} from db", id);
        return userRepository.findById(id);
    }

    @Override
    public int createOrUpdate(User newUser) {
        return userRepository.findByLogin(newUser.getLogin())
                .map(user -> {
                    LOGGER.info("Updating User with Id - {} and Login - {}", user.getId(), user.getLogin());

                    user.setPassword(newUser.getPassword());
                    user.setActive(newUser.isActive());
                    user.setRoles(newUser.getRoles().equals("null") ? Roles.USER : newUser.getRoles());

                    return userRepository.save(user).getId();
                }).orElseGet(() -> {
                    LOGGER.info("Creating User with login {}", newUser.getLogin());

                    User user = new User();

                    user.setLogin(newUser.getLogin());
                    user.setPassword(newUser.getPassword());
                    user.setActive(true);
                    user.setRoles(newUser.getRoles() == null ? Roles.USER : newUser.getRoles());

                    return userRepository.save(user).getId();
                });
    }

    @Override
    public int update(User newUser) {
        return userRepository.findByLogin(newUser.getLogin())
                .map(user -> {
                    LOGGER.info("Updating User with Id - {} and Login - {}", user.getId(), user.getLogin());

                    user.setPassword(newUser.getPassword());
                    user.setActive(newUser.isActive());
                    user.setRoles(Roles.USER);

                    return userRepository.save(user).getId();
                }).get();
    }

    @Override
    public Optional<User> deleteById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            LOGGER.info("Deleting User by Id - {} from db", id);
            userRepository.deleteById(id);
        }
        return user;
    }
}
