package com.somraj.HottelMGM.service.adminroom;

import com.somraj.HottelMGM.dto.ReservationResponseDto;

public interface ReservationService {
    
    ReservationResponseDto getAllReservation(int pageNumber);

    boolean changeReservationStatus(Long id, String status);
}
