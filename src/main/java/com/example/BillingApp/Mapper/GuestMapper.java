package com.example.BillingApp.Mapper;

import com.example.BillingApp.DTO.GuestDTO;
import com.example.BillingApp.Entity.Guest;
import org.springframework.stereotype.Component;

@Component
public class GuestMapper {

    public GuestDTO toDTO(Guest guest) {
        if (guest == null) return null;

        GuestDTO dto = new GuestDTO();
        dto.setId(guest.getId());
        dto.setFirstName(guest.getFirstName());
        dto.setLastName(guest.getLastName());
        dto.setEmail(guest.getEmail());
        dto.setPhone(guest.getPhone());
        dto.setAddress(guest.getAddress());
        return dto;
    }

    public Guest toEntity(GuestDTO dto) {
        if (dto == null) return null;

        Guest guest = new Guest();
        guest.setId(dto.getId());
        guest.setFirstName(dto.getFirstName());
        guest.setLastName(dto.getLastName());
        guest.setEmail(dto.getEmail());
        guest.setPhone(dto.getPhone());
        guest.setAddress(dto.getAddress());
        return guest;
    }
}