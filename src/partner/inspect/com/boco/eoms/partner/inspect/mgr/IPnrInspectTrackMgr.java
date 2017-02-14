package com.boco.eoms.partner.inspect.mgr;

import java.util.List;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.inspect.model.PnrInspectTrack;

/**
 * 
 * Description:  巡检轨迹service
 * Copyright:   Copyright (c)2009 
 * Company:     boco 
 * @author:     lee 
 * @version:    1.0 
 * Create at:   Jul 11, 2012 4:01:12 PM
 */
public interface IPnrInspectTrackMgr extends CommonGenericService<PnrInspectTrack>{

	public String getTimeOnSite(String res_id);
	
	public List getLineByLocation(String sql);
	
}
