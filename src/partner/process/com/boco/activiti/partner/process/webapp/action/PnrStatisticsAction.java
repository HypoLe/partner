package com.boco.activiti.partner.process.webapp.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.LocalDate;

import com.boco.activiti.partner.process.model.AccreditOrder;
import com.boco.activiti.partner.process.model.WorkerState;
import com.boco.activiti.partner.process.po.PreflightDetailStatisticPartnerModel;
import com.boco.activiti.partner.process.po.PreflightStatisticPartnerModel;
import com.boco.activiti.partner.process.po.ProjectMoneyModel;
import com.boco.activiti.partner.process.po.ShowInWalkNum;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsDrillModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel2;
import com.boco.activiti.partner.process.service.IPnrStatisticsService;
import com.boco.activiti.partner.process.service.IPnrTaskTicketService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.activiti.partner.process.service.IPnrTroubleTicketService;
import com.boco.activiti.partner.process.service.IPnrWorkerStateService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.area.dao.hibernate.TawSystemAreaDaoHibernate;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.dao.TawSystemUserDao;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.partner.process.util.DateUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * Description:任务工单
 */

public class PnrStatisticsAction extends BaseAction {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 
	/**
	 * 显示工单查询页面
	 */
	public ActionForward showInWalkNum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		
		//查看登录人的地市
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
        String userId = sessionForm.getUserid();
		//根据派发人的部门所属区域，确定工单的区域
		ITawSystemDeptManager deptSys = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		TawSystemDept tawSystemDept = deptSys.getDeptinfobydeptid(sessionForm
				.getDeptid(), "0");
		String areaid = tawSystemDept.getAreaid();
		
		String cityWhere = "";
		if((StaticMethod.nullObject2String(areaid)).length()==2){
			
		}else if((StaticMethod.nullObject2String(areaid)).length()==4){
			cityWhere = " and tn.city='"+areaid+"' ";
		}else if((StaticMethod.nullObject2String(areaid)).length()==6){
			cityWhere = " and tn.city='"+areaid.substring(0,4)+"' ";

		}
		
		//cityWhere = " and tn.city='2819' ";
	
		String type = StaticMethod.nullObject2String(request.getParameter("type"));
		
		//String typeStr = "'interface','arteryPrecheck'";
		String typeStr = "'interface'";//现阶段只需要本地网的
		if("interface".equals(type)){
			typeStr ="'interface'";
		}else if("arteryPrecheck".equals(type)){
			typeStr ="'arteryPrecheck'";
			
		}

		
		String searchSql="select td.* from  (select tn.city," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='need' then 1 else 0 end) neednum_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='workOrderCheck' then 1 else 0 end) workchecknum_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='cityLineExamine' then 1 else 0 end) city_l_examinenum_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='cityLineDirectorAudit' then 1 else 0 end) city_l_auditnum_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='cityManageExamine' then 1 else 0 end) city_m_examinenum_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='cityManageDirectorAudit' then 1 else 0 end) city_m_auditnum_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='cityViceAudit' then 1 else 0 end) city_v_auditnum_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='provinceLineExamine' then 1 else 0 end) pro_l_examinenum_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='provinceLineViceAudit' then 1 else 0 end) pro_l_auditnum_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_= 'provinceManageExamine' then 1 else 0 end) pro_m_examinenum_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='provinceManageViceAudit' and tn.state<>8 then 1 else 0 end) pro_m_auditnum_crash," +
				
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='need' then tn.project_amount else 0 end) needmoney_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='workOrderCheck' then tn.project_amount else 0 end) workcheckmoney_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='cityLineExamine' then tn.project_amount else 0 end) city_l_examinemoney_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='cityLineDirectorAudit' then tn.project_amount else 0 end) city_l_auditmoney_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='cityManageExamine' then tn.project_amount else 0 end) city_m_examinemoney_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='cityManageDirectorAudit' then tn.project_amount else 0 end) city_m_auditmoney_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='cityViceAudit' then tn.project_amount else 0 end) city_v_auditmoney_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='provinceLineExamine' then tn.project_amount else 0 end) pro_l_examinemoney_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='provinceLineViceAudit' then tn.project_amount else 0 end) pro_l_auditmoney_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_= 'provinceManageExamine' then tn.project_amount else 0 end) pro_m_examinemoney_crash," +
				"sum(case  when tn.precheck_flag='101231401' and kt.task_def_key_='provinceManageViceAudit' and tn.state<>8 then tn.project_amount else 0 end) pro_m_auditmoney_crash," +
				
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='need' then 1 else 0 end) neednum," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='workOrderCheck' then 1 else 0 end) workchecknum," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='cityLineExamine' then 1 else 0 end) city_l_examinenum," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='cityLineDirectorAudit' then 1 else 0 end) city_l_auditnum," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='cityManageExamine' then 1 else 0 end) city_m_examinenum," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='cityManageDirectorAudit' then 1 else 0 end) city_m_auditnum," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='cityViceAudit' then 1 else 0 end) city_v_auditnum," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='provinceLineExamine' then 1 else 0 end) pro_l_examinenum," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='provinceLineViceAudit' then 1 else 0 end) pro_l_auditnum," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_= 'provinceManageExamine' then 1 else 0 end) pro_m_examinenum," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='provinceManageViceAudit' and tn.state<>8 then 1 else 0 end) pro_m_auditnum," +
				
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='need' then tn.project_amount else 0 end) needmoney," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='workOrderCheck' then tn.project_amount else 0 end) workcheckmoney," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='cityLineExamine' then tn.project_amount else 0 end) city_l_examinemoney," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='cityLineDirectorAudit' then tn.project_amount else 0 end) city_l_auditmoney," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='cityManageExamine' then tn.project_amount else 0 end) city_m_examinemoney," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='cityManageDirectorAudit' then tn.project_amount else 0 end) city_m_auditmoney," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='cityViceAudit' then tn.project_amount else 0 end) city_v_auditmoney," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='provinceLineExamine' then tn.project_amount else 0 end) pro_l_examinemoney," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='provinceLineViceAudit' then tn.project_amount else 0 end) pro_l_auditmoney," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_= 'provinceManageExamine' then tn.project_amount else 0 end) pro_m_examinemoney," +
				"sum(case  when tn.precheck_flag='101231402' and kt.task_def_key_='provinceManageViceAudit' and tn.state<>8 then tn.project_amount else 0 end) pro_m_auditmoney" +
				"  from act_ru_task kt left join " +
				" (select process_instance_id,themeinterface,city,nvl(precheck_flag, '101231402') as precheck_flag,state,nvl(project_amount,0) as project_amount from pnr_act_transfer_office_main) tn on kt.proc_inst_id_ = tn.process_instance_id" +
				" where kt.assignee_ is not null and tn.themeinterface in ("+typeStr+")" +
				" and tn.state <> 1 and length(tn.city)=4 "+cityWhere+
				" group by tn.city)td" +
				" left join taw_system_area ta on td.city = ta.areaid" +
				" order by nvl(ta.sort,0)"; 
		
		
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		System.out.println("----------在途工单数量sql="+searchSql);
		List  list = new ArrayList();
		for (ListOrderedMap listOrderedMap : resultList) {
		  ShowInWalkNum  model= new ShowInWalkNum();
			model.setCityId((String)listOrderedMap.get("city"));
			//应急 工单数量
			model.setNeednum_crash(listOrderedMap.get("neednum_crash")==null?0:Double.parseDouble(listOrderedMap.get("neednum_crash").toString()));
			model.setWorkchecknum_crash(listOrderedMap.get("workchecknum_crash")==null?0:Double.parseDouble(listOrderedMap.get("workchecknum_crash").toString()));
			model.setCity_l_examinenum_crash(listOrderedMap.get("city_l_examinenum_crash")==null?0:Double.parseDouble(listOrderedMap.get("city_l_examinenum_crash").toString()));
			model.setCity_l_auditnum_crash(listOrderedMap.get("city_l_auditnum_crash")==null?0:Double.parseDouble(listOrderedMap.get("city_l_auditnum_crash").toString()));
			model.setCity_m_examinenum_crash(listOrderedMap.get("city_m_examinenum_crash")==null?0:Double.parseDouble(listOrderedMap.get("city_m_examinenum_crash").toString()));
			model.setCity_m_auditnum_crash(listOrderedMap.get("city_m_auditnum_crash")==null?0:Double.parseDouble(listOrderedMap.get("city_m_auditnum_crash").toString()));
			model.setCity_v_auditnum_crash(listOrderedMap.get("city_v_auditnum_crash")==null?0:Double.parseDouble(listOrderedMap.get("city_v_auditnum_crash").toString()));
			model.setPro_l_examinenum_crash(listOrderedMap.get("pro_l_examinenum_crash")==null?0:Double.parseDouble(listOrderedMap.get("pro_l_examinenum_crash").toString()));
			model.setPro_l_auditnum_crash(listOrderedMap.get("pro_l_auditnum_crash")==null?0:Double.parseDouble(listOrderedMap.get("pro_l_auditnum_crash").toString()));
			model.setPro_m_examinenum_crash(listOrderedMap.get("pro_m_examinenum_crash")==null?0:Double.parseDouble(listOrderedMap.get("pro_m_examinenum_crash").toString()));
			model.setPro_m_auditnum_crash(listOrderedMap.get("pro_m_auditnum_crash")==null?0:Double.parseDouble(listOrderedMap.get("pro_m_auditnum_crash").toString()));
			
			//应急 工单金额
			model.setNeedmoney_crash(listOrderedMap.get("needmoney_crash")==null?0:Double.parseDouble(listOrderedMap.get("needmoney_crash").toString()));
			model.setWorkcheckmoney_crash(listOrderedMap.get("workcheckmoney_crash")==null?0:Double.parseDouble(listOrderedMap.get("workcheckmoney_crash").toString()));
			model.setCity_l_examinemoney_crash(listOrderedMap.get("city_l_examinemoney_crash")==null?0:Double.parseDouble(listOrderedMap.get("city_l_examinemoney_crash").toString()));
			model.setCity_l_auditmoney_crash(listOrderedMap.get("city_l_auditmoney_crash")==null?0:Double.parseDouble(listOrderedMap.get("city_l_auditmoney_crash").toString()));
			model.setCity_m_examinemoney_crash(listOrderedMap.get("city_m_examinemoney_crash")==null?0:Double.parseDouble(listOrderedMap.get("city_m_examinemoney_crash").toString()));
			model.setCity_m_auditmoney_crash(listOrderedMap.get("city_m_auditmoney_crash")==null?0:Double.parseDouble(listOrderedMap.get("city_m_auditmoney_crash").toString()));
			model.setCity_v_auditmoney_crash(listOrderedMap.get("city_v_auditmoney_crash")==null?0:Double.parseDouble(listOrderedMap.get("city_v_auditmoney_crash").toString()));
			model.setPro_l_examinemoney_crash(listOrderedMap.get("pro_l_examinemoney_crash")==null?0:Double.parseDouble(listOrderedMap.get("pro_l_examinemoney_crash").toString()));
			model.setPro_l_auditmoney_crash(listOrderedMap.get("pro_l_auditmoney_crash")==null?0:Double.parseDouble(listOrderedMap.get("pro_l_auditmoney_crash").toString()));
			model.setPro_m_examinemoney_crash(listOrderedMap.get("pro_m_examinemoney_crash")==null?0:Double.parseDouble(listOrderedMap.get("pro_m_examinemoney_crash").toString()));
			model.setPro_m_auditmoney_crash(listOrderedMap.get("pro_m_auditmoney_crash")==null?0:Double.parseDouble(listOrderedMap.get("pro_m_auditmoney_crash").toString()));
			
			//常规 工单数量
			model.setNeednum(listOrderedMap.get("neednum")==null?0:Double.parseDouble(listOrderedMap.get("neednum").toString()));
			model.setWorkchecknum(listOrderedMap.get("workchecknum")==null?0:Double.parseDouble(listOrderedMap.get("workchecknum").toString()));
			model.setCity_l_examinenum(listOrderedMap.get("city_l_examinenum")==null?0:Double.parseDouble(listOrderedMap.get("city_l_examinenum").toString()));			
			model.setCity_l_auditnum(listOrderedMap.get("city_l_auditnum")==null?0:Double.parseDouble(listOrderedMap.get("city_l_auditnum").toString()));
			model.setCity_m_examinenum(listOrderedMap.get("city_m_examinenum")==null?0:Double.parseDouble(listOrderedMap.get("city_m_examinenum").toString()));
			model.setCity_m_auditnum(listOrderedMap.get("city_m_auditnum")==null?0:Double.parseDouble(listOrderedMap.get("city_m_auditnum").toString()));
			model.setCity_v_auditnum(listOrderedMap.get("city_v_auditnum")==null?0:Double.parseDouble(listOrderedMap.get("city_v_auditnum").toString()));
			model.setPro_l_examinenum(listOrderedMap.get("pro_l_examinenum")==null?0:Double.parseDouble(listOrderedMap.get("pro_l_examinenum").toString()));
			model.setPro_l_auditnum(listOrderedMap.get("pro_l_auditnum")==null?0:Double.parseDouble(listOrderedMap.get("pro_l_auditnum").toString()));
			model.setPro_m_examinenum(listOrderedMap.get("pro_m_examinenum")==null?0:Double.parseDouble(listOrderedMap.get("pro_m_examinenum").toString()));
			model.setPro_m_auditnum(listOrderedMap.get("pro_m_auditnum")==null?0:Double.parseDouble(listOrderedMap.get("pro_m_auditnum").toString()));
			
			//应急 工单金额
			model.setNeedmoney(listOrderedMap.get("needmoney")==null?0:Double.parseDouble(listOrderedMap.get("needmoney").toString()));
			model.setWorkcheckmoney(listOrderedMap.get("workcheckmoney")==null?0:Double.parseDouble(listOrderedMap.get("workcheckmoney").toString()));
			model.setCity_l_examinemoney(listOrderedMap.get("city_l_examinemoney")==null?0:Double.parseDouble(listOrderedMap.get("city_l_examinemoney").toString()));
			model.setCity_l_auditmoney(listOrderedMap.get("city_l_auditmoney")==null?0:Double.parseDouble(listOrderedMap.get("city_l_auditmoney").toString()));
			model.setCity_m_examinemoney(listOrderedMap.get("city_m_examinemoney")==null?0:Double.parseDouble(listOrderedMap.get("city_m_examinemoney").toString()));
			model.setCity_m_auditmoney(listOrderedMap.get("city_m_auditmoney")==null?0:Double.parseDouble(listOrderedMap.get("city_m_auditmoney").toString()));
			model.setCity_v_auditmoney(listOrderedMap.get("city_v_auditmoney")==null?0:Double.parseDouble(listOrderedMap.get("city_v_auditmoney").toString()));
			model.setPro_l_examinemoney(listOrderedMap.get("pro_l_examinemoney")==null?0:Double.parseDouble(listOrderedMap.get("pro_l_examinemoney").toString()));
			model.setPro_l_auditmoney(listOrderedMap.get("pro_l_auditmoney")==null?0:Double.parseDouble(listOrderedMap.get("pro_l_auditmoney").toString()));
			model.setPro_m_examinemoney(listOrderedMap.get("pro_m_examinemoney")==null?0:Double.parseDouble(listOrderedMap.get("pro_m_examinemoney").toString()));
			model.setPro_m_auditmoney(listOrderedMap.get("pro_m_auditmoney")==null?0:Double.parseDouble(listOrderedMap.get("pro_m_auditmoney").toString()));
			list.add(model);
		}
		
