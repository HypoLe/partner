package com.boco.eoms.materials.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.model.SysUser;

public interface SysUserDao {

	/**
	 * 获取所有用户信息（SysUser）
	 * 
	 * @return List<SysUser>
	 */
	@SuppressWarnings("unchecked")
	public abstract List<SysUser> getSysUser();

	/**
	 * 根据id获取用户信息（SysUser）
	 * 
	 * @param id
	 * @return SysUser
	 */
	public abstract SysUser getSysUser(String id);


	/**
	 * 分页获取用户信息（SysUser）
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	@SuppressWarnings("unchecked")
	public abstract Map<Integer, Integer> getSysUser(final Integer curPage,
			final Integer pageSize, final String whereStr);

	/**
	 * 根据id删除用户信息（SysUser）
	 * 
	 * @param id
	 */
	public abstract void removeSysUser(String id);

	/**
	 * 保存用户信息（SysUser）
	 * 
	 * @param sysUser
	 */
	public abstract void saveSysUser(SysUser sysUser);

}