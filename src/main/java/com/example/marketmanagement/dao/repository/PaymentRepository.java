package com.example.marketmanagement.dao.repository;

import com.example.marketmanagement.dao.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: nijataghayev
 */

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
