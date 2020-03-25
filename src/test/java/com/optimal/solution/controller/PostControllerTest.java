package com.optimal.solution.controller;

import com.optimal.solution.dto.CommentsDto;
import com.optimal.solution.dto.PostDto;
import com.optimal.solution.dto.PostsDto;
import com.optimal.solution.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
class PostControllerTest {

    private MockMvc mockMvc;

    private PostsDto post;

    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
        post = new PostsDto(1, "Test", "Test", new Date(1223121443), "admin", "IT", Collections.singleton(new CommentsDto()));
    }

    @Test
    void findAll() throws Exception {
        List<PostsDto> allPosts = Collections.singletonList(post);

        when(postService.findAll()).thenAnswer(invocation -> allPosts);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/posts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"id\":1,\"title\":\"Test\",\"body\":\"Test\",\"createDate\":1223121443,\"author\":\"admin\",\"categoriesName\":\"IT\",\"comment\":[{\"text\":null,\"postedDate\":null,\"userName\":null}]}]"));
    }

    @Test
    void findById() throws Exception {
        PostsDto postDto = new PostsDto();
        postDto.setBody(post.getBody());
        postDto.setTitle(post.getTitle());

        when(postService.findById(1)).thenAnswer(invocation -> postDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/posts/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":0,\"title\":\"Test\",\"body\":\"Test\",\"createDate\":null,\"author\":null,\"categoriesName\":null,\"comment\":null}"));
    }
}