package com.nichoshop.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nichoshop.main.model.CustomerSupport;

@Repository
public interface CustomerSupportRepository extends JpaRepository<CustomerSupport, Long>{
    
}
