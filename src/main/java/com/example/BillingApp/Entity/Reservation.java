package com.example.BillingApp.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "guestId", nullable = false)
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "roomId", nullable = false)
    private Room room;

    private LocalDate checkInDate;
    private LocalDate checkoutDate;

    private double totalAmount;

    public void calculateTotalAmount(){
        if(checkInDate != null && checkoutDate != null && room != null){
            long nights = ChronoUnit.DAYS.between(checkInDate, checkoutDate);
            if(nights<=0)nights = 1;
            this.totalAmount = nights * room.getPricePerNight();
        }
    }
}
