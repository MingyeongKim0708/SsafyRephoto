package com.ssafy.mvc.controller;

import com.ssafy.mvc.model.dto.Comment;
import com.ssafy.mvc.model.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성
    @PostMapping("")
    public ResponseEntity<?> writeComment(@RequestBody Comment comment) {
    	System.out.println(100);
    	System.out.println("받은 댓글 데이터 try 전: " + comment);
    	try {
        	System.out.println("받은 댓글 데이터: " + comment);
            commentService.writeComment(comment);
            return ResponseEntity.ok("댓글이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("댓글 등록 중 오류가 발생했습니다.");
        }
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<?> modifyComment(@PathVariable("commentId") int commentId, @RequestBody Comment comment) {
        try {
            comment.setCommentId(commentId);
            commentService.modifyComment(comment);
            return ResponseEntity.ok("댓글이 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("댓글 수정 중 오류가 발생했습니다.");
        }
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}/{boardId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") int commentId, @PathVariable("boardId") int boardId) {
        try {
            boolean result = commentService.removeComment(commentId, boardId);
            if (result) {
                return ResponseEntity.ok("댓글이 성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.status(404).body("삭제할 댓글을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("댓글 삭제 중 오류가 발생했습니다.");
        }
    }
}
