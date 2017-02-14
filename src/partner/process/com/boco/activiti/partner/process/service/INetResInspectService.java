package com.boco.activiti.partner.process.service;

import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.model.NetResInspect;
import com.boco.activiti.partner.process.model.NetResInspectDraft;
import com.boco.activiti.partner.process.model.NetResInspectParam;
import com.boco.activiti.partner.process.model.OptionUtil;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrPrecheckPhoto;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.NetResInspectModel;
import com.boco.activiti.partner.process.po.PrecheckShipModel;
import com.boco.activiti.partner.process.po.RoomDemolitionModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.eoms.commons.accessories.webapp.form.TawCommonsAccessoriesForm;
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
public interface INetResInspectService extends CommonGenericService<NetResInspect> {
	
//	public String getSpecialtyIdByResourceType(String resourceTypeId);
	
	
	
	
	
	
	
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
	public List<NetResInspectModel> getTransferNewPrecheckList(String userid,
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
	public List<NetResInspectModel> getHaveProcessList(String processDefinitionKey,
			String userId,ConditionQueryModel conditionQueryModel,int firstResult,
			int endResult, int pageSize);
	
	
	/**
	 * 由我创建的工单条数
	 * 
	 */ 
	public int getByCreatingWorkOrderCount(String processDefinitionKey, String userId,ConditionQueryModel conditionQueryModel);
	
	/**
	 * 由我创建的工单明细
	 * 
	 */
	public List<NetResInspectModel> getByCreatingWorkOrderList(String processDefinitionKey,
			String userId, ConditionQueryModel conditionQueryModel, int firstResult,
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
	public List<NetResInspectModel> getQueryWorkOrderList(String areaid,
			String flag, String processKey,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize);
	
	/**
	 *	机房拆除工单查询集合数
	 */
	public int getQueryWorkOrderCount(String areaid, String flag,String processKey,
			ConditionQueryModel conditionQueryModel);
	
	/**
	 * 	 根据区县和专业获取现场核实人
	 	 * @author WANGJUN
	 	 * @title: getChecker
	 	 * @date Jun 21, 2016 9:01:39 AM
	 	 * @param county
	 	 * @param specialty
	 	 * @return String
	 */
	public String getChecker(String county,String specialty);
	
	/**
	 * 	 自动派发子流程
	 	 * @author WANGJUN
	 	 * @title: autoSendSubProcess
	 	 * @date Jun 23, 2016 2:20:48 PM
	 	 * @param netResInspect 工单信息
	 	 * @param type	子流程类型
	 	 * @return String
	 */
	public Map<String,String> autoSendSubProcess(NetResInspect netResInspect,String type);
	
	/**
	 * 	 判断下一步流程走向
	 	 * @author WANGJUN
	 	 * @title: judgmentFlowTrend
	 	 * @date Jun 24, 2016 3:29:58 PM
	 	 * @param isHiddenDanger 专业
	 	 * @param isHiddenDanger 是否隐患
	 	 * @param isBuild 是否建设
	 	 * @param isLineHiddenDanger 是否线路隐患
	 	 * @param autoSendProcess 派发流程
	 	 * @return String
	 */
	public String judgmentFlowTrend(String specialty,String isHiddenDanger,String isBuild,String isLineHiddenDanger,String autoSendProcess);
	
	/**
	 * 	 现场核实环节处理方法	 
	 	 * @author WANGJUN
	 	 * @title: siteCheckSubmit
	 	 * @date Jun 27, 2016 10:11:13 AM
	 	 * @param param
	 	 * @return String
	 */
	public String siteCheckSubmit(NetResInspectParam param);
	/**
	 * 	 现场核实环节提交 
	 	 * @author zhoukeqing
	 	 * @title: worOrderProcessing
	 	 * @date Jul 4, 2016 10:11:13 AM
	 	 * @param param
	 	 * @return String
	 */
	public String worOrderProcessing(NetResInspectParam param);
	/**
	 * 	 工单归档 
	 	 * @author zhoukeqing
	 	 * @title: workOrderArchiving
	 	 * @date Jul 5, 2016 10:11:13 AM
	 	 * @param param
	 	 * @return String
	 */
	public String workOrderArchiving(String processInstanceId,String userId);
	
	/**
	 * 	 查询自动派发的子工单数量
	 	 * @author WANGJUN
	 	 * @title: getSubCount
	 	 * @date Jun 28, 2016 2:23:33 PM
	 	 * @param userId
	 	 * @param processType
	 	 * @param conditionQueryModel
	 	 * @return int
	 */
	public int getSubCount(String userId,String processType,ConditionQueryModel conditionQueryModel);
	
	/**
	 * 	 查询自动派发的子工单集合
	 	 * @author WANGJUN
	 	 * @title: getSubCount
	 	 * @date Jun 28, 2016 2:23:33 PM
	 	 * @param userId
	 	 * @param processType
	 	 * @param conditionQueryModel
	 	 * @return int
	 */
	public List<TaskModel> getSubList(String userid,String processKey,ConditionQueryModel conditionQueryModel, int firstResult,int endResult, int pageSize);
	/**
	 * 	 查询自动派发的子工单信息
	 	 * @author ZHOUKEQING
	 	 * @title: getNetResInspectDraft
	 	 * @date Jun 29, 2016 2:23:33 PM
	 	 * @param id
	 	 * @return NetResInspectDraft
	 */
	public NetResInspectDraft getNetResInspectDraft(String processInstanceId);
	/**
	 * 	 改变众筹工单派发到本地网及抢修工单草稿状态
	 	 * @author ZHOUKEQING
	 	 * @title: getNetResInspectDraft
	 	 * @date Jun 29, 2016 2:23:33 PM
	 	 * @param id
	 	 * @return NetResInspectDraft
	 */
	public void updateNetResInspectDraft(String netResInspectId);
	
	/**
	 * 	 现场核实环节 转派
	 	 * @author 你的名字
	 	 * @title: siteCheckTurnSend
	 	 * @date Jul 7, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public String siteCheckTurnSend(NetResInspectParam param);
	/**
	 * 	 获取地市的select
	 	 * @author zhoukeqing
	 	 * @title: getCountrySelect
	 	 * @date Jul 11, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public List<OptionUtil> getCountrySelect(String city);
	/**
	 * 	 手机工单代办列表查询
	 	 * @author zhoukeqing
	 	 * @title: searchListsendundo
	 	 * @date Jul 13, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public List<NetResInspect> searchListsendundo(String userid,int pageSize,int pageIndex);
	/**
	 * 	 手机工单代办列表总数查询
	 	 * @author zhoukeqing
	 	 * @title: searchListsendundoTotal
	 	 * @date Jul 13, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public int searchListsendundoTotal(String userid);
	/**
	 * 	 手机工单详情页面查询
	 	 * @author zhoukeqing
	 	 * @title: serchDetailAndroid
	 	 * @date Jul 13, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public NetResInspect serchDetailAndroid(String processInstanceId);
	/**
	 * 	 查询pnr_android_photo
	 	 * @author zhoukeqing
	 	 * @title: serchAndroidPhoto
	 	 * @date Jul 13, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public List<PnrAndroidPhotoFile> serchAndroidPhoto(String processInstanceId);
	/**
	 * 	 将手机拍摄的照片和流程关联
	 	 * @author zhoukeqing
	 	 * @title: saveFlowPhone
	 	 * @date Jul 15, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public void saveFlowPhone(String processInstanceId,String type);
	/**
	 * 	 将手机拍摄的照片和流程关联  新建工单用
	 	 * @author zhoukeqing
	 	 * @title: saveFlowPhone
	 	 * @date Jul 15, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public void savePhoneStarFlow(List<PnrAndroidPhotoFile> list,String processInstanceId);
	/**
     * 众筹流程--附件显示
     * @param processInstanceId
     * @param flag
     * @return
     * @throws Exception
     */
     public List<TawCommonsAccessoriesForm> getRoomDemolitionAccessoriesList(String processInstanceId,String flag)throws Exception;
}
