package com.infy.skillbuilder.entity;

import com.infy.skillbuilder.dto.SkillLevel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "mentor_skills")
public class MentorSkills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mentorSkillId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "skill_id")
    private Skills skill;
    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel;
}