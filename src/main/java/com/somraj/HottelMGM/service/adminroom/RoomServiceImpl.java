package com.somraj.HottelMGM.service.adminroom;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.somraj.HottelMGM.dto.RoomDto;
import com.somraj.HottelMGM.dto.RoomsResponseDto;
import com.somraj.HottelMGM.entity.Room;
import com.somraj.HottelMGM.repository.RoomRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
    
     @Autowired
    private RoomRepository roomRepository; 

    //private final RoomRepository roomRepository;

    public boolean postRoom(RoomDto roomDto){
        try {
            Room room = new Room();

            room.setName(roomDto.getName());
            room.setPrice(roomDto.getPrice());
            room.setType(roomDto.getType());
            room.setAvailable(true);

            roomRepository.save(room);
            return true;
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
        
    }

    @Override
    public RoomsResponseDto getAllRooms(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 1);
        Page<Room> roomPage = roomRepository.findAll(pageable);

        RoomsResponseDto roomsResponseDto = new RoomsResponseDto();
        roomsResponseDto.setPageNumber(roomPage.getPageable().getPageNumber());
        roomsResponseDto.setTotalPages(roomPage.getTotalPages());
        roomsResponseDto.setRoomDtoList(roomPage.stream().map(Room::getRoomDto).collect(Collectors.toList()));
        
        return roomsResponseDto;
    }

    @Override
    public RoomDto getRoomById(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);

        if (optionalRoom.isPresent()) {
            return optionalRoom.get().getRoomDto();
        }
        else{
            throw new EntityNotFoundException("Room Not Present");
        }
    }

    @Override
    public boolean updateRoom(Long id, RoomDto roomDto) {
       Optional<Room> optionalRoom = roomRepository.findById(id);

       if (optionalRoom.isPresent()) {
            Room existingRoom = optionalRoom.get();

            existingRoom.setName(roomDto.getName());
            existingRoom.setPrice(roomDto.getPrice());
            existingRoom.setType(roomDto.getType());

            roomRepository.save(existingRoom);
            
            return true;
       }
       return false;
    }

    @Override
    public void deleteRoom(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            roomRepository.deleteById(id);
        }
        else{
            throw new EntityNotFoundException("Room Not Found");
        }
    }

    
}
