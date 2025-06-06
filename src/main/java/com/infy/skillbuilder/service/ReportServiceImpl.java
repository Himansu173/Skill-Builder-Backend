package com.infy.skillbuilder.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.infy.skillbuilder.dto.ReportAndSuggestionStatus;
import com.infy.skillbuilder.dto.ReportsDTO;
import com.infy.skillbuilder.dto.UserType;
import com.infy.skillbuilder.entity.Notifications;
import com.infy.skillbuilder.entity.Reports;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.repository.NotificationsRepository;
import com.infy.skillbuilder.repository.ReportsRepository;

import jakarta.transaction.Transactional;

@Service("reportService")
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportsRepository reportsRepository;
    @Autowired
    private NotificationsRepository notificationsRepository;

    ModelMapper mapper = new ModelMapper();

    @Override
    public Integer registerNewReport(ReportsDTO reportsDTO) throws SkillBuilderException {
        Reports report = mapper.map(reportsDTO, Reports.class);
        report.setStatus(ReportAndSuggestionStatus.PENDING);
        report.setCreatedAt(LocalDateTime.now());
        return reportsRepository.save(report).getReportId();
    }

    @Override
    public void updateReportStatus(Integer repostId, ReportAndSuggestionStatus status) throws SkillBuilderException {
        Reports report = reportsRepository.findById(repostId)
                .orElseThrow(() -> new SkillBuilderException("Service.REPORT_NOT_FOUND"));
        report.setStatus(status);

        Notifications notification = new Notifications();
        notification.setSenderId(10001);
        notification.setSenderType(UserType.ADMIN);
        notification.setReceiverId(report.getUserId());
        notification.setReceiverType(report.getUserType());
        notification.setMessage(
                "The current status of your Report on " + report.getCategoryOfViolation() + " is " + status);
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notificationsRepository.save(notification);
    }

    @Override
    public List<ReportsDTO> getAllReports() throws SkillBuilderException {
        Sort sort = Sort.by("createdAt").descending();
        return mapper.map(reportsRepository.findAll(sort), new TypeToken<List<ReportsDTO>>() {
        }.getType());
    }

    @Override
    public List<ReportsDTO> findByUserIdAndUserType(Integer id, UserType user) throws SkillBuilderException {
        return mapper.map(reportsRepository.findByUserIdAndUserTypeOrderByCreatedAtDesc(id, user),
                new TypeToken<List<ReportsDTO>>() {
                }.getType());
    }
}