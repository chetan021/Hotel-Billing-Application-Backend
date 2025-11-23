package com.example.BillingApp.Service;

import com.example.BillingApp.DTO.GuestDTO;
import com.example.BillingApp.Entity.Guest;
import com.example.BillingApp.Exception.ResourceNotFoundException;
import com.example.BillingApp.Mapper.GuestMapper;
import com.example.BillingApp.Repository.GuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuestServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private GuestMapper guestMapper;

    @InjectMocks
    private GuestService guestService;

    private Guest guest;
    private GuestDTO guestDTO;

    @BeforeEach
    void setUp() {
        guest = new Guest(1L, "John", "Doe", "john@test.com", "1234567890", "123 Main St");
        guestDTO = new GuestDTO();
        guestDTO.setId(1L);
        guestDTO.setFirstName("John");
        guestDTO.setLastName("Doe");
        guestDTO.setEmail("john@test.com");
    }

    @Test
    void saveGuest_ShouldReturnSavedGuest() {
        when(guestMapper.toEntity(any(GuestDTO.class))).thenReturn(guest);
        when(guestRepository.save(any(Guest.class))).thenReturn(guest);
        when(guestMapper.toDTO(any(Guest.class))).thenReturn(guestDTO);

        GuestDTO result = guestService.saveGuest(guestDTO);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(guestRepository, times(1)).save(any(Guest.class));
    }

    @Test
    void getGuestById_WhenGuestExists_ShouldReturnGuest() {
        when(guestRepository.findById(1L)).thenReturn(Optional.of(guest));
        when(guestMapper.toDTO(guest)).thenReturn(guestDTO);

        GuestDTO result = guestService.getGuestById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getGuestById_WhenGuestNotExists_ShouldThrowException() {
        when(guestRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> guestService.getGuestById(1L));
    }
}