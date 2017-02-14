package com.boco.eoms.materials.service.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.service.impl.BaseManager;
import com.boco.eoms.materials.dao.MatePermissionDao;
import com.boco.eoms.materials.dao.StoreInputDao;
import com.boco.eoms.materials.model.StoreInput;
import com.boco.eoms.materials.service.StoreInputManager;

/**
 * 入库单信息(StoreInput) 业务实现类
 * 
 * @author wanghongliang
 * 
 */
public class StoreInputManagerImpl extends BaseManager implements StoreInputManager {

	private StoreInputDao dao;
	private MatePermissionDao matePerDao;
	
	

	public MatePermissionDao getMatePerDao() {
		return matePerDao;
	}

	public void setMatePerDao(MatePermissionDao matePerDao) {
		this.matePerDao = matePerDao;
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInputManager#setStoreInputDao(com.boco.eoms.materials.dao.StoreInputDao)
	 */
	public void setStoreInputDao(StoreInputDao dao) {
		this.dao = dao;
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInputManager#getStoreInput()
	 */
	public List<StoreInput> getStoreInput() {
		return dao.getStoreInput();
	}
	

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInputManager#getStoreInput(java.lang.String)
	 */
	public StoreInput getStoreInput(final String id) {
		return dao.getStoreInput(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInputManager#saveStoreInput(com.boco.eoms.materials.model.StoreInput)
	 */
	public void saveStoreInput(StoreInput storeInput) {
		dao.saveStoreInput(storeInput);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInputManager#removeStoreInput(java.lang.String)
	 */
	public void removeStoreInput(final String id) {
		dao.removeStoreInput(new String(id));
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInputManager#getStoreInput(java.lang.Integer, java.lang.Integer)
	 */
	public Map<Integer, Integer> getStoreInput(final Integer curPage,
			final Integer pageSize) {
		return dao.getStoreInput(curPage, pageSize, null);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.service.impl.StoreInputManager#getStoreInput(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	public Map<Integer, Integer> getStoreInput(final Integer curPage,
			final Integer pageSize, final String whereStr) {
		return dao.getStoreInput(curPage, pageSize, whereStr);
	}

	public void alterStoreInput(StoreInput storeInput) {
		dao.alterStoreInput(storeInput);
		
	}

	public List<StoreInput> getStoreInputByTime(String start, String end) {
		// TODO Auto-generated method stub
		
		return dao.getStoreInputByTime(start, end);
	}

	public void updateStoreInput(StoreInput entity) {
		// TODO Auto-generated method stub
		dao.updateStoreInput(entity);
	}


	public Map<Integer, Integer> getCountByTime() {
		// TODO Auto-generated method stub
		return dao.getCountByTime();
	}

	public String getMatePer(String userid) {
		// TODO Auto-generated method stub
		return matePerDao.getPermission(userid);
	}


}
