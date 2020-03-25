package com.optimal.solution.controller;

import com.optimal.solution.model.User;
import com.optimal.solution.service.UserService;
import com.optimal.solution.util.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
class UserControllerTest {

    private MockMvc mockMvc;

    private User user;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        user = new User("admin", "admin", true, Roles.ADMIN);
        user.setId(1);
    }

    @Test
    void findAll() throws Exception {

        List<User> allUsers = Collections.singletonList(user);

        when(userService.findAll()).thenAnswer(invocation -> allUsers);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"id\":1,\"login\":\"admin\",\"password\":\"admin\",\"active\":true,\"roles\":\"ADMIN\"}]"));
    }

    @Test
    void findById() throws Exception {
        when(userService.findById(1)).thenAnswer(invocation -> Optional.of(user));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"login\":\"admin\",\"password\":\"admin\",\"active\":true,\"roles\":\"ADMIN\"}"));
    }

    @Test
    void create() throws Exception {
        when(userService.create(user)).thenAnswer(invocation -> 1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"login\":\"admin\",\"password\":\"admin\",\"active\":true,\"roles\":\"ADMIN\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .string("Created"));
    }

    @Test
    void update() throws Exception {
        when(userService.update(user, 1)).thenAnswer(invocation -> Optional.of(user));

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"login\":\"admin\",\"password\":\"admin\",\"active\":true,\"roles\":\"ADMIN\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"login\":\"admin\",\"password\":\"admin\",\"active\":true,\"roles\":\"ADMIN\"}"));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("Deleted user with id - 1"));
    }
}