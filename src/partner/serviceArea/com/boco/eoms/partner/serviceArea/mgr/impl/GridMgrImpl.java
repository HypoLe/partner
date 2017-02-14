package com.boco.eoms.partner.serviceArea.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.serviceArea.dao.IGridDao;
import com.boco.eoms.partner.serviceArea.mgr.IGridMgr;
import com.boco.eoms.partner.serviceArea.model.Grid;

/**
 * <p>
 * Title:网格
 * </p>
 * <p>
 * Description:网格
 * </p>
 * <p>
 * Tue Nov 17 17:51:41 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class GridMgrImpl implements IGridMgr {
 
	private IGridDao  gridDao;
 	
	public IGridDao getGridDao() {
		return this.gridDao;
	}
 	
	public void setGridDao(IGridDao gridDao) {
		this.gridDao = gridDao;
	}
 	
    public List getGrids() {
    	return gridDao.getGrids();
    }

    public List getGridsByWhere( final String whereStr ){
    	return gridDao.getGridsByWhere(whereStr);
    }
    
    public List listCityOfArea(final String areaId,final String len) {
    	return gridDao.listCityOfArea(areaId,len);
    }

    public List listCountryOfCity(final String cityId,final String len) {
    	return gridDao.listCountryOfCity(cityId,len);
    }
    
    public Grid getGrid(final String id) {
    	return gridDao.getGrid(id);
    }
    
    public void saveGrid(Grid grid) {
    	gridDao.saveGrid(grid);
    }
    
    public void removeGrid(final String id) {
    	gridDao.removeGrid(id);
    }
    
    public Map getGrids(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return gridDao.getGrids(curPage, pageSize, whereStr);
	}

    public List getGridsByGridName( final String gridName ){
    	return gridDao.getGridsByGridName(gridName);
    }
 
}