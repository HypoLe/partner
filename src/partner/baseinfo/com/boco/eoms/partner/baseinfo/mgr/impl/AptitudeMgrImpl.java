package com.boco.eoms.partner.baseinfo.mgr.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.baseinfo.model.Aptitude;
import com.boco.eoms.partner.baseinfo.mgr.IAptitudeMgr;
import com.boco.eoms.partner.baseinfo.dao.IAptitudeDao;

/**
 * <p>
 * Title:合作伙伴资质
 * </p>
 * <p>
 * Description:合作伙伴资质
 * </p>
 * <p>
 * Fri Dec 18 14:11:52 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class AptitudeMgrImpl implements IAptitudeMgr {
 
	private IAptitudeDao  aptitudeDao;
 	
	public IAptitudeDao getAptitudeDao() {
		return this.aptitudeDao;
	}
 	
	public void setAptitudeDao(IAptitudeDao aptitudeDao) {
		this.aptitudeDao = aptitudeDao;
	}
 	
    public List getAptitudes() {
    	return aptitudeDao.getAptitudes();
    }
    
    public Aptitude getAptitude(final String id) {
    	return aptitudeDao.getAptitude(id);
    }
    
    public void saveAptitude(Aptitude aptitude) {
    	aptitudeDao.saveAptitude(aptitude);
    }
    
    public void removeAptitude(final String id) {
    	aptitudeDao.removeAptitude(id);
    }
    
    public Map getAptitudes(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return aptitudeDao.getAptitudes(curPage, pageSize, whereStr);
	}
    
	public Map getAptitudes(final Integer curPage, final Integer pageSize,
			final String whereStr, final Date asts ,final Date aste ,final Date aets, final Date aete) {
    	return aptitudeDao.getAptitudes(curPage, pageSize, whereStr, asts, aste, aets, aete);
    }
	
	public List getAptitudes(final String where){
		return aptitudeDao.getAptitudes(where);
	}
}