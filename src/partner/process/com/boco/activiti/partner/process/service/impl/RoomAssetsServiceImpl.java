package com.boco.activiti.partner.process.service.impl;

import java.util.List;

import com.boco.activiti.partner.process.dao.IRoomAssetsDao;
import com.boco.activiti.partner.process.dao.IRoomAssetsJDBCDao;
import com.boco.activiti.partner.process.model.RoomAssets;
import com.boco.activiti.partner.process.po.AssetQueryConditionModel;
import com.boco.activiti.partner.process.po.RoomAssetsModel;
import com.boco.activiti.partner.process.service.IRoomAssetsService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

/**
 * 机房资产SERVICE实现类
 * 
 * @author WANGJUN
 * 
 */
public class RoomAssetsServiceImpl extends CommonGenericServiceImpl<RoomAssets>
		implements IRoomAssetsService {

	private IRoomAssetsJDBCDao roomAssetsJDBCDao;

	private IRoomAssetsDao roomAssetsDao;

	public IRoomAssetsJDBCDao getRoomAssetsJDBCDao() {
		return roomAssetsJDBCDao;
	}

	public void setRoomAssetsJDBCDao(IRoomAssetsJDBCDao roomAssetsJDBCDao) {
		this.roomAssetsJDBCDao = roomAssetsJDBCDao;
	}

	public IRoomAssetsDao getRoomAssetsDao() {
		return roomAssetsDao;
	}

	public void setRoomAssetsDao(IRoomAssetsDao roomAssetsDao) {
		this.roomAssetsDao = roomAssetsDao;
		this.setCommonGenericDao(roomAssetsDao);
	}

	/**
	 * 查询资产
	 * 
	 * @param assetQueryConditionModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<RoomAssetsModel> queryAssetsList(
			AssetQueryConditionModel assetQueryConditionModel, int firstResult,
			int endResult, int pageSize) {
		return roomAssetsJDBCDao.queryAssetsList(assetQueryConditionModel,
				firstResult, endResult, pageSize);
	}

	public String[] getRoomAssetsByProcessInstanceId(String processInstanceId) {
		return roomAssetsJDBCDao
				.getRoomAssetsByProcessInstanceId(processInstanceId);
	}
	
	/**
	 * 通过流程号删除对应的机房资产
	 * @param processInstanceId
	 */
	public void deleteRoomAssetsByProcessInstanceId(String processInstanceId){
		roomAssetsJDBCDao.deleteRoomAssetsByProcessInstanceId(processInstanceId);
	}

	@Override
	public int queryAssetsListCount(
			AssetQueryConditionModel assetQueryConditionModel, int firstResult,
			int endResult, int pageSize) {
		// TODO Auto-generated method stub
		return roomAssetsJDBCDao.queryAssetsListCount(assetQueryConditionModel, firstResult, endResult, pageSize);
	}

}
