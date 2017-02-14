package com.boco.eoms.materials.service.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.service.impl.BaseManager;
import com.boco.eoms.materials.dao.StoreOutDao;
import com.boco.eoms.materials.model.StoreOut;
import com.boco.eoms.materials.service.StoreOutManager;

/**
 * 出库单信息(StoreOut) 业务实现类
 * 
 * @author wanghongliang
 * 
 */
public class StoreOutManagerImpl extends BaseManager implements StoreOutManager {

	private StoreOutDao dao;

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreOutManager#setStoreOutDao(com.boco.eoms.materials.dao.StoreOutDao)
	 */
	public void setStoreOutDao(StoreOutDao dao) {
		this.dao = dao;
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreOutManager#getStoreOut()
	 */
	public List<StoreOut> getStoreOut() {
		return dao.getStoreOut();
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreOutManager#getStoreOut(java.lang.String)
	 */
	public StoreOut getStoreOut(final String id) {
		return dao.getStoreOut(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreOutManager#saveStoreOut(com.boco.eoms.materials.model.StoreOut)
	 */
	public void saveStoreOut(StoreOut storeOut) {
		dao.saveStoreOut(storeOut);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreOutManager#removeStoreOut(java.lang.String)
	 */
	public void removeStoreOut(final String id) {
		dao.removeStoreOut(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreOutManager#getStoreOut(java.lang.Integer, java.lang.Integer)
	 */
	public Map<Integer, Integer> getStoreOut(final Integer curPage,
			final Integer pageSize) {
		return dao.getStoreOut(curPage, pageSize, null);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreOutManager#getStoreOut(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	public Map<Integer, Integer> getStoreOut(final Integer curPage,
			final Integer pageSize, final String whereStr) {
		return dao.getStoreOut(curPage, pageSize, whereStr);
	}
	
	public List<StoreOut> getStoreOutByTime(String start, String end){
		return dao.getStoreOutByTime(start, end);
	}

	public void alterStoreOut(StoreOut storeOut) {
		// TODO Auto-generated method stub
		dao.alterStoreOut(storeOut);
	}

	public void updateStoreOut(StoreOut entity) {
		// TODO Auto-generated method stub
		dao.updateStoreOut(entity);
	}

	public Map<Integer, Integer> getCountByTime() {
		// TODO Auto-generated method stub
		return dao.getCountByTime();
	}
}
