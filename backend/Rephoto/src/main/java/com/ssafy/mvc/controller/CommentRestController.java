package com.ssafy.mvc.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.mvc.model.service.CommentService;

@RestController
@RequestMapping("/comment")
@CrossOrigin("*")
public class CommentRestController {

	// 서비스 의존성 주입
	private final CommentService commentService;
	
	public CommentRestController(CommentService commentService) {
		this.commentService = commentService;
	}
}
