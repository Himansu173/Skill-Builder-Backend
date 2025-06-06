package com.infy.skillbuilder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.skillbuilder.dto.SpecializationsDTO;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.service.SpecializationsService;

@RestController
@Validated
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/specializationapi")
public class SpecializationAPI {
    @Autowired
    SpecializationsService specializationsService;

    @GetMapping("/getSpecialization")
    public ResponseEntity<List<SpecializationsDTO>> getSpecializations() throws SkillBuilderException {
        return new ResponseEntity<>(specializationsService.getSpecializations(), HttpStatus.OK);
    }
}