package com.infy.skillbuilder.service;

import java.util.List;

import com.infy.skillbuilder.dto.NotificationsDTO;
import com.infy.skillbuilder.dto.UserType;
import com.infy.skillbuilder.exception.SkillBuilderException;

public interface NotificationService {
    List<NotificationsDTO> getNotificationByReceiverId(Integer id, UserType receiverType) throws SkillBuilderException;

    void deleteNotificationById(Integer id) throws SkillBuilderException;

    void updateIsRead(Integer id) throws SkillBuilderException;
}