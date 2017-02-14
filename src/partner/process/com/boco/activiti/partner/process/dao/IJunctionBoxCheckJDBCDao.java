package com.boco.activiti.partner.process.dao;

import com.boco.activiti.partner.process.po.JunctionBoxModel;


/**
 * 
 */
public interface IJunctionBoxCheckJDBCDao {
	
	/**
	 * 根据交接箱ID查询核查表中的数据条数
	 * 
	 * @param fiberNodeId	交接箱ID
	 * @return
	 */
	public int judgeJunctionBoxIsCheckCompleted(String fiberNodeId);
	
	/**
	 * 通过交接箱ID获取交接箱详情
	 * @param fiberNodeId
	 * @return
	 */
	public JunctionBoxModel findJunctionBoxDetail(String fiberNodeId);

}
