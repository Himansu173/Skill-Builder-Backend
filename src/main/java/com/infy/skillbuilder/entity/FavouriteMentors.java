package com.infy.skillbuilder.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "favourite_mentors")
public class FavouriteMentors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer favouriteId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;
}