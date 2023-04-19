package com.nichoshop.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nichoshop.main.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE username = ?1 OR email = ?1", nativeQuery = true)
    User findByUsernameOrEmail(String userString);

    @Query(value = "UPDATE users SET phone_confirmed = true WHERE id = ?1", nativeQuery = true)
    User confirmPhone(Long userId);

}