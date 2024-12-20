package com.ssafy.mvc.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.mvc.model.dto.User;

public interface UserDao {
	
	public List<User> selectAll();

	public int checkUser(Map<String,String> info);
	
	public void insertUser(User user);

	public void insertFile(User user);
	
	public User selectOne(Map<String, String> info);
	// User의 아이디와 비밀번호변수에 userImg, userUuid를 받아온다.
	public User selectUuid(String id); 
	
	public int deleteUser(String id);
	
	public void updateUser(User user);
	
	public void updateFile(User user);
}