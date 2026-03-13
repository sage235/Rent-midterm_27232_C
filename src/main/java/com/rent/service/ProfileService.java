package com.rent.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rent.modal.Profile;
import com.rent.modal.User;
import com.rent.repository.ProfileRepository;
import com.rent.repository.UserRepository;

@Service
public class ProfileService {

@Autowired
private ProfileRepository profileRepository;

@Autowired
private UserRepository userRepository;

// Add Profile
public Profile addProfile(Long userId, Profile profile) {

    Optional<User> userOpt = userRepository.findById(userId);

    if (userOpt.isEmpty()) {
        return null;
    }

    User user = userOpt.get();

    if (user.getProfile() != null) {
        return null;
    }

    profile.setUser(user);

    Profile savedProfile = profileRepository.save(profile);

    user.setProfile(savedProfile);

    return savedProfile;
}

// Update Profile
public Profile updateProfile(Long id, Profile updatedProfile) {

    Optional<Profile> existingProfile = profileRepository.findById(id);

    if (existingProfile.isPresent()) {

        Profile profile = existingProfile.get();

        profile.setPhone(updatedProfile.getPhone());
        profile.setProfilePictureUrl(updatedProfile.getProfilePictureUrl());

        return profileRepository.save(profile);
    }

    return null;
}

// Delete Profile
public boolean deleteProfile(Long id) {

    Optional<Profile> existingProfile = profileRepository.findById(id);

    if (existingProfile.isPresent()) {
        profileRepository.delete(existingProfile.get());
        return true;
    }
    return false;
}

    // GET ALL PROFILES
    public List<Profile> getAll(){
        return profileRepository.findAll();
    }

      // GET PROFILE BY ID
    public Optional<Profile> getById(Long id){
        return profileRepository.findById(id);
    }


    // SEARCH PROFILE BY ID
    public Optional<Profile> searchById(Long id){

        Optional<Profile> profiles = profileRepository.findById(id);

        if(profiles != null && !profiles.isEmpty()){
            return profiles;
        }else{
            return null;
        }
    }
}
