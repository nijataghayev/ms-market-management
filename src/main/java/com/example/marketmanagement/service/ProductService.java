package com.example.marketmanagement.service;

import com.example.marketmanagement.dao.entity.CategoryEntity;
import com.example.marketmanagement.dao.entity.ProductEntity;
import com.example.marketmanagement.dao.repository.CategoryRepository;
import com.example.marketmanagement.dao.repository.ProductRepository;
import com.example.marketmanagement.exception.NotFoundException;
import com.example.marketmanagement.mapper.ProductMapper;
import com.example.marketmanagement.model.ProductDto;
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
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getAllProducts() {
        log.info("ActionLog.getAllProducts.start");
        List<ProductEntity> productEntityList = productRepository.findAll();
        List<ProductDto> productDtoList = productEntityList.stream()
                .map(productMapper::mapToDto)
                .toList();
        log.info("ActionLog.getAllProducts.end");

        return productDtoList;
    }

    public ProductDto getProduct(Long productId) {
        log.info("ActionLog.getProduct.start productId {}", productId);
        ProductEntity productEntity = productRepository
                .findById(productId)
                .orElseThrow(() -> new NotFoundException(
                        "PRODUCT_NOT_FOUND",
                        String.format("ActionLog.getProduct.id %s not found", productId)
                ));
        ProductDto productDto = productMapper.mapToDto(productEntity);
        log.info("ActionLog.getProduct.end productId {}", productId);

        return productDto;
    }

    public void createProduct(ProductDto productDto) {
        log.debug("ActionLog.createProduct.start product {}", productDto);
        CategoryEntity categoryEntity = categoryRepository.findByName(productDto.getCategory().getName())
                .orElseGet(() -> {
                    CategoryEntity newCategory = new CategoryEntity();
                    newCategory.setName(productDto.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });

        ProductEntity productEntity = productMapper.mapToEntity(productDto);
        productEntity.setCategory(categoryEntity);
        productRepository.save(productEntity);

        log.debug("ActionLog.createProduct.end product {}", productDto);
    }

    public void updateProduct(Long productId, ProductDto productDto) {
        ProductEntity existingProduct = productRepository
                .findById(productId)
                .orElseThrow(() -> new NotFoundException(
                        "PRODUCT_NOT_FOUND",
                        String.format("ActionLog.updateProduct.id %s not found", productId)
                ));
        ProductEntity updateProduct = productMapper.mapToEntity(productDto);

        existingProduct.setPrice(updateProduct.getPrice());
        existingProduct.setQuantity(updateProduct.getQuantity());

        productRepository.save(existingProduct);
    }

    public void deleteProduct(Long productId) {
        log.info("ActionLog.deleteProduct.start productId {}", productId);
        ProductEntity productEntity = productRepository
                .findById(productId)
                .orElseThrow(() -> new NotFoundException(
                        "PRODUCT_NOT_FOUND",
                        String.format("ActionLog.deleteProduct.id %s not found", productId)
                ));
        productRepository.delete(productEntity);
        log.info("ActionLog.deleteProduct.end productId {}", productId);
    }
}
