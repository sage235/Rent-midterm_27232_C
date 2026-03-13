package com.rent.controller;

import com.rent.modal.Profile;
import com.rent.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    // ADD PROFILE FOR A USER
    @PostMapping("/user/{userId}")
    public ResponseEntity<?> addProfile(@PathVariable Long userId, @RequestBody Profile profile) {
        Profile savedProfile = profileService.addProfile(userId, profile);
        if (savedProfile != null) {
            return new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User not found or profile already exists", HttpStatus.BAD_REQUEST);
        }
    }

    // UPDATE PROFILE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody Profile profile) {
        Profile updatedProfile = profileService.updateProfile(id, profile);
        if (updatedProfile != null) {
            return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Profile not found", HttpStatus.NOT_FOUND);
        }
    }

    // DELETE PROFILE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long id) {
        boolean deleted = profileService.deleteProfile(id);
        if (deleted) {
            return new ResponseEntity<>("Profile deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Profile not found", HttpStatus.NOT_FOUND);
        }
    }

    // GET ALL PROFILES
    @GetMapping("/all")
    public ResponseEntity<?> getAllProfiles() {
        List<Profile> profiles = profileService.getAll();
        if (profiles != null && !profiles.isEmpty()) {
            return new ResponseEntity<>(profiles, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No profiles found", HttpStatus.NOT_FOUND);
        }
    }

    // GET PROFILE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable Long id) {
        Optional<Profile> profile = profileService.getById(id);
        if (profile.isPresent()) {
            return new ResponseEntity<>(profile.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Profile not found", HttpStatus.NOT_FOUND);
        }
    }

    // SEARCH PROFILE BY ID (alternative endpoint)
    @GetMapping("/search/{id}")
    public ResponseEntity<?> searchProfileById(@PathVariable Long id) {
        Optional<Profile> profile = profileService.searchById(id);
        if (profile != null && profile.isPresent()) {
            return new ResponseEntity<>(profile.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Profile not found", HttpStatus.NOT_FOUND);
        }
    }
}