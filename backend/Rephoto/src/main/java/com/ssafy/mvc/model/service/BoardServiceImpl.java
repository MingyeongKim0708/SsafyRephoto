package com.ssafy.mvc.model.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.ssafy.mvc.model.dao.BoardDao;
import com.ssafy.mvc.model.dto.Board;
import com.ssafy.mvc.model.dto.SearchCondition;

import jakarta.servlet.ServletContext;

@Service
public class BoardServiceImpl implements BoardService {

	private final BoardDao boardDao;
	private final ResourceLoader resourceLoader;

	public BoardServiceImpl(BoardDao boardDao, ResourceLoader resourceLoader) {
		this.boardDao = boardDao;
		this.resourceLoader = resourceLoader;
	}

	// 게시물 전체 목록 조회 및 검색 + pagination
	@Override
	public List<Board> search(SearchCondition condition) {
		System.out.println(condition);
		System.out.println("게시물 전체 목록 조회 및 검색 + pagination");

//		condition.setOffset((condition.getPage() -1 )*condition.getSize());

		return boardDao.search(condition);

	}

	// 전체 게시물 수
	@Override
	public int getTotalCount(SearchCondition condition) {
		return boardDao.countBoards(condition); // 전체 게시물 수를 구하는 메서드
	}

	// 게시물 상세 조회
	@Override
	public Board readBoard(int boardId) {
		System.out.println(boardId + "번 상세 조회");
		boardDao.updateViewCnt(boardId);
		return boardDao.selectOne(boardId);
	}

	// 게시글 삭제
	@Override
	public boolean removeBoard(int boardId) {
		System.out.println("게시글 삭제 시도.");
		// 게시글에 해당하는 사진 UUID 가져오기
	    String photoUuid = boardDao.getPhotoUuidByBoardId(boardId);
	    
	    // 사진 파일이 존재한다면 삭제
	    if (photoUuid != null && !photoUuid.isEmpty()) {
	        deleteFile(photoUuid);  // 파일 삭제 메서드 호출
	    }
	    
	    // 게시글 삭제
		int result = boardDao.deleteBoard(boardId);
		System.out.println("게시글 삭제 성공시 1 :" + result);
		return result == 1;
	}
	
	// 파일 삭제
	private void deleteFile(String photoUuid) {
	    try {
	        // 이미지 파일 경로 지정
	    	Resource resource = resourceLoader.getResource("classpath:/static/img/" + photoUuid);
	        File file = resource.getFile();

	        // 파일이 존재하면 삭제
	        if (file.exists()) {
	            boolean deleted = file.delete();
	            if (!deleted) {
	                System.out.println("파일 삭제 실패: " + file.getPath());
	            } else {
	                System.out.println("파일 삭제 성공: " + file.getPath());
	            }
	        } else {
	            System.out.println("파일이 존재하지 않습니다: " + file.getPath());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("파일 삭제 중 오류 발생");
	    }
	}

	// 게시물 등록 및 파일 업로드
	@Override
	public void fileUpload(MultipartFile file, Board board) {
		if (file != null && file.getSize() > 0) {
			try {
				String fileName = file.getOriginalFilename(); // 실제파일 이름

				// 확장자 검사
				String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				String[] allowedExtensions = { "jpg", "jpeg", "png", "webp" };

				boolean isAllowed = Arrays.asList(allowedExtensions).contains(extension);
				if (!isAllowed) {
					throw new IllegalArgumentException("허용되지 않은 확장자입니다.");
				}

				String fileId = UUID.randomUUID().toString() + "." + extension; // UUID 생성(확장자없음)
				board.setPhotoUuid(fileId); // 확장자 추가
				board.setPhotoName(fileName);

				// 파일 저장
				Resource resource = resourceLoader.getResource("classpath:/static/img");
				file.transferTo(new File(resource.getFile(), fileId));

				// DB 처리
				// DB 처리 전 유효한 userNick인지 체크
				if (!isValidUserNick(board.getUserNick())) {
					throw new IllegalArgumentException("Invalid userNick: " + board.getUserNick());
				}
				boardDao.insertBoard(board); // 삽입 시도
				boardDao.insertFile(board);
				System.out.println("파일 업로드 및 게시글 등록 처리 Service");

			} catch (IllegalStateException e) {
				throw e; // 예외를 그냥 던짐 (Controller에서 처리될 예정)
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("파일 저장 중 문제가 발생했습니다.", e); // 예외를 던짐
			}

		}

	}

	// DB에서 userNick 존재 여부를 확인
	private boolean isValidUserNick(String userNick) {
		return boardDao.isUserNickExist(userNick);
	}

	// 파일 원본명 조회
	@Override
	public String getFileNAmeByUuid(String fileUuid) {
		return boardDao.getFileNameByUuid(fileUuid);
	}

//	@Override
//	public void modifyBoard(Board board) {
//		System.out.println("게시글을 수정합니다.");
//		
//		//실제로 우리 수정하고 싶은 id를 가진 게시글을 일단 가지고와서.
////		Board tmp = boardDao.selectOne(board.getId());
////		tmp.setTitle(board.getTitle());
//		boardDao.updateBoard(board);
//	}

	// 안씀
//	@Override
//	public List<Board> getBoardList() {
//		System.out.println("모든 게시글 가지고 왔습니다.");
//		return boardDao.selectAll();
//	}

	// 게시물 등록
//	@Override
//	public void writeBoard(Board board) {
//		System.out.println("게시글 작성했습니다");
//		boardDao.insertBoard(board);
//	}

}
