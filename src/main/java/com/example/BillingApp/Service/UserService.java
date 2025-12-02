package com.example.BillingApp.Service;

import com.example.BillingApp.DTO.UpdateUserRequest;
import com.example.BillingApp.DTO.UserRequest;
import com.example.BillingApp.DTO.UserResponse;

//import com.hotel.billing.entity.User;
//import com.hotel.billing.exception.ResourceNotFoundException;
//import com.hotel.billing.exception.DuplicateResourceException;
//import com.hotel.billing.repository.UserRepository;
//import com.hotel.billing.service.UserService;
import com.example.BillingApp.Entity.User;
import com.example.BillingApp.Exception.ResourceNotFoundException;
import com.example.BillingApp.Exception.DuplicateResourceException;
import com.example.BillingApp.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        // Check for duplicate username
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new DuplicateResourceException("Username already exists: " + userRequest.getUsername());
        }

        // Check for duplicate email
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + userRequest.getEmail());
        }

        User user = new User();
        user.setName(userRequest.getName());
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(userRequest.getRole());

        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapToResponse(user);
    }

    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Update name if provided
        if (updateUserRequest.getName() != null && !updateUserRequest.getName().isEmpty()) {
            user.setName(updateUserRequest.getName());
        }

        // Update username if provided and not duplicate
        if (updateUserRequest.getUsername() != null && !updateUserRequest.getUsername().isEmpty()) {
            if (!user.getUsername().equals(updateUserRequest.getUsername())
                    && userRepository.existsByUsername(updateUserRequest.getUsername())) {
                throw new DuplicateResourceException("Username already exists: " + updateUserRequest.getUsername());
            }
            user.setUsername(updateUserRequest.getUsername());
        }

        // Update email if provided and not duplicate
        if (updateUserRequest.getEmail() != null && !updateUserRequest.getEmail().isEmpty()) {
            if (!user.getEmail().equals(updateUserRequest.getEmail())
                    && userRepository.existsByEmail(updateUserRequest.getEmail())) {
                throw new DuplicateResourceException("Email already exists: " + updateUserRequest.getEmail());
            }
            user.setEmail(updateUserRequest.getEmail());
        }

        // Update password if provided
        if (updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        }

        // Update role if provided
        if (updateUserRequest.getRole() != null) {
            user.setRole(updateUserRequest.getRole());
        }

        User updatedUser = userRepository.save(user);
        return mapToResponse(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
