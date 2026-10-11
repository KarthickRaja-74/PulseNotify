//package com.pulsenotify.notification;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.pulsenotify.notifications.Enum.Channel;
//import com.pulsenotify.notifications.Enum.Status;
//import com.pulsenotify.notifications.entity.Notification;
//import com.pulsenotify.notifications.repository.NotificationRepository;
//
//@SpringBootTest
//public class NotificationTest {
//	@Autowired
//	private NotificationRepository repo;
//	
////	@Test
////	void saveNotificationWithTimeStamp() {
////		Notification notification = new Notification();
////		notification.setMessage("Your order #123 has shipped");
////		notification.setReceiver("customer@gmail.com");
////		notification.setChannel(Channel.EMAIL);
////		notification.setStatus(Status.PENDING);
////		Notification saved  = repo.save(notification);
////		assertNotNull(saved.getId());
////		assertNotNull(saved.getCreatedAt());
////		assertNotNull(saved.getUpdatedAt());
////
////		assertEquals("Your order #123 has shipped", saved.getMessage());
////		assertEquals("customer@gmail.com", saved.getReceiver());
////		assertEquals(Channel.EMAIL, saved.getChannel());
////		assertEquals(Status.PENDING, saved.getStatus());
////		
////		
////	}
//	
//	 @Test
//	    void shouldAllowPendingToProcessing() {
//	        Notification notification = new Notification();
//	        
//
//	        notification.transitionTo(Status.PROCESSING);
//
//	        assertEquals(Status.PROCESSING, notification.getStatus());
//	    }
//
//	    @Test
//	    void shouldRejectPendingToSent() {
//	        Notification notification = new Notification();
//	        
//
//	        assertThrows(
//	            IllegalStateException.class,
//	            () -> notification.transitionTo(Status.SENT)
//	        );
//
//	        assertEquals(Status.PENDING, notification.getStatus());
//	    }
//
//	    @Test
//	    void shouldRejectSentToFailed() {
//	        Notification notification = new Notification();
//	        
//
//	        assertThrows(
//	            IllegalStateException.class,
//	            () -> notification.transitionTo(Status.FAILED)
//	        );
//
//	        assertEquals(Status.SENT, notification.getStatus());
//	    }
//	    
//	    @Test
//	    void shouldAllowProcessingToSent() {
//	        Notification notification = new Notification();
//	        
//
//	        notification.transitionTo(Status.SENT);
//
//	        assertEquals(Status.SENT, notification.getStatus());
//	    }
//
//	    @Test
//	    void shouldAllowProcessingToFailed() {
//	        Notification notification = new Notification();
//	        
//
//	        notification.transitionTo(Status.FAILED);
//
//	        assertEquals(Status.FAILED, notification.getStatus());
//	    }
//
//	    @Test
//	    void shouldRejectFailedToProcessing() {
//	        Notification notification = new Notification();
//	        
//
//	        assertThrows(
//	            IllegalStateException.class,
//	            () -> notification.transitionTo(Status.PROCESSING)
//	        );
//
//	        assertEquals(Status.FAILED, notification.getStatus());
//	    }
//
//	    @Test
//	    void shouldRejectPendingToFailed() {
//	        Notification notification = new Notification();
//	        
//
//	        assertThrows(
//	            IllegalStateException.class,
//	            () -> notification.transitionTo(Status.FAILED)
//	        );
//
//	        assertEquals(Status.PENDING, notification.getStatus());
//	    }
//}
