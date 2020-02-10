package com.optimal.solution.auth;

import com.optimal.solution.auth.service.LoginDetailsService;
import com.optimal.solution.auth.util.JwtUtil;
import com.optimal.solution.model.Post;
import com.optimal.solution.model.User;
import com.optimal.solution.repository.UserRepository;
import com.optimal.solution.service.PostService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private LoginDetailsService loginDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PostService postService;

    private String tocken;

    @Test
    @Transactional
    public void getUserTest() {
        Optional<User> actual = userRepository.findByLogin("admin");
        assertThat(actual.get().getId()).isEqualTo(1);
    }

    @Test
    public void accessTest() throws Exception {
        this.mockMvc.perform(get("/posts"))
                .andDo(print())
                .andExpect(status().isForbidden()).andReturn();
    }

    @Test
    public void authTest() {
        Authentication authRequest = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("admin", "admin"));

        Assert.assertTrue(authRequest.isAuthenticated());
    }

    @Test
    public void tokenTest() {
        UserDetails loginDetails = loginDetailsService
                .loadUserByUsername("admin");

        tocken = jwtUtil.generateToken(loginDetails);

        Assert.assertNotNull(tocken);
    }

    @Test
    public void infoTest() {
        List<Post> postList = postService.findAll();

        int id = postList.get(0).getId();

        Assert.assertEquals(id, 1);
    }
}
