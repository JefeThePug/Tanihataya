package com.example.group.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.group.Entity.Users;
import com.example.group.repository.UsersMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UsersMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userMapper.findByName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User does not exist");
		}
		return new UserDetailsImpl(user);
	}
}
