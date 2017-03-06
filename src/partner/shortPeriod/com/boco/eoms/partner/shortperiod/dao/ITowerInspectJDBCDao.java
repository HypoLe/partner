package com.boco.eoms.partner.shortperiod.dao;

import java.util.List;

import com.boco.eoms.partner.shortperiod.po.TowerModel;
import com.boco.eoms.partner.shortperiod.po.TowerQueryConditionModel;

public interface ITowerInspectJDBCDao {
	
	public int getTowerCount(String userId,TowerQueryConditionModel towerQueryConditionModel);

	public List<TowerModel> getTowerList(String userId,TowerQueryConditionModel towerQueryConditionModel,
			int firstResult, int endResult, int pageSize);
	
	/**
	 * 铁塔统计数(铁塔核查20170210) 
	 * @param userId
	 * @param towerQueryConditionModel
	 * @return
	 */
	public int getTowerNewCount(String areaId,String userId,TowerQueryConditionModel towerQueryConditionModel);

	/**
	 * 铁塔详情(铁塔核查20170210)
	 * @param userId
	 * @param towerQueryConditionModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TowerModel> getTowerNewList(String areaId,String userId,TowerQueryConditionModel towerQueryConditionModel,int firstResult, int endResult, int pageSize);
	
	/**
	 * 判断当前用户是否有修改权限
	 * @param areaId 地市Id
	 * @param userId 用户Id
	 * @return
	 */
	public int getTowerUserIdPowerConfig(String areaId,String userId);
}
