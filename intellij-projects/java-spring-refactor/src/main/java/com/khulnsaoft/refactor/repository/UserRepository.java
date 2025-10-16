package com.khulnasoft.refactor.repository;

import com.khulnasoft.refactor.entity.User;
import com.khulnasoft.refactor.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * User repository interface providing data access operations for User entities.
 * Follows the repository pattern in layered architecture.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by username.
     */
    Optional<User> findByUsername(String username);

    /**
     * Find user by email.
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by username or email.
     */
    Optional<User> findByUsernameOrEmail(String username, String email);

    /**
     * Find users by active status.
     */
    List<User> findByActive(Boolean active);

    /**
     * Find users by role.
     */
    List<User> findByRole(UserRole role);

    /**
     * Find users by active status and role.
     */
    List<User> findByActiveAndRole(Boolean active, UserRole role);

    /**
     * Find users created after a specific date.
     */
    List<User> findByCreatedAtAfter(LocalDateTime date);

    /**
     * Find users updated after a specific date.
     */
    List<User> findByUpdatedAtAfter(LocalDateTime date);

    /**
     * Search users by username containing a keyword.
     */
    List<User> findByUsernameContainingIgnoreCase(String keyword);

    /**
     * Search users by email containing a keyword.
     */
    List<User> findByEmailContainingIgnoreCase(String keyword);

    /**
     * Search users by first name or last name containing a keyword.
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> findByNameContainingIgnoreCase(@Param("keyword") String keyword);

    /**
     * Find users with pagination and sorting.
     */
    Page<User> findAll(Pageable pageable);

    /**
     * Find active users with pagination.
     */
    Page<User> findByActiveTrue(Pageable pageable);

    /**
     * Find users by role with pagination.
     */
    Page<User> findByRole(UserRole role, Pageable pageable);

    /**
     * Check if username exists.
     */
    boolean existsByUsername(String username);

    /**
     * Check if email exists.
     */
    boolean existsByEmail(String email);

    /**
     * Check if username exists excluding a specific user ID.
     */
    boolean existsByUsernameAndIdNot(String username, Long id);

    /**
     * Check if email exists excluding a specific user ID.
     */
    boolean existsByEmailAndIdNot(String email, Long id);

    /**
     * Count users by active status.
     */
    long countByActive(Boolean active);

    /**
     * Count users by role.
     */
    long countByRole(UserRole role);

    /**
     * Soft delete user by setting active to false.
     */
    @Modifying
    @Query("UPDATE User u SET u.active = false, u.updatedAt = :updatedAt WHERE u.id = :id")
    int softDeleteById(@Param("id") Long id, @Param("updatedAt") LocalDateTime updatedAt);

    /**
     * Activate user.
     */
    @Modifying
    @Query("UPDATE User u SET u.active = true, u.updatedAt = :updatedAt WHERE u.id = :id")
    int activateById(@Param("id") Long id, @Param("updatedAt") LocalDateTime updatedAt);

    /**
     * Deactivate user.
     */
    @Modifying
    @Query("UPDATE User u SET u.active = false, u.updatedAt = :updatedAt WHERE u.id = :id")
    int deactivateById(@Param("id") Long id, @Param("updatedAt") LocalDateTime updatedAt);

    /**
     * Update user role.
     */
    @Modifying
    @Query("UPDATE User u SET u.role = :role, u.updatedAt = :updatedAt WHERE u.id = :id")
    int updateRoleById(@Param("id") Long id, @Param("role") UserRole role, @Param("updatedAt") LocalDateTime updatedAt);

    /**
     * Find users created in the last N days.
     */
    @Query("SELECT u FROM User u WHERE u.createdAt >= :since")
    List<User> findUsersCreatedSince(@Param("since") LocalDateTime since);

    /**
     * Find recently active users (updated in the last N days).
     */
    @Query("SELECT u FROM User u WHERE u.updatedAt >= :since ORDER BY u.updatedAt DESC")
    List<User> findRecentlyActiveUsers(@Param("since") LocalDateTime since);

    /**
     * Get user statistics by role.
     */
    @Query("SELECT u.role, COUNT(u) FROM User u GROUP BY u.role")
    List<Object[]> getUserStatisticsByRole();

    /**
     * Get user activity summary.
     */
    @Query("SELECT " +
           "COUNT(CASE WHEN u.active = true THEN 1 END) as activeUsers, " +
           "COUNT(CASE WHEN u.active = false THEN 1 END) as inactiveUsers, " +
           "COUNT(u) as totalUsers " +
           "FROM User u")
    Object[] getUserActivitySummary();
}
