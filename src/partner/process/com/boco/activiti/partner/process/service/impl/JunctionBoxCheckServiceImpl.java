package com.boco.activiti.partner.process.service.impl;

import com.boco.activiti.partner.process.dao.IJunctionBoxCheckDao;
import com.boco.activiti.partner.process.dao.IJunctionBoxCheckJDBCDao;
import com.boco.activiti.partner.process.model.JunctionBoxCheckInfor;
import com.boco.activiti.partner.process.po.JunctionBoxModel;
import com.boco.activiti.partner.process.service.IJunctionBoxCheckService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

public class JunctionBoxCheckServiceImpl extends CommonGenericServiceImpl<JunctionBoxCheckInfor> implements IJunctionBoxCheckService {

	private IJunctionBoxCheckJDBCDao junctionBoxCheckDaoJDBC;
	private IJunctionBoxCheckDao	junctionBoxCheckDao;

	public IJunctionBoxCheckJDBCDao getJunctionBoxCheckDaoJDBC() {
		return junctionBoxCheckDaoJDBC;
	}

	public void setJunctionBoxCheckDaoJDBC(
			IJunctionBoxCheckJDBCDao junctionBoxCheckDaoJDBC) {
		this.junctionBoxCheckDaoJDBC = junctionBoxCheckDaoJDBC;
	}

	public IJunctionBoxCheckDao getJunctionBoxCheckDao() {
		return junctionBoxCheckDao;
	}

	public void setJunctionBoxCheckDao(IJunctionBoxCheckDao junctionBoxCheckDao) {
		this.junctionBoxCheckDao = junctionBoxCheckDao;
		setCommonGenericDao(junctionBoxCheckDao);
	}

	/**
	 * 判断交接箱是否被核查
	 * 
	 * @param fiberNodeId
	 *            交接箱ID
	 * @return
	 */
	public boolean judgeJunctionBoxIsCheckCompleted(String fiberNodeId) {
		boolean result = true;
		int num = junctionBoxCheckDaoJDBC
				.judgeJunctionBoxIsCheckCompleted(fiberNodeId);
		if (num == 0) {
			result = false;
		}
		return result;
	}

	/**
	 * 通过交接箱ID获取交接箱详情
	 * 
	 * @param fiberNodeId
	 * @return
	 */
	public JunctionBoxModel findJunctionBoxDetail(String fiberNodeId) {
		return junctionBoxCheckDaoJDBC.findJunctionBoxDetail(fiberNodeId);
	}

}