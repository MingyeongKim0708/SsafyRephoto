package com.ssafy.mvc.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.mvc.model.dto.Board;

import com.ssafy.mvc.model.dto.Comment;
import com.ssafy.mvc.model.dto.SearchCondition;
import com.ssafy.mvc.model.service.BoardService;
import com.ssafy.mvc.model.service.CommentService;


@RestController // @Controller + @ResponseBody
@RequestMapping("/board")
public class BoardRestController {

	// 서비스 의존성 주입
	private final BoardService boardService;
	private final CommentService commentService;

	public BoardRestController(BoardService boardService, CommentService commentService) {
		this.boardService = boardService;
		this.commentService = commentService;
	}

	// 게시물 전체 목록 조회 및 검색
	@GetMapping("")
	public ResponseEntity<?> list(@ModelAttribute SearchCondition condition) {
		try {
			// 인증 정보가 없거나 유효하지 않으면 401 Unauthorized 반환 - 아직 인증 관련된 것 안넣어서 주석 처리
//	        if (authorization == null || !isValidToken(authorization)) {
//				System.out.println("HttpStatus 401 Unauthorized");
//            	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("401 Unauthorized: Invalid or missing authorization token");
//        }

			// 400: 잘못된 요청 - 유효하지 않은 query parameter
			if (condition.getKey() == null || condition.getOrderBy() == null || condition.getOrderByDir() == null) {
				System.out.println("HttpStatus 400 BAD_REQUEST");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 Bad Request: Invalid boardId");
			}

			System.out.println("Condition received: " + condition);

			// 검색 수행
			List<Board> list = boardService.search(condition);

			// 204: 검색 결과 없음
			if (list == null || list.isEmpty()) {
				System.out.println("HttpStatus 204 NO_CONTENT");
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("204 No content");
			}

//			// 페이지네이션 정보 추가 (전체 게시물 수, 페이지 크기, 총 페이지 수)
//			int total = boardService.getTotalCount(condition); // 전체 게시물 수
//			int totalPages = (int) Math.ceil((double) total / condition.getSize()); // 전체 페이지 수 계산
//
//			PaginationResponse paginationResponse = new PaginationResponse(list, total, totalPages, condition.getPage(),
//					condition.getSize());

			// 200: 게시물 전체 불러오기 성공
			return new ResponseEntity<>(list, HttpStatus.OK);

		} catch (Exception e) {
			// 500: 서버 오류
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
	}


	// 유저 게시글 목록 조회 및 검색
		@GetMapping("/userPhoto/{userNick}")
		public ResponseEntity<?> photoList(@PathVariable String userNick, @ModelAttribute SearchCondition condition) {
			try {
				// 인증 정보가 없거나 유효하지 않으면 401 Unauthorized 반환 - 아직 인증 관련된 것 안넣어서 주석 처리
//		        if (authorization == null || !isValidToken(authorization)) {
//					System.out.println("HttpStatus 401 Unauthorized");
//	            	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("401 Unauthorized: Invalid or missing authorization token");
//	        }

				// 400: 잘못된 요청 - 유효하지 않은 query parameter
				
				if (userNick.isEmpty() || userNick == null ||condition.getOrderBy() == null || condition.getOrderByDir() == null) {
					System.out.println("HttpStatus 400 BAD_REQUEST");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 Bad Request: Invalid boardId");
				}

				condition.setKey("user_nick");
				condition.setWord(userNick);
				System.out.println("Condition received: " + condition);

				// 검색 수행
				List<Board> list = boardService.searchByUserNick(condition);

				// 204: 검색 결과 없음
				if (list == null || list.isEmpty()) {
					System.out.println("HttpStatus 204 NO_CONTENT");
					return ResponseEntity.status(HttpStatus.NO_CONTENT).body("204 No content");
				}

//				// 페이지네이션 정보 추가 (전체 게시물 수, 페이지 크기, 총 페이지 수)
//				int total = boardService.getTotalCount(condition); // 전체 게시물 수
//				int totalPages = (int) Math.ceil((double) total / condition.getSize()); // 전체 페이지 수 계산
	//
//				PaginationResponse paginationResponse = new PaginationResponse(list, total, totalPages, condition.getPage(),
//						condition.getSize());

				// 200: 게시물 전체 불러오기 성공
				return new ResponseEntity<>(list, HttpStatus.OK);

			} catch (Exception e) {
				// 500: 서버 오류
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
			}
		}
		

	// 게시글 상세 보기 + 댓글 불러오기
	@GetMapping("/{boardId}")
	public ResponseEntity<?> detail(@PathVariable("boardId") int boardId) {

		try {
			// 인증 정보가 없거나 유효하지 않으면 401 Unauthorized 반환 - 아직 인증 관련된 것 안넣어서 주석 처리
//	        if (authorization == null || !isValidToken(authorization)) {
//				System.out.println("HttpStatus 401 Unauthorized");
//            	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("401 Unauthorized: Invalid or missing authorization token");
//        }

			// 유효하지 않은 boardId 처리 (400 Bad Request)
			if (boardId <= 0) {
				System.out.println("HttpStatus 400 BAD_REQUEST");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 Bad Request: Invalid boardId");
			}

			// 게시글 조회 (200)
			Board board = boardService.readBoard(boardId);
			System.out.println("게시글 상세 : " + board);
			
			// 게시글 없음 (404 Not Found)
			if (board == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Board not found");
	        }

	        // 게시글 정보에 댓글 포함
			System.out.println("댓글 불러오기 시도");
	        List<Comment> comments = commentService.getCommentList(boardId);
	        System.out.println(comments);
	        board.setComments(comments); // DTO에 댓글 포함
	        System.out.println("댓글 : " + board.getComments());
	        return ResponseEntity.ok(board);

			
		} catch (Exception e) {
			// 서버 오류 (500 Internal server error)
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
	}

	// 게시물 등록 및 파일 업로드
	@PostMapping("")
	public ResponseEntity<?> write(@RequestParam("file") MultipartFile file, 
			@RequestParam("userNick") String userNick,
			@RequestParam("boardTitle") String boardTitle,
			@RequestParam("boardInfo") String boardInfo) {
		try {

			// 게시글 내용이 유효하지 않으면 400 Bad Request 반환
			if (userNick == null || userNick.isEmpty() || boardTitle == null
					|| boardTitle.isEmpty() || boardInfo == null
					|| boardInfo.isEmpty()) {
				System.out.println("HttpStatus 400 BAD_REQUEST");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 Bad Request: Missing required fields");
			}

			// 파일이 있다면 업로드 처리
			Board board = new Board(userNick, boardTitle, boardInfo);
			if (file != null && !file.isEmpty()) {
				String photoUuid = boardService.fileUpload(file); // 파일 업로드
				board.setPhotoName(file.getOriginalFilename());
				board.setPhotoUuid(photoUuid);
				boardService.writeBoard(board); // 게시물 등록
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 Bad Request : 파일이 없습니다.");
			}

			// 게시물 등록 후 목록으로 가는 것이 아니라 작성한 글의 상세 조회로 이동하기 위해 헤더에 URI 설정
			// Location 헤더에 생성된 게시글의 URI 설정
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create("/board/" + board.getBoardId())); // URI는 boardId를 포함해야 함

			// 게시물 등록 성공 (201)
			System.out.println("파일 업로드 및 게시물 등록 성공 Controller");
			return new ResponseEntity<Board>(headers, HttpStatus.CREATED);

		} catch (IllegalArgumentException e) {
			String errorMessage = e.getMessage();
			if (errorMessage.contains("Invalid userNick")) {
				// userNick 유효성 검사 실패
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 Bad Request: 유효하지 않은 userNick입니다.");
			} else if (errorMessage.contains("허용되지 않은 파일 확장자")) {
				// 파일 확장자 오류
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 Bad Request: 허용되지 않은 파일 확장자");
			} else {
				// 기타 IllegalArgumentException
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("500 Internal Server Error: " + errorMessage);
			}
		} catch (RuntimeException e) {
			// 파일 저장 중 오류 (서버 오류)
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
	}

	// 게시글 삭제
	@DeleteMapping("/{boardId}")
	public ResponseEntity<String> delete(@PathVariable("boardId") int boardId) {
		try {
			// 인증 정보가 없거나 유효하지 않으면 401 Unauthorized 반환 - 아직 인증 관련된 것 안넣어서 주석 처리
//	        if (authorization == null || !isValidToken(authorization)) {
//				System.out.println("HttpStatus 401 Unauthorized");
//            	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("401 Unauthorized: Invalid or missing authorization token");
//        }

			boolean isDeleted = boardService.removeBoard(boardId);

			// 게시물 삭제 성공 (204)
			if (isDeleted)
				return ResponseEntity.noContent().build();

			// 삭제 실패 - 해당 게시물이 존재하지 않음 (404 Not Found)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404 Not Found: Board not found");
		} catch (IllegalArgumentException e) {
			// 잘못된 요청 (400 Bad Request)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 Bad Request: Invalid boardId");
		} catch (Exception e) {
			// 서버 오류 (500 Internal server error)
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
	}

}
