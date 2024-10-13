package com.example.marketmanagement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: nijataghayev
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    @Schema(description = "Name of the product", example = "Smartphone")
    @NotNull
    private String name;

    @Schema(description = "Brand of the product", example = "Apple")
    @NotNull
    private String brand;

    @Schema(description = "Description of the product", example = "Latest model with advanced features")
    private String description;

    @Schema(description = "Price of the product", example = "999.99")
    @NotNull
    @Positive
    private Double price;

    @Schema(description = "Quantity of the product in stock", example = "100")
    @Positive
    private Integer quantity;

    @Schema(description = "Category of the product")
    private Category category;

    @Data
    public static class Category {
        @Schema(description = "Name of the category", example = "Electronics")
        @NotNull
        private String name;
    }
}
