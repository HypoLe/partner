package com.boco.eoms.partner.serviceArea.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.serviceArea.model.Point;
import com.boco.eoms.partner.serviceArea.mgr.IPointMgr;
import com.boco.eoms.partner.serviceArea.dao.IPointDao;

/**
 * <p>
 * Title:数据点
 * </p>
 * <p>
 * Description:服务资源配置 数据点
 * </p>
 * <p>
 * Mon Nov 23 11:36:10 CST 2009
 * </p>
 * 
 * @author wangjunfeng
 * @version 1.0
 * 
 */
public class PointMgrImpl implements IPointMgr {
 
	private IPointDao  pointDao;
 	
	public IPointDao getPointDao() {
		return this.pointDao;
	}
 	
	public void setPointDao(IPointDao pointDao) {
		this.pointDao = pointDao;
	}
 	
    public List getPoints() {
    	return pointDao.getPoints();
    }
 
    public List getPointName(final String pointName) {
    	return pointDao.getPointName(pointName);
    }
 
    public List listCityOfArea(final String areaId,final String len) {
    	return pointDao.listCityOfArea(areaId,len);
    }

    public List listCountryOfCity(final String cityId,final String len) {
    	return pointDao.listCountryOfCity(cityId,len);
    }
    
    public Point getPoint(final String id) {
    	return pointDao.getPoint(id);
    }
    
    public void savePoint(Point point) {
    	pointDao.savePoint(point);
    }
    
    public void removePoint(final String id) {
    	pointDao.removePoint(id);
    }
    
    public Map getPoints(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return pointDao.getPoints(curPage, pageSize, whereStr);
	}
	
}