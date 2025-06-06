
package com.infy.skillbuilder.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.skillbuilder.dto.SpecializationsDTO;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.repository.SpecializationsRepository;

import jakarta.transaction.Transactional;

@Service("specializationsService")
@Transactional
public class SpecializationsServiceImpl implements SpecializationsService {
    @Autowired
    SpecializationsRepository specializationsRepository;

    ModelMapper mapper = new ModelMapper();

    @Override
    public List<SpecializationsDTO> getSpecializations() throws SkillBuilderException {
        org.springframework.data.domain.Sort sort = org.springframework.data.domain.Sort.by("specializationName")
                .ascending();
        return mapper.map(specializationsRepository.findAll(sort), new TypeToken<List<SpecializationsDTO>>() {
        }.getType());
    }

}