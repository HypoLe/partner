package com.boco.eoms.partner.inspect.mgr;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.inspect.model.InspectTemplateItem;

/**
 * 
 * Description:  巡检模版子项service
 * Copyright:   Copyright (c)2009 
 * Company:     boco 
 * @author:     lee 
 * @version:    1.0 
 * Create at:   Jul 11, 2012 4:01:12 PM
 */
public interface IInspectTemplateItemMgr extends CommonGenericService<InspectTemplateItem>{
	
	public void updateInspectTemplateItem(String templateId);

}
