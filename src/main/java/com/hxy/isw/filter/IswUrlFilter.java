package com.hxy.isw.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.Sys;

public class IswUrlFilter implements Filter {
	
	public static DatabaseHelper databaseHelper;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		requestlog(req);
		
		String url = req.getRequestURI();
		
		Date now = new Date();
		//Sys.out("ConstantUtil.environment:"+ConstantUtil.environment);
		//log(request, response);
		// url like /sms/company/findall.xhtml
		if( ConstantUtil.TEXT ){
			if(ConstantUtil.environment.equals("maven"))//maven
				url = url.replace(req.getContextPath()+"/","");
			else //tomcat
				url = url.substring(1);
		}else 
			url = url.substring(1, url.length());
		
		
		url = url.replace(".action","");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Sys.out(sdf.format(now)+"...url..."+url);
		
		HttpSession session = req.getSession(true);
		Object obj = session.getAttribute("loginEmp");
		
		if(url.indexOf("Service")!=-1){
			//来自客户端的请求
			String signature = req.getParameter("signature");
			String timestamp = req.getParameter("timestamp");
			String nonce = req.getParameter("nonce");
			
			Long times = now.getTime();
			 
			 Sys.out("req..timestamp.."+timestamp);
			 Sys.out("ser..timestamp.."+times);
			 
			 /* Long l_timestamp = Long.parseLong(timestamp);
			   	if(times-l_timestamp>60*1000l){
				 JsonUtil.success2page(resp, "{\"msg\":\"fail\",\"info\":\"签名过期\"}");
				 return;
			 }*/
			if(url.indexOf("file")==-1&&url.indexOf("getExceptionInfo")==-1){
				boolean flag = ConstantUtil.checkSignature(signature, timestamp, nonce);
				if(!flag){
					JsonUtil.success2page(resp, "{\"msg\":\"fail\",\"info\":\"签名错误\"}");
					return;
				}
			}
			
			chain.doFilter(req, resp);
			return;
		}else{//后台系统
			if(obj==null &&url.indexOf("doLogin")==-1&&url.indexOf("testfromvue")==-1){//除登录请求外，其他请求在session过期时需重新登录
				
				JsonUtil.timeout2page(resp);
				return;
				
			}else{//系统内部可通过的接口
				chain.doFilter(request, response);
				return;
			}
			
		}
		
		
	}

	/*private void userlogin(){
		//记录用户活跃时间
		String userid = req.getParameter("userid");
		if(userid!=null&&userid.length()>0){
			
			//检查该用户今天是否有活跃记录
			StringBuffer check = new StringBuffer("select ual from UserActiveLog ual where ual.fuserid = ").append(Long.parseLong(userid))
					.append(" and  date(ual.createtime) = curdate()");
			
			List<Object> lst =databaseHelper.getResultListByHql(check.toString());
			if(lst.size()>0){
				UserActiveLog ual = (UserActiveLog)lst.get(0);
				ual.setLasttime(now);
				ual.setUserreq(url);
				databaseHelper.updateObject(ual);
			}else{
				UserActiveLog ual = new  UserActiveLog();
				ual.setCreatetime(now);
				ual.setFuserid(Long.parseLong(userid));
				ual.setLasttime(now);
				ual.setUserreq(url);
				databaseHelper.persistObject(ual);
			}
		}
	}*/
	

	@Override
	public void destroy() {

	}
	
	
	private void requestlog(HttpServletRequest req){
		Map map=req.getParameterMap();  
	    Set keSet=map.entrySet();  
	    for(Iterator itr=keSet.iterator();itr.hasNext();){  
	        Map.Entry me=(Map.Entry)itr.next();  
	        Object ok=me.getKey();  
	        Object ov=me.getValue();  
	        String[] value=new String[1];  
	        if(ov instanceof String[]){  
	            value=(String[])ov;  
	        }else{  
	            value[0]=ov.toString();  
	        }  
	  
	        for(int k=0;k<value.length;k++){  
	            Sys.out(ok+"="+value[k]);  
	        }  
	      }  
	}
	
	
}
