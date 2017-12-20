package com.hxy.isw.thread;

import java.util.Formatter;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.util.ConstantUtil;
import com.hxy.isw.util.HttpConnectionUtil;
import com.hxy.isw.util.Sys;



public class ReflushAccessToken implements Runnable {
	
	
	public static String QY_ACCESS_TOKEN;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while (true)
		{
			
			try {
				String result = getAccessTokenByQy();
				
				JsonObject json = (JsonObject) new JsonParser().parse(result);
				
				if(json.get("expires_in")==null){
					
					Sys.out("get accesstoken error sleep one min ...");
					
					Thread.sleep(60*1000);
					
				}else{
					
					int expires_in = Integer.parseInt(json.get("expires_in").getAsString());
					
					Thread.sleep((expires_in-60)*1000);//防止过期，提前一分钟刷新access_token
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private String getAccessTokenByQy(){
		
		String url = ConstantUtil.access_token_url.replace("CorpID", ConstantUtil.CorpID).replace("SECRET", ConstantUtil.QYSECRET);
		
		String result =  HttpConnectionUtil.get(url);
		
		Sys.out("getqyAccessToken..."+result);
		
		JsonObject json = (JsonObject) new JsonParser().parse(result);
		
		String access_token = json.get("access_token").getAsString();
		
		Sys.out("qy_access_token.."+access_token);
		
		QY_ACCESS_TOKEN = access_token;
		
		return result;
		
	}
	
	public static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
	
}
