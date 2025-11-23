package com.example.BillingApp.Mapper;

import com.example.BillingApp.DTO.RoomDTO;
import com.example.BillingApp.Entity.Room;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between Room entity and RoomDTO
 * This separates the database layer from the API layer
 */
@Component
public class RoomMapper {

    /**
     * Converts Room entity to RoomDTO
     * @param room The Room entity to convert
     * @return RoomDTO or null if input is null
     */
    public RoomDTO toDTO(Room room) {
        if (room == null) {
            return null;
        }

        RoomDTO dto = new RoomDTO();
        dto.setRoomId(room.getRoomId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setRoomType(room.getRoomType());
        dto.setPricePerNight(room.getPricePerNight());
        dto.setRoomStatus(room.getRoomStatus());

        return dto;
    }

    /**
     * Converts RoomDTO to Room entity
     * @param dto The RoomDTO to convert
     * @return Room entity or null if input is null
     */
    public Room toEntity(RoomDTO dto) {
        if (dto == null) {
            return null;
        }

        Room room = new Room();
        room.setRoomId(dto.getRoomId());
        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());
        room.setPricePerNight(dto.getPricePerNight());
        room.setRoomStatus(dto.getRoomStatus());

        return room;
    }

    /**
     * Converts a list of Room entities to a list of RoomDTOs
     * @param rooms List of Room entities
     * @return List of RoomDTOs
     */
    public List<RoomDTO> toDTOList(List<Room> rooms) {
        if (rooms == null) {
            return null;
        }

        return rooms.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of RoomDTOs to a list of Room entities
     * @param dtos List of RoomDTOs
     * @return List of Room entities
     */
    public List<Room> toEntityList(List<RoomDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing Room entity with data from RoomDTO
     * This is useful for PUT/PATCH operations
     * @param room The existing Room entity to update
     * @param dto The RoomDTO containing new data
     */
    public void updateEntityFromDTO(Room room, RoomDTO dto) {
        if (room == null || dto == null) {
            return;
        }

        // Update only the fields that are present in DTO
        if (dto.getRoomNumber() != null) {
            room.setRoomNumber(dto.getRoomNumber());
        }
        if (dto.getRoomType() != null) {
            room.setRoomType(dto.getRoomType());
        }
        if (dto.getPricePerNight() != null) {
            room.setPricePerNight(dto.getPricePerNight());
        }
        if (dto.getRoomStatus() != null) {
            room.setRoomStatus(dto.getRoomStatus());
        }
    }
}
