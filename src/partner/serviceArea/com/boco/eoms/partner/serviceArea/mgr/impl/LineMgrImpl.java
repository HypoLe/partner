package com.boco.eoms.partner.serviceArea.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.serviceArea.dao.ILineDao;
import com.boco.eoms.partner.serviceArea.mgr.ILineMgr;
import com.boco.eoms.partner.serviceArea.model.Line;

/**
 * <p>
 * Title:线路
 * </p>
 * <p>
 * Description:服务资源配置 线路
 * </p>
 * <p>
 * Fri Nov 13 10:10:56 CST 2009
 * </p>
 * 
 * @author wangjunfeng
 * @version 1.0
 * 
 */
public class LineMgrImpl implements ILineMgr {
 
	private ILineDao  lineDao;
 	
	public ILineDao getLineDao() {
		return this.lineDao;
	}
 	
	public void setLineDao(ILineDao lineDao) {
		this.lineDao = lineDao;
	}
 	
    public List getLines() {
    	return lineDao.getLines();
    }
    
    public Line getLine(final String id) {
    	return lineDao.getLine(id);
    }
    
    public List getLineName(final String lineName) {
    	return lineDao.getLineName(lineName);
    }
    
    public List listCityOfArea(final String areaId,final String len) {
    	return lineDao.listCityOfArea(areaId,len);
    }

    public List listCountryOfCity(final String cityId,final String len) {
    	return lineDao.listCountryOfCity(cityId,len);
    }

    
    public List listProviderOfCity(final String region,final String city) {
    	return lineDao.listProviderOfCity(region,city);
    }
    
    public List listProviderByRegion(final String region) {
    	return lineDao.listProviderByRegion(region);
    }

    public void saveLine(Line line) {
    	lineDao.saveLine(line);
    }
  
    //判断段落名称是否已经存在 
    public Boolean isUnique(Line line) {
    	return lineDao.isUnique(line);
    }

    public void removeLine(final String id) {
    	lineDao.removeLine(id);
    }


    public Map getLines(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return lineDao.getLines(curPage, pageSize, whereStr);
	}
	
}