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
 * @author:     zhangkeqi
 * @version:    1.0
 * Create at:   Apr 5, 2012 4:35:59 PM
 */
public class Excel2XMLUtil {
	
	public static Map<String,Map<String,Map>> excelMappingMap = new HashMap<String,Map<String,Map>>();
	
	/**配置文件dataSyncConstant.xml路径*/
	public static String Excel_Mapping_BS = "/com/boco/eoms/partner/dataSynch/config/excelmapping/excel2xmlMapping-bs.xml";
	public static String Excel_Mapping_GROUP = "/com/boco/eoms/partner/dataSynch/config/excelmapping/excel2xmlMapping-group.xml";
	public static String Excel_Mapping_SPACE = "/com/boco/eoms/partner/dataSynch/config/excelmapping/excel2xmlMapping-space.xml";
	public static String Excel_Mapping_TOWER = "/com/boco/eoms/partner/dataSynch/config/excelmapping/excel2xmlMapping-tower.xml";
	public static String Excel_Mapping_TRANS = "/com/boco/eoms/partner/dataSynch/config/excelmapping/excel2xmlMapping-trans.xml";
	public static String Excel_Mapping_WLAN = "/com/boco/eoms/partner/dataSynch/config/excelmapping/excel2xmlMapping-wlan.xml";
	
	public Excel2XMLUtil() {
	}
	
	public static void initMap() throws Exception{
		loadMap(Excel_Mapping_BS);
		loadMap(Excel_Mapping_GROUP);
		loadMap(Excel_Mapping_SPACE);
		loadMap(Excel_Mapping_TOWER);
		loadMap(Excel_Mapping_TRANS);
		loadMap(Excel_Mapping_WLAN);
	}
	
	/**
	 * 装载Map
	 * @return
	 */
	public static void loadMap(String type) throws Exception{
		XMLProperties xml = XmlManage.getFile(type);
		List<Element> list = xml.getElement().getChildren();
		
		Element model = null;
		Map<String,Map> fieldMap = null;
		Map<String,String> fieldCfgMap= null;
		List<Element> fieldList = null;
		String name,excelCellNum;
		int fieldCount = 0;
		for(int i=0;i<list.size();i++) {
			fieldMap = new HashMap<String,Map>();
			model = list.get(i);
			fieldList = model.getChildren();
			fieldCount = 0;
			for(int j=0;j<fieldList.size();j++) {
				fieldCfgMap = new HashMap<String,String>();
				name = fieldList.get(j).getAttributeValue("name");
				excelCellNum = fieldList.get(j).getAttributeValue("excelCellNum");
				if(!"".equals(excelCellNum)) {
					try {
						excelCellNum = (Integer.parseInt(excelCellNum)-1) + "";
					} catch(Exception e) {
						e.printStackTrace();
						throw new Exception("Excel字段映射错误，请检查是否输入有误："+type+":"+model.getName()+":"+name+":"+excelCellNum+":"+fieldList.get(j).getAttributeValue("remark"));
					}
					fieldCfgMap.put("name", name);
					fieldCfgMap.put("excelCellNum", excelCellNum);
					fieldMap.put(fieldCount+"", fieldCfgMap);
					fieldCount++;
				}
			}
			excelMappingMap.put(model.getName(), fieldMap);
		}
		
	}
	
	public static void main(String[]dsf) throws Exception{
		initMap();
		
		System.out.println(excelMappingMap);
	}
}
