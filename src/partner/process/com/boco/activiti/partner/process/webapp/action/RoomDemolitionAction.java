package com.boco.activiti.partner.process.webapp.action;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.ServiceImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sun.misc.BASE64Decoder;

import com.boco.activiti.partner.process.cmd.HistoryProcessInstanceDiagramCmd;
import com.boco.activiti.partner.process.model.PnrActMaterial;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrPrecheckPhoto;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.model.PnrTransferOfficeHandle;
import com.boco.activiti.partner.process.model.RoomAssets;
import com.boco.activiti.partner.process.model.RoomAssetsRelation;
import com.boco.activiti.partner.process.model.RoomDemolition;
import com.boco.activiti.partner.process.model.RoomDemolitionHandle;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.PrecheckRoleModel;
import com.boco.activiti.partner.process.po.PrecheckShipModel;
import com.boco.activiti.partner.process.po.RoomDemolitionModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.service.IPnrActMaterialService;
import com.boco.activiti.partner.process.service.IPnrAndroidMgr;
import com.boco.activiti.partner.process.service.IPnrTransferNewPrecheckService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeHandleService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.activiti.partner.process.service.IPnrTransferPrecheckService;
import com.boco.activiti.partner.process.service.IProcessTaskService;
import com.boco.activiti.partner.process.service.IRoomAssetsRelationService;
import com.boco.activiti.partner.process.service.IRoomAssetsService;
import com.boco.activiti.partner.process.service.IRoomDemolitionHandleService;
import com.boco.activiti.partner.process.service.IRoomDemolitionService;
import com.boco.activiti.partner.process.service.PnrPrecheckPhotoService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.accessories.webapp.form.TawCommonsAccessoriesForm;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;


public class RoomDemolitionAction extends SheetAction {

	private final String processDefinitionKey = "roomDemolition";
	private final DateFormat sFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static Logger logger = Logger.getLogger(RoomDemolitionAction.class);
	// 发信息用的常量
	public final static String TASK_MESSAGE = "机房拆除";
	public final static String TASK_WORKORDERDEGREE = "紧急程度：";
	public final static String TASK_WORKORDERTYPE = "类型：";
	public final static String TASK_SUBTYPE = "子类型：";
	public final static String TASK_NO_STR = "工单编号:";
	public final static String TASK_TITLE_STR = "主题:";
	public final static String TASK_DEADSUBMIT_STR = "申请提交时间:";
	public final static String TASK_DEADLINE_STR = "截止时间:";
	public final static String TASK_CONTACT_STR = "联系人及电话:";
	public final static String TASK_STATION_NAME = "机房名称:";
	public final static String TASK_SEND_TIME = "派单时间:";

	// 施工队
	private final static String TASK_TRANSFERHANDLE = "transferHandle";
	// 市运维
	public final static String TASK_CITYMANAGEEXAMINE_STATUS = "市运维审批完毕";

	public final static String TASK_PROVINCEMANAGEEXAMINE_STATUS = "省运维审批完毕";
	
	private final static String TASK_WORKORDERCHECK = "workOrderCheck";
	private final static String TASK_CITYLINEEXAMINE = "cityLineExamine";
	private final static String TASK_CITYLINEDIRECTOR = "cityLineDirectorAudit";
	private final static String TASK_PROVINCEMANAGE = "provinceManageCheck";
	private final static String TASK_CITYMANAGEEXAMINE = "cityManageExamine";
	private final static String TASK_CITYMANAGEDIRECTOR = "cityManageDirectorAudit";
	private final static String TASK_CITYVICEAUDIT = "cityViceAudit";
	private final static String TASK_PROVINCELINEVICE = "provinceLineViceAudit";
	private final static String TASK_PROVINCEMANAGEEXAMINE = "provinceManageExamine";
	private final static String TASK_PROVINCEMANAGEVICE = "provinceManageViceAudit";
	// 工单删除状态
	public final static Integer TASK_DELETE_STATE = 1;
	/** 动态获取角色id实体类 */
	private PrecheckRoleModel precheckRoleModel = (PrecheckRoleModel) ApplicationContextHolder
			.getInstance().getBean("precheckRoleModel");
	private IProcessTaskService processTaskService = (IProcessTaskService) ApplicationContextHolder.getInstance().getBean("processTaskService");
	private static double EARTH_RADIUS = 6378.137;
	private static double rad(double d)
	{
	   return d * Math.PI / 180.0;
	}
	

	/**
	 * 预检预修工单--新增页面
	 * 
	 * @author wangyue
	 * @title: newTask
	 * @date Feb 4, 2015 10:27:41 AM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward newTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {;
		this.getRegionalInfo(request);
		// 获取当前系统时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String initiateTime = df.format(new Date());// new Date()为获取当前系统时间
		request.setAttribute("initiateTime", initiateTime);// 发起时间
		request.setAttribute("access", 1);//保存草稿按钮标示，1为显示
		return mapping.findForward("new");
	}
	
	/**
	 * 获取初始化的地市信息
	 * 
	 * @param request
	 */
	private void getRegionalInfo(HttpServletRequest request) {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		String deptId = sessionForm.getDeptid();

		ITawSystemDeptManager deptSys = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		TawSystemDept tawSystemDept = deptSys.getDeptinfobydeptid(deptId, "0");
		String quxian = tawSystemDept.getAreaid();
		String city = quxian;
		if ((StaticMethod.nullObject2String(quxian)).length() > 0
				&& quxian.length() > 4) {
			city = quxian.substring(0, 4);
		}

		TawSystemDept list = null;
		String deptIdString = "";
		String region = "";
		String executeDept = deptId;

		if (deptId.length() >= 5) {
			deptIdString = deptId.substring(0, 5);
			list = deptSys.getDeptinfobydeptid(deptIdString, "0");
		}
		if ("admin".equals(userId) || "superUser".equals(userId)) {// 超级管理员
			deptIdString = "";
		}

		if (list != null) {
			region = list.getAreaid();
		}
		// 针对自卫人员
		if (deptId.length() >= 5 && deptId.substring(0, 3).equals("201")) {
			executeDept = deptId.substring(0, 5);
		}

		if (userId.equals("admin")) {
			region = "";
			executeDept = "";
		}
		if (deptId.substring(0, 3).equals("101")) {
			executeDept = "";
		}

		request.setAttribute("userId", userId);// 工单发起人
		request.setAttribute("deptId", deptId);// 发起人部门
		request.setAttribute("city", city);// 地市
		request.setAttribute("quxian", quxian);// 区县
		request.setAttribute("executeDept", executeDept);
		request.setAttribute("region", region);
		request.setAttribute("country", deptIdString);
	}
	
	
	/**
	 * 选择机房
	 */
	public ActionForward chooseRoom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		String whereStr = " where 1=1 ";
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	request.setAttribute("city1", city);
    	PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
    	String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		
		String resName = request.getParameter("resName");
		String specialty = request.getParameter("specialty");
		
		 String tempSpecialty = "";
		    if ((!(StringUtils.isEmpty(specialty))) && ((("10123160101".equals(specialty)) || ("10123160102".equals(specialty))))) {
		      tempSpecialty = specialty;
		      specialty = "1122505";
		    }
		
		String region = request.getParameter("region");
		String executeDept = request.getParameter("executeDept");
		
		if(!StringUtils.isEmpty(resName)){
			whereStr = whereStr+" and resName like '%"+resName+"%'";
		}
		if(!StringUtils.isEmpty(specialty)){
			whereStr = whereStr+" and specialty='"+specialty+"'";
		}
		if(!StringUtils.isEmpty(region)){
			whereStr = whereStr+" and city='"+region+"'";
		}
		//部门小组id
		if(!StringUtils.isEmpty(executeDept)){
			whereStr = whereStr+" and executeDept like '"+executeDept+"%'";
		}
		
    	Map map = pnrResConfiMgr.getResources(firstResult*CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE, whereStr);
    	request.setAttribute("list",map.get("result"));
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("resName", resName);
		 if ((tempSpecialty != null) && (!"".equals(tempSpecialty))){
		      specialty = tempSpecialty;
		 }
		request.setAttribute("specialty", specialty);
		request.setAttribute("region", region);
		request.setAttribute("executeDept", executeDept);
		request.setAttribute("multiple", "multiple");
		return mapping.findForward("resConfigSingleChooseList");
	}
	
	/**
	 * 条件查询图片
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward conditionSelectPhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		//获取登录人userID
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		request.setAttribute("deptId", deptId);
		String country = deptId;
	if (country.length() > 5) {
		country = country.substring(0, 5);
	}
	if ("admin".equals(userId) || "superUser".equals(userId)) {// 超级管理员
		country = "";
	}
		request.setAttribute("country", country);
		// 图片描述
		String photoDescribe = StaticMethod.nullObject2String(request
				.getParameter("photoDescribe"));
		// 地点
		String faultLocation = StaticMethod.nullObject2String(request
				.getParameter("faultLocation"));
		// 拍照时间
		String createPhotoTime = StaticMethod.nullObject2String(request
				.getParameter("createPhotoTime"));
		//拍照人
		String photoCreate = StaticMethod.nullObject2String(request
				.getParameter("photoCreate"));
		//根据登录人所述地市查询一定时间内的服务器上的图片
		IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");		
		List<PnrAndroidPhotoFile> photoList = roomDemolitionService.getPrecheckPhotoes(userId,photoDescribe,createPhotoTime,photoCreate,faultLocation);
		if (photoList != null) {
			request.setAttribute("total", photoList.size());
		} else {
			request.setAttribute("total", 0);
		}
		request.setAttribute("list", photoList);
		request.setAttribute("photoDescribe",photoDescribe);
		request.setAttribute("createPhotoTime",createPhotoTime);
		request.setAttribute("faultLocation",faultLocation);
		// 拍照人回显
		String showPhotoCreate = photoCreate;
		if (!photoCreate.equals("")) {
			showPhotoCreate = "[{id:'" + photoCreate
					+ "',nodeType:'user',categoryId:'photoCreate'}]";
		}
		request.setAttribute("photoCreate",showPhotoCreate);
		
		return mapping.findForward("openSelectPhotoView");
	}
	
	/**
	 * 计算机房和图片经纬度之间的距离 单位：m
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String calculationDistance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		String tempRoomLongitude = request.getParameter("roomLongitude");//机房经度
		String tempRoomLatitude = request.getParameter("roomLatitude");//机房纬度
		String tempPotoAvgLongitude = request.getParameter("potoAvgLongitude");//图片经度
		String tempPotoAvgLatitude = request.getParameter("potoAvgLatitude");//图片纬度
		
		double roomLongitude = Double.parseDouble(tempRoomLongitude);
		double roomLatitude = Double.parseDouble(tempRoomLatitude);
		double potoAvgLongitude = Double.parseDouble(tempPotoAvgLongitude);
		double potoAvgLatitude = Double.parseDouble(tempPotoAvgLatitude);
		
		double jieguo = GetDistance(roomLatitude, roomLongitude, potoAvgLatitude, potoAvgLongitude);
		double jieguo2 = jieguo*1000;
		long jieguo3 = Math.round(jieguo2);
		System.out.println("-----------------------两点距离为："+jieguo3+" 单位 米 ");
		
		response.getWriter().write(Long.toString(jieguo3));
		return "";
	}
	
	public static double GetDistance(double y1, double x1, double y2, double x2) {
		double radLat1 = rad(y1);
		double radLat2 = rad(y2);
		double a = radLat1 - radLat2;
		double b = rad(x1) - rad(x2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		return s;
	}
	
	/**
	 * 机房拆除--工单发起
	 */
	public ActionForward performAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		String isDraft = request.getParameter("isDraft");//保存草稿标石
