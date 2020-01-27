package com.optimal.solution.service.impl;

import com.optimal.solution.model.Category;
import com.optimal.solution.model.Post;
import com.optimal.solution.repository.CategoryRepository;
import com.optimal.solution.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        LOGGER.info("Getting all categories from db");
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(int id) {
        LOGGER.info("Getting category by id - {}", id);
        return categoryRepository.findById(id);
    }

    @Override
    public int createOrUpdate(Category newCategory) {
        return categoryRepository.findById(newCategory.getId())
                .map(category -> {
                    LOGGER.info("Updating category with id - {}", category.getId());
                    category.setName(newCategory.getName());
                    category.setPosts(newCategory.getPosts()
                            .stream()
                            .map(post -> {
                                return new Post(post.getId());
                            }).collect(Collectors.toSet()));

                    return categoryRepository.save(category).getId();
                }).orElseGet(() -> {
                    LOGGER.info("Creating category with id - {} and name - {}", newCategory.getId(), newCategory.getName());
                    Category category = new Category();

                    category.setName(newCategory.getName());
                    if (newCategory.getPosts() != null) {
                        category.setPosts(newCategory.getPosts()
                                .stream()
                                .map(post -> {
                                    return new Post(post.getId());
                                }).collect(Collectors.toSet()));
                    }

                    return categoryRepository.save(category).getId();
                });
    }

    @Override
    public Optional<Category> deleteById(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            LOGGER.info("Deleting category by id - {}", id);
            categoryRepository.deleteById(id);
        }
        return category;
    }
}
