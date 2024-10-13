package com.example.marketmanagement.controller;

import com.example.marketmanagement.model.RoleDto;
import com.example.marketmanagement.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: nijataghayev
 */

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    @Operation(summary = "Get all roles", description = "Returns a list of all roles.")
    public List<RoleDto> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get role by user ID", description = "Returns the role details for the given user ID.")
    public RoleDto getRoleByUserId(@PathVariable Long userId) {
        return roleService.getRoleByUserId(userId);
    }

    @PutMapping("/changeRole/{userId}")
    @Operation(summary = "Change user role to Admin", description = "Changes the role of the specified user to Admin.")
    public void changeUserRoleToAdmin(@PathVariable Long userId) {
        roleService.changeUserRoleToAdmin(userId);
    }
}
