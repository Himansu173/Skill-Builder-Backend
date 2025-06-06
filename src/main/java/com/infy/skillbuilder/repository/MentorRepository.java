package com.infy.skillbuilder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.infy.skillbuilder.entity.Mentor;

public interface MentorRepository extends JpaRepository<Mentor, Integer> {
    Mentor findByEmail(String email);

    List<Mentor> findBySpecializationSpecializationName(String specializationName);

    List<Mentor> findBymentorSkillsSkillSkillName(String skillName);

    @Query("SELECT m.name FROM Mentor m WHERE m.mentorId=:id")
    String getName(@Param("id") Integer id);
}