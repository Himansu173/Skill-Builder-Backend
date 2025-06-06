package com.infy.skillbuilder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "specializations")
public class Specializations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer specializationId;
    private String specializationName;
}