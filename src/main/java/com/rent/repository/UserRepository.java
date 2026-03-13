package com.rent.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rent.modal.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    List<User> findByVillageCode(String code);

    List<User> findByVillageName(String name);

    List<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);

    @Query("SELECT u FROM User u " +
           "JOIN u.village v " +
           "JOIN v.parent cell " +
           "JOIN cell.parent sector " +
           "JOIN sector.parent district " +
           "JOIN district.parent province " +
           "WHERE province.code = :identifier OR province.name = :identifier")
    List<User> findByProvinceCodeOrName(@Param("identifier") String identifier);
}