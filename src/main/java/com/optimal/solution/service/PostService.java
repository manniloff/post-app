package com.optimal.solution.service;

import com.optimal.solution.dto.PostDto;
import com.optimal.solution.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> findAll();

    Optional<Post> findById(int id);

    Optional<Post> findByIdAccount(int id);

    int createOrUpdate(PostDto newPost);

    Optional<Post> deleteById(int id);

    List<Post> findAccountAll();
}
