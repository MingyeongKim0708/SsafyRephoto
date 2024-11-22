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
public class PaginationResponse {

	private List<Board> boards; // 전체 게시물 리스트
	private int total; // 전체 게시물 수
	private int totalPages; // 전체 페이지 수
	private int currentPages; // 현재 페이지
	private int pageSize; // 한 페이지당 게시물 수
	
}
