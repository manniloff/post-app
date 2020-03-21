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

    void createOrUpdate(PostDto newPost);

    int deleteById(int id);

    List<PostsDto> findAccountAll();
}
