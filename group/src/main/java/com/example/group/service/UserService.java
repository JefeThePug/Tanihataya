package com.example.group.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.group.Entity.Users;
<<<<<<< HEAD
import com.example.group.form.UserForm;
=======
import com.example.group.repository.UsersMapper;
>>>>>>> 2e1a1830084d2b7a3eb46b6c93111e32d5f88577

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	private final UsersMapper userMapper;

	public Users findByEmail(String email) {
		return userMapper.findByEmail(email).get(0);
	}
	
	public Users findById(Integer userId) {
		return userMapper.findById(userId).get(0);
	}
	
	public Users findByName(String username) {
		return userMapper.findByName(username).get(0);
	}

	public void insert(UserForm user) {
		userMapper.insert(user);
	}

	public void update(UserForm user) {
		userMapper.update(user);
	}

	public void delete(Integer userId) {
		userMapper.delete(userId);
	}
}
