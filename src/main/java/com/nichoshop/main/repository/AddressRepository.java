package com.nichoshop.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import com.nichoshop.main.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
    // public Address findById(Long id);
    public Address findByUserId(Long userId);
    @Query(value = "SELECT * FROM addresses WHERE user_id = ?1 And address_type = ?2" ,nativeQuery=true)
    Address findByUserIdAndType(Long userId, int addressType);


}
