package com.khulnasoft.refactor.service;

import com.khulnasoft.refactor.entity.UserProfile;
import com.khulnasoft.refactor.repository.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final ProfileRepo profileRepo;

    @Autowired
    public ProfileService(ProfileRepo profileRepo) {
        this.profileRepo = profileRepo;
    }

    public UserProfile findUserProfileById(Long id) {
        return profileRepo.findUserProfileById(id);
    }

    public UserProfile save(UserProfile userProfile) {
        return profileRepo.save(userProfile);
    }
}