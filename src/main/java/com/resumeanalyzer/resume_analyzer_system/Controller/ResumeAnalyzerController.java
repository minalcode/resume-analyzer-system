package com.resumeanalyzer.resume_analyzer_system.Controller;

import com.resumeanalyzer.resume_analyzer_system.DTO.ResumeAnalysisRequestDTO;
import com.resumeanalyzer.resume_analyzer_system.DTO.ResumeAnalysisResponseDTO;
import com.resumeanalyzer.resume_analyzer_system.Model.ResumeAnalysis;
import com.resumeanalyzer.resume_analyzer_system.Service.ResumeAnalyzerService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resume")
public class ResumeAnalyzerController {

    private final ResumeAnalyzerService resumeAnalyzerService;

    public ResumeAnalyzerController(
            ResumeAnalyzerService resumeAnalyzerService) {
        this.resumeAnalyzerService = resumeAnalyzerService;
    }

    @PostMapping("/analyze")
    public ResumeAnalysisResponseDTO analyzeResume(
            @RequestBody ResumeAnalysisRequestDTO requestDTO) {
        return resumeAnalyzerService.analyzeResume(requestDTO);
    }

    @GetMapping("/history")
    public List<ResumeAnalysis> getAllAnalyses() {
        return resumeAnalyzerService.getAllAnalyses();
    }

    @GetMapping("/history/{id}")
    public ResumeAnalysis getAnalysisById(@PathVariable Long id) {
        return resumeAnalyzerService.getAnalysisById(id);
    }
}