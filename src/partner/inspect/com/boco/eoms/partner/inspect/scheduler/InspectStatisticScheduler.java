package com.boco.eoms.partner.inspect.scheduler;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanStatisticMgr;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/** 
 * Description: 统计轮询
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Aug 2, 2012 9:13:51 AM 
 */
public class InspectStatisticScheduler implements Job{
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LocalDate today = new LocalDate();
		//采集区域巡检统计数据
		saveStatisticAreaData(today);
		//采集代维公司巡检统计数据
		saveStatisticPartnerData(today);
		//更新巡检计划的计划完成数
		updateInspectPlanResDoneNum();
		//按地市更新资源的超时未完成状态
		updateInspectPlanResState();
	}
	
	/**
	 * 采集区域巡检数据
	 */
	@SuppressWarnings("unchecked")
	public void saveStatisticAreaData(LocalDate today){
		try {
			IInspectPlanStatisticMgr inspectPlanStatisticMgr = (IInspectPlanStatisticMgr)ApplicationContextHolder
					.getInstance().getBean("inspectPlanStatisticMgr");
			ITawSystemAreaManager areaMgr = (ITawSystemAreaManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemAreaManager");
			//获取当前年月日
			Integer year = today.getYear();
			Integer month = today.getMonthOfYear();
			int day = today.getDayOfMonth();
			
			//如果当天是当月第一天
			if(1 == day){
				//将当前数据移动到历史表
				inspectPlanStatisticMgr.moveStatisticAreaDataToHis();
			}
			//删除当前数据
			inspectPlanStatisticMgr.deleteStatisticAreaData();
			
			//采集地市数据
			PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) ApplicationContextHolder.getInstance().getBean("pnrBaseAreaIdList");
			String provinceAreaId = pnrBaseAreaIdList.getRootAreaId();
			List<TawSystemArea> cityList = areaMgr.getSonAreaByAreaId(provinceAreaId);
			List<Map<String,String>> list =Lists.newArrayList();
			for (TawSystemArea tawSystemArea : cityList) {
				Map<String,String> map = Maps.newHashMap();
				String city = tawSystemArea.getAreaid();
				map.put("city", city);
				map.put("id", UUIDHexGenerator.getInstance().getID());
				list.add(map);
			}
			inspectPlanStatisticMgr.saveStatisticCityData(list, year.toString(), month.toString());
			
			//采集区县数据
			String city = "";
			for (TawSystemArea tawSystemArea : cityList) {
				List<Map<String,String>> countryMapList =Lists.newArrayList();
				city = tawSystemArea.getAreaid();
				List<TawSystemArea> countryList = areaMgr.getSonAreaByAreaId(city);
				for (TawSystemArea countryArea : countryList) {
					Map<String,String> map = Maps.newHashMap();
					String countryId = countryArea.getAreaid();
					map.put("country", countryId);
					map.put("id", UUIDHexGenerator.getInstance().getID());
					countryMapList.add(map);
				}
				try {
					inspectPlanStatisticMgr.saveStatisticCountryData(countryMapList, year.toString(), month.toString(),city);
				} catch (Exception e) {
					e.printStackTrace();
					String cityName = areaMgr.getAreaByAreaId(city).getAreaname();
					System.out.println(cityName+"("+city+")的巡检数据统计失败");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("区域巡检数据统计失败");
		}
	}
	
	/**
	 * 采集代维公司巡检数据
	 */
	@SuppressWarnings("unchecked")
	public void saveStatisticPartnerData(LocalDate today){
		try {
			IInspectPlanStatisticMgr inspectPlanStatisticMgr = (IInspectPlanStatisticMgr)ApplicationContextHolder
					.getInstance().getBean("inspectPlanStatisticMgr");
			//获取当前年月日
			Integer year = today.getYear();
			Integer month = today.getMonthOfYear();
			int day = today.getDayOfMonth();
			
			//如果是月初
			if(1 == day){
				//将当前数据移动到历史表
				inspectPlanStatisticMgr.moveStatisticPartnerDataToHis();
			}
			//删除当前数据
			inspectPlanStatisticMgr.deleteStatisticPartnerData();
			
			//采集总代维公司数据
			List<Map<String,String>> allHeadPartnerIdList = inspectPlanStatisticMgr.findAllHeadPartnerId();
			for (Map<String,String> headPartnerIdMap : allHeadPartnerIdList) {
				headPartnerIdMap.put("id", UUIDHexGenerator.getInstance().getID());
			}
			inspectPlanStatisticMgr.saveStatisticHearPartnerData(allHeadPartnerIdList, year.toString(), month.toString());
			
			//采集地市代维公司数据
			String headPartnerName = "";
			for (Map<String,String> headPartnerIdMap  : allHeadPartnerIdList) {
				try {
					headPartnerName = headPartnerIdMap.get("partnerName");
					List<Map<String,String>> cityPartnerMapList = inspectPlanStatisticMgr.findAllSubPartner(headPartnerIdMap.get("partnerId"));
					for (Map<String, String> cityPartnerMap : cityPartnerMapList) {
						cityPartnerMap.put("id", UUIDHexGenerator.getInstance().getID());
					}
					inspectPlanStatisticMgr.saveStatisticCityPartnerData(cityPartnerMapList, year.toString(), month.toString(),headPartnerIdMap.get("partnerId"));
					
					//采集区县代维公司数据
					String cityPartnerName = "";
					for (Map<String, String> cityPartnerMap : cityPartnerMapList) {
						cityPartnerName = cityPartnerMap.get("partnerName");
						List<Map<String,String>> countryPartnerMapList = inspectPlanStatisticMgr.findAllSubPartner(cityPartnerMap.get("partnerId"));
						for (Map<String, String> countryPartnerMap : countryPartnerMapList) {
							countryPartnerMap.put("id", UUIDHexGenerator.getInstance().getID());
						}
						try {
							inspectPlanStatisticMgr.saveStatisticCountryPartnerData(countryPartnerMapList, year.toString(), month.toString(),cityPartnerMap.get("partnerId"));
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println(cityPartnerName+"下的区县代维公司巡检数据统计失败");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(headPartnerName+"下的地市代维公司巡检数据统计失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("代维公司巡检数据统计失败");
		}
	}
	
	/**
	 * 更新巡检计划的计划完成数
	 */
	public void updateInspectPlanResDoneNum(){
		try {
			IInspectPlanMgr inspectPlanMgr = (IInspectPlanMgr)ApplicationContextHolder
					.getInstance().getBean("inspectPlanMgr");
			inspectPlanMgr.updateInspectPlanResDoneNum();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("轮询更新巡检计划的计划完成数失败");
		}
	}
	
	
	/**
	 * 按地市更新资源的超时未完成状态
	 * inspectState巡检状态为 2超时未关联未完成或者 3超时已关联未完成
	 */
	public void updateInspectPlanResState(){
		IInspectPlanMgr inspectPlanMgr = (IInspectPlanMgr)ApplicationContextHolder
					.getInstance().getBean("inspectPlanMgr");
		String city = "";
		String now = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
		try {
			List<Map<String,Object>> cityList = inspectPlanMgr.findResConfigAllCity();
			for (Map<String, Object> cityMap : cityList) {
				city = cityMap.get("city").toString();
				try {
					inspectPlanMgr.updateInspectPlanResState(city, now);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("地市代码为"+city+"的巡检资源更新超时未完成状态失败");
				}
				System.out.println("地市代码为"+city+"的巡检资源更新超时未完成状态成功");
			}
			System.out.println("所有地市巡检资源更新超时未完成状态完毕");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("所有地市的巡检资源更新超时未完成状态失败");
		}
	}
}
