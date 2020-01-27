package com.optimal.solution.service.impl;

import com.optimal.solution.model.Category;
import com.optimal.solution.model.Post;
import com.optimal.solution.model.User;
import com.optimal.solution.repository.PostRepository;
import com.optimal.solution.repository.UserRepository;
import com.optimal.solution.service.PostService;
import com.optimal.solution.util.Roles;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public List<Post> findAll() {
        LOGGER.info("Getting list of Posts from db");
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> findById(int id) {
        LOGGER.info("Getting Post by Id - {} from db", id);
        return postRepository.findById(id);
    }

    @Override
    public int createOrUpdate(Post newPost) {
        return postRepository.findById(newPost.getId())
                .map(post -> {
                    LOGGER.info("Updating Post with Id - {} and Title - {}", post.getId(), post.getTitle());

                    post.setTitle(newPost.getTitle());
                    post.setBody(newPost.getBody());
                    post.setCategories(newPost.getCategories()
                                            .stream()
                                            .map( category -> {
                                                return new Category(category.getId());
                                            })
                                            .collect(Collectors.toSet()));
                    post.setCreateDate(new Date());
                    post.setUser(userRepository.findByLogin(newPost.getUser().getLogin()).get());

                    return postRepository.save(post).getId();
                }).orElseGet(() -> {
                    LOGGER.info("Creating Post with title {}", newPost.getTitle());

                    Post post = new Post();

                    post.setTitle(newPost.getTitle());
                    post.setBody(newPost.getBody());
                    post.setCategories(newPost.getCategories()
                            .stream()
                            .map( category -> {
                                return new Category(category.getId());
                            })
                            .collect(Collectors.toSet()));
                    post.setCreateDate(new Date());
                    post.setUser(userRepository.findByLogin(newPost.getUser().getLogin()).get());

                    return postRepository.save(post).getId();
                });
    }

    @Override
    public Optional<Post> deleteById(int id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            LOGGER.info("Deleting Post by Id - {} from db", id);
            postRepository.deleteById(id);
        }
        return post;
    }
}
