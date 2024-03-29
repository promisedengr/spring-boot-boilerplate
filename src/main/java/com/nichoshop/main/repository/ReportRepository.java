package com.nichoshop.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nichoshop.main.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

}