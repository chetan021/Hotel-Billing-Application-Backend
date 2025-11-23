package com.example.BillingApp.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ReservationDTO {
    private Long reservationId;

    @NotNull(message = "Guest ID is required")
    private Long guestId;

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotNull(message = "Check-in date is required")
    @FutureOrPresent(message = "Check-in must be today or future")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    @Future(message = "Check-out must be in future")
    private LocalDate checkoutDate;

    private Double totalAmount;
    private String status;

    // Include guest and room details in response
    private GuestDTO guest;
    private RoomDTO room;
}