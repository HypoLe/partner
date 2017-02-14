package com.boco.eoms.partner.deviceAssess.mgr.impl;

import java.util.List;

import com.boco.eoms.partner.deviceAssess.dao.DeviceAssessContentDao;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessContentMgr;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessContent;

/** 
 * Description:  
 * Copyright:   Copyright (c)2011
 * Company:     BOCO
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Nov 2, 2011 3:48:32 PM 
 */
public class DeviceAssessContentMgrImpl implements DeviceAssessContentMgr {
	
	private DeviceAssessContentDao deviceAssessContentDao;

	public DeviceAssessContentDao getDeviceAssessContentDao() {
		return deviceAssessContentDao;
	}

	public void setDeviceAssessContentDao(
			DeviceAssessContentDao deviceAssessContentDao) {
		this.deviceAssessContentDao = deviceAssessContentDao;
	}
	
	public void saveAssessContent(DeviceAssessContent content){
		deviceAssessContentDao.saveObject(content);
	}
	
	public List<DeviceAssessContent> findAssessContentList(String assessId){
		String hql = "from DeviceAssessContent where assessId=?";
		return deviceAssessContentDao.findByVarParams(hql, assessId);
	}
}
