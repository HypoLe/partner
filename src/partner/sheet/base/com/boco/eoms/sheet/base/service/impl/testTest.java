package com.boco.eoms.sheet.base.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class testTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String hours=""; 
	    try{ 
	    Calendar cal = Calendar.getInstance(); 

	    cal.add(Calendar.DATE,-10); 
	    Date date = cal.getTime(); 
	DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	hours = format.format(date); 
	
	System.out.println(hours);
	    }catch(Exception e){ 
	    e.printStackTrace(); 
	    } 
	    System.out.println(hours); 
	}

}
