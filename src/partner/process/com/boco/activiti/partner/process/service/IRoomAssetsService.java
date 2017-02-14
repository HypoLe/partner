package com.boco.activiti.partner.process.service;

import java.util.List;

import com.boco.activiti.partner.process.model.RoomAssets;
import com.boco.activiti.partner.process.po.AssetQueryConditionModel;
import com.boco.activiti.partner.process.po.RoomAssetsModel;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;

/**
 * 机房资产SERVICE接口
 * 
 * @author WANGJUN
 * 
 */
public interface IRoomAssetsService extends CommonGenericService<RoomAssets> {

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
			int endResult, int pageSize);
	/**
	 * 查询资产
	 * 
	 * @param assetQueryConditionModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public int queryAssetsListCount(
			AssetQueryConditionModel assetQueryConditionModel, int firstResult,
			int endResult, int pageSize);

	/**
	 *  通过流程号获取对应的机房资产
	 * @param processInstanceId
	 * @return
	 */
	public String[] getRoomAssetsByProcessInstanceId(String processInstanceId);
	
	/**
	 * 通过流程号删除对应的机房资产
	 * @param processInstanceId
	 */
	public void deleteRoomAssetsByProcessInstanceId(String processInstanceId);
}
