package com.ssafy.mvc.model.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.mvc.model.dao.BoardDao;
import com.ssafy.mvc.model.dao.UserDao;
import com.ssafy.mvc.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final BoardDao boardDao;
	private final ResourceLoader resourceLoader;

	public UserServiceImpl(UserDao userDao, BoardDao boardDao, ResourceLoader resourceLoader) {
		this.userDao = userDao;
		this.boardDao = boardDao;
		this.resourceLoader = resourceLoader;
	}

	@Override
	public List<User> getUserList() {
		return userDao.selectAll();
	}

	@Override
	public boolean check(String condition, String word) {
		Map<String, String> info = new HashMap<>();
		info.put("condition", condition);
		info.put("word", word);
		System.out.println(info);
		int result = userDao.checkUser(info);
		if (result == 0) {
			return true;
		}
		return false;
	}

	@Override
	public String upload(MultipartFile file) {
		if (file != null && file.getSize() > 0) {
			try {

				String fileName = file.getOriginalFilename(); // 실제파일 이름

				if (fileName.isEmpty()) {
					return "0";
				}

				String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				String[] allowedExtensions = { "jpg", "jpeg", "png", "webp" };

				boolean check = false;
				for (int i = 0; i < 4; i++) {
					if (extension.equals(allowedExtensions[i])) {
						check = true;
						break;
					}
				}

				if (!check) {
					throw new IllegalArgumentException("허용되지 않은 확장자입니다.");
				}

				String fileId = UUID.randomUUID().toString() + "." + extension; // 고유한 이름

				// 저장할 위치 지정
				Resource resource = resourceLoader.getResource("classpath:/static/img");
				file.transferTo(new File(resource.getFile(), fileId));
				return fileId;
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "0";
	}

	@Override
	public void signup(User user) {
		if (user.getUserEmail().isEmpty() || user.getUserId().isEmpty() || user.getUserNick().isEmpty()
				|| user.getUserPassword().isEmpty()) {
			return;
		}
		userDao.insertUser(user);
		userDao.insertFile(user);
	}

	@Override
	public User login(String id, String password) {
		if (password == null) {
			return null;
		}
		Map<String, String> info = new HashMap<>();
		info.put("id", id);
		info.put("password", password);
		User tmp_user = userDao.selectOne(info);
		User tmp = userDao.selectUuid(id);
		tmp_user.setUserImg(tmp.getUserImg());
		tmp_user.setUserUuid(tmp.getUserUuid());
		return tmp_user;
	}

	@Override
	public User getInfo(String id) {

		Map<String, String> info = new HashMap<>();
		System.out.println(id);
		info.put("id", id);
		info.put("password", null);
		User tmp_user = userDao.selectOne(info);
		User tmp = userDao.selectUuid(id);
		System.out.println(tmp);
		tmp_user.setUserImg(tmp.getUserImg());
		tmp_user.setUserUuid(tmp.getUserUuid());
		tmp_user.setUserEmail(null);
		tmp_user.setUserPassword(null);
		System.out.println("서비스유저"+tmp_user);
		return tmp_user;
	}

	@Override
	public int removeUser(String id) throws IOException {
		User tmp = userDao.selectUuid(id);
		String userUuid = tmp.getUserUuid();
		Resource resource = resourceLoader.getResource("classpath:/static/img");
		if(!userUuid.equals("0.webp")) {
			File file = new File(resource.getFile(),userUuid);
			file.delete();
		}
		int result = userDao.deleteUser(id);
		boardDao.updateAllAvgScore();
		return result;
		
	}
	
	@Override
	public void removeProfile(String id) throws IOException {
		User tmp = userDao.selectUuid(id);
		System.out.println("service"+tmp);
		String userUuid = tmp.getUserUuid();
		Resource resource = resourceLoader.getResource("classpath:/static/img");
		if(!userUuid.equals("0.webp")) {
			File file = new File(resource.getFile(),userUuid);
			file.delete();
		}	
	}

	@Override
	public void emitUser(User user) throws IOException {
		if (user.getUserEmail().isEmpty() || user.getUserId().isEmpty() || user.getUserNick().isEmpty()
				|| user.getUserPassword().isEmpty()) {
			return;
		}

		userDao.updateUser(user);
		userDao.updateFile(user);
	}

	@Override
	public File getProfile(String Uuid) throws IOException {

		Resource resource = resourceLoader.getResource("classpath:/static/img/" + Uuid);
		if (!resource.exists()) {
			return null; // 파일이 없을 경우 처리
		}

		// File 객체 반환
		return resource.getFile();
	}

	

}
