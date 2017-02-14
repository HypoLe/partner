package com.boco.eoms.commons.log.webapp.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;

import com.boco.eoms.commons.log.dao.TawCommonLogOperatorDao;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;

public class TawCommonLogStatAction extends BaseAction {

	// private TawCommonLogOperatorDao tawCommonLogOperatorDao;

	public ActionForward execute(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward myforward = null;
		String myaction = mapping.getParameter();

		if ("LOGSTAT".equalsIgnoreCase(myaction)) { // 所有未处理工单
			myforward = performListStat(mapping, actionForm, request, response);
		} else if ("LOGSTATDEPT".equalsIgnoreCase(myaction)) {
			myforward =performListDeptStat(mapping, actionForm, request, response);
		} else if ("LOGSTATUSER".equalsIgnoreCase(myaction)) {
			myforward =performListUserStat(mapping, actionForm, request, response);
		} else if ("LOGSTATALL".equalsIgnoreCase(myaction)) {
			myforward =performListAllStat(mapping, actionForm, request, response);
		}

		return myforward;

	}

	@SuppressWarnings("unchecked")
	private ActionForward performListStat(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {

		List<TawSystemDept> deptarray = getAlldepts();
//		String[] deptarray = { "1", "14", "15", "16", "17", "18", "19", "20",
//				"21", "22" };
//		String[] deptname = { "省公司", "贵阳", "遵义", "安顺", "黔南分公司", "黔东南分公司",
//				"铜仁分公司", "毕节分公司", "六盘水分公司", "黔西南分公司" };
		String depts = "";
		String deptcount = "";
		String usercount = "";
		String allconunt = "";
		List list = new ArrayList();
		try {
			Map formMap = ((DynaActionForm) actionForm).getMap();

//			String starttime = (String) formMap.get("searchbystarttime");
//			String endtime = (String) formMap.get("searchbyendtime");
			String starttime = StaticMethod.nullObject2String(request.getParameter("searchbystarttime"));
			String endtime = StaticMethod.nullObject2String(request.getParameter("searchbyendtime"));

			if (!starttime.equals("") && starttime != null) {
				starttime = starttime + " 00:00:00";
			} else {
				starttime = "";
			}
			// !"".equals(endtime)
			if (!endtime.equals("") && endtime != null) {
				endtime = endtime + " 23:59:59";
			} else {
				endtime = "";
			}
			TawCommonLogOperatorDao dao = (TawCommonLogOperatorDao) ApplicationContextHolder
					.getInstance().getBean("tawCommonLogOperatorDao");
			// 部门登录数
			List deptcountList = dao.getAlldeptcount("", starttime, endtime);
			java.util.HashMap deptCountMap = new java.util.HashMap();
			for(int i = 0;deptcountList!=null && i<deptcountList.size();i++){
				Object[] arr = (Object[])deptcountList.get(i);
				String deptId = StaticMethod.nullObject2String(arr[1]);
				String count = StaticMethod.nullObject2String(arr[0],"0");
				deptCountMap.put(deptId, count);
			}
			// 用户登录数
			List usercountList = dao.getAllusercount("", starttime, endtime);
			java.util.HashMap userCountMap = new java.util.HashMap();
			for(int i = 0;usercountList!=null && i<usercountList.size();i++){
				Object[] arr = (Object[])usercountList.get(i);
				String deptId = StaticMethod.nullObject2String(arr[1]);
				String count = StaticMethod.nullObject2String(arr[0],"0");
				userCountMap.put(deptId, count);
			}
			
			// 总登录数
			List allconuntList = dao.getAllcount("", starttime, endtime);
			java.util.HashMap allCountMap = new java.util.HashMap();
			for(int i = 0;allconuntList!=null && i<allconuntList.size();i++){
				Object[] arr = (Object[])allconuntList.get(i);
				String deptId = StaticMethod.nullObject2String(arr[1]);
				String count = StaticMethod.nullObject2String(arr[0],"0");
				allCountMap.put(deptId, count);
			}
			for (int i = 0; i < deptarray.size(); i++) {
				List listTemp = new ArrayList();
//				depts = dao.getAllwelcomebyone(deptarray[i]);
				
				String deptid = deptarray.get(i).getDeptId().trim();
				listTemp.add(deptarray.get(i).getDeptName());
				if(deptCountMap.containsKey(deptid)){
					listTemp.add(StaticMethod.nullObject2String(deptCountMap.get(deptid)));
				}else{
					listTemp.add("0");
				}
				if(userCountMap.containsKey(deptid)){
					listTemp.add(StaticMethod.nullObject2String(userCountMap.get(deptid)));
				}else{
					listTemp.add("0");
				}
				if(allCountMap.containsKey(deptid)){
					listTemp.add(StaticMethod.nullObject2String(allCountMap.get(deptid)));
				}else{
					listTemp.add("0");
				}
				list.add(listTemp);
			}

			request.setAttribute("list", list);
			request.setAttribute("starttime", starttime);
			request.setAttribute("endtime", endtime);

		} catch (Exception e) {

			BocoLog.error(this, "查询错误 " + e.getMessage());
			return mapping.findForward("failure");

		}
		return mapping.findForward("success");

	}

	private ActionForward performListDeptStat(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {

		List<TawSystemDept> deptarray = getAlldepts();
//		String[] deptarray = { "1", "14", "15", "16", "17", "18", "19", "20",
//				"21", "22" };
//		String[] deptname = { "省公司", "贵阳", "遵义", "安顺", "黔南分公司", "黔东南分公司",
//				"铜仁分公司", "毕节分公司", "六盘水分公司", "黔西南分公司" };
		String depts = "";
		String deptcount = "";
		String usercount = "";
		String allconunt = "";
		List list = new ArrayList();
		try {
			Map formMap = ((DynaActionForm) actionForm).getMap();

			String starttime = (String)request.getParameter("searchbystarttime");
			String endtime = (String) request.getParameter("searchbyendtime");
			String flag = (String) request.getParameter("flag");

			if (!starttime.equals("") && starttime != null) {
				starttime = starttime + " 00:00:00";
			} else {
				starttime = "";
			}
			// !"".equals(endtime)
			if (!endtime.equals("") && endtime != null) {
				endtime = endtime + " 23:59:59";
			} else {
				endtime = "";
			}

			TawCommonLogOperatorDao dao = (TawCommonLogOperatorDao) ApplicationContextHolder
					.getInstance().getBean("tawCommonLogOperatorDao");

//			depts = dao.getAllwelcomebyone(deptarray[Integer.parseInt(flag)]);
//			depts = dao.getAllwelcomebyone(deptarray.get(Integer.parseInt(flag)).getDeptId());
			list = dao.getListdeptStat(deptarray.get(Integer.parseInt(flag)).getDeptId(), starttime, endtime);

			request.setAttribute("list", list);
			request.setAttribute("starttime", starttime);
			request.setAttribute("endtime", endtime);

		} catch (Exception e) {

			BocoLog.error(this, "查询错误 " + e.getMessage());
			return mapping.findForward("failure");

		}
		return mapping.findForward("success");

	}

	private ActionForward performListUserStat(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {

		List<TawSystemDept> deptarray = getAlldepts();
//		String[] deptarray = { "1", "14", "15", "16", "17", "18", "19", "20",
//				"21", "22" };
//		String[] deptname = { "省公司", "贵阳", "遵义", "安顺", "黔南分公司", "黔东南分公司",
//				"铜仁分公司", "毕节分公司", "六盘水分公司", "黔西南分公司" };
		String depts = "";
		String deptcount = "";
		String usercount = "";
		String allconunt = "";
		List list = new ArrayList();
		try {
			Map formMap = ((DynaActionForm) actionForm).getMap();

			String starttime = (String)request.getParameter("searchbystarttime");
			String endtime = (String) request.getParameter("searchbyendtime");
			String flag = (String) request.getParameter("flag");

			if (!starttime.equals("") && starttime != null) {
				starttime = starttime + " 00:00:00";
			} else {
				starttime = "";
			}
			// !"".equals(endtime)
			if (!endtime.equals("") && endtime != null) {
				endtime = endtime + " 23:59:59";
			} else {
				endtime = "";
			}

			TawCommonLogOperatorDao dao = (TawCommonLogOperatorDao) ApplicationContextHolder
					.getInstance().getBean("tawCommonLogOperatorDao");

//			depts = dao.getAllwelcomebyone(deptarray[Integer.parseInt(flag)]);
//			depts = dao.getAllwelcomebyone(deptarray.get(Integer.parseInt(flag)).getDeptId());
			list = dao.getListuserStat(deptarray.get(Integer.parseInt(flag)).getDeptId(), starttime, endtime);

			request.setAttribute("list", list);
			request.setAttribute("starttime", starttime);
			request.setAttribute("endtime", endtime);

		} catch (Exception e) {

			BocoLog.error(this, "查询错误 " + e.getMessage());
			return mapping.findForward("failure");

		}
		return mapping.findForward("success");

	}

	private ActionForward performListAllStat(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {

		List<TawSystemDept> deptarray = getAlldepts();
//		String[] deptarray = { "1", "14", "15", "16", "17", "18", "19", "20",
//				"21", "22" };
//		String[] deptname = { "省公司", "贵阳", "遵义", "安顺", "黔南分公司", "黔东南分公司",
//				"铜仁分公司", "毕节分公司", "六盘水分公司", "黔西南分公司" };
		String depts = "";
		String deptcount = "";
		String usercount = "";
		String allconunt = "";
		
		Map mapa = request.getParameterMap();
		
//		for(int i=0;i<mapa.size();i++){
//			
//		}
		Iterator<String> it = mapa.keySet().iterator();
		boolean exportflag = false;   //导出标识。如果为true 则为导出
		while(it.hasNext()){
			String parmater = it.next();
			if(parmater.endsWith("-e")){
				exportflag = true ;
			}
		}
		System.out.println(exportflag);
		try {
			Map formMap = ((DynaActionForm) actionForm).getMap();

			String starttime = (String)request.getParameter("searchbystarttime");
			String endtime = (String) request.getParameter("searchbyendtime");
			String flag = (String) request.getParameter("flag");

			if (!starttime.equals("") && starttime != null) {
				starttime = starttime + " 00:00:00";
			} else {
				starttime = "";
			}
			// !"".equals(endtime)
			if (!endtime.equals("") && endtime != null) {
				endtime = endtime + " 23:59:59";
			} else {
				endtime = "";
			}
			String pageIndexName = new org.displaytag.util.ParamEncoder(
					"logList")
					.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
			// 每页显示条数
			Integer pageSize = UtilMgrLocator.getEOMSAttributes()
					.getPageSize();
			final Integer pageIndex = 
				new Integer(
					GenericValidator.isBlankOrNull(request
							.getParameter(pageIndexName)) ? 0 : (Integer
							.parseInt(request.getParameter(pageIndexName)) - 1));
			TawCommonLogOperatorDao dao = (TawCommonLogOperatorDao) ApplicationContextHolder
					.getInstance().getBean("tawCommonLogOperatorDao");

			depts = deptarray.get(Integer.parseInt(flag)).getDeptId();
			
			Map map = null;
			if(exportflag){
				pageSize = -1;
			}
			map = dao.getAllStat(pageIndex, pageSize, depts, starttime, endtime);
			List list = (List) map.get("result");
			request.setAttribute("logList", list);
			request.setAttribute("resultSize", map.get("total"));
			System.out.println(map.get("total"));
			request.setAttribute("pageSize", pageSize);

		} catch (Exception e) {

			BocoLog.error(this, "查询错误 " + e.getMessage());
			return mapping.findForward("failure");

		}
		return mapping.findForward("success");

	}

	public ActionForward search(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("list");
	}
	
	public List getAlldepts(){
		TawCommonLogOperatorDao dao = (TawCommonLogOperatorDao) ApplicationContextHolder
		.getInstance().getBean("tawCommonLogOperatorDao");
		return dao.getAlldepts();
	} 

}