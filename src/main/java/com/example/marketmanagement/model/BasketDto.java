package com.example.marketmanagement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author: nijataghayev
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketDto {
    private Long id;

    @Schema(description = "User who owns the basket")
    private User user;

    @Schema(description = "List of products in the basket")
    private List<Product> products;

    @Schema(description = "Map of product IDs to quantities in the basket")
    private Map<Long, Integer> productQuantities;

    @Data
    @Schema(description = "User Data Transfer Object")
    public static class User {
        private Long id;

        @Schema(description = "Name of the user", example = "Nijat")
        private String name;

        @Schema(description = "Surname of the user", example = "Aghayev")
        private String surname;
    }

    @Data
    @Schema(description = "Product Data Transfer Object")
    public static class Product {
        private Long id;

        @Schema(description = "Name of the product", example = "Laptop")
        private String name;

        @Schema(description = "Brand of the product", example = "Dell")
        private String brand;

        @Schema(description = "Description of the product", example = "High-performance laptop with 16GB RAM")
        private String description;

        @Schema(description = "Price of the product", example = "999.99")
        private Double price;
    }
}
