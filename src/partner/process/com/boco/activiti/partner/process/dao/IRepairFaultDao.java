package com.boco.activiti.partner.process.dao;

import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.model.NetResInspect;
import com.boco.activiti.partner.process.model.NetResInspectTurnToSendModel;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.SchemeRate;
import com.boco.activiti.partner.process.model.SchemeRateRejectModel;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;

/**
 
 */
public interface IRepairFaultDao{
	/**
	 * 地市故障类型统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	public Map<String,Object> faultTypeListPage(String startTime,String endTime);
	/**
	 * 区县故障类型统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	public Map<String,Object> repairFaultqxList(String county,String startTime,String endTime);
	/**
	 * 非故障统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	public Map<String,Object> nonFaultcsList(String startTime,String endTime);
	/**
	 * 区县非故障统计list查询
	  * @author xujinliang 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	public Map<String,Object> nonFaultqxList(String county,String startTime,String endTime);
}
