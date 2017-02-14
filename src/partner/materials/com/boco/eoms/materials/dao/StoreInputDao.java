package com.boco.eoms.materials.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.model.StoreInput;

public interface StoreInputDao {

	/**
	 * 获取所有入库单（StoreInput）
	 * 
	 * @return List<StoreInput>
	 */
	@SuppressWarnings("unchecked")
	public abstract List<StoreInput> getStoreInput();
	public abstract List<StoreInput> getStoreInputByTime(String start, String end);
	public abstract Map<Integer, Integer> getCountByTime();
	/**
	 * 根据id获取入库单（StoreInput）
	 * 
	 * @param id
	 * @return StoreInput
	 */
	public abstract StoreInput getStoreInput(String id);


	/**
	 * 分页获取入库单（StoreInput）
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	@SuppressWarnings("unchecked")
	public abstract Map<Integer, Integer> getStoreInput(final Integer curPage,
			final Integer pageSize, final String whereStr);

	/**
	 * 根据id删除入库单（StoreInput）
	 * 
	 * @param id
	 */
	public abstract void removeStoreInput(String id);

	/**
	 * 保存入库单（StoreInput）
	 * 
	 * @param storeInput
	 */
	public abstract void saveStoreInput(StoreInput storeInput);
	public abstract void updateStoreInput(StoreInput storeInput);
	public abstract void alterStoreInput(StoreInput storeInput);
}