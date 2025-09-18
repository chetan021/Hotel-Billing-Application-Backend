package com.example.BillingApp.Service;

import com.example.BillingApp.Entity.Guest;
import com.example.BillingApp.Entity.Reservation;
import com.example.BillingApp.Entity.Room;
import com.example.BillingApp.Repository.GuestRepository;
import com.example.BillingApp.Repository.ReservationRepository;
import com.example.BillingApp.Repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository repo;
    private final GuestRepository guestRepo;
    private final RoomRepository roomRepo;

    ReservationService(ReservationRepository repo, GuestRepository guestRepo, RoomRepository roomRepository){
        this.repo= repo;
        this.guestRepo = guestRepo;
        this.roomRepo = roomRepository;
    }

    public Reservation saveReservation(Reservation reservation){
        Guest guest = guestRepo.findById(reservation.getGuest().getId())
                        .orElseThrow(() -> new RuntimeException("Guest not found"));

        Room room = roomRepo.findById(reservation.getRoom().getRoomId())
                        .orElseThrow(()->new RuntimeException("Room not found"));

        reservation.setGuest(guest);
        reservation.setRoom(room);

        reservation.calculateTotalAmount();
        return repo.save(reservation);
    }

    public List<Reservation> getAllReservations(){
        return repo.findAll();
    }
}
