package com.infy.skillbuilder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infy.skillbuilder.dto.UserType;
import com.infy.skillbuilder.entity.Notifications;

public interface NotificationsRepository extends JpaRepository<Notifications, Integer> {
    List<Notifications> findByReceiverIdAndReceiverTypeOrderByCreatedAtDesc(Integer id, UserType receiverType);
}