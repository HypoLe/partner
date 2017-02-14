package com.boco.eoms.partner.inspect.scheduler;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMgr;
import com.boco.eoms.partner.inspect.util.InspectConstants;
import com.google.common.primitives.Ints;

/** 
 * Description: 巡检计划轮询(自动生成计划、资源、巡检项)
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 12, 2012 3:06:23 PM 
 */
public class InspectPlanScheduler implements Job{

	public void execute(JobExecutionContext context) throws JobExecutionException {
		LocalDate now = new LocalDate();
		Integer day = now.getDayOfMonth();
		
		if(1 == day){ //如果当天是当月第一天
			//生成巡检资源
			this.generateInspectPlanRes(now);
			//生成巡检项
			this.generateInspectPlanItem();
			//生成巡检计划
			//本操作两个步骤：
			//1：复制所有计划
			//2：根据上月计划关联的所有元任务，将本月新生成的元任务关联到复制出来的计划上。
			this.generateInspectPlan();
			//将本月必须执行的巡检资源关联到合适的计划上(跨月的并且本月必须执行的所有元任务关联到本月计划中，依据是元任务的周期结束时间小于当前月的结束时间)
			this.updatePlanResForceAssignMatchedPlan(now);
			//更新巡检计划关联的资源数目
			this.updatePlanResNum(now);
		}
	}
	
	/**
	 * 生成巡检资源
	 * 生成条件：资源已经分配周期和巡检小组并且该资源所属资源类别的巡检模板已经建立
	 * @param now
	 */
	public void generateInspectPlanRes(LocalDate now){
		long startTime = System.currentTimeMillis();
		Integer month = now.getMonthOfYear();
		String cycleStartTime = now + " 00:00:00"; 
		String cycleEndTime = "";
		
		//各种周期生成巡检资源的月份
		int[] yearScope = {1};
		int[] halfOfYearScope = {1,7};
		int[] quarterScope = {1,4,7,10};
		int[] doubleMonthScope = {1,3,5,7,9,11};
		
		//年
		if(Ints.contains(yearScope, month)){ 
			cycleEndTime = now.dayOfYear().withMaximumValue() + " 23:59:59"; 
			this.generateInspectPlanRes(InspectConstants.PERIOD_OF_YEAR, cycleStartTime, cycleEndTime);
		}
		//半年
		if(Ints.contains(halfOfYearScope, month)){
			cycleEndTime = now.plusMonths(5).dayOfMonth().withMaximumValue() + " 23:59:59"; 
			this.generateInspectPlanRes(InspectConstants.PERIOD_OF_HALF_YEAR, cycleStartTime, cycleEndTime);
		}
		//季度
		if(Ints.contains(quarterScope, month)){
			cycleEndTime = now.plusMonths(2).dayOfMonth().withMaximumValue() + " 23:59:59"; 
			this.generateInspectPlanRes(InspectConstants.PERIOD_OF_QUARTER, cycleStartTime, cycleEndTime);
		}
		//双月
		if(Ints.contains(doubleMonthScope, month)){
			cycleEndTime = now.plusMonths(1).dayOfMonth().withMaximumValue() + " 23:59:59"; 
			this.generateInspectPlanRes(InspectConstants.PERIOD_OF_DOUBLE_MONTH, cycleStartTime, cycleEndTime);
		}
		//月
		cycleEndTime = now.dayOfMonth().withMaximumValue() + " 23:59:59"; 
		this.generateInspectPlanRes(InspectConstants.PERIOD_OF_MONTH, cycleStartTime, cycleEndTime);
		//周
		LocalDate weekStart = null;
		LocalDate weekEnd = null;
		int maxWeekCountOfOneMonth = 6; //一个月最多包含的周数
		for(int i=0;i<maxWeekCountOfOneMonth;i++){
			weekStart = now.dayOfWeek().withMinimumValue().plusWeeks(i);
			if(weekStart.getMonthOfYear() == month){//如果周开始第一天的所在月份是当月
				weekEnd = weekStart.dayOfWeek().withMaximumValue();
				this.generateInspectPlanRes(InspectConstants.PERIOD_OF_WEEK, weekStart+ " 00:00:00", weekEnd + " 23:59:59");
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println(month+"月份所有地市巡检资源生成共耗时:"+(endTime-startTime)+"ms，" +
				"如果未生成成功请检查资源是否已经分配周期和巡检小组，并且该资源所属资源类别的巡检模板是否已经创建");
	}
	
	/**
	 * 生成巡检计划资源
	 * 规则：按周期为所有地市分别生成所有巡检资源
	 * @param cycle
	 * @param cycleStartTime
	 * @param cycleEndTime
	 */
	public void generateInspectPlanRes(String cycle,String cycleStartTime,String cycleEndTime){
		String cycleName = InspectConstants.cycleMap.get(cycle);
		System.out.println(cycleName + ",周期开始:"+cycleStartTime+",周期结束:"+cycleEndTime);
		
		IInspectPlanMgr inspectPlanMgr = (IInspectPlanMgr)ApplicationContextHolder
					.getInstance().getBean("inspectPlanMgr");
		String today = new LocalDate().toString();
		String city = "";
		try {
			List<Map<String,Object>> cityList = inspectPlanMgr.findResConfigAllCity();
			for (Map<String, Object> cityMap : cityList) {
				city = cityMap.get("city").toString();
				try {
					inspectPlanMgr.generateInspectPlanRes(cycle, city, cycleStartTime, cycleEndTime,today);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("地市代码为"+city+"周期为"+cycleName+"的巡检资源生成失败");
				}
			}
			System.out.println("周期为"+cycleName+"的巡检资源生成完毕");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("所有地市周期为"+cycleName+"的巡检资源生成失败");
		}
	}
	
	/**
	 * 生成巡检项
	 * 规则：查询当天生成的巡检资源并按所有地市分别生成巡检项
	 */
	public void generateInspectPlanItem(){
		IInspectPlanMgr inspectPlanMgr = (IInspectPlanMgr)ApplicationContextHolder
					.getInstance().getBean("inspectPlanMgr");
		String today = new LocalDate().toString();
		String city = "";
		try {
			List<Map<String,Object>> cityList = inspectPlanMgr.findResConfigAllCity();
			for (Map<String, Object> cityMap : cityList) {
				city = cityMap.get("city").toString();
				try {
					inspectPlanMgr.generateInspectPlanItem(city,today);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("地市代码为"+city+"的巡检资源巡检项生成失败");
				}
				System.out.println("地市代码为"+city+"的巡检资源巡检项生成成功");
			}
			System.out.println("巡检资源巡检项生成完毕，如果未生成成功请检查资源所属资源类别的巡检模板项是否已经创建");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("所有地市的巡检资源巡检项生成失败");
		}
	}
	
	/**
	 * 更新巡检计划关联的资源数目
	 * @param now
	 */
	public void updatePlanResNum(LocalDate now){
		try {
			Integer year = new LocalDate().getYear();
			Integer currentMonth = new LocalDate().getMonthOfYear();
			IInspectPlanMgr inspectPlanMgr = (IInspectPlanMgr)ApplicationContextHolder
				.getInstance().getBean("inspectPlanMgr");
			inspectPlanMgr.updatePlanResNum(year.toString(), currentMonth.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("更新巡检计划关联的资源数目");
		}
	}
	
	/**
	 * 生成巡检计划
	 */
	public void generateInspectPlan(){
		IInspectPlanMgr inspectPlanMgr = (IInspectPlanMgr)ApplicationContextHolder
					.getInstance().getBean("inspectPlanMgr");
		ITawSystemAreaManager areaMgr = (ITawSystemAreaManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemAreaManager");
		String city = "";
		String cityName = "";
		Integer currentYear = new LocalDate().getYear();  //当前年
		Integer currentMonth = new LocalDate().getMonthOfYear(); //当前月
		Integer monthOfLastPlan = 0; //上月计划所在月
		Integer yearOfLastPlan = 0;  //上月计划所在年
		if(1 != currentMonth){
			monthOfLastPlan = currentMonth - 1;
			yearOfLastPlan = currentYear;
		}else{
			monthOfLastPlan = 12;
			yearOfLastPlan = currentYear -1;
		}
		//本月最后一天
		String maxCycleEndTime = new LocalDate().dayOfMonth().withMaximumValue().toString() + " 23:59:59";
		try {
			List<Map<String,Object>> cityList = inspectPlanMgr.findResConfigAllCity();
			for (Map<String, Object> cityMap : cityList) {
				city = cityMap.get("city").toString();
				cityName = areaMgr.getAreaByAreaId(city).getAreaname();
				try {
					//查询该地市上月所有计划
					List<Map<String,Object>> planIdList = inspectPlanMgr.findPlanIdWithPnrDept(city, yearOfLastPlan.toString(), monthOfLastPlan.toString());
					if(null == planIdList || planIdList.size() == 0){//该地市上月没有制定计划
						System.out.println(cityName+"上月未制定巡检计划");
						continue;
					}
					//为每个上月计划生成一个当月计划ID
					for (Map<String, Object> map : planIdList) {
						map.put("newPlanId", UUIDHexGenerator.getInstance().getID());
					}
					//生成该地市巡检计划
					inspectPlanMgr.saveNewPlan(planIdList, currentYear.toString(), currentMonth.toString(), maxCycleEndTime);
					System.out.println(cityName+"的巡检计划生成成功");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(cityName+"的巡检计划生成失败");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("所有地市的巡检计划生成失败");
		}
	}
	
	/**
	 * 将本月必须执行的巡检资源关联到合适的计划上
	 * (本操作可以将所有资源关联到合适的计划上)
	 * @param now
	 */
	public void updatePlanResForceAssignMatchedPlan(LocalDate now){
		try {
			IInspectPlanMgr inspectPlanMgr = (IInspectPlanMgr)ApplicationContextHolder
					.getInstance().getBean("inspectPlanMgr");
			Integer year = new LocalDate().getYear();
			Integer currentMonth = new LocalDate().getMonthOfYear();
			//周期结束日期最早为本月第一天
			String minCycleEndTime = new LocalDate().dayOfMonth().withMinimumValue().toString() + " 00:00:00";
			//周期结束日期最晚为本月最后一天
			String maxCycleEndTime = new LocalDate().dayOfMonth().withMaximumValue().toString() + " 23:59:59";
			inspectPlanMgr.updatePlanResForceAssignMatchedPlan(year.toString(), currentMonth.toString(), minCycleEndTime,maxCycleEndTime);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("将本月必须执行的巡检资源关联到合适的计划上失败");
		}
		
	}
}
