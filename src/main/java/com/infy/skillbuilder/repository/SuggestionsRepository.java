package com.infy.skillbuilder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.infy.skillbuilder.dto.UserType;
import com.infy.skillbuilder.entity.Suggestions;

public interface SuggestionsRepository extends JpaRepository<Suggestions, Integer> {
    @Query("SELECT s from Suggestions s ORDER BY createdAt DESC")
    List<Suggestions> getPopularSuggestions();

    List<Suggestions> findByUserIdAndUserTypeOrderByCreatedAtDesc(Integer id, UserType user);
}