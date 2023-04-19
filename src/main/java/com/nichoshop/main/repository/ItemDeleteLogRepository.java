package com.nichoshop.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.nichoshop.main.model.ItemDeleteLog;

@Repository
public interface ItemDeleteLogRepository extends JpaRepository<ItemDeleteLog, Long>{
    
}
