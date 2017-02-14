package com.boco.eoms.partner.assess.AssFlow.dao;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.assess.AssFlow.model.AssOperateStep;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title:后评估主表信息
 * </p>
 * <p>
 * Description:后评估主表信息
 * </p>
 * <p>
 * Fri Sep 10 13:54:56 CST 2010
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public interface IAssOperateStepDao extends Dao {

    /**
    *
    *取后评估主表信息列表
    * @return 返回后评估主表信息列表
    */
    public List getAssOperateSteps();

    /**
    *
    *取后评估主表信息列表
    * @return 返回后评估主表信息列表
    */
    public List getAssOperateSteps(final String reportId);
    /**
    * 根据主键查询后评估主表信息
    * @param id 主键
    * @return 返回某id的后评估主表信息
    */
    public AssOperateStep getAssOperateStep(final String id);
    
    /**
    *
    * 保存后评估主表信息    
    * @param assOperateStep 后评估主表信息
    * 
    */
    public void saveAssOperateStep(AssOperateStep assOperateStep);
    
    /**
    * 根据id删除后评估主表信息
    * @param id 主键
    * 
    */
    public void removeAssOperateStep(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getAssOperateSteps(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}