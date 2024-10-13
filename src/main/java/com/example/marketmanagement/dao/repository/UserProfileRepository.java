package com.example.marketmanagement.dao.repository;

import com.example.marketmanagement.dao.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author: nijataghayev
 */

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {
    @Query("SELECT u FROM UserProfileEntity u WHERE EXTRACT(MONTH FROM u.birthDate) = :month AND EXTRACT(DAY FROM u.birthDate) = :day")
    List<UserProfileEntity> findByBirthDate(@Param("month") int month, @Param("day") int day);
}
