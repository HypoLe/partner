package com.boco.eoms.role_tease.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.role_tease.model.TawSystemRoletease;
import com.boco.eoms.roleWorkflow.model.TawSystemRoleWorkflow;

/**
 * <p>
 * Title:角色梳理
 * </p>
 * <p>
 * Description:角色梳理
 * </p>
 * <p>
 * Tue Aug 04 11:38:53 CST 2009
 * </p>
 * 
 * @author fengshaohong
 * @version 3.5
 * 
 */
 public interface TawSystemRoleteaseMgr {
 
	/**
	 *
	 * 取角色梳理 列表
	 * @return 返回角色梳理列表
	 */
	public List getTawSystemRoleteases();
    
	/**
	 * 根据主键查询角色梳理
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public TawSystemRoletease getTawSystemRoletease(final String id);
    
	/**
	 * 保存角色梳理
	 * @param tawSystemRoletease 角色梳理
	 */
	public void saveTawSystemRoletease(TawSystemRoletease tawSystemRoletease);
    
	/**
	 * 根据主键删除角色梳理
	 * @param id 主键
	 */
	public void removeTawSystemRoletease(final String id);
    
	/**
	 * 根据条件分页查询角色梳理
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回角色梳理的分页列表
	 */
	public Map getTawSystemRoleteases(final Integer curPage, final Integer pageSize,
			final String whereStr);
	/**
	 * 通过子角色id们得到大角色id们
	 * 
	 * @param userid
	 * @return
	 */
public List getRoleidsBysubRoleIds(List subRoleIds);
/**
 * 得到某用户的所有流程子角色
 * 
 * @param userid
 * @return
 */
public List getRoleidByuserid(String userid);
/**
 * 根据flowid查询流程信息
 * @param flowid 流程名称
 * @return
 * @throws Exception
 */
public TawSystemRoleWorkflow getTawSystemWorkflowByFlowId(final String flowId) throws Exception;
}