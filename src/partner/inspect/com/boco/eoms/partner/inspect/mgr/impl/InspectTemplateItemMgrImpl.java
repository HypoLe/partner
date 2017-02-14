package com.boco.eoms.partner.inspect.mgr.impl;


import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.inspect.dao.IInspectTemplateItemDao;
import com.boco.eoms.partner.inspect.mgr.IInspectTemplateItemMgr;
import com.boco.eoms.partner.inspect.model.InspectTemplateItem;

/** 
 * Description: 巡检模版子项service实现
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     lee 
 * @version:    1.0 
 * Create at:   Jul 9, 2012 10:09:39 AM 
 */
public class InspectTemplateItemMgrImpl extends CommonGenericServiceImpl<InspectTemplateItem> 
							implements IInspectTemplateItemMgr {
	private IInspectTemplateItemDao inspectTemplateItemDao;

	public IInspectTemplateItemDao getInspectTemplateItemDao() {
		return inspectTemplateItemDao;
	}

	public void setInspectTemplateItemDao(
			IInspectTemplateItemDao inspectTemplateItemDao) {
		this.inspectTemplateItemDao = inspectTemplateItemDao;
		this.setCommonGenericDao(inspectTemplateItemDao);
	}
//	http://localhost:8082/eoms/services/TemplateItem?wsdl
	public String getString(){
		return "aaaaaaaaaaaaaaaa";
	}

	public void updateInspectTemplateItem(String templateId) {
		
		String hql = " update  InspectTemplateItem set status=0 where templateId= '"+templateId+"'";
		
		inspectTemplateItemDao.bulkUpdateByHql(hql);
	}
}
