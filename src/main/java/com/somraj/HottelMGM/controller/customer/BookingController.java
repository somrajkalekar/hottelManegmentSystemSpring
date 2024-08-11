package com.somraj.HottelMGM.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.somraj.HottelMGM.dto.ReservationDto;
import com.somraj.HottelMGM.service.booking.BookingService;

@RestController
@RequestMapping("/api/customer")
public class BookingController {
    
    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<?> postBooking(@RequestBody ReservationDto reservationDto){
        boolean success = bookingService.postReservation(reservationDto);

        if (success) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Created");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Created");
        }

    }

    @GetMapping("/bookings/{userId}/{pageNumber}")
        public ResponseEntity<?> getAllBookingByUserId(@PathVariable Long userid, @PathVariable int pageNumber){
            try {
                return ResponseEntity.ok(bookingService.getAllReservationByUserId(userid, pageNumber));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went Roung...");
            }
        }
}
