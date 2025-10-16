package com.khulnasoft.refactor.controller;

import com.khulnasoft.commons.dto.ApiResponse;
import com.khulnasoft.commons.dto.UserDto;
import com.khulnasoft.commons.rest.BaseRestController;
import com.khulnasoft.commons.rest.ResourceNotFoundException;
import com.khulnasoft.commons.rest.RestUtils;
import com.khulnasoft.commons.rest.ValidationException;
import com.khulnasoft.refactor.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * REST controller for user management operations.
 * Follows the controller layer pattern in layered architecture.
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User management API")
public class UserController extends BaseRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a paginated list of users with optional filtering and sorting")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<Object>> getUsers(
            @Parameter(description = "Page number (1-based)") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") Integer size,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String sortDir,
            @Parameter(description = "Filter by username") @RequestParam(required = false) String username,
            @Parameter(description = "Filter by email") @RequestParam(required = false) String email,
            @Parameter(description = "Filter by active status") @RequestParam(required = false) Boolean active) {

        try {
            // Create pagination and sorting
            Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
            Pageable pageable = PageRequest.of(page - 1, size, sort);

            Page<UserDto> users;

            // Apply filters
            if (username != null && !username.trim().isEmpty()) {
                List<UserDto> filteredUsers = userService.searchUsersByUsername(username);
                // For demo purposes, return first page of filtered results
                users = new org.springframework.data.domain.PageImpl<>(
                    filteredUsers.subList(0, Math.min(filteredUsers.size(), size)),
                    pageable,
                    filteredUsers.size()
                );
            } else if (email != null && !email.trim().isEmpty()) {
                List<UserDto> filteredUsers = userService.searchUsersByEmail(email);
                users = new org.springframework.data.domain.PageImpl<>(
                    filteredUsers.subList(0, Math.min(filteredUsers.size(), size)),
                    pageable,
                    filteredUsers.size()
                );
            } else if (active != null) {
                users = active ? userService.getActiveUsers(pageable) : userService.getAllUsers(pageable);
            } else {
                users = userService.getAllUsers(pageable);
            }

            // Create response with pagination metadata
            Map<String, Object> responseData = Map.of(
                "users", users.getContent(),
                "pagination", RestUtils.createPaginationMetadata(users.getTotalElements(), page, size)
            );

            return success("Users retrieved successfully", responseData);

        } catch (Exception e) {
            LOGGER.error("Error retrieving users", e);
            throw new RuntimeException("Failed to retrieve users", e);
        }
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", description = "Retrieve a specific user by their ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<UserDto>> getUserById(
            @Parameter(description = "User ID", required = true) @PathVariable Long userId) {

        try {
            UserDto user = userService.getUserById(userId);
            return success("User retrieved successfully", user);

        } catch (ResourceNotFoundException e) {
            LOGGER.warn("User not found: {}", userId);
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            LOGGER.error("Error retrieving user: {}", userId, e);
            throw new RuntimeException("Failed to retrieve user", e);
        }
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user with the provided information")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User created successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "User already exists"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<UserDto>> createUser(
            @Valid @RequestBody UserDto userDto) {

        try {
            UserDto createdUser = userService.createUser(userDto);
            LOGGER.info("User created successfully: {}", createdUser.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User created successfully", createdUser));

        } catch (ValidationException e) {
            LOGGER.warn("Validation error creating user: {}", e.getMessage());
            return ResponseEntity.badRequest()
                .body(ApiResponse.error(e.getMessage()));

        } catch (Exception e) {
            LOGGER.error("Error creating user", e);
            throw new RuntimeException("Failed to create user", e);
        }
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user", description = "Update an existing user's information")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User updated successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<UserDto>> updateUser(
            @Parameter(description = "User ID", required = true) @PathVariable Long userId,
            @Valid @RequestBody UserDto userDto) {

        try {
            UserDto updatedUser = userService.updateUser(userId, userDto);
            LOGGER.info("User updated successfully: {}", updatedUser.getUsername());
            return success("User updated successfully", updatedUser);

        } catch (ValidationException e) {
            LOGGER.warn("Validation error updating user {}: {}", userId, e.getMessage());
            return ResponseEntity.badRequest()
                .body(ApiResponse.error(e.getMessage()));

        } catch (ResourceNotFoundException e) {
            LOGGER.warn("User not found for update: {}", userId);
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            LOGGER.error("Error updating user: {}", userId, e);
            throw new RuntimeException("Failed to update user", e);
        }
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user", description = "Delete a user by their ID (soft delete)")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @Parameter(description = "User ID", required = true) @PathVariable Long userId) {

        try {
            userService.deleteUser(userId);
            LOGGER.info("User deleted successfully: {}", userId);
            return ResponseEntity.noContent().build();

        } catch (ResourceNotFoundException e) {
            LOGGER.warn("User not found for deletion: {}", userId);
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            LOGGER.error("Error deleting user: {}", userId, e);
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    @PostMapping("/{userId}/activate")
    @Operation(summary = "Activate user", description = "Activate a user account")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User activated successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<UserDto>> activateUser(
            @Parameter(description = "User ID", required = true) @PathVariable Long userId) {

        try {
            UserDto activatedUser = userService.activateUser(userId);
            LOGGER.info("User activated successfully: {}", userId);
            return success("User activated successfully", activatedUser);

        } catch (ResourceNotFoundException e) {
            LOGGER.warn("User not found for activation: {}", userId);
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            LOGGER.error("Error activating user: {}", userId, e);
            throw new RuntimeException("Failed to activate user", e);
        }
    }

    @PostMapping("/{userId}/deactivate")
    @Operation(summary = "Deactivate user", description = "Deactivate a user account")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User deactivated successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<UserDto>> deactivateUser(
            @Parameter(description = "User ID", required = true) @PathVariable Long userId) {

        try {
            UserDto deactivatedUser = userService.deactivateUser(userId);
            LOGGER.info("User deactivated successfully: {}", userId);
            return success("User deactivated successfully", deactivatedUser);

        } catch (ResourceNotFoundException e) {
            LOGGER.warn("User not found for deactivation: {}", userId);
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            LOGGER.error("Error deactivating user: {}", userId, e);
            throw new RuntimeException("Failed to deactivate user", e);
        }
    }

    @GetMapping("/statistics")
    @Operation(summary = "Get user statistics", description = "Get user statistics and metrics")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Statistics retrieved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<Map<String, Long>>> getUserStatistics() {

        try {
            Map<String, Long> statistics = userService.getUserStatistics();
            return success("User statistics retrieved successfully", statistics);

        } catch (Exception e) {
            LOGGER.error("Error retrieving user statistics", e);
            throw new RuntimeException("Failed to retrieve user statistics", e);
        }
    }

    @GetMapping("/recent")
    @Operation(summary = "Get recently created users", description = "Get users created in the last N days")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Recent users retrieved successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad request"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ApiResponse<List<UserDto>>> getRecentUsers(
            @Parameter(description = "Number of days", example = "7") @RequestParam(defaultValue = "7") int days) {

        if (days < 1 || days > 365) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error("Days must be between 1 and 365"));
        }

        try {
            List<UserDto> recentUsers = userService.getRecentlyCreatedUsers(days);
            return success("Recent users retrieved successfully", recentUsers);

        } catch (Exception e) {
            LOGGER.error("Error retrieving recent users", e);
            throw new RuntimeException("Failed to retrieve recent users", e);
        }
    }
}
