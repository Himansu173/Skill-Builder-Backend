package com.infy.skillbuilder.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.skillbuilder.dto.NotificationsDTO;
import com.infy.skillbuilder.dto.UserType;
import com.infy.skillbuilder.entity.Notifications;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.repository.NotificationsRepository;

import jakarta.transaction.Transactional;

@Service("notificationService")
@Transactional
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationsRepository notificationsRepository;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public List<NotificationsDTO> getNotificationByReceiverId(Integer id, UserType receiverType)
            throws SkillBuilderException {
        return mapper.map(notificationsRepository.findByReceiverIdAndReceiverTypeOrderByCreatedAtDesc(id, receiverType),
                new TypeToken<List<NotificationsDTO>>() {
                }.getType());
    }

    @Override
    public void deleteNotificationById(Integer id) throws SkillBuilderException {
        Notifications notification = notificationsRepository.findById(id)
                .orElseThrow(() -> new SkillBuilderException("Service.NOTIFICATION_NOT_FOUND"));
        notificationsRepository.delete(notification);
    }

    @Override
    public void updateIsRead(Integer id) throws SkillBuilderException {
        Notifications notification = notificationsRepository.findById(id)
                .orElseThrow(() -> new SkillBuilderException("Service.NOTIFICATION_NOT_FOUND"));
        notification.setIsRead(true);
    }
}