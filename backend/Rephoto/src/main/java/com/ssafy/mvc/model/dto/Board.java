package com.ssafy.mvc.model.dto;

import java.util.List;

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
public class Board {
	private int boardId;
	private String userNick;
	private String userId; // 추가: 작성자의 userId
	private String boardTitle;
	private String boardInfo;
	private String boardCreatedAt;
	private int viewCnt = 0;
	private float avgScore = 0;
	private String photoName;
	private String photoUuid;
	private List<Comment> comments;
	public Board(String userNick, String boardTitle, String boardInfo) {
		super();
		this.userNick = userNick;
		this.boardTitle = boardTitle;
		this.boardInfo = boardInfo;
	}

	
}
