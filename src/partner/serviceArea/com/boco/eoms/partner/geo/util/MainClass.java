package com.boco.eoms.partner.geo.util;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainClass {

	public static void main(String[] args) {
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		
		String jsonString = gson.toJson(new Date(System.currentTimeMillis()),Date.class);
		System.out.println(jsonString);
	}
	
}
