package com.boco.eoms.partner.management.dao;

import com.boco.eoms.base.dao.Dao;
import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.management.model.Management;

/**
 * <p>
 * Title:管理成本
 * </p>
 * <p>
 * Description:管理成本
 * </p>
 * <p>
 * Wed Mar 28 09:29:15 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
public interface IManagementDao extends Dao {

    /**
     *
     *取管理成本列表
     * @return 返回管理成本列表
     */
    public List getManagements();
    
    /**
     * 根据主键查询管理成本
     * @param id 主键
     * @return 返回某id的管理成本
     */
    public Management getManagement(final String id);
    
    /**
     *
     * 保存管理成本    
     * @param management 管理成本
     * 
     */
    public void saveManagement(Management management);
    
    /**
     * 根据id删除管理成本
     * @param id 主键
     * 
     */
    public void removeManagement(final String id);
    
    /**
     * 分页取列表
     * @param curPage 当前页
     * @param pageSize 每页显示条数
     * @param whereStr where条件
     * @return map中total为条数,result(list) curPage页的记录
     */
    public Map getManagements(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}