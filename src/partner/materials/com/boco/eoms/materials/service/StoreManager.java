package com.boco.eoms.materials.service;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.dao.StoreDao;
import com.boco.eoms.materials.model.Store;

public interface StoreManager {

	public abstract void setStoreDao(StoreDao dao);

	/**
	 * 获取所有的仓库信息
	 * 
	 * @return List<Store>
	 */
	public abstract List<Store> getStore();

	/**
	 * 根据id获取仓库信息
	 * 
	 * @param id
	 * @return Store
	 */
	public abstract Store getStore(final String id);

	/**
	 * 保存仓库信息
	 * 
	 * @param store
	 */
	public abstract void saveStore(Store store);

	/**
	 * 删除仓库信息
	 * 
	 * @param id
	 */
	public abstract void removeStore(final String id);

	/**
	 * 分页获取仓库信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getStore(final Integer curPage,
			final Integer pageSize);

	/**
	 * 分页获取仓库信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getStore(final Integer curPage,
			final Integer pageSize, final String whereStr);

}