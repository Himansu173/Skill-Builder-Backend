package com.infy.skillbuilder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.skillbuilder.entity.DesiredSkills;

public interface DesiredSkillsRepository extends JpaRepository<DesiredSkills, Integer> {

}