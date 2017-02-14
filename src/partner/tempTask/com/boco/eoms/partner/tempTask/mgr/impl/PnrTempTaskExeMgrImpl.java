package com.boco.eoms.partner.tempTask.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.tempTask.dao.IPnrTempTaskExeDao;
import com.boco.eoms.partner.tempTask.mgr.IPnrTempTaskExeMgr;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskExe;

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
public class PnrTempTaskExeMgrImpl implements IPnrTempTaskExeMgr {
 
	private IPnrTempTaskExeDao  pnrTempTaskExeDao;
 	
	public IPnrTempTaskExeDao getPnrTempTaskExeDao() {
		return this.pnrTempTaskExeDao;
	}
 	
	public void setPnrTempTaskExeDao(IPnrTempTaskExeDao pnrTempTaskExeDao) {
		this.pnrTempTaskExeDao = pnrTempTaskExeDao;
	}
 	
    public List getPnrTempTaskExes() {
    	return pnrTempTaskExeDao.getPnrTempTaskExes();
    }
	 

	public List getPnrTempTaskExes(final String agreeId){
		return pnrTempTaskExeDao.getPnrTempTaskExes(agreeId);
	}
	
    public PnrTempTaskExe getPnrTempTaskExe(final String id) {
    	return pnrTempTaskExeDao.getPnrTempTaskExe(id);
    }
    
    public void savePnrTempTaskExe(PnrTempTaskExe pnrTempTaskExe) {
    	pnrTempTaskExeDao.savePnrTempTaskExe(pnrTempTaskExe);
    }
    
    public void removePnrTempTaskExe(final String id) {
    	pnrTempTaskExeDao.removePnrTempTaskExe(id);
    }
    
    public Map getPnrTempTaskExes(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return pnrTempTaskExeDao.getPnrTempTaskExes(curPage, pageSize, whereStr);
	}
	public Map getPnrTempTaskUndo(final Integer curPage, final Integer pageSize,
			final String userId,final String deptId){
		String date = StaticMethod.getLocalString();
		return pnrTempTaskExeDao.getPnrTempTaskUndo(curPage, pageSize, userId,deptId,date);
	}
	public Map getPnrTempTaskForExecute(final Integer curPage, final Integer pageSize,
			final String userId,final String deptId){
		String date = StaticMethod.getLocalString();
		return pnrTempTaskExeDao.getPnrTempTaskForExecute(curPage, pageSize, userId,deptId,date);
	}
}