package com.boco.eoms.examonline.controller;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005-12-20</p>
 * <p>Company: </p>
 * @author 邓林华
 * @version 1.0
 */


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.dept.dao.hibernate.TawSystemDeptDaoHibernate;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.dao.hibernate.TawSystemUserDaoHibernate;
import com.boco.eoms.commons.system.user.dao.hibernate.TawSystemUserRefRoleDaoHibernate;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.examonline.dao.ExamDAO;
import com.boco.eoms.examonline.form.AdminSelectExamForm;
import com.boco.eoms.examonline.form.ExamTypeSelForm;
import com.boco.eoms.examonline.form.SubmodifyForm;
import com.boco.eoms.examonline.model.ExamConfig;
import com.boco.eoms.examonline.model.ExamContent;
import com.boco.eoms.examonline.model.ExamIssue;
import com.boco.eoms.examonline.model.ExamSubmit;
import com.boco.eoms.examonline.model.ExamTypeSel;
import com.boco.eoms.examonline.model.ExamWarehouse;
import com.boco.eoms.examonline.qo.ExamDeptQueryVO;
import com.boco.eoms.examonline.qo.ExamTesterQueryVO;
import com.boco.eoms.examonline.service.ExamResultService;
import com.boco.eoms.examonline.service.ExamService;
import com.boco.eoms.examonline.service.ExamTypeSelService;
import com.boco.eoms.examonline.util.ExamUtil;
import com.boco.eoms.gz.util.SystemResource;
import com.boco.eoms.gz.util.TimeUtil;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;

public class ExamAction extends Action {
	
