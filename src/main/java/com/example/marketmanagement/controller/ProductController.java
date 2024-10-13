package com.example.marketmanagement.controller;

import com.example.marketmanagement.model.ProductDto;
import com.example.marketmanagement.service.ProductService;
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
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products", description = "Returns a list of all products.")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get product by ID", description = "Returns the product details for the given product ID.")
    public ProductDto getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product with the provided details.")
    public void createProduct(@RequestBody @Valid ProductDto productDto) {
        productService.createProduct(productDto);
    }

    @PutMapping("/{productId}")
    @Operation(summary = "Update product by ID", description = "Updates the details of the product for the given product ID.")
    public void updateProduct(@PathVariable Long productId, @RequestBody @Valid ProductDto productDto) {
        productService.updateProduct(productId, productDto);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete product by ID", description = "Deletes the product for the given product ID.")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}
