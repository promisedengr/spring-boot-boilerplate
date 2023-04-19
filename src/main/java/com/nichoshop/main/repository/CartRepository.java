package com.nichoshop.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nichoshop.main.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
    
}