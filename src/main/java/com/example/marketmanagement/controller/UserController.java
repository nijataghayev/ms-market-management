package com.example.marketmanagement.controller;

import com.example.marketmanagement.model.UserDto;
import com.example.marketmanagement.model.UserProfileReqDto;
import com.example.marketmanagement.model.UserProfileResDto;
import com.example.marketmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: nijataghayev
 */

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Register a new user", description = "Registers a new user with the provided user details.")
    public void register(@RequestBody @Valid UserDto userDto) {
        userService.register(userDto);
    }

    @PostMapping("/verifyOtp")
    @Operation(
            summary = "Verify OTP code",
            description = "Verifies the OTP code sent to the user's email. Returns success if the OTP is valid and has not expired.",
            parameters = {
                    @Parameter(name = "email", description = "The email address of the user", required = true),
                    @Parameter(name = "otp", description = "The OTP code sent to the user's email", required = true)
            }
    )
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean isVerified = userService.verifyOtp(email, otp);
        if (isVerified) {
            return ResponseEntity.ok("OTP verification successful.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired OTP code.");
        }
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a list of all registered users.")
    public List<UserProfileResDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", description = "Returns the user profile for the specified user ID.")
    public UserProfileResDto getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user profile", description = "Updates the profile of the specified user ID with new details.")
    public void updateUser(@PathVariable Long userId, @RequestBody @Valid UserProfileReqDto profileDto) {
        userService.updateUser(userId, profileDto);
    }
}
