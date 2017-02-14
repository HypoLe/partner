package com.boco.eoms.role_tease.dao;

import com.boco.eoms.base.dao.Dao;
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
public interface TawSystemRoleteaseDao extends Dao {

    /**
    *
    *取角色梳理列表
    * @return 返回角色梳理列表
    */
    public List getTawSystemRoleteases();
    
    /**
    * 根据主键查询角色梳理
    * @param id 主键
    * @return 返回某id的角色梳理
    */
    public TawSystemRoletease getTawSystemRoletease(final String id);
    
    /**
    *
    * 保存角色梳理    
    * @param tawSystemRoletease 角色梳理
    * 
    */
    public void saveTawSystemRoletease(TawSystemRoletease tawSystemRoletease);
    
    /**
    * 根据id删除角色梳理
    * @param id 主键
    * 
    */
    public void removeTawSystemRoletease(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getTawSystemRoleteases(final Integer curPage, final Integer pageSize,
			final String whereStr);
    /**
	 * 得到某用户的所有流程子角色
	 * 
	 * @param userid
	 * @return
	 */
	public List getRoleidByuserid(String userid);
	/**
	 * 通过子角色id们得到大角色id们
	 * 
	 * @param userid
	 * @return
	 */
public List getRoleidsBysubRoleIds(List subRoleIds);
/**
 * 根据flowid查询流程信息
 * @param flowid 流程名称
 * @return
 * @throws Exception
 */
public TawSystemRoleWorkflow getTawSystemWorkflowByFlowId(final String flowId) throws Exception;
}