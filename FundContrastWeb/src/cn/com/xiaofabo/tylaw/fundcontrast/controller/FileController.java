package cn.com.xiaofabo.tylaw.fundcontrast.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sun.xml.internal.fastinfoset.sax.Properties;

import cn.com.xiaofabo.tylaw.fundcontrast.entity.FileParam;
import cn.com.xiaofabo.tylaw.fundcontrast.textprocessor.DocGenerator;
import cn.com.xiaofabo.tylaw.fundcontrast.utils.SystemCommandExecutor;
import cn.com.xiaofabo.tylaw.fundcontrast.utils.TextUtils;

@Controller
public class FileController {

	@Autowired
	FileUtil fileUtil;

	// @Value("${config.aaa}")
	// private String attr1;
	/**
	 * 上传
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadFile2")
	// @ResponseBody
	public void uploadFile2(HttpServletRequest request, ModelMap map, HttpServletResponse response) {
		init(request);
		try {
			fileUtil.upload(request);
			request.setAttribute("uploadName2", request.getParameter("uploadName2"));
			request.setAttribute("map", getMap());
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(-1);
			/// File move for case when user uploads 2 files with same name
			String originalUploadDir = session.getServletContext().getRealPath("/") + "upload/document/";
			String destUploadDir = originalUploadDir + "upload2/";
			File destDir = new File(destUploadDir);
			if (!destDir.exists()) {
				destDir.mkdir();
			}
			Path tmp = Files.move(Paths.get(originalUploadDir + request.getParameter("uploadName2")),
					Paths.get(destUploadDir + request.getParameter("uploadName2")));
			request.getSession().setAttribute("docPath", destUploadDir + request.getParameter("uploadName2"));
			System.out.println("Upload file 2: " + request.getSession().getAttribute("docPath"));
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
			// return -1;
		}
	}

	@RequestMapping(value = "/uploadFile1")
	// @ResponseBody
	public void uploadFile1(HttpServletRequest request, ModelMap map, HttpServletResponse response) {
		init(request);
		try {
			fileUtil.upload(request);
			request.setAttribute("uploadName1", request.getParameter("uploadName1"));
			request.setAttribute("map", getMap());
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(-1);

			/// File move for case when user uploads 2 files with same name
			String originalUploadDir = session.getServletContext().getRealPath("/") + "upload/document/";
			String destUploadDir = originalUploadDir + "upload1/";
			File destDir = new File(destUploadDir);
			if (!destDir.exists()) {
				destDir.mkdir();
			}
			Path tmp = Files.move(Paths.get(originalUploadDir + request.getParameter("uploadName1")),
					Paths.get(destUploadDir + request.getParameter("uploadName1")));

			// String filename = request.getParameter("uploadName1");
			// String extension = "";
			// if (filename.contains(".")) {
			// extension = filename.substring(filename.lastIndexOf("."));
			// }
			// String genFileName = TextUtils.getAlphaNumericString(15) + extension;
			// File beforeRename = new File(session.getServletContext().getRealPath("/") +
			// "upload/document/" + filename);
			// File afterRename = new File(
			// session.getServletContext().getRealPath("/") + "upload/document/" +
			// genFileName);
			// boolean renameSuccess = beforeRename.renameTo(afterRename);
			request.getSession().setAttribute("orignDocPath", destUploadDir + request.getParameter("uploadName1"));
			System.out.println("Upload file 1: " + request.getSession().getAttribute("orignDocPath"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/doCompare")
	@ResponseBody
	public void compare(HttpServletRequest request) {
		try {
			// String fileName=new
			// String((request.getParameter("fileName")).getBytes("iso-8859-1"),"utf-8");
			// String docPath= request.getSession().getServletContext().getRealPath("/") +
			// "upload/document/"+fileName;
			// System.out.println(this.getClass().getClassLoader().getResource("/").getPath()+"/guolv.txt");
			String docPath = (String) request.getSession().getAttribute("docPath");
			String orignDocPath = (String) request.getSession().getAttribute("orignDocPath");
			// int errorCode = DocGenerator.generate(docPath,orignDocPath,
			// request.getSession().getServletContext().getRealPath("/")
			// +"data/output"+"/条文对照表.docx",request.getSession().getServletContext().getRealPath("/"),"","");
			// System.out.println("Result: " + errorCode);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 页面跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(HttpServletRequest request) {
		init(request);
		request.setAttribute("map", getMap());
		return new ModelAndView("fileOperate/filelist");
	}

	/**
	 * 加载页面，异步初始化文件夹中的文件
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dataList")
	@ResponseBody
	public List<FileParam> initData(HttpServletRequest request) {
		init(request);
		List<FileParam> fileList = fileUtil.listFiles(FileUtil.FILEDIR);
		return fileList;
	}

	/**
	 * Check file syntax errors
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/errorCheck")
	@ResponseBody
	public void errorCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String originDocPath = (String) request.getSession().getAttribute("orignDocPath");
		String outputFile = request.getSession().getServletContext().getRealPath("/")
				+ "python/word-diff/bin/pack_tmp/dist/check_result.docx";
		String usageCountLogFile = request.getSession().getServletContext().getRealPath("/") + "log/UsageCount.txt";

		System.out.println("Original doc path: " + originDocPath);
		System.out.println("Output path:" + outputFile);
		List<String> command = new ArrayList<String>();
		command.add(request.getSession().getServletContext().getRealPath("/")
				+ "python/word-diff/bin/pack_tmp/dist/check_start.exe");
		command.add(originDocPath);
		command.add(outputFile);

		SystemCommandExecutor commandExecutor = new SystemCommandExecutor(command);
		int result = commandExecutor.executeCommand();
		System.out.println("Python execution result: " + result);
		StringBuilder stdout = commandExecutor.getStandardOutputFromCommand();
		String[] outStrList = stdout.toString().split("\n");
		String lastLine = "";
		for (int i = outStrList.length - 1; i >= 0; --i) {
			if (!outStrList[i].isEmpty()) {
				lastLine = outStrList[i];
				break;
			}
		}
		System.out.println("Last line output: " + lastLine);
		String errorMessage = "";
		if (lastLine != null && !lastLine.trim().isEmpty() && !lastLine.equalsIgnoreCase("0")) {
			errorMessage = lastLine.substring(lastLine.indexOf(":")).trim();
		}

		// downloadfFileName = new
		// String(downloadfFileName.getBytes("iso-8859-1"),"utf-8");
		// String fileName =
		// downloadfFileName.substring(downloadfFileName.indexOf("_")+1);
		String userAgent = request.getHeader("User-Agent").toLowerCase();
		byte[] bytes = (userAgent.contains("msie") || userAgent.contains("like gecko")) ? outputFile.getBytes()
				: outputFile.getBytes("UTF-8");
		outputFile = new String(bytes, "ISO-8859-1");
		response.setHeader("Content-disposition",
				String.format("attachment; filename=\"%s\"", "errorcheck_result.docx"));
		fileUtil.download(outputFile, response.getOutputStream());
		
		File inFile = new File(originDocPath);
		File outFile = new File(outputFile);
		
		if (inFile.delete()) {
			System.out.println("File deleted: " + originDocPath);
		}
		if (outFile.delete()) {
			System.out.println("File deleted: " + outputFile);
		}

		/// BEGIN: Usage count file creation/editing
		File logDir = new File(request.getSession().getServletContext().getRealPath("/") + "log");
		if (!logDir.exists()) {
			logDir.mkdir();
		}
		File usageCountFile = new File(usageCountLogFile);
		if (!usageCountFile.exists()) {
			usageCountFile.createNewFile();
		}

		try {
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String strDate = sdfDate.format(now);
			String usageStr = "[Error Check]@" + strDate;
			Files.write(Paths.get(usageCountLogFile), usageStr.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.out.println("ERROR: Cannot find usage count file.");
		}
		/// END: Usage count file creation/editing
	}

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/download")
	@ResponseBody
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 对比
			String docPath = (String) request.getSession().getAttribute("docPath");
			String orignDocPath = (String) request.getSession().getAttribute("orignDocPath");
			String outputFile = request.getSession().getServletContext().getRealPath("/")
					+ "python/word-diff/bin/pack_tmp/dist/diff_result.docx";
			String usageCountLogFile = request.getSession().getServletContext().getRealPath("/") + "log/UsageCount.txt";

			System.out.println("Doc 1 path: " + docPath);
			System.out.println("Doc 2 path: " + orignDocPath);

			System.out.println("Session path: " + request.getSession().getServletContext().getRealPath("/"));

			/// Execute python diff-word
			List<String> command = new ArrayList<String>();
			// command.add("python");
			command.add(request.getSession().getServletContext().getRealPath("/")
					+ "python/word-diff/bin/pack_tmp/dist/diff_start.exe");
			command.add(docPath);
			command.add(orignDocPath);
			command.add(outputFile);

			SystemCommandExecutor commandExecutor = new SystemCommandExecutor(command);
			int result = commandExecutor.executeCommand();
			System.out.println("Python execution result: " + result);
			StringBuilder stdout = commandExecutor.getStandardOutputFromCommand();
			String[] outStrList = stdout.toString().split("\n");
			String lastLine = "";
			for (int i = outStrList.length - 1; i >= 0; --i) {
				if (!outStrList[i].isEmpty()) {
					lastLine = outStrList[i];
					break;
				}
			}
			System.out.println("Last line output: " + lastLine);
			String errorMessage = "";
			if (lastLine != null && !lastLine.trim().isEmpty() && !lastLine.equalsIgnoreCase("0")) {
				errorMessage = lastLine.substring(lastLine.indexOf(":")).trim();
			}

			// downloadfFileName = new
			// String(downloadfFileName.getBytes("iso-8859-1"),"utf-8");
			// String fileName =
			// downloadfFileName.substring(downloadfFileName.indexOf("_")+1);
			String userAgent = request.getHeader("User-Agent").toLowerCase();
			byte[] bytes = (userAgent.contains("msie") || userAgent.contains("like gecko")) ? outputFile.getBytes()
					: outputFile.getBytes("UTF-8");
			outputFile = new String(bytes, "ISO-8859-1");
			response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", "diff_result.docx"));
			fileUtil.download(outputFile, response.getOutputStream());

			File doc1 = new File(docPath);
			File doc2 = new File(orignDocPath);
			File outDoc = new File(outputFile);

			if (doc1.delete()) {
				System.out.println("File deleted: " + doc1);
			}
			if (doc2.delete()) {
				System.out.println("File deleted: " + doc2);
			}
			if (outDoc.delete()) {
				System.out.println("File deleted: " + outDoc);
			}

			/// BEGIN: Usage count file creation/editing
			File logDir = new File(request.getSession().getServletContext().getRealPath("/") + "log");
			if (!logDir.exists()) {
				logDir.mkdir();
			}
			File usageCountFile = new File(usageCountLogFile);
			if (!usageCountFile.exists()) {
				usageCountFile.createNewFile();
			}

			try {
				SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				String strDate = sdfDate.format(now);
				String usageStr = "[Fund Contrast]@" + strDate;
				Files.write(Paths.get(usageCountLogFile), usageStr.getBytes(), StandardOpenOption.APPEND);
			} catch (IOException e) {
				System.out.println("ERROR: Cannot find usage count file.");
			}
			/// END: Usage count file creation/editing
		} catch (Exception e) {
			e.printStackTrace();
			// return "1";
		}
	}

	/**
	 * @description 使用异步的方式删除文件
	 * @date 2016年7月21日 上午11:03:57
	 * @param fileName
	 * @param request
	 * @return Map<String,String>
	 */
	@RequestMapping(value = "/remove")
	@ResponseBody
	public Map<String, String> removeFile(String fileName, HttpServletRequest request) {
		System.out.println("Remove unnecessary files");
		Map<String, String> map = new HashMap<String, String>();
		// String dir = fileName.substring(0, 8);
		boolean flag = false;
		File file = new File(FileUtil.FILEDIR + "/" + fileName);
		flag = file.delete();
		if (flag) {
			map.put("result", "success");
		}
		return map;
	}

	/**
	 * 初始化路径
	 * 
	 * @param request
	 */
	private void init(HttpServletRequest request) {
		if (FileUtil.FILEDIR == null) {
			FileUtil.FILEDIR = request.getSession().getServletContext().getRealPath("/") + "upload/document";
		}
	}

	/**
	 * getMap
	 * 
	 * @return
	 */
	private Map<String, String> getMap() {
		Map<String, String> map = new HashMap<String, String>();
		File[] files = new File(FileUtil.FILEDIR).listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					File[] files2 = file.listFiles();
					if (files2 != null) {
						for (File file2 : files2) {
							String name = file2.getName();
							// map.put(file2.getParentFile().getName() + "/" + name,
							// name.substring(name.lastIndexOf("_")+1));
							map.put(file2.getParentFile().getName() + "/" + name, name);
						}
					}
				}
			}
		}
		return map;
	}
}