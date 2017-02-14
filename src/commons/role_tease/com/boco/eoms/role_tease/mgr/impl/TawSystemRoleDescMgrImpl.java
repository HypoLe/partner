package com.boco.eoms.role_tease.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.role_tease.model.TawSystemRoleDesc;
import com.boco.eoms.role_tease.mgr.TawSystemRoleDescMgr;
import com.boco.eoms.role_tease.dao.TawSystemRoleDescDao;

/**
 * <p>
 * Title:角色说明
 * </p>
 * <p>
 * Description:角色说明
 * </p>
 * <p>
 * Fri Aug 07 11:13:20 CST 2009
 * </p>
 * 
 * @author fengshaohong
 * @version 3.5
 * 
 */
public class TawSystemRoleDescMgrImpl implements TawSystemRoleDescMgr {
 
	private TawSystemRoleDescDao  tawSystemRoleDescDao;
 	
	public TawSystemRoleDescDao getTawSystemRoleDescDao() {
		return this.tawSystemRoleDescDao;
	}
 	
	public void setTawSystemRoleDescDao(TawSystemRoleDescDao tawSystemRoleDescDao) {
		this.tawSystemRoleDescDao = tawSystemRoleDescDao;
	}
 	
    public List getTawSystemRoleDescs() {
    	return tawSystemRoleDescDao.getTawSystemRoleDescs();
    }
    
    public TawSystemRoleDesc getTawSystemRoleDesc(final String id) {
    	return tawSystemRoleDescDao.getTawSystemRoleDesc(id);
    }
    
    public void saveTawSystemRoleDesc(TawSystemRoleDesc tawSystemRoleDesc) {
    	tawSystemRoleDescDao.saveTawSystemRoleDesc(tawSystemRoleDesc);
    }
    
    public void removeTawSystemRoleDesc(final String id) {
    	tawSystemRoleDescDao.removeTawSystemRoleDesc(id);
    }
    
    public Map getTawSystemRoleDescs(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return tawSystemRoleDescDao.getTawSystemRoleDescs(curPage, pageSize, whereStr);
	}
    /**
	 *     通过roleId得到TawSystemRoleDesc
	 * @see com.boco.eoms.role_tease.TawSystemRoleDescDao#getTawSystemRoleDesc(java.lang.String)
	 *
	 */
	public TawSystemRoleDesc getTawSystemRoleDescByRoleId(final long roleId){
		return tawSystemRoleDescDao.getTawSystemRoleDescByRoleId(roleId);
	}
}