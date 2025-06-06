package com.infy.skillbuilder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.skillbuilder.entity.Skills;

public interface SkillsRepository extends JpaRepository<Skills, Integer> {
    Skills findBySkillNameIgnoreCase(String skillName);
}