package com.boco.eoms.sheet.base.service.impl;

import java.util.Calendar;
import java.util.TimeZone;

public class testTimeZone {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance(); 
		String a[] = TimeZone.getAvailableIDs();
		System.out.println(a);
	}

}
