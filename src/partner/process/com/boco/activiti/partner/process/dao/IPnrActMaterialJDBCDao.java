package com.boco.activiti.partner.process.dao;

/**
 * 
 * @author WangJun
 *
 */

public interface IPnrActMaterialJDBCDao {

	/**
	 * 通过流程实例ID值删除材料
	 * @param processInstanceId 流程实例ID
	 */
	public void deletePnrActMaterialsByProcessInstanceId(String processInstanceId);
}
