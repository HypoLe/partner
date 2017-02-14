package com.boco.eoms.materials.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.model.StoreInventory;

public interface StoreInventoryDao {

	/**
	 * 获取所有库存信息（StoreInventory）
	 * 
	 * @return List<StoreInventory>
	 */
	@SuppressWarnings("unchecked")
	public abstract List<StoreInventory> getStoreInventory();
	public abstract void update(StoreInventory entity);
	public abstract List<StoreInventory> selectByItem(StoreInventory storeInventory);
	/**
	 * 根据原始单号获取库存信息
	 * 
	 * @return List<StoreInventory>
	 */
	public abstract StoreInventory getInventoryByMaterialId(String materialId, String storeSid);
	
	/**
	 * 根据id获取库存信息（StoreInventory）
	 * 
	 * @param id
	 * @return StoreInventory
	 */
	public abstract StoreInventory getStoreInventory(String id);


	/**
	 * 分页获取库存信息（StoreInventory）
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	@SuppressWarnings("unchecked")
	public abstract Map<Integer, Integer> getStoreInventory(
			final Integer curPage, final Integer pageSize, final String whereStr);

	/**
	 * 根据id删除库存信息（StoreInventory）
	 * 
	 * @param id
	 */
	public abstract void removeStoreInventory(String id);

	/**
	 * 保存库存信息（StoreInventory）
	 * 
	 * @param storeInventory
	 */
	public abstract void saveStoreInventory(StoreInventory storeInventory);

}