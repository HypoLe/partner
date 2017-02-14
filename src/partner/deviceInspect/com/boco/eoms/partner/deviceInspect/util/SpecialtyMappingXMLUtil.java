package com.boco.eoms.partner.deviceInspect.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.boco.eoms.commons.util.xml.XMLProperties;
import com.boco.eoms.commons.util.xml.XmlManage;
import com.boco.eoms.partner.deviceInspect.mp.NetResourceMapping;

public class SpecialtyMappingXMLUtil {
	
	public static String XML_Path = "/com/boco/eoms/partner/deviceInspect/config/specialtyMapping.xml";
	public static Map<String, List<NetResourceMapping>> SpecialtyMapping = new HashMap<String, List<NetResourceMapping>>();
	public static Map<String,NetResourceMapping> Table2NetResourceMapping = new HashMap<String,NetResourceMapping>();
	
	static {
		SpecialtyMapping = loadMapping();
		Table2NetResourceMapping = getNetResourceMapping();
	}

	@SuppressWarnings("unchecked")
	public static Map<String, List<NetResourceMapping>> loadMapping(){
		
		Map<String, List<NetResourceMapping>> map = new HashMap<String, List<NetResourceMapping>>();
		XMLProperties xml = XmlManage.getFile(XML_Path);
		List<Element> list = xml.getElement().getChildren();
		
		/** 获得所有的专业  */
		List<Element> specialtyList = list.get(0).getChildren();
		
		for(int i = 0 ;i<specialtyList.size();i++){
			List<NetResourceMapping> netResourceList = new ArrayList<NetResourceMapping>();
			String specialty = specialtyList.get(i).getAttributeValue("name");
			map.put(specialty, netResourceList);
		}
		
		/** 在专业下放置网络资源类别 */
		if(list.size()>0){
			for(int i=1;i<list.size();i++){
				NetResourceMapping netResource = new NetResourceMapping();
				String specialty = list.get(i).getAttributeValue("specialty-type");
				String resourceName = list.get(i).getChild("resource-name").getText();
				String resourceMark = list.get(i).getChild("resource-mark").getText();
				String tableName = list.get(i).getChild("table-name").getText();
				String typeByFiled = list.get(i).getChild("type-by-filed").getText().toLowerCase();
				String fieldName = list.get(i).getChild("field").getAttributeValue("name");
				String fieldChName = list.get(i).getChild("field").getAttributeValue("chname");
				List<Element> fileld = list.get(i).getChild("field").getChildren();
				List<String> filedList = new ArrayList<String>();
				if("y".equals(typeByFiled)){
					for(int j= 0 ;j<fileld.size(); j++){
						filedList.add(fileld.get(j).getText());
					}
					netResource.setFiledList(filedList);
					netResource.setFieldName(fieldName);
					netResource.setFieldChName(fieldChName);
				}
				netResource.setSpecialty(specialty);
				netResource.setResourceName(resourceName);
				netResource.setTableName(tableName);
				netResource.setTypeByFiled(typeByFiled);
				netResource.setResourceMark(resourceMark);
				
				List<NetResourceMapping> resourceList = map.get(specialty);
				resourceList.add(netResource);
				map.put(specialty, resourceList);
			}
		}
		
		return map;
		
	}
	
	public static Map<String,NetResourceMapping> getNetResourceMapping() {
		Map<String,NetResourceMapping> map = new HashMap<String,NetResourceMapping>();
		for(String key : SpecialtyMapping.keySet()) {
			for(NetResourceMapping nrm :  SpecialtyMapping.get(key)) {
				map.put(nrm.getTableName(), nrm);
			}
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> loadSpecial(){
		
		Map<String, String> map = new HashMap<String, String>();
		
		XMLProperties xml = XmlManage.getFile(XML_Path);
		List<Element> list = xml.getElement().getChildren();
		
		/** 获得所有的专业  */
		List<Element> specialtyList = list.get(0).getChildren();
		
		for(int i = 0 ;i<specialtyList.size();i++){
		
			String specialty = specialtyList.get(i).getAttributeValue("name");
			String value = specialtyList.get(i).getAttributeValue("value");
			map.put(specialty, value);
		}
		
		return map;
	}
	
	public static void main(String args[]){
		 new SpecialtyMappingXMLUtil().loadMapping();
	}
	
}
