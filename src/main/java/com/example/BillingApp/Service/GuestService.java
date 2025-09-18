package com.example.BillingApp.Service;

import com.example.BillingApp.Entity.Guest;
import com.example.BillingApp.Repository.GuestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {
    public final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository){
        this.guestRepository = guestRepository;
    }
    
    public Guest saveGuest(Guest guest){
        return guestRepository.save(guest);
    }
    
    public Guest getGuestById(Long id){
        return guestRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Guest not found"));
    }

    public List<Guest> getAllGuests(){
        return guestRepository.findAll();
    }

    public void deleteGuestById(Long id){
        guestRepository.deleteById(id);
    }
}
