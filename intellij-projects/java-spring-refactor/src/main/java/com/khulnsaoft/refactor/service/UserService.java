package com.khulnasoft.refactor.service;

import com.khulnasoft.commons.dto.UserDto;
import com.khulnasoft.commons.rest.ResourceNotFoundException;
import com.khulnasoft.commons.validation.ValidationException;
import com.khulnasoft.commons.validation.ValidatorFactory;
import com.khulnasoft.refactor.entity.User;
import com.khulnasoft.refactor.entity.UserRole;
import com.khulnasoft.refactor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * User service implementing business logic for user management.
 * Follows the service layer pattern in layered architecture.
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Create a new user.
     */
    public UserDto createUser(UserDto userDto) {
        validateUserDto(userDto);

        // Check for existing username or email
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new ValidationException("Username already exists: " + userDto.getUsername());
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ValidationException("Email already exists: " + userDto.getEmail());
        }

        // Create entity from DTO
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        user.setActive(userDto.getActive() != null ? userDto.getActive() : true);
        user.setRole(userDto.getRole() != null ? UserRole.valueOf(userDto.getRole()) : UserRole.USER);

        // Save user
        User savedUser = userRepository.save(user);

        // Convert back to DTO
        return convertToDto(savedUser);
    }

    /**
     * Get user by ID.
     */
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return convertToDto(user);
    }

    /**
     * Get user by username.
     */
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .map(this::convertToDto);
    }

    /**
     * Get user by email.
     */
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .map(this::convertToDto);
    }

    /**
     * Update user.
     */
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        validateUserDtoForUpdate(userDto);

        // Check for conflicts with existing users (excluding current user)
        if (userDto.getUsername() != null &&
            userRepository.existsByUsernameAndIdNot(userDto.getUsername(), id)) {
            throw new ValidationException("Username already exists: " + userDto.getUsername());
        }

        if (userDto.getEmail() != null &&
            userRepository.existsByEmailAndIdNot(userDto.getEmail(), id)) {
            throw new ValidationException("Email already exists: " + userDto.getEmail());
        }

        // Update fields
        if (userDto.getUsername() != null) {
            user.setUsername(userDto.getUsername());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }
        if (userDto.getPhone() != null) {
            user.setPhone(userDto.getPhone());
        }
        if (userDto.getActive() != null) {
            user.setActive(userDto.getActive());
        }
        if (userDto.getRole() != null) {
            user.setRole(UserRole.valueOf(userDto.getRole()));
        }

        // Save updated user
        User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }

    /**
     * Delete user (soft delete by deactivating).
     */
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.deactivate();
        userRepository.save(user);
    }

    /**
     * Activate user.
     */
    public UserDto activateUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.activate();
        User activatedUser = userRepository.save(user);
        return convertToDto(activatedUser);
    }

    /**
     * Deactivate user.
     */
    public UserDto deactivateUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.deactivate();
        User deactivatedUser = userRepository.save(user);
        return convertToDto(deactivatedUser);
    }

    /**
     * Get all users with pagination.
     */
    @Transactional(readOnly = true)
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
            .map(this::convertToDto);
    }

    /**
     * Get active users with pagination.
     */
    @Transactional(readOnly = true)
    public Page<UserDto> getActiveUsers(Pageable pageable) {
        return userRepository.findByActiveTrue(pageable)
            .map(this::convertToDto);
    }

    /**
     * Search users by username.
     */
    @Transactional(readOnly = true)
    public List<UserDto> searchUsersByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username)
            .stream()
            .map(this::convertToDto)
            .toList();
    }

    /**
     * Search users by email.
     */
    @Transactional(readOnly = true)
    public List<UserDto> searchUsersByEmail(String email) {
        return userRepository.findByEmailContainingIgnoreCase(email)
            .stream()
            .map(this::convertToDto)
            .toList();
    }

    /**
     * Get user statistics.
     */
    @Transactional(readOnly = true)
    public Map<String, Long> getUserStatistics() {
        Map<String, Long> statistics = new HashMap<>();

        Object[] summary = userRepository.getUserActivitySummary();
        if (summary != null && summary.length >= 3) {
            statistics.put("activeUsers", ((Number) summary[0]).longValue());
            statistics.put("inactiveUsers", ((Number) summary[1]).longValue());
            statistics.put("totalUsers", ((Number) summary[2]).longValue());
        }

        // Statistics by role
        List<Object[]> roleStats = userRepository.getUserStatisticsByRole();
        for (Object[] roleStat : roleStats) {
            if (roleStat.length >= 2) {
                UserRole role = (UserRole) roleStat[0];
                Long count = ((Number) roleStat[1]).longValue();
                statistics.put(role.name().toLowerCase() + "Count", count);
            }
        }

        return statistics;
    }

    /**
     * Get recently created users.
     */
    @Transactional(readOnly = true)
    public List<UserDto> getRecentlyCreatedUsers(int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        return userRepository.findUsersCreatedSince(since)
            .stream()
            .map(this::convertToDto)
            .toList();
    }

    /**
     * Get recently active users.
     */
    @Transactional(readOnly = true)
    public List<UserDto> getRecentlyActiveUsers(int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        return userRepository.findRecentlyActiveUsers(since)
            .stream()
            .map(this::convertToDto)
            .toList();
    }

    // Private helper methods

    private void validateUserDto(UserDto userDto) {
        if (userDto == null) {
            throw new ValidationException("User DTO cannot be null");
        }

        if (userDto.getUsername() == null || userDto.getUsername().trim().isEmpty()) {
            throw new ValidationException("Username is required");
        }

        if (userDto.getEmail() == null || userDto.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email is required");
        }

        // Use shared validators
        ValidatorFactory.stringValidator(3, 50).validate(userDto.getUsername());
        ValidatorFactory.email().validate(userDto.getEmail());

        if (userDto.getFirstName() != null) {
            ValidatorFactory.stringValidator(1, 100).validate(userDto.getFirstName());
        }

        if (userDto.getLastName() != null) {
            ValidatorFactory.stringValidator(1, 100).validate(userDto.getLastName());
        }

        if (userDto.getPhone() != null) {
            ValidatorFactory.stringValidator(10, 20).validate(userDto.getPhone());
        }
    }

    private void validateUserDtoForUpdate(UserDto userDto) {
        if (userDto == null) {
            throw new ValidationException("User DTO cannot be null");
        }

        if (userDto.getUsername() != null) {
            ValidatorFactory.stringValidator(3, 50).validate(userDto.getUsername());
        }

        if (userDto.getEmail() != null) {
            ValidatorFactory.email().validate(userDto.getEmail());
        }

        if (userDto.getFirstName() != null) {
            ValidatorFactory.stringValidator(1, 100).validate(userDto.getFirstName());
        }

        if (userDto.getLastName() != null) {
            ValidatorFactory.stringValidator(1, 100).validate(userDto.getLastName());
        }

        if (userDto.getPhone() != null) {
            ValidatorFactory.stringValidator(10, 20).validate(userDto.getPhone());
        }
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setActive(user.getActive());
        dto.setRole(user.getRole() != null ? user.getRole().name() : null);
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setVersion(user.getVersion() != null ? user.getVersion().toString() : null);
        return dto;
    }
}
