package com.example.marketmanagement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author: nijataghayev
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
    private Long id;
    @Schema(description = "Card number", example = "1234-5678-9012-3456")
    private String cardNumber;

    @Schema(description = "Amount available on the card", example = "500.00")
    private Double amount;

    @Schema(description = "Expiry date of the card", example = "2039-08-31")
    private LocalDate expireDate;

    @Schema(description = "Creation date of the card", example = "2024-08-31")
    private LocalDate creationDate;

    @Schema(description = "User associated with the card")
    private User user;

    @Data
    @Schema(description = "User Data Transfer Object")
    public static class User {
        private Long id;

        @Schema(description = "Name of the user", example = "Nijat")
        private String name;

        @Schema(description = "Surname of the user", example = "Aghayev")
        private String surname;

        @Schema(description = "Father's name of the user", example = "Ilgar")
        private String fatherName;
    }
}
