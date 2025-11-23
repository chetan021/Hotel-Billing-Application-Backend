package com.example.BillingApp.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RoomDTO {
    private Long roomId;

    @NotBlank(message = "Room number is required")
    private String roomNumber;

    @NotBlank(message = "Room type is required")
    private String roomType;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private Double pricePerNight;

    private String roomStatus;
}