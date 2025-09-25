package com.example.group.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.group.Entity.Users;

@Mapper
public interface UsersMapper {
	
	//ユーザー情報取得
	List<Users>findAll();
	
	//ユーザーキーで取得
	List<Users>findById(Integer id);
	
	//ユーザー情報の特録
	void insert(Users users);
	
	//ユーザー情報の更新
	void update(Users users);
	
	//ユーザー情報の削除
	void delete(Integer id);

}
