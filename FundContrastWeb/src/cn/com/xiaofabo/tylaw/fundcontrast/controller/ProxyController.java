package cn.com.xiaofabo.tylaw.fundcontrast.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class ProxyController {
	private static Log log = LogFactory.getLog(ProxyController.class);
	
	/**
	  * @descr 测试Controller
	  * @param 
	  * @return
	  * @author hzx
	  * @date 
	 */
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "password", required = false) String password) throws Exception{
		
		return "/index";
	}
	
}
