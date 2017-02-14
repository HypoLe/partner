package com.boco.eoms.partner.deviceAssess.util.excelimport;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.partner.deviceAssess.model.InsideDispose;

public abstract class DataSaveCallback {
	public abstract void doSaveRow2Data(HSSFRow row) throws Exception ;
	
	/**
	 * 
	 * @param name 			 字典值名
	 * @param initDictId	 该字典模块的字典值
	 * @return
	 * @throws Exception
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
			throw new RuntimeException("導入數據與數據字典不匹配！");
		}
		return id;
	}
	
	/**
	 * 
	 * @param initDictId	字典模块值
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> getDictMap(String initDictId) throws Exception{
		if("".equals(initDictId) || initDictId == null) {
			return null;
		}
		
		// 取字典数据
		ITawSystemDictTypeManager dictManager = (ITawSystemDictTypeManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemDictTypeManager");
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
				
				dictMap.put(itemId, itemName);
			}
		} else {
			return null;
		}
		return dictMap;
	}
	
	/**
	 * 两日期相减 
	 * @param date1 yyyy-MM-dd HH:mm:ss
	 * @param date2 yyyy-MM-dd HH:mm:ss
	 * @return 日期差值（h:mm)
	 */
	public String dateSubtract(Date date1,Date date2) {
		Long date1TimeMs = date1.getTime();
		Long date2TimeMs = date2.getTime();
		
		Long resumeLong = date1TimeMs - date2TimeMs;
		int minute = (int)(resumeLong/60000);   //共计分钟数
		int hour = minute/60;  //共计小时数
		int minuteLast = minute%60;
		String minuteString = String.valueOf(minuteLast);
		if(minuteString.length()==1){
			minuteString = "0" + minuteString  ;
		}
		String result = String.valueOf(hour)+":"+minuteString;
		
		return result;
	}
	
	/**
	 * 将每行数据构建为考核指标对象
	 * @param <T>
	 * @param row  Excel行
	 * @return
	 * @throws Exception
	 */
	public abstract <T> T fromRow2Object(HSSFRow row) throws Exception;
}
