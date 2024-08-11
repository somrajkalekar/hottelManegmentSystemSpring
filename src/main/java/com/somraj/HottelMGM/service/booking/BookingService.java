package com.somraj.HottelMGM.service.booking;

import com.somraj.HottelMGM.dto.ReservationDto;
import com.somraj.HottelMGM.dto.ReservationResponseDto;

public interface BookingService {
    
    boolean postReservation(ReservationDto reservationDto);

    ReservationResponseDto getAllReservationByUserId(Long userId, int pageNumber);
}

