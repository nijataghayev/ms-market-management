package com.example.marketmanagement.dao.repository;

import com.example.marketmanagement.dao.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: nijataghayev
 */

public interface CardRepository extends JpaRepository<CardEntity, Long> {
}
