package com.example.BillingApp.Controller;

import com.example.BillingApp.Entity.Room;
import com.example.BillingApp.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // ✅ GET all rooms
    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.findAll();
    }

    // ✅ POST new room
    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return roomService.createRoom(room);
    }

    // ✅ DELETE room
    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable String id) {
        roomService.deleteRoomByRoomId(id);
    }
}

