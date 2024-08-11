package com.somraj.HottelMGM.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.somraj.HottelMGM.dto.RoomDto;
import com.somraj.HottelMGM.service.adminroom.RoomService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class RoomsController {
    
    //@Autowired
    private final RoomService roomService;

    @PostMapping("/room")
    public ResponseEntity<?> postRoom(@RequestBody RoomDto roomDto){
        boolean sucess = roomService.postRoom(roomDto);
        if(sucess){
        return ResponseEntity.status(HttpStatus.OK).body("Created");
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not Created");
        }

    }

    @GetMapping("/room/{pageNumber}")
    public ResponseEntity<?> getAllRooms(@PathVariable Integer pageNumber){
        return ResponseEntity.ok(roomService.getAllRooms(pageNumber));
    }

     @GetMapping("/rooms/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(roomService.getRoomById(id));
        }  catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Roung");
        }  

    }

    @PutMapping("/room/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable Long id, @RequestBody RoomDto roomDto){
        boolean sucess = roomService.updateRoom(id, roomDto);
        if (sucess) {
            return ResponseEntity.status(HttpStatus.OK).body("Updated");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something Went Roung");
        }
    }

    @DeleteMapping("/room/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id){
        try {
            roomService.deleteRoom(id);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted SuccessFull...");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something Went Roung..." + e.getMessage());
        }
    }
    
}
