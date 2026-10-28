package com.resumeanalyzer.resume_analyzer_system.Repository;

import com.resumeanalyzer.resume_analyzer_system.Model.ResumeAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeAnalysisRepository extends JpaRepository<ResumeAnalysis, Long> {
}