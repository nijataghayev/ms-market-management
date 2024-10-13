package com.example.marketmanagement.controller;

import com.example.marketmanagement.model.CategoryDto;
import com.example.marketmanagement.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: nijataghayev
 */

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Get all categories", description = "Returns a list of all categories.")
    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @Operation(summary = "Get category by ID", description = "Returns the category details for the given category ID.")
    @GetMapping("/{categoryId}")
    public CategoryDto getCategory(@PathVariable Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @Operation(summary = "Create a new category", description = "Creates a new category with the provided details.")
    @PostMapping
    public void createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);
    }

    @Operation(summary = "Update category by ID", description = "Updates the details of the category for the given category ID.")
    @PutMapping("/{categoryId}")
    public void updateCategory(@PathVariable Long categoryId, @RequestBody @Valid CategoryDto categoryDto) {
        categoryService.updateCategory(categoryId, categoryDto);
    }

    @DeleteMapping("/{categoryId}")
    @Operation(summary = "Delete category by ID", description = "Deletes the category for the given category ID.")
    public void deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
