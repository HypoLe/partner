package com.boco.eoms.partner.inspect.dao;

import java.util.List;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;
import com.boco.eoms.partner.inspect.model.PnrInspectTrack;

/** 
 * Description: 巡检轨迹DAO 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     lee 
 * @version:    1.0 
 * Create at:   Jul 9, 2012 9:58:17 AM 
 */
public interface IPnrInspectTrackDao extends CommonGenericDao<PnrInspectTrack, String>{
	public String getTimeOnSite(String res_id);
	public List getLineByLocation(String sql);
}
