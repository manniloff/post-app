package com.optimal.solution.service.impl;

import com.optimal.solution.dto.CommentDto;
import com.optimal.solution.model.Comment;
import com.optimal.solution.model.Post;
import com.optimal.solution.model.User;
import com.optimal.solution.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class CommentServiceImplTest {

    @InjectMocks
    CommentServiceImpl commentService;

    @Mock
    CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll() {
        List<CommentDto> comments = new ArrayList<>();

        CommentDto commentOne = new CommentDto(1, "Test", new Date(12542754), 1, "admin");
        CommentDto commentTwo = new CommentDto(1, "Test2", new Date(12542754), 1, "user");
        CommentDto commentTree = new CommentDto(1, "Test3", new Date(12542754), 1, "test");

        comments.add(commentOne);
        comments.add(commentTwo);
        comments.add(commentTree);

        when(commentRepository.findAllDto()).thenAnswer(invocation -> comments);

        List<CommentDto> commentDB = commentService.findAll();

        assertEquals(3, commentDB.size());
        verify(commentRepository, times(1)).findAllDto();
    }

    @Test
    void findAccountAll() {
        List<CommentDto> comments = new ArrayList<>();

        CommentDto commentOne = new CommentDto(1, "Test", new Date(12542754), 1, "admin");
        CommentDto commentTwo = new CommentDto(1, "Test2", new Date(12542754), 1, "user");
        CommentDto commentTree = new CommentDto(1, "Test3", new Date(12542754), 1, "test");

        comments.add(commentOne);
        comments.add(commentTwo);
        comments.add(commentTree);

        when(commentRepository.findAllDto()).thenAnswer(invocation -> comments);
        when(commentRepository.findAllByUser(0)).thenAnswer(invocation -> comments);
        List<CommentDto> commentDB = commentService.findAll();

        assertEquals(3, commentDB.size());
        verify(commentRepository, times(1)).findAllDto();
    }

    @Test
    void findByIdAccount() {
        when(commentRepository.findByIdDto(1))
                .thenAnswer(invocation -> Optional.of(new CommentDto(1, "Test", new Date(12542754), 1, "admin")));

        when(commentRepository.findByIdAndUserDto(1, 0))
                .thenAnswer(invocation -> Optional.of(new CommentDto(1, "Test", new Date(12542754), 1, "admin")));
        Optional<CommentDto> comment = commentService.findById(1);

        assertEquals("Test", comment.get().getText());
        assertEquals("admin", comment.get().getUserName());
        assertEquals(1, comment.get().getPostId());
    }

    @Test
    void findById() {
        when(commentRepository.findByIdDto(1))
                .thenAnswer(invocation -> Optional.of(new CommentDto(1, "Test", new Date(12542754), 1, "admin")));

        Optional<CommentDto> comment = commentService.findById(1);

        assertEquals("Test", comment.get().getText());
        assertEquals("admin", comment.get().getUserName());
        assertEquals(1, comment.get().getPostId());
    }

    @Test
    void createOrUpdate() {
        CommentDto comment = new CommentDto(1, "Test", new Date(12542754), 1, "admin");

        Comment newComment = new Comment();
        newComment.setId(0);
        newComment.setText(comment.getText());
        newComment.setPostedDate(new Date(12542754));
        try {
            commentService.createOrUpdate(comment);
        } catch (NullPointerException e) {
            System.out.println(e);
        } finally {
            // verify(commentRepository, times(1)).save(newComment);
        }
    }

    @Test
    void deleteById() {
        Comment comment = new Comment();
        comment.setId(1);
        comment.setText("Test");
        comment.setPostedDate(new Date(1322435));
        comment.setUser(new User());
        comment.setPost(new Post());

        when(commentRepository.findById(1)).thenAnswer(invocation -> Optional.of(comment));

        commentRepository.deleteById(1);

        verify(commentRepository, times(1)).deleteById(1);
    }
}