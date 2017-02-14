package com.boco.eoms.materials.service;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.dao.StoreInventoryHistoryDao;
import com.boco.eoms.materials.model.StoreInventoryHistory;

public interface StoreInventoryHistoryManager {

	public abstract void setStoreInventoryHistoryDao(
			StoreInventoryHistoryDao dao);

	/**
	 * 获取所有的库存历史记录信息
	 * 
	 * @return List<StoreInventoryHistory>
	 */
	public abstract List<StoreInventoryHistory> getStoreInventoryHistory();

	/**
	 * 根据id获取库存历史记录信息
	 * 
	 * @param id
	 * @return StoreInventoryHistory
	 */
	public abstract StoreInventoryHistory getStoreInventoryHistory(
			final String id);

	/**
	 * 保存库存历史记录信息
	 * 
	 * @param storeInventoryHistory
	 */
	public abstract void saveStoreInventoryHistory(
			StoreInventoryHistory storeInventoryHistory);

	/**
	 * 删除库存历史记录信息
	 * 
	 * @param id
	 */
	public abstract void removeStoreInventoryHistory(final String id);

	/**
	 * 分页获取库存历史记录信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getStoreInventoryHistory(
			final Integer curPage, final Integer pageSize);

	/**
	 * 分页获取库存历史记录信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getStoreInventoryHistory(
			final Integer curPage, final Integer pageSize, final String whereStr);

}