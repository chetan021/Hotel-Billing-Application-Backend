package com.example.BillingApp.Controller;

import com.example.BillingApp.Entity.Reservation;
import com.example.BillingApp.Service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "http://localhost:5173")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service){
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> createReservation(@RequestBody Map<String, Object> requestBody) {
        try {
            System.out.println("Received reservation request: " + requestBody);

            // Extract data from request
            Long guestId = Long.valueOf(requestBody.get("guestId").toString());
            Long roomId = Long.valueOf(requestBody.get("roomId").toString());
            String checkInDate = requestBody.get("checkInDate").toString();
            String checkoutDate = requestBody.get("checkoutDate").toString();

            // Create reservation
            Reservation reservation = service.createReservation(guestId, roomId, checkInDate, checkoutDate);

            return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error creating reservation: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create reservation: " + e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Reservation>> listReservations() {
        try {
            List<Reservation> reservations = service.getAllReservations();
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            System.err.println("Error fetching reservations: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getReservation(@PathVariable Long id) {
        try {
            Reservation reservation = service.getReservationById(id);
            return ResponseEntity.ok(reservation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> cancelReservation(@PathVariable Long id) {
        try {
            service.cancelReservation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

