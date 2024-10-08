package com.khulnasoft.refactor.listener;

import com.khulnasoft.refactor.entity.UserProfile;
import com.khulnasoft.refactor.repository.ProfileRepo;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ProfileInsertListener implements ApplicationListener<ApplicationReadyEvent> {

    private final ProfileRepo profileRepo;

    public ProfileInsertListener(ProfileRepo profileRepo) {
        this.profileRepo = profileRepo;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        UserProfile profile = new UserProfile();
        profile.setId(1234L);
        profile.setName("John Smith");
        System.out.println("Inserting a user " + profile);
        profileRepo.save(profile);
    }
}