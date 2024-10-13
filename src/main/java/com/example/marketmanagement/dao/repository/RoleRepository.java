package com.example.marketmanagement.dao.repository;

import com.example.marketmanagement.dao.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: nijataghayev
 */

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    List<RoleEntity> findByUserId(Long userId);
}
