package com.pulsenotify;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pulsenotify.notifications.Enum.Channel;
import com.pulsenotify.notifications.Enum.Status;
import com.pulsenotify.notifications.entity.Notification;
import com.pulsenotify.notifications.repository.NotificationRepository;

@SpringBootTest
public class NotificationTest {
	@Autowired
	private NotificationRepository repo;
	
	@Test
	void saveNotificationWithTimeStamp() {
		Notification notification = new Notification();
		notification.setMessage("Your order #123 has shipped");
		notification.setReceiver("customer@gmail.com");
		notification.setChannel(Channel.EMAIL);
		notification.setStatus(Status.PENDING);
		Notification saved  = repo.save(notification);
		assertNotNull(saved.getId());
		assertNotNull(saved.getCreatedAt());
		assertNotNull(saved.getUpdatedAt());

		assertEquals("Your order #123 has shipped", saved.getMessage());
		assertEquals("customer@gmail.com", saved.getReceiver());
		assertEquals(Channel.EMAIL, saved.getChannel());
		assertEquals(Status.PENDING, saved.getStatus());
		
		
	}
}
