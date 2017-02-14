package com.boco.activiti.partner.process.service.impl;

import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.dao.IPnrActCityBudgetAmountDao;
import com.boco.activiti.partner.process.dao.IPnrActCityBudgetAmountJDBCDao;
import com.boco.activiti.partner.process.model.PnrActCityBudgetAmount;
import com.boco.activiti.partner.process.po.BudgetAmountModel;
import com.boco.activiti.partner.process.service.IPnrActCityBudgetAmountService;

public class PnrActCityBudgetAmountServiceImpl implements IPnrActCityBudgetAmountService {
	private IPnrActCityBudgetAmountDao pnrActCityBudgetAmountDao;
	private IPnrActCityBudgetAmountJDBCDao pnrActCityBudgetAmountDaoJDBC;

	/**
	 * 保存会审人员
	 */
	public void savePnrActCityBudgetAmount(PnrActCityBudgetAmount pnrActCityBudgetAmount) {
		pnrActCityBudgetAmountDao.savePnrActCityBudgetAmount(pnrActCityBudgetAmount);
	}

	/**
	 * 通过主键ID获取会审人员
	 * 
	 * @param id
	 * @return
	 */
	public PnrActCityBudgetAmount getPnrActCityBudgetAmount(String id) {
		return pnrActCityBudgetAmountDao.getPnrActCityBudgetAmount(id);
	}
	
	/**
	 * 查询会审人员
	 */
	@SuppressWarnings("unchecked")
	public Map queryPnrActCityBudgetAmount(Integer pageIndex,Integer pageSize,String whereStr){
		return pnrActCityBudgetAmountDao.queryPnrActCityBudgetAmount( pageIndex, pageSize, whereStr);
	}

	/**
	 * 校验唯一性
	 * @param cityId
	 * @param budgetYear
	 * @param budgetQuarter
	 * @return
	 */
	public String validateUnique(String cityId,String cityName, String budgetYear,
			String budgetQuarter) {
		int result=0;
		String budgetQuarterStr="";
		String errorStr = "";//记录异常信息
		String resultStr="";//返回JSON结果
		if (cityId != null && !"".equals(cityId)) {
			//季度转换
			if(budgetQuarter.equals("01")){
				budgetQuarterStr="第一季度";
			}else if(budgetQuarter.equals("04")){
				budgetQuarterStr="第二季度";
			}else if(budgetQuarter.equals("07")){
				budgetQuarterStr="第三季度";
			}else if(budgetQuarter.equals("10")){
				budgetQuarterStr="第四季度";
			}
			
			String[] tempCityIds = cityId.split(",");
			String[] tempCityNames = cityName.split(",");
			for(int i=0;i<tempCityIds.length;i++){
				result=pnrActCityBudgetAmountDaoJDBC.checkOneUnique(tempCityIds[i], budgetYear,budgetQuarter);
				if(result==1){
					errorStr+=tempCityNames[i]+","+budgetYear+"年,"+budgetQuarterStr+"的金额已存在。";
				}
			}
			
			if(!errorStr.equals("")){
				errorStr+="不能保存，请重新选择！";
				resultStr="{\"result\":\"error\",\"content\":\""+errorStr+"\"}";
			}else{
				resultStr="{\"result\":\"success\",\"content\":\"唯一性校验通过\"}";
			}
			
			
		}
		return resultStr;
	}
	
	/**
	 * 查询地市金额添加页面
	 */
	@SuppressWarnings("unchecked")
	public Map selectPnrActCityBudgetAmountList(Integer pageIndex,Integer pageSize,String whereStr,String orderStr){
		return pnrActCityBudgetAmountDao.selectPnrActCityBudgetAmountList( pageIndex, pageSize, whereStr,orderStr);
	}
	
	/**
	 * 查询区县金额添加页面
	 */
	@SuppressWarnings("unchecked")
	public Map selectPnrActCountyBudgetAmountList(String whereStr){
		return pnrActCityBudgetAmountDao.selectPnrActCountyBudgetAmountList(whereStr);
	}
	/**
	 * 查询预算金额
	 * 
	 * @param cityId	地市id
	 * @param budgetYear	年
	 * @param budgetQuarter	季度
	 * @return
	 */
	public String getCityBudgetAmount(String cityId,String budgetYear,String budgetQuarter){
		return pnrActCityBudgetAmountDaoJDBC.getCityBudgetAmount( cityId, budgetYear, budgetQuarter);
	}
	
	

	public IPnrActCityBudgetAmountDao getPnrActCityBudgetAmountDao() {
		return pnrActCityBudgetAmountDao;
	}

	public void setPnrActCityBudgetAmountDao(
			IPnrActCityBudgetAmountDao pnrActCityBudgetAmountDao) {
		this.pnrActCityBudgetAmountDao = pnrActCityBudgetAmountDao;
	}

	public IPnrActCityBudgetAmountJDBCDao getPnrActCityBudgetAmountDaoJDBC() {
		return pnrActCityBudgetAmountDaoJDBC;
	}

	public void setPnrActCityBudgetAmountDaoJDBC(
			IPnrActCityBudgetAmountJDBCDao pnrActCityBudgetAmountDaoJDBC) {
		this.pnrActCityBudgetAmountDaoJDBC = pnrActCityBudgetAmountDaoJDBC;
	}
	
	/**
	 *   查询区县配置的预算金额
	 	 * @author WANGJUN 
	 	 * @title: getCountyAmountByCityId
	 	 * @date Jan 16, 2017 3:02:14 PM
	 	 * @param pId 父id即地市表中的主键id值
	 	 * @return List<BudgetAmountModel>
	 */
	public List<BudgetAmountModel> getCountyAmountByCityId(String pId){
		return pnrActCityBudgetAmountDaoJDBC.getCountyAmountByCityId(pId);
	}
	
	/**
	 *   更新区县配置金额
	 	 * @author WANGJUN
	 	 * @title: updateCountyBudgetAmount
	 	 * @date Jan 16, 2017 4:22:44 PM
	 	 * @param id
	 	 * @param budgetAmount
	 	 * @return int
	 */
	public int updateCountyBudgetAmount(String id,String budgetAmount){
		return pnrActCityBudgetAmountDaoJDBC.updateCountyBudgetAmount(id,budgetAmount);
	}
}