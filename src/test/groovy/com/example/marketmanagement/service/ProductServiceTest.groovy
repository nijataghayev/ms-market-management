package com.example.marketmanagement.service

import com.example.marketmanagement.dao.entity.CategoryEntity
import com.example.marketmanagement.dao.entity.ProductEntity
import com.example.marketmanagement.dao.repository.CategoryRepository
import com.example.marketmanagement.dao.repository.ProductRepository
import com.example.marketmanagement.mapper.ProductMapper
import com.example.marketmanagement.model.ProductDto
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

/**
 * @author: nijataghayev
 */

class ProductServiceTest extends Specification {
    private EnhancedRandom random
    private ProductService productService
    private ProductRepository productRepository
    private CategoryRepository categoryRepository
    private ProductMapper productMapper

    void setup() {
        random = EnhancedRandomBuilder.aNewEnhancedRandom()
        productRepository = Mock()
        categoryRepository = Mock()
        productMapper = Mock()
        productService = new ProductService(productRepository, categoryRepository, productMapper)
    }

    def "GetAllProducts"() {
        given:
        def productEntities = random.objects(ProductEntity, 5).toList()
        def productDtos = productEntities.collect { random.nextObject(ProductDto) }
        productRepository.findAll() >> productEntities
        productEntities.eachWithIndex { entity, i ->
            productMapper.mapToDto(entity) >> productDtos[i]
        }

        when:
        def result = productService.getAllProducts()

        then:
        result.size() == productDtos.size()
        result == productDtos
    }

    def "GetProduct"() {
        given:
        def productId = 1L
        def productEntity = random.nextObject(ProductEntity)
        def productDto = random.nextObject(ProductDto)

        when:
        def result = productService.getProduct(productId)

        then:
        1 * productRepository.findById(productId) >> Optional.of(productEntity)
        1 * productMapper.mapToDto(productEntity) >> productDto

        result == productDto
    }

    def "CreateProduct"() {
        given:
        def productDto = random.nextObject(ProductDto)
        def categoryEntity = random.nextObject(CategoryEntity)
        def productEntity = random.nextObject(ProductEntity)

        when:
        productService.createProduct(productDto)

        then:
        1 * categoryRepository.findByName(productDto.getCategory().getName()) >> Optional.of(categoryEntity)
        1 * productMapper.mapToEntity(productDto) >> productEntity
        productEntity.setCategory(categoryEntity)
        1 * productRepository.save(productEntity)
    }

    def "UpdateProduct"() {
        given:
        def productId = 1L
        def existingProduct = random.nextObject(ProductEntity)
        def updateProduct = random.nextObject(ProductEntity)
        def productDto = random.nextObject(ProductDto)

        when:
        productService.updateProduct(productId, productDto)

        then:
        1 * productRepository.findById(productId) >> Optional.of(existingProduct)
        1 * productMapper.mapToEntity(productDto) >> updateProduct
        existingProduct.setPrice(updateProduct.getPrice())
        existingProduct.setQuantity(updateProduct.getQuantity())
        1 * productRepository.save(existingProduct)
    }

    def "DeleteProduct"() {
        given:
        def productId = 1L
        def productEntity = random.nextObject(ProductEntity)

        when:
        productService.deleteProduct(productId)

        then:
        1 * productRepository.findById(productId) >> Optional.of(productEntity)
        1 * productRepository.delete(productEntity)
    }

}
