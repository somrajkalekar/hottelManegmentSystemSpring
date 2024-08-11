package com.somraj.HottelMGM.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.somraj.HottelMGM.entity.User;
import com.somraj.HottelMGM.enums.UserRole;



@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    
    Optional<User> findFirstByEmail(String email);

    Optional<User> findByUserRole(UserRole userRole);

}
