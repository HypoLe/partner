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
}
