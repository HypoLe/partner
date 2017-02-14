package com.boco.eoms.materials.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.model.SysDict;

public interface SysDictDao {

	/**
	 * 获取所有字典表信息（SysDict）
	 * 
	 * @return List<SysDict>
	 */
	@SuppressWarnings("unchecked")
	public abstract List<SysDict> getSysDict();

	/**
	 * 根据id获取字典表信息（SysDict）
	 * 
	 * @param id
	 * @return SysDict
	 */
	public abstract SysDict getSysDict(String id);


	/**
	 * 分页获取字典表信息（SysDict）
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	@SuppressWarnings("unchecked")
	public abstract Map<Integer, Integer> getSysDict(final Integer curPage,
			final Integer pageSize, final String whereStr);

	/**
	 * 根据id删除字典表信息（SysDict）
	 * 
	 * @param id
	 */
	public abstract void removeSysDict(String id);

	/**
	 * 保存字典表信息（SysDict）
	 * 
	 * @param sysDict
	 */
	public abstract void saveSysDict(SysDict sysDict);

}