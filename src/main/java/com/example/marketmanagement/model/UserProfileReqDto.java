package com.example.marketmanagement.model;

import com.example.marketmanagement.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
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
public class UserProfileReqDto {
    private Long id;
    @Schema(description = "Name of the user", example = "Nijat")
    @NotNull
    private String name;

    @Schema(description = "Surname of the user", example = "Aghayev")
    private String surname;

    @Schema(description = "Father's name of the user", example = "Ilgar")
    private String fatherName;

    @Schema(description = "Gender of the user", example = "MALE")
    @NotNull
    private Gender gender;

    @Schema(description = "Birth date of the user", example = "1990-01-01")
    @Past
    private LocalDate birthDate;

    @Schema(description = "Address of the user", example = "Azerbaijan, Baku")
    @NotEmpty
    private String address;

    @Schema(description = "Phone number of the user", example = "+994555555555")
    @Pattern(regexp = "\\+994\\d{9}", message = "Phone number must match the format +994XXXXXXXXX")
    private String phone;

    private Card card;

    @Data
    public static class Card{
        private Long id;
    }
}
