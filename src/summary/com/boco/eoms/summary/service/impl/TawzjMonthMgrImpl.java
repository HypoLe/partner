package com.boco.eoms.summary.service.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.summary.model.TawzjMonth;
import com.boco.eoms.summary.service.TawzjMonthMgr;
import com.boco.eoms.summary.dao.ITawzjMonthDao;

/**
 * <p>
 * Title:月工作总结
 * </p>
 * <p>
 * Description:true
 * </p>
 * <p>
 * Wed Jun 17 14:24:09 CST 2009
 * </p>
 * 
 * @author hanlu
 * @version 3.5
 * 
 */
public class TawzjMonthMgrImpl implements TawzjMonthMgr {
 
	private ITawzjMonthDao  tawzjMonthDao;
 	
	public ITawzjMonthDao getTawzjMonthDao() {
		return this.tawzjMonthDao;
	}
 	
	public void setTawzjMonthDao(ITawzjMonthDao tawzjMonthDao) {
		this.tawzjMonthDao = tawzjMonthDao;
	}
 	
    public List getTawzjMonths() {
    	return tawzjMonthDao.getTawzjMonths();
    }
    
    public TawzjMonth getTawzjMonth(final String id) {
    	return tawzjMonthDao.getTawzjMonth(id);
    }
    
    public void saveTawzjMonth(TawzjMonth tawzjMonth) {
    	tawzjMonthDao.saveTawzjMonth(tawzjMonth);
    }
    
    public void removeTawzjMonth(final String id) {
    	tawzjMonthDao.removeTawzjMonth(id);
    }
    
    public Map getTawzjMonths(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return tawzjMonthDao.getTawzjMonths(curPage, pageSize, whereStr);
	}
	
}