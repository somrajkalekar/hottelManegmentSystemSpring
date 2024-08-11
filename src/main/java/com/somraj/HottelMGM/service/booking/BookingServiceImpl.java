package com.somraj.HottelMGM.service.booking;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.somraj.HottelMGM.dto.ReservationDto;
import com.somraj.HottelMGM.dto.ReservationResponseDto;
import com.somraj.HottelMGM.entity.Reservation;
import com.somraj.HottelMGM.entity.Room;
import com.somraj.HottelMGM.entity.User;
import com.somraj.HottelMGM.enums.ReservationStatus;
import com.somraj.HottelMGM.repository.ResevationRepository;
import com.somraj.HottelMGM.repository.RoomRepository;
import com.somraj.HottelMGM.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ResevationRepository resevationRepository;

    public static final int SEARCH_RESULT_PER_PAGE = 4;

    public boolean postReservation(ReservationDto reservationDto){
        //First User ch details and second Room he doni details 
        //milun je ahe reservation create hoto

        Optional<User> optionalUser = userRepository.findById(reservationDto.getId());
        Optional<Room> optionalRoom = roomRepository.findById(reservationDto.getId());
        
        if (optionalUser.isPresent() && optionalRoom.isPresent()) {
            Reservation reservation = new Reservation();
            reservation.setRoom(optionalRoom.get());
            reservation.setUser(optionalUser.get());
            reservation.setCheckInDate(reservationDto.getCheckInDate());
            reservation.setCheckOutDate(reservationDto.getCheckOutDate());
            reservation.setReservationStatus(ReservationStatus.PENDING);
        

        //calculation of Days
        Long days = ChronoUnit.DAYS.between(reservationDto.getCheckInDate(), reservationDto.getCheckOutDate());

        //calculation of price between days 
        reservation.setPrice(optionalRoom.get().getPrice()*days);

        resevationRepository.save(reservation);
        return true;

        }
        return false;
    }

    @Override
    public ReservationResponseDto getAllReservationByUserId(Long userId, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);

        Page<Reservation> reservationPage = resevationRepository.findAllByUserId(pageable,userId);

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();

        reservationResponseDto.setReservationDtoList(reservationPage.stream().map(Reservation::getReservationDto)
        .collect(Collectors.toList()));

        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());
        reservationResponseDto.setTotalPages(reservationPage.getTotalPages());

        return reservationResponseDto;
    }
}
