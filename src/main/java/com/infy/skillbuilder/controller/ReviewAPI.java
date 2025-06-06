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

import com.infy.skillbuilder.dto.ReviewsDTO;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.service.ReviewService;

import jakarta.validation.Valid;

@RestController
@Validated
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/reviewapi")
public class ReviewAPI {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private Environment environment;

    @PostMapping("/registerNewReview/{appointmentId}")
    public ResponseEntity<String> registerNewReview(@PathVariable Integer appointmentId,
            @RequestBody @Valid ReviewsDTO review) throws SkillBuilderException {
        Integer id = reviewService.registerNewReview(appointmentId, review);
        return new ResponseEntity<>(environment.getProperty("API.REVIEW_CREATED") + id, HttpStatus.CREATED);
    }

    @PutMapping("/updateReview/{appointmentId}")
    public ResponseEntity<String> updateReview(@PathVariable Integer appointmentId, @RequestParam Integer rating,
            @RequestParam String desc) throws SkillBuilderException {
        reviewService.updateReview(appointmentId, rating, desc);
        return new ResponseEntity<>(environment.getProperty("API.REVIEW_UPDATED_SUCCESSFULLY"), HttpStatus.OK);
    }

    @GetMapping("/getReview/{id}")
    public ResponseEntity<ReviewsDTO> getReview(@PathVariable Integer id) throws SkillBuilderException {
        return new ResponseEntity<>(reviewService.getReview(id), HttpStatus.OK);
    }

    @GetMapping("/getReviewByMentor/{id}")
    public ResponseEntity<List<ReviewsDTO>> getReviewByMentor(@PathVariable Integer id) throws SkillBuilderException {
        return new ResponseEntity<>(reviewService.getReviewByMentor(id), HttpStatus.OK);
    }

    @GetMapping("/getReviewByMentee/{id}")
    public ResponseEntity<List<ReviewsDTO>> getReviewByMentee(@PathVariable Integer id) throws SkillBuilderException {
        return new ResponseEntity<>(reviewService.getReviewByMentee(id), HttpStatus.OK);
    }

    @PostMapping("/giveResponse/{id}")
    public ResponseEntity<String> giveResponse(@PathVariable Integer id, @RequestParam String response)
            throws SkillBuilderException {
        reviewService.giveResponse(id, response);
        return new ResponseEntity<>(environment.getProperty("API.RESPONSE_CREATED") + id, HttpStatus.CREATED);
    }

    @PutMapping("/updateResponse/{id}")
    public ResponseEntity<String> updateResponse(@PathVariable Integer id, @RequestParam String response)
            throws SkillBuilderException {
        reviewService.updateResponse(id, response);
        return new ResponseEntity<>(environment.getProperty("API.RESPONSE_UPDATED_SUCCESSFULLY"), HttpStatus.OK);
    }
}