package com.example.marketmanagement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: nijataghayev
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    @Schema(description = "Name of the category", example = "Electronics")
    @NotNull
    private String name;

    @Schema(description = "Description of the category", example = "All kinds of electronic devices and accessories")
    @NotNull
    private String description;
}
