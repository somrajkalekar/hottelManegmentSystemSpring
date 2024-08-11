package com.somraj.HottelMGM.service.customer;

import java.util.stream.Collectors;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.somraj.HottelMGM.dto.RoomsResponseDto;
import com.somraj.HottelMGM.entity.Room;
import com.somraj.HottelMGM.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceCustomerImpl implements RoomServiceCustomer{
    
    //@Autowired
    private final RoomRepository roomRepository;

    public RoomsResponseDto getAvailableRoom(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 1);
        Page<Room> roomPage = roomRepository.findByAvailable(true, pageable);

        RoomsResponseDto roomsResponseDto = new RoomsResponseDto();
        roomsResponseDto.setPageNumber(roomPage.getPageable().getPageNumber());
        roomsResponseDto.setTotalPages(roomPage.getTotalPages());
        roomsResponseDto.setRoomDtoList(roomPage.stream().map(Room::getRoomDto).collect(Collectors.toList()));
        
        return roomsResponseDto;
    }
}
