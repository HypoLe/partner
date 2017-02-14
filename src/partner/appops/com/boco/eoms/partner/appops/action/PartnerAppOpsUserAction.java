package com.boco.eoms.partner.appops.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import webwork.action.ServletActionContext;

import com.boco.RW_Excel.excel.Workbook;
import com.boco.RW_Excel.excel.write.Label;
import com.boco.RW_Excel.excel.write.WritableCellFormat;
import com.boco.RW_Excel.excel.write.WritableFont;
import com.boco.RW_Excel.excel.write.WritableSheet;
import com.boco.RW_Excel.excel.write.WritableWorkbook;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.dao.hibernate.TawSystemDictTypeDaoHibernate;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.appops.model.IPnrPartnerAppOpsDept;
import com.boco.eoms.partner.appops.model.IPnrPartnerAppOpsUser;
import com.boco.eoms.partner.appops.model.PnrAppOpsUserModel;
import com.boco.eoms.partner.appops.model.StatisticsAppopsUserBySpecaillty;
import com.boco.eoms.partner.appops.model.StatisticsByCompanyAndDept;
import com.boco.eoms.partner.appops.model.StatisticsBySpecialityAndDept;
import com.boco.eoms.partner.appops.service.PartnerAppopsDeptService;
import com.boco.eoms.partner.appops.service.PartnerAppopsUserService;
import com.boco.eoms.partner.appops.webapp.form.IPnrPartnerAppOpsUserForm;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * 
  * @author wangyue
  * @ClassName: PartnerAppOpsUserAction
  * @Copyright 亿阳信通
  * @date Sep 24, 2014 1:49:12 PM
  * @description 运维人员管理Action
 */
