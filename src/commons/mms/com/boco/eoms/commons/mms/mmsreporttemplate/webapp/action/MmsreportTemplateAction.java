package com.boco.eoms.commons.mms.mmsreporttemplate.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.job.exception.ScheduleRunException;
import com.boco.eoms.commons.job.model.TawCommonsJobsort;
import com.boco.eoms.commons.job.model.TawCommonsJobsubscibe;
import com.boco.eoms.commons.job.plugin.InitSchedulerPlugin;
import com.boco.eoms.commons.job.service.ITawCommonsJobmonitorManager;
import com.boco.eoms.commons.job.service.ITawCommonsJobsortManager;
import com.boco.eoms.commons.job.service.ITawCommonsJobsubscibeManager;
import com.boco.eoms.commons.mms.base.config.Report;
import com.boco.eoms.commons.mms.base.config.Reports;
import com.boco.eoms.commons.mms.base.config.Sheet;
import com.boco.eoms.commons.mms.base.util.MMSConstants;
import com.boco.eoms.commons.mms.mmsreporttemplate.mgr.MmsreportTemplateMgr;
import com.boco.eoms.commons.mms.mmsreporttemplate.model.MmsreportTemplate;
import com.boco.eoms.commons.mms.mmsreporttemplate.util.MmsreportTemplateConstants;
import com.boco.eoms.commons.mms.mmsreporttemplate.webapp.form.MmsreportTemplateForm;
import com.boco.eoms.commons.statistic.base.reference.ParseXmlService;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;

/**
 * <p>
 * Title:彩信报模板
 * </p>
 * <p>
 * Description:彩信报系统
 * </p>
 * <p>
 * Tue Feb 17 14:50:50 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() 李志刚
 * @moudle.getVersion() 3.5
 * 
 */

public final class MmsreportTemplateAction extends BaseAction {

	private String getSubId(int jobid) throws Exception {
	     String subId = "";
	    String xyzLength = "1000";
	    String regionId="JOB";
	    String jobSortId = "";
	    String strYYMMDD = StaticMethod.getYYMMDD();
	    subId += strYYMMDD + "-100" ;
	    MmsreportTemplateMgr mgr = (MmsreportTemplateMgr) getBean("mmsreportTemplateMgr");
	    int xyz= mgr.getCountSubId(subId)+1;
	    String strXYZ = String.valueOf(StaticMethod.null2int(xyzLength) + xyz);
	    subId += "-" + strXYZ.substring(1);
	    String resub = regionId + "-" + jobid + "-" + subId;
//	    return subId;
	    return resub;
	  }
	