//		request.setAttribute("total", list.size());
//		request.setAttribute("pageSize", 100);
		request.setAttribute("taskList", list);
		return mapping.findForward("showInWalkNum");
	}
	/**
	 * 显示工单查询页面
	 */
	public ActionForward showMoneySelect(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
//		统计的类型：默认大修改造
		String type = StaticMethod.nullObject2String(request.getParameter("type"));
		
		//String typeStr = "'interface','arteryPrecheck'";
		String typeStr = "'interface','overhaul','reform'";
		if("interface".equals(type)){
			typeStr ="'interface'";
		}else if("arteryPrecheck".equals(type)){
			typeStr ="'arteryPrecheck'";
			
		}
		
		String dayStr ="";
		String  endTime="";
		String  beginTime ="";
		
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		if("".equals(year)||"".equals(month)){//默认查询开始及结束时间
		    String curDate = dateFormat.format(new Date());
			year = curDate.substring(0, 4);
			month = curDate.substring(5,7);
			int monthInt = Integer.parseInt(month);
			
			beginTime =DateUtils.getThisSeasonFirstTime(year, monthInt);
			
			endTime =DateUtils.getThisSeasonFinallyTime(year, monthInt);
			
			
		}else {
			
//			 dayStr = year+"-"+month;
			
//			LocalDate now = new LocalDate(dayStr);
//			endTime= now.plusMonths(2).dayOfMonth().withMaximumValue()+"";
//		    beginTime = now.dayOfMonth().withMinimumValue()+"";
			int monthInt = Integer.parseInt(month);
			beginTime =DateUtils.getThisSeasonFirstTime(year, monthInt);
			endTime =DateUtils.getThisSeasonFinallyTime(year, monthInt);
			
		}
		
		//月转换成季度
		String quarter = DateUtils.getQuarterByMonth(month);
		//request.setAttribute("month", month);
		request.setAttribute("month", quarter);
		request.setAttribute("year", year);
//		当前季度，截止时间为当天的前一天
		Date endD = dateFormat.parse(endTime);
		if(endD.after(new Date())){
			Date beforeDate = DateUtils.getNextDay(new Date());
			endTime =dateFormat.format(beforeDate);
			
		}
//		查看登录人的地市
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
        String userId = sessionForm.getUserid();
		//根据派发人的部门所属区域，确定工单的区域
		ITawSystemDeptManager deptSys = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		TawSystemDept tawSystemDept = deptSys.getDeptinfobydeptid(sessionForm
				.getDeptid(), "0");
		String areaid = tawSystemDept.getAreaid();
		
		String citywhere = "";
		if((StaticMethod.nullObject2String(areaid)).length()==2){
			
		}else if((StaticMethod.nullObject2String(areaid)).length()==4){
			citywhere = " and pm.city='"+areaid+"' ";
		}else if((StaticMethod.nullObject2String(areaid)).length()==6){
			
			return showMoneySelectCountry( mapping,  form, request, response);
		}
		
		
		String searchSql="select tt.*,nvl(pbt.budget_amount,0) budget_amount,(nvl(pbt.budget_amount,0)*0.9-tt.province_money-province_money_repair) blance," +
				"nvl(pbt.budget_amount,0)*0.1-tt.province_money_crash blance_crash from ("+ 
		"select uc.areaid," +
		 "sum( case when uc.taskType='1' and uc.rate<0.5 then 1 else 0 end) city_count_repair," +
			" sum( case when uc.taskType='1' and uc.rate<0.5 then uc.project_amount else 0 end) city_money_repair," +
			" sum( case when uc.taskType='1' and uc.rate<0.5 and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') >='"+beginTime+"' " +
			" and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') <='"+endTime+"' " +
			" then 1 else 0 end) province_count_repair," +
			" sum(case when uc.taskType='1' and uc.rate<0.5 and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') >='"+beginTime+"'" +
			" and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') <='"+endTime+"' " +
			" then uc.project_amount else 0 end) province_money_repair ," +
		
		    "sum( case when uc.taskType='0' and uc.PRECHECK_FLAG='101231401' then 1 else 0 end) city_count_crash," +
			" sum( case when uc.taskType='0' and uc.PRECHECK_FLAG='101231401' then uc.project_amount else 0 end) city_money_crash," +
			" sum( case when uc.taskType='0' and uc.PRECHECK_FLAG='101231401' and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') >='"+beginTime+"' " +
			" and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') <='"+endTime+"' " +
			" then 1 else 0 end) province_count_crash," +
			" sum(case when uc.taskType='0' and uc.PRECHECK_FLAG='101231401' and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') >='"+beginTime+"'" +
			" and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') <='"+endTime+"' " +
			" then uc.project_amount else 0 end) province_money_crash ," +
			
			"sum( case when uc.taskType='0' and uc.PRECHECK_FLAG='101231402' then 1 else 0 end) city_count," +
			" sum( case when uc.taskType='0' and uc.PRECHECK_FLAG='101231402' then uc.project_amount else 0 end) city_money," +
			" sum( case when uc.taskType='0' and uc.PRECHECK_FLAG='101231402' and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') >='"+beginTime+"' " +
			" and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') <='"+endTime+"' " +
			" then 1 else 0 end) province_count," +
			" sum(case when uc.taskType='0' and uc.PRECHECK_FLAG='101231402' and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') >='"+beginTime+"'" +
			" and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') <='"+endTime+"' " +
			" then uc.project_amount else 0 end) province_money" +
		
		
		" from ( select row_number() over(partition by kt.proc_inst_id_ order by kt.start_time_ desc) row_number ," +
		"kt.end_time_,ut.areaid, ut.DISTRIBUTED_INTERFACE_TIME,ut.PRECHECK_FLAG,kt.proc_inst_id_,ut.project_amount, " +
			"ut.taskType,ut.rate " +
			"from (" +
		"select pm.city areaid,case pm.themeinterface when 'interface' then 'cityViceAudit' " +
		" when  'overhaul' then 'cityViceAudit' " +
		"when  'reform' then 'cityViceAudit' else '0' end stat," +
		"decode( pm.themeinterface,'interface','0','overhaul','1','reform','1','2') taskType," +
		"pm.process_instance_id,pm.DISTRIBUTED_INTERFACE_TIME,pm.project_amount,pm.PRECHECK_FLAG," +
		" round(pm.COMPENSATE/pm.project_amount,2) rate " +
		" from pnr_act_transfer_office_main pm " +
		"left join act_ru_task rk on pm.process_instance_id=rk.proc_inst_id_ " +
		"left join act_hi_procinst hpt on pm.process_instance_id = hpt.proc_inst_id_ " +
		"where pm.themeinterface in ("+typeStr+") " +
		"and to_char(hpt.start_time_,'yyyy-mm-dd')  <= '"+endTime+"'" +
		"and to_char(hpt.start_time_,'yyyy-mm-dd')  >= '"+beginTime+"' and length(pm.city)=4 " +
				"and pm.PRECHECK_FLAG  is not null and pm.state <>1"+citywhere +
		" and rk.task_def_key_ not in ('need','workOrderCheck','cityLineExamine','cityLineDirectorAudit','cityManageExamine','cityManageDirectorAudit','cityViceAudit'))ut " +
		"left join act_hi_taskinst kt on ut.process_instance_id = kt.proc_inst_id_ and ut.stat = kt.task_def_key_) uc" +
		" where uc.row_number =1 and ((to_char(uc.end_time_,'yyyy-mm-dd') >='"+beginTime+"' " +
		"and to_char(uc.end_time_,'yyyy-mm-dd') <='"+endTime+"') or(to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') >='"+beginTime+"' " +
		"and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') <='"+endTime+"')) group by uc.areaid) tt" +
		" left join pnr_act_city_budget_amount pbt on tt.areaid=pbt.city_id and pbt.budget_year='"+year+"' " +
		"and pbt.budget_quarter='"+month+"' order by pbt.order_code";
		
		System.out.println("----------------------预算差额报表地市查询sql="+searchSql);
		
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		List  list = new ArrayList();
		for (ListOrderedMap listOrderedMap : resultList) {
			ProjectMoneyModel  model= new ProjectMoneyModel();
			model.setCityId((String)listOrderedMap.get("areaid"));
			model.setTaskType((String)listOrderedMap.get("PRECHECK_FLAG"));
			model.setCityCount(listOrderedMap.get("city_count")==null?0:Double.parseDouble(listOrderedMap.get("city_count").toString()));
			model.setCityMoney(listOrderedMap.get("city_money")==null?0:Double.parseDouble(listOrderedMap.get("city_money").toString()));
			model.setProvinceCount(listOrderedMap.get("province_count")==null?0:Double.parseDouble(listOrderedMap.get("province_count").toString()));
			model.setProvinceMoney(listOrderedMap.get("province_money")==null?0:Double.parseDouble(listOrderedMap.get("province_money").toString()));
			model.setBalance(listOrderedMap.get("blance")==null?0:Double.parseDouble(listOrderedMap.get("blance").toString()));
			
			model.setProjectMoney(listOrderedMap.get("budget_amount")==null?0:Double.parseDouble(listOrderedMap.get("budget_amount").toString()));
			model.setCityCountCrash(listOrderedMap.get("city_count_crash")==null?0:Double.parseDouble(listOrderedMap.get("city_count_crash").toString()));
			model.setCityMoneyCrash(listOrderedMap.get("city_money_crash")==null?0:Double.parseDouble(listOrderedMap.get("city_money_crash").toString()));
			model.setProvinceCountCrash(listOrderedMap.get("province_count_crash")==null?0:Double.parseDouble(listOrderedMap.get("province_count_crash").toString()));
			model.setProvinceMoneyCrash(listOrderedMap.get("province_money_crash")==null?0:Double.parseDouble(listOrderedMap.get("province_money_crash").toString()));
			model.setBalanceCrash(listOrderedMap.get("blance_crash")==null?0:Double.parseDouble(listOrderedMap.get("blance_crash").toString()));
			
			model.setCityCountRepair(listOrderedMap.get("city_count_repair")==null?0:Double.parseDouble(listOrderedMap.get("city_count_repair").toString()));
			model.setCityMoneyRepair(listOrderedMap.get("city_money_repair")==null?0:Double.parseDouble(listOrderedMap.get("city_money_repair").toString()));
			model.setProvinceCountRepair(listOrderedMap.get("province_count_repair")==null?0:Double.parseDouble(listOrderedMap.get("province_count_repair").toString()));
			model.setProvinceMoneyRepair(listOrderedMap.get("province_money_repair")==null?0:Double.parseDouble(listOrderedMap.get("province_money_repair").toString()));
			list.add(model);
		}

//		request.setAttribute("total", list.size());
//		request.setAttribute("pageSize", 100);
		request.setAttribute("taskList", list);
		return mapping.findForward("showMoneySelect");
	}

	public ActionForward showMoneySelectCountry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
        
		String type = StaticMethod.nullObject2String(request.getParameter("type"));
		
		String typeStr = "'interface','overhaul','reform'";
