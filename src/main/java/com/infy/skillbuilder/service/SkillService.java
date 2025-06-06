package com.infy.skillbuilder.service;

import java.util.List;

import com.infy.skillbuilder.dto.SkillsDTO;
import com.infy.skillbuilder.exception.SkillBuilderException;

public interface SkillService {
    Integer registerNewSkill(String skillName) throws SkillBuilderException;

    List<SkillsDTO> getSkills() throws SkillBuilderException;
}