package com.optimal.solution.service;

import com.optimal.solution.dto.CommentDto;
import com.optimal.solution.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<CommentDto> findAll();

    Optional<CommentDto> findById(int id);

    void createOrUpdate(CommentDto newComment);

    Optional<Comment> deleteById(int id);

    List<CommentDto> findAccountAll();

    Optional<CommentDto> findByIdAccount(int id);
}
