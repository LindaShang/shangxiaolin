package com.hxy.isw.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hxy.isw.filter.IswUrlFilter;
import com.hxy.isw.thread.ReflushAccessToken;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.SessionUtils;
import com.hxy.isw.util.Sys;
import com.hxy.isw.util.ThreadPoolManager;



@Controller
public class IndexControl {
	
	@Autowired
	DatabaseHelper databaseHelper;
	
	@PostConstruct
	public void init() throws Exception{
		//Sys.out(MD5.JM("123456"));
		String path = this.getClass().getResource("/").getPath();
	
		initialize();
		
		if(path.indexOf("/workspace/")!=-1){
			ConstantUtil.environment = "maven";
		}else{
			ConstantUtil.environment = "tomcat";
		}
		
		Sys.out("ConstantUtil.environment:"+ConstantUtil.environment);
		
		ConstantUtil.PROJECT_PATH = path;
		
		//启动reflushAccessToken
		ReflushAccessToken reflushAccessToken = new ReflushAccessToken();
		ThreadPoolManager.exec(reflushAccessToken);
		
	}
	
	@PreDestroy
	public void destroy(){
		
	}
	
	
	private void  initialize(){
		
	}
	
	
}
