package com.boco.activiti.partner.process.dao;

import java.util.Map;

import com.boco.activiti.partner.process.po.PnrOltBbuOfficeMainModel;


public interface IPnrOltBbuOfficeRelationJDBCDao {
	
	/**
	 * 
	 * 查询OLT-BBU机房优化申请流程工单详情 （工单信息+机房信息+优化信息+环节人员等）
	 *  
	 * @param processInstanceId
	 * @return
	 */
	public PnrOltBbuOfficeMainModel findPnrOltBbuOfficeMainByProcessInstanceId(String processInstanceId);
	
	/**
	 * 
	 * 删除olt-bbu工单和机房关联表数据
	 * 
	 * @param param 删除参数
	 */
	public void deletePnrOltBbuOfficeRelation(Map<String,Object> param);
	
	/**
	 * 
	 * 根据工单流程id查询出于该工单关联的图片
	 * 
	 * @param processInstanceId	流程号
	 * @param param	其他查询参数
	 * @return
	 */
	public String[] getPhotoByProcessInstanceId(String processInstanceId,Map<String,String> param);
	
}
