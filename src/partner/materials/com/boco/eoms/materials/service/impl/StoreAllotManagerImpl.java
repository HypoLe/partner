package com.boco.eoms.materials.service.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.service.impl.BaseManager;
import com.boco.eoms.materials.dao.StoreAllotDao;
import com.boco.eoms.materials.model.StoreAllot;
import com.boco.eoms.materials.service.StoreAllotManager;

/**
 * 调拨单信息(StoreAllot) 业务实现类
 * 
 * @author wanghongliang
 * 
 */
public class StoreAllotManagerImpl extends BaseManager implements StoreAllotManager {

	private StoreAllotDao dao;

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreAllotManager#setStoreAllotDao(com.boco.eoms.materials.dao.StoreAllotDao)
	 */
	public void setStoreAllotDao(StoreAllotDao dao) {
		this.dao = dao;
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreAllotManager#getStoreAllot()
	 */
	public List<StoreAllot> getStoreAllot() {
		return dao.getStoreAllot();
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreAllotManager#getStoreAllot(java.lang.String)
	 */
	public StoreAllot getStoreAllot(final String id) {
		return dao.getStoreAllot(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreAllotManager#saveStoreAllot(com.boco.eoms.materials.model.StoreAllot)
	 */
	public void saveStoreAllot(StoreAllot storeAllot) {
		dao.saveStoreAllot(storeAllot);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreAllotManager#removeStoreAllot(java.lang.String)
	 */
	public void removeStoreAllot(final String id) {
		dao.removeStoreAllot(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreAllotManager#getStoreAllot(java.lang.Integer, java.lang.Integer)
	 */
	public Map<Integer, Integer> getStoreAllot(final Integer curPage,
			final Integer pageSize) {
		return dao.getStoreAllot(curPage, pageSize, null);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreAllotManager#getStoreAllot(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	public Map<Integer, Integer> getStoreAllot(final Integer curPage,
			final Integer pageSize, final String whereStr) {
		return dao.getStoreAllot(curPage, pageSize, whereStr);
	}

	public List<StoreAllot> getStoreAllotByTime(String start, String end) {
		// TODO Auto-generated method stub
		return dao.getStoreAllotByTime(start, end);
	}

	public void alterStoreAllot(StoreAllot storeAllot) {
		// TODO Auto-generated method stub
		dao.alterStoreAllot(storeAllot);
		
	}

	public void updateStoreAllot(StoreAllot entity) {
		// TODO Auto-generated method stub
		dao.updateStoreAllot(entity);
	}

	public Map<Integer, Integer> getCountByTime() {
		// TODO Auto-generated method stub
		return dao.getCountByTime();
	}
}
