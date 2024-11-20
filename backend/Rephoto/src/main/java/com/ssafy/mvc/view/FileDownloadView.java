package com.ssafy.mvc.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.ssafy.mvc.model.dao.BoardDao;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FileDownloadView extends AbstractView{

	//스프링에서 파일이나 클래스 등등 리소스를 로드할 때는 ResourceLoader 인터페이스를 이용해서 가져온다
	private ResourceLoader resourceLoader;
	
	private final BoardDao boardDao;
	
	public FileDownloadView(ResourceLoader resourceLoader, BoardDao boardDao){
		this.resourceLoader = resourceLoader;
		this.boardDao = boardDao;
	}
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String fileUuid = (String)model.get("fileUuid");
		String originalFileName = boardDao.getFileNameByUuid(fileUuid);  // UUID로 원본 파일명 조회

		// 파일 경로 설정 (원본 파일명 사용)
		Resource resource = resourceLoader.getResource("classpath:/static/img");
		File file = new File(resource.getFile(), fileUuid);
		
		
		// 응답 헤더 설정
		String encodedFileName = new String(originalFileName.getBytes("UTF-8"), "ISO-8859-1"); //한글같은데 다른 브라우저 혹은 어딘가에서 깨질 수 있다.
		response.setHeader("Content-Disposition","attachment; fileName=\""+encodedFileName+"\";") ;
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		// 파일을 클라이언트로 전송
		System.out.println(1000000);
		try(FileInputStream fis = new FileInputStream(file);OutputStream os = response.getOutputStream()) {
			System.out.println(1000001);
			FileCopyUtils.copy(fis, os);
			System.out.println(1000002);
		}
		
	}
	
}
