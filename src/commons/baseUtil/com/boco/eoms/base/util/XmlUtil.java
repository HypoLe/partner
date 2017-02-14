package com.boco.eoms.base.util;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Jun 11, 2010 9:36:47 AM
 * </p>
 * 
 * @author 江海龙
 * @version 1.0
 * 
 */

import java.io.StringWriter;
import java.io.Writer;

import com.thoughtworks.xstream.XStream; 
import com.thoughtworks.xstream.io.xml.DomDriver; 

public class XmlUtil {
	
	//将对象转为XML 
	public static String simpleobject2xml(Object model) {
		XStream xStream = new XStream(new DomDriver());
		xStream.alias(model.getClass().getSimpleName(), model.getClass()); 
		String xml = xStream.toXML(model);
		//Writer sw = new StringWriter();
		//xStream.toXML(model, sw);
		//String xml = sw.toString();
		return filter(xml); 
	}
	
	public static String simpleobject2xml(Object model, String rootName) {
		XStream xStream = new XStream(new DomDriver());
		xStream.alias(rootName, model.getClass()); 
		String xml = xStream.toXML(model);
		//Writer sw = new StringWriter();
		//xStream.toXML(model, sw);
		//String xml = sw.toString();
		return filter(xml);
	}
	
	static String filter(String str){
		String retStr = str.replace("\n", "").replace("  ", "");
		return retStr;
		//String retStr = str.replace("&lt;", "<");
		//retStr = retStr.replace("&gt;", ">");
		//retStr = retStr.replace("<String>", "");
		//retStr = retStr.replace("</String>", "");
		//return str;
	}
	
    //将XML转为对象 
	public static Object simplexml2object(String xml,Object model) {
		XStream xStream = new XStream(new DomDriver()); 
		xStream.alias(model.getClass().getSimpleName(), model.getClass()); 
		Object reobj = xStream.fromXML(xml); 
		return reobj;
	}

}