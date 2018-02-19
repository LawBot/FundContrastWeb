//package cn.com.xiaofabo.tylaw.fundcontrast.controller;
//
//import java.io.File;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import cn.edu.jseti.util.User;
//
//
//
//
//@Controller
//public class ProxyController {
//	private static Log log = LogFactory.getLog(ProxyController.class);
//	
//	/**
//	  * @descr 测试Controller
//	  * @param 
//	  * @return
//	  * @author hzx
//	  * @date 
//	 */
//	@RequestMapping(value = "/login")
//	public String login(HttpServletRequest request,HttpServletResponse response,
//			@RequestParam(value = "userName", required = false) String userName,
//			@RequestParam(value = "password", required = false) String password) throws Exception{
//		
//		return "/index";
//	}
//	
//	@RequestMapping("uploadForm")
//	public String uploadForm() {
//		return "uploadForm";
//	}
//
//	// �ϴ��ļ����Զ��󶨵�MultipartFile��
//	@RequestMapping(value = "/upload", method = RequestMethod.POST)
//	public String upload(HttpServletRequest request, @RequestParam("description") String description,
//			@RequestParam("file") MultipartFile file) throws Exception {
//		System.out.println(description);
//		System.out.println(file);
//		// ����ļ���Ϊ�գ�д���ϴ�·��
//		if (!file.isEmpty()) {
//			// �ϴ��ļ�·��
//			String path = request.getServletContext().getRealPath("/images/");
//			System.out.println(path);
//			// �ϴ��ļ���
//			String filename = file.getOriginalFilename();
//			System.out.println(filename);
//			File filepath = new File(path, filename);
//			System.out.println(filepath);
//			// �ж�·���Ƿ���ڣ���������ھʹ���һ��
//			if (!filepath.getParentFile().exists()) {
//				filepath.getParentFile().mkdirs();
//			}
//			// ���ϴ��ļ����浽һ��Ŀ���ļ�����
//			file.transferTo(new File(path + File.separator + filename));
//			return "success";
//		} else {
//			return "error";
//		}
//
//	}
//
//	@RequestMapping("registerForm")
//	public String registerForm() {
//		return "registerForm";
//	}
//
//	@RequestMapping(value = "/register")
//	public String register(HttpServletRequest request, @ModelAttribute User user, Model model) throws Exception {
//		System.out.println(user.getUsername());
//		// ����ļ���Ϊ�գ�д���ϴ�·��
////		if (!user.getImage().isEmpty()) {
//			// �ϴ��ļ�·��
//			String path = request.getServletContext().getRealPath("/images/");
//			// �ϴ��ļ���
//			String filename = user.getImage().getOriginalFilename();
//			File filepath = new File(path, filename);
//			// �ж�·���Ƿ���ڣ���������ھʹ���һ��
//			if (!filepath.getParentFile().exists()) {
//				filepath.getParentFile().mkdirs();
//			}
//			// ���ϴ��ļ����浽һ��Ŀ���ļ�����
//			user.getImage().transferTo(new File(path + File.separator + filename));
//			// ���û���ӵ�model
//			model.addAttribute("user", user);
//			return "userInfo";
////		} else {
////			return "error";
////		}
//	}
//	
//	@RequestMapping(value = "/download")
//	public ResponseEntity<byte[]> download(HttpServletRequest request, @RequestParam("filename") String filename,
//			Model model) throws Exception {
//		// �����ļ�·��
//		String path = request.getServletContext().getRealPath("/images/");
//		File file = new File(path + File.separator + filename);
//		HttpHeaders headers = new HttpHeaders();
//		// ������ʾ���ļ������������������������
//		String downloadFielName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
//		// ֪ͨ�������attachment�����ط�ʽ����ͼƬ
//		headers.setContentDispositionFormData("attachment", downloadFielName);
//		// application/octet-stream �� �����������ݣ�������ļ����أ���
//		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
//	}
//	
//}
