package com.boco.eoms.partner.interfaces.common.init;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class RequestConfig {
	private static Properties properties;
	
	public static void init(File file) throws IOException {
		FileInputStream in =new FileInputStream(file);
		properties = new Properties();
		properties.load(new BufferedInputStream(in));
	}
	public static String getUrl(){
		return properties.getProperty("partner.jdbc.url");
	}
	public static String getUsername(){
		return properties.getProperty("partner.jdbc.username");
	}
	public static String getPassword(){
		return properties.getProperty("partner.jdbc.password");
	}
	public static String getLoadingFeedback(){
		return properties.getProperty("partner.LoadingFeedback.url");
	}
	public static String getftphost(){
		return properties.getProperty("partner.ftp.host");
	}
	public static String getftpport(){
		return properties.getProperty("partner.ftp.port");
	}
	public static String getftpusername(){
		return properties.getProperty("partner.ftp.username");
	}
	public static String getftppassword(){
		return properties.getProperty("partner.ftp.password");
	}
	public static String getftpdir(){
		return properties.getProperty("partner.ftp.dir");
	}
	public static String getftpdedir(){
		return properties.getProperty("partner.ftp.dedir");
	}
	public static String getftpdestdir(){
		return properties.getProperty("partner.ftp.destdir");
	}
}
