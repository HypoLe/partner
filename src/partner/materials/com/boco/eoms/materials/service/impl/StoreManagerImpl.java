package com.boco.eoms.materials.service.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.service.impl.BaseManager;
import com.boco.eoms.materials.dao.StoreDao;
import com.boco.eoms.materials.model.Store;
import com.boco.eoms.materials.service.StoreManager;

/**
 * 仓库信息(Store) 业务实现类
 * 
 * @author wanghongliang
 * 
 */
public class StoreManagerImpl extends BaseManager implements StoreManager {

	private StoreDao dao;

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreManager#setStoreDao(com.boco.eoms.materials.dao.StoreDao)
	 */
	public void setStoreDao(StoreDao dao) {
		this.dao = dao;
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreManager#getStore()
	 */
	public List<Store> getStore() {
		return dao.getStore();
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreManager#getStore(java.lang.String)
	 */
	public Store getStore(final String id) {
		return dao.getStore(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreManager#saveStore(com.boco.eoms.materials.model.Store)
	 */
	public void saveStore(Store store) {
		dao.saveStore(store);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreManager#removeStore(java.lang.String)
	 */
	public void removeStore(final String id) {
		dao.removeStore(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreManager#getStore(java.lang.Integer, java.lang.Integer)
	 */
	public Map<Integer, Integer> getStore(final Integer curPage,
			final Integer pageSize) {
		return dao.getStore(curPage, pageSize, null);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreManager#getStore(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	public Map<Integer, Integer> getStore(final Integer curPage,
			final Integer pageSize, final String whereStr) {
		return dao.getStore(curPage, pageSize, whereStr);
	}
}
