package com.nichoshop.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nichoshop.main.model.DuoConfirm;

@Repository
public interface DuoConfirmRepository extends JpaRepository<DuoConfirm, Long>{
    DuoConfirm findByState(String state);
}