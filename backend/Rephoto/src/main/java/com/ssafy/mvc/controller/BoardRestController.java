package com.ssafy.mvc.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.mvc.model.dto.Board;
import com.ssafy.mvc.model.dto.PaginationResponse;
import com.ssafy.mvc.model.dto.SearchCondition;
import com.ssafy.mvc.model.service.BoardService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController // @Controller + @ResponseBody
@RequestMapping("/board")
@CrossOrigin("*")
public class BoardRestController {

	// 서비스 의존성 주입
	private final BoardService boardService;

	public BoardRestController(BoardService boardService) {
		this.boardService = boardService;
	}

	// 게시물 전체 목록 조회 및 검색 + pagination
	@GetMapping("")
	public ResponseEntity<?> list(@ModelAttribute SearchCondition condition){
		try {
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
			
			// 페이지네이션 정보 추가 (전체 게시물 수, 페이지 크기, 총 페이지 수)
			int total = boardService.getTotalCount(condition); // 전체 게시물 수
			int totalPages = (int) Math.ceil((double) total/condition.getSize()); // 전체 페이지 수 계산
			
			PaginationResponse paginationResponse = new PaginationResponse(list, total, totalPages, condition.getPage(), condition.getSize());

			// 200: 게시물 전체 불러오기 성공
			return new ResponseEntity<>(paginationResponse, HttpStatus.OK);

		} catch (Exception e) {
			// 500: 서버 오류
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
	}

	// 게시글 상세 보기
	@GetMapping("/{boardId}")
	public ResponseEntity<?> detail(@PathVariable("boardId") int boardId) {

		try {
			// 인증 실패 처리 (401 Unauthorized) 아직 인증 관련된 것 안넣어서 주석 처리
//	        if (authorization == null || !isValidToken(authorization)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                                 .body("Unauthorized: Invalid or missing token");
//        }

			// 유효하지 않은 boardId 처리 (400 Bad Request)
			if (boardId <= 0) {
				System.out.println("HttpStatus 400 BAD_REQUEST");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 Bad Request: Invalid boardId");
			}

			// 게시글 조회 (200)
			Board board = boardService.readBoard(boardId);
			System.out.println("게시글 상세 : " + board);
			if (board != null) {
				return ResponseEntity.ok(board);
			}

			// 게시글 없음 (404 Not Found)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404 Not Found: No board with the given ID");
		} catch (Exception e) {
			// 서버 오류 (500 Internal server error)
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
	}

	// 게시물 등록 및 파일 업로드
	@PostMapping("")
	public ResponseEntity<?> write(@RequestParam("file") MultipartFile file, @ModelAttribute Board board) {
		try {
			// 인증 정보가 없거나 유효하지 않으면 401 Unauthorized 반환 - 아직 인증 관련된 것 못넣어서 주석 처리
//	        if (authToken == null || !isValidAuthToken(authToken)) {
//	            System.out.println("HttpStatus 401 Unauthorized");
//	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("401 Unauthorized: Invalid or missing authorization token");
//	        }

			// 게시글 내용이 유효하지 않으면 400 Bad Request 반환
			if (board.getUserNick() == null || board.getUserNick().isEmpty() || board.getBoardTitle() == null
					|| board.getBoardTitle().isEmpty() || board.getBoardInfo() == null
					|| board.getBoardInfo().isEmpty()) {
				System.out.println("HttpStatus 400 BAD_REQUEST");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 Bad Request: Missing required fields");
			}

			// 파일이 있다면 업로드 처리
			if (file != null && !file.isEmpty()) {
				boardService.fileUpload(file, board); // 파일 업로드 및 게시글 등록
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

	// 파일 업로드 - 게시물 등록이랑 통합 (어차피 같이 해야하는 부분이라)
//	@PostMapping("/upload")
//	public ResponseEntity<?> fileUpload(@RequestParam("file") MultipartFile file, @ModelAttribute Board board) {
//
//		try {
//			System.out.println(file.getOriginalFilename());
//			System.out.println(board);
//
//			boardService.fileUpload(file, board);
//
//			// 파일 업로드 성공
//			return new ResponseEntity<>(HttpStatus.OK);
//
//		} catch (IllegalStateException e) {
//			// 파일 확장자 오류
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 Bad Request: 파일 확장자 오류");
//		} catch (RuntimeException e) {
//			// 파일 저장 중 오류 (서버 오류)
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
//		}
//	}

	// // 게시글 수정
//	// swagger를 사용하면 RequestBody 임포트할 때 두개가 뜨는데 import org.springframework.web.bind.annotation.RequestBody; 로 임포트 해야한다 주의!!
//	// 잘 모르겠으면 syso로 board 잘 받아오는지 확인해보기
//	@PutMapping("/board/{id}")
//	public ResponseEntity<Void> update(@PathVariable("id") int id, @RequestBody Board board) {
//		System.out.println(board);
//		board.setId(id);
//		boardService.modifyBoard(board);
//		// 아주 심플하게 무조건 성공! 해놓았지만...
//		// 세밀하게 컨트롤할 수 있는 부분
//		return new ResponseEntity<Void>(HttpStatus.OK);
//	}
//	

	// --- 안씀 ---

	// 게시글 전체 조회
//	@GetMapping("/")
//	@CrossOrigin(value="*", methods = RequestMethod.GET)
//	public ResponseEntity<List<Board>> list() {
//		System.out.println("왔나");
//		List<Board> list = boardService.search(null)
//		return new ResponseEntity<>(list, HttpStatus.OK);
//	}
//	

}
