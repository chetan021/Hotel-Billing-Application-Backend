package com.example.BillingApp.Controller;

import com.example.BillingApp.Entity.Reservation;
import com.example.BillingApp.Service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "http://localhost:5173")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service){
        this.service= service;
    }

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation){
        return service.saveReservation(reservation);
    }

    @GetMapping
    public List<Reservation> listReservtion(){
        return service.getAllReservations();
    }
}
