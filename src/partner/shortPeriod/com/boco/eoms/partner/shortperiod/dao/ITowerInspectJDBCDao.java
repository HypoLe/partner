package com.boco.eoms.partner.shortperiod.dao;

import java.util.List;

import com.boco.eoms.partner.shortperiod.po.TowerModel;
import com.boco.eoms.partner.shortperiod.po.TowerQueryConditionModel;

public interface ITowerInspectJDBCDao {
	
	public int getTowerCount(String userId,TowerQueryConditionModel towerQueryConditionModel);

	public List<TowerModel> getTowerList(String userId,TowerQueryConditionModel towerQueryConditionModel,
			int firstResult, int endResult, int pageSize);
}
