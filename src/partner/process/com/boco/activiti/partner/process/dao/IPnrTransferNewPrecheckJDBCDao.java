package com.boco.activiti.partner.process.dao;

import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.RoomAssets;
import com.boco.activiti.partner.process.po.ChildSceneReportsModel;
import com.boco.activiti.partner.process.po.ConditionQueryDAMModel;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.DownAttachMentModel;
import com.boco.activiti.partner.process.po.DownWorkOrderModel;
import com.boco.activiti.partner.process.po.TaskModel;

public interface IPnrTransferNewPrecheckJDBCDao {
	/**
	 * 预检预修工单--待回复或待办工单--工单总数
	 * 
	 * @author wangyue
	 * @title: getTransferNewPrecheckCount
	 * @date Feb 6, 2015 4:08:58 PM
	 * @param userid
	 *            登录人id
	 * @param flag
	 *            待回复和待办工单标志
	 * @param conditionQueryModel
	 *            条件查询实体类
	 * @return int
	 */
	public int getTransferNewPrecheckCount(String userid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel);
	
	/**
	 * 预检预修--待回复或待办工单--工单集合
	  * @author wangyue
	  * @title: getTransferNewPrecheck
	  * @date Feb 6, 2015 4:41:55 PM
	  * @param userid
	  * @param flag
	  * @param processKey
	  * @param conditionQueryModel
	  * @param firstResult
	  * @param endResult
	  * @param pageSize
	  * @return List<TaskModel>
	 */
	public List<TaskModel> getTransferNewPrecheckList(String userid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel,
			int firstResult, int endResult, int pageSize);
	/**
	 * 预检预修工单--根据区县运维中心主管获取其它部门人员
	  * @author wangyue
	  * @title: getPrecheckShipModelBycountryCharge
	  * @date Feb 11, 2015 1:49:49 PM
	  * @param countryCharge  区县维护中心主管
	  * @return PrecheckShipModel 预检预修人员实体类
	 */
	public List<Map> getPrecheckShipModelBycountryCharge(String countryCharge);
	
	/**
	 * 已处理工单明细
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getHaveProcessList(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status, int firstResult,
			int endResult, int pageSize);

	/**
	 * 已处理工单条数
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @return
	 */ 
	public int getHaveProcessCount(String processDefinitionKey, String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status);
	


	/**
	 * 由我创建的工单条数
	 * 
	 */ 
	public int getByCreatingWorkOrderCount(String processDefinitionKey, String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status);
	
	/**
	 * 由我创建的工单明细
	 * 
	 */
	public List<TaskModel> getByCreatingWorkOrderList(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status, int firstResult,
			int endResult, int pageSize);
	/**
	 * 本地网预检预修--新建工单图片集合
	  * @author wangyue
	  * @title: getPrecheckPhotoes
	  * @date Mar 13, 2015 4:07:07 PM
	  * @return List<PnrAndroidWorkOderPhotoFile>
	 */
	public List<PnrAndroidPhotoFile> getPrecheckPhotoes(String userId,String photoDescribe,String createTime,String photoCreate,String faultLocation);
	
	/**
	 * 查询事情照片
	 * 
	 * @author WangJun 
	 * @param param	参数可以任意的封装
	 * @return
	 */
	public List<PnrAndroidPhotoFile> getPrecheckPhotoes(Map<String,String> param);
	
	/**
	 * 根据id查询出图片存储的路径
	  * @author wangyue
	  * @title: getPhotoPlth
	  * @date Mar 17, 2015 10:45:22 AM
	  * @param id
	  * @return String
	 */
	public String getPhotoPlth(String id);
	/**
	 * 根据工单号查看本工单新建时选择的图片
	  * @author wangyue
	  * @title: showPhotoByProcessInstanceId
	  * @date Mar 19, 2015 4:18:07 PM
	  * @param processInstanceId
	  * @return List<PnrAndroidWorkOderPhotoFile>
	 */
	public List<PnrAndroidPhotoFile> showPhotoByProcessInstanceId(String processInstanceId);
	/**
	 * 根据图片id查询该图片的所有信息
	  * @author wangyue
	  * @title: getOnePnrAndroidPhotoFileById
	  * @date Mar 20, 2015 10:13:32 AM
	  * @param id
	  * @return PnrAndroidPhotoFile
	 */
	public PnrAndroidPhotoFile getOnePnrAndroidPhotoFileById(String id);
	/**
	 * 根据工单号查询该工单下的资产清单
	 * @param processInstanceId
	 * @return
	 */
	public List<RoomAssets> showAssetsByProcessInstanceId(String processInstanceId);
	
