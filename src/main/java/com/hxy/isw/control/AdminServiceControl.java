package com.hxy.isw.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hxy.isw.service.AdminService;
import com.hxy.isw.util.JsonUtil;

/**
* @author lcc
* @date 2017年11月1日 下午2:39:36
* @describe  后台管理员模块
*/

@Controller
@RequestMapping("/adminService")
public class AdminServiceControl {

	@Autowired
	AdminService adminService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public String login(HttpSession session){
		if(session.getAttribute("loginEmp")==null){
			return "isw/login";
		}else{
			return "isw/index";
			
		}
	}
	
	/**
	 * 异常信息接收
	 * @param request
	 * @param response
	 * @param session
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/getExceptionInfo")
	public void getExceptionInfo(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String info = request.getParameter("info");
			String type = request.getParameter("type");
			
			String json = adminService.getExceptionInfo(info,type);
			JsonUtil.success2client(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	

	@RequestMapping(method = RequestMethod.POST, value = "/uploadfile")
	public void uploadfile(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String info = request.getParameter("info");
			
			String json = adminService.uploadfile(info);
			JsonUtil.success2client(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/downloadfile")
	public void downloadfile(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			
			String info = request.getParameter("info");
			String path = request.getRealPath("/ico");
			String json = adminService.downloadfile(info,path);
			JsonUtil.success2client(response, json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String mess = e.getMessage();
			JsonUtil.success2client(response, "{\"msg\":\"fail\",\"info\":\""+mess+"\"}");
			e.printStackTrace();
		}
	}
	
}
