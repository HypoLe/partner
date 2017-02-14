package com.boco.eoms.partner.inspect.webapp.action;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.sql.Clob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.CLOB;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.lob.SerializableClob;
import org.joda.time.LocalDate;

import sun.misc.BASE64Decoder;
import utils.PartnerPrivUtils;

import com.boco.activiti.partner.process.model.PnrTaskTicket;
import com.boco.activiti.partner.process.service.IPnrTaskTicketService;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.mobile.util.MobileCommonUtils;
import com.boco.eoms.mobile.util.MobileConstants;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.baseinfo.util.FileUploadProcessor;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.deviceInspect.model.PnrInspectTaskLink;
import com.boco.eoms.partner.deviceInspect.service.PnrInspectTaskLinkService;
import com.boco.eoms.partner.deviceInspect.switchcfg.PnrDeviceInspectSwitchConfig;
import com.boco.eoms.partner.inspect.dao.hibernate.PnrInspectTaskFileDaoHibernate;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanExecuteMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanItemMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMainMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanResMgr;
import com.boco.eoms.partner.inspect.mgr.IPnrInspectTaskFileMgr;
import com.boco.eoms.partner.inspect.mgr.IPnrInspectTrackMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanItem;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;
import com.boco.eoms.partner.inspect.model.PnrInspectTaskFile;
import com.boco.eoms.partner.inspect.model.PnrInspectTrack;
import com.boco.eoms.partner.inspect.util.InspectConstants;
import com.boco.eoms.partner.inspect.util.InspectUtils;
import com.boco.eoms.partner.inspect.util.MapToBeanUtil;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanItemForm;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanMainToListForm;
import com.boco.eoms.partner.inspect.webapp.form.PnrInspectTaskFileForm;
import com.boco.eoms.partner.res.mgr.IPnrTransLineMgr;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * Description:
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 *
 * @author: LEE
 * @version: 1.0
 * Create at:   Jul 18, 2012 3:37:26 PM
 */
public class InspectPlanExecuteAction extends BaseAction {

    /**
     * 跳转到异常信息修改页面
     */

    @SuppressWarnings("unchecked")
    public ActionForward gotoUpdateExcectipnPage(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request, HttpServletResponse response) {
        String exception = request.getParameter("exception");
        request.setAttribute("exception", exception);

        return mapping.findForward("updateException");
    }

    /**
     * 修改异常信息
     */

    @SuppressWarnings("unchecked")
    public ActionForward updateExcectipn(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response) throws IOException {
        String exception = request.getParameter("exception_content");
        String itemId = request.getParameter("inspect_plan_item_id");

        IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr) getBean("inspectPlanItemMgrImpl");
        InspectPlanItem inspectPlanItem = (InspectPlanItem) inspectPlanItemMgr.findById(itemId);
        inspectPlanItem.setExceptionContent(exception);
        inspectPlanItemMgr.save(inspectPlanItem);

        response.getWriter().print("true");
        return null;
    }

    /**
     * 查询巡检计划
     */
    public ActionForward findInspectPlanMainByCheckUserList(ActionMapping mapping, ActionForm form,
                                                            HttpServletRequest request, HttpServletResponse response) {
        Search search = new Search();
        search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
        String currentMonth = request.getParameter("currentMonth");
        TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr) getBean("inspectPlanMainMgr");
        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "list");
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
        search.setMaxResults(CommonConstants.PAGE_SIZE);
        search.addFilterEqual("status", 1);
        search.addSortDesc("createTime");
        search.addFilterEqual("approveStatus", 1);


        String userId = getUserId(request);
        TawSystemSessionForm sysuser = this.getUser(request);
        PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
        PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);

        //关于代维与自维的权限增加-20140416---根据自维公司的deptid开头是201
        boolean isMaintain=true;
        if(null!=user&&user.getDeptId().startsWith("201")){
        	isMaintain=false;
        }
        //关于代维与自维的权限增加-20140416
        
        if (null != user&&isMaintain) {//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是deptMagId like
            String deptMagId = sessionForm.getDeptid();
            if (deptMagId.length() > 6) {
                search.addFilterLike("deptMagId", deptMagId.substring(0, deptMagId.length() - 2) + "%");
            } else {
                search.addFilterLike("deptMagId", deptMagId + "%");
            }
        } else if (!"admin".equals(userId)) {//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like

            ITawSystemDeptManager mgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
            TawSystemDept dept = mgr.getDeptinfobydeptid(sysuser.getDeptid(), "0");
            String areaid = dept.getAreaid();
            PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
            List partnerList = partnerDeptMgr.getPartnerDepts(" and countyId like '" + areaid + "%'");
            List<String> deptMagIdList = new ArrayList<String>();

            for (int i = 0; partnerList != null && i < partnerList.size(); i++) {
                PartnerDept partnerDept = (PartnerDept) partnerList.get(i);

                String deptMagId = StaticMethod.nullObject2String(partnerDept.getDeptMagId());
                if (!deptMagId.equals("")) {
                    deptMagIdList.add(deptMagId);
                }
            }
            if (deptMagIdList != null && deptMagIdList.isEmpty() == false) {
                search.addFilterIn("deptMagId", deptMagIdList);
            } else {//如果没有管理代维公司，则不允许看该界面
                return mapping.findForward("inspectnopriv");
            }
        }


        if (StringUtils.isEmpty(currentMonth)) {
            LocalDate date = new LocalDate();
            search.addFilterEqual("year", date.getYear());
            search.addFilterEqual("month", date.getMonthOfYear());
        }

        SearchResult<InspectPlanMain> searchResult = inspectPlanMainMgr.searchAndCount(search);
        List<InspectPlanMain> list = searchResult.getResult();
        InspectPlanMainToListForm inspectPlanMainToListForm;
        List<InspectPlanMainToListForm> returnList = new ArrayList<InspectPlanMainToListForm>();
        for (int i = 0; i < list.size(); i++) {
            inspectPlanMainToListForm = new InspectPlanMainToListForm();
            try {
                list.get(i).setCreateTime(null);
                BeanUtilsBean2.getInstance().copyProperties(inspectPlanMainToListForm, list.get(i));
//				Map<String,Integer> map = inspectPlanResMgr.queryTotalAndHasDoneNum(inspectPlanMainToListForm.getId());
                inspectPlanMainToListForm.setHasDone(list.get(i).getResDoneNum());
                inspectPlanMainToListForm.setResNumber(list.get(i).getResNum());
//				inspectPlanMainToListForm.setPlanNumber(map.get("planCount"));
                inspectPlanMainToListForm.setPlanNumber(list.get(i).getResPlanDoneNum());
                returnList.add(inspectPlanMainToListForm);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("yesOrNoMap", InspectConstants.yesOrNoMap);
        request.setAttribute("approveStatusMap", InspectConstants.approveStatusMap);
        request.setAttribute("list", returnList);
        request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
        request.setAttribute("bsfrList", searchResult.getResult());
        request.setAttribute("size", searchResult.getTotalCount());
        request.setAttribute("carApprove", StaticMethod.null2String(request.getParameter("carApprove")));
        return mapping.findForward("findInspectPlanMainByCheckUserList");
    }

    /**
     * 巡检资源执行列表
     */
    @SuppressWarnings("unchecked")
    public ActionForward getInspectPlanMainDetail(ActionMapping mapping, ActionForm form,
                                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
        IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr) getBean("inspectPlanMainMgr");
        IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
        TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        String id = request.getParameter("id");
        String resName = request.getParameter("resName");
        String inspectCycle = request.getParameter("inspectCycle");
        String executeObject = request.getParameter("executeObject");
        String inspectState = request.getParameter("inspectState");
        InspectPlanMain planMain = inspectPlanMainMgr.find(id);
        request.setAttribute("planMain", planMain);

        final Integer pageSize = CommonConstants.PAGE_SIZE;
        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "list");
        final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        String userId = getUserId(request);
        TawSystemSessionForm sysuser = this.getUser(request);
