package com.boco.eoms.role_tease.dao;

import com.boco.eoms.base.dao.Dao;
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
public interface TawSystemRoleDescDao extends Dao {

    /**
    *
    *取角色说明列表
    * @return 返回角色说明列表
    */
    public List getTawSystemRoleDescs();
    
    /**
    * 根据主键查询角色说明
    * @param id 主键
    * @return 返回某id的角色说明
    */
    public TawSystemRoleDesc getTawSystemRoleDesc(final String id);
    
    /**
    *
    * 保存角色说明    
    * @param tawSystemRoleDesc 角色说明
    * 
    */
    public void saveTawSystemRoleDesc(TawSystemRoleDesc tawSystemRoleDesc);
    
    /**
    * 根据id删除角色说明
    * @param id 主键
    * 
    */
    public void removeTawSystemRoleDesc(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
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