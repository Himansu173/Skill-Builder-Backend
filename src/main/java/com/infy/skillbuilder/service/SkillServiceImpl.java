package com.infy.skillbuilder.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.skillbuilder.dto.SkillsDTO;
import com.infy.skillbuilder.entity.Skills;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.repository.SkillsRepository;

import jakarta.transaction.Transactional;

@Service("skillService")
@Transactional
public class SkillServiceImpl implements SkillService {
    @Autowired
    SkillsRepository skillsRepository;

    ModelMapper mapper = new ModelMapper();

    @Override
    public Integer registerNewSkill(String skillName) throws SkillBuilderException {
        Skills skill = skillsRepository.findBySkillNameIgnoreCase(skillName);
        if (skill != null) {
            throw new SkillBuilderException("Service.SKILL_ALREADY_PRESENT");
        }
        skill = new Skills();
        skill.setSkillName(skillName);
        return skillsRepository.save(skill).getSkillId();
    }

    @Override
    public List<SkillsDTO> getSkills() throws SkillBuilderException {
        org.springframework.data.domain.Sort sort = org.springframework.data.domain.Sort.by("skillName").ascending();
        return mapper.map(skillsRepository.findAll(sort), new TypeToken<List<SkillsDTO>>() {
        }.getType());
    }

}