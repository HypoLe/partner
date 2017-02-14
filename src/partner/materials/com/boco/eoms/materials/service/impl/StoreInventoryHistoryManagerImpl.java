package com.boco.eoms.materials.service.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.service.impl.BaseManager;
import com.boco.eoms.materials.dao.StoreInventoryHistoryDao;
import com.boco.eoms.materials.model.StoreInventoryHistory;
import com.boco.eoms.materials.service.StoreInventoryHistoryManager;

/**
 * 库存历史记录信息(StoreInventoryHistory) 业务实现类
 * 
 * @author wanghongliang
 * 
 */
public class StoreInventoryHistoryManagerImpl extends BaseManager implements StoreInventoryHistoryManager {

	private StoreInventoryHistoryDao dao;

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInventoryHistoryManager#setStoreInventoryHistoryDao(com.boco.eoms.materials.dao.StoreInventoryHistoryDao)
	 */
	public void setStoreInventoryHistoryDao(StoreInventoryHistoryDao dao) {
		this.dao = dao;
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInventoryHistoryManager#getStoreInventoryHistory()
	 */
	public List<StoreInventoryHistory> getStoreInventoryHistory() {
		return dao.getStoreInventoryHistory();
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInventoryHistoryManager#getStoreInventoryHistory(java.lang.String)
	 */
	public StoreInventoryHistory getStoreInventoryHistory(final String id) {
		return dao.getStoreInventoryHistory(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInventoryHistoryManager#saveStoreInventoryHistory(com.boco.eoms.materials.model.StoreInventoryHistory)
	 */
	public void saveStoreInventoryHistory(StoreInventoryHistory storeInventoryHistory) {
		dao.saveStoreInventoryHistory(storeInventoryHistory);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInventoryHistoryManager#removeStoreInventoryHistory(java.lang.String)
	 */
	public void removeStoreInventoryHistory(final String id) {
		dao.removeStoreInventoryHistory(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInventoryHistoryManager#getStoreInventoryHistory(java.lang.Integer, java.lang.Integer)
	 */
	public Map<Integer, Integer> getStoreInventoryHistory(final Integer curPage,
			final Integer pageSize) {
		return dao.getStoreInventoryHistory(curPage, pageSize, null);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInventoryHistoryManager#getStoreInventoryHistory(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	public Map<Integer, Integer> getStoreInventoryHistory(final Integer curPage,
			final Integer pageSize, final String whereStr) {
		return dao.getStoreInventoryHistory(curPage, pageSize, whereStr);
	}
}
