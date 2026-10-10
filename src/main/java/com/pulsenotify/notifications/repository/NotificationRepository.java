package com.pulsenotify.notifications.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pulsenotify.notifications.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

}
