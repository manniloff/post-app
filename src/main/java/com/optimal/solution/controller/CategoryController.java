package com.optimal.solution.controller;

import com.optimal.solution.model.Category;
import com.optimal.solution.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;

    @GetMapping(value = {"", "/"}, produces = "application/json")
    List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping(value = {"/{id}"}, produces = "application/json")
    Optional<Category> findById(@PathParam("id") int id) {
        return categoryService.findById(id);
    }

    @PostMapping(value = {"", "/"}, produces = "application/json")
    int createOrUpdate(@RequestBody Category newCategory) {
        return categoryService.createOrUpdate(newCategory);
    }

    @DeleteMapping(value = {"/{id}"}, produces = "application/json")
    Optional<Category> deleteById(@PathParam("id") int id) {
        return categoryService.deleteById(id);
    }
}
