package com.hxy.isw.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
* @author lcc
* @date 2017年11月6日 下午2:28:21
* @describe
*/

public class UploadFileToWx {

	/** 
	 * 上传媒体文件 
	 * @param accessToken 接口访问凭证 
	 * @param type 媒体文件类型，分别有图片（image）、语音（voice）、视频（video），普通文件(file) 
	 * @param media form-data中媒体文件标识，有filename、filelength、content-type等信息 
	 * @param mediaFileUrl 媒体文件的url 
	 * 上传的媒体文件限制 
	    * 图片（image）:1MB，支持JPG格式 
	    * 语音（voice）：2MB，播放长度不超过60s，支持AMR格式 
	    * 视频（video）：10MB，支持MP4格式 
	    * 普通文件（file）：10MB 
	 * */  
	public static String uploadMedia(String accessToken, String type, File file) {  
		
		BufferedReader reader = null;  
        String result = null;
	    // 拼装请求地址  
	    String uploadMediaUrl = ConstantUtil.uploadMediaUrl.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);  
	  
	    try {
			URL urlObj = new URL(uploadMediaUrl);  
			HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();  
			con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式  
			con.setDoInput(true);  
			con.setDoOutput(true);  
			con.setUseCaches(false); // post方式不能使用缓存  
			// 设置请求头信息  
			con.setRequestProperty("Connection", "Keep-Alive");  
			con.setRequestProperty("Charset", "UTF-8");  
			// 设置边界  
			String BOUNDARY = "----------" + System.currentTimeMillis();  
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ BOUNDARY);  
			// 请求正文信息  
			// 第一部分：  
			StringBuilder sb = new StringBuilder();  
			sb.append("--"); // 必须多两道线  
			sb.append(BOUNDARY);  
			sb.append("\r\n");  
			sb.append("Content-Disposition: form-data;name=\"file\";filename=\""+ file.getName() + "\"\r\n");  
			sb.append("Content-Type:application/octet-stream\r\n\r\n");  
			
			byte[] head = sb.toString().getBytes("utf-8");  
			// 获得输出流  
			OutputStream out = new DataOutputStream(con.getOutputStream());  
			// 输出表头  
			out.write(head);  
			// 文件正文部分  
			// 把文件已流文件的方式 推入到url中  
			DataInputStream in = new DataInputStream(new FileInputStream(file));  
			int bytes = 0;  
			byte[] bufferOut = new byte[1024];  
			while ((bytes = in.read(bufferOut)) != -1) {  
				out.write(bufferOut, 0, bytes);  
			}  
			in.close();  
			// 结尾部分  
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线  
			out.write(foot);  
			out.flush();  
			out.close();  
			
			StringBuffer buffer = new StringBuffer();  
			
			  
			// 定义BufferedReader输入流来读取URL的响应  
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));  
			String line = null;  
			while ((line = reader.readLine()) != null) {  
				buffer.append(line);  
			}  
			if(result==null){  
				result = buffer.toString();  
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {  
	        try {
				if(reader!=null){  
				    reader.close();  
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	    }    
       
        return result; 
	  
	       
	}  
	
	
	
    /** 
     * 获取媒体文件 
     * @param accessToken 接口访问凭证 
     * @param media_id 媒体文件id 
     * @param savePath 文件在服务器上的存储路径 
     * */  
    public static String downloadMedia(String accessToken, String mediaId, String savePath) {  
        String filePath = null;  
        // 拼接请求地址  
        String requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";  
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);  
        System.out.println(requestUrl);  
        try {  
            URL url = new URL(requestUrl);  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            conn.setDoInput(true);  
            conn.setRequestMethod("GET");  
  
            if (!savePath.endsWith("/")) {  
                savePath += "/";  
            }  
            // 根据内容类型获取扩展名  
            Sys.out("contentType.."+conn.getHeaderField("Content-Type"));
            String fileExt = WeixinUtil.getFileEndWitsh(conn.getHeaderField("Content-Type"));  
            // 将mediaId作为文件名  
            filePath = savePath + mediaId + fileExt;  
            Sys.out("filePath.."+filePath);
  
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());  
            FileOutputStream fos = new FileOutputStream(new File(filePath));  
            byte[] buf = new byte[8096];  
            int size = 0;  
            while ((size = bis.read(buf)) != -1)  
                fos.write(buf, 0, size);  
            fos.close();  
            bis.close();  
  
            conn.disconnect();  
            String info = String.format("下载媒体文件成功，filePath=" + filePath);  
            System.out.println(info);  
        } catch (Exception e) {  
            filePath = null;  
            String error = String.format("下载媒体文件失败：%s", e);  
            System.out.println(error);  
        }  
        return filePath;  
    }  
}
