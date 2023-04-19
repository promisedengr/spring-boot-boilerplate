package com.nichoshop.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nichoshop.main.model.Sell;

@Repository
public interface SellRepository extends JpaRepository<Sell, Long>{

}