package com.boco.activiti.partner.process.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.boco.activiti.partner.process.model.SchemeRate;
import com.boco.activiti.partner.process.model.SchemeRateRejectModel;
import com.boco.activiti.partner.process.po.ChildSceneReportsModel;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;

public interface ISchemeRateService extends CommonGenericService<SchemeRate> {
	/**
	 * 方案合格率统计list查询
	  * @author zhoukeqing 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return List<SchemeRate>
	 */
	public Map<String,Object> schemeRateList(String county,String startTime,String endTime);
	/**
	 * 方案合格率统计 驳回原因列表钻取list
	  * @author zhoukeqing 
	  * @title: schemeRateRejectList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time  themeinterface, pageSize, firstResult, endResult
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
	
	/**
	 *   子场景工单统计
	 	 * @author WANGJUN
	 	 * @title: childSceneReports
	 	 * @date Jul 21, 2016 9:43:00 AM
	 	 * @param sheetAcceptLimit
	 	 * @param sheetCompleteLimit
	 	 * @param cityId
	 	 * @return List<TaskModel>
	 */
	public List<ChildSceneReportsModel> childSceneReports(String sheetAcceptLimit,String sheetCompleteLimit,String cityId,String flag);
	
	/**
	 *   导出 子场景工单统计
	 	 * @author WANGJUN
	 	 * @title: exportChildSceneReports
	 	 * @date Jul 25, 2016 10:13:00 AM
	 	 * @param sheetAcceptLimit
	 	 * @param sheetCompleteLimit
	 	 * @param cityId
	 	 * @param flag
	 	 * @return HSSFWorkbook
	 */
	public HSSFWorkbook exportChildSceneReports(String sheetAcceptLimit,String sheetCompleteLimit,String cityId,String flag);
}
