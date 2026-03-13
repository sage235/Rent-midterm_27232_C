package com.rent.controller;

import com.rent.modal.Amenity;
import com.rent.service.AmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amenities")
public class AmenityController {

    @Autowired
    private AmenityService amenityService;

    // ADD AMENITY
    @PostMapping("/add")
    public ResponseEntity<?> addAmenity(@RequestBody Amenity amenity) {
        String response = amenityService.saveAmenity(amenity);
        if (response.contains("successfully")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // GET ALL AMENITIES
    @GetMapping("/all")
    public ResponseEntity<?> getAllAmenities() {
        List<Amenity> amenities = amenityService.getAllAmenities();
        if (amenities != null && !amenities.isEmpty()) {
            return new ResponseEntity<>(amenities, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No amenities found", HttpStatus.NOT_FOUND);
        }
    }

    // GET AMENITY BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAmenityById(@PathVariable Long id) {
        Amenity amenity = amenityService.getAmenityById(id);
        if (amenity != null) {
            return new ResponseEntity<>(amenity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Amenity not found", HttpStatus.NOT_FOUND);
        }
    }

    // GET AMENITY BY NAME
    @GetMapping("/by-name")
    public ResponseEntity<?> getAmenityByName(@RequestParam String name) {
        Amenity amenity = amenityService.getAmenityByName(name);
        if (amenity != null) {
            return new ResponseEntity<>(amenity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Amenity not found", HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE AMENITY
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAmenity(@PathVariable Long id, @RequestBody Amenity amenity) {
        String response = amenityService.updateAmenity(id, amenity);
        if (response.contains("successfully")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE AMENITY
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAmenity(@PathVariable Long id) {
        boolean deleted = amenityService.deleteAmenity(id);
        if (deleted) {
            return new ResponseEntity<>("Amenity deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Amenity not found", HttpStatus.NOT_FOUND);
        }
    }
}