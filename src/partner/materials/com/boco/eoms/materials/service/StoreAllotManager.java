package com.boco.eoms.materials.service;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.dao.StoreAllotDao;
import com.boco.eoms.materials.model.StoreAllot;

public interface StoreAllotManager {

	public abstract void setStoreAllotDao(StoreAllotDao dao);
	public abstract void alterStoreAllot(StoreAllot storeAllot);
	public abstract Map<Integer, Integer> getCountByTime();
	/**
	 * 获取所有的调拨单信息
	 * 
	 * @return List<StoreAllot>
	 */
	public abstract List<StoreAllot> getStoreAllot();
	public abstract void updateStoreAllot(StoreAllot entity);
	/**
	 * 根据id获取调拨单信息
	 * 
	 * @param id
	 * @return StoreAllot
	 */
	public abstract StoreAllot getStoreAllot(final String id);

	/**
	 * 保存调拨单信息
	 * 
	 * @param storeAllot
	 */
	public abstract void saveStoreAllot(StoreAllot storeAllot);

	/**
	 * 删除调拨单信息
	 * 
	 * @param id
	 */
	public abstract void removeStoreAllot(final String id);

	/**
	 * 分页获取调拨单信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getStoreAllot(final Integer curPage,
			final Integer pageSize);
	public abstract List<StoreAllot> getStoreAllotByTime(String start, String end);
	/**
	 * 分页获取调拨单信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getStoreAllot(final Integer curPage,
			final Integer pageSize, final String whereStr);

}