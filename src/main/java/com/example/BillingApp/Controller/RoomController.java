package com.example.BillingApp.Controller;

import com.example.BillingApp.Entity.Room;
import com.example.BillingApp.Repository.RoomRepository;
import com.example.BillingApp.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomController {

    private final RoomRepository roomRepo;

    public RoomController(RoomRepository roomRepo) {
        this.roomRepo = roomRepo;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    @PostMapping
    public Room addRoom(@RequestBody Room room) {
        room.setRoomStatus("Available"); // default
        return roomRepo.save(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room roomDetails) {
        return roomRepo.findById(id).map(room -> {
            room.setRoomNumber(roomDetails.getRoomNumber());
            room.setRoomType(roomDetails.getRoomType());
            room.setPricePerNight(roomDetails.getPricePerNight());
            room.setRoomStatus(roomDetails.getRoomStatus());
            return ResponseEntity.ok(roomRepo.save(room));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        return roomRepo.findById(id).map(room -> {
            roomRepo.delete(room);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

