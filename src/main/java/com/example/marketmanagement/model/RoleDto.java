package com.example.marketmanagement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: nijataghayev
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    @Schema(description = "Name of the role", example = "ADMIN")
    private String name;

    @Schema(description = "User associated with the role")
    private User user;

    @Data
    public static class User {
        @Schema(description = "Username of the user", example = "nijat_aghayev")
        private String username;

        @Schema(description = "Email address of the user", example = "nijat.aghayev@gmail.com")
        private String email;
    }}
