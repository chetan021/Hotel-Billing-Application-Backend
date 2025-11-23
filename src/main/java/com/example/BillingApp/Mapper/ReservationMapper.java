package com.example.BillingApp.Mapper;

import com.example.BillingApp.DTO.ReservationDTO;
import com.example.BillingApp.Entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    private final GuestMapper guestMapper;
    private final RoomMapper roomMapper;

    public ReservationMapper(GuestMapper guestMapper, RoomMapper roomMapper) {
        this.guestMapper = guestMapper;
        this.roomMapper = roomMapper;
    }

    public ReservationDTO toDTO(Reservation reservation) {
        if (reservation == null) return null;

        ReservationDTO dto = new ReservationDTO();
        dto.setReservationId(reservation.getReservationId());
        dto.setGuestId(reservation.getGuest().getId());
        dto.setRoomId(reservation.getRoom().getRoomId());
        dto.setCheckInDate(reservation.getCheckInDate());
        dto.setCheckoutDate(reservation.getCheckoutDate());
        dto.setTotalAmount(reservation.getTotalAmount());

        // Include full guest and room details
        dto.setGuest(guestMapper.toDTO(reservation.getGuest()));
        dto.setRoom(roomMapper.toDTO(reservation.getRoom()));

        return dto;
    }
}