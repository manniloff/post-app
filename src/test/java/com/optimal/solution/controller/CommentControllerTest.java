package com.optimal.solution.controller;

import com.optimal.solution.dto.CommentDto;
import com.optimal.solution.service.CommentService;
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
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
class CommentControllerTest {

    private MockMvc mockMvc;

    private CommentDto comment;

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
        comment = new CommentDto(1, "admin", new Date(12542754), 1, "admin");
    }

    @Test
    void findAll() throws Exception {
        List<CommentDto> allComments = Collections.singletonList(comment);

        when(commentService.findAll()).thenAnswer(invocation -> allComments);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/comments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"id\":1,\"text\":\"admin\",\"postedDate\":12542754,\"postId\":1,\"userName\":\"admin\"}]"));
    }

    @Test
    void findById() throws Exception {
        when(commentService.findById(1)).thenAnswer(invocation -> Optional.of(comment));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/comments/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"text\":\"admin\",\"postedDate\":12542754,\"postId\":1,\"userName\":\"admin\"}"));
    }
}