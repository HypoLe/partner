package com.boco.activiti.partner.process.service.impl;

import com.boco.activiti.partner.process.dao.IRoomAssetsRelationDao;
import com.boco.activiti.partner.process.model.RoomAssetsRelation;
import com.boco.activiti.partner.process.service.IRoomAssetsRelationService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

/**
 * 机房资产和流程关联SERVICE实现类
 * 
 * @author WANGJUN
 * 
 */
public class RoomAssetsRelationServiceImpl extends CommonGenericServiceImpl<RoomAssetsRelation>
		implements IRoomAssetsRelationService {

	private IRoomAssetsRelationDao roomAssetsRelationDao;

	public IRoomAssetsRelationDao getRoomAssetsRelationDao() {
		return roomAssetsRelationDao;
	}

	public void setRoomAssetsRelationDao(
			IRoomAssetsRelationDao roomAssetsRelationDao) {
		this.roomAssetsRelationDao = roomAssetsRelationDao;
		this.setCommonGenericDao(roomAssetsRelationDao);
	}

}
