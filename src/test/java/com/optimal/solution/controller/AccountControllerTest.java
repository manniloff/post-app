package com.optimal.solution.controller;

import com.optimal.solution.dto.CommentDto;
import com.optimal.solution.dto.CommentsDto;
import com.optimal.solution.dto.PostsDto;
import com.optimal.solution.dto.UserDto;
import com.optimal.solution.model.User;
import com.optimal.solution.service.CommentService;
import com.optimal.solution.service.PostService;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
class AccountControllerTest {

    private MockMvc mockMvc;

    private PostsDto post;

    private CommentDto comment;

    @InjectMocks
    private AccountController accountController;

    @Mock
    private UserService userService;

    @Mock
    private PostService postService;

    @Mock
    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();

        post = new PostsDto(1, "Test", "Test", new Date(1223121443), "admin", "IT", Collections.singleton(new CommentsDto()));
        comment = new CommentDto(1, "admin", new Date(12542754), 1, "admin");
    }


    @Test
    void findUserByIdUser() throws Exception {
        UserDto user = new UserDto("admin", "admin", true, Roles.ADMIN);
        when(userService.findById(0)).thenAnswer(invocation -> Optional.of(user));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/accounts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"login\":\"admin\",\"password\":\"admin\",\"role\":\"ADMIN\",\"active\":true}"));
    }

    @Test
    void createOrUpdateUser() throws Exception {
        User user = new User("admin", "admin", true, Roles.ADMIN);
        when(userService.update(user, 1)).thenAnswer(invocation -> 1);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"login\":\"admin\",\"password\":\"admin\",\"active\":true,\"roles\":\"ADMIN\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .string("Created or Updated"));
    }

    @Test
    void findAllPost() throws Exception {
        List<PostsDto> allPosts = Collections.singletonList(post);

        when(postService.findAccountAll()).thenAnswer(invocation -> allPosts);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/accounts/posts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"id\":1,\"title\":\"Test\",\"body\":\"Test\",\"createDate\":1223121443,\"author\":\"admin\",\"categoriesName\":\"IT\",\"comment\":[{\"text\":null,\"postedDate\":null,\"userName\":null}]}]"));
    }

    @Test
    void findByIdPost() throws Exception {
        when(postService.findByIdAccount(1)).thenAnswer(invocation -> post);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/accounts/posts/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"title\":\"Test\",\"body\":\"Test\",\"createDate\":1223121443,\"author\":\"admin\",\"categoriesName\":\"IT\",\"comment\":[{\"text\":null,\"postedDate\":null,\"userName\":null}]}"));
    }

    @Test
    void createOrUpdatePost() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/accounts/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"title\":\"Test\",\"body\":\"Test\",\"createDate\":1223121443,\"updateDate\":\"1223123443\",\"userId\":\"1\",\"categoriesId\":[\"1\"],\"commentsId\":[\"1\"]}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .string("Created or Updated"));
    }

    @Test
    void deleteByIdPost() throws Exception {
        when(postService.deleteById(1)).thenAnswer(invocation -> 1);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/accounts/posts/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("1"));
    }

    @Test
    void findAllComment() throws Exception {
        List<CommentDto> allPosts = Collections.singletonList(comment);

        when(commentService.findAccountAll()).thenAnswer(invocation -> allPosts);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/accounts/comments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"id\":1,\"text\":\"admin\",\"postedDate\":12542754,\"postId\":1,\"userName\":\"admin\"}]"));
    }

    @Test
    void findByIdComment() throws Exception {
        when(commentService.findByIdAccount(1)).thenAnswer(invocation -> Optional.of(comment));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/accounts/comments/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"text\":\"admin\",\"postedDate\":12542754,\"postId\":1,\"userName\":\"admin\"}"));
    }

    @Test
    void createOrUpdateComment() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/accounts/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"text\":\"Test\",\"postedDate\":1223121443,\"postId\":\"1\",\"userName\":\"1\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .string("Created or Updated"));
    }

    @Test
    void deleteByIdComment() throws Exception {
        when(commentService.deleteById(1)).thenAnswer(invocation -> 1);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/accounts/comments/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("1"));
    }
}