public class PartnerAppOpsUserAction extends BaseAction{
	SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	/**
	 * 逻辑删除人员和相关联的信息
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
		PartnerAppopsUserService appopsUserService = (PartnerAppopsUserService)getBean("pnrAppopsUserService");
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!id.equals("")){
//			appopsUserService.deletePartnerUserInfoAndSkillInfo(id);//删除人员和相关联的信息
			IPnrPartnerAppOpsUser user=appopsUserService.find(id);
        	user.setIsDelete("1");
        	appopsUserService.save(user);
		}
		String[] ids = request.getParameterValues("checkbox11");
        if(ids!=null){
	        for(int i=0;i<ids.length;i++){
//	        	appopsUserService.deletePartnerUserInfoAndSkillInfo(ids[i]);//删除人员和相关联的信息
				IPnrPartnerAppOpsUser user=appopsUserService.find(ids[i]);
	        	user.setIsDelete("1");
	        	appopsUserService.save(user);
//	        	appopsUserService.insertPartnerUserToTawSystemUser(user, null);
	        }
        }   
        return mapping.findForward("success");
	}
	/**
	 * 点击人员管理,跳转到查询运维人员页面
	  * @author wangyue
	  * @title: searchAppOpsUser
	  * @date Sep 24, 2014 1:55:01 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward searchAppOpsUser(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");

		String pageIndexName = new org.displaytag.util.ParamEncoder("partnerUserList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		PartnerAppopsUserService appopsUserService = (PartnerAppopsUserService)getBean("pnrAppopsUserService");
				
		String whereStr = " where 1=1";
	
		//组装查询条件
		if(request.getParameter("nameSearch")!=null&&!request.getParameter("nameSearch").equals("")){
			whereStr += " and userName like '%"+request.getParameter("nameSearch")+"%'";
			request.setAttribute("nameSearch", request.getParameter("nameSearch"));

		}
	
		if(request.getParameter("prov")!=null&&!request.getParameter("prov").equals("")){
			whereStr += " and team = '"+request.getParameter("prov")+"'";
			request.setAttribute("prov", request.getParameter("prov"));
		}
		
		if(request.getParameter("mobilePhoneSearch")!=null&&!request.getParameter("mobilePhoneSearch").equals("")){
			whereStr += " and phone like '%"+request.getParameter("mobilePhoneSearch")+"%'";
			request.setAttribute("mobilePhoneSearch", request.getParameter("mobilePhoneSearch"));
		}
		if(request.getParameter("emailSearch")!=null&&!request.getParameter("emailSearch").equals("")){
			whereStr += " and email like '%"+request.getParameter("emailSearch")+"%'";
			request.setAttribute("emailSearch", request.getParameter("emailSearch"));
		}
		//不是管理员则只能查看自己部门的人力信息
		String hasRightForDelAndAdd ="";
		if(!sessionForm.getUserid().equals("admin")){
			PartnerAppopsDeptService partnerDeptMgr = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");

			String userDeptId = sessionForm.getDeptid();
			if (!"".equals(userDeptId)) {/**/
				/*先判断是移动公司人员还是代维公司人员*/
				List<IPnrPartnerAppOpsDept> list0=partnerDeptMgr.getPartnerDepts("and deptMagId='"+userDeptId+"' and substr(deptMagId,1,3)!='"+com.boco.eoms.partner.process.util.CommonUtils.startDeptMagId+"'");
				if(userDeptId.length()>5){
					hasRightForDelAndAdd="0";
				}else{
					hasRightForDelAndAdd="1";
					
				}
				if (list0.size()!=0&&list0!=null) {//不等于0表示是代维公司的
					/*代维公司人员只能浏览本公司代维公司员工的权利*/
//					hasRightForDelAndAdd="0";
					whereStr += " and deptId like '"+userDeptId+"%'";//代维公司权限限定
				}else {
					/*移动公司人员拥有删除、修改、增加本区域代维公司员工的权利*/
//					hasRightForDelAndAdd="1";
					ITawSystemDeptManager deptManager=(ITawSystemDeptManager)getBean("ItawSystemDeptManager");
					TawSystemDept dept=deptManager.getDeptinfobydeptid(userDeptId,"0");
					if (dept!=null) {
						whereStr += " and areaId like '"+StaticMethod.null2String(dept.getAreaid())+"%'";/*区域权限限定*/
					}
				}
			}
		}
		//人力信息管理员才能批量删除 2009-7-29
		String hasRightForDel = "1";
		request.setAttribute("hasRightForDel", hasRightForDel);
				
		Map map = (Map) appopsUserService.getPartnerUsers(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("partnerUserList", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);

		return mapping.findForward("list");
	}
	/**
	 * 跳转到添加人员页面
	  * @author wangyue
	  * @title: toAddPage
	  * @date Sep 24, 2014 2:05:16 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward toAddPage(ActionMapping mapping ,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
        String id = request.getParameter("id");
        if(id!=null && !id.equals("")){
        	IPnrPartnerAppOpsUser user=null;
    		PartnerAppopsUserService appopsUserService = (PartnerAppopsUserService)getBean("pnrAppopsUserService");
    		user = appopsUserService.find(id);
    		request.setAttribute("iPnrPartnerAppOpsUser", user);
        }

		return mapping.findForward("addUser");
	}
	
	public ActionForward addAppOpsUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		PartnerAppopsUserService appopsUserService = (PartnerAppopsUserService)getBean("pnrAppopsUserService");
		
		IPnrPartnerAppOpsUser appopsUser = null;
	    String id = request.getParameter("id");
		if(id!=null && !"".equals(id)){
			appopsUser = appopsUserService.find(id);
		}else{
			
			appopsUser = new IPnrPartnerAppOpsUser();
		}
		
		IPnrPartnerAppOpsUser appops = getAppOpsUserFromRequest(request,appopsUser);
//		IPnrPartnerAppOpsUserForm partnerUserForm = (IPnrPartnerAppOpsUserForm)form;
//		IPnrPartnerAppOpsUser appops = (IPnrPartnerAppOpsUser) convert(partnerUserForm);
		appops.setIsDelete("0");
		//判断必须填写项是否都填写--start
		boolean flag = true;//judgeIsNull(appops);
		//判断结束--end
		//true 添加员工信息,false 回显不添加
		String forward ="";
		if(flag){
			forward = "success";
		}else{
			forward= "addUser";
		}
	
		appopsUserService.save(appops);
		
		return searchAppOpsUser(mapping,form,request,response);
	}
	/**
	 * 批量导入
	  * @author wangyue
	  * @title: importAppopsUser
	  * @date Sep 26, 2014 7:05:05 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward importAppopsUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("欢迎进入批量导入功能");
		response.setCharacterEncoding("utf-8");
		IPnrPartnerAppOpsUserForm uploadUserForm = (IPnrPartnerAppOpsUserForm)form;
		FormFile formFile = uploadUserForm.getImportFile();
		PrintWriter writer = null;
			try{
				PartnerAppopsUserService appopsUserService = (PartnerAppopsUserService)getBean("pnrAppopsUserService");
				writer = response.getWriter();
				ImportResult result = appopsUserService.importFromFile(formFile, request);
				if (result.getResultCode().equals("200")) {
					writer.write(
						new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "true")
							.put("msg", "ok")
							.put("infor", "导入成功,共计导入"+result.getImportCount()+"条记录").build()));
				}
			}catch(Exception e){
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
	 * 获得页面传来的值，返回一个运维人员实体类对象
	  * @author wangyue
	  * @title: getAppOpsUserFromRequest
	  * @date Sep 25, 2014 11:20:13 AM
	  * @param request
	  * @return IPnrPartnerAppOpsUser
	 */
	public IPnrPartnerAppOpsUser getAppOpsUserFromRequest(HttpServletRequest request, IPnrPartnerAppOpsUser appopsUser){
	
		
		//分公司
		String company = request.getParameter("company");
			   	appopsUser.setCompany(company);
		//人员编号
		String userNumber = request.getParameter("userNumber");
				appopsUser.setUserNumber(userNumber);
		//姓名
		String userName = request.getParameter("userName");
				appopsUser.setUserName(userName);
		//性别
		String userSex = request.getParameter("userSex");
				appopsUser.setUserSex(userSex);
		//出生年份
		String userBorth = request.getParameter("userBorth");
				appopsUser.setUserBorth(userBorth);
		//年龄
		String userAge = request.getParameter("userAge");
		int age = 0;
			if(userAge!=null && !"".equals(userAge.trim())){
			age = Integer.parseInt(userAge);
		}
				appopsUser.setUserAge(age);
		//固定电话
		String telephone = request.getParameter("telephone");
				appopsUser.setTelephone(telephone);
		//手机
		String phone = request.getParameter("phone");
				appopsUser.setPhone(phone);
		//邮箱
		String email = request.getParameter("email");
				appopsUser.setEmail(email);
		//开始工作时间
		String workTime = request.getParameter("workTime");
	
				
		appopsUser.setWorkTime(workTime);

		//工龄
		String workNumber = request.getParameter("workNumber");
		int workNum = 0;
		if(workNumber!=null && !"".equals(workNumber.trim())){
			workNum = Integer.parseInt(workNumber);
		}
		appopsUser.setWorkNumber(workNum);
		//入司时间
		String companyTime = request.getParameter("companyTime");
	
		appopsUser.setCompanyTime(companyTime);
			
		//司龄
		String companyNumber = request.getParameter("companyNumber");
		int companyNum = 0;
		if(companyNumber!=null && !"".equals(companyNumber.trim())){
			companyNum = Integer.parseInt(companyNumber);
		}
				appopsUser.setCompanyNumber(companyNum);
		//从事维护工作时间
		String appopsWorkTime = request.getParameter("appopsWorkTime");
		 appopsUser.setAppopsWorkTime(appopsWorkTime);
			
		//从事维护工作时限
		String appopsNumber = request.getParameter("appopsNumber");
		int appopsNum = 0;
		if(appopsNumber!=null && !"".equals(appopsNumber.trim())){
			appopsNum = Integer.parseInt(appopsNumber);
		}
				appopsUser.setAppopsNumber(appopsNum);
		//最高学历
		String highestDegree = request.getParameter("highestDegree");
				appopsUser.setHighestDegree(highestDegree);
		//最高学历毕业院校
		String school = request.getParameter("school");
				appopsUser.setSchool(school);
		//第一学位
		String firstDegree = request.getParameter("firstDegree");
				appopsUser.setFirstDegree(firstDegree);
		//第一学位专业
		String firstSpecialty = request.getParameter("firstSpecialty");
				appopsUser.setFirstSpecialty(firstSpecialty);
		//第二学位
		String secondDegree = request.getParameter("secondDegree");
				appopsUser.setSecondDegree(secondDegree);
		//第二学位专业
		String secondSpecialty = request.getParameter("secondSpecialty");
				appopsUser.setSecondSpecialty(secondSpecialty);
		//其它证书
		String certificate = request.getParameter("certificate");
				appopsUser.setCertificate(certificate);
		//员工类别
		String workSort = request.getParameter("workSort");
				appopsUser.setWorkSort(workSort);
		//职称
		String jobTitle = request.getParameter("jobTitle");
				appopsUser.setJobTitle(jobTitle);
		//用工类别
		String useSort = request.getParameter("useSort");
				appopsUser.setUseSort(useSort);
		//岗位职称
		String jobLevel = request.getParameter("jobLevel");
				appopsUser.setJobLevel(jobLevel);
		//管理层级 
		String managerLevel = request.getParameter("managerLevel");
				appopsUser.setManagerLevel(managerLevel);
		//所在部门
		String dept = request.getParameter("dept");
				appopsUser.setDept(dept);
				
		//所属部门编码
		PartnerAppopsUserService appopsUserService = (PartnerAppopsUserService)getBean("pnrAppopsUserService");	
		String deptCode=appopsUserService.getDeptCodeFromDeptId(dept);
		appopsUser.setDeptCode(deptCode);
		
		//所在班组
		String team = request.getParameter("team");
				appopsUser.setTeam(team);
	    //--add--14-10-11 为了权限增加地市
		PartnerAppopsDeptService partnerDeptMgr = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
		IPnrPartnerAppOpsDept teamDept =partnerDeptMgr.getPartnerDept(team);
		IPnrPartnerAppOpsDept deptEntity =partnerDeptMgr.getPartnerDept(dept);
		
		if(teamDept!=null){
			appopsUser.setTeamName(teamDept.getName());
			appopsUser.setAreaId(teamDept.getCountyId());
		}
		if(deptEntity!=null){
			appopsUser.setDeptName(deptEntity.getName());
			
		}	
		
		//--end
		
		//专业类别
		String specialty = request.getParameter("specialty");
				appopsUser.setSpecialty(specialty);
		//工作岗位--专职
		String operatingPostZ = request.getParameter("operatingPostZ");
		appopsUser.setOperatingPostZ(operatingPostZ);
		//工作岗位--兼职
		String operatingPostJ= request.getParameter("operatingPostJ");
		appopsUser.setOperatingPostJ(operatingPostJ);
		
		//工作岗位--兼职名称
		String operatingPostJName=request.getParameter("operatingPostJName");
		appopsUser.setOperatingPostJName(operatingPostJName);
//		String[] operatingPostJs = request.getParameterValues("operatingPostJ");
//		String operatingPostJ = "";
//		
//		for(int i=0;i<operatingPostJs.length;i++){
//			if(!"".equals(operatingPostJ)){
//				operatingPostJ +=",";
//			}
//			
//			operatingPostJ +=operatingPostJs[i];
//		}
		//--add--14-10-11 为导出存兼职
//		String operatingPostJName="";
//		operatingPostJName =CommonUtils.getId2NameString(operatingPostJ, 1, ",");//要是导出用的话,可以考虑按照#分割?
//		appopsUser.setOperatingPostJName(operatingPostJName);
		//--add -end
		
		//工作职责描述
		String workDescribe = request.getParameter("workDescribe");
		appopsUser.setWorkDescribe(workDescribe);
		return appopsUser;
	}
	
	/**
	 * 根据专业类别统计运维人员人数
	  * @author wangyue
	  * @title: statisticsAppopsUserBySpeciallty
	  * @date Sep 28, 2014 5:53:29 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception ActionForward
	 */
	public ActionForward  statisticsAppopsUserBySpeciallty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("欢迎进入根据专业类别统计运维人员人数");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		
		/*---------------获取检索条件start------------------*/
		PnrAppOpsUserModel pnrAppOpsUserModel =new PnrAppOpsUserModel();
		
		String[] company =request.getParameterValues("company_check");//分公司
		String userName =request.getParameter("userName");//姓名
		String userSex =request.getParameter("userSex");//性别
		String[] userBorth =request.getParameterValues("userBorth_check");//出生年月
		String phone =request.getParameter("phone");//手机
		String email =request.getParameter("email");//email
		String[] workTime =request.getParameterValues("workTime_check");//参加工作时间
		String[] companyTime =request.getParameterValues("companyTime_check");//入司时间
		String[] appopsWorkTime =request.getParameterValues("appopsWorkTime_check");//从事维护工作时间
		String[] highestDegree =request.getParameterValues("highestDegree_check");//最高学历
		String school =request.getParameter("school");//最高学历毕业院校
		String[] workSort =request.getParameterValues("workSort_check");//员工类别
		String[] jobTitle =request.getParameterValues("jobTitle_check");//职称
		String[] useSort =request.getParameterValues("useSort_check");//用工类别
		String[] jobLevel =request.getParameterValues("jobLevel_check");//岗位职级
		String[] managerLevel =request.getParameterValues("managerLevel_check");//管理层级
		String workDescribe =request.getParameter("workDescribe");//工作职责描述
		String organizationNo =request.getParameter("organizationNo");//组织编码
		String deptId =request.getParameter("dept");//所在部门
		String deptName =request.getParameter("deptName");//所在部门名称
		String team =request.getParameter("team");//所在班组
		String specialty1 =request.getParameter("specialty");//工作岗位-专职
		String operatingPostZ =request.getParameter("operatingPostZ");//工作岗位-专职
		String operatingPostJ =request.getParameter("operatingPostJ");//工作岗位-兼职
		String operatingPostJName =request.getParameter("operatingPostJName");//工作岗位-兼职名称
		
		
		
		pnrAppOpsUserModel.setCompany(company);
		pnrAppOpsUserModel.setUserName(userName);
		pnrAppOpsUserModel.setUserSex(userSex);
		pnrAppOpsUserModel.setUserBorth(userBorth);
		pnrAppOpsUserModel.setPhone(phone);
		pnrAppOpsUserModel.setEmail(email);
		pnrAppOpsUserModel.setWorkTime(workTime);
		pnrAppOpsUserModel.setCompanyTime(companyTime);
		pnrAppOpsUserModel.setAppopsWorkTime(appopsWorkTime);
		pnrAppOpsUserModel.setHighestDegree(highestDegree);
		pnrAppOpsUserModel.setSchool(school);
		pnrAppOpsUserModel.setWorkSort(workSort);
		pnrAppOpsUserModel.setJobTitle(jobTitle);
		pnrAppOpsUserModel.setUseSort(useSort);
		pnrAppOpsUserModel.setJobLevel(jobLevel);
		pnrAppOpsUserModel.setManagerLevel(managerLevel);
		pnrAppOpsUserModel.setWorkDescribe(workDescribe);
		pnrAppOpsUserModel.setDept(deptId);
		pnrAppOpsUserModel.setTeam(team);
		pnrAppOpsUserModel.setSpecialty(specialty1);
		pnrAppOpsUserModel.setOperatingPostZ(operatingPostZ);
		pnrAppOpsUserModel.setOperatingPostJ(operatingPostJ);
		
		
		
		/*------------------------获取检索条件结束----------------------------------------*/
		PnrAppOpsUserModel appOpsUser=new PnrAppOpsUserModel();
		String flag = request.getParameter("flag");
		System.out.println("flag1="+flag);
		String city = request.getParameter("city");
		System.out.println("city1="+city);
		//统计2、统计3、统计4查询条件
		IPnrPartnerAppOpsUser appopsUser = new IPnrPartnerAppOpsUser();
		
		String workerState = request.getParameter("workerState");
		System.out.println("workerState1="+workerState);
		String highAgree = request.getParameter("highAgree");
		System.out.println("highAgree1="+highAgree);
		String zhicheng = request.getParameter("zhicheng");
		System.out.println("zhicheng1="+zhicheng);
		//String operatingPostZ = request.getParameter("operatingPostZ");
		System.out.println("operatingPostZ1="+operatingPostZ);
		//String operatingPostJ = request.getParameter("operatingPostJ");
		System.out.println("operatingPostJ1="+operatingPostJ);
		appopsUser.setUseSort(workerState);
		appopsUser.setHighestDegree(highAgree);
		appopsUser.setJobTitle(zhicheng);
		appopsUser.setOperatingPostZ(operatingPostZ);
		appopsUser.setOperatingPostJ(operatingPostJ);
		
		//获取当前登陆人所在地市id 判断登陆人是省或地市或县区
		String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionform.getDeptid());
		
		
		PartnerAppopsUserService appopsUserService = (PartnerAppopsUserService)getBean("pnrAppopsUserService");
		String forward = "statisticsByspecailly";
		if("1".equals(flag)){
			String searchCity = request.getParameter("searchCity");
			String searchSpecialty = request.getParameter("searchSpecialty");
			List<StatisticsAppopsUserBySpecaillty> list = appopsUserService.getStatisticsBySpecaillty(userId,areaid,searchCity,searchSpecialty,pnrAppOpsUserModel);
			request.setAttribute("taskList", list);
			/*--------------返回检索条件------------------*/
			request.setAttribute("company", company);
			request.setAttribute("userName", userName);
			request.setAttribute("userSex", userSex);
			request.setAttribute("userBorth",userBorth);
			request.setAttribute("phone",phone);
			request.setAttribute("email",email);
			request.setAttribute("workTime",workTime);
			request.setAttribute("companyTime",companyTime);
			request.setAttribute("appopsWorkTime",appopsWorkTime);
			request.setAttribute("highestDegree",highestDegree);
			request.setAttribute("school",school);
			request.setAttribute("workSort",workSort);
			request.setAttribute("jobTitle",jobTitle);
			request.setAttribute("useSort",useSort);
			request.setAttribute("jobTitle",jobTitle);
			request.setAttribute("jobLevel",jobLevel);
			request.setAttribute("managerLevel",managerLevel);
			request.setAttribute("workDescribe",workDescribe);
			request.setAttribute("organizationNo",organizationNo);
			request.setAttribute("deptId",deptId);
			request.setAttribute("deptName",deptName);
			request.setAttribute("team",team);
			request.setAttribute("specialty",specialty1);
			request.setAttribute("operatingPostZ",operatingPostZ);
			request.setAttribute("operatingPostJ",operatingPostJ);
			request.setAttribute("operatingPostJName",operatingPostJName);
		}else if("2".equals(flag)){
			
			//String[] str = new String[]{"运行维护部","网络维护中心","网络优化中心","公众客户客响中心","集团客户客响中心","资源管理中心","线路维护中心","省级平台维护中心","国际海缆监控中心","维护中心"};
			String[] str = new String[]{"运行维护部","网络维护中心","网络优化中心","公众客户响应中心","集团客户响应中心","资源管理中心","线路维护中心","省级平台维护中心","国际海缆监控中心","维护中心"};
			List<StatisticsByCompanyAndDept> list = appopsUserService.getStatisticsByCompanyAndDept(userId,areaid,str,appopsUser,pnrAppOpsUserModel);
			request.setAttribute("taskList", list);
			
			/*--------------返回检索条件------------------*/
			request.setAttribute("company", company);
			request.setAttribute("userName", userName);
			request.setAttribute("userSex", userSex);
			request.setAttribute("userBorth",userBorth);
			request.setAttribute("phone",phone);
			request.setAttribute("email",email);
			request.setAttribute("workTime",workTime);
			request.setAttribute("companyTime",companyTime);
			request.setAttribute("appopsWorkTime",appopsWorkTime);
			request.setAttribute("highestDegree",highestDegree);
			request.setAttribute("school",school);
			request.setAttribute("workSort",workSort);
			request.setAttribute("jobTitle",jobTitle);
			request.setAttribute("useSort",useSort);
			request.setAttribute("jobTitle",jobTitle);
			request.setAttribute("jobLevel",jobLevel);
			request.setAttribute("managerLevel",managerLevel);
			request.setAttribute("workDescribe",workDescribe);
			request.setAttribute("organizationNo",organizationNo);
			request.setAttribute("deptId",deptId);
			request.setAttribute("deptName",deptName);
			request.setAttribute("team",team);
			request.setAttribute("specialty",specialty1);
			request.setAttribute("operatingPostZ",operatingPostZ);
			request.setAttribute("operatingPostJ",operatingPostJ);
			request.setAttribute("operatingPostJName",operatingPostJName);
			forward = "statisticsByCompanyAndDept";
		}else if("3".equals(flag)){
		//	String[] str = new String[]{"运行维护部","网络维护中心","网络优化中心","公众客户客响中心","集团客户客响中心","资源管理中心","线路维护中心","省级平台维护中心","国际海缆监控中心","维护中心"};
			String[] str = new String[]{"dddd-邹城市","dddd-任城区","dddd-市中区","dddd-曲阜市","dddd-邹城市","dddd-任城区","dddd-市中区","dddd-曲阜市","dddd-市中区","dddd-曲阜市"};
		//	String[] specialty = new String[]{"主任（副主任）","移动核心网维护","无线网维护","传输网维护","数据网维护","接入网维护"
		//			,"业务平台维护","交换网维护","线路维护","IT系统维护","网络服务","动力环境及配套","资源管理","网络信息安全管理"
		//			,"网络监控","机线员","维护管理","现场综合维护"};
			//String[] specialty = new String[]{"主任（副主任）","移动核心网维护","无线网维护","传输网维护","主任（副主任）","移动核心网维护","无线网维护","传输网维护","主任（副主任）","移动核心网维护","无线网维护","传输网维护","主任（副主任）","移动核心网维护","无线网维护","传输网维护","无线网维护","传输网维护"};
			String[] specialty = new String[]{"主任（副主任）","移动核心网维护","无线网络维护","传输网维护","数据网维护","接入网维护","业务平台维护","交换网维护","线路维护","IT系统维护","网络服务","动力环境及配套","资源管理","网络信息安全管理","网络监控","机线员","维护管理","现场综合维护"};
			List<StatisticsBySpecialityAndDept> list = appopsUserService.getStatisticsBySpecialtyAndDept(userId,areaid,str,specialty,appopsUser,pnrAppOpsUserModel);
			request.setAttribute("taskList", list);
			/*--------------返回检索条件------------------*/
			request.setAttribute("company", company);
			request.setAttribute("userName", userName);
			request.setAttribute("userSex", userSex);
			request.setAttribute("userBorth",userBorth);
			request.setAttribute("phone",phone);
			request.setAttribute("email",email);
			request.setAttribute("workTime",workTime);
			request.setAttribute("companyTime",companyTime);
			request.setAttribute("appopsWorkTime",appopsWorkTime);
			request.setAttribute("highestDegree",highestDegree);
			request.setAttribute("school",school);
			request.setAttribute("workSort",workSort);
			request.setAttribute("jobTitle",jobTitle);
			request.setAttribute("useSort",useSort);
			request.setAttribute("jobTitle",jobTitle);
			request.setAttribute("jobLevel",jobLevel);
			request.setAttribute("managerLevel",managerLevel);
			request.setAttribute("workDescribe",workDescribe);
			request.setAttribute("organizationNo",organizationNo);
			request.setAttribute("deptId",deptId);
			request.setAttribute("deptName",deptName);
			request.setAttribute("team",team);
			request.setAttribute("specialty",specialty1);
			request.setAttribute("operatingPostZ",operatingPostZ);
			request.setAttribute("operatingPostJ",operatingPostJ);
			request.setAttribute("operatingPostJName",operatingPostJName);
			forward = "statisticsBySpecialtyAndDept";
		}else if("4".equals(flag)){
		//	String[] specialty = new String[]{"主任（副主任）","移动核心网维护","无线网维护","传输网维护","数据网维护","接入网维护"
			//		,"业务平台维护","交换网维护","线路维护","IT系统维护","网络服务","动力环境及配套","资源管理","网络信息安全管理"
				//	,"网络监控","机线员","维护管理","现场综合维护"};
			String[] specialty = new String[]{"主任（副主任）","移动核心网维护","无线网络维护","传输网维护","主任（副主任）","移动核心网维护","无线网维护","传输网维护","主任（副主任）","移动核心网维护","无线网维护","传输网维护","主任（副主任）","移动核心网维护","无线网维护","传输网维护","无线网维护","传输网维护"};
		//	List<StatisticsBySpecialityAndDept> list = appopsUserService.getStatisticsByCityAndSpecialty(userId,"运维人员", city, specialty,appopsUser);
			List<StatisticsBySpecialityAndDept> list = appopsUserService.getStatisticsByCityAndSpecialty(userId,"hhh", city, specialty,appopsUser);
			request.setAttribute("taskList", list);
			request.setAttribute("city", city);
			forward = "statisticsByCityAndSpecialty";
		}
		return mapping.findForward(forward);
	}
	
	/**
	 * 导出统计2
	 */
	public ActionForward exportStatisticsByCompanyAndDept(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		
		
		
		//统计2、统计3、统计4查询条件
		IPnrPartnerAppOpsUser appopsUser = new IPnrPartnerAppOpsUser();
//		String workerState = request.getParameter("workerState");
//		System.out.println("workerState="+workerState);
//		String highAgree = request.getParameter("highAgree");
//		System.out.println("highAgree="+highAgree);
//		String zhicheng = request.getParameter("zhicheng");
//		System.out.println("zhicheng="+zhicheng);
//		String operatingPostZ = request.getParameter("operatingPostZ");
//		System.out.println("operatingPostZ="+operatingPostZ);
//		String operatingPostJ = request.getParameter("operatingPostJ");
//		System.out.println("operatingPostJ="+operatingPostJ);
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
//		appopsUser.setUseSort(workerState);
//		appopsUser.setHighestDegree(highAgree);
//		appopsUser.setJobTitle(zhicheng);
//		appopsUser.setOperatingPostZ(operatingPostZ);
//		appopsUser.setOperatingPostJ(operatingPostJ);
		
		
		/*---------------新获取检索条件start------------------*/
		PnrAppOpsUserModel pnrAppOpsUserModel =new PnrAppOpsUserModel();
		
		String[] company =request.getParameterValues("company_check");//分公司
		String userName =request.getParameter("userName");//姓名
		String userSex =request.getParameter("userSex");//性别
		String[] userBorth =request.getParameterValues("userBorth_check");//出生年月
		String phone =request.getParameter("phone");//手机
		String email =request.getParameter("email");//email
		String[] workTime =request.getParameterValues("workTime_check");//参加工作时间
		String[] companyTime =request.getParameterValues("companyTime_check");//入司时间
		String[] appopsWorkTime =request.getParameterValues("appopsWorkTime_check");//从事维护工作时间
		String[] highestDegree =request.getParameterValues("highestDegree_check");//最高学历
		String school =request.getParameter("school");//最高学历毕业院校
		String[] workSort =request.getParameterValues("workSort_check");//员工类别
		String[] jobTitle =request.getParameterValues("jobTitle_check");//职称
		String[] useSort =request.getParameterValues("useSort_check");//用工类别
		String[] jobLevel =request.getParameterValues("jobLevel_check");//岗位职级
		String[] managerLevel =request.getParameterValues("managerLevel_check");//管理层级
		String workDescribe =request.getParameter("workDescribe");//工作职责描述
		String deptId =request.getParameter("dept");//所在部门
		String team =request.getParameter("team");//所在班组
		String specialty1 =request.getParameter("specialty");//工作岗位-专职
		String operatingPostZ =request.getParameter("operatingPostZ");//工作岗位-专职
		String operatingPostJ =request.getParameter("operatingPostJ");//工作岗位-兼职
		
		pnrAppOpsUserModel.setCompany(company);
		pnrAppOpsUserModel.setUserName(userName);
		pnrAppOpsUserModel.setUserSex(userSex);
		pnrAppOpsUserModel.setUserBorth(userBorth);
		pnrAppOpsUserModel.setPhone(phone);
		pnrAppOpsUserModel.setEmail(email);
		pnrAppOpsUserModel.setWorkTime(workTime);
		pnrAppOpsUserModel.setCompanyTime(companyTime);
		pnrAppOpsUserModel.setAppopsWorkTime(appopsWorkTime);
		pnrAppOpsUserModel.setHighestDegree(highestDegree);
		pnrAppOpsUserModel.setSchool(school);
		pnrAppOpsUserModel.setWorkSort(workSort);
		pnrAppOpsUserModel.setJobTitle(jobTitle);
		pnrAppOpsUserModel.setUseSort(useSort);
		pnrAppOpsUserModel.setJobLevel(jobLevel);
		pnrAppOpsUserModel.setManagerLevel(managerLevel);
		pnrAppOpsUserModel.setWorkDescribe(workDescribe);
		pnrAppOpsUserModel.setDept(deptId);
		pnrAppOpsUserModel.setTeam(team);
		pnrAppOpsUserModel.setSpecialty(specialty1);
		pnrAppOpsUserModel.setOperatingPostZ(operatingPostZ);
		pnrAppOpsUserModel.setOperatingPostJ(operatingPostJ);
		
		
		//获取当前登陆人所在地市id 判断登陆人是省或地市或县区
		String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionform.getDeptid());
		/*------------------------获取检索条件结束----------------------------------------*/
		
		
		
		System.out.println("进到里面了吗？");
		String[] str = new String[] { "运行维护部", "网络维护中心", "网络优化中心", "公众客户客响中心",
				"集团客户客响中心", "资源管理中心", "线路维护中心", "省级平台维护中心", "国际海缆监控中心", "维护中心" };
