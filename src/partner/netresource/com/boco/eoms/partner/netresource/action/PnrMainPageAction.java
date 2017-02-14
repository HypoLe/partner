package com.boco.eoms.partner.netresource.action;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.LocalDate;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.dao.hibernate.TawSystemDictTypeDaoHibernate;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.dao.hibernate.PartnerDeptDaoHibernate;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.contact.model.ContactMain;
import com.boco.eoms.partner.dataSynch.util.DataSynchJdbcUtil;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMainMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanMainToListForm;
import com.boco.eoms.partner.netresource.service.IEomsService;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;


public class PnrMainPageAction extends BaseAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("");
	}

	/**
	 * 加载4条业务联系函
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadYWLXHData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		int dataCount = 5;
		/*获取数据 begin*/
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionInfo.getUserid();
		String userDept = sessionInfo.getDeptid();
		StringBuffer sql = new StringBuffer();
		
		IEomsService eomsService = (IEomsService) ApplicationContextHolder.getInstance().getBean("eomsService");
		
		
		sql.append( " select distinct main.* from contact_main main,contact_task task ")
		.append(" where  main.id = task.mainid  and main.type in (1,2,3) ")
		.append(" and ((task.taskownerid = '"+userId+"'  and task.taskstate = 1  )")
		.append(" or (task.taskownerid = '"+userDept+"' and task.taskstate = 1  )) " )
		.append("  order by isurgent desc,main.publisherdeptname desc ");
		String sqlStr = sql.toString();
		String countSql = "select count(distinct main.id) count from contact_main main,contact_task task "
		+" where  main.id = task.mainid  and main.type in (1,2,3) "
		+" and ((task.taskownerid = '"+userId+"'  and task.taskstate = 1  )"
		+" or (task.taskownerid = '"+userDept+"' and task.taskstate = 1  )) ";
		Map map = eomsService.getDataList(ContactMain.class, "main", sqlStr, countSql, 0, 5);
//		List<Map<String,Object>> YWLXHList =(List)map.get("list");
		List<Map<String,Object>> YWLXHList = this.getDataListBySQL(sqlStr);
		/*获取数据 end*/
		response.setCharacterEncoding("utf-8");
		JSONObject json = new JSONObject();
		StringBuffer sb = new StringBuffer();
		if(YWLXHList!=null && YWLXHList.size()>0) {
			for(int i=0;i<dataCount;i++) {
				if(i<YWLXHList.size()) {
					sb.append("<tr>");
					sb.append("<td>");
					String type=YWLXHList.get(i).get("type").toString();
					if (type.equals("1")) {
						 sb.append("<a href=\"contact/contact.do?method=getJsp&pageName=auditPage&pageType=edit&mainId="+YWLXHList.get(i).get("id").toString()+"\" target=\"_blank\">"+YWLXHList.get(i).get("code").toString()+"</a>");
					}else if (type.equals("2")||type.equals("3")) {
						sb.append("<a href=\"contact/contact.do?method=getJsp&pageName=handlePage&pageType=edit&mainId="+YWLXHList.get(i).get("id").toString()+"\" target=\"_blank\">"+YWLXHList.get(i).get("code").toString()+"</a>");
					} 
					sb.append("</td>");
					sb.append("<td>");
					sb.append(informixTimeFix(YWLXHList.get(i).get("publishername").toString()));
					sb.append("</td>");
					sb.append("<td>");
					sb.append(YWLXHList.get(i).get("subject").toString());
					sb.append("</td>");
					sb.append("<td>");
					sb.append(informixTimeFix(YWLXHList.get(i).get("deathtime").toString()));
					sb.append("</td>");
					sb.append("</tr>");
				} else {
					sb.append("<tr><td></td><td></td><td></td><td></td></tr>");
				}
			}
		} else {
			sb.append("<tr><td colspan=\"4\" rowspan=\"4\" style=\"height:63px\">无数据</td></tr>");
		}
		
		json.put("success", "true");
		json.put("dataEmpty", "false");
		json.put("otherInfo","none");
		json.put("data", sb.toString());
		
		String jsonStr = json.toString();
		response.getWriter().write(jsonStr);
		return null;
	}
	
	/**
	 * 加载工单
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadGongDanData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		int dataCount = 5;
		/*获取数据 begin*/
