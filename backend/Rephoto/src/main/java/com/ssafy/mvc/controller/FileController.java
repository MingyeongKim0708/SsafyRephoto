package com.ssafy.mvc.controller;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FileController {
	
	@GetMapping("/download")
	public String fileDownload(@RequestParam("fileUuid") String fileUuid, Model model) {
		model.addAttribute("fileUuid", fileUuid);
		return "fileDownloadView";
	}
}
