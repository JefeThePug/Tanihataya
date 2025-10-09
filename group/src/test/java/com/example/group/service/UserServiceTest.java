package com.example.group.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.group.entity.Users;
import com.example.group.form.UserForm;
import com.example.group.repository.UsersMapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Service Unit Test")
class UserServiceTest {
	@Mock
	private UsersMapper userMapper;
	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserService userService;

	private UserForm form;
	private Users user;
	private List<Users> users;

	@BeforeEach
	void setUp() throws Exception {
		when(passwordEncoder.encode("password")).thenReturn("P@$sVV0rD");
		// Set up UserForm / UserFormにサンプル値を設定
		form = new UserForm();
		form.setUsersId(42);
		form.setName("Tester");
		form.setEmail("email@example.com");
		form.setPassword("password");
		form.setPostcode("1234567");
		form.setAddress("Osaka Japan");
		form.setTel("08012345678");
		form.setActive(true);
		// Set up Users entity / Usersにサンプル値を設定
		user = new Users();
		user.setUsersId(1);
		user.setName("Taro");
		user.setEmail("google@google.com");
		user.setPassword(passwordEncoder.encode("password"));
		user.setPostcode("1112222");
		user.setAddress("New York, New York");
		user.setTel("09008675309");
		user.setActive(true);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		//Make List of (one) Users / （1つの）Usersのリストを作成
		users = List.of(user);
	}

	@Nested
	@DisplayName("findById Tests")
	class FindByIdTests {
		@Test
		void testFindById() {
			when(userMapper.findById(1)).thenReturn(users);
			Users result = userService.findById(1);
			assertEquals(1, result.getUsersId());
			assertEquals("Taro", result.getName());
			assertEquals("google@google.com", result.getEmail());
			assertNotNull(result.getPassword());
			verify(userMapper).findById(1);
		}
	}

	@Nested
	@DisplayName("Insert Method Tests")
	class InsertTests {
		@Test
		void testInsert() {
			ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
			userService.insert(form);
			verify(userMapper).insert(captor.capture());
			Users inserted = captor.getValue();
			assertEquals(42, inserted.getUsersId(), "UsersId should match form");
			assertEquals("Tester", inserted.getName(), "Name should match form");
			assertEquals("email@example.com", inserted.getEmail(), "Email should match form");
			assertNotNull(inserted.getPassword(), "Password should be set");
			assertNotEquals("password", inserted.getPassword(), "Password should be encrypted");
			assertTrue(inserted.isActive(), "New User should be marked active");
			assertNotNull(inserted.getCreatedAt(), "New User should have an account creation date");
			assertNotNull(inserted.getUpdatedAt(), "New User should have an account update date");
			// Remove milliseconds and nanoseconds / ナノ秒とミリ秒を削除
			LocalDateTime created = inserted.getCreatedAt().truncatedTo(ChronoUnit.SECONDS);
			LocalDateTime updated = inserted.getUpdatedAt().truncatedTo(ChronoUnit.SECONDS);
			assertEquals(created, updated, "Created and Updated dates should be the same");
		}
	}

	@Nested
	@DisplayName("Update Password Encoding Tests")
	class UpdateTests {
		// Only testing the password encoding business logic
		// パスワードのエンコード処理（ビジネスロジック）のみをテストしています
		@Test
		void testUpdate() {
			UserForm updateForm = new UserForm();
			updateForm.setUsersId(42);
			updateForm.setName("Updated Tester");
			updateForm.setEmail("update@example.com");
			updateForm.setPassword("password");
			userService.update(updateForm);
			ArgumentCaptor<UserForm> captor = ArgumentCaptor.forClass(UserForm.class);
			verify(userMapper).update(captor.capture());
			UserForm updated = captor.getValue();
			assertEquals("P@$sVV0rD", updated.getPassword(), "Password should be encoded");
		}
	}
}
