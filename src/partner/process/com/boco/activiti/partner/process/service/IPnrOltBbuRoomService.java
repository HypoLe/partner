package com.boco.activiti.partner.process.service;

import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrOltBbuOfficeRelation;
import com.boco.activiti.partner.process.model.PnrOltBbuRoom;
import com.boco.activiti.partner.process.model.RoomAssets;
import com.boco.activiti.partner.process.po.ConditionQueryDAMModel;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.DownAttachMentModel;
import com.boco.activiti.partner.process.po.DownWorkOrderModel;
import com.boco.activiti.partner.process.po.PnrOltBbuOfficeMainModel;
import com.boco.activiti.partner.process.po.PrecheckShipModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
/**
 * 
   * @author wangyue
   * @ClassName: IPnrTransferNewPrecheckService
   * @Version 版本 
   * @Copyright boco
   * @date Feb 4, 2015 10:18:21 AM
   * @description 新预检预修工单--service接口
 */
public interface IPnrOltBbuRoomService extends CommonGenericService<PnrOltBbuRoom> {
	/**
	 * olt bbu机房优化申请--待回复或待办工单--工单总数
	  * @author wangyue
	  * @title: getNewPrecheckCount
	  * @date Feb 6, 2015 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param conditionQueryModel
	  * @return int
	 */
	public int getOltBbuCount(String userid, String flag,String processKey,
			ConditionQueryModel conditionQueryModel);
	/**
	 * olt-bbu机房优化申请--待回复或待办工单--工单集合
	  * @author chujingang
	  * @title: getOltBbuList
	  * @date Feb 6, 2015 4:39:17 PM
	  * @param userid
	  * @param flag
	  * @param processKey
	  * @param conditionQueryModel
	  * @param firstResult
	  * @param endResult
	  * @param pageSize
	  * @return List<TaskModel>
	 */
	public List<PnrOltBbuOfficeMainModel> getOltBbuList(String userid,
			String flag, String processKey,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize);
	/**
	 * 预检预修工单--根据区县运维中心主管获取其它部门人员
	  * @author wangyue
	  * @title: getPrecheckShipModelBycountryCharge
	  * @date Feb 11, 2015 1:49:49 PM
	  * @param countryCharge  区县维护中心主管
	  * @return PrecheckShipModel 预检预修人员实体类
	 */
	public PrecheckShipModel getPrecheckShipModelBycountryCharge(String countryCharge);
	
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
	public List<PnrOltBbuOfficeMainModel> getHaveProcessList(String processDefinitionKey,
			String userId, ConditionQueryModel conditionQueryModel,
			int firstResult, int endResult, int pageSize);
	
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
	public List<PnrOltBbuOfficeMainModel> getByCreatingWorkOrderList(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status, int firstResult,
			int endResult, int pageSize);
	/**
	 * 本地网预检预修-新建工单页面图片集合
	  * @author wangyue
	  * @title: getPrecheckPhotoes
	  * @date Mar 13, 2015 4:04:17 PM
	  * @return List<PnrAndroidWorkOderPhotoFile>
	 */
	public List<PnrAndroidPhotoFile> getPrecheckPhotoes(String userId,String photoDescribe,String createTime,String photoCreate,String faultLocation);
	/**
	 * 根据id查询出图片存储的路径
	  * @author wangyue
	  * @title: getPhotoPlth
	  * @date Mar 17, 2015 10:43:32 AM
	  * @param id
	  * @return String
	 */
	public String getPhotoPlth(String id);
	
	/**
	 * 根据工单id查询出新建工单时选择的图片--本地网和干线工单
	  * @author wangyue
	  * @title: showPhotoByProcessInstanceId
	  * @date Mar 19, 2015 4:33:01 PM
	  * @param processInstanceId
	  * @return List<PnrAndroidWorkOderPhotoFile>
	 */
	public List<PnrAndroidPhotoFile> showPhotoByProcessInstanceId(String processInstanceId);
	/**
	 * 根据工单号查询出该工单下的资产清单
	 * @return
	 */
	public List<RoomAssets> showAssetsByProcessInstanceId(String processInstanceId);
	/**
	 * 根据图片id，查询出该图片的所有信息
	  * @author wangyue
	  * @title: getOnePnrAndroidPhotoFileById
	  * @date Mar 20, 2015 10:11:54 AM
	  * @param id
	  * @return PnrAndroidPhotoFile
	 */
	public PnrAndroidPhotoFile getOnePnrAndroidPhotoFileById(String id);
	
	
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
	 * 修改图片的state状态
	  * @author wangyue
	  * @title: setStateByPhotoId
	  * @date Apr 1, 2015 5:07:56 PM
	  * @param photo
	  * @param state void
	 */
	public void setStateByPhotoId(String photo,String state);

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
	 * 通过照片ID删除事情照片
	 * @param id 照片ID
	 */
	public void deletePictureById(String id);

