package com.boco.activiti.partner.process.dao;
import java.util.Map;

/**
 
 */
public interface IFaultSataDao{
	/**
	 * 故障统计表 by city 查询
	  * @author zhoukeqing 
	  * @title: FaultStatCityList
	  * @date Aug 10, 2016 3:39:51 PM
	  * @param endTime
	  * @param startTime   
	  * @return Map<String,Object>
	 */
	public Map<String,Object> faultStatCityList(String startTime,String endTime);
	/**
	 * 故障统计表 by County 查询
	  * @author zhoukeqing 
	  * @title: FaultStatCountyList
	  * @date Aug 10, 2016 3:39:51 PM
	  * @param endTime
	  * @param startTime   
	  * @return Map<String,Object>
	 */
	public Map<String,Object> faultStatCountyList(String startTime,String endTime,String city);
	/**
	 * 超时工单数量查询
	  * @author xujinliang 
	  * @title: timeoutGongdanList
	  * @date Aug 10, 2016 3:39:51 PM
	  * @param type
	  * @param city   
	  * @return Map<String,Object>
	 */
	public Map<String,Object> timeoutGongdanList(String type,String city,String startTime,String endTime,String cityId);
}
