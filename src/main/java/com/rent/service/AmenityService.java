package com.rent.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rent.modal.Amenity;
import com.rent.repository.AmenityRepository;

@Service
public class AmenityService {

@Autowired
    private AmenityRepository amenityRepository;

    // CREATE AMENITY
    public String saveAmenity(Amenity amenity) {
        Optional<Amenity> existing = amenityRepository.findByName(amenity.getName());
        if (existing.isPresent()) {
            return "Amenity with name " + amenity.getName() + " already exists";
        }
        amenityRepository.save(amenity);
        return "Amenity saved successfully";
    }

    // GET ALL AMENITIES
    public List<Amenity> getAllAmenities() {
        return amenityRepository.findAll();
    }

    // GET AMENITY BY ID
    public Amenity getAmenityById(Long id) {
        Optional<Amenity> amenity = amenityRepository.findById(id);
        return amenity.orElse(null);
    }

    // GET AMENITY BY NAME
    public Amenity getAmenityByName(String name) {
        Optional<Amenity> amenity = amenityRepository.findByName(name);
        return amenity.orElse(null);
    }

    // UPDATE AMENITY
    public String updateAmenity(Long id, Amenity updatedAmenity) {
        Optional<Amenity> existingOpt = amenityRepository.findById(id);
        if (!existingOpt.isPresent()) {
            return "Amenity not found";
        }
        Amenity existing = existingOpt.get();
        existing.setName(updatedAmenity.getName());
        amenityRepository.save(existing);
        return "Amenity updated successfully";
    }

    // DELETE AMENITY
    public boolean deleteAmenity(Long id) {
        if (amenityRepository.existsById(id)) {
            amenityRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
    
