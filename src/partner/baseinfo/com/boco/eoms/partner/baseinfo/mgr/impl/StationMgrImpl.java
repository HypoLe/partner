package com.boco.eoms.partner.baseinfo.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.baseinfo.model.Station;
import com.boco.eoms.partner.baseinfo.mgr.IStationMgr;
import com.boco.eoms.partner.baseinfo.dao.IStationDao;

/**
 * <p>
 * Title:驻点
 * </p>
 * <p>
 * Description:驻点
 * </p>
 * <p>
 * Fri Dec 18 09:31:48 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class StationMgrImpl implements IStationMgr {
 
	private IStationDao  stationDao;
 	
	public IStationDao getStationDao() {
		return this.stationDao;
	}
 	
	public void setStationDao(IStationDao stationDao) {
		this.stationDao = stationDao;
	}
 	
    public List getStations() {
    	return stationDao.getStations();
    }
    
    public Station getStation(final String id) {
    	return stationDao.getStation(id);
    }
    
    public void saveStation(Station station) {
    	stationDao.saveStation(station);
    }
    
    public void removeStation(final String id) {
    	stationDao.removeStation(id);
    }
    
    public Map getStations(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return stationDao.getStations(curPage, pageSize, whereStr);
	}
	
    public List getStations(final String where){
    	return stationDao.getStations(where);
    }
}