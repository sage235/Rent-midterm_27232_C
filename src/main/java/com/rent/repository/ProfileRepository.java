package com.rent.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rent.modal.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    List<Profile> findByPhone(String phone);
    Optional<Profile> findByUserId(Long userId);
    Optional<Profile> findById(Long id);
}