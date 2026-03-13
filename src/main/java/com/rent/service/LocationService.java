package com.rent.service;

import com.rent.modal.ELocationType;
import com.rent.modal.Location;
import com.rent.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    // Add Location  
    public String saveLocation(Location location) {
        Optional<Location> existing = locationRepository.findByCode(location.getCode());
        if (existing.isPresent()) {
            return "Location with code " + location.getCode() + " already exists";
        }

        if (location.getParent() != null && location.getParent().getId() != null) {
            Optional<Location> parentOpt = locationRepository.findById(location.getParent().getId());
            if (!parentOpt.isPresent()) {
                return "Parent location not found";
            }
            Location parent = parentOpt.get();

            if (location.getLevel() == ELocationType.DISTRICT && parent.getLevel() != ELocationType.PROVINCE) {
                return "Parent of a District must be a Province";
            }
            if (location.getLevel() == ELocationType.SECTOR && parent.getLevel() != ELocationType.DISTRICT) {
                return "Parent of a Sector must be a District";
            }
            if (location.getLevel() == ELocationType.CELL && parent.getLevel() != ELocationType.SECTOR) {
                return "Parent of a Cell must be a Sector";
            }
            if (location.getLevel() == ELocationType.VILLAGE && parent.getLevel() != ELocationType.CELL) {
                return "Parent of a Village must be a Cell";
            }
            location.setParent(parent);
        } else {
            if (location.getLevel() != ELocationType.PROVINCE) {
                return "Only Province can have no parent";
            }
        }

        locationRepository.save(location);
        return "Location saved successfully";
    }

    // Get all locations with pagination and sorting
    public Page<Location> getAllLocationsPaginated(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    // Get location by ID
    public Location getLocationById(Long id) {
        Optional<Location> location = locationRepository.findById(id);
        return location.orElse(null);
    }

    // Update location
    public String updateLocation(Long id, Location updatedLocation) {
        Optional<Location> existingOpt = locationRepository.findById(id);
        if (!existingOpt.isPresent()) {
            return "Location not found";
        }
        Location existing = existingOpt.get();
        existing.setName(updatedLocation.getName());
        existing.setCode(updatedLocation.getCode());
        existing.setLevel(updatedLocation.getLevel());
        locationRepository.save(existing);
        return "Location updated successfully";
    }

    // Delete location
    public boolean deleteLocation(String code) {
        if (locationRepository.existsByCode(code)) {
            locationRepository.deleteByCode(code);
            return true;
        }
        return false;
    }
}