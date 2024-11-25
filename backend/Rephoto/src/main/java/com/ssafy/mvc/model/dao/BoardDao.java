package com.ssafy.mvc.model.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.mvc.model.dto.Board;
import com.ssafy.mvc.model.dto.SearchCondition;

public interface BoardDao {
	// 게시물 전체 목록 조회 및 검색
	public List<Board> search(SearchCondition condition);

	// 전체 게시물 수
	public int countBoards(SearchCondition condition);

	// 특정 유저의 게시글 조회
	public List<Board> searchByUserNick(SearchCondition condition);
	
	// service에서 두 개를 같이 요청함
	// ID에 해당하는 게시물 상세 조회
	public Board selectOne(int id);

	// 조회수 증가
	public void updateViewCnt(int id);

	// service에서 두 개를 같이 요청함
	// 게시글 등록
	public void insertBoard(Board board);

	// 사진 첨부
	public void insertFile(Board board);

	// 게시글 삭제
	public int deleteBoard(int id);

	// 파일 원본명 조회
	public String getFileNameByUuid(String fileUuid);

	// 게시글 작성 전 유효한 userNick인지 확인
	public boolean isUserNickExist(String userNick);
	
	// photoUuid 조회
	public String getPhotoUuidByBoardId(int boardId);

	// 점수 갱신
	public void updateAvgScore(int boardId);

	// 페이징 처리된 게시물을 조회하는 기능
//	public List<Board> searchWithPagination(SearchCondition condition, int offset, int size);

	// 전체 게시글을 조회
//	public List<Board> selectAll();

	// 게시글 수정
//	public void updateBoard(Board board);

}
