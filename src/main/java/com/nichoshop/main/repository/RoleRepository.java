package com.nichoshop.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.nichoshop.main.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByUserId(Long long1);
}
