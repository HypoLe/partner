package com.boco.eoms.partner.netresource.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

/** 
 * Description: 网络资源工具类
 * Copyright:   Copyright (c)2013
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 25, 2013 9:52:50 AM
 */
public class PnrNetResourceUtil {
	public final static String CLASS_PATH_FLAG = "classpath:";             //classpath标识
	public final static String REG = "^[0-9]{1,3}.[0-9]{1,13}$";           //classpath标识
	public final static URL CLASS_PATH_URL = PnrNetResourceUtil.class.getResource("/"); //classpath绝对URL
	
	/**
	 * 解析配置文件pnrnetresource-config.xml
	 * @param model
	 * @return
	 */
	public static PnrNetResourceConfig parsePnrNetResourceConfig(String model){
		PnrNetResourceConfig config = null;
		FileInputStream fis = null;
		try{
			String xmlPath = PnrNetResourceUtil.getAbstractPath(PnrNetResourceUtil.CLASS_PATH_FLAG+"com/boco/eoms/partner/netresource/config/pnrnetresource-config.xml");
			fis = new FileInputStream(xmlPath);
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader reader = factory.createXMLStreamReader(fis);
			boolean flag = false;
			while (reader.hasNext()) {
				int event = reader.getEventType();
				switch (event) {
					case XMLStreamConstants.START_ELEMENT:
						String localName = reader.getLocalName();
						if("config".equals(localName)){
							config = new PnrNetResourceConfig();
						}else if("model".equals(localName)){
							String modelConfig = reader.getElementText();
							if(model.equalsIgnoreCase(modelConfig)){
								flag = true;
							}
						}else if("specialty".equals(localName)){
							if(flag){
								config.setSpecialty(reader.getElementText());
							}
						}else if("restype".equals(localName)){
							if(flag){
								config.setResType(reader.getElementText());
							}
						}else if("wholeSql".equals(localName)){
							if(flag){
								config.setWholeSql(reader.getElementText());
							}
						}
					case XMLStreamConstants.END_ELEMENT:
						if("config".equals(reader.getLocalName())){
							if(flag){
								return config;
							}
						}
				}
				
				if (reader.hasNext()) {
					event = reader.next();
				} else {
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(fis != null) fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		return config;
	}
	
	/**
	 * 解析出配置文件中所有的网络资源类型名
	 * @return
	 */
	public static List<String> parseAllConfigModel(){
		List<String> list = new ArrayList<String>();
		FileInputStream fis = null;
		try{
			String xmlPath = PnrNetResourceUtil.getAbstractPath(PnrNetResourceUtil.CLASS_PATH_FLAG+"com/boco/eoms/partner/netresource/config/pnrnetresource-config.xml");
			fis = new FileInputStream(xmlPath);
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader reader = factory.createXMLStreamReader(fis);
			while (reader.hasNext()) {
				int event = reader.getEventType();
				switch (event) {
					case XMLStreamConstants.START_ELEMENT:
						if("model".equals(reader.getLocalName())){
							list.add(reader.getElementText());
						}
				}
				if (reader.hasNext()) {
					event = reader.next();
				} else {
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(fis != null) fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		return list;
	}
	
	public static void main(String[]args){
		PnrNetResourceConfig c = parsePnrNetResourceConfig("irms_wlan_hot");
		System.out.println(c.getWholeSql());
//		List<String> list = parseAllConfigModel();
//		for (String s : list) {
//			System.out.println(s);
//		}
	}
	


	/**
	 * 获取文件绝对路径
	 * @param filePath 形如classpath:config/person.xml的文件路径
	 * @return
	 */
	public static String getAbstractPath(String filePath) {
		if (filePath != null) {
			if (filePath.length() > CLASS_PATH_FLAG.length()) {
				if (CLASS_PATH_FLAG.equals(filePath.substring(0, CLASS_PATH_FLAG.length()))) {
					filePath = CLASS_PATH_URL.getFile() + filePath.substring(CLASS_PATH_FLAG.length());
				}
			}
		}
		return filePath;
	}
	
	/**
	 * 检查字符串是否为数字
	 * @param str
	 * @return
	 */
	public static Boolean checkIsNumber(String cellString){
		Boolean bn = true;
		if (!cellString.matches("^\\d+(\\.{1}\\d+)?$")) {
			bn = false;
		}
		return bn;
	}
	/**
	 * 
	 * @param row
	 * @param cellNum
	 * @return
	 */
	public  static Boolean checkIsNull(String cellString){
		Boolean bn = false;
		if (cellString!=null) {
			if ("".equals(cellString)){
				bn=true;
			}
		}	
		return bn;
	}
}
