package com.somraj.HottelMGM.service.adminroom;

import com.somraj.HottelMGM.dto.RoomDto;

import com.somraj.HottelMGM.dto.RoomsResponseDto;

public interface RoomService {
   
    boolean postRoom(RoomDto roomDto);

    RoomsResponseDto getAllRooms(int pageNumber);

    RoomDto getRoomById(Long id);

    boolean updateRoom(Long id, RoomDto roomDto);

    void deleteRoom(Long id);
}
