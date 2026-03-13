package com.rent.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rent.modal.ELocationType;
import com.rent.modal.Location;
import com.rent.modal.User;
import com.rent.repository.LocationRepository;
import com.rent.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    // Add User
    public String registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            return "Email " + user.getEmail() + " is already registered";
        }

        if (user.getVillage() != null && user.getVillage().getId() != null) {
            Optional<Location> villageOpt = locationRepository.findById(user.getVillage().getId());
            if (!villageOpt.isPresent()) {
                return "Village not found";
            }
            Location village = villageOpt.get();
            if (village.getLevel() != ELocationType.VILLAGE) {
                return "User location must be a Village (lowest level)";
            }
            user.setVillage(village);
        } else {
            return "Village ID is required";
        }

        userRepository.save(user);
        return "User registered successfully";
    }

    // GET ALL USERS
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // SEARCH USERS BY KEYWORD (name or email)
    public List<User> searchByKeyword(String keyword) {
        List<User> users = userRepository
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
        return users.isEmpty() ? null : users;
    }

    // GET USER BY ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // GET USER BY EMAIL
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    // CHECK EMAIL EXISTS
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // GET USERS BY PROVINCE (province code or name)
        public List<User> getUsersByProvince(String identifier) {
        List<User> users = userRepository.findByProvinceCodeOrName(identifier);
        return users.isEmpty() ? null : users;
    }

    // UPDATE USER
    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingOpt = userRepository.findById(id);
        if (!existingOpt.isPresent()) {
            return null;
        }
        User existing = existingOpt.get();
        existing.setName(updatedUser.getName());
        existing.setEmail(updatedUser.getEmail());
        existing.setPassword(updatedUser.getPassword());
        existing.setRole(updatedUser.getRole());

        if (updatedUser.getVillage() != null && updatedUser.getVillage().getId() != null) {
            Optional<Location> villageOpt = locationRepository.findById(updatedUser.getVillage().getId());
            if (villageOpt.isPresent() && villageOpt.get().getLevel() == ELocationType.VILLAGE) {
                existing.setVillage(villageOpt.get());
            }
        }

        return userRepository.save(existing);
    }

    // DELETE USER
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // UPDATE PASSWORD
    public User updatePassword(Long id, String newPassword) {
        Optional<User> existingOpt = userRepository.findById(id);
        if (!existingOpt.isPresent()) {
            return null;
        }
        User existing = existingOpt.get();
        existing.setPassword(newPassword);
        return userRepository.save(existing);
    }
}