package com.example.BillingApp.Controller;

import com.example.BillingApp.Entity.Guest;
import com.example.BillingApp.Service.GuestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
@CrossOrigin(origins = "http://localhost:5173")
public class GuestController {
    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    public Guest saveGuest(@RequestBody Guest guest) {
        return guestService.saveGuest(guest);
    }

    @GetMapping
    public List<Guest> findAllGuest() {
        return guestService.getAllGuests();
    }

    @GetMapping("/{id}")
    public Guest findById(@PathVariable Long id) {
        return guestService.getGuestById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        guestService.deleteGuestById(id);
    }
}
