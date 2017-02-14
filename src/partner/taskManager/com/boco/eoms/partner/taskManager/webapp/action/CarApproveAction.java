package com.boco.eoms.partner.taskManager.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import utils.PartnerPrivUtils;


import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.resourceInfo.model.Car;
import com.boco.eoms.partner.resourceInfo.service.CarService;
import com.boco.eoms.partner.taskManager.model.CarApprove;
import com.boco.eoms.partner.taskManager.model.CarApproveLink;
import com.boco.eoms.partner.taskManager.model.CarApproveTask;
import com.boco.eoms.partner.taskManager.model.CarTask;
import com.boco.eoms.partner.taskManager.service.ICarApproveLinkService;
import com.boco.eoms.partner.taskManager.service.ICarApproveService;
import com.boco.eoms.partner.taskManager.service.ICarApproveTaskService;
import com.boco.eoms.partner.taskManager.service.ICarTaskService;
import com.boco.eoms.partner.taskManager.util.CarApproveConstants;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class CarApproveAction extends BaseAction {

	/**
	 * 跳转车辆申请页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward toAddCarApplyFrom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String carNumber = StaticMethod.null2String(request.getParameter("carNumber"));
		CarService carService = (CarService)getBean("carService");
		List<Car> carList = carService.getDispatchCar(request);
		request.setAttribute("carList", carList);
		HashMap<String, String> usermap = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
		String flag = usermap.get("isPersonnel");
		String deptid = "";
		if(flag.equals("y")){  //是代维人员
			deptid = this.getUser(request).getDeptid();
			deptid = deptid.substring(0, PartnerPrivUtils.getProvinceDeptLength());
		}else if("admin".equals(this.getUserId(request))){
			deptid = "2";
		}
		request.setAttribute("carNumber", carNumber);
		request.setAttribute("rootid", deptid);
		return mapping.findForward("carApplyFrom");
	}
	
	/**
	 * 车辆申请
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveAddCarApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ICarApproveService carApproveService = (ICarApproveService) getBean("carApproveService");
		ITawSystemUserManager userMgr = (ITawSystemUserManager) ApplicationContextHolder.getInstance().getBean("itawSystemUserManager");
		TawSystemSessionForm sessionForm = this.getUser(request);
		String carNum = StaticMethod.null2String(request.getParameter("carNum"));
		String taskType = StaticMethod.null2String(request.getParameter("taskType"));
		String approveUserId = StaticMethod.null2String(request.getParameter("checkStaff"));
		String taskId = StaticMethod.null2String(request.getParameter("taskId"));
		String taskName = StaticMethod.null2String(request.getParameter("taskName"));
		
		String remark = StaticMethod.null2String(request.getParameter("remark"));
		CarApprove carApprove = new CarApprove();
		carApprove.setApplyUser(sessionForm.getUserid());
		carApprove.setApplyTime(new Date());
		//只有代维人员有部门
		carApprove.setApplyUserDept(carApproveService.getDeptId(sessionForm));
		carApprove.setApproveStatue(0);
		carApprove.setCarNum(carNum);
		TawSystemUser user = userMgr.getTawSystemUserByuserid(approveUserId);
		carApprove.setApproveUser(approveUserId);
		//移动人员不存在部门Id或者是错误数据，前台选择的认为只能是代维人员；
		carApprove.setApproveUserDept(user.getDeptid());
		carApprove.setRemark(remark);
		
		CarApproveLink carApproveLink = new CarApproveLink();
		carApproveLink.setOperateTime(new Date());
		carApproveLink.setOperateUser(sessionForm.getUserid());
		carApproveLink.setOperateType(CarApproveConstants.CAR_APPROVE_NEW);
		carApproveLink.setRemark(remark);
		
		CarTask carTask = new CarTask();
		carTask.setCarNum(carNum);
		carTask.setTaskName(taskName);
		carTask.setTaskId(taskId);
		carTask.setTaskType(taskType);
		carTask.setCarUser(sessionForm.getUserid());//add by fengguangping 2013-05-15
		CarApproveTask carApproveTask = new CarApproveTask();
		carApproveTask.setTaskUser(approveUserId);
		carApproveTask.setStatue(0);
		
		carApproveService.commitCarApprove(carApprove, carApproveLink, carApproveTask, carTask);
		
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/carApprove.do?method=getByMeApplyList");
		actionForward.setRedirect(true);
		
		return actionForward;
	}
	
	/**
	 * 车辆申请列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getCarApplyList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		return mapping.findForward("carApplyList");
	}
	
	/**
	 * 车辆审批列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward toCarApproveList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ICarApproveService carApproveService = (ICarApproveService) getBean("carApproveService");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		
		String whereStr = " where task_user='"+this.getUserId(request)+"' and statue=0 and approve_Statue <>2 and approve_Statue<>-1";
		
		String applyUserName = StaticMethod.null2String(request.getParameter("checkStaff"));
		String prov = StaticMethod.null2String(request.getParameter("prov"));
		String carNum = StaticMethod.null2String(request.getParameter("carNum"));
		String taskType = StaticMethod.null2String(request.getParameter("taskType"));
		String taskName = StaticMethod.null2String(request.getParameter("taskName"));
		
		if(!StringUtils.isEmpty(applyUserName)){
			whereStr += " and apply_user ='"+applyUserName+"' ";
		}
		if(!StringUtils.isEmpty(prov)){
			PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr)getBean("partnerDeptMgr");
			String deptMagId = partnerDeptMgr.getPartnerDept(prov).getDeptMagId();
			whereStr += " and apply_user_dept ='"+deptMagId+"' ";
		}
		if(!StringUtils.isEmpty(carNum)){
			whereStr += " and approve.car_num ='"+carNum+"' ";
		}
		if(!StringUtils.isEmpty(taskType)){
			whereStr += " and task_type='"+taskType+"' ";
		}
		if(!StringUtils.isEmpty(taskName)){
			whereStr += " and task_name = '"+taskName+"' ";
		}
		
		List approveList = carApproveService.getAllCarApproveOrApply(firstResult * CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE, whereStr, "");
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("list", approveList.get(0));
		request.setAttribute("size", approveList.get(1));
		request.setAttribute("carApproveMap", CarApproveConstants.carApproveMap);
		return mapping.findForward("carApproveList");
	}
	
	/**
	 * 跳转到车辆审批
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toCarApproveForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ICarApproveService carApproveService = (ICarApproveService) getBean("carApproveService");
		ICarTaskService carTaskService = (ICarTaskService) getBean("carTaskDaoService");
		String id = StaticMethod.null2String(request.getParameter("id"));
		CarApprove carApprove = carApproveService.find(id);
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.addFilterEqual("carApproveId", id);
		CarTask carTask = carTaskService.searchUnique(search);
		request.setAttribute("carTask", carTask);
		request.setAttribute("carApprove", carApprove);
		return mapping.findForward("carApproveForm");
	}
	
	/**
	 * 车辆审批
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveCarApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ICarApproveService carApproveService = (ICarApproveService) getBean("carApproveService");
		ICarApproveTaskService carApproveTaskService = (ICarApproveTaskService) getBean("carApproveTaskDaoService");
		String carApproveId = StaticMethod.null2String(request.getParameter("carApproveId"));
		//是通过，还是不通过
		String approveStatus = StaticMethod.null2String(request.getParameter("approveStatus"));
		String approveIdea = StaticMethod.null2String(request.getParameter("approveIdea"));
		CarApprove carApprove = carApproveService.find(carApproveId);;
		
		CarApproveTask newTask = new CarApproveTask();
		newTask.setCarApproveId(carApproveId);
		newTask.setStatue(0);
		newTask.setTaskUser(carApprove.getApplyUser());
		
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.addFilterEqual("carApproveId", carApproveId);
		search.addFilterEqual("statue", 0);
		CarApproveTask oldTask = carApproveTaskService.searchUnique(search);
		oldTask.setStatue(1);
		
		CarApproveLink link = new CarApproveLink();
		carApprove.setApproveStatue(Integer.parseInt(approveStatus));
		carApprove.setApproveTime(new Date());
		
		link.setCarApproveId(carApproveId);
		link.setOperateTime(new Date());
		link.setOperateUser(this.getUserId(request));
		link.setRemark(approveIdea);
		if("1".equals(approveStatus)){
			link.setOperateType(CarApproveConstants.CAR_APPROVE_PASS);
		}else{//审批驳回
			link.setOperateType(CarApproveConstants.CAR_APPROVE_RETURN);
		}
		List<Object> list = new ArrayList<Object>();
		list.add(carApprove);
		list.add(link);
		list.add(oldTask);
		list.add(newTask); 
		carApproveService.saveAllObject(list,approveStatus,carApprove.getCarNum());
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/carApprove.do?method=toCarApproveList");
		actionForward.setRedirect(true);
		return actionForward;
	}
	
	/**
	 * 跳转到车辆终止列表页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward toEndCarApplyList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ICarApproveService carApproveService = (ICarApproveService) getBean("carApproveService");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		
		String whereStr = " where approve_statue <> '2' ";
		String applyUserName = StaticMethod.null2String(request.getParameter("checkStaff"));
		String prov = StaticMethod.null2String(request.getParameter("prov"));
		String carNum = StaticMethod.null2String(request.getParameter("carNum"));
		String taskType = StaticMethod.null2String(request.getParameter("taskType"));
		String taskName = StaticMethod.null2String(request.getParameter("taskName"));
		
		if(!StringUtils.isEmpty(applyUserName)){
			whereStr += " and apply_user ='"+applyUserName+"' ";
		}
		if(!StringUtils.isEmpty(prov)){
			PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr)getBean("partnerDeptMgr");
			String deptMagId = partnerDeptMgr.getPartnerDept(prov).getDeptMagId();
			whereStr += " and apply_user_dept ='"+deptMagId+"' ";
		}
		if(!StringUtils.isEmpty(carNum)){
			whereStr += " and approve.car_num ='"+carNum+"' ";
		}
		if(!StringUtils.isEmpty(taskType)){
			whereStr += " and task_type='"+taskType+"' ";
		}
		if(!StringUtils.isEmpty(taskName)){
			whereStr += " and task_name = '"+taskName+"' ";
		}
		
		
		List approveList = carApproveService.getByMeApply(firstResult * CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE, whereStr);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("list", approveList.get(0));
		request.setAttribute("size", approveList.get(1));
		request.setAttribute("carApproveMap", CarApproveConstants.carApproveMap);
		return mapping.findForward("endCarApplyList");
	}
	
	/**
	 * 跳转到车辆终止Form页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toEndCarApplyForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ICarApproveService carApproveService = (ICarApproveService) getBean("carApproveService");
		ICarTaskService carTaskService = (ICarTaskService) getBean("carTaskDaoService");
		String id = StaticMethod.null2String(request.getParameter("id"));
		CarApprove carApprove = carApproveService.find(id);
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.addFilterEqual("carApproveId", id);
		CarTask carTask = carTaskService.searchUnique(search);
		request.setAttribute("carTask", carTask);
		request.setAttribute("carApprove", carApprove);
		return mapping.findForward("endCarApplyForm");
	}
	
	/**
	 * 跳转到车辆驳回列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toRegectCarApplyList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ICarApproveService carApproveService = (ICarApproveService) getBean("carApproveService");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		String whereStr = " where approve_user='"+this.getUserId(request)+"' and approve_statue='-1'";
		List approveList = carApproveService.getAllCarApproveOrApply(firstResult * CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE, whereStr, "");
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("list", approveList.get(0));
		request.setAttribute("size", approveList.get(1));
		request.setAttribute("carApproveMap", CarApproveConstants.carApproveMap);
		return mapping.findForward("regectCarApplyList");
	}
	
	/**
	 * 车辆终止
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward saveEndCarApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		ICarApproveService carApproveService = (ICarApproveService) getBean("carApproveService");
		ICarApproveTaskService carApproveTaskService = (ICarApproveTaskService) getBean("carApproveTaskDaoService");
		String carApproveId = StaticMethod.null2String(request.getParameter("carApproveId"));
		String remark = StaticMethod.null2String(request.getParameter("remark"));
		CarApprove carApprove = carApproveService.find(carApproveId);
		carApprove.setBackTime(new Date());
		carApprove.setBackUser(this.getUserId(request));
		carApprove.setApproveStatue(2);
		
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.addFilterEqual("carApproveId", carApproveId);
		search.addFilterEqual("statue", 0);
		CarApproveTask approveTask = carApproveTaskService.searchUnique(search);
		approveTask.setStatue(1);
		
		CarApproveLink link = new CarApproveLink();
		link.setCarApproveId(carApproveId);
		link.setOperateTime(new Date());
		link.setOperateUser(this.getUserId(request));
		link.setOperateType(CarApproveConstants.CAR_APPROVE_BACK);
		link.setRemark(remark);
		
		List list = new ArrayList();
		list.add(carApprove);
		list.add(approveTask);
		list.add(link);
		System.out.println(carApprove.getCarNum());
		carApproveService.endApplyCar(list, carApprove.getCarNum());
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/carApprove.do?method=toEndCarApplyList");
		actionForward.setRedirect(true);
		return actionForward;
	}
	
	/**
	 * 跳转到车辆归还Form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toBackCarForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ICarApproveService carApproveService = (ICarApproveService) getBean("carApproveService");
		ICarTaskService carTaskService = (ICarTaskService) getBean("carTaskDaoService");
		String id = StaticMethod.null2String(request.getParameter("id"));
		CarApprove carApprove = carApproveService.find(id);
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.addFilterEqual("carApproveId", id);
		CarTask carTask = carTaskService.searchUnique(search);
		request.setAttribute("carTask", carTask);
		request.setAttribute("carApprove", carApprove);
		return mapping.findForward("backCarForm");
	}
	
	/**
	 * 车辆归还
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward saveBackCar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ICarApproveService carApproveService = (ICarApproveService) getBean("carApproveService");
		ICarApproveTaskService carApproveTaskService = (ICarApproveTaskService) getBean("carApproveTaskDaoService");
		String carApproveId = StaticMethod.null2String(request.getParameter("carApproveId"));
		String remark = StaticMethod.null2String(request.getParameter("remark"));
		CarApprove carApprove = carApproveService.find(carApproveId);
		carApprove.setBackTime(new Date());
		carApprove.setBackUser(this.getUserId(request));
		carApprove.setApproveStatue(2);
		
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.addFilterEqual("carApproveId", carApproveId);
		search.addFilterEqual("statue", 0);
		CarApproveTask approveTask = carApproveTaskService.searchUnique(search);
		approveTask.setStatue(1);
		
		CarApproveLink link = new CarApproveLink();
		link.setCarApproveId(carApproveId);
		link.setOperateTime(new Date());
		link.setOperateUser(this.getUserId(request));
		link.setOperateType(CarApproveConstants.CAR_APPROVE_BACK);
		link.setRemark(remark);
		
		List list =  new ArrayList();
		list.add(carApprove);
		list.add(link);
		list.add(approveTask);
		
		carApproveService.backApplyCar(list, carApprove.getCarNum());
		
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/carApprove.do?method=toCarApproveList");
		actionForward.setRedirect(true);
		return actionForward;
	}
	
	/**
	 * 我申请的车辆
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getByMeApplyList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ICarApproveService carApproveService = (ICarApproveService) getBean("carApproveService");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		
		String whereStr = " where apply_user='"+this.getUserId(request)+"'";
		
//		String applyUserName = StaticMethod.null2String(request.getParameter("applyUserName"));
//		String prov = StaticMethod.null2String(request.getParameter("prov"));
		String carNum = StaticMethod.null2String(request.getParameter("carNum"));
		String taskType = StaticMethod.null2String(request.getParameter("taskType"));
		String taskName = StaticMethod.null2String(request.getParameter("taskName"));
		
		if(!StringUtils.isEmpty(carNum)){
			whereStr += " and approve.car_num ='"+carNum+"' ";
		}
		if(!StringUtils.isEmpty(taskType)){
			whereStr += " and task_type='"+taskType+"' ";
		}
		if(!StringUtils.isEmpty(taskName)){
			whereStr += " and task_name = '"+taskName+"' ";
		}
		
		
		List approveList = carApproveService.getByMeApply(firstResult * CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE, whereStr);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("list", approveList.get(0));
		request.setAttribute("size", approveList.get(1));
		request.setAttribute("carApproveMap", CarApproveConstants.carApproveMap);
		return mapping.findForward("byMeApplyList");
	}
	
	/**
	 * 跳转到车辆归还Form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findCarApproveList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ICarApproveService carApproveService = (ICarApproveService) getBean("carApproveService");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		
		String whereStr = " where 1=1 ";
		
		String carNum = StaticMethod.null2String(request.getParameter("carNum"));
		String taskType = StaticMethod.null2String(request.getParameter("taskType"));
		String taskName = StaticMethod.null2String(request.getParameter("taskName"));
		String approveStatue = StaticMethod.null2String(request.getParameter("approveStatue"));
		String applyUser = StaticMethod.null2String(request.getParameter("checkStaff"));
		String prov = StaticMethod.null2String(request.getParameter("prov"));
		String startTime = StaticMethod.null2String(request.getParameter("startTime"));
		String endTime = StaticMethod.null2String(request.getParameter("endTime"));
		
		if(!StringUtils.isEmpty(prov)){
			PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr)getBean("partnerDeptMgr");
			String deptMagId = partnerDeptMgr.getPartnerDept(prov).getDeptMagId();
			whereStr += " and apply_user_dept ='"+deptMagId+"' ";
		}
		if(!StringUtils.isEmpty(approveStatue)){
			whereStr += " and approve_Statue="+approveStatue;
		}
		if(!StringUtils.isEmpty(applyUser)){
			whereStr += " and approve.apply_user ='"+applyUser+"' ";
		}
		if(!StringUtils.isEmpty(startTime)){
			whereStr += " and "+CommonSqlHelper.formatDateTime(startTime)+" <apply_time ";
		}
		
		if(!StringUtils.isEmpty(endTime)){
			whereStr += " and "+CommonSqlHelper.formatDateTime(endTime)+" >apply_time ";
		}
		if(!StringUtils.isEmpty(taskType)){
			whereStr += " and task_type='"+taskType+"' ";
		}
		if(!StringUtils.isEmpty(taskName)){
			whereStr += " and task_name = '"+taskName+"' ";
		}
		if(!StringUtils.isEmpty(carNum)){
			whereStr += " and approve.car_num ='"+carNum+"' ";
		}
		
		List approveList = carApproveService.getByMeApply(firstResult * CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE, whereStr);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("list", approveList.get(0));
		request.setAttribute("size", approveList.get(1));
		request.setAttribute("carApproveMap", CarApproveConstants.carApproveMap);
		return mapping.findForward("findCarApproveList");
	}
	
	/**
	 * 车辆调度详情
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toCarApproveDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ICarApproveService carApproveService = (ICarApproveService) getBean("carApproveService");
		ICarApproveLinkService linkService = (ICarApproveLinkService) getBean("carApproveLinkService");
		ICarTaskService carTaskService = (ICarTaskService) getBean("carTaskDaoService");
		String id = StaticMethod.null2String(request.getParameter("id"));
		CarApprove carApprove = carApproveService.find(id);
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.addFilterEqual("carApproveId", id);
		search.addSortAsc("operateTime");
		SearchResult<CarApproveLink> result = linkService.searchAndCount(search);
		
		Search search1 = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search1);
		search.addFilterEqual("carApproveId", id);
		CarTask carTask = carTaskService.searchUnique(search1);
		
		request.setAttribute("list",result.getResult());
		request.setAttribute("carApprove", carApprove);
		request.setAttribute("carTask", carTask);
		request.setAttribute("carApproveMap", CarApproveConstants.carApproveMap);
		
		return mapping.findForward("carApproveDetail");
	}
}
