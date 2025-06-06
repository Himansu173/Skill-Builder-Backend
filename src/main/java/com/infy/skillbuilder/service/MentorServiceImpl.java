package com.infy.skillbuilder.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.infy.skillbuilder.dto.MentorAvailabilityDTO;
import com.infy.skillbuilder.dto.MentorDTO;
import com.infy.skillbuilder.dto.MentorSkillsDTO;
import com.infy.skillbuilder.dto.WorkExperiencesDTO;
import com.infy.skillbuilder.entity.Mentor;
import com.infy.skillbuilder.entity.MentorAvailability;
import com.infy.skillbuilder.entity.MentorSkills;
import com.infy.skillbuilder.entity.Skills;
import com.infy.skillbuilder.entity.WorkExperiences;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.repository.MentorRepository;
import com.infy.skillbuilder.repository.SkillsRepository;
import com.infy.skillbuilder.repository.SpecializationsRepository;

import jakarta.transaction.Transactional;

@Service("mentorService")
@Transactional
public class MentorServiceImpl implements MentorService {

    @Autowired
    private MentorRepository mentorRepository;
    @Autowired
    private SpecializationsRepository specializationsRepository;
    @Autowired
    private SkillsRepository skillsRepository;
    ModelMapper mapper = new ModelMapper();
    String userNotFound = "Service.USER_NOT_FOUND";

    @Override
    public Integer registerMentor(MentorDTO mentorDTO) throws SkillBuilderException {
        Mentor mentor = mentorRepository.findByEmail(mentorDTO.getEmail());
        if (mentor != null) {
            throw new SkillBuilderException("Service.USER_Email_ALREADY_PRESENT");
        }
        mentor = mapper.map(mentorDTO, Mentor.class);
        return mentorRepository.save(mentor).getMentorId();
    }

    @Override
    public Integer loginMentor(String email, String password) throws SkillBuilderException {
        Mentor mentor = mentorRepository.findByEmail(email);
        if (mentor == null || !mentor.getPassword().equals(password)) {
            throw new SkillBuilderException("Service.USER_INVALID_CREDENTIALS");
        }
        return mentor.getMentorId();
    }

