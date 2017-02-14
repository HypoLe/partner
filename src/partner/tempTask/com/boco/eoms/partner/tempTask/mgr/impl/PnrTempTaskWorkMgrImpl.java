package com.boco.eoms.partner.tempTask.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.tempTask.model.PnrTempTaskWork;
import com.boco.eoms.partner.tempTask.mgr.IPnrTempTaskWorkMgr;
import com.boco.eoms.partner.tempTask.dao.IPnrTempTaskWorkDao;

/**
 * <p>
 * Title:合作伙伴临时任务管理
 * </p>
 * <p>
 * Description:合作伙伴临时任务管理主表信息
 * </p>
 * <p>
 * Mon Apr 26 14:09:01 CST 2010
 * </p>
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
public class PnrTempTaskWorkMgrImpl implements IPnrTempTaskWorkMgr {
 
	private IPnrTempTaskWorkDao  pnrTempTaskWorkDao;
 	
	public IPnrTempTaskWorkDao getPnrTempTaskWorkDao() {
		return this.pnrTempTaskWorkDao;
	}
 	
	public void setPnrTempTaskWorkDao(IPnrTempTaskWorkDao pnrTempTaskWorkDao) {
		this.pnrTempTaskWorkDao = pnrTempTaskWorkDao;
	}
 	
    public List getPnrTempTaskWorks() {
    	return pnrTempTaskWorkDao.getPnrTempTaskWorks();
    }
	 

	public List getPnrTempTaskWorks(final String agreeId){
		return pnrTempTaskWorkDao.getPnrTempTaskWorks(agreeId);
	}
	
    public PnrTempTaskWork getPnrTempTaskWork(final String id) {
    	return pnrTempTaskWorkDao.getPnrTempTaskWork(id);
    }
    
    public void savePnrTempTaskWork(PnrTempTaskWork pnrTempTaskWork) {
    	pnrTempTaskWorkDao.savePnrTempTaskWork(pnrTempTaskWork);
    }
    
    public void removePnrTempTaskWork(final String id) {
    	pnrTempTaskWorkDao.removePnrTempTaskWork(id);
    }
    
    public Map getPnrTempTaskWorks(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return pnrTempTaskWorkDao.getPnrTempTaskWorks(curPage, pageSize, whereStr);
	}
	
}