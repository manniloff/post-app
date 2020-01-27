package com.optimal.solution.service.impl;

import com.optimal.solution.model.Comment;
import com.optimal.solution.model.Post;
import com.optimal.solution.repository.CommentRepository;
import com.optimal.solution.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);
    private final CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        LOGGER.info("Getting all Comments from db");
        return commentRepository.findAll();
    }

    @Override
    public Optional<Comment> findById(int id) {
        LOGGER.info("Getting Comment by id - {}", id);
        return commentRepository.findById(id);
    }

    @Override
    public int createOrUpdate(Comment newComment) {
        return commentRepository.findById(newComment.getId())
                .map(comment -> {
                    LOGGER.info("Updating comment with id - {}", comment.getId());
                    comment.setPost(new Post(newComment.getId()));
                    comment.setPostedDate(new Date());
                    comment.setText(newComment.getText());

                    return commentRepository.save(comment).getId();
                }).orElseGet(() -> {
                    LOGGER.info("Creating comment with id - {}", newComment.getId());
                    Comment comment = new Comment();

                    comment.setPost(new Post(newComment.getId()));
                    comment.setPostedDate(new Date());
                    comment.setText(newComment.getText());

                    return commentRepository.save(comment).getId();
                });
    }

    @Override
    public Optional<Comment> deleteById(int id) {
        Optional<Comment> comment = commentRepository.findById(id);

        if (comment.isPresent()) {
            LOGGER.info("Deleting Comment with id - {}", id);
            commentRepository.deleteById(id);
        }
        return comment;
    }
}
