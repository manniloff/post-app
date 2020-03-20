package com.optimal.solution.service.impl;

import com.optimal.solution.auth.filter.JwtRequestFilter;
import com.optimal.solution.dto.CommentsDto;
import com.optimal.solution.dto.PostDto;
import com.optimal.solution.dto.PostsDto;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private static final Logger LOGGER = LoggerFactory.getLogger("repo");
    private final PostRepository postRepository;

    @Override
    public List<PostsDto> findAll() {
        LOGGER.info("Getting list of Posts from db");
        List<Post> posts = postRepository.findAll();

        return getDataPosts(posts);
    }

    @Override
    public List<PostsDto> findAccountAll() {
        if (JwtRequestFilter.role.equals(Roles.ADMIN)) {
            LOGGER.info("Getting list of Posts from db");
            List<Post> posts = postRepository.findAll();

            return getDataPosts(posts);
        } else {
            LOGGER.info("Getting list of Posts from db");
            List<Post> posts = postRepository.findAllByUser(JwtRequestFilter.id);

            return getDataPosts(posts);
        }
    }

    @Override
    public PostsDto findById(int id) {
        LOGGER.info("Getting Post by Id - {} from db", id);
        return getDataPost(postRepository.findById(id).get());
    }

    @Override
    public PostsDto findByIdAccount(int id) {
        if (JwtRequestFilter.role.equals(Roles.ADMIN)) {
            LOGGER.info("Getting Post by Id - {} from db", id);
            return getDataPost(postRepository.findById(id).get());
        } else {
            LOGGER.info("Getting list of Posts from db");
            return getDataPost(postRepository.findByIdAndUser(id, JwtRequestFilter.id).get());
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

    private List<PostsDto> getDataPosts(List<Post> posts) {
        return posts.stream().map(post -> {
            PostsDto postDto = new PostsDto();

            postDto.setId(post.getId());
            postDto.setBody(post.getBody());
            postDto.setCategoriesName(post.getCategories().stream().map(Category::getName).findFirst().get());
            postDto.setAuthor(post.getUser().getLogin());
            postDto.setCreateDate(post.getCreateDate());
            postDto.setTitle(post.getTitle());

            Set<CommentsDto> commentsDtoSet = post.getComments().stream().map(comment -> {
                CommentsDto commentDto = new CommentsDto();

                commentDto.setText(comment.getText());
                commentDto.setPostedDate(comment.getPostedDate());
                commentDto.setUserName(comment.getUser().getLogin());

                return commentDto;
            }).collect(Collectors.toSet());

            postDto.setComment(commentsDtoSet);

            return postDto;
        }).collect(Collectors.toList());
    }

    private PostsDto getDataPost(Post post) {
        PostsDto postsDto = new PostsDto();

        postsDto.setTitle(post.getTitle());
        postsDto.setAuthor(post.getUser().getLogin());
        postsDto.setId(post.getId());
        postsDto.setBody(post.getBody());
        postsDto.setCreateDate(post.getCreateDate());
        postsDto.setCategoriesName(post.getCategories().stream().map(Category::getName).findFirst().get());

        Set<CommentsDto> commentsDtoSet = post.getComments().stream().map(comment -> {
            CommentsDto commentDto = new CommentsDto();

            commentDto.setText(comment.getText());
            commentDto.setPostedDate(comment.getPostedDate());
            commentDto.setUserName(comment.getUser().getLogin());

            return commentDto;
        }).collect(Collectors.toSet());

        postsDto.setComment(commentsDtoSet);

        return postsDto;
    }
}
