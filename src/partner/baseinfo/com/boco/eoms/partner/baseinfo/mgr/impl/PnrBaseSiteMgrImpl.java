package com.boco.eoms.partner.baseinfo.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.baseinfo.dao.IPnrBaseSiteDao;
import com.boco.eoms.partner.baseinfo.mgr.IPnrBaseSiteMgr;
import com.boco.eoms.partner.baseinfo.model.PnrBaseSite;

/**
 * <p>
 * Title:合作伙伴站址信息管理
 * </p>
 * <p>
 * Description:站址信息管理
 * </p>
 * <p>
 * Wed Mar 24 14:01:56 CST 2010
 * </p>
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
public class PnrBaseSiteMgrImpl implements IPnrBaseSiteMgr {
 
	private IPnrBaseSiteDao  pnrBaseSiteDao;
 	
	public IPnrBaseSiteDao getPnrBaseSiteDao() {
		return this.pnrBaseSiteDao;
	}
 	
	public void setPnrBaseSiteDao(IPnrBaseSiteDao pnrBaseSiteDao) {
		this.pnrBaseSiteDao = pnrBaseSiteDao;
	}
 	
    public List getPnrBaseSites() {
    	return pnrBaseSiteDao.getPnrBaseSites();
    }
    
    public PnrBaseSite getPnrBaseSite(final String id) {
    	return pnrBaseSiteDao.getPnrBaseSite(id);
    }
    
    public void savePnrBaseSite(PnrBaseSite pnrBaseSite) {
    	pnrBaseSiteDao.savePnrBaseSite(pnrBaseSite);
    }
    public void removePnrBaseSite(final String id) {

    	pnrBaseSiteDao.removePnrBaseSite(id);
    }
    public void removePnrBaseSite(final String id,final String user) {
    	PnrBaseSite pnrBaseSite = pnrBaseSiteDao.getPnrBaseSite(id);
    	pnrBaseSite.setDelTime(StaticMethod.getLocalTime());
    	pnrBaseSite.setDelUser(user);
    	pnrBaseSite.setIsDel("1");
    	pnrBaseSiteDao.savePnrBaseSite(pnrBaseSite);
    }
    
    public Map getPnrBaseSites(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return pnrBaseSiteDao.getPnrBaseSites(curPage, pageSize, whereStr);
	}
    public List listProviderOfCity(final String cityId) {
    	return pnrBaseSiteDao.listProviderOfCity(cityId);
    }
}