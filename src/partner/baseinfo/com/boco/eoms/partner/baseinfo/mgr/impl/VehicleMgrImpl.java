package com.boco.eoms.partner.baseinfo.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.baseinfo.model.Vehicle;
import com.boco.eoms.partner.baseinfo.mgr.IVehicleMgr;
import com.boco.eoms.partner.baseinfo.dao.IVehicleDao;

/**
 * <p>
 * Title:车辆配置
 * </p>
 * <p>
 * Description:资源配置管理 车辆配置
 * </p>
 * <p>
 * Mon Dec 07 19:17:45 CST 2009
 * </p>
 * 
 * @author wangjunfeng
 * @version 1.0
 * 
 */
public class VehicleMgrImpl implements IVehicleMgr {
 
	private IVehicleDao  vehicleDao;
 	
	public IVehicleDao getVehicleDao() {
		return this.vehicleDao;
	}
 	
	public void setVehicleDao(IVehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}
 	
    public List getVehicles() {
    	return vehicleDao.getVehicles();
    }
    
    public List listCityOfUser(final String userName) {
    	return vehicleDao.listCityOfUser(userName);
    }

    public List listCityOfAreaName(final String areaName) {
    	return vehicleDao.listCityOfAreaName(areaName);
    }

    public List listCityOfArea(final String areaid) {
    	return vehicleDao.listCityOfArea(areaid);
    }

    public List listCountyOfCity(final String cityId,final String len) {
    	return vehicleDao.listCountyOfCity(cityId,len);
    }
    

    public List isUnique(final String whereStr) {
    	return vehicleDao.isUnique(whereStr);
    }

    
    public Vehicle getVehicle(final String id) {
    	return vehicleDao.getVehicle(id);
    }
    
    public void saveVehicle(Vehicle vehicle) {
    	vehicleDao.saveVehicle(vehicle);
    }
    
    public void removeVehicle(final String id) {
    	vehicleDao.removeVehicle(id);
    }
    
    public Map getVehicles(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return vehicleDao.getVehicles(curPage, pageSize, whereStr);
	}
	
}