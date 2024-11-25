package com.ssafy.mvc.model.service;

import java.util.List;

import com.ssafy.mvc.model.dto.Comment;

public interface CommentService {
	// 댓글 전체 조회
	public List<Comment> getCommentList(int boardId);
	
	// 댓글 등록
	public void writeComment(Comment comment);
	
	// 댓글 수정
	public void modifyComment(Comment comment);
	
	// 댓글 삭제
	public boolean removeComment(int commentId, int boardId);
	
	// 전체 댓글 수
	public int getTotalComment(int boardId);

}
