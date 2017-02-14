package com.boco.eoms.role_tease.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.role_tease.model.TawSystemRoleDesc;

/**
 * <p>
 * Title:角色说明
 * </p>
 * <p>
 * Description:角色说明
 * </p>
 * <p>
 * Fri Aug 07 11:13:20 CST 2009
 * </p>
 * 
 * @author fengshaohong
 * @version 3.5
 * 
 */
 public interface TawSystemRoleDescMgr {
 
	/**
	 *
	 * 取角色说明 列表
	 * @return 返回角色说明列表
	 */
	public List getTawSystemRoleDescs();
    
	/**
	 * 根据主键查询角色说明
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public TawSystemRoleDesc getTawSystemRoleDesc(final String id);
    
	/**
	 * 保存角色说明
	 * @param tawSystemRoleDesc 角色说明
	 */
	public void saveTawSystemRoleDesc(TawSystemRoleDesc tawSystemRoleDesc);
    
	/**
	 * 根据主键删除角色说明
	 * @param id 主键
	 */
	public void removeTawSystemRoleDesc(final String id);
    
	/**
	 * 根据条件分页查询角色说明
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回角色说明的分页列表
	 */
	public Map getTawSystemRoleDescs(final Integer curPage, final Integer pageSize,
			final String whereStr);
	/**
	 *     通过roleId得到TawSystemRoleDesc
	 * @see com.boco.eoms.role_tease.TawSystemRoleDescDao#getTawSystemRoleDesc(java.lang.String)
	 *
	 */
	public TawSystemRoleDesc getTawSystemRoleDescByRoleId(final long roleId);
}