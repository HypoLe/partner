package com.boco.eoms.partner.shortperiod.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.shortperiod.model.BackTower;
import com.boco.eoms.partner.shortperiod.po.TowerModel;
import com.boco.eoms.partner.shortperiod.po.TowerQueryConditionModel;

public interface ITowerInspectService extends CommonGenericService<BackTower> {

	public int getTowerCount(String userId,TowerQueryConditionModel towerQueryConditionModel);

	public List<TowerModel> getTowerList(String userId,TowerQueryConditionModel towerQueryConditionModel,int firstResult, int endResult, int pageSize);
	
	public HSSFWorkbook exportTowerList(String userId,TowerQueryConditionModel towerQueryConditionModel);
	
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
	public boolean judgeChangePermissions(String areaId,String userId);
}
