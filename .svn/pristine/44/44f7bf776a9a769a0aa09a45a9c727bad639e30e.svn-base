package com.hxy.isw.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.w3c.dom.Document;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hxy.isw.thread.ReflushAccessToken;

import sun.misc.BASE64Decoder;

public class ConstantUtil {

	public ConstantUtil() {
		super();
	}
	
	public static boolean TEXT = true;
	public static boolean SYSOUT = true; //是否开启控制台输出  system.out.print
	public static String PASSWORD;
	public static String URL;
	public static String USER_NAME;
	public static String DRIVER_CLASS_NAME;
	
	public static String PROJECT_PATH = null;

	public static Document DOM = null;
	
	public static Integer LIMIT = 10;//默认页容量  10条
	
	public static String environment;//项目运行环境
	
	private static final String charset = "utf-8";
	
	public static final String TOKEN = "runfEngkj2017nEur0n168";
	
	public static final String CorpID = "wxa25b9c9892b68b9a";
	
	public static final String QYSECRET = "7I-Z64laOE8Pfbn0z8ujg00dGQ8Ic7Ya19Ew0F7wfuE";//"sfcpV-UCO1cyFNd02_DrQF3k76GfxsK_UnGEA-iHl1U";
	
	public static final int agentId = 1000009;
	
	//获取企业号access_token的接口地址（GET）   
	public static String access_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CorpID&corpsecret=SECRET";
	
	//企业号推送消息接口地址
	public static String send_msg_wx = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";
	
	//企业号媒体接口地址
	public static String uploadMediaUrl = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE"; 
	
	/**
	 * 企业号发送文本信息到员工
	 * @param content 内容
	 * @param agentid 应用id
	 * @return
	 * @throws Exception
	 */
	public static boolean sendTextMsgToCusByQy(String content,int agentid,int tag) throws Exception{
		
		String url = send_msg_wx.replace("ACCESS_TOKEN", ReflushAccessToken.QY_ACCESS_TOKEN);

		String p = "{\"totag\": \""+tag+"\",\"msgtype\": \"text\",\"agentid\": \""+agentid+"\",\"text\": {\"content\": \""+content+"\"},\"safe\": \"0\"}";
		
    	String result =  HttpRequest.sendPost(url, p);
		
		JsonObject json = (JsonObject) new JsonParser().parse(result);
		
		String errmsg = json.get("errmsg").getAsString();
		
		Sys.out("sendmsgbytext...result:"+errmsg);
		
		return errmsg.equals("ok")?true:false;
	}
	
	
	/**
	 * 企业号发送文件信息到员工
	 * @param content 内容
	 * @param agentid 应用id
	 * @return
	 * @throws Exception
	 */
	public static boolean sendFileMsgToCusByQy(String media_id,int agentid) throws Exception{
		
		String url = send_msg_wx.replace("ACCESS_TOKEN", ReflushAccessToken.QY_ACCESS_TOKEN);

		String p = "{\"totag\": \"1\",\"msgtype\": \"file\",\"agentid\": \""+agentid+"\",\"file\": {\"media_id\": \""+media_id+"\"},\"safe\": \"0\"}";
		
    	String result =  HttpRequest.sendPost(url, p);
		
		JsonObject json = (JsonObject) new JsonParser().parse(result);
		
		String errmsg = json.get("errmsg").getAsString();
		
		Sys.out("sendmsgbyfile...result:"+errmsg);
		
		return errmsg.equals("ok")?true:false;
	}
	
	private static final String [] usernamefilter = {"=","or","#","<",">"};//防止sql注入
	
	//用户名非法字符检查
	public static boolean checkUsername(String username){
    	boolean flag = true;
    	for (String filter : usernamefilter) {
			if(username.indexOf(filter)!=-1){
				flag = false;
				break;
			}
		}
    	return flag;
	}
	
	
	
	//计算数据的页数
	public static int pages(int records ,int limit){
		return (records/limit+(records%limit==0?0:1));
	}
	
