package com.boco.eoms.deviceManagement.baseInfo.service;


import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.boco.eoms.deviceManagement.baseInfo.model.CPBaseStation;
import com.boco.eoms.deviceManagement.baseInfo.model.CheckPoint;
import com.boco.eoms.deviceManagement.baseInfo.model.HandWell;
import com.boco.eoms.deviceManagement.baseInfo.model.LightJoinBox;
import com.boco.eoms.deviceManagement.baseInfo.model.Pole;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.deviceManagement.faultInfo.model.ImportResult;


public interface CheckPointService extends CommonGenericService<CheckPoint> {
	public void save(CheckPoint cp,CPBaseStation bs,String type,String oldType);
	public void save(CheckPoint cp,HandWell hw,String type,String oldType);
	public void save(CheckPoint cp,LightJoinBox ljb,String type,String oldType);
	public void save(CheckPoint cp,Pole pole,String type,String oldType);
	public void delete(CheckPoint cp);
	public void deleteAll(String[]ids);
	public <T> T findByType(String id,T bean);
	public ImportResult importFromFile(InputStream inputStream,
			String fileName, Map<String, String> params) throws Exception;
	/**
	 * 根据巡检段id查询巡检段包括的巡检点
	 * @param segmentId
	 * @return
	 */
	public List<CheckPoint> findCheckPointsBySegmentId(String segmentId);
}
