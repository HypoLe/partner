package com.boco.eoms.materials.service.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.service.impl.BaseManager;
import com.boco.eoms.materials.dao.SysUserDao;
import com.boco.eoms.materials.model.SysUser;
import com.boco.eoms.materials.service.SysUserManager;

/**
 * 用户信息(SysUser) 业务实现类
 * 
 * @author wanghongliang
 * 
 */
public class SysUserManagerImpl extends BaseManager implements SysUserManager {

	private SysUserDao dao;

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.SysUserManager#setSysUserDao(com.boco.eoms.materials.dao.SysUserDao)
	 */
	public void setSysUserDao(SysUserDao dao) {
		this.dao = dao;
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.SysUserManager#getSysUser()
	 */
	public List<SysUser> getSysUser() {
		return dao.getSysUser();
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.SysUserManager#getSysUser(java.lang.String)
	 */
	public SysUser getSysUser(final String id) {
		return dao.getSysUser(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.SysUserManager#saveSysUser(com.boco.eoms.materials.model.SysUser)
	 */
	public void saveSysUser(SysUser sysUser) {
		dao.saveSysUser(sysUser);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.SysUserManager#removeSysUser(java.lang.String)
	 */
	public void removeSysUser(final String id) {
		dao.removeSysUser(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.SysUserManager#getSysUser(java.lang.Integer, java.lang.Integer)
	 */
	public Map<Integer, Integer> getSysUser(final Integer curPage,
			final Integer pageSize) {
		return dao.getSysUser(curPage, pageSize, null);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.SysUserManager#getSysUser(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	public Map<Integer, Integer> getSysUser(final Integer curPage,
			final Integer pageSize, final String whereStr) {
		return dao.getSysUser(curPage, pageSize, whereStr);
	}
}
