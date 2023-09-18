package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Report;

public interface ReportDAO extends JpaRepository<Report, Integer> {

}
