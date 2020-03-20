package com.optimal.solution.service;

import com.optimal.solution.dto.PostDto;
import com.optimal.solution.dto.PostsDto;
import com.optimal.solution.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<PostsDto> findAll();

    PostsDto findById(int id);

    PostsDto findByIdAccount(int id);

    int createOrUpdate(PostDto newPost);

    Optional<Post> deleteById(int id);

    List<PostsDto> findAccountAll();
}
