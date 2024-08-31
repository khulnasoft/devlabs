package com.khulnasoft.refactor.repository;

import com.khulnasoft.refactor.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepo extends JpaRepository<UserProfile, Long> {
    UserProfile findUserProfileById(Long id);
}