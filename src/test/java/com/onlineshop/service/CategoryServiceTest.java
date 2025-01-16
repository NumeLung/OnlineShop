package com.onlineshop.service;

import com.onlineshop.entities.Category;
import com.onlineshop.repositories.CategoryRepository;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.onlineshop.services.CategoryService;

class CategoryServiceTest {

    private CategoryService categoryService;
    private CategoryRepository categoryRepository;


    @Test
    void getAllCategories_ShouldReturnCategoryList() {
        // Arrange
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Electronics");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Clothing");

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        // Act
        List<Category> result = categoryService.getAllCategories();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Electronics", result.get(0).getName());
        assertEquals("Clothing", result.get(1).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getCategoryById_ShouldReturnCategory() {
        // Arrange
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // Act
        Optional<Category> result = categoryService.getCategoryById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Electronics", result.get().getName());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void getCategoryById_ShouldReturnEmptyOptionalWhenCategoryNotFound() {
        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Category> result = categoryService.getCategoryById(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void saveCategory_ShouldReturnSavedCategory() {
        // Arrange
        Category category = new Category();
        category.setName("Electronics");

        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("Electronics");

        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        // Act
        Category result = categoryService.saveCategory(category);

        // Assert
        assertNotNull(result);
        assertEquals("Electronics", result.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void deleteCategory_ShouldInvokeDeleteById() {
        // Act
        categoryService.deleteCategory(1L);

        // Assert
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void getCategoryByName_ShouldReturnCategory() {
        // Arrange
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        when(categoryRepository.findByName("Electronics")).thenReturn(category);

        // Act
        Category result = categoryService.getCategoryByName("Electronics");

        // Assert
        assertNotNull(result);
        assertEquals("Electronics", result.getName());
        verify(categoryRepository, times(1)).findByName("Electronics");
    }
}
