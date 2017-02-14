package com.boco.eoms.netresource.modify.dao;

import com.boco.eoms.base.dao.Dao;

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
public interface IModifyDao extends Dao {

    /**
     *
     *取资源变更管理列表
     * @return 返回资源变更管理列表
     */
    public List getModifys();
    
    /**
     * 根据主键查询资源变更管理
     * @param id 主键
     * @return 返回某id的资源变更管理
     */
    public Modify getModify(final String id);
    
    /**
     *
     * 保存资源变更管理    
     * @param modify 资源变更管理
     * 
     */
    public void saveModify(Modify modify);
    
    /**
     * 根据id删除资源变更管理
     * @param id 主键
     * 
     */
    public void removeModify(final String id);
    
    /**
     * 分页取列表
     * @param curPage 当前页
     * @param pageSize 每页显示条数
     * @param whereStr where条件
     * @return map中total为条数,result(list) curPage页的记录
     */
    public Map getModifys(final Integer curPage, final Integer pageSize,final String whereStr);
    
	
}