	/**
	 * 待下载附件--工单总数
	  * @author wyl
	  * @title: getDownAttachMentCount
	  * @date 20150430
	  * @param userid
	  * @param conditionQueryModel
	  * @return int
	 */
	public int getDownAttachMentCount(String userid,
			ConditionQueryDAMModel conditionQueryModel);
	
	/**
	 * 待下载附件--工单集合
	  * @author wyl
	  * @title: getTransferNewPrecheckList
	  * @date 20150430
	  * @param userid
	  * @param conditionQueryModel
	  * @param firstResult
	  * @param endResult
	  * @param pageSize
	  * @return List<TaskModel>
	 */
	public List<DownAttachMentModel> getDownAttachMentList(String userid,
			ConditionQueryDAMModel conditionQueryModel, int firstResult,
			int endResult, int pageSize);

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
	 * add by wangchang 20150507
	 * 工单下载记录数
	 */
	public int getDownWorkOrderCount(String userid,
			ConditionQueryDAMModel conditionQueryModel);
	
	/**
	 * add by wangchang 20150507
	 * 下载附件结果集合
	 */
	public List<DownWorkOrderModel> getDownWorkOrderList(String userid,
			ConditionQueryDAMModel conditionQueryModel, int firstResult,
			int endResult, int pageSize);
	
	/**
	 * add by wangchang 20150507
	 * 查询所有工单信息
	 */
	public List getDownWorkOrderListAll(String userid,
			ConditionQueryDAMModel conditionQueryModel);
	
	/**
	 * add by wangchang 20150507
	 * 下载图片附件记录数
	 */
	public int getDownImageImportCount(String userid,
			ConditionQueryDAMModel conditionQueryModel) ;
	
	/**
	 * add by wangchang 20150507
	 * 下载图片附件结果集合
	 */
	public List<DownAttachMentModel> getDownImageImportList(String userid,
			ConditionQueryDAMModel conditionQueryModel, int firstResult,
			int endResult, int pageSize);
	
	/**
	 * 工单集合
	  * @author chujingang
	  * @title: getLocalNetworkWorkOrderList
	  * @date Feb 6, 2015 4:39:17 PM
	  * @param userid
	  * @param flag
	  * @param processKey
	  * @param conditionQueryModel
	  * @param firstResult
	  * @param endResult
	  * @param pageSize
	  * @return List<TaskModel>
	 */
	public List<PnrOltBbuOfficeMainModel> getOltBbuWorkOrderList(String areaid,String userid,
			String flag, String processKey,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize);
	
	/**
	 * 
	 * 工单集合数
	 */
	public int getOltBbuWorkOrderListCount(String areaid,String userid, String flag,String processKey,
			ConditionQueryModel conditionQueryModel);
	
	/**
	 * activiti驳回到任意节点的实现
	 * 
	 * @param procInstId
	 * @param destTaskKey
	 * @param rejectMessage
	 */
	public void rejectTask(String procInstId, String destTaskKey,String rejectMessage);
	
	/**
	 * activiti驳回到任意节点的实现
	 * 
	 * @param processInstanceId	流程号
	 * @param toTaskKey	驳回到的任务key值
	 * @param rejectMessage	驳回原因
	 * @param variables	涉及到的流程参数	设定驳回标志	
	 */
	public void rejectToAnyTask(String processInstanceId, String toTaskKey,
			String rejectMessage,Map<String, Object> variables) ;
	
	/**
	 * 根据环节的ID和工单号查询该环节的处理人
	 * 
	 * @param userTaskId	环节的userTask的Id
	 * @param processInstanceId	工单号
	 * @return
	 */
	public String getLinkProcessingUserId(String userTaskId,String processInstanceId);
	
	/**
	 * 市运维主管审批的时候,对项目金额进行判断
	 * 
	 * @param sessionform	操作人
	 * @param processInstanceId	流程号
	 * @return
	 */
	//public String validateBudgetAmount(TawSystemSessionForm sessionform,String processInstanceId);
	
	/**
	 * 查询流程某个环节的某个月的预算金额总和
	 * 
	 * @param conditionQueryModel	查询条件
	 * @return
	 */
	//public String getLinkMonthTotalProjectAmount(ConditionQueryModel conditionQueryModel);
	
	/**
	 * 机房优化申请-获取机房清单信息总数
	  * @author chujingang
	 */
	public int selectOltBbuRoomCount(String processKey,
			ConditionQueryModel conditionQueryModel);
	
	/**
	 * 机房优化申请-获取机房清单信息集合
	  * @author chujingang
	 */
	public List<PnrOltBbuRoom> selectOltBbuRoolList(String processKey,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize);
	/**
	 * 按机房清单id查询详情
	  * @author chujingang
	 */
	public List<PnrOltBbuRoom> findOltBbuRoomByid(String id);
	/**
	 * 生成olt-bbu机房优化申请 工单编码
	 * 格式：JF-JN-GX-20150918-1
	 * 机房-地市双位缩写-区县双位缩写-建单日期-个数序号
	 * @author chujingang
	 */
	public String getSheetId(String processKey, String initator, String type);
}
