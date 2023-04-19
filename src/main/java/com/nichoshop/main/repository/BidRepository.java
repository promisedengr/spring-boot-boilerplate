package com.nichoshop.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nichoshop.main.model.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query(value = "SELECT * FROM bids WHERE item_id = ?1", nativeQuery = true)
    public List<Bid> findAllByItemId(Long itemId);

}