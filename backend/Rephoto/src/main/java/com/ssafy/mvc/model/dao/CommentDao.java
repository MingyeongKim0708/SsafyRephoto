package com.ssafy.mvc.model.dao;

import java.util.List;

import com.ssafy.mvc.model.dto.Comment;

public interface CommentDao {

	// 댓글 전체 조회
	public List<Comment> selectAllComment(int boardId);
	
	// 댓글 등록
	public void insertComment(Comment comment);
	
	// 댓글 수정
	public void updateComment(Comment comment);
	
	// 댓글 삭제
	public int deleteComment(int commentId);

	// 댓글 수 확인
	public int countComments(int boardId);
	
}
