package com.infy.skillbuilder.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "mentor")
public class Mentor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mentorId;
    private String name;
    private Integer age;
    private String email;
    private String profilePic;
    private Integer rating;
    private Integer reviewCount;
    private String education;
    private String bio;
    private String password;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "specialization_id")
    private Specializations specialization;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "mentor_id")
    private List<MentorSkills> mentorSkills;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "mentor_id")
    private List<WorkExperiences> workExperiences;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "mentor_id")
    private List<MentorAvailability> mentorAvailability;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "mentor_id")
    private List<Reviews> reviews;
}