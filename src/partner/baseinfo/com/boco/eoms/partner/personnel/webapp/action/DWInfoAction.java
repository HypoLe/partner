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
import com.boco.eoms.partner.personnel.mgr.CertificateMgr;
import com.boco.eoms.partner.personnel.mgr.DWInfoMgr;
import com.boco.eoms.partner.personnel.mgr.PXExperienceMgr;
import com.boco.eoms.partner.personnel.model.Certificate;
import com.boco.eoms.partner.personnel.model.DWInfo;
import com.boco.eoms.partner.personnel.model.PXExperience;
import com.boco.eoms.partner.personnel.util.MyUtil;
import com.boco.eoms.partner.personnel.util.PartnerUserHander;
import com.boco.eoms.partner.personnel.webapp.form.DWInfoForm;
import com.boco.eoms.partner.process.util.PnrProcessCach;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
/**
 * <p>
 * Title:代维人员资质信息管理
 * </p>
 * <p>
 * Description:代维人员资质信息管理
 * </p>
 * <p>
 * Jul 16, 2012 10:26:49 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class DWInfoAction extends BaseAction {
	/**
	 * 进行页面操作名称（增加，修改）
	 */
	private final String TOADD = "toadd";  //进入添加页面
	private final String TOEDIT = "toedit";//进入编辑页面
	private final String TOMGR = "tomgr";//进入管理页面
	private final String TOIMPORT = "toimport";//进入导入页面
	private String operationType = null;
	private String personCardNo = null;//人员身份证 
	
	
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
		DWInfoForm uploadForm=(DWInfoForm)form;
		FormFile formFile=uploadForm.getImportFile();
		PrintWriter writer=null;
		try {
			 DWInfoMgr dwinfoMgr = (DWInfoMgr) getBean("refdwinfoMgr");
			writer=response.getWriter();
			ImportResult result=dwinfoMgr.importFromFile(formFile,request);
			if (result.getResultCode().equals("200")) {
				writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
						.put("success", "true")
						.put("msg", "ok")
						.put("infor", "导入成功,共计导入"+result.getImportCount()+"条记录.\n"+result.getRestultMsg()).build()));
			}
		}catch (Exception e) {
			e.printStackTrace();
			writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "false")
							.put("msg", "failure")
							.put("infor", e.getMessage()).build()));
		}finally{
			System.out.println("-------重新加载技能信息缓存------");
			PnrProcessCach.dwInfoListCach.clear();
			PnrProcessCach.loadDwinfoList();
			if(writer != null){
				writer.close();
			}
		}
		return null;
	}
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
		String fileName ="";
		PnrDeptAndUserConfigSetList setList=(PnrDeptAndUserConfigSetList)getBean("pnrDeptAndUserConfigSetList");
		if ("personCardNo".equals(setList.getDwInfoValidateType())) {
			fileName="增加人员技能信息模板2.xls";
		}else {
			fileName="增加人员技能信息模板1.xls";
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
	 * 进入资质信息编辑页面
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
		//  0代维;1自维
		String isPartner = request.getParameter("isPartner");
		request.setAttribute("isPartner", isPartner);
		operationType = request.getParameter("operationType");
		 if(TOIMPORT.equals(operationType)){
				 request.setAttribute("operationType", operationType);
				 return mapping.findForward("import");
			 }
		 else if(TOADD.equals(operationType)){
			 request.setAttribute("operationType", operationType);
			//----人员信息综合页面处理---
				if(!"".equals(personCardNo)&&personCardNo!=null){
					request.setAttribute("personCardNo",personCardNo);
					request.setAttribute("workerid", request.getParameter("workerid"));
					request.setAttribute("deptid", MyUtil.getString(request.getParameter("deptid")));
					request.setAttribute("workername", MyUtil.getString(request.getParameter("workername")));
					request.setAttribute("deptname",MyUtil.getString(request.getParameter("deptname")));
					
				}
			//----人员信息综合页面处理---End
				
			ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
			request.setAttribute("specialtyList", mgr.getDictSonsByDictid("11225"));	
			request.setAttribute("countNoList", mgr.getDictSonsByDictid("12401"));	
			 return mapping.findForward("add_edit");
		 }
			 else
				 if(TOEDIT.equals(operationType)){
					 //取得数据 填充页面
					 String id = request.getParameter("id");
					 System.out.println(id);
					 DWInfoMgr dwinfoMgr = (DWInfoMgr) getBean("refdwinfoMgr");
					 DWInfo dwInfo = dwinfoMgr.find(id);
					 request.setAttribute("operationType", operationType);
					 request.setAttribute("dwInfo", dwInfo);
					CertificateMgr certificateMgr = (CertificateMgr) getBean("refcertificateMgr");
					 PXExperienceMgr pxexperienceMgr = (PXExperienceMgr) getBean("refpxexperienceMgr");
					 String userid=StaticMethod.null2String(dwInfo.getWorkerid());
						List<Certificate> certList=certificateMgr.getCertificateByUserid(userid);
						List<PXExperience> pxExperienceList=pxexperienceMgr.getPXExperienceByUserid(userid);
						String certListImagepaths="";
						String pxListImagepaths="";
						for (int i = 0; i < certList.size(); i++) {
							Certificate cert=certList.get(i);
							if (!"".equals(cert.getImagepath())) {
								if (!"".equals(certListImagepaths)) {
									certListImagepaths+=",";
								}
								certListImagepaths+=cert.getImagepath();
							}
						}
						for (int i = 0; i < pxExperienceList.size(); i++) {
							PXExperience px=pxExperienceList.get(i);
							if (!"".equals(px.getImagepath())) {
								if (!"".equals(pxListImagepaths)) {
									pxListImagepaths+=",";
								}
								pxListImagepaths+=px.getImagepath();
							}
						}
						ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
						request.setAttribute("specialtyList", mgr.getDictSonsByDictid("11225"));
						request.setAttribute("countNoList", mgr.getDictSonsByDictid("12401"));
						request.setAttribute("pxListImagepaths", pxListImagepaths);
						request.setAttribute("certListImagepaths", certListImagepaths);
						//----人员信息综合页面处理---
						if(!"".equals(personCardNo)&&personCardNo!=null){
							request.setAttribute("personCardNo",personCardNo);
							request.setAttribute("workerid", request.getParameter("workerid"));
							request.setAttribute("deptid", MyUtil.getString(request.getParameter("deptid")));
							request.setAttribute("workername", MyUtil.getString(request.getParameter("workername")));
							request.setAttribute("deptname",MyUtil.getString(request.getParameter("deptname")));
						}
					//----人员信息综合页面处理---End
					 return mapping.findForward("add_edit");
				 }
		 return mapping.findForward("fail");
	}
	/**
	 * 保存资质信息信息
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
			DWInfoMgr dwinfoMgr = (DWInfoMgr) getBean("refdwinfoMgr");
			DWInfoForm dwinfoForm = (DWInfoForm) form;
			DWInfo dwinfo = (DWInfo) convert(dwinfoForm);
			PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
			String workerId=dwinfo.getWorkerid();
			PartnerUser partnerUser=partnerUserMgr.getPartnerUserByUserId(workerId);
			if(partnerUser!=null){
				dwinfo.setGroup(partnerUser.getDeptName());
			}
			String professional=StaticMethod.null2String(dwinfo.getProfessional());
			String operationTime = (new Date()).toString();
			dwinfo.setAdduser(sessionInfo.getUsername());
			dwinfo.setAdddept(sessionInfo.getDeptname());
			dwinfo.setAddtime(operationTime);
			dwinfo.setLastediterid(sessionInfo.getUserid());
			dwinfo.setLasteditername(sessionInfo.getUsername());
			dwinfo.setLastedittime(operationTime);
			PartnerUserHander hander= new PartnerUserHander();
			dwinfo=hander.handleSkillLevelInfo(dwinfo);
			String userid=dwinfo.getWorkerid();
			String personCardNo1=dwinfo.getPersonCardNo();
			try {
				dwinfoMgr.save(dwinfo);
				//更新缓存中的静态资源
				PnrProcessCach.dwInfoListCach.put("userId_"+userid+",pro_"+professional, professional);
				PnrProcessCach.dwInfoListCach.put("personCardNo_"+personCardNo1+",pro_"+professional, professional);
			} catch (org.springframework.dao.DataIntegrityViolationException e) {
				request.setAttribute("msg", "该员工已经拥有了该专业技能!");
				return mapping.findForward("fail");
			}catch (Exception e) {
				request.setAttribute("msg", "保存出错请重试!");
				return mapping.findForward("fail");
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
	/**
	 * 修改资质信息信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			DWInfoMgr dwinfoMgr = (DWInfoMgr) getBean("refdwinfoMgr");
			DWInfo dwinfo = new DWInfo();
			String id=StaticMethod.null2String(request.getParameter("id"));
			String personCardNo=StaticMethod.null2String(request.getParameter("personCardNo"));
			String userid=StaticMethod.null2String(request.getParameter("workerid"));
			dwinfo=dwinfoMgr.find(id);
			String oldProfessional=dwinfo.getProfessional();
			DWInfoForm dwinfoForm = (DWInfoForm) form;
			dwinfo=(DWInfo) convert(dwinfoForm);
			String newProfessional=dwinfo.getProfessional();
			dwinfo.setLastediterid(sessionInfo.getUserid());
			dwinfo.setLasteditername(sessionInfo.getUsername());
			dwinfo.setLastedittime((new Date()).toString());
			PartnerUserHander hander= new PartnerUserHander();
			dwinfo=hander.handleSkillLevelInfo(dwinfo);
			dwinfoMgr.save(dwinfo);
			//更新缓存
			if (!PnrProcessCach.dwInfoListCach.containsKey(newProfessional)) {
				PnrProcessCach.dwInfoListCach.put("userId_"+userid+",pro_"+newProfessional, newProfessional);
				PnrProcessCach.dwInfoListCach.put("personCardNo_"+personCardNo+",pro_"+newProfessional, newProfessional);
				PnrProcessCach.dwInfoListCach.remove("userId_"+userid+",pro_"+oldProfessional);
				PnrProcessCach.dwInfoListCach.remove("personCardNo_"+personCardNo+",pro_"+oldProfessional);
			}
		} catch (RuntimeException e) {
			return mapping.findForward("fail");
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
	/**
	 * 
	* @Title: search 
	* @Description: 查询列表页面
	* @param 
	* @Time:Nov 28, 2012-5:48:33 PM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		operationType = request.getParameter("operationType");
		if("".equals(operationType)||operationType==null){
			if(request.getAttribute("operationType")!=null)
				operationType = request.getAttribute("operationType").toString();
		}
		String pageIndexName = new org.displaytag.util.ParamEncoder("dwInfoList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		DWInfoMgr dwinfoMgr = (DWInfoMgr) getBean("refdwinfoMgr");
		Search search = new Search();
		/*移动人员和代维人员的操作权限的决定begin*/
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
			request.setAttribute("deptname",user.getDeptName());
			request.setAttribute("deptid",user.getDeptId());
		}
		/*单个代维人员或者移动人员进入search页面,只能查看某个代维人员的所有的技能信息end*/
		search.setFirstResult(pageIndex.intValue()*CommonConstants.PAGE_SIZE);
		String allFlag=StaticMethod.null2String(request.getParameter("6578706f7274"));
		if ("".equals(allFlag)) {//执行全表导出
			search.setMaxResults(CommonConstants.PAGE_SIZE);
		}
		SearchResult<DWInfo> searchResult = dwinfoMgr.searchAndCount(search);
        List<DWInfo> list = searchResult.getResult();
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("resultSize", searchResult.getTotalCount());
		request.setAttribute("dwInfoList", list);
		request.setAttribute("hasRightForDelAndAdd", hasRightForDelAndAdd);
