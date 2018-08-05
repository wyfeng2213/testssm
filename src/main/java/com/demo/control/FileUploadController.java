package com.demo.control;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/user")
public class FileUploadController {

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	private String fildUpload(@RequestParam(value = "file", required = false) MultipartFile imageFile,
			HttpServletRequest request) throws Exception {
		// 获取文件的上传路径
		String uploadUrl = request.getSession().getServletContext().getRealPath("/") + "upload/";
		// 获取上传的文件名字
		String filename = imageFile.getOriginalFilename();
		// 判断文件是否存在
		File dir = new File(uploadUrl);
		if (!dir.exists()) {
			dir.mkdirs(); // 创建一个
		}
		System.out.println("文件上传到: " + uploadUrl + filename);

		File targetFile = new File(uploadUrl + filename);
		if (!targetFile.exists()) {
			try {
				targetFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			imageFile.transferTo(targetFile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 重定向到文件
		return "success";
	}

	// 因为我的JSP在WEB-INF目录下面，浏览器无法直接访问
	@RequestMapping(value = "/forward")
	private String forward() {
		return "index";
	}

}
