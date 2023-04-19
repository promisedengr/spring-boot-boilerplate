package com.nichoshop.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nichoshop.main.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}