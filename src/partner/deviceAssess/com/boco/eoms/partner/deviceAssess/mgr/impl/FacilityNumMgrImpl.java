package com.boco.eoms.partner.deviceAssess.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.deviceAssess.dao.FacilityNumDao;
import com.boco.eoms.partner.deviceAssess.mgr.FacilityNumMgr;
import com.boco.eoms.partner.deviceAssess.model.FacilityNum;

/**
 * <p>
 * Title:设备量信息
 * </p>
 * <p>
 * Description:设备量信息
 * </p>
 * <p> 
 * Wed Sep 29 11:28:40 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class FacilityNumMgrImpl implements FacilityNumMgr {
 
	private FacilityNumDao  facilityNumDao;
 	
	public FacilityNumDao getFacilityNumDao() {
		return this.facilityNumDao;
	}
 	
	public void setFacilityNumDao(FacilityNumDao facilityNumDao) {
		this.facilityNumDao = facilityNumDao;
	}
 	
    public List getFacilityNums() {
    	return facilityNumDao.getFacilityNums();
    }
    
    public FacilityNum getFacilityNum(final String id) {
    	return facilityNumDao.getFacilityNum(id);
    }
    
    public void saveFacilityNum(FacilityNum facilityNum) {
    	facilityNumDao.saveFacilityNum(facilityNum);
    }
    
    public void removeFacilityNum(final String id) {
    	facilityNumDao.removeFacilityNum(id);
    }
    
    public Map getFacilityNums(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return facilityNumDao.getFacilityNums(curPage, pageSize, whereStr);
	}
	
}