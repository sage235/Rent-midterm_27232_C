package com.rent.controller;

import com.rent.modal.ELocationType;
import com.rent.modal.Location;
import com.rent.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    // Add location
    @PostMapping("/add")
    public ResponseEntity<?> addLocation(@RequestBody Location location) {
        String response = locationService.saveLocation(location);
        if (response.contains("successfully")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    // Get all locations with pagination and sorting
    @GetMapping("/all-paginated")
    public ResponseEntity<?> getAllLocationsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort) {

        Sort sortOrder = Sort.by(sort[0]).ascending();
        if (sort.length > 1 && "desc".equalsIgnoreCase(sort[1])) {
            sortOrder = Sort.by(sort[0]).descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Location> locations = locationService.getAllLocationsPaginated(pageable);
        if (locations.hasContent()) {
            return new ResponseEntity<>(locations, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No locations found", HttpStatus.NOT_FOUND);
        }
    }

    // Get location by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getLocationById(@PathVariable Long id) {
        Location location = locationService.getLocationById(id);
        if (location != null) {
            return new ResponseEntity<>(location, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Location not found", HttpStatus.NOT_FOUND);
        }
    }

    // Update location
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLocation(@PathVariable Long id, @RequestBody Location location) {
        String response = locationService.updateLocation(id, location);
        if (response.contains("successfully")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Delete location
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable String code) {
        boolean deleted = locationService.deleteLocation(code);
        if (deleted) {
            return new ResponseEntity<>("Location deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Location not found", HttpStatus.NOT_FOUND);
        }
    }
}