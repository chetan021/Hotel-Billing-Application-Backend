package com.example.BillingApp.Repository;

import com.example.BillingApp.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r WHERE r.room.roomId = :roomId " +
            "AND ((r.checkInDate <= :checkOut AND r.checkoutDate >= :checkIn))")
    List<Reservation> findConflictingReservations(
            @Param("roomId") Long roomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );

    List<Reservation> findByGuestId(Long guestId);

    @Query("SELECT r FROM Reservation r WHERE r.checkInDate = :date")
    List<Reservation> findTodayCheckIns(@Param("date") LocalDate date);
}