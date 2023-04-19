package com.nichoshop.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.nichoshop.main.model.SUC;

@Repository
public interface SUCRepository extends JpaRepository<SUC, Long> {
    @Query(value = "SELECT code FROM sucs WHERE user_id = ?1", nativeQuery = true)
    public List<String> findByUserId(Long userId);

    @Query(value = "SELECT code FROM sucs WHERE user_id = ?1 AND suc_type = ?2", nativeQuery = true)
    SUC findLastWithIdAndType(Long userId, Integer sucType);

    @Query(value = "SELECT * FROM sucs WHERE user_id = ?1 AND code = ?2 AND suc_type = ?3", nativeQuery = true)
    SUC findSUC(Long userId, String code, Integer sucType);

}
