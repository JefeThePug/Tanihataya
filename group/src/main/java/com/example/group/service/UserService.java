package com.example.group.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	private final UserMapper userMapper;

	List<Users> findAll() {
		return userMapper.findAll();
	}

	Users findById(Integer userId) {
		return userMapper.findById(userId);
	}

	void insert(Users user) {
		userMapper.insert(user);
	}

	void update(Users user) {
		userMapper.update(user);
	}

	void delete(Integer userId) {
		userMapper.delete(userId);
	}
}
