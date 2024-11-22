package com.ssafy.mvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user, HttpSession session) {

		String id = user.getUserId();
		String password = user.getUserPassword();

		// 길이와 관련된 제한
		if (id.length() == 0 || id.length() > 16 || password.length() < 10 || password.length() > 30) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
		}

		// ID 형식과 관련된 제한 (정규표현식)
		if (!id.matches("^[a-zA-Z0-9-_]+$")) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
		}

		User tmp_user = userService.login(id, password);

		// 유저가 없다면 로그인 실패
		if (tmp_user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
		}

		// 로그인 성공 시 session에 아이디와 닉네임을 저장함
		session.setAttribute("userId", tmp_user.getUserId());
		session.setAttribute("userNick", tmp_user.getUserNick());

		return new ResponseEntity<String>(tmp_user.getUserNick(), HttpStatus.OK);
	}

	// 로그아웃
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {

		try {
			// 세션을 만료시킴
			session.invalidate();
			return ResponseEntity.status(HttpStatus.OK).body(1);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
		}
	}

	// 아이디나 닉네임, 이메일의 중복 여부 확인
	@GetMapping("/check/{condition}/{word}")
	public ResponseEntity<?> check(@PathVariable("condition") String condition, @PathVariable("word") String word) {
		// 중복이 되면 false, 아니면 true를 반환하는 result1
		boolean result1 = userService.check(condition, word);

		if (result1) {
			return ResponseEntity.status(HttpStatus.OK).body(1);
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
	}

	// 회원가입
	@PostMapping("/regist")
	public ResponseEntity<?> regist(@RequestParam("userId") String userId,
			@RequestParam("userPassword") String userPassword, @RequestParam("userNick") String userNick,
			@RequestParam("userEmail") String userEmail,
			@RequestParam(value = "file", required = false) MultipartFile file) {

		// 필요한 것들이 없다면 에러
		if (userId.isEmpty() || userPassword.isEmpty() || userNick.isEmpty() || userEmail.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
		}
		User user = new User(userId, userPassword, userNick, userEmail);
		if (file != null && file.getSize() > 0) {
			user.setUserImg(file.getOriginalFilename());
			String userUuid = userService.upload(file);
			user.setUserUuid(userUuid);
		}

		try {
			userService.signup(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(1);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
		}
	}

	// 유저 아이디들로 유저 정보를 불러옴 (프로필 페이지를 위해)
	@GetMapping("/myPage/{userId}")
	public ResponseEntity<?> seeMyPage(@PathVariable String userId) {
		User user = userService.getInfo(userId);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// 유저들의 프로필사진을 Uuid를 통해 가져온다.
	@GetMapping("/userImg/{userUuid}")
	public ResponseEntity<?> getProfile(@PathVariable String userUuid) throws IOException {
		
		File file = userService.getProfile(userUuid);
		if (file == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
		}

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		String fileName = file.getName();
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

		MediaType mediaType;
		switch (extension) {
		case "jpg":
		case "jpeg":
			mediaType = MediaType.IMAGE_JPEG;
			break;
		case "png":
			mediaType = MediaType.IMAGE_PNG;
			break;
		case "webp":
			mediaType = MediaType.valueOf("image/webp");
			break;
		default:
			mediaType = MediaType.APPLICATION_OCTET_STREAM; // 기본값: 바이너리 파일
		}

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + file.getName()) // inline으로
																													// 변경
				.contentType(mediaType) // 이미지의 경우 적절한 MIME 타입 사용 (예: JPEG, PNG 등)
				.contentLength(file.length()).body(resource);
	}

	// 회원 탈퇴
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> quit(@PathVariable String userId, HttpSession session) {

		try {

			// 로그인한 유저와 탈퇴하고자 하는 유저의 id가 같아야 탈퇴 가능
			if (userId.equals(session.getAttribute("userId"))) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
			}

			// 이 Id로 탈퇴시키는 유저 수
			int result = userService.removeUser(userId);
			if (result == 1) {
				return ResponseEntity.status(HttpStatus.OK).body(1);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
		}
	}

	// 편집되지 않는다면 지금까지 작업을 롤백해야 하므로 Transactional 처리
	// 회원 정보 수정
	@Transactional
	@PutMapping("")
	public ResponseEntity<?> emit(@RequestBody User user, HttpSession session) {

		try {

			// 로그인한 유저와 수정하고자 하는 유저의 id가 같아야 수정 가능
			if (user.getUserId().equals(session.getAttribute("userId"))) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
			}

			userService.emitUser(user);
			session.setAttribute("userNick", user.getUserNick());
			return ResponseEntity.status(HttpStatus.OK).body(1);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0);
		}
	}

}
