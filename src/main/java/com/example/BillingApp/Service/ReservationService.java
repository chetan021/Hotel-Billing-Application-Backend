package com.example.BillingApp.Service;

import com.example.BillingApp.Entity.Guest;
import com.example.BillingApp.Entity.Reservation;
import com.example.BillingApp.Entity.Room;
import com.example.BillingApp.Repository.GuestRepository;
import com.example.BillingApp.Repository.ReservationRepository;
import com.example.BillingApp.Repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepo;
    private final GuestRepository guestRepo;
    private final RoomRepository roomRepo;

    public ReservationService(ReservationRepository reservationRepo,
                              GuestRepository guestRepo,
                              RoomRepository roomRepo) {
        this.reservationRepo = reservationRepo;
        this.guestRepo = guestRepo;
        this.roomRepo = roomRepo;
    }

    public Reservation createReservation(Long guestId, Long roomId, String checkInDateStr, String checkoutDateStr) {
        System.out.println("Creating reservation for guest: " + guestId + ", room: " + roomId);

        // Parse dates
        LocalDate checkInDate = LocalDate.parse(checkInDateStr);
        LocalDate checkoutDate = LocalDate.parse(checkoutDateStr);

        // Validate dates
        validateDates(checkInDate, checkoutDate);

        // Fetch guest
        Guest guest = guestRepo.findById(guestId)
                .orElseThrow(() -> new IllegalArgumentException("Guest not found with ID: " + guestId));

        System.out.println("Found guest: " + guest.getFirstName() + " " + guest.getLastName());

        // Fetch and validate room
        Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + roomId));

        System.out.println("Found room: " + room.getRoomNumber() + " (" + room.getRoomStatus() + ")");

        if (!"Available".equals(room.getRoomStatus())) {
            throw new IllegalArgumentException("Room " + room.getRoomNumber() + " is not available");
        }

        // Create reservation
        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setRoom(room);
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckoutDate(checkoutDate);

        // Calculate total amount
        reservation.calculateTotalAmount();

        System.out.println("Calculated total amount: " + reservation.getTotalAmount());

        // Update room status
        room.setRoomStatus("Occupied");
        roomRepo.save(room);

        System.out.println("Updated room status to Occupied");

        // Save reservation
        Reservation saved = reservationRepo.save(reservation);

        System.out.println("Reservation saved with ID: " + saved.getReservationId());

        return saved;
    }

    private void validateDates(LocalDate checkIn, LocalDate checkOut) {
        LocalDate today = LocalDate.now();

        if (checkIn.isBefore(today)) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }

        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (days > 30) {
            throw new IllegalArgumentException("Maximum reservation period is 30 days");
        }

        System.out.println("Date validation passed: " + days + " nights");
    }

    public List<Reservation> getAllReservations() {
        return reservationRepo.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with ID: " + id));
    }

    public void cancelReservation(Long id) {
        Reservation reservation = getReservationById(id);

        // Free up the room
        Room room = reservation.getRoom();
        room.setRoomStatus("Available");
        roomRepo.save(room);

        // Delete reservation
        reservationRepo.delete(reservation);

        System.out.println("Reservation cancelled and room " + room.getRoomNumber() + " is now available");
    }
}