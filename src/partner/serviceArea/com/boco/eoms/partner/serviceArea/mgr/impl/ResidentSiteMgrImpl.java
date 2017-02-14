package com.boco.eoms.partner.serviceArea.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.serviceArea.dao.IResidentSiteDao;
import com.boco.eoms.partner.serviceArea.mgr.IResidentSiteMgr;
import com.boco.eoms.partner.serviceArea.model.ResidentSite;

/**
 * <p>
 * Title:驻点信息管理
 * </p>
 * <p>
 * Description:驻点信息管理
 * </p>
 * <p>
 * Tue Nov 17 17:51:41 CST 2009
 * </p>
 * 
 * @version 1.0
 * 
 */
public class ResidentSiteMgrImpl implements IResidentSiteMgr {
 
	private IResidentSiteDao  residentSiteDao;
 	
	public IResidentSiteDao getResidentSiteDao() {
		return this.residentSiteDao;
	}
 	
	public void setResidentSiteDao(IResidentSiteDao residentSiteDao) {
		this.residentSiteDao = residentSiteDao;
	}
 	
    public List getResidentSites() {
    	return residentSiteDao.getResidentSites();
    }
 
    
    public List listCityOfArea(final String areaId,final String len) {
    	return residentSiteDao.listCityOfArea(areaId,len);
    }

    public List listCountryOfCity(final String cityId,final String len) {
    	return residentSiteDao.listCountryOfCity(cityId,len);
    }

    public ResidentSite getResidentSite(final String id) {
    	return residentSiteDao.getResidentSite(id);
    }
    
    public void saveResidentSite(ResidentSite residentSite) {
    	residentSiteDao.saveResidentSite(residentSite);
    }
    
    public void removeResidentSite(final String id) {
    	residentSiteDao.removeResidentSite(id);
    }
    
    public Map getResidentSites(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return residentSiteDao.getResidentSites(curPage, pageSize, whereStr);
	}
	
    public List getResidentSitesByResidentSiteNo( final String residentSiteNo ){
    	return residentSiteDao.getResidentSitesByResidentSiteNo(residentSiteNo);
    }  
    
    public ResidentSite getResidentSiteByResidentSiteNo(final String id){
    	return residentSiteDao.getResidentSiteByResidentSiteNo(id);
    }   
    public Integer getResidentSitesNo(final String whereStr) {
    	return residentSiteDao.getResidentSitesNo(whereStr);
    }
}