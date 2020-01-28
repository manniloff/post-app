package com.optimal.solution.service;

import com.optimal.solution.dto.CommentDto;
import com.optimal.solution.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<CommentDto> findAll();

    Optional<Comment> findById(int id);

    int createOrUpdate(CommentDto newComment);

    Optional<Comment> deleteById(int id);
}