//		PartnerUserMgr  partnerUserMgr = (PartnerUserMgr)getBean("partnerUserMgr");
//		PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);
        String whereStr = " where ";
        whereStr += " planId='" + id + "'";
        Map map1 = PartnerPrivUtils.userIsPersonnel(request);
        String flag = map1.get("isPersonnel").toString();

        if ("y".equals(flag)) {//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept like
            whereStr += "  and executeDept like '" + map1.get("deptMagId").toString() + "%'";
        } else if (!"admin".equals(userId)) {//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like

            ITawSystemDeptManager mgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");

            TawSystemDept dept = mgr.getDeptinfobydeptid(sysuser.getDeptid(), "0");

            PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");


            PartnerDept partnerDept = partnerDeptMgr.getPartnerDept(sysuser.getDeptid());

//			whereStr += " executeDept like '"+ partnerDept.get +"%'";

            String areaid = dept.getAreaid();
            if (areaid != null && !areaid.trim().equals("")) {
                whereStr += "  and country like '" + areaid + "%'";
            } else {//如果移动人员所在部门没有地域，则不允许查看
                return mapping.findForward("inspectnopriv");
            }
        }

        if (!StringUtils.isEmpty(resName)) {
            whereStr += "  and  resName like '%" + resName + "%'";
        }
        if (!StringUtils.isEmpty(inspectCycle)) {
            whereStr += "  and  inspectCycle='" + inspectCycle + "'";
        }
        if (!StringUtils.isEmpty(executeObject)) {
            whereStr += "  and  executeObject='" + executeObject + "'";
        }
        if (!StringUtils.isEmpty(inspectState)) {
            whereStr += "  and  inspectState='" + inspectState + "'";
        }

        if (!StringUtils.isEmpty(request.getParameter("tlDis"))) {
            whereStr += " and tlDis like '" + request.getParameter("tlDis") + "%'";
        }
        if (!StringUtils.isEmpty(request.getParameter("tlWire"))) {
            whereStr += " and tlWire like '" + request.getParameter("tlWire") + "%'";
        }
        if (!StringUtils.isEmpty(request.getParameter("tlSeg"))) {
            whereStr += " and tlSeg like '" + request.getParameter("tlSeg") + "%'";
        }

        Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex, pageSize, whereStr);
        List list = (List) map.get("result");
        request.setAttribute("list", list);
        request.setAttribute("resultSize", map.get("total"));
        request.setAttribute("pageSize", pageSize);
        PnrDeviceInspectSwitchConfig transLineswitch = (PnrDeviceInspectSwitchConfig) request.getSession().getServletContext().getAttribute("pnrInspect2SwitchConfig");
        if ("1122502".equals(planMain.getSpecialty()) && transLineswitch.isOpenTransLineInspect()) {
            request.setAttribute("transLine", "yes");
        }
        request.setAttribute("cycleMap", InspectConstants.cycleMap);
        request.setAttribute("pageType", request.getParameter("pageType"));

        if (MobileCommonUtils.isAndroidRequest(request)) {
            com.boco.eoms.mobile.util.TransLineHandler.handRequestLineSeg(request, response);
            return null;
        }
        return mapping.findForward("inspectPlanMainDetailByCheckUser");
    }

    public ActionForward gotoUpdateAuditStatePage(ActionMapping mapping, ActionForm form,
                                                  HttpServletRequest request, HttpServletResponse response) {
        Long resId = Long.parseLong(request.getParameter("id"));

        IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
       
        
        InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(resId);
        TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        String userId = sessionForm.getUserid();
        String deptId = sessionForm.getDeptid();
        request.setAttribute("resId", resId);
        request.setAttribute("userId", userId);
        request.setAttribute("deptId", deptId);
        request.setAttribute("resName", inspectPlanRes.getResName());

        if (inspectPlanRes.getExceptionFlag() != null && inspectPlanRes.getExceptionFlag() == 0) {
        	String msg = "";
        	IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr) getBean("inspectPlanItemMgrImpl");
             Search search = new Search();
             search.addFilterEqual("planResId", resId);
             search.addFilterEqual("exceptionFlag", 0);
             
             List<InspectPlanItem> list = inspectPlanItemMgr.search(search);
             for(int i=0;i<list.size();i++){
            	 
            		 msg+=(i+1)+"、"+list.get(i).getContent()+"异常信息:"+list.get(i).getExceptionContent()+"。\r\n";
            		 
      
             }
             request.setAttribute("mainRemark", msg);

            return mapping.findForward("gotoUpdateAuditStateSendTask");
        } else {
            return mapping.findForward("gotoUpdateAuditStatePage");
        }


    }

    public ActionForward updateAuditStatePage(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request, HttpServletResponse response) {
        Long resId = Long.parseLong(request.getParameter("resId"));
        String auditStatusTemp = request.getParameter("auditStatus");
        Integer auditState = Integer.parseInt(auditStatusTemp);
        String auditOpinion = request.getParameter("auditOpinion");
        IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
        InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(resId);
        if (auditState == 2) {
            inspectPlanRes.setInspectState(0);
        }
        inspectPlanRes.setAuditState(auditState);
        inspectPlanRes.setAuditOpinion(auditOpinion);
        inspectPlanResMgr.save(inspectPlanRes);
        return mapping.findForward("success");
    }

    public ActionForward updateAuditStateSendTask(ActionMapping mapping, ActionForm form,
                                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
        String operateType = request.getParameter("operateType");
        Long resId = Long.parseLong(request.getParameter("resId"));
        request.setAttribute("resId", resId);
        if (operateType.equals("jump")) {
            return mapping.findForward("gotoUpdateAuditStatePage");
        }
        DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        //工单主题
        String title = request.getParameter("title").trim();
        //派单人
        String initiator = request.getParameter("initiator").trim();
        // 工单生成时间
        Date createTime = new Date();
//		工单子类型
        String mainFaultNet = request.getParameter("mainFaultNet").trim();
//		站点
        String mainResName = request.getParameter("mainResName").trim();
//		站点ID
        String mainResId = request.getParameter("mainResId").trim();
//		工单内容
        String mainRemark = request.getParameter("mainRemark").trim();

//		计划开始时间
        String sheetAcceptLimit = request.getParameter("sheetAcceptLimit");

//		计划结束时间
        String sheetCompleteLimit = request.getParameter("sheetCompleteLimit");

/*//		故障站点
        String faultResName = request.getParameter("faultResName");
//		故障站点ID
        String faultResId = request.getParameter("faultResId");*/
//		涉及专业
//        String mainSpecialty = request.getParameter("mainSpecialty");
        String[] specialtyStrings = request.getParameterValues("mainSpecialty");
        String mainSpecialty = "";
        for (int i = 0; i < specialtyStrings.length; i++) {
            if (i == (specialtyStrings.length - 1)) {
                mainSpecialty += specialtyStrings[i];
            } else {
                mainSpecialty += specialtyStrings[i] + ",";
            }
        }
        //接收人
        String candidateUser = request.getParameter("candidateUser");
//		接收组
        String candidateGroup
                = request.getParameter("candidateGroup");
        //流程开始
        FormService formService = (FormService) getBean("formService");
        IdentityService identityService = (IdentityService) getBean("identityService");
        identityService.setAuthenticatedUserId(initiator);
        RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("taskWorkOrder");
        String processInstanceId = processInstance.getId();
        TaskService taskService = (TaskService) getBean("taskService");
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).active().list();
        String taskId = taskList.get(0).getId();
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        Map<String, String> map = new HashMap<String, String>();
        for (FormProperty formProperty : taskFormData.getFormProperties()) {
            if (formProperty.isWritable()) {
                String name = formProperty.getId();
                map.put(name, request.getParameter(name));
            }
        }
        formService.submitTaskFormData(taskId, map);
        IPnrTaskTicketService pnrService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
        PnrTaskTicket entity = new PnrTaskTicket();
        entity.setTheme(title);
        entity.setSubType(mainFaultNet);
        entity.setSite(mainResName);
        entity.setMainResId(mainResId);
        entity.setContent(mainRemark);
        entity.setCreateTime(createTime);
        entity.setStartTime(sFormat.parse(sheetAcceptLimit));
        entity.setEndTime(sFormat.parse(sheetCompleteLimit));
       /* entity.setFailedSite(faultResName);
        entity.setFaultResId(faultResId);*/
        //保存故障字符串
        entity.setSpecialty(mainSpecialty);
        //保存专业与工单的关系--start
        pnrService.saveOrUpateSpecialty(processInstanceId, specialtyStrings);
        //保存专业与工单的关系--end
        entity.setInitiator(initiator);
        entity.setProcessInstanceId(processInstanceId);
        entity.setCandidateUser(candidateUser);
        entity.setCandidateGroup(candidateGroup);
        IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
        InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(resId);
        entity.setCity(inspectPlanRes.getCity());
        entity.setCountry(inspectPlanRes.getCountry());
        entity.setState(1);
        try {
            pnrService.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
         return mapping.findForward("gotoUpdateAuditStatePage");
    }

    /**
     * 查看巡检项详情
     */
    public ActionForward goToInspectPlanMainItemList(ActionMapping mapping, ActionForm form,
                                                     HttpServletRequest request, HttpServletResponse response) {
        IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr) getBean("inspectPlanItemMgrImpl");
        String plan_res_id = request.getParameter("planResId");

        Search search = new Search();
        search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
        String detail = request.getParameter("detail");
        String pageIndexString = request.getParameter((new org.displaytag.util.ParamEncoder("inspectPlanItemList").encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
        search.setMaxResults(CommonConstants.PAGE_SIZE);
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
       // search.addSortAsc("exceptionFlag");
        //search.addSortDesc("saveTime");
        search.addFilterEqual("planResId", plan_res_id);
        /*查看公共巡检项 add by zhangkeqi 2013-02-26 begin*/
        search.addFilter(new Filter().or(new Filter().notEqual("deviceInspectFlag", 1), new Filter().isNull("deviceInspectFlag")));
		/*查看公共巡检项 add by zhangkeqi 2013-02-26 end*/

        SearchResult<InspectPlanItem> returnSearch = inspectPlanItemMgr.searchAndCount(search);


        List<InspectPlanItem> inspectPlanItemList = returnSearch.getResult();
        IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
        InspectPlanRes res = inspectPlanResMgr.get(Long.parseLong(plan_res_id));
        if (inspectPlanItemList.isEmpty()) {
            request.setAttribute("resultSize", 0);
        } else {
            InspectPlanItemForm inspectPlanItemForm;
            List<InspectPlanItemForm> returnList = new ArrayList<InspectPlanItemForm>();
            for (int i = 0; i < inspectPlanItemList.size(); i++) {
                inspectPlanItemForm = new InspectPlanItemForm();
                try {
                    BeanUtils.copyProperties(inspectPlanItemForm, inspectPlanItemList.get(i));
                    inspectPlanItemForm.setEndTime((res.getPlanEndTime() + "").substring(0, (res.getPlanEndTime() + "").length() - 2));
//					String dictIdTemp = inspectPlanItemList.get(i).getItemResult();
                    returnList.add(inspectPlanItemForm);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            request.setAttribute("inspectPlanItemList", returnList);
            request.setAttribute("resultSize", returnSearch.getTotalCount());
        }
        IInspectPlanExecuteMgr executeMgr = (IInspectPlanExecuteMgr) getBean("inspectPlanExecuteMgrImpl");
        request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
        request.setAttribute("planResId", plan_res_id);
        request.setAttribute("planId", res.getPlanId());
        request.setAttribute("InspectPlanRes", res);
        request.setAttribute("isCheckUser", executeMgr.isCheckUser(getUser(request).getDeptid(), res.getExecuteDept()));
        if ("detail".equals(detail)) {
            return mapping.findForward("inspectPlanItemListByCheckUserDetai");
        } else {
            return mapping.findForward("inspectPlanItemListByCheckUser");
        }
    }

    /**
     * 单个巡检项执行保存
     */
    @SuppressWarnings({"deprecation", "unchecked"})
    public void saveCheckResult(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response) {
        IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr) getBean("inspectPlanItemMgrImpl");
        IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr) getBean("inspectPlanMainMgr");
        IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
        Map receiveMap = MapToBeanUtil.getParameterMap(request);
        String id = StaticMethod.nullObject2String(receiveMap.get("inputId"));
        String exceptionFlag = StaticMethod.nullObject2String(receiveMap.get("exceptionFlag"));
        String device = StaticMethod.nullObject2String(request.getParameter("device"));
        String exceptionContent = StaticMethod.nullObject2String(receiveMap.get("exceptionContent"));
        try {
            String itemResult = "";
            if (!"".equals(device) && "mobile".equals(device)) {
                id = StaticMethod.nullObject2String(request.getParameter("inputId"));

                String temp = StaticMethod.nullObject2String(request.getParameter("json"));
                JSONArray tempArray = new JSONArray("[" + temp + "]");
                JSONObject tempObj = tempArray.getJSONObject(0);
                String saveData = tempObj.get("saveData") + "";

                JSONArray array = new JSONArray(saveData);
                JSONObject object = array.getJSONObject(0);

                String inputIdTemp = object.get("inputId") + "";
                itemResult = object.get("value") + "";
                exceptionContent = object.get("exceptionContent") + "";
                exceptionFlag = object.get("exceptionFlag") + "";

                System.out.println(saveData);
                if ("".equals(inputIdTemp)) {
                    response.getWriter().write(MobileConstants.failureStr);
                    return;
                } else {
                    id = inputIdTemp;
                }
            }

            Search search = new Search();
            search.addFilterEqual("id", id);
            InspectPlanItem item = inspectPlanItemMgr.searchUnique(search);

            search = new Search();
            search.addFilterEqual("id", item.getPlanResId());
            InspectPlanRes res = inspectPlanResMgr.get(item.getPlanResId());
            if ("0".equals(res.getInspectState() + "")) {
                if (!"0".equals(exceptionFlag)) {   //这时候是无设备
                    item.setExceptionFlag(-1);
                    item.setItemResult("");
                    item.setSaveTime(new Date());
                    item.setExceptionContent("");  //此时的巡检项一定没有异常。元任务的异常情况也是原来的信息，不用更改。
                    Search search1 = new Search();
                    search1.addFilterEqual("planResId", item.getPlanResId());
                    search1.addFilterNotEqual("id", id);
                    List<InspectPlanItem> itemList = inspectPlanItemMgr.search(search1);
                    boolean bool = false;
                    for (int i = 0; i < itemList.size(); i++) {
                        InspectPlanItem inspectPlanItem = itemList.get(i);
                        if (!StringUtils.isEmpty(inspectPlanItem.getExceptionContent())) {  //此时该巡检项有异常
                            bool = true;
                            break;
                        }
                    }
                    if (bool) {
                        if (res.getExceptionFlag() != 1) {
                            res.setExceptionFlag(1);
                            inspectPlanResMgr.save(res);
                        }
                    } else {
                        res.setExceptionFlag(0);   //此时当前资源下的巡检项全为正常
                        inspectPlanResMgr.save(res);
                    }

                } else {

                    //此时有设备,现在要区分是否有异常
                    if (!(!"".equals(device) && "mobile".equals(device))) {
                        item.setItemResult(receiveMap.get("value").toString());
                    } else {
                        item.setItemResult(itemResult);
                    }
                    item.setSaveTime(new Date());
                    item.setExceptionContent(exceptionContent);

                    if (StringUtils.isEmpty(exceptionContent)) {
                        if (item.getExceptionFlag() == 0) {
                            Search search1 = new Search();
                            search1.addFilterEqual("planResId", item.getPlanResId());
                            search1.addFilterNotEqual("id", id);
                            List<InspectPlanItem> itemList = inspectPlanItemMgr.search(search1);
                            boolean bool = false;
                            for (int i = 0; i < itemList.size(); i++) {
                                InspectPlanItem inspectPlanItem = itemList.get(i);
                                if (!StringUtils.isEmpty(inspectPlanItem.getExceptionContent())) {  //此时该巡检项有异常
                                    bool = true;
                                    break;
                                }
                            }
                            if (bool) {
                                if (res.getExceptionFlag() != 1) {
                                    res.setExceptionFlag(1);
                                    inspectPlanResMgr.save(res);
                                }
                            } else {
                                res.setExceptionFlag(0);   //此时当前资源下的巡检项全为正常
                                inspectPlanResMgr.save(res);
                            }
                        }
                        item.setExceptionFlag(1);
                        inspectPlanResMgr.save(res);
                    } else {
                        item.setExceptionFlag(0);
//					if(res.getExceptionFlag() == null || res.getExceptionFlag()==0){
//						res.setExceptionFlag(1);
//						inspectPlanResMgr.save(res);
//					}
                        res.setExceptionFlag(1);
                        inspectPlanResMgr.save(res);
                    }

                }
                inspectPlanItemMgr.save(item);
                search = new Search();
                search.addFilterEqual("id", res.getPlanId());
                InspectPlanMain inspectPlanMain = inspectPlanMainMgr.search(search).get(0);
                boolean transLine = false;
                int itemFlag = StaticMethod.nullObject2Integer(item.getDeviceInspectFlag());
                PnrDeviceInspectSwitchConfig transLineswitch = (PnrDeviceInspectSwitchConfig) request.getSession().getServletContext().getAttribute("pnrInspect2SwitchConfig");
                //如果是线路线路巡检的公共巡检项，则只保存巡检项，不与元任务有关。
                if (res.getSpecialty().equals("1122502") && itemFlag == 1 && transLineswitch.isOpenTransLineInspect()) {  //此时是线路巡检
                    IPnrTransLineMgr pnrTransLineMgr = (IPnrTransLineMgr) getBean("pnrTransLineMgrImpl");
                    Search searchItem = new Search();
                    searchItem.addFilterEqual("id", item.getInspectTaskLinkId());
                    pnrTransLineMgr.setPersistentClass(PnrInspectTaskLink.class);
                    SearchResult searchResult = pnrTransLineMgr.searchAndCount(searchItem);
                    PnrInspectTaskLink itl = (PnrInspectTaskLink) (searchResult.getResult().get(0));
                    boolean finsh = inspectPointFinsh(item.getInspectTaskLinkId()); //判断当前敷设点是否完成
                    int signStatus = -1;
                    if (null != itl) {
                        signStatus = StaticMethod.nullObject2int(itl.getSignStatus());
                    }
                    if (finsh && (-1 == signStatus || signStatus == 0)) {
                        if (null != itl) {
                            itl.setSignStatus(-1);
                        }
                    }

                    pnrTransLineMgr.setPersistentClass(PnrInspectTaskLink.class);
                    pnrTransLineMgr.save(itl);
                    Integer inspectStatue = StaticMethod.nullObject2Integer(res.getInspectState());
                    InspectPlanRes inspectPlanRes = updateResCfg(res, request);   //根据敷设点判断资源是否完成
                    Integer newInspectStatue = StaticMethod.nullObject2Integer(inspectPlanRes.getInspectState());
                    if (inspectStatue != 1 && newInspectStatue == 1) {   //该资源在执行这个巡检项以前是未完成，执行该巡检项后该资源完成后，就更新巡检计划
                        transLine = true;
                    }

                }

                //已完成的巡检数量
                search = new Search();
                search.addFilterNotEmpty("saveTime");
                search.addFilterEqual("planResId", item.getPlanResId());
                search.addFilterOr(new Filter().equal("pictureUploadFlag", "1"), new Filter().equal("pictureNum", "0"));
                int hasCheckTotal = inspectPlanItemMgr.searchAndCount(search).getTotalCount();//已巡检数量
                search = new Search();
                search.addFilterEqual("planResId", item.getPlanResId());
                int total = inspectPlanItemMgr.searchAndCount(search).getTotalCount();//全部数量
                if (total <= hasCheckTotal || transLine) {//如果相等则更新巡检资源的状态为已巡检即 1
                    search = new Search();
                    search.addFilterEqual("id", item.getPlanResId());
                    Integer inspectStadue = res.getInspectState();
                    res.setItemDoneNum(hasCheckTotal);
                    res.setInspectState(1);
                    res.setInspectTime(new Date());
                    inspectPlanResMgr.save(res);//更新巡检资源

                    if (!"1".equals(inspectStadue + "")) {  //如果保存巡检项前已经完成该资源的巡检，则不用更行巡检计划完成数

                        if (inspectPlanMain.getResDoneNum() == null) {
                            inspectPlanMain.setResDoneNum(1);
                        } else {
                            inspectPlanMain.setResDoneNum(inspectPlanMain.getResDoneNum() + 1);
                        }
                        //更新资源的异常数
                        if (StaticMethod.nullObject2int(res.getExceptionFlag()) == 1) {
                            if (StringUtils.isEmpty(inspectPlanMain.getResExceptionNum() + "") || inspectPlanMain.getResExceptionNum() == null) {
                                inspectPlanMain.setResExceptionNum(1);
                            } else {
                                inspectPlanMain.setResExceptionNum(inspectPlanMain.getResExceptionNum() + 1);
                            }
                        }
                        inspectPlanMainMgr.save(inspectPlanMain);//更新巡检计划的已完成数
                    }
                    //往巡检轨迹表中添加数据
                    IPnrInspectTrackMgr inspectTrackMgr = (IPnrInspectTrackMgr) this.getBean("pnrInspectTrackMgrImpl");
                    PnrInspectTrack pnrInspectTrack = new PnrInspectTrack();
                    pnrInspectTrack.setPlanResId(res.getId() + "");
                    pnrInspectTrack.setSignTime(StaticMethod.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
                    pnrInspectTrack.setSignState("0");
                    pnrInspectTrack.setStatus("1");

                    PnrInspectTrack pnrInspectTrack1 = new PnrInspectTrack();
                    pnrInspectTrack1.setPlanResId(res.getId() + "");
                    pnrInspectTrack1.setSignTime(StaticMethod.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
                    pnrInspectTrack1.setSignState("0");
                    pnrInspectTrack.setStatus("0");
                    if ("".equals(res.getEndLongitude() + "") || res.getEndLatitude() == null) {   //如果没有终点经纬度，表明资源的类型为传输线路
                        pnrInspectTrack.setSignLatitude(StaticMethod.nullObject2String(res.getResLatitude()));
                        pnrInspectTrack.setSignLongitude(StaticMethod.nullObject2String(res.getResLongitude()));
                        pnrInspectTrack1.setSignLatitude(StaticMethod.nullObject2String(res.getResLatitude()));
                        pnrInspectTrack1.setSignLongitude(StaticMethod.nullObject2String(res.getResLongitude()));
                    } else {
                        pnrInspectTrack.setSignLatitude(StaticMethod.nullObject2String(res.getEndLatitude()));
                        pnrInspectTrack.setSignLongitude(StaticMethod.nullObject2String(res.getEndLongitude()));
                        pnrInspectTrack1.setSignLatitude(StaticMethod.nullObject2String(res.getEndLatitude()));
                        pnrInspectTrack1.setSignLongitude(StaticMethod.nullObject2String(res.getEndLongitude()));
                    }

                    //更新pnr_res_config中资源的上次完成时间
                    PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
                    PnrResConfig pnrResConfig = pnrResConfiMgr.find(res.getResCfgId());
                    pnrResConfig.setLastInspectTime(new Date());
                    pnrResConfiMgr.save(pnrResConfig);

                    inspectTrackMgr.save(pnrInspectTrack);
                    inspectTrackMgr.save(pnrInspectTrack1);

                } else {
//				InspectPlanRes res = inspectPlanResMgr.get(item.getPlanResId());
                    res.setInspectState(0);
                    res.setItemDoneNum(hasCheckTotal);
                    res.setInspectTime(new Date());
                    inspectPlanResMgr.save(res);
                }
                response.getWriter().write(MobileConstants.successStr);
            } else {
                response.getWriter().write(MobileConstants.failureStr);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量保存
     */
    @SuppressWarnings("deprecation")
    public void saveAllCheckResult(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            String json = StaticMethod.nullObject2String(request.getParameter("json"));

            String planResId = StaticMethod.nullObject2String(request.getParameter("planResId"));
            System.out.println("returnJson    " + json);
            JSONArray array = new JSONArray(json);
            JSONObject obj;
            IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr) getBean("inspectPlanItemMgrImpl");
            IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
            IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr) getBean("inspectPlanMainMgr");

            Search search;
            search = new Search();

            search.addFilterEqual("planResId", planResId);
            //int total = inspectPlanItemMgr.searchAndCount(search).getTotalCount();//全部数量

            InspectPlanItem item;
            int itemFlag = 0;
            String inspectTaskLinkId = "";
//			int itemFlag = StaticMethod.nullObject2Integer(item.getDeviceInspectFlag());
            if (null == array || array.length() == 0) {
                response.getWriter().write(MobileConstants.failureStr);
                return;
            }
            InspectPlanRes res = inspectPlanResMgr.get(Long.parseLong(planResId));
            //res.setItemDoneNum(total);//如果用户点击了全部提交,则将资源的已巡检数量直接更新为巡检项总数
            Integer inspectStadue = res.getInspectState();

            if ("0".equals(inspectStadue + "")) {
                //更新计划,
                search = new Search();
                search.addFilterEqual("id", res.getPlanId());
                InspectPlanMain inspectPlanMain = inspectPlanMainMgr.search(search).get(0);
                boolean isExceptionRes = false;
                for (int i = 0; i < array.length(); i++) {
                    obj = array.getJSONObject(i);
                    String inputId = obj.getString("inputId");
                    String value = obj.getString("value");
                    String exceptionContent = obj.getString("exceptionContent");
                    String exceptionFlag = obj.getString("exceptionFlag");
                    System.out.println("异常标识：" + exceptionFlag);
                    search = new Search();
                    search.addFilterEqual("id", inputId);
                    item = inspectPlanItemMgr.search(search).get(0);
                    inspectTaskLinkId = item.getInspectTaskLinkId();
                    itemFlag = StaticMethod.nullObject2Integer(item.getDeviceInspectFlag());
                    if ("-1".equals(exceptionFlag)) {
                        item.setExceptionFlag(Integer.parseInt(exceptionFlag));
                        item.setItemResult("");
                        item.setSaveTime(new Date());
                        item.setExceptionContent("");
                        item.setExceptionFlag(-1);
                        //isExceptionRes = true;
                    } else {
                        if (StringUtils.isEmpty(exceptionContent)) {   //无异常
                            item.setExceptionFlag(1);
                        } else {
                            item.setExceptionFlag(0);
                            item.setItemResult("");
                            isExceptionRes = true;
                        }
                        //item.setExceptionFlag(Integer.parseInt(exceptionFlag));
                        item.setItemResult(value);
                        item.setSaveTime(new Date());
                        item.setExceptionContent(exceptionContent);
                    }
                    inspectPlanItemMgr.save(item);
                }
                //更新异常
//				if(isExceptionRes){
//					res.setExceptionFlag(1);
//				}else{
//					res.setExceptionFlag(0);
//				}

                boolean transLine = false;
//				int itemFlag = StaticMethod.nullObject2Integer(item.getDeviceInspectFlag());
                //如果是线路线路巡检的公共巡检项，则只保存巡检项，不与元任务有关。
                PnrDeviceInspectSwitchConfig transLineswitch = (PnrDeviceInspectSwitchConfig) request.getSession().getServletContext().getAttribute("pnrInspect2SwitchConfig");
                if (res.getSpecialty().equals("1122502") && itemFlag == 1 && transLineswitch.isOpenTransLineInspect()) {  //此时是线路巡检
                    IPnrTransLineMgr pnrTransLineMgr = (IPnrTransLineMgr) getBean("pnrTransLineMgrImpl");
                    Search searchItem = new Search();
                    searchItem.addFilterEqual("id", inspectTaskLinkId);
                    pnrTransLineMgr.setPersistentClass(PnrInspectTaskLink.class);
                    SearchResult searchResult = pnrTransLineMgr.searchAndCount(searchItem);
                    PnrInspectTaskLink itl = (PnrInspectTaskLink) (searchResult.getResult().get(0));
                    boolean finsh = inspectPointFinsh(inspectTaskLinkId); //判断当前敷设点是否完成
                    int signStatus = -1;
                    if (null != itl) {
                        signStatus = StaticMethod.nullObject2int(itl.getSignStatus());
                    }
                    if (finsh && (-1 == signStatus || signStatus == 0)) {
                        if (null != itl) {
                            itl.setSignStatus(-1);
                        }
                    }

                    pnrTransLineMgr.setPersistentClass(PnrInspectTaskLink.class);
                    pnrTransLineMgr.save(itl);
                    Integer inspectStatue = StaticMethod.nullObject2Integer(res.getInspectState());
                    InspectPlanRes inspectPlanRes = updateResCfg(res, request);   //根据敷设点判断资源是否完成
                    Integer newInspectStatue = StaticMethod.nullObject2Integer(inspectPlanRes.getInspectState());
                    if (inspectStatue != 1 && newInspectStatue == 1) {   //该资源在执行这个巡检项以前是未完成，执行该巡检项后该资源完成后，就更新巡检计划
                        transLine = true;
                    }

                }


                search = new Search();
                search.addFilterNotEmpty("saveTime");
                search.addFilterEqual("planResId", res.getId());
                search.addFilterOr(new Filter().equal("pictureUploadFlag", "1"), new Filter().equal("pictureNum", "0"));
                int hasCheckTotal = inspectPlanItemMgr.searchAndCount(search).getTotalCount();//已巡检数量

                //此时判断当前资源下的公共巡检项是否全部执行
                if (res.getItemNum() == hasCheckTotal || transLine) {   //当前的巡检项全部执行完成
                    res.setInspectState(1);
                    res.setInspectTime(new Date());
                    res.setInspectUser(this.getUserId(request));
                    res.setItemDoneNum(res.getItemNum());
                } else {//当前巡检项没有全部执行完成
                    res.setInspectState(0);
                    res.setItemDoneNum(hasCheckTotal);
                }

                //判断资源是否异常

                Search search1 = new Search();
                search1.addFilterEqual("planResId", res.getId());
                List<InspectPlanItem> itemList = inspectPlanItemMgr.search(search1);
                res.setExceptionFlag(0);
                for (int i = 0; i < itemList.size(); i++) {
                    InspectPlanItem inspectPlanItem = itemList.get(i);
                    if (inspectPlanItem.getExceptionFlag() == 0) {  //此时该巡检项有异常
                        res.setExceptionFlag(1);
                        break;
                    }
                }

                inspectPlanResMgr.save(res);//更新巡检资源
                //往巡检轨迹表中添加数据
                IPnrInspectTrackMgr inspectTrackMgr = (IPnrInspectTrackMgr) this.getBean("pnrInspectTrackMgrImpl");
                PnrInspectTrack pnrInspectTrack = new PnrInspectTrack();
                pnrInspectTrack.setPlanResId(res.getId() + "");
                pnrInspectTrack.setSignTime(StaticMethod.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
                pnrInspectTrack.setSignState("0");
                pnrInspectTrack.setStatus("1");

                PnrInspectTrack pnrInspectTrack1 = new PnrInspectTrack();
                pnrInspectTrack1.setPlanResId(res.getId() + "");
                pnrInspectTrack1.setSignTime(StaticMethod.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
                pnrInspectTrack1.setSignState("0");
                pnrInspectTrack1.setStatus("0");
                if ("".equals(res.getEndLongitude() + "") || res.getEndLatitude() == null) {   //如果没有终点经纬度，表明资源的类型为传输线路
                    pnrInspectTrack.setSignLatitude(StaticMethod.nullObject2String(res.getResLatitude()));
                    pnrInspectTrack.setSignLongitude(StaticMethod.nullObject2String(res.getResLongitude()));
                    pnrInspectTrack1.setSignLatitude(StaticMethod.nullObject2String(res.getResLatitude()));
                    pnrInspectTrack1.setSignLongitude(StaticMethod.nullObject2String(res.getResLongitude()));
                } else {
                    pnrInspectTrack.setSignLatitude(StaticMethod.nullObject2String(res.getEndLatitude()));
                    pnrInspectTrack.setSignLongitude(StaticMethod.nullObject2String(res.getEndLongitude()));
                    pnrInspectTrack1.setSignLatitude(StaticMethod.nullObject2String(res.getEndLatitude()));
                    pnrInspectTrack1.setSignLongitude(StaticMethod.nullObject2String(res.getEndLongitude()));
                }

                inspectTrackMgr.save(pnrInspectTrack);
                inspectTrackMgr.save(pnrInspectTrack1);

                if (res.getItemNum() == hasCheckTotal || transLine) {   //当前的巡检项全部执行完成
                    //资源保存后，应该更新巡检主表的巡检数量
                    if (!"1".equals(inspectStadue + "")) {  //如果保存巡检项前已经完成该资源的巡检，则不用更行巡检计划完成数
                        if (inspectPlanMain.getResDoneNum() == null) {
                            inspectPlanMain.setResDoneNum(1);
                        } else {
                            inspectPlanMain.setResDoneNum(inspectPlanMain.getResDoneNum() + 1);
                        }

                        //更新计划异常数
                        if (isExceptionRes) {
                            if (inspectPlanMain.getResExceptionNum() == null || inspectPlanMain.getResExceptionNum().equals("")) {
                                inspectPlanMain.setResExceptionNum(1);
                            } else {
                                inspectPlanMain.setResExceptionNum(inspectPlanMain.getResExceptionNum() + 1);
                            }
                        }

                        if (inspectPlanMain.getResDoneNum() == inspectPlanMain.getResNum()) {
                            inspectPlanMain.setFinishFlag(1);
                        }

                        inspectPlanMainMgr.save(inspectPlanMain);//更新巡检计划的已完成数
                    }
                }
                //更新pnr_res_config中资源的上次完成时间
                PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
                PnrResConfig pnrResConfig = pnrResConfiMgr.find(res.getResCfgId());
                pnrResConfig.setLastInspectTime(new Date());
                pnrResConfiMgr.save(pnrResConfig);

                response.getWriter().write(MobileConstants.successStr);

            } else {
                response.getWriter().write(MobileConstants.failureStr);

            }


        } catch (IOException e) {
            try {
                response.getWriter().write(MobileConstants.failureStr);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * 查看详情
     */
    public ActionForward gotoInspectPlanMainUploadPicture(ActionMapping mapping, ActionForm form,
                                                          HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("id", request.getParameter("id"));
        request.setAttribute("typeId", request.getParameter("typeId"));

        return mapping.findForward("gotoInspectPlanMainUploadPicture");
    }

    @SuppressWarnings("unchecked")
    public ActionForward inspectPlanMainUploadPicture(ActionMapping mapping, ActionForm form,
                                                      HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setCharacterEncoding("UTF-8");
        String picture_id = StaticMethod.nullObject2String(request.getParameter("picture_id"));
        String idType = StaticMethod.nullObject2String(request.getParameter("idType"));

        String savePath = servlet.getServletContext().getRealPath("" + File.separator + "partner" + File.separator + "partnerRes" + File.separator + "");
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }

        PnrInspectTaskFileForm pnrInspectTaskFileForm = (PnrInspectTaskFileForm) form;

        FormFile formFile = pnrInspectTaskFileForm.getImportFile();

        if (formFile == null) {  //说明文件过大，不能从页面通过Struts传到后台
            response.getWriter().write(
                    new Gson().toJson(new ImmutableMap.Builder<String, String>()
                            .put("success", "false")
                            .put("msg", "failure")
                            .put("infor", "上传失败！单个力图片不得大于2M。").build()));
            return null;
        }

        String environmentPhotoFileName, file1, exp1;

        file1 = formFile.getFileName();
        int size1 = formFile.getFileSize();

        //文件大小检查 begin
        JsonObject jo = new JsonObject();
        jo.addProperty("info", "false");
        String infor = "上传失败！单个力图片不得大于2M。";
        boolean checkSize = true;
        if (size1 / 1024 / 1024 > 2) {
            infor += "照片:" + (size1 / 1024) + "KB,大于2M!";
            checkSize = false;
        }
        if (!checkSize) {
            response.getWriter().write(
                    new Gson().toJson(new ImmutableMap.Builder<String, String>()
                            .put("success", "false")
                            .put("msg", "failure")
                            .put("infor", "上传失败！单个力图片不得大于2M。").build()));
            return null;
        }
        //文件大小检查 end

        //构建名字
        exp1 = file1.substring(file1.lastIndexOf('.'));

        if (!exp1.toLowerCase().equals(".jpg") && !exp1.toLowerCase().equals(".png") && !exp1.toLowerCase().equals(".jpeg") && !exp1.toLowerCase().equals(".bmp")
                && !exp1.toLowerCase().equals(".gif")) {
            response.getWriter().write(
                    new Gson().toJson(new ImmutableMap.Builder<String, String>()
                            .put("success", "false")
                            .put("msg", "failure")
                            .put("infor", "图片格式不正确，上传失败！").build()));
            return null;
        }

        environmentPhotoFileName = UUIDHexGenerator.getInstance().getID() + exp1;

        //保存文件
        FileUploadProcessor.processUploadedFile(formFile, savePath + File.separator + environmentPhotoFileName);

        //检查是否上传成功
        boolean flag = false;
        File f1;
        f1 = new File(savePath + File.separator + environmentPhotoFileName);

        if (f1.exists()) {
            flag = true;
        }
        //Base64转码
//		String webPath = StaticMethod.getWebPath();
        if (flag) {
            try {
//				String f1Path = webPath+"partner"+File.separator+"partnerRes"+File.separator+""+environmentPhotoFileName;
                File baseFolder = new File(this.getServletContext().getRealPath(""), "partner" + File.separator + "partnerRes" + File.separator + "" + environmentPhotoFileName);
                String f1Path = baseFolder.getAbsolutePath();
                String data1 = InspectUtils.convertImageDataToBASE64(f1Path);
                //保存数据到数据库
                IPnrInspectTaskFileMgr mgr = (IPnrInspectTaskFileMgr) getBean("pnrInspectTaskFileMgrImpl");
                PnrInspectTaskFile taskFile1 = new PnrInspectTaskFile();
                taskFile1.setFileType(exp1.substring(1, exp1.length()));
                taskFile1.setFile_id(picture_id);
                taskFile1.setIdType(idType);
                taskFile1.setPhotoType("3");

                if (CommonSqlHelper.isOracleDialect()) {
                    PnrInspectTaskFileDaoHibernate dao = (PnrInspectTaskFileDaoHibernate) getBean("pnrInspectTaskFileDao");
//					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f1Path)));
                    Session session = dao.getHibernateTemplate().getSessionFactory().openSession();
                    Transaction tx = session.beginTransaction();
                    taskFile1.setFileData(Hibernate.createClob("1"));
                    session.saveOrUpdate(taskFile1);
                    session.flush();
                    session.refresh(taskFile1, LockMode.UPGRADE);
                    SerializableClob cb = (SerializableClob) taskFile1.getFileData();
                    Clob wrapClob = (Clob) cb.getWrappedClob();
                    if (wrapClob instanceof CLOB) {
                        CLOB clob = (CLOB) wrapClob;
                        clob.putString(1, data1);
//					    Writer writer = clob.getCharacterOutputStream();
//					    String temp = "";
//		    			String dataStr = "";
//		    			while((temp = br.readLine()) != null){
//		    				dataStr = dataStr + temp;
//		    			}
//					    writer.write(dataStr);
//					    writer.close();
                    }
                    tx.commit();
                    session.close();
                } else if (CommonSqlHelper.isInformixDialect()) {
                    mgr.saveOrUpdateTaskFile(taskFile1, data1);
                }

            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            } finally {
                if (flag) {  //成功的时候  后面再打印出去
//					response.getWriter().write(
//							new Gson().toJson(new ImmutableMap.Builder<String, String>()
//								.put("success", "true")
//								.put("msg", "ok")
//								.put("infor", "上传成功,转码成功！").build()));


                } else {
                    response.getWriter().write(
                            new Gson().toJson(new ImmutableMap.Builder<String, String>()
                                    .put("success", "false")
                                    .put("msg", "failure")
                                    .put("infor", "文件Base64转码失败！").build()));

                }
            }
        } else {
            response.getWriter().write(
                    new Gson().toJson(new ImmutableMap.Builder<String, String>()
                            .put("success", "false")
                            .put("msg", "failure")
                            .put("infor", "文件保存失败！").build()));
        }

        //如果上传成功，更新资源和巡检项为有图片
        if ("res".equals(idType)) {
            IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
            InspectPlanRes res = inspectPlanResMgr.get(Long.parseLong(picture_id));
            res.setHasPicture(1);
            res.setSignStatus(-1);
            inspectPlanResMgr.save(res);
            response.getWriter().write(
                    new Gson().toJson(new ImmutableMap.Builder<String, String>()
                            .put("success", "true")
                            .put("msg", "ok")
                            .put("infor", "上传成功,转码成功！")
                            .put("pictureNum", "aa").build()));
        } else if ("planItem".equals(idType)) {
            IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr) getBean("inspectPlanItemMgrImpl");
            Search search = new Search();
            search.addFilterEqual("id", picture_id);
            InspectPlanItem inspectPlanItem = inspectPlanItemMgr.searchUnique(search);
            inspectPlanItem.setHasPicture(1);
            inspectPlanItem.setPictureUploadNum(inspectPlanItem.getPictureUploadNum() + 1);
            if (inspectPlanItem.getPictureNum() <= inspectPlanItem.getPictureUploadNum()) {
                inspectPlanItem.setPictureUploadFlag(1);
            }
            inspectPlanItemMgr.save(inspectPlanItem);
            search = new Search();
            search.addFilterNotEmpty("saveTime");
            search.addFilterEqual("planResId", inspectPlanItem.getPlanResId());
            search.addFilterEqual("pictureUploadFlag", "1");
            int hasCheckTotal = inspectPlanItemMgr.searchAndCount(search).getTotalCount();//已巡检数量
            IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
            InspectPlanRes res = inspectPlanResMgr.get(inspectPlanItem.getPlanResId());
            boolean transLine = false;
            int itemFlag = StaticMethod.nullObject2Integer(inspectPlanItem.getDeviceInspectFlag());
            PnrDeviceInspectSwitchConfig transLineswitch = (PnrDeviceInspectSwitchConfig) request.getSession().getServletContext().getAttribute("pnrInspect2SwitchConfig");
            //如果是线路线路巡检的公共巡检项，则只保存巡检项，不与元任务有关。
            if (res.getSpecialty().equals("1122502") && itemFlag == 1 && transLineswitch.isOpenTransLineInspect()) {  //此时是线路巡检
                IPnrTransLineMgr pnrTransLineMgr = (IPnrTransLineMgr) getBean("pnrTransLineMgrImpl");
                Search searchItem = new Search();
                searchItem.addFilterEqual("id", inspectPlanItem.getInspectTaskLinkId());
                pnrTransLineMgr.setPersistentClass(PnrInspectTaskLink.class);
                SearchResult searchResult = pnrTransLineMgr.searchAndCount(searchItem);
                PnrInspectTaskLink itl = (PnrInspectTaskLink) (searchResult.getResult().get(0));
                boolean finsh = inspectPointFinsh(inspectPlanItem.getInspectTaskLinkId()); //判断当前敷设点是否完成
                int signStatus = -1;
                if (null != itl) {
                    signStatus = StaticMethod.nullObject2int(itl.getSignStatus());
                }
                if (finsh && (-1 == signStatus || signStatus == 0)) {
                    if (null != itl) {
                        itl.setSignStatus(-1);
                    }
                }

                pnrTransLineMgr.setPersistentClass(PnrInspectTaskLink.class);
                if (itl.getSignStatus() == -1) {
                    pnrTransLineMgr.save(itl);
                }
                Integer inspectStatue = StaticMethod.nullObject2Integer(res.getInspectState());
                InspectPlanRes inspectPlanRes = updateResCfg(res, request);   //根据敷设点判断资源是否完成
                Integer newInspectStatue = StaticMethod.nullObject2Integer(inspectPlanRes.getInspectState());
                if (inspectStatue != 1 && newInspectStatue == 1) {   //该资源在执行这个巡检项以前是未完成，执行该巡检项后该资源完成后，就更新巡检计划
                    transLine = true;
                }

            }

            if ("0".equals(res.getInspectState() + "")) {
                res.setItemDoneNum(hasCheckTotal);
                if (res.getItemNum() == hasCheckTotal || transLine) {
                    res.setInspectState(1);
                    //判断计划是否完成
                    IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr) getBean("inspectPlanMainMgr");
                    InspectPlanMain inspectPlanMain = inspectPlanMainMgr.search(search).get(0);
                    if (inspectPlanMain.getResDoneNum() == null) {
                        inspectPlanMain.setResDoneNum(1);
                    } else {
                        inspectPlanMain.setResDoneNum(inspectPlanMain.getResDoneNum() + 1);
                    }
                    if (inspectPlanMain.getResDoneNum() == inspectPlanMain.getResNum()) {
                        inspectPlanMain.setFinishFlag(1);
                    }
                    inspectPlanMainMgr.save(inspectPlanMain);
                }
                inspectPlanResMgr.save(res);

            }
            response.getWriter().write(
                    new Gson().toJson(new ImmutableMap.Builder<String, String>()
                            .put("success", "true")
                            .put("msg", "ok")
                            .put("infor", "上传成功,转码成功！")
                            .put("pictureNum", StaticMethod.nullObject2String(inspectPlanItem.getPictureUploadNum())).build()));
        }
        response.getWriter().close();
        request.getInputStream().close();
        f1.delete();

        return null;
    }

    /**
     * 跳转到图片展示页面
     */

    @SuppressWarnings("unchecked")
    public ActionForward gotoShowPicture(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String typeId = request.getParameter("idType");
        if ("res".equals(typeId)) {
            PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
            PnrResConfig pnrResConfig = pnrResConfiMgr.find(request.getParameter("resCfgId"));
            request.setAttribute("resCfgId", request.getParameter("resCfgId"));
            request.setAttribute("pnrResConfig", pnrResConfig);
        }
        request.setAttribute("id", id);
        request.setAttribute("curPage", request.getParameter("curPage"));
        IPnrInspectTaskFileMgr mgr = (IPnrInspectTaskFileMgr) getBean("pnrInspectTaskFileMgrImpl");
        Map map = mgr.getResources(0, 1, " where file_id='" + id + "'");
        List<PnrInspectTaskFile> list = (List<PnrInspectTaskFile>) map.get("result");
        if (list.size() > 0) {
            PnrInspectTaskFile taskFile = list.get(0);
            request.setAttribute("photoType", taskFile.getPhotoType());
        } else {
            request.setAttribute("photoType", "");
        }
        request.setAttribute("total", map.get("total"));
        request.setAttribute("idType", request.getParameter("idType"));
        return mapping.findForward("gotoShowPicture");
    }

    /**
     * 图片展示
     */

    @SuppressWarnings("unchecked")
    public ActionForward showPicture(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        String pageIndexString = request.getParameter("curPage");   //得到当前是第几张
        IPnrInspectTaskFileMgr mgr = (IPnrInspectTaskFileMgr) getBean("pnrInspectTaskFileMgrImpl");
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        PnrInspectTaskFile taskFile = new PnrInspectTaskFile();
        Map map = mgr.getResources(firstResult, 1, " where file_id='" + id + "'");
        List<PnrInspectTaskFile> list = (List<PnrInspectTaskFile>) map.get("result");
        if (list != null && list.size() > 0) {
            taskFile = (PnrInspectTaskFile) list.get(0);
        }
        java.sql.Clob clob = taskFile.getFileData();

        if (clob != null) {
            File tempFile = File.createTempFile(new Date().getTime() + "", "tmp");
            try {
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0);
                response.setContentType("image/jpeg");
                int len;
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
            } finally {
                tempFile.deleteOnExit();
            }

        }
        return null;
    }
    /**
     * 图片展示--根据所存路径
     */
    
    @SuppressWarnings("unchecked")
    public ActionForward showPictureByPath(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String id = request.getParameter("id");
    	String pageIndexString = request.getParameter("curPage");   //得到当前是第几张
    	IPnrInspectTaskFileMgr mgr = (IPnrInspectTaskFileMgr) getBean("pnrInspectTaskFileMgrImpl");
    	int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
    	PnrInspectTaskFile taskFile = new PnrInspectTaskFile();
    	Map map = mgr.getResources(firstResult, 1, " where file_id='" + id + "'");
    	List<PnrInspectTaskFile> list = (List<PnrInspectTaskFile>) map.get("result");
    	if (list != null && list.size() > 0) {
    		taskFile = (PnrInspectTaskFile) list.get(0);
    	}
    	String path = taskFile.getPath();
    	//path = "C:\\Users\\Administrator\\Desktop\\帅哥\\201412311116341.jpg";
    	//path = "C:\\Users\\Administrator\\"+path;
	    String patch = path;  
	    
	  /*  //获取盘符
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
	    	patch = strUrl.substring(0,2)+patch;
	    }else if("/".equals(flag)){//Linux环境下的分隔符是/
//	    	patch = "/"+strUrl+patch;
	    	patch = patch;
	    }
	      //肉哥思路
	    FileInputStream is = new FileInputStream(patch);  
		int i = is.available(); // 得到文件大小  
		byte data[] = new byte[i];  
		is.read(data); // 读数据  
		is.close();  
		response.setContentType("image/*"); // 设置返回的文件类型  
		OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象  
		toClient.write(data); // 输出数据  
		toClient.close();
	    */
	    InputStream in = null ;
        ByteArrayOutputStream out = null ;
        try {
            //创建远程文件对象
           // String remotePhotoUrl = com.boco.eoms.base.util.StaticVariable.IMG_INSPECT_PUBLIC_URL+patch;
        	String remotePhotoUrl = com.boco.eoms.partner.process.util.CommonUtils.getImgInspectPublicUrl()+patch;
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
            String msg = "巡检管理-下载远程文件出错：" + e.getLocalizedMessage();
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

    @SuppressWarnings("unchecked")
    public ActionForward getTaskFilePhotoType(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        String pageIndexString = request.getParameter("curPage");   //得到当前是第几张
        IPnrInspectTaskFileMgr mgr = (IPnrInspectTaskFileMgr) getBean("pnrInspectTaskFileMgrImpl");
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        PnrInspectTaskFile taskFile = new PnrInspectTaskFile();
        Map map = mgr.getResources(firstResult, 1, " where file_id='" + id + "'");
        List<PnrInspectTaskFile> list = (List<PnrInspectTaskFile>) map.get("result");
        if (list != null && list.size() > 0) {
            taskFile = (PnrInspectTaskFile) list.get(0);
        }
//		response.getWriter().print("{'success': true, 'msg': "+taskFile.getPhotoType()+"}");
        response.getWriter().print("[{\"sucess\":\"true\",\"msg\":\"" + taskFile.getPhotoType() + "\",\"msg2\":\"" + map.get("total") + "\"}]");
        return null;
    }

    /**
     * 由我建立(巡检工单专用)
     */
    @SuppressWarnings("unchecked")
    public ActionForward findOwnInspectPlanSheet(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        Search search = new Search();
        search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
        String currentMonth = request.getParameter("currentMonth");
        TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr) getBean("inspectPlanMainMgr");
        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "list");
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
        search.setMaxResults(CommonConstants.PAGE_SIZE);
        search.addFilterEqual("status", 1);
        search.addSortDesc("createTime");
        search.addFilterEqual("approveStatus", 1);

        search.addFilterEqual("creator", sessionForm.getUserid()); //建立者才能看

//		String userId = getUserId(request);
//		TawSystemSessionForm sysuser = this.getUser(request);
//		PartnerUserMgr  partnerUserMgr = (PartnerUserMgr)getBean("partnerUserMgr");
//		PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);


//		if(null != user){//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是deptMagId like
//			String deptMagId = sessionForm.getDeptid();
//			if(deptMagId.length()>6){
//				search.addFilterLike("deptMagId", deptMagId.substring(0, deptMagId.length()-2)+ "%");
//			}else{
//				search.addFilterLike("deptMagId", deptMagId+ "%");
//			}
//		}else if(!"admin".equals(userId)){//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like
//			
//			ITawSystemDeptManager mgr = (ITawSystemDeptManager)getBean("ItawSystemDeptManager");
//			TawSystemDept dept = mgr.getDeptinfobydeptid(sysuser.getDeptid(), "0");
//			String areaid = dept.getAreaid();
//			PartnerDeptMgr  partnerDeptMgr = (PartnerDeptMgr)getBean("partnerDeptMgr");
//			List partnerList = partnerDeptMgr.getPartnerDepts(" and countyId like '"+areaid+"%'");
//			List<String> deptMagIdList = new ArrayList<String>();
//			
//			for(int i=0;partnerList!=null && i<partnerList.size();i++){
//				PartnerDept partnerDept = (PartnerDept)partnerList.get(i);
//				
//				String deptMagId = StaticMethod.nullObject2String(partnerDept.getDeptMagId());
//				if(!deptMagId.equals("")){
//					deptMagIdList.add(deptMagId);
//				}
//			}
//			if(deptMagIdList!=null && deptMagIdList.isEmpty()==false){
//				search.addFilterIn("deptMagId", deptMagIdList);
//			}else{//如果没有管理代维公司，则不允许看该界面
//				return mapping.findForward("inspectnopriv");
//			}
//		}


        if (StringUtils.isEmpty(currentMonth)) {
            LocalDate date = new LocalDate();
            search.addFilterEqual("year", date.getYear());
            search.addFilterEqual("month", date.getMonthOfYear());
        }

        SearchResult<InspectPlanMain> searchResult = inspectPlanMainMgr.searchAndCount(search);
        List<InspectPlanMain> list = searchResult.getResult();
        InspectPlanMainToListForm inspectPlanMainToListForm;
        List<InspectPlanMainToListForm> returnList = new ArrayList<InspectPlanMainToListForm>();
        for (int i = 0; i < list.size(); i++) {
            inspectPlanMainToListForm = new InspectPlanMainToListForm();
            try {
                list.get(i).setCreateTime(null);
                BeanUtilsBean2.getInstance().copyProperties(inspectPlanMainToListForm, list.get(i));
//				Map<String,Integer> map = inspectPlanResMgr.queryTotalAndHasDoneNum(inspectPlanMainToListForm.getId());
                inspectPlanMainToListForm.setHasDone(list.get(i).getResDoneNum());
                inspectPlanMainToListForm.setResNumber(list.get(i).getResNum());
//				inspectPlanMainToListForm.setPlanNumber(map.get("planCount"));
                inspectPlanMainToListForm.setPlanNumber(list.get(i).getResPlanDoneNum());
                returnList.add(inspectPlanMainToListForm);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("yesOrNoMap", InspectConstants.yesOrNoMap);
        request.setAttribute("approveStatusMap", InspectConstants.approveStatusMap);
        request.setAttribute("list", returnList);
        request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
        request.setAttribute("bsfrList", searchResult.getResult());
        request.setAttribute("size", searchResult.getTotalCount());
        request.setAttribute("sheet", "true");
        return mapping.findForward("findInspectPlanMainByCheckUserListSheet");
    }

    public ActionForward waitExceptionSheet(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response) {

        Search search = new Search();
        search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
        String currentMonth = request.getParameter("currentMonth");
        TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr) getBean("inspectPlanMainMgr");
        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "list");
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
        search.setMaxResults(CommonConstants.PAGE_SIZE);
        search.addFilterEqual("status", 1);
        search.addSortDesc("createTime");
        search.addFilterEqual("approveStatus", 1);


        String userId = getUserId(request);
        TawSystemSessionForm sysuser = this.getUser(request);
        PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
        PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);


        if (null != user) {//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是deptMagId like
            String deptMagId = sessionForm.getDeptid();
            if (deptMagId.length() > 6) {
                search.addFilterLike("deptMagId", deptMagId.substring(0, deptMagId.length() - 2) + "%");
            } else {
                search.addFilterLike("deptMagId", deptMagId + "%");
            }
        } else if (!"admin".equals(userId)) {//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like

            ITawSystemDeptManager mgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
            TawSystemDept dept = mgr.getDeptinfobydeptid(sysuser.getDeptid(), "0");
            String areaid = dept.getAreaid();
            PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
            List partnerList = partnerDeptMgr.getPartnerDepts(" and countyId like '" + areaid + "%'");
            List<String> deptMagIdList = new ArrayList<String>();

            for (int i = 0; partnerList != null && i < partnerList.size(); i++) {
                PartnerDept partnerDept = (PartnerDept) partnerList.get(i);

                String deptMagId = StaticMethod.nullObject2String(partnerDept.getDeptMagId());
                if (!deptMagId.equals("")) {
                    deptMagIdList.add(deptMagId);
                }
            }
            if (deptMagIdList != null && deptMagIdList.isEmpty() == false) {
                search.addFilterIn("deptMagId", deptMagIdList);
            } else {//如果没有管理代维公司，则不允许看该界面
                return mapping.findForward("inspectnopriv");
            }
        }


        if (StringUtils.isEmpty(currentMonth)) {
            LocalDate date = new LocalDate();
            search.addFilterEqual("year", date.getYear());
            search.addFilterEqual("month", date.getMonthOfYear());
        }

        SearchResult<InspectPlanMain> searchResult = inspectPlanMainMgr.searchAndCount(search);
        List<InspectPlanMain> list = searchResult.getResult();
        InspectPlanMainToListForm inspectPlanMainToListForm;
        List<InspectPlanMainToListForm> returnList = new ArrayList<InspectPlanMainToListForm>();
        for (int i = 0; i < list.size(); i++) {
            inspectPlanMainToListForm = new InspectPlanMainToListForm();
            try {
                list.get(i).setCreateTime(null);
                BeanUtilsBean2.getInstance().copyProperties(inspectPlanMainToListForm, list.get(i));
//				Map<String,Integer> map = inspectPlanResMgr.queryTotalAndHasDoneNum(inspectPlanMainToListForm.getId());
                inspectPlanMainToListForm.setHasDone(list.get(i).getResDoneNum());
                inspectPlanMainToListForm.setResNumber(list.get(i).getResNum());
//				inspectPlanMainToListForm.setPlanNumber(map.get("planCount"));
                inspectPlanMainToListForm.setPlanNumber(list.get(i).getResPlanDoneNum());
                returnList.add(inspectPlanMainToListForm);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("yesOrNoMap", InspectConstants.yesOrNoMap);
        request.setAttribute("approveStatusMap", InspectConstants.approveStatusMap);
        request.setAttribute("list", returnList);
        request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
        request.setAttribute("bsfrList", searchResult.getResult());
        request.setAttribute("size", searchResult.getTotalCount());

        return mapping.findForward("waitExceptionSheet");
    }

    public ActionForward aleradyExceptionSheet(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request, HttpServletResponse response) {

        Search search = new Search();
        search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
        String currentMonth = request.getParameter("currentMonth");
        TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr) getBean("inspectPlanMainMgr");
        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "list");
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
        search.setMaxResults(CommonConstants.PAGE_SIZE);
        search.addFilterEqual("status", 1);
        search.addSortDesc("createTime");
        search.addFilterEqual("approveStatus", 1);


        String userId = getUserId(request);
        TawSystemSessionForm sysuser = this.getUser(request);
        PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
        PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);


        if (null != user) {//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是deptMagId like
            String deptMagId = sessionForm.getDeptid();
            if (deptMagId.length() > 6) {
                search.addFilterLike("deptMagId", deptMagId.substring(0, deptMagId.length() - 2) + "%");
            } else {
                search.addFilterLike("deptMagId", deptMagId + "%");
            }
        } else if (!"admin".equals(userId)) {//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like

            ITawSystemDeptManager mgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
            TawSystemDept dept = mgr.getDeptinfobydeptid(sysuser.getDeptid(), "0");
            String areaid = dept.getAreaid();
            PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
            List partnerList = partnerDeptMgr.getPartnerDepts(" and countyId like '" + areaid + "%'");
            List<String> deptMagIdList = new ArrayList<String>();

            for (int i = 0; partnerList != null && i < partnerList.size(); i++) {
                PartnerDept partnerDept = (PartnerDept) partnerList.get(i);

                String deptMagId = StaticMethod.nullObject2String(partnerDept.getDeptMagId());
                if (!deptMagId.equals("")) {
                    deptMagIdList.add(deptMagId);
                }
            }
            if (deptMagIdList != null && deptMagIdList.isEmpty() == false) {
                search.addFilterIn("deptMagId", deptMagIdList);
            } else {//如果没有管理代维公司，则不允许看该界面
                return mapping.findForward("inspectnopriv");
            }
        }


        if (StringUtils.isEmpty(currentMonth)) {
            LocalDate date = new LocalDate();
            search.addFilterEqual("year", date.getYear());
            search.addFilterEqual("month", date.getMonthOfYear());
        }

        SearchResult<InspectPlanMain> searchResult = inspectPlanMainMgr.searchAndCount(search);
        List<InspectPlanMain> list = searchResult.getResult();
        InspectPlanMainToListForm inspectPlanMainToListForm;
        List<InspectPlanMainToListForm> returnList = new ArrayList<InspectPlanMainToListForm>();
        for (int i = 0; i < list.size(); i++) {
            inspectPlanMainToListForm = new InspectPlanMainToListForm();
            try {
                list.get(i).setCreateTime(null);
                BeanUtilsBean2.getInstance().copyProperties(inspectPlanMainToListForm, list.get(i));
//				Map<String,Integer> map = inspectPlanResMgr.queryTotalAndHasDoneNum(inspectPlanMainToListForm.getId());
                inspectPlanMainToListForm.setHasDone(list.get(i).getResDoneNum());
                inspectPlanMainToListForm.setResNumber(list.get(i).getResNum());
//				inspectPlanMainToListForm.setPlanNumber(map.get("planCount"));
                inspectPlanMainToListForm.setPlanNumber(list.get(i).getResPlanDoneNum());
                returnList.add(inspectPlanMainToListForm);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("yesOrNoMap", InspectConstants.yesOrNoMap);
        request.setAttribute("approveStatusMap", InspectConstants.approveStatusMap);
        request.setAttribute("list", returnList);
        request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
        request.setAttribute("bsfrList", searchResult.getResult());
        request.setAttribute("size", searchResult.getTotalCount());

        return mapping.findForward("aleradyExceptionSheet");
    }

    /**
     * 巡检资源执行列表
     */
    @SuppressWarnings("unchecked")
    public ActionForward getInspectPlanMainDetailSheet(ActionMapping mapping, ActionForm form,
                                                       HttpServletRequest request, HttpServletResponse response) {
        IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr) getBean("inspectPlanMainMgr");
        IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
        TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        String id = request.getParameter("id");
        String resName = request.getParameter("resName");
        String inspectCycle = request.getParameter("inspectCycle");
        String executeObject = request.getParameter("executeObject");
        String inspectState = request.getParameter("inspectState");
        InspectPlanMain planMain = inspectPlanMainMgr.find(id);
        request.setAttribute("planMain", planMain);
        String finsh = StaticMethod.null2String(request.getParameter("finsh"));

        final Integer pageSize = CommonConstants.PAGE_SIZE;
        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "list");
        final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        String userId = getUserId(request);
        TawSystemSessionForm sysuser = this.getUser(request);
        PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
        PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);
        String whereStr = " where ";
        whereStr += " planId='" + id + "'";

        if (null != user) {//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept like
            whereStr += "  and executeDept like '" + sessionForm.getDeptid() + "%'";
        } else if (!"admin".equals(userId)) {//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like

            ITawSystemDeptManager mgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");

            TawSystemDept dept = mgr.getDeptinfobydeptid(sysuser.getDeptid(), "0");
            String areaid = dept.getAreaid();
            if (areaid != null && !areaid.trim().equals("")) {
                whereStr += "  and country like '" + areaid + "%'";
            } else {//如果移动人员所在部门没有地域，则不允许查看
                return mapping.findForward("inspectnopriv");
            }
        }

        if (!StringUtils.isEmpty(resName)) {
            whereStr += "  and  resName like '%" + resName + "%'";
        }
        if (!StringUtils.isEmpty(inspectCycle)) {
            whereStr += "  and  inspectCycle='" + inspectCycle + "'";
        }
        if (!StringUtils.isEmpty(executeObject)) {
            whereStr += "  and  executeObject='" + executeObject + "'";
        }
//		if(!StringUtils.isEmpty(inspectState)){
//			whereStr+="  and  inspectState='"+inspectState+"'";
//		}
        if ("no".equals(finsh)) {
            whereStr += " and inspectState !=1";
        } else if ("yes".equals(finsh)) {
            whereStr += " and inspectState =1";
        }

//		whereStr += " and inspectState !=1";


        Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex, pageSize, whereStr);
        List list = (List) map.get("result");
        request.setAttribute("list", list);
        request.setAttribute("resultSize", map.get("total"));
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("finsh", finsh);
        request.setAttribute("cycleMap", InspectConstants.cycleMap);
        return mapping.findForward("inspectPlanMainDetailByCheckUserSheet");
    }

    public ActionForward gotoWiteQualityInspectList(ActionMapping mapping, ActionForm form,
                                                    HttpServletRequest request, HttpServletResponse response) {
        PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
        String province = pnrBaseAreaIdList.getRootAreaId();
        List city = PartnerCityByUser.getCityByProvince(province);
        request.setAttribute("city", city);
        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "list");
        int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        Integer pageSize = CommonConstants.PAGE_SIZE;
        IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");

        String whereStr = "";

        String resName = StaticMethod.null2String(request.getParameter("resName"));
        String specialty = StaticMethod.null2String(request.getParameter("resSpecialty"));
        String resType = StaticMethod.null2String(request.getParameter("resType"));
        String resLevel = StaticMethod.null2String(request.getParameter("resLevel"));
        String resCity = StaticMethod.null2String(request.getParameter("city"));
        String country = StaticMethod.null2String(request.getParameter("country"));

        if (!StringUtils.isEmpty(resName)) {
            whereStr += " and res.res_name like '%" + resName + "%'";
        }
        if (!StringUtils.isEmpty(specialty)) {
            whereStr += " and res.specialty='" + specialty + "'";
        }
        if (!StringUtils.isEmpty(resType)) {
            whereStr += " and res.res_type='" + resType + "'";
        }
        if (!StringUtils.isEmpty(resLevel)) {
            whereStr += " and res.res_level='" + resLevel + "'";
        }
        if (!StringUtils.isEmpty(resCity)) {
            whereStr += " and res.city='" + resCity + "'";
        }
        if (!StringUtils.isEmpty(country)) {
            whereStr += " and res.country='" + country + "'";
        }

        InspectPlanRes res = new InspectPlanRes();
        res.setResName(resName);
        res.setSpecialty(specialty);
        res.setResType(resType);
        res.setResLevel(resLevel);
        res.setCity(resCity);
        res.setCountry(country);

        List<Object> list = inspectPlanResMgr.getAllWaitInspectPlanRes(whereStr, pageIndex * pageSize, pageSize);
        request.setAttribute("list", list.get(1));
        request.setAttribute("resultSize", list.get(0));
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("res", res);
        return mapping.findForward("gotoWiteQualityInspectList");
    }

    public ActionForward aleradyWiteQualityInspectList(ActionMapping mapping, ActionForm form,
                                                       HttpServletRequest request, HttpServletResponse response) {

        PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
        String province = pnrBaseAreaIdList.getRootAreaId();
        List city = PartnerCityByUser.getCityByProvince(province);
        request.setAttribute("city", city);
        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "list");
        int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        Integer pageSize = CommonConstants.PAGE_SIZE;
        IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");

        String whereStr = "";

        String resName = StaticMethod.null2String(request.getParameter("resName"));
        String specialty = StaticMethod.null2String(request.getParameter("resSpecialty"));
        String resType = StaticMethod.null2String(request.getParameter("resType"));
        String resLevel = StaticMethod.null2String(request.getParameter("resLevel"));
        String resCity = StaticMethod.null2String(request.getParameter("city"));
        String country = StaticMethod.null2String(request.getParameter("country"));
        String yearStr = StaticMethod.null2String(request.getParameter("year"));
        String monthStr = StaticMethod.null2String(request.getParameter("month"));
        int year;
        int month;

        LocalDate date = new LocalDate();

        if (!StringUtils.isEmpty(resName)) {
            whereStr += " and res.res_name like '%" + resName + "%'";
        }
        if (!StringUtils.isEmpty(specialty)) {
            whereStr += " and res.specialty='" + specialty + "'";
        }
        if (!StringUtils.isEmpty(resType)) {
            whereStr += " and res.res_type='" + resType + "'";
        }
        if (!StringUtils.isEmpty(resLevel)) {
            whereStr += " and res.res_level='" + resLevel + "'";
        }
        if (!StringUtils.isEmpty(resCity)) {
            whereStr += " and res.city='" + resCity + "'";
        }
        if (!StringUtils.isEmpty(country)) {
            whereStr += " and res.country='" + country + "'";
        }
        if (!StringUtils.isEmpty(yearStr)) {   //年和月要么都为空，要么都不为空
            year = Integer.parseInt(yearStr);
            month = Integer.parseInt(monthStr);
        } else {
            year = date.getYear();
            month = date.getMonthOfYear();
        }

        InspectPlanRes res = new InspectPlanRes();
        res.setResName(resName);
        res.setSpecialty(specialty);
        res.setResType(resType);
        res.setResLevel(resLevel);
        res.setCity(resCity);
        res.setCountry(country);


        List<Object> list = inspectPlanResMgr.getAllAleradyInspectPlanRes(year, month, whereStr, pageIndex * pageSize, pageSize);
        request.setAttribute("list", list.get(1));
        request.setAttribute("resultSize", list.get(0));
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("res", res);

        return mapping.findForward("aleradyWiteQualityInspectList");
    }

    public ActionForward gotoQualityInspectForm(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request, HttpServletResponse response) {

        String id = StaticMethod.null2String(request.getParameter("id"));
        request.setAttribute("id", id);
        IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
        InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(Long.parseLong(id));
        request.setAttribute("inspectPlanRes", inspectPlanRes);
        return mapping.findForward("gotoQualityInspectForm");
    }

    public void qualityInspectForm(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {

        TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        String id = StaticMethod.null2String(request.getParameter("id"));
        String satisfaction = StaticMethod.null2String(request.getParameter("satisfaction"));
        String textRemark = StaticMethod.null2String(request.getParameter("textRemark"));
        IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
        InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(Long.parseLong(id));
        inspectPlanRes.setSatisfaction(satisfaction);
        inspectPlanRes.setTextRemark(textRemark);
        inspectPlanRes.setQualityInspectUser(sessionForm.getUserid());
        inspectPlanRes.setQualityInspectTime(new Date());
        inspectPlanResMgr.save(inspectPlanRes);
        request.setAttribute("id", id);

        PrintWriter pw = response.getWriter();

        pw.print("{'success': true}");


    }


    public ActionForward qualityInspectDetail(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String id = StaticMethod.null2String(request.getParameter("id"));

        IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
        InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(Long.parseLong(id));

        request.setAttribute("res", inspectPlanRes);

        return mapping.findForward("qualityInspectDetail");
    }

    public ActionForward getAudioPath(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        String id = request.getParameter("id");

        IPnrInspectTaskFileMgr mgr = (IPnrInspectTaskFileMgr) getBean("pnrInspectTaskFileMgrImpl");
        PnrInspectTaskFile file = mgr.getPnrInspectTaskFileByFileId(id);
        if (null == file) {
            response.getWriter().print("error");
        }
        java.sql.Clob clob = file.getFileData();


        File source = File.createTempFile(new Date().getTime() + "", "tmp.3gp");
        System.out.println(source.getPath());
        String data = "";
        if (clob != null) {
            try {
                Reader reader = clob.getCharacterStream();
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder b = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    b.append(line);
                }
                data = b.toString();


                BASE64Decoder base64 = new BASE64Decoder();

                System.out.println(data);
                byte[] b2 = base64.decodeBuffer(data);
//				System.out.println(new String(b2));

                String path = request.getRealPath("fileTemp");
                String fileName = new Date().getTime() + "" + "tmp.mp3";
                File target = new File(path + "/" + fileName);
                if (!target.exists()) {
                    target.createNewFile();
                }
//			    File target = File.createTempFile(new Date().getTime()+"", "tmp.mp3");
                FileOutputStream fileOutputStream = new FileOutputStream(source);
                fileOutputStream.write(b2);
                fileOutputStream.flush();
                fileOutputStream.close();

                AudioAttributes audio = new AudioAttributes();
                audio.setCodec("libmp3lame");
                audio.setBitRate(new Integer(8000));
                audio.setSamplingRate(new Integer(11025));
                EncodingAttributes attrs = new EncodingAttributes();
                attrs.setFormat("mp3");
                attrs.setAudioAttributes(audio);
                it.sauronsoftware.jave.Encoder encoder = new it.sauronsoftware.jave.Encoder();
                encoder.encode(source, target, attrs);

                source.deleteOnExit();
                System.out.println(target.getPath());
                response.getWriter().print(fileName);
            } finally {
            }
        }
        return null;
    }

    public ActionForward goToDeviceInspectList(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        String detail = StaticMethod.null2String(request.getParameter("detail"));


        PnrInspectTaskLinkService pitls = (PnrInspectTaskLinkService) getBean("pnrInspectTaskLinkService");
        String planResId = StaticMethod.null2String(request.getParameter("planResId"));

        Search search = new Search();
        int firstResult = CommonUtils.getFirstResultOfDisplayTag(request, "deviceInspectList");
        search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
        search.setMaxResults(CommonConstants.PAGE_SIZE);
        search = CommonUtils.getSqlFromRequestMap(request, search);
        search.addFilterEqual("planResId", planResId);

        SearchResult searchResult = pitls.searchAndCount(search);
        List deviceInspectList = searchResult.getResult();

        request.setAttribute("deviceInspectList", deviceInspectList);
        request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
        request.setAttribute("size", searchResult.getTotalCount());
		/*if("detail".equals(detail)){
			return mapping.findForward("inspectPlanItemListByCheckUserDetai");
		}*/

        IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
        InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(Long.parseLong(planResId));
        if (inspectPlanRes.getSpecialty().equals("1122502")) {
            request.setAttribute("transLine", "yes");
        }
        request.setAttribute("detail", detail);
        return mapping.findForward("goToDeviceInspectList");

    }

    public ActionForward goToDeviceInspectPlanMainItemList(ActionMapping mapping, ActionForm form,
                                                           HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr) getBean("inspectPlanItemMgrImpl");
        String plan_res_id = request.getParameter("planResId");
        String inspectTaskLinkId = StaticMethod.null2String(request.getParameter("inspectTaskLinkId"));
        String detail = StaticMethod.null2String(request.getParameter("detail"));
        Search search = new Search();
        search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
        String pageIndexString = request.getParameter((new org.displaytag.util.ParamEncoder("inspectPlanItemList").encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
        search.setMaxResults(CommonConstants.PAGE_SIZE);
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
//		search.addSortAsc("saveTime");
        search.addFilterEqual("planResId", plan_res_id);
		/*查看公共巡检项 add by zhangkeqi 2013-02-26 begin*/
        search.addFilterEqual("inspectTaskLinkId", inspectTaskLinkId);
        search.addFilterEqual("deviceInspectFlag", 1);
		/*查看公共巡检项 add by zhangkeqi 2013-02-26 end*/
        SearchResult<InspectPlanItem> returnSearch = inspectPlanItemMgr.searchAndCount(search);


        List<InspectPlanItem> inspectPlanItemList = returnSearch.getResult();
        IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
        InspectPlanRes res = inspectPlanResMgr.get(Long.parseLong(plan_res_id));
        if (inspectPlanItemList.isEmpty()) {
            request.setAttribute("resultSize", 0);
        } else {
            InspectPlanItemForm inspectPlanItemForm;
            List<InspectPlanItemForm> returnList = new ArrayList<InspectPlanItemForm>();
            for (int i = 0; i < inspectPlanItemList.size(); i++) {
                inspectPlanItemForm = new InspectPlanItemForm();
                try {
                    BeanUtils.copyProperties(inspectPlanItemForm, inspectPlanItemList.get(i));
                    inspectPlanItemForm.setEndTime((res.getPlanEndTime() + "").substring(0, (res.getPlanEndTime() + "").length() - 2));
//					String dictIdTemp = inspectPlanItemList.get(i).getItemResult();
                    returnList.add(inspectPlanItemForm);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            request.setAttribute("inspectPlanItemList", returnList);
            request.setAttribute("resultSize", returnSearch.getTotalCount());
        }
        IInspectPlanExecuteMgr executeMgr = (IInspectPlanExecuteMgr) getBean("inspectPlanExecuteMgrImpl");
        request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
        request.setAttribute("planResId", plan_res_id);
        request.setAttribute("InspectPlanRes", res);
        request.setAttribute("isCheckUser", executeMgr.isCheckUser(getUser(request).getDeptid(), res.getExecuteDept()));
        if ("detail".equals(detail)) {
            return mapping.findForward("inspectPlanItemListByCheckUserDetai");
        }
        return mapping.findForward("deviceInspectPlanItemListByCheckUser");
    }


    public boolean inspectPointFinsh(String pointId) {
        boolean finsh = false;
        IPnrTransLineMgr pnrTransLineMgr = (IPnrTransLineMgr) getBean("pnrTransLineMgrImpl");
        finsh = pnrTransLineMgr.isFinishedTLPInspectItem(pointId);
        return finsh;
    }

    public InspectPlanRes updateResCfg(InspectPlanRes res, HttpServletRequest request) {
        IPnrTransLineMgr pnrTransLineMgr = (IPnrTransLineMgr) getBean("pnrTransLineMgrImpl");
        //此时是手机完成的
        if (res.getTlRealArrivedRate() != null && (Long.parseLong(res.getTlRealArrivedRate()) >= Long.parseLong(res.getTlArrivedRate()))) {
            res.setInspectState(1);
            res.setSignStatus(0);
        } else {
            //查询比到点中的已巡检了的个数
            String sql1 = "select count(*) from pnr_inspect_task_link where PLAN_RES_ID=" + res.getId() + " and is_must_arrive=1 and " + CommonSqlHelper.formatNotEmpty("SIGN_STATUS") + "";
            //查询所有必到点
            String sql2 = "select count(*) from pnr_inspect_task_link where PLAN_RES_ID=" + res.getId() + " and is_must_arrive=1 ";
            int num1 = pnrTransLineMgr.getPnrTransLineDao().getJdbcTemplate().queryForInt(sql1);
            int num2 = pnrTransLineMgr.getPnrTransLineDao().getJdbcTemplate().queryForInt(sql2);

            if (num2 == 0 || (((double) num1) / ((double) num2) >= Double.parseDouble(res.getTlArrivedRate()))) {
                res.setInspectState(1);
                res.setSignStatus(-1);
                res.setInspectTime(new Date());
                res.setInspectUser(this.getUserId(request));
//				res.setItemDoneNum(StaticMethod.nullObject2int(res.getItemDoneNum())+1);
            }
        }
//		inspectPlanResMgr.save(res);
        return res;
    }
    
    /**
     * 巡检资源列表
     */
    @SuppressWarnings("unchecked")
    public ActionForward getInspectPlanMainDetailList(ActionMapping mapping, ActionForm form,
                                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
        IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr) getBean("inspectPlanMainMgr");
        IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
        TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        String resName = request.getParameter("resName");
        String specialty = request.getParameter("specialty");
        String resType = request.getParameter("resType");
        String resLevel = request.getParameter("resLevel");
        String region = request.getParameter("region");
        String country = request.getParameter("country");
//        String inspectCycle = request.getParameter("inspectCycle");
//        String executeObject = request.getParameter("executeObject");
        String inspectState = request.getParameter("inspectState");

        final Integer pageSize = CommonConstants.PAGE_SIZE;
        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "list");
        final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        String userId = getUserId(request);
        //获取地市集合
        PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
        List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
        TawSystemSessionForm sysuser = this.getUser(request);
        //获取当前 年 月
        Calendar cale = null;
		cale = Calendar.getInstance();
		String year =Integer.toString(cale.get(Calendar.YEAR));
		String month =Integer.toString(cale.get(Calendar.MONTH) + 1);
		 System.out.println();
        String whereStr = " where 1=1 and res.planId=main.id ";
        Map map1 = PartnerPrivUtils.userIsPersonnel(request);
        String flag = map1.get("isPersonnel").toString();
        //根据当前登陆人部门id 获取地市id
        String areaids=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionForm.getDeptid());
        	System.out.println(areaids);
        	if(areaids!=null && !"".equals(areaids)){
        		whereStr+=" and res.country like '"+areaids+"%' ";
        	}
        
        if ("y".equals(flag)) {//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept like
            whereStr += "  and res.executeDept like '" + map1.get("deptMagId").toString() + "%'";
        } else if (!"admin".equals(userId)) {//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like

            ITawSystemDeptManager mgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");


            PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");


            PartnerDept partnerDept = partnerDeptMgr.getPartnerDept(sysuser.getDeptid());

//			whereStr += " executeDept like '"+ partnerDept.get +"%'";

            if (areaids != null && !areaids.trim().equals("")) {
                whereStr += "  and res.country like '" + areaids + "%'";
            } else {
                return mapping.findForward("inspectnopriv");
            }
        }
        
        if(year!=null && !"".equals(year)){
        	whereStr +=" and main.year='"+year+"'";
        }
        if(month!=null && !"".equals(month)){
        	whereStr +=" and main.month='"+month+"'";
        }
        if (!StringUtils.isEmpty(resName)) {
            whereStr += "  and  res.resName like '%" + resName + "%'";
        }
        if (!StringUtils.isEmpty(specialty)) {
            whereStr += "  and  res.specialty='" + specialty + "'";
        }
        if (!StringUtils.isEmpty(resType)) {
            whereStr += "  and  res.resType='" + resType + "'";
        }
        if (!StringUtils.isEmpty(resLevel)) {
            whereStr += "  and  res.resLevel='" + resLevel + "'";
        }
        if (!StringUtils.isEmpty(region)) {
            whereStr += "  and  res.city='" + region + "'";
        }
        if (!StringUtils.isEmpty(country)) {
            whereStr += "  and  res.country='" + country + "'";
        }
        
        if (!StringUtils.isEmpty(inspectState)) {
        	whereStr += "  and  res.inspectState='" + inspectState + "'";
        }
        
        
        if (!StringUtils.isEmpty(request.getParameter("tlDis"))) {
            whereStr += " and tlDis like '" + request.getParameter("tlDis") + "%'";
        }
        if (!StringUtils.isEmpty(request.getParameter("tlWire"))) {
            whereStr += " and tlWire like '" + request.getParameter("tlWire") + "%'";
        }
        if (!StringUtils.isEmpty(request.getParameter("tlSeg"))) {
            whereStr += " and tlSeg like '" + request.getParameter("tlSeg") + "%'";
        }

        Map map = (Map) inspectPlanResMgr.getInspectPlanMainDetailList(pageIndex, pageSize, whereStr);
        List list = (List) map.get("result");
        request.setAttribute("list", list);
        request.setAttribute("resultSize", map.get("total"));
        request.setAttribute("pageSize", pageSize);
        PnrDeviceInspectSwitchConfig transLineswitch = (PnrDeviceInspectSwitchConfig) request.getSession().getServletContext().getAttribute("pnrInspect2SwitchConfig");
        request.setAttribute("cycleMap", InspectConstants.cycleMap);
        request.setAttribute("specialty", "specialty");
        request.setAttribute("resType", "resType");
        request.setAttribute("resLevel", "resLevel");
        request.setAttribute("region", "region");
        request.setAttribute("country", "country");
        request.setAttribute("inspect_State", "inspect_State");
        request.setAttribute("pageType", request.getParameter("pageType"));

        if (MobileCommonUtils.isAndroidRequest(request)) {
            com.boco.eoms.mobile.util.TransLineHandler.handRequestLineSeg(request, response);
            return null;
        }
        return mapping.findForward("inspectPlanMainDetailByCheckUserList");
    }
    /**
     * 查看巡检项详情
     */
    public ActionForward goToInspectPlanMainItemLists(ActionMapping mapping, ActionForm form,
                                                     HttpServletRequest request, HttpServletResponse response) {
        IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr) getBean("inspectPlanItemMgrImpl");
        String plan_res_id = request.getParameter("planResId");

        Search search = new Search();
        search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
        String detail = request.getParameter("detail");
        String pageIndexString = request.getParameter((new org.displaytag.util.ParamEncoder("inspectPlanItemList").encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
        search.setMaxResults(CommonConstants.PAGE_SIZE);
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
       // search.addSortAsc("exceptionFlag");
        //search.addSortDesc("saveTime");
        search.addFilterEqual("planResId", plan_res_id);
        /*查看公共巡检项 add by zhangkeqi 2013-02-26 begin*/
        search.addFilter(new Filter().or(new Filter().notEqual("deviceInspectFlag", 1), new Filter().isNull("deviceInspectFlag")));
		/*查看公共巡检项 add by zhangkeqi 2013-02-26 end*/

        SearchResult<InspectPlanItem> returnSearch = inspectPlanItemMgr.searchAndCount(search);


        List<InspectPlanItem> inspectPlanItemList = returnSearch.getResult();
        IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
        InspectPlanRes res = inspectPlanResMgr.get(Long.parseLong(plan_res_id));
        if (inspectPlanItemList.isEmpty()) {
            request.setAttribute("resultSize", 0);
        } else {
            InspectPlanItemForm inspectPlanItemForm;
            List<InspectPlanItemForm> returnList = new ArrayList<InspectPlanItemForm>();
            for (int i = 0; i < inspectPlanItemList.size(); i++) {
                inspectPlanItemForm = new InspectPlanItemForm();
                try {
                    BeanUtils.copyProperties(inspectPlanItemForm, inspectPlanItemList.get(i));
                    inspectPlanItemForm.setEndTime((res.getPlanEndTime() + "").substring(0, (res.getPlanEndTime() + "").length() - 2));
//					String dictIdTemp = inspectPlanItemList.get(i).getItemResult();
                    returnList.add(inspectPlanItemForm);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            request.setAttribute("inspectPlanItemList", returnList);
            request.setAttribute("resultSize", returnSearch.getTotalCount());
        }
        IInspectPlanExecuteMgr executeMgr = (IInspectPlanExecuteMgr) getBean("inspectPlanExecuteMgrImpl");
        request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
        request.setAttribute("planResId", plan_res_id);
        request.setAttribute("planId", res.getPlanId());
        request.setAttribute("InspectPlanRes", res);
        request.setAttribute("isCheckUser", executeMgr.isCheckUser(getUser(request).getDeptid(), res.getExecuteDept()));
        if ("detail".equals(detail)) {
            return mapping.findForward("inspectPlanItemListByCheckUserDetaiList");
        } else {
            return mapping.findForward("inspectPlanItemListByCheckUserList");
        }
    }

}