package com.example.group.service.security;

import java.util.List;

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
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    List<Users> users = userMapper.findByEmail(email);
	    if (users.isEmpty()) {
	        throw new UsernameNotFoundException("User does not exist");
	    }
	    Users user = users.get(0);
	    return new UserDetailsImpl(user);
	}

}
