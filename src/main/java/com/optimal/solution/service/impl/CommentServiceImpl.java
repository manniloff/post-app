package com.optimal.solution.service.impl;

import com.optimal.solution.auth.filter.JwtRequestFilter;
import com.optimal.solution.dto.CommentDto;
import com.optimal.solution.model.Comment;
import com.optimal.solution.model.Post;
import com.optimal.solution.model.User;
import com.optimal.solution.repository.CommentRepository;
import com.optimal.solution.service.CommentService;
import com.optimal.solution.util.Roles;
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
    private static final Logger LOGGER = LoggerFactory.getLogger("repo");
    private final CommentRepository commentRepository;

    @Override
    public List<CommentDto> findAll() {
        LOGGER.info("Getting all Comments from db");
        return commentRepository.findAllDto();
    }

    @Override
    public List<CommentDto> findAccountAll() {
        if (JwtRequestFilter.role.equals(Roles.ADMIN)) {
            LOGGER.info("Getting all Comments from db");
            return commentRepository.findAllDto();
        } else {
            LOGGER.info("Getting list of Posts from db");
            return commentRepository.findAllByUser(JwtRequestFilter.id);
        }
    }

    @Override
    public Optional<CommentDto> findByIdAccount(int id) {
        if (JwtRequestFilter.role.equals(Roles.ADMIN)) {
            LOGGER.info("Getting Comment by id - {}", id);
            return commentRepository.findByIdDto(id);
        } else {
            LOGGER.info("Getting list of Posts from db");
            return commentRepository.findByIdAndUserDto(id, JwtRequestFilter.id);
        }
    }

    @Override
    public Optional<CommentDto> findById(int id) {
        LOGGER.info("Getting Comment by id - {}", id);
        return commentRepository.findByIdDto(id);
    }

    @Override
    public void createOrUpdate(CommentDto newComment) {
        commentRepository.findByIdAndUser(newComment.getId(), JwtRequestFilter.id)
                .map(comment -> {
                    LOGGER.info("Updating comment with id - {}", comment.getId());
                    comment.setPost(new Post(newComment.getPostId()));
                    comment.setPostedDate(new Date());
                    comment.setUser(new User(JwtRequestFilter.id));
                    comment.setText(newComment.getText());

                    return commentRepository.save(comment).getId();
                }).orElseGet(() -> {
            LOGGER.info("Creating comment with id - {}", newComment.getId());
            Comment comment = new Comment();

            comment.setPost(new Post(newComment.getPostId()));
            comment.setPostedDate(new Date());
            comment.setUser(new User(JwtRequestFilter.id));
            comment.setText(newComment.getText());

            return commentRepository.save(comment).getId();
        });
    }

    @Override
    public Optional<Comment> deleteById(int id) {
        Optional<Comment> comment;
        if (JwtRequestFilter.role.equals(Roles.ADMIN)) {
            comment = commentRepository.findById(id);
        } else {
            LOGGER.info("Getting list of Posts from db");
            comment = commentRepository.findByIdAndUser(id, JwtRequestFilter.id);
        }
        if (comment.isPresent()) {
            LOGGER.info("Deleting Comment with id - {}", id);
            commentRepository.deleteById(id);
        }
        return comment;
    }
}
