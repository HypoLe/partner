package com.boco.eoms.materials.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.model.StoreAllot;

public interface StoreAllotDao {

	public abstract void alterStoreAllot(StoreAllot storeAllot);
	
	/**
	 * 获取所有调拨单（StoreAllot）
	 * 
	 * @return List<StoreAllot>
	 */
	@SuppressWarnings("unchecked")
	public abstract List<StoreAllot> getStoreAllot();
	public abstract void updateStoreAllot(StoreAllot storeAllot);
	public abstract List<StoreAllot> getStoreAllotByTime(String start, String end);
	public abstract Map<Integer, Integer> getCountByTime();
	/**
	 * 根据id获取调拨单（StoreAllot）
	 * 
	 * @param id
	 * @return StoreAllot
	 */
	public abstract StoreAllot getStoreAllot(String id);

	/**
	 * 分页获取调拨单（StoreAllot）
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	@SuppressWarnings("unchecked")
	public abstract Map<Integer, Integer> getStoreAllot(final Integer curPage,
			final Integer pageSize, final String whereStr);

	/**
	 * 根据id删除调拨单（StoreAllot）
	 * 
	 * @param id
	 */
	public abstract void removeStoreAllot(String id);

	/**
	 * 保存调拨单（StoreAllot）
	 * 
	 * @param storeAllot
	 */
	public abstract void saveStoreAllot(StoreAllot storeAllot);

}