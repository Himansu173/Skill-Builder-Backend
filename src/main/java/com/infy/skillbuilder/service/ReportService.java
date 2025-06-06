package com.infy.skillbuilder.service;

import java.util.List;

import com.infy.skillbuilder.dto.ReportAndSuggestionStatus;
import com.infy.skillbuilder.dto.ReportsDTO;
import com.infy.skillbuilder.dto.UserType;
import com.infy.skillbuilder.exception.SkillBuilderException;

public interface ReportService {
    Integer registerNewReport(ReportsDTO reportsDTO) throws SkillBuilderException;

    void updateReportStatus(Integer repostId, ReportAndSuggestionStatus status) throws SkillBuilderException;

    List<ReportsDTO> getAllReports() throws SkillBuilderException;

    List<ReportsDTO> findByUserIdAndUserType(Integer id, UserType user) throws SkillBuilderException;
}