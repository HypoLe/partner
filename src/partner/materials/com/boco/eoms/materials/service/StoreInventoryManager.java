package com.boco.eoms.materials.service;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.dao.StoreInventoryDao;
import com.boco.eoms.materials.model.StoreInventory;

public interface StoreInventoryManager {

	public abstract void setStoreInventoryDao(StoreInventoryDao dao);

	/**
	 * 获取所有的库存信息
	 * 
	 * @return List<StoreInventory>
	 */
	public abstract List<StoreInventory> getStoreInventory();
	public abstract List<StoreInventory> selectByItem(StoreInventory storeInventory);
	/**
	 * 根据单据号查询库存信息
	 * 
	 * @param billId
	 * @return
	 */
	public abstract StoreInventory getInventoryByMaterialId(String materialId, String storeSid);
	
	/**
	 * 根据id获取库存信息
	 * 
	 * @param id
	 * @return StoreInventory
	 */
	public abstract StoreInventory getStoreInventory(final String id);

	/**
	 * 保存库存信息
	 * 
	 * @param storeInventory
	 */
	public abstract void saveStoreInventory(StoreInventory storeInventory);

	/**
	 * 删除库存信息
	 * 
	 * @param id
	 */
	public abstract void removeStoreInventory(final String id);

	/**
	 * 分页获取库存信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getStoreInventory(
			final Integer curPage, final Integer pageSize);

	/**
	 * 分页获取库存信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getStoreInventory(
			final Integer curPage, final Integer pageSize, final String whereStr);
	
	public abstract void update(StoreInventory entity);

}