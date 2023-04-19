package com.nichoshop.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nichoshop.main.model.FeedBack;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {

}
