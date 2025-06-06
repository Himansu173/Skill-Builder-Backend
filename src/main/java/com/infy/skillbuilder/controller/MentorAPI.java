package com.infy.skillbuilder.controller;

import org.springframework.core.env.Environment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.infy.skillbuilder.dto.MentorAvailabilityDTO;
import com.infy.skillbuilder.dto.MentorDTO;
import com.infy.skillbuilder.dto.MentorSkillsDTO;
import com.infy.skillbuilder.dto.WorkExperiencesDTO;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.service.MentorService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@RestController
@Validated
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/mentorapi")
public class MentorAPI {
    @Autowired
    private MentorService mentorService;
    @Autowired
    private Environment environment;

    @PostMapping("/register")
    public ResponseEntity<String> registerMentor(@Valid @RequestBody MentorDTO mentorDTO) throws SkillBuilderException {
        Integer id = mentorService.registerMentor(mentorDTO);
        return new ResponseEntity<>(environment.getProperty("API.USER_REGISTRATION_SUCCESS") + id, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<Integer> loginMentor(
            @RequestParam @Pattern(regexp = "[A-Za-z].*@[A-Za-z]{4,}.[A-Za-z]{2,}", message = "{user.email.invalid}") String email,
            @RequestParam String password) throws SkillBuilderException {
        Integer id = mentorService.loginMentor(email, password);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/getMentor/{id}")
    public ResponseEntity<MentorDTO> getMentor(@PathVariable Integer id) throws SkillBuilderException {
        MentorDTO mentor = mentorService.getMentor(id);
        return new ResponseEntity<>(mentor, HttpStatus.OK);
    }

    @PostMapping("/uploadProfiePicture/{id}")
    public ResponseEntity<String> uploadProfiePicture(@PathVariable Integer id,
            @RequestParam("profilePic") MultipartFile profilePic) throws SkillBuilderException {
        mentorService.uploadProfiePicture(id, profilePic);
        return new ResponseEntity<>(environment.getProperty("API.USER_PICTURE_UPDATED"), HttpStatus.OK);
    }

    @PutMapping("/updatePersonalDetails/{id}")
    public ResponseEntity<String> updateMentorDetails(@PathVariable Integer id, @RequestBody MentorDTO mentorDTO)
            throws SkillBuilderException {
        mentorService.updatePersonalDetails(id, mentorDTO);
        return new ResponseEntity<>(environment.getProperty("API.USER_DETAILS_UPDATED"), HttpStatus.OK);
    }

    @PutMapping("/updateSkillDetails/{id}")
    public ResponseEntity<String> updateSkillDetails(@PathVariable Integer id,
            @RequestBody @Valid List<MentorSkillsDTO> mentorSkillsDTO) throws SkillBuilderException {
        mentorService.updateSkillDetails(id, mentorSkillsDTO);
        return new ResponseEntity<>(environment.getProperty("API.MENTOR_SKILLS_UPDATED"), HttpStatus.OK);
    }

    @PutMapping("/updateWorkDetails/{id}")
    public ResponseEntity<String> updateWorkDetails(@PathVariable Integer id,
            @RequestBody @Valid List<WorkExperiencesDTO> workExperiencesDTO) throws SkillBuilderException {
        mentorService.updateWorkDetails(id, workExperiencesDTO);
        return new ResponseEntity<>(environment.getProperty("API.MENTOR_WORK_UPDATED"), HttpStatus.OK);
    }

    @PutMapping("/updateAvailabilityDetails/{id}")
    public ResponseEntity<String> updateAvailabilityDetails(@PathVariable Integer id,
            @RequestBody @Valid List<MentorAvailabilityDTO> mentorAvailabilityDTO) throws SkillBuilderException {
        mentorService.updateAvailabilityDetails(id, mentorAvailabilityDTO);
        return new ResponseEntity<>(environment.getProperty("API.MENTOR_AVAILABILITY_UPDATED"), HttpStatus.OK);
    }

    @PutMapping("/updatePassword/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable Integer id, @RequestParam String oldpass,
            @RequestParam @NotNull(message = "{user.password.notpresent}") @Pattern(regexp = "(?=.*[A-Z])(?=.*[a-z])(?=.*[\\d])(?=.*[^A-Za-z\\d])([A-Za-z\\d[^A-Za-z\\d]]{8,})", message = "{user.password.invalid}") String newpass)
            throws SkillBuilderException {
        mentorService.updatePassword(id, oldpass, newpass);
        return new ResponseEntity<>(environment.getProperty("API.USER_PASSWORD_UPDATED"), HttpStatus.OK);
    }

    @GetMapping("/getAvailability/{id}/{view}")
    public ResponseEntity<List<MentorAvailabilityDTO>> getAvailability(@PathVariable Integer id,
            @PathVariable String view) throws SkillBuilderException {
        return new ResponseEntity<>(mentorService.getAvailability(id, view), HttpStatus.OK);
    }

    @GetMapping("/filterBySpecialization/{specializationName}")
    public ResponseEntity<List<MentorDTO>> filterBySpecialization(@PathVariable String specializationName)
            throws SkillBuilderException {
        return new ResponseEntity<>(mentorService.filterBySpecialization(specializationName), HttpStatus.OK);
    }

    @GetMapping("/filterBySkill/{skilName}")
    public ResponseEntity<List<MentorDTO>> filterBySkill(@PathVariable String skilName) throws SkillBuilderException {
        return new ResponseEntity<>(mentorService.filterBySkillName(skilName), HttpStatus.OK);
    }

    @GetMapping("/getAllMentors")
    public ResponseEntity<List<MentorDTO>> getAllMentors() throws SkillBuilderException {
        return new ResponseEntity<>(mentorService.getAllMentors(), HttpStatus.OK);
    }

    @GetMapping("/getName/{mentorId}")
    public ResponseEntity<String> getMentorName(@PathVariable Integer mentorId) throws SkillBuilderException {
        return new ResponseEntity<>(mentorService.getMentorName(mentorId), HttpStatus.OK);
    }
}