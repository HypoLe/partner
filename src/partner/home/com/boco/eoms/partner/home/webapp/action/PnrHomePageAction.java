package com.boco.eoms.partner.home.webapp.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.dataSynch.mgr.ISynchExceptionRecordMgr;
import com.boco.eoms.partner.dataSynch.model.SynchExceptionRecord;
import com.boco.eoms.partner.dataSynch.util.DataSynchJdbcUtil;
import com.boco.eoms.partner.home.util.HomeHelper;
import com.boco.eoms.partner.netresource.action.PnrMainPageAction;
import com.boco.eoms.partner.statistically.pojo.TdObjModel;
import com.boco.eoms.sheet.base.service.IEomsAllSheetListService;
import com.boco.eoms.sheet.base.util.SheetAttributes;


public class PnrHomePageAction extends BaseAction {
	/**
	 * 查询展示异常列表
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		ISynchExceptionRecordMgr synchExceptionRecordMgr = (ISynchExceptionRecordMgr) getBean("synchExceptionRecordMgr");
		SynchExceptionRecord record = new SynchExceptionRecord();
		record.setDataType(request.getParameter("dataType"));
		record.setCuid(request.getParameter("createTime"));
		
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				"list")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		PageModel pm = synchExceptionRecordMgr.findSynchExceptionRecordList(pageIndex*pageSize, pageSize,record);
		request.setAttribute("list", pm.getDatas());
		request.setAttribute("size", pm.getTotal());
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list"); 
	}
	
	/**
	 * 取得首页模块内容
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public ActionForward  getModuleData(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws IOException{
		ApplicationContext ctx = WebApplicationContextUtils
		.getRequiredWebApplicationContext(servlet.getServletContext());
		request.setAttribute("applicationContext", ctx);
		
		return mapping.findForward("moduleData");
		}
	/**
	 * 跳转工单统计钻取页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public ActionForward  goToSheetStatisticsDetailPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		PnrMainPageAction ppa = new PnrMainPageAction();
		String whereString = "where 1=1";
		
		String province = StaticMethod.null2String(request.getParameter("province"));
		if("".equals(province)) {
//			whereString += " (province='' or province is null)";
		} else {
			whereString += " and province='"+province+"'";
		}
		String city = StaticMethod.null2String(request.getParameter("city"));
		if("".equals(city)) {
//			whereString += " and (city='' or city is null)";
		} else {
			whereString += " and city='"+city+"'";
		}
		String country = StaticMethod.null2String(request.getParameter("country"));
		if("".equals(country)) {
//			whereString += " and (country='' or country is null)";
		} else {
			whereString += " and country='"+country+"'";
		}
		String groupdept = StaticMethod.null2String(request.getParameter("groupdept"));
		if("".equals(groupdept)) {
//			whereString += " and (groupdeptid='' or groupdeptid is null)";
		} else {
			whereString += " and groupdeptid='"+groupdept+"'";
		}
		String dealuser = StaticMethod.null2String(request.getParameter("dealuser"));
		if("".equals(dealuser)) {
//			whereString += " and (dealuserid='' or dealuserid is null)";
		} else {
			whereString += " and dealuserid='"+dealuser+"'";
		}
		String holdyear = StaticMethod.null2String(request.getParameter("holdyear"));
		if("".equals(holdyear)) {
//			whereString += " and (holdyear='' or holdyear is null)";
		} else {
			whereString += " and holdyear='"+holdyear+"'";
		}
		String holdhalfyear = StaticMethod.null2String(request.getParameter("holdhalfyear"));
		if("".equals(holdhalfyear)) {
//			whereString += " and (holdhalfyear='' or holdhalfyear is null)";
		} else {
			whereString += " and holdhalfyear='"+holdhalfyear+"'";
		}
		String holdquarter = StaticMethod.null2String(request.getParameter("holdquarter"));
		if("".equals(holdquarter)) {
//			whereString += " and (holdquarter='' or holdquarter is null)";
		} else {
			whereString += " and holdquarter='"+holdquarter+"'";
		}
		String holdmonth = StaticMethod.null2String(request.getParameter("holdmonth"));
		if("".equals(holdmonth)) {
//			whereString += " and (holdmonth='' or holdmonth is null)";
		} else {
			whereString += " and holdmonth='"+holdmonth+"'";
		}
		String holdday = StaticMethod.null2String(request.getParameter("holdday"));
		if("".equals(holdday)) {
//			whereString += " and (holdday='' or holdday is null)";
		} else {
			whereString += " and holdday='"+holdday+"'";
		}
		String holdstatisfied= StaticMethod.null2String(request.getParameter("holdstatisfied"));
		if("".equals(holdstatisfied)) {
//			whereString += " and (holdstatisfied='' or holdstatisfied is null)";
		} else {
			whereString += " and holdstatisfied='"+holdstatisfied+"'";
		}
		
		String specialty = StaticMethod.null2String(request.getParameter("specialty"));
		if("".equals(specialty)) {
//			whereString += " and (holdstatisfied='' or holdstatisfied is null)";
		} else {
			whereString += " and specialty='"+specialty+"'";
		}
		
		String citydeptid = StaticMethod.null2String(request.getParameter("citydeptid"));
		if("".equals(citydeptid)) {
//			whereString += " and (holdstatisfied='' or holdstatisfied is null)";
		} else {
			whereString += " and citydeptid=(SELECT dept_mag_id FROM pnr_dept WHERE id='"+citydeptid+"')";
		}
		
		String zqcitydeptid = StaticMethod.null2String(request.getParameter("zqcitydeptid"));
		if(!"".equals(zqcitydeptid)) {
			whereString += " and citydeptid='"+zqcitydeptid+"'";
		} 
		String zqprovincedeptid = StaticMethod.null2String(request.getParameter("zqprovincedeptid"));
		if(!"".equals(zqprovincedeptid)) {
			whereString += " and provincedeptid='"+zqprovincedeptid+"'";
		} 
		String zqcountrydeptid = StaticMethod.null2String(request.getParameter("zqcountrydeptid"));
		if(!"".equals(zqcountrydeptid)) {
			whereString += " and countrydeptid='"+zqcountrydeptid+"'";
		} 
		String zqgroupdeptid = StaticMethod.null2String(request.getParameter("zqgroupdeptid"));
		if(!"".equals(zqgroupdeptid)) {
			whereString += " and groupdeptid='"+zqgroupdeptid+"'";
		} 
		whereString += " and mainifcharging='1'";
		
		String eventtype = StaticMethod.null2String(request.getParameter("metering_type"));
		if("".equals(eventtype)) {
//			whereString += " and (eventtype='' or eventtype is null)";
		} else {
			whereString += " and eventtype='"+eventtype+"'";
		}
		
		String sql = "select * from pnr_metering_main " + whereString;
						
		List<Map<String,Object>> dataList = ppa.getDataListBySQL(sql);
		
		request.setAttribute("dataList",dataList);
		request.setAttribute("pageSize",dataList.size());
		
		return mapping.findForward("sheetStatisticsDetailPage");
	}
	
	/**
	 * 所有待办工单展现 copy EomsAllSheetListAction中的getUndoAllLists方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getEomsUndoAllSheetList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		/** 获取登陆信息，add by qinmin* */
		String userId = StaticMethod.nullObject2String(request
				.getParameter("userName"));
		/** 若无法从URL中获取userid，则从session中获取，modify by 秦敏 2009-08-12 begin */
		if (userId.equals("")) {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			userId = sessionform.getUserid();
		}

