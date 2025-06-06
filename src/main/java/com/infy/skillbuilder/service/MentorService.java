package com.infy.skillbuilder.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.infy.skillbuilder.dto.MentorAvailabilityDTO;
import com.infy.skillbuilder.dto.MentorDTO;
import com.infy.skillbuilder.dto.MentorSkillsDTO;
import com.infy.skillbuilder.dto.WorkExperiencesDTO;
import com.infy.skillbuilder.exception.SkillBuilderException;

public interface MentorService {
    Integer registerMentor(MentorDTO mentorDTO) throws SkillBuilderException;

    Integer loginMentor(String username, String password) throws SkillBuilderException;

    MentorDTO getMentor(Integer id) throws SkillBuilderException;

    void uploadProfiePicture(Integer id, MultipartFile file) throws SkillBuilderException;

    void updatePersonalDetails(Integer id, MentorDTO mentorDTO) throws SkillBuilderException;

    void updateSkillDetails(Integer id, List<MentorSkillsDTO> mentorSkillsDTO) throws SkillBuilderException;

    void updateWorkDetails(Integer id, List<WorkExperiencesDTO> workExperiencesDTO) throws SkillBuilderException;

    void updateAvailabilityDetails(Integer id, List<MentorAvailabilityDTO> mentorAvailabilityDTO)
            throws SkillBuilderException;

    void updatePassword(Integer id, String oldPass, String newPass) throws SkillBuilderException;

    List<MentorAvailabilityDTO> getAvailability(Integer id, String view) throws SkillBuilderException;

    void updateRating(Integer id, Integer rating) throws SkillBuilderException;

    List<MentorDTO> filterBySpecialization(String specializationName) throws SkillBuilderException;

    List<MentorDTO> filterBySkillName(String skillName) throws SkillBuilderException;

    List<MentorDTO> getAllMentors() throws SkillBuilderException;

    String getMentorName(Integer mentorId) throws SkillBuilderException;
}