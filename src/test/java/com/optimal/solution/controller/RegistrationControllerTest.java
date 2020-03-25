package com.optimal.solution.controller;

import com.optimal.solution.model.User;
import com.optimal.solution.service.UserService;
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

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
class RegistrationControllerTest {

    private MockMvc mockMvc;

    private User user;

    @InjectMocks
    private RegistrationController registrationController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
        user = new User();
        user.setId(1);
        user.setLogin("admin");
        user.setPassword("admin");
    }

    @Test
    void createUser() throws Exception {
        when(userService.create(user)).thenAnswer(invocation -> 1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"login\":\"admin\",\"password\":\"admin\",\"active\":true,\"roles\":\"ADMIN\"}"))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content()
                        .string("User with login - admin exists! Change login and try again."));
    }
}