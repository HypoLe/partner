package com.boco.activiti.partner.process.dao;

import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.TaskModel;

public interface IPnrTransferOverhaulRemakeJDBCDao {
	/**
	 * 根据区县账号ID值获取市环节操作人
	 * 
	 * @param areaCountyAccount
	 *            区县账号ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getCitylinkOfOperationPerson(String areaCountyAccount);

	/**
	 * 根据区县账号ID值和角色ID值获取省环节操作人
	 * 
	 * @param areaCountyAccount
	 *            区县账号ID
	 * @param roleid
	 *            角色ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getProvincelinkOfOperationPerson(String areaCountyAccount,
			String roleid);

	/**
	 * 获取区县账号下拉选的值
	 * 
	 * @param userId
	 *            建单人ID
	 * @return
	 */
	List<Map<String, String>> getAreaCountyAccount(String userId);

	/**
	 * 待回复工单 条数
	 * 
	 * @param userid
	 * @param flag
	 * @param processKey
	 * @param conditionQueryModel
	 * @return
	 */
	public int repairOrderCount(String userid, String flag, String processKey,
			ConditionQueryModel conditionQueryModel);

	/**
	 * 待回复工单 集合
	 * 
	 * @param userid
	 * @param flag
	 * @param processKey
	 * @param conditionQueryModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> repairOrderList(String userid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel,
			int firstResult, int endResult, int pageSize);

	/**
	 * 已处理工单条数
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param conditionQueryModel
	 * @return
	 */
	public int getHaveProcessCount(String processDefinitionKey, String userId,
			ConditionQueryModel conditionQueryModel);

	/**
	 * 已处理工单明细
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param conditionQueryModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getHaveProcessList(String processDefinitionKey,
			String userId, ConditionQueryModel conditionQueryModel,
			int firstResult, int endResult, int pageSize);

	/**
	 * 由我创建的工单条数
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param conditionQueryModel
	 * @return
	 */
	public int getByCreatingWorkOrderCount(String processDefinitionKey,
			String userId, ConditionQueryModel conditionQueryModel);

	/**
	 * 由我创建的工单明细
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param conditionQueryModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getByCreatingWorkOrderList(
			String processDefinitionKey, String userId,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize);

	/**
	 * 工单抓回 查询条数
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param relationTableName
	 *            关系表名
	 * @param taskIds
	 *            能够抓回的taskId
	 * @param conditionQueryModel
	 * @return
	 */
	public int getBackSheetCount(String processDefinitionKey, String userId,
			String relationTableName, String taskIds,
			ConditionQueryModel conditionQueryModel);

	/**
	 * 工单抓回 查询明细
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param relationTableName
	 *            关系表名
	 * @param taskIds
	 *            能够抓回的taskId
	 * @param conditionQueryModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getBackSheetList(String processDefinitionKey,
			String userId, String relationTableName, String taskIds,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize);

	/**
	 * 已归档工单条数
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param themeInterface
	 *            工单的类型
	 * @param onditionQueryModel
	 * @return
	 */
	public int getArchivedCount(String processDefinitionKey, String userId,
			String themeInterface, ConditionQueryModel conditionQueryModel);

	/**
	 * 已归档工单明细
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param themeInterface
	 * @param onditionQueryModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getArchivedList(String processDefinitionKey,
			String userId, String themeInterface,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize);
	
	/**
	 * 新建工单页面图片集合
	 * @param userId
	 * @param photoDescribe
	 * @param createTime
	 * @return
	 */
	public List<PnrAndroidPhotoFile> getPrecheckPhotoes(String userId,String photoDescribe,String createTime);
	/**
	 * 根据工单流程id查询出于该工单关联的图片
	  * @author wangyue
	  * @title: getPhotoByProcessInstanceId
	  * @date Apr 2, 2015 10:37:47 AM
	  * @param processInstanceId
	  * @return List<PnrAndroidPhotoFile>
	 */
	public String[] getPhotoByProcessInstanceId(String processInstanceId);

	
}
