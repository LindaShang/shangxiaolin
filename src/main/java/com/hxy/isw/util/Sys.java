package com.hxy.isw.util;

public class Sys {
	//private static Logger log = Logger.getLogger(Sys.class);  
	public static void out(Object objs){
		if(ConstantUtil.SYSOUT)System.out.println(objs);
		//else log.info(objs);
	}
	
}