//		String typeStr = "'interface','arteryPrecheck'";
		if("interface".equals(type)){
			typeStr ="'interface'";
		}else if("arteryPrecheck".equals(type)){
			typeStr ="'arteryPrecheck'";
			
		}
		
		String dayStr ="";
		String  endTime="";
		String  beginTime ="";
		
		String city ="";
		String country ="";
		String cityWhere ="";
		
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		city = StaticMethod.nullObject2String(request.getParameter("city"));
		String areaid =city;
		
		
		if("".equals(year)||"".equals(month)){
			   String curDate = dateFormat.format(new Date());
				year = curDate.substring(0, 4);
				month = curDate.substring(5,7);
				int monthInt = Integer.parseInt(month);
				
				beginTime =DateUtils.getThisSeasonFirstTime(year, monthInt);
				endTime =DateUtils.getThisSeasonFinallyTime(year, monthInt);
			
		}else {
			
			int monthInt = Integer.parseInt(month);
			beginTime =DateUtils.getThisSeasonFirstTime(year, monthInt);
			endTime =DateUtils.getThisSeasonFinallyTime(year, monthInt);
			
		}
		
		//月转换成季度
		String quarter = DateUtils.getQuarterByMonth(month);
		request.setAttribute("month", quarter);
		request.setAttribute("year", year);
		
