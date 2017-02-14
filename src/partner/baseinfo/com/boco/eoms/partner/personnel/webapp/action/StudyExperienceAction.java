package com.boco.eoms.partner.personnel.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonConstants;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.baseinfo.util.PnrDeptAndUserConfigSetList;
import com.boco.eoms.partner.personnel.mgr.RewardMgr;
import com.boco.eoms.partner.personnel.mgr.StudyExperienceMgr;
import com.boco.eoms.partner.personnel.model.Reward;
import com.boco.eoms.partner.personnel.model.StudyExperience;
import com.boco.eoms.partner.personnel.util.MyUtil;
import com.boco.eoms.partner.personnel.util.PageData;
import com.boco.eoms.partner.personnel.util.PartnerUserHander;
import com.boco.eoms.partner.personnel.util.SearchUtil;
import com.boco.eoms.partner.personnel.util.fusionchart.service.BuildFusionChartXml;
import com.boco.eoms.partner.personnel.webapp.form.StudyExperienceForm;
import com.boco.eoms.partner.resourceInfo.form.ApparatusForm;
import com.boco.eoms.partner.resourceInfo.service.ApparatusService;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.statistically.pojo.TdObjModel;
import com.boco.eoms.partner.statistically.utils.TableHelper;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
/**
 * <p>
 * Title:人员学习经历管理
 * </p>
 * <p>
 * Description:人员学习经历管理
 * </p>
 * <p>
 * Jul 19, 2012 11:26:49 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class StudyExperienceAction extends BaseAction {
	/**
	 * 进行页面操作名称（增加，修改）
	 */
	private final String TOADD = "toadd";  //进入添加页面
	private final String TOEDIT = "toedit";//进入编辑页面
	private final String TOMGR = "tomgr";//进入管理页面
	private final String TOSTA = "statistics";//进入统计页面
	private String operationType = null;
	private String personCardNo = null;//人员身份证  
	/**
	 * 下载模板
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rootPath = request.getRealPath("/partner/processExcelModel");
		String fileName="";
		PnrDeptAndUserConfigSetList setList=(PnrDeptAndUserConfigSetList)getBean("pnrDeptAndUserConfigSetList");
		if ("personCardNo".equals(setList.getDwInfoValidateType())) {
			fileName="增加人员教育经历模板2.xls";
		}else {
			fileName="增加人员教育经历模板1.xls";
		}
		File file = new File(rootPath + File.separator + fileName);
		// 读到流中
		InputStream inStream = new FileInputStream(file);// 文件的存放路径
		// 设置输出的格式
		response.reset();
		response.setContentType("application/x-msdownload;charset=GBK");
		response.setCharacterEncoding("GB2312");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("gbk"), "iso8859-1"));
		// 循环取出流中的数据
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = inStream.read(b)) > 0)
			response.getOutputStream().write(b, 0, len);
		inStream.close();
	}
	/**
	 * 导入数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward importData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		StudyExperienceForm uploadForm=(StudyExperienceForm)form;
		FormFile formFile=uploadForm.getImportFile();
		PrintWriter writer=null;
		try {
			 StudyExperienceMgr studyExperienceMgr = (StudyExperienceMgr) getBean("refstudyExperienceMgr");
			writer=response.getWriter();
			ImportResult result=studyExperienceMgr.importFromFile(formFile,request);
			if (result.getResultCode().equals("200")) {
				writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
						.put("success", "true")
						.put("msg", "ok")
						.put("infor", "导入成功,共计导入"+result.getImportCount()+"条记录.\n"+result.getRestultMsg()).build()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "false")
							.put("msg", "failure")
							.put("infor", e.getMessage()).build()));
		}finally{
			if(writer != null){
				writer.close();
			}
		}
		return null;
	}
	/**
	 * 进入学习经历编辑页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getjsp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		operationType = request.getParameter("operationType");
		 if(TOADD.equals(operationType)){
			 request.setAttribute("operationType", operationType);
			//----人员信息综合页面处理---
				if(!"".equals(personCardNo)&&personCardNo!=null){
					request.setAttribute("personCardNo",personCardNo);
					request.setAttribute("workerid", request.getParameter("workerid"));
					request.setAttribute("workername", MyUtil.getString(request.getParameter("workername")));
				}
			//----人员信息综合页面处理---End
			 return mapping.findForward("add_edit");
		 }
		 else
			 if(TOEDIT.equals(operationType)){
				 //取得数据 填充页面
				 String id = request.getParameter("id");
				 System.out.println(id);
				 StudyExperienceMgr studyExperienceMgr = (StudyExperienceMgr) getBean("refstudyExperienceMgr");
				 StudyExperience studyExperience = studyExperienceMgr.find(id);
				 request.setAttribute("operationType", operationType);
				 request.setAttribute("studyExperience", studyExperience);
				 
				//----人员信息综合页面处理---
					if(!"".equals(personCardNo)&&personCardNo!=null){
						request.setAttribute("personCardNo",personCardNo);
						request.setAttribute("workerid", request.getParameter("workerid"));
						request.setAttribute("workername", MyUtil.getString(request.getParameter("workername")));
						
					}
				//----人员信息综合页面处理---End
				 
				 return mapping.findForward("add_edit");
			 }
		 else if(TOSTA.equals(operationType)){
			//代维专业字典数据
				ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
				request.setAttribute("specialtyList", mgr.getDictSonsByDictid("11225"));
			 return mapping.findForward("statistics");
			}
		 return mapping.findForward("fail");
	}
	/**
	 * 保存学习经历信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			StudyExperienceMgr studyExperienceMgr = (StudyExperienceMgr) getBean("refstudyExperienceMgr");
			StudyExperienceForm studyExperienceForm = (StudyExperienceForm) form;
			StudyExperience studyExperience = (StudyExperience) convert(studyExperienceForm);
			String operationTime = (new Date()).toString();
			studyExperience.setAdduser(sessionInfo.getUsername());
			studyExperience.setAdddept(sessionInfo.getDeptname());
			studyExperience.setAddtime(operationTime);
			studyExperience.setLastediterid(sessionInfo.getUserid());
			studyExperience.setLasteditername(sessionInfo.getUsername());
			studyExperience.setLastedittime(operationTime);
			PartnerUserHander hander= new PartnerUserHander();
			studyExperience=hander.handleDegreeInfo(studyExperience);
			studyExperienceMgr.save(studyExperience);
			//----人员信息综合页面处理---
			if(!"".equals(personCardNo)&&personCardNo!=null){
				request.setAttribute("personCardNo", personCardNo);
				request.setAttribute("operationType", TOMGR);
				return this.search(mapping, form, request, response);
			}
			//----人员信息综合页面处理---End
		return mapping.findForward("success");
	}
	/**
	 * 修改学习经历信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		StudyExperienceMgr studyExperienceMgr = (StudyExperienceMgr) getBean("refstudyExperienceMgr");
		StudyExperienceForm studyExperienceForm = (StudyExperienceForm) form;
		StudyExperience studyExperience = (StudyExperience) convert(studyExperienceForm);
		studyExperience.setLastediterid(sessionInfo.getUserid());
		studyExperience.setLasteditername(sessionInfo.getUsername());
		studyExperience.setLastedittime((new Date()).toString());
		PartnerUserHander hander= new PartnerUserHander();
		studyExperience=hander.handleDegreeInfo(studyExperience);
		studyExperienceMgr.save(studyExperience);
		//----人员信息综合页面处理---
		if(!"".equals(personCardNo)&&personCardNo!=null){
			request.setAttribute("personCardNo", personCardNo);
			request.setAttribute("operationType", TOMGR);
			return this.search(mapping, form, request, response);
		}
		//----人员信息综合页面处理---End
		return mapping.findForward("success");
	}
	/**
	 * 查询，显示学习经历列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		operationType = request.getParameter("operationType");
		if("".equals(operationType)||operationType==null){
			if(request.getAttribute("operationType")!=null)
				operationType = request.getAttribute("operationType").toString();
		}
		
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				"studyExperienceList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		StudyExperienceMgr studyExperienceMgr = (StudyExperienceMgr) getBean("refstudyExperienceMgr");
		Search search = new Search();
		/*/*移动人员和代维人员的操作权限的决定begin*/
		TawSystemSessionForm sessionForm=this.getUser(request);
		String deptid=sessionForm.getDeptid();
		String hasRightForDelAndAdd="1";
		PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
		List<PartnerDept> list0=partnerDeptMgr.getPartnerDepts("and deptMagId='"+deptid+"'");
		if (list0.size()!=0&&list0!=null) {//不等于0表示是代维公司的
			hasRightForDelAndAdd="0";
		}else {
			hasRightForDelAndAdd="1";
		}
		/*移动人员和代维人员的操作权限的决定end*/
		search = CommonUtils.getSqlFromRequestMap(request, search);
		search.addFilterEqual("isdelete", 0);
		personCardNo = request.getParameter("personCardNo");
		/*从列表页面进入search方法根据代维人员公司和移动人员管理区域权限控制 begin*/
		if("".equals(personCardNo)||personCardNo==null){
			if(request.getAttribute("personCardNo")!=null)
				personCardNo = request.getAttribute("personCardNo").toString();
			if(!sessionForm.getUserid().equals("admin")){
				String userDeptId = sessionForm.getDeptid();
				if (!"".equals(userDeptId)) {/**/
					/*先判断是移动公司人员还是代维公司人员*/
					if (list0.size()!=0&&list0!=null) {//不等于0表示是代维公司的
						/*代维公司人员只能浏览本公司代维公司员工的权利*/
						search.addFilterLike("deptid", userDeptId+"%");
					}else {
						/*移动公司人员拥有删除、修改、增加本区域代维公司员工的权利*/
						ITawSystemDeptManager deptManager=(ITawSystemDeptManager)getBean("ItawSystemDeptManager");
						TawSystemDept dept=deptManager.getDeptinfobydeptid(userDeptId,"0");
						if (dept!=null) {
							search.addFilterLike("areaid",StaticMethod.null2String(dept.getAreaid())+"%");/*区域权限限定*/
						}
					}
				}
			}
		}
		/*从列表页面进入search方法根据代维人员公司和移动人员管理区域权限控制 end*/
		
		/*单个代维人员或者移动人员进入search页面,只能查看某个代维人员的所有的技能信息begin*/
		if(!"".equals(personCardNo)&&personCardNo!=null){
			PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
			PartnerUser user = partnerUserMgr.getPartnerUserByPersonCardNo(personCardNo);
			search.addFilterEqual("workerid", user.getUserId());
			request.setAttribute("workerid", user.getUserId());
			request.setAttribute("workername", user.getName());
		}
		/*单个代维人员或者移动人员进入search页面,只能查看某个代维人员的所有的技能信息end*/
		
		search.setFirstResult(pageIndex.intValue()*CommonConstants.PAGE_SIZE);
		String allFlag=StaticMethod.null2String(request.getParameter("6578706f7274"));
		if ("".equals(allFlag)) {//执行全表导出
			search.setMaxResults(CommonConstants.PAGE_SIZE);
		}
		SearchResult<StudyExperience> searchResult = studyExperienceMgr.searchAndCount(search);
        List<StudyExperience> list = searchResult.getResult();
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("resultSize", searchResult.getTotalCount());
		request.setAttribute("studyExperienceList", list);