//		String sql = "select skip 0 first "+dataCount+" "+
//					    "a.res_name as res_name,"+
//					    "b.name as execute_object,"+
//					    "a.cycle_start_time as cycle_start_time,"+
//					    "a.cycle_end_time as cycle_end_time "+
//					"from pnr_inspect_plan_res a,pnr_dept b "+
//					"where a.execute_object = b.id";
//		List<Map<String,Object>> YWLXHList = this.getDataListBySQL(sql);
		List<Map<String,Object>> YWLXHList = new ArrayList<Map<String,Object>>();
		/*获取数据 end*/
		
		response.setCharacterEncoding("utf-8");
		JSONObject json = new JSONObject();
		
		StringBuffer sb = new StringBuffer();
		if(YWLXHList.size()>0) {
			for(int i=0;i<dataCount;i++) {
				if(i<YWLXHList.size()) {
					sb.append("<tr>");
					sb.append("<td>");
					sb.append(YWLXHList.get(i).get("res_name").toString());
					sb.append("</td>");
					sb.append("<td>");
					sb.append(YWLXHList.get(i).get("execute_object").toString());
					sb.append("</td>");
					sb.append("<td>");
					sb.append(YWLXHList.get(i).get("cycle_start_time").toString());
					sb.append("</td>");
					sb.append("<td>");
					sb.append(YWLXHList.get(i).get("cycle_end_time").toString());
					sb.append("</td>");
					sb.append("</tr>");
				} else {
					sb.append("<tr><td></td><td></td><td></td><td></td></tr>");
				}
			}
		} else {
			sb.append("<tr><td colspan=\"4\" rowspan=\"4\" style=\"height:63px\">无数据</td></tr>");
		}
		
		json.put("success", "true");
		json.put("dataEmpty", "false");
		json.put("otherInfo","none");
		json.put("data", sb.toString());
		
		String jsonStr = json.toString();
		response.getWriter().write(jsonStr);
		return null;
	}
	/**
	 * 加载公告
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadGongGaoData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		response.setCharacterEncoding("utf-8");
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		
		
		
		json.put("success", "true");
		json.put("otherInfo","none");
		json.put("data", array);
		
		
		String jsonStr = json.toString();
		response.getWriter().write(jsonStr);
		return null;
	}
	
	/**
	 * 加载巡检任务
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadXJRWData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		int dataCount = 5;
		/*获取数据 begin*/
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr)getBean("inspectPlanMainMgr");
		search.setFirstResult(0);
		search.setMaxResults(5);
		search.addFilterEqual("status", 1);
		search.addSortDesc("createTime");
		search.addFilterEqual("approveStatus", 1);
		
		
		String userId = getUserId(request);
		TawSystemSessionForm sysuser = this.getUser(request);
		PartnerUserMgr  partnerUserMgr = (PartnerUserMgr)getBean("partnerUserMgr");
		PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);
		
		
		if(null != user){//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是deptMagId like
			String deptMagId = sessionForm.getDeptid();
			if(deptMagId.length()>6){
				search.addFilterLike("deptMagId", deptMagId.substring(0, deptMagId.length()-2)+ "%");
			}else{
				search.addFilterLike("deptMagId", deptMagId+ "%");
			}
		}else if(!"admin".equals(userId)){//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like
			
			ITawSystemDeptManager mgr = (ITawSystemDeptManager)getBean("ItawSystemDeptManager");
			TawSystemDept dept = mgr.getDeptinfobydeptid(sysuser.getDeptid(), "0");
			String areaid = dept.getAreaid();
			PartnerDeptMgr  partnerDeptMgr = (PartnerDeptMgr)getBean("partnerDeptMgr");
			List partnerList = partnerDeptMgr.getPartnerDepts(" and countyId like '"+areaid+"%'");
			List<String> deptMagIdList = new ArrayList<String>();
			
			for(int i=0;partnerList!=null && i<partnerList.size();i++){
				PartnerDept partnerDept = (PartnerDept)partnerList.get(i);
				
				String deptMagId = StaticMethod.nullObject2String(partnerDept.getDeptMagId());
				if(!deptMagId.equals("")){
					deptMagIdList.add(deptMagId);
				}
			}
			if(deptMagIdList!=null && deptMagIdList.isEmpty()==false){
				search.addFilterIn("deptMagId", deptMagIdList);
			}else{//如果没有管理代维公司，则不允许看该界面
				return mapping.findForward("inspectnopriv");
			}
		}
		
		
			LocalDate date = new LocalDate();
			search.addFilterEqual("year", date.getYear());
			search.addFilterEqual("month", date.getMonthOfYear());
		
		SearchResult<InspectPlanMain> searchResult = inspectPlanMainMgr.searchAndCount(search);
		List<InspectPlanMain> list = searchResult.getResult();
		InspectPlanMainToListForm inspectPlanMainToListForm;
		List<InspectPlanMainToListForm> XJRWList = new ArrayList<InspectPlanMainToListForm>();
		for(int i = 0;i<list.size();i++){
			inspectPlanMainToListForm = new InspectPlanMainToListForm();
			try {
				list.get(i).setCreateTime(null);
				BeanUtilsBean2.getInstance().copyProperties(inspectPlanMainToListForm, list.get(i));
//				Map<String,Integer> map = inspectPlanResMgr.queryTotalAndHasDoneNum(inspectPlanMainToListForm.getId());
				inspectPlanMainToListForm.setHasDone(list.get(i).getResDoneNum());
				inspectPlanMainToListForm.setResNumber(list.get(i).getResNum());
//				inspectPlanMainToListForm.setPlanNumber(map.get("planCount"));
				inspectPlanMainToListForm.setPlanNumber(list.get(i).getResPlanDoneNum());
				XJRWList.add(inspectPlanMainToListForm);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		/*获取数据 end*/
		
		TawSystemDictTypeDaoHibernate dictDao = (TawSystemDictTypeDaoHibernate)getBean("tawSystemDictTypeDao");
		PartnerDeptDaoHibernate partnerDeptDao = (PartnerDeptDaoHibernate)getBean("partnerDeptDao");
		
		
		response.setCharacterEncoding("utf-8");
		JSONObject json = new JSONObject();
		
		StringBuffer sb = new StringBuffer();
		if(XJRWList.size()>0) {
			for(int i=0;i<dataCount;i++) {
				if(i<XJRWList.size()) {
					sb.append("<tr>");
					sb.append("<td>");
					sb.append("<a target=\"_blank\" href=\"partner/inspect/inspectPlanExecute.do?method=getInspectPlanMainDetail&id="+XJRWList.get(i).getId()+"&pageType=mainpage\">"+XJRWList.get(i).getPlanName()+"</a>");
					sb.append("</td>");
					sb.append("<td>");
					sb.append(dictDao.id2Name(XJRWList.get(i).getSpecialty()));
					sb.append("</td>");
					sb.append("<td>");
					sb.append(partnerDeptDao.id2Name(XJRWList.get(i).getPartnerDeptId()));
					sb.append("</td>");
					sb.append("<td>");
					sb.append(XJRWList.get(i).getYear()+"年"+XJRWList.get(i).getMonth()+"月");
					sb.append("</td>");
					sb.append("<td>");
					sb.append("<a target=\"_blank\" href=\"partner/inspect/inspectPlanExecute.do?method=getInspectPlanMainDetail&id="+XJRWList.get(i).getId()+"&pageType=mainpage\">查看</a>");
					sb.append("</td>");
					sb.append("</tr>");
				} else {
					sb.append("<tr><td></td><td></td><td></td><td></td><td></td></tr>");
				}
			}
		} else {
			sb.append("<tr><td colspan=\"5\" rowspan=\"4\" style=\"height:63px\">无数据</td></tr>");
		}
		
		json.put("success", "true");
		json.put("dataEmpty", "false");
		json.put("otherInfo","none");
		json.put("data", sb.toString());
		
		String jsonStr = json.toString();
		response.getWriter().write(jsonStr);
		return null;
	}
	
	/**
	 * 获取数据列表
	 * @param userId
	 * @param deptId
	 * @return
	 */
	public List<Map<String,Object>> getDataListBySQL(String sql) {
		DataSynchJdbcUtil jdbcUtil = (DataSynchJdbcUtil)ApplicationContextHolder.getInstance().getBean("dataSynchJdbcUtil");
		Connection conn = null;
		Statement stmt = null;
		List<Map<String,Object>> resultList = null;
		try {
			conn = jdbcUtil.getCon();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			resultList = DataSynchJdbcUtil.resultSet2ListMap(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(stmt != null){
					stmt.close();
				}
				if(conn != null) {
					if(!conn.isClosed()) {
						conn.close();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return resultList;
	}
	
	public String informixTimeFix(String informixTime) {
		if(informixTime!=null && informixTime.length()>19) {
			informixTime = informixTime.substring(0, 19);
		}
		return informixTime;
	}
}

