package com.infy.skillbuilder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

import com.infy.skillbuilder.dto.DesiredSkillsDTO;
import com.infy.skillbuilder.dto.FavouriteMentorsDTO;
import com.infy.skillbuilder.dto.MenteeDTO;
import com.infy.skillbuilder.dto.MenteeIndustryInterestsDTO;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.service.MenteeService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@RestController
@Validated
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/menteeapi")
public class MenteeAPI {
    @Autowired
    private MenteeService menteeService;
    @Autowired
    private Environment environment;

    @PostMapping("/register")
    public ResponseEntity<String> registerMentee(@Valid @RequestBody MenteeDTO menteeDTO) throws SkillBuilderException {
        Integer id = menteeService.registerMentee(menteeDTO);
        return new ResponseEntity<>(environment.getProperty("API.USER_REGISTRATION_SUCCESS") + id, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<Integer> loginMentee(
            @RequestParam @Pattern(regexp = "[A-Za-z].*@[A-Za-z]{4,}.[A-Za-z]{2,}", message = "{user.email.invalid}") String email,
            @RequestParam String password) throws SkillBuilderException {
        Integer id = menteeService.loginMentee(email, password);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/getMentee/{id}")
    public ResponseEntity<MenteeDTO> getMentee(@PathVariable Integer id) throws SkillBuilderException {
        MenteeDTO mentee = menteeService.getMentee(id);
        return new ResponseEntity<>(mentee, HttpStatus.OK);
    }

    @PostMapping("/uploadProfiePicture/{id}")
    public ResponseEntity<String> uploadProfiePicture(@PathVariable Integer id,
            @RequestParam("profilePic") MultipartFile profilePic) throws SkillBuilderException {
        menteeService.uploadProfiePicture(id, profilePic);
        return new ResponseEntity<>(environment.getProperty("API.USER_PICTURE_UPDATED"), HttpStatus.OK);
    }

    @PutMapping("/updatePersonalDetails/{id}")
    public ResponseEntity<String> updatePersonalDetails(@PathVariable Integer id, @RequestBody MenteeDTO menteeDTO)
            throws SkillBuilderException {
        menteeService.updatePersonalDetails(id, menteeDTO);
        return new ResponseEntity<>(environment.getProperty("API.USER_DETAILS_UPDATED"), HttpStatus.OK);
    }

    @PutMapping("/updateDesiredSkillsDetails/{id}")
    public ResponseEntity<String> updateDesiredSkillsDetails(@PathVariable Integer id,
            @RequestBody @Valid List<DesiredSkillsDTO> desiredSkillsDTO) throws SkillBuilderException {
        menteeService.updateDesiredSkillsDetails(id, desiredSkillsDTO);
        return new ResponseEntity<>(environment.getProperty("API.MENTEE_DESIRED_KILLS_UPDATED"), HttpStatus.OK);
    }

    @PutMapping("/updateIndustryInterestsDetails/{id}")
    public ResponseEntity<String> updateIndustryInterestsDetails(@PathVariable Integer id,
            @RequestBody @Valid List<MenteeIndustryInterestsDTO> menteeIndustryInterestsDTO)
            throws SkillBuilderException {
        menteeService.updateIndustryInterestsDetails(id, menteeIndustryInterestsDTO);
        return new ResponseEntity<>(environment.getProperty("API.MENTEE_INDUSTRY_INTEREST_UPDATED"), HttpStatus.OK);
    }

    @PutMapping("/updatePassword/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable Integer id, @RequestParam String oldpass,
            @RequestParam @NotNull(message = "{user.password.notpresent}") @Pattern(regexp = "(?=.*[A-Z])(?=.*[a-z])(?=.*[\\d])(?=.*[^A-Za-z\\d])([A-Za-z\\d[^A-Za-z\\d]]{8,})", message = "{user.password.invalid}") String newpass)
            throws SkillBuilderException {
        menteeService.updatePassword(id, oldpass, newpass);
        return new ResponseEntity<>(environment.getProperty("API.USER_PASSWORD_UPDATED"), HttpStatus.OK);
    }

    @PutMapping("/addFavouriteMentor/{menteeId}/{mentorId}")
    public ResponseEntity<String> addFavouriteMentor(@PathVariable Integer menteeId, @PathVariable Integer mentorId)
            throws SkillBuilderException {
        menteeService.addFavouriteMentor(menteeId, mentorId);
        return new ResponseEntity<>(environment.getProperty("API.MENTEE_FAVOURITE_MENTOR_ADDED"), HttpStatus.OK);
    }

    @PutMapping("/removeFavouriteMentor/{menteeId}/{mentorId}")
    public ResponseEntity<String> removeFavouriteMentor(@PathVariable Integer menteeId, @PathVariable Integer mentorId)
            throws SkillBuilderException {
        menteeService.removeFavouriteMentor(menteeId, mentorId);
        return new ResponseEntity<>(environment.getProperty("API.MENTEE_FAVOURITE_MENTOR_REMOVED"), HttpStatus.OK);
    }

    @GetMapping("/getAllFavouriteMentor/{menteeId}")
    public ResponseEntity<List<FavouriteMentorsDTO>> getAllFavouriteMentor(@PathVariable Integer menteeId)
            throws SkillBuilderException {
        return new ResponseEntity<>(menteeService.getAllFavouriteMentor(menteeId), HttpStatus.OK);
    }

    @GetMapping("/getName/{menteeId}")
    public ResponseEntity<String> getMenteeName(@PathVariable Integer menteeId) throws SkillBuilderException {
        return new ResponseEntity<>(menteeService.getMenteeName(menteeId), HttpStatus.OK);
    }
}