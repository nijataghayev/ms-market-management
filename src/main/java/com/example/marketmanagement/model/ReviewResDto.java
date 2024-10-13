package com.example.marketmanagement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: nijataghayev
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResDto {
    private Long id;
    @Schema(description = "Content of the review", example = "This product is excellent and worth the price!")
    @NotNull
    private String content;

    @Schema(description = "User who wrote the review")
    private User user;

    @Schema(description = "Product that the review is for")
    private Product product;

    @Data
    public static class User {
        private Long id;

        @Schema(description = "Name of the user", example = "Nijat")
        private String name;

        @Schema(description = "Surname of the user", example = "Aghayev")
        private String surname;
    }

    @Data
    public static class Product {
        private Long id;

        @Schema(description = "Name of the product", example = "Smartphone")
        private String name;

        @Schema(description = "Brand of the product", example = "Apple")
        private String brand;
    }
}