	/**
	 * 未指定方法时默认调用的方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return search(mapping, form, request, response);
	}

	/**
	 * 新增彩信报模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String configpath = MMSConstants.REPORT_CONFIG;
		Reports r = (Reports) ParseXmlService.create().xml2object(
				Reports.class, StaticMethod.getFilePathForUrl(configpath));
		
		Report[] reports = r.getReports();
		request.setAttribute("reports", reports);
		
//		List sheetList = r.getAllSheet();//new ArrayList();
//		Report report = null;
//		for (int i = 0; i < reports.getAllSheet().size(); i++) {
//			report = new Report();
//			report.setId(((Sheet) (reports.getAllSheet().get(i))).getId());
//			report.setType(((Sheet) (reports.getAllSheet().get(i))).getName());
//			sheetList.add(report);
//		}
		
//		request.setAttribute("sheetList", sheetList);
		return mapping.findForward("add");
	}
	
	public ActionForward selectMenu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control","no-cache");
        String targetId=request.getParameter("id");
        
        String configpath = MMSConstants.REPORT_CONFIG;
		Reports reports = (Reports) ParseXmlService.create().xml2object(
				Reports.class, StaticMethod.getFilePathForUrl(configpath));
		Report report = reports.getReportById(targetId);
		String xml_start="<selects>";
        String xml_end="</selects>";
        String xml="";
		if(report != null)
		{
			Sheet[] sheets = report.getSheet();
			for(int i=0; i<sheets.length;i++)
	        {
	        	xml +="<select><value>" + sheets[i].getId()  + "</value><text>" + sheets[i].getName() + "</text></select>";
	        }
		}
//        if(targetId.equalsIgnoreCase("0")){
//            xml="<select><value>0</value><text>Unbounded</text></select>";
//        }else if(targetId.equalsIgnoreCase("01")){
//            xml="<select><value>1</value><text>Mana Burn</text></select>";
//            xml +="<select><value>2</value><text>Death Coil</text></select>";
//            xml +="<select><value>3</value><text>Unholy Aura</text></select>";
//            xml +="<select><value>4</value><text>Unholy Fire</text></select>";
//        }else if(targetId.equalsIgnoreCase("02")){
//            xml="<select><value>1</value><text>Corprxplode</text></select>";
//            xml +="<select><value>2</value><text>Raise Dead</text></select>";
//            xml +="<select><value>3</value><text>Brilliance Aura</text></select>";
//            xml +="<select><value>4</value><text>Aim Aura</text></select>";
//        }else{
//            xml="<select><value>1</value><text>Rain of Chaos</text></select>";
//            xml +="<select><value>2</value><text>Finger of Death</text></select>";
//            xml +="<select><value>3</value><text>Bash</text></select>";
//            xml +="<select><value>4</value><text>Summon Doom</text></select>";
//        }
        String last_xml=xml_start+xml+xml_end;
        response.getWriter().write(last_xml);
        response.getWriter().flush();
//        System.out.println(last_xml);
		return null;
	}

	/**
	 * 修改彩信报模板
	 * 
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
		
		MmsreportTemplateMgr mmsreportTemplateMgr = (MmsreportTemplateMgr) getBean("mmsreportTemplateMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		MmsreportTemplate mmsreportTemplate = mmsreportTemplateMgr
				.getMmsreportTemplate(id);
		MmsreportTemplateForm mmsreportTemplateForm = (MmsreportTemplateForm) convert(mmsreportTemplate);
		updateFormBean(mapping, request, mmsreportTemplateForm);
		String configpath = MMSConstants.REPORT_CONFIG;
		Reports reports = (Reports) ParseXmlService.create().xml2object(
				Reports.class, StaticMethod.getFilePathForUrl(configpath));
		
		//已经选定的报表
		List sheetList = new ArrayList();//reports.getAllSheet();
		String[] statReportids = mmsreportTemplate.getStatReportId().split(",");//之前已经判定最少选择一个报表所以后面不会报数组越界
		
		//报表类型与可以选择的报表
		String statReportType = reports.getStatReportTypeBySheetId(statReportids[0]);
		Report report = reports.getReportBySheetId(statReportids[0]);
		Sheet[] sheets = report.getSheet();
		for(int i=0;i<sheets.length;i++)
		{
			sheetList.add(sheets[i]);
		}
		mmsreportTemplate.setStatReportType(statReportType);
		
//		Report report = null;
//		for (int i = 0; i < reports.getAllSheet().size(); i++) {
//			report = new Report();
//			report.setId(((Sheet) (reports.getAllSheet().get(i))).getId());
//			report.setType(((Sheet) (reports.getAllSheet().get(i))).getName());
//			sheetList.add(report);
//		}
		request.setAttribute("mmsreportTemplate", mmsreportTemplate);
		request.setAttribute("sheetList", sheetList);
		return mapping.findForward("edit");
	}
	
	public String getStatReportType()
	{
		String str = "";
		
		return str;
	}
	
	/**
	 * 查看彩信报模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward find(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		 MmsreportTemplateMgr mmsreportTemplateMgr = (MmsreportTemplateMgr) getBean("mmsreportTemplateMgr");
		 String id = StaticMethod.null2String(request.getParameter("id"));
		 MmsreportTemplate mmsreportTemplate = mmsreportTemplateMgr.getMmsreportTemplate(id);
		 MmsreportTemplateForm mmsreportTemplateForm = (MmsreportTemplateForm) convert(mmsreportTemplate);
		 updateFormBean(mapping, request, mmsreportTemplateForm);
		
		 return mapping.findForward("find");
		
	}

	/**
	 * 保存彩信报模板
	 * 
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
		String[] statReportIds = request.getParameterValues("statReportIds");
		String statReportId = "";
		for(int i=0;i<statReportIds.length;i++){
			if(i==statReportIds.length-1){
				statReportId = statReportId + statReportIds[i];
			}else{
			statReportId = statReportId + statReportIds[i]+ ",";
			}
		}
		String monthCreateTime = request.getParameter("reportMonthCreatDate");
		MmsreportTemplateMgr mmsreportTemplateMgr = (MmsreportTemplateMgr) getBean("mmsreportTemplateMgr");
		MmsreportTemplateForm mmsreportTemplateForm = (MmsreportTemplateForm) form;
		
		if(mmsreportTemplateForm.getExecuteCycle().equals("monthReport")){
			mmsreportTemplateForm.setReportCreatDate(monthCreateTime);
		}
		mmsreportTemplateForm.setStatReportId(statReportId);
		String statReportType = request.getParameter("statReportType");
		mmsreportTemplateForm.setStatReportType(statReportType);
		
		String cycle = "";
		String statMode = mmsreportTemplateForm.getExecuteCycle();
		int statNum = Integer.valueOf(mmsreportTemplateForm.getReportCreatDate()).intValue();
		if("weekReport".equals(statMode)){
			switch (statNum) {
		          //周报的时间安排在凌晨1点
		          case 1:
		            cycle = "0 0 1 ? * MON";
		            break;
		          case 2:
		            cycle = "0 0 1 ? * TUE";
		            break;
		          case 3:
		            cycle = "0 0 1 ? * WED";
		            break;
		          case 4:
		            cycle = "0 0 1 ? * THU";
		            break;
		          case 5:
		            cycle = "0 0 1 ? * FRI";
		            break;
		          case 6:
		            cycle = "0 0 1 ? * SAT";
		            break;
		          case 7:
		            cycle = "0 0 1 ? * SUN";
		            break;
		          default:
		            cycle = "";
		      }
		}
		if("monthReport".equals(statMode)){
			cycle = "0 0 2 "+ statNum +" * ?";
		}
		if("dailyReport".equals(statMode)){
			cycle = "0 0 4 * * ?";
		}
//		MmsreportTemplate mt = mmsreportTemplateMgr.getMmsreportTemplate(mmsreportTemplateForm.getId());
		TawCommonsJobsort tawCommonsJobsort = null;
		TawCommonsJobsubscibe tawCommonsJobsubscibe = null;
		boolean newFlg = false;
		if(mmsreportTemplateForm.getId() == null || mmsreportTemplateForm.getId().equalsIgnoreCase(""))//新建保存
		{
			//Jobsort模型
			tawCommonsJobsort = new TawCommonsJobsort();
			//Jobsubscibe模型
			tawCommonsJobsubscibe = new TawCommonsJobsubscibe();
			newFlg = true;
		}
		else //修改保存
		{
			ITawCommonsJobsubscibeManager tawCommonsJobsubscibeManager = (ITawCommonsJobsubscibeManager) getBean("ItawCommonsJobsubscibeManager");
			ITawCommonsJobsortManager tawCommonsJobsortManager = (ITawCommonsJobsortManager) getBean("ItawCommonsJobsortManager");
			MmsreportTemplate mt = mmsreportTemplateMgr.getMmsreportTemplate(mmsreportTemplateForm.getId());
			tawCommonsJobsubscibe = tawCommonsJobsubscibeManager.getTawCommonsJobsubscibeForSubID(mt.getJobid());
			tawCommonsJobsort = tawCommonsJobsortManager.getTawCommonsJobsort(tawCommonsJobsubscibe.getJobSortId().toString());
			newFlg = false;
		}
		
		//设置Jobsort属性
		tawCommonsJobsort.setDeleted(new Integer(0));
		tawCommonsJobsort
				.setJobClassName("com.boco.eoms.commons.mms.scheduler.MMSchedulerV35"); //轮训类
		tawCommonsJobsort.setJobSortName(mmsreportTemplateForm.getMmsName()); //任务类型名称
		tawCommonsJobsort.setMaxExecuteTime(new Integer(5)); //最大运行时间分
		ITawCommonsJobsortManager mgr1 = (ITawCommonsJobsortManager) getBean("ItawCommonsJobsortManager");
		mgr1.saveTawCommonsJobsort(tawCommonsJobsort);
		int id = tawCommonsJobsort.getId().intValue();
		
		//设置Jobsubscibe模型属性
		tawCommonsJobsubscibe.setDeleted(new Integer(0));
		tawCommonsJobsubscibe.setJobCycle(cycle);
		tawCommonsJobsubscibe.setJobSortId(new Integer(id));
		//类型 simple cron
		tawCommonsJobsubscibe.setJobType("cron");//轮询类型
		if(tawCommonsJobsubscibe.getSubId() == null || "".equalsIgnoreCase(tawCommonsJobsubscibe.getSubId()))
		{
			tawCommonsJobsubscibe.setSubId(this.getSubId(id));
		}
		tawCommonsJobsubscibe.setSubscriberDeptId(new Integer(this.getUser(request).getDeptid()));
		tawCommonsJobsubscibe.setSubscriberId(this.getUser(request).getUserid());
		tawCommonsJobsubscibe.setSubscribeTime(StaticMethod.getCurrentDateTime());
		ITawCommonsJobsubscibeManager mgr2 = (ITawCommonsJobsubscibeManager) getBean("ItawCommonsJobsubscibeManager");
		String subid = tawCommonsJobsubscibe.getSubId();
		mgr2.saveTawCommonsJobsubscibe(tawCommonsJobsubscibe, false);
		mmsreportTemplateForm.setJobid(subid);
		
		boolean isNew = (null == mmsreportTemplateForm.getId() || ""
				.equals(mmsreportTemplateForm.getId()));
		mmsreportTemplateForm.setUserid(this.getUser(request).getUserid());
		MmsreportTemplate mmsreportTemplate = (MmsreportTemplate) convert(mmsreportTemplateForm);

		if (isNew) {
			mmsreportTemplateMgr.saveMmsreportTemplate(mmsreportTemplate);
		} else {
			mmsreportTemplateMgr.saveMmsreportTemplate(mmsreportTemplate);
		}

		return mapping.findForward("success_mmsreportTemplates");//search(mapping, mmsreportTemplateForm, request, response);
	}

	/**
	 * 删除彩信报模板
	 * 
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
		MmsreportTemplateMgr mmsreportTemplateMgr = (MmsreportTemplateMgr) getBean("mmsreportTemplateMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		MmsreportTemplate mmsreportTemplate = mmsreportTemplateMgr.getMmsreportTemplate(id);
		
		//删除轮训表记录
		ITawCommonsJobsubscibeManager tawCommonsJobsubscibeManager = (ITawCommonsJobsubscibeManager) getBean("ItawCommonsJobsubscibeManager");
		ITawCommonsJobsortManager tawCommonsJobsortManager = (ITawCommonsJobsortManager) getBean("ItawCommonsJobsortManager");
//		ITawCommonsJobmonitorManager tawCommonsJobmonitorManager = (ITawCommonsJobmonitorManager)getBean("ItawCommonsJobmonitorManager");
		String subID = mmsreportTemplate.getJobid();
		TawCommonsJobsubscibe tawCommonsJobsubscibe = tawCommonsJobsubscibeManager.getTawCommonsJobsubscibeForSubID(subID);
		int jobSortId=tawCommonsJobsubscibe.getJobSortId().intValue();
		tawCommonsJobsubscibeManager.removeTawCommonsJobsubscibe(tawCommonsJobsubscibe.getId());////删除TawCommonsJobsubscibe
		tawCommonsJobsortManager.removeTawCommonsJobsort(String.valueOf(jobSortId)); //删除TawCommonsJobsubscibe
//		tawCommonsJobmonitorManager.removeTawCommonsJobmonitor(id);
		
		//删除定制信息
		mmsreportTemplateMgr.removeMmsreportTemplate(id);
		
		//如果该报表被订阅了 同样应该删除
		//TODO
		
		return mapping.findForward("success");//search(mapping, form, request, response);
	}
	/**
	 * 彩信报查询页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userid = sessionform.getUserid();
		request.setAttribute("userid", userid);
		return mapping.findForward("showPage");
	}
	/**
	 * 分页显示彩信报模板列表
	 * 
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
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				MmsreportTemplateConstants.MMSREPORTTEMPLATE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		MmsreportTemplateMgr mmsreportTemplateMgr = (MmsreportTemplateMgr) getBean("mmsreportTemplateMgr");
		/**
		 * 查询条件
		 *
		 */
		String mmsName = request.getParameter("mmsName");
		String executeCycle = request.getParameter("executeCycle");
		String userid = request.getParameter("userid");

//		查询条件
		String where = " where 1=1 ";
	
		if(!"".equalsIgnoreCase(userid) && userid != null)
		{
			where += " and mmsreportTemplate.userid like '%" + userid + "%'";
		}
		if(!"".equalsIgnoreCase(mmsName) && mmsName != null)
		{
			where += " and mmsreportTemplate.mmsName like '%" + mmsName + "%'";
		}
		if(!"".equalsIgnoreCase(executeCycle) && executeCycle != null)
		{
			where += " and mmsreportTemplate.executeCycle like '%" + executeCycle + "%'";
		}
		
		Map map = (Map) mmsreportTemplateMgr.getMmsreportTemplates(pageIndex,pageSize, where);
		List list = (List) map.get("result");
		request.setAttribute(MmsreportTemplateConstants.MMSREPORTTEMPLATE_LIST,
				list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
}