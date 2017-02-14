package com.boco.eoms.role_tease.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.role_tease.model.TawSystemRoletease;
import com.boco.eoms.role_tease.mgr.TawSystemRoleteaseMgr;
import com.boco.eoms.role_tease.dao.TawSystemRoleteaseDao;
import com.boco.eoms.roleWorkflow.model.*;

/**
 * <p>
 * Title:角色梳理
 * </p>
 * <p>
 * Description:角色梳理
 * </p>
 * <p>
 * Tue Aug 04 11:38:53 CST 2009
 * </p>
 * 
 * @author fengshaohong
 * @version 3.5
 * 
 */
public class TawSystemRoleteaseMgrImpl implements TawSystemRoleteaseMgr {
 
	private TawSystemRoleteaseDao  tawSystemRoleteaseDao;
 	
	public TawSystemRoleteaseDao getTawSystemRoleteaseDao() {
		return this.tawSystemRoleteaseDao;
	}
 	
	public void setTawSystemRoleteaseDao(TawSystemRoleteaseDao tawSystemRoleteaseDao) {
		this.tawSystemRoleteaseDao = tawSystemRoleteaseDao;
	}
 	
    public List getTawSystemRoleteases() {
    	return tawSystemRoleteaseDao.getTawSystemRoleteases();
    }
    
    public TawSystemRoletease getTawSystemRoletease(final String id) {
    	return tawSystemRoleteaseDao.getTawSystemRoletease(id);
    }
    
    public void saveTawSystemRoletease(TawSystemRoletease tawSystemRoletease) {
    	tawSystemRoleteaseDao.saveTawSystemRoletease(tawSystemRoletease);
    }
    
    public void removeTawSystemRoletease(final String id) {
    	tawSystemRoleteaseDao.removeTawSystemRoletease(id);
    }
    
    public Map getTawSystemRoleteases(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return tawSystemRoleteaseDao.getTawSystemRoleteases(curPage, pageSize, whereStr);
	}
    /**
	 * 得到某用户的所有流程子角色
	 * 
	 * @param userid
	 * @return
	 */
	public List getRoleidByuserid(String userid){
		return tawSystemRoleteaseDao.getRoleidByuserid(userid);
	}
	/**
	 * 通过子角色id们得到大角色id们
	 * 
	 * @param userid
	 * @return
	 */
public List getRoleidsBysubRoleIds(List subRoleIds){
	return tawSystemRoleteaseDao.getRoleidsBysubRoleIds(subRoleIds);
}
/**
 * 根据flowid查询流程信息
 * @param flowid 流程名称
 * @return
 * @throws Exception
 */
public TawSystemRoleWorkflow getTawSystemWorkflowByFlowId(final String flowId) throws Exception{
	return tawSystemRoleteaseDao.getTawSystemWorkflowByFlowId(flowId);
}
}