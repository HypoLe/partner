package com.boco.eoms.partner.assess.AssWeight.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.assess.AssWeight.dao.ICityWeightExeDao;
import com.boco.eoms.partner.assess.AssWeight.mgr.ICityWeightExeMgr;
import com.boco.eoms.partner.assess.AssWeight.model.CityWeightExe;

/**
 * <p>
 * Title:地市配置权重
 * </p>
 * <p>
 * Description:地市配置权重
 * </p>
 * <p>
 * Tue Feb 22 15:42:16 CST 2011
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public abstract class CityWeightExeMgrImpl implements ICityWeightExeMgr {
 
	private ICityWeightExeDao  cityWeightExeDao;
 	
	public ICityWeightExeDao getCityWeightExeDao() {
		return this.cityWeightExeDao;
	}
 	
	public void setCityWeightExeDao(ICityWeightExeDao cityWeightExeDao) {
		this.cityWeightExeDao = cityWeightExeDao;
	}
 	
    public List getCityWeightExes() {
    	return cityWeightExeDao.getCityWeightExes();
    }
    
    public CityWeightExe getCityWeightExe(final String id) {
    	return cityWeightExeDao.getCityWeightExe(id);
    }
    
    public void saveCityWeightExe(CityWeightExe cityWeightExe) {
    	cityWeightExeDao.saveCityWeightExe(cityWeightExe);
    }
    
    public void removeCityWeightExe(final String id) {
    	cityWeightExeDao.removeCityWeightExe(id);
    }
    
    public Map getCityWeightExes(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return cityWeightExeDao.getCityWeightExes(curPage, pageSize, whereStr);
	}

	public List getCityWeightExes(final String where){
		return cityWeightExeDao.getCityWeightExes(where);
	}
}