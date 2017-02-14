package com.boco.activiti.partner.process.dao;

import java.util.List;

import com.boco.activiti.partner.process.po.BudgetAmountModel;

/**
 * 
 * @author WangJun
 *
 */

public interface IPnrActCityBudgetAmountJDBCDao {
	
   /**
    * 校验唯一性
    * @param cityId
    * @param budgetYear
    * @param budgetQuarter
    * @return
    */
    public int checkOneUnique(String cityId,String budgetYear,String budgetQuarter);
    
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
	 *   查询区县配置的预算金额
	 	 * @author WANGJUN
	 	 * @title: getCountyAmountByCityId
	 	 * @date Jan 16, 2017 2:40:18 PM
	 	 * @param cityId
	 	 * @param budgetYear
	 	 * @param budgetQuarter
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
