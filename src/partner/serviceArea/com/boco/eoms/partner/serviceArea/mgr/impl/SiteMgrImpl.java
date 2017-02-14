package com.boco.eoms.partner.serviceArea.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.serviceArea.dao.ISiteDao;
import com.boco.eoms.partner.serviceArea.mgr.ISiteMgr;
import com.boco.eoms.partner.serviceArea.model.Site;

/**
 * <p>
 * Title:站点信息管理
 * </p>
 * <p>
 * Description:站点信息管理
 * </p>
 * <p>
 * Tue Nov 17 17:51:41 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class SiteMgrImpl implements ISiteMgr {
 
	private ISiteDao  siteDao;
 	
	public ISiteDao getSiteDao() {
		return this.siteDao;
	}
 	
	public void setSiteDao(ISiteDao siteDao) {
		this.siteDao = siteDao;
	}
 	
    public List getSites() {
    	return siteDao.getSites();
    }
 
    
    public List listCityOfArea(final String areaId,final String len) {
    	return siteDao.listCityOfArea(areaId,len);
    }

    public List listCountryOfCity(final String cityId,final String len) {
    	return siteDao.listCountryOfCity(cityId,len);
    }

    public Site getSite(final String id) {
    	return siteDao.getSite(id);
    }
    
    public void saveSite(Site site) {
    	siteDao.saveSite(site);
    }
    
    public void removeSite(final String id) {
    	siteDao.removeSite(id);
    }
    
    public Map getSites(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return siteDao.getSites(curPage, pageSize, whereStr);
	}
	
    public List getSitesBySiteNo( final String siteNo ){
    	return siteDao.getSitesBySiteNo(siteNo);
    }  
    
    public Site getSiteBySiteNo(final String id){
    	return siteDao.getSiteBySiteNo(id);
    }   
    public Integer getSitesNo(final String whereStr) {
    	return siteDao.getSitesNo(whereStr);
    }
}