//		request.setAttribute("groupStringLike", request.getParameter("groupStringLike"));
//		request.setAttribute("dutyStringLike", request.getParameter("dutyStringLike"));
		/*更加角色进入不同页面权限控制begin*/
		if (list0.size()!=0&&list0!=null) {//不等于0表示是代维公司的
			return mapping.findForward("list");
		}
		 request.setAttribute("operationType", operationType);
		 return mapping.findForward("listmgr");
		 /*更加角色进入不同页面权限控制end*/
	}
	/**
	 * 
	* @Title: searchCompanyDwinfoList 
	* @Description: 证书信息列表页面，代维人员和移动人员分别进入不同的页面各自有权限控制；
	* @param 
	* @Time:Nov 28, 2012-4:52:50 PM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward  searchCompanyDwinfoList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder("dwInfoList")
		.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		DWInfoMgr dwinfoMgr = (DWInfoMgr) getBean("refdwinfoMgr");
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
		SearchResult<DWInfo> searchResult = dwinfoMgr.searchAndCount(search);
        List<DWInfo> list = searchResult.getResult();
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("resultSize", searchResult.getTotalCount());
		request.setAttribute("dwInfoList", list);
		request.setAttribute("hasRightForDelAndAdd", hasRightForDelAndAdd);
		/*只有移动管理人员才能有删除、增加和修改的权限 2012-11-28*/
		if (list0.size()!=0&&list0!=null) {
			return mapping.findForward("list");
		}
		 return mapping.findForward("listmgr");
	}
	/**
	 * 删除资质信息信息
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
		DWInfoMgr dwinfoMgr = (DWInfoMgr) getBean("refdwinfoMgr");
		String selectids = StaticMethod.null2String(request.getParameter("selectids"));
		DWInfo dwinfo;
		final String lastOpUserid = sessionInfo.getUserid();
		final String lastOpUserName = sessionInfo.getUsername();
		try {
			if (!"".equals(selectids)) {
				String[] sel = selectids.split("\\|");
				for (int i = 0; i < sel.length; i++) {
					dwinfo=dwinfoMgr.find(sel[i]);
					String userid=StaticMethod.null2String(dwinfo.getWorkerid());
					String professional=StaticMethod.null2String(dwinfo.getProfessional());
					String personCardNo=StaticMethod.null2String(dwinfo.getPersonCardNo());
					dwinfoMgr.removeById(sel[i]);
					PnrProcessCach.dwInfoListCach.remove("userId_"+userid+",pro_"+professional);
					PnrProcessCach.dwInfoListCach.remove("personCardNo_"+personCardNo+",pro_"+professional);
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
			PnrProcessCach.dwInfoListCach.clear();
			PnrProcessCach.loadDwinfoList();
			return mapping.findForward("fail");
		}
	return mapping.findForward("fail");
	}
	/**
	 * 
	* @Title: getDetail 
	* @Description: 获取代维资质详情；
	* @param 
	* @Time:Dec 1, 2012-9:34:09 AM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward getDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DWInfoMgr dwinfoMgr = (DWInfoMgr) getBean("refdwinfoMgr");
		String id = StaticMethod.getString(request.getParameter("id"));
		String personCardNo=StaticMethod.null2String(request.getParameter("personCardNo"));
		String hasRightGoBack=StaticMethod.null2String(request.getParameter("hasRightGoBack"));
		DWInfo  dwinfo = dwinfoMgr.find(id);
		String userid=StaticMethod.null2String(dwinfo.getWorkerid());
		CertificateMgr certificateMgr = (CertificateMgr) getBean("refcertificateMgr");
		 PXExperienceMgr pxexperienceMgr = (PXExperienceMgr) getBean("refpxexperienceMgr");
		List<Certificate> certList=certificateMgr.getCertificateByUserid(userid);
		List<PXExperience> pxExperienceList=pxexperienceMgr.getPXExperienceByUserid(userid);
		String certListImagepaths="";
		String pxListImagepaths="";
		for (int i = 0; i < certList.size(); i++) {
			Certificate cert=certList.get(i);
			if (!"".equals(cert.getImagepath())) {
				if (!"".equals(certListImagepaths)) {
					certListImagepaths+=",";
				}
				certListImagepaths+=cert.getImagepath();
			}
		}
		for (int i = 0; i < pxExperienceList.size(); i++) {
			PXExperience px=pxExperienceList.get(i);
			if (!"".equals(px.getImagepath())) {
				if (!"".equals(pxListImagepaths)) {
					pxListImagepaths+=",";
				}
				pxListImagepaths+=px.getImagepath();
			}
		}
		try {
			dwinfoMgr.save(dwinfo);
		} catch (RuntimeException e) {
			return mapping.findForward("fail");
		}
		request.setAttribute("hasRightGoBack", hasRightGoBack);
		request.setAttribute("dwinfo", dwinfo);
		request.setAttribute("pxListImagepaths", pxListImagepaths);
		request.setAttribute("certListImagepaths", certListImagepaths);
		request.setAttribute("personCardNo", personCardNo);
		return mapping.findForward("detail");
	}
}
