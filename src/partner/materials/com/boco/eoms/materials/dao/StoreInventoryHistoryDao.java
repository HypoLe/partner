package com.boco.eoms.materials.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.model.StoreInventoryHistory;

public interface StoreInventoryHistoryDao {

	/**
	 * 获取所有库存历史记录信息（StoreInventoryHistory）
	 * 
	 * @return List<StoreInventoryHistory>
	 */
	@SuppressWarnings("unchecked")
	public abstract List<StoreInventoryHistory> getStoreInventoryHistory();

	/**
	 * 根据id获取库存历史记录信息（StoreInventoryHistory）
	 * 
	 * @param id
	 * @return StoreInventoryHistory
	 */
	public abstract StoreInventoryHistory getStoreInventoryHistory(String id);


	/**
	 * 分页获取库存历史记录信息（StoreInventoryHistory）
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	@SuppressWarnings("unchecked")
	public abstract Map<Integer, Integer> getStoreInventoryHistory(
			final Integer curPage, final Integer pageSize, final String whereStr);

	/**
	 * 根据id删除库存历史记录信息（StoreInventoryHistory）
	 * 
	 * @param id
	 */
	public abstract void removeStoreInventoryHistory(String id);

	/**
	 * 保存库存历史记录信息（StoreInventoryHistory）
	 * 
	 * @param storeInventoryHistory
	 */
	public abstract void saveStoreInventoryHistory(
			StoreInventoryHistory storeInventoryHistory);

}