package com.ssafy.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.BoardDao;
import com.ssafy.mvc.model.dao.CommentDao;
import com.ssafy.mvc.model.dto.Comment;
import com.ssafy.mvc.model.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	private final CommentDao commentDao;
	private final BoardDao boardDao; // avg_score 업데이트 위해 필요
	
	public CommentServiceImpl(CommentDao commentDao, BoardDao boardDao) {
		this.commentDao = commentDao;
		this.boardDao = boardDao;
	};

	@Override
	public List<Comment> getCommentList(int boardId) {
		System.out.println(boardId + "의 댓글 전체 불러오기");
		return commentDao.selectAllComment(boardId);
	}

	@Override
	public void writeComment(Comment comment) {
		System.out.println("DB에 삽입할 데이터: " + comment);
		commentDao.insertComment(comment);
		boardDao.updateAvgScore(comment.getBoardId()); // 댓글 등록 후 평균 점수 업데이트
		System.out.println("댓글 등록 처리 Service");
	}

	@Override
	public void modifyComment(Comment comment) {
		commentDao.updateComment(comment);
		boardDao.updateAvgScore(comment.getBoardId());
		System.out.println("댓글 수정 처리 Service");
	}

	@Override
	public boolean removeComment(int commentId, int boardId) {
		int result = commentDao.deleteComment(commentId);
		boardDao.updateAvgScore(boardId); // 댓글 삭제 후 평균 점수 업데이트
		System.out.println("댓글 삭제 Service");
		return result == 1;
	}

	@Override
	public int getTotalComment(int boardId) {
		int result = commentDao.countComments(boardId);
		return result;
	}

}
