package com.boco.eoms.materials.service;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.dao.StoreOutDao;
import com.boco.eoms.materials.model.StoreOut;

public interface StoreOutManager {

	public abstract void setStoreOutDao(StoreOutDao dao);
	public abstract void alterStoreOut(StoreOut storeOut);
	/**
	 * 获取所有的出库单信息
	 * 
	 * @return List<StoreOut>
	 */
	public abstract List<StoreOut> getStoreOut();
	public abstract List<StoreOut> getStoreOutByTime(String start, String end);
	public abstract void updateStoreOut(StoreOut entity);
	public abstract Map<Integer, Integer> getCountByTime();
	/**
	 * 根据id获取出库单信息
	 * 
	 * @param id
	 * @return StoreOut
	 */
	public abstract StoreOut getStoreOut(final String id);

	/**
	 * 保存出库单信息
	 * 
	 * @param storeOut
	 */
	public abstract void saveStoreOut(StoreOut storeOut);

	/**
	 * 删除出库单信息
	 * 
	 * @param id
	 */
	public abstract void removeStoreOut(final String id);

	/**
	 * 分页获取出库单信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getStoreOut(final Integer curPage,
			final Integer pageSize);

	/**
	 * 分页获取出库单信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getStoreOut(final Integer curPage,
			final Integer pageSize, final String whereStr);

}