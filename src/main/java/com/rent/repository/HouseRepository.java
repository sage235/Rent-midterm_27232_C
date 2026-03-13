package com.rent.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rent.modal.House;

public interface HouseRepository extends JpaRepository<House, Long> {

    List<House> findByOwnerId(UUID ownerId);

    List<House> findByLocationCode(String code);

    Page<House> findAll(Pageable pageable);

    Optional<House> findById(Long id);
}