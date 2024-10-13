package com.example.marketmanagement.dao.repository;

import com.example.marketmanagement.dao.entity.UserCredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author: nijataghayev
 */

public interface UserCredentialsRepository extends JpaRepository<UserCredentialEntity, Long> {
    Optional<UserCredentialEntity> findByUsername(String username);
}
