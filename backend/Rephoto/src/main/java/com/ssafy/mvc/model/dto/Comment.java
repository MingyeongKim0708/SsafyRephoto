package com.ssafy.mvc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int commentId;            // 댓글 ID
    private int boardId;              // 게시글 ID
    private String userNick;          // 작성자 닉네임
    private int score;                // 평점
    private String review;            // 리뷰 내용
    private String commentCreatedAt;  // 생성일
    private String commentUpdatedAt;  // 수정일
    private String userUuid;           // 작성자 프로필 이미지 (추가)

    // 댓글 등록 시 사용할 생성자
    public Comment(int boardId, String userNick, int score, String review) {
        this.boardId = boardId;
        this.userNick = userNick;
        this.score = score;
        this.review = review;
    }
}