//		//根据派发人的部门所属区域，确定工单的区域
//		ITawSystemDeptManager deptSys = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
//		TawSystemDept tawSystemDept = deptSys.getDeptinfobydeptid(sessionForm
//				.getDeptid(), "0");
//		String areaid = tawSystemDept.getAreaid();
//		String city = areaid;
//		if((StaticMethod.nullObject2String(areaid)).length()>0 && areaid.length()>4){
//			city = areaid.substring(0, 4);
//		}
		IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");
		String themeId = request.getParameter("themeId");
		String processInstanceId=request.getParameter("processInstanceId");//获取流程实例ID
		String tempSheetId = request.getParameter("sheetId");//获取工单编号
		String attachments = request.getParameter("sheetAccessories");//附件
		String photoesIds = request.getParameter("photoIds");//图片IDS
		
		int state = 0;// 默认为0，正常派单。
		
		RoomDemolition roomDemolition= null ;
		if (themeId != null && themeId.length() > 0) {
			roomDemolition = roomDemolitionService.find(themeId);// 如果存在就取出数据实体
		}else{
			roomDemolition=new RoomDemolition();
			roomDemolition.setSendTime(new Date());//派发时间 再次派发时候不修改派发时间
			//发起时间*
			String initiateTime = StaticMethod.nullObject2String(request
					.getParameter("initiateTime"));
		    roomDemolition.setInitiateTime(sFormat.parse(initiateTime));
			
		}
		if("true".equals(isDraft)){//如果是保存草稿状态为0  设置保存草稿时间
			state = 2;
			roomDemolition.setSaveDraftDate(new Date());
		}
		this.setRoomDemolitionByRequest(request, roomDemolition);//往MODEL存值
		
		//1.生成工单编号		
		String sheetId = "";
		if (tempSheetId != null && !tempSheetId.equals("")) {
			sheetId = tempSheetId;
		} else {
			if (processInstanceId == null || "".equals(processInstanceId)) {

				try {
					sheetId = roomDemolitionService.getSheetId(
							processDefinitionKey, userId, "B", "JFCC");

				} catch (Exception e) {
					request.setAttribute("msg", "请联系管理员，生成工单编号错误："
							+ e.toString());
					return mapping.findForward("failure");

				}
			}
		}
		roomDemolition.setSheetId(sheetId);
		
		
		FormService formService = (FormService) getBean("formService");
		IdentityService identityService = (IdentityService) getBean("identityService");
		identityService.setAuthenticatedUserId(roomDemolition.getInitiator());
		RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");
		TaskService taskService = (TaskService) getBean("taskService");

		if (processInstanceId == null || "".equals(processInstanceId)) {
			ProcessInstance processInstance = runtimeService
					.startProcessInstanceByKey(processDefinitionKey);
			processInstanceId = processInstance.getId();
		}
		
		String person="";//在工单主表中添加处理人
		String taskDefKey = "cityManageExamine";//在工单主表中添加环节KEY
		String taskDefKeyName = "市公司运维部主管审批";//在工单主表中添加环节NAME
		if(!"true".equals(isDraft)){//保存草稿不走派发流程
			//2.派发流程
			List<Task> taskList = taskService.createTaskQuery().processInstanceId(
					processInstanceId).active().list();
			String taskId = taskList.get(0).getId();
			Map<String, String> map = new HashMap<String, String>();
			map.put("cityManageChargeAudit",roomDemolition.getCityManageCharge());
			formService.submitTaskFormData(taskId, map);
			person=roomDemolition.getCityManageCharge();
		}
		
		
		//3.保存附件
			//清空该环节的附件
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		if (processInstanceId != null && !"".equals(processInstanceId)) {
			pnrTransferOfficeService.deleteAttachmentNamesByProcessInstanceIdAndStep(processInstanceId, "1");
		}
		
		if (!"".equals(attachments)) {
			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments,"1");
		}
		
		//4.保存工单表
		roomDemolition.setProcessInstanceId(processInstanceId);
		roomDemolition.setState(state);
		
		try {
			processTaskService.setTvalueOfTask(processInstanceId, person, roomDemolition, RoomDemolition.class, taskDefKey, taskDefKeyName,false);
			roomDemolitionService.save(roomDemolition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//5.保存图片
		//将工单与图片关联起来
		PnrPrecheckPhotoService pnrPrecheckPhotoService = (PnrPrecheckPhotoService) getBean("pnrPrecheckPhotoService");
		String[] photoesId = photoesIds.split(",");
		if (photoesId != null && photoesId.length > 0) {
			roomDemolitionService
					.judgeIsExitBypgohoIdAndProcessInstanceId(processInstanceId);
			for (String str : photoesId) {
				if (str != null && !"".equals(str)) {
					PnrPrecheckPhoto pnrPrecheckPhoto = new PnrPrecheckPhoto();
					pnrPrecheckPhoto.setProcessInstanceId(processInstanceId);
					pnrPrecheckPhoto.setPhotoId(str);
					pnrPrecheckPhotoService.save(pnrPrecheckPhoto);
					roomDemolitionService.setStateByPhotoId(str, "1");
				}
			}
		}
		
		//6.保存物料
		//if (processInstanceId != null && !"".equals(processInstanceId)) {
			//IPnrActMaterialService pnrActMaterialService = (IPnrActMaterialService) getBean("pnrActMaterialService");
			//pnrActMaterialService.deletePnrActMaterialsByProcessInstanceId(processInstanceId);
		//}
		//this.savePnrActMaterial(request, processInstanceId);
		
		//保存资产
		if (processInstanceId != null && !"".equals(processInstanceId)) {
			IRoomAssetsService roomAssetsService = (IRoomAssetsService) getBean("roomAssetsService");
			roomAssetsService.deleteRoomAssetsByProcessInstanceId(processInstanceId);
		}
		this.saveRoomAssets(request, processInstanceId);
		
		
		if(!"true".equals(isDraft)){//保存草稿不需要短信提醒,派发需要短信提醒
			//7.派发短信
			String messagePerson = roomDemolition.getCityManageCharge();
			String msg = TASK_MESSAGE + "；" + TASK_NO_STR + roomDemolition.getSheetId()
					+ ";" + TASK_STATION_NAME + roomDemolition.getStationName() + ";" + TASK_SEND_TIME
					+ sFormat.format(roomDemolition.getSendTime()) + "；请及时处理。" ;
			CommonUtils.sendMsg(msg, messagePerson);
		}
		
		return mapping.findForward("success");
	}
	
	/**
	 * 保存资产
	 * 
	 * @param request
	 * @param processInstanceId
	 * @throws ParseException
	 */
	private void saveRoomAssets(HttpServletRequest request,
			String processInstanceId) throws ParseException {
		IRoomAssetsRelationService roomAssetsRelationService = (IRoomAssetsRelationService) getBean("roomAssetsRelationService");
		String[] idAndExit = request.getParameterValues("idAndExit");
		if(idAndExit!=null){
			int k = 1;// 排序码
			for (int i = 0; i < idAndExit.length; i++) {
				try{
					// System.out.println(idAndExit[i]);
					String[] split = idAndExit[i].split(",");
					RoomAssetsRelation roomAssetsRelation = new RoomAssetsRelation();
					roomAssetsRelation.setProcessInstanceId(processInstanceId);
					roomAssetsRelation.setRoomAssetsId(split[0]);
					roomAssetsRelation.setExitDirection(split[1]);
					roomAssetsRelation.setOrderCode(k);
					roomAssetsRelationService.save(roomAssetsRelation);
					k++;
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}
				
			}
		}
	}
	
	
	/**
	 * 保存材料
	 * @param request
	 * @param processInstanceId
	 * @throws ParseException
	 */
	private void savePnrActMaterial(HttpServletRequest request,
			String processInstanceId) throws ParseException {
		IPnrActMaterialService pnrActMaterialService = (IPnrActMaterialService) getBean("pnrActMaterialService");
		for (int i = 1; i <= 8; i++) {
			String serialNum = StaticMethod.nullObject2String(request.getParameter("serialNum"+i)) ;
			String materialName = StaticMethod.nullObject2String(request.getParameter("materialName"+i)) ;
			String specifications = StaticMethod.nullObject2String(request.getParameter("specifications"+i)) ;
			String unitPrice = StaticMethod.nullObject2String(request.getParameter("unitPrice"+i)) ;
			String materialNum = StaticMethod.nullObject2String(request.getParameter("number"+i)) ;
			String amount = StaticMethod.nullObject2String(request.getParameter("amount"+i)) ;
			
			if (!materialName.equals("") && !specifications.equals("")
					&& !amount.equals("")) {
				PnrActMaterial pnrActMaterial=new PnrActMaterial();
				pnrActMaterial.setSerialNum(serialNum);
				pnrActMaterial.setMaterialName(materialName);
				pnrActMaterial.setSpecifications(specifications);
				pnrActMaterial.setUnitPrice(unitPrice);
				pnrActMaterial.setMaterialNum(materialNum);
				pnrActMaterial.setAmount(amount);
				pnrActMaterial.setProcessInstanceId(processInstanceId);
				pnrActMaterialService.save(pnrActMaterial);
			} else {
				continue;
			}
		}
	}
	
	/**
	 * 往Model里设置值
	 * @param request
	 * @param roomDemolition
	 * @throws ParseException
	 */
	private void setRoomDemolitionByRequest(HttpServletRequest request,
			RoomDemolition roomDemolition) throws ParseException {
		//工单名称*
		String theme = StaticMethod.nullObject2String(request
				.getParameter("title"));
		//工单发起人*
		String initiator = StaticMethod.nullObject2String(request
				.getParameter("initiator"));
		//发起人部门*
		String dept = StaticMethod.nullObject2String(request
				.getParameter("dept"));
		//地市*
		String city = StaticMethod.nullObject2String(request
				.getParameter("city"));
		//区县*
		String country = StaticMethod.nullObject2String(request
				.getParameter("country"));
		//机房类型*
		String stationType = StaticMethod.nullObject2String(request
				.getParameter("stationType"));
		String stationId="";
		String stationName="";
		String stationLevelId="";
		String stationLevel="";
		
		if("1122501".equals(stationType)||"1122505".equals(stationType)||"10123160101".equals(stationType)||"10123160102".equals(stationType)){
			//机房ID*
			 stationId = StaticMethod.nullObject2String(request
					.getParameter("stationId"));
			//机房名称*
			 stationName = StaticMethod.nullObject2String(request
					.getParameter("stationName"));
			//机房级别ID*
			 stationLevelId = StaticMethod.nullObject2String(request
					.getParameter("stationLevelId"));
			//机房级别*
			 stationLevel = StaticMethod.nullObject2String(request
					.getParameter("stationLevel"));
		}else{
			//机房名称*
			 stationName = StaticMethod.nullObject2String(request
					.getParameter("tempStationName"));
			//机房级别*
			 stationLevel = StaticMethod.nullObject2String(request
					.getParameter("tempStationLevel"));
		}
		//机房面积
		String stationArea = StaticMethod.nullObject2String(request
				.getParameter("stationArea"));
		//机房产权*
		String stationEquity = StaticMethod.nullObject2String(request
				.getParameter("stationEquity"));
		//年租金（元）*
		String annualRent = StaticMethod.nullObject2String(request
				.getParameter("annualRent"));
		//租用日期*
		String hireDate = StaticMethod.nullObject2String(request
				.getParameter("hireDate"));
		//合同到期时间
		String contractTime = StaticMethod.nullObject2String(request
				.getParameter("contractTime"));
		//光缆纤芯数*
		String opticcableNum = StaticMethod.nullObject2String(request
				.getParameter("opticcableNum"));
		//光缆改造方式*
		String opticcableWay = StaticMethod.nullObject2String(request
				.getParameter("opticcableWay"));
		//设备机架数*
		String equipmentRackNum = StaticMethod.nullObject2String(request
				.getParameter("equipmentRackNum"));
		//材料费用*
		String materialCost = StaticMethod.nullObject2String(request
				.getParameter("materialCost"));
		//对应能耗系统机房名称*
		String energyStationName = StaticMethod.nullObject2String(request
				.getParameter("energyStationName"));
		//对应能耗系统机房编号*
		String energyStationCode = StaticMethod.nullObject2String(request
				.getParameter("energyStationCode"));
		//对应能耗系统是否关站*
		String energyIsstation = StaticMethod.nullObject2String(request
				.getParameter("energyIsstation"));
		
		//事前照片与巡检经纬度比对（允许误差范围1千米）
		String comparisonResults = StaticMethod.nullObject2String(request
				.getParameter("comparisonResults"));
		//事前照片*
		String photoIds = StaticMethod.nullObject2String(request
				.getParameter("photoIds"));
		//描述
		String description = StaticMethod.nullObject2String(request
				.getParameter("description"));
		//附件（方案）* 另外处理 新增附件
		String attachments = request.getParameter("sheetAccessories");
		int attachmentsNum = 0;
		if (attachments != null && attachments.length() > 0) {

			attachmentsNum = attachments.split(",").length;
		}		
		//自动计算的合计
		String amountTotal = StaticMethod.nullObject2String(request
				.getParameter("amountTotal"));
		//机房经度
		String roomLongitude = StaticMethod.nullObject2String(request
				.getParameter("roomLongitude"));
		//机房纬度
		String roomLatitude = StaticMethod.nullObject2String(request
				.getParameter("roomLatitude"));
		//照片平均经度
		String potoAvgLongitude = StaticMethod.nullObject2String(request
				.getParameter("potoAvgLongitude"));
		//照片平均纬度
		String potoAvgLatitude = StaticMethod.nullObject2String(request
				.getParameter("potoAvgLatitude"));
		//距离差
		String distance = StaticMethod.nullObject2String(request
				.getParameter("distance"));
		//批次
		String pici = StaticMethod.nullObject2String(request
				.getParameter("pici"));
		
		//往Model里存值
		roomDemolition.setTheme(theme);
		roomDemolition.setDept(dept);
		roomDemolition.setCity(city);
		roomDemolition.setCountry(country);
		roomDemolition.setStationType(stationType);
		roomDemolition.setStationId(stationId);
		roomDemolition.setStationName(stationName);
		roomDemolition.setStationLevelId(stationLevelId);
		roomDemolition.setStationLevel(stationLevel);
		roomDemolition.setStationArea(stationArea);
		roomDemolition.setStationEquity(stationEquity);
		roomDemolition.setAnnualRent(annualRent);
		roomDemolition.setHireDate(sFormat.parse(hireDate));
		if(!contractTime.equals("")){
			roomDemolition.setContractTime(sFormat.parse(contractTime));
		}
		roomDemolition.setOpticcableNum(opticcableNum);
		roomDemolition.setOpticcableWay(opticcableWay);
		roomDemolition.setEquipmentRackNum(equipmentRackNum);
		roomDemolition.setMaterialCost(materialCost);
		roomDemolition.setEnergyStationName(energyStationName);
		roomDemolition.setEnergyStationCode(energyStationCode);
		roomDemolition.setEnergyIsstation(energyIsstation);
			
		//事前照片与巡检经纬度比对（允许误差范围1千米）
		roomDemolition.setComparisonResults(comparisonResults);
		//事前照片IDS* 先不存
		roomDemolition.setDescription(description);
		roomDemolition.setAttachmentsNum(attachmentsNum);
		//自动计算的合计
		roomDemolition.setAmountTotal(amountTotal);
		//机房经度
		roomDemolition.setRoomLongitude(roomLongitude);
		//机房纬度
		roomDemolition.setRoomLatitude(roomLatitude);
		//照片平均经度
		roomDemolition.setPotoAvgLongitude(potoAvgLongitude);
		//照片平均纬度
		roomDemolition.setPotoAvgLatitude(potoAvgLatitude);
		//距离差
		roomDemolition.setDistance(distance);
		//批次
		roomDemolition.setPici(pici);
		
		//驳回标识
		roomDemolition.setRollbackFlag("0");
		
		//生成环节的处理人
		roomDemolition.setCreateWork(initiator);//工单发起人
		roomDemolition.setInitiator(initiator);//区县维护中心
		
		//区县维护中心主管
		
		
		roomDemolition.setCountryCharge(initiator);
		//市公司运维部主管
		IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");
		PrecheckShipModel precheckShipModel = roomDemolitionService.getPrecheckShipModelBycountryCharge(initiator);
		roomDemolition.setCityManageCharge(precheckShipModel.getCityManageCharge());
		//省公司运维部主管
		IPnrTransferPrecheckService pnrTransferPrecheckService = (IPnrTransferPrecheckService) getBean("pnrTransferPrecheckService");
		List<Map> provinceManageChargeList = pnrTransferPrecheckService.getSGSByCountryCSJ(initiator,precheckRoleModel.getRoomDemolitionProvinceManage());
		String provinceManageCharge = "superUser";
		if(provinceManageChargeList!=null && provinceManageChargeList.size()>0){
			if(provinceManageChargeList.get(0).get("USERID")!=null && !"".equals(provinceManageChargeList.get(0).get("USERID"))){
				provinceManageCharge = provinceManageChargeList.get(0).get("USERID").toString();
			}
		}
		roomDemolition.setProvinceManageCharge(provinceManageCharge);
	}
	
	
	
	
	/**
	 * 预检预修工单--工单保存
	  * @author wangyue
	  * @title: performSave
	  * @date Feb 4, 2015 10:38:49 AM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward performSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		// 默认为0，正常派单。

		int state = 0;

		PnrTransferOffice pnrTransferOffice = new PnrTransferOffice();
		// setTransferOfficeByRequest(request, pnrTransferOffice);
		String themeId = pnrTransferOffice.getId();
		String attachments = request.getParameter("sheetAccessories");
		String processInstanceId = pnrTransferOffice.getProcessInstanceId();
		String initiator = pnrTransferOffice.getInitiator();
		// 获取工单流程类型
		String processType = pnrTransferOffice.getThemeInterface();
		// 是否调用接口标志
		String viewFlag = StaticMethod.nullObject2String(request
				.getParameter("viewFlag"));

		IdentityService identityService = (IdentityService) getBean("identityService");
		identityService.setAuthenticatedUserId(initiator);
		RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");
		String processDefinitionKey = "";
		if (processInstanceId == null || "".equals(processInstanceId)) {
			ProcessInstance processInstance = runtimeService
					.startProcessInstanceByKey(processDefinitionKey);
			processInstanceId = processInstance.getId();
		}

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		if (themeId != null && themeId.length() > 0) {
			pnrTransferOffice = pnrTransferOfficeService.find(themeId);// 如果存在就取出数据实体
			// setTransferOfficeByRequest(request, pnrTransferOffice);//
			// 如果存在就再将变动的内容重置下！
		}

		pnrTransferOffice.setProcessInstanceId(processInstanceId);
		pnrTransferOffice.setState(state);

		// attachment--start
		if (!"".equals(attachments)) {
			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments, "1");
		}
		// attachment--end
		pnrTransferOfficeService.save(pnrTransferOffice);
		// 判断是否调用接口
		if (viewFlag != null && "newJob".equals(viewFlag)) {// 属于新建页面，需要调用预算接口
			System.out.println("需要调用预算接口！！！！！！！！");
		}
		return mapping.findForward("success");
	}
	
	/**
	  * 机房拆除流程--待回复查询（待办工单除外）
	  * @author wangyue
	  * @title: listBacklog
	  * @date Feb 6, 2015 3:28:21 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward listBacklog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pageSize = CommonUtils.PAGE_SIZE;
		String tempPageSize = request.getParameter("pagesize");
		if (tempPageSize != null && !"".equals(tempPageSize)) {
			pageSize = Integer.parseInt(tempPageSize);
		}
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();

		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		String status = request.getParameter("status");
		
		String region = request.getParameter("region");//地市
		String country = request.getParameter("country");
		//String lineType = request.getParameter("lineType");
		//String provName = request.getParameter("provName");//工单类型
		//String precheckFlag = request.getParameter("precheckFlag");
		String mainSceneId = request.getParameter("mainSceneId");//场景
		
		
		 ConditionQueryModel conditionQueryModel =new  ConditionQueryModel();
		 conditionQueryModel.setSendStartTime(sendStartTime);
		 conditionQueryModel.setSendEndTime(sendEndTime);
		 conditionQueryModel.setWorkNumber(wsNum);
		 conditionQueryModel.setTheme(wsTitle);
		 conditionQueryModel.setStatus(status);
		 conditionQueryModel.setCity(region);
		 conditionQueryModel.setCountry(country);
		 //conditionQueryModel.setLineType(lineType);
		 //conditionQueryModel.setWorkOrderType(provName);
		 //conditionQueryModel.setPrecheckFlag(precheckFlag);
		 conditionQueryModel.setMainSceneId(mainSceneId);
		 conditionQueryModel.setPageSize(Integer.toString(pageSize));
		
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	
    	IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");
		// 工单管理-传输局工单-预检预修工单-待回复工单 集合条数
		int total = 0;
		try {
			total = roomDemolitionService.getNewPrecheckCount(userId,
					"backlog", processDefinitionKey, conditionQueryModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 工单管理-传输局工单-预检预修工单-待回复工单 集合
		List<RoomDemolitionModel> rPnrTransferList = null;
		try {
			rPnrTransferList = roomDemolitionService
					.getTransferNewPrecheckList(userId, "backlog", processDefinitionKey,
							conditionQueryModel, firstResult, endResult,
							pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String batchApprovalFlag="no";
		System.out.println("--------------status="+status);
		if(status!=null){
//			if (status.equals("workOrderCheck") || status.equals("cityLineExamine")
//					|| status.equals("cityLineDirectorAudit")
//					|| status.equals("cityManageExamine")
//					|| status.equals("cityManageDirectorAudit")
//					|| status.equals("cityViceAudit")
//					|| status.equals("provinceLineExamine")
//					|| status.equals("provinceLineViceAudit")
//					|| status.equals("provinceManageExamine")
//					|| status.equals("provinceManageViceAudit")) {
//				batchApprovalFlag="yes";
//			}
			
			if (status.equals("provinceLineExamine")
					|| status.equals("provinceLineViceAudit")
					|| status.equals("provinceManageExamine")
					|| status.equals("provinceManageViceAudit")) {
				batchApprovalFlag = "yes";
			} 
			
		}

		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
		request.setAttribute("wsStatus", status);
		//request.setAttribute("provName", provName);
		//request.setAttribute("lineType", lineType);
		request.setAttribute("country", country);
		request.setAttribute("region", region);
		//request.setAttribute("precheckFlag", precheckFlag);
		request.setAttribute("batchApprovalFlag", batchApprovalFlag);
		//登录人，为了在页面区分是否是省级别的四个工作人员（是否显示批量处理按钮）
		request.setAttribute("loginUserId", userId);
		//返回查询条件
		request.setAttribute("condition", this.jointListBacklogCondition(conditionQueryModel));
		return mapping.findForward("backlogList");
	}
	
	//拼接待回复列表查询条件
	private String jointListBacklogCondition(ConditionQueryModel conditionQueryModel){
		String condition="";
		//派单开始时间
		condition+="&sheetAcceptLimit="+StaticMethod.nullObject2String(conditionQueryModel.getSendStartTime());
		//派单结束时间
		condition+="&sheetCompleteLimit="+StaticMethod.nullObject2String(conditionQueryModel.getSendEndTime());
		//工单号
		condition+="&wsNum="+StaticMethod.nullObject2String(conditionQueryModel.getWorkNumber());
		//工单名称
		condition+="&wsTitle="+StaticMethod.nullObject2String(conditionQueryModel.getTheme());
		//地市
		condition+="&region="+StaticMethod.nullObject2String(conditionQueryModel.getCity());
		//区县
		condition+="&country="+StaticMethod.nullObject2String(conditionQueryModel.getCountry());
		//线路属性
		//condition+="&lineType="+StaticMethod.nullObject2String(conditionQueryModel.getLineType());
		//工单类型
		//condition+="&provName="+StaticMethod.nullObject2String(conditionQueryModel.getWorkOrderType());
		//场景
		//condition+="&mainSceneId="+StaticMethod.nullObject2String(conditionQueryModel.getMainSceneId());
		//工单状态
		condition+="&status="+StaticMethod.nullObject2String(conditionQueryModel.getStatus());
		//工单类别
		//condition+="&precheckFlag="+StaticMethod.nullObject2String(conditionQueryModel.getPrecheckFlag());
		
		//每页显示条数
		if(conditionQueryModel.getPageSize()==null||"".equals(conditionQueryModel.getPageSize())){
			condition+="&pagesize=20";//默认条数20
		}else{
			condition+="&pagesize="+StaticMethod.nullObject2String(conditionQueryModel.getPageSize());
		}
		
		System.out.println("-------------机房拆除流程 拼接待回复列表查询条件字符串="+condition);
		
		return condition;
	}
	
	//获取request传递的查询条件封装到ConditionQueryModel中
	private ConditionQueryModel doRequestToConditionQueryModel(HttpServletRequest request){
		//拼接查询条件
		String sendStartTime = StaticMethod.nullObject2String(request.getParameter("sheetAcceptLimit"));
		String sendEndTime = StaticMethod.nullObject2String(request.getParameter("sheetCompleteLimit"));
		String wsNum = StaticMethod.nullObject2String(request.getParameter("wsNum"));
		String wsTitle = StaticMethod.nullObject2String(request.getParameter("wsTitle"));
		String status = StaticMethod.nullObject2String(request.getParameter("status"));

		String region = StaticMethod.nullObject2String(request.getParameter("region"));// 地市
		String country = StaticMethod.nullObject2String(request.getParameter("country"));
		//String lineType = StaticMethod.nullObject2String(request.getParameter("lineType"));
		//String precheckFlag = StaticMethod.nullObject2String(request.getParameter("precheckFlag"));
		//String provName = StaticMethod.nullObject2String(request.getParameter("provName"));//工单类型
		
		//String mainSceneId = StaticMethod.nullObject2String(request.getParameter("mainSceneId"));//场景
		String pageSize = StaticMethod.nullObject2String(request.getParameter("pagesize"));//每页显示条数

		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setSendStartTime(sendStartTime);
		conditionQueryModel.setSendEndTime(sendEndTime);
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		conditionQueryModel.setStatus(status);
		conditionQueryModel.setCity(region);
		conditionQueryModel.setCountry(country);
		//conditionQueryModel.setLineType(lineType);
		//conditionQueryModel.setPrecheckFlag(precheckFlag);
		//conditionQueryModel.setWorkOrderType(provName);
		//conditionQueryModel.setMainSceneId(mainSceneId);
		conditionQueryModel.setPageSize(pageSize);
		return conditionQueryModel;
	}
	/**
	 *  回复
	  * @author wangyue
	  * @title: todo
	  * @date Feb 9, 2015 9:32:45 AM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward todo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//拼接查询条件
		ConditionQueryModel conditionQueryModel = this.doRequestToConditionQueryModel(request);
		request.setAttribute("condition", this.jointListBacklogCondition(conditionQueryModel));
		
		this.getRegionalInfo(request);
		
		String taskId = request.getParameter("taskId");
		if("".equals(taskId)||"-".equals(taskId)){
			request.setAttribute("msg", "错误编号：sd-taskId-004;工单有问题,请联系管理员");
			return mapping.findForward("failure");
		}
		
		TaskService taskService = (TaskService) getBean("taskService");
		List<Task> taskList = taskService.createTaskQuery().taskId(taskId)
				.list();
		request.setAttribute("taskId", taskId);//------------回传 taskId 任务ID

		if (taskList != null && taskList.size() != 0) {
			Task task = taskList.get(0);
			String processInstanceId = task.getProcessInstanceId();
			request.setAttribute("processInstanceId", processInstanceId);//------------回传 processInstanceId 流程实例ID
			request.setAttribute("auditor", task.getAssignee());//------------回传 auditor 当前任务操作人
			IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			
			SearchResult t = roomDemolitionService.searchAndCount(search);
			List<RoomDemolition> list = t.getResult();
			// 回复message
			RoomDemolition roomDemolition =list.get(0);
			request.setAttribute("roomDemolition", roomDemolition);//------------回传 roomDemolition 工单对象
			
			IRoomDemolitionHandleService roomDemolitionHandleService = (IRoomDemolitionHandleService) getBean("roomDemolitionHandleService");
			//获取审批信息
			Map<String, Object> approveMap = new HashMap<String, Object>();
			List<RoomDemolitionHandle> approveHandlelist = null;
			int approveHandleSize =0;
			Search approveSearch = new Search();
			approveSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			approveSearch.addFilterEqual("operation", "01");
			approveSearch.addSort("checkTime", true);// 按回复时间排序
			approveMap=getHandleList(approveMap,roomDemolitionHandleService, approveSearch, approveHandlelist,approveHandleSize);
			
			//获取审批驳回信息
			Map<String, Object> approveBackMap = new HashMap<String, Object>();
			List<RoomDemolitionHandle> approveBackHandlelist = null;
			int approveBackHandleSize =0;	
			Search approveBackSearch = new Search();
			approveBackSearch.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			approveBackSearch.addFilterEqual("operation", "02");
			approveBackSearch.addSort("checkTime", true);// 按回复时间排序
			approveBackMap=getHandleList(approveBackMap,roomDemolitionHandleService, approveBackSearch, approveBackHandlelist,approveBackHandleSize);
			
			//回显 审批消息
			request.setAttribute("approveListsize", approveMap.get("size"));
			request.setAttribute("approveHandleList", approveMap.get("list"));
			
			//回显 驳回信息
			request.setAttribute("approveBackListsize", approveBackMap.get("size"));
			request.setAttribute("approveBackList", approveBackMap.get("list"));
			
			//附件处理
			PnrTransferOffice ticket = new PnrTransferOffice();
			String userTaskId=task.getTaskDefinitionKey();
			String step="";//环节所在步骤的值
			if(userTaskId.equals("need")){//工单发起
				step="1";
			}else if(userTaskId.equals("cityManageExamine")){//市公司运维部主管审批
				step="2";
			}else if(userTaskId.equals("provinceManageExamine")){//省公司运维部主管审批
				step="3";
			}else if(userTaskId.equals("worker")){//区县维护中心主管回单
				step="4";
			}else if(userTaskId.equals("orderAudit")){//市公司运维部主管验收
				step="5";
			}else if(userTaskId.equals("daiweiAudit")){//省公司运维部主管审核
				step="6";
			}else if(userTaskId.equals("manualArchive")){//省公司运维部主管手动归档
				step="7";
			}

			//对应环节页面的附件回显
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			String sheetAccessories = pnrTransferOfficeService
					.getAttachmentNamesByProcessInstanceIdAndUserTaskId(processInstanceId,step);
			ticket.setSheetAccessories(sheetAccessories);
			request.setAttribute("sheetMain", ticket);
			
			//附件列表回显
			List<TawCommonsAccessoriesForm> accessoriesList  = pnrTransferOfficeService.getRoomDemolitionAccessoriesList(processInstanceId,"");
			request.setAttribute("sheetAccessories", accessoriesList);
			
			if (task.getTaskDefinitionKey().equalsIgnoreCase("need")) {//区县维护中心主管工单发起				
				String[] photoList = roomDemolitionService.getPhotoByProcessInstanceId(processInstanceId);
				//遍历photoID字符串，将图片关联标志state设置成0
				if(photoList[0]!=null&&!"".equals(photoList[0])){
					String[] photoesId = photoList[0].split(",");
					if(photoesId!=null &&photoesId.length>0){
						for(String str :photoesId){
							if(str!=null && !"".equals(str)){
								roomDemolitionService.setStateByPhotoId(str, "0");
							}
						}
					}
				}
				//回显的图片id串
				request.setAttribute("photoIds", photoList[0]);
				//回显的图片详细信息
				request.setAttribute("photoList", photoList[1]);
				request.setAttribute("initiateTime", sFormat.format(roomDemolition.getSendTime()));//回显发起时间
				request.setAttribute("comparisonResults","yes");//事前照片与巡检经纬度比对（允许误差范围1千米）
				//回显材料
				//this.showPnractMaterial(request,processInstanceId);
				
				//回显资产
				IRoomAssetsService roomAssetsService = (IRoomAssetsService) getBean("roomAssetsService");
				String[] roomAssetsList = roomAssetsService.getRoomAssetsByProcessInstanceId(processInstanceId);
				//回显资产id串
				request.setAttribute("assetIds", roomAssetsList[0]);
				//回显资产详细信息
				request.setAttribute("assetList", roomAssetsList[1]);
				
				return mapping.findForward("new");
			} else if (task.getTaskDefinitionKey().equalsIgnoreCase("cityManageExamine")) {//市公司运维部主管审批
				
				return mapping.findForward("cityManageExamine");
				
			}else if (task.getTaskDefinitionKey().equalsIgnoreCase("provinceManageExamine")) {//省公司运维部主管审批
				
				return mapping.findForward("provinceManageExamine");
			}else if (task.getTaskDefinitionKey().equalsIgnoreCase("worker")) {//区县维护中心主管回单 施工队
				
				return mapping.findForward("transferHandle");
			}else if (task.getTaskDefinitionKey().equalsIgnoreCase("orderAudit")) {//市公司运维部主管验收
				
				return mapping.findForward("orderAudit");
			}else if (task.getTaskDefinitionKey().equalsIgnoreCase("daiweiAudit")) {//省公司运维部主管审核
			
				return mapping.findForward("daiweiAudit");
			}else if (task.getTaskDefinitionKey().equalsIgnoreCase("manualArchive")) {//省公司运维部主管手动归档
				
				return mapping.findForward("manualArchive");
			}
		}

		return mapping.findForward("error");
	}
	
	private void showPnractMaterial(HttpServletRequest request,
			String processInstanceId) throws ParseException {
		IPnrActMaterialService pnrActMaterialService = (IPnrActMaterialService) getBean("pnrActMaterialService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		search.addSortAsc("serialNum");// 按回复时间排序
		SearchResult t = pnrActMaterialService.searchAndCount(search);
		List<PnrActMaterial> list = t.getResult();
		int j = 1;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				PnrActMaterial pnrActMaterial = list.get(i);
				request.setAttribute("materialName" + j, pnrActMaterial
						.getMaterialName());
				request.setAttribute("specifications" + j, pnrActMaterial
						.getSpecifications());
				request.setAttribute("unitPrice" + j, pnrActMaterial
						.getUnitPrice());
				request.setAttribute("number" + j, pnrActMaterial
						.getMaterialNum());
				request.setAttribute("amount" + j, pnrActMaterial.getAmount());
				j++;
			}
		}
	}
	
	
	/**
	 * 预检预修工单--普通审核通过
	  * @author wangyue
	  * @title: transferProgram
	  * @date Feb 9, 2015 10:40:03 AM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward transferProgram(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		// 发短信
		String processInstanceId = "";
		boolean isSend = true;

		String userId = sessionform.getUserid();
		// 工单ID
		String themeId = request.getParameter("titleId");
		// 工单号
		processInstanceId = request.getParameter("processInstanceId");
		// 工单主题
		String theme = request.getParameter("title");
		// 审批时间
		Date createTime = new Date();
		// 简要描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("dealPerformer2"));
		// 制定人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("assignee"));
		// 制定人
		String nextPerson = StaticMethod.nullObject2String(request
				.getParameter("nextPerson"));
		
		String taskId = request.getParameter("taskId");
		//当前所处环节
		String linkName = request.getParameter("linkName");
		
		String step = request.getParameter("step");
		
		String handleId = request.getParameter("handleId");
		
		String directList = request.getParameter("directList");//区分处理完毕后跳转的页面
		
		// 故障是否恢复(施工队环节特有)
		String isRecover = request.getParameter("isRecover");


	//	IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IRoomDemolitionHandleService roomDemolitionHandleService = (IRoomDemolitionHandleService) getBean("roomDemolitionHandleService");
		RoomDemolitionHandle entity = null;
		if (handleId != null && !handleId.equals("")) {
			Search search = new Search();
			search.addFilterEqual("id", handleId);
			SearchResult t = roomDemolitionHandleService.searchAndCount(search);
			List<RoomDemolitionHandle> list = t.getResult();
			entity = list.get(0);
			if("worker".equals(linkName)){
				entity.setIsRecover(isRecover);
			}
			entity.setHandleDescription(mainRemark);
			entity.setDoPerson(auditor);
		} else {
			entity = new RoomDemolitionHandle();
			entity.setThemeId(themeId);
			entity.setTheme(theme);
			entity.setCheckTime(createTime);
			entity.setHandleDescription(mainRemark);
			entity.setAuditor(userId);
			entity.setProcessInstanceId(processInstanceId);
			entity.setDoPerson(auditor);
			entity.setLinkName(linkName);
			if("worker".equals(linkName)){
				entity.setIsRecover(isRecover);
				//entity.setOperation("04");
			}
			entity.setOperation("01");
		}
		roomDemolitionHandleService.save(entity);
		
		// 用于更新时间字段或发短信用
		//IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<RoomDemolition> pnrTicketList = roomDemolitionService
				.search(search);
		RoomDemolition pnrTicket = null;

		FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
			}
		}
		formService.submitTaskFormData(taskId, map);
		
		if (pnrTicketList != null && pnrTicketList.size() > 0) {
			// 设置回复时间问题
			pnrTicket = pnrTicketList.get(0);
			//将被处理的工单驳回标志置成0，代表该工单不是驳回工单
			pnrTicket.setRollbackFlag("0");
			//pnrTicket.setState(0);
			if("provinceManageExamine".equals(linkName)){
				pnrTicket.setProvinceManageExamineTime(new Date());
			}
			if("orderAudit".equals(linkName)){
				pnrTicket.setOrderAuditTime(new Date());
			}
			//在工单主表中添加流程信息
			processTaskService.setTvalueOfTask(processInstanceId, nextPerson, pnrTicket, RoomDemolition.class, null, null,false);
			roomDemolitionService.save(pnrTicket);
		}
		
		//施工队环节有附件
		if("worker".equals(linkName)){
			//附件
			String attachments = request.getParameter(linkName);
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			//清空该环节的附件
			pnrTransferOfficeService.deleteAttachmentNamesByProcessInstanceIdAndStep(processInstanceId, step);
			
			if (!"".equals(attachments)) {
				pnrTransferOfficeService.saveAttachment(processInstanceId,
						attachments,step);
			}
			
		}
		
		//跳转页面 check
		 String  goPage ="check";
		if("waitWorkOrderList".equals(directList)){
			goPage ="waitWorkOrderList";
		}
		request.setAttribute("success", goPage);

		// 发短信
		if (isSend) {
			String msg = TASK_MESSAGE + "；" + TASK_NO_STR + pnrTicket.getSheetId()
			+ ";" + TASK_STATION_NAME + pnrTicket.getStationName() + ";" + TASK_SEND_TIME
			+ sFormat.format(pnrTicket.getSendTime()) + "；请及时处理。" ;
			CommonUtils.sendMsg(msg, nextPerson);
		}
		request.setAttribute("condition", request.getParameter("condition"));
		return mapping.findForward("showSuccess");
	}
	
	/**
	 * 预检预修工单--市运维主任审批
	  * @author wangyue
	  * @title: cityManageDirectorAudit
	  * @date Feb 9, 2015 5:30:00 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward cityManageDirectorAudit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 发短信
		String processInstanceId = "";
		boolean isSend = true;
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		// 工单ID
		String themeId = request.getParameter("titleId");
		// 工单号
		processInstanceId = request.getParameter("processInstanceId");
		// 工单主题
		String theme = request.getParameter("title");
		// 审批时间
		Date createTime = new Date();
		// 简要描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("dealPerformer2"));
		// 制定人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("assignee"));
		
		String taskId = request.getParameter("taskId");
		//当前所处环节
		String linkName = request.getParameter("linkName");
		//附件
		String attachments = request.getParameter("cityManageDirector");
		
		String step = request.getParameter("step");
		
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();

		entity.setThemeId(themeId);
		entity.setTheme(theme);
		entity.setCheckTime(createTime);
		entity.setHandleDescription(mainRemark);
		entity.setAuditor(userId);
		entity.setProcessInstanceId(processInstanceId);
		entity.setDoPerson(auditor);
		entity.setLinkName(linkName);
		entity.setOperation("01");
		transferHandleService.save(entity);
		
		// 用于更新时间字段或发短信用
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTicket = null;
		if (pnrTicketList != null && pnrTicketList.size() > 0) {
			// 设置回复时间问题
			pnrTicket = pnrTicketList.get(0);
			//将驳回标志置成0，代表该工单不是驳回工单
			pnrTicket.setRollbackFlag("0");
		}
		
		//清空该环节的附件
		pnrTransferOfficeService.deleteAttachmentNamesByProcessInstanceIdAndStep(processInstanceId, step);
		// attachment--start
		if (!"".equals(attachments)) {
			
			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments,step);
		}
		// attachment--end
		//在此判断金额大小，根据不同的金额走不同的流程
		//工单发起填写的预算金额
		double projectAmount = 0.0;
		if (pnrTicketList != null && pnrTicketList.size() > 0) {
			pnrTicket = pnrTicketList.get(0);
			projectAmount = pnrTicket.getProjectAmount()==null?0.0: pnrTicket.getProjectAmount();
		}
		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();
		String firstMoneyLimitDicId = precheckRoleModel.getFirstMoneyLimitDicId();
		String secondMoneyLimitDicId = precheckRoleModel.getSecondMoneyLimitDicId();
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		
		TawSystemDictType dictypeFirst = mgr.getDictByDictId(firstMoneyLimitDicId);
		TawSystemDictType dictypeSecond = mgr.getDictByDictId(secondMoneyLimitDicId);
		double doubleFirst = 0L;
		double doubleSecond = 0L;
		
		if(dictypeFirst != null && StaticMethod.getFloatValue(dictypeFirst.getDictRemark())>-1){
			doubleFirst = Double.parseDouble(dictypeFirst.getDictRemark());
		}else{
			request.setAttribute("msg","市运维主任审批下-金额限制，有问题；请联系管理员");
			return mapping.findForward("failure");				
		}
		if(dictypeSecond != null && StaticMethod.getFloatValue(dictypeSecond.getDictRemark())>-1){
			doubleSecond = Double.parseDouble(dictypeSecond.getDictRemark());
		}else{
			request.setAttribute("msg","市公司副总下-金额限制，有问题；请联系管理员");
			return mapping.findForward("failure");				
		}
		String nextPerson = "";
		if(projectAmount>doubleFirst){//金额大于一千--走市公司副总审核
			map.put("cityManageDirectorState", "city");
			map.put("nextTaskAssignee", pnrTicket.getCityGS());
			nextPerson = pnrTicket.getCityGS();
			formService.submitTaskFormData(taskId, map);
			pnrTicket.setState(0);
		}else if(projectAmount>doubleSecond){//走省线路主管审核
			map.put("cityManageDirectorState", "province");
			map.put("nextTaskAssignee", pnrTicket.getProvinceLineCharge());
			nextPerson = pnrTicket.getProvinceLineCharge();
			formService.submitTaskFormData(taskId, map);
			pnrTicket.setState(0);
		}else if(projectAmount<=doubleFirst && projectAmount<=doubleSecond){//调用商城接口,流程停留在当前环节
			pnrTicket.setState(8);
			isSend = false;
		}
		pnrTransferOfficeService.save(pnrTicket);
		if(isSend){
			
			String	msg = TASK_MESSAGE+"  "+TASK_NO_STR+processInstanceId+","+TASK_TITLE_STR+theme+","+TASK_WORKORDERDEGREE+
			getDictNameByDictid(pnrTicket.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTicket.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTicket.getSubTypeName()+"。";
			CommonUtils.sendMsg(msg, nextPerson);
		}
		return mapping.findForward("showSuccess");
	}
	/**
	 * 预检预修工单--市公司副总审核
	  * @author wangyue
	  * @title: cityViceAudit
	  * @date Feb 9, 2015 5:29:17 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward cityViceAudit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 发短信
		String processInstanceId = "";
		boolean isSend = true;
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		// 工单ID
		String themeId = request.getParameter("titleId");
		// 工单号
		processInstanceId = request.getParameter("processInstanceId");
		// 工单主题
		String theme = request.getParameter("title");
		// 审批时间
		Date createTime = new Date();
		// 简要描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("dealPerformer2"));
		// 制定人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("assignee"));
		
		String taskId = request.getParameter("taskId");
		//当前所处环节
		String linkName = request.getParameter("linkName");
		//附件
		String attachments = request.getParameter(linkName);
		
		String step = request.getParameter("step");
		
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
		
		entity.setThemeId(themeId);
		entity.setTheme(theme);
		entity.setCheckTime(createTime);
		entity.setHandleDescription(mainRemark);
		entity.setAuditor(userId);
		entity.setProcessInstanceId(processInstanceId);
		entity.setDoPerson(auditor);
		entity.setLinkName(linkName);
		entity.setOperation("01");
		transferHandleService.save(entity);
		
		// 用于更新时间字段或发短信用
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
		.search(search);
		PnrTransferOffice pnrTicket = null;
		if (pnrTicketList != null && pnrTicketList.size() > 0) {
			// 设置回复时间问题
			pnrTicket = pnrTicketList.get(0);
			//将驳回标志置成0，代表该工单不是驳回工单
			pnrTicket.setRollbackFlag("0");
		}
		
		// 清空该环节的附件
		pnrTransferOfficeService.deleteAttachmentNamesByProcessInstanceIdAndStep(processInstanceId, step);
		// attachment--start
		if (!"".equals(attachments)) {
			
			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments,step);
		}
		// attachment--end
		//工单发起填写的预算金额
		double projectAmount = 0.0;
		if (pnrTicketList != null && pnrTicketList.size() > 0) {
			pnrTicket = pnrTicketList.get(0);
			projectAmount = pnrTicket.getProjectAmount()==null?0.0: pnrTicket.getProjectAmount();
		}
		//在此判断金额大小，根据不同的金额走不同的流程
		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();
		String secondMoneyLimitDicId = precheckRoleModel.getSecondMoneyLimitDicId();
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		
		TawSystemDictType dictypeSecond = mgr.getDictByDictId(secondMoneyLimitDicId);
		double doubleSecond = 0L;
		
		if(dictypeSecond != null && StaticMethod.getFloatValue(dictypeSecond.getDictRemark())>-1){
			doubleSecond = Double.parseDouble(dictypeSecond.getDictRemark());
		}else{
			request.setAttribute("msg","市公司副总下-金额限制，有问题；请联系管理员");
			return mapping.findForward("failure");				
		}
		if(projectAmount>doubleSecond){//金额大于一万--走省线路主管审核
			map.put("cityDiveState", "handle");
			map.put("nextTaskAssignee", pnrTicket.getProvinceLineCharge());
			formService.submitTaskFormData(taskId, map);
		}else{//调用工单接口,流程停留在当前环节，并将工单状态state设置成8
			pnrTicket.setState(8);
			isSend = false;
		}
		pnrTransferOfficeService.save(pnrTicket);
		if(isSend){
			String	msg = TASK_MESSAGE+"  "+TASK_NO_STR+processInstanceId+","+TASK_TITLE_STR+theme+","+TASK_WORKORDERDEGREE+
			getDictNameByDictid(pnrTicket.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTicket.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTicket.getSubTypeName()+"。";
			CommonUtils.sendMsg(msg, pnrTicket.getProvinceLineCharge());
		}
		return mapping.findForward("showSuccess");
	}
	
	/**
	 * 预检预修工单--省运维部总经理审批
	  * @author wangyue
	  * @title: provinceManageViceAudit
	  * @date Feb 9, 2015 5:28:36 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward provinceManageViceAudit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 发短信
		String processInstanceId = "";
		boolean isSend = true;
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		// 工单ID
		String themeId = request.getParameter("titleId");
		// 工单号
		processInstanceId = request.getParameter("processInstanceId");
		// 工单主题
		String theme = request.getParameter("title");
		// 审批时间
		Date createTime = new Date();
		// 简要描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("dealPerformer2"));
		// 制定人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("assignee"));
		
		String taskId = request.getParameter("taskId");
		//当前所处环节
		String linkName = request.getParameter("linkName");
		//附件
		String attachments = request.getParameter(linkName);
		
		String step = request.getParameter("step");
		
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
		
		entity.setThemeId(themeId);
		entity.setTheme(theme);
		entity.setCheckTime(createTime);
		entity.setHandleDescription(mainRemark);
		entity.setAuditor(userId);
		entity.setProcessInstanceId(processInstanceId);
		entity.setDoPerson(auditor);
		entity.setLinkName(linkName);
		entity.setOperation("01");
		transferHandleService.save(entity);
		
		// 用于更新时间字段或发短信用
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
		.search(search);
		PnrTransferOffice pnrTicket = null;
		if (pnrTicketList != null && pnrTicketList.size() > 0) {
			// 设置回复时间问题
			pnrTicket = pnrTicketList.get(0);
			pnrTicket.setRollbackFlag("0");
		}
		
		//清空该环节的附件
		pnrTransferOfficeService.deleteAttachmentNamesByProcessInstanceIdAndStep(processInstanceId, step);
		// attachment--start
		if (!"".equals(attachments)) {
			
			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments,step);
		}
		//调用工单接口,流程停留在当前环节，并将工单状态state设置成8
			pnrTicket.setState(8);
			pnrTransferOfficeService.save(pnrTicket);
		
		return mapping.findForward("showSuccess");
	}
	
	/**
	 * 预检预修工单--待办功能
	  * @author wangyue
	  * @title: waitWorkOrder
	  * @date Feb 9, 2015 5:49:55 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward waitWorkOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String processInstanceId = StaticMethod.nullObject2String(request
				.getParameter("processInstanceId"));
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
		.search(search);
		PnrTransferOffice pnrTicket = null;
		if (pnrTicketList != null && pnrTicketList.size() > 0) {
			pnrTicket = pnrTicketList.get(0);
		}
		//并将工单状态state设置成3
			pnrTicket.setState(3);
			pnrTransferOfficeService.save(pnrTicket);
		
		return mapping.findForward("showSuccess");
	}
	
	/**
	 * 预检预修工单--待办工单查询
	  * @author wangyue
	  * @title: waitWorkOrderList
	  * @date Feb 10, 2015 8:32:32 AM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward waitWorkOrderList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();

		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		String status = request.getParameter("status");
		
		String region = request.getParameter("region");//地市
		String country = request.getParameter("country");
		String lineType = request.getParameter("lineType");
		String provName = request.getParameter("provName");//工单类型
		String precheckFlag = request.getParameter("precheckFlag");//预检预修工单类别
		
		 ConditionQueryModel conditionQueryModel =new  ConditionQueryModel();
		 conditionQueryModel.setSendStartTime(sendStartTime);
		 conditionQueryModel.setSendEndTime(sendEndTime);
		 conditionQueryModel.setWorkNumber(wsNum);
		 conditionQueryModel.setTheme(wsTitle);
		 conditionQueryModel.setStatus(status);
		 conditionQueryModel.setCity(region);
		 conditionQueryModel.setCountry(country);
		 conditionQueryModel.setLineType(lineType);
		 conditionQueryModel.setWorkOrderType(provName);
		 conditionQueryModel.setPrecheckFlag(precheckFlag);
		
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	
    	IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");
		// 工单管理-传输局工单-预检预修工单-待回复工单 集合条数
		int total = 0;
		try {
			total = roomDemolitionService.getNewPrecheckCount(userId,
					"wait", "transferNewPrechechProcess", conditionQueryModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 工单管理-传输局工单-预检预修工单-待回复工单 集合
		List<RoomDemolitionModel> rPnrTransferList = null;
		try {
			rPnrTransferList = roomDemolitionService
					.getTransferNewPrecheckList(userId, "wait", "transferNewPrechechProcess",
							conditionQueryModel, firstResult, endResult,
							pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
		request.setAttribute("wsStatus", status);
		request.setAttribute("provName", provName);
		request.setAttribute("lineType", lineType);
		request.setAttribute("country", country);
		request.setAttribute("region", region);
		request.setAttribute("precheckFlag", precheckFlag);
		return mapping.findForward("waitWorkOrderList");
	}
	/**
	 * 打开驳回页面，给驳回界面传递相关的数据信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward openRejectView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 
		return mapping.findForward("openRejectView");
	}
	

	/**
	 * 预检预修工单--回退
	  * @author wangyue
	  * @title: rollback
	  * @date Feb 10, 2015 10:15:50 AM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward rollback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String tempUserId = sessionForm.getUserid();

		// 短息接收人
		String msgPerson = "";
		// 退回提示信息
		// String returnMsg ="该工单已驳回，请重新处理！";

		// String initiator = request.getParameter("initiator");
		String handle = request.getParameter("handle");
		String taskId = request.getParameter("taskId");
		String returnPerson = request.getParameter("returnPerson");
		msgPerson = returnPerson;
		String processInstanceId = request.getParameter("processInstanceId");
		// 工单ID
		String themeId = request.getParameter("themeId");
		// 工单主题
		String theme = request.getParameter("theme");
		// theme = new String(theme.getBytes("ISO-8859-1"),"UTF-8");

		try {
			FormService formService = (FormService) getBean("formService");
			Map<String, String> map = new HashMap<String, String>();

			String hjflag = "";
			// 当前所处环节
			String linkName = "";
			if (handle.equals("cityManageExamine")) {// 市公司运维部主管审批驳回
				hjflag = "市公司运维部主管审批驳回";
				map.put("initiator", returnPerson);
				map.put("cityManageChargeState", "rollback");
				linkName = "cityManageExamine";
			} else if (handle.equals("provinceManageExamine")) {// 省公司运维部主管审批管驳回
				hjflag = "省公司运维部主管审批管驳回";
				map.put("cityManageChargeAudit", returnPerson);
				map.put("provinceManageChargeState", "rollback");
				linkName = "provinceManageExamine";
			} else if (handle.equals("worker")) {// 区县维护中心主管回单驳回
				hjflag = "区县维护中心主管回单驳回";
				map.put("provinceManageCharge", returnPerson);
				map.put("workerState", "rollback");
				linkName = "worker";
			} else if (handle.equals("orderAudit")) {// 市公司运维部主管验收驳回
				hjflag = "市公司运维部主管验收驳回";
				map.put("handleWorker", returnPerson);
				map.put("orderAuditHandleState", "rollback");
				linkName = "orderAudit";
			} else if (handle.equals("daiweiAudit")) {// 省公司运维部主管审核驳回
				hjflag = "省公司运维部主管审核驳回";
				map.put("orderAuditCheck", returnPerson);
				map.put("daiweiAuditHandleState", "rollback");
				linkName = "daiweiAudit";
			}

			String msg = "";
			IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService) getBean("roomDemolitionService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			List<RoomDemolition> pnrTicketList = roomDemolitionService
					.search(search);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			logger.info("当前的操作人：" + tempUserId + ";当前的流程:预检预修;当前的操作环节:"
					+ hjflag + ";当前的工单号:" + processInstanceId + ";当前的taskid:"
					+ taskId + ";当前的map值:" + map.toString() + ";当前时间:"
					+ sdf.format(new Date()) + ";--start");
			formService.submitTaskFormData(taskId, map);
			logger.info("当前的操作人：" + tempUserId + ";当前的流程:预检预修;当前的操作环节:"
					+ hjflag + ";当前的工单号:" + processInstanceId + ";当前的taskid:"
					+ taskId + ";当前的map值:" + map.toString() + ";当前时间:"
					+ sdf.format(new Date()) + ";--end");
			
			if (pnrTicketList != null) {
				RoomDemolition roomDemolition = pnrTicketList.get(0);

				msg = TASK_MESSAGE + "；" + TASK_NO_STR
						+ roomDemolition.getSheetId() + ";" + TASK_STATION_NAME
						+ roomDemolition.getStationName() + ";"
						+ TASK_SEND_TIME
						+ sFormat.format(roomDemolition.getSendTime())
						+ "；请及时处理。";

				// 将驳回标志置成1，代表该工单是驳回工单
				roomDemolition.setRollbackFlag("1");
				processTaskService.setTvalueOfTask(processInstanceId, returnPerson, roomDemolition, RoomDemolition.class, null, null,false);
				roomDemolitionService.save(roomDemolition);

			}

			IRoomDemolitionHandleService roomDemolitionHandleService = (IRoomDemolitionHandleService) getBean("roomDemolitionHandleService");
			RoomDemolitionHandle entity = new RoomDemolitionHandle();
			// 将回退原因存入操作表中
			String rejectReason = request.getParameter("rejectReason");
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");

			String userId = sessionform.getUserid();
			// 审批时间
			Date createTime = new Date();
			entity.setThemeId(themeId);
			entity.setTheme(theme);
			entity.setCheckTime(createTime);
			entity.setTaskId(taskId);
			entity.setProcessInstanceId(processInstanceId);
			entity.setAuditor(userId);
			entity.setHandleDescription(rejectReason);
			entity.setLinkName(linkName);
			entity.setOperation("02");
			roomDemolitionHandleService.save(entity);

			// 当数据从页面传来时，将工单主题补充完整，实现短信提醒功能
			CommonUtils.sendMsg(msg, msgPerson);
			out.print("true");

		} catch (Exception e) {
			out.print("false");
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 预检预修工单--外包公司派发
	  * @author wangyue
	  * @title: mainSecond
	  * @date Feb 13, 2015 9:20:22 AM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward mainSecond(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String deptId = sessionForm.getDeptid();
		// 派发人

		String initiator = sessionForm.getUserid();
		// 接收人
		String handleWorker = request.getParameter("handleWorker");
		//时限
		String limit = request.getParameter("sheetCompleteLimit");
		//工单创建时间
		String createTime = request.getParameter("createTime");
		//计算结束时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Calendar cal = Calendar.getInstance();
		 Date date= new Date();
		 try{
			 date = sdf.parse(createTime);
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 cal.setTime(date);
		 //默认时限为2小时
		 int timeNum=2;
		 try{
			 timeNum = Integer.parseInt(limit);
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 cal.add(Calendar.HOUR_OF_DAY, timeNum);
		 date = cal.getTime();
		// 接收人字符串
		String taskAssigneeJSON = request.getParameter("sendObjectTotalJSON");

		// 工单ID
		String themeId = request.getParameter("themeId");
		// taskId
		String taskId = request.getParameter("taskId");
		
		// 流程开始
		FormService formService = (FormService) getBean("formService");

		Map<String, String> map = new HashMap<String, String>();
		String tempKeyValue="";
		map.put("handleWorker", handleWorker);
		map.put("dueDate", sdf.format(date));
		tempKeyValue+="handleWorker"+":"+handleWorker+";"+"dueDate"+":"+date+";";
		
		String processInstanceId=request.getParameter("processInstanceId");
		logger.info("当前的操作人："+initiator+";当前的流程:预检预修;当前的操作环节:工单转派;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人："+initiator+";当前的流程:预检预修;当前的操作环节:工单转派;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--end");
		
		// 流程结束
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		PnrTransferOffice entity = new PnrTransferOffice();
		if (themeId != null && themeId.length() > 0) {
			entity = pnrTransferOfficeService.find(themeId);
		}
		
		// 接收人
		entity.setTaskAssignee(handleWorker);
		// 处理人
		entity.setTaskAssigneeJSON(taskAssigneeJSON);
		if ("".equals(limit)) {
			limit = "2";// 如果程序出现错误，默认处理时限2小时。
		}
		entity.setProcessLimit(Double
				.parseDouble(limit));
		
		entity.setEndTime(sdf.parse(sdf.format(date)));
		// 派发人更改
		entity.setInitiator(initiator);
		pnrTransferOfficeService.save(entity);

		// 发短信

		String deadlineTime = entity.getEndTime()==null?"":sFormat.format(entity.getEndTime());
		
		String msg = TASK_MESSAGE+"  "+TASK_NO_STR+entity.getProcessInstanceId()+","+TASK_TITLE_STR+entity.getTheme()+","+TASK_WORKORDERDEGREE+
		getDictNameByDictid(entity.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+entity.getWorkOrderTypeName()+","+TASK_SUBTYPE+entity.getSubTypeName()+"。";
		  CommonUtils.sendMsg(msg, handleWorker);
		  
		return mapping.findForward("showSuccess");

	}
	/**
	 * 预检预修工单--施工队处理
	  * @author wangyue
	  * @title: transferHandle
	  * @date Feb 13, 2015 9:31:13 AM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward transferHandle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userName = sessionForm.getUsername();
		
		// 主题
		String title = StaticMethod.nullObject2String(request
				.getParameter("title"));
		// 主题ID
		String titleId = StaticMethod.nullObject2String(request
				.getParameter("titleId"));
		// 回单人
		String userId = StaticMethod.nullObject2String(request
				.getParameter("userId"));
		// 审核人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("initiatorCheck"));

		// 回单时间
		Date createTime = new Date();
		// 处理描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));
		// 故障是否恢复
		String isRecover = request.getParameter("isRecover");
		// 处理人
		String dealPerformer2 = request.getParameter("dealPerformer2");
		// 当前所处环节
		String linkName = request.getParameter("linkName");

		String taskId = StaticMethod.nullObject2String(request
				.getParameter("taskId"));

		String processInstanceId = StaticMethod.nullObject2String(request
				.getParameter("processInstanceId"));

		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
		entity.setTheme(title);
		entity.setThemeId(titleId);
		entity.setReceivePerson(userId);
		entity.setAuditor(auditor); //当前
		entity.setDoPersons(dealPerformer2);//前台的
		entity.setReceiveTime(createTime);
		entity.setCheckTime(createTime);
		entity.setDoPerson(userName);// 当前登录人
		entity.setHandleDescription(mainRemark);//前台的
		entity.setIsRecover(isRecover);//前台的
		entity.setProcessInstanceId(processInstanceId);
		entity.setLinkName(linkName);
		entity.setTaskId(taskId);
		entity.setOperation("04");
		
		String msg="";
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			pnrTransferOffice.setLastReplyTime(new Date());
			pnrTransferOfficeService.save(pnrTransferOffice);
			
			
			msg = TASK_MESSAGE+"  "+TASK_NO_STR+pnrTransferOffice.getProcessInstanceId()+","+TASK_TITLE_STR+pnrTransferOffice.getTheme()+","+TASK_WORKORDERDEGREE+
			getDictNameByDictid(pnrTransferOffice.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTransferOffice.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTransferOffice.getSubTypeName()+"。";
			
			
//			deadLineTime =pnrTransferOffice.getEndTime()==null?"":sFormat.format(pnrTransferOffice.getEndTime());
//			contact=pnrTransferOffice.getConnectPerson();
		}
		transferHandleService.save(entity);
		/*
		 * //发短信 String mainResName=""; if (pnrTroubleTicketList != null) {
		 * mainResName = pnrTroubleTicketList.get(0).getFailedSite(); }
		 */
		// 流程提交到下一级
		FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		String tempKeyValue="";
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
				tempKeyValue+=name+":"+request.getParameter(name)+";";
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("当前的操作人："+userId+";当前的流程:预检预修;当前的操作环节:工单执行;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--start");
		formService.submitTaskFormData(taskId, map);
		logger.info("当前的操作人："+userId+";当前的流程:预检预修;当前的操作环节:工单执行;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--end");
		
		// 流程提交到下一级-end

		
		
		  CommonUtils.sendMsg(msg, auditor);
		 
		return mapping.findForward("showSuccess");

	}
	

	/**
	 * 省公司运维部主管审核 处理
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward secondAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		// 流程号
		String processInstanceId = request.getParameter("processInstanceId");

		String userId = sessionform.getUserid();
		// 工单ID
		String themeId = request.getParameter("themeId");
		// 工单主题
		String theme = request.getParameter("theme");

		// 审核时间
		Date createTime = new Date();

		// 审核描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));

		// 审核状态 通过、不通过
		String auditState = StaticMethod.nullObject2String(request
				.getParameter("daiweiAuditHandleState"));

		// 驳回人
		String returnPerson = request.getParameter("returnPerson");

		// 下一步 手动归档环节处理人
		String manualArchiveCheck = request.getParameter("manualArchiveCheck");

		// 当前环节的步骤值
		// String step=request.getParameter("step");

		// 当前所处环节
		String linkName = StaticMethod.nullObject2String(request
				.getParameter("linkName"));
		// 任务ID
		String taskId = request.getParameter("taskId");

		// 是否发短信
		boolean isSend = true;

		// 接受短信的人
		String msgPerson = "";

		// 1.向回复表中保存数据
		IRoomDemolitionHandleService roomDemolitionHandleService = (IRoomDemolitionHandleService) getBean("roomDemolitionHandleService");
		RoomDemolitionHandle entity = new RoomDemolitionHandle();
		entity.setThemeId(themeId);
		entity.setTheme(theme);
		entity.setCheckTime(createTime);
		entity.setOpinion(mainRemark);
		entity.setHandleDescription(mainRemark);
		entity.setState(auditState);
		entity.setAuditor(userId);
		entity.setLinkName(linkName);
		entity.setProcessInstanceId(processInstanceId);
		if ("handle".equals(auditState)) {
			entity.setOperation("01");
		} else {
			entity.setOperation("02");
		}
		roomDemolitionHandleService.save(entity);

		// 2.更新工单主表中的数据
		IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService) getBean("roomDemolitionService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<RoomDemolition> pnrTicketList = roomDemolitionService
				.search(search);
		RoomDemolition pnrTicket = null;
		// 3.流程派发
		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();
		if ("handle".equals(auditState)) {
			map.put("daiweiAuditHandleState", "handle");
			map.put("manualArchiveCheck", manualArchiveCheck);
			msgPerson = manualArchiveCheck;
			isSend = false;
		} else if ("rollback".equals(auditState)) {
			map.put("daiweiAuditHandleState", "rollback");
			map.put("orderAuditCheck", returnPerson);
			msgPerson = returnPerson;
		}

		formService.submitTaskFormData(taskId, map);
		
		if (pnrTicketList != null) {
			// 设置回复时间问题
			pnrTicket = pnrTicketList.get(0);

			if (pnrTicket != null) {
				if (auditState.equals("rollback")) {
					// pnrTicket.setLastreplyTime(null);
					pnrTicket.setRollbackFlag("1");

				} else if (auditState.equals("handle")) {
					pnrTicket.setRollbackFlag("0");
				}
				//在工单主表中添加流程信息
				processTaskService.setTvalueOfTask(processInstanceId, msgPerson, pnrTicket, RoomDemolition.class, null, null,false);
				roomDemolitionService.save(pnrTicket);
			}
		}
		
		// 4.发短信
		if (isSend) {
			String msg = TASK_MESSAGE + ";" + TASK_NO_STR
					+ pnrTicket.getSheetId() + ";" + TASK_STATION_NAME
					+ pnrTicket.getStationName() + ";" + TASK_SEND_TIME
					+ sFormat.format(pnrTicket.getSendTime()) + "；请及时处理。";
			CommonUtils.sendMsg(msg, msgPerson);
		}

		request.setAttribute("success", "check");
		request.setAttribute("condition", request.getParameter("condition"));
		return mapping.findForward("showSuccess");
	}

	/**
	 * 工单执行驳回（施工队驳回）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward workerRollback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String tempUserId = sessionForm.getUserid();
		
		//短息接收人
		String msgPerson = "";
		//退回提示信息
		String returnMsg ="该工单已驳回，请重新处理！";
		
		String initiator = request.getParameter("initiator");
		String handle = request.getParameter("handle");
		String taskId = request.getParameter("taskId");
		String returnPerson = request.getParameter("returnPerson");
		String processInstanceId = request.getParameter("processInstanceId");
		// 工单ID
		String themeId = request.getParameter("themeId");
		// 工单主题
		String theme = StaticMethod.nullObject2String(request
				.getParameter("theme"));
		theme = new String(theme.getBytes("ISO-8859-1"),"UTF-8");
		
		
			FormService formService = (FormService) getBean("formService");
			Map<String, String> map = new HashMap<String, String>();
	
			String hjflag="";
			//当前所处环节
			String linkName="";
			if (handle.equals(TASK_TRANSFERHANDLE)) {//施工队回退
				hjflag="施工队驳回";
				map.put("nextTaskAssignee", initiator);
				map.put("workerHandleState", "rollback");
				msgPerson=initiator;
				returnMsg = "该工单未能处理，已回退，请重新处理！";
				linkName = "worker";
			}
			
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   logger.info("当前的操作人："+tempUserId+";当前的流程:预检预修;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
		   formService.submitTaskFormData(taskId, map);
		   logger.info("当前的操作人："+tempUserId+";当前的流程:预检预修;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
			
			String msg="";
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
					.search(search);
			if (pnrTicketList != null) {
				PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
				
				msg = TASK_MESSAGE+"  "+TASK_NO_STR+pnrTransferOffice.getProcessInstanceId()+","+TASK_TITLE_STR+pnrTransferOffice.getTheme()+","+TASK_WORKORDERDEGREE+
				getDictNameByDictid(pnrTransferOffice.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTransferOffice.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTransferOffice.getSubTypeName()+"。";
			}
			
			//当数据从页面传来时，将工单主题补充完整，实现短信提醒功能
			  CommonUtils.sendMsg(msg, msgPerson);
			  
		return mapping.findForward("showSuccess");
	}
	
	/**
	 * request得到传输局对象
	 * 
	 * @author wangyue
	 * @title: setTransferOfficeByRequest
	 * @date Nov 10, 2014 4:08:01 PM
	 * @param request
	 * @param pnrTransferOffice
	 * @throws ParseException
	 *             void
	 */
	private void setTransferOfficeByRequest(HttpServletRequest request,
			PnrTransferOffice pnrTransferOffice) throws ParseException {

		// 主题
		String title = StaticMethod.nullObject2String(request
				.getParameter("title"));
		// 第一派单人
		String initiator = StaticMethod.nullObject2String(request
				.getParameter("initiator"));
		// 工单类型
		String workOrderType = StaticMethod.nullObject2String(request
				.getParameter("workOrderType"));
		// 工单子类型
		String subType = request.getParameter("subType");
		// 关键字
		String keyWord = StaticMethod.nullObject2String(request
				.getParameter("keyWord"));
		// 预检预修提交申请时间
		String mainFaultOccurTime = request.getParameter("mainFaultOccurTime");
		// 紧急程度
		String workOrderDegree = StaticMethod.nullObject2String(request
				.getParameter("workOrderDegree"));
		// 故障描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));
		// 干线类型
		String workType = StaticMethod.nullObject2String(request
				.getParameter("workType"));
		// 承载业务级别
		String bearService = StaticMethod.nullObject2String(request
				.getParameter("bearService"));
		// 项目金额估算
		String projectAmount = StaticMethod.nullObject2String(request
				.getParameter("projectAmount"));
		// 接收人
		String taskAssignee = "";
		// 接收人字符串
		String taskAssigneeJSON = "";
		// 工单ID
		String themeId = request.getParameter("themeId");
		
		//未做任何操作--start
		// 经度
		String longitude = StaticMethod.nullObject2String(request
				.getParameter("longitude"));
		// 纬度
		String latitude = StaticMethod.nullObject2String(request
				.getParameter("latitude"));
		//图片
		String picture = request.getParameter("picture");
		//工单类别
		String precheckFlag = request.getParameter("precheckFlag");
		//
		String compensate = request.getParameter("compensate");
		//--end
		
		
		
		// 新增附件
		String attachments = request.getParameter("sheetAccessories");
		int attachmentsNum = 0;
		if (attachments != null && attachments.length() > 0) {

			attachmentsNum = attachments.split(",").length;
		}

		String processInstanceId = request.getParameter("processInstanceId");

		pnrTransferOffice.setId(themeId);
		// 主题
		pnrTransferOffice.setTheme(title);
		pnrTransferOffice.setBearService(bearService);
		// 第一派发人
		pnrTransferOffice.setInitiator(initiator);
		pnrTransferOffice.setOneInitiator(initiator);
		pnrTransferOffice.setCreateWork(initiator);
		pnrTransferOffice.setCountryCSJ(initiator);
		// 预检预修提交申请时间
		pnrTransferOffice.setSubmitApplicationTime(sFormat
				.parse(mainFaultOccurTime));
		// 描述
		pnrTransferOffice.setFaultDescription(mainRemark);
		pnrTransferOffice.setTaskAssigneeJSON(taskAssigneeJSON);
		// 工单类型
		pnrTransferOffice.setWorkOrderType(workOrderType);
		pnrTransferOffice.setSubType(subType);
		pnrTransferOffice.setSubTypeName(CommonUtils.getId2NameString(subType,1, ","));
		pnrTransferOffice.setWorkOrderTypeName(CommonUtils.getId2NameString(workOrderType,1, ","));
		// 紧急程度
		pnrTransferOffice.setWorkOrderDegree(workOrderDegree);
		// 关键字
		pnrTransferOffice.setKeyWord(keyWord);
		//没有确定的人，只是添加--默认superUser
		IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");
		PrecheckShipModel precheckShipModel = roomDemolitionService.getPrecheckShipModelBycountryCharge(initiator);
		//区县主任或者市城区班长
		pnrTransferOffice.setSecondCreateWork(precheckShipModel.getCountryDirector());
		pnrTransferOffice.setTaskAssignee(precheckShipModel.getCountryDirector());
		//市线路主管
		pnrTransferOffice.setCityLineCharge(precheckShipModel.getCityLineCharge());
		//市线路主任
		pnrTransferOffice.setCityLineDirector(precheckShipModel.getCityLineDirector());
		//市运维主管
		pnrTransferOffice.setCityManageCharge(precheckShipModel.getCityManageCharge());
		//市运维主任
		pnrTransferOffice.setCityManageDirector(precheckShipModel.getCityManageDirector());
		//市公司副总
		pnrTransferOffice.setCityGS(precheckShipModel.getCityCompany());
		//角色获取
		IPnrTransferPrecheckService pnrTransferPrecheckService = (IPnrTransferPrecheckService) getBean("pnrTransferPrecheckService");
		List<Map> list = pnrTransferPrecheckService.getSGSByCountryCSJ(initiator,precheckRoleModel.getProvinceLineExamine());
		String provinceLineCharge = "superUser";
		if(list!=null && list.size()>0){
			if(list.get(0).get("USERID")!=null && !"".equals(list.get(0).get("USERID"))){
				provinceLineCharge = list.get(0).get("USERID").toString();
			}
		}
		//省线路主管
		pnrTransferOffice.setProvinceLineCharge(provinceLineCharge);
		
		List<Map> provinceLineDirectorList = pnrTransferPrecheckService.getSGSByCountryCSJ(initiator,precheckRoleModel.getProvinceLineConductor());
		String provinceLineDirector = "superUser";
		if(provinceLineDirectorList!=null && provinceLineDirectorList.size()>0){
			if(provinceLineDirectorList.get(0).get("USERID")!=null && !"".equals(provinceLineDirectorList.get(0).get("USERID"))){
				provinceLineDirector = provinceLineDirectorList.get(0).get("USERID").toString();
			}
		}
		//省线路总经理
		pnrTransferOffice.setProvinceLine(provinceLineDirector);
		
		List<Map> provinceManageChargeList = pnrTransferPrecheckService.getSGSByCountryCSJ(initiator,precheckRoleModel.getProvinceManageExamine());
		String provinceManageCharge = "superUser";
		if(provinceManageChargeList!=null && provinceManageChargeList.size()>0){
			if(provinceManageChargeList.get(0).get("USERID")!=null && !"".equals(provinceManageChargeList.get(0).get("USERID"))){
				provinceManageCharge = provinceManageChargeList.get(0).get("USERID").toString();
			}
		}
		//省运维主管
		pnrTransferOffice.setProvinceManageCharge(provinceManageCharge);
		
		List<Map> provinceManageDirectorList = pnrTransferPrecheckService.getSGSByCountryCSJ(initiator,precheckRoleModel.getProvinceManageConductor());
		String provinceManageDirector = "superUser";
		if(provinceManageDirectorList!=null && provinceManageDirectorList.size()>0){
			if(provinceManageDirectorList.get(0).get("USERID")!=null && !"".equals(provinceManageDirectorList.get(0).get("USERID"))){
				provinceManageDirector = provinceManageDirectorList.get(0).get("USERID").toString();
			}
		}
		//省运维总经理
		pnrTransferOffice.setProvinceManage(provinceManageDirector);
		
		//版本标志：1 代表新预检预修工单，旧预检预修工单为空
		pnrTransferOffice.setVersionFlag("2");

		// 附件个数
		pnrTransferOffice.setAttachmentsNum(attachmentsNum);
		pnrTransferOffice.setProcessInstanceId(processInstanceId);
		
		pnrTransferOffice.setWorkType(workType);
		pnrTransferOffice.setProjectAmount(Double.parseDouble(projectAmount));
		pnrTransferOffice.setSendTime(new Date());
		pnrTransferOffice.setRollbackFlag("0");
		pnrTransferOffice.setPrecheckFlag(precheckFlag);
		pnrTransferOffice.setCompensate(Double.parseDouble(compensate==""?"0":compensate));

	}
	/**
	 * 查看历史【包含流程跟踪、任务列表（完成和未完成）、流程变量】
	 * 
	 * @return
	 */
	public ActionForward viewHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "historicTasks");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int totalSize = 0;

		HistoryService historyService = (HistoryService) getBean("historyService");
		String processInstanceId = request.getParameter("processInstanceId");
		
		logger.info("historicTasks开始"+processInstanceId+";");
		List<HistoricTaskInstance> historicTasks = historyService
				.createHistoricTaskInstanceQuery().processInstanceId(
						processInstanceId)
				.orderByHistoricTaskInstanceStartTime().asc().listPage(
						firstResult * pageSize, pageSize);
		logger.info("historicTasks结束"+processInstanceId+";");
		
		logger.info("createHistoricTaskInstanceQuery开始"+processInstanceId+";");
		totalSize = (int) historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId).count();
		logger.info("createHistoricTaskInstanceQuery结束"+processInstanceId+";");
		
		logger.info("historicVariableInstances开始"+processInstanceId+";");
		List<HistoricVariableInstance> historicVariableInstances = historyService
				.createHistoricVariableInstanceQuery().processInstanceId(
						processInstanceId).list();
		logger.info("historicVariableInstances结束"+processInstanceId+";");
		
		// historyService

		request.setAttribute("historicTasks", historicTasks);
		request.setAttribute("historicTasksPageSize", pageSize);
		request.setAttribute("historicTasksTotal", totalSize);
		request.setAttribute("historicVariableInstances",
				historicVariableInstances);
		request.setAttribute("historicVariableInstancesPageSize", pageSize);
		request.setAttribute("historicVariableInstancesTotal",
				historicVariableInstances.size());
		request.setAttribute("processInstanceId", processInstanceId);
		return mapping.findForward("viewHistory");
	}
	/**
	 * 流程跟踪
	 * 
	 * @throws Exception
	 */
	public void graphHistoryProcessInstance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RepositoryService repositoryService = (RepositoryService) getBean("repositoryService");
		String processInstanceId = request.getParameter("processInstanceId");
		Command<InputStream> cmd = null;
		cmd = new HistoryProcessInstanceDiagramCmd(processInstanceId);

		InputStream is = ((ServiceImpl) repositoryService).getCommandExecutor()
				.execute(cmd);
		response.setContentType("image/png");

		int len = 0;
		byte[] b = new byte[1024];

		while ((len = is.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}

	
	/**
	 * 获取回复信息
	 * @param transferHandleService
	 * @param search
	 * @param handlelist
	 * @return
	 */
	private Map<String, Object> getHandleList(Map<String, Object> gMap,IPnrTransferOfficeHandleService transferHandleService,Search search,List<PnrTransferOfficeHandle> handlelist,int handleSize ){
	
		SearchResult<PnrTransferOfficeHandle> results = transferHandleService
				.searchAndCount(search);
		handleSize = results.getTotalCount();
		if (handleSize > 0) {
			handlelist = results.getResult();

		}		
		
		gMap.put("size", handleSize);
		gMap.put("list", handlelist);
		
		return gMap;
		
	}
	/**
	 * 获取回复信息
	 * @param transferHandleService
	 * @param search
	 * @param handlelist
	 * @return
	 */
	private Map<String, Object> getHandleList(Map<String, Object> gMap,IRoomDemolitionHandleService roomDemolitionHandleService,Search search,List<RoomDemolitionHandle> handlelist,int handleSize ){
		
		SearchResult<RoomDemolitionHandle> results = roomDemolitionHandleService
		.searchAndCount(search);
		handleSize = results.getTotalCount();
		if (handleSize > 0) {
			handlelist = results.getResult();
			
		}		
		
		gMap.put("size", handleSize);
		gMap.put("list", handlelist);
		
		return gMap;
		
	}
	/**
	 * 显示回复的附件！
	 * 
	 * @param request
	 * @param handlist
	 */
	private void showReplySetAttribute(HttpServletRequest request,
			List<PnrTransferOfficeHandle> handlist) {
		if (handlist != null) {
			int handSize = handlist.size();
			if (handSize > 0) {
				for (int i = 0; i < handSize; i++) {
					request.setAttribute("sheetReply" + i, handlist.get(i));
				}
			}
		}
	}
	
	/**
	 * display attachments
	 */
	public ActionForward showSheetAccessoriesList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		pnrTransferOfficeService.showSheetAccessoriesList(mapping, form, request, response);
		
		return mapping.findForward("sheetAccessories");
	}
	/**
	 * 根据紧急程度id，获取紧急程度汉字
	 * @param dictId
	 * @return
	 */
	public String getDictNameByDictid(String dictId){
		String msg="";
		if("101230901".equals(dictId)){
			msg="特急";
		}else if("101230902".equals(dictId)){
			msg="紧急";
			
		}else if("101230903".equals(dictId)){
			msg="一般";
		}
		return msg;
	}
	
	/**
	 * 更改本地网预检预修工单--紧急程度
	  * @author wangyue
	  * @title: changeDegree
	  * @date Mar 10, 2015 2:51:04 PM
	  * @return String
	 */
	public String changeDegree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		String workOrderDegree = request.getParameter("degree");
		String processInstanceId = request.getParameter("processInstanceId");
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		if(processInstanceId!=null && !"".equals(processInstanceId)){
			Search search = new Search();
			search.addFilterEqual("processInstanceId",processInstanceId);
			
			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
			List<PnrTransferOffice> list = t.getResult();
			response.setCharacterEncoding("utf-8");		
			
			if(list!=null && list.size()>0){
				//更改工单紧急程度
				PnrTransferOffice pnrTransferOffice =list.get(0);
				pnrTransferOffice.setWorkOrderDegree(workOrderDegree);
				pnrTransferOfficeService.save(pnrTransferOffice);
				response.getWriter().write("紧急程度修改成功！");
			}else{
				response.getWriter().write("紧急程度修改失败！");
				
			}
		}else{
			response.getWriter().write("紧急程度修改失败！");
		}
		return "";
	}
	/**
	 * 打开选择图片窗口
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward openSelectPhotoView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("photoDescribe","");
		request.setAttribute("createPhotoTime","");
		return mapping.findForward("openSelectPhotoView");
	}
	
	  /**
	   * 本地网和干线工单照片查看--查看的图片以二进制流存在数据库中
	   * @param mapping
	   * @param form
	   * @param request
	   * @param response
	   * @return
	   * @throws Exception
	   */
	    public ActionForward precheckShowPicture(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	      throws Exception
	    {
	      String id = request.getParameter("id");
	      String pageIndexString = request.getParameter("curPage");
	      getClass().getClassLoader().getResource("images/login.png");
//	      System.out.println("--测试----" + pageIndexString);
	      int firstResult = (com.google.common.base.Strings.isNullOrEmpty(pageIndexString)) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;

	      PnrAndroidPhotoFile taskFile = null;
	      IPnrAndroidMgr pnrAndroidMgr = (IPnrAndroidMgr)getBean("ipnrAndroidMgrImpl");
		    Search search = new Search();
		    search.addFilterEqual("id", id);
		    List list = pnrAndroidMgr.search(search);
		    if ((list != null) && (list.size() > 0))
		      taskFile = (PnrAndroidPhotoFile)list.get(0);

	      Clob clob = taskFile.getFileData();

	      if (clob != null)
	      {
	        File tempFile = File.createTempFile("tmp", ".png");
//	        System.out.println("--tempFilePath-" + tempFile.getPath());
	        try {
	          int len;
	          response.setHeader("Pragma", "no-cache");
	          response.setHeader("Cache-Control", "no-cache");
	          response.setDateHeader("Expires", 0L);
	          response.setContentType("image/jpeg");

	          char[] buf = new char[255];
	          Reader reader = clob.getCharacterStream();
	          FileOutputStream fos = new FileOutputStream(tempFile);
	          BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	          while ((len = reader.read(buf)) > 0) {
	            bw.write(buf, 0, len);
	            bw.flush();
	          }
	          bw.close();
	          FileInputStream fis = new FileInputStream(tempFile);
	          BASE64Decoder base64 = new BASE64Decoder();
	          base64.decodeBuffer(fis, response.getOutputStream());
	        }
	        finally
	        {
	          tempFile.deleteOnExit();
	        }
	      }

	      return null;
	    }
	/**
	   *本地网、干线工单照片查看--照片以路径形式存储在数据库中
	   * @param mapping
	   * @param form
	   * @param request
	   * @param response
	   * @return
	   * @throws Exception
	   */
	  public ActionForward showPicture (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	  throws Exception
	  {
	    	 String id = request.getParameter("id");
			    String pageIndexString = request.getParameter("curPage");
//			    getClass().getClassLoader().getResource("images/login.png");
//			    System.out.println("--测试----" + pageIndexString);
			    int firstResult = (com.google.common.base.Strings.isNullOrEmpty(pageIndexString)) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;

			    PnrAndroidPhotoFile taskFile = null;
//			    IPnrAndroidMgrImpl pnrAndroidMgrImpl = (IPnrAndroidMgrImpl)getBean("ipnrAndroidMgrImpl");
			    IPnrAndroidMgr pnrAndroidMgrImpl = (IPnrAndroidMgr)getBean("ipnrAndroidMgrImpl");

			    Search search = new Search();
			    search.addFilterEqual("id", id);
			    List list = pnrAndroidMgrImpl.search(search);
			    if ((list != null) && (list.size() > 0))
			      taskFile = (PnrAndroidPhotoFile)list.get(0);

			    String path = taskFile.getPath();
//			    String path="wyphotoTest/qq.jpg";
			    String patch = path;  
			    
			   /* //获取盘符
			    String strClassName = getClass().getName();
			    String strPackageName="";
			    if(getClass().getPackage()!=null){
			    	strPackageName = getClass().getPackage().getName();
			    }
			    String strFileName = "";
			    if(!"".equals(strPackageName)){
			    	strFileName = strClassName.substring(strPackageName.length()+1,strClassName.length());
			    }else{
			    	strFileName = strClassName;
			    }
			    URL url =null ;
			    url = getClass().getResource(strFileName+".class");
			    String strUrl = url.toString();
			    strUrl = strUrl.substring(strUrl.indexOf('/')+1,strUrl.lastIndexOf('W'));
			    
			    String flag = File.separator;
			    if("\\".equals(flag)){//windows环境下的分隔符是\\
			    	patch = strUrl+patch;
			    }else if("/".equals(flag)){//Linux环境下的分隔符是/
			    	patch = "/"+strUrl+patch;
			    }
			    System.out.println(patch);
			    //肉哥思路
			    FileInputStream is = new FileInputStream(patch);  
				int i = is.available(); // 得到文件大小  
				byte data[] = new byte[i];  
				is.read(data); // 读数据  
				is.close();  
				response.setContentType("image/*"); // 设置返回的文件类型  
				OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象  
				toClient.write(data); // 输出数据  
				toClient.close(); */
			    
			    InputStream in = null ;
		        ByteArrayOutputStream out = null ;
		        try {
		            //创建远程文件对象
		            //String remotePhotoUrl = StaticVariable.IMG_WORKSHEET_PUBLIC_URL+patch;
		        	String remotePhotoUrl = CommonUtils.getImgWorksheetPublicUrl()+patch;
		            SmbFile remoteFile = new SmbFile(remotePhotoUrl);
		            remoteFile.connect(); //尝试连接
		            //创建文件流
		            in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
		            out = new ByteArrayOutputStream((int)remoteFile.length());
		            //读取文件内容
		            byte[] buffer = new byte[4096];
		            int len = 0; //读取长度
		            while ((len = in.read(buffer, 0, buffer.length)) != - 1) {
		                out.write(buffer, 0, len);
		            }

		            out.flush(); //刷新缓冲的输出流
		           
		            response.setContentType("image/*"); // 设置返回的文件类型  
		    		OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象  
		    		toClient.write(out.toByteArray()); // 输出数据  
		    		toClient.close(); 
		        }
		        catch (Exception e) {
		            String msg = "工单照片选择-下载远程文件出错：" + e.getLocalizedMessage();
		            System.out.println(msg);
		        }
		        finally {
		            try {
		                if(out != null) {
		                    out.close();
		                }
		                if(in != null) {
		                    in.close();
		                }
		            }
		            catch (Exception e) {}
		        }
			   
			  return null;
	  }
	  
	  
	  /**
	   * 处理添加页面选择的图片
	    * @author wangyue
	    * @title: selectPhotoTodo
	    * @date Mar 16, 2015 2:54:00 PM
	    * @param mapping
	    * @param form
	    * @param request0
	    * @param response
	    * @return
	    * @throws Exception ActionForward
	   */
	  public ActionForward selectPhotoTodo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	  throws Exception
	  {
		  String photoes = request.getParameter("photoes");
		  String[] ids = photoes.split(",");
		  String names="";
		  List<PnrAndroidPhotoFile> photoList = new ArrayList<PnrAndroidPhotoFile>(); 
		  JSONArray json = new JSONArray();
		  
		  int count=0;//计数器
		  double sumLongitude=0.0;//所选照片经度的和
		  double sumLatitude=0.0;//所选照片纬度的和
		  
		  //获取每一张图片的信息
		  for(String str:ids){
			  if(!"".equals(str)){
				  IPnrAndroidMgr pnrAndroidMgr = (IPnrAndroidMgr)getBean("ipnrAndroidMgrImpl");
				    Search search = new Search();
				    search.addFilterEqual("id", str);
				    photoList = pnrAndroidMgr.search(search);
				    if(photoList!=null && photoList.size()>0){
				    	
				    	JSONObject jo = new JSONObject();
				    	jo.put("id", photoList.get(0).getId());
				    	jo.put("longitude",photoList.get(0).getLongitude()==null?'无':photoList.get(0).getLongitude());
				    	sumLongitude+=photoList.get(0).getLongitude()==null?0.0:Double.parseDouble(photoList.get(0).getLongitude());
				    	jo.put("latitude", photoList.get(0).getLatitude()==null?'无':photoList.get(0).getLatitude());
				    	sumLatitude+=photoList.get(0).getLatitude()==null?0.0:Double.parseDouble(photoList.get(0).getLatitude());
				    	jo.put("createtime", photoList.get(0).getCreateTime()==null?'无':photoList.get(0).getCreateTime());
				    	jo.put("userId", photoList.get(0).getUserId()==null?'无':CommonUtils.getId2NameString(photoList.get(0).getUserId(), 4, ","));
				    	json.put(jo);
				    }
				    count++;
			  }
		  }
		  
		  double avgLongitude = 0.0;
		  double avgLatitude = 0.0;
		  if (count > 0) {
			// 计算经度平均值
			  avgLongitude=sumLongitude/count;
			  BigDecimal b = new BigDecimal(avgLongitude);
			  avgLongitude = b.setScale(6, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			  
			// 计算纬度平均值
			  avgLatitude=sumLatitude/count;
			  BigDecimal c = new BigDecimal(avgLatitude);
			  avgLatitude = c.setScale(6, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			  
		  }
		  
		  System.out.println("平均经度="+avgLongitude);
		  System.out.println("平均纬度="+avgLatitude);

		  JSONObject avgJo = new JSONObject();
		  avgJo.put("avgLongitude", avgLongitude);
		  avgJo.put("avgLatitude", avgLatitude);
		  json.put(avgJo);
		  
		  response.getWriter().write(json.toString());		
		  return null;
	  }
	  /***
	   * 工单详情中，新建工单图片查看
	    * @author wangyue
	    * @title: showCreateWorkPhoto
	    * @date Mar 19, 2015 4:06:39 PM
	    * @param mapping
	    * @param form
	    * @param request
	    * @param response
	    * @return
	    * @throws Exception ActionForward
	   */
	  public ActionForward showCreateWorkPhoto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	  throws Exception
	  {
		// 工单号
			String processInstanceId = StaticMethod.nullObject2String(request
					.getParameter("pid"));
			IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");
			List<PnrAndroidPhotoFile> photoList = roomDemolitionService.showPhotoByProcessInstanceId(processInstanceId);
			request.setAttribute("list", photoList);
			return mapping.findForward("showPhotoView");
	  }
	  
	  
	/**
	 * 打开批量处理子窗口
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward openBatchApproveView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//跳转到处理页面
		String forwardView="";
			forwardView="openBatchApproveView";
		return mapping.findForward(forwardView);
	}
	
	/**
	 * 批量处理-总方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doBatchApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out=response.getWriter();
		//接受参数
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();	//当前操作人
		String taskAssignee = StaticMethod.nullObject2String(request.getParameter("teskAssignee"));	//审批人
		String dealPerformer = StaticMethod.nullObject2String(request.getParameter("dealPerformer"));	//简要描述
		String selectedIDs = request.getParameter("selectedIDs");	//任务号+流程号+所在环节
		
		
		String result="";	//批量处理结果 success error
		String content="";	//回复内容
		String returnJson="";
		String error = "";
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		//解析任务号+流程号+所在环节
		try {			
			String[] ids = selectedIDs.split("#");
			//判断是否有工单需要处理
			if(ids!=null && ids.length>0){
				for(int i=0;i<ids.length;i++){
					List<PnrTransferOffice> pnrTicketList = null;
					PnrTransferOffice pnrTicket = null;
					String[] id = ids[i].split(",");
					String taskId = id[0]==null?"":id[0].trim();
					String processInstanceId = id[1]==null?"":id[1].trim();
					String taskKey = id[2]==null?"":id[2].trim();
					//获取该处理工单
					Search search = new Search();
					search.addFilterEqual("processInstanceId", processInstanceId);
					pnrTicketList = pnrTransferOfficeService.search(search);
					if (pnrTicketList != null && pnrTicketList.size() > 0) {
						pnrTicket = pnrTicketList.get(0);
						//改变回退工单状态
						pnrTicket.setRollbackFlag("0");
					}
					
					//解析字符串，获取到所在环节字段，并根据环节获取下一流程人
					if("provinceManageViceAudit".equals(taskKey)){
						pnrTicket.setState(8);
					}else{
						try{
							
							FormService formService = (FormService) getBean("formService");
							Map<String, String> taskMap = new HashMap<String, String>();
							String nextPerson = "";
							String nextAssigness = "";//流程下一步人员
							String transferHandleFlag = "";//处理标志
							if("provinceLineExamine".equals(taskKey)){//省线路主管
								nextAssigness  = pnrTicket.getProvinceLine();
								nextPerson = "provinceLineViceAudit";
								transferHandleFlag = "provinceLineChargeState";
							}else if("provinceLineViceAudit".equals(taskKey)){//省线路总经理
								nextAssigness = pnrTicket.getProvinceManageCharge();
								nextPerson = "provinceManageCharge";
								transferHandleFlag = "provinceLineViceState";
							}else if("provinceManageExamine".equals(taskKey)){//省运维主管
								nextAssigness = pnrTicket.getProvinceManage();
								nextPerson = "provinceManageVice";
								transferHandleFlag = "provinceManageChargeState";
							}
							taskMap.put(nextPerson,nextAssigness );
							taskMap.put(transferHandleFlag, "handle");
							formService.submitTaskFormData(taskId, taskMap);
						}catch(Exception e){
							e.printStackTrace();
							error+="工单号:" + processInstanceId + ";";;
						}
					}
				
				
					//记录处理信息
					PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
					entity.setThemeId(pnrTicket.getId());
					entity.setTheme(pnrTicket.getTheme());
					entity.setCheckTime(new Date());
					entity.setHandleDescription(dealPerformer);
					entity.setAuditor(userId);
					entity.setProcessInstanceId(processInstanceId);
					entity.setDoPerson(taskAssignee);
					entity.setLinkName(taskKey);
					entity.setOperation("01");
					transferHandleService.save(entity);
				}
			
			
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnJson="{\"result\":\"error\",\"content\":\"工单ID解析异常\"}";
			out.print(returnJson);
			return null;
		}
		if("".equals(error)){
			returnJson="{\"result\":\"success\",\"content\":\"批量审批成功\"}";
			
		}else{
			returnJson="{\"result\":\"error\",\"content\":\""+content+"\"}";
			
		}
		out.print(returnJson);
		return null;
		
	}
	
	
	/**
	 * 批量处理-共用
	 * 
	 * @param list
	 * @param paramMap
	 * @return
	 */
	
	private String communalBatchApprove(
			List<Map<String, String>> list, Map<String, String> paramMap) {
		System.out.println("--------进到批量处理-共用方法了吗？---------------");
		String content = ""; // 返回结果说明
		String successStr = "";// 成功的工单
		String erorrStr = "";// 失败的工单
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		FormService formService = (FormService) getBean("formService");

		String wsStatus=paramMap.get("wsStatus");
		Iterator<Map<String, String>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Map<String, String> map = iterator.next();
			String processInstanceId = map.get("processInstanceId");
			String taskId=map.get("taskId");

			// 遍历开始
			try {
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTicket = null;
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTicket = pnrTicketList.get(0);
				}
				
				//提交流程
				Map<String, String> taskMap = new HashMap<String, String>();
				if (wsStatus.equals("workOrderCheck")) { // 工单发起审核
					taskMap.put("workOrderState", "handle"); // 工单发起审核审核标志
					taskMap.put("cityLineChargeAduit", pnrTicket
							.getCityLineCharge()); // 市线路主管审核人
					
				} else if (wsStatus.equals("cityLineExamine")) { // 市线路主管审核
					taskMap.put("cityLineChargeState", "handle"); // 市线路主管审核标志
					taskMap.put("cityLineDirectorAudit", pnrTicket
							.getCityLineDirector()); // 市线路主任审批人
					
				} else if (wsStatus.equals("cityLineDirectorAudit")) { // 市线路主任审核
					taskMap.put("cityLineDirectorState", "handle"); // 市线路主任审核标志
					taskMap.put("cityManageChargeAudit", pnrTicket
							.getCityManageCharge()); // 市运维主管审核人
					
				} else if (wsStatus.equals("cityManageExamine")) { // 市运维主管审核
					taskMap.put("cityManageChargeState", "handle"); // 标志
					taskMap.put("cityManageDirectorAudit", pnrTicket
							.getCityManageDirector()); //市运维主任审核
					
				}
				
				formService.submitTaskFormData(taskId, taskMap);

				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setThemeId(pnrTicket.getId());
				entity.setTheme(pnrTicket.getTheme());
				entity.setCheckTime(new Date());
				entity.setHandleDescription(paramMap.get("dealPerformer"));
				entity.setAuditor(paramMap.get("userId"));
				entity.setProcessInstanceId(processInstanceId);
				entity.setDoPerson(paramMap.get("taskAssignee"));
				entity.setLinkName(wsStatus);
				entity.setOperation("01");
				transferHandleService.save(entity);
				
				successStr += "工单号:" + processInstanceId + ";";
			} catch (Exception e) {
				erorrStr += "工单号:" + processInstanceId + ";";
				e.printStackTrace();
			}
		}

		if (!erorrStr.equals("")) {
			content = erorrStr + "处理异常。";
			if (!successStr.equals("")) {
				content = content + successStr + "处理成功。";
			}
		} else {
			content = "处理成功";
		}

		return content;
	}
	
	/**
	 * 批量处理-市运维主任审核
	 * 
	 * @param list
	 * @param paramMap
	 * @return
	 */
	
	private String cityManageDirectorAuditBatchApprove(
			List<Map<String, String>> list, Map<String, String> paramMap) {
		System.out.println("--------进到批量处理-市运维主任审核方法了吗？---------------");
		String content = ""; // 返回结果说明
		String successStr = "";// 成功的工单
		String erorrStr = "";// 失败的工单
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		FormService formService = (FormService) getBean("formService");

		Iterator<Map<String, String>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Map<String, String> map = iterator.next();
			String processInstanceId = map.get("processInstanceId");
			String taskId=map.get("taskId");

			// 遍历开始
			try {
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTicket = null;
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTicket = pnrTicketList.get(0);
				}

				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setThemeId(pnrTicket.getId());
				entity.setTheme(pnrTicket.getTheme());
				entity.setCheckTime(new Date());
				entity.setHandleDescription(paramMap.get("dealPerformer"));
				entity.setAuditor(paramMap.get("userId"));
				entity.setProcessInstanceId(processInstanceId);
				entity.setDoPerson(paramMap.get("taskAssignee"));
				entity.setLinkName(paramMap.get("wsStatus"));
				entity.setOperation("01");
				transferHandleService.save(entity);

				//提交流程
				Map<String, String> taskMap = new HashMap<String, String>();
				//省线路主管审核
				taskMap.put("provinceLineChargeState", "handle");	//省线路主管审核标志
				taskMap.put("provinceLineViceAudit", pnrTicket.getProvinceLine());	//省线路总经理审核
				formService.submitTaskFormData(taskId, taskMap);
				
				successStr += "工单号:" + processInstanceId + ";";
			} catch (Exception e) {
				erorrStr += "工单号:" + processInstanceId + ";";
				e.printStackTrace();
			}
		}

		if (!erorrStr.equals("")) {
			content = erorrStr + "处理异常。";
			if (!successStr.equals("")) {
				content = content + successStr + "处理成功。";
			}
		} else {
			content = "处理成功";
		}

		return content;
	}
	
	/**
	 * 批量处理-市公司副总审核
	 * 
	 * @param list
	 * @param paramMap
	 * @return
	 */
	
	private String cityViceAuditBatchApprove(
			List<Map<String, String>> list, Map<String, String> paramMap) {
		System.out.println("--------进到批量处理-市公司副总审核方法了吗？---------------");
		String content = ""; // 返回结果说明
		String successStr = "";// 成功的工单
		String erorrStr = "";// 失败的工单
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		FormService formService = (FormService) getBean("formService");

		Iterator<Map<String, String>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Map<String, String> map = iterator.next();
			String processInstanceId = map.get("processInstanceId");
			String taskId=map.get("taskId");

			// 遍历开始
			try {
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTicket = null;
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTicket = pnrTicketList.get(0);
				}

				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setThemeId(pnrTicket.getId());
				entity.setTheme(pnrTicket.getTheme());
				entity.setCheckTime(new Date());
				entity.setHandleDescription(paramMap.get("dealPerformer"));
				entity.setAuditor(paramMap.get("userId"));
				entity.setProcessInstanceId(processInstanceId);
				entity.setDoPerson(paramMap.get("taskAssignee"));
				entity.setLinkName(paramMap.get("wsStatus"));
				entity.setOperation("01");
				transferHandleService.save(entity);

				//提交流程
				Map<String, String> taskMap = new HashMap<String, String>();
				//省线路主管审核
				taskMap.put("provinceLineChargeState", "handle");	//省线路主管审核标志
				taskMap.put("provinceLineViceAudit", pnrTicket.getProvinceLine());	//省线路总经理审核
				formService.submitTaskFormData(taskId, taskMap);
				
				successStr += "工单号:" + processInstanceId + ";";
			} catch (Exception e) {
				erorrStr += "工单号:" + processInstanceId + ";";
				e.printStackTrace();
			}
		}

		if (!erorrStr.equals("")) {
			content = erorrStr + "处理异常。";
			if (!successStr.equals("")) {
				content = content + successStr + "处理成功。";
			}
		} else {
			content = "处理成功";
		}

		return content;
	}
	
	/**
	 * 批量处理-省线路主管审核
	 * 
	 * @param list
	 * @param paramMap
	 * @return
	 */
	
	private String provinceLineExamineBatchApprove(
			List<Map<String, String>> list, Map<String, String> paramMap) {
		System.out.println("--------进到批量处理-省线路主管审核方法了吗？---------------");
		String content = ""; // 返回结果说明
		String successStr = "";// 成功的工单
		String erorrStr = "";// 失败的工单
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		FormService formService = (FormService) getBean("formService");

		Iterator<Map<String, String>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Map<String, String> map = iterator.next();
			String processInstanceId = map.get("processInstanceId");
			String taskId=map.get("taskId");

			// 遍历开始
			try {
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTicket = null;
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTicket = pnrTicketList.get(0);
				}
				
				//提交流程
				Map<String, String> taskMap = new HashMap<String, String>();
				//省线路主管审核
				taskMap.put("provinceLineChargeState", "handle");	//省线路主管审核标志
				taskMap.put("provinceLineViceAudit", pnrTicket.getProvinceLine());	//省线路总经理审核
				formService.submitTaskFormData(taskId, taskMap);
				
				//发短信
				String	msg = TASK_MESSAGE+"  "+TASK_NO_STR+processInstanceId+","+TASK_TITLE_STR+pnrTicket.getTheme()+","+TASK_WORKORDERDEGREE+
				getDictNameByDictid(pnrTicket.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTicket.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTicket.getSubTypeName()+"。";
				CommonUtils.sendMsg(msg, pnrTicket.getProvinceLine());

				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setThemeId(pnrTicket.getId());
				entity.setTheme(pnrTicket.getTheme());
				entity.setCheckTime(new Date());
				entity.setHandleDescription(paramMap.get("dealPerformer"));
				entity.setAuditor(paramMap.get("userId"));
				entity.setProcessInstanceId(processInstanceId);
				entity.setDoPerson(paramMap.get("taskAssignee"));
				entity.setLinkName(paramMap.get("wsStatus"));
				entity.setOperation("01");
				transferHandleService.save(entity);
				
				successStr += "工单号:" + processInstanceId + ";";
			} catch (Exception e) {
				erorrStr += "工单号:" + processInstanceId + ";";
				e.printStackTrace();
			}
		}

		if (!erorrStr.equals("")) {
			content = erorrStr + "处理异常。";
			if (!successStr.equals("")) {
				content = content + successStr + "处理成功。";
			}
		} else {
			content = "处理成功";
		}

		return content;
	}
	
	/**
	 * 批量处理-省线路总经理审核
	 * 
	 * @param list
	 * @param paramMap
	 * @return
	 */
	
	private String provinceLineViceAuditBatchApprove(
			List<Map<String, String>> list, Map<String, String> paramMap) {
		System.out.println("--------进到批量处理-省线路总经理审核方法了吗？---------------");
		String content = ""; // 返回结果说明
		String successStr = "";// 成功的工单
		String erorrStr = "";// 失败的工单
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		FormService formService = (FormService) getBean("formService");

		Iterator<Map<String, String>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Map<String, String> map = iterator.next();
			String processInstanceId = map.get("processInstanceId");
			String taskId=map.get("taskId");

			// 遍历开始
			try {
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTicket = null;
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTicket = pnrTicketList.get(0);
				}
				
				//提交流程
				Map<String, String> taskMap = new HashMap<String, String>();
				//省线路总经理审核
				taskMap.put("provinceLineViceState", "handle");	//省线路总经理审核标志
				taskMap.put("provinceManageCharge", pnrTicket.getProvinceManageCharge());	//省运维主管审核人
				formService.submitTaskFormData(taskId, taskMap);
				
				//发短信
				String	msg = TASK_MESSAGE+"  "+TASK_NO_STR+processInstanceId+","+TASK_TITLE_STR+pnrTicket.getTheme()+","+TASK_WORKORDERDEGREE+
				getDictNameByDictid(pnrTicket.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTicket.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTicket.getSubTypeName()+"。";
				CommonUtils.sendMsg(msg, pnrTicket.getProvinceManageCharge());

				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setThemeId(pnrTicket.getId());
				entity.setTheme(pnrTicket.getTheme());
				entity.setCheckTime(new Date());
				entity.setHandleDescription(paramMap.get("dealPerformer"));
				entity.setAuditor(paramMap.get("userId"));
				entity.setProcessInstanceId(processInstanceId);
				entity.setDoPerson(paramMap.get("taskAssignee"));
				entity.setLinkName(paramMap.get("wsStatus"));
				entity.setOperation("01");
				transferHandleService.save(entity);
				
				successStr += "工单号:" + processInstanceId + ";";
			} catch (Exception e) {
				erorrStr += "工单号:" + processInstanceId + ";";
				e.printStackTrace();
			}
		}

		if (!erorrStr.equals("")) {
			content = erorrStr + "处理异常。";
			if (!successStr.equals("")) {
				content = content + successStr + "处理成功。";
			}
		} else {
			content = "处理成功";
		}

		return content;
	}
	
	/**
	 * 批量处理-省运维主管审核
	 * 
	 * @param list
	 * @param paramMap
	 * @return
	 */
	
	private String provinceManageExamineBatchApprove(
			List<Map<String, String>> list, Map<String, String> paramMap) {
		String content = ""; // 返回结果说明
		String successStr = "";// 成功的工单
		String erorrStr = "";// 失败的工单
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		FormService formService = (FormService) getBean("formService");

		Iterator<Map<String, String>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Map<String, String> map = iterator.next();
			String processInstanceId = map.get("processInstanceId");
			String taskId=map.get("taskId");

			// 遍历开始
			try {
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTicket = null;
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTicket = pnrTicketList.get(0);
				}

				//提交流程
				Map<String, String> taskMap = new HashMap<String, String>();
				//省运维主管审核
				taskMap.put("provinceManageChargeState", "handle");	//省运维主管审核标志
				taskMap.put("provinceManageVice", pnrTicket.getProvinceManage());	//省运维总经理审批人
				formService.submitTaskFormData(taskId, taskMap);
				
				//发短信
				String	msg = TASK_MESSAGE+"  "+TASK_NO_STR+processInstanceId+","+TASK_TITLE_STR+pnrTicket.getTheme()+","+TASK_WORKORDERDEGREE+
				getDictNameByDictid(pnrTicket.getWorkOrderDegree())+","+TASK_WORKORDERTYPE+pnrTicket.getWorkOrderTypeName()+","+TASK_SUBTYPE+pnrTicket.getSubTypeName()+"。";
				CommonUtils.sendMsg(msg, pnrTicket.getProvinceManage());
				
				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setThemeId(pnrTicket.getId());
				entity.setTheme(pnrTicket.getTheme());
				entity.setCheckTime(new Date());
				entity.setHandleDescription(paramMap.get("dealPerformer"));
				entity.setAuditor(paramMap.get("userId"));
				entity.setProcessInstanceId(processInstanceId);
				entity.setDoPerson(paramMap.get("taskAssignee"));
				entity.setLinkName(paramMap.get("wsStatus"));
				entity.setOperation("01");
				transferHandleService.save(entity);
				
				successStr += "工单号:" + processInstanceId + ";";
			} catch (Exception e) {
				erorrStr += "工单号:" + processInstanceId + ";";
				e.printStackTrace();
			}
		}

		if (!erorrStr.equals("")) {
			content = erorrStr + "处理异常。";
			if (!successStr.equals("")) {
				content = content + successStr + "处理成功。";
			}
		} else {
			content = "处理成功";
		}

		return content;
	}
	
	
	/**
	 * 批量处理-省运维总经理审批
	 * 
	 * @param list
	 * @param paramMap
	 * @return
	 */
	private String provinceManageViceAuditBatchApprove(
			List<Map<String, String>> list, Map<String, String> paramMap) {
		String content = ""; // 返回结果说明
		String successStr = "";// 成功的工单
		String erorrStr = "";// 失败的工单
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		Iterator<Map<String, String>> iterator = list.iterator();
		while (iterator.hasNext()) {
			Map<String, String> map = iterator.next();
			String processInstanceId = map.get("processInstanceId");

			// 遍历开始
			try {
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTicket = null;
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTicket = pnrTicketList.get(0);
				}

				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setThemeId(pnrTicket.getId());
				entity.setTheme(pnrTicket.getTheme());
				entity.setCheckTime(new Date());
				entity.setHandleDescription(paramMap.get("dealPerformer"));
				entity.setAuditor(paramMap.get("userId"));
				entity.setProcessInstanceId(processInstanceId);
				entity.setDoPerson(paramMap.get("taskAssignee"));
				//entity.setLinkName(paramMap.get("wsStatus"));
				entity.setOperation("01");
				transferHandleService.save(entity);

				pnrTicket.setState(8);
				pnrTransferOfficeService.save(pnrTicket);
				successStr += "工单号:" + processInstanceId + ";";
			} catch (Exception e) {
				erorrStr += "工单号:" + processInstanceId + ";";
				e.printStackTrace();
			}
		}

		if (!erorrStr.equals("")) {
			content = erorrStr + "处理异常。";
			if (!successStr.equals("")) {
				content = content + successStr + "处理成功。";
			}
		} else {
			content = "处理成功";
		}

		return content;
	}
	
	/**
	 * 打开批量回退子窗口
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward openBatchRegressionView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("openBatchRegressionView");
	}
	
	/**
	 * 批量回退-总方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doBatchRegression(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long startTime=System.currentTimeMillis();
		PrintWriter out = response.getWriter();
		// 接受参数
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid(); // 当前操作人
		String wsStatus = request.getParameter("wsStatus"); // 工单所处环节
		String rejectReason = StaticMethod.nullObject2String(request
				.getParameter("rejectReason")); // 驳回原因
		String selectedIDs = request.getParameter("selectedIDs"); // 任务号+流程号+当前环节
		
		
		String result="";	//批量回退结果 success error
		String content="";	//回复内容
		String returnJson="";
		String error = "";
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		//解析任务号+流程号+所在环节
		try{
			String[] ids = selectedIDs.split("#");
			//判断是否有工单需要处理
			if(ids!=null && ids.length>0){
				for(int i=0;i<ids.length;i++){
					List<PnrTransferOffice> pnrTicketList = null;
					PnrTransferOffice pnrTicket = null;
					String[] id = ids[i].split(",");
					String taskId = id[0]==null?"":id[0].trim();
					String processInstanceId = id[1]==null?"":id[1].trim();
					String taskKey = id[2]==null?"":id[2].trim();
					//获取该处理工单
					Search search = new Search();
					search.addFilterEqual("processInstanceId", processInstanceId);
					pnrTicketList = pnrTransferOfficeService.search(search);
					if (pnrTicketList != null && pnrTicketList.size() > 0) {
						pnrTicket = pnrTicketList.get(0);
						//改变回退工单状态
						pnrTicket.setRollbackFlag("1");
					}
					//获取限制金额--start
					String firstMoneyLimitDicId = precheckRoleModel.getFirstMoneyLimitDicId();
					ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
					
					TawSystemDictType dictypeFirst = mgr.getDictByDictId(firstMoneyLimitDicId);
					double doubleFirst = 0L;
					if(dictypeFirst != null && StaticMethod.getFloatValue(dictypeFirst.getDictRemark())>-1){
						doubleFirst = Double.parseDouble(dictypeFirst.getDictRemark());
					}else{
						request.setAttribute("msg","市运维主任审批下-金额限制，有问题；请联系管理员");
						return mapping.findForward("failure");				
					}
					//获取限制金额--end
					FormService formService = (FormService) getBean("formService");
					Map<String, String> taskMap = new HashMap<String, String>();
					String nextPerson = "";
					String beforeAssigness = "";//流程上一步人员
					String transferHandleFlag = "";//回退标志
					String returnFlag = "rollback";
					if("provinceLineExamine".equals(taskKey)){//省线路主管
						//工单发起填写的预算金额
						double projectAmount = 0.0;
						if (pnrTicketList != null && pnrTicketList.size() > 0) {
							pnrTicket = pnrTicketList.get(0);
							projectAmount = pnrTicket.getProjectAmount()==null?0.0: pnrTicket.getProjectAmount();
						}
						if(projectAmount>doubleFirst){//回退到市公司总经理审核
							nextPerson = "nextTaskAssignee";
							beforeAssigness=pnrTicket.getCityGS();
							transferHandleFlag="provinceLineChargeState";
						}else if(projectAmount<=doubleFirst){//回退到市运维审核
							nextPerson = "cityManageDirectorAudit";
							beforeAssigness=pnrTicket.getCityManageDirector();
							transferHandleFlag="provinceLineChargeState";
							returnFlag = "cityManage";
						}
					}else if("provinceLineViceAudit".equals(taskKey)){//省线路总经理
						nextPerson = "nextTaskAssignee";
						beforeAssigness=pnrTicket.getProvinceLineCharge();
						transferHandleFlag="provinceLineViceState";
					}else if("provinceManageExamine".equals(taskKey)){//省运维主管
						nextPerson = "provinceLineViceAudit";
						beforeAssigness=pnrTicket.getProvinceLine();
						transferHandleFlag="provinceManageChargeState";
						// 删除会审辅助表记录的辅助信息
						pnrTransferOfficeService
								.deleteCountersignInfo(processInstanceId);
						pnrTicket.setState(7);// 停止会审
						pnrTransferOfficeService.save(pnrTicket);
					}else if("provinceManageViceAudit".equals(taskKey)){//省运维总经理
						nextPerson = "provinceManageCharge";
						beforeAssigness=pnrTicket.getProvinceManageCharge();
						transferHandleFlag="provinceManageViceState";
					}
					taskMap.put(nextPerson,beforeAssigness );
					taskMap.put(transferHandleFlag, returnFlag);
					formService.submitTaskFormData(taskId, taskMap);
				
					// 保存流转信息
					PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
					entity.setThemeId(pnrTicket.getId());
					entity.setTheme(pnrTicket.getTheme());
					Date createTime = new Date();// 审批时间
					entity.setCheckTime(createTime);
					entity.setTaskId(taskId);
					entity.setProcessInstanceId(processInstanceId);
					entity.setAuditor(userId);
					entity.setHandleDescription(rejectReason);
					entity.setLinkName(taskKey);
					entity.setOperation("02");
					transferHandleService.save(entity);
				
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			returnJson = "{\"result\":\"error\",\"content\":\"工单ID解析异常\"}";
			out.print(returnJson);
			content="批量回退失败";
			return null;
		}
		
		

		// 给前台返回异常信息
		if (content.equals("")) {
			returnJson = "{\"result\":\"success\",\"content\":\"批量回退成功\"}";
		} else {
			returnJson = "{\"result\":\"error\",\"content\":\"" + content
					+ "\"}";
		}
		out.print(returnJson);
		long endTime=System.currentTimeMillis(); 
		return null;

	}
	
	/**
	 * 批量回退-共用
	 * 
	 * @param list
	 * @param paramMap
	 * @return
	 */
	
	private String communalBatchRegression(List<Map<String, String>> list,
			Map<String, String> paramMap) {
		System.out.println("--------进到批量回退-共用方法了吗？---------------");
		String content = ""; // 返回结果说明
		String successStr = "";// 成功的工单
		String erorrStr = "";// 失败的工单
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		FormService formService = (FormService) getBean("formService");

		String wsStatus = paramMap.get("wsStatus");
		Iterator<Map<String, String>> iterator = list.iterator();
		while (iterator.hasNext()) {
			String tempMsg = "";
			Map<String, String> map = iterator.next();
			String processInstanceId = map.get("processInstanceId");
			String taskId = map.get("taskId");

			// 遍历开始
			try {
				List<PnrTransferOffice> pnrTicketList = null;
				PnrTransferOffice pnrTicket = null;
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				pnrTicketList = pnrTransferOfficeService.search(search);
				if (pnrTicketList != null && pnrTicketList.size() > 0) {
					pnrTicket = pnrTicketList.get(0);
				}

				Map<String, String> taskMap = new HashMap<String, String>();
				String msgPerson = "";
				String linkName = "";
				if (wsStatus.equals("provinceLineExamine")) {// 省线路主管
					// provinceLineExamine
					double projectAmount = 0.0;
					projectAmount = pnrTicket.getProjectAmount() == null ? -1
							: pnrTicket.getProjectAmount();
					String firstMoneyLimitDicId = precheckRoleModel
							.getFirstMoneyLimitDicId();
					TawSystemDictType dictypeFirst = mgr
							.getDictByDictId(firstMoneyLimitDicId);
					double doubleFirst = 0L;
					if (dictypeFirst != null
							&& StaticMethod.getFloatValue(dictypeFirst
									.getDictRemark()) > -1) {
						doubleFirst = Double.parseDouble(dictypeFirst
								.getDictRemark());
					} else {
						tempMsg = "是否干线处-金额限制，有问题；请联系管理员";
						erorrStr += "工单号:" + processInstanceId + ":" + tempMsg
								+ ";";
						continue;
					}
					if (projectAmount > doubleFirst) {// 回退到市公司副总
						taskMap.put("nextTaskAssignee", pnrTicket.getCityGS());
						taskMap.put("provinceLineChargeState", "rollback");
						msgPerson = pnrTicket.getCityGS();
					} else {// 回退到市运维主任
						taskMap.put("cityManageDirectorAudit", pnrTicket
								.getCityManageDirector());
						taskMap.put("provinceLineChargeState", "cityManage");
						msgPerson = pnrTicket.getCityManageDirector();
					}
					linkName = "provinceLineExamine";
				} else if (wsStatus.equals("provinceLineViceAudit")) {// 省线路总经理
					// provinceLineViceAudit
					taskMap.put("nextTaskAssignee", pnrTicket
							.getProvinceLineCharge());
					taskMap.put("provinceLineViceState", "rollback");
					msgPerson = pnrTicket.getProvinceLineCharge();
					linkName = "provinceLineViceAudit";
				} else if (wsStatus.equals("provinceManageExamine")) {// 省运维主管
					// provinceManageExamine
					taskMap.put("provinceLineViceAudit", pnrTicket
							.getProvinceLine());
					taskMap.put("provinceManageChargeState", "rollback");
					msgPerson = pnrTicket.getProvinceLine();
					linkName = "provinceManageExamine";
				} else if (wsStatus.equals("provinceManageViceAudit")) {// 省运维总经理
					// provinceManageViceAudit
					taskMap.put("provinceManageCharge", pnrTicket
							.getProvinceManageCharge());
					taskMap.put("provinceManageViceState", "rollback");
					msgPerson = pnrTicket.getProvinceManageCharge();
					linkName = "provinceManageViceAudit";
				}

				// 调用流程引擎
				formService.submitTaskFormData(taskId, taskMap);

				// 发短信
				String msg = TASK_MESSAGE + "  " + TASK_NO_STR
						+ pnrTicket.getProcessInstanceId() + ","
						+ TASK_TITLE_STR + pnrTicket.getTheme() + ","
						+ TASK_WORKORDERDEGREE
						+ getDictNameByDictid(pnrTicket.getWorkOrderDegree())
						+ "," + TASK_WORKORDERTYPE
						+ pnrTicket.getWorkOrderTypeName() + "," + TASK_SUBTYPE
						+ pnrTicket.getSubTypeName() + "。";
				
				CommonUtils.sendMsg(msg, msgPerson);

				// 省运维主管环节驳回，停止会审
				if (wsStatus.equals("provinceManageExamine")) {
					// 删除会审辅助表记录的辅助信息
					pnrTransferOfficeService
							.deleteCountersignInfo(processInstanceId);
					pnrTicket.setState(7);// 停止会审
					pnrTransferOfficeService.save(pnrTicket);
				}

				// 保存流转信息
				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setThemeId(pnrTicket.getId());
				entity.setTheme(pnrTicket.getTheme());
				Date createTime = new Date();// 审批时间
				entity.setCheckTime(createTime);
				entity.setTaskId(taskId);
				entity.setProcessInstanceId(processInstanceId);
				entity.setAuditor(paramMap.get("userId"));
				entity.setHandleDescription(paramMap.get("rejectReason"));
				entity.setLinkName(linkName);
				entity.setOperation("02");
				transferHandleService.save(entity);

				successStr += "工单号:" + processInstanceId + ";";
			} catch (Exception e) {
				erorrStr += "工单号:" + processInstanceId + ";";
				e.printStackTrace();
			}
		}

		if (!erorrStr.equals("")) {
			content = erorrStr + "处理异常。";
			if (!successStr.equals("")) {
				content = content + successStr + "处理成功。";
			}
		} else {
			content = "处理成功";
		}

		return content;
	}
	
	/**
	 * 已处理的工单
	 * 
	 * @return
	 */
	public ActionForward listInvolvedUnfinishedProcessInstances(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		int pageSize = CommonUtils.PAGE_SIZE;
		String tempPageSize = request.getParameter("pagesize");
		if (tempPageSize != null && !"".equals(tempPageSize)) {
			pageSize = Integer.parseInt(tempPageSize);
		}
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		//获得查询条件
		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		String status = request.getParameter("status");
		
		//封装查询条件
		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setSendStartTime(sendStartTime);
		conditionQueryModel.setSendEndTime(sendEndTime);
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		conditionQueryModel.setStatus(status);
		
		IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");
		
		//已处理工单条数
		int total = roomDemolitionService.getHaveProcessCount(processDefinitionKey,userId,conditionQueryModel);
		
		//已处理工单明细
		List<RoomDemolitionModel> rPnrTicketList = roomDemolitionService.getHaveProcessList(processDefinitionKey,userId,conditionQueryModel,firstResult,
				endResult, pageSize);
		
		request.setAttribute("taskList", rPnrTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
		request.setAttribute("wsStatus", status);
		return mapping.findForward("involvedProcessInstancesList");
	}
	
	/**
	 * 详细信息呈现
	 */
	public ActionForward gotoDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String processInstanceId = request.getParameter("processInstanceId");
		//1.取工单主表信息
		IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		SearchResult t = roomDemolitionService.searchAndCount(search);
		List<RoomDemolition> list = t.getResult();
		//2.附件列表回显
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		List<TawCommonsAccessoriesForm> accessoriesList  = pnrTransferOfficeService.getRoomDemolitionAccessoriesList(processInstanceId, "");
		request.setAttribute("sheetAccessories", accessoriesList);
        request.setAttribute("roomDemolition", list.get(0));
		request.setAttribute("processInstanceId", processInstanceId);
		
		return mapping.findForward("gotoDetail");

	}
	
	/**
	 * 删除流程实例
	 */
	public ActionForward deleteProcessInstance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("##############详细信息呈现");

		String processInstanceId = request.getParameter("processInstanceId");
		RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");
		String deleteReason="垃圾数据";
		runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
		
		return mapping.findForward("showSuccess");
	}
	
	/**
	 * 由我创建的工单查询
	 * 
	 * @return
	 */
	public ActionForward listRunningProcessInstances(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
	//	HistoryService historyService = (HistoryService) getBean("historyService");
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		String status = request.getParameter("status");
		

		IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");
		
		//由我创建的工单条数
		int total = roomDemolitionService.getByCreatingWorkOrderCount(processDefinitionKey,userId,sendStartTime,
				sendEndTime,wsNum,wsTitle,status);
		
		//由我创建的工单明细
		List<TaskModel> rPnrTicketList = roomDemolitionService.getByCreatingWorkOrderList(processDefinitionKey,userId, sendStartTime,
				sendEndTime, wsNum, wsTitle, status, firstResult,
				endResult, pageSize);

		request.setAttribute("taskList", rPnrTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
		request.setAttribute("wsStatus", status);
		return mapping.findForward("runningProcessInstancesList");
	}
	
	/**
	 * 按照流程实例ID删除流程
	 */
	public ActionForward removeProcessInstance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String processInstanceId = request.getParameter("processInstanceId");
		RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");
		
		try {
			runtimeService.deleteProcessInstance(processInstanceId, "delete");
		} catch (Exception e) {
			e.printStackTrace();
		}

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		SearchResult<PnrTransferOffice> tResult = pnrTransferOfficeService
				.searchAndCount(search);
		List<PnrTransferOffice> eList = tResult.getResult();
		if (eList != null && eList.size() > 0) {
			PnrTransferOffice entity = eList.get(0);
			entity.setState(TASK_DELETE_STATE);
			pnrTransferOfficeService.save(entity);
		}

		return mapping.findForward("delete_success");
	}
	
	/**
	 * 工单抓回 查询
	 * 
	 * @return
	 */
	public ActionForward getBack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		String userTaskFlag=request.getParameter("userTaskFlag");

		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);

		IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");
		// 工单管理-传输局工单-预检预修工单-待回复工单 集合条数
		int total = 0;
		try {
			total = roomDemolitionService.getBackSheetCount(
					processDefinitionKey,userId,
					conditionQueryModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 工单管理-传输局工单-预检预修工单-待回复工单 集合
		List<TaskModel> rPnrTransferList = null;
		try {
			rPnrTransferList = roomDemolitionService
					.getBackSheetList(
							processDefinitionKey,userId,
							conditionQueryModel,
							firstResult, endResult, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
		return mapping.findForward("getBack");
	}
/**
	 * 抓回工单
	 * 
	 * @return
	 */
	public ActionForward catchBackWorkOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String processInstanceId = request.getParameter("processInstanceId");
		String taskId = request.getParameter("taskId");
		String taskDefKey = request.getParameter("taskDefKey");

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTransferOffice = null;
		if (pnrTicketList != null) {
			pnrTransferOffice = pnrTicketList.get(0);
		}
		
		FormService formService = (FormService) getBean("formService");
		Map<String, String> map = new HashMap<String, String>();
		if(taskDefKey.equals("workOrderCheck")){//工单发起审核
			map.put("initiator", pnrTransferOffice.getCreateWork());
			map.put("workOrderState", "rollback");
		}else if(taskDefKey.equals("cityLineExamine")){//市线路主管审核
			map.put("workOrderCheck",pnrTransferOffice.getSecondCreateWork());
			map.put("cityLineChargeState", "rollback");
		}else if(taskDefKey.equals("cityLineDirectorAudit")){//市线路主任审核
			map.put("cityLineChargeAduit",pnrTransferOffice.getCityLineCharge());
			map.put("cityLineDirectorState", "rollback");
		}else if(taskDefKey.equals("cityManageExamine")){//市运维主管审核
			map.put("cityLineDirectorAudit",pnrTransferOffice.getCityLineDirector());
			map.put("cityManageChargeState", "rollback");
		}else if(taskDefKey.equals("cityManageDirectorAudit")){//市运维主任审核
			map.put("cityManageChargeAudit", pnrTransferOffice.getCityManageCharge());
			map.put("cityManageDirectorState", "rollback");
		}
		formService.submitTaskFormData(taskId, map);
		
		//2代表抓回
		pnrTransferOffice.setRollbackFlag("2");
		pnrTransferOfficeService.save(pnrTransferOffice);
		
		return mapping.findForward("catchBackSuccess");
	}
	
	
	
	/**
	 * 已归档工单
	 * 
	 * @return
	 */
	public ActionForward listInvolvedFinishedProcessInstances(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		//String status = request.getParameter("status");
		String status = "archived";
		String themeInterface="interface";
		
		IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");
		
		//已归档工单条数
		int total = roomDemolitionService.getArchivedCount(processDefinitionKey,userId,sendStartTime,
				sendEndTime,wsNum,wsTitle,status,themeInterface);
		
		//已归档工单明细
		List<TaskModel> rPnrTicketList = roomDemolitionService.getArchivedList(processDefinitionKey,userId, sendStartTime,
				sendEndTime, wsNum, wsTitle, status,themeInterface,firstResult,
				endResult, pageSize);
		
		request.setAttribute("taskList", rPnrTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
		request.setAttribute("wsStatus", status);
		return mapping.findForward("involvedFinishedList");
	}
	
	
	/**
	 * 手动归档 处理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doOneWorkOrderArchiving(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String taskId = request.getParameter("taskId");
		String processInstanceId = request.getParameter("processInstanceId");
		String theme = request.getParameter("title");
		String themeId = request.getParameter("titleId");
		// 审批时间
		Date createTime = new Date();
		String linkName = request.getParameter("linkName");

		// 1.保存回复信息
		IRoomDemolitionHandleService roomDemolitionHandleService = (IRoomDemolitionHandleService) getBean("roomDemolitionHandleService");
		RoomDemolitionHandle entity = new RoomDemolitionHandle();
		entity.setThemeId(themeId);
		entity.setTheme(theme);
		entity.setCheckTime(createTime);
		entity.setHandleDescription("手动归档完成");
		entity.setAuditor(userId);
		entity.setProcessInstanceId(processInstanceId);
		entity.setLinkName(linkName);
		entity.setOperation("01");
		roomDemolitionHandleService.save(entity);

		// 2.更新工单主表
		IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService) getBean("roomDemolitionService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<RoomDemolition> pnrTicketList = roomDemolitionService
				.search(search);
		RoomDemolition roomDemolition = null;

		// 1. 流程提交到下一级
		FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
			}
		}
		formService.submitTaskFormData(taskId, map);
		
		if (pnrTicketList != null) {
			roomDemolition = pnrTicketList.get(0);
			roomDemolition.setArchivingTime(sFormat.parse(sFormat
					.format(new Date())));
			roomDemolition.setState(5);
			//在工单主表中添加流程信息
		    processTaskService.setTvalueOfTask(processInstanceId, "", roomDemolition, RoomDemolition.class, "archive", "已归档",true);	
			roomDemolitionService.save(roomDemolition);
		}

		// 2.删除机房数据
		String resultStr = roomDemolitionService
				.deleteRoomResourceById(roomDemolition.getStationId());
		
		//封装查询条件
		String flag = request.getParameter("flag");
		String condition="";
		if("listview".equals(flag)){
			ConditionQueryModel conditionQueryModel = this.doRequestToConditionQueryModel(request);
			condition=this.jointListBacklogCondition(conditionQueryModel);
		}else if("manualview".equals(flag)){
			condition=request.getParameter("condition");
		}
		request.setAttribute("condition",condition );
		
		String forwardView="deleteRoomError";		
		if(resultStr.equals("success")){
			 request.setAttribute("success", "check");
			 forwardView="showSuccess";		 
		}else{
			request.setAttribute("errorStr",resultStr);
		}
		
		return mapping.findForward(forwardView);
	}
	
	/**
	 * 回单环节修改对应能耗系统是否关站值
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String changeEnergyIsstation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		String energyIsstation = request.getParameter("energyIsstation");
		String processInstanceId = request.getParameter("processInstanceId");
		
		IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");
		if(processInstanceId!=null && !"".equals(processInstanceId)){
			Search search = new Search();
			search.addFilterEqual("processInstanceId",processInstanceId);
			
			SearchResult t = roomDemolitionService.searchAndCount(search);
			List<RoomDemolition> list = t.getResult();
			response.setCharacterEncoding("utf-8");		
			
			if(list!=null && list.size()>0){
				//更改工单紧急程度
				RoomDemolition roomDemolition =list.get(0);
				roomDemolition.setEnergyIsstation(energyIsstation);
				roomDemolitionService.save(roomDemolition);
				response.getWriter().write("对应能耗系统是否关站修改成功！");
			}else{
				response.getWriter().write("对应能耗系统是否关站修改失败！");
				
			}
		}else{
			response.getWriter().write("对应能耗系统是否关站修改失败！");
		}
		return "";
	}
	
		/**
	 * 工单详情中资产清单
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showCreateWorkAssets(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 工单号
		String processInstanceId = StaticMethod.nullObject2String(request
				.getParameter("pid"));
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService) getBean("pnrTransferNewPrecheckService");
		List<RoomAssets> assetsList = pnrTransferNewPrecheckService
				.showAssetsByProcessInstanceId(processInstanceId);
		request.setAttribute("list", assetsList);
		return mapping.findForward("showAssetsView");
	}
	
	/**
	 * 机房拆除工单查询
	 * 
	 * @author chujingang
	 * @title: queryWorkOrder
	 * @date Feb 7, 2015 9:00:21 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ActionForward
	 */
	public ActionForward queryWorkOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pageSize = CommonUtils.PAGE_SIZE;
		String tempPageSize = request.getParameter("pagesize");
		if (tempPageSize != null && !"".equals(tempPageSize)) {
			pageSize = Integer.parseInt(tempPageSize);
		}
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();

		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		String status = request.getParameter("status");
		String roomPici = request.getParameter("roomPici");

		String region = request.getParameter("region");// 地市
		String country = request.getParameter("country");
		// String lineType = request.getParameter("lineType");
		// String provName = request.getParameter("provName");//工单类型
		// String precheckFlag = request.getParameter("precheckFlag");
		String mainSceneId = request.getParameter("mainSceneId");// 场景

		ConditionQueryModel conditionQueryModel = new ConditionQueryModel();
		conditionQueryModel.setSendStartTime(sendStartTime);
		conditionQueryModel.setSendEndTime(sendEndTime);
		conditionQueryModel.setWorkNumber(wsNum);
		conditionQueryModel.setTheme(wsTitle);
		conditionQueryModel.setStatus(status);
		conditionQueryModel.setCity(region);
		conditionQueryModel.setCountry(country);
		conditionQueryModel.setRoomPici(roomPici);
		// conditionQueryModel.setLineType(lineType);
		// conditionQueryModel.setWorkOrderType(provName);
		// conditionQueryModel.setPrecheckFlag(precheckFlag);
		conditionQueryModel.setMainSceneId(mainSceneId);
		conditionQueryModel.setPageSize(Integer.toString(pageSize));

		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", city);
		String areaid = com.boco.eoms.partner.process.util.CommonUtils
				.getAreaIdByDeptId(sessionForm.getDeptid());
		IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService) getBean("roomDemolitionService");
		// 工单集合数
		int total = 0;
		try {
			total = roomDemolitionService.getQueryWorkOrderCount(areaid,
					"backlog", processDefinitionKey, conditionQueryModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 工单集合
		List<RoomDemolitionModel> rPnrTransferList = null;
		try {
			rPnrTransferList = roomDemolitionService.getQueryWorkOrderList(
					areaid, "backlog", processDefinitionKey,
					conditionQueryModel, firstResult, endResult, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String batchApprovalFlag = "no";
		System.out.println("--------------status=" + status);
		if (status != null) {
			// if (status.equals("workOrderCheck") ||
			// status.equals("cityLineExamine")
			// || status.equals("cityLineDirectorAudit")
			// || status.equals("cityManageExamine")
			// || status.equals("cityManageDirectorAudit")
			// || status.equals("cityViceAudit")
			// || status.equals("provinceLineExamine")
			// || status.equals("provinceLineViceAudit")
			// || status.equals("provinceManageExamine")
			// || status.equals("provinceManageViceAudit")) {
			// batchApprovalFlag="yes";
			// }

			if (status.equals("provinceLineExamine")
					|| status.equals("provinceLineViceAudit")
					|| status.equals("provinceManageExamine")
					|| status.equals("provinceManageViceAudit")) {
				batchApprovalFlag = "yes";
			}

		}

		request.setAttribute("taskList", rPnrTransferList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
		request.setAttribute("wsStatus", status);
		request.setAttribute("roomPici",roomPici);
		// request.setAttribute("provName", provName);
		// request.setAttribute("lineType", lineType);
		request.setAttribute("country", country);
		request.setAttribute("region", region);
		// request.setAttribute("precheckFlag", precheckFlag);
		request.setAttribute("batchApprovalFlag", batchApprovalFlag);
		// 登录人，为了在页面区分是否是省级别的四个工作人员（是否显示批量处理按钮）
		request.setAttribute("loginUserId", userId);
		// 返回查询条件
		request.setAttribute("condition", this
				.jointListBacklogCondition(conditionQueryModel));
		return mapping.findForward("queryWorkOrderList");
	}
	
	
}
