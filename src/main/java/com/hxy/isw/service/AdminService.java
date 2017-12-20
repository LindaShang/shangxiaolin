package com.hxy.isw.service;
/**
* @author lcc
* @date 2017年11月1日 下午2:20:49
* @describe
*/
public interface AdminService {

	
	public String getExceptionInfo(String info,String type) throws Exception;
	public String uploadfile(String info) throws Exception;
	public String downloadfile(String info,String path) throws Exception;
	
	
}
