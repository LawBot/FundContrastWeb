package cn.com.xiaofabo.tylaw.fundcontrast.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.xiaofabo.tylaw.fundcontrast.entity.FileParam;

@Repository("fileUtil")
public class FileUtil {
	public static String FILEDIR = null;
	/**
	 * 上传
	 * @param request
	 * @throws IOException
	 */
	public void upload(HttpServletRequest request) throws IOException{
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = mRequest.getFileMap();
//        File file = new File(FILEDIR);
//        if (!file.exists()) {
//            file.mkdir();
//        }
		Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, MultipartFile> entry = it.next();
			MultipartFile mFile = entry.getValue();
			if(mFile.getSize() != 0 && !"".equals(mFile.getName())){
				write(mFile.getInputStream(), new FileOutputStream(initPath(mFile.getOriginalFilename())));
			}
		}
	}

//    private static String initFilePath(String name) {
////        String dir = getFileDir(name) + "";
//        File file = new File(FILEDIR);
//        if (!file.exists()) {
//            file.mkdir();
//        }
////        Long num = new Date().getTime();
////        Double d = Math.random()*num;
//        return (file.getPath() + "/" + name).replaceAll(" ", "-");
//    }

//    private static int getFileDir(String name) {
//        return name.hashCode() & 0xf;
//    }

	/**
	 * 下载
	 * @param downloadfFileName
	 * @param out
	 */
	public void download(String downloadfFileName, ServletOutputStream out) {
		try {
			FileInputStream in = new FileInputStream(downloadfFileName);
			write(in, out);
		} catch (FileNotFoundException e) {
			try {
				FileInputStream in = new FileInputStream(new File(FILEDIR + "/"
						+ new String(downloadfFileName.getBytes("iso-8859-1"),"utf-8")));
				write(in, out);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写入数据
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public void write(InputStream in, OutputStream out) throws IOException{
		try{
			byte[] buffer = new byte[1024];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			out.flush();
		} finally {
			try {
				in.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
			try {
				out.close();
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 路径处理
	 * @param path
	 * @return
	 */
	private File initPath(String path){
//		Calendar date = Calendar.getInstance();
//		String month=date.get(Calendar.MONTH)+1+"";
//		if(date.get(Calendar.MONTH)+1<10)
//			month = "0"+(date.get(Calendar.MONTH)+1);
//		String time = ""+date.get(Calendar.YEAR)+month+date.get(Calendar.DAY_OF_MONTH);
		String targetPath = FILEDIR;
		File temp = new File(targetPath);
		if(!temp.exists())
			temp.mkdirs();
		return new File(targetPath+"/"+path);
	}


	/**
	 * @description 获取文件
	 * @date 2016年7月21日 上午11:02:57
	 * @param path
	 * @return List<FileParam>
	 */
	public List<FileParam> listFiles(File path){
		List<FileParam> fileList = new ArrayList<FileParam>();
		if(path!=null&&path.exists()&&path.isDirectory()){
			fileList = eachFile(path,fileList);
		}
		return fileList;
	}

	/**
	 * @description listFiles方法重载，输入参数可以是String类型
	 * @date 2016年7月21日 上午11:03:13
	 * @param path
	 * @return List<FileParam>
	 */
	public List<FileParam> listFiles(String path){
		return listFiles(new File(path));
	}

	/**
	 * @description 使用迭代的方式获取upload文件加下的文件
	 * @date 2016年7月21日 上午11:03:17
	 * @param file
	 * @param fileList
	 * @return List<FileParam>
	 */
	private List<FileParam> eachFile(File file,List<FileParam>fileList){
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for (File thisFile : files) {
				eachFile(thisFile,fileList);
			}
		}else{
			FileParam fileDto = new FileParam();
			String name = file.getName();
			String type = name.substring(name.lastIndexOf(".")!=-1?name.lastIndexOf("."):name.length()-1);
			fileDto.setFileName(name);
			fileDto.setFileType(type);
			fileDto.setFilePath(file.getPath());
			fileDto.setSize(file.length());
			fileList.add(fileDto);
		}
		return fileList;
	}

}


