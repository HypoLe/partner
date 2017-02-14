package com.boco.eoms.partner.deviceInspect.util;

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
 * @author:     zhangkeqi
 * @version:    1.0
 * Create at:   Apr 4, 2013 4:35:59 PM
 */
public class TransLineAreaMappingUtil {
	
	public static Map<String,String[]> MappingMap = new HashMap<String,String[]>();
	
	/**配置文件路径*/
	public static String Area_Mapping_File = "/com/boco/eoms/partner/deviceInspect/config/trans-line-area-mapping.xml";
	
	public TransLineAreaMappingUtil() {
	}
	
	public static void initMap() throws Exception{
		loadMap(Area_Mapping_File);
	}
	
	/**
	 * 装载Map
	 * @return
	 */
	public static void loadMap(String type) throws Exception {
		XMLProperties xml = XmlManage.getFile(type);
		List<Element> areaList = xml.getElement().getChildren();
		
		Element model = null;
		String[] city_country = null;
		String mappingArea = "";
		for(int i=0;i<areaList.size();i++) {
			city_country = new String[2];
			model = areaList.get(i);
			mappingArea = model.getAttributeValue("mapping-area");
			city_country[0] = model.getAttributeValue("city-code");
			city_country[1] = model.getAttributeValue("country-code");
			if(!"".equals(mappingArea)) {
				MappingMap.put(mappingArea, city_country);
			}
		}
	}
	
	public static void main(String[]dsf) throws Exception{
		initMap();
		System.out.println(MappingMap);
	}
}
