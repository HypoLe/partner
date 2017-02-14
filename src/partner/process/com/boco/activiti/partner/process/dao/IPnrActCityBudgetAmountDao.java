package com.boco.activiti.partner.process.dao;

import java.util.Map;

import com.boco.activiti.partner.process.model.PnrActCityBudgetAmount;


public interface IPnrActCityBudgetAmountDao {

	/**
	 * 保存地市预算金额
	 */
    public void savePnrActCityBudgetAmount(PnrActCityBudgetAmount pnrActCityBudgetAmount);
    
    /**
	 * 通过主键ID获取地市预算金额
	 * @param id
	 * @return
	 */
    public PnrActCityBudgetAmount getPnrActCityBudgetAmount(final String id);
    
    /**
     * 查询地市预算金额
     * @param pageIndex
     * @param pageSize
     * @param whereStr
     * @return
     */
    @SuppressWarnings("unchecked")
	public Map queryPnrActCityBudgetAmount(final Integer curPage,final Integer pageSize,final String whereStr);
    
    /**
	 * 查询地市金额添加页面
	 */
	@SuppressWarnings("unchecked")
	public Map selectPnrActCityBudgetAmountList(final Integer pageIndex,final Integer pageSize,final String whereStr,final String orderStr);
    
	/**
	 * 查询区县金额添加页面
	 */
	@SuppressWarnings("unchecked")
	public Map selectPnrActCountyBudgetAmountList(String whereStr);
}