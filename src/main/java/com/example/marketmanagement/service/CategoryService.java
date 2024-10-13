package com.example.marketmanagement.service;

import com.example.marketmanagement.dao.entity.CategoryEntity;
import com.example.marketmanagement.dao.repository.CategoryRepository;
import com.example.marketmanagement.exception.NotFoundException;
import com.example.marketmanagement.mapper.CategoryMapper;
import com.example.marketmanagement.model.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: nijataghayev
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> getAllCategories() {
        log.info("ActionLog.getAllCategories.start");
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = categoryEntityList.stream()
                .map(categoryMapper::mapToDto)
                .toList();
        log.info("ActionLog.getAllCategories.end");
        return categoryDtos;
    }

    public CategoryDto getCategory(Long categoryId) {
        log.info("ActionLog.getCategory.start categoryId {}", categoryId);
        var categoryEntity = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new NotFoundException(
                        "CATEGORY_NOT_FOUND",
                        String.format("ActionLog.getCategory.id %s not found", categoryId)
                ));
        var categoryDto = categoryMapper.mapToDto(categoryEntity);
        log.info("ActionLog.getCategory.end categoryId {}", categoryId);
        return categoryDto;
    }

    public void createCategory(CategoryDto categoryDto) {
        log.debug("ActionLog.createCategory.start category {}", categoryDto);
        var categoryEntity = categoryMapper.mapToEntity(categoryDto);
        categoryRepository.save(categoryEntity);
        log.debug("ActionLog.createCategory.start category {}", categoryDto);
    }

    public void updateCategory(Long categoryId, CategoryDto categoryDto) {
        CategoryEntity existingCategory = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new NotFoundException(
                        "CATEGORY_NOT_FOUND",
                        String.format("ActionLog.updateCategory.id %s not found", categoryId)
                ));

        CategoryEntity updateCategory = categoryMapper.mapToEntity(categoryDto);
        existingCategory.setName(updateCategory.getName());
        existingCategory.setDescription(updateCategory.getDescription());

        categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long categoryId) {
        log.info("ActionLog.deleteCategory.start categoryId {}", categoryId);
        CategoryEntity categoryEntity = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new NotFoundException(
                        "CATEGORY_NOT_FOUND",
                        String.format("ActionLog.deleteCategory.id %s not found", categoryId)
                ));
        categoryRepository.delete(categoryEntity);
        log.info("ActionLog.deleteCategory.end categoryId {}", categoryId);
    }
}
