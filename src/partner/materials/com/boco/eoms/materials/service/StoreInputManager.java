package com.boco.eoms.materials.service;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.dao.StoreInputDao;
import com.boco.eoms.materials.model.StoreInput;

public interface StoreInputManager {

	public abstract void setStoreInputDao(StoreInputDao dao);
	public abstract List<StoreInput> getStoreInputByTime(String start, String end);
	/**
	 * 获取所有的入库单信息
	 * 
	 * @return List<StoreInput>
	 */
	public abstract List<StoreInput> getStoreInput();
	public abstract void updateStoreInput(StoreInput entity);
	public abstract Map<Integer, Integer> getCountByTime();
	/**
	 * 根据id获取入库单信息
	 * 
	 * @param id
	 * @return StoreInput
	 */
	public abstract StoreInput getStoreInput(final String id);

	/**
	 * 保存入库单信息
	 * 
	 * @param storeInput
	 */
	public abstract void saveStoreInput(StoreInput storeInput);

	/**
	 * 删除入库单信息
	 * 
	 * @param id
	 */
	public abstract void removeStoreInput(final String id);

	/**
	 * 分页获取入库单信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getStoreInput(final Integer curPage,
			final Integer pageSize);

	/**
	 * 分页获取入库单信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getStoreInput(final Integer curPage,
			final Integer pageSize, final String whereStr);

	public abstract void alterStoreInput(StoreInput storeInput);
	
	public abstract String getMatePer(String userid);
}