//		request.setAttribute("universityStringLike", request.getParameter("universityStringLike"));
//		request.setAttribute("professionalStringLike", request.getParameter("professionalStringLike"));
//		request.setAttribute("degreeStringEqual", request.getParameter("degreeStringEqual"));
		request.setAttribute("hasRightForDelAndAdd", hasRightForDelAndAdd);
		/*更加角色进入不同页面权限控制begin*/
		if (list0.size()!=0&&list0!=null) {//不等于0表示是代维公司的
			return mapping.findForward("list");
		}
		 request.setAttribute("operationType", operationType);
		 return mapping.findForward("listmgr");
		 /*更加角色进入不同页面权限控制end*/
	}
	/**
	* @Title: searchCompanyStudyExperienceList 
	* @Description: 代维人员和移动管理人员进入list页面
	* @param 
	* @Time:Nov 28, 2012-5:04:47 PM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward  searchCompanyStudyExperienceList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder("studyExperienceList")
		.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		StudyExperienceMgr studyExperienceMgr = (StudyExperienceMgr) getBean("refstudyExperienceMgr");
		Search search = new Search();
		search =CommonUtils.getSqlFromRequestMap(request, search);
		search.addFilterEqual("isdelete", 0);
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		/*只有移动管理人员才能有删除、增加和修改权限 2012-11-28*/
		//不是管理员则只能查看自己部门的人力信息
		List<PartnerDept> list0=new ArrayList<PartnerDept>();
		String hasRightForDelAndAdd="1";
		if(!sessionForm.getUserid().equals("admin")){
			String userDeptId = sessionForm.getDeptid();
			if (!"".equals(userDeptId)) {/**/
				/*先判断是移动公司人员还是代维公司人员*/
				PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
				list0=partnerDeptMgr.getPartnerDepts("and deptMagId='"+userDeptId+"'");
				if (list0.size()!=0&&list0!=null) {//不等于0表示是代维公司的
					/*代维公司人员只能浏览本公司代维公司员工的权利*/
					hasRightForDelAndAdd="0";
					search.addFilterLike("deptid", userDeptId+"%");
				}else {
					/*移动公司人员拥有删除、修改、增加本区域代维公司员工的权利*/
					hasRightForDelAndAdd="1";
					ITawSystemDeptManager deptManager=(ITawSystemDeptManager)getBean("ItawSystemDeptManager");
					TawSystemDept dept=deptManager.getDeptinfobydeptid(userDeptId,"0");
					if (dept!=null) {
						search.addFilterLike("areaid",StaticMethod.null2String(dept.getAreaid())+"%");/*区域权限限定*/
					}
				}
			}
		}
		search.setFirstResult(pageIndex.intValue()*CommonConstants.PAGE_SIZE); 
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		SearchResult<StudyExperience> searchResult = studyExperienceMgr.searchAndCount(search);
        List<StudyExperience> list = searchResult.getResult();
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("resultSize", searchResult.getTotalCount());
		request.setAttribute("studyExperienceList", list);
		request.setAttribute("hasRightForDelAndAdd", hasRightForDelAndAdd);
		/*只有移动管理人员才能有删除、增加和修改的权限 2012-11-28*/
		if (list0.size()!=0&&list0!=null) {
			return mapping.findForward("list");
		}
		 return mapping.findForward("listmgr");
	}
	/**
	 * 删除学习经历信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		StudyExperienceMgr studyExperienceMgr = (StudyExperienceMgr) getBean("refstudyExperienceMgr");
		String selectids = StaticMethod.null2String(request.getParameter("selectids"));
		StudyExperience studyExperience;
		final String lastOpUserid = sessionInfo.getUserid();
		final String lastOpUserName = sessionInfo.getUsername();
		try {
			if (!"".equals(selectids)) {
				String[] sel = selectids.split("\\|");
				for (int i = 0; i < sel.length; i++) {
					studyExperience = studyExperienceMgr.find(sel[i]);
					studyExperience.setIsdelete("1");
					studyExperience.setLastediterid(lastOpUserid);
					studyExperience.setLasteditername(lastOpUserName);
					studyExperience.setLastedittime((new Date()).toString());
					studyExperienceMgr.save(studyExperience);
				}
				//----人员信息综合页面处理---
				if(!"".equals(personCardNo)&&personCardNo!=null){
					request.setAttribute("personCardNo", personCardNo);
					request.setAttribute("operationType", TOMGR);
					return this.search(mapping, form, request, response);
				}
				//----人员信息综合页面处理---End
				return mapping.findForward("success");
			} 
		} catch (Exception e) {
			return mapping.findForward("fail");
		}
	return mapping.findForward("fail");
	}
	/**
	 * 
	* @Title: getDetail 
	* @Description: 获得教育经历信息详情
	* @param 
	* @Time:Dec 1, 2012-9:36:07 AM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward getDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StudyExperienceMgr studyExperienceMgr = (StudyExperienceMgr) getBean("refstudyExperienceMgr");
		String id = StaticMethod.getString(request.getParameter("id"));
		String personCardNo=StaticMethod.null2String(request.getParameter("personCardNo"));
		String hasRightGoBack=StaticMethod.null2String(request.getParameter("hasRightGoBack"));
		StudyExperience  studyExperience = studyExperienceMgr.find(id);
		request.setAttribute("hasRightGoBack", hasRightGoBack);
		request.setAttribute("studyExperience", studyExperience);
		request.setAttribute("personCardNo", personCardNo);
		return mapping.findForward("detail");
	}
}
