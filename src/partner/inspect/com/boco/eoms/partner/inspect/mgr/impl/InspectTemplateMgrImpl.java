package com.boco.eoms.partner.inspect.mgr.impl;


import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.inspect.dao.IInspectTemplateDao;
import com.boco.eoms.partner.inspect.mgr.IInspectTemplateMgr;
import com.boco.eoms.partner.inspect.model.InspectTemplate;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     lee 
 * @version:    1.0 
 * Create at:   Jul 9, 2012 10:09:39 AM 
 */
public class InspectTemplateMgrImpl extends CommonGenericServiceImpl<InspectTemplate> 
							implements IInspectTemplateMgr {
	private IInspectTemplateDao inspectTemplateDao;

	public IInspectTemplateDao getInspectTemplateDao() {
		return inspectTemplateDao;
	}

	public void setInspectTemplateDao(IInspectTemplateDao inspectTemplateDao) {
		this.setCommonGenericDao(inspectTemplateDao);
		this.inspectTemplateDao = inspectTemplateDao;
	}

	

	
	
}
