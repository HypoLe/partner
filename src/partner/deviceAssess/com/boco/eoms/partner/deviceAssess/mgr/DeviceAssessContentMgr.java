package com.boco.eoms.partner.deviceAssess.mgr;

import java.util.List;

import com.boco.eoms.partner.deviceAssess.model.DeviceAssessContent;

/** 
 * Description: 审批意见 
 * Copyright:   Copyright (c)2011
 * Company:     BOCO
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Nov 2, 2011 3:48:15 PM 
 */
public interface DeviceAssessContentMgr {
	public void saveAssessContent(DeviceAssessContent content);
	
	/**
	 * 查询审批意见
	 * @param assessId
	 * @return
	 */
	public List<DeviceAssessContent> findAssessContentList(String assessId);
}
