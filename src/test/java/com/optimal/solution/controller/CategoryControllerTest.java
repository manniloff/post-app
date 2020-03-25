package com.optimal.solution.controller;

import com.optimal.solution.model.Category;
import com.optimal.solution.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
class CategoryControllerTest {

    private MockMvc mockMvc;

    private Category category;

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
        category = new Category(1, "IT");
    }

    @Test
    void findAll() throws Exception {
        List<Category> allCategories = Collections.singletonList(category);

        when(categoryService.findAll()).thenAnswer(invocation -> allCategories);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"id\":1,\"name\":\"IT\"}]"));
    }

    @Test
    void findById() throws Exception {
        when(categoryService.findById(1)).thenAnswer(invocation -> Optional.of(category));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"name\":\"IT\"}"));
    }

    @Test
    void createOrUpdate() throws Exception {
        when(categoryService.createOrUpdate(category)).thenAnswer(invocation -> 1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"IT\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .string("Created or Updated"));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/categories/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("Deleted category with id - 1"));
    }
}