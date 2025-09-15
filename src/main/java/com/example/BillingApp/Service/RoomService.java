package com.example.BillingApp.Service;

import com.example.BillingApp.Entity.Room;
import com.example.BillingApp.Repository.RoomRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class RoomService {
    RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }
    public Room createRoom( Room room){
        return roomRepository.save(room);
    }

    public Room findRoomByRoomId(String roomId){
        return roomRepository.findByRoomNumber(roomId);
    }

    public List<Room> findAll(){
        return roomRepository.findAll();
    }

    public void deleteRoomByRoomId(String roomId){
        roomRepository.delete(roomRepository.findByRoomNumber(roomId));
    }
}
