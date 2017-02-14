package com.boco.eoms.partner.deviceInspect.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.boco.eoms.commons.util.xml.XMLProperties;
import com.boco.eoms.commons.util.xml.XmlManage;


public class TableNameMappingNetres {

	public static String XML_Path = "/com/boco/eoms/partner/deviceInspect/config/tableNameMappingNetresName.xml";
	
	@SuppressWarnings("unchecked")
	public Map<String, String> loadMapping(){
		Map<String, String> map = new HashMap<String, String>();
		
		XMLProperties xml = XmlManage.getFile(XML_Path);
		List<Element> list = xml.getElement().getChildren();
		for(int i = 0;i<list.size();i++){
			String tableName = list.get(i).getAttributeValue("tablename");
			String fieldname = list.get(i).getAttributeValue("fieldname");
			map.put(tableName, fieldname);
		}
		return map;
	}
	
	public  Map<String, String> loadModelMapping(){
		Map<String, String> map = new HashMap<String, String>();
		XMLProperties xml = XmlManage.getFile(XML_Path);
		List<Element> list = xml.getElement().getChildren();
		for(int i = 0;i<list.size();i++){
			String tablename = list.get(i).getAttributeValue("tablename");
			String devicetypename = list.get(i).getAttributeValue("devicetypename");
			map.put(tablename, devicetypename);
		}
		
		
		return map;
	}
}
