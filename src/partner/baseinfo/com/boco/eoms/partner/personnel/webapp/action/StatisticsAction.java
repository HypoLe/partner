package com.boco.eoms.partner.personnel.webapp.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import utils.PartnerPrivUtils;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.home.util.HomeHelper;
import com.boco.eoms.partner.personnel.util.PnrUserLostStatisticTableHelper;
import com.boco.eoms.partner.personnel.util.PnrUserStatisticsTableHelper;
import com.boco.eoms.partner.personnel.util.fusionchart.service.BuildFusionChartXml;
import com.boco.eoms.partner.property.util.StatisticsResultExport;
import com.boco.eoms.partner.resourceInfo.util.ResourceInfoUtils;
import com.boco.eoms.partner.statistically.pojo.TdObjModel;
	/**
 * <p>
 * Title:人员信息统计
 * </p>
 * <p>
 * Description:人员信息统计
 * </p>
 * <p>
 * Jul 10, 2012 10:56:52 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class StatisticsAction extends BaseAction {
	
	/**
	 * 根据 page参数 取得对应页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getJsp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
				/*//代维专业字典数据
				ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
				request.setAttribute("specialtyList", mgr.getDictSonsByDictid("11225"));
			return mapping.findForward(MyUtil.getString(request.getParameter("page")));*/
			
			String isPartner=StaticMethod.null2String(request.getParameter("isPartner"));//0代维 ；1自维
	    	request.setAttribute("isPartner", isPartner);
			String where=request.getParameter("goToPage");
			if ("cert".equals(where)) {
				return mapping.findForward("certStatistics");
			}else if ("dwinfo".equals(where)) {
				return mapping.findForward("dwInfoStatistics");
			}else if ("configRate".equals(where)) {
				/**
		    	 * 根据当前省ID，列出所有地市,省份
		    	 */
		    	request.setAttribute("provinceList", StaticMethod.getUserRootProvinceAreaList());
		    	request.setAttribute("city", StaticMethod.getUserCityAreaList());
				return mapping.findForward("pnrUserConfigRateStatistics");
			}else if ("degree".equals(where)) {
				return mapping.findForward("pnrUserDegreeStatistics");
			}else if ("lostRate".equals(where)) {
				return mapping.findForward("pnrUserLostRateStatistics");
			}else if ("pnrUser".equals(where)) {
				return mapping.findForward("pnrUserStatistics");
			}else {
				return mapping.findForward("fail");
			}
		
	}
	/**
	 * 
	* @Title: certStatistics 
	* @Description: 人员证书统计
	* @param 
	* @Time:Dec 3, 2012-9:03:56 AM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward certStatistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		//rowString 字段和数据库里的字段名字完全相同
		String checkedIdsStr=StaticMethod.null2String(request.getParameter("checkedIds"));
		//checkedString带有标识以及公式的字段
		String checkedValuesStr=StaticMethod.null2String(request.getParameter("statisticsItems"));
		String checkedIds[]=checkedIdsStr.split(";");
		//数值转化为字符串，作为sql的search条件
		String checkedValues[]=checkedValuesStr.split(";");
		String searchStr="";
		String group="";
		for (int i = 0; i < checkedIds.length; i++) {
			if(!checkedIds[i].contains("_certtype")){
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
				if(!group.equals("")){
					group+=",";
				}
				group+=checkedValues[i];
			}else {
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
			}
			if (i!=checkedIds.length-1) {
				searchStr+=",";
			}
		}
		//获取where条件值
		String area=StaticMethod.nullObject2String(request.getParameter("area_id"),"");
		String maintainCompany=StaticMethod.nullObject2String(request.getParameter("maintainCompany_id"),"");
		if (!"".equals(maintainCompany)&&maintainCompany.length()>20) {
			PartnerDeptMgr deptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			PartnerDept dept=deptMgr.getPartnerDept(maintainCompany);
			if (dept!=null) {
				String deptid=StaticMethod.null2String(dept.getDeptMagId());
				maintainCompany=deptid;
			}
		}
		String maintenanceMajor=StaticMethod.nullObject2String(request.getParameter("maintenanceMajor"),"");
		String exportFlag=StaticMethod.nullObject2String(request.getParameter("exportFlag"),"");
		String whereStr=" ";
		if(!"".equals(area)){
			whereStr+=" and u.area_id='"+area+"'";
		}
		if (!"".equals(maintainCompany)) {
			whereStr+=" and u.dept_id='"+maintainCompany+"'";
		}
		if (!"".equals(maintenanceMajor)) {
			whereStr+=" and dw.professional='"+maintenanceMajor+"'";
		}
		String otherConditions="  group by "+group+" order by "+group;
		if (!searchStr.contains("TypeLike")) {
			otherConditions="";
		}
		String statisticsSql="select "+searchStr+",count(p.id) as count from partner_certificate p,pnr_user u,partner_dwinfo dw  where " +
				"u.deleted='0' and dw.isdelete='0' and p.isdelete='0' and u.user_id=p.workerid and p.workerid=dw.workerid "+whereStr+otherConditions;
		String sql="select "+searchStr+" from panter_studyexperience p,pnr_user u,partner_dwinfo dw  where " +
		"u.deleted='0' and dw.isdelete='0' and p.isdelete='0' and u.user_id=p.workerid and p.workerid=dw.workerid "+whereStr+otherConditions;
		StringBuffer items=new StringBuffer();
		List<String> headList = new ArrayList<String>();
		//[area, maintenanceCompany, maintenanceMajor, apparatusName, apparatusType, apparatusProperty, apparatusStatus]
		for (int i = 0; i < checkedIds.length; i++) {
			if ("area_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("所属区域");
			}else if ("dept_idTypeLikeDept".equals(checkedIds[i])) {
				headList.add("代维组织");
			}else if ("professionalTypeLikedict".equals(checkedIds[i])) {
				headList.add("代维专业");
			}else if ("eg_certtype".equals(checkedIds[i])) {
				headList.add("电工证");
			}else if ("dg_certtype".equals(checkedIds[i])) {
				headList.add("登高证");
			}else if ("zl_certtype".equals(checkedIds[i])) {
				headList.add("制冷证");
			}else if ("js_certtype".equals(checkedIds[i])) {
				headList.add("驾驶证");
			}else if ("other_certtype".equals(checkedIds[i])) {
				headList.add("移动认证资质");
			}
		}
		//headList.add("总数");
		List<List<TdObjModel>> tempList = PnrUserStatisticsTableHelper.verticalGrowp(checkedIds,statisticsSql,"/personnel/statistics.do?method=certStatisticsSearchInto");
		if (searchStr.contains("_certtype")) {
			//不需要数据钻取，去掉count字段；
			for (List<TdObjModel>  list : tempList) {
				list.remove(list.size()-1);
			}
		}else {
			headList.add("总数");
		}
		request.setAttribute("areaStringLike", area);
		request.setAttribute("maintainCompanyStringLike", maintainCompany);
		request.setAttribute("major", maintenanceMajor);
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkedList", checkedIdsStr);
		if (!"".equals(exportFlag)&&exportFlag.equals("2")) {
			//执行导出
			String fileName="人员证书情况统计";
			StatisticsResultExport.exportStatisticsResultToXLSFile(true, tempList, headList, fileName, response,request);
			return null;
		}else {
			//跳转到统计页面
			request.setAttribute("hasSend", "ok");
			return mapping.findForward("certStatistics");
		}
	}
	/**
	 * 
	* @Title: certStatisticsSearchInto 
	* @Description: 人员证书统计钻取
	* @param 
	* @Time:Dec 3, 2012-9:06:11 AM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward  certStatisticsSearchInto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		CommonSpringJdbcServiceImpl csjs=(CommonSpringJdbcServiceImpl)getBean("commonSpringJdbcService");
		request.setCharacterEncoding("utf-8");
		String area=StaticMethod.null2String(request.getParameter("area_idtypelikearea"));
		String company=StaticMethod.null2String(request.getParameter("dept_idtypelikedept"));
		String professional=StaticMethod.null2String(request.getParameter("professionaltypelikedict"));
		String certype=StaticMethod.null2String(request.getParameter("type1"));
		String whereStr="";
		if (!"".equals(area)) {
			whereStr+=" and u.area_id='"+area+"'";
		}
		if (!"".equals(company)) {
			whereStr+=" and u.dept_id='"+company+"'";
		}
		if (!"".equals(professional)) {
			whereStr+=" and d.professional='"+professional+"'";
		}
		if (!"".equals(certype)) {
			whereStr+=" and c.type1='"+certype+"'";
		}
		int pageIndex = CommonUtils.getFirstResultOfDisplayTag(request, "list");
		int pageSize = 15;
		int skip = pageIndex*pageSize;
		String searchSql= "d.id as duuid,c.id as cuuid,u.name,u.user_id,u.area_id,u.dept_id,d.professional,c.type1 " +
				" from pnr_user u, partner_dwinfo d, partner_certificate c " +
				" where u.user_id = d.workerid and u.user_id = c.workerid and u.deleted='0' and d.isdelete='0' and c.isdelete='0'  "+whereStr;
		List<ListOrderedMap> list2=csjs.queryForList(CommonSqlHelper.formatPageSql(searchSql, skip, pageSize));
//		List<Map> list = new ArrayList<Map>();
//		for (int i = 0; i < list2.size(); i++) {
//			ListOrderedMap listOrderedMap=list2.get(i);
//			Map map = new HashMap();
//			for(Object object : listOrderedMap.keySet()) {
//				//判断连接数据库类型，如果是oracle则将查询结果的列名转为小写。
//				if (hqlDialect.equals("org.hibernate.dialect.OracleDialect")){
//                    map.put(object.toString().toLowerCase(), listOrderedMap.get(object));
//				}else if (hqlDialect.equals("org.hibernate.dialect.InformixDialect")) {
//					map.put(object, listOrderedMap.get(object));
//				}
//			}
//			list.add(map);
//		}
		int resultSize=list2.size();
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("resultSize", CommonSqlHelper.getResultSize(searchSql));
		request.setAttribute("list", list2);
		return mapping.findForward("certStatisticsSearchInto");
	}
	/**
	 * 
	* @Title: dwInfoStatistics 
	* @Description: 人员技能统计
	* @param 
	* @Time:Dec 3, 2012-9:04:11 AM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward  dwInfoStatistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		//rowString 字段和数据库里的字段名字完全相同
		String checkedIdsStr=StaticMethod.null2String(request.getParameter("checkedIds"));
		//checkedString带有标识以及公式的字段
		String checkedValuesStr=StaticMethod.null2String(request.getParameter("statisticsItems"));
		String checkedIds[]=checkedIdsStr.split(";");
		//数值转化为字符串，作为sql的search条件
		String checkedValues[]=checkedValuesStr.split(";");
		String searchStr="";
		String group="";
		for (int i = 0; i < checkedIds.length; i++) {
			if(!checkedIds[i].contains("_skilllevel")){
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
				if(!group.equals("")){
					group+=",";
				}
				group+=checkedValues[i];
			}else {
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
			}
			if (i!=checkedIds.length-1) {
				searchStr+=",";
			}
		}
		//获取where条件值
		String area=StaticMethod.nullObject2String(request.getParameter("area_id"),"");
		String maintainCompany=StaticMethod.nullObject2String(request.getParameter("maintainCompany_id"),"");
		if (!"".equals(maintainCompany)&&maintainCompany.length()>20) {
			PartnerDeptMgr deptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			PartnerDept dept=deptMgr.getPartnerDept(maintainCompany);
			if (dept!=null) {
				String deptid=StaticMethod.null2String(dept.getDeptMagId());
				maintainCompany=deptid;
			}
		}
		String maintenanceMajor=StaticMethod.nullObject2String(request.getParameter("maintenanceMajor"),"");
		String exportFlag=StaticMethod.nullObject2String(request.getParameter("exportFlag"),"");
		String whereStr=" ";
		if(!"".equals(area)){
			whereStr+=" and u.area_id='"+area+"'";
		}
		if (!"".equals(maintainCompany)) {
			whereStr+=" and u.dept_id='"+maintainCompany+"'";
		}
		if (!"".equals(maintenanceMajor)) {
			whereStr+=" and p.professional='"+maintenanceMajor+"'";
		}
		String otherConditions="  group by "+group+" order by "+group;
		if (!searchStr.contains("TypeLike")) {
			otherConditions="";
		}
		String statisticsSql="select "+searchStr+",count(p.id) as count from pnr_user u,partner_dwinfo p  where " +
				"u.deleted='0' and p.isdelete='0' and u.user_id=p.workerid  "+whereStr+otherConditions;
		//String sql="select "+searchStr+" from panter_studyexperience p,pnr_user u,partner_dwinfo dw  where " +
		//"u.deleted='0' and dw.isdelete='0' and p.isdelete='0' and u.user_id=p.workerid and p.workerid=dw.workerid "+whereStr+"  group by "+group+" order by "+group;
		StringBuffer items=new StringBuffer();
		List<String> headList = new ArrayList<String>();
		//[area, maintenanceCompany, maintenanceMajor, apparatusName, apparatusType, apparatusProperty, apparatusStatus]
		for (int i = 0; i < checkedIds.length; i++) {
			if ("area_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("所属区域");
			}else if ("dept_idTypeLikeDept".equals(checkedIds[i])) {
				headList.add("代维组织");
			}else if ("professionalTypeLikedict".equals(checkedIds[i])) {
				headList.add("代维专业");
			}else if ("junior_skilllevel".equals(checkedIds[i])) {
				headList.add("初级");
			}else if ("middle_skilllevel".equals(checkedIds[i])) {
				headList.add("中级");
			}else if ("advanced_skilllevel".equals(checkedIds[i])) {
				headList.add("高级");
			}
		}
		//headList.add("总数");
		List<List<TdObjModel>> tempList = PnrUserStatisticsTableHelper.verticalGrowp(checkedIds,statisticsSql,"/personnel/statistics.do?method=dwInfoStatisticsSearchInto");
		if (searchStr.contains("_skilllevel")) {//当包含具体的某一项时不需要总数这个项目，当没有具体某一项时统计总数
			//不需要数据钻取，去掉count字段；
			for (List<TdObjModel>  list : tempList) {
				list.remove(list.size()-1);
			}
		}else {
			headList.add("总数");
		}
		request.setAttribute("areaStringLike", area);
		request.setAttribute("maintainCompanyStringLike", maintainCompany);
		request.setAttribute("major", maintenanceMajor);
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkedList", checkedIdsStr);
		if (!"".equals(exportFlag)&&exportFlag.equals("2")) {
			//执行导出
			String fileName="人员技能等级统计";
			StatisticsResultExport.exportStatisticsResultToXLSFile(true, tempList, headList, fileName, response,request);
			return null;
		}else {
			//跳转到统计页面
			request.setAttribute("hasSend", "ok");
			return mapping.findForward("dwInfoStatistics");
		}
	}
	/**
	 * 
	 *@Description 人员信息多维统计，统计人员的基本信息，技能信息，最近一次培训经历，证书信息，奖励信息，上一次工作经历信息
	 *@date Apr 23, 2013 5:58:57 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param mapping
	 *@param form
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	public ActionForward  pnrUserStatistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String checkedIdsStr=StaticMethod.null2String(request.getParameter("checkedIds"));
		String checkedValuesStr=StaticMethod.null2String(request.getParameter("statisticsItems"));
		String checkedIds[]=checkedIdsStr.split(";");
		String checkedValues[]=checkedValuesStr.split(";");
		boolean isBaseinfo=false;
		boolean isWorkinfo=false;
		boolean isDwinfo=false;
		boolean isCertinfo=false;
		boolean isRewardinfo=false;
		boolean isStudyinfo=false;
		boolean isPxinfo=false;
		List headList=new ArrayList();//导出Excel的标题;
		headList.add("区域");
		headList.add("代维公司");
		headList.add("姓名");
		headList.add("性别");
		headList.add("身份证号");
		headList.add("用户ID");
		for (String item : checkedValues) {
			if ("isBaseinfo".equals(item)) {
				isBaseinfo=true;
				headList.add("员工编码");
				headList.add("籍贯");
				headList.add("民族");
				headList.add("出生日期");
				headList.add("年龄");
				headList.add("集团短号");
				headList.add("Email");
				headList.add("参加工作时间");
				headList.add("移动电话");
				headList.add("在职状态");
				headList.add("黑名单标识");
			}else if ("isWorkinfo".equals(item)) {
				isWorkinfo=true;
				headList.add("工作经历");
			}else if ("isDwinfo".equals(item)) {
				isDwinfo=true;
				headList.add("技能信息");
			}else if ("isCertinfo".equals(item)) {
				isCertinfo=true;
				headList.add("证书信息");
			}else if ("isRewardinfo".equals(item)) {
				isRewardinfo=true;
				headList.add("曾获奖励名称");
			}else if ("isStudyinfo".equals(item)) {
				isStudyinfo=true;
				headList.add("毕业院校");
				headList.add("所学专业");
				headList.add("学历情况");
			}else if ("isPxinfo".equals(item)) {
				isPxinfo=true;
				headList.add("最近培训内容");
			}
		}
		String whereStr="";
		String area=StaticMethod.null2String(request.getParameter("areaId"));
		String btnValue=StaticMethod.null2String(request.getParameter("btnValue"));
		String maintainCompany=StaticMethod.null2String(request.getParameter("companyId"));
		String companyId=StaticMethod.null2String(request.getParameter("companyId"));
		String companyName=StaticMethod.null2String(request.getParameter("companyName"));
		String areaName=StaticMethod.null2String(request.getParameter("areaName"));
		String companyDeptId=ResourceInfoUtils.deptUUidToDeptId(companyId);
		String exportFlag=StaticMethod.nullObject2String(request.getParameter("exportFlag"),"");
		
		String workCompany=StaticMethod.null2String(request.getParameter("workCompany"));//工作单位的筛选条件
		String skill=StaticMethod.null2String(request.getParameter("skill"));//技能名称得筛选条件
		String skilllevelStr=StaticMethod.null2String(request.getParameter("skilllevel"));//技能等级的晒选条件
		String certType=StaticMethod.null2String(request.getParameter("certType"));//证书名称得筛选条件
		String rewardName=StaticMethod.null2String(request.getParameter("rewardName"));//曾获奖励名称得筛选条件;
		String pxContent=StaticMethod.null2String(request.getParameter("pxContent"));//曾获奖励名称得筛选条件;
		String isPartner=StaticMethod.null2String(request.getParameter("isPartner"));//0代维 ；1自维
		
		boolean export=false;
		if ("2".equals(exportFlag)) {
			export=true;
		}
		Map map=PartnerPrivUtils.userIsPersonnel(request);
		String userType=map.get("isPersonnel").toString();
		String userLimited="";
		/*根据当前登录用户权限控制查询结果*/
		if ("y".equals(userType)) {//代维人员
			whereStr+=" and dept_id like '"+map.get("deptMagId").toString()+"%' ";
			userLimited+=" and deptid like '"+map.get("deptMagId").toString()+"%' ";
		}else if ("n".equals(userType)) {//移动人员
			whereStr+=" and area_id like '"+map.get("areaId").toString()+"%' ";
			userLimited+=" and areaid like '"+map.get("areaId").toString()+"%' ";
		}
		/*	前台页面的区域和代维公司限制条件*/
		if (!"".equals(area)) {
			whereStr+=" and area_id like '"+area+"%' ";
			userLimited+=" and areaid like '"+area+"%' ";
		}
		if (!"".equals(companyDeptId)) {
			whereStr+=" and dept_id like '"+companyDeptId+"%' ";
			userLimited+=" and deptid like '"+companyDeptId+"%' ";
		}
		request.setAttribute("areaId", area);
		request.setAttribute("areaName", areaName);
		request.setAttribute("companyId", companyId);
		request.setAttribute("companyName", companyName);
		Map requestMap=request.getParameterMap();
		String clause;
		String clauseValue;
		for (Object keyObject : requestMap.keySet()) {
			clause = String.valueOf(keyObject);
			clauseValue = ((String[]) requestMap.get(clause))[0].toString();
			// 设置查询条件到request attribute中。
			request.setAttribute(clause, clauseValue);
			if (clauseValue.equals("")) {
				continue;
			} else if (clause.indexOf("StringEqual") != -1) {
				clause = clause.substring(0, clause.indexOf("StringEqual"));
				whereStr+=" and "+clause+"='"+clauseValue.trim()+"'";
			} else if (clause.indexOf("StringLike") != -1) {
				clause = clause.substring(0, clause.indexOf("StringLike"));
				whereStr+=" and "+clause+" like '%"+clauseValue.trim()+"%'";
			}else if (clause.indexOf("StringLessThan") != -1) {
				clause = clause.substring(0, clause.indexOf("StringLessThan"));
				whereStr+=" and "+clause+"<'"+clauseValue.trim()+"'";
			}else if (clause.indexOf("StringGreatThan") != -1) {
				clause = clause.substring(0, clause.indexOf("StringGreatThan"));
				whereStr+=" and "+clause+">'"+clauseValue.trim()+"'";
			}else if (clause.indexOf("StringLessOrEqual") != -1) {
				clause = clause.substring(0, clause.indexOf("StringLessOrEqual"));
				whereStr+=" and "+clause+"<='"+clauseValue.trim()+"'";
			}else if (clause.indexOf("StringGreatOrEqual") != -1) {
				clause = clause.substring(0, clause.indexOf("StringGreatOrEqual"));
				whereStr+=" and "+clause+">='"+clauseValue.trim()+"'";
			}

		}
		if(!"".equals(isPartner)){
				whereStr+=" and is_partner="+isPartner+" ";
			
		}
		String statisticsSql="select id,name,person_card_no,area_name,area_id,dept_name,userno,user_id,nativeplace,nationality,age,groupphone,graduateschool," +
				"learnspecialty,mobile_phone,email,sex,birthdey,diploma,work_time,post_state,blacklist from pnr_user where deleted='0' "
				+whereStr+" order by area_name,dept_name";
	//	System.out.println("人员信息统计sql:"+statisticsSql);
		List<ListOrderedMap> userinfoList=jdbcService.queryForList(statisticsSql);
		List<ListOrderedMap> dwinfoList=new ArrayList<ListOrderedMap>();
		List<ListOrderedMap> certinfoList=new ArrayList<ListOrderedMap>();
		List<ListOrderedMap> rewardinfoList=new ArrayList<ListOrderedMap>();
		List<ListOrderedMap> workinfoList=new ArrayList<ListOrderedMap>();
		List<ListOrderedMap> pxinfoList=new ArrayList<ListOrderedMap>();
		List<List> exportList=new ArrayList<List>();//导出的list
		/*技能信息统计*/
		if (isDwinfo) {
			String dwWhereStr="";
			if (!"".equals(skill)) {
				dwWhereStr+=" and professional='"+skill+"' ";
			}
			if (!"".equals(skilllevelStr)) {
				dwWhereStr+=" and skilllevel='"+skilllevelStr+"' ";
			}
			String dwinfoSql="select workerid,workername,professional,skilllevel from partner_dwinfo where isdelete='0' "+userLimited+dwWhereStr;//先更加区域和部门id进行一次过滤减少查询结果
			System.out.println("技能信息统计sql:"+dwinfoSql);
			dwinfoList=jdbcService.queryForList(dwinfoSql);
		}
		/*证书信息统计*/
		if (isCertinfo) {
			String certWhereStr="";
			if (!"".equals(certType)) {
				certWhereStr+=" and type1 like '%"+certType+"%' ";
			}
			String certinfoSql="select workerid,type1,level1 from partner_certificate where isdelete='0' "+userLimited+certWhereStr;
			System.out.println("证书信息统计sql:"+certinfoSql);
			certinfoList=jdbcService.queryForList(certinfoSql);
		}
		/*奖励信息统计*/
		if (isRewardinfo) {
			String rewardWhereStr="";
			if (!"".equals(rewardName)) {
				rewardWhereStr+=" and reward like '%"+rewardName+"%' ";
			}
			String rewardinfoSql="select workerid,reward from panter_reward where isdelete='0' "+userLimited+rewardWhereStr;
			System.out.println("奖励信息统计sql:"+rewardinfoSql);
			rewardinfoList=jdbcService.queryForList(rewardinfoSql);
		}
		/*工作经历信息统计:统计人员上一份工作单位和职务*/
		if (isWorkinfo) {
			String workWhereStr="";
			if (!"".equals(workCompany)) {
				workWhereStr+=" and company like '%"+workCompany+"%' ";
			}
			String workinfoSql="select workerid,company,duty,max(leavetime) as leavetime from partner_workexperience where isdelete='0' "+userLimited +workWhereStr+
			"group by " +
			" workerid,company,duty ";
			System.out.println("工作经历信息统计sql:"+workinfoSql);
			workinfoList=jdbcService.queryForList(workinfoSql);
		}
		/*培训信息统计:列出最近一次培训内容*/
		if (isPxinfo) {
			String pxWhereStr="";
			if (!"".equals(pxContent)) {
				pxWhereStr+=" and content like '%"+pxContent+"%'";
			}
			String pxinfoSql="select workerid,content,max(endtime) as endtime from panter_pxexperience where isdelete='0' " +userLimited+pxWhereStr+
			"group by " +
			" workerid,content ";
			System.out.println("培训经历信息统计sql:"+pxinfoSql);
			pxinfoList=jdbcService.queryForList(pxinfoSql);
		}
		/*对统计结果进行处理*/
		List<List> resultList=new ArrayList<List>();
		ID2NameService service = (ID2NameService) ApplicationContextHolder	.getInstance().getBean("ID2NameGetServiceCatch");
		List sonList,exportSonList;
		for (ListOrderedMap user : userinfoList) {
			if (!"".equals(certType)) {//如果证书查询框有查询值但是没有结果则直接中断处理结果;
				if (certinfoList.size()==0) {
					break;
				}
			}
			if (!"".equals(skill)||!"".equals(skilllevelStr)) {//如果技能和等级有查询值但是没有结果则直接中断处理结果;
				if (dwinfoList.size()==0) {
					break;
				}
			}
			if (!"".equals(rewardName)) {//如果证书查询框有查询值但是没有结果则直接中断处理结果;
				if (rewardinfoList.size()==0) {
					break;
				}
			}
			if (!"".equals(workCompany)) {//如果证书技能和等级有查询值但是没有结果则直接中断处理结果;
				if (workinfoList.size()==0) {
					break;
				}
			}
			if (!"".equals(pxContent)) {//如果证书技能和等级有查询值但是没有结果则直接中断处理结果;
				if (pxinfoList.size()==0) {
					break;
				}
			}
			sonList=new ArrayList();
			exportSonList=new ArrayList();
			String userid=user.get("user_id").toString();//通过人员的userid与其它各种技能信息进行关联;
			/*处理人员工作经历信息：一对一关系 begin*/
			String userWorkinfo="";
			for (ListOrderedMap info : workinfoList) {
				String workerid=StaticMethod.nullObject2String(info.get("workerid"));
				if(userid.equals(workerid)){
					String userWorkCompany=StaticMethod.nullObject2String(info.get("company"));
					String userWorkDuty=StaticMethod.nullObject2String(info.get("duty"));
					if ("".equals(userWorkDuty)) {
						userWorkDuty="无";
					}
					userWorkinfo=userWorkCompany+"|"+userWorkDuty;
					break;//因为只有一条结果，所以找到后就终止。
				}
			}
			if ("".equals(userWorkinfo)&&!"".equals(workCompany)) {
			//对工作经历再做一次过滤,如果没有该userid的工作经历信息且查询条件不为空，则该人员被滤除;
				continue;
			}else {
				userWorkinfo="无";
			}
			/*处理人员工作经历信息：一对一关系 end*/
			/*处理人员技能信息：一对多关系 begin*/
			String userDwinfo="";
			for (ListOrderedMap info : dwinfoList) {
				String workerid=StaticMethod.nullObject2String(info.get("workerid"));
				if(userid.equals(workerid)){
					String professional=StaticMethod.nullObject2String(info.get("professional"));
					String skilllevel=StaticMethod.nullObject2String(info.get("skilllevel"));
					userDwinfo+=service.id2Name(professional, "tawSystemDictTypeDao")+"|"+service.id2Name(skilllevel, "tawSystemDictTypeDao")+"<br>";
				}
			}
			if ("".equals(userDwinfo)&&(!"".equals(skill)||!"".equals(skilllevelStr))) {
				//对技能信息再做一次过滤,如果没有该userid的技能信息且查询条件不为空，则该人员被滤除;
				continue;
			}else {
				userDwinfo="无";
			}
			/*处理人员技能信息：一对多关系 end*/
			/*处理人员证书信息：一对多关系 begin*/
			String userCertinfo="";
			for (ListOrderedMap info : certinfoList) {
				String workerid=StaticMethod.nullObject2String(info.get("workerid"));
				if(userid.equals(workerid)){
					String type1=StaticMethod.nullObject2String(info.get("type1"));
					String level1=StaticMethod.nullObject2String(info.get("level1"));
					if ("".equals(level1)) {
						level1="无";
					}
					userCertinfo+=type1+"|"+level1+"<br>";
				}
			}
			if ("".equals(userCertinfo)) {
				if (!"".equals(certType)) {
					//对证书信息再做一次过滤,如果没有该userid的证书信息且查询条件不为空，则该人员被滤除;
					continue;
				}
				userCertinfo="无";
			}
			/*处理人员证书信息：一对多关系end*/
			/*处理人员奖励信息：一对多关系 begin*/
			String userRewardinfo="";
			for (ListOrderedMap info : rewardinfoList) {
				String workerid=StaticMethod.nullObject2String(info.get("workerid"));
				if(userid.equals(workerid)){
					String reward=StaticMethod.nullObject2String(info.get("reward"));
					userRewardinfo+=reward+"<br>";
				}
			}
			if ("".equals(userRewardinfo)&&!"".equals(rewardName)) {
				//对奖励信息再做一次过滤,如果没有该userid的奖励信息且查询条件不为空，则该人员被滤除;
				continue;
			}else{
				userRewardinfo="无";
			}
			/*处理人员奖励信息：一对多关系 end*/
			/*处理人员培训经历信息：一对一关系 begin*/
			String userPxinfo="";
			for (ListOrderedMap info : pxinfoList) {
				String workerid=StaticMethod.nullObject2String(info.get("workerid"));
				if(userid.equals(workerid)){
					userPxinfo=StaticMethod.nullObject2String(info.get("content"));
					break;//因为只有一条结果，所以找到后就终止。
				}
			}
			if ("".equals(userPxinfo)&&!"".equals(pxContent)) {
				//对培训经历信息再做一次过滤,如果没有该userid的培训经历信息且查询条件不为空，则该人员被滤除;
				continue;
			}else {
				userPxinfo="无";
			}
			/*处理人员培训经历信息：一对一关系 end*/
			
			String sexStr=StaticMethod.nullObject2String(user.get("sex"));
			if ("112020201".equals(sexStr)) {
				sexStr="男";
			}else if ("112020202".equals(sexStr)) {
				sexStr="女";
			}else {
				sexStr="--";
			}
			String nationality=StaticMethod.nullObject2String(user.get("nationality"));//人员民族字典
			String postState=StaticMethod.nullObject2String(user.get("post_state"));//人员在职状态字典
			nationality=service.id2Name(nationality, "tawSystemDictTypeDao");//人员民族名称
			postState=service.id2Name(postState, "tawSystemDictTypeDao");//人员在职状态名称
			String diploma=StaticMethod.nullObject2String(user.get("diploma"));//人员学历字典
			diploma=service.id2Name(diploma, "tawSystemDictTypeDao");//人员学历名称
			String black=StaticMethod.nullObject2String(user.get("blacklist"));//黑名单标识
			if ("0".equals(black)) {
				black="否";
			}else {
				black="是";
			}
			if (export) {
				exportSonList.add(user.get("area_name"));//第1列人员所在区域，测试add(null)是否报异常？
				exportSonList.add(user.get("dept_name"));//第2列人员所在代维公司
				exportSonList.add(user.get("name"));//第3列人员姓名
				exportSonList.add(sexStr);//第4列人员性别
				exportSonList.add(user.get("person_card_no"));//第5列人员身份证号
				exportSonList.add(userid);//第6列人员用户ID
				if (isBaseinfo) {
					exportSonList.add(user.get("userno").toString());//第7列人员员工编码
					exportSonList.add(user.get("nativeplace"));//第8列人员籍贯
					exportSonList.add(nationality);//第9列人员民族
					exportSonList.add(user.get("birthdey"));//第10列人员出生日期
					exportSonList.add(user.get("age"));//第11列人员年龄
					exportSonList.add(user.get("groupphone"));//第12列人员集团短号
					exportSonList.add(user.get("email"));//第13列人员Email
					exportSonList.add(user.get("work_time"));//第14列人员参加工作时间
					exportSonList.add(user.get("mobile_phone"));//第15列人员手机号码
					exportSonList.add(postState);//第16列人员在职状态
					exportSonList.add(black);//第17列人员黑名单标识
				}
				if (isWorkinfo) {
					exportSonList.add(userWorkinfo);//第19列人员最近一份工作经历和职务
				}
				if (isDwinfo) {
					exportSonList.add(userDwinfo.replace("<br>", ","));//第20列人员技能信息
				}
				if (isCertinfo) {
					exportSonList.add(userCertinfo.replace("<br>", ","));//第21列人员证书信息
				}
				if (isRewardinfo) {
					exportSonList.add(userRewardinfo.replace("<br>", ","));//第22列人员奖励信息
				}
				if (isStudyinfo) {
					exportSonList.add(user.get("graduateschool"));//第23列人员毕业院校
					exportSonList.add(user.get("learnspecialty"));//第24列人员所学专业
					exportSonList.add(service.id2Name(diploma, "tawSystemDictTypeDao"));//第25列人员学历
				}
				if (export&&isPxinfo) {
					exportSonList.add(userPxinfo);//第26列人员最近一次培训内容
				}
				exportList.add(exportSonList);
			}else {
				sonList.add(user.get("area_name"));//第1列人员所在区域，测试add(null)是否报异常？
				sonList.add(user.get("dept_name"));//第2列人员所在代维公司
				sonList.add(user.get("name"));//第3列人员姓名
				sonList.add(sexStr);//第4列人员性别
				sonList.add(user.get("person_card_no"));//第5列人员身份证号
				sonList.add(userid);//第6列人员用户ID
				sonList.add(user.get("userno").toString());//第7列人员员工编码
				sonList.add(user.get("nativeplace"));//第8列人员籍贯
				sonList.add(nationality);//第9列人员民族
				sonList.add(user.get("birthdey"));//第10列人员出生日期
				sonList.add(user.get("age"));//第11列人员年龄
				sonList.add(user.get("groupphone"));//第12列人员集团短号
				sonList.add(user.get("email"));//第13列人员Email
				sonList.add(user.get("work_time"));//第14列人员参加工作时间
				sonList.add(user.get("mobile_phone"));//第15列人员联系电话
				sonList.add(postState);//第16列人员在职状态
				sonList.add(black);//第17列人员黑名单标识
				sonList.add(userWorkinfo);//第18列人员最近一份工作经历和职务
				sonList.add(userDwinfo);//第19列人员技能信息
				sonList.add(userCertinfo);//第20列人员证书信息
				sonList.add(userRewardinfo);//第21列人员奖励信息
				sonList.add(user.get("graduateschool"));//第22列人员毕业院校
				sonList.add(user.get("learnspecialty"));//第23列人员所学专业
				sonList.add(diploma);//第24列人员学历
				sonList.add(userPxinfo);//第25列人员最近一次培训内容
				resultList.add(sonList);
			}
		}
		if (export) {//执行导出
			List<List<TdObjModel>> tempList =HomeHelper.verticalGrowp(2, 0, 0, exportList);
			String fileName="人员信息多维统计";
			StatisticsResultExport.exportStatisticsResultToXLSFile(true, tempList, headList, fileName, response,request);
			return null;
		}else {
			List<List<TdObjModel>> tempList =HomeHelper.verticalGrowp(2, 0, 0, resultList);
			request.setAttribute("tableList", tempList);
			request.setAttribute("btnValue", btnValue);
			request.setAttribute("checkedIdsStr", checkedIdsStr);
			request.setAttribute("checkedIdsDisableVal", request.getParameter("checkedIdsDisableVal"));//获取checkbox的disable的属性
			request.setAttribute("isBaseinfo", isBaseinfo);
			request.setAttribute("isWorkinfo", isWorkinfo);
			request.setAttribute("isDwinfo", isDwinfo);
			request.setAttribute("isCertinfo", isCertinfo);
			request.setAttribute("isRewardinfo", isRewardinfo);
			request.setAttribute("isStudyinfo", isStudyinfo);
			request.setAttribute("isPxinfo", isPxinfo);
			request.setAttribute("statistics", "2");
			//跳转到统计页面
			request.setAttribute("hasSend", "ok");
			return mapping.findForward("pnrUserStatistics");
		}
	}
	/**
	 * 
	 *@Description 人员信息钻取
	 *@date Apr 26, 2013 2:52:27 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param mapping
	 *@param form
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	public ActionForward  pnrUserSearchInto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String  userid=request.getParameter("userid");
		List<ListOrderedMap> userinfoList=new ArrayList<ListOrderedMap>();
		List<ListOrderedMap> dwinfoList=new ArrayList<ListOrderedMap>();
		List<ListOrderedMap> certinfoList=new ArrayList<ListOrderedMap>();
		List<ListOrderedMap> rewardinfoList=new ArrayList<ListOrderedMap>();
		List<ListOrderedMap> workinfoList=new ArrayList<ListOrderedMap>();
		List<ListOrderedMap> pxinfoList=new ArrayList<ListOrderedMap>();
		String userSql="select id,name,person_card_no,area_name,area_id,dept_name,userno,user_id,nativeplace,nationality,age,groupphone,graduateschool," +
		"learnspecialty,mobile_phone,email,sex,birthdey,diploma,work_time,post_state,blacklist from pnr_user where deleted='0' and user_id='"+userid+"'";
		String dwinfoSql="select id,sysno,professional,skilllevel,duty,accountno from partner_dwinfo" +
				" where isdelete='0' and workerid='"+userid+"' ";
		String certinfoSql="select id,validity,imagepath,sysno,type1,level1,issueorg,issuetime,codeno,memo from partner_certificate " +
				" where isdelete='0' and workerid='"+userid+"' ";
		String rewardinfoSql="select id,sysno,reward,awarddept,awardtime,memo from panter_reward" +
				" where isdelete='0' and workerid='"+userid+"' ";
		String workinfoSql="select id,sysno,entrytime,leavetime,company,duty,memo from partner_workexperience " +
				" where isdelete='0' and workerid='"+userid+"' ";
		String pxinfoSql="select id,sysno,starttime,endtime,content,score,memo,days  from panter_pxexperience " +
				" where isdelete='0' and workerid='"+userid+"' ";
		userinfoList=jdbcService.queryForList(userSql);
		workinfoList=jdbcService.queryForList(workinfoSql);
		rewardinfoList=jdbcService.queryForList(rewardinfoSql);
		certinfoList=jdbcService.queryForList(certinfoSql);
		pxinfoList=jdbcService.queryForList(pxinfoSql);
		dwinfoList=jdbcService.queryForList(dwinfoSql);
		request.setAttribute("workinfoList",workinfoList );
		request.setAttribute("rewardinfoList",rewardinfoList);
		request.setAttribute("certinfoList",certinfoList);
		request.setAttribute("pxinfoList",pxinfoList);
		request.setAttribute("dwinfoList",dwinfoList );
		request.setAttribute("userinfoList",userinfoList);
		if (userinfoList.size()==0) {
			request.setAttribute("msg", "无"+userid+"的详细信息,请检查该人员是否存在!");
			return mapping.findForward("fail");
		}
		return mapping.findForward("pnrUserStatisticsSearchInto");
	}
	/**
	 * 
	* @Title: dwInfoStatisticsSearchInto 
	* @Description: 人员技能统计钻取
	* @param 
	* @Time:Dec 3, 2012-9:06:54 AM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward  dwInfoStatisticsSearchInto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception{
		CommonSpringJdbcServiceImpl csjs=(CommonSpringJdbcServiceImpl)getBean("commonSpringJdbcService");
		request.setCharacterEncoding("utf-8");
		String area=StaticMethod.null2String(request.getParameter("area_idtypelikearea"));
		String company=StaticMethod.null2String(request.getParameter("dept_idtypelikedept"));
		String professional=StaticMethod.null2String(request.getParameter("professionaltypelikedict"));
		String certype=StaticMethod.null2String(request.getParameter("skilllevel"));
		String whereStr="";
		if (!"".equals(area)) {
			whereStr+=" and u.area_id='"+area+"'";
		}
		if (!"".equals(company)) {
			whereStr+=" and u.dept_id='"+company+"'";
		}
		if (!"".equals(professional)) {
			whereStr+=" and d.professional='"+professional+"'";
		}
		if (!"".equals(certype)) {
			whereStr+=" and d.skilllevel='"+certype+"'";
		}
		int pageIndex = CommonUtils.getFirstResultOfDisplayTag(request, "list");
		int pageSize = 15;
		int skip = pageIndex*pageSize;
		String selectSql="u.id as useruuid,d.id as duuid,u.area_id,u.dept_id,u.user_id,u.name,u.person_card_no,d.skilllevel,d.professional  " + 
				"  from pnr_user u, partner_dwinfo d  " +
				"where u.user_id = d.workerid and u.deleted = '0'  and d.isdelete = '0'" + whereStr;
		List<ListOrderedMap> list2=csjs.queryForList(CommonSqlHelper.formatPageSql(selectSql, skip, pageSize));
//		List<Map> list = new ArrayList<Map>();
//		for (int i = 0; i < list2.size(); i++) {
//			ListOrderedMap listOrderedMap=list2.get(i);
//			Map map = new HashMap();
//			for(Object object : listOrderedMap.keySet()) {
//				map.put(object, listOrderedMap.get(object));
//			}
//			list.add(map);
//		}
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("resultSize", CommonSqlHelper.getResultSize(selectSql));
		request.setAttribute("list", list2);	
		return mapping.findForward("dwInfoStatisticsSearchInto");
	}
	/**
	 * 
	* @Title: partnerUserConfigRateStatistics 
	* @Description: 代维人员配置率统计
	* @param 
	* @Time:Dec 3, 2012-9:04:28 AM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward  partnerUserConfigRateStatistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception{
    	//rowString 字段和数据库里的字段名字完全相同
		String checkedIdsStr=StaticMethod.null2String(request.getParameter("checkedIds"));
		//checkedString带有标识以及公式的字段
		String checkedValuesStr=StaticMethod.null2String(request.getParameter("statisticsItems"));
		String checkedIds[]=checkedIdsStr.split(";");
		//[add_time_y, add_time_m, province_idTypeLikeArea, city_idTypeLikeArea, country_idTypeLikeArea, company_name, standard_config, actual_config, config_rate]
		//数值转化为字符串，作为sql的search条件
		String checkedValues[]=checkedValuesStr.split(";");
		String searchStr="";
		String group="";
		for (int i = 0; i < checkedIds.length; i++) {
			if (!checkedValues[i].contains("sum(")) {
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
				if (!"".equals(group)) {
					group+=",";
				}
				group+=checkedValues[i];
			}else{
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
			}
			if (i!=checkedIds.length-1) {
				searchStr+=",";
			}
		}
		//获取where条件值
		String provinceId=StaticMethod.null2String(request.getParameter("provinceSearch"));
		String cityId=StaticMethod.null2String(request.getParameter("region"));
		String countryId=StaticMethod.null2String(request.getParameter("country"));
		String maintainCompany=StaticMethod.null2String(request.getParameter("maintainCompany"));
		String maintainCompanyId=StaticMethod.null2String(request.getParameter("maintainCompanyId"));
		String maintenanceMajor=StaticMethod.null2String(request.getParameter("maintenanceMajor"));
		String addTimeY=StaticMethod.null2String(request.getParameter("year"));
		String addTimeM=StaticMethod.null2String(request.getParameter("month"));
		String exportFlag=StaticMethod.null2String(request.getParameter("exportFlag"));
		String isPartner = StaticMethod.null2String(request.getParameter("isPartner"));//0代维 1 自维
		String whereStr=" ";
		if (!"".equals(addTimeY)) {
			whereStr+=" and add_time_y="+addTimeY;
		}
		
		if (!"".equals(addTimeM)) {
			whereStr+=" and add_time_m="+addTimeM;
		}
		if (!"".equals(provinceId)) {
			whereStr+=" and province_id='"+provinceId+"'";
		}
		if (!"".equals(cityId)) {
			whereStr+=" and city_id='"+cityId+"'";
		}
		if (!"".equals(countryId)) {
			whereStr+=" and country_id='"+countryId+"'";
		}
		if (!"".equals(maintainCompany)) {
			whereStr+=" and company_name='"+maintainCompany+"'";
		}
		if (!"".equals(maintenanceMajor)) {
			whereStr+=" and professional='"+maintenanceMajor+"'";
		}
		String otherConditions="  group by "+group+" order by "+group;
		if (!searchStr.contains("TypeLike")) {
			otherConditions="";
		}
		StringBuffer statisticsSql = new StringBuffer();
		statisticsSql.append(" select ").append(searchStr).append(" from pnr_source_config ,(select dept_mag_id,is_partner from pnr_dept) d where ");
		statisticsSql.append("  d.dept_mag_id=company_mag_id and isdeleted='0' and config_type='1240201' ");
		statisticsSql.append(" and d.is_partner=").append(isPartner);
		statisticsSql.append(whereStr).append(otherConditions);
/*		String statisticsSql="select "+searchStr+" from pnr_source_config  where " +
		"isdeleted='0'  and  config_type='1240201' "+whereStr+otherConditions;
*/		String sql=statisticsSql.toString();
		String str=searchStr;
		if (searchStr.contains("config_rate")) {
			str=searchStr.substring(0, searchStr.lastIndexOf(","));
		}
		sql="select "+str+" from pnr_source_config  where  isdeleted='0'  and  config_type='1240201' "+whereStr+otherConditions;
		StringBuffer items=new StringBuffer();
		List<String> headList = new ArrayList<String>();
		for (int i = 0; i < checkedIds.length; i++) {
			if ("add_time_y".equals(checkedIds[i])) {
				headList.add("年");
			}else if ("add_time_m".equals(checkedIds[i])) {
				headList.add("月");
			}else if ("province_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("省份");
			}else if ("city_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("地市");
			}else if ("country_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("区县");
			}else if ("company_name".equals(checkedIds[i])) {
				if(isPartner.equals("0")){
					headList.add("代维组织");
				}else if(isPartner.equals("1")){
					headList.add("自维组织");
				}
				
			}else if ("professionalTypeLikedict".equals(checkedIds[i])) {
				headList.add("代维专业");
			}else if ("standard_amout".equals(checkedIds[i])) {
				headList.add("应配人数");
				items.append("应配人数,");
			}else if ("actual_config".equals(checkedIds[i])) {
				headList.add("实际人数");
				items.append("实际人数,");
			}else if ("config_rate".equals(checkedIds[i])) {
				headList.add("到位率");
			}
		}			
		List<List<TdObjModel>> tempList = PnrUserStatisticsTableHelper.
		verticalGrowp(checkedIds,statisticsSql.toString(),"/personnel/statistics.do?method=partnerUserConfigRateStatisticsSearchInto");
		for (int i=0;i<tempList.size();i++) {
			List<TdObjModel> list=tempList.get(i);
			if(checkedIdsStr.contains("config_rate")){//如果包含到位率这一项就对到位率进行处理；
				TdObjModel rateModel=list.get(list.size()-2);//获取流失率model
				TdObjModel actualModel=list.get(list.size()-3);//获取实际人数
				TdObjModel standardModel=list.get(list.size()-4);//获取应配人数；
				double actualNum=Double.parseDouble(actualModel.getName());
				double standardNum=Double.parseDouble(standardModel.getName());
				double rate=0;
				if(standardNum!=0){
					rate=actualNum/standardNum;
				}else {
					rate=1;//不要求配置人员时此时值如何确定？
				}
				String rateStr=rate*100+"0";
				if((rateStr.length()-rateStr.indexOf("."))>3){
					rateStr=rateStr.substring(0, rateStr.indexOf(".")+3);
				}
				rateModel.setName(rateStr+"%");
			}
			list.remove(list.size()-1);//去掉最后一个统计结果
		}
		//request.setAttribute("areaStringLike", area);
		/**
    	 * 根据当前省ID，列出所有地市,省份
    	 */
    	request.setAttribute("provinceList", StaticMethod.getUserRootProvinceAreaList());
    	request.setAttribute("city", StaticMethod.getUserCityAreaList());
    	request.setAttribute("cityId", cityId);
    	request.setAttribute("provinceSearch", provinceId);
    	request.setAttribute("country", countryId);
		request.setAttribute("maintainCompany", maintainCompany);
		request.setAttribute("maintainCompanyId", maintainCompanyId);
		request.setAttribute("major", maintenanceMajor);
		request.setAttribute("year1", addTimeY);
		request.setAttribute("month1", addTimeM);
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkedList", checkedIdsStr);
		if (!"".equals(exportFlag)&&exportFlag.equals("2")) {
			//执行导出
			String fileName="人员配置比率统计";
			StatisticsResultExport.exportStatisticsResultToXLSFile(true, tempList, headList, fileName, response,request);
			return null;
		}else {
			//跳转到统计页面
			request.setAttribute("hasSend", "ok");
			if (!"".equals(items.toString())&&tempList.size()>0) {
				String[] titles=items.toString().split(",");
				String[] colors=new String[]{"94B6D2","D6A91F","E79764","D57C7C","7EA900","000000","FF0000","FFFF00","00FF00","00FFFF","FF00FF","008080"};
				if(isPartner.equals("0")){
					BuildFusionChartXml.setTitle("代维人员配置统计(单位:个)");
				}else if(isPartner.equals("1")){
					BuildFusionChartXml.setTitle("自维人员配置统计(单位:个)");
				}
				BuildFusionChartXml.setNumberSuf("个");
				BuildFusionChartXml.setColors(colors);
				BuildFusionChartXml.setSpan(2);
				BuildFusionChartXml.setShowValues(0);
				String xml = BuildFusionChartXml.getSimpleBarXML(sql.toString(),titles);
				request.setAttribute("xml",xml );
				request.setAttribute("width",BuildFusionChartXml.getWidth() );
			}
			return mapping.findForward("pnrUserConfigRateStatistics");
		}
	}
	/**
	 * 
	* @Title: partnerUserConfigRateStatisticsSearchInto 
	* @Description: 人员配置率钻取
	* @param 
	* @Time:Dec 3, 2012-9:07:29 AM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward  partnerUserConfigRateStatisticsSearchInto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		String addTimeY=StaticMethod.null2String(request.getParameter("add_time_y"));
		String addTimeM=StaticMethod.null2String(request.getParameter("add_time_m"));
		String provinceId=StaticMethod.null2String(request.getParameter("province_idtypelikearea"));
		String cityId=StaticMethod.null2String(request.getParameter("city_idtypelikearea"));
		String countryId=StaticMethod.null2String(request.getParameter("country_idtypelikearea"));
		String professional=StaticMethod.null2String(request.getParameter("professionaltypelikearea"));
		String company=StaticMethod.null2String(request.getParameter("company_name"));
		CommonSpringJdbcServiceImpl csjs=(CommonSpringJdbcServiceImpl)getBean("commonSpringJdbcService");
		request.setCharacterEncoding("utf-8");
		String whereStr="";
		int pageIndex = CommonUtils.getFirstResultOfDisplayTag(request, "list");
		int pageSize = 15;
		int skip = pageIndex*pageSize;
		String sql=" select skip "+skip+" limit "+pageSize+" u.id as useruuid,d.id as duuid,c.id as cuuid,u.*,c.*,d.*  " +
				"from pnr_user u,partner_dwinfo d,panter_studyexperience c where u.user_id=d.workerid and d.workerid=c.workerid " +
				"and c.isdelete='0' and u.deleted='0' and d.isdelete='0'"+whereStr;
		List<ListOrderedMap> list2=csjs.queryForList(sql);
		List<Map>list=new ArrayList<Map>();
		for (int i = 0; i < list2.size(); i++) {
			ListOrderedMap listOrderedMap=list2.get(i);
			Map map = new HashMap();
			for(Object object : listOrderedMap.keySet()) {
				map.put(object, listOrderedMap.get(object));
			}
			list.add(map);
		}
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("resultSize", list.size());
		request.setAttribute("list", list);
		return mapping.findForward("pnrUserConfigRateStatisticsSearchInto");
	}
	/**
	 * 
	* @Title: partnerUserDegreeStatistics 
	* @Description: 代维人员学历统计
	* @param 
	* @Time:Dec 3, 2012-9:04:48 AM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward  partnerUserDegreeStatistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		//rowString 字段和数据库里的字段名字完全相同
		String checkedIdsStr=StaticMethod.null2String(request.getParameter("checkedIds"));
		//checkedString带有标识以及公式的字段
		String checkedValuesStr=StaticMethod.null2String(request.getParameter("statisticsItems"));
		String checkedIds[]=checkedIdsStr.split(";");
		//数值转化为字符串，作为sql的search条件
		String checkedValues[]=checkedValuesStr.split(";");
		String searchStr="";
		String group="";
		for (int i = 0; i < checkedIds.length; i++) {
			if(!checkedIds[i].contains("_degree")){
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
				if(!group.equals("")){
					group+=",";
				}
				group+=checkedValues[i];
			}else {
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
			}
			if (i!=checkedIds.length-1) {
				searchStr+=",";
			}
		}
		Boolean NoProSelection=false;
		if (!searchStr.contains("_degree")) {
			searchStr+=",count(p.id) as count";
			NoProSelection=true;
		}
		//获取where条件值
		String area=StaticMethod.nullObject2String(request.getParameter("area_id"),"");
		String maintainCompany=StaticMethod.nullObject2String(request.getParameter("maintainCompany_id"),"");
		if (!"".equals(maintainCompany)&&maintainCompany.length()>20) {
			PartnerDeptMgr deptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			PartnerDept dept=deptMgr.getPartnerDept(maintainCompany);
			if (dept!=null) {
				String deptid=StaticMethod.null2String(dept.getDeptMagId());
				maintainCompany=deptid;
			}
		}
		String maintenanceMajor=StaticMethod.nullObject2String(request.getParameter("maintenanceMajor"),"");
		String exportFlag=StaticMethod.nullObject2String(request.getParameter("exportFlag"),"");
		String whereStr=" ";
		if(!"".equals(area)){
			whereStr+=" and u.area_id='"+area+"'";
		}
		if (!"".equals(maintainCompany)) {
			whereStr+=" and u.dept_id='"+maintainCompany+"'";
		}
		if (!"".equals(maintenanceMajor)) {
			whereStr+=" and dw.professional='"+maintenanceMajor+"'";
		}
		String otherConditions="  group by "+group+" order by "+group;
		if (!searchStr.contains("TypeLike")) {
			otherConditions="";
		}
		String statisticsSql="select "+searchStr+"  from panter_studyexperience p,pnr_user u,partner_dwinfo dw  where " +
				"u.deleted='0' and dw.isdelete='0' and p.isdelete='0' and u.user_id=p.workerid and p.workerid=dw.workerid "+whereStr+otherConditions;
		String sql="select "+searchStr+" from panter_studyexperience p,pnr_user u,partner_dwinfo dw  where " +
		"u.deleted='0' and dw.isdelete='0' and p.isdelete='0' and u.user_id=p.workerid and p.workerid=dw.workerid "+whereStr+otherConditions;
		StringBuffer items=new StringBuffer();
		List<String> headList = new ArrayList<String>();
		//[area, maintenanceCompany, maintenanceMajor, apparatusName, apparatusType, apparatusProperty, apparatusStatus]
		for (int i = 0; i < checkedIds.length; i++) {
			if ("area_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("所属区域");
			}else if ("dept_idTypeLikeDept".equals(checkedIds[i])) {
				headList.add("代维组织");
			}else if ("professinalTypeLikedict".equals(checkedIds[i])) {
				headList.add("代维专业");
			}else if ("junior_degree".equals(checkedIds[i])) {
				headList.add("初中及以下");
				items.append("初中及以下,");
			}else if ("technical_degree".equals(checkedIds[i])) {
				headList.add("中专");
				items.append("中专,");
			}else if ("senior_degree".equals(checkedIds[i])) {
				headList.add("高中");
				items.append("高中,");
			}else if ("college_degree".equals(checkedIds[i])) {
				headList.add("大专");
				items.append("大专,");
			}else if ("undergraduate_degree".equals(checkedIds[i])) {
				headList.add("本科");
				items.append("本科,");
			}else if ("master_degree".equals(checkedIds[i])) {
				headList.add("硕士及以上");
				items.append("硕士及以上,");
			}
		}
		List<List<TdObjModel>> tempList =PnrUserStatisticsTableHelper.verticalGrowp(checkedIds,statisticsSql,"/personnel/statistics.do?method=partnerUserDegreeStatisticsSearchInto");
		if (NoProSelection) {//没有专业选项时
			headList.add("总数");
		}else {
			//不需要数据钻取，去掉count字段；
			for (List<TdObjModel>  list : tempList) {
				list.remove(list.size()-1);
			}
		}
		request.setAttribute("areaStringLike", area);
		request.setAttribute("maintainCompanyStringLike", maintainCompany);
		request.setAttribute("major", maintenanceMajor);
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkedList", checkedIdsStr);
		if (!"".equals(exportFlag)&&exportFlag.equals("2")) {
			//执行导出
			String fileName="人员学历证书统计";
			StatisticsResultExport.exportStatisticsResultToXLSFile(true, tempList, headList, fileName, response,request);
			return null;
		}else {
			//跳转到统计页面
			request.setAttribute("hasSend", "ok");
			if (!"".equals(items.toString())&&tempList.size()>0) {
				String[] titles=items.toString().split(",");
				String[] colors=new String[]{"94B6D2","D6A91F","E79764","D57C7C","7EA900","000000","FF0000","FFFF00","00FF00","00FFFF","FF00FF","008080"};
				BuildFusionChartXml.setTitle("代维人员学历证书统计(单位:个)");
				BuildFusionChartXml.setNumberSuf("个");
				BuildFusionChartXml.setColors(colors);
				BuildFusionChartXml.setSpan(5);
				BuildFusionChartXml.setShowValues(0);
				String xml = BuildFusionChartXml.getSimpleBarXML(sql.toString(),titles);
				request.setAttribute("xml",xml );
				request.setAttribute("width",BuildFusionChartXml.getWidth() );
			}
			return mapping.findForward("pnrUserDegreeStatistics");
		}
	}
	/**
	 * 
	* @Title: partnerUserDegreeStatisticsSearchInto 
	* @Description: 人员学历统计钻取
	* @param 
	* @Time:Dec 3, 2012-9:08:11 AM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward  partnerUserDegreeStatisticsSearchInto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		CommonSpringJdbcServiceImpl csjs=(CommonSpringJdbcServiceImpl)getBean("commonSpringJdbcService");
		request.setCharacterEncoding("utf-8");
		String area=StaticMethod.null2String(request.getParameter("area_idtypelikearea"));
		String company=StaticMethod.null2String(request.getParameter("dept_idtypelikedept"));
		String professional=StaticMethod.null2String(request.getParameter("professinaltypelikedict"));
		String degree=StaticMethod.null2String(request.getParameter("degree"));
		String whereStr="";
		if (!"".equals(area)) {
			whereStr+="and u.area_id='"+area+"'";
		}
		if (!"".equals(company)) {
			whereStr+="and u.dept_id='"+company+"'";
		}
		if (!"".equals(professional)) {
			whereStr+="and d.professional='"+professional+"'";
		}
		if (!"".equals(degree)) {
			whereStr+="and c.degree='"+degree+"'";
		}
		int pageIndex = CommonUtils.getFirstResultOfDisplayTag(request, "list");
		int pageSize = 15;
		int skip = pageIndex*pageSize;
		String selectSql="u.id AS useruuid,d.id AS duuid,C.id AS cuuid,C.degree,u.user_id,u.area_id,u.dept_id,u.name,u.person_card_no,d.professional" +
				" FROM pnr_user u, partner_dwinfo d, panter_studyexperience C " +
				" WHERE u.user_id = d.workerid AND d.workerid = C.workerid  AND C.isdelete = '0' AND u.deleted = '0'  AND d.isdelete = '0' " + whereStr;
		List<ListOrderedMap> list2=csjs.queryForList(CommonSqlHelper.formatPageSql(selectSql, skip, pageSize));
//		List<Map>list=new ArrayList<Map>();
//		for (int i = 0; i < list2.size(); i++) {
//			ListOrderedMap listOrderedMap=list2.get(i);
//			Map map = new HashMap();
//			for(Object object : listOrderedMap.keySet()) {
//				map.put(object, listOrderedMap.get(object));
//			}
//			list.add(map);
//		}
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("resultSize", CommonSqlHelper.getResultSize(selectSql));
		request.setAttribute("list", list2);
		return mapping.findForward("pnrUserDegreeStatisticsSearchInto");
	}
	/**
	 * 
	* @Title: partnerUserLostRateStatistics 
	* @Description: 代维人员流失率统计
	* @param 
	* @Time:Dec 3, 2012-9:05:21 AM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward  partnerUserLostRateStatistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		//rowString 字段和数据库里的字段名字完全相同
		String checkedIdsStr=StaticMethod.null2String(request.getParameter("checkedIds"));
		String checkedIdsStr2="leavatimeyear;leavatimemonth;";
		checkedIdsStr2+=checkedIdsStr;
		//checkedString带有标识以及公式的字段
		String checkedValuesStr=StaticMethod.null2String(request.getParameter("statisticsItems"));
		String checkedIds[]=checkedIdsStr.split(";");
		String checkedIds2[]=checkedIdsStr2.split(";");
		//数值转化为字符串，作为sql的search条件
		String checkedValues[]=checkedValuesStr.split(";");
//		String searchStr="leavatimeyear,leavatimemonth";
//		String group="leavatimeyear,leavatimemonth";
		String searchStr="";
		String group="";
		for (int i = 0; i < checkedIds.length; i++) {
			if(checkedIds[i].contains("TypeLike")){
				if(!searchStr.equals("")){
					searchStr+=",";
				}
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
				if(!group.equals("")){
					group+=",";
				}
				group+=checkedValues[i];
			}
		}
		//获取where条件值
		String area=StaticMethod.nullObject2String(request.getParameter("area_id"),"");
		String maintainCompany=StaticMethod.nullObject2String(request.getParameter("maintainCompany_id"),"");
		if (!"".equals(maintainCompany)&&maintainCompany.length()>20) {
			PartnerDeptMgr deptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			PartnerDept dept=deptMgr.getPartnerDept(maintainCompany);
			if (dept!=null) {
				String deptid=StaticMethod.null2String(dept.getDeptMagId());
				maintainCompany=deptid;
			}
		}
		String maintenanceMajor=StaticMethod.nullObject2String(request.getParameter("maintenanceMajor"),"");
		String maxDateInMonth=StaticMethod.nullObject2String(request.getParameter("maxDateVal"),"");
		String minDateInMonth=StaticMethod.nullObject2String(request.getParameter("minDateVal"),"");
		String year=StaticMethod.nullObject2String(request.getParameter("year"),"");
		String month0=StaticMethod.nullObject2String(request.getParameter("month"),"");
		String month=month0;
		if (month.length()==1) {
			month="0"+month0;
		}
		String exportFlag=StaticMethod.nullObject2String(request.getParameter("exportFlag"),"");
			String whereStr=" ";
		String url="&year="+year+"&month="+month;
		if(!"".equals(area)){
			whereStr+=" and u.area_id='"+area+"'";
		}
		if (!"".equals(maintainCompany)) {
			whereStr+=" and u.dept_id='"+maintainCompany+"'";
		}
		if (!"".equals(maintenanceMajor)) {
			whereStr+=" and d.professional='"+maintenanceMajor+"'";
		}
		String statisticsSql="";
		if ("".equals(group)) {
			searchStr+="sum(case  when post_state='1240903'  and deleted='0' and leavatimeyear='"+year+"' and leavatimemonth='"+month+"'  then 1 else 0 end) as latitude,";
			searchStr+="sum(case  when  post_state <> '1240901' and deleted='0'  and savetimelongvalue <="+year+month+"31"+" then 1 else 0 end) as longtitude";
			statisticsSql="select "+searchStr+",count(*) as count from pnr_user u,partner_dwinfo d  where " +
			"u.deleted='0' and d.isdelete='0' and u.user_id=d.workerid "+whereStr;
		}else {
			searchStr+=",sum(case  when post_state='1240903'  and deleted='0' and leavatimeyear='"+year+"' and leavatimemonth='"+month+"'  then 1 else 0 end) as latitude,";
			searchStr+="sum(case  when  post_state <> '1240901' and deleted='0'  and savetimelongvalue <="+year+month+"31"+" then 1 else 0 end) as longtitude";
			statisticsSql="select "+searchStr+",count(*) from pnr_user u,partner_dwinfo d  where " +
			"u.deleted='0' and d.isdelete='0' and u.user_id=d.workerid "+whereStr+"  group by  "+group+" order by "+group;
		}
		StringBuffer items=new StringBuffer();
		List<String> headList = new ArrayList<String>();
		//[area, maintenanceCompany, maintenanceMajor, apparatusName, apparatusType, apparatusProperty, apparatusStatus]
		headList.add("年");
		headList.add("月");
		for (int i = 0; i < checkedIds.length; i++) {
			if ("area_idTypeLikeArea".equals(checkedIds[i])) {
				headList.add("所属区域");
			}else if ("dept_idTypeLikeDept".equals(checkedIds[i])) {
				headList.add("代维组织");
			}else if ("professionalTypeLikedict".equals(checkedIds[i])) {
				headList.add("代维专业");
			}else if ("latitude".equals(checkedIds[i])) {
				headList.add("离职人数");
			}else if ("longtitude".equals(checkedIds[i])) {
				headList.add("人员总数");
			}
		}
		headList.add("人员流失率");
		//headList.add("总数");
		List<List<TdObjModel>> tempList = PnrUserLostStatisticTableHelper.verticalGrowp(
				checkedIds2,statisticsSql,"/personnel/statistics.do?method=partnerUserLostRateStatisticsSearchInto"+url);
		//不需要数据钻取，去掉count字段；
		for (int i = 0; i < tempList.size(); i++) {
			List<TdObjModel> list1=tempList.get(i);
			TdObjModel yearModel=list1.get(0);
			TdObjModel monthModel=list1.get(1);
			yearModel.setName(year);
			monthModel.setName(month);
			int s1=list1.size();
			if (i==0) {
				yearModel.setShow(true);
				monthModel.setShow(true);
				monthModel.setRowspan(tempList.size());
				yearModel.setRowspan(tempList.size());
			}else {
				yearModel.setShow(false);
				monthModel.setShow(false);
				monthModel.setRowspan(tempList.size());
				yearModel.setRowspan(tempList.size());
			}
			if (s1>3) {
				TdObjModel rateModel=list1.get(s1-1);//获得的是离职率model
				TdObjModel totalModel=list1.get(s1-2);//获得的是在职总数model
				TdObjModel lizhiModel=list1.get(s1-3);//获得的是离职人数model
				String totalStr=StaticMethod.null2String(totalModel.getName());
				String lizhiStr=StaticMethod.null2String(lizhiModel.getName());
				if ("".equals(totalStr)) {
					totalStr="0";
				}
				if ("".equals(lizhiStr)) {
					lizhiStr="0";
				}
				double totalD=Double.parseDouble(totalStr);
				double lizhiD=Double.parseDouble(lizhiStr);
				String rateStr="";
				if (totalD==0) {
					rateStr+="0.0%";
				}else {
					BigDecimal bigDecimal=new BigDecimal(lizhiD/totalD*100);
					double rateD=bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					rateStr=rateD+"%";
				}
				rateModel.setName(rateStr);
				rateModel.setUrl("");
			}
		}
		request.setAttribute("areaStringLike", area);
		request.setAttribute("maintainCompanyStringLike", maintainCompany);
		request.setAttribute("major", maintenanceMajor);
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("year1", year);
		request.setAttribute("month1", month0);
		request.setAttribute("checkedList", checkedIdsStr);
		if (!"".equals(exportFlag)&&exportFlag.equals("2")) {
			//执行导出
			String fileName="人员流失率统计";
			StatisticsResultExport.exportStatisticsResultToXLSFile(true, tempList, headList, fileName, response,request);
			return null;
		}else {
			//跳转到统计页面
			request.setAttribute("hasSend", "ok");
			return mapping.findForward("pnrUserLostRateStatistics");
		}
	}
	/**
	 * 
	* @Title: partnerUserLostRateStatisticsSearchInto 
	* @Description: 代维人员流失率数据钻取
	* @param 
	* @Time:Dec 3, 2012-9:08:33 AM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward  partnerUserLostRateStatisticsSearchInto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		CommonSpringJdbcServiceImpl csjs=(CommonSpringJdbcServiceImpl)getBean("commonSpringJdbcService");
		request.setCharacterEncoding("utf-8");
		String area=StaticMethod.null2String(request.getParameter("area_idtypelikearea0"));
		String company=StaticMethod.null2String(request.getParameter("dept_idtypelikedept0"));
		String professional=StaticMethod.null2String(request.getParameter("professionaltypelikedict0"));
		String degree=StaticMethod.null2String(request.getParameter("degree"));
		String leavatimeyear=StaticMethod.null2String(request.getParameter("year"));
		String leavatimemonth=StaticMethod.null2String(request.getParameter("month"));
		String postStatus=StaticMethod.null2String(request.getParameter("postStatus"));
		String whereStr="";
		if (!"".equals(area)) {
			whereStr+="and u.area_id='"+area+"'";
		}
		if (!"".equals(company)) {
			whereStr+="and u.dept_id='"+company+"'";
		}
		if (!"".equals(professional)) {
			whereStr+="and d.professional='"+professional+"'";
		}
		if ("1240902".equals(postStatus)) {//在职
			if (leavatimemonth.length()==1) {
				leavatimemonth="0"+leavatimemonth;
			}
			int saveTimeLongValue=Integer.parseInt(leavatimeyear+leavatimemonth+"31");
			whereStr+="and u.post_state <> '1240901' and savetimelongvalue<="+saveTimeLongValue;
		}
		if ("1240903".equals(postStatus)) {//离职
			whereStr+="and u.post_state='"+postStatus+"' and leavatimeyear='"+leavatimeyear+"' and leavatimemonth='"+leavatimemonth+"' ";
		}
		int pageIndex = CommonUtils.getFirstResultOfDisplayTag(request, "list");
		int pageSize = 15;
		int skip = pageIndex*pageSize;
		String selectSql="u.id as useruuid,d.id as duuid, u.area_id,u.dept_name,u.user_id,u.name,u.person_card_no,u.post_state,u.savetime,u.leavatime," +
				"u.leavareason,u.blacklist,d.professional " +
				"from pnr_user u, partner_dwinfo d " +
				"where u.user_id = d.workerid   and d.isdelete = '0'   and u.deleted = '0'" + whereStr;
		List<ListOrderedMap> list2=csjs.queryForList(CommonSqlHelper.formatPageSql(selectSql, skip, pageSize));
//		List<Map>list=new ArrayList<Map>();
//		for (int i = 0; i < list2.size(); i++) {
//			ListOrderedMap listOrderedMap=list2.get(i);
//			Map map = new HashMap();
//			for(Object object : listOrderedMap.keySet()) {
//				map.put(object, listOrderedMap.get(object));
//			}
//			list.add(map);
//		}
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("resultSize", CommonSqlHelper.getResultSize(selectSql));
		request.setAttribute("postState", postStatus);
		request.setAttribute("list", list2);
		return mapping.findForward("pnrUserLostRateStatisticsSearchInto");
	}
	
}
