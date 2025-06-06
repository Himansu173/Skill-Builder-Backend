package com.infy.skillbuilder.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.infy.skillbuilder.dto.DesiredSkillsDTO;
import com.infy.skillbuilder.dto.FavouriteMentorsDTO;
import com.infy.skillbuilder.dto.MenteeDTO;
import com.infy.skillbuilder.dto.MenteeIndustryInterestsDTO;
import com.infy.skillbuilder.exception.SkillBuilderException;

public interface MenteeService {
    Integer registerMentee(MenteeDTO menteeDTO) throws SkillBuilderException;

    Integer loginMentee(String email, String password) throws SkillBuilderException;

    MenteeDTO getMentee(Integer id) throws SkillBuilderException;

    void uploadProfiePicture(Integer id, MultipartFile file) throws SkillBuilderException;

    void updatePersonalDetails(Integer id, MenteeDTO menteeDTO) throws SkillBuilderException;

    void updateDesiredSkillsDetails(Integer id, List<DesiredSkillsDTO> desiredSkillsDTO) throws SkillBuilderException;

    void updateIndustryInterestsDetails(Integer id, List<MenteeIndustryInterestsDTO> menteeIndustryInterestsDTO)
            throws SkillBuilderException;

    void updatePassword(Integer id, String oldPass, String newPass) throws SkillBuilderException;

    void addFavouriteMentor(Integer menteeId, Integer mentorId) throws SkillBuilderException;

    void removeFavouriteMentor(Integer menteeId, Integer mentorId) throws SkillBuilderException;

    List<FavouriteMentorsDTO> getAllFavouriteMentor(Integer menteeId) throws SkillBuilderException;

    String getMenteeName(Integer menteeId) throws SkillBuilderException;
}