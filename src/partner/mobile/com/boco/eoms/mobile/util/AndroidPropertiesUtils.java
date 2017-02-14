package com.boco.eoms.mobile.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import org.junit.Test;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.common.util.StaticMethod;

public class AndroidPropertiesUtils {
	public static Properties properties;
	static String filePath = AndroidPropertiesUtils.class.getResource("/").getPath()+"com/boco/eoms/mobile/config/partner_inspect.properties";
	static{
		properties = new Properties();
		try {
			InputStream inputStream = new FileInputStream(filePath);
			properties.load(inputStream);
			if(null != inputStream){
				inputStream.close();
				inputStream = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getValue(String KEY){
		return properties.getProperty (KEY);
	}
}
