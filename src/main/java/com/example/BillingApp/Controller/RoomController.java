package com.example.BillingApp.Controller;

import com.example.BillingApp.Entity.Room;
import com.example.BillingApp.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<Room> findAll() {
        return roomService.findAll();
    }

    @PostMapping
    public Room save(@RequestBody Room room) {
        return roomService.createRoom(room);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        roomService.deleteRoomByRoomId(id);
    }
}
