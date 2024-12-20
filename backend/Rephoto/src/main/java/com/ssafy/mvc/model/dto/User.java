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
	private String authority = "general";
	private String userImg = "window.webp";
	private String userUuid = "0.webp";

	public User(String userId, String userPassword) {
		this.userId = userId;
		this.userPassword = userPassword;
	}

	public User(String userId, String userPassword, String userNick, String userEmail) {
		this.userId = userId;
		this.userPassword = userPassword;
		this.userNick = userNick;
		this.userEmail = userEmail;
	}

}
