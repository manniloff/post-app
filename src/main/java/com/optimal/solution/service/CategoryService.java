package com.optimal.solution.service;

import com.optimal.solution.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();

    Optional<Category> findById(int id);

    int createOrUpdate(Category newCategory);

    Optional<Category> deleteById(int id);
}
