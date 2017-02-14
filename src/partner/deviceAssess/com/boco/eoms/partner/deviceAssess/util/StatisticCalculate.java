package com.boco.eoms.partner.deviceAssess.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.boco.eoms.base.expression.Expression2;
import com.boco.eoms.base.expression.Param;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.partner.deviceAssess.mgr.AssessIndicatorService;
import com.boco.eoms.partner.deviceAssess.mgr.AssessIndicatorSubService;
import com.boco.eoms.partner.deviceAssess.model.AssessIndicatorSub;
import com.googlecode.genericdao.search.Search;

public class StatisticCalculate {

	
	
	
	
	
	/**
	 * 判断MAP 中得value最大值或者最小值
	 * @param map 要判断的MAP
	 * @param ifmax 传入‘max’ 返回最大值 传入‘min’ 返回最小值
	 * @return
	 */
	public static  Map maxOrMin(Map<String,Float> map,String ifmax){
		Set  keys = map.keySet();
		if("max".equals(ifmax)){
			Map  maxMap = new HashMap();
			Float max = new Float(0);
			String maxKey="";
			for (Object key : keys) {
				if((Float)map.get(key)>max){
					max = (Float)map.get(key);
					maxKey=key.toString();
				}
				
			}
			maxMap.put(maxKey, max);
			return maxMap;
		}
		else if("min".equals(ifmax)){
			Map  minMap = new HashMap();
			Float min = new Float(0);
			String minKey="";
			for (Object key : keys) {
				if((Float)map.get(key)<=min){
					min = (Float)map.get(key);
				minKey=key.toString();
				}
			}
			minMap.put(minKey, min);
			return minMap;
		}
		return null;
	}
	
	/**
	 * 
	 * @param special 专业的ID 
	 * @return map  返回每个专业下对应的所有设备类型的一个分数的MAP.
	 */
	public  static Map getTheScore(String special){
		Map map = new HashMap();
		Map<String,Float> treeMap1 = new HashMap<String,Float> ();
		treeMap1.put("112150204",new Float(94));
		treeMap1.put("112150202",new Float(83.2));
		treeMap1.put( "112150205",new Float(82.2));
		treeMap1.put( "112150203",new Float(72.1));
		map.put("112160101", treeMap1);
		TreeMap<String,Float> treeMap2 = new TreeMap<String,Float>();
		treeMap2.put("112150204",new Float(94));
		treeMap2.put( "112150202",new Float(12.2));
		treeMap2.put("112150205",new Float(76.2));
		treeMap2.put( "112150203",new Float(89.3));
		treeMap2.put( "112150203",new Float(89.3));
		map.put("112160102", treeMap2);
		return map;
	}
	
	/**
	 * 
	 * @param IndicatorName 传入指标名字
	 * @return 返回对应的公式
	 */
	public static String expression(String indicatorname,String indicatorid){
		AssessIndicatorSubService assessIndicatorSubService = (AssessIndicatorSubService) ApplicationContextHolder
		.getInstance().getBean("assessIndicatorSubService");
		Search search = new Search();
		search.addFilterEqual("indicatorName", indicatorname);
		search .addFilterEqual("indicatorid", indicatorid);
		AssessIndicatorSub sub = assessIndicatorSubService.searchUnique(search);
		return sub.getArithmetic();
	}
	
}
