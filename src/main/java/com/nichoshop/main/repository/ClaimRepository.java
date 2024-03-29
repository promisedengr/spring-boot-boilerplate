package com.nichoshop.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nichoshop.main.model.Claim;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long>{

}