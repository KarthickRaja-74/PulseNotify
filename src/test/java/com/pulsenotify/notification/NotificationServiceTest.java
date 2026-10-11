//package com.pulsenotify.notification;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import com.pulsenotify.exception.ResourceNotFoundException;
//import com.pulsenotify.notifications.Enum.Status;
//import com.pulsenotify.notifications.entity.Notification;
//import com.pulsenotify.notifications.repository.NotificationRepository;
//import com.pulsenotify.notifications.service.NotificatonService;
//
//@ExtendWith(MockitoExtension.class)
//class NotificationServiceTest {
//
//    @Mock
//    private NotificationRepository notificationRepository;
//
//    @InjectMocks
//    private NotificatonService notificationService;
//
//    @Test
//    void shouldSaveNotificationAfterValidTransition() {
//        UUID notificationId = UUID.randomUUID();
//
//        Notification notification = new Notification();
//        
//
//        when(notificationRepository.findById(notificationId))
//                .thenReturn(Optional.of(notification));
//
//        notificationService.transitionNotificationStatus(
//                notificationId, Status.PROCESSING);
//
//        assertEquals(Status.PROCESSING, notification.getStatus());
//
//        verify(notificationRepository).save(notification);
//    }
//    
//    @Test
//    void shouldNotSaveNotificationAfterInvalidTransition() {
//        UUID notificationId = UUID.randomUUID();
//
//        Notification notification = new Notification();
//        
//
//        when(notificationRepository.findById(notificationId))
//                .thenReturn(Optional.of(notification));
//
//        assertThrows(
//                IllegalStateException.class,
//                () -> notificationService.transitionNotificationStatus(
//                        notificationId, Status.SENT)
//        );
//
//        assertEquals(Status.PENDING, notification.getStatus());
//        verify(notificationRepository, never()).save(notification);
//    }
//    
//    @Test
//    void shouldThrowExceptionWhenNotificationDoesNotExist() {
//        UUID notificationId = UUID.randomUUID();
//
//        when(notificationRepository.findById(notificationId))
//                .thenReturn(Optional.empty());
//
//        assertThrows(
//                ResourceNotFoundException.class,
//                () -> notificationService.transitionNotificationStatus(
//                        notificationId, Status.PROCESSING)
//        );
//
//        verify(notificationRepository, never()).save(
//                org.mockito.ArgumentMatchers.any(Notification.class)
//        );
//    }
//}