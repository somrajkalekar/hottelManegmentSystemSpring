package com.somraj.HottelMGM.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.somraj.HottelMGM.service.adminroom.ReservationService;

@RestController
@RequestMapping("/api/admin")
public class ReservationController {
    
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservation/{pageNumber}")
    public ResponseEntity<?> getAllReservation(@PathVariable int pageNumber){
        try {
            return ResponseEntity.ok(reservationService.getAllReservation(pageNumber));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Roung...");
        }
    }


    @GetMapping("/reservation/{id}/{status}")
    public ResponseEntity<?> changeReservationStatus(@PathVariable Long id, @PathVariable String status){
        boolean success = reservationService.changeReservationStatus(id, status);

        if (success) {
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Went Roung");
        }
    }
}
