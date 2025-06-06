package com.infy.skillbuilder.service;

import java.util.List;

import com.infy.skillbuilder.dto.SpecializationsDTO;
import com.infy.skillbuilder.exception.SkillBuilderException;

public interface SpecializationsService {
    List<SpecializationsDTO> getSpecializations() throws SkillBuilderException;
}