package com.boco.eoms.partner.inspect.mgr.impl;

import java.util.List;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.inspect.dao.IInspectPlanGisDao;
import com.boco.eoms.partner.inspect.dao.IInspectPlanGisDaoJdbc;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanGisMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanGis;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     liaojiming
 * @version:    1.0 
 */
public class InspectPlanGisMgrImpl extends CommonGenericServiceImpl<InspectPlanGis> implements IInspectPlanGisMgr {

	private IInspectPlanGisDao inspectPlanGisDao;
	
	private IInspectPlanGisDaoJdbc inspectPlanGisDaoJdbc;
	
	public IInspectPlanGisDao getInspectPlanGisDao() {
		return inspectPlanGisDao;
	}

	public void setInspectPlanGisDao(IInspectPlanGisDao inspectPlanGisDao) {
		this.setCommonGenericDao(inspectPlanGisDao);
		this.inspectPlanGisDao = inspectPlanGisDao;
	}

	public IInspectPlanGisDaoJdbc getInspectPlanGisDaoJdbc() {
		return inspectPlanGisDaoJdbc;
	}

	public void setInspectPlanGisDaoJdbc(
			IInspectPlanGisDaoJdbc inspectPlanGisDaoJdbc) {
		this.inspectPlanGisDaoJdbc = inspectPlanGisDaoJdbc;
	}

	/**
	 * 按地市保存任务的完成数
	 */
	public void saveInspectPlanGis() {
		//1.将表pnr_inspect_gis_cityres中的数据全部删除
		inspectPlanGisDaoJdbc.deleteInspectGisCityres();
		//2.往表pnr_inspect_gis_cityres 中添加数据
		inspectPlanGisDaoJdbc.saveInspectPlanGis();
		
	}

	/**
	 * 按部门保存任务的完成数
	 */
	public void saveInspectPlanGisPnrDept() {
		//将按部门统计的数据全部删除
		inspectPlanGisDaoJdbc.deleteInspectGisPnrDept();
		//重新按统计任务的完成情况
		inspectPlanGisDaoJdbc.saveInspectPlanGisPnrDept();
		
	}
	
	/**
	 * 查询当前地市下任务的完成数
	 * @param city
	 * @return
	 */
	public List findInspectplanGis(String city){
		return inspectPlanGisDaoJdbc.findInspectplanGis(city);
	}
	
	
}
