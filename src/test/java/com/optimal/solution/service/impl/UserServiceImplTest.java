package com.optimal.solution.service.impl;

import com.optimal.solution.dto.UserDto;
import com.optimal.solution.model.User;
import com.optimal.solution.repository.UserRepository;
import com.optimal.solution.util.Roles;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll() {
        List<User> users = new ArrayList<>();

        User userOne = new User("Admin", "admin", true, Roles.ADMIN);
        User userTwo = new User("User", "user", true, Roles.USER);
        User userTree = new User("Test", "test", true, Roles.USER);

        users.add(userOne);
        users.add(userTwo);
        users.add(userTree);

        when(userRepository.findAllUsers()).thenAnswer(invocation -> users);

        List<UserDto> usersDB = userService.findAll();

        assertEquals(3, usersDB.size());
        verify(userRepository, times(1)).findAllUsers();
    }

    @Test
    public void findById() {
        when(userRepository.findByIdUsers(1))
                .thenAnswer(invocation -> Optional.of(new UserDto("Admin", "admin", true, Roles.ADMIN)));

        Optional<UserDto> user = userService.findById(1);

        assertEquals("Admin", user.get().getLogin());
        assertEquals("admin", user.get().getPassword());
        assertEquals(Roles.ADMIN, user.get().getRole());
    }

    @Test
    public void create() {
        User user = new User("Admin", "admin", true, Roles.ADMIN);

        try {
            userService.create(user);
        } catch (NullPointerException e) {
            System.out.println(e);
        } finally {
            verify(userRepository, times(1)).save(user);
        }
    }

    @Test
    public void deleteById() {
        User user = new User("Admin", "admin", true, Roles.ADMIN);

        when(userRepository.findById(1)).thenAnswer(invocation -> Optional.of(user));

        userService.deleteById(1);

        verify(userRepository, times(1)).deleteById(1);
    }
}