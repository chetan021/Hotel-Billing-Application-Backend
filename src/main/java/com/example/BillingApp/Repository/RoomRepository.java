package com.example.BillingApp.Repository;

import com.example.BillingApp.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findByRoomNumber(String roomNumber);

}
