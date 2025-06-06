package com.infy.skillbuilder.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.infy.skillbuilder.dto.AdminDTO;
import com.infy.skillbuilder.entity.Admin;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.repository.AdminRepository;

import jakarta.transaction.Transactional;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    private ModelMapper mapper = new ModelMapper();

    private String userNotFound = "Service.USER_NOT_FOUND";

    @Override
    public Integer loginAdmin(String email, String password) throws SkillBuilderException {
        Admin mentee = adminRepository.findByEmail(email);
        if (mentee == null || !mentee.getPassword().equals(password)) {
            throw new SkillBuilderException("Service.USER_INVALID_CREDENTIALS");
        }
        return mentee.getAdminId();
    }

    @Override
    public AdminDTO getAdmin(Integer id) throws SkillBuilderException {
        return mapper.map(adminRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound)),
                AdminDTO.class);
    }

    @Override
    public void uploadProfiePicture(Integer id, MultipartFile file) throws SkillBuilderException {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
        final String UPLOAD_DIR = "uploads/adminImages/";
        String fileName = null;

        if (file != null && !file.isEmpty()) {
            try {
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String[] extensions = { ".jpg", ".jpeg", ".png", ".webp" };
                for (String ext : extensions) {
                    String existingFile = "mentee_" + id + ext;
                    Path existingPath = Paths.get(UPLOAD_DIR, existingFile);
                    Files.deleteIfExists(existingPath);
                }

                String originalFilename = file.getOriginalFilename();
                String extension = "";

                if (originalFilename != null && originalFilename.contains(".")) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                fileName = "admin_" + id + extension;
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new SkillBuilderException("Service.FAILED_TO_UPLOAD_IMAGE");
            }
            admin.setProfilePic(
                    "http://localhost:8080/skillbuilder/images/adminImages/" + fileName + "?t=" + LocalDateTime.now());
        }
    }

    @Override
    public void updatePersonalDetails(Integer id, AdminDTO adminDTO) throws SkillBuilderException {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
        admin.setName(adminDTO.getName());
        admin.setEmail(adminDTO.getEmail());
    }

    @Override
    public void updatePassword(Integer id, String oldPass, String newPass) throws SkillBuilderException {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
        if (admin.getPassword().equals(oldPass)) {
            admin.setPassword(newPass);
        } else {
            throw new SkillBuilderException("Service.INVALID_PASSWORD");
        }

    }

    @Override
    public String getAdminName(Integer adminId) throws SkillBuilderException {
        return adminRepository.getName(adminId);
    }
}