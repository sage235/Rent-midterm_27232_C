package com.rent.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rent.modal.Location;

import com.rent.modal.ELocationType;

public interface LocationRepository extends JpaRepository<Location, UUID> {

    boolean existsByCode(String code);

    Optional<Location> findByCode(String code);

    List<Location> findByLevel(ELocationType level);

    void deleteByCode(String code);

    Optional<Location> findById(Long id);
}