  private static int PAGE_LENGTH = 20;
  private ExamService studyOnlineBO = (ExamService)ApplicationContextHolder.getInstance().getBean("examService");
  private ExamTypeSelService typeSelBO = (ExamTypeSelService)ApplicationContextHolder.getInstance().getBean("examTypeSelService");
  static 
  {
    ResourceBundle prop = ResourceBundle.getBundle("resources.application_zh_CN");
    try
    {
      PAGE_LENGTH = Integer.parseInt(prop.getString("list.page.length"));
    }
    catch (Exception e) {
    }
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception{
    ActionForward myforward = null;
    String myaction = mapping.getParameter();
    TawSystemSessionForm saveSessionBeanForm = (TawSystemSessionForm)
    request.getSession().getAttribute("sessionform");
    if (saveSessionBeanForm == null) {
      return mapping.findForward("timeout");
    }
    if (isCancelled(request)) {
      return mapping.findForward("cancel");
    }

    if ("".equalsIgnoreCase(myaction)) 
    {
      myforward = mapping.findForward("failure1");
    }
    else if ("import".equalsIgnoreCase(myaction)) {
      myforward = performImport(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("import_word".equalsIgnoreCase(myaction)) {
      myforward = performImport_word(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("StudySelect".equalsIgnoreCase(myaction)) {
      myforward = performStudySelect(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("saveDefault".equalsIgnoreCase(myaction)) {
      myforward = performSaveDefault(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("study".equalsIgnoreCase(myaction)) {
      myforward = performStudy(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("StudySubmit".equalsIgnoreCase(myaction)) {
      myforward = performStudySubmit(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("subManage".equalsIgnoreCase(myaction)) {
      myforward = performSubManage(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("deleteSub".equalsIgnoreCase(myaction)) {
      myforward = performDeleteSub(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("batchDelete".equalsIgnoreCase(myaction)) {
      myforward = performBatchDelete(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("modifySub".equalsIgnoreCase(myaction)) {
      myforward = performSubModify(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("modifySubmit".equalsIgnoreCase(myaction)) {
      myforward = performModifySubmit(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("examConfig".equalsIgnoreCase(myaction)) {
      myforward = performExamConfig(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("examConfigList".equalsIgnoreCase(myaction)) {
        myforward = performExamConfigList(mapping, form, request, response,saveSessionBeanForm);
      }
    else if ("configSubmit".equalsIgnoreCase(myaction)) 
    {
      myforward = performConfigSubmit(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("examine".equalsIgnoreCase(myaction)) {
      myforward = performExamine(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("studyQuery".equalsIgnoreCase(myaction)) {
      myforward = performStudyQuery(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("queryDo".equalsIgnoreCase(myaction)) {
      myforward = performQueryDo(mapping, form, request, response,saveSessionBeanForm);
    }else if("queryDoSelf".equalsIgnoreCase(myaction)){
      myforward = performQueryDoSelf(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("detail".equalsIgnoreCase(myaction)) {
      myforward = performDetail(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("examQuery".equalsIgnoreCase(myaction)) {
      myforward = performExamQuery(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("examQuerySub".equalsIgnoreCase(myaction)) {
        myforward = performExamQuerySub(mapping, form, request, response,saveSessionBeanForm);
      }
    else if ("examQueryDO".equalsIgnoreCase(myaction)) {
      myforward = performExamQueryDO(mapping, form, request, response,saveSessionBeanForm);
    } 
    else if ("examDeptQuery".equalsIgnoreCase(myaction)) {
      myforward = performExamDeptQuery(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("examTesterQuery".equalsIgnoreCase(myaction)) {
        myforward = performExamTesterQuery(mapping, form, request, response,saveSessionBeanForm);
      }
    else if ("examFinish".equalsIgnoreCase(myaction)) {
      myforward = performExamFinish(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("report".equalsIgnoreCase(myaction)) {
      myforward = performReport(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("reportDO".equalsIgnoreCase(myaction)) {
      myforward = performReportDO(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("delImport".equalsIgnoreCase(myaction)) {
      myforward = performDelImport(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("reportDetail".equalsIgnoreCase(myaction)) {
      myforward = performReportDetail(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("physicalDelSub".equalsIgnoreCase(myaction)) {
      myforward = performPhysicalDelSub(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("auditing".equalsIgnoreCase(myaction)) {
      myforward = performAuditing(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("auditingDO".equalsIgnoreCase(myaction)) {
      myforward = performAuditingDO(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("reject".equalsIgnoreCase(myaction)) {
      myforward = performReject(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("auditingDetail".equalsIgnoreCase(myaction)) {
      myforward = performAuditingDetail(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("issueConfig".equalsIgnoreCase(myaction)) {
      myforward = performIssueConfig(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("issueDO".equalsIgnoreCase(myaction)) {
      myforward = performIssueDO(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("issueInfo".equalsIgnoreCase(myaction)) {
      myforward = performIssueInfo(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("examDelete".equalsIgnoreCase(myaction)) {
      myforward = performExamDelete(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("examDeleteDO".equalsIgnoreCase(myaction)) {
      myforward = performExamDeleteDO(mapping, form, request, response,saveSessionBeanForm);
    }
    else if ("ajaxgetdept".equalsIgnoreCase(myaction)) {
    	performExamAjaxgetdept(mapping, form, request, response,saveSessionBeanForm);
      }
    else if ("ajaxgetuser".equalsIgnoreCase(myaction)) {
    	performExamAjaxgetUser(mapping, form, request, response,saveSessionBeanForm);
      }
    
    else if ("examdelete_ajax".equalsIgnoreCase(myaction)) {
    	performDeleteExamcif(mapping, form, request, response,saveSessionBeanForm);
      }
    else if ("designate".equalsIgnoreCase(myaction)) {
    	myforward=designate(mapping, form, request, response,saveSessionBeanForm);
      }
    else if ("uniontesters".equalsIgnoreCase(myaction)) {
    	uniontesters(mapping, form, request, response,saveSessionBeanForm);
      }
    else if ("gettestersbyIssueid".equalsIgnoreCase(myaction)) {
    	gettestersbyIssueid(mapping, form, request, response,saveSessionBeanForm);
      }
    else if ("myexamList".equalsIgnoreCase(myaction)) {
    	myforward=getMyExamList(mapping, form, request, response,saveSessionBeanForm);
      }
    else if ("getAdminExamList".equalsIgnoreCase(myaction)) {
    	myforward=getAdminExamList(mapping, form, request, response,saveSessionBeanForm);
      }
    else if ("getAdminSelectExamList".equalsIgnoreCase(myaction)) {
    	myforward=getAdminSelectExamList(mapping, form, request, response,saveSessionBeanForm);
      }
    else if ("wordupload".equalsIgnoreCase(myaction)) {
    	myforward=wordupload(mapping, form, request, response,saveSessionBeanForm);
      }
    else if("findExamCount".equalsIgnoreCase(myaction)){
    	myforward=findExamCount(mapping, form, request, response,saveSessionBeanForm);
    }
    else if("worddownload".equalsIgnoreCase(myaction)){
    	myforward=worddownload(mapping, form, request, response,saveSessionBeanForm);
    }else if("operateExamConfig".equalsIgnoreCase(myaction)){
    	myforward = operateExamConfig(mapping, form, request, response,saveSessionBeanForm);
    }else if("operateExamConfigAdd".equalsIgnoreCase(myaction)){
    	myforward = operateExamConfigAdd(mapping, form, request, response,saveSessionBeanForm);
    }else if("findQaMarkList".equalsIgnoreCase(myaction)){
    	myforward = findQaMarkList(mapping, form, request, response,saveSessionBeanForm);
    }
    else if("findQaMarkPerson".equalsIgnoreCase(myaction)){
    	myforward = findQaMarkPerson(mapping, form, request, response,saveSessionBeanForm);
    }
    else if("qaMarkFinish".equalsIgnoreCase(myaction)){
    	myforward = qaMarkFinish(mapping, form, request, response,saveSessionBeanForm);
    }else if("mockExam".equalsIgnoreCase(myaction)){
    	myforward = performMockExam(mapping, form, request, response,saveSessionBeanForm);
    }else if("changeExamScore".equalsIgnoreCase(myaction)){
    	myforward = changeExamScore(mapping, form, request, response,saveSessionBeanForm);
    }else if("exportScore".equalsIgnoreCase(myaction)){
	  myforward = exportScore(mapping, form, request, response,saveSessionBeanForm);
    }
    else if("addTypesel".equalsIgnoreCase(myaction)){
	  myforward = addTypesel(mapping, form, request, response,saveSessionBeanForm);
	  }
    else if("delTypesel".equalsIgnoreCase(myaction)){
    	myforward = delTypesel(mapping, form, request, response,saveSessionBeanForm);
    }
    else if("performGernateExam".equalsIgnoreCase(myaction)){
    	myforward = performGernateExam(mapping, form, request, response,saveSessionBeanForm);
    }
    else if("sendSelectDeptPage".equalsIgnoreCase(myaction)){
    	myforward = sendSelectDeptPage(mapping, form, request, response,saveSessionBeanForm);
    }
    else if("generateExam".equalsIgnoreCase(myaction)){
    	myforward = generateExam(mapping, form, request, response,saveSessionBeanForm);
    }
    else if("generateExamPartner".equalsIgnoreCase(myaction)){
    	myforward = generateExamPartner(mapping, form, request, response,saveSessionBeanForm);
    }
    return myforward;
  }

  private ActionForward performImport(ActionMapping mapping,
                                      ActionForm actionForm,
                                      HttpServletRequest request,
                                      HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm) {
	  return mapping.findForward("success");
  }
  private ActionForward performImport_word(ActionMapping mapping,
                                        ActionForm actionForm,
                                        HttpServletRequest request,
                                        HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm) {
      request.setAttribute("specialtySelList", ExamUtil.getSpecialtySel());
      request.setAttribute("manufacturerSelList", SystemResource.getManufacturerSel());
      return mapping.findForward("success");
    }

  /**
   * 自测练习
   */
  private ActionForward performStudySelect(ActionMapping mapping,
                                      ActionForm actionForm,
                                      HttpServletRequest request,
                                      HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
    String currentUser = "";   //执行人ID
    currentUser = saveSessionBeanForm.getUserid();
    try{
      List configLabel = studyOnlineBO.getStudyConfigLabel(currentUser);
      request.setAttribute("specialtySelList", ExamUtil.getSpecialtySel());
      request.setAttribute("manufacturerSelList", SystemResource.getManufacturerSel());
      request.setAttribute("configLabel", configLabel);
    }catch (Exception e) {
      generalError(request, e);
      return mapping.findForward("failure");
    }
    return mapping.findForward("success");
  }

  private ActionForward performSaveDefault(ActionMapping mapping,
                                      ActionForm actionForm,
                                      HttpServletRequest request,
                                      HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm) {
    String currentUser = "";   //执行人ID
    currentUser = saveSessionBeanForm.getUserid();
    try{
      studyOnlineBO.saveDefault(currentUser, ((DynaActionForm)actionForm).getMap());
    }
    catch (Exception e) {
      generalError(request, e);
      return mapping.findForward("failure");
    }
    return mapping.findForward("success");
  }

  /**
   * 自测练习随机出题
   */
  private ActionForward performStudy(ActionMapping mapping,
                                      ActionForm actionForm,
                                      HttpServletRequest request,
                                      HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm) {
     String currentUser = "";   //执行人ID
     currentUser = saveSessionBeanForm.getUserid();
     try{
    	 List subObjlist = null;
    	 List toplist = null;
    	 String typeSel = StaticMethod.null2String(request.getParameter("typeSel"));
    	 if ( "".equalsIgnoreCase(typeSel) )
    		 return mapping.findForward("error");
	    //获取用户信息
	    ExamContent userContent = new ExamContent();
	    userContent = studyOnlineBO.getUserStudycontent(currentUser);
	    //随机获取试题
	    subObjlist = studyOnlineBO.NewSubject(typeSel);
	    //获取积分top10
	    toplist = studyOnlineBO.getTopTen();

	    if (subObjlist.size() == 0)
	    	return mapping.findForward("warehousenull");
	    request.setAttribute("ExamWarehouse",subObjlist);
	    request.setAttribute("Usercontent",userContent);
	    request.setAttribute("TopTen",toplist);
	  }catch (Exception e) {
	    generalError(request, e);
	    return mapping.findForward("failure");
	  }
    return mapping.findForward("success");
}

  /**
   * 自测随机练习提交
   */
private ActionForward performStudySubmit(ActionMapping mapping,
          ActionForm actionForm,
          HttpServletRequest request,
          HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm) {
    String currentUser = "";   //执行人ID
    currentUser = saveSessionBeanForm.getUserid();
    try{
    	String optionsTemp = StaticMethod.null2String(request.getParameter("options"));
	    //将答案转成OnlineWarehouse的格式，对象中只存放有 subId 和 用户提交的答案
	    List answerList = studyOnlineBO.getOptions(optionsTemp);
	    ExamWarehouse subObj =studyOnlineBO.submitStudy(answerList , currentUser);
	    request.setAttribute("ExamWarehouse",subObj);
	}catch (Exception e) {
		generalError(request, e);
		return mapping.findForward("failure");
	}
	return mapping.findForward("success");
}
  
  /**
   * 自测练习模拟考试出题
   */
  private ActionForward performMockExam(ActionMapping mapping,
                                      ActionForm actionForm,
                                      HttpServletRequest request,
                                      HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm) {
	  String tag = "mockExam";
	    try {
	      String begin = request.getParameter("begin");
	      if (StringUtils.isEmpty(begin)) {
	        request.setAttribute("specialtySelList", ExamUtil.getSpecialtySel());
	        request.setAttribute("manufacturerSelList", SystemResource.getManufacturerSel(new int[0]));
	        return mapping.findForward("config");
	      }
	      String remock = StaticMethod.null2String(request.getParameter("cmd"));
	      if ("remock".equals(remock)) {
	        List examTypeSelList = (List)request.getSession().getAttribute("mockExamTypeSelList");
	        List list = new ArrayList();
	        List examWarehouseList = this.typeSelBO.getExamWarehouseList(examTypeSelList, tag);
	        List templist = new ArrayList();

	        for (int j = 1; j < 6; ++j) {
	          for (int i = 0; i < examWarehouseList.size(); ++i) {
	            if (((ExamWarehouse)examWarehouseList.get(i)).getContype() == j) {
	              templist.add((ExamWarehouse)examWarehouseList.get(i));
	            }
	          }
	        }
	        list = templist;

	        request.setAttribute("list", list);
	        return mapping.findForward("mockExam");
	      }

	      if (request.getSession().getAttribute("mockExamTypeSelList") != null) {
	        request.getSession().removeAttribute("mockExamTypeSelList");
	      }
	      String typeSelValue = request.getParameter("typeSelValue");
	      String difficultyValue = request.getParameter("difficultyValue");
	      String manufacturerSel = request.getParameter("manufacturerSel");
	      int radio = StaticMethod.null2int(request.getParameter("radio"));
	      int judge = StaticMethod.null2int(request.getParameter("judge"));
	      int multiple = StaticMethod.null2int(request.getParameter("multiple"));
	      List list = new ArrayList();

	      List examTypeSelList = new ArrayList();
	      ExamTypeSel examTypeSel = new ExamTypeSel();
	      examTypeSel.setSpecialty(typeSelValue);
	      examTypeSel.setRadioCount(Integer.valueOf(radio));
	      examTypeSel.setJudgeCount(Integer.valueOf(judge));
	      examTypeSel.setMultipleCount(Integer.valueOf(multiple));
	      examTypeSel.setDifficulty(Integer.valueOf(StaticMethod.nullObject2int(Integer.valueOf(Integer.parseInt(difficultyValue) + 1))));
	      examTypeSel.setManufacturer(Integer.valueOf(Integer.parseInt(manufacturerSel)));
	      examTypeSelList.add(examTypeSel);
	      List examWarehouseList = this.typeSelBO.getExamWarehouseList(examTypeSelList, tag);
	      List templist = new ArrayList();

	      for (int j = 1; j < 6; ++j) {
	        for (int i = 0; i < examWarehouseList.size(); ++i) {
	          if (((ExamWarehouse)examWarehouseList.get(i)).getContype() == j) {
	            templist.add((ExamWarehouse)examWarehouseList.get(i));
	          }
	        }
	      }
	      list = templist;

	      request.setAttribute("list", list);
	      request.getSession().setAttribute("mockExamTypeSelList", examTypeSelList);
	      return mapping.findForward("mockExam");
	    }
	    catch (Exception e)
	    {
	      generalError(request, e); }
	    return mapping.findForward("failure");
  }

  private ActionForward performSubManage(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm) {
  try{
    ExamDAO DAO =(ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
    List sublist = DAO.getAllExamWarehouse();
    List infoLablist = studyOnlineBO.getInfoLab();
    request.setAttribute("SUBLIST",sublist);
    request.setAttribute("INFOLABLIST",infoLablist);
   }
    catch (Exception e) {
      generalError(request, e);
      return mapping.findForward("failure");
    }
    return mapping.findForward("success");
  }

  private ActionForward performDeleteSub(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
    try{
      String checkSel = StaticMethod.null2String(request.getParameter("checkSel"));
      ExamDAO DAO =(ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
      DAO.deleteSubs(checkSel);
    }
    catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
    }
    return mapping.findForward("success");
  }

  private ActionForward performBatchDelete(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
    try{
      String batchSel = StaticMethod.null2String(request.getParameter("batchSel"));
      ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
      DAO.batchDelete(batchSel);
    }
    catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
    }
    return mapping.findForward("success");
  }

  /**
   *  修改题库试题展现页面
   */
  private ActionForward performSubModify(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
	  try
	    {
	      ID2NameService service = (ID2NameService)ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
	      String subId = StaticMethod.null2String(request.getParameter("subId"));
	      ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
	      ExamWarehouse onlineWarehouse = DAO.loadSingleOfExamWarehouse(subId);
	      request.setAttribute("specialtySelList", ExamUtil.getSpecialtySel(new String[] { onlineWarehouse.getSpecialty() }));
	      int i = onlineWarehouse.getManufacturer();
//	      String str = i;
	      request.setAttribute("manufacturerSelList", SystemResource.getManufacturerSel(new int[] { onlineWarehouse.getManufacturer() }));
	      request.setAttribute("ExamWarehouse", onlineWarehouse);
	    }
	    catch (Exception e) {
	      generalError(request, e);
	      return mapping.findForward("failure");
	    }
	    return mapping.findForward("success");
  }

  /**
   * 试题修改提交
   */
  private ActionForward performModifySubmit(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
	  try
	    {
	      String subId = StaticMethod.nullObject2String(request.getParameter("subId"));
	      ExamWarehouse onlineWarehouse = (ExamWarehouse)this.studyOnlineBO.load(ExamWarehouse.class, subId);
	      onlineWarehouse.setImportId(StaticMethod.nullObject2String(request.getParameter("importId")));
	      ID2NameService service = (ID2NameService)ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
	      SubmodifyForm form = (SubmodifyForm)actionForm;
	      FormFile file = form.getUploadFile();
	      String filename = file.getFileName();
	      String image = "";

	      if ((!("".equals(filename))) && (filename != null)) {
	        byte[] b = new byte[1024];
	        InputStream is = null;
	        FileOutputStream fos = null;
	        String filePath = this.servlet.getServletContext().getRealPath("/");
	        filePath = filePath + "examonline/upload/";

	        String newFileName = ExamUtil.getRandomFileName(filename, filePath);
	        image = newFileName;
	        try
	        {
	          is = file.getInputStream();
	          fos = new FileOutputStream(filePath + newFileName);
	          while (is.read(b) != -1)
	            fos.write(b);
	        }
	        catch (Exception e) {
	          e.printStackTrace();
	        } finally {
	          try {
	            fos.close();
	            is.close();
	          } catch (IOException e) {
	            e.printStackTrace();
	          }
	        }
	      }

	      onlineWarehouse.setTitle(StaticMethod.nullObject2String(form.getTitle()));
	      onlineWarehouse.setOptions(StaticMethod.nullObject2String(form.getOptions()));
	      onlineWarehouse.setResult(StaticMethod.nullObject2String(form.getResult()));
	      onlineWarehouse.setDifficulty(StaticMethod.nullObject2int(Integer.valueOf(form.getDifficulty())));
	      onlineWarehouse.setIssueType(StaticMethod.nullObject2int(request.getParameter("issueType")));

	      onlineWarehouse.setSpecialty(StaticMethod.nullObject2String(Integer.valueOf(form.getSpecialty())));
	      onlineWarehouse.setManufacturer(StaticMethod.nullObject2int(Integer.valueOf(form.getManufacturer())));
	      onlineWarehouse.setComment(StaticMethod.nullObject2String(form.getComment().trim()));
	      if (!("".equals(image))) {
	        onlineWarehouse.setImage(image);
	      }
	      this.studyOnlineBO.update(onlineWarehouse);

	      request.setAttribute("specialtySelList", ExamUtil.getSpecialtySel(new String[] { onlineWarehouse.getSpecialty() }));

	      request.setAttribute("manufacturerSelList", SystemResource.getManufacturerSel(new int[] { onlineWarehouse.getManufacturer() }));
	      request.setAttribute("ExamWarehouse", onlineWarehouse);
	      request.setAttribute("contype", Integer.valueOf(onlineWarehouse.getContype()));
	    } catch (Exception e) {
	      generalError(request, e);
	      return mapping.findForward("failure");
	    }
	    return mapping.findForward("success");
  }

  /**
   * 制定考试列表
   */
  private ActionForward performExamConfig(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
    String currentUser = "";   //执行人ID
    currentUser = saveSessionBeanForm.getUserid();
    if(request.getSession().getAttribute(ExamUtil.TYPESEL)!=null){
    	request.getSession().removeAttribute(ExamUtil.TYPESEL);}
    if(request.getSession().getAttribute("examWarehouseList")!=null){
    	request.getSession().removeAttribute("examWarehouseList");
    }
    if(request.getSession().getAttribute("totalScore")!=null){
    	request.getSession().removeAttribute("totalScore");
    }
    if (request.getSession().getAttribute("typesel") != null) {
        request.getSession().removeAttribute("typesel");
      }
      if (request.getSession().getAttribute("storeCount") != null) {
        request.getSession().removeAttribute("storeCount");
      }
      if (request.getSession().getAttribute("examTypeSelForm") != null) {
        request.getSession().removeAttribute("examTypeSelForm");
      }
    try{
      //权限判断
    	TawSystemUserRefRoleDaoHibernate tsurd=
    		(TawSystemUserRefRoleDaoHibernate)ApplicationContextHolder.getInstance().getBean("tawSystemUserRefRoleDao");
    	List roleList=tsurd.getRoleidByuserid(currentUser);
    	//专业下拉
        request.setAttribute("specialtySelList", ExamUtil.getSpecialtySel());
        //厂家
        request.setAttribute("manufacturerSelList", SystemResource.getManufacturerSel());
        
    	//如果不拥有制定考试的权限或者不是管理员
    	if(!roleList.contains("8a4282592b301114012b4bfe60372a3e")&&!currentUser.equals("admin")){
    		return mapping.findForward("nopriv");
    	}
        return mapping.findForward("success");
    }catch (Exception e) {
		generalError(request, e);
		return mapping.findForward("failure");
	}
  }
  
  
  /**
   *  省级以及地市级试卷汇总
   */
  private ActionForward performExamConfigList(ActionMapping mapping,
          ActionForm actionForm,
          HttpServletRequest request,
          HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
//	    String currentUser = "";   //执行人ID
//	    currentUser = saveSessionBeanForm.getUserid();
	    
	    String examType = request.getParameter("examType"); //试卷类型 （省级prov、网络部net、地市city）
	    request.setAttribute("examType", examType);
	    String userDeptType = ""; //用户所在的部门类型（省级prov、网络部net、地市city）
	    try{
	    	//分页相关
	        String offsetStr = request.getParameter("pager.offset") == null ? "0" 
		    		: request.getParameter("pager.offset");
		    int offset  = Integer.parseInt(offsetStr);
		    int pagesize = 10;
		    
		    //省级部门ID
		    String provDeptStr = ExamUtil.provDeptStr; 
		    //网络部部门ID
		    String netDeptStr = ExamUtil.netDeptStr;
		    //除了省级部门ID以及网络部部门ID，剩下的就是地市的
		    
		    //根据用户所在部门ID判断用户所在的部门类型
		    String userDeptId = saveSessionBeanForm.getDeptid(); //用户所在部门ID
		    if(ExamUtil.isContainStr(userDeptId, provDeptStr)){
		    	userDeptType = "prov";
		    }else if(ExamUtil.isContainStr(userDeptId, netDeptStr)){
		    	userDeptType = "net";
		    }else{
		    	userDeptType = "city";
		    }
	    	
		    if("prov".equals(examType)){ //如果是省级试卷汇总
		    	if("prov".equals(userDeptType)){
		    		request.setAttribute("pm", studyOnlineBO.findExamConfigList(provDeptStr, offset, pagesize));
		    	}
		    }else if("net".equals(examType)){ //如果是网络部试卷汇总
		    	if("prov".equals(userDeptType) || "net".equals(userDeptType)){
		    		request.setAttribute("pm", studyOnlineBO.findExamConfigList(netDeptStr, offset, pagesize));
		    	}
		    }else if("city".equals(examType)){ //如果是地市试卷汇总
		    	if("prov".equals(userDeptType) || "net".equals(userDeptType)){
		    		String deptStr = provDeptStr + "," + netDeptStr;
		    		request.setAttribute("pm", studyOnlineBO.findExamConfigListCity(false, deptStr, offset, pagesize));
		    	}else{
		    		request.setAttribute("pm", studyOnlineBO.findExamConfigListCity(true, userDeptId, offset, pagesize));
		    	}
		    }
	    	return mapping.findForward("success");
	    	
//	        //权限判断
//	    	TawSystemUserRefRoleDaoHibernate tsurd=
//	    		(TawSystemUserRefRoleDaoHibernate)ApplicationContextHolder.getInstance().getBean("tawSystemUserRefRoleDao");
//	        ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
//	        
//	        String offsetStr = request.getParameter("pager.offset") == null ? "0" 
//	        		: request.getParameter("pager.offset");
//	        int offset  = Integer.parseInt(offsetStr);
//	        int pagesize = 10;
//	    	
//	        boolean flag = false;
//	    	if("zhoutao".equals(currentUser) || "heyuwu".equals(currentUser) 
//	    			|| "dengxianhui".equals(currentUser) || "admin".equals(currentUser)){
//	       	 	flag = true;
//	        }
//	    	String hql="select dept.deptType from  TawSystemDeptExam dept where dept.deptId = " +
//	    			"(select deptid from TawSystemUser where userid='"+currentUser+"')";
//	     
//	    	List ll=tsurd.getHibernateTemplate().find(hql);
//	      
//	        TawSystemDeptExam d = null;
//	        
//	        //如果是管理员或者所在的deptType为1
//	        if(flag ||(ll.size()>0&&ll.get(0)!=null&&String.valueOf(ll.get(0)).trim().equals("1"))){
//	        	StringBuffer hql3 = new StringBuffer("from ExamConfig where issueUser ");
//	        	String subHql = "";
//	        	if("admin".equals(issueUser)){
//	        		subHql = " in ('admin','heyuwu','zhoutao') ";
//	        		request.setAttribute("issueUser", issueUser);
//	        	}else{
//	        		subHql = " not in ('admin','heyuwu','zhoutao') ";
//	        	}
//	        	hql3.append(subHql).append(" order by startTime desc");
//	        	request.setAttribute("pm", DAO.searchPaginated(hql3.toString(), null, offset, pagesize));
//	        	request.setAttribute( "adminType",1);
//	        	return mapping.findForward("success");
//	        }else
//	        	d =studyOnlineBO.getDeptinfobydeptid(saveSessionBeanForm.getDeptid(), "0");
//	        	StringBuffer hql4 = new StringBuffer("from ExamConfig where issueUser ");
//	        	String subHql4 = "";
//	        	if("admin".equals(issueUser)){
//	        		subHql4 = " in ('admin','heyuwu','zhoutao') ";
//	        		request.setAttribute("issueUser", issueUser);
//	        	}else{
//	        		subHql4 = " not in ('admin','heyuwu','zhoutao') ";
//	        	}
//	        	hql4.append(subHql4).append(" and companyId=" + d.getDeptType() + " order by startTime desc");
//	        	
//	        	request.setAttribute("pm", DAO.searchPaginated(hql4.toString(), null, offset, pagesize));
//	        	return mapping.findForward("success");
	    	}
	    	catch (Exception e) {
	    		generalError(request, e);
	    		return mapping.findForward("failure");
	    	}
  }
 
  /**
   * 设置试题的参数项(包括专业,厂家,难度,试题类型,试题分数,试题内容)
   */
  private ActionForward performConfigSubmit(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
	  
    String currentUser = "";   //执行人ID
    String typeSel=request.getParameter("typeSel");
//    List<ExamTypeSel> examTypeSelList =new ArrayList<ExamTypeSel>();
    List<ExamTypeSel> examTypeSelList = (List<ExamTypeSel>) request.getSession().getAttribute(ExamUtil.TYPESEL);
    String issueId=null;
	try {
		issueId = UUIDHexGenerator.getInstance().getID();
	} catch (Exception e1) {
		e1.printStackTrace();
	}
    for(int i=0;i<examTypeSelList.size();i++){
    	ExamTypeSel examTypeSel=(ExamTypeSel) examTypeSelList.get(i);
//    	examTypeSel.setIssueId(issueId);
    	examTypeSel.setTypesel_fk(issueId);
//    	typeSelBO.addTypeSel(examTypeSel);
    }
    currentUser = saveSessionBeanForm.getUserid();
    try{
    	request.getSession().setAttribute("issueId", issueId);
    }
    catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
   }
    return mapping.findForward("generate");
  }
  
  /*
   * 对生成的试题进行增删改操作
   */
  private ActionForward operateExamConfig(ActionMapping mapping,ActionForm actionForm,HttpServletRequest request,
  					HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
	  ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
	  String operate = request.getParameter("op"); //操作类型
	  request.setAttribute("examWarehouseList", request.getSession().getAttribute("examWarehouseList"));
	  List<ExamWarehouse> examWarehouseList=(List)request.getSession().getAttribute("examWarehouseList"); //生成的试题列表
	  List<ExamTypeSel> examTypeSelList= (List<ExamTypeSel>) request.getSession().getAttribute(ExamUtil.TYPESEL);
	  ExamTypeSel examTypeSel=null;
	  String contype = request.getParameter("contype");
	  int contypeInt = !StringUtils.isEmpty(contype)? Integer.parseInt(contype) : 0;
	  if (request.getSession().getAttribute("typesel") != null) {
	      request.getSession().removeAttribute("typesel");
	    }
	  if(!StringUtils.isEmpty(operate)){
		  if("remove".equals(operate)){ //如果是删除操作
			  String subId = request.getParameter("subId"); //删除的试题ID号
			  ExamWarehouse warehouse = null;
			  for(int i=0;i<examWarehouseList.size();i++){
		  		  warehouse = (ExamWarehouse) examWarehouseList.get(i);
		  		  if(warehouse.getSubId().equals(subId)){
		  			examWarehouseList.remove(i); //移除试题
		  			//删除试题后就不能再循环了，如果再次循环warehouse.getSubId()题中有重复的话它会删除下一条数据
		  			break;
		  		  }
		  	  }
			  for(ExamTypeSel typeSel:examTypeSelList){
				  if(typeSel.getId().equals(warehouse.getTypeSelId())){
					  examTypeSel = typeSel;
				  }
			  }
//					  更新session范围内的各种类型的题数
			  if("1".equals(contype)){ //单选
				  examTypeSel.setRadioCount(examTypeSel.getRadioCount()-1);
			  }else if("2".equals(contype)){ //多选
				  examTypeSel.setMultipleCount(examTypeSel.getMultipleCount()-1);
			  }else if("3".equals(contype)){ //判断
				  examTypeSel.setJudgeCount(examTypeSel.getJudgeCount()-1);
			  }else if("4".equals(contype)){ //简答
				  examTypeSel.setQaCount(examTypeSel.getQaCount()-1);
			  }else if("5".equals(contype)){ //论述
				  examTypeSel.setEssayCount(examTypeSel.getEssayCount()-1);
			  }
		  }else if("add".equals(operate)){ //如果是添加操作
			  int typesel=Integer.parseInt(request.getParameter("typesel"));
			  examTypeSel=examTypeSelList.get(typesel);
			  int perScore = 0; //每题分数
		  	  Integer radioScore = examTypeSel.getRadioScore();
		  	  Integer multipleScore =examTypeSel.getMultipleScore();
		  	  Integer judgeScore =examTypeSel.getJudgeScore();
		  	  Integer qaScore =examTypeSel.getQaScore();
		  	  Integer essayScore =examTypeSel.getEssayScore();
//		  	  
		  	  if(contypeInt == 1){
				  perScore = radioScore;
			  }else if(contypeInt == 2){
				  perScore = multipleScore;
			  }else if(contypeInt == 3){
				  perScore = judgeScore;
			  }else if(contypeInt == 4){
				  perScore = qaScore;
			  }else if(contypeInt == 5){
				  perScore = essayScore;
			  }
			  List examlsitTmp = (List) request.getSession().getAttribute("examWarehouseList");
			  String subIdStr = request.getParameter("checkSel");//选中添加的试题 格式为：'8a4282a62b9a1b16012b9a2685ac0020','8a4282a62b9a1b16012b9a2685ad0021'
			  subIdStr = subIdStr.replaceAll("'", "");
			  int addNum = 0;
			  String[] subIdArr = subIdStr.split(",");
			  boolean flag = true; //true未重复 false重复 （如果重复就不添加）
			  //查询添加的试题是否重复
			  for(int j=0;j<subIdArr.length;j++){
				  String subId = subIdArr[j];
				  for(int i=0;i<examlsitTmp.size();i++){
					  ExamWarehouse warehouse = (ExamWarehouse) examlsitTmp.get(i);
					  if(warehouse.getSubId().equals(subId)){
						  flag = false;
						  break;
					  }
				  }
				  if(flag){//如果试题没有重复
					  ExamWarehouse house = DAO.loadSingleOfExamWarehouse(subId);
					  //新增的试题分数必须和最开始在页面上设置的分数一致
					  house.setValue(perScore);
					  house.setTypeSelId(examTypeSel.getId());
					  examWarehouseList.add(house);
					  addNum++;
				  }
				  flag = true;
			  }
			  
			  if("1".equals(contype)){ //单选
				  examTypeSel.setRadioCount(examTypeSel.getRadioCount()+addNum);
			  }else if("2".equals(contype)){ //多选
				  examTypeSel.setMultipleCount(examTypeSel.getMultipleCount()+addNum);
			  }else if("3".equals(contype)){ //判断
				  examTypeSel.setJudgeCount(examTypeSel.getJudgeCount()+addNum);
			  }else if("4".equals(contype)){ //简答
				  examTypeSel.setQaCount(examTypeSel.getQaCount()+addNum);
			  }else if("5".equals(contype)){ //论述
				  examTypeSel.setEssayCount(examTypeSel.getEssayCount()+addNum);
			  }
		  }else if("refresh".equals(operate)){  //如果是添加操作后页面自动刷新
//			  request.getSession().setAttribute("examWarehouseList", request.getSession().getAttribute("examWarehouseList"));
//			  request.getSession().setAttribute("examTypeSelList", request.getSession().getAttribute(ExamUtil.TYPESEL));
		  } 
	  }
//	  request.getSession().setAttribute("examWarehouseList", examWarehouseList);
//	  int totalScore=(Integer) request.getSession().getAttribute("totalScore");
	  int totalScore=0;
	  for(ExamWarehouse examWarehouse:examWarehouseList){
		  totalScore+=examWarehouse.getValue();
		}
		request.getSession().setAttribute("totalScore", totalScore);
	  return mapping.findForward("success");				
  }
  
  /**
   *  ajax更改session中试题列表的试题的分数
   */
  private ActionForward changeExamScore(ActionMapping mapping,ActionForm actionForm,
	        HttpServletRequest request,
	        HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){  
	    List examlist1=(List)request.getSession().getAttribute("examlist1"); //生成的试题列表
	    Integer totalScore = (Integer)request.getSession().getAttribute("totalScore"); //总分
	    String id = request.getParameter("id"); 
	    String newValueStr = request.getParameter("newValue"); //修改后的分数
	    String newTotalScore = request.getParameter("newTotalScore");  //修改后的总分
	    if(!StringUtils.isEmpty(id) && !StringUtils.isEmpty(newValueStr) && !StringUtils.isEmpty(newTotalScore)){
	    	 int newValue = Integer.parseInt(newValueStr);
	    	 //更新session里面试题集得试题分数
	    	 for(int i=0;i<examlist1.size();i++){
				  ExamWarehouse w = (ExamWarehouse) examlist1.get(i);
				  if(w.getSubId().equals(id)){
					  w.setValue(newValue);
				  }
	    	 }
	    	 totalScore = Integer.valueOf(Integer.parseInt(newTotalScore));
	    	 request.getSession().setAttribute("examlist1", examlist1);
//	    	 request.getSession().setAttribute("totalScore", totalScore);
	    }
		return null;
  }
  
  /**
   * 试题添加页面
   */
  private ActionForward operateExamConfigAdd(ActionMapping mapping,
          ActionForm actionForm,
          HttpServletRequest request,
          HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
	    String typeselIndex;
	  	//String contype=request.getParameter("contype");
	  	String typesel=request.getParameter("typesel");
	  	if (typesel != null) {
	        request.getSession().setAttribute("typesel", typesel);
	      }
	  	 if (typesel != null)
	  	      typeselIndex = typesel;
	  	    else {
	  	      typeselIndex = (String)request.getSession().getAttribute("typesel");
	  	    }
	  	request.setAttribute("typesel", typesel);
		String pageIndexStr = request.getParameter("pager.offset")==null?"0" : request.getParameter("pager.offset");
        int pageIndex  = Integer.parseInt(pageIndexStr);
        List<ExamTypeSel> examTypeSelList= (List<ExamTypeSel>)request.getSession().getAttribute(ExamUtil.TYPESEL);
        String spec=null;
        String manu = null;
        String diff =null;
        String contype = request.getParameter("contype");
        request.setAttribute("contype", contype);
        if(!examTypeSelList.isEmpty()){
//        	for(int i=0;i<examTypeSelList.size();i++){
        	spec = ((ExamTypeSel)examTypeSelList.get(StaticMethod.null2int(typeselIndex))).getSpecialty();
            manu = ((ExamTypeSel)examTypeSelList.get(StaticMethod.null2int(typeselIndex))).getManufacturer().toString();
            diff = ((ExamTypeSel)examTypeSelList.get(StaticMethod.null2int(typeselIndex))).getDifficulty().toString();
//        	}
        }
        int pageSize = 30;
        PageModel pm = new PageModel();
        if (pageIndex >= 0) {
            pm = this.studyOnlineBO.findExamConfigAdd(spec, manu, diff, contype, pageIndex, pageSize);
            ((ExamTypeSel)examTypeSelList.get(StaticMethod.null2int(typesel))).getRadioCount();

            request.setAttribute("ExamConfigList", pm.getDatas());
            request.setAttribute("resultSize", Integer.valueOf(StaticMethod.nullObject2int(Integer.valueOf(pm.getTotal()))));
          }
          else {
            request.setAttribute("ExamConfigList", pm.getDatas());
            request.setAttribute("resultSize", Integer.valueOf(StaticMethod.nullObject2int(Integer.valueOf(pm.getTotal()))));
          }
          request.setAttribute("pageSize", Integer.valueOf(pageSize));
          return mapping.findForward("success");
  }

  /**
   * 开始考试（包括动作：开始考试、翻页、交卷）
   */
  private ActionForward performExamine(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
    String currentUser = "";   //执行人ID
    currentUser = saveSessionBeanForm.getUserid();
    try{
      //提取发布信息
      ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
      String issueId=request.getParameter("issueid");
      ExamConfig config =  DAO.getIssueInfoByIssueId(issueId);//.getIssueInfo( currentTime );
      if (config == null )
        return mapping.findForward("examinenull");

      //判断是否已交卷
      if ( studyOnlineBO.ifFinish( issueId , currentUser ) ){
    	  request.setAttribute("msg", "对不起,你已经交卷,不能再进行考试!");
          return mapping.findForward("failure1");
      }

      //保存提交答案(非问答题)
      String optionsTemp = StaticMethod.null2String(request.getParameter("options"));
      if ( !"".equalsIgnoreCase(optionsTemp) )
        studyOnlineBO.submitExam(studyOnlineBO.getOptions(optionsTemp) , issueId , currentUser );
      
      //保存提交答案(问答题)
      Map map = request.getParameterMap();
      for(Iterator itor = map.entrySet().iterator();itor.hasNext();){
			Map.Entry entry = (Map.Entry)itor.next();
			String name = entry.getKey().toString();
			String answer =  (((String[])entry.getValue())[0]).trim();
			if(name.startsWith("qa_")){
				String id = name.replace("qa_", "");
				studyOnlineBO.saveQaExam(currentUser, issueId, id, answer);
			}
	  }

      //取题（翻页）
      int length = PAGE_LENGTH;
      int offset = StaticMethod.nullObject2int(request.getParameter("pageroffset"), 0);
      int size = StaticMethod.nullObject2int(request.getParameter("pagersize") , 0);
      request.setAttribute("pageroffset", offset);
      int[] pagePra = {offset, length, size};
      List sublist =studyOnlineBO.getExamsubs( currentUser , issueId , pagePra );
      if ( sublist.size() == 0 )
       return mapping.findForward("nopriv");

     //获取已提交的答案
      Map m = studyOnlineBO.getAnswerOfExam( sublist , issueId , currentUser);
     //设置已提交的答案（问答题以及非问答题）
     for(Iterator itor = m.entrySet().iterator();itor.hasNext();){
    	 Map.Entry entry = (Map.Entry)itor.next();
    	 request.setAttribute(entry.getKey().toString(), entry.getValue().toString().trim());
     }

     request.setAttribute("issueid", issueId);
     request.setAttribute( "examConfig" , config );
     request.setAttribute( "ExamWarehouse" , sublist );

     String url =
         mapping.getPath().substring( mapping.getPath().lastIndexOf("/") ) + ".do";
     String pagerHeader = studyOnlineBO.getPager( pagePra[0] , pagePra[1] , pagePra[2] , url );
     request.setAttribute("pagerHeader", pagerHeader);

     //交卷操作
     if ( "true".equalsIgnoreCase(StaticMethod.null2String(request.getParameter("isFinish"))) ){
       //如果无需阅卷，直接将考试成绩生成并插入到examresult表
       if(config.getMarkFlag() == 0 || config.getMarkFlag() == null){
    	   ExamResultService resultService = (ExamResultService)ApplicationContextHolder.getInstance().getBean("examResultService");
    	   resultService.createExamResult(issueId,currentUser);
       } else{
    	   //需要在阅卷后再将考试成绩生成并插入到examresult表
       }
     	return mapping.findForward("finish");
     }
    }
    catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
    }

    return mapping.findForward("success");
  }

  private ActionForward performStudyQuery(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
    try {
      //仅显示本地区的部门
      request.setAttribute("REGIONID", new Integer( 1 ));
    }
    catch (Exception e) {
      generalError(request, e);
      return mapping.findForward("failure");
    }

    return mapping.findForward("success");
  }

  private ActionForward performQueryDo(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
    try{
      List QOlist =studyOnlineBO.getStudyQuerylist(request);
      if ( QOlist.size() == 0 ){
    	  request.setAttribute("msg", "对不起,你选择的人员没有参加练习!");
    	  return mapping.findForward("failure1");
      }
      request.setAttribute( "QOlist" , QOlist );
    }
    catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
    }

    return mapping.findForward("success");
  }
  
  private ActionForward performQueryDoSelf(ActionMapping mapping,
          ActionForm actionForm,
          HttpServletRequest request,
          HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
		try{
			String userId = saveSessionBeanForm.getUserid();
			List QOlist =studyOnlineBO.getStudyQuerySelfList(userId);
			if ( QOlist.size() == 0 ){
				request.setAttribute("msg", "对不起,你没有参加练习!");
				return mapping.findForward("failure1");
			}
			request.setAttribute( "QOlist" , QOlist );
		}catch (Exception e) {
			generalError(request, e);
			return mapping.findForward("failure");
		}
		return mapping.findForward("success");
	}
  

  /**
   *  显示做题明细
   */
  private ActionForward performDetail(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
    try{
      List contentlist=studyOnlineBO.getDetailOfStudy(request);
      if ( contentlist.size() == 0 )
        return mapping.findForward("failure1");
      request.setAttribute( "ContentList" , contentlist );
    }
    catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
    }
    return mapping.findForward("success");
  }

  /**
   * 成绩查询
   */
  private ActionForward performExamQuery(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
    return mapping.findForward("success");
  }
  
  /**
   * 成绩查询子页面
   */
  private ActionForward performExamQuerySub(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
      try{
    	String currentTime = StaticMethod.getCurrentDateTime();
    	//分页相关代码
    	String pageIndexStr = request.getParameter("pager.offset")==null
    					?"0" : request.getParameter("pager.offset");
        int offset  = Integer.parseInt(pageIndexStr);
    	int pagesize = 10;
    	
    	String examType = request.getParameter("examType"); //试卷类型 （省级prov、网络部net、地市city）
    	request.setAttribute("examType", examType);
  	    String userDeptType = ""; //用户所在的部门类型（省级prov、网络部net、地市city）
    	
    	 //省级部门ID
	    String provDeptStr = ExamUtil.provDeptStr; 
	    //网络部部门ID
	    String netDeptStr = ExamUtil.netDeptStr;
	    //除了省级部门ID以及网络部部门ID，剩下的就是地市的
	    
	    //根据用户所在部门ID判断用户所在的部门类型
	    String userDeptId = saveSessionBeanForm.getDeptid(); //用户所在部门ID
	    if(ExamUtil.isContainStr(userDeptId, provDeptStr)){
	    	userDeptType = "prov";
	    }else if(ExamUtil.isContainStr(userDeptId, netDeptStr)){
	    	userDeptType = "net";
	    }else{
	    	userDeptType = "city";
	    }
    	
	    if("prov".equals(examType)){ //如果是省级试卷汇总
	    	if("prov".equals(userDeptType)){
	    		request.setAttribute("pm", this.studyOnlineBO.getAllConfigList(currentTime, provDeptStr, pagesize, offset, saveSessionBeanForm.getUserid(), ExamUtil.isContainStr(userDeptId, provDeptStr)));
	    	}
	    }else if("net".equals(examType)){ //如果是网络部试卷汇总
	    	if("prov".equals(userDeptType) || "net".equals(userDeptType)){
	    		request.setAttribute("pm", this.studyOnlineBO.getAllConfigList(currentTime, netDeptStr, pagesize, offset, saveSessionBeanForm.getUserid(), ExamUtil.isContainStr(userDeptId, provDeptStr)));
	    	}
	    }else if("city".equals(examType)){ //如果是地市试卷汇总
	    	if("prov".equals(userDeptType) || "net".equals(userDeptType)){
	    		String deptStr = provDeptStr + "," + netDeptStr;
	    		request.setAttribute("pm", this.studyOnlineBO.getAllConfigListCity(currentTime, false, deptStr, pagesize, offset, saveSessionBeanForm.getUserid()));
	    	}else{
	    		request.setAttribute("pm", this.studyOnlineBO.getAllConfigListCity(currentTime, true, userDeptId, pagesize, offset, saveSessionBeanForm.getUserid()));
	    	}
	    }
    	return mapping.findForward("success");
    	
    	
//      String currentTime = StaticMethod.getCurrentDateTime();
//      PageModel pm = studyOnlineBO.getAllConfigList(currentTime, pageSize, pageIndex);
//      request.setAttribute("ExamConfigList" , pm.getDatas());
//      request.setAttribute("resultSize",pm.getTotal());
//	  request.setAttribute("pageSize", pageSize);
    }
    catch (Exception e) {
      generalError(request, e);
      return mapping.findForward("failure");
    }
  }
  


  /**
   * 查询某单位下所有人的具体成绩
   */
  private ActionForward performExamQueryDO(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
    try {
    	String issueId=request.getParameter("issueId");
      List QOlist = studyOnlineBO.getExamQueryList(request);
      request.setAttribute( "QOlist" , QOlist);
      request.setAttribute("issueId", issueId);      
    }catch (Exception e) {
      generalError(request, e);
      return mapping.findForward("failure");
    }
    return mapping.findForward("success");
  }
  
  /**
   *  查询某次考试各单位总体考试情况
   */
  private ActionForward performExamDeptQuery(ActionMapping mapping,
          ActionForm actionForm,
          HttpServletRequest request,
          HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
	try {
		String issueId = StaticMethod.nullObject2String(request.getParameter("issueId"));
		ExamDAO examDao = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
		List<ExamDeptQueryVO> list = examDao.findExamDeptQuery(issueId);
		request.setAttribute("issueId", issueId);
		request.setAttribute("list", list);
	}
	catch (Exception e) {
		generalError(request, e);
		return mapping.findForward("failure");
	}

	return mapping.findForward("success");
}
  
  /**
   * 查询某次考试各单位所有参考人员的总体考试情况
   */
  private ActionForward performExamTesterQuery(ActionMapping mapping,
          ActionForm actionForm,
          HttpServletRequest request,
          HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
	try {
		String issueId = StaticMethod.nullObject2String(request.getParameter("issueId"));
		ExamDAO examDao = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
		List<ExamTesterQueryVO> list = examDao.findExamTesterQuery(issueId);
		request.setAttribute("list", list);
		request.setAttribute("issueId", issueId);
	}
	catch (Exception e) {
		generalError(request, e);
		return mapping.findForward("failure");
	}
	return mapping.findForward("success");
  }

  /**
   * 交卷操作
   */
  private ActionForward performExamFinish(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
    String currentUser = "";   //执行人ID
    currentUser = saveSessionBeanForm.getUserid();
    String issueId =  request.getAttribute("issueid").toString();
    try{
      //提取发布信息
      ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
      ExamConfig config = DAO.getIssueInfoByIssueId(issueId);
      if ( config == null){
    	  return mapping.findForward("examinenull");
      }
      studyOnlineBO.examFinish( issueId , currentUser );
    }
    catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
    }
    return mapping.findForward("success");
  }

  /**
   *  试题历史导入记录
   */
  private ActionForward performReport(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){

	String currentUserName = saveSessionBeanForm.getUsername();
	
	String pageIndexStr = request.getParameter("pager.offset")==null ? "0" : request.getParameter("pager.offset");
    int pageIndex  = Integer.parseInt(pageIndexStr);
	//定义每页显示数
	int pageSize = 15;
    try{
    	PageModel pm = studyOnlineBO.findHistoryImport(currentUserName, pageIndex, pageSize);
        request.setAttribute( "INFOLIST", pm.getDatas() );
		request.setAttribute("resultSize",pm.getTotal());
		request.setAttribute("pageSize", pageSize);
    }catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
    }
    return mapping.findForward("success");
  }

  private ActionForward performReportDO(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
    try{
      String importId = StaticMethod.null2String(request.getParameter("checkSel"));
      String specialty = (StaticMethod.null2String(request.getParameter("specialty")));
      studyOnlineBO.reportSubmit( importId  , specialty );
    }
    catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
    }
    return mapping.findForward("success");
  }

  private ActionForward performDelImport(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
    try{
      String importId = request.getParameter("checkSel");
      studyOnlineBO.delImport( importId );
    }
    catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
    }

    return mapping.findForward("success");
  }

  private ActionForward performReportDetail(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm) {
   try{
    String importId = StaticMethod.null2String(request.getParameter("importId"));
    List sublist = studyOnlineBO.getAllOnlineWarehouse(importId);
    request.setAttribute("SUBLIST",sublist);
   }
    catch (Exception e) {
      generalError(request, e);
      return mapping.findForward("failure");
    }
    return mapping.findForward("success");
  }

  private ActionForward performPhysicalDelSub(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm) {
   try{
    String checkSel = StaticMethod.null2String(request.getParameter("checkSel"));
    ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
    DAO.delExamWarehouseOfSingle(checkSel);
   }
    catch (Exception e) {
      generalError(request, e);
      return mapping.findForward("failure");
    }
    return mapping.findForward("success");
  }

  private ActionForward performAuditing(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
    try{
      String condition = "and onlineInfo.auditing in (1)";
      List infolist = studyOnlineBO.getInfolistOfcondition( condition );
      request.setAttribute("INFOLIST" , infolist);
    }catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
    }
    return mapping.findForward("success");
  }

  private ActionForward performAuditingDO(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
    try{
      String importId = StaticMethod.null2String(request.getParameter("checkSel"));
      studyOnlineBO.auditingDO(importId);
    }catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
    }
    return mapping.findForward("success");
  }

  private ActionForward performReject(ActionMapping mapping,
                                  ActionForm actionForm,
                                  HttpServletRequest request,
                                  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
    try{
      String importId = StaticMethod.null2String(request.getParameter("checkSel"));
      String comment = (StaticMethod.null2String(request.getParameter("comment")));
      studyOnlineBO.reject(importId  , comment);
    }catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
    }
    return mapping.findForward("success");
  }

  private ActionForward performAuditingDetail(ActionMapping mapping,
                                    ActionForm actionForm,
                                    HttpServletRequest request,
                                    HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm) {
     try{
    	 String importId = StaticMethod.null2String(request.getParameter("importId"));
    	 List sublist = studyOnlineBO.getAllOnlineWarehouse(importId);
    	 request.setAttribute("SUBLIST",sublist);
     }catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
      }
      return mapping.findForward("success");
    }

    private ActionForward performIssueConfig(ActionMapping mapping,
                                    ActionForm actionForm,
                                    HttpServletRequest request,
                                    HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
      try {
        String currentTime = StaticMethod.getCurrentDateTime();
        List configlist = studyOnlineBO.getConfigLab(currentTime, studyOnlineBO.staticQueryTypeEndtime);
        request.setAttribute( "ConfigList" , configlist);
      }catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
      }
      return mapping.findForward("success");
    }

    private ActionForward performIssueDO(ActionMapping mapping,
                                    ActionForm actionForm,
                                    HttpServletRequest request,
                                    HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
      try {
        studyOnlineBO.updateIssueTime(request);
      }
      catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
      }
      return mapping.findForward("success");
    }

    private ActionForward performIssueInfo(ActionMapping mapping,
                                    ActionForm actionForm,
                                    HttpServletRequest request,
                                    HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
      try {
        String currentTime = StaticMethod.getCurrentDateTime();
        List configlist = studyOnlineBO.getConfigLab(currentTime
                           , studyOnlineBO.staticQueryTypeBetweenOfIssue);
        request.setAttribute( "ConfigList" , configlist);
      }
      catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
      }
      return mapping.findForward("success");
    }

    private ActionForward performExamDelete(ActionMapping mapping,
                                    ActionForm actionForm,
                                    HttpServletRequest request,
                                    HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
      try {
        String currentTime = StaticMethod.getCurrentDateTime();
        List configlist = studyOnlineBO.getConfigLab(currentTime
                           , studyOnlineBO.staticQueryTypeExamUnfinished);
        request.setAttribute( "ConfigList" , configlist);
      }
      catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
      }
      return mapping.findForward("success");
    }

    private ActionForward performExamDeleteDO(ActionMapping mapping,
                                    ActionForm actionForm,
                                    HttpServletRequest request,
                                    HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
      try {
        String configSel = StaticMethod.null2String(request.getParameter("configSel"));
        ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
        DAO.delExamAllInfo(configSel);
      }
      catch (Exception e) {
        generalError(request, e);
        return mapping.findForward("failure");
      }
      return mapping.findForward("success");
    }
//----------------------------------------------------------------------------------------------------
private void performExamAjaxgetdept(ActionMapping mapping,
            ActionForm actionForm,
            HttpServletRequest request,
            HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
		StringBuffer sb=new StringBuffer();
		try {
			String deptid = StaticMethod.null2String(request.getParameter("deptid"));
			TawSystemDeptDaoHibernate tsddh = (TawSystemDeptDaoHibernate)ApplicationContextHolder.getInstance().getBean("tawSystemDeptDao");
			List l=tsddh.getNextLevecDepts(deptid, "0");
			
			for (int i = 0; i < l.size(); i++) {
			TawSystemDept t=(TawSystemDept)l.get(i);
			sb.append("<option value='").append(t.getDeptId()).append("'>").append(t.getDeptName()).append("</option>");
			}
			response.setCharacterEncoding("UTF-8");
			PrintWriter write=response.getWriter();
			write.write(sb.toString());
			write.close();
		}
		catch (Exception e) {
			generalError(request, e);
		}
    }
private void performExamAjaxgetUser(ActionMapping mapping,
        ActionForm actionForm,
        HttpServletRequest request,
        HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
StringBuffer sb=new StringBuffer();
try {
String deptid = StaticMethod.null2String(request.getParameter("deptid"));
TawSystemUserDaoHibernate tsudh=(TawSystemUserDaoHibernate)ApplicationContextHolder.getInstance().getBean("tawSystemUserDao");
 List l=tsudh.getUserBydeptids(deptid);
//TawSystemDeptDaoHibernate tsddh = (TawSystemDeptDaoHibernate)ApplicationContextHolder.getInstance().getBean("tawSystemDeptDao");
//List l=tsddh.getNextLevecDepts(deptid, "0");

for (int i = 0; i < l.size(); i++) {
	TawSystemUser t=(TawSystemUser)l.get(i);
sb.append("<option value='").append(t.getUserid()).append("'>").append(t.getUsername()).append("</option>");
}
response.setCharacterEncoding("UTF-8");
PrintWriter write=response.getWriter();
write.write(sb.toString());
write.close();

}
catch (Exception e) {
generalError(request, e);

}
}
private void performDeleteExamcif(ActionMapping mapping,
        ActionForm actionForm,
        HttpServletRequest request,
        HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm)
{
	String isid=request.getParameter("issueid");
	 ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
	 ExamConfig ocf=(ExamConfig)DAO.load(ExamConfig.class,isid);
	DAO.delete(ocf);
	studyOnlineBO.printExamcfg(response,ocf,"");
}

/**
 * 指派参考人
 */
private ActionForward designate(ActionMapping mapping,
        ActionForm actionForm,
        HttpServletRequest request,
        HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
	String companyid=request.getParameter("companyid");
	TawSystemDeptDaoHibernate tsddh=(TawSystemDeptDaoHibernate)ApplicationContextHolder.getInstance().getBean("tawSystemDeptDao");
	List l=tsddh.getHibernateTemplate().find("from TawSystemDeptExam  where deptType="+companyid);
	request.setAttribute("depts", l);
	return mapping.findForward("success");
}

/**
 * 将指定的参考人与已制定的考试题目相关联
 * （ajax调用）
 */
private void uniontesters(ActionMapping mapping,ActionForm actionForm,
        HttpServletRequest request,
        HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
	String issueId = request.getParameter("issueId");
	String  testers=StaticMethod.nullObject2String(request.getParameter("testers"));
	int r=studyOnlineBO.issueRalateUser(issueId,testers);
	StringBuffer sb=new StringBuffer();
	sb.append(r);
	response.setCharacterEncoding("UTF-8");
	PrintWriter write;
	try {
		write = response.getWriter();
		write.write(sb.toString());
		write.close();
	} catch (IOException e){
		e.printStackTrace();
	}
}
private void gettestersbyIssueid(ActionMapping mapping,ActionForm actionForm,
        HttpServletRequest request,
        HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
	String isid=request.getParameter("issueid");
	ID2NameService service = (ID2NameService)ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
	 ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
	 ExamConfig ocf=(ExamConfig)DAO.load(ExamConfig.class,isid);
	 String tester[]=ocf.getTester().split(";");
	 StringBuffer sb=new StringBuffer();
	 for (int i = 0; i < tester.length; i++) {
		 sb.append(";").append(service.id2Name(tester[i], "tawSystemUserDao"));
	}
	sb.deleteCharAt(0);
	response.setCharacterEncoding("UTF-8");
	PrintWriter write;
	try {
		write = response.getWriter();
		write.write(sb.toString());
		write.close();
	} catch (IOException e){
		e.printStackTrace();
	}
}

	/**
	 * 获取实时联考主列表
	 */
	private ActionForward getMyExamList(ActionMapping mapping,ActionForm actionForm,
	        HttpServletRequest request,
	        HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){   
		//权限判断
		TawSystemUserRefRoleDaoHibernate tsurd = (TawSystemUserRefRoleDaoHibernate)ApplicationContextHolder.getInstance().getBean("tawSystemUserRefRoleDao");
		String currentUser = saveSessionBeanForm.getUserid();
    	List roleList=tsurd.getRoleidByuserid(currentUser);
    	
    	String pageIndexStr = request.getParameter("pager.offset")==null ? "0" : request.getParameter("pager.offset");
        int pageIndex  = Integer.parseInt(pageIndexStr);
		//定义每页显示数
		int pageSize = 10;
		
    	//如果是普通应试者
    	if(!roleList.contains("8a4282592b301114012b4bfe60372a3e")&&!currentUser.equals("admin")){
    		 ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO"); 
    		 String hqlL = "from ExamConfig ecf where ecf.tester like '%;" + 
    	        currentUser + "' or  ecf.tester like '" + 
    	        currentUser + ";%' or ecf.tester like '%;" + 
    	        currentUser + ";%' or  ecf.tester like '" + currentUser + "'  order by ecf.startTime desc";
    		 
    		 PageModel pm = DAO.searchPaginated(hqlL, null, pageIndex, pageSize);
    		 List l = pm.getDatas();
    		 
    		 //从来都未参与考试
    		 if(l ==null || l.size()==0){
    			 request.setAttribute("msg", "对不起，没有需要您参与的考试!");
    			 return mapping.findForward("failure1");
    		 }
    		request.setAttribute("myexamlist", l);
    		request.setAttribute("resultSize",pm.getTotal());
    		request.setAttribute("pageSize", pageSize);
    		
    		return  mapping.findForward("success");
    	}
    	return  mapping.findForward("adminsuccess");
	}
	
	/**
	 * 获取实时联考管理员主列表子页面
	 */
	private ActionForward getAdminExamList(ActionMapping mapping,ActionForm actionForm,
	        HttpServletRequest request,
	        HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){ 
		
	    String examType = request.getParameter("examType"); //试卷类型 （省级prov、网络部net、地市city）
	    request.setAttribute("examType", examType);
	    String userDeptType = ""; //用户所在的部门类型（省级prov、网络部net、地市city）
	    PageModel pm = null;
	    List<ExamConfig> l = null;
	    try{
	    	//分页相关
	        String offsetStr = request.getParameter("pager.offset") == null ? "0" 
		    		: request.getParameter("pager.offset");
		    int offset  = Integer.parseInt(offsetStr);
		    int pagesize = 10;
		    
		    //省级部门ID
		    String provDeptStr = ExamUtil.provDeptStr; 
		    //网络部部门ID
		    String netDeptStr = ExamUtil.netDeptStr;
		    //除了省级部门ID以及网络部部门ID，剩下的就是地市的
		    
		    //根据用户所在部门ID判断用户所在的部门类型
		    String userDeptId = saveSessionBeanForm.getDeptid(); //用户所在部门ID
		    if(ExamUtil.isContainStr(userDeptId, provDeptStr)){
		    	userDeptType = "prov";
		    }else if(ExamUtil.isContainStr(userDeptId, netDeptStr)){
		    	userDeptType = "net";
		    }else{
		    	userDeptType = "city";
		    }
	    	
		    if("prov".equals(examType)){ //如果是省级试卷汇总
		    	if("prov".equals(userDeptType)){
		    		pm = studyOnlineBO.findExamConfigList(provDeptStr, offset, pagesize);
		    	}
		    }else if("net".equals(examType)){ //如果是网络部试卷汇总
		    	if("prov".equals(userDeptType) || "net".equals(userDeptType)){
		    		pm = studyOnlineBO.findExamConfigList(netDeptStr, offset, pagesize);
		    	}
		    }else if("city".equals(examType)){ //如果是地市试卷汇总
		    	if("prov".equals(userDeptType) || "net".equals(userDeptType)){
		    		String deptStr = provDeptStr + "," + netDeptStr;
		    		pm = studyOnlineBO.findExamConfigListCity(false, deptStr, offset, pagesize);
		    	}else{
		    		pm = studyOnlineBO.findExamConfigListCity(true, userDeptId, offset, pagesize);
		    	}
		    }
		    l = pm.getDatas();
    		prosessExamConfigList(l,request);
    		request.setAttribute("resultSize",pm.getTotal());
    		request.setAttribute("pageSize", pagesize);
		    
	    	return mapping.findForward("success");
	    }catch (Exception e) {
			generalError(request, e);
			return mapping.findForward("failure");
		}
	}
	
	/**
	 * 获取实时联考管理员统计列表
	 */
	private ActionForward getAdminSelectExamList(ActionMapping mapping,ActionForm actionForm,
	        HttpServletRequest request,
	        HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){ 
		
		
		//专业下拉/////////////////////////////////////////////
        request.setAttribute("specialtySelList", ExamUtil.getSpecialtySel());
        
		if("true".equals(request.getParameter("firstTime"))){
			return  mapping.findForward("success");
		}
		
		String pageIndexStr = request.getParameter("pager.offset")==null ? 
    			"0" : request.getParameter("pager.offset");
        int pageIndex  = Integer.parseInt(pageIndexStr);
		//定义每页显示数
		int pageSize = 10;
		Map conditonMap = new HashMap();
		AdminSelectExamForm examForm = (AdminSelectExamForm)actionForm;
		String starttime = examForm.getStarttime();
		String endtime = examForm.getEndtime();
		String provtype = examForm.getProvtype();
		String company = examForm.getCompany();
		String specialty = examForm.getSpecialty();
		conditonMap.put("starttime", starttime);
		conditonMap.put("endtime", endtime);
		conditonMap.put("provtype", provtype);
		conditonMap.put("company", company);
		conditonMap.put("specialty", specialty);
		conditonMap.put("pageIndex", pageIndex);
		conditonMap.put("pageSize", pageSize);
		
		request.setAttribute("starttime", starttime);
		request.setAttribute("endtime", endtime);
		request.setAttribute("provtype", provtype);
		request.setAttribute("company", company);
		request.setAttribute("specialty", specialty);

		ExamDAO dao = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO"); 
		PageModel pm = dao.getAdminSelectExamList(conditonMap);
		
		request.setAttribute("examlist", pm.getDatas());
		request.setAttribute("resultSize",pm.getTotal());
		request.setAttribute("pageSize", pageSize);
		return  mapping.findForward("success");
	}
	
	
	/**
	 * 试题导入
	 */
	private ActionForward wordupload(ActionMapping mapping,ActionForm actionForm,
	        HttpServletRequest request,
	        HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm) throws Exception{
		String diff = request.getParameter("difficultyValue");
	    String a = request.getParameter("a");
		String path1 = request.getSession().getServletContext().getRealPath("studyonline/upload/");
	    DiskFileItemFactory factory = new DiskFileItemFactory();
	    ServletFileUpload upload = new ServletFileUpload(factory);
		List items = upload.parseRequest(request);
		Iterator iter = items.iterator();
		Map<String, String> map = new HashMap<String, String>();
		String fileName = "";
		while (iter.hasNext()) {
		    FileItem item = (FileItem) iter.next();
		    String name = item.getFieldName();
		   
		    if (item.isFormField()) { //如果是form表单数据(非文件)
		    	map.put(item.getFieldName(), item.getString());
		    	if("youhua".equals(name)){
		    		map.put("specialtySel", item.getString());
		    	}
		    	if (("difficulty3".equals(name)) && (map.get("difficulty3") != "")) {
		            map.put("difficultyValue", (String)map.get("difficulty3"));
		          }
		          else if (("difficulty".equals(name)) && (map.get("difficulty") != ""))
		            map.put("difficultyValue", (String)map.get("difficulty"));
		        }else { //如果是文件
		    	java.io.File dic = new java.io.File(path1);
				boolean m=dic.exists()&& dic.isDirectory();
				if(!m){
					m = dic.mkdirs();
				}
				fileName = item.getName().substring(item.getName().lastIndexOf("\\")+1);	
				fileName = path1+File.separator+fileName;
				item.write(new java.io.File(fileName));
				System.out.println("File field " + name + " with file name "
								+ item.getName() + " detected."+item);
		    }
		}
		String tip2 = null;
		String errMsg = "";
		try {
	       tip2 = studyOnlineBO.importwordWarehouse(fileName,
	    		   map.get("specialtySel"),
	    		   //Integer.valueOf(map.get("specialtySel")).intValue(),
	    		   Integer.valueOf(map.get("manufacturerSel")).intValue(),
	               Integer.valueOf(map.get("contype")).intValue(),
	               Integer.valueOf(map.get("issueType")).intValue(),
	               Integer.valueOf(map.get("s_value")).intValue(),
	               Integer.valueOf(StaticMethod.null2int((String)map.get("difficultyValue"))).intValue(), 
	               saveSessionBeanForm.getUsername() );
	   } catch (Exception ex) {
	       ex.printStackTrace();
	       errMsg = ex.getMessage();
	       tip2 = "error";
	   }
	   if(!tip2.equalsIgnoreCase("error")){
		   if( tip2.equalsIgnoreCase("all") ){
			   request.setAttribute("msg", "数据已导入成功");
		   }
		   else{
			   request.setAttribute("msg", "数据已导入."+tip2+".请查看数据缺失情况");
			   System.out.println("################数据已导入."+tip2+"成功.请查看数据缺失情况#############");
			   return mapping.findForward("success");
		   }
		   return mapping.findForward("success");
	   }
	   else{
		   request.setAttribute("msg", 
	"导入数据发生错误，可能是以下原因:<br>1、目前系统只支持Microsoft Word97-2003<br>2、导入的Microsoft Word文件数据格式不正确<br>3、系统问题<br>返回的错误信息是："+errMsg);
		   return mapping.findForward("failure1");
	   }
	}
	
	/**
	 * 试题模板下载
	 */
	private ActionForward worddownload(ActionMapping mapping,ActionForm actionForm,
	        HttpServletRequest request,
	        HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm) throws Exception{
//		String path=request.getParameter("path");  
		String fileName = "技能认证平台试题导入模板.zip";
		String path = "examonline/download/" + fileName;
		   BufferedInputStream bis=null;    
		   BufferedOutputStream bos=null;    
		   OutputStream os=null;    
		   InputStream is=null;    
		   String filePath=servlet.getServletContext().getRealPath("/"+path);    
		   try {    
		       File downloadFile=new File(filePath);    
		       is=new FileInputStream(downloadFile);    
		       bis=new BufferedInputStream(is);    
		       os=response.getOutputStream();    
		       bos=new BufferedOutputStream(os);    
		       response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(fileName,"utf-8"));    
		       int bytesRead=0;    
		       byte[] buffer=new byte[8192];    
		       while((bytesRead=bis.read(buffer,0,8192))!=-1){    
		    	   bos.write(buffer, 0, bytesRead);    
		       }
		       bos.flush();                           
		       is.close();    
		       bis.close();    
		       os.close();    
		       bos.close();    
		   } catch(Exception e){    
		       e.printStackTrace();    
		   }    
		   return null;    
	}


private void prosessExamConfigList(List<ExamConfig> l,HttpServletRequest request){
	List ll=new ArrayList();
	for (int i = 0; i <l.size(); i++) {
		ExamConfig examConfig = l.get(i);
		Map m=new HashMap();
		m.put("issueId", examConfig.getIssueId());
		m.put("title", examConfig.getTitle());
		m.put("startTime", examConfig.getStartTime());
		m.put("endTime", examConfig.getEndTime());
		m.put("companyName", ExamUtil.examCompanyMap.get(examConfig.getCompanyId()));
		m.put("testerCount", examConfig.getTesterCount());
		if(examConfig.getMarkFlag() == null ){ //旧的数据此字段肯定为空
			m.put("markFlag", 0);
		}else{
			m.put("markFlag", examConfig.getMarkFlag());
		}
		
		ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
		List userSubmit = DAO.query("from ExamSubmit submit where submit.issueId = '"+ examConfig.getIssueId() +"'");
		StringBuffer notestsb = new StringBuffer();
		StringBuffer testsb = new StringBuffer();
		StringBuffer tester = new StringBuffer(examConfig.getTester());
		ID2NameService service = (ID2NameService)ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		//移除规定考试人员中已经参考的人员，获取未参考人员
		for (int j = 0; j <userSubmit.size(); j++){
			ExamSubmit es = (ExamSubmit)userSubmit.get(j);
			testsb.append(";").append(service.id2Name(es.getUserId(), "tawSystemUserDao"));
			try{
				int start = tester.indexOf(es.getUserId());
				int end = start + es.getUserId().length();
				tester.delete(start, end);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		String teststr[]=  tester.toString().replaceAll(";;", ";").split(";");
		for (int ii = 0; ii < teststr.length; ii++) {
			notestsb.append(";").append(service.id2Name(teststr[ii], "tawSystemUserDao"));
		}
		String c = StaticMethod.getCurrentDateTime();
		testsb.append(notestsb);
		if(testsb.length()>0)
			 testsb.deleteCharAt(0);
		if(testsb.length()==0){
			m.put("tester", "还没有指派参考人");
		}else{
			m.put("tester", testsb.toString().replaceAll(";;", ";"));
		}
			
		if(notestsb.length()>0)
			notestsb.deleteCharAt(0);
		if(notestsb.toString().startsWith(";")){
			notestsb.deleteCharAt(0);
		}
		if(notestsb == null || "".equals(notestsb.toString())){
			notestsb = new StringBuffer("无");
		}
	 if(TimeUtil.timeCompareto(c,StaticMethod.getTimestampString(examConfig.getEndTime()))>0){
		 m.put("noTester", notestsb);
	 }else{
		 m.put("noTester", "考试正在进行");
	 }
	 ll.add(m);
	}
	 request.setAttribute("ExamConfigList", ll);
}

  private void generalError(HttpServletRequest request, Exception e) {
    ActionMessages aes = new ActionMessages();
    aes.add("EOMS_WORKSHEET_ERROR",
            new ActionMessage("error.general", e.getMessage()));
    saveMessages(request, aes);
    BocoLog.error(this, 2, "[StudyOnlineAction] Error -", e);
  }
  
  /**
   *  ajax查询各种题型有多少道并显示在页面上
   */
  private ActionForward findExamCount(ActionMapping mapping,ActionForm actionForm,
	        HttpServletRequest request,
	        HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){  
	    ExamDAO dao =(ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
		String specialtySel = request.getParameter("specialtySel");
		
		
		String manufacturerSel = request.getParameter("manufacturerSel");
		String difficulty = request.getParameter("difficulty");
		int count1 = dao.getExamCount(specialtySel, Integer.parseInt(manufacturerSel), Integer.parseInt(difficulty), 1);
		int count2 = dao.getExamCount(specialtySel, Integer.parseInt(manufacturerSel), Integer.parseInt(difficulty), 2);
		int count3 = dao.getExamCount(specialtySel, Integer.parseInt(manufacturerSel), Integer.parseInt(difficulty), 3);
		int count4 = dao.getExamCount(specialtySel, Integer.parseInt(manufacturerSel), Integer.parseInt(difficulty), 4);
		int count5 = dao.getExamCount(specialtySel, Integer.parseInt(manufacturerSel), Integer.parseInt(difficulty), 5);
		try {
			response.setContentType("text/html;charset=UTF-8");	
			response.getWriter().print(count1+","+count2+","+count3+","+count4+","+count5);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
  }
  
  /**
   *  获取考试的待阅卷列表
   */
  private ActionForward findQaMarkList(ActionMapping mapping,ActionForm actionForm,
	        HttpServletRequest request,
	        HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){  
	  String issueId = request.getParameter("issueId");
	  if(StringUtils.isEmpty(issueId)){
		  issueId = request.getAttribute("issueId").toString();
	  }
	  List list = studyOnlineBO.findQaMarkList(issueId);
	  request.setAttribute("list", list);
	  request.setAttribute("issueId", issueId);
	  
	  ExamConfig config = (ExamConfig)studyOnlineBO.load(ExamConfig.class,issueId);
	  request.setAttribute("endTime", config.getEndTime());
	  
	  return mapping.findForward("success");
  }
  
  /**
   *  获取某考生的主观题答案
   */
  private ActionForward findQaMarkPerson(ActionMapping mapping,ActionForm actionForm,
	        HttpServletRequest request,
	        HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){  
	  String markType = request.getParameter("markType");
	  
	  //完成主观题打分
	  if("person".equals(markType)){
		  Map map = request.getParameterMap();
		  for(Iterator it = map.entrySet().iterator();it.hasNext();){
			  Map.Entry entry = (Map.Entry)it.next();
			  String key = entry.getKey().toString();
			  if(!StringUtils.isEmpty(key) && key.length() == 32){
				  String id = key;
				  String[] scoreArr = (String[] )entry.getValue();
				  String scoreStr = scoreArr != null ? scoreArr[0].toString() : "0";
				  int score = Integer.parseInt(scoreStr);
				  ExamContent content = (ExamContent)studyOnlineBO.load(ExamContent.class,id);
				  content.setMark(score);
				  studyOnlineBO.update(content);
			  }
		  }
		  request.setAttribute("issueId", request.getParameter("issueId"));
		  return mapping.findForward("qaMark");
	  }else{//获取主观题标准答案、考生答案、该题总分
		  String issueId = request.getParameter("issueId");
		  String userId = request.getParameter("userId");
		  List list = studyOnlineBO.findQaMarkPerson(userId, issueId);
		  request.setAttribute("list", list);
		  request.setAttribute("issueId", issueId);
		  return mapping.findForward("qaMarkPerson");
	  }
  }
  
  /**
   * 完成某次考试（所有考生）的阅卷
   * @return
   */
  private ActionForward qaMarkFinish(ActionMapping mapping,ActionForm actionForm,
	        HttpServletRequest request,
	        HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){ 
	  String issueId = request.getParameter("issueId");
	  ExamConfig config = (ExamConfig)studyOnlineBO.load(ExamConfig.class,issueId);
	  config.setMarkFlag(2);
	  studyOnlineBO.update(config);
	  return mapping.findForward("success");
  }
  
  /**
   *  导出成绩
   * @return
   */
  private ActionForward exportScore(ActionMapping mapping,ActionForm actionForm,
		  HttpServletRequest request,
		  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){ 
	  try
	    {
	      OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	      response.setContentType("application/octet-stream");
	      String cmd = StaticMethod.null2String(request.getParameter("cmd"));
	      String issueId = StaticMethod.null2String(request.getParameter("issueId"));
	      if (cmd.equals("dept")) {
	        response.reset();
	        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("deptscore.xls", "utf-8"));
	        this.studyOnlineBO.exportScoreByDept(issueId, toClient);
	        if (toClient != null) toClient.close();
	      } else if (cmd.equals("score")) {
	        response.reset();
	        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("score.xls", "utf-8"));
	        this.studyOnlineBO.exportScoreByScore(issueId, toClient);
	        if (toClient != null) toClient.close();
	      } else if (cmd.equals("company")) {
	        response.reset();
	        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("comscore.xls", "utf-8"));
	        this.studyOnlineBO.exportScoreByCompany(request, toClient);
	        if (toClient != null) toClient.close();
	      }
	      toClient.flush();
	      toClient.close();
	    } catch (IOException ex) {
	      ex.printStackTrace();
	      ex.printStackTrace();
	    }

	    return mapping.findForward("");
  }
  /**
   * 添加试题参数到session中
   */
  private ActionForward addTypesel(ActionMapping mapping,ActionForm actionForm,
		  HttpServletRequest request,
		  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){ 
	  String issuetitle=request.getParameter("issuetitle");
	  request.getSession().setAttribute("issuetitle", issuetitle);
	  ExamTypeSelForm examTypeSelForm=(ExamTypeSelForm)actionForm;
	  ExamTypeSel examTypeSel=new ExamTypeSel();
	  Object objStore = request.getSession().getAttribute("storeCount");
	    List storeCountList = null;

	    if (request.getSession().getAttribute("storeCount") == null)
	      storeCountList = new ArrayList();
	    else {
	      storeCountList = (List)objStore;
	    }
	    storeCountList.add(examTypeSelForm);
	    request.getSession().setAttribute("storeCount", storeCountList);
	    JSONArray jsonArray = JSONArray.fromObject(storeCountList);
	    request.getSession().setAttribute("examTypeSelForm", jsonArray);
	  Object obj = request.getSession().getAttribute(ExamUtil.TYPESEL);
	  List<ExamTypeSel> list = null;
	  if(obj == null){
		  list = new ArrayList<ExamTypeSel>();
	  }else{
		  list = (List<ExamTypeSel>)obj;
	  }
	  try {
		BeanUtils.copyProperties(examTypeSel, examTypeSelForm);
		try {
			String id =  UUIDHexGenerator.getInstance().getID();
			//此ID仅是临时ID，不是入库的ID，是为了后面对题的增删改时，临时用一下
			examTypeSel.setId(id);
		} catch (Exception e) {
		}
		list.add(examTypeSel);
		request.getSession().setAttribute(ExamUtil.TYPESEL, list);
//		typeSelBO.addTypeSel(examTypeSel);
		
	} catch (IllegalAccessException e) {
		e.printStackTrace();
	} catch (InvocationTargetException e) {
		e.printStackTrace();
	}
	  return null;
  }
  /**
   * 从session中删除试题
   */
  private ActionForward delTypesel(ActionMapping mapping,ActionForm actionForm,
		  HttpServletRequest request,
		  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){ 
	  String selstr=request.getParameter("sel");
	  int sel=Integer.parseInt(selstr);
	  List<ExamTypeSel> examTypeSelList = (List<ExamTypeSel>) request.getSession().getAttribute(ExamUtil.TYPESEL);
	  if(!examTypeSelList.isEmpty()){
		  examTypeSelList.remove(sel);
	  }
	  return null;
  }
  
  /**
   * 跳转到selectDept.jsp选择考试部门页面
   */
  private ActionForward sendSelectDeptPage(ActionMapping mapping,ActionForm actionForm,
		  HttpServletRequest request,
		  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){ 
	  String issuetitle = request.getParameter("title");
	  //获取所有省下属代维公司
	  PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
	  String hql = " id=interfaceHeadId ";
	  List<PartnerDept> prnDeptList = partnerDeptMgr.getPartnerDeptsByHql(hql);
	  request.setAttribute("prnDeptList", prnDeptList);
	  
	  request.getSession().setAttribute("issuetitle", issuetitle);
	  request.getSession().getAttribute("typeSelList");
	  return mapping.findForward("success");
  }
  /**
   * 设置试题的参数项(包括专业,厂家,难度,试题类型,试题分数,试题内容)
   * @param mapping
   * @param actionForm
   * @param request
   * @param response
   * @param saveSessionBeanForm
   * @return
   */
  private ActionForward performGernateExam(ActionMapping mapping,
          ActionForm actionForm,
          HttpServletRequest request,
          HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){
	    List templist;
	    int totalScore=0;
	    String currentUser = "";   //执行人ID
//	    String typeSel=request.getParameter("typeSel");
	    List<ExamTypeSel> examTypeSelList = (List<ExamTypeSel>) request.getSession().getAttribute(ExamUtil.TYPESEL);
	    request.setAttribute("examTypeSelList", examTypeSelList);
	    List<ExamWarehouse> examWarehouseList=null;
		 try{
			 String tag = "examTag";
			 examWarehouseList=typeSelBO.getExamWarehouseList(examTypeSelList,tag);
			 templist=new ArrayList<ExamWarehouse>();
//			 排序把所有typesel的单选，多选。。。放在一起
			 for(int j = 1; j < 6; ++j){
				 for(int i=0;i<examWarehouseList.size();i++){
				 	if(examWarehouseList.get(i).getContype()==j){
					 	templist.add(examWarehouseList.get(i));
				 	}
			 	}
			 }
			 examWarehouseList=templist;
//			 request.getSession().removeAttribute(ExamUtil.TYPESEL);
	 
			 currentUser = saveSessionBeanForm.getUserid();
	    }
	    catch (Exception e) {
	        generalError(request, e);
	        return mapping.findForward("failure");
	   }
	    
		  for(ExamWarehouse examWarehouse:examWarehouseList){
			  totalScore+=examWarehouse.getValue();
			}
			request.getSession().setAttribute("totalScore", totalScore);
			request.getSession().setAttribute("examWarehouseList", examWarehouseList);
			request.setAttribute("listAll", examWarehouseList);
	  return mapping.findForward("selectDeptSub");
  }
  /**
   *  生成试题
   */
  private ActionForward generateExam(ActionMapping mapping,ActionForm actionForm,
		  HttpServletRequest request,
		  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){ 
	  String starttime =StaticMethod.nullObject2String(request.getParameter("starttime"));
	  String endtime = StaticMethod.nullObject2String(request.getParameter("endtime"));
	  String companyId = StaticMethod.nullObject2String(request.getParameter("companyId"));
	  int testerCount=StaticMethod.null2int(request.getParameter("testerCount"));
	  String companys=StaticMethod.nullObject2String(request.getParameter("companys"));
	  List<ExamWarehouse> examWarehouseList=(List<ExamWarehouse>) request.getSession().getAttribute("examWarehouseList");
	  ExamConfig tmp=(ExamConfig)request.getSession().getAttribute("onlineConfig");
	  String currentUser = "";   //执行人ID
	    currentUser = saveSessionBeanForm.getUserid();
	  String title=(String) request.getSession().getAttribute("issuetitle");
	  //试卷列表
	  List examlist1=null;
	  List<ExamTypeSel> examTypeSelList=(List) request.getSession().getAttribute(ExamUtil.TYPESEL);
	  String carray[]=companys.split(";");
	  ExamDAO DAO =(ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
	//每种题在页面上设置的单题分值
	    String radioScoreStr =  (String)request.getSession().getAttribute("radioScore");
	    String multipleScoreStr = (String)request.getSession().getAttribute("multipleScore");
	    String judgeScoreStr = (String)request.getSession().getAttribute("judgeScore");
  	String qaScoreStr = (String)request.getSession().getAttribute("qaScore");
  	String essayScoreStr = (String)request.getSession().getAttribute("essayScore");
  	  
  	Integer radioScore = !StringUtils.isEmpty(radioScoreStr)? Integer.parseInt(radioScoreStr) : 0;
  	Integer multipleScore = !StringUtils.isEmpty(multipleScoreStr)? Integer.parseInt(multipleScoreStr) : 0;
  	Integer judgeScore = !StringUtils.isEmpty(judgeScoreStr)? Integer.parseInt(judgeScoreStr) : 0;
  	Integer qaScore = !StringUtils.isEmpty(qaScoreStr)? Integer.parseInt(qaScoreStr) : 0;
  	Integer essayScore = !StringUtils.isEmpty(essayScoreStr)? Integer.parseInt(essayScoreStr) : 0;
  	String typesel_fk=null;
//  	Integer totalScore = 0;
  	List<ExamWarehouse> l = new ArrayList<ExamWarehouse>();
  	
	  for(int i=0;i<carray.length;i++){
		  ExamConfig examConfig=new ExamConfig();
		  request.getSession().setAttribute("onlineConfig", examConfig);
		  examConfig.setStartTime(Timestamp.valueOf(starttime));
		  examConfig.setEndTime(Timestamp.valueOf(endtime));
		  examConfig.setCompanyId(companyId);
		  examConfig.setTesterCount(testerCount);
		  examConfig.setTitle(title);
		  examConfig.setIssueUser(currentUser);
		  String singlecompany[]=carray[i].split(":");
			companyId=singlecompany[0];
			testerCount=Integer.valueOf(singlecompany[1]);
			examConfig.setCompanyId(companyId);
			examConfig.setTesterCount(testerCount);
			//Integer totalScore = (Integer)request.getSession().getAttribute("totalScore");
			int score=0;
			for(ExamWarehouse examWarehouse:examWarehouseList){
				score+=examWarehouse.getValue();
				
			}
			examConfig.setMark(score);
			
			for (ExamTypeSel examTypeSel : examTypeSelList) {
				if((examTypeSel.getEssayCount()!=null&&examTypeSel.getEssayCount()!=0)||(examTypeSel.getQaCount()!=null&&examTypeSel.getQaCount()!=0)){;
				examConfig.setMarkFlag(1); //待阅卷
				break;
				}else{
					examConfig.setMarkFlag(0);//不需要阅卷
				}
			}
			try {
				typesel_fk = UUIDHexGenerator.getInstance().getID();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			examConfig.setTypesel_fk(typesel_fk);
			studyOnlineBO.save(examConfig);
			
			for(ExamTypeSel examTypeSel:examTypeSelList){
				examTypeSel.setTypesel_fk(typesel_fk);
				typeSelBO.addTypeSel(examTypeSel);
			}
			
			for (ExamWarehouse warehouse : examWarehouseList) {
	    		ExamIssue ei=new ExamIssue();
	    		try {
					BeanUtils.copyProperties(ei,warehouse);
					ei.setIssueid(examConfig.getIssueId());
					studyOnlineBO.save(ei);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
			
	  }
	  
	  
	  if(request.getSession().getAttribute(ExamUtil.TYPESEL)!=null){
	    	request.getSession().removeAttribute(ExamUtil.TYPESEL);}
	    if(request.getSession().getAttribute("examWarehouseList")!=null){
	    	request.getSession().removeAttribute("examWarehouseList");
	    }
	    if(request.getSession().getAttribute("totalScore")!=null){
	    	request.getSession().removeAttribute("totalScore");
	    }
	    if (request.getSession().getAttribute("typesel") != null) {
	        request.getSession().removeAttribute("typesel");
	      }
	      if (request.getSession().getAttribute("storeCount") != null) {
	        request.getSession().removeAttribute("storeCount");
	      }
	      if (request.getSession().getAttribute("examTypeSelForm") != null) {
	        request.getSession().removeAttribute("examTypeSelForm");
	      }
	  return mapping.findForward("success");
  }
  
  /**
   *  生成试题(代维人员版)
   */
  private ActionForward generateExamPartner(ActionMapping mapping,ActionForm actionForm,
		  HttpServletRequest request,
		  HttpServletResponse response,TawSystemSessionForm saveSessionBeanForm){ 
	  String starttime =StaticMethod.nullObject2String(request.getParameter("starttime"));
	  String endtime = StaticMethod.nullObject2String(request.getParameter("endtime"));
	  String companyId = StaticMethod.nullObject2String(request.getParameter("companyId"));
	  int testerCount=StaticMethod.null2int(request.getParameter("testerCount"));
	  String companys=StaticMethod.nullObject2String(request.getParameter("companys"));
	  List<ExamWarehouse> examWarehouseList=(List<ExamWarehouse>) request.getSession().getAttribute("examWarehouseList");
	  ExamConfig tmp=(ExamConfig)request.getSession().getAttribute("onlineConfig");
	  String currentUser = "";   //执行人ID
	    currentUser = saveSessionBeanForm.getUserid();
	  String title=(String) request.getSession().getAttribute("issuetitle");
	  //试卷列表
	  List examlist1=null;
	  List<ExamTypeSel> examTypeSelList=(List) request.getSession().getAttribute(ExamUtil.TYPESEL);
	  String carray[]=companys.split(";");
	  ExamDAO DAO =(ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
	//每种题在页面上设置的单题分值
	    String radioScoreStr =  (String)request.getSession().getAttribute("radioScore");
	    String multipleScoreStr = (String)request.getSession().getAttribute("multipleScore");
	    String judgeScoreStr = (String)request.getSession().getAttribute("judgeScore");
  	String qaScoreStr = (String)request.getSession().getAttribute("qaScore");
  	String essayScoreStr = (String)request.getSession().getAttribute("essayScore");
  	  
  	Integer radioScore = !StringUtils.isEmpty(radioScoreStr)? Integer.parseInt(radioScoreStr) : 0;
  	Integer multipleScore = !StringUtils.isEmpty(multipleScoreStr)? Integer.parseInt(multipleScoreStr) : 0;
  	Integer judgeScore = !StringUtils.isEmpty(judgeScoreStr)? Integer.parseInt(judgeScoreStr) : 0;
  	Integer qaScore = !StringUtils.isEmpty(qaScoreStr)? Integer.parseInt(qaScoreStr) : 0;
  	Integer essayScore = !StringUtils.isEmpty(essayScoreStr)? Integer.parseInt(essayScoreStr) : 0;
  	String typesel_fk=null;
//  	Integer totalScore = 0;
  	List<ExamWarehouse> l = new ArrayList<ExamWarehouse>();
  	
	  for(int i=0;i<carray.length;i++){
		  ExamConfig examConfig=new ExamConfig();
		  request.getSession().setAttribute("onlineConfig", examConfig);
		  examConfig.setStartTime(Timestamp.valueOf(starttime));
		  examConfig.setEndTime(Timestamp.valueOf(endtime));
		  examConfig.setCompanyId(companyId);
		  examConfig.setTesterCount(testerCount);
		  examConfig.setTitle(title);
		  examConfig.setIssueUser(currentUser);
		  String singlecompany[]=carray[i].split(":");
			companyId=singlecompany[0];
			testerCount=Integer.valueOf(singlecompany[1]);
			examConfig.setCompanyId(companyId);
			examConfig.setTesterCount(testerCount);
			//Integer totalScore = (Integer)request.getSession().getAttribute("totalScore");
			int score=0;
			for(ExamWarehouse examWarehouse:examWarehouseList){
				score+=examWarehouse.getValue();
				
			}
			examConfig.setMark(score);
			
			for (ExamTypeSel examTypeSel : examTypeSelList) {
				if((examTypeSel.getEssayCount()!=null&&examTypeSel.getEssayCount()!=0)||(examTypeSel.getQaCount()!=null&&examTypeSel.getQaCount()!=0)){;
				examConfig.setMarkFlag(1); //待阅卷
				break;
				}else{
					examConfig.setMarkFlag(0);//不需要阅卷
				}
			}
			try {
				typesel_fk = UUIDHexGenerator.getInstance().getID();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			examConfig.setTypesel_fk(typesel_fk);
			
			//这里暂时都默认考试类型为代维上岗考试类型，如果需要变成根据页面选择考试类型请修改此处
			examConfig.setExamType(2);
			
			studyOnlineBO.save(examConfig);
			
			for(ExamTypeSel examTypeSel:examTypeSelList){
				examTypeSel.setTypesel_fk(typesel_fk);
				typeSelBO.addTypeSel(examTypeSel);
			}
			
			for (ExamWarehouse warehouse : examWarehouseList) {
	    		ExamIssue ei=new ExamIssue();
	    		try {
					BeanUtils.copyProperties(ei,warehouse);
					ei.setIssueid(examConfig.getIssueId());
					studyOnlineBO.save(ei);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
			
			//自动将试题与代维人员关联，不再手动关联
			PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
			PartnerUserMgr partnerUserMgr = (PartnerUserMgr) ApplicationContextHolder.getInstance().getBean("partnerUserMgr");
			PartnerDept dept = partnerDeptMgr.getPartnerDept(companyId);
			List<PartnerUser> users = partnerUserMgr.findAllPnrTestters(dept.getDeptMagId());
			String usersStr = "";
			for (PartnerUser partnerUser : users) {
				usersStr += partnerUser.getPersonCardNo() + ";"; //使用身份证作为考试标识
			}
			studyOnlineBO.issueRalateUser(examConfig.getIssueId(), usersStr);
			
	  }
	  
	  
	  if(request.getSession().getAttribute(ExamUtil.TYPESEL)!=null){
	    	request.getSession().removeAttribute(ExamUtil.TYPESEL);}
	    if(request.getSession().getAttribute("examWarehouseList")!=null){
	    	request.getSession().removeAttribute("examWarehouseList");
	    }
	    if(request.getSession().getAttribute("totalScore")!=null){
	    	request.getSession().removeAttribute("totalScore");
	    }
	    if (request.getSession().getAttribute("typesel") != null) {
	        request.getSession().removeAttribute("typesel");
	      }
	      if (request.getSession().getAttribute("storeCount") != null) {
	        request.getSession().removeAttribute("storeCount");
	      }
	      if (request.getSession().getAttribute("examTypeSelForm") != null) {
	        request.getSession().removeAttribute("examTypeSelForm");
	      }
	      
	      
	      
	  return mapping.findForward("success");
  }
}
