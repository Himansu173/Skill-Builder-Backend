package com.infy.skillbuilder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.skillbuilder.entity.Reviews;

public interface ReviewsRepository  extends JpaRepository<Reviews, Integer> {
    List<Reviews> findByMentorIdOrderByCreatedAtDesc(Integer id);
    List<Reviews> findByMenteeIdOrderByCreatedAtDesc(Integer id);
}
