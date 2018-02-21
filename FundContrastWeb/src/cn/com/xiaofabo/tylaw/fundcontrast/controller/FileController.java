package cn.com.xiaofabo.tylaw.fundcontrast.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.xiaofabo.tylaw.fundcontrast.entity.FileParam;
import cn.com.xiaofabo.tylaw.fundcontrast.textprocessor.DocGenerator;

@Controller
public class FileController {

    @Autowired
    FileUtil fileUtil;

    /**
     * 上传
     * @param request
     * @return
     */
    @RequestMapping(value="/uploadFile")
    public String uploadFile(HttpServletRequest request){
        init(request);
        try {
            fileUtil.upload(request);
            request.setAttribute("uploadName", request.getParameter("uploadName"));
            request.setAttribute("map", getMap());
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            return "index";
        }
    }
    
    @RequestMapping(value="/doCompare")
    @ResponseBody
    public void compare(HttpServletRequest request){
    	try {
    		String fileName=new String((request.getParameter("fileName")).getBytes("iso-8859-1"),"utf-8");
    		String docPath= request.getSession().getServletContext().getRealPath("/") + "upload/document/"+fileName;
            System.out.println(this.getClass().getClassLoader().getResource("/").getPath()+"/guolv.txt");
            String path = request.getServletContext().getContextPath();
            int errorCode = DocGenerator.generate(docPath, request.getSession().getServletContext().getRealPath("/") +"data/output"+"/条文对照表.docx",request.getSession().getServletContext().getRealPath("/"));
            System.out.println("Result: " + errorCode);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

    /**
     * 页面跳转
     * @param request
     * @return
     */
    @RequestMapping(value="/list")
    public ModelAndView list(HttpServletRequest request){
        init(request);
        request.setAttribute("map", getMap());
        return new ModelAndView("fileOperate/filelist");
    }

    /**
     * 加载页面，异步初始化文件夹中的文件
     * @param request
     * @return
     */
    @RequestMapping(value="/dataList")
    @ResponseBody
    public List<FileParam> initData(HttpServletRequest request){
        init(request);
        List<FileParam> fileList = fileUtil.listFiles(FileUtil.FILEDIR);
        return fileList;
    }

    /**
     * 下载
     * @param request
     * @param response
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping(value="/download")
    @ResponseBody
    public void download(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
    	String fileName=new String((request.getParameter("fileName")).getBytes("iso-8859-1"),"utf-8");
		String docPath= request.getSession().getServletContext().getRealPath("/") + "upload/document/"+fileName;
        System.out.println(this.getClass().getClassLoader().getResource("/").getPath()+"/guolv.txt");
        String path = request.getSession().getServletContext().getRealPath("/");
        int errorCode = DocGenerator.generate(docPath, request.getSession().getServletContext().getRealPath("/") +"data/output"+"/条文对照表.docx",path);
        System.out.println("Result: " + errorCode);
        init(request);
        try {
            String downloadfFileName = "条文对照表.docx";
//            downloadfFileName = new String(downloadfFileName.getBytes("iso-8859-1"),"utf-8");
//            String fileName = downloadfFileName.substring(downloadfFileName.indexOf("_")+1);
            String userAgent = request.getHeader("User-Agent").toLowerCase();
            byte[] bytes = (userAgent.contains("msie")||userAgent.contains("like gecko")) ? downloadfFileName.getBytes() : downloadfFileName.getBytes("UTF-8");
            downloadfFileName = new String(bytes, "ISO-8859-1");
            response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", downloadfFileName));
            fileUtil.download(request.getSession().getServletContext().getRealPath("/") +"data/output"+"/条文对照表.docx", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
//            return "1";
        }
    }

    /**
     * @description 使用异步的方式删除文件
     * @date 2016年7月21日 上午11:03:57
     * @param fileName
     * @param request
     * @return Map<String,String>
     */
    @RequestMapping(value="/remove")
    @ResponseBody
    public Map<String, String> removeFile(String fileName,HttpServletRequest request){
        Map<String, String> map = new HashMap<String, String>();
//        String dir = fileName.substring(0, 8);
        boolean flag = false;
        File file = new File(FileUtil.FILEDIR+"/"+fileName);
        flag = file.delete();
        if(flag){
            map.put("result", "success");
        }
        return map;
    }

    /**
     * 初始化路径
     * @param request
     */
    private void init(HttpServletRequest request) {
        if(FileUtil.FILEDIR == null){
            FileUtil.FILEDIR = request.getSession().getServletContext().getRealPath("/") + "upload/document";
        }
    }

    /**
     * getMap
     * @return
     */
    private Map<String, String> getMap(){
        Map<String, String> map = new HashMap<String, String>();
        File[] files = new File(FileUtil.FILEDIR).listFiles();
        if(files != null){
            for (File file : files) {
                if(file.isDirectory()){
                    File[] files2 = file.listFiles();
                    if(files2 != null){
                        for (File file2 : files2) {
                            String name = file2.getName();
//                            map.put(file2.getParentFile().getName() + "/" + name, name.substring(name.lastIndexOf("_")+1));
                            map.put(file2.getParentFile().getName() + "/" + name, name);
                        }
                    }
                }
            }
        }
        return map;
    }
}