package com.example.group.service.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.group.entity.Users;
import com.example.group.repository.UsersMapper;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Details Service Unit Test")
class UserDetailsServiceImplTest {
	@Mock
	private UsersMapper userMapper;

	@InjectMocks
	private UserDetailsServiceImpl userDetailsService;

	private Users testUser;

	@BeforeEach
	void setUp() throws Exception {
		testUser = new Users();
		testUser.setEmail("test@example.com");
		testUser.setPassword("P@$sVV0rD");
	}

	@Nested
	@DisplayName("Load User by Email Test")
	class LoadUserByUsernameTest {
		@Test
		void whenUserExistsTest() {
			String testEmail = "test@example.com";
			when(userMapper.findByEmail(testEmail)).thenReturn(List.of(testUser));
			UserDetails result = userDetailsService.loadUserByUsername(testEmail);
			assertNotNull(result);
			assertEquals(testEmail, result.getUsername());
			verify(userMapper).findByEmail(testEmail);
		}

		@Test
		void whenUserDoesNotExistTest() {
			String testEmail = "nonexistent@example.com";
			when(userMapper.findByEmail(testEmail)).thenReturn(Collections.emptyList());
			UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
				userDetailsService.loadUserByUsername(testEmail);
			});
			assertEquals(exception.getMessage(), "メールアドレス " + testEmail + " のユーザーは存在しません");
			verify(userMapper).findByEmail(testEmail);
		}

		@Test
		void whenMapperReturnsNullTest() {
			String testEmail = "null@example.com";
			when(userMapper.findByEmail("null@example.com")).thenReturn(null);
			UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
				userDetailsService.loadUserByUsername(testEmail);
			});
			assertEquals(exception.getMessage(), "メールアドレス " + testEmail + " のユーザーは存在しません");
			verify(userMapper).findByEmail(testEmail);
		}
	}
}
