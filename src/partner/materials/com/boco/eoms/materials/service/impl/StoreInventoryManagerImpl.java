package com.boco.eoms.materials.service.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.service.impl.BaseManager;
import com.boco.eoms.materials.dao.StoreInventoryDao;
import com.boco.eoms.materials.model.StoreInventory;
import com.boco.eoms.materials.service.StoreInventoryManager;

/**
 * 库存信息(StoreInventory) 业务实现类
 * 
 * @author wanghongliang
 * 
 */
public class StoreInventoryManagerImpl extends BaseManager implements StoreInventoryManager {

	private StoreInventoryDao dao;

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInventoryManager#setStoreInventoryDao(com.boco.eoms.materials.dao.StoreInventoryDao)
	 */
	public void setStoreInventoryDao(StoreInventoryDao dao) {
		this.dao = dao;
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInventoryManager#getStoreInventory()
	 */
	public List<StoreInventory> getStoreInventory() {
		return dao.getStoreInventory();
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInventoryManager#getStoreInventory(java.lang.String)
	 */
	public StoreInventory getStoreInventory(final String id) {
		return dao.getStoreInventory(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInventoryManager#saveStoreInventory(com.boco.eoms.materials.model.StoreInventory)
	 */
	public void saveStoreInventory(StoreInventory storeInventory) {
		dao.saveStoreInventory(storeInventory);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInventoryManager#removeStoreInventory(java.lang.String)
	 */
	public void removeStoreInventory(final String id) {
		dao.removeStoreInventory(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInventoryManager#getStoreInventory(java.lang.Integer, java.lang.Integer)
	 */
	public Map<Integer, Integer> getStoreInventory(final Integer curPage,
			final Integer pageSize) {
		return dao.getStoreInventory(curPage, pageSize, null);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInventoryManager#getStoreInventory(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	public Map<Integer, Integer> getStoreInventory(final Integer curPage,
			final Integer pageSize, final String whereStr) {
		return dao.getStoreInventory(curPage, pageSize, whereStr);
	}

	public StoreInventory getInventoryByMaterialId(String materialId, String storeSid) {
		// TODO Auto-generated method stub
		return dao.getInventoryByMaterialId(materialId, storeSid);
	}

	public void update(StoreInventory entity) {
		// TODO Auto-generated method stub
		dao.update(entity);
	}

	public List<StoreInventory> selectByItem(StoreInventory storeInventory) {
		// TODO Auto-generated method stub
		return dao.selectByItem(storeInventory);
	}
}
