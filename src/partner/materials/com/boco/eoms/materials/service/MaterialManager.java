package com.boco.eoms.materials.service;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.dao.MaterialDao;
import com.boco.eoms.materials.model.Material;

public interface MaterialManager {

	public abstract void setMaterialDao(MaterialDao dao);

	/**
	 * 获取所有的材料信息
	 * 
	 * @return List<Material>
	 */
	public abstract List<Material> getMaterial();

	/**
	 * 根据id获取材料信息
	 * 
	 * @param id
	 * @return Material
	 */
	public abstract Material getMaterial(final String id);

	/**
	 * 保存材料信息
	 * 
	 * @param material
	 */
	public abstract void saveMaterial(Material material);

	/**
	 * 删除材料信息
	 * 
	 * @param id
	 */
	public abstract void removeMaterial(final String id);

	/**
	 * 分页获取材料信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getMaterial(final Integer curPage,
			final Integer pageSize);

	/**
	 * 分页获取材料信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getMaterial(final Integer curPage,
			final Integer pageSize, final String whereStr);

}