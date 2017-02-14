package com.boco.eoms.netresource.line.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.netresource.line.model.Lines;
import com.boco.eoms.netresource.line.service.LinesService;
import com.boco.eoms.partner.net.mgr.IGridMgr;
import com.boco.eoms.partner.serviceArea.model.Grid;

/**
 * Excel 数据导入合法性验证
* @Title: 
* 
* @Description: TODO
* 
* @author WangGuangping  
* 
* @date Feb 19, 2012 4:49:32 PM
* 
* @version V1.0   
*
 */

public abstract class LinesDataSaveCallback {
	
	/**
	 * 保存数据
	 * @param row
	 * @throws Exception
	 */
	public abstract void doSaveRow2Data(HSSFRow row) throws Exception ;
	
	/**
	 * 将每行数据构建为考核指标对象
	 * @param <T>
	 * @param row  Excel行
	 * @return
	 * @throws Exception
	 */
	//public abstract <T> T fromRow2Object(HSSFRow row) throws Exception;
	
	/**
	 * 
	 * @param initDictId	取地域信息 名称与ID
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> getDictMap(String initDictId) throws Exception{
		if("".equals(initDictId) || initDictId == null) {
			return null;
		}
		
		// 取地域信息 名称与ID
		ITawSystemAreaManager areaManager = (ITawSystemAreaManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemAreaManager");
		List list = areaManager.getAllSonAreaByAreaid(initDictId);

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
	
	/**
	 * 地域名称 转 ID
	 * @param name 			 地域名称
	 * @param initDictId	 该字典模块的字典值
	 * @return
	 * @throws Exception
	 */
	public String name2Id(String name,String initDictId) throws Exception{
		
		if(name == null || "".equals(name)){
			throw new RuntimeException("导入的地市或区县字段为空！");
		}
		
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
			throw new RuntimeException("导入的地市或区县字段与实际地域不匹配！");
		}
		return id;
	}
	
	
	/**
	 * 网格名称 转 ID
	 * @param name 网格名称
	 * @param initId 初始化ID
	 * @return
	 * @throws Exception
	 */
	public String gridName2Id(String gridName) throws Exception{
		IGridMgr gridMgr = (IGridMgr)ApplicationContextHolder.getInstance().getBean("gridMgr");
		List list = gridMgr.getGridsByWhere(" gridName = '"+gridName+"' ");
		if(list == null || list.isEmpty() || null == list.get(0)){
			throw new RuntimeException("导入的【网格名称】不存在！");
		}
		Grid grid = (Grid)list.get(0);
		return grid.getId();
	}
	
	/**
	 * 线路名称转ID
	 * @param linesName
	 * @return
	 */
	public String lineName2Id(String linesName) throws Exception{
		LinesService lineService = (LinesService)ApplicationContextHolder.getInstance().getBean("linesService");
		Lines lines = lineService.getLinesByProperty(" lineName = '"+linesName+"' ");
		if(lines == null || lines.getId() == null || "".equals(lines.getId())){
			throw new RuntimeException("导入的【线路名称】字段与实际线路不匹配！");
		}
		return lines.getId();
	}
	
	
	/**
	 * 经纬度坐标合法性验证
	 * @param langLat
	 * @return
	 */
	public String checkLongLat(String longLat){
		String pattern = "[0-9]{1,4}+(.[0-9]{1,7})";//必须为11位小数，小数点前1-4位，小数点后1-7位
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(longLat);   
        boolean flag = m.matches();   
        if(!flag) {
			throw new RuntimeException("导入的经纬度字段不符合要求(小数点前1-4位,小数点后1-7位)！");
		}
		return longLat;
	}
	
	/**
	 * 手机号码 合法性验证
	 * @param langLat
	 * @return
	 */
	public String checkMobile(String mobile){
		String pattern = "[1]{1}+([0-9]{10})";//必须为以1开始的11位数字
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(mobile);   
        boolean flag = m.matches();   
        if(!flag) {
			throw new RuntimeException("导入的手机号码不正确(必须为以1开始的11位数字)！");
		}
		return mobile;
	}
	
	/**
	 * 数值 合法性验证
	 * @param langLat
	 * @return
	 */
	public String checkNumber(String numbers){
		String pattern = "([0-9]{1,32})";//为1-32位的数字
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(numbers);   
        boolean flag = m.matches();   
        if(!flag) {
			throw new RuntimeException("导入的数字格式不正确(可能包含特殊字符或者字母等)！");
		}
		return numbers;
	}
	
	/**
	 * 检查字段否是为空
	 * @param str
	 * @return
	 */
	public String checkIsNull(String str){
		if(str == null || "".equals(str)){
			throw new RuntimeException("导入的字段为空！");
		}
		return str;
	}
	
	/**
	 * 时间字段为空检查及转换
	 * @param dateTime
	 * @return
	 */
	public String checkTime(Date date){
		if(date == null){
			throw new RuntimeException("导入的时间字段为空！");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	/**
	 * 检查带小数点的值
	 * @param str
	 * @return
	 */
	public String checkFloat(String str){
		String pattern = "[0-9]{1,5}+(.[0-9]{1,5})";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);   
        boolean flag = m.matches();   
        if(!flag) {
			throw new RuntimeException("导入的误差范围字段格式不正确！");
		}
		return str;
	}
	
}
