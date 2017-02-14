package com.boco.eoms.partner.res.util;

public class NullException  {
	
	public String nullException(String str) throws Exception{
//		if(str.trim().equals("")){
//			str = null;
//			//有意报异常
//			System.out.println(str.length());
//		}
//		return str;
		boolean flag = true;
		if(str != null){
			if(!str.trim().equals("")){
				flag = false;
			}
		}
		if(flag){
			throw new Exception("");
		}
		return str;
	}
	
}
