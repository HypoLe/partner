package com.boco.activiti.partner.process.service;

import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.model.PnrActCityBudgetAmount;
import com.boco.activiti.partner.process.po.BudgetAmountModel;

public interface IPnrActCityBudgetAmountService  {

	/**
	 * 保存地市金额
	 */
	public void savePnrActCityBudgetAmount(PnrActCityBudgetAmount pnrActCityBudgetAmount);

	/**
	 * 通过主键ID获取地市金额
	 * 
	 * @param id
	 * @return
	 */
	public PnrActCityBudgetAmount getPnrActCityBudgetAmount(String id);
	
	/**
	 * 查询地市金额
	 */
	@SuppressWarnings("unchecked")
	public Map queryPnrActCityBudgetAmount(Integer pageIndex,Integer pageSize,String whereStr);
	
	/**
	 * 校验唯一性
	 * @param cityId
	 * @param cityName
	 * @param budgetYear
	 * @param budgetQuarter
	 * @return
	 */
	public String validateUnique(String cityId,String cityName,String budgetYear,String budgetQuarter);
	
	/**
	 * 查询地市金额添加页面
	 */
	@SuppressWarnings("unchecked")
	public Map selectPnrActCityBudgetAmountList(Integer pageIndex,Integer pageSize,String whereStr,String orderStr);
	
	/**
	 * 查询预算金额
	 * 
	 * @param cityId	地市id
	 * @param budgetYear	年
	 * @param budgetQuarter	季度
	 * @return
	 */
	public String getCityBudgetAmount(String cityId,String budgetYear,String budgetQuarter);
	/**
	 * 查询区县金额页面
	 */
	@SuppressWarnings("unchecked")
	public Map selectPnrActCountyBudgetAmountList(String whereSt);

	/**
	 *   查询区县配置的预算金额
	 	 * @author WANGJUN 
	 	 * @title: getCountyAmountByCityId
	 	 * @date Jan 16, 2017 3:02:14 PM
	 	 * @param pId 父id即地市表中的主键id值
	 	 * @return List<BudgetAmountModel>
	 */
	public List<BudgetAmountModel> getCountyAmountByCityId(String pId);
	
	/**
	 *   更新区县配置金额
	 	 * @author WANGJUN
	 	 * @title: updateCountyBudgetAmount
	 	 * @date Jan 16, 2017 4:22:44 PM
	 	 * @param id
	 	 * @param budgetAmount
	 	 * @return int
	 */
	public int updateCountyBudgetAmount(String id,String budgetAmount);
}