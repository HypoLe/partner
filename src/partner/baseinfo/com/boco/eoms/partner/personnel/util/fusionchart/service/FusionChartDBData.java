package com.boco.eoms.partner.personnel.util.fusionchart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.partner.personnel.util.MyUtil;
import com.boco.eoms.partner.personnel.util.fusionchart.bean.XMLData;
	/**
 * <p>
 * Title:取得统计数据
 * </p>
 * <p>
 * Description:取得统计数据
 * </p>
 * <p>
 * Jul 27, 2012 10:51:13 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class FusionChartDBData {
	
	/**
	 * 
	 * @param sql   统计查询字段   最后一字段必须为统计结果 ，倒数第二字段必须为默认统计条件
	 * @param fieldStr	//默认统计条件 字段名 有别名的写别名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  List<XMLData>getDBXMLData(String sql,String fieldStr){
		if(MyUtil.isEmpty(fieldStr))
			throw new RuntimeException("\n\n统计纬度字段为空");
		List<XMLData>  xmlDataList = new ArrayList<XMLData>();
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		List<ListOrderedMap> resultList = jdbcService.queryForList(sql);
		if(resultList.size()==0)
			return null;
		//解析查询结果，进行封装
		int mapSize = resultList.get(0).size(); //查询的字段数量  通过数量 确定要拼接的lable 和 统计数量
		if(mapSize<2)
			throw new RuntimeException("\n\n统计字段有误 ,结果集字段数少于2个");
		boolean fieldStrIsRight = false;
		for (ListOrderedMap map : resultList) {
			
			if(!fieldStrIsRight)
				for(int i=0,k=map.size();i<k;i++){
					if(map.get(i).toString().equals(fieldStr))
						fieldStrIsRight = true;
				}
			if(!fieldStrIsRight)
				throw new RuntimeException("\n\n统计字段有误,结果集中没有找到 ："+fieldStr);
			
			
			XMLData xmlData = new XMLData();
			StringBuilder xmlLable = new StringBuilder();
			if(mapSize==2)
				xmlLable.append(map.get(fieldStr).toString());
			else{
				String field,value ;
				for(int i=0;i<mapSize-2;i++){
					field = map.get(i).toString();//字段名
					value = map.getValue(i).toString(); //数据
					value = MyUtil.dict2Name(field, value);
					xmlLable.append(value).append(";");//
				}
				xmlLable.deleteCharAt(xmlLable.length()-1);
			}
			
			xmlData.setCategoryLable(xmlLable.toString());
			xmlData.setDatasetName(MyUtil.dict2Name(fieldStr, map.get(fieldStr).toString()));
			xmlData.setValue(map.getValue(map.size()-1).toString());
			
			xmlDataList.add(xmlData);
		}
		return xmlDataList;
	}
	
	public  List<XMLData>getSimpleDBXMLData(String sql,String[] statisticsFields){
		if(statisticsFields.length<0||MyUtil.isEmpty(statisticsFields[0]))
			throw new RuntimeException("\n\n统计纬度字段为空");
		List<XMLData>  xmlDataList = new ArrayList<XMLData>();
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		List<ListOrderedMap> resultList = jdbcService.queryForList(sql);
		if(resultList.size()==0)
			return null;
		//解析查询结果，进行封装
		int mapSize = resultList.get(0).size(); //查询的字段数量  通过数量 确定要拼接的lable 和 统计数量
		//if(mapSize<=statisticsFields.length)//modify by fengguangping 
		if(mapSize<statisticsFields.length)//modify by fengguangping 
			throw new RuntimeException("\n\n统计字段有误 ,结果集字段数少于"+statisticsFields.length+"个");
		String[] setNames,values;
		for (ListOrderedMap map : resultList) {
			XMLData xmlData = new XMLData();
			StringBuilder xmlLable = new StringBuilder();
			String field,value ;
				if (mapSize==statisticsFields.length) {//不添加标签 modify by fengguangping,增加当字段相同时的情况
					field=";";
					value="";
					value = MyUtil.dict2Name(field, value);
					xmlLable.append(value).append(";");
				}else {
					for(int i=0;i<mapSize-statisticsFields.length;i++){
						field = StaticMethod.nullObject2String(map.get(i));//字段名
						value = StaticMethod.nullObject2String(map.getValue(i)); //数据
						value = MyUtil.dict2Name(field, value);
						xmlLable.append(value).append(";");//       
					}
				}
			xmlLable.deleteCharAt(xmlLable.length()-1);
			
			xmlData.setCategoryLable(xmlLable.toString());
			
			values = new String[statisticsFields.length];
			setNames = new String[statisticsFields.length];
			for(int i = 0;i<statisticsFields.length;i++){
				setNames[i] = statisticsFields[i];
			    values[i] = MyUtil.dict2Name(StaticMethod.nullObject2String(map.get(mapSize-statisticsFields.length+i)),
			    		StaticMethod.nullObject2String(map.getValue(mapSize-statisticsFields.length+i)));
			}
			xmlData.setDatasetNames(setNames);
			xmlData.setValues(values);
			
			xmlDataList.add(xmlData);
		}
		return xmlDataList;
	}
	
}
