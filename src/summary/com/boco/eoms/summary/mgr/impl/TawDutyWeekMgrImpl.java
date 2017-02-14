package com.boco.eoms.summary.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.summary.model.TawDutyWeek;
import com.boco.eoms.summary.mgr.TawDutyWeekMgr;
import com.boco.eoms.summary.dao.TawDutyWeekDao;

/**
 * <p>
 * Title:值周数据
 * </p>
 * <p>
 * Description:true
 * </p>
 * <p>
 * Tue Jun 16 17:25:37 CST 2009
 * </p>
 * 
 * @author LXM
 * @version 3.5
 * 
 */
public class TawDutyWeekMgrImpl implements TawDutyWeekMgr {
 
	private TawDutyWeekDao  tawDutyWeekDao;
 	
	public TawDutyWeekDao getTawDutyWeekDao() {
		return this.tawDutyWeekDao;
	}
 	
	public void setTawDutyWeekDao(TawDutyWeekDao tawDutyWeekDao) {
		this.tawDutyWeekDao = tawDutyWeekDao;
	}
 	
    public List getTawDutyWeeks() {
    	return tawDutyWeekDao.getTawDutyWeeks();
    }
    
    public TawDutyWeek getTawDutyWeek(final String id) {
    	return tawDutyWeekDao.getTawDutyWeek(id);
    }
    
    public void saveTawDutyWeek(TawDutyWeek tawDutyWeek) {
    	tawDutyWeekDao.saveTawDutyWeek(tawDutyWeek);
    }
    
    public void removeTawDutyWeek(final String id) {
    	tawDutyWeekDao.removeTawDutyWeek(id);
    }
    
    public Map getTawDutyWeeks(final Integer curPage, final Integer pageSize,
			final String whereStr,String title,String weekFlag,String userName,String startTime,String endTime) {
		return tawDutyWeekDao.getTawDutyWeeks(curPage, pageSize, whereStr,title,weekFlag,userName,startTime,endTime);
	}
    public Map getTawDutyWeeks(final Integer curPage, final Integer pageSize,
			final String whereStr,String userid) {
		return tawDutyWeekDao.getTawDutyWeeks(curPage, pageSize, whereStr,userid);
	}
	
}