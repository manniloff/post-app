package com.optimal.solution.controller;

import com.optimal.solution.auth.model.AuthRequest;
import com.optimal.solution.auth.model.LoginDetails;
import com.optimal.solution.auth.service.LoginDetailsService;
import com.optimal.solution.auth.util.JwtUtil;
import com.optimal.solution.model.User;
import com.optimal.solution.util.Roles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collection;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    private AuthRequest authRequest;

    private User user;

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private LoginDetailsService loginDetailsService;
    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        authRequest = new AuthRequest("admin", "admin");
        user = new User("admin", "admin", true, Roles.ADMIN);
    }

    @Test
    void createAuthToken() throws Exception {
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword())))
                .thenAnswer(invocation -> new Authentication() {
                    @Override
                    public boolean equals(Object another) {
                        return false;
                    }

                    @Override
                    public String toString() {
                        return null;
                    }

                    @Override
                    public int hashCode() {
                        return 0;
                    }

                    @Override
                    public String getName() {
                        return null;
                    }

                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return null;
                    }

                    @Override
                    public Object getCredentials() {
                        return null;
                    }

                    @Override
                    public Object getDetails() {
                        return null;
                    }

                    @Override
                    public Object getPrincipal() {
                        return null;
                    }

                    @Override
                    public boolean isAuthenticated() {
                        return false;
                    }

                    @Override
                    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

                    }
                });
        when(loginDetailsService.loadUserByUsername(authRequest.getLogin()))
                .thenAnswer(invocation -> new LoginDetails(user));
        when(jwtUtil.generateToken(new LoginDetails(user)))
                .thenAnswer(invocation -> "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzbGF2YSIsImV4cCI6MTU4NDgyMjgyMCwiaWF0IjoxNTg0Nzg2ODIwfQ.x_P5CDRREBdI_90E6v8xxOrKVhCLBBx3QXOVYflVqWg");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"admin\",\"password\":\"admin\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzbGF2YSIsImV4cCI6MTU4NDgyMjgyMCwiaWF0IjoxNTg0Nzg2ODIwfQ.x_P5CDRREBdI_90E6v8xxOrKVhCLBBx3QXOVYflVqWg"));
    }
}