		String deptId = "";

		// 获取每页显示条数
		Integer pageSize = ((SheetAttributes) ApplicationContextHolder
				.getInstance().getBean("SheetAttributes")).getPageSize();

		String pageIndexName = new org.displaytag.util.ParamEncoder("taskList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 当前页数
		Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request
				.getParameter(pageIndexName)) ? 0 : (Integer.parseInt(request
				.getParameter(pageIndexName)) - 1));
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		userId = sessionform.getUserid();
		deptId = sessionform.getDeptid();

		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}
		String order = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		String orderCondition = "";
		if (!order.equals("")) {
			if (order.equals("1")) {
				order = " asc";
			} else {
				order = " desc";
			}
		}
		if (!sort.equals("")) {
			orderCondition = " " + sort + order;
		}
		IEomsAllSheetListService taskService = (IEomsAllSheetListService) getBean("IEomsAllSheetListService");

		Map condition = new HashMap();
		condition.put("orderCondition", orderCondition);
		HashMap taskListMap = null;
		// if (!userId.equals("")pageSize != null && pageSize.intValue() != 0) {
		taskListMap = taskService.getUndoAllSheetTask(condition, userId,
				deptId, "", pageIndex, pageSize);
		int total = ((Integer) taskListMap.get("taskTotal")).intValue();
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);
		List tasList = (List) taskListMap.get("taskList");
		request.setAttribute("taskList", tasList);

		if (taskListMap.get("taskCountList") != null) {
			List taskCountList = (List) taskListMap.get("taskCountList");
			List list = new ArrayList();
			for (int i = 0; taskCountList != null && i < taskCountList.size(); i++) {
				Object[] obj = (Object[]) taskCountList.get(i);
				HashMap map = new HashMap();
				

				String sheetType = StaticMethod.nullObject2String(obj[0]);
				if(!sheetType.equals("")){
					sheetType = sheetType.trim();
				}
				map.put("sheetType", sheetType);
				String url = "";
				url = sheetType + ".do";
				// 由于新业务试点工单的特殊性，所以必须将之replace掉
				if (sheetType.equals("businesspilot")) {
					sheetType = "business";
				}
				url = "../" + sheetType + "/" + url;
				map.put("sheetTypeUrl", url);
				String sheetTypeName = StaticMethod.nullObject2String( obj[1]);
				if(!sheetTypeName.equals("")){
					sheetTypeName = sheetTypeName.trim();
				}
				map.put("sheetTypeName", sheetTypeName);
				String count = StaticMethod.nullObject2String(obj[2]);
				if (!count.equals("")) {
					map.put("count", count.trim());
				}
				list.add(map);
			}
			request.setAttribute("taskCountList", list);
		}
		
		/**
		 * modify by chenyuanshu 2012-04-19 begin
		 * 由于某省直接把eoms里的url放到门户里，所以需要将token取出供后续页面中使用
		 */
		String Token = StaticMethod.nullObject2String(request
				.getParameter("Token"));
		if(!Token.equals("")){
			Token = "&Token="+Token;
		}
		request.setAttribute("Token", Token);
		/**
		 * modify by chenyuanshu 2012-04-19 end
		 */
		
		return mapping.findForward("eomsUndoAllSheetList");
	}
	
	
	/**
	 * 计次费用明细
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward pnrResMeteringDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String eventId = StaticMethod.null2String(request.getParameter("eventId"));
		
		List<Map<String,Object>> pnrResMeteringDetail = this.getPnrResMeteringDetail(eventId);
		
		request.setAttribute("pnrResMeteringDetail", pnrResMeteringDetail);
		return mapping.findForward("pnrResMeteringDetail");
	}
	/**
	 * 计次费用明细列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward pnrResMeteringSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		//where s_year=?
		//and s_mon=?
		//and province_comany_id=?
		//and region_comany_id=?
		//and city_comany_id=?;
		String s_year = StaticMethod.null2String(request.getParameter("s_year"));
		String s_mon = StaticMethod.null2String(request.getParameter("s_mon"));
		String province_comany_id = StaticMethod.null2String(request.getParameter("province_comany_id"));
		String region_comany_id = StaticMethod.null2String(request.getParameter("region_comany_id"));
		String city_comany_id = StaticMethod.null2String(request.getParameter("city_comany_id"));
		String metering_type = StaticMethod.null2String(request.getParameter("metering_type"));
		String specialty = StaticMethod.null2String(request.getParameter("specialty"));
		boolean queryAllFlag = true;
		if(!s_year.equals("") || !s_mon.equals("") || !province_comany_id.equals("") || !region_comany_id.equals("") || !city_comany_id.equals("")|| !metering_type.equals("")|| !specialty.equals("")) {
			queryAllFlag = false;
		}
		request.setAttribute("s_year", s_year);
		request.setAttribute("s_mon", StaticMethod.null2int(s_mon));
		request.setAttribute("province_comany_id", province_comany_id);
		request.setAttribute("region_comany_id", region_comany_id);
		request.setAttribute("city_comany_id", city_comany_id);

		/*导出判断 begin*/
		int pageIndex = 0;
		int pageSize = 0;
		String exportType = StaticMethod
		.null2String(request
				.getParameter(new org.displaytag.util.ParamEncoder(
						"pnrResMeteringList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		request.setAttribute("exportType", exportType);
		if(!exportType.equals("")) {
			pageSize = new Integer(-1);
		} else {
			pageIndex = CommonUtils.getFirstResultOfDisplayTag(request, "pnrResMeteringList");
			pageSize = 15;
		}
		int skip = pageIndex*pageSize;
		/*导出判断 end*/
		
		String fromAndWhere = "";
		if(queryAllFlag) {
			fromAndWhere = " from pnr_res_metering ";
		} else {
			fromAndWhere =" from pnr_res_metering where 1=1 ";
			
			if(!s_year.equals("")) {
				fromAndWhere += "and s_year='"+s_year+"' ";
			}
			if(!s_mon.equals("")) {
				fromAndWhere += "and s_mon='"+s_mon+"' ";
			}
			if(!province_comany_id.equals("")) {
				fromAndWhere += "and province_comany_id='"+province_comany_id+"' ";
			}
			if(!region_comany_id.equals("")) {
				fromAndWhere += "and region_comany_id='"+region_comany_id+"' ";
			}
			if(!city_comany_id.equals("")) {
				fromAndWhere += "and city_comany_id='"+city_comany_id+"' ";
			}
			if(!metering_type.equals("")) {
				fromAndWhere += "and metering_type_id='"+metering_type+"' ";
			}
			if(!specialty.equals("")) {
				fromAndWhere += "and specialty='"+specialty+"' ";
			}
		}
		
		String sql = "";
		if(pageSize == -1) {
			sql = "select skip "+skip+" ";
		} else {
			sql = "select skip "+skip+" limit "+pageSize+" ";
		}
		sql +=  " s_year ,"+       
				"s_mon ,"+              
				"soa_get_deptname(province_comany_id,'province') as deptname_province,"+              
				"soa_get_deptname(region_comany_id,'region') as deptname_region,"+              
				"soa_get_deptname(city_comany_id,'city') as deptname_city,"+              
				"res_name ,"+              
				"sheetid ,"+              
				"sheet_sysid ,"+              
				"sheet_title,"+              
				"metering_type,"+              
				"specialty_name ,"+              
				"nvl(result_for_res_type,0)+"+
				"nvl(result_for_res_level,0)+"+
				"nvl(result_for_dev_type,0)+"+
				"nvl(result_for_env,0)+"+
				"nvl(result_for_region,0)+"+
				"nvl(result_for_intime,0)+"+
				"nvl(result_for_archive,0) as tot_num,"+              
				"res_type_name ,"+              
				"nvl(result_for_res_type,0) as result_for_res_type,"+              
				"res_level_name ,"+              
				"nvl(result_for_res_level,0) as result_for_res_level,"+              
				"dev_type_name ,"+              
				"nvl(result_for_dev_type,0) as result_for_dev_type,"+              
				"env_features_name ,"+              
				"nvl(result_for_env,0) as result_for_env,"+              
				"region_type_name ,"+              
				"nvl(result_for_region ,0) as result_for_region,"+       
				"intime_level,"+              
				"nvl(result_for_intime,0) as result_for_intime,"+             
				"archive_level ,"+              
				"nvl(result_for_archive,0) as result_for_archive,"+          
				"soa_get_username(operateuserid) as username "+fromAndWhere;
		String countSql = "select count(*) " + fromAndWhere;
		
		Map<String,Object> map = getDataSynchResultList(sql,countSql);
		List<Map<String,Object>> resultList = (List<Map<String,Object>>)map.get("result");
		int count = (Integer)map.get("count");
		
		request.setAttribute("pnrResMeteringList",resultList);
		request.setAttribute("pagesize", pageSize);
		request.setAttribute("size", count);
		
		//ActionForward forward = new ActionForward("WEB-INF/pages/partner/netresource/pnrResMeteringList.jsp",true);
		
		return mapping.findForward("pnrResMeteringList");
	}
	
	public Map<String,Object> getDataSynchResultList(String sql,String countSql) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		DataSynchJdbcUtil jdbcUtil = (DataSynchJdbcUtil)ApplicationContextHolder.getInstance().getBean("dataSynchJdbcUtil");
		Connection conn = null;
		Statement stmt = null;
		List<Map<String,Object>> resultList = null;
		try {
			conn = jdbcUtil.getCon();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			resultList = DataSynchJdbcUtil.resultSet2ListMap(rs);
			
			map.put("result", resultList);
			
			ResultSet crs = stmt.executeQuery(countSql);
			int count = 0;
			while(crs.next()) {
				count = crs.getInt(1);
				break;
			}
			
			map.put("count", count);
			
			
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
		return map;
	}
	public List<Map<String,Object>> getPnrResMeteringDetail(String eventId) {
		String whereString = " where sheet_sysid='"+eventId+"'";
		String sql = "select  s_year ,"+       
				"s_mon ,"+              
				"soa_get_deptname(province_comany_id,'province') as deptname_province,"+              
				"soa_get_deptname(region_comany_id,'region') as deptname_region,"+              
				"soa_get_deptname(city_comany_id,'city') as deptname_city,"+              
				"res_name ,"+              
				"sheetid ,"+              
				"sheet_title,"+              
				"metering_type,"+              
				"specialty_name ,"+              
				"nvl(result_for_res_type,0)+"+
				"nvl(result_for_res_level,0)+"+
				"nvl(result_for_dev_type,0)+"+
				"nvl(result_for_env,0)+"+
				"nvl(result_for_region,0)+"+
				"nvl(result_for_intime,0)+"+
				"nvl(result_for_archive,0) as tot_num,"+              
				"res_type_name ,"+              
				"nvl(result_for_res_type,0) as result_for_res_type,"+              
				"res_level_name ,"+              
				"nvl(result_for_res_level,0) as result_for_res_level,"+              
				"dev_type_name ,"+              
				"nvl(result_for_dev_type,0) as result_for_dev_type,"+              
				"env_features_name ,"+              
				"nvl(result_for_env,0) as result_for_env,"+              
				"region_type_name ,"+              
				"nvl(result_for_region ,0) as result_for_region,"+       
				"intime_level,"+              
				"nvl(result_for_intime,0) as result_for_intime,"+             
				"archive_level ,"+              
				"nvl(result_for_archive,0) as result_for_archive,"+             
				"soa_get_username(operateuserid) as username,"+
				"weight_for_res_type,"+
				"adjnum_for_res_type,"+
				"weight_for_res_level,"+
				"adjnum_for_res_level,"+
				"weight_for_dev_type,"+
				"adjnum_for_dev_type,"+
				"weight_for_env,"+
				"adjnum_for_env,"+
				"weight_for_region,"+
				"adjnum_for_region,"+
				"weight_for_intime,"+
				"adjnum_for_intime,"+
				"weight_for_archive,"+
				"adjnum_for_archive"+
				" from pnr_res_metering " + whereString; 
		
		List<Map<String,Object>> resultList = null;
		
		if(!"".equals(eventId)) {
			DataSynchJdbcUtil jdbcUtil = (DataSynchJdbcUtil)ApplicationContextHolder.getInstance().getBean("dataSynchJdbcUtil");
			Connection conn = null;
			Statement stmt = null;
			try {
				conn = jdbcUtil.getCon();
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
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
		}
		
		return resultList;
	}
	
	/**
	 * 统计表
	 * @author huhao
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward allFindSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
		.getInstance().getBean("commonSpringJdbcService");
		String seeOnly=StaticMethod.nullObject2String(request.getParameter("seeOnly"));
		String requestAreaId=StaticMethod.nullObject2String(request.getParameter("areaId"));
		String areaIdSearch=StaticMethod.nullObject2String(request.getParameter("areaIdSearch"));
		String requestCompanyId=StaticMethod.nullObject2String(request.getParameter("maintainCompanyId"));
		String maintainCompany=StaticMethod.nullObject2String(request.getParameter("maintainCompany"));
		String searchType=StaticMethod.nullObject2String(request.getParameter("statisticsItems"));
		String holdyear=StaticMethod.nullObject2String(request.getParameter("year"));
		String holdmonth=StaticMethod.nullObject2String(request.getParameter("month"));
		String serchFor=StaticMethod.nullObject2String(request.getParameter("searchFor"));
		String checkedIdsStr=StaticMethod.null2String(request.getParameter("checkedIds"));
//		String copyRequestAreaId=requestAreaId;
		if("".equals(serchFor)&&!"".equals(requestAreaId)){
			requestAreaId=requestAreaId.substring(0, requestAreaId.length()-2);
		}
		String userId = getUserId(request);
		TawSystemSessionForm sysuser = this.getUser(request);
		PartnerUserMgr  partnerUserMgr = (PartnerUserMgr)getBean("partnerUserMgr");
		PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);
		String level="";
		if(user!=null){//代维公司人员
			//此时按代维公司进行统计任务的完成情况
			String deptId=user.getDeptId();
			request.setAttribute("rootDeptId", deptId);
			PartnerDeptMgr  partnerDeptMgr = (PartnerDeptMgr)getBean("partnerDeptMgr");
			String hql="deptMagId= '"+deptId+"' ";
			List<PartnerDept> deptList=partnerDeptMgr.getPartnerDeptsByHql(hql);
			if(deptList!=null&&!deptList.isEmpty()){
				PartnerDept partner=(PartnerDept)deptList.get(0);
				 level=partner.getDeptLevel();
				 if(level.equals("4")){
				 request.setAttribute("level", "end");
				 }
				if("".equals(requestAreaId)){
				requestAreaId=partner.getAreaId();
				}
				if("".equals(requestCompanyId)){
					requestCompanyId=partner.getDeptMagId();	
				}
				searchType="allSearch";
			}
			else{
				return mapping.findForward("nopriv");	
			}
		}else{//移动公司人
			ITawSystemDeptManager deptmanager=(ITawSystemDeptManager)getBean("ItawSystemDeptManager");
			TawSystemDept dd=deptmanager.getDeptinfobydeptid(sysuser.getDeptid(), "0");
			if(null!=dd){
				if("".equals(requestAreaId)){
					requestAreaId=dd.getAreaid();
					if(requestAreaId.length()==2){
						requestAreaId="";
					}
				}
			}
			else{
				return mapping.findForward("nopriv");	
			}
		}
		
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String datetime = tempDate.format(new java.util.Date());
		if("".equals(holdyear)||"".equals(holdmonth)){
			holdyear=datetime.substring(0, 4);
			holdmonth=datetime.substring(5, 7);
			holdmonth=String.valueOf(Integer.parseInt(holdmonth));
		}
		String sql="";
		if("".equals(searchType)){
		/*-----begin区域sql-----*/
		 sql=" SELECT " 
		+" 	areaevent.areaid, "
		+" 	areaevent.areaname, "
		+" 	areaevent.dictid, "
		+" 	areaevent.dictname, "
		+" 	areaevent.eventtypes, "
		+" 	areaevent.eventtypesname, "
		+" 	nvl(holdyear,"+holdyear+") holdyear ,  "//--当选择年时，这里默认为所选择的年
		+" 	nvl(holdmonth,"+holdmonth+") holdmonth,  "//--当选择月是，这里默认为所选择的月
//		+" 	nvl(M.price,0) price, "
		+" 	COUNT(M.eventkey) counts, "
		+" 	nvl(SUM(M.eventnumber),0) eventtimes, "
		+" 	nvl(SUM(M.charging),0) charging "
		+" FROM "
		+" 	(	SELECT "
		+" 			areaid, "
		+" 			areaname, "
		+" 			dicttypes.* "
		+" 		FROM "
		+" 			taw_system_area area "
		+" 			LEFT JOIN (	SELECT "
		+" 							dictid, "
		+" 							dictname, "
		+" 							ts.eventtypes, "
		+" 							CASE "
		+" 								WHEN ts.eventtypes='001' "
		+" 								THEN '故障处理' "
		+" 								WHEN ts.eventtypes='002' "
		+" 								THEN '投诉处理' "
		+" 								WHEN ts.eventtypes='001' "
		+" 								THEN '故障处理' "
		+" 								WHEN ts.eventtypes='002' "
		+" 								THEN '投诉处理' "
		+" 								WHEN ts.eventtypes='003' "
		+" 								THEN '网络变更调整' "
		+" 								WHEN ts.eventtypes='004' "
		+" 								THEN '应急保障' "
		+" 								WHEN ts.eventtypes='006' "
		+" 								THEN '网络优化实施' "
		+" 								WHEN ts.eventtypes='007' "
		+" 								THEN '工程验收与交维' "
		+" 								WHEN ts.eventtypes='008' "
		+" 								THEN '随工任务' "
		+" 								WHEN ts.eventtypes='010' "
		+" 								THEN '其他任务' "
		+" 								WHEN ts.eventtypes='013' "
		+" 								THEN '业务咨询' "
		+" 								WHEN ts.eventtypes='005' "
		+" 								THEN '发电保障' "
		+" 								WHEN ts.eventtypes='009' "
		+" 								THEN '资源核查' "
		+" 								WHEN ts.eventtypes='012' "
		+" 								THEN '隐患处理' "
		+" 								WHEN ts.eventtypes='011' "
		+" 								THEN '巡检任务' "
		+" 								ELSE '未知类型' "
		+" 							END eventtypesname "
		+" 						FROM "
		+" 							taw_system_dicttype d "
		+" 							LEFT JOIN (	SELECT "
		+" 											CASE "
		+" 												WHEN tabid<10 "
		+" 												THEN '00'||tabid "
		+" 												ELSE '0'||tabid "
		+" 											END eventtypes "
		+" 										FROM "
		+" 											systables s "
		+" 										WHERE "
		+" 											tabid<=13 "
		+" 							) "
		+" 							ts "
		+" 							ON 1=1 "
		+" 						WHERE "
		+" 							d.parentdictid='11225' "
		+" 						ORDER BY "
		+" 							d.dictid, "
		+" 							ts.eventtypes "
		+" 			) "
		+" 			dicttypes "
		+" 			ON 1=1 "
		+" 		WHERE ";
	if("".equals(areaIdSearch)){
		if("".equals(requestAreaId)){
			sql=sql+" 			areaid ='28' ";//----如果是要看地市，则应该是 areaid in 地域id
		}
		else if(!"".equals(requestAreaId)){
			sql=sql+" 			parentareaid ='"+requestAreaId+"' ";
		}
	}
	else{
		sql=sql+" 			areaid ='"+areaIdSearch+"' ";
	}
			sql=sql+" 	) "
		+" 	areaevent "
		+" 		LEFT JOIN pnr_metering_main M "
		+" 		ON M.eventtype=areaevent.eventtypes AND "
		+" 		areaevent.dictid=M.specialty ";
			
	if("".equals(areaIdSearch)){		
		if("".equals(requestAreaId)){
			sql=sql+" 		and m.province=areaevent.areaid  ";//--省
			request.setAttribute("displayLevel", "1");
		}
		else if(!"".equals(requestAreaId)&&requestAreaId.length()==2){
			sql=sql+" 		 AND M.city=areaevent.areaid  ";//--地市时使用
			request.setAttribute("displayLevel", "2");
		}
		else if(!"".equals(requestAreaId)&&requestAreaId.length()==4){
			sql=sql+" 		  and m.country=areaevent.areaid  ";//--区县时使用
			request.setAttribute("displayLevel", "3");
			request.setAttribute("deptlevel", "end");
		}
			}
			else{
				if(!"".equals(areaIdSearch)&&areaIdSearch.length()==2){
					sql=sql+" 		and m.province=areaevent.areaid  ";//--省
					request.setAttribute("displayLevel", "1");
				}
				else if(!"".equals(areaIdSearch)&&areaIdSearch.length()==4){
					sql=sql+" 		 AND M.city=areaevent.areaid  ";//--地市时使用
					request.setAttribute("displayLevel", "2");
				}
				else if(!"".equals(areaIdSearch)&&areaIdSearch.length()==6){
					sql=sql+" 		  and m.country=areaevent.areaid  ";//--区县时使用
					request.setAttribute("displayLevel", "3");
					request.setAttribute("deptlevel", "end");
				}
			}
		sql=sql+" 		AND "
		+" 		holdyear="+holdyear+" AND "
		+" 		holdmonth="+holdmonth+" and mainifcharging=1  "//--当选择年和月的时候在这里拼条件
		+" 		 "
		+" 	GROUP BY "
		+" 		areaevent.areaid, "
		+" 		areaevent.areaname, "
		+" 		areaevent.dictid, "
		+" 		areaevent.dictname, "
		+" 		areaevent.eventtypes, "
		+" 		areaevent.eventtypesname, "
		+" 		holdyear, "
		+" 		holdmonth "
//		+" 		price "
		+" 	ORDER BY "
		+" 		areaevent.areaid, "
		+" 		areaevent.areaname, "
		+" 		areaevent.dictid, "
		+" 		areaevent.dictname, "
		+" 		areaevent.eventtypes, "
		+" 		areaevent.eventtypes, "
		+" 		areaevent.eventtypesname, "
		+" 		holdyear, "
		+" 		holdmonth ";
//		+" 		price ";
		/*-----end区域sql-----*/
		}
		else if("allSearch".equals(searchType)){
		/*-----begin区域+代维公司sql-----*/
		 sql=" SELECT " 
			+" areaevent.county_id areaid, areaevent.area_name areaname, ";
			if("allSearch".equals(searchType)){
				sql=sql+" areaevent.dept_mag_id, areaevent.name, ";
			}
			sql=sql+" areaevent.dictid, "
			+" areaevent.dictname, "
			+" areaevent.eventtypes, "
			+" areaevent.eventtypesname, ";
			sql=sql+"  "	;
//			+" nvl(holdyear,2013) holdyear , "// --当选择年时，这里默认为所选择的年
//			+" nvl(holdmonth,1) holdmonth,  "//--当选择月是，这里默认为所选择的月
			sql=sql+"  "
			+" 	nvl(M.price,0) price, "
			+" COUNT(M.eventkey) counts, "
			
			+" nvl(SUM(M.eventnumber),0) eventtimes, "
			+" nvl(SUM(M.charging),0) charging "
			+" FROM "
			+" (SELECT "
			+" dept.county_id, dept.area_name, "
			+" dept.dept_mag_id, "
			+" dept.name, "
			+" dicttypes.* "
			+" FROM "
			+" pnr_dept dept "
			+" LEFT JOIN ( SELECT "
			+" dictid, "
			+" dictname, "
			+" ts.eventtypes, "
			+" CASE "
			+" WHEN ts.eventtypes='001' "
			+" THEN '故障处理' "
			+" WHEN ts.eventtypes='002' "
			+" THEN '投诉处理' "
			+" WHEN ts.eventtypes='003' "
			+" THEN '网络变更调整' "
			+" WHEN ts.eventtypes='004' "
			+" THEN '应急保障' "
			+" WHEN ts.eventtypes='006' "
			+" THEN '网络优化实施' "
			+" WHEN ts.eventtypes='007' "
			+" THEN '工程验收与交维' "
			+" WHEN ts.eventtypes='008' "
			+" THEN '随工任务' "
			+" WHEN ts.eventtypes='010' "
			+" THEN '其他任务' "
			+" WHEN ts.eventtypes='013' "
			+" THEN '业务咨询' "
			+" WHEN ts.eventtypes='005' "
			+" THEN '发电保障' "
			+" WHEN ts.eventtypes='009' "
			+" THEN '资源核查' "
			+" WHEN ts.eventtypes='012' "
			+" THEN '隐患处理' "
			+" WHEN ts.eventtypes='011' "
			+" THEN '巡检任务' "
			+" ELSE '未知类型' "
			+" END eventtypesname "
			+" FROM "
			+" taw_system_dicttype d "
			+" LEFT JOIN ( SELECT "
			+" CASE "
			+" WHEN tabid<10 "
			+" THEN '00'||tabid "
			+" ELSE '0'||tabid "
			+" END eventtypes "
			+" FROM "
			+" systables s "
			+" WHERE "
			+" tabid<=13 "
			+" ) "
			+" ts "
			+" ON 1=1 "
			+" WHERE "
			+" d.parentdictid='11225' "
			+" ORDER BY "
			+" d.dictid, "
			+" ts.eventtypes "
			+" ) "
			+" dicttypes "
			+" ON 1=1 "
			+" "
			+" WHERE ";
			if(!"".equals(areaIdSearch)){
				requestAreaId=areaIdSearch.substring(0, areaIdSearch.length()-2);	
			}
			if("".equals(requestAreaId)){
				sql=sql+" dept.deptlevel=1  ";// --省级代维公司
				request.setAttribute("displayLevel", "1");
			}
			else if(!"".equals(requestAreaId)&&requestAreaId.length()==2){
				sql=sql+" dept.deptlevel=2  ";// --地市级代维公司
				request.setAttribute("displayLevel", "2");
			}
			else if(!"".equals(requestAreaId)&&requestAreaId.length()==4){
				sql=sql+"  dept.deptlevel=3  ";// --区县级代维公司
				request.setAttribute("displayLevel", "3");
			}
			else if(!"".equals(requestAreaId)&&requestAreaId.length()==6){
				sql=sql+"  dept.deptlevel=4  ";//--代维小组级代维公司  
				request.setAttribute("deptlevel", "end");
				request.setAttribute("displayLevel", "4");
			}
			 if(!"".equals(requestAreaId)&&requestAreaId.length()==4){
			sql=sql+" and dept.city_id= '"+requestAreaId+"' "; //地域条件在这里拼,切记代维公司不同的层级这个county_id就是不同的地市、区县的地域id
			}
			 else if(!"".equals(requestAreaId)&&requestAreaId.length()==6){
				sql=sql+" and dept.county_id= '"+requestAreaId+"' "; //地域条件在这里拼,切记代维公司不同的层级这个county_id就是不同的地市、区县的地域id
				}
			if(!"".equals(requestCompanyId)){
	        sql=sql+"  and dept.dept_mag_id like '"+requestCompanyId+"%' ";//代维公司条件在这里拼，如果只是移动公司人钻取地市，那么不加这个条件，钻代维公司才加
			}
			if(!"".equals(areaIdSearch)){
				sql=sql+" and DEPT.COUNTY_ID='"+areaIdSearch+"' ";
			}
			sql=sql+" and dept.deleted='0' ) "
			+" areaevent "
			+" LEFT JOIN pnr_metering_main M "
			+" ON M.eventtype=areaevent.eventtypes AND "
			+" areaevent.dictid=M.specialty ";
				if("".equals(requestAreaId)){
					sql=sql+"and areaevent.dept_mag_id=m.provincedeptid ";//--省级代维公司 
				}
				else	if(!"".equals(requestAreaId)&&requestAreaId.length()==2){
					sql=sql+" and areaevent.dept_mag_id= m.citydeptid ";// --地市级代维公司 
				}
				else	if(!"".equals(requestAreaId)&&requestAreaId.length()==4){
						sql=sql+" and areaevent.dept_mag_id = m.countrydeptid  ";//--区县代维公司
				}
				else	if(!"".equals(requestAreaId)&&requestAreaId.length()==6){
							sql=sql+" and areaevent.dept_mag_id=m.groupdeptid  ";//--代维小组
					}
					sql=sql+" AND "
			+" holdyear="+holdyear+" AND "
			+" holdmonth="+holdmonth+" and mainifcharging=1  "//--当选择年和月的时候在这里拼条件
			+" GROUP BY "
			+" areaevent.county_id, areaevent.area_name, ";
			if("allSearch".equals(searchType)){
			sql=sql+" areaevent.dept_mag_id,areaevent.name, ";
			}
			sql=sql+" areaevent.dictid, "
			+" areaevent.dictname, "
			+" areaevent.eventtypes, "
			+" areaevent.eventtypesname ,price"
			
			+" ORDER BY "
			+" areaevent.county_id, areaevent.area_name ,";
			if("allSearch".equals(searchType)){
				sql=sql+" areaevent.dept_mag_id,areaevent.name, ";
			}
			sql=sql+" areaevent.dictid, "
			+" areaevent.dictname, "
			+" areaevent.eventtypes, "
			+" areaevent.eventtypes, "
			+" areaevent.eventtypesname ,price";
		}
			/*-----end区域+代维公司sql-----*/
		if("".equals(sql)){
			return mapping.findForward("nopriv");
		}
		System.out.println("首页工单统计Sql为："+sql);
		List<ListOrderedMap> findList = jdbcService.queryForList(sql);
		int i=findList.size()/13;
		List allList=new ArrayList();
		TreeMap<String,String> allTypeMap=new TreeMap<String,String>();
		List chargingTotalEndList=new ArrayList();
		/**
		 * 行转列，并小计
		 */
		List<List<String>> chargingTotalList=new ArrayList<List<String>>();
		List<List<String>> countsTotalList=new ArrayList<List<String>>();
		List<List<String>> eventtimesTotalList=new ArrayList<List<String>>();
		if(i>1||i==1){
			for(int j=0;j<i;j++){
				List list=new ArrayList();
				List<ListOrderedMap> removeList=new ArrayList<ListOrderedMap>();
				List<String> chargingCountList=new ArrayList<String>();
				List<String> countsCountList=new ArrayList<String>();
				List<String> eventtimesCountList=new ArrayList<String>();
				for(int ii=0;ii<13;ii++){
					ListOrderedMap map =findList.get(ii);
					if(ii==0){
						 String areaid=StaticMethod.nullObject2String(map.get("areaid"));
						 list.add(areaid);
						 String areaname=StaticMethod.nullObject2String(map.get("areaname"));
						 list.add(areaname);
						 if("allSearch".equals(searchType)){ 
						 String dept_mag_id=StaticMethod.nullObject2String(map.get("dept_mag_id"));
						 list.add(dept_mag_id);
						 String name=StaticMethod.nullObject2String(map.get("name"));
						 list.add(name);
						 }
						 String dictid=StaticMethod.nullObject2String(map.get("dictid"));
						 list.add(dictid);
						 String dictname=StaticMethod.nullObject2String(map.get("dictname"));
						 list.add(dictname);
						 System.out.println("开始载入地市为"+areaid+","+areaname+" 专业为"+dictid+","+dictname+" 统计数据");
					}
					String eventtypes = StaticMethod.nullObject2String(map.get("eventtypes"));
					allTypeMap.put(eventtypes, eventtypes);
					String counts=StaticMethod.nullObject2String(map.get("counts"));
					if("".equals(counts)||"0.000".equals(counts)){
						counts="0";
					}
					countsCountList.add(counts);
					 list.add(counts);
					 String eventtimes=StaticMethod.nullObject2String(map.get("eventtimes"));
						if("".equals(eventtimes)||"0.000".equals(eventtimes)){
							eventtimes="0";
						}
						eventtimesCountList.add(eventtimes);
					 list.add(eventtimes);
					 if("allSearch".equals(searchType)){
					 String price=StaticMethod.nullObject2String(map.get("price"));
						if("".equals(price)||"0.000".equals(price)){
							price="0";
						}
					 list.add(price);
					 }
					 String charging=StaticMethod.nullObject2String(map.get("charging"));
						if("".equals(charging)||"0.000".equals(charging)){
							charging="0";
						}
						chargingCountList.add(charging);
					 list.add(charging);
					 removeList.add(map);
					 if(ii==12){
						 System.out.println("该地市专业 统计数据载入完毕"); 
					 }
				}
				countsTotalList.add(countsCountList);
				eventtimesTotalList.add(eventtimesCountList);
				chargingTotalList.add(chargingCountList);
				double chargingCountChild =0;
				for(int t=0;t<chargingCountList.size();t++){
					 BigDecimal   big=BigDecimal.valueOf(chargingCountChild);
						BigDecimal   newBig=BigDecimal.valueOf(Double.valueOf(chargingCountList.get(t)));
						chargingCountChild=big.add(newBig).doubleValue();
//					countChild=countChild+Float.valueOf(countList.get(t));
				}
				String newchargingCountChild=HomeHelper.formatObject(String.valueOf(chargingCountChild),3);
				list.add(newchargingCountChild);
				allList.add(list);
				findList.removeAll(removeList);
			}
			/**
			 * 计算各地市合计
			 */
			String dictSql="select count(*) as dictCount from TAW_SYSTEM_DICTTYPE where PARENTDICTID='11225'";
			List<ListOrderedMap> dictCount = jdbcService.queryForList(dictSql);
			int dictCountFor=0;
			if(!dictCount.isEmpty()){
				String count=StaticMethod.nullObject2String(dictCount.get(0).get("dictCount"));
				if(!"".equals(count)){
					dictCountFor=Integer.parseInt(count);
					System.out.println("根据"+count+"个专业来进行分类统计     sql为"+dictSql);
				}
				else{
					System.out.println("专业为空！     sql为"+dictSql);
				}
			}
			List copyList=new ArrayList();
			copyList.addAll(allList);
			int j=copyList.size()/dictCountFor;
			List totalCount=new ArrayList();
			if(j>1||j==1){
		     for(int tt=0;tt<j;tt++){
		    	 List removeList=new ArrayList();
		    	 double getCount=0.f;
		    	 for(int rr=0;rr<dictCountFor;rr++){
		    	 List getCountList=(List)copyList.get(rr);
		    	 
		    	 BigDecimal   big=BigDecimal.valueOf(getCount);
					BigDecimal   newBig=BigDecimal.valueOf(Double.valueOf(getCountList.get(getCountList.size()-1).toString()));
					getCount=big.add(newBig).doubleValue();
//		    	 getCount=getCount+Float.valueOf(getCountList.get(getCountList.size()-1).toString());
		    	  removeList.add(getCountList);
		    	 }
		    		String newgetCount=HomeHelper.formatObject(String.valueOf(getCount),3);
		    	 totalCount.add(newgetCount) ;
		    	 copyList.removeAll(removeList); 
		     }
		     int w=0;
		     for(int ii=0;ii<allList.size();ii++){
		    		 List getAllListChild=(List)allList.get(ii);
		    		 getAllListChild.add(totalCount.get(w));
		    	 if((ii+1)%dictCountFor==0&&ii!=0&&(w+1)<totalCount.size()){
		    		 w++;
		    	 }
		     }
			}
			/**
			 * 总计
			 */
//			List<String> countsChild=countsTotalList.get(0);
//			List<String> eventtimesChild=eventtimesTotalList.get(0);
			List<String> chargingChild=chargingTotalList.get(0);
//			int countsChildSize=countsChild.size();
//			int eventtimesSize=eventtimesChild.size();
//			int chargingChildSize=chargingChild.size();
			for(int x=0;x<chargingChild.size();x++){
				int getCountsCount=0;
				double getEventtimesCount=0;
				double getChargingCount=0;
//				BigDecimal   b1   =   BigDecimal.valueOf(f0);  
				for(int xx=0;xx<chargingTotalList.size();xx++){
					
					List<String>  chargingList=chargingTotalList.get(xx);
					BigDecimal   bigCharging=BigDecimal.valueOf(getChargingCount);
					BigDecimal   newBigCharging=BigDecimal.valueOf(Double.valueOf(chargingList.get(x).toString()));
					getChargingCount=bigCharging.add(newBigCharging).doubleValue();
					
					List<String>  countsList=countsTotalList.get(xx);
					BigDecimal   bigCounts=BigDecimal.valueOf(getCountsCount);
					BigDecimal   newBigCounts=BigDecimal.valueOf(Double.valueOf(countsList.get(x).toString()));
					getCountsCount=bigCounts.add(newBigCounts).intValue();
					
					List<String>  eventtimesList=eventtimesTotalList.get(xx);
					BigDecimal   bigEventtimes=BigDecimal.valueOf(getEventtimesCount);
					BigDecimal   newBigEventtimes=BigDecimal.valueOf(Double.valueOf(eventtimesList.get(x).toString()));
//					DecimalFormat df=new DecimalFormat("0.000");
//					String newDf=df.format(bigEventtimes.add(newBigEventtimes).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()).toString();
//					getEventtimesCount=Double.valueOf(newDf);
					getEventtimesCount=bigEventtimes.add(newBigEventtimes).doubleValue();
				}
//				String newgetCountsCount=HomeHelper.formatObject(String.valueOf(getCountsCount),3);
//				chargingTotalEndList.add(newgetCountsCount);
				chargingTotalEndList.add(getCountsCount);
//				chargingTotalEndList.add(getEventtimesCount);
				String newgetEventtimesCount=HomeHelper.formatObject(String.valueOf(getEventtimesCount),3);
				chargingTotalEndList.add(newgetEventtimesCount);
				 if("allSearch".equals(searchType)){
				chargingTotalEndList.add("-");
				 }
				 String newgetChargingCount=HomeHelper.formatObject(String.valueOf(getChargingCount),3);
					chargingTotalEndList.add(newgetChargingCount);
//				chargingTotalEndList.add(getChargingCount);
			}
			double getChargingTotalCount=0;
			for(int xxx=0;xxx<totalCount.size();xxx++){
				BigDecimal   big=BigDecimal.valueOf(getChargingTotalCount);
				BigDecimal   newBig=BigDecimal.valueOf(Double.valueOf(totalCount.get(xxx).toString()));
				getChargingTotalCount=big.add(newBig).doubleValue();
//				getChargingTotalCount=getChargingTotalCount+Float.valueOf(totalCount.get(xxx).toString());
			}
			String newgetChargingTotalCount=HomeHelper.formatObject(String.valueOf(getChargingTotalCount),3);
			chargingTotalEndList.add(newgetChargingTotalCount);
//			chargingTotalEndList.add(getChargingTotalCount);
		}
		Set keySet = allTypeMap.keySet();
		Iterator it = keySet.iterator();
		List<String> allTypeList = new ArrayList<String>();
		while(it.hasNext()){
			String type = StaticMethod.nullObject2String(it.next());
			allTypeList.add(type);
		}
		int colNum=0;
		int colMax=0;
		int toCol=0;
		if("allSearch".equals(searchType)){
			 colNum=4;
			 colMax=60;
			 toCol=4;
		}
		else if("".equals(searchType)){
			 colNum=2;
			 colMax=45;
			 toCol=2;
		}
		List<List<TdObjModel>> allMap=HomeHelper.verticalGrowp(colNum,colMax,toCol,allList);
		request.setAttribute("tableList", allMap);
		request.setAttribute("seeOnly", seeOnly);
		request.setAttribute("allTypeList", allTypeList);
		request.setAttribute("chargingTotalEndList", chargingTotalEndList);
		request.setAttribute("allList", allList);
		request.setAttribute("searchType", searchType);
		request.setAttribute("areaStringLike", areaIdSearch);
		request.setAttribute("maintainCompanyId", requestCompanyId);
		request.setAttribute("maintainCompany", maintainCompany);
		request.setAttribute("year1", holdyear);
		request.setAttribute("month1", holdmonth);
		request.setAttribute("checkedIdsStr", checkedIdsStr);
		return mapping.findForward("allFindSearch");
	}
}