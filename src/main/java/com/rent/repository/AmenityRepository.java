package com.rent.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rent.modal.Amenity;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {

    boolean existsByName(String name);

    Optional<Amenity> findByName(String name);
    boolean existsById(Long id);
    Optional<Amenity> findById(Long id);
}