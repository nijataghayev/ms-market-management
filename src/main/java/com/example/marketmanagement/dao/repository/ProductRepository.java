package com.example.marketmanagement.dao.repository;

import com.example.marketmanagement.dao.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author: nijataghayev
 */

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
