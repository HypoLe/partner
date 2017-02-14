package com.boco.eoms.partner.dataSynch.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.boco.eoms.commons.util.xml.XMLProperties;
import com.boco.eoms.commons.util.xml.XmlManage;

/** 
 * Description: 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 5, 2012 4:35:59 PM
 */
public class DataSynchConstant {
	
	public static String EXCEPTION_KEY = "EXCEPTION"; //异常数据标识
	public static String BASE_PATH = "com/boco/eoms/partner/dataSynch/config/rule";
	
	/**配置文件dataSyncConstant.xml路径*/
	public static String Event_Mapping_File = "/com/boco/eoms/partner/dataSynch/config/dataSyncConstant.xml";
	
	public DataSynchConstant() {
	}
	
	/**
	 * 从配置文件dataSyncConstant.xml中获取本地IP
	 * @return
	 */
	public static String getLocalIp() {
		XMLProperties xml = XmlManage.getFile(Event_Mapping_File);
		String LOCAL_IP = xml.getElement().getChild("LOCAL_IP").getText().trim();
		return LOCAL_IP;
	}
	/**
	 * 从配置文件dataSyncConstant.xml中获取端口名
	 * @return
	 */
	public static String getLocalPort() {
		XMLProperties xml = XmlManage.getFile(Event_Mapping_File);
		String LOCAL_PORT = xml.getElement().getChild("LOCAL_PORT").getText().trim();
		return LOCAL_PORT;
	}
	
	/**
	 * 从配置文件dataSyncConstant.xml中获取工程名
	 * @return
	 */
	public static String getLocalProject() {
		XMLProperties xml = XmlManage.getFile(Event_Mapping_File);
		String LOCAL_PROJECT = xml.getElement().getChild("LOCAL_PROJECT").getText().trim();
		return LOCAL_PROJECT;
	}
	
	/**
	 * 从配置文件dataSyncConstant.xml中获取工程名
	 * @return
	 */
	public static String getDeliveryRequestSOAPport_http_address() {
		XMLProperties xml = XmlManage.getFile(Event_Mapping_File);
		String DeliveryRequestSOAPport_http_address = xml.getElement().getChild("DeliveryRequestSOAPport_http_address").getText().trim();
		return DeliveryRequestSOAPport_http_address;
	}
	
	/**
	 * 从配置文件dataSyncConstant.xml中数据库类型
	 * @return
	 */
	public static String getDBType() {
		XMLProperties xml = XmlManage.getFile(Event_Mapping_File);
		String dbType = xml.getElement().getChild("db-type").getText().trim();
		return dbType;
	}
	
	/**
	 * 获取手动同步信息
	 * @return
	 */
	public static Map<String,String> getHandleSynchConfig() {
		Map<String,String> map = new HashMap<String,String>();
		XMLProperties xml = XmlManage.getFile(Event_Mapping_File);
		List<Element> list = xml.getElement().getChild("handle-config").getChildren();
		
		for(int i=0;i<list.size();i++) {
			map.put(list.get(i).getName(),list.get(i).getText());
		}
		
		return map;
	}
	
	public static void main(String[]dsf) {
		System.out.println(DataSynchConstant.getLocalIp());
		System.out.println(DataSynchConstant.getLocalPort());
		System.out.println(DataSynchConstant.getLocalProject());
	}
}