//		String[] str = new String[]{"dddd-邹城市","dddd-任城区","dddd-市中区","dddd-曲阜市","dddd-邹城市","dddd-任城区","dddd-市中区","dddd-曲阜市","dddd-市中区","dddd-曲阜市"};
		PartnerAppopsUserService appopsUserService = (PartnerAppopsUserService) getBean("pnrAppopsUserService");
		TawSystemDictTypeDaoHibernate dao = (TawSystemDictTypeDaoHibernate) ApplicationContextHolder
		.getInstance().getBean("ItawSystemDictTypeDao");
		
		List<StatisticsByCompanyAndDept> list = appopsUserService
				.getStatisticsByCompanyAndDept(userId,areaid,str,appopsUser,pnrAppOpsUserModel);

		// 导出
		String title = "统计2";
		String sheetName = "统计2";
		// 初始化Excel文件
		OutputStream os = response.getOutputStream();// 取得输出流
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
			title = new String(title.getBytes("UTF-8"), "ISO8859-1");// firefox浏览器
		}
		if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
			title = URLEncoder.encode(title, "UTF-8");// IE浏览器
		}
		response.reset();// 清空输出流
		// 设定输出文件头,该方法有两个参数，分别表示应答头的名字和值。
		response.setHeader("Content-disposition", "attachment;filename=\""
				+ title + ".xls\"");
		// 定义输出类型
		response.setContentType("application/msexcel");
		WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
		WritableSheet wsheet = wbook.createSheet(sheetName, 0); // sheet名称
		// 标题列
		String[] head = new String[] { "分公司", "运行维护部", "网络维护中心", "网络优化中心",
				"公众客户客响中心", "集团客户客响中心", "资源管理中心", "线路维护中心", "省级平台维护中心",
				"国际海缆监控中心", "县（区）维护中心", "合计" };

		WritableFont font = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.BOLD, false);
		WritableCellFormat cellFormat = new WritableCellFormat(font);
		for (int i = 0; i < head.length; i++) {
			Label label = new Label(i, 0, head[i]);
			label.setCellFormat(cellFormat);
			wsheet.addCell(label);
		}

		Iterator<StatisticsByCompanyAndDept> iterator = list.iterator();
		int row=1;
		while(iterator.hasNext()){
			int n=0;
			StatisticsByCompanyAndDept model = iterator.next();
			if(!model.getCompany().equals("合计")){
				wsheet.addCell(new Label(n++,row,dao.id2Name(model.getCompany())));
				}else{
					wsheet.addCell(new Label(n++,row,"合计"));
				}
			wsheet.addCell(new Label(n++,row,model.getDept_one()));
			wsheet.addCell(new Label(n++,row,model.getDept_two()));
			wsheet.addCell(new Label(n++,row,model.getDept_three()));
			wsheet.addCell(new Label(n++,row,model.getDept_forth()));
			wsheet.addCell(new Label(n++,row,model.getDept_five()));
			wsheet.addCell(new Label(n++,row,model.getDept_sex()));
			wsheet.addCell(new Label(n++,row,model.getDept_seven()));
			wsheet.addCell(new Label(n++,row,model.getDept_ength()));
			wsheet.addCell(new Label(n++,row,model.getDept_nine()));
			wsheet.addCell(new Label(n++,row,model.getDept_ten()));
			wsheet.addCell(new Label(n++,row,model.getRowSum().toString()));			
			
			row++;
		}

		wbook.write(); // 写入文件
		wbook.close();
		os.close(); // 关闭流

		return null;
	}
	
	
	
	
	/**
	 * "增加运维人员信息.xls"导入模板下载
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String rootPath = request.getRealPath("/partner/processExcelModel");
		String fileName = "增加运维人员信息.xls";
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
		return null;
	}
	
	/**
	 * 校验手机号码唯一
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkMobile(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("---进方法了吗?------");
		String mobile = request.getParameter("mobile").trim();
		String userId = request.getParameter("userId");
		PartnerAppopsUserService appopsUserService = (PartnerAppopsUserService) getBean("pnrAppopsUserService");
		int check=0;
		try {
			 check = appopsUserService.getMobileCheck(mobile,userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	//	int check = appopsUserService.getMobileCheck(mobile,userId);  
		System.out.println("---check------"+check);
		try {
			PrintWriter writer = response.getWriter();
			writer.print(check);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取统计列表所需要检索的字典值 返回json串
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getDictValue(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		System.out.println("====== 进入方法了麽 ======");
		PrintWriter out=response.getWriter();
		
		ITawSystemDictTypeManager objDictManager = (ITawSystemDictTypeManager) ApplicationContextHolder
		.getInstance().getBean("ItawSystemDictTypeManager");
		
		//获取分公司
		String fgsDictId=request.getParameter("fgsDictId");
		List<TawSystemDictType> list=objDictManager.getTawSystemDictTypeByParentdictId(fgsDictId);
		JSONArray json = new JSONArray();
        for(TawSystemDictType a : list){
        	JSONObject jo = new JSONObject();
            jo.put("key","fgs");//分公司
            jo.put("id", a.getId());
            jo.put("dictId", a.getDictId());
            jo.put("dictName", a.getDictName());
            jo.put("parentDictId", a.getParentDictId());
            json.put(jo);
        }
        //获取性别
        String sexDictId=request.getParameter("sexDictId");
        List<TawSystemDictType> list1=objDictManager.getTawSystemDictTypeByParentdictId(sexDictId);		
        for(TawSystemDictType a : list1){
        	JSONObject jo = new JSONObject();
            jo.put("key","sex");//性别
            jo.put("id", a.getId());
            jo.put("dictId", a.getDictId());
            jo.put("dictName", a.getDictName());
            jo.put("parentDictId", a.getParentDictId());
            json.put(jo);
        }
        
      //获取最高学历
        String highestDegreeDictId=request.getParameter("highestDegreeDictId");
        List<TawSystemDictType> list2=objDictManager.getTawSystemDictTypeByParentdictId(highestDegreeDictId);		
        for(TawSystemDictType a : list2){
        	JSONObject jo = new JSONObject();
            jo.put("key","highestDegree");
            jo.put("id", a.getId());
            jo.put("dictId", a.getDictId());
            jo.put("dictName", a.getDictName());
            jo.put("parentDictId", a.getParentDictId());
            json.put(jo);
        }
      //获取员工类别
        String workSortDictId=request.getParameter("workSortDictId");
        List<TawSystemDictType> list3=objDictManager.getTawSystemDictTypeByParentdictId(workSortDictId);		
        for(TawSystemDictType a : list3){
        	JSONObject jo = new JSONObject();
            jo.put("key","workSort");
            jo.put("id", a.getId());
            jo.put("dictId", a.getDictId());
            jo.put("dictName", a.getDictName());
            jo.put("parentDictId", a.getParentDictId());
            json.put(jo);
        }
        
      //获取职称
        String jobTitleDictId=request.getParameter("jobTitleDictId");
        List<TawSystemDictType> list4=objDictManager.getTawSystemDictTypeByParentdictId(jobTitleDictId);		
        for(TawSystemDictType a : list4){
        	JSONObject jo = new JSONObject();
            jo.put("key","jobTitle");
            jo.put("id", a.getId());
            jo.put("dictId", a.getDictId());
            jo.put("dictName", a.getDictName());
            jo.put("parentDictId", a.getParentDictId());
            json.put(jo);
        }
        
      //获取用工类别
        String useSortDictId=request.getParameter("useSortDictId");
        List<TawSystemDictType> list5=objDictManager.getTawSystemDictTypeByParentdictId(useSortDictId);		
        for(TawSystemDictType a : list5){
        	JSONObject jo = new JSONObject();
            jo.put("key","useSort");
            jo.put("id", a.getId());
            jo.put("dictId", a.getDictId());
            jo.put("dictName", a.getDictName());
            jo.put("parentDictId", a.getParentDictId());
            json.put(jo);
        }
        
      //获取岗位职级
        String jobLevelDictId=request.getParameter("jobLevelDictId");
        List<TawSystemDictType> list6=objDictManager.getTawSystemDictTypeByParentdictId(jobLevelDictId);		
        for(TawSystemDictType a : list6){
        	JSONObject jo = new JSONObject();
            jo.put("key","jobLevel");
            jo.put("id", a.getId());
            jo.put("dictId", a.getDictId());
            jo.put("dictName", a.getDictName());
            jo.put("parentDictId", a.getParentDictId());
            json.put(jo);
        }
      //获取管理层级
        String managerLevelDictId=request.getParameter("managerLevelDictId");
        List<TawSystemDictType> list7=objDictManager.getTawSystemDictTypeByParentdictId(managerLevelDictId);		
        for(TawSystemDictType a : list7){
        	JSONObject jo = new JSONObject();
            jo.put("key","managerLevel");
            jo.put("id", a.getId());
            jo.put("dictId", a.getDictId());
            jo.put("dictName", a.getDictName());
            jo.put("parentDictId", a.getParentDictId());
            json.put(jo);
        }
        System.out.println(json.toString());
		out.write(json.toString());
		
		return null;
	}
	
	/**
	 * 根据部门id获取部门名称
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getDeptName(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception{
		System.out.println("====== 进入方法了麽 ======");
		PrintWriter out=response.getWriter();
		String deptId=request.getParameter("deptId");
		System.out.println(deptId);
		PartnerAppopsDeptService partnerAppopsDeptService = (PartnerAppopsDeptService) ApplicationContextHolder
		.getInstance().getBean("pnrAppopsDeptService");
		
		IPnrPartnerAppOpsDept partnerDept = partnerAppopsDeptService
		.getPartnerDept(deptId);
		String deptName =partnerDept.getName();
		out.print(deptName);
		return null;
	}	
	
}
