package com.example.marketmanagement.dao.repository;

import com.example.marketmanagement.dao.entity.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author: nijataghayev
 */

public interface BasketRepository extends JpaRepository<BasketEntity, Long> {
}