    @Override
    public MentorDTO getMentor(Integer id) throws SkillBuilderException {
        return mapper.map(mentorRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound)),
                MentorDTO.class);
    }

    @Override
    public void updatePersonalDetails(Integer id, MentorDTO mentorDTO) throws SkillBuilderException {
        Mentor mentor = mentorRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
        mentor.setName(mentorDTO.getName());
        mentor.setEmail(mentorDTO.getEmail());
        mentor.setAge(mentorDTO.getAge());
        mentor.setBio(mentorDTO.getBio());
        mentor.setEducation(mentorDTO.getEducation());
        mentor.setSpecialization(specializationsRepository.findById(mentorDTO.getSpecialization().getSpecializationId())
                .orElseThrow(() -> new SkillBuilderException("Service.SPECIALIZATION_NOT_FOUND")));
    }

    @Override
    public void updateSkillDetails(Integer id, List<MentorSkillsDTO> mentorSkillsDTO) throws SkillBuilderException {
        Mentor mentor = mentorRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
        mentor.getMentorSkills().clear();
        if (mentorSkillsDTO != null && !mentorSkillsDTO.isEmpty()) {
            mentorSkillsDTO.stream()
                    .forEach(s -> {
                        if (mentor.getMentorSkills().size() == 20) {
                            throw new SkillBuilderException("Service.MENTOR_MAX_SKILLS_REACHED");
                        }
                        MentorSkills mentorSkill = new MentorSkills();
                        Skills skill = skillsRepository.findById(s.getSkill().getSkillId()).get();
                        mentorSkill.setSkill(skill);
                        mentorSkill.setSkillLevel(s.getSkillLevel());
                        mentor.getMentorSkills().add(mentorSkill);
                    });
        }
    }

    @Override
    public void updateWorkDetails(Integer id, List<WorkExperiencesDTO> workExperiencesDTO)
            throws SkillBuilderException {
        Mentor mentor = mentorRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
        mentor.getWorkExperiences().clear();
        if (workExperiencesDTO != null && !workExperiencesDTO.isEmpty()) {
            mentor.setWorkExperiences(mapper.map(workExperiencesDTO, new TypeToken<List<WorkExperiences>>() {
            }.getType()));
        }
    }

    @Override
    public void updateAvailabilityDetails(Integer id, List<MentorAvailabilityDTO> mentorAvailabilityDTO)
            throws SkillBuilderException {
        Mentor mentor = mentorRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
        mentor.getMentorAvailability().clear();
        if (mentorAvailabilityDTO != null && !mentorAvailabilityDTO.isEmpty()) {
            List<MentorAvailability> list = mapper.map(mentorAvailabilityDTO,
                    new TypeToken<List<MentorAvailability>>() {
                    }.getType());
            mentor.setMentorAvailability(
                    list.stream().sorted((o1, o2) -> o1.getDate().compareTo(o2.getDate())).toList());
        }
    }

    @Override
    public void updatePassword(Integer id, String oldPass, String newPass) throws SkillBuilderException {
        Mentor mentor = mentorRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
        if (mentor.getPassword().equals(oldPass)) {
            mentor.setPassword(newPass);
        } else {
            throw new SkillBuilderException("Service.INVALID_PASSWORD");
        }
    }

    @Override
    public List<MentorAvailabilityDTO> getAvailability(Integer id, String view) throws SkillBuilderException {
        Mentor mentor = mentorRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
        LocalDate sDate;
        LocalDate eDate;
        if (view.equalsIgnoreCase("Daily")) {
            sDate = eDate = LocalDate.now();
        } else if (view.equalsIgnoreCase("weekly")) {
            sDate = LocalDate.now();
            eDate = LocalDate.now().plusWeeks(1);
        } else {
            sDate = LocalDate.now();
            eDate = LocalDate.now().plusMonths(1);
        }
        List<MentorAvailability> list = mentor.getMentorAvailability().stream()
                .filter(a -> a.getDate().isAfter(sDate.minusDays(1)) && a.getDate().isBefore(eDate.plusDays(1)))
                .toList();
        return mapper.map(list, new TypeToken<List<MentorAvailabilityDTO>>() {
        }.getType());
    }

    @Override
    public void updateRating(Integer id, Integer rating) throws SkillBuilderException {
        Mentor mentor = mentorRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
        mentor.setReviewCount(mentor.getReviewCount() + 1);
        mentor.setRating(mentor.getRating() + rating);
    }

    @Override
    public List<MentorDTO> filterBySpecialization(String specializationName) throws SkillBuilderException {
        return mentorRepository.findBySpecializationSpecializationName(specializationName).stream()
                .map(m -> {
                    MentorDTO mentorDTO = new MentorDTO();
                    mentorDTO = mapper.map(m, MentorDTO.class);
                    mentorDTO.setReviews(null);
                    mentorDTO.setMentorAvailability(null);
                    mentorDTO.setWorkExperiences(null);
                    return mentorDTO;
                }).toList();
    }

    @Override
    public List<MentorDTO> filterBySkillName(String skillName) throws SkillBuilderException {
        return mentorRepository.findBymentorSkillsSkillSkillName(skillName).stream()
                .map(m -> {
                    MentorDTO mentorDTO = new MentorDTO();
                    mentorDTO = mapper.map(m, MentorDTO.class);
                    mentorDTO.setReviews(null);
                    mentorDTO.setMentorAvailability(null);
                    mentorDTO.setWorkExperiences(null);
                    return mentorDTO;
                }).toList();
    }

    @Override
    public List<MentorDTO> getAllMentors() throws SkillBuilderException {
        Sort sort = Sort.by("rating").and(Sort.by("reviewCount").descending());
        return mentorRepository.findAll(sort).stream()
                .map(m -> {
                    MentorDTO mentorDTO = new MentorDTO();
                    mentorDTO = mapper.map(m, MentorDTO.class);
                    mentorDTO.setReviews(null);
                    mentorDTO.setMentorAvailability(null);
                    mentorDTO.setWorkExperiences(null);
                    return mentorDTO;
                }).toList();
    }

    @Override
    public void uploadProfiePicture(Integer id, MultipartFile file) throws SkillBuilderException {
        Mentor mentor = mentorRepository.findById(id).orElseThrow(() -> new SkillBuilderException(userNotFound));
        final String UPLOAD_DIR = "uploads/mentorImages/";
        String fileName = null;

        if (file != null && !file.isEmpty()) {
            try {
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String[] extensions = { ".jpg", ".jpeg", ".png", ".webp" };
                for (String ext : extensions) {
                    String existingFile = "mentor_" + id + ext;
                    Path existingPath = Paths.get(UPLOAD_DIR, existingFile);
                    Files.deleteIfExists(existingPath);
                }

                String originalFilename = file.getOriginalFilename();
                String extension = "";

                if (originalFilename != null && originalFilename.contains(".")) {
                    extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                fileName = "mentor_" + id + extension;
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new SkillBuilderException("Service.FAILED_TO_UPLOAD_IMAGE");
            }
            mentor.setProfilePic(
                    "http://localhost:8080/skillbuilder/images/mentorImages/" + fileName + "?t=" + LocalDateTime.now());
        }
    }

    @Override
    public String getMentorName(Integer mentorId) throws SkillBuilderException {
        return mentorRepository.getName(mentorId);
    }
}