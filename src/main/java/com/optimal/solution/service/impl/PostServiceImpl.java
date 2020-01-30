package com.optimal.solution.service.impl;

import com.optimal.solution.auth.filter.JwtRequestFilter;
import com.optimal.solution.dto.PostDto;
import com.optimal.solution.model.Category;
import com.optimal.solution.model.Post;
import com.optimal.solution.model.User;
import com.optimal.solution.repository.PostRepository;
import com.optimal.solution.service.PostService;
import com.optimal.solution.util.Roles;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);
    private final PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        if (JwtRequestFilter.role.equals(Roles.ADMIN)) {
            LOGGER.info("Getting list of Posts from db");
            return postRepository.findAll();
        } else {
            LOGGER.info("Getting list of Posts from db");
            return postRepository.findAllByUser(JwtRequestFilter.id);
        }
    }

    @Override
    public Optional<Post> findById(int id) throws Exception {
        if (JwtRequestFilter.role.equals(Roles.ADMIN)) {
            LOGGER.info("Getting Post by Id - {} from db", id);
            return postRepository.findById(id);
        } else {
            LOGGER.info("Getting list of Posts from db");
            return postRepository.findByIdAndUser(id, JwtRequestFilter.id);
        }
    }

    @Override
    public int createOrUpdate(PostDto newPost) {
        return postRepository.findByIdAndUser(newPost.getId(), JwtRequestFilter.id)
                .map(post -> {
                    LOGGER.info("Updating Post with Id - {} and Title - {}", post.getId(), post.getTitle());

                    post.setTitle(newPost.getTitle());
                    post.setBody(newPost.getBody());
                    post.setCategories(newPost.getCategoriesId()
                            .stream()
                            .map(Category::new)
                            .collect(Collectors.toSet()));
                    post.setUpdateDate(new Date());
                    post.setUser(new User(newPost.getUserId()));

                    return postRepository.save(post).getId();
                }).orElseGet(() -> {
                    LOGGER.info("Creating Post with title {}", newPost.getTitle());

                    Post post = new Post();

                    post.setTitle(newPost.getTitle());
                    post.setBody(newPost.getBody());
                    post.setCategories(newPost.getCategoriesId()
                            .stream()
                            .map(Category::new)
                            .collect(Collectors.toSet()));
                    post.setCreateDate(new Date());
                    post.setUser(new User(newPost.getUserId()));

                    return postRepository.save(post).getId();
                });
    }

    @Override
    public Optional<Post> deleteById(int id) {
        Optional<Post> post;
        if (JwtRequestFilter.role.equals(Roles.ADMIN)) {
            LOGGER.info("Getting Post by Id - {} from db", id);
            post = postRepository.findById(id);
        } else {
            LOGGER.info("Getting list of Posts from db");
            post = postRepository.findByIdAndUser(id, JwtRequestFilter.id);
        }

        if (post.isPresent()) {
            LOGGER.info("Deleting Post by Id - {} from db", id);
            postRepository.deleteByIds(id);
        }
        return post;
    }
}
