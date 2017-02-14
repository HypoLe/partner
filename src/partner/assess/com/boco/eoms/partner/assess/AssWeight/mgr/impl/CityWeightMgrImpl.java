package com.boco.eoms.partner.assess.AssWeight.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.assess.AssWeight.dao.ICityWeightDao;
import com.boco.eoms.partner.assess.AssWeight.mgr.ICityWeightMgr;
import com.boco.eoms.partner.assess.AssWeight.model.CityWeight;

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
public abstract class CityWeightMgrImpl implements ICityWeightMgr {
 
	private ICityWeightDao  cityWeightDao;
 	
	public ICityWeightDao getCityWeightDao() {
		return this.cityWeightDao;
	}
 	
	public void setCityWeightDao(ICityWeightDao cityWeightDao) {
		this.cityWeightDao = cityWeightDao;
	}
 	
    public List getCityWeights() {
    	return cityWeightDao.getCityWeights();
    }
    
    public CityWeight getCityWeight(final String id) {
    	return cityWeightDao.getCityWeight(id);
    }
    
    public void saveCityWeight(CityWeight cityWeight) {
    	cityWeightDao.saveCityWeight(cityWeight);
    }
    
    public void removeCityWeight(final String id) {
    	cityWeightDao.removeCityWeight(id);
    }
    
    public Map getCityWeights(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return cityWeightDao.getCityWeights(curPage, pageSize, whereStr);
	}

	public List getCityWeights(final String where){
		return cityWeightDao.getCityWeights(where);
	}
}