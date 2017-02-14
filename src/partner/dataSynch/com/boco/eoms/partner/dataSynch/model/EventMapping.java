package com.boco.eoms.partner.dataSynch.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.boco.eoms.commons.util.xml.XMLProperties;
import com.boco.eoms.commons.util.xml.XmlManage;

/**
 * 类说明：事件ID和MgrBeanName映射 (注意：事件ID(EventId)，必须与applicationContext-sequence.xml一致)
 * 		 请在applicationContext-attributes.xml将sequenceOpen配置为true
 *       eventKey为PNR_开头加资源类名
 *       eventID和资源对应的表名一样
 * 创建人： zhangkeqi
 * 创建时间：2012-12-01 18:05:58
 */
public class EventMapping {
	public static String Event_Mapping_File = "/com/boco/eoms/partner/dataSynch/config/dataSyncEventMapping.xml";
	
	public static Map<String,String> eventKey2EventIDMap = new HashMap<String,String>();
	public static Map<String,String> eventID2MgrNameMap = new HashMap<String,String>();
	
	static {
		XMLProperties xml = XmlManage.getFile(Event_Mapping_File);
		List<Element> list = xml.getElement().getChildren();
		
		Element ele = null;
		String eventKey,eventID;
		for(int i=0;i<list.size();i++) {
			ele = list.get(i);
			eventKey2EventIDMap.put(ele.getName(), ele.getChildText("eventID"));
			eventID2MgrNameMap.put(ele.getChildText("eventID"), ele.getChildText("mgrBeanName"));
		}
	}
	
	public EventMapping() {
		XMLProperties xml = XmlManage.getFile(Event_Mapping_File);
		List<Element> list = xml.getElement().getChildren();
		
		Element ele = null;
		String eventKey,eventID;
		for(int i=0;i<list.size();i++) {
			ele = list.get(i);
			eventKey2EventIDMap.put(ele.getName(), ele.getChildText("eventID"));
			eventID2MgrNameMap.put(ele.getChildText("eventID"), ele.getChildText("mgrBeanName"));
		}
	}
	
	public static void main(String[]aa) {
		System.out.println(new EventMapping().eventKey2EventIDMap);
		System.out.println(new EventMapping().eventID2MgrNameMap);
	}
	
}