//		当前季度，截止时间为当天的前一天
		Date endD = dateFormat.parse(endTime);
		if(endD.after(new Date())){
			Date beforeDate = DateUtils.getNextDay(new Date());
			endTime =dateFormat.format(beforeDate);
			
		}
		
		if("".equals(city)){
			
			
			
			TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
	        String userId = sessionForm.getUserid();
			//根据派发人的部门所属区域，确定工单的区域
			ITawSystemDeptManager deptSys = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
			TawSystemDept tawSystemDept = deptSys.getDeptinfobydeptid(sessionForm
					.getDeptid(), "0");
			areaid = tawSystemDept.getAreaid();
			
			if(areaid.length()<=4){
				
				return showMoneySelect( mapping,  form, request, response);
			}
		}
		
        if((StaticMethod.nullObject2String(areaid)).length()==2){
			
		}else if((StaticMethod.nullObject2String(areaid)).length()==4){
			cityWhere = " and pm.city ='"+areaid+"'";
			
		}else if((StaticMethod.nullObject2String(areaid)).length()==6){
			cityWhere = " and pm.city ='"+areaid.substring(0, 4)+"' and pm.country='"+areaid+"' ";
			
		}
		
		
		String searchSql="select cb.* ,ct.* from ("+ 
		"select uc.areaid," +

		 " sum( case when uc.taskType='1' and uc.rate<0.5 then 1 else 0 end) city_count_repair," +
			" sum( case when uc.taskType='1' and uc.rate<0.5 then uc.project_amount else 0 end) city_money_repair," +
			" sum( case when uc.taskType='1' and uc.rate<0.5 and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') >='"+beginTime+"' " +
			" and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') <='"+endTime+"' " +
			" then 1 else 0 end) province_count_repair," +
			" sum(case when uc.taskType='1' and uc.rate<0.5 and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') >='"+beginTime+"'" +
			" and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') <='"+endTime+"' " +
			" then uc.project_amount else 0 end) province_money_repair ," +
		
		 " sum( case when uc.taskType='0' and uc.PRECHECK_FLAG='101231401' then 1 else 0 end) city_count_crash," +
			" sum( case when uc.taskType='0' and uc.PRECHECK_FLAG='101231401' then uc.project_amount else 0 end) city_money_crash," +
			" sum( case when uc.taskType='0' and uc.PRECHECK_FLAG='101231401' and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') >='"+beginTime+"' " +
			" and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') <='"+endTime+"' " +
			" then 1 else 0 end) province_count_crash," +
			" sum(case when uc.taskType='0' and uc.PRECHECK_FLAG='101231401' and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') >='"+beginTime+"'" +
			" and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') <='"+endTime+"' " +
			" then uc.project_amount else 0 end) province_money_crash ," +
			
			"sum( case when uc.taskType='0' and uc.PRECHECK_FLAG='101231402' then 1 else 0 end) city_count," +
			" sum( case when uc.taskType='0' and uc.PRECHECK_FLAG='101231402' then uc.project_amount else 0 end) city_money," +
			" sum( case when uc.taskType='0' and uc.PRECHECK_FLAG='101231402' and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') >='"+beginTime+"' " +
			" and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') <='"+endTime+"' " +
			" then 1 else 0 end) province_count," +
			" sum(case when uc.taskType='0' and uc.PRECHECK_FLAG='101231402' and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') >='"+beginTime+"'" +
			" and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') <='"+endTime+"' " +
			" then uc.project_amount else 0 end) province_money" +
			
		" from ( select row_number() over(partition by kt.proc_inst_id_ order by kt.start_time_ desc) row_number ," +
		"kt.end_time_,ut.areaid, ut.DISTRIBUTED_INTERFACE_TIME,ut.PRECHECK_FLAG,kt.proc_inst_id_,ut.project_amount,ut.taskType,ut.rate from (" +
			"select pm.country areaid,case pm.themeinterface when 'interface' then 'cityViceAudit' " +
			" when  'overhaul' then 'cityViceAudit' " +
			"when  'reform' then 'cityViceAudit' else '0' end stat," +
			"decode( pm.themeinterface,'interface','0','overhaul','1','reform','1','2') taskType," +
			"" +
			"pm.process_instance_id,pm.DISTRIBUTED_INTERFACE_TIME,pm.project_amount,pm.PRECHECK_FLAG," +
			"round(pm.COMPENSATE/pm.project_amount,2) rate " +
			" from pnr_act_transfer_office_main pm " +
			"left join act_ru_task rk on pm.process_instance_id=rk.proc_inst_id_ " +
			"left join act_hi_procinst hpt on pm.process_instance_id = hpt.proc_inst_id_ " +
			"where pm.themeinterface in ("+typeStr+") " +
			"and to_char(hpt.start_time_,'yyyy-mm-dd')  <= '"+endTime+"'" +
			"and to_char(hpt.start_time_,'yyyy-mm-dd')  >= '"+beginTime+"' and length(pm.city)=4 " +
			"and pm.PRECHECK_FLAG  is not null "+cityWhere+" and pm.state <>1" +
			"and rk.task_def_key_ not in ('need','workOrderCheck','cityLineExamine','cityLineDirectorAudit','cityManageExamine','cityManageDirectorAudit','cityViceAudit'))ut " +
		"left join act_hi_taskinst kt on ut.process_instance_id = kt.proc_inst_id_ and ut.stat = kt.task_def_key_) uc" +
		" where uc.row_number =1 and ((to_char(uc.end_time_,'yyyy-mm-dd') >='"+beginTime+"' " +
		"and to_char(uc.end_time_,'yyyy-mm-dd') <='"+endTime+"') or(to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') >='"+beginTime+"' " +
		"and to_char(uc.DISTRIBUTED_INTERFACE_TIME,'yyyy-mm-dd') <='"+endTime+"')) group by uc.areaid" +
		") cb left join ( " +
		"select pm.country," +
			"sum(case when decode( pm.themeinterface,'interface','0','overhaul','1','reform','1','2')='0'" +
			" and pm.PRECHECK_FLAG='101231401' then pm.project_amount else 0 end ) submit_money_crash," +
			"sum(case when " +
			" decode( pm.themeinterface,'interface','0','overhaul','1','reform','1','2')='0'" +
			" and pm.PRECHECK_FLAG='101231402' then pm.project_amount else 0 end ) submit_money," +
			"sum(case when " +
			" decode( pm.themeinterface,'interface','0','overhaul','1','reform','1','2')='1'" +
			" and round(pm.COMPENSATE/pm.project_amount,2)<0.5 then pm.project_amount else 0 end ) submit_money_repair" +
			
		" from pnr_act_transfer_office_main pm where 1=1 " +cityWhere+
		" and pm.themeinterface in ("+typeStr+") " +
		" and to_char(pm.send_time,'yyyy-mm-dd')>='"+beginTime+"' and to_char(pm.send_time,'yyyy-mm-dd')<='"+endTime+"' " +
		"group by pm.country ) ct on cb.areaid = ct.country ";
		
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		System.out.println("----------------------预算差额报表钻取sql="+searchSql);
		List  list = new ArrayList();
		for (ListOrderedMap listOrderedMap : resultList) {
			ProjectMoneyModel  model= new ProjectMoneyModel();
			model.setCityId((String)listOrderedMap.get("areaid"));
			model.setTaskType((String)listOrderedMap.get("PRECHECK_FLAG"));
			model.setCityMoney(listOrderedMap.get("city_money")==null?0:Double.parseDouble(listOrderedMap.get("city_money").toString()));
			model.setProvinceMoney(listOrderedMap.get("province_money")==null?0:Double.parseDouble(listOrderedMap.get("province_money").toString()));
			model.setBalance(listOrderedMap.get("submit_money")==null?0:Double.parseDouble(listOrderedMap.get("submit_money").toString()));
			
			model.setCityMoneyCrash(listOrderedMap.get("city_money_crash")==null?0:Double.parseDouble(listOrderedMap.get("city_money_crash").toString()));
			model.setProvinceMoneyCrash(listOrderedMap.get("province_money_crash")==null?0:Double.parseDouble(listOrderedMap.get("province_money_crash").toString()));
			model.setBalanceCrash(listOrderedMap.get("submit_money_crash")==null?0:Double.parseDouble(listOrderedMap.get("submit_money_crash").toString()));
			
			model.setCityMoneyRepair(listOrderedMap.get("city_money_repair")==null?0:Double.parseDouble(listOrderedMap.get("city_money_repair").toString()));
			model.setProvinceMoneyRepair(listOrderedMap.get("province_money_repair")==null?0:Double.parseDouble(listOrderedMap.get("province_money_repair").toString()));
			model.setProjectMoney(listOrderedMap.get("submit_money_repair")==null?0:Double.parseDouble(listOrderedMap.get("submit_money_repair").toString()));
			
			list.add(model);
		}
		
