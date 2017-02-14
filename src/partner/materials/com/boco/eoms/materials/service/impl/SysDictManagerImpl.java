package com.boco.eoms.materials.service.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.service.impl.BaseManager;
import com.boco.eoms.materials.dao.SysDictDao;
import com.boco.eoms.materials.model.SysDict;
import com.boco.eoms.materials.service.SysDictManager;

/**
 * 字典表信息(SysDict) 业务实现类
 * 
 * @author wanghongliang
 * 
 */
public class SysDictManagerImpl extends BaseManager implements SysDictManager {

	private SysDictDao dao;

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.SysDictManager#setSysDictDao(com.boco.eoms.materials.dao.SysDictDao)
	 */
	public void setSysDictDao(SysDictDao dao) {
		this.dao = dao;
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.SysDictManager#getSysDict()
	 */
	public List<SysDict> getSysDict() {
		return dao.getSysDict();
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.SysDictManager#getSysDict(java.lang.String)
	 */
	public SysDict getSysDict(final String id) {
		return dao.getSysDict(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.SysDictManager#saveSysDict(com.boco.eoms.materials.model.SysDict)
	 */
	public void saveSysDict(SysDict sysDict) {
		dao.saveSysDict(sysDict);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.SysDictManager#removeSysDict(java.lang.String)
	 */
	public void removeSysDict(final String id) {
		dao.removeSysDict(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.SysDictManager#getSysDict(java.lang.Integer, java.lang.Integer)
	 */
	public Map<Integer, Integer> getSysDict(final Integer curPage,
			final Integer pageSize) {
		return dao.getSysDict(curPage, pageSize, null);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.SysDictManager#getSysDict(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	public Map<Integer, Integer> getSysDict(final Integer curPage,
			final Integer pageSize, final String whereStr) {
		return dao.getSysDict(curPage, pageSize, whereStr);
	}
}
