package com.boco.eoms.materials.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.model.Store;

public interface StoreDao {

	/**
	 * 获取所有仓库信息（Store）
	 * 
	 * @return List<Store>
	 */
	@SuppressWarnings("unchecked")
	public abstract List<Store> getStore();

	/**
	 * 根据id获取仓库信息（Store）
	 * 
	 * @param id
	 * @return Store
	 */
	public abstract Store getStore(String id);


	/**
	 * 分页获取仓库信息（Store）
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	@SuppressWarnings("unchecked")
	public abstract Map<Integer, Integer> getStore(final Integer curPage,
			final Integer pageSize, final String whereStr);

	/**
	 * 根据id删除仓库信息（Store）
	 * 
	 * @param id
	 */
	public abstract void removeStore(String id);

	/**
	 * 保存仓库信息（Store）
	 * 
	 * @param Store
	 */
	public abstract void saveStore(Store store);

}