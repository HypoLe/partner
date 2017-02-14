package com.boco.activiti.partner.process.service;

import java.util.List;

import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.RoomDemolition;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.PrecheckShipModel;
import com.boco.activiti.partner.process.po.RoomDemolitionModel;
import com.boco.activiti.partner.process.po.TaskModel;
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
public interface IRoomDemolitionService extends CommonGenericService<RoomDemolition> {
	/**
	 * 预检预修--待回复或待办工单--工单总数
	  * @author wangyue
	  * @title: getNewPrecheckCount
	  * @date Feb 6, 2015 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param conditionQueryModel
	  * @return int
	 */
	public int getNewPrecheckCount(String userid, String flag,String processKey,
			ConditionQueryModel conditionQueryModel);
	/**
	 * 预检预修--待回复或待办工单--工单集合
	  * @author wangyue
	  * @title: getTransferNewPrecheckList
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
	public List<RoomDemolitionModel> getTransferNewPrecheckList(String userid,
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
	 * @param processDefinitionKey
	 * @param userId
	 * @param conditionQueryModel
	 * @return
	 */
	public int getHaveProcessCount(String processDefinitionKey, String userId,
			ConditionQueryModel conditionQueryModel);
	
	/**
	 * 已处理工单明细
	 * @param processDefinitionKey
	 * @param userId
	 * @param conditionQueryModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<RoomDemolitionModel> getHaveProcessList(String processDefinitionKey,
			String userId,ConditionQueryModel conditionQueryModel,int firstResult,
			int endResult, int pageSize);
	
	
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
	 * 修改图片的state状态
	  * @author wangyue
	  * @title: setStateByPhotoId
	  * @date Apr 1, 2015 5:07:56 PM
	  * @param photo
	  * @param state void
	 */
	public void setStateByPhotoId(String photo,String state);
	
	/**
	 * 根据工单流程id查询出于该工单关联的图片
	  * @author wangyue
	  * @title: getPhotoByProcessInstanceId
	  * @date Apr 2, 2015 10:37:47 AM
	  * @param processInstanceId
	  * @return List<PnrAndroidPhotoFile>
	 */
	public String[] getPhotoByProcessInstanceId(String processInstanceId);
	
	/**
	 * 生成工单编号
	 * @param processKey
	 * @param initator
	 * @param type
	 * @param shortName
	 * @return
	 */
	public String getSheetId(String processKey,String initator,String type,String shortName);
	
	/**
	 * 根据机房ID删除机房
	 * @param id
	 */
	public String deleteRoomResourceById(String id); 
	
	/**
	 *	机房拆除工单查询
	 */
	public List<RoomDemolitionModel> getQueryWorkOrderList(String areaid,
			String flag, String processKey,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize);
	
	/**
	 *	机房拆除工单查询集合数
	 */
	public int getQueryWorkOrderCount(String areaid, String flag,String processKey,
			ConditionQueryModel conditionQueryModel);

}
