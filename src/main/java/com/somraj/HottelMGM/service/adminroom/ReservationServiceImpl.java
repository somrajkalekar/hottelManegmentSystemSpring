package com.somraj.HottelMGM.service.adminroom;


import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.somraj.HottelMGM.dto.ReservationResponseDto;
import com.somraj.HottelMGM.entity.Reservation;
import com.somraj.HottelMGM.entity.Room;
import com.somraj.HottelMGM.enums.ReservationStatus;
import com.somraj.HottelMGM.repository.ResevationRepository;
import com.somraj.HottelMGM.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class ReservationServiceImpl implements ReservationService{
    
    @Autowired
    private ResevationRepository resevationRepository;

    @Autowired
    private RoomRepository roomRepository;

    public static final int SEARCH_RESULT_PER_PAGE = 4;

    @Override
    public ReservationResponseDto getAllReservation(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);

        Page<Reservation> reservationPage = resevationRepository.findAll(pageable);

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();

        reservationResponseDto.setReservationDtoList(reservationPage.stream().map(Reservation::getReservationDto)
        .collect(Collectors.toList()));

        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());
        reservationResponseDto.setTotalPages(reservationPage.getTotalPages());

        return reservationResponseDto;
    }

    public boolean changeReservationStatus(Long id, String status){
        
        Optional<Reservation> optionalResevation = resevationRepository.findById(id);

        if (optionalResevation.isPresent()) {
            Reservation existingReservation = optionalResevation.get();

            if (Objects.equals(status, "Approve")) {
                existingReservation.setReservationStatus(ReservationStatus.APPROVED);
            }
            else{
                existingReservation.setReservationStatus(ReservationStatus.REJECTED);
            }

            resevationRepository.save(existingReservation);

            Room existingRoom = existingReservation.getRoom();
            existingRoom.setAvailable(false);

            roomRepository.save(existingRoom);

            return true;
        }
        
        return false;
    }

    
}
