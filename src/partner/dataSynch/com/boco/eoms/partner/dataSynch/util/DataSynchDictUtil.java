package com.boco.eoms.partner.dataSynch.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;


public class DataSynchDictUtil {
	
	/**
	 * 字典name转字典值
	 * @param name
	 * @param initDictId
	 * @return
	 */
	public String name2Id(String name,String initDictId) {
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
		return id;
	}
	
	/**
	 * 根据字典值构建该字典的所有字典项到Map中
	 * @param initDictId
	 * @return
	 */
	public static Map<String,String> getDictMap(String initDictId) {
		if("".equals(initDictId) || initDictId == null) {
			return null;
		}
		
		// 取字典数据
		ITawSystemDictTypeManager dictManager = (ITawSystemDictTypeManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemDictTypeManager");
//		ITawSystemDictTypeManager dictManager = (ITawSystemDictTypeManager) DataSyncTest.factory.getBean("ItawSystemDictTypeManager");
		List list = dictManager.getDictSonsByDictid(initDictId);

		Map<String,String> dictMap = new HashMap<String,String>();
		if(list.size()>0){
			String itemName = null;
			String itemId = null;
			// 将list中的值元素插入Map
			for (Iterator it = list.iterator(); it.hasNext();) {
				TawSystemDictType item = (TawSystemDictType) it.next();
				itemName = StaticMethod.null2String(item.getDictName());
				itemId = StaticMethod.null2String(item.getDictId());
				
				dictMap.put(itemName,itemId);
			}
		} else {
			return dictMap;
		}
		return dictMap;
	}
	
	/**
	 * 动态新增字典
	 * @param paredictid
	 * @param dictName
	 * @return
	 */
	public static String dynamicAddDictType(String paredictid,String dictName) {
		TawSystemDictType tawSystemDictType = new TawSystemDictType();
		tawSystemDictType.setParentDictId(paredictid);
		tawSystemDictType.setDictName(dictName);
		
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) ApplicationContextHolder
		.getInstance().getBean("ItawSystemDictTypeManager");
//		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) DataSyncTest.factory.getBean("ItawSystemDictTypeManager");
		
		String newdictid = mgr.getMaxDictid(paredictid);
		TawSystemDictType dictType = new TawSystemDictType();
		dictType = mgr.getDictTypeByDictid(paredictid);
		if (tawSystemDictType.getDictId() == null
				|| tawSystemDictType.getDictId().equals("")) {
			tawSystemDictType.setDictId(newdictid);
		}

		tawSystemDictType.setDictCode(tawSystemDictType.getDictId());

		dictType.setLeaf(Integer.valueOf(StaticVariable.NOTLEAF));
		tawSystemDictType.setSysType(new Integer(dictType.getSysType()
				.intValue() + 1));
		tawSystemDictType.setLeaf(Integer.valueOf(StaticVariable.LEAF));
		mgr.saveTawSystemDictType(dictType);
		mgr.saveTawSystemDictType(tawSystemDictType);
		
		return newdictid;
	}
	
	public static String getNewDictId(String paredictid) {
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) ApplicationContextHolder
		.getInstance().getBean("ItawSystemDictTypeManager");
//		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) DataSyncTest.factory.getBean("ItawSystemDictTypeManager");
		
		String newdictid = mgr.getMaxDictid(paredictid);
		
		return newdictid;
	}
}
