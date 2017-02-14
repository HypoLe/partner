package com.boco.activiti.partner.process.service;

import com.boco.activiti.partner.process.model.JunctionBoxCheckInfor;
import com.boco.activiti.partner.process.po.JunctionBoxModel;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;

public interface IJunctionBoxCheckService extends CommonGenericService<JunctionBoxCheckInfor> {

	/**
	 * 判断交接箱是否被核查
	 * 
	 * @param fiberNodeId	交接箱ID
	 * @return
	 */
	public boolean judgeJunctionBoxIsCheckCompleted(String fiberNodeId);
	
	/**
	 * 通过交接箱ID获取交接箱详情
	 * @param fiberNodeId
	 * @return
	 */
	public JunctionBoxModel findJunctionBoxDetail(String fiberNodeId);
}
