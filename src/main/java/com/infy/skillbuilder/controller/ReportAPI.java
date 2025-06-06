package com.infy.skillbuilder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.skillbuilder.dto.ReportAndSuggestionStatus;
import com.infy.skillbuilder.dto.ReportsDTO;
import com.infy.skillbuilder.dto.UserType;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.service.ReportService;

import jakarta.validation.Valid;

@RestController
@Validated
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/reportapi")
public class ReportAPI {
    @Autowired
    private ReportService reportService;
    @Autowired
    private Environment environment;

    @PostMapping("/register")
    public ResponseEntity<String> registerNewReport(@RequestBody @Valid ReportsDTO reportsDTO)
            throws SkillBuilderException {
        Integer id = reportService.registerNewReport(reportsDTO);
        return new ResponseEntity<>(environment.getProperty("API.REPORT_REGISTRATION_SUCCESS") + id,
                HttpStatus.CREATED);
    }

    @PutMapping("/updateReportStatus/{id}/{status}")
    public ResponseEntity<String> updateReportStatus(@PathVariable Integer id,
            @PathVariable ReportAndSuggestionStatus status) throws SkillBuilderException {
        reportService.updateReportStatus(id, status);
        return new ResponseEntity<>(environment.getProperty("API.REPORT_UPDATE_SUCCESS"), HttpStatus.OK);
    }

    @GetMapping("/getAllReports")
    public ResponseEntity<List<ReportsDTO>> getAllReports() throws SkillBuilderException {
        return new ResponseEntity<>(reportService.getAllReports(), HttpStatus.OK);
    }

    @GetMapping("/findByUserIdAndUserType/{id}/{user}")
    public ResponseEntity<List<ReportsDTO>> findByUserIdAndUserType(@PathVariable Integer id,
            @PathVariable UserType user) throws SkillBuilderException {
        return new ResponseEntity<>(reportService.findByUserIdAndUserType(id, user), HttpStatus.OK);
    }
}