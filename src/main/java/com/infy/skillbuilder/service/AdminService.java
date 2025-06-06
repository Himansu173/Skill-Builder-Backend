package com.infy.skillbuilder.service;

import org.springframework.web.multipart.MultipartFile;

import com.infy.skillbuilder.dto.AdminDTO;
import com.infy.skillbuilder.exception.SkillBuilderException;

public interface AdminService {
    Integer loginAdmin(String email, String password) throws SkillBuilderException;

    AdminDTO getAdmin(Integer id) throws SkillBuilderException;

    void uploadProfiePicture(Integer id, MultipartFile file) throws SkillBuilderException;

    void updatePersonalDetails(Integer id, AdminDTO adminDTO) throws SkillBuilderException;

    void updatePassword(Integer id, String oldPass, String newPass) throws SkillBuilderException;

    String getAdminName(Integer adminId) throws SkillBuilderException;
}