package com.resumeanalyzer.resume_analyzer_system.Service;

import com.resumeanalyzer.resume_analyzer_system.DTO.ResumeAnalysisRequestDTO;
import com.resumeanalyzer.resume_analyzer_system.DTO.ResumeAnalysisResponseDTO;
import com.resumeanalyzer.resume_analyzer_system.Model.ResumeAnalysis;
import com.resumeanalyzer.resume_analyzer_system.Repository.ResumeAnalysisRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class ResumeAnalyzerService {

    private final ResumeAnalysisRepository resumeAnalysisRepository;

    private final List<String> skills = Arrays.asList(
            "Java",
            "Spring Boot",
            "Spring Security",
            "Spring",
            "Hibernate",
            "JPA",
            "SQL",
            "MySQL",
            "MongoDB",
            "REST API",
            "React",
            "JavaScript",
            "HTML",
            "CSS",
            "Git",
            "GitHub",
            "Docker",
            "AWS",
            "Microservices",
            "Python",
            "Redis",
            "Kafka",
            "Kubernetes",
            "Maven",
            "Gradle",
            "JWT"
    );

    public ResumeAnalyzerService(
            ResumeAnalysisRepository resumeAnalysisRepository) {
        this.resumeAnalysisRepository = resumeAnalysisRepository;
    }

    public ResumeAnalysisResponseDTO analyzeResume(
            ResumeAnalysisRequestDTO requestDTO) {

        if (requestDTO.getResumeText() == null
                || requestDTO.getResumeText().isBlank()) {
            throw new IllegalArgumentException("Resume text is required");
        }

        if (requestDTO.getJobDescription() == null
                || requestDTO.getJobDescription().isBlank()) {
            throw new IllegalArgumentException("Job description is required");
        }

        String resumeText =
                requestDTO.getResumeText().toLowerCase(Locale.ROOT);

        String jobDescription =
                requestDTO.getJobDescription().toLowerCase(Locale.ROOT);

        List<String> requiredSkills = new ArrayList<>();
        List<String> matchedSkills = new ArrayList<>();
        List<String> missingSkills = new ArrayList<>();

        for (String skill : skills) {
            String lowerSkill = skill.toLowerCase(Locale.ROOT);

            if (jobDescription.contains(lowerSkill)) {
                requiredSkills.add(skill);

                if (resumeText.contains(lowerSkill)) {
                    matchedSkills.add(skill);
                } else {
                    missingSkills.add(skill);
                }
            }
        }

        double matchPercentage = 0.0;

        if (!requiredSkills.isEmpty()) {
            matchPercentage = Math.round(
                    (matchedSkills.size() * 100.0 / requiredSkills.size())
                            * 100.0
            ) / 100.0;
        }

        ResumeAnalysis analysis = new ResumeAnalysis();

        analysis.setResumeText(requestDTO.getResumeText());
        analysis.setJobDescription(requestDTO.getJobDescription());
        analysis.setMatchedSkills(String.join(", ", matchedSkills));
        analysis.setMissingSkills(String.join(", ", missingSkills));
        analysis.setMatchPercentage(matchPercentage);
        analysis.setAnalyzedAt(LocalDateTime.now());

        ResumeAnalysis savedAnalysis =
                resumeAnalysisRepository.save(analysis);

        ResumeAnalysisResponseDTO responseDTO =
                new ResumeAnalysisResponseDTO();

        responseDTO.setId(savedAnalysis.getId());
        responseDTO.setMatchPercentage(savedAnalysis.getMatchPercentage());
        responseDTO.setMatchedSkills(matchedSkills);
        responseDTO.setMissingSkills(missingSkills);
        responseDTO.setAnalyzedAt(savedAnalysis.getAnalyzedAt());

        return responseDTO;
    }

    public List<ResumeAnalysis> getAllAnalyses() {
        return resumeAnalysisRepository.findAll();
    }

    public ResumeAnalysis getAnalysisById(Long id) {
        return resumeAnalysisRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Resume analysis not found with id: " + id));
    }
}