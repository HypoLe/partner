package com.boco.eoms.partner.deviceInspect.service;


import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.netresource.service.IEomsService;
import com.googlecode.genericdao.search.SearchResult;

/**
 * 
 * 描述：设备巡检service
 * 作者：zhangkeqi
 * 时间：Jan 30, 2013-11:02:57 AM
 */
public interface IDeviceInspectService extends IEomsService {

	/**
	 * 产生巡检任务（和以前的IInspectPlanMgr中的generateInspectPlanRes一样）
	 * @param cycle 周期
	 * @param city  地市     
	 * @param cycleStartTime 周期开始时间
	 * @param cycleEndTime   周期结束时间   
	 * @param createTime     资源产生日期   
	 */
	public void generateInspectPlanRes(String cycle,String city,
			String cycleStartTime,String cycleEndTime,String createTime);
	
	/**
	 * 产生巡检计划执行项
	 * @param city 地市
	 * @param createTime 资源产生日期   
	 */
	public void generateInspectPlanItem(String city,String createTime);
	
	/**
	 * 产生突发巡检任务
	 * @param ids
	 * @return
	 */
	public String generateBurstInspectPlanRes(String ids);
	
	/**
	 * 产生突发巡检计划执行项
	 * @param city 地市
	 * @param createTime 资源产生日期
	 */
	public void generateBurstInspectPlanItem(String createTime);
	
	/**
	 * 生成设备巡检(元任务和巡检项)
	 */
	public void generateBurstDeviceInspect(String ids);
	
	/**
	 * 生成设备巡检(元任务和巡检项)
	 */
	public void generateBurstDeviceInspect(String ids,String planStartTime,String planEndTime);
	/**
	 * 生成设备巡检(元任务和巡检项) 2013-08-23
	 */
	public void generateBurstDeviceInspect(String ids,String cycleStartTime,String cycleEndTime,String createTime);
	
	public SearchResult test();
	/**
	 * 通过资源点ID，返回周期的时间list
	 */
	public List<Map<String,String>> generateCycleTime(String ids);
}
