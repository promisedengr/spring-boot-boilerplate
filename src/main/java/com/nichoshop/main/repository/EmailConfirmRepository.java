package com.nichoshop.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.nichoshop.main.model.EmailConfirm;

@Repository
public interface EmailConfirmRepository extends JpaRepository<EmailConfirm, Long> {
    Optional<EmailConfirm> findByUserId(Long userId);

    EmailConfirm findByCode(String code);
}