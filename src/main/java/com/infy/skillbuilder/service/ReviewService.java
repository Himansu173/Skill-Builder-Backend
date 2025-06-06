package com.infy.skillbuilder.service;

import java.util.List;

import com.infy.skillbuilder.dto.ReviewsDTO;
import com.infy.skillbuilder.exception.SkillBuilderException;

public interface ReviewService {
    Integer registerNewReview(Integer appointmentId, ReviewsDTO review) throws SkillBuilderException;

    void updateReview(Integer reviewId, Integer rating, String desc) throws SkillBuilderException;

    ReviewsDTO getReview(Integer id) throws SkillBuilderException;

    List<ReviewsDTO> getReviewByMentor(Integer mentorId) throws SkillBuilderException;

    List<ReviewsDTO> getReviewByMentee(Integer menteeId) throws SkillBuilderException;

    void giveResponse(Integer reviewId, String response) throws SkillBuilderException;

    void updateResponse(Integer reviewId, String response) throws SkillBuilderException;
}