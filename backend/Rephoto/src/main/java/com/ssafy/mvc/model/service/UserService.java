package com.ssafy.mvc.model.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.mvc.model.dto.User;

public interface UserService {
	
	//전체 사용자 목록 불러오기
	public List<User> getUserList();
	//ID, 닉네임, 이메일 중복확인
	public boolean check(String condition, String word);
	// 파일 업로드
	public String upload(MultipartFile file);
	//사용자 등록하기
	public void signup(User user);
	//로그인 하기
	public User login(String id, String password);
	// 프로필 정보 가져오기
	public User getInfo(String id);
	// 프로필 사진 가져오기
	public File getProfile(String Uuid) throws IOException;
	// 사용자 탈퇴
	public int removeUser(String id) throws IOException;
	// 사용자 정보 수정
	public void emitUser(User user);
}
