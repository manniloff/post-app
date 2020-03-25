package com.optimal.solution.service.impl;

import com.optimal.solution.model.Category;
import com.optimal.solution.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class CategoryServiceImplTest {

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll() {
        List<Category> categories = new ArrayList<>();

        Category categoryOne = new Category(1, "IT");
        Category categoryTwo = new Category(2, "Business");
        Category categoryTree = new Category(3, "Policy");

        categories.add(categoryOne);
        categories.add(categoryTwo);
        categories.add(categoryTree);

        when(categoryRepository.findAll()).thenAnswer(invocation -> categories);

        List<Category> categoriesDB = categoryService.findAll();

        assertEquals(3, categoriesDB.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(categoryRepository.findById(1))
                .thenAnswer(invocation -> Optional.of(new Category(1, "IT")));

        Optional<Category> category = categoryService.findById(1);

        assertEquals("IT", category.get().getName());
        assertEquals(1, category.get().getId());
    }

    @Test
    void createOrUpdate() {
        Category category = new Category(0, "IT");

        try {
            categoryService.createOrUpdate(category);
        } catch (NullPointerException e) {
            System.out.println(e);
        } finally {
            verify(categoryRepository, times(1)).save(category);
        }
    }

    @Test
    void deleteById() {
        Category category = new Category(1, "IT");

        when(categoryRepository.findById(1)).thenAnswer(invocation -> Optional.of(category));

        categoryService.deleteById(1);

        verify(categoryRepository, times(1)).deleteById(1);
    }
}