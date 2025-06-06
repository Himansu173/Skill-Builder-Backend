package com.infy.skillbuilder.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.infy.skillbuilder.dto.DesiredSkillsDTO;
import com.infy.skillbuilder.dto.FavouriteMentorsDTO;
import com.infy.skillbuilder.dto.MenteeDTO;
import com.infy.skillbuilder.dto.MenteeIndustryInterestsDTO;
import com.infy.skillbuilder.entity.DesiredSkills;
import com.infy.skillbuilder.entity.FavouriteMentors;
import com.infy.skillbuilder.entity.Mentee;
import com.infy.skillbuilder.entity.MenteeIndustryInterests;
import com.infy.skillbuilder.entity.Mentor;
import com.infy.skillbuilder.entity.Skills;
import com.infy.skillbuilder.entity.Specializations;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.repository.MenteeRepository;
import com.infy.skillbuilder.repository.MentorRepository;
import com.infy.skillbuilder.repository.SkillsRepository;
import com.infy.skillbuilder.repository.SpecializationsRepository;

import jakarta.transaction.Transactional;

@Service("menteeService")
@Transactional
public class MenteeServiceImpl implements MenteeService {
    @Autowired
    private MenteeRepository menteeRepository;
    @Autowired
    private SpecializationsRepository specializationsRepository;
    @Autowired
    private SkillsRepository skillsRepository;
    @Autowired
    MentorRepository mentorRepository;
    private String userNotFound = "Service.USER_NOT_FOUND";
    ModelMapper mapper = new ModelMapper();

    @Override
    public Integer registerMentee(MenteeDTO menteeDTO) throws SkillBuilderException {
        Mentee mentee = menteeRepository.findByEmail(menteeDTO.getEmail());
        if (mentee != null) {
            throw new SkillBuilderException("Service.USER_Email_ALREADY_PRESENT");
        }
        return menteeRepository.save(mapper.map(menteeDTO, Mentee.class)).getMenteeId();
    }

    @Override
    public Integer loginMentee(String email, String password) throws SkillBuilderException {
        Mentee mentee = menteeRepository.findByEmail(email);
        if (mentee == null || !mentee.getPassword().equals(password)) {
            throw new SkillBuilderException("Service.USER_INVALID_CREDENTIALS");
        }
        return mentee.getMenteeId();
    }

    @Override
    public MenteeDTO getMentee(Integer id) throws SkillBuilderException {
        Mentee mentee = menteeRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
        mentee.setFavouriteMentors(null);
        return mapper.map(mentee, MenteeDTO.class);
    }

    @Override
    public void updatePersonalDetails(Integer id, MenteeDTO menteeDTO) throws SkillBuilderException {
        Mentee mentee = menteeRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
        mentee.setName(menteeDTO.getName());
        mentee.setEmail(menteeDTO.getEmail());
        mentee.setAge(menteeDTO.getAge());
        mentee.setCareerObjective(menteeDTO.getCareerObjective());
    }

    @Override
    public void updateDesiredSkillsDetails(Integer id, List<DesiredSkillsDTO> desiredSkillsDTO)
            throws SkillBuilderException {
        Mentee mentee = menteeRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
        mentee.getDesiredSkills().clear();
        if (desiredSkillsDTO != null && !desiredSkillsDTO.isEmpty()) {
            desiredSkillsDTO.stream()
                    .forEach(s -> {
                        Skills skill = skillsRepository.findById(s.getSkill().getSkillId()).get();
                        DesiredSkills desiredSkills = new DesiredSkills();
                        desiredSkills.setSkill(skill);
                        mentee.getDesiredSkills().add(desiredSkills);
                    });
        }
    }

    @Override
    public void updateIndustryInterestsDetails(Integer id, List<MenteeIndustryInterestsDTO> menteeIndustryInterestsDTO)
            throws SkillBuilderException {
        Mentee mentee = menteeRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
        mentee.getIndustryInterests().clear();
        if (menteeIndustryInterestsDTO != null && !menteeIndustryInterestsDTO.isEmpty()) {
            menteeIndustryInterestsDTO.stream()
                    .forEach(s -> {
                        Specializations specializations = specializationsRepository
                                .findById(s.getIndustryInterests().getSpecializationId()).get();
                        MenteeIndustryInterests menteeIndustryInterests = new MenteeIndustryInterests();
                        menteeIndustryInterests.setIndustryInterests(specializations);
                        mentee.getIndustryInterests().add(menteeIndustryInterests);
                    });
        }
    }

    @Override
    public void updatePassword(Integer id, String oldPass, String newPass) throws SkillBuilderException {
        Mentee mentee = menteeRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
        if (mentee.getPassword().equals(oldPass)) {
            mentee.setPassword(newPass);
        } else {
            throw new SkillBuilderException("Service.INVALID_PASSWORD");
        }
    }

    @Override
    public void addFavouriteMentor(Integer menteeId, Integer mentorId) throws SkillBuilderException {
        Mentee mentee = menteeRepository.findById(menteeId).orElseThrow(() -> new SkillBuilderException(userNotFound));
        Mentor mentor = mentorRepository.findById(mentorId).orElseThrow(() -> new SkillBuilderException(userNotFound));
        FavouriteMentors favouriteMentors = new FavouriteMentors();
        favouriteMentors.setMentor(mentor);
        mentee.getFavouriteMentors().add(favouriteMentors);
    }

    @Override
    public void removeFavouriteMentor(Integer menteeId, Integer mentorId) throws SkillBuilderException {
        Mentee mentee = menteeRepository.findById(menteeId).orElseThrow(() -> new SkillBuilderException(userNotFound));
        mentee.setFavouriteMentors(mentee.getFavouriteMentors().stream()
                .filter(m -> !m.getMentor().getMentorId().equals(mentorId)).toList());
    }

    @Override
    public void uploadProfiePicture(Integer id, MultipartFile file) throws SkillBuilderException {
        Mentee mentee = menteeRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
        final String UPLOAD_DIR = "uploads/menteeImages/";
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
                fileName = "mentee_" + id + extension;
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new SkillBuilderException("Service.FAILED_TO_UPLOAD_IMAGE");
            }
            mentee.setProfilePic(
                    "http://localhost:8080/skillbuilder/images/menteeImages/" + fileName + "?t=" + LocalDateTime.now());
        }
    }

    @Override
    public List<FavouriteMentorsDTO> getAllFavouriteMentor(Integer menteeId) throws SkillBuilderException {
        Mentee mentee = menteeRepository.findById(menteeId).orElseThrow(() -> new SkillBuilderException(userNotFound));
        return mapper.map(mentee.getFavouriteMentors(), new TypeToken<List<FavouriteMentorsDTO>>() {
        }.getType());
    }

    @Override
    public String getMenteeName(Integer menteeId) throws SkillBuilderException {
        return menteeRepository.getName(menteeId);
    }
}