package com.boco.eoms.partner.management.mgr;

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
 public interface IManagementMgr {
 
    /**
     *
     * 取管理成本 列表
     * @return 返回管理成本列表
     */
    public List getManagements();

    /**
     * 根据主键查询管理成本
     * @param id 主键
     * @return 返回某id的对象
     */
    public Management getManagement(final String id);

    /**
     * 保存管理成本
     * @param management 管理成本
     */
    public void saveManagement(Management management);

    /**
     * 根据主键删除管理成本
     * @param id 主键
     */
    public void removeManagement(final String id);
    
    /**
     * 根据主键批量删除管理成本
     * @param ids 主键
     */
    public void removeManagement(final String[] ids);

    /**
     * 根据条件分页查询管理成本
     * @param curPage 当前页
     * @param pageSize 每页包含记录条数
     * @param whereStr 查询条件
     * @return 返回管理成本的分页列表
     */
    public Map getManagements(final Integer curPage, final Integer pageSize,
            final String whereStr);

}