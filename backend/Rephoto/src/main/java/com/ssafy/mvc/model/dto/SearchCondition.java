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
public class SearchCondition {
	private String key = "none"; // 어떤 컬럼
	private String word; // 검색어
	private String orderBy = "board_created_at"; // 뭘 기준으로 정렬
	private String orderByDir = "desc"; // 오름차순, 내림차순
}
