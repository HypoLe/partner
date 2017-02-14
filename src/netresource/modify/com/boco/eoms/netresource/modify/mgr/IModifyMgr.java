package com.boco.eoms.netresource.modify.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.netresource.modify.model.Modify;

/**
 * <p>
 * Title:资源变更管理
 * </p>
 * <p>
 * Description:资源变更管理
 * </p>
 * <p>
 * Tue Feb 21 11:40:03 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
 public interface IModifyMgr {
 
    /**
     *
     * 取资源变更管理 列表
     * @return 返回资源变更管理列表
     */
    public List getModifys();

    /**
     * 根据主键查询资源变更管理
     * @param id 主键
     * @return 返回某id的对象
     */
    public Modify getModify(final String id);

    /**
     * 保存资源变更管理
     * @param modify 资源变更管理
     */
    public void saveModify(Modify modify);

    /**
     * 根据主键删除资源变更管理
     * @param id 主键
     */
    public void removeModify(final String id);
    
    /**
     * 根据主键批量删除资源变更管理
     * @param ids 主键
     */
    public void removeModify(final String[] ids);

    /**
     * 根据条件分页查询资源变更管理
     * @param curPage 当前页
     * @param pageSize 每页包含记录条数
     * @param whereStr 查询条件
     * @return 返回资源变更管理的分页列表
     */
    public Map getModifys(final Integer curPage, final Integer pageSize,final String whereStr);
    
    
}