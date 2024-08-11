package com.somraj.HottelMGM.controller.customer;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.somraj.HottelMGM.service.customer.RoomServiceCustomer;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class RoomController {
    
    //@Autowired
    private final RoomServiceCustomer roomService;
    
    @GetMapping("/rooms/{pageNumber}")
    public ResponseEntity<?> getAvailableRooms(@PathVariable int pageNumber){
        
        return ResponseEntity.ok(roomService.getAvailableRoom(pageNumber));
    }
}
