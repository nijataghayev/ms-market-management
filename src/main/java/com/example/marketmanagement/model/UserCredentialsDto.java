package com.example.marketmanagement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author: nijataghayev
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialsDto {
    @Schema(description = "Email address of the user", example = "nijat.aghayev@gmail.com")
    @NotNull
    @Email
    private String email;

    @Schema(description = "Username of the user", example = "nijat_aghayev")
    @NotNull
    private String username;

    @Schema(description = "Password of the user")
    @NotNull
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$")
    private String password;
}
