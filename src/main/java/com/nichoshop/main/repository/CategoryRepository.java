package com.nichoshop.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

import com.nichoshop.main.model.Category;
import com.nichoshop.main.model.schema.SpecificsSchema;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT conditions FROM Categories", nativeQuery = true)
    List<String> getConditionsList();

    @Query(value = "SELECT specifics FROM Categories", nativeQuery = true)
    List<SpecificsSchema> findSpecifics();

    @Query(value = "SELECT * FROM Categories WHERE parent_id = ?1 AND ord = ?2", nativeQuery = true)
    List<Category> findByParentIdAndOrd(Long parentId, int ord);

    List<Category> findByParentId(Long parentId);

}