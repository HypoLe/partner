package com.boco.eoms.materials.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.model.Material;

public interface MaterialDao {

	/**
	 * 获取所有货品信息（Material）
	 * 
	 * @return List<Material>
	 */
	@SuppressWarnings("unchecked")
	public abstract List<Material> getMaterial();

	/**
	 * 根据id获取货品信息（Material）
	 * 
	 * @param id
	 * @return Material
	 */
	public abstract Material getMaterial(String id);


	/**
	 * 分页获取货品信息（Material）
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	@SuppressWarnings("unchecked")
	public abstract Map<Integer, Integer> getMaterial(final Integer curPage,
			final Integer pageSize, final String whereStr);

	/**
	 * 根据id删除货品信息（Material）
	 * 
	 * @param id
	 */
	public abstract void removeMaterial(String id);

	/**
	 * 保存货品信息（Material）
	 * 
	 * @param material
	 */
	public abstract void saveMaterial(Material material);

}