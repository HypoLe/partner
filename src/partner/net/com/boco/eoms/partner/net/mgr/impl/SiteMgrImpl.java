package com.boco.eoms.partner.net.mgr.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.partner.net.dao.ISiteDao;
import com.boco.eoms.partner.net.mgr.ISiteMgr;
import com.boco.eoms.partner.net.model.StationPoint;

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
 
    /**
     * 根据id批量删除
     * @param id 主键
     * 
     */
    public void removeSites(final String[] ids){
    	siteDao.removeSites(ids);
    }
    
    public List listCityOfArea(final String areaId,final String len) {
    	return siteDao.listCityOfArea(areaId,len);
    }

    public List listCountryOfCity(final String cityId,final String len) {
    	return siteDao.listCountryOfCity(cityId,len);
    }

    public StationPoint getSite(final String id) {
    	return siteDao.getSite(id);
    }
    
    public void saveSite(StationPoint site) {
    	siteDao.saveSite(site);
    }
    
    public void removeSite(final String id) {
    	siteDao.removeSite(id);
    }
    
    public Map getSites(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return siteDao.getSites(curPage, pageSize, whereStr);
	}

    
    public Map getCycSite(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return siteDao.getCycSite(curPage, pageSize, whereStr);
	}
    
    public List getSitesBySiteNo( final String siteNo ){
    	return siteDao.getSitesBySiteNo(siteNo);
    }  
    
    public StationPoint getSiteBySiteNo(final String id){
    	return siteDao.getSiteBySiteNo(id);
    }   
    public Integer getSitesNo(final String whereStr) {
    	return siteDao.getSitesNo(whereStr);
    }
    
    public List getSitesByWhere(final String whereStr){
    	return siteDao.getSitesByWhere(whereStr);
    }
	/**
	 *
	 * 根据地市得到各网格的基站数汇总信息
	 * @return 返回各网格的基站数汇总信息
	 */
	public Map getSitesByGrid(final String city){
		List siteList = siteDao.getSitesByCity(city);
		Map siteNumMap = new HashMap();
		float siteTotal = 0;
		for(int i=0;i<siteList.size();i++){
			Object[] object =(Object[])siteList.get(i);
			siteNumMap.put(String.valueOf(object[0]), object[1]);
			siteTotal = siteTotal + Float.parseFloat(String.valueOf(object[1]));
		}
		siteNumMap.put("siteTotal", String.valueOf(siteTotal));
		return siteNumMap;
	}

}