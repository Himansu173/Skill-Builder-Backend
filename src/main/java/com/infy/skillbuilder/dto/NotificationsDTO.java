package com.infy.skillbuilder.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NotificationsDTO {
    private Integer notificationId;
    private Integer senderId;
    private UserType senderType;
    private Integer receiverId;
    private UserType receiverType;
    private String message;
    private Boolean isRead;
    private LocalDateTime createdAt;
}