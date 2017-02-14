package com.boco.activiti.partner.process.dao;

import java.util.List;

import com.boco.activiti.partner.process.po.ChildSceneReportsModel;


public interface ISchemeRateJDBCDao {
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
}
