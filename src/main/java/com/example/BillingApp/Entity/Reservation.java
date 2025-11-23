package com.example.BillingApp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guestId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Guest guest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roomId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Room room;

    private LocalDate checkInDate;
    private LocalDate checkoutDate;

    private Double totalAmount;

    public void calculateTotalAmount() {
        if (checkInDate != null && checkoutDate != null && room != null) {
            long nights = ChronoUnit.DAYS.between(checkInDate, checkoutDate);
            if (nights <= 0) nights = 1;
            this.totalAmount = nights * room.getPricePerNight();
        }
    }
}
