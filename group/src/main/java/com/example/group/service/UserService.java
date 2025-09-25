package com.example.group.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.group.Entity.Users;
import com.example.group.repository.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	private final UserMapper userMapper;

	public List<Users> findAll() {
		return userMapper.findAll();
	}

	public Users findById(Integer userId) {
		return userMapper.findById(userId);
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
