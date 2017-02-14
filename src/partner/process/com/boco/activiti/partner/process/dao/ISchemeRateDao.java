package com.boco.activiti.partner.process.dao;

import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.model.SchemeRateRejectModel;

/**
 
 */
public interface ISchemeRateDao{
	/**
	 * 方案合格率统计list查询
	  * @author zhoukeqing 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	public Map<String,Object> schemeRateList(String county,String startTime,String endTime);
	/**
	 * 方案合格率统计 驳回原因列表钻取list
	  * @author zhoukeqing 
	  * @title: schemeRateRejectList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return List<SchemeRateRejectModel>
	 */
	public List<SchemeRateRejectModel> schemeRateRejectList(String city,String startTime,String endTime,String themeinterface,int pageSize,int firstResult,int endResult);
	/**
	 * 方案合格率统计 驳回原因列表钻取total
	  * @author zhoukeqing 
	  * @title: schemeRateRejectTotal
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time themeinterface
	  * @return int
	 */
	public int schemeRateRejectTotal(String city,String startTime,String endTime,String themeinterface);
	
}