	/**
	 * 工单抓回 条数
	 * @param processDefinitionKey 流程ID
	 * @param userTaskFlag	能抓回的环节ID集合
	 * @param userId	登录用户ID
	 * @param conditionQueryModel 查询条件
	 * @return
	 */
	public int getBackSheetCount(String processDefinitionKey,String userId,ConditionQueryModel conditionQueryModel);
	
	/**
	 * 工单抓回 明细
	 * @param processDefinitionKey 流程ID
	 * @param userTaskFlag	能抓回的环节ID集合
	 * @param userId	登录用户ID
	 * @param conditionQueryModel	查询条件
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getBackSheetList(String processDefinitionKey,String userId,ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize);
	/**
	 * 删除该工单下关联的所有图片
	  * @author wangyue
	  * @title: judgeIsExitBypgohoIdAndProcessInstanceId
	  * @date Mar 23, 2015 2:13:11 PM
	  * @param processInstanceId
	  * @param photoId
	  * @return void
	 */
	public void judgeIsExitBypgohoIdAndProcessInstanceId(String processInstanceId);
	
	/**
	 * 已归档工单条数
	 * @param processDefinitionKey 流程ID
	 * @param userId 用户ID
	 * @param sendStartTime 派单开始时间
	 * @param sendEndTime	派单结束时间
	 * @param wsNum	工单号	
	 * @param wsTitle	工单主题
	 * @param status	工单状态
	 * @return
	 */ 
	public int getArchivedCount(String processDefinitionKey, String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status,String themeInterface);
	
	/**
	 * 已归档工单明细
	 * @param processDefinitionKey	流程ID
	 * @param userId	用户ID
	 * @param sendStartTime	派单开始时间
	 * @param sendEndTime	派单结束时间
	 * @param wsNum	工单号
	 * @param wsTitle	工单主题
	 * @param status 工单状态
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getArchivedList(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status,String themeInterface, int firstResult,
			int endResult, int pageSize);
	
	/**
	 * 修改图片状态
	  * @author wangyue
	  * @title: setStateByphotoId
	  * @date Apr 1, 2015 5:11:44 PM
	  * @param photoId
	  * @param state void
	 */
	public void setStateByphotoId(String photoId,String state);
	
	/**
	 * 待下载附件--总数
	 * 
	 * @author wyl
	 * @title: getDownAttachMentCount
	 * @date 20150430
	 * @param userid
	 *            登录人id
	 * @param conditionQueryModel
	 *            条件查询实体类
	 * @return int
	 */
	public int getDownAttachMentCount(String userid,
			ConditionQueryDAMModel conditionQueryModel);
	
	/**
	 * 待下载附件--工单集合
	  * @author wyl
	  * @title: getDownAttachMentList
	  * @date 20150430
	  * @param userid
	  * @param conditionQueryModel
	  * @param firstResult
	  * @param endResult
	  * @param pageSize
	  * @return List<TaskModel>
	 */
	public List<DownAttachMentModel> getDownAttachMentList(String userid, 
			ConditionQueryDAMModel conditionQueryModel,
			int firstResult, int endResult, int pageSize);
	
	
	/**
	 * 待下载附件--工单集合
	  * @author wyl
	  * @title: getDownAttachMentList
	  * @date 20150430
	  * @param userid
	  * @param conditionQueryModel
	  * @return List<TaskModel>
	 */
	public List<DownAttachMentModel> getDownAttachMentListAll(String userid, 
			ConditionQueryDAMModel conditionQueryModel);
	
