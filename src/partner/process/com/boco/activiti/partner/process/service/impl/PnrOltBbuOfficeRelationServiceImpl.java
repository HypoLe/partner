package com.boco.activiti.partner.process.service.impl;

import java.util.Map;

import com.boco.activiti.partner.process.dao.IPnrOltBbuOfficeRelationDao;
import com.boco.activiti.partner.process.dao.IPnrOltBbuOfficeRelationJDBCDao;
import com.boco.activiti.partner.process.model.PnrOltBbuOfficeRelation;
import com.boco.activiti.partner.process.po.PnrOltBbuOfficeMainModel;
import com.boco.activiti.partner.process.service.IPnrOltBbuOfficeRelationService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

/**
 * olt-bbu机房优化工单和机房关联 
 * 
 * @author Jun
 *
 */
public class PnrOltBbuOfficeRelationServiceImpl extends
		CommonGenericServiceImpl<PnrOltBbuOfficeRelation> implements
		IPnrOltBbuOfficeRelationService {
	
	private IPnrOltBbuOfficeRelationDao pnrOltBbuOfficeRelationDao;
	
	private IPnrOltBbuOfficeRelationJDBCDao pnrOltBbuOfficeRelationJDBCDao;

	public IPnrOltBbuOfficeRelationDao getPnrOltBbuOfficeRelationDao() {
		return pnrOltBbuOfficeRelationDao;
	}

	public void setPnrOltBbuOfficeRelationDao(
			IPnrOltBbuOfficeRelationDao pnrOltBbuOfficeRelationDao) {
		this.pnrOltBbuOfficeRelationDao = pnrOltBbuOfficeRelationDao;
		this.setCommonGenericDao(pnrOltBbuOfficeRelationDao);
	}

	public IPnrOltBbuOfficeRelationJDBCDao getPnrOltBbuOfficeRelationJDBCDao() {
		return pnrOltBbuOfficeRelationJDBCDao;
	}

	public void setPnrOltBbuOfficeRelationJDBCDao(
			IPnrOltBbuOfficeRelationJDBCDao pnrOltBbuOfficeRelationJDBCDao) {
		this.pnrOltBbuOfficeRelationJDBCDao = pnrOltBbuOfficeRelationJDBCDao;
	}
	

	/**
	 * 
	 * 查询OLT-BBU机房优化申请流程工单详情 （工单信息+机房信息+优化信息+环节人员等）
	 *  
	 * @param processInstanceId
	 * @return
	 */
	public PnrOltBbuOfficeMainModel findPnrOltBbuOfficeMainByProcessInstanceId(String processInstanceId){
		return pnrOltBbuOfficeRelationJDBCDao.findPnrOltBbuOfficeMainByProcessInstanceId(processInstanceId);
	}
	
	/**
	 * 
	 * 删除olt-bbu工单和机房关联表数据
	 * 
	 * @param param 删除参数
	 */
	public void deletePnrOltBbuOfficeRelation(Map<String,Object> param){
		pnrOltBbuOfficeRelationJDBCDao.deletePnrOltBbuOfficeRelation(param);
	}
	
	/**
	 * 
	 * 根据工单流程id查询出于该工单关联的图片
	 * 
	 * @param processInstanceId	流程号
	 * @param param	其他查询参数
	 * @return
	 */
	public String[] getPhotoByProcessInstanceId(String processInstanceId,Map<String,String> param){
		return pnrOltBbuOfficeRelationJDBCDao.getPhotoByProcessInstanceId(processInstanceId, param);
	}
	
	
}
