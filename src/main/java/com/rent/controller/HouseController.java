package com.rent.controller;

import com.rent.modal.House;
import com.rent.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/houses")
public class HouseController {

    @Autowired
    private HouseService houseService;

    // ADD HOUSE 
    @PostMapping("/post")
    public ResponseEntity<?> postHouse(@RequestBody House house, @RequestParam(required = false) Set<Long> amenityIds) {
        String response = houseService.postHouse(house, amenityIds);
        if (response.contains("successfully")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // GET ALL HOUSES WITH PAGINATION AND SORTING
    @GetMapping("/all")
    public ResponseEntity<?> getAllHouses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "price,asc") String[] sort) {

        Sort sortOrder = Sort.by(sort[0]).ascending();
        if (sort.length > 1 && "desc".equalsIgnoreCase(sort[1])) {
            sortOrder = Sort.by(sort[0]).descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<House> houses = houseService.getAllHouses(pageable);
        if (houses.hasContent()) {
            return new ResponseEntity<>(houses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No houses found", HttpStatus.NOT_FOUND);
        }
    }

    // GET ALL HOUSES (SIMPLE LIST)
    @GetMapping("/list")
    public ResponseEntity<?> getAllHousesList() {
        List<House> houses = houseService.getAll();
        if (houses != null && !houses.isEmpty()) {
            return new ResponseEntity<>(houses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No houses found", HttpStatus.NOT_FOUND);
        }
    }

    // GET HOUSE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getHouseById(@PathVariable Long id) {
        Optional<House> house = houseService.getHouseById(id);
        if (house.isPresent()) {
            return new ResponseEntity<>(house.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("House not found", HttpStatus.NOT_FOUND);
        }
    }

    // DELETE HOUSE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHouse(@PathVariable Long id) {
        boolean deleted = houseService.deleteHouse(id);
        if (deleted) {
            return new ResponseEntity<>("House deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("House not found", HttpStatus.NOT_FOUND);
        }
    }

    // SEARCH HOUSES BY LOCATION CODE
    @GetMapping("/by-location")
    public ResponseEntity<?> searchByLocationCode(@RequestParam String code) {
        List<House> houses = houseService.searchByLocationCode(code);
        if (houses != null) {
            return new ResponseEntity<>(houses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No houses found for location code: " + code, HttpStatus.NOT_FOUND);
        }
    }

    // SEARCH HOUSES BY OWNER ID (UUID)
    @GetMapping("/by-owner")
    public ResponseEntity<?> searchByOwner(@RequestParam UUID ownerId) {
        List<House> houses = houseService.searchByOwner(ownerId);
        if (houses != null) {
            return new ResponseEntity<>(houses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No houses found for owner ID: " + ownerId, HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE HOUSE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateHouse(@PathVariable Long id, @RequestBody House house, @RequestParam(required = false) Set<Long> amenityIds) {
        House updatedHouse = houseService.updateHouse(id, house, amenityIds);
        if (updatedHouse != null) {
            return new ResponseEntity<>(updatedHouse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("House not found", HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE HOUSE LOCATION (by location code)
    @PatchMapping("/{id}/location")
    public ResponseEntity<?> updateHouseLocation(@PathVariable Long id, @RequestParam String code) {
        House updatedHouse = houseService.updateHouseLocation(id, code);
        if (updatedHouse != null) {
            return new ResponseEntity<>(updatedHouse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("House not found or location code invalid", HttpStatus.NOT_FOUND);
        }
    }
}