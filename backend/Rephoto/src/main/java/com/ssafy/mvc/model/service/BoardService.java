package com.ssafy.mvc.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.mvc.model.dto.Board;
import com.ssafy.mvc.model.dto.SearchCondition;

public interface BoardService {
	// 게시물 전체 목록 조회 및 검색 + pagination
	public List<Board> search(SearchCondition condition);
	
	// 게시물 상세 조회
	public Board readBoard(int boardId);
	
	// 게시글 삭제
	public boolean removeBoard(int id);

	// 파일업로드
	public void fileUpload(MultipartFile file, Board board);
	
	// 파일 원본명 조회
	public String getFileNAmeByUuid(String fileUuid);

	// 전체 게시물 수
	public int getTotalCount(SearchCondition condition);

	
	
	
	// 게시글 전체 조회
//	public List<Board> getBoardList();
	
	// 게시글 작성
//	public void writeBoard(Board board);
	
	// 게시글 수정
//	public void modifyBoard(Board board);
}
