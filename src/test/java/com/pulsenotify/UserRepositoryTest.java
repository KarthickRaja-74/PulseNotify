//package com.pulsenotify;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.pulsenotify.users.entity.User;
//import com.pulsenotify.users.repository.UserRepository;
//
//@SpringBootTest
//public class UserRepositoryTest {
//
//	@Autowired
//	private UserRepository repo;
//	
//	@Test
//	void saveUserWithTimeStamp() {
//		User user = new User();
//		user.setEmail("kr@kmail.com");
//		
//		User saved = repo.save(user);
//		
//		assertThat(saved.getId()).isNotNull();
//        assertThat(saved.getCreatedAt()).isNotNull();
//        assertThat(saved.getUpdatedAt()).isNotNull();
//	}
//	
//    @Test
//    void shouldUpdateUserAndKeepCreatedAt() throws InterruptedException {
//        User user = new User();
//        user.setEmail("cherno@gmail.com");
//
//        User saved = repo.saveAndFlush(user);
//
//        var createdAt = saved.getCreatedAt();
//        var updatedAt = saved.getUpdatedAt();
//
//        // Ensure enough time passes for the timestamp to differ.
//        Thread.sleep(10);
//
//        saved.setEmail("gipsy@gmail.com");
//        User updated = repo.saveAndFlush(saved);
//
//        assertThat(updated.getCreatedAt()).isEqualTo(createdAt);
//        assertThat(updated.getUpdatedAt()).isNotEqualTo(updatedAt);
//    }
//}
