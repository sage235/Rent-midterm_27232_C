package com.rent.service;

import com.rent.modal.*;
import com.rent.repository.AmenityRepository;
import com.rent.repository.HouseRepository;
import com.rent.repository.LocationRepository;
import com.rent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class HouseService {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private AmenityRepository amenityRepository;

    @Transactional
    public String postHouse(House house, Set<Long> amenityIds) {

        if (house.getOwner() == null || house.getOwner().getId() == null) {
            return "Owner information is required";
        }
        Optional<User> ownerOpt = userRepository.findById(house.getOwner().getId());
        if (!ownerOpt.isPresent()) {
            return "Owner not found";
        }
        User owner = ownerOpt.get();
        if (owner.getRole() != Role.OWNER)  {
            return "User is not an owner";
        }
        house.setOwner(owner);

        if (house.getLocation() == null || house.getLocation().getId() == null) {
            return "Location is required";
        }
        Optional<Location> locationOpt = locationRepository.findById(house.getLocation().getId());
        if (!locationOpt.isPresent()) {
            return "Location not found";
        }
        Location location = locationOpt.get();
        if (location.getLevel() != ELocationType.VILLAGE) {
            return "House location must be a Village";
        }
        house.setLocation(location);

        if (amenityIds != null && !amenityIds.isEmpty()) {
            Set<Amenity> amenities = new HashSet<>();
            for (Long id : amenityIds) {
                Optional<Amenity> amenityOpt = amenityRepository.findById(id);
                if (!amenityOpt.isPresent()) {
                    return "Amenity with id " + id + " not found";
                }
                amenities.add(amenityOpt.get());
            }
            house.setAmenities(amenities);
        }

        houseRepository.save(house);
        return "House posted successfully";
    }

    // GET ALL HOUSES WITH PAGINATION
    public Page<House> getAllHouses(Pageable pageable) {
        return houseRepository.findAll(pageable);
    }

    // GET HOUSE BY ID
    public Optional<House> getHouseById(Long id) {
        return houseRepository.findById(id);
    }

    // DELETE HOUSE
    public boolean deleteHouse(Long id) {
        if (houseRepository.existsById(id)) {
            houseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // GET ALL HOUSES 
    public List<House> getAll() {
        return houseRepository.findAll();
    }

    // SEARCH HOUSES BY LOCATION CODE
    public List<House> searchByLocationCode(String code) {
        Optional<Location> locationOpt = locationRepository.findByCode(code);
        if (!locationOpt.isPresent() || locationOpt.get().getLevel() != ELocationType.VILLAGE) {
            return null;
        }
        List<House> houses = houseRepository.findByLocationCode(code);
        return houses.isEmpty() ? null : houses;
    }

    // SEARCH HOUSES BY OWNER ID 
    public List<House> searchByOwner(UUID ownerId) {
        List<House> houses = houseRepository.findByOwnerId(ownerId);
        return houses.isEmpty() ? null : houses;
    }

    // UPDATE HOUSE
    @Transactional
    public House updateHouse(Long id, House updatedHouse, Set<Long> amenityIds) {
        Optional<House> existingOpt = houseRepository.findById(id);
        if (!existingOpt.isPresent()) {
            return null;
        }
        House existing = existingOpt.get();
        existing.setTitle(updatedHouse.getTitle());
        existing.setDescription(updatedHouse.getDescription());
        existing.setPrice(updatedHouse.getPrice());

        if (updatedHouse.getLocation() != null && updatedHouse.getLocation().getId() != null) {
            Optional<Location> locationOpt = locationRepository.findById(updatedHouse.getLocation().getId());
            if (locationOpt.isPresent() && locationOpt.get().getLevel() == ELocationType.VILLAGE) {
                existing.setLocation(locationOpt.get());
            }
        }

        if (amenityIds != null) {
            Set<Amenity> amenities = new HashSet<>();
            for (Long aid : amenityIds) {
                Optional<Amenity> amenityOpt = amenityRepository.findById(aid);
                if (!amenityOpt.isPresent()) {
                    return null; 
                }
                amenities.add(amenityOpt.get());
            }
            existing.setAmenities(amenities);
        }

        return houseRepository.save(existing);
    }

    // UPDATE HOUSE LOCATION 
    public House updateHouseLocation(Long id, String locationCode) {
        Optional<House> existingOpt = houseRepository.findById(id);
        if (!existingOpt.isPresent()) {
            return null;
        }
        House house = existingOpt.get();
        Optional<Location> locationOpt = locationRepository.findByCode(locationCode);
        if (locationOpt.isPresent() && locationOpt.get().getLevel() == ELocationType.VILLAGE) {
            house.setLocation(locationOpt.get());
            return houseRepository.save(house);
        }
        return null;
    }
}