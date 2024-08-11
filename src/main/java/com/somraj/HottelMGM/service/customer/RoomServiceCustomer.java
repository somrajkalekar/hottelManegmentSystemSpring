package com.somraj.HottelMGM.service.customer;

import com.somraj.HottelMGM.dto.RoomsResponseDto;


public interface RoomServiceCustomer {

    RoomsResponseDto getAvailableRoom(int pageNumber);
} 
