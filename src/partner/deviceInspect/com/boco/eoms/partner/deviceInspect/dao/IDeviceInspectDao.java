package com.boco.eoms.partner.deviceInspect.dao;

import com.boco.eoms.partner.netresource.dao.IEomsDao;

public interface IDeviceInspectDao extends IEomsDao {
	/**
	 * 产生巡检任务（和以前的IInspectPlanMgr中的generateInspectPlanRes一样）
	 * @param cycle 周期
	 * @param city  地市     
	 * @param cycleStartTime 周期开始时间
	 * @param cycleEndTime   周期结束时间      
	 * @param createTime     资源产生日期 
	 */
	public void generateInspectPlanRes(String cycle,String city,String cycleStartTime,
			String cycleEndTime,String createTime);
	
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
	 * 产生突发巡检任务
	 * @param ids
	 * @return
	 */
	public String generateBurstInspectPlanRes(String ids,String planStartTime,String planEndTime);
	
	/**
	 * 产生突发巡检计划执行项
	 * @param city 地市
	 * @param createTime 资源产生日期
	 */
	public void generateBurstInspectPlanItem(String createTime);
	/**
	 * 产生突发巡检任务 2013-08-23
	 * @param ids
	 * @param cycleStartTime
	 * @param cycleEndTime
	 * @param novalue
	 * @return
	 */
	public String generateBurstInspectPlanRes(String ids,String cycleStartTime,String cycleEndTime,String novalue);
	
	/**
	 * 确定自动任务执行的问题
	 */
	public int isPlanExcute(String date);
	/**
	 * 存储当前月自动任务执行时间
	 */
	public void insertPlanExcuteDate();
}
