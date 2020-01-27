package com.optimal.solution.service;

import com.optimal.solution.model.Post;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> findAll();

    Optional<Post> findById(int id);

    int createOrUpdate(Post newPost);

    Optional<Post> deleteById(int id);
}
