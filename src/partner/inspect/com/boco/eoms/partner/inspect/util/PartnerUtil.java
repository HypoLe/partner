package com.boco.eoms.partner.inspect.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import sun.misc.BASE64Encoder;

public class PartnerUtil {
	public static String inputStreamToString(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader r = new BufferedReader(new InputStreamReader(in,"UTF-8"), 1000);
		for (String line = r.readLine(); line != null; line = r.readLine()) {
			sb.append(line);
		}
		in.close();
		if ("".equals(sb.toString())) {
			return "";
		} else
			return sb.toString();
	}
	
	public static String toJson(String str) {
		return "[" + str + "]";
	}
	/**
	 * base64编码
	 */
	public static String convertImageDataToBASE64(String filePath) throws IOException {
		File file = new File(filePath);
		FileInputStream fs = new FileInputStream(file);
		BASE64Encoder c1 = new BASE64Encoder();
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		c1.encodeBuffer(fs,baos);
		String uploadData = new String(baos.toByteArray()); //进行Base64编码 
		baos.close();
		fs.close();
		return uploadData;
	}
}

