package com.optimal.solution.controller;

import com.optimal.solution.dto.ResponseJsonDto;
import com.optimal.solution.model.Category;
import com.optimal.solution.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private static final Logger LOGGER = LoggerFactory.getLogger("rest");
    private final CategoryService categoryService;

    @GetMapping(value = {"", "/"}, produces = "application/json")
    ResponseEntity<?> findAll() {
        try {
            LOGGER.info("Getting list of categories!");
            return ResponseEntity.ok(categoryService.findAll());
        } catch (Exception e) {
            LOGGER.error("Exception on getting list of categories: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = {"/{id}"}, produces = "application/json")
    ResponseEntity<?> findById(@PathVariable int id) {
        try {
            LOGGER.info("Getting category by id");
            return ResponseEntity.ok(categoryService.findById(id));
        } catch (Exception e) {
            LOGGER.error("Exception on getting category by id: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value = {"", "/"}, produces = "application/json")
    ResponseEntity<?> createOrUpdate(@RequestBody Category newCategory) {
        try {
            LOGGER.info("Creating or updating a category");
            categoryService.createOrUpdate(newCategory);
            return new ResponseEntity<>("Created or Updated", HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Exception on creating or updating a category: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping(value = {"/{id}"}, produces = "application/json")
    ResponseEntity<?> deleteById(@PathVariable int id) {
        try {
            LOGGER.info("Deleting category by id");
            categoryService.deleteById(id);
            return ResponseEntity.ok("Deleted category with id - " + id);
        } catch (Exception e) {
            LOGGER.error("Exception on deleting category by id: ", e);
            return new ResponseEntity<>(ResponseJsonDto.buildNoContent(), HttpStatus.NO_CONTENT);
        }
    }
}