//		request.setAttribute("total", list.size());
//		request.setAttribute("pageSize", 100);
		request.setAttribute("taskList", list);
		return mapping.findForward("showMoneySelectCountry");
	}
	/**
	 * 显示工单查询页面
	 */
	public ActionForward showQueryPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 区分跳转的页面
		String skipPage = request.getParameter("skipPage");
		String forward = "query";

		if ("transfer".equals(skipPage)) {
			forward = "workOrderQueryTransfer";
		}

		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		String endTime = dateFormat.format(calendar.getTime());
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		ITawSystemDeptManager deptSys = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		TawSystemDept tawSystemDept = deptSys.getDeptinfobydeptid(sessionForm
				.getDeptid(), "0");
		request.setAttribute("startTime", dateFormat.format(date));
		request.setAttribute("endTime", endTime);
		String areaid = tawSystemDept.getAreaid();
		if (areaid != null && areaid.length() > 4) {
			areaid = areaid.substring(0, 4);
		}
		request.setAttribute("city", PartnerCityByUser
				.getCityByRootArea(areaid));
		request.setAttribute("isPartner", 1);
		String country = sessionForm.getDeptid();
		if (sessionForm.getUserid().equals("admin")) {
			country = "-1";
		}
		request.setAttribute("country", country);

		return mapping.findForward(forward);
	}

	public ActionForward workOrderQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// 2013-12-18 add 区县筛选条件
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String deptId = sessionForm.getDeptid();
		String userId = sessionForm.getUserid();
		String candidateGroup = request.getParameter("candidateGroup");
		if (candidateGroup != null && candidateGroup.length() > 0) {
			deptId = candidateGroup;
		}

		// 2013-12-18 end
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String beginTime = request.getParameter("sheetAcceptLimit");
		String endTime = request.getParameter("sheetCompleteLimit");
		String taskType = request.getParameter("taskType");
		String city = request.getParameter("mainCity");
		String theme = request.getParameter("title").trim();
		String workerid = request.getParameter("workerid");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}
		String subType = request.getParameter("mainFaultNet");

		if (taskType.equals("1012201")) {
			IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
			List<TaskModel> taskModels = pnrTroubleTicketService
					.workOrderQuery(deptId, workerid, beginTime, endTime,
							subType, theme, city, firstResult
									* CommonUtils.PAGE_SIZE, endResult
									* CommonUtils.PAGE_SIZE);
			int total = pnrTroubleTicketService.workOrderCount(deptId,
					workerid, beginTime, endTime, subType, theme, city);

			request.setAttribute("taskList", taskModels);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("total", (int) total);
		} else if (taskType.equals("1011101")) {
			IPnrTaskTicketService pnrTaskTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
			List<TaskModel> taskModels = pnrTaskTicketService.workOrderQuery(
					deptId, workerid, beginTime, endTime, subType, theme, city,
					firstResult * pageSize, endResult * pageSize);
			int total = pnrTaskTicketService.workOrderCount(deptId, workerid,
					beginTime, endTime, subType, theme, city);

			request.setAttribute("taskList", taskModels);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("total", (int) total);
		} else if (taskType.equals("1012301")) {
			// 找到地市-start
			ITawSystemDeptManager deptManager = (ITawSystemDeptManager)getBean("ItawSystemDeptManager"); 
			TawSystemDept dept = deptManager.getDeptinfobydeptid(deptId,"0");
			city = dept.getAreaid();
			if(city != null&&city.length()>4){
				city = city.substring(0, 4);
			}
			// 地市--end
			
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			List<TaskModel> taskModels = pnrTransferOfficeService
					.workOrderQueryAdmin(userId,"transferOffice",deptId, workerid, beginTime, endTime,
							subType, theme, city, firstResult * pageSize,
							endResult * pageSize);
			int total = pnrTransferOfficeService.workOrderCountAdmin(userId,"transferOffice",deptId,
					workerid, beginTime, endTime, subType, theme, city);

			request.setAttribute("taskList", taskModels);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("total", (int) total);
		}else if(taskType.equals("1012306")){
			// 找到地市-start
			ITawSystemDeptManager deptManager = (ITawSystemDeptManager)getBean("ItawSystemDeptManager"); 
			TawSystemDept dept = deptManager.getDeptinfobydeptid(deptId,"0");
			city = dept.getAreaid();
			if(city != null&&city.length()>4){
				city = city.substring(0, 4);
			}
			// 地市--end
			
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			List<TaskModel> taskModels = pnrTransferOfficeService
					.workOrderQueryAdmin(userId,"interface",deptId, workerid, beginTime, endTime,
							subType, theme, city, firstResult * pageSize,
							endResult * pageSize);
			int total = pnrTransferOfficeService.workOrderCountAdmin(userId,"interface",deptId,
					workerid, beginTime, endTime, subType, theme, city);

			request.setAttribute("taskList", taskModels);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("total", (int) total);
			
		}
		request.setAttribute("taskType", taskType);

		return mapping.findForward("workOrderQueryList");
	}

	/**
	 * 提交action判断走哪个action方--权限解决
	 */

	public ActionForward workOrderStatisticstijiao(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		// ItawSystemDeptManager
		ITawSystemDeptManager systemDeptManager = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		TawSystemDept tawSystemDept = systemDeptManager.getDeptinfobydeptid(
				deptId, "0");
		String AreaId = tawSystemDept.getAreaid();

		TawSystemAreaDaoHibernate tawSystemAreaDao = (TawSystemAreaDaoHibernate) getBean("tawSystemAreaDao");

		int ordercode = 1;
		String areaname = null;
		try {
			areaname = tawSystemAreaDao.id2Name(AreaId);
			ordercode = tawSystemAreaDao.getAreaByAreaId(AreaId).getOrdercode();
		} catch (DictDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(AreaId);
		System.out.println(areaname);

		request.setAttribute("city", AreaId);
		request.setAttribute("cityname", areaname);

		if (ordercode == 1) {
			return workOrderStatistics(mapping, form, request, response);
		} else if (ordercode == 2) {
			return workOrderStatisticsByCity(mapping, form, request, response);
		} else if (ordercode == 3) {
			return workOrderStatisticsByCountry(mapping, form, request,
					response);
		}

		return workOrderStatistics(mapping, form, request, response);
	}

	/**
	 * 工单查询统计
	 */
	public ActionForward workOrderStatistics(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String beginTime = request.getParameter("bTime");
		String endTime = request.getParameter("eTime");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}
		String[] subTypeArray = request.getParameterValues("subType");
		String subTypeString = null;
		if (subTypeArray != null) {
			for (String s : subTypeArray) {
				if (subTypeString == null) {
					subTypeString = s;
				} else {
					subTypeString = subTypeString + "," + s;
				}
			}
		}
		String taskType = request.getParameter("taskType");
		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		List<WorkOrderStatisticsModel> workOrderStatisticsModels = pnrStatisticsService
				.workOrderStatistics(taskType, beginTime, endTime,
						subTypeString);
		// 用于钻取 start
		request.setAttribute("taskType", taskType);
		request.setAttribute("subType", subTypeString);
		request.setAttribute("bTime", beginTime);
		request.setAttribute("eTime", endTime);

		// 用于钻取 end
		request.setAttribute("taskList", workOrderStatisticsModels);
		request.setAttribute("pageSize", CommonUtils.PAGE_SIZE);
		request.setAttribute("total", workOrderStatisticsModels.size());
		return mapping.findForward("performStatisticsQuery");
	}

	/**
	 * 工单查询统计 按地市钻取
	 */
	public ActionForward workOrderStatisticsByCity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String beginTime = request.getParameter("bTime");
		String endTime = request.getParameter("eTime");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}
		String[] subTypeArray = request.getParameterValues("subType");
		String subTypeString = null;
		if (subTypeArray != null) {
			for (String s : subTypeArray) {
				if (subTypeString == null) {
					subTypeString = s;
				} else {
					subTypeString = subTypeString + "," + s;
				}
			}
		}
		String city = null;
		String cityname = null;

		city = request.getParameter("city");
		cityname = request.getParameter("cityname");

		if (city == null) {
			city = (String) request.getAttribute("city");
		}
		if (cityname == null) {
			cityname = (String) request.getAttribute("cityname");
		}
		String taskType = request.getParameter("taskType");

		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		List<WorkOrderStatisticsModel> workOrderStatisticsModels = pnrStatisticsService
				.workOrderStatisticsbyCity(city, cityname, taskType, beginTime,
						endTime, subTypeString);

		// 用于钻取 start
		request.setAttribute("taskType", taskType);
		request.setAttribute("subType", subTypeString);
		request.setAttribute("bTime", beginTime);
		request.setAttribute("eTime", endTime);

		// 用于钻取 end
		request.setAttribute("taskList", workOrderStatisticsModels);
		request.setAttribute("pageSize", CommonUtils.PAGE_SIZE);
		request.setAttribute("total", workOrderStatisticsModels.size());

		return mapping.findForward("performStatisticsQuerybyCity");
	}

	/**
	 * 工单查询统计--县区级钻取
	 */
	public ActionForward workOrderStatisticsByCountry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// 分页
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// String taskType ="all";
		String taskType = request.getParameter("taskType");
		// String flag = "1";
		String flag = request.getParameter("flag");
		// String city ="2821" ;
		String city = request.getParameter("city");
		// String beginTime = "2013-09-01";
		String beginTime = request.getParameter("bTime");
		// String endTime = "2013-09-26";
		String endTime = request.getParameter("eTime");

		String subTypeString = request.getParameter("subType");

		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}

		if (city == null) {
			city = (String) request.getAttribute("city");
		}
		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");

		Map<String, Object> map;
		map = pnrStatisticsService.workOrderStatisticsbyCountry(city, taskType,
				beginTime, endTime, subTypeString, firstResult * pageSize,
				endResult * pageSize);
		// 用于钻取 start
		request.setAttribute("taskType", taskType);
		request.setAttribute("subType", subTypeString);
		request.setAttribute("bTime", beginTime);
		request.setAttribute("eTime", endTime);

		int total = Integer.parseInt(map.get("size").toString());
		List<WorkOrderStatisticsModel> workOrder;
		workOrder = (List<WorkOrderStatisticsModel>) map.get("list");

		request.setAttribute("taskList", workOrder);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		return mapping.findForward("performStatisticsQuerybycountry");

	}

	/**
	 * 工单查询统计---归档工单
	 */
	public ActionForward workOrderStatistics2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// 初始化地市-权限限制地市
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		ITawSystemDeptManager deptSys = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		String deptIdString = "";
		if (deptId.length() >= 5) {
			deptIdString = deptId.substring(0, 5);
		}
		TawSystemDept list = deptSys.getDeptinfobydeptid(deptIdString, "0");
		String areaId = "";
		if (list != null) {
			areaId = list.getAreaid();
		}
		if (userId.equals("admin") || areaId == null) {
			areaId = "";
		}
		// 初始化地市-end
		request.setAttribute("areaId", areaId);
		String code = "city";

		String beginTime = request.getParameter("sheetAcceptLimit");
		String endTime = request.getParameter("sheetCompleteLimit");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = getFirstDay();
		}
		if (endTime == null || endTime.equals("")) {
			endTime = getLastDay();
		}
		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		List<WorkOrderStatisticsModel2> workOrderStatisticsModels;
		if (areaId.equals("")) {
			workOrderStatisticsModels = pnrStatisticsService
					.workOrderStatistics2(beginTime, endTime);
		} else {
			workOrderStatisticsModels = pnrStatisticsService
					.workOrderStatistics2(areaId, beginTime, endTime);
			code = "country";// 用于限制权限
		}
		request.setAttribute("taskList", workOrderStatisticsModels);
		request.setAttribute("pageSize", workOrderStatisticsModels.size());
		request.setAttribute("total", workOrderStatisticsModels.size());
		request.setAttribute("code", code);
		request.setAttribute("mark", 1);// 用于隐藏“返回”按钮

		return mapping.findForward("taskStatisticsPartnerIndex");
	}

	/**
	 * 工单查询统计---在途工单
	 */
	public ActionForward workOrderStatistics3(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// 初始化地市-权限限制地市
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		ITawSystemDeptManager deptSys = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		String deptIdString = "";
		if (deptId.length() >= 5) {
			deptIdString = deptId.substring(0, 5);
		}
		TawSystemDept list = deptSys.getDeptinfobydeptid(deptIdString, "0");
		String areaId = "";
		if (list != null) {
			areaId = list.getAreaid();
		}
		if (userId.equals("admin") || areaId == null) {
			areaId = "";
		}
		// 初始化地市-end
		request.setAttribute("areaId", areaId);
		String code = "city";

		String beginTime = request.getParameter("sheetAcceptLimit");
		String endTime = request.getParameter("sheetCompleteLimit");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = getFirstDay();
		}
		if (endTime == null || endTime.equals("")) {
			endTime = getLastDay();
		}
		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		List<WorkOrderStatisticsModel2> workOrderStatisticsModels = null;
		if (areaId.equals("")) {
			workOrderStatisticsModels = pnrStatisticsService
					.workOrderStatistics3(beginTime, endTime);
		} else {
			workOrderStatisticsModels = pnrStatisticsService
					.workOrderStatistics3(areaId, beginTime, endTime);
			code = "country";// 用于限制权限
		}
		request.setAttribute("taskList", workOrderStatisticsModels);
		request.setAttribute("pageSize", workOrderStatisticsModels.size());
		request.setAttribute("total", workOrderStatisticsModels.size());
		request.setAttribute("code", code);
		request.setAttribute("mark", 1);// 用于隐藏“返回”按钮

		return mapping.findForward("taskStatisticsPartnerIndex3");
	}

	/**
	 * 工单查询统计
	 */
	public ActionForward workOrderStatistics2Country(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String city = request.getParameter("city");
		String beginTime = request.getParameter("sheetAcceptLimit");
		String endTime = request.getParameter("sheetCompleteLimit");

		String code = request.getParameter("code");

		if (beginTime == null || beginTime.equals("")) {
			beginTime = getFirstDay();
		}
		if (endTime == null || endTime.equals("")) {
			endTime = getLastDay();
		}
		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		List<WorkOrderStatisticsModel2> workOrderStatisticsModels = null;
		// 当前是地市
		if (code.equals("city")) { // 点击就来到当前地市的所有县级结构。
			workOrderStatisticsModels = pnrStatisticsService
					.workOrderStatistics2(city, beginTime, endTime);
			request.setAttribute("code", "country");

		} else if (code.equals("country"))// 当前是县级
		{ // 点击就来到当前县级的所有处理人信息。
			workOrderStatisticsModels = pnrStatisticsService
					.workOrderStatistics2Person(city, beginTime, endTime);
			request.setAttribute("code", "person");
			// 辅助数字统计
			request.setAttribute("country", city);
		}
		request.setAttribute("taskList", workOrderStatisticsModels);
		request.setAttribute("pageSize", workOrderStatisticsModels.size());
		request.setAttribute("total", workOrderStatisticsModels.size());

		return mapping.findForward("taskStatisticsPartnerIndex");
	}

	/**
	 * 工单查询统计--在途统计
	 */
	public ActionForward workOrderStatistics2Country3(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String city = request.getParameter("city");
		String beginTime = request.getParameter("sheetAcceptLimit");
		String endTime = request.getParameter("sheetCompleteLimit");

		String code = request.getParameter("code");

		if (beginTime == null || beginTime.equals("")) {
			beginTime = getFirstDay();
		}
		if (endTime == null || endTime.equals("")) {
			endTime = getLastDay();
		}
		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		List<WorkOrderStatisticsModel2> workOrderStatisticsModels = null;
		// 当前是地市
		if (code.equals("city")) { // 点击就来到当前地市的所有县级结构。
			workOrderStatisticsModels = pnrStatisticsService
					.workOrderStatistics3(city, beginTime, endTime);
			request.setAttribute("code", "country");

		} else if (code.equals("country"))// 当前是县级
		{ // 点击就来到当前县级的所有处理人信息。
			workOrderStatisticsModels = pnrStatisticsService
					.workOrderStatistics2Person3(city, beginTime, endTime);
			request.setAttribute("code", "person");
			// 辅助数字统计
			request.setAttribute("country", city);
		}
		request.setAttribute("taskList", workOrderStatisticsModels);
		request.setAttribute("pageSize", workOrderStatisticsModels.size());
		request.setAttribute("total", workOrderStatisticsModels.size());

		return mapping.findForward("taskStatisticsPartnerIndex3");
	}

	/**
	 * 当月第一天
	 * 
	 * @return
	 */
	private String getFirstDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date theDate = calendar.getTime();

		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first).append(
				" 00:00:00");
		return str.toString();

	}

	/**
	 * 当月最后一天
	 * 
	 * @return
	 */
	private String getLastDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date theDate = calendar.getTime();
		String s = df.format(theDate);
		StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
		return str.toString();

	}

	/**
	 * 显示工单统计页面
	 */
	public ActionForward showStatisticsPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ITawSystemDictTypeManager taw = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		List<TawSystemDictType> troubleList = taw
				.getDictSonsByDictid("1012201");
		List<TawSystemDictType> taskList = taw.getDictSonsByDictid("1011101");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		request.setAttribute("troubleList", troubleList);
		request.setAttribute("taskList", taskList);
		request.setAttribute("firstDay", getFirstDay().substring(0, 10));
		request.setAttribute("lastDay", getLastDay().substring(0, 10));

		return mapping.findForward("showStatisticsPage");
	}

	/**
	 * 显示工单统计钻取页面-工单列表
	 */
	public ActionForward workOrderStatisticsDrill(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 分页

		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// String taskType ="all";
		String taskType = request.getParameter("taskType");
		// String flag = "1";
		String flag = request.getParameter("flag");
		// String city ="2821" ;
		String city = request.getParameter("city");
		// String beginTime = "2013-09-01";
		String beginTime = request.getParameter("bTime");
		// String endTime = "2013-09-26";
		String endTime = request.getParameter("eTime");
		// String[] subTypeArray
		// ={"101220101","101220102","101110101","101110102","101110103"};
		String subTypeString = request.getParameter("subType");

		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}

		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		Map<String, Object> map;
		map = pnrStatisticsService.workOrderStatisticsDrill(taskType, flag,
				city, beginTime, endTime, subTypeString,
				firstResult * pageSize, endResult * pageSize);
		int total = Integer.parseInt(map.get("size").toString());
		List<WorkOrderStatisticsDrillModel> workOrder;
		workOrder = (List<WorkOrderStatisticsDrillModel>) map.get("list");

		request.setAttribute("taskList", workOrder);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		return mapping.findForward("workOrderStatisticsDrill");
	}

	/**
	 * 在处理人工单页面 - 钻取
	 */
	public ActionForward workOrderStatisticsDrillbyperson(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 分页

		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// String taskType ="all";
		String taskType = request.getParameter("taskType");
		// String flag = "1";

		String flag = request.getParameter("flag");
		// String city ="2821" ;
		String city = request.getParameter("city");

		String person = request.getParameter("person");
		System.out.println(city + "     " + person);
		// String beginTime = "2013-09-01";
		String beginTime = request.getParameter("bTime");
		// String endTime = "2013-09-26";
		String endTime = request.getParameter("eTime");
		// String[] subTypeArray
		// ={"101220101","101220102","101110101","101110102","101110103"};
		String subTypeString = request.getParameter("subType");

		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}

		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		Map<String, Object> map;
		map = pnrStatisticsService.workOrderStatisticsDrillbyperson(person,
				taskType, flag, city, beginTime, endTime, subTypeString,
				firstResult * pageSize, endResult * pageSize);
		int total = Integer.parseInt(map.get("size").toString());
		List<WorkOrderStatisticsDrillModel> workOrder;
		workOrder = (List<WorkOrderStatisticsDrillModel>) map.get("list");

		request.setAttribute("taskList", workOrder);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		return mapping.findForward("workOrderStatisticsDrill");
	}

	/**
	 * 显示工单统计钻取页面-工单列表
	 */
	public ActionForward workOrderStatisticsDrillbycity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 分页

		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// String taskType ="all";
		String taskType = request.getParameter("taskType");
		// String flag = "1";
		String flag = request.getParameter("flag");
		// String city ="2821" ;
		String city = request.getParameter("city");
		// String beginTime = "2013-09-01";
		String beginTime = request.getParameter("bTime");
		// String endTime = "2013-09-26";
		String endTime = request.getParameter("eTime");
		// String[] subTypeArray
		// ={"101220101","101220102","101110101","101110102","101110103"};
		String subTypeString = request.getParameter("subType");

		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (beginTime == null || beginTime.equals("")) {
			beginTime = sFormat.format(new Date());
		}
		if (endTime == null || endTime.equals("")) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			endTime = sFormat.format(calendar.getTime());
		}

		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		Map<String, Object> map;
		map = pnrStatisticsService.workOrderStatisticsDrillbycity(taskType,
				flag, city, beginTime, endTime, subTypeString, firstResult
						* pageSize, endResult * pageSize);
		int total = Integer.parseInt(map.get("size").toString());
		List<WorkOrderStatisticsDrillModel> workOrder;
		workOrder = (List<WorkOrderStatisticsDrillModel>) map.get("list");

		request.setAttribute("taskList", workOrder);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		return mapping.findForward("workOrderStatisticsDrill");
	}

	/**
	 * 首页工单统计钻取页面-工单列表--归档统计
	 */
	public ActionForward statisticsPartnerIndexDrill(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 分页

		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		String flag = request.getParameter("flag");

		String city = request.getParameter("city");
		String level = request.getParameter("level");
		String person = null;
		// 点击地市页面的数字时
		if ("city".equals(level)) {
			request.setAttribute("code", "country");

		}
		// 点击区县页面的数字时
		else if ("country".equals(level)) {
			request.setAttribute("code", "person");
		}
		// 当点击到处理人页面时；city获取到的是人的ID，真正的city需要传过来
		else if ("person".equals(level)) {
			city = request.getParameter("country");
			person = request.getParameter("city");
		}

		String beginTime = request.getParameter("sheetAcceptLimit");
		String endTime = request.getParameter("sheetCompleteLimit");

		if (beginTime == null || beginTime.equals("")) {
			beginTime = getFirstDay();
		}
		if (endTime == null || endTime.equals("")) {
			endTime = getLastDay();
		}
		String subType = request.getParameter("subType");

		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		Map<String, Object> map;
		map = pnrStatisticsService.statisticsPartnerIndexDrill(flag, city,
				beginTime, endTime, subType, level, person, pageSize
						* firstResult, pageSize * endResult);
		List<WorkOrderStatisticsDrillModel> workOrder;
		workOrder = (List<WorkOrderStatisticsDrillModel>) map.get("list");
		int total = 0;
		total = Integer.parseInt(map.get("size").toString());

		request.setAttribute("taskList", workOrder);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		return mapping.findForward("statisticsPartnerIndexDrill");
	}

	/**
	 * 首页工单统计钻取页面-工单列表--在途统计; 与归档统计同用一个页面。
	 */
	public ActionForward statisticsPartnerIndexDrill3(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 分页

		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		String flag = request.getParameter("flag");

		String city = request.getParameter("city");
		String level = request.getParameter("level");
		String person = null;
		// 点击地市页面的数字时
		if ("city".equals(level)) {
			request.setAttribute("code", "country");

		}
		// 点击区县页面的数字时
		else if ("country".equals(level)) {
			request.setAttribute("code", "person");
		}
		// 当点击到处理人页面时；city获取到的是人的ID，真正的city需要传过来
		else if ("person".equals(level)) {
			city = request.getParameter("country");
			person = request.getParameter("city");
		}

		String beginTime = request.getParameter("sheetAcceptLimit");
		String endTime = request.getParameter("sheetCompleteLimit");

		if (beginTime == null || beginTime.equals("")) {
			beginTime = getFirstDay();
		}
		if (endTime == null || endTime.equals("")) {
			endTime = getLastDay();
		}
		String subType = request.getParameter("subType");

		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		Map<String, Object> map = pnrStatisticsService
				.statisticsPartnerIndexDrill3(flag, city, beginTime, endTime,
						subType, level, person, firstResult * pageSize,
						endResult * pageSize);
		List<WorkOrderStatisticsDrillModel> workOrder;
		workOrder = (List<WorkOrderStatisticsDrillModel>) map.get("list");
		int total = 0;
		total = Integer.parseInt(map.get("size").toString());

		request.setAttribute("taskList", workOrder);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);

		return mapping.findForward("statisticsPartnerIndexDrill");
	}

	/**
	 * 显示个人状态页面
	 */
	public ActionForward showWorkerStatePage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int total = 0;
		int flag = 0;
		WorkerState workerState = null;
		List<AccreditOrder> accreditOrderList = null;
		String userName = "";
		String userNameId = "";

		// 获取登录员工的信息
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		String deptId = sessionForm.getDeptid();
		IPnrWorkerStateService pnrWorkerStateService = (IPnrWorkerStateService) getBean("pnrWorkerStateService");
		TawSystemUserDao tawSystemUserDao = (TawSystemUserDao) getBean("tawSystemUserDao");
		// 根据登录人的id查询是否有授权记录
		workerState = pnrWorkerStateService.getWorkerId(userId, "101011102");
		if (workerState == null) {
			workerState = new WorkerState();
			workerState.setFlag("0");
			workerState.setState("101011101");
			userNameId = userId;
		} else {
			workerState.setFlag("1");
			accreditOrderList = pnrWorkerStateService
					.getAccreditOrder(workerState.getId());
			userNameId = workerState.getAccredit();
		}

		if (accreditOrderList != null) {
			total = accreditOrderList.size();
		}

		userName = tawSystemUserDao.id2Name(userNameId);

		request.setAttribute("dept", deptId);
		request.setAttribute("pageSize", 20);
		request.setAttribute("total", total);
		request.setAttribute("taskList", accreditOrderList);
		request.setAttribute("workerState", workerState);
		request.setAttribute("userName", userName);
		return mapping.findForward("showWorkerState");
	}

	/**
	 * 请假授权工单
	 */
	public ActionForward addWorkerStatePage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean isWorkFlag = true, isWorkTaskAssignee = true;

		String addresString = "showSuccess";
		// 获取登录员工的信息
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String deptId = sessionForm.getDeptid();
		String userId = sessionForm.getUserid();
		// 获得个人状态页面信息
		// 授权人
		String taskAssignee = request.getParameter("taskAssignee");
		// 个人状态：工作状态、请假状态
		String state = request.getParameter("faultSource");
		// 定义时间格式
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = sFormat.format(new Date());

		IPnrWorkerStateService pnrWorkerStateService = (IPnrWorkerStateService) getBean("pnrWorkerStateService");
		// 判断个人状态，请假状态，直接将数据添加到数据库;工作状态，表示请假恢复工作，这时将数据库中记录的数据修改一下
		WorkerState workerState = pnrWorkerStateService.getWorkerId(userId,
				"101011102");
		if ("101011101".equals(state.trim())) {// 工作状态
			if (workerState != null) {
				workerState.setEndTime(sFormat.parse(nowTime));
				workerState.setState(state);
				pnrWorkerStateService.save(workerState);
			}
		} else if ("101011102".equals(state.trim())) {// 请假状态
			WorkerState workerTaskAssignee = pnrWorkerStateService.getWorkerId(
					taskAssignee, "101011102");

			if (workerTaskAssignee != null) {
				isWorkTaskAssignee = false;
				addresString = "showError";
			}
			if (workerState != null) {
				isWorkFlag = false;
				addresString = "showError";
			}

			if (isWorkFlag && isWorkTaskAssignee) {
				WorkerState worker = new WorkerState();
				worker.setWorkerId(userId);
				worker.setAccredit(taskAssignee);
				worker.setBeginTime(sFormat.parse(nowTime));
				worker.setState(state);
				pnrWorkerStateService.save(worker);
			}

		}
		return mapping.findForward(addresString);
	}
	
	/**
	 * 预检预修项目会审情况汇总表统计
	 */
	public ActionForward findPreflightPartnerStatistic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// 分页
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// 获取传入的参数
		String themeinterfaces[] = request.getParameterValues("themeinterface");// 工单类型
		String themeinterface = "";
		if (themeinterfaces != null) {
			for (int i = 0; i < themeinterfaces.length; i++) {
				themeinterface += "'" + themeinterfaces[i] + "',";
			}
			if (themeinterface.length() > 0)
				themeinterface = themeinterface.substring(0, themeinterface
						.length() - 1);
		}

		String taskdefkeys[] = request.getParameterValues("taskdefkey");// 所属环节
		String taskdefkey = "";
		if (taskdefkeys != null) {
			for (int i = 0; i < taskdefkeys.length; i++) {
				taskdefkey += "'" + taskdefkeys[i] + "',";
			}
			if (taskdefkey.length() > 0)
				taskdefkey = taskdefkey.substring(0, taskdefkey.length() - 1);
		}
		String month=request.getParameter("month");//月份
		String quarter=request.getParameter("quarter");//季度
		
		LocalDate date = new LocalDate();
		if(StringUtils.isEmpty(quarter) && StringUtils.isEmpty(month)){
			month= date.getMonthOfYear()+"";
		}
		
		//季度判断
		if(!"".equals(month) && month!=null &&  !"".equals(quarter) && quarter!=null ){//月份 季度都不为空 
			quarter=quarter;
		}else if(((!"".equals(month) && month !=null) &&  ("".equals(quarter)) || quarter==null)){//季度为空 根据月份判断季度
			 if("1".equals(month) || "2".equals(month) || "3".equals(month) ){
				 quarter="01";
			 }
			 if("4".equals(month) || "5".equals(month) || "6".equals(month) ){
				 quarter="04";
			 }
			 if("7".equals(month)  || "8".equals(month) || "9".equals(month) ){
				 quarter="07";
			 }
			 if("10".equals(month)  || "11".equals(month) || "12".equals(month) ){
				 quarter="10";
			 }
		}else {//月份为空 直接是季度
			quarter=quarter;
		}
		
		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		//查询
		List<PreflightStatisticPartnerModel> list = Lists.newArrayList();
		
		list=(List)pnrStatisticsService.findPreflightStatisticCityHis(themeinterface, taskdefkey,quarter);
		//返回list
		
		request.setAttribute("list", list);
		request.setAttribute("themeinterface",themeinterface);
		request.setAttribute("taskdefkey",taskdefkey);
		request.setAttribute("month",month);
		request.setAttribute("quarter",quarter);
		return mapping.findForward("partnerPreflightStatistic");

	}
	
	/** 
	 * 区县钻取
	 * 预检预修项目会审情况汇总表统计
	 */
	public ActionForward partnerPreflightCountryStatistic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// 分页
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// 获取传入的参数
		String themeinterfaces[] = request.getParameterValues("themeinterface");// 工单类型
		String themeinterface = "";
		if (!"".equals(themeinterfaces[0]) && themeinterfaces != null) {
			for (int i = 0; i < themeinterfaces.length; i++) {
				themeinterface += "'" + themeinterfaces[i] + "',";
			}
			if (themeinterface.length() > 0)
				themeinterface = themeinterface.substring(0, themeinterface
						.length() - 1);
		}

		String taskdefkeys[] = request.getParameterValues("taskdefkey");// 所属环节
		String taskdefkey = "";
		if (!"".equals(taskdefkeys[0]) && taskdefkeys != null) {
			for (int i = 0; i < taskdefkeys.length; i++) {
				taskdefkey += "'" + taskdefkeys[i] + "',";
			}
			if (taskdefkey.length() > 0)
				taskdefkey = taskdefkey.substring(0, taskdefkey.length() - 1);
		}
		String month=request.getParameter("month");//月份
		String quarter=request.getParameter("quarter");//季度
		
		//默认当前季度
		LocalDate date = new LocalDate();
		if(StringUtils.isEmpty(quarter) && StringUtils.isEmpty(month)){
			month= date.getMonthOfYear()+"";
		}
		
		//季度判断
		if(!"".equals(month) && month!=null &&  !"".equals(quarter) && quarter!=null ){//月份 季度都不为空 
			quarter=quarter;
		}else if(((!"".equals(month) && month !=null) &&  ("".equals(quarter)) || quarter==null)){//季度为空 根据月份判断季度
			 if("1".equals(month) || "2".equals(month) || "3".equals(month) ){
				 quarter="01";
			 }
			 if("4".equals(month) || "5".equals(month) || "6".equals(month) ){
				 quarter="04";
			 }
			 if("7".equals(month)  || "8".equals(month) || "9".equals(month) ){
				 quarter="07";
			 }
			 if("10".equals(month)  || "11".equals(month) || "12".equals(month) ){
				 quarter="10";
			 }
		}else {//月份为空 直接是季度
			quarter=quarter;
		}
		
		//城市
		String city=request.getParameter("city");
		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		//查询
		List<PreflightStatisticPartnerModel> list = Lists.newArrayList();
		list=(List)pnrStatisticsService.findPreflightStatisticCountryHis(themeinterface, taskdefkey,quarter,city);
		//返回list
		request.setAttribute("list", list);
		request.setAttribute("themeinterface",themeinterface);
		request.setAttribute("taskdefkey",taskdefkey);
		request.setAttribute("month",month);
		request.setAttribute("quarter",quarter);
		return mapping.findForward("partnerPreflightCountryStatistic");

	}
	
	/**
	 *  预检预修项目详情表统计
	 */
	public ActionForward findPreflightDetailPartnerStatistic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		// 分页
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		
		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		String excelType=request.getParameter("excelType");//报表类型
		//默认当前年月
		LocalDate date = new LocalDate();
		if(StringUtils.isEmpty(year)){
			year = date.getYear()+"";
			month= (date.getMonthOfYear()-1)+"";
		}
		//月份前面补“0”
		if("1".equals(month) || "2".equals(month) || "3".equals(month) || "4".equals(month) || "5".equals(month) || "6".equals(month) || "7".equals(month) || "8".equals(month) || "9".equals(month) ){
			month="0"+month;
		}
		//数量
		int total = 0;
		try {
			total = pnrStatisticsService.findPreflightDatilStatisticCityHisCount(year,month,excelType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//查询
		List<PreflightDetailStatisticPartnerModel> list = Lists.newArrayList();
		if(!StringUtils.isEmpty(year) && !StringUtils.isEmpty(month) ){
				list = pnrStatisticsService.findPreflightDatilStatisticCityHis(year,month,excelType,firstResult,endResult,pageSize);
		}
		request.setAttribute("list", list);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		return mapping.findForward("partnerPreflightDetailStatistic");
	}
	
	/**
	 *  机房优化周报
	 */
	public ActionForward findWeeklyPartnerStatistic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int pageSize = CommonUtils.PAGE_SIZE;// 默认分页记录条数
		String tempPageSize = request.getParameter("pagesize");
		if (tempPageSize != null && !"".equals(tempPageSize)) {
			pageSize = Integer.parseInt(tempPageSize);
		}

		// 获取当前页码
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;

		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// 获取用户id
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();

		// 获取传入的参数
		String sendStartTime = request.getParameter("sheetAcceptLimit");// 开始时间
		String sendEndTime = request.getParameter("sheetCompleteLimit");// 结束时间
		String region = request.getParameter("region");// 地市
		String country = request.getParameter("country");// 区县
		
		// 获取地市列表
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", city);
		
		String themeinterfaces[] = request.getParameterValues("themeinterface");// 工单类型
		String themeinterface = "";
		if (themeinterfaces != null) {
			for (int i = 0; i < themeinterfaces.length; i++) {
				themeinterface += "'" + themeinterfaces[i] + "',";
			}
			if (themeinterface.length() > 0)
				themeinterface = themeinterface.substring(0, themeinterface
						.length() - 1);
		}

		String taskdefkeys[] = request.getParameterValues("taskdefkey");// 所属环节
		String taskdefkey = "";
		if (taskdefkeys != null) {
			for (int i = 0; i < taskdefkeys.length; i++) {
				taskdefkey += "'" + taskdefkeys[i] + "',";
			}
			if (taskdefkey.length() > 0)
				taskdefkey = taskdefkey.substring(0, taskdefkey.length() - 1);
		}
		IPnrStatisticsService pnrStatisticsService = (IPnrStatisticsService) getBean("pnrStatisticsService");
		////////////////////////////////////////list集合对象需要改
		//查询
//		List<PreflightDetailStatisticPartnerModel> list = Lists.newArrayList();
//				list = pnrStatisticsService.findWeeklyStatisticHis(sendStartTime,sendEndTime,region,country,themeinterface,taskdefkey,firstResult,endResult,pageSize);
//		request.setAttribute("list", list);
		request.setAttribute("startTime", sendStartTime);// 开始时间
		request.setAttribute("endTime", sendEndTime);// 结束时间
		request.setAttribute("region", region);// 地市
		request.setAttribute("country", country);// 区县
		request.setAttribute("themeinterface", themeinterface);// 工单类型
		request.setAttribute("taskdefkey", taskdefkey);// 所属环节
		return mapping.findForward("partnerWeeklyStatistic");
	}

}