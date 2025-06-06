package com.infy.skillbuilder.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.skillbuilder.dto.ReviewsDTO;
import com.infy.skillbuilder.entity.Appointments;
import com.infy.skillbuilder.entity.Mentor;
import com.infy.skillbuilder.entity.Reviews;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.repository.AppointmentRepository;
import com.infy.skillbuilder.repository.MentorRepository;
import com.infy.skillbuilder.repository.ReviewsRepository;

import jakarta.transaction.Transactional;

@Service("reviewService")
@Transactional
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewsRepository reviewsRepository;
    @Autowired
    private MentorRepository mentorRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private MentorService mentorService;

    private String reviewNotFound = "Service.REVIEW_NOT_FOUND";
    private ModelMapper mapper = new ModelMapper();

    @Override
    public Integer registerNewReview(Integer appointmentId, ReviewsDTO review) throws SkillBuilderException {
        Reviews r = reviewsRepository.save(mapper.map(review, Reviews.class));
        r.setCreatedAt(LocalDateTime.now());
        Appointments app = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new SkillBuilderException(reviewNotFound));
        if (app.getReview() != null) {
            throw new SkillBuilderException("Service.REVIEW_ALREADY_GIVEN");
        }
        app.setReview(r);
        app.getMentor().getReviews().add(r);
        app.getMentee().getReviews().add(r);
        mentorService.updateRating(app.getMentor().getMentorId(), review.getRating());
        return r.getReviewId();
    }

    @Override
    public void updateReview(Integer id, Integer rating, String desc) throws SkillBuilderException {
        Reviews review = reviewsRepository.findById(id).orElseThrow(() -> new SkillBuilderException(reviewNotFound));
        Mentor mentor = mentorRepository.findById(review.getMentorId())
                .orElseThrow(() -> new SkillBuilderException("reviewNotFound"));
        if (review.getCreatedAt().until(LocalDateTime.now(), ChronoUnit.HOURS) <= 48) {
            mentor.setRating(mentor.getRating() + rating - review.getRating());
            review.setRating(rating);
            review.setReviewDescription(desc);
        } else {
            throw new SkillBuilderException("Service.REVIEW_EDIT_NOT_ALLOWED");
        }
    }

    @Override
    public ReviewsDTO getReview(Integer id) throws SkillBuilderException {
        return mapper.map(reviewsRepository.findById(id).orElseThrow(() -> new SkillBuilderException(reviewNotFound)),
                ReviewsDTO.class);
    }

    @Override
    public List<ReviewsDTO> getReviewByMentor(Integer mentorId) throws SkillBuilderException {
        return mapper.map(reviewsRepository.findByMentorIdOrderByCreatedAtDesc(mentorId),
                new TypeToken<List<ReviewsDTO>>() {
                }.getType());
    }

    @Override
    public List<ReviewsDTO> getReviewByMentee(Integer menteeId) throws SkillBuilderException {
        return mapper.map(reviewsRepository.findByMenteeIdOrderByCreatedAtDesc(menteeId),
                new TypeToken<List<ReviewsDTO>>() {
                }.getType());
    }

    @Override
    public void giveResponse(Integer reviewId, String response) throws SkillBuilderException {
        Reviews review = reviewsRepository.findById(reviewId)
                .orElseThrow(() -> new SkillBuilderException(reviewNotFound));
        review.setResponse(response);
        review.setResponseCreatedAt(LocalDateTime.now());
    }

    @Override
    public void updateResponse(Integer reviewId, String response) throws SkillBuilderException {
        Reviews review = reviewsRepository.findById(reviewId)
                .orElseThrow(() -> new SkillBuilderException(reviewNotFound));
        if (review.getResponseCreatedAt() != null
                && review.getResponseCreatedAt().until(LocalDateTime.now(), ChronoUnit.HOURS) <= 48) {
            review.setResponse(response);
        } else {
            throw new SkillBuilderException("Service.RESPONSE_EDIT_NOT_ALLOWED");
        }
    }
}