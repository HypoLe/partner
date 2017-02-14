package com.boco.eoms.materials.service;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.dao.SysDictDao;
import com.boco.eoms.materials.model.SysDict;

public interface SysDictManager {

	public abstract void setSysDictDao(SysDictDao dao);

	/**
	 * 获取所有的字典表信息
	 * 
	 * @return List<SysDict>
	 */
	public abstract List<SysDict> getSysDict();

	/**
	 * 根据id获取字典表信息
	 * 
	 * @param id
	 * @return SysDict
	 */
	public abstract SysDict getSysDict(final String id);

	/**
	 * 保存字典表信息
	 * 
	 * @param sysDict
	 */
	public abstract void saveSysDict(SysDict sysDict);

	/**
	 * 删除字典表信息
	 * 
	 * @param id
	 */
	public abstract void removeSysDict(final String id);

	/**
	 * 分页获取字典表信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getSysDict(final Integer curPage,
			final Integer pageSize);

	/**
	 * 分页获取字典表信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getSysDict(final Integer curPage,
			final Integer pageSize, final String whereStr);

}