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
public class ReviewReqDto {
    @Schema(description = "Content of the review", example = "This product is excellent and worth the price!")
    @NotNull
    private String content;
}
