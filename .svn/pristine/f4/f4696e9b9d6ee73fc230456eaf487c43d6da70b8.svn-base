package com.hxy.isw.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hxy.isw.service.AdminService;
import com.hxy.isw.thread.ReflushAccessToken;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.DatabaseHelper;
import com.hxy.isw.util.JsonUtil;
import com.hxy.isw.util.UploadFileToWx;

/**
* @author lcc
* @date 2017年11月1日 下午2:41:51
* @describe
*/

@Repository
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	DatabaseHelper databaseHelper;
	

	/**
	 * 异常信息接收
	 * @param info  
	 * @return  登录信息
	 * @throws Exception
	 */
	@Override
	public String getExceptionInfo(String info,String type) throws Exception {
		
		if("text".equals(type)){
			String title="时间\\t班次\\t\\t指标名\\t预警值\\t正常值\n", msgA = "",msgB="";
			JsonArray arr	 = (JsonArray) JsonUtil.getJsonParser().parse(info);
			int noA = 1,noB =1;
			for (JsonElement jsonElement : arr) {
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				String exceptionType = jsonObject.get("exceptionType")==null?"":jsonObject.get("exceptionType").getAsString();
				String exceptionName = jsonObject.get("exceptionName")==null?"":jsonObject.get("exceptionName").getAsString();
				String exceptionValue = jsonObject.get("exceptionValue")==null?"":jsonObject.get("exceptionValue").getAsString();
				String exceptionDate = jsonObject.get("exceptionDate")==null?"":jsonObject.get("exceptionDate").getAsString();
				String exceptionTime = jsonObject.get("exceptionTime")==null?"":jsonObject.get("exceptionTime").getAsString();
				String normalRange = jsonObject.get("normalRange")==null?"":jsonObject.get("normalRange").getAsString();
				if("A".equals(exceptionType)){
					msgA +=noA+"、时间："+exceptionDate+"\t\n班次："+exceptionTime+"\t\n指标名："+exceptionName+"\t\n预警值："+exceptionValue+"\t\n正常值："+normalRange+"\n";
//					msgA +=exceptionDate+"\\t"+exceptionTime+"\\t"+exceptionName+"\\t"+exceptionValue+"\\t"+normalRange+"\n";
					noA++;
				}else if("B".equals(exceptionType)){
					msgB +=noB+"、时间："+exceptionDate+"\t\n班次："+exceptionTime+"\t\n指标名："+exceptionName+"\t\n预警值："+exceptionValue+"\t\n正常值："+normalRange+"\n";
//					msgB +=exceptionDate+"\\t"+exceptionTime+"\\t"+exceptionName+"\\t"+exceptionValue+"\\t"+normalRange+"\n";
					noB++;
				}
				
			}
			if(msgA.length()>0){
				boolean flag = ConstantUtil.sendTextMsgToCusByQy(msgA, ConstantUtil.agentId,1);
				System.out.println("msgA..send.."+flag);
			}
			if(msgB.length()>0){
				boolean flag = ConstantUtil.sendTextMsgToCusByQy(msgB, ConstantUtil.agentId,2);
				System.out.println("msgB..send.."+flag);
			}
			return "true";
		}else{
			boolean flag = ConstantUtil.sendFileMsgToCusByQy(info, ConstantUtil.agentId);
			return String.valueOf(flag);
		}
		
	}


	@Override
	public String uploadfile(String info) throws Exception {
		// TODO Auto-generated method stub
		
		File file = new File(info);
		
		String result = UploadFileToWx.uploadMedia(ReflushAccessToken.QY_ACCESS_TOKEN, "file", file);
		
		return result;
	}


	@Override
	public String downloadfile(String info,String path) throws Exception {
		// TODO Auto-generated method stub
		
		String result = UploadFileToWx.downloadMedia(ReflushAccessToken.QY_ACCESS_TOKEN, info, path);
		return result;
	}

	
}
