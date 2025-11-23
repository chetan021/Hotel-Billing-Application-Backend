package com.example.BillingApp.Service;

import com.example.BillingApp.DTO.GuestDTO;
import com.example.BillingApp.Entity.Guest;
import com.example.BillingApp.Exception.ResourceNotFoundException;
import com.example.BillingApp.Mapper.GuestMapper;
import com.example.BillingApp.Repository.GuestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GuestService {

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    public GuestService(GuestRepository guestRepository, GuestMapper guestMapper){
        this.guestRepository = guestRepository;
        this.guestMapper = guestMapper;
    }

    public GuestDTO saveGuest(GuestDTO guestDTO) {
        // Check if phone already exists
        guestRepository.findByPhone(guestDTO.getPhone()).ifPresent(g -> {
            throw new IllegalArgumentException("Guest with this phone already exists");
        });

        Guest guest = guestMapper.toEntity(guestDTO);
        Guest saved = guestRepository.save(guest);
        return guestMapper.toDTO(saved);
    }

    public GuestDTO getGuestById(Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with ID: " + id));
        return guestMapper.toDTO(guest);
    }

    public List<GuestDTO> getAllGuests() {
        return guestRepository.findAll().stream()
                .map(guestMapper::toDTO)
                .collect(Collectors.toList());
    }

    public GuestDTO updateGuest(Long id, GuestDTO guestDTO) {
        Guest existing = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found"));

        existing.setFirstName(guestDTO.getFirstName());
        existing.setLastName(guestDTO.getLastName());
        existing.setEmail(guestDTO.getEmail());
        existing.setPhone(guestDTO.getPhone());
        existing.setAddress(guestDTO.getAddress());

        Guest updated = guestRepository.save(existing);
        return guestMapper.toDTO(updated);
    }

    public void deleteGuestById(Long id) {
        if (!guestRepository.existsById(id)) {
            throw new ResourceNotFoundException("Guest not found");
        }
        guestRepository.deleteById(id);
    }
}