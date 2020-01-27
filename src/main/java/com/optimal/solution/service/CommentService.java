package com.optimal.solution.service;

import com.optimal.solution.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<Comment> findAll();

    Optional<Comment> findById(int id);

    int createOrUpdate(Comment newComment);

    Optional<Comment> deleteById(int id);
}
