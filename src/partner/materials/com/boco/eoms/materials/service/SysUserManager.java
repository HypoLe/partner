package com.boco.eoms.materials.service;

import java.util.List;
import java.util.Map;

import com.boco.eoms.materials.dao.SysUserDao;
import com.boco.eoms.materials.model.SysUser;

public interface SysUserManager {

	public abstract void setSysUserDao(SysUserDao dao);

	/**
	 * 获取所有的用户信息
	 * 
	 * @return List<SysUser>
	 */
	public abstract List<SysUser> getSysUser();

	/**
	 * 根据id获取用户信息
	 * 
	 * @param id
	 * @return SysUser
	 */
	public abstract SysUser getSysUser(final String id);

	/**
	 * 保存用户信息
	 * 
	 * @param sysUser
	 */
	public abstract void saveSysUser(SysUser sysUser);

	/**
	 * 删除用户信息
	 * 
	 * @param id
	 */
	public abstract void removeSysUser(final String id);

	/**
	 * 分页获取用户信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getSysUser(final Integer curPage,
			final Integer pageSize);

	/**
	 * 分页获取用户信息
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return Map<Integer, Integer>
	 */
	public abstract Map<Integer, Integer> getSysUser(final Integer curPage,
			final Integer pageSize, final String whereStr);

}