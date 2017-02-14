package com.boco.eoms.partner.deviceInspect.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.deviceManagement.common.pojo.EomsSearch;
import com.boco.eoms.partner.deviceInspect.dao.IDeviceInspectDao;
import com.boco.eoms.partner.deviceInspect.model.PnrInspectMapping;
import com.boco.eoms.partner.deviceInspect.service.IDeviceInspectService;
import com.boco.eoms.partner.netresource.service.impl.EomsServiceImpl;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.google.common.primitives.Ints;
import com.googlecode.genericdao.search.SearchResult;

public class DeviceInspectServiceImpl extends EomsServiceImpl  implements IDeviceInspectService{
	private IDeviceInspectDao deviceInspectDao;

	public void setDeviceInspectDao(IDeviceInspectDao deviceInspectDao) {
		this.deviceInspectDao = deviceInspectDao;
		this.setEomsDao(deviceInspectDao);
	}

	/**
	 * 产生巡检任务（和以前的IInspectPlanMgr中的generateInspectPlanRes一样）
	 * @param cycle 周期
	 * @param city  地市     
	 * @param cycleStartTime 周期开始时间
	 * @param cycleEndTime   周期结束时间   
	 * @param createTime     资源产生日期   
	 */
	public void generateInspectPlanRes(String cycle,String city,
			String cycleStartTime,String cycleEndTime,String createTime) {
		this.deviceInspectDao.generateInspectPlanRes(cycle, city,cycleStartTime,cycleEndTime,createTime);
	}
	
	/**
	 * 产生巡检计划执行项
	 * @param city 地市
	 * @param createTime 资源产生日期
	 */
	public void generateInspectPlanItem(String city,String createTime){
		this.deviceInspectDao.generateInspectPlanItem(city,createTime);
	}
	
	

	public SearchResult test() {
		this.setPersistentClass(PnrInspectMapping.class);
		EomsSearch eomsSearch = new EomsSearch();
		SearchResult searchResult = this.searchAndCount(eomsSearch);
		return searchResult;
	}
	
	/**
	 * 产生突发巡检计划执行项
	 * @param city 地市
	 * @param createTime 资源产生日期
	 */
	public String generateBurstInspectPlanRes(String ids) {
		String createTime = this.deviceInspectDao.generateBurstInspectPlanRes(ids);
		return createTime;
	}
	
	/**
	 * 产生突发巡检计划执行项
	 * @param city 地市
	 * @param createTime 资源产生日期
	 */
	public String generateBurstInspectPlanRes(String ids,String planStartTime,String planEndTime) {
		String planResId = this.deviceInspectDao.generateBurstInspectPlanRes(ids,planStartTime,planEndTime);
		return planResId;
	}
	/**
	 * 产生突发巡检计划执行项  2013-08-23
	 * @param city 地市
	 * @param createTime 资源产生日期
	 */
	public String generateBurstInspectPlanRes(String ids,String cycleStartTime,String cycleEndTime,String novalue){
		String planResId = this.deviceInspectDao.generateBurstInspectPlanRes( ids, cycleStartTime, cycleEndTime, novalue);

		return planResId;
	}

	/**
	 * 产生突发巡检任务
	 * @param ids
	 * @return
	 */
	public void generateBurstInspectPlanItem(String planResId) {
		this.deviceInspectDao.generateBurstInspectPlanItem(planResId);
	}
	
	/**
	 * 生成设备巡检(元任务和巡检项)
	 */
	public void generateBurstDeviceInspect(String ids) {
		String planResId = generateBurstInspectPlanRes(ids);
		generateBurstInspectPlanItem(planResId);
	}
	
	/**
	 * 生成设备巡检(元任务和巡检项)
	 */
	public void generateBurstDeviceInspect(String ids,String planStartTime,String planEndTime) {
		String planResId = generateBurstInspectPlanRes(ids,planStartTime,planEndTime);
		generateBurstInspectPlanItem(planResId);
	}
	/**
	 * 生成设备巡检(元任务和巡检项) 2013-08-23
	 */
	public void generateBurstDeviceInspect(String ids,String cycleStartTime,String cycleEndTime,String novalue){
		String planResId = generateBurstInspectPlanRes(ids,cycleStartTime,cycleEndTime,novalue);
		generateBurstInspectPlanItem(planResId);
	}

	
	public List<Map<String,String>> generateCycleTime(String ids){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		PnrResConfigMgr pnrResConfigMgr = (PnrResConfigMgr) ApplicationContextHolder
		.getInstance().getBean("PnrResConfigMgr");
		
		PnrResConfig pnr =pnrResConfigMgr.find(ids);
		String cycleString = pnr.getInspectCycle();
		
		Integer currentYear = new LocalDate().getYear();  //当前年
		Integer currentMonth = new LocalDate().getMonthOfYear(); //当前月
		Map<String, String> map =  new HashMap<String, String>();
		
		//各种周期生成巡检资源的月份
//		int[] yearScope = {1};
		int[] halfOfYearScope = {1,7};
		int[] quarterScope = {1,4,7,10};
		int[] doubleMonthScope = {1,3,5,7,9,11};
		
		int[] quarterScopeHelp={2,5,8,11};
		
		LocalDate now = new LocalDate();
		Integer month = now.getMonthOfYear();
		
		String cycleStartTime = now + " 00:00:00"; 
		String cycleEndTime = "";

		if(cycleString.equals("halfOfMonth"))
		{
//			计算当月的一般天数减一的值
			int plusNum = now.dayOfMonth().withMaximumValue().getDayOfMonth()/2-1;	
			//当前月当前的天数
			int dayNum = now.getDayOfMonth();
			int halfNum = plusNum+1;
			
			//是否过半月
			if(dayNum<=halfNum)
			{		//		前半月
				LocalDate	beforeMonthStart = now;
				LocalDate beforeMonthEnd=now.dayOfMonth().withMinimumValue().plusDays(plusNum);
					//	后半月
				LocalDate afterMonthStart =beforeMonthEnd.plusDays(1);
				LocalDate afterMonthEnd = now.dayOfMonth().withMaximumValue(); 
				
				map.put(beforeMonthStart.toString()+ " 00:00:00", beforeMonthEnd.toString()+ " 23:59:59");
				map.put(afterMonthStart.toString()+ " 00:00:00", afterMonthEnd.toString()+ " 23:59:59");
			}else
			{
				//后半月
				LocalDate afterMonthStart =now;
				LocalDate afterMonthEnd = now.dayOfMonth().withMaximumValue(); 
				map.put(afterMonthStart.toString()+ " 00:00:00", afterMonthEnd.toString()+ " 23:59:59");
				
			}
			
			
	    }else if(cycleString.equals("doubleMonth"))
	    {
	    	//双月开始月
	    	if(Ints.contains(doubleMonthScope, month))
	    	{
				cycleEndTime = now.plusMonths(1).dayOfMonth().withMaximumValue() + " 23:59:59"; 
				map.put(cycleStartTime, cycleEndTime);
	    	}else
	    	{
				cycleEndTime = now.dayOfMonth().withMaximumValue() + " 23:59:59"; 
				map.put(cycleStartTime, cycleEndTime);

	    	}
			
		}else if(cycleString.equals("month"))
		{
			cycleEndTime = now.dayOfMonth().withMaximumValue() + " 23:59:59"; 
			map.put(cycleStartTime, cycleEndTime);
			
		}else if(cycleString.equals("quarter"))
		{
			//季度开始月份
	    	if(Ints.contains(quarterScope, month))
	    	{
				cycleEndTime = now.plusMonths(2).dayOfMonth().withMaximumValue() + " 23:59:59"; 
				map.put(cycleStartTime, cycleEndTime);

	    	}else
	    	{
	    		//季度开始第二月份
	    		if(Ints.contains(quarterScopeHelp, month))
	    		{
	    			cycleEndTime = now.plusMonths(1).dayOfMonth().withMaximumValue() + " 23:59:59"; 
					map.put(cycleStartTime, cycleEndTime);
	    		}else
	    		{
		    		//季度开始末月份
	    			cycleEndTime = now.dayOfMonth().withMaximumValue() + " 23:59:59"; 
					map.put(cycleStartTime, cycleEndTime);
	    		}
	    		
	    	}
			
		}else if(cycleString.equals("halfOfYear")){
//			判断是否大于临界值 7
			if(Ints.contains(halfOfYearScope, month))
			{
				cycleEndTime = now.plusMonths(5).dayOfMonth().withMaximumValue() + " 23:59:59"; 
				map.put(cycleStartTime, cycleEndTime);

			}else
			{
				if(month<7)
				{
					cycleEndTime = now.plusMonths(6-month).dayOfMonth().withMaximumValue() + " 23:59:59"; 
					map.put(cycleStartTime, cycleEndTime);

				}else
				{
					cycleEndTime = now.plusMonths(12-month).dayOfMonth().withMaximumValue() + " 23:59:59"; 
					map.put(cycleStartTime, cycleEndTime);

				}
				
			}
			
		}else if(cycleString.equals("year"))
		{
			cycleEndTime = now.dayOfYear().withMaximumValue() + " 23:59:59"; 

			map.put(cycleStartTime, cycleEndTime);

		}else if(cycleString.equals("week"))
		{
			
			LocalDate weekStart = null;
			LocalDate weekEnd = null;
			int maxWeekCountOfOneMonth = 6; //一个月最多包含的周数
			for(int i=0;i<maxWeekCountOfOneMonth;i++){
				weekStart = now.dayOfWeek().withMinimumValue().plusWeeks(i);
				if(weekStart.getMonthOfYear() == month){//如果周开始第一天的所在月份是当月
					weekEnd = weekStart.dayOfWeek().withMaximumValue();
					
					map.put(weekStart.toString()+ " 00:00:00", weekEnd.toString()+ " 23:59:59");

				}
			}
		}
		
		list.add(map);
		
		return list;
		
	}

}
