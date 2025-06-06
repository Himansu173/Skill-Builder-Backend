package com.infy.skillbuilder.entity;

import java.time.LocalDateTime;

import com.infy.skillbuilder.dto.UserType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "notifications")
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;
    private Integer senderId;
    @Enumerated(EnumType.STRING)
    private UserType senderType;
    private Integer receiverId;
    @Enumerated(EnumType.STRING)
    private UserType receiverType;
    private String message;
    private Boolean isRead;
    private LocalDateTime createdAt;
}