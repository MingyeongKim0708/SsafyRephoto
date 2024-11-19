package com.ssafy.mvc.controller;

import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.mvc.model.dao.UserDao;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private final UserDao userDao;

	public UserController(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@PostMapping("/login")
	public static RequestEntity<?> login(@RequestBody String id){
		return null;
	}
	
	
	
	
}