	/**
	 * 将字符串写入Html文件
	 * @param path  文件路径
	 * @param filename 文件名
	 * @param allhtml 需要写入的字符串
	 * @throws Exception
	 */
	public static void writeStr2Html(String path,String filename,String allhtml) throws Exception{
		
		
    	 if (makeDir(path))
         {
             createFile(path, filename);
         }
         
    	 StringBuilder sb = new StringBuilder(); 
    	 
    	 OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(path + File.separator + filename),"UTF-8");
    	 
    	 BufferedWriter writer=new BufferedWriter(write);   
    	 
    	 writer.write(allhtml);
    	 writer.close();
	}
	
	private static boolean makeDir(String path)
    {
        boolean mk = true;
        File myPath = new File(path);
        if (!myPath.exists())
        {
            
            mk = myPath.mkdirs();
            
        }
        return mk;
    }
	
	 private static boolean createFile(String path, String fileName)
	            throws IOException
	    {
	        boolean creator = true;
	        File myPath = new File(path, fileName);
	        if (!myPath.exists())
	        {
	            creator = myPath.createNewFile();
	        }
	        return creator;
	        
	    }
	 
	 /**
	     * 获取服务器IP地址
	     * @return
	     */
	    @SuppressWarnings("unchecked")
	    public static String  getServerIp(){
	        String SERVER_IP = null;
	        try {
	            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
	            InetAddress ip = null;
	            while (netInterfaces.hasMoreElements()) {
	                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
	                ip = (InetAddress) ni.getInetAddresses().nextElement();
	                SERVER_IP = ip.getHostAddress();
	                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
	                        && ip.getHostAddress().indexOf(":") == -1) {
	                    SERVER_IP = ip.getHostAddress();
	                    break;
	                } else {
	                    ip = null;
	                }
	            }
	        } catch (SocketException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    
	        return SERVER_IP;
	    }
	    
	    
	    private static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";    
	    /** 
	     * 获取任意位的随机字符串(0-9 a-z A-Z) 
	     * @param size 位数 
	     * @return 
	     */  
	    public static final String getRandomNum(int size){  
	     StringBuffer sb = new StringBuffer();    
	     Random random = new Random();  
	     for (int i = 0; i < size; i++) {    
	         sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));    
	     }  
	     return sb.toString();  
	    }  
	    
	    
	    public synchronized static final String getMD5Str(String str,String charSet) { //md5加密  
	        MessageDigest messageDigest = null;    
	        try {    
	            messageDigest = MessageDigest.getInstance("MD5");    
	            messageDigest.reset();   
	            if(charSet==null){  
	                messageDigest.update(str.getBytes());  
	            }else{  
	                messageDigest.update(str.getBytes(charSet));    
	            }             
	        } catch (Exception e) {    
	            //log.error("md5 error:"+e.getMessage(),e);  
	        } 
	          
	        byte[] byteArray = messageDigest.digest();    
	        StringBuffer md5StrBuff = new StringBuffer();    
	        for (int i = 0; i < byteArray.length; i++) {                
	            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)    
	                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));    
	            else    
	                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));    
	        }    
	        return md5StrBuff.toString();    
	    }  
	    
	    
	    /**
	     * 删除单个文件
	     * 
	     * @param sPath
	     *            被删除文件的路径+文件名
	     * @return 单个文件删除成功返回true，否则返回false
	     */
	    public static boolean deleteFile(String sPath) {
	        Boolean flag = false;
	        File file = new File(sPath);
	        Sys.out("sPath:"+sPath);
	        // 路径为文件且不为空则进行删除
	        if (file.isFile() && file.exists()) {
	            file.delete();
	            flag = true;
	        }
	        Sys.out("del.file.:"+flag);
	        return flag;
	    }
	    
	    public static long setThreadStartTimer(String hour,String min,String secd){
	    	long diff = 0l;
	    	hour = hour==null?"00":hour;
	    	min = min==null?"00":min;
	    	secd = secd==null?"00":secd;
	    	try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				String day = sdf.format(now).split(" ")[0];
				String threadtime = day+" "+hour+":"+min+":"+secd;
				
				diff = now.getTime()-sdf.parse(threadtime).getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	Sys.out(diff);
	    	return diff;
	    }
	    
	    
	    
	    
	    
	  //EXCEL单元格格式化
		public static String getCellFormatValue(HSSFCell cell) {

	        String cellvalue = "";
	        if (cell != null) {
	            switch (cell.getCellType()) {
	            case HSSFCell.CELL_TYPE_NUMERIC:
	            case HSSFCell.CELL_TYPE_FORMULA: {
	                if (HSSFDateUtil.isCellDateFormatted(cell)) {
	                    
	                    Date date = cell.getDateCellValue();
	                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
	                    cellvalue = sdf.format(date);
	                    
	                }
	                else {
	                	DecimalFormat df = new DecimalFormat("#");
	                    cellvalue = df.format(cell.getNumericCellValue());
	                }
	                break;
	            }
	            case HSSFCell.CELL_TYPE_STRING:
	                cellvalue = cell.getRichStringCellValue().getString();
	                break;
	            default:
	                cellvalue = " ";
	            }
	        } else {
	            cellvalue = "";
	        }
	        

	        return cellvalue;

	    }
		
		
		 public static boolean checkSignature(String signature, String timestamp, String nonce) {
			 
		    String[] arr = { TOKEN, timestamp, nonce };

		    Arrays.sort(arr);
		    StringBuilder content = new StringBuilder();
		    for (int i = 0; i < arr.length; i++) {
		      content.append(arr[i]);
		    }
		    //Sys.out(content.toString());
		    MessageDigest md = null;
		    String tmpStr = null;
		    try
		    {
		      md = MessageDigest.getInstance("SHA-1");

		      byte[] digest = md.digest(content.toString().getBytes());
		      tmpStr = byteToStr(digest);
		    } catch (NoSuchAlgorithmException e) {
		      e.printStackTrace();
		    }

		    content = null;
		    //Sys.out(tmpStr);
		    return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
		  }
			public static String byteToStr(byte[] byteArray) {
			    String strDigest = "";
			    for (int i = 0; i < byteArray.length; i++) {
			      strDigest = strDigest + byteToHexStr(byteArray[i]);
			    }
			    return strDigest;
			  }

			  public static String byteToHexStr(byte mByte) {
			    char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
			    char[] tempArr = new char[2];
			    tempArr[0] = Digit[(mByte >>> 4 & 0xF)];
			    tempArr[1] = Digit[(mByte & 0xF)];

			    String s = new String(tempArr);
			    return s;
			  }
			  
			  
			  public static String getSignature(String token,String timestamp, String nonce) {
					String[] arr = new String[] { token, timestamp, nonce };
					// 将token、timestamp、nonce三个参数进行字典序排序
					Arrays.sort(arr);
					StringBuilder content = new StringBuilder();
					for (int i = 0; i < arr.length; i++) {
						content.append(arr[i]);
					}
					MessageDigest md = null;
					String tmpStr = null;

					try {
						md = MessageDigest.getInstance("SHA-1");
						// 将三个参数字符串拼接成一个字符串进行sha1加密
						byte[] digest = md.digest(content.toString().getBytes());
						tmpStr = byteToStr(digest);
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					}

					content = null;
					return tmpStr;
				}
			  
			  
			
			
			/**
			 * 生成随机6位数
			 */
		    public static String gencode(){
				StringBuffer code = new StringBuffer();
				Random rd = new Random();
				
				for (int i = 0; i < 6; i++) {
					code.append(rd.nextInt(10));
				}
				
				return code.toString();
			}
		    
		    public static boolean broadcast(String relationId, Long userCode, String message) {
				 if (SessionUtils.hasConnection(relationId, userCode)) {
				 SessionUtils.get(relationId, userCode).getAsyncRemote().sendText(message);
				 	return true;
				 } else {
				 //throw new NullPointerException(SessionUtils.getKey(relationId, userCode) +"Connection does not exist");
					 return false;
				}
			}
		    
		    //验证身份证号码
			public static boolean idCardNumber(String number)
			{
				String rgx = "^\\d{15}|^\\d{17}([0-9]|X|x)$";
				
				return isCorrect(rgx, number);
			}
			
			//正则验证
			public static boolean isCorrect(String rgx, String res)
			{
				Pattern p = Pattern.compile(rgx);
				
				Matcher m = p.matcher(res);
				
				return m.matches();
			}
		    
		    /** 
		     * 大陆号码或香港号码均可 
		     */  
		    public static boolean isPhoneLegal(String str)throws PatternSyntaxException {  
		        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);  
		    }  
		  
		    /** 
		     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 
		     * 此方法中前三位格式有： 
		     * 13+任意数 
		     * 15+除4的任意数 
		     * 18+除1和4的任意数 
		     * 17+除9的任意数 
		     * 147 
		     */  
		    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {  
		        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147))\\d{8}$";  
		        Pattern p = Pattern.compile(regExp);  
		        Matcher m = p.matcher(str);  
		        return m.matches();  
		    }  
		  
		    /** 
		     * 香港手机号码8位数，5|6|8|9开头+7位任意数 
		     */  
		    public static boolean isHKPhoneLegal(String str)throws PatternSyntaxException {  
		        String regExp = "^(5|6|8|9)\\d{7}$";  
		        Pattern p = Pattern.compile(regExp);  
		        Matcher m = p.matcher(str);  
		        return m.matches();  
		    }  
		    
		    
			//计算两个日期相差天数
		    public static int differentDays(Date date1,Date date2){
				 Calendar cal1 = Calendar.getInstance();
				 cal1.setTime(date1);
				  
				 Calendar cal2 = Calendar.getInstance();
				 cal2.setTime(date2);
				 int day1= cal1.get(Calendar.DAY_OF_YEAR);
				 int day2 = cal2.get(Calendar.DAY_OF_YEAR);
				  
				 int year1 = cal1.get(Calendar.YEAR);
				 int year2 = cal2.get(Calendar.YEAR);
				 if(year1 != year2){ //同一年
					 int timeDistance = 0 ;
					 for(int i = year1 ; i < year2 ; i ++){
						 if(i%4==0 && i%100!=0 || i%400==0){ //闰年  
				  
							 timeDistance += 366;
						 }else {//不是闰年
							 timeDistance += 365;
						 }
					 }
				   
					 return timeDistance + (day2-day1) ;
				 }else{ //不同年
					 Sys.out("判断day2 - day1 : " + (day2-day1));
					 return day2-day1;
				 }
			 }
		    
		    public static double formatDouble(double d) {
		        // 旧方法，已经不再推荐使用
//		        BigDecimal bg = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);

		        
		        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
		        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);

		        
		        return bg.doubleValue();
		    }
		    
		    public static double formatdouble(double d) {
		      
		        return (int)d;
		    }
		    
		 
		    
		    public static List<String> readFileByLines(String fileName) {
		        File file = new File(fileName);
		        BufferedReader reader = null;
		        List<String> lst = new ArrayList<String>();
		        try {
		            System.out.println("read file：");
		            reader = new BufferedReader(new FileReader(file));
		            String tempString = null;
		            int line = 1;
		            // 一次读入一行，直到读入null为文件结束
		            while ((tempString = reader.readLine()) != null) {
		                // 显示行号
		                System.out.println("line " + line + ": " + tempString);
		                lst.add(tempString);
		                line++;
		            }
		            reader.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        } finally {
		            if (reader != null) {
		                try {
		                    reader.close();
		                } catch (IOException e1) {
		                }
		            }
		        }
		        
		        return lst;
		    }
		    
		    
		    public static boolean checksign(String sign,String isw){
		    	
		    	List<String> lst = readFileByLines(isw);
		    	
				String token = lst.get(2);
		    	
				return token.equals(sign)?true:false;
		    }
		    
		    
}
