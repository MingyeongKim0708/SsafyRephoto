package com.ssafy.mvc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	private String userId;
	private String userPassword;
	private String userNick;
	private String userEmail;
	private String userCreatedAt;
	private String authority= "general";
	private String photoName = "window.webp";
	private String PhotoUuid = "0";
	
	public User() {};
	
	public User(String userId, String userPassword) {
		this.userId = userId;
		this.userPassword = userPassword;
	}
	
}
