package com.somraj.HottelMGM.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.somraj.HottelMGM.entity.Reservation;

@Repository
public interface ResevationRepository extends JpaRepository<Reservation, Long>{
    
    Page<Reservation> findAllByUserId(Pageable pageable, Long userId);
}
