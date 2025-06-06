package com.infy.skillbuilder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.skillbuilder.dto.NotificationsDTO;
import com.infy.skillbuilder.dto.UserType;
import com.infy.skillbuilder.exception.SkillBuilderException;
import com.infy.skillbuilder.service.NotificationService;

@RestController
@Validated
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/notificationapi")
public class NotificationAPI {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private Environment environment;

    @GetMapping("/getNotifications/{id}/{userType}")
    public ResponseEntity<List<NotificationsDTO>> getNotificationByReceiverId(@PathVariable Integer id,
            @PathVariable UserType userType) throws SkillBuilderException {
        return new ResponseEntity<>(notificationService.getNotificationByReceiverId(id, userType), HttpStatus.OK);
    }

    @DeleteMapping("/deleteNotificationById/{id}")
    public ResponseEntity<String> deleteNotificationById(@PathVariable Integer id) throws SkillBuilderException {
        notificationService.deleteNotificationById(id);
        return new ResponseEntity<>(environment.getProperty("API.NOTIFICATION_DELETED"), HttpStatus.OK);
    }

    @PutMapping("/updateIsRead/{id}")
    public ResponseEntity<String> updateIsRead(@PathVariable Integer id) throws SkillBuilderException {
        notificationService.updateIsRead(id);
        return new ResponseEntity<>(environment.getProperty("API.NOTIFICATION_UPDATED"), HttpStatus.OK);
    }
}