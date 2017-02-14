package com.boco.eoms.netresource.modify.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 根据ID获取名称
* @Title: 
* 
* @Description: TODO
* 
* @author WangGuangping  
* 
* @date Feb 22, 2012 11:28:08 AM
* 
* @version V1.0   
*
 */

public final class ModifyId2Name {
	
	/**
	 * 根据ID获取名称
	 * @param id
	 * @param typeNum	10：申请的审批状态
	 * @param typeNum	20：变更类型
	 * @param typeNum	30：资源类型
	 * @return
	 */
	public static String modifyId2Name(String id , String typeNum){
		if(id == null || "".equals(id)){
			return id;
		}
		
		Map<String,String> map = getMap();
		
		String keyId = typeNum.trim() + id.trim();
		String value = map.get(keyId);
		
		if("".equals(value) || null == value){
			value = id;
		}

		return value;
	}
	
	/**
	 * 从properties文件读取
	 * @param id
	 * @param typeNum
	 * @return
	 */
	public String getModifyNameById(String id , String typeNum){
		if(id == null || "".equals(id)){
			return id;
		}
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("com/boco/eoms/netresource/modify/config/modify.properties");    
		
		Properties p = new Properties();    
		try {
			p.load(inputStream);
		  } catch (IOException e1) {
		   e1.printStackTrace();
		}
		String keyId = typeNum.trim() + id.trim();
		String value = p.getProperty(keyId);
		
		if("".equals(value) || null == value){
			value = id;
		}
		
		return value;
	}
	
	/**
	 * 获取map
	 * @return
	 */
	public static Map getMap(){
		
		Map<String,String> map = new HashMap<String,String>();
		//申请的审批状态
		map.put("101", "待审批");
		map.put("102", "已受理");
		map.put("103", "同意");
		map.put("104", "驳回");
		
		//变更类型
		map.put("201", "坐标变更");
		map.put("202", "资源新增");
		map.put("203", "资源修改");
		map.put("204", "资源删除");
		
		//资源类型
		map.put("301", "基站");
		map.put("302", "线路");
		map.put("303", "标点");
		map.put("304", "设备");
		
		return map;
	}
	

}
