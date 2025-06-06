package com.infy.skillbuilder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.skillbuilder.dto.SkillsDTO;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.service.SkillService;

import jakarta.validation.constraints.NotNull;

@RestController
@Validated
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/skillapi")
public class SkillAPI {
    @Autowired
    SkillService skillService;

    @PostMapping("/register/{skillName}")
    public ResponseEntity<Integer> registerNewSkill(
            @PathVariable @NotNull(message = "{skill.name.notpresent}") String skillName) throws SkillBuilderException {
        Integer id = skillService.registerNewSkill(skillName);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/getSkills")
    public ResponseEntity<List<SkillsDTO>> getAllSkills() throws SkillBuilderException {
        return new ResponseEntity<>(skillService.getSkills(), HttpStatus.OK);
    }
}