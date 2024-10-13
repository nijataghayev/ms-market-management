package com.example.marketmanagement.service

import com.example.marketmanagement.dao.entity.CategoryEntity
import com.example.marketmanagement.dao.repository.CategoryRepository
import com.example.marketmanagement.mapper.CategoryMapper
import com.example.marketmanagement.model.CategoryDto
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

/**
 * @author: nijataghayev
 */

class CategoryServiceTest extends Specification {
    private EnhancedRandom random
    private CategoryService categoryService
    private CategoryRepository categoryRepository
    private CategoryMapper categoryMapper

    void setup() {
        random = EnhancedRandomBuilder.aNewEnhancedRandom()
        categoryRepository = Mock()
        categoryMapper = Mock()
        categoryService = new CategoryService(categoryRepository, categoryMapper)
    }

    def "GetAllCategories"() {
        given:
        def categoryEntities = random.objects(CategoryEntity, 5).toList()
        def categoryDtos = categoryEntities.collect { random.nextObject(CategoryDto) }
        categoryRepository.findAll() >> categoryEntities
        categoryEntities.eachWithIndex { entity, i ->
            categoryMapper.mapToDto(entity) >> categoryDtos[i]
        }

        when:
        def result = categoryService.getAllCategories()

        then:
        result.size() == categoryDtos.size()
        result == categoryDtos
    }

    def "GetCategory"() {
        given:
        def categoryId = 1L
        def categoryEntity = random.nextObject(CategoryEntity)
        def categoryDto = random.nextObject(CategoryDto)

        when:
        def result = categoryService.getCategory(categoryId)

        then:
        1 * categoryRepository.findById(categoryId) >> Optional.of(categoryEntity)
        1 * categoryMapper.mapToDto(categoryEntity) >> categoryDto

        result == categoryDto
    }

    def "CreateCategory"() {
        given:
        def categoryDto = random.nextObject(CategoryDto)
        def categoryEntity = random.nextObject(CategoryEntity)

        when:
        categoryService.createCategory(categoryDto)

        then:
        1 * categoryMapper.mapToEntity(categoryDto) >> categoryEntity
        1 * categoryRepository.save(categoryEntity)
    }

    def "UpdateCategory"() {
        given:
        def categoryId = 1L
        def categoryDto = random.nextObject(CategoryDto)
        def existingCategory = random.nextObject(CategoryEntity)
        def updateCategory = random.nextObject(CategoryEntity)

        when:
        categoryService.updateCategory(categoryId, categoryDto)

        then:
        1 * categoryRepository.findById(categoryId) >> Optional.of(existingCategory)
        1 * categoryMapper.mapToEntity(categoryDto) >> updateCategory
        1 * categoryRepository.save(existingCategory)
    }

    def "DeleteCategory"() {
        given:
        def categoryId = 1L
        def categoryEntity = random.nextObject(CategoryEntity)

        when:
        categoryService.deleteCategory(categoryId)

        then:
        1 * categoryRepository.findById(categoryId) >> Optional.of(categoryEntity)
        1 * categoryRepository.delete(categoryEntity)
    }
}
