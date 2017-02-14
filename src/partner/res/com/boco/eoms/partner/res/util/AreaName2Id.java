package com.boco.eoms.partner.res.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;

/** 
 * Description: 地市的name转化成Id 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liaojimin 
 * @version:    1.0 
 */
public class AreaName2Id {

	/**
	 * 地市的name转化成Id 
	 */
	public String name2Id(String name,String initDictId) throws Exception{
		Map<String,String> dictMap = this.getDictMap(initDictId);
		String id = "";
		String value = "";
		for(String key : dictMap.keySet()) {
			value = dictMap.get(key);
			if(name.equals(value)) {
				id = key;
				break;
			}
		}
		if("".equals(id)) {
			throw new RuntimeException("导入的字典不匹配！");
		}
		return id;
	}
	
	/**
	 * 获得字典Id
	 * @param initDictId
	 * @throws Exception
	 */
	public Map<String,String> getDictMap(String initDictId) throws Exception{
		if("".equals(initDictId) || initDictId == null) {
			return null;
		}
		
		// 取字典数据
		ITawSystemAreaManager  dictManager = (ITawSystemAreaManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemAreaManager");
		List list = dictManager.getSonAreaByAreaId(initDictId);  
		
		Map<String,String> dictMap = new HashMap<String,String>();
		if(list.size()>0){
			String itemName = null;
			String itemId = null;
			// 将list中的值元素插入Map
			for (Iterator it = list.iterator(); it.hasNext();) {
				TawSystemArea item = (TawSystemArea) it.next();
				itemName = StaticMethod.null2String(item.getAreaname());
				itemId = StaticMethod.null2String(item.getAreaid());
				
				dictMap.put(itemId, itemName);
			}
		} else {
			return null;
		}
		return dictMap;
	}
	
}
