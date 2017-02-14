package com.boco.eoms.partner.serviceArea.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.serviceArea.dao.ISitePapersDao;
import com.boco.eoms.partner.serviceArea.mgr.ISitePapersMgr;
import com.boco.eoms.partner.serviceArea.model.SitePapers;

/**
 * <p>
 * Title:基站证件信息
 * </p>
 * <p>
 * Description:基站证件信息
 * </p>
 * <p>
 * Wed Nov 18 11:29:29 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class SitePapersMgrImpl implements ISitePapersMgr {
 
	private ISitePapersDao  sitePapersDao;
 	
	public ISitePapersDao getSitePapersDao() {
		return this.sitePapersDao;
	}
 	
	public void setSitePapersDao(ISitePapersDao sitePapersDao) {
		this.sitePapersDao = sitePapersDao;
	}
 	
    public List getSitePaperss() {
    	return sitePapersDao.getSitePaperss();
    }
    
    public SitePapers getSitePapers(final String id) {
    	return sitePapersDao.getSitePapers(id);
    }
    
    public void saveSitePapers(SitePapers sitePapers) {
    	sitePapersDao.saveSitePapers(sitePapers);
    }
    
    public void removeSitePapers(final String id) {
    	sitePapersDao.removeSitePapers(id);
    }
    
    public Map getSitePaperss(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return sitePapersDao.getSitePaperss(curPage, pageSize, whereStr);
	}

	public List getSitePapersByPaperNo(String papersNo) {
		return sitePapersDao.getSitePapersByPaperNo(papersNo);
	}
	
	public List getSitePapersBySiteNo( final Integer siteNo ){
		return sitePapersDao.getSitePapersBySiteNo(siteNo);
	}
}