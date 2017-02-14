package com.boco.eoms.partner.property.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDateFormat {

	public static void main(String[] args) {
		Date now=new Date();
		System.out.println(now);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		 String dateString = formatter.format(now);
		 String dateString = "2012-09-01";
		 System.out.println(dateString);
		 ParsePosition pos = new ParsePosition(0);
		 Date currentTime_2;
			currentTime_2 = formatter.parse(dateString,pos);
			System.out.print(currentTime_2);
	}
}