	/**
	 * 待下载附件 是否允许下载附件
	  * @author wyl
	  * @title: IsEnableDownAttachMent
	  * @date 20150504
	  * @param userid
	  * @return List<TaskModel>
	 */
	public Boolean IsEnableDownAttachMent(String userid);
	
	/**
	 * 增加下载记录信息
	 * @param id
	 * @param userid
	 */
	public void addDownAttachMentInfo(String id, String userid);
	
	/**
	 * 删除下载记录信息
	 * @param id
	 */
	public void deleteDownAttachMentInfo(String id);
	

	/**
	 * 通过照片ID删除事情照片
	 * @param id 照片ID
	 */
	public void deletePictureById(String id);
	
	/**
	 * add by wangchang 20150507
	 * 工单下载记录数
	 */
	public int getDownWorkOrderCount(String userid,
			ConditionQueryDAMModel conditionQueryModel);
	
	/**
	 * 获取工单信息
	 */
	
	public List<DownWorkOrderModel> getDownWorkOrderList(String userid,
			ConditionQueryDAMModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) ;
	
	/**
	 * 获取所有工单列表
	 * 
	 */
	public List getDownWorkOrderListAll(String userid, 
			ConditionQueryDAMModel conditionQueryModel);
	
	
	/**
	 * add by wangchang 20150507
	 * 获取图片下载附件个数
	 */
	public int getDownImageImportCount(String userid,
			ConditionQueryDAMModel conditionQueryModel);
	
	
	/**
	 * 图片附件下载
	 */
	public List<DownAttachMentModel> getDownImageImportList(String userid,
			ConditionQueryDAMModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) ;
	
	/**
	 * 本地网-预检预修工单集合
	  * @author chujingang
	  * @title: getLocalNetworkWorkOrderList
	  * @date Feb 6, 2015 4:41:55 PM
	  * @param userid
	  * @param flag
	  * @param processKey
	  * @param conditionQueryModel
	  * @param firstResult
	  * @param endResult
	  * @param pageSize
	  * @return List<TaskModel>
	 */
	public List<TaskModel> getLocalNetworkWorkOrderList(String areaid,String userid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel,
			int firstResult, int endResult, int pageSize);
	
	/**
	 * 工单集合数
	 */
	public int getLocalNetworkWorkOrderListCount(String areaid, String userid,
			String flag, String processKey,
			ConditionQueryModel conditionQueryModel);
	
	/**
	 * 根据环节的ID和工单号查询该环节的处理人
	 * 
	 * @param userTaskId	环节的userTask的Id
	 * @param processInstanceId	工单号
	 * @return
	 */
	public String getLinkProcessingUserId(String userTaskId,String processInstanceId);
	
	/**
	 * 查询流程某个环节的某个月的预算金额总和
	 * 
	 * @param param	查询条件
	 * @return
	 */
	public String getLinkMonthTotalProjectAmount(Map<String,String> param);
	
	/**
	 * 待接口调用工单明细
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> workOrderRemind(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status, int firstResult,
			int endResult, int pageSize);
	/**
	 * 待接口调用工单条数
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @return
	 */ 
	public int workOrderRemindCount(String processDefinitionKey, String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status);
			
	/**
	 * 省公司审批通过已40天工单明细
	 */
	public List<TaskModel> InterfaceCallSmsAlertsList();
	
	/**
	 * 	查询抽检工单条数
	 	 * @author WANGJUN
	 	 * @title: querySamplingCount
	 	 * @date Aug 4, 2016 10:40:41 AM
	 	 * @param userId
	 	 * @param conditionQueryModel
	 	 * @return int
	 */
	public int querySamplingCount(String userId,ConditionQueryModel conditionQueryModel);
	
	/**
	 * 	 查询抽检工单明细
	 	 * @author WANGJUN
	 	 * @title: querySamplingList
	 	 * @date Aug 4, 2016 10:40:50 AM
	 	 * @param userId
	 	 * @param conditionQueryModel
	 	 * @param firstResult
	 	 * @param endResult
	 	 * @param pageSize
	 	 * @return List<TaskModel>
	 */
	public List<TaskModel> querySamplingList(String userId,ConditionQueryModel conditionQueryModel,int firstResult,int endResult,int pageSize);
}
