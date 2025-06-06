package com.infy.skillbuilder.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="mentee")
public class Mentee {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer menteeId;
    private String name;
    private Integer age;
    private String email;
    private String profilePic;
    private String careerObjective;
    private String password;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="mentee_id")
    private List<DesiredSkills> desiredSkills;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="mentee_id")
    private List<MenteeIndustryInterests> industryInterests;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="mentee_id")
    private List<FavouriteMentors> favouriteMentors;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="mentee_id")
    private List<Reviews> reviews;
}