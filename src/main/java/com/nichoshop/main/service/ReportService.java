package com.nichoshop.main.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nichoshop.main.model.Report;
import com.nichoshop.main.repository.ReportRepository;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    public Report getReportById(Long id) {
        return reportRepository.findById(id).get();
    }

    public List<Report> getAllReports() {
        List<Report> Reports = new ArrayList<Report>();
        reportRepository.findAll().forEach(Report -> Reports.add(Report));
        return Reports;
    }

    public void saveOrUpdate(Report Report) {
        reportRepository.save(Report);
    }

    public void deleteReportById(Long id) {
        reportRepository.deleteById(id);
    }
}