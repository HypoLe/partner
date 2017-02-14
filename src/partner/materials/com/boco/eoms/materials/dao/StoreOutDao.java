package com.boco.eoms.materials.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.model.StoreOut;

public interface StoreOutDao {
	public abstract void alterStoreOut(StoreOut storeOut);
	
	/**
	 * 获取所有出库单信息（StoreOut）
	 * 
	 * @return List<StoreOut>
	 */
	@SuppressWarnings("unchecked")
	public abstract List<StoreOut> getStoreOut();
	public abstract List<StoreOut> getStoreOutByTime(String start, String end);
	public abstract void updateStoreOut(StoreOut entity);
	public abstract Map<Integer, Integer> getCountByTime();
	/**
	 * 根据id获取出库单信息（StoreOut）
	 * 
	 * @param id
	 * @return StoreOut
	 */
	public abstract StoreOut getStoreOut(String id);


	/**
	 * 分页获取出库单信息（StoreOut）
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	@SuppressWarnings("unchecked")
	public abstract Map<Integer, Integer> getStoreOut(final Integer curPage,
			final Integer pageSize, final String whereStr);

	/**
	 * 根据id删除出库单信息（StoreOut）
	 * 
	 * @param id
	 */
	public abstract void removeStoreOut(String id);

	/**
	 * 保存出库单信息（StoreOut）
	 * 
	 * @param storeOut
	 */
	public abstract void saveStoreOut(StoreOut storeOut);

}