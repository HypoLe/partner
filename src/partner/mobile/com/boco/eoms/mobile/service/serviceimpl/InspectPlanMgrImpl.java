package com.boco.eoms.mobile.service.serviceimpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.mortbay.jetty.security.Credential.MD5;

import utils.PartnerPrivUtils;

import com.boco.activiti.partner.process.model.PnrTroubleTicket;
import com.boco.activiti.partner.process.service.IPnrTroubleTicketService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.area.dao.hibernate.TawSystemAreaDaoHibernate;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.mobile.form.InspectPlanResForm;
import com.boco.eoms.mobile.service.InspectPlanMgr;
import com.boco.eoms.mobile.util.MobileCommonUtils;
import com.boco.eoms.mobile.util.MobileConstants;
import com.boco.eoms.mobile.util.TransLineHandler;
import com.boco.eoms.partner.baseinfo.dao.hibernate.PartnerDeptDaoHibernate;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndDept;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.deviceInspect.model.PnrInspectTaskLink;
import com.boco.eoms.partner.deviceInspect.service.PnrInspectTaskLinkService;
import com.boco.eoms.partner.inspect.mgr.IErrorDistanceMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectLineTrackMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanItemMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMainMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanResMgr;
import com.boco.eoms.partner.inspect.mgr.IPnrInspectTrackMgr;
import com.boco.eoms.partner.inspect.model.ErrorDistance;
import com.boco.eoms.partner.inspect.model.InspectLineTrack;
import com.boco.eoms.partner.inspect.model.InspectPlanItem;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;
import com.boco.eoms.partner.inspect.model.PnrInspectTrack;
import com.boco.eoms.partner.inspect.util.InspectUtils;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanItemForm;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.partner.res.dao.IPnrResConfigDaoJdbc;
import com.boco.eoms.partner.res.dao.jdbc.PnrResConfigDaoJdbc;
import com.boco.eoms.partner.res.mgr.IJkRoomFillGpsService;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.model.JkRoomFillGps;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class InspectPlanMgrImpl implements InspectPlanMgr {
	/**
	 * 
	 */
	public void listInspectPlanRes(HttpServletRequest request,
			HttpServletResponse response, String userId, String deptId)
			throws ServletException, IOException {
		try {
			IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) ApplicationContextHolder.getInstance().getBean("inspectPlanResMgr");
			boolean isSpotcheck = Boolean.parseBoolean(request.getParameter("isSpotcheck"));
			TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			String cycleQuery = StaticMethod.nullObject2String(request.getParameter("cycleQuery"));
			String radiusQuery = StaticMethod.nullObject2String(request.getParameter("radiusQuery"));
			String res_longitude = StaticMethod.nullObject2String(request.getParameter("res_longitude"));
			String res_latitude = StaticMethod.nullObject2String(request.getParameter("res_latitude"));
			String resNameQuery = StaticMethod.nullObject2String(request.getParameter("resNameQuery"));
			String completeQuery = StaticMethod.nullObject2String(request.getParameter("completeQuery"));

			String res_type = StaticMethod.nullObject2String(request.getParameter("res_type"));
			String res_level = StaticMethod.nullObject2String(request.getParameter("res_level"));
			
			//标识是手机巡检还是资源清查
			String resourceInventoryFlag = StaticMethod.nullObject2String(request.getParameter("res_flag"));
			
			
			String pageIndex = request.getParameter("pageIndex");

			int flag = 1;// 1是planMainId存在的，2是planMainId 不存在的
			// System.out.println(pageIndex);
			// PartnerDeptMgr deptMgr=
			// (PartnerDeptMgr)ApplicationContextHolder.getInstance().getBean("PartnerDeptMgrImplFlush");
			// PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);
			// 查询权限 暂时关闭
			// String whereStr =
			// " where 1=1 and planId='"+planMainId+"' and planStartTime <=
			// '"+StaticMethod.getCurrentDateTime()
			// +" and planEndTime >= '"+StaticMethod.getCurrentDateTime()+"' and
			// executeDept = '"+user.getDeptId()+"'";

			String whereStr = "";
			if (isSpotcheck) {
				whereStr = " where planId is null";
			} else {
				whereStr = " where planId is not null ";
			}

			PartnerUserMgr partnerUserMgr = (PartnerUserMgr) ApplicationContextHolder
					.getInstance().getBean("partnerUserMgr");
			PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);
			if (null != user) {// 不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept
								// like
				if (sessionForm.getDeptid().startsWith("201")
						&& sessionForm.getDeptid().length() <= 7) {

					ITawSystemDeptManager mgr = (ITawSystemDeptManager) ApplicationContextHolder
							.getInstance().getBean("ItawSystemDeptManager");

					TawSystemDept dept = mgr.getDeptinfobydeptid(sessionForm
							.getDeptid(), "0");
					String areaid = dept.getAreaid();
					if (areaid != null && !areaid.trim().equals("")) {
						whereStr += "  and country like '" + areaid + "%'";
					} else {// 如果移动人员所在部门没有地域，则不允许查看
						MobileCommonUtils.responseWrite(response, "", "UTF-8");

						return;
					}

				}else {

					whereStr += "  and executeDept like '"
							+ sessionForm.getDeptid() + "%'";
					ITawSystemDeptManager mgr = (ITawSystemDeptManager) ApplicationContextHolder.getInstance().getBean("ItawSystemDeptManager");
					TawSystemDept dept = mgr.getDeptinfobydeptid(sessionForm.getDeptid(), "0");
					String areaid = dept.getAreaid().trim();
					if(isRes2Person(areaid)){
						whereStr += " AND resCfgId IN "+ buildConfigsStr(userId,deptId);
					}
				}

			} else if (!"admin".equals(userId)) {// 如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId
													// like

				ITawSystemDeptManager mgr = (ITawSystemDeptManager) ApplicationContextHolder
						.getInstance().getBean("ItawSystemDeptManager");

				TawSystemDept dept = mgr.getDeptinfobydeptid(sessionForm
						.getDeptid(), "0");
				String areaid = dept.getAreaid();
				if (areaid != null && !areaid.trim().equals("")) {
					whereStr += "  and country like '" + areaid + "%'";
				} else {// 如果移动人员所在部门没有地域，则不允许查看
					MobileCommonUtils.responseWrite(response, "", "UTF-8");

					return;
				}
			}

			/*
			 * if ("admin".equals(userId)) { whereStr = " where planId='" +
			 * planMainId + "'"; } else if (deptId.length() >= 7) { whereStr += "
			 * and execute_dept like '" + deptId.substring(0, deptId.length() -
			 * 2) + "%'"; } else { whereStr += " and execute_dept='" + deptId +
			 * "'"; }
			 */

//			if (!"".equals(completeQuery) && "".equals(resNameQuery)) {
			if (!"".equals(completeQuery)) {
				if ("1".equals(completeQuery)) {
					whereStr += " and inspectState = 0 ";
				}
				if ("2".equals(completeQuery)) {
					whereStr += " and inspectState = 1 ";
				}
				if ("3".equals(completeQuery)) {
					whereStr += " and (inspectState = 0 or inspectState = 1)  ";
				}
			}

//			if (!"".equals(cycleQuery) && "".equals(resNameQuery)) {
			if (!"".equals(cycleQuery)) {
				whereStr += "  and inspectCycle ='" + cycleQuery + "' ";
			}

			if (!"".equals(radiusQuery) && !"".equals(res_longitude)
					&& !"".equals(res_latitude)) {
				double longitude = Double.parseDouble(res_longitude);
				double latitude = Double.parseDouble(res_latitude);
				double radius = Double.parseDouble(radiusQuery);

				whereStr += " and resLongitude <= " + (longitude + radius/(111*Math.cos(latitude)))
						+ " and resLongitude >= " + (longitude - radius/(111*Math.cos(latitude)))
						+ " and resLatitude >= " + (latitude - radius/111)
						+ " and resLatitude <= " + (latitude + radius/111);
			}
			if (!"".equals(resNameQuery)) {
				whereStr += "  and resName like '%" + resNameQuery + "%' ";
			}

			if (isSpotcheck) {
				whereStr += " and cycleEndTime > "	+ CommonSqlHelper.getCurrentDateTimeFunc() + " ";
			} else {
//				whereStr += " and planEndTime > "	+ CommonSqlHelper.getCurrentDateTimeFunc() + " ";
				whereStr += " and cycleEndTime > "	+ CommonSqlHelper.getCurrentDateTimeFunc() + " ";
			}
			
			if(!"".equals(res_type)){
				whereStr += " and resType='"+res_type+"'";					
			}
			if(!"".equals(res_level)){
				whereStr += " and resLevel='"+res_level+"'";					
			}
			
			//区分手机巡检和资源清查巡检
			if("resourceInventory".equals(resourceInventoryFlag)){//代表是资源清查专业
				whereStr += " and specialty ='1122511'";	
			}else{//其他专业
				whereStr += " and specialty !='1122511'";
			}
			
			if(!"".equals(res_longitude)){
				whereStr += " order by abs(resLongitude - " + res_longitude + ")*100000 + abs(resLatitude - " + res_latitude + ")*111320";
			}
			// System.out.println("whereStr " + whereStr);
			System.out.println("------巡检任务whereStr=" + whereStr);
			Map map = (Map) inspectPlanResMgr.findInspectPlanResList(Integer
					.parseInt(pageIndex) - 1, CommonConstants.PAGE_SIZE10,
					whereStr);

			List<InspectPlanRes> tempList = (List) map.get("result");
			// 当资源没有计划时 --start

			Map map2 = null;
			if (isSpotcheck	&& tempList.size() == 0) {
				flag = 2;
				String whereStr2 = " where planId is null";

				partnerUserMgr = (PartnerUserMgr) ApplicationContextHolder
						.getInstance().getBean("partnerUserMgr");
				PartnerUser user2 = partnerUserMgr
						.getPartnerUserByUserId(userId);
				if (null != user2) {// 不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept
									// like
					if ((sessionForm.getDeptid().startsWith("201") || sessionForm
							.getDeptid().startsWith("101"))
							&& sessionForm.getDeptid().length() <= 7) {

						ITawSystemDeptManager mgr = (ITawSystemDeptManager) ApplicationContextHolder
								.getInstance().getBean("ItawSystemDeptManager");

						TawSystemDept dept = mgr.getDeptinfobydeptid(
								sessionForm.getDeptid(), "0");
						String areaid = dept.getAreaid();
						if (areaid != null && !areaid.trim().equals("")) {
							whereStr2 += "  and country like '" + areaid + "%'";
						} else {// 如果移动人员所在部门没有地域，则不允许查看
							MobileCommonUtils.responseWrite(response, "",
									"UTF-8");

							return;
						}

					} else {

						whereStr2 += "  and executeDept like '"
								+ sessionForm.getDeptid() + "%'";
					}

				}

//				if (!"".equals(completeQuery) && "".equals(resNameQuery)) {
				if (!"".equals(completeQuery)) {
					if ("1".equals(completeQuery)) {
						whereStr2 += " and inspectState = 0 ";
					}
					if ("2".equals(completeQuery)) {
						whereStr2 += " and inspectState = 1 ";
					}
					if ("3".equals(completeQuery)) {
						whereStr2 += " and (inspectState = 0 or inspectState = 1)  ";
					}
				}

//				if (!"".equals(cycleQuery) && "".equals(resNameQuery)) {
				if (!"".equals(cycleQuery)) {
					whereStr2 += "  and inspectCycle ='" + cycleQuery + "' ";
				}

				if (!"".equals(resNameQuery)) {
					whereStr2 += "  and resName like '%" + resNameQuery + "%' ";
				}

				whereStr2 += " and cycleEndTime > "
						+ CommonSqlHelper.getCurrentDateTimeFunc() + " ";
				
				
				if(!"".equals(res_type)){
					whereStr2 += " and resType='"+res_type+"'";					
				}
				if(!"".equals(res_level)){
					whereStr2 += " and resLevel='"+res_level+"'";					
				}
				
				//区分手机巡检和资源清查巡检
				if("resourceInventory".equals(resourceInventoryFlag)){//代表是资源清查专业
					whereStr2 += " and specialty ='1122511'";	
				}else{//其他专业
					whereStr2 += " and specialty !='1122511'";
				}
				
				// System.out.println("whereStr " + whereStr);
				System.out.println("------巡检任务whereStr2=" + whereStr2);
				map2 = (Map) inspectPlanResMgr.findInspectPlanResList(Integer
						.parseInt(pageIndex) - 1, CommonConstants.PAGE_SIZE10,
						whereStr2);

				tempList = (List) map2.get("result");

			}
			// 当资源没有计划时 --end

			// 当资源有计划但是不在资源附近，即经度，维度不正确 查代办的--start

			Map map3 = null;
			if (!isSpotcheck && tempList.size() == 0 && "".equals(radiusQuery)) {
				flag = 3;
				String whereStr3 = " where planId is not null ";

				partnerUserMgr = (PartnerUserMgr) ApplicationContextHolder
						.getInstance().getBean("partnerUserMgr");
				PartnerUser user2 = partnerUserMgr
						.getPartnerUserByUserId(userId);
				if (null != user2) {// 不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept
									// like
					if ((sessionForm.getDeptid().startsWith("201") || sessionForm
							.getDeptid().startsWith("101"))
							&& sessionForm.getDeptid().length() <= 7) {

						ITawSystemDeptManager mgr = (ITawSystemDeptManager) ApplicationContextHolder
								.getInstance().getBean("ItawSystemDeptManager");

						TawSystemDept dept = mgr.getDeptinfobydeptid(
								sessionForm.getDeptid(), "0");
						String areaid = dept.getAreaid();
						if (areaid != null && !areaid.trim().equals("")) {
							whereStr3 += "  and country like '" + areaid + "%'";
						} else {// 如果移动人员所在部门没有地域，则不允许查看
							MobileCommonUtils.responseWrite(response, "",
									"UTF-8");

							return;
						}

					} else {

						whereStr3 += "  and executeDept like '"
								+ sessionForm.getDeptid() + "%'";
						ITawSystemDeptManager mgr = (ITawSystemDeptManager) ApplicationContextHolder.getInstance().getBean("ItawSystemDeptManager");
						TawSystemDept dept = mgr.getDeptinfobydeptid(sessionForm.getDeptid(), "0");
						String areaid = dept.getAreaid().trim();
						if(isRes2Person(areaid)){
							whereStr3 += " AND resCfgId IN "+ buildConfigsStr(userId,deptId);
						}
					}

				}

//				if (!"".equals(completeQuery) && "".equals(resNameQuery)) {
				if (!"".equals(completeQuery)) {
					if ("1".equals(completeQuery)) {
						whereStr3 += " and inspectState = 0 ";
					}
					if ("2".equals(completeQuery)) {
						whereStr3 += " and inspectState = 1 ";
					}
					if ("3".equals(completeQuery)) {
						whereStr3 += " and (inspectState = 0 or inspectState = 1)  ";
					}
				}

//				if (!"".equals(cycleQuery) && "".equals(resNameQuery)) {
				if (!"".equals(cycleQuery)) {
					whereStr3 += "  and inspectCycle ='" + cycleQuery + "' ";
				}

				if (!"".equals(resNameQuery)) {
					whereStr3 += "  and resName like '%" + resNameQuery + "%' ";
				}

//				whereStr3 += " and planEndTime > "
				whereStr3 += " and cycleEndTime > "
						+ CommonSqlHelper.getCurrentDateTimeFunc() + " ";

				if(!"".equals(res_type)){
					whereStr3 += " and resType='"+res_type+"'";					
				}
				if(!"".equals(res_level)){
					whereStr3 += " and resLevel='"+res_level+"'";					
				}
				
				//区分手机巡检和资源清查巡检
				if("resourceInventory".equals(resourceInventoryFlag)){//代表是资源清查专业
					whereStr3 += " and specialty ='1122511'";	
				}else{//其他专业
					whereStr3 += " and specialty !='1122511'";
				}
				
				// System.out.println("whereStr " + whereStr);
				System.out.println("------巡检任务whereStr3=" + whereStr3); 
				map3 = (Map) inspectPlanResMgr.findInspectPlanResList(Integer
						.parseInt(pageIndex) - 1, CommonConstants.PAGE_SIZE10,
						whereStr3);

				tempList = (List) map3.get("result");

			}
			// 当资源有计划但是不在资源附近，即经度，维度不正确 查代办的 --end

			ITawSystemDictTypeManager dao = (ITawSystemDictTypeManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemDictTypeManager");
			InspectPlanResForm resForm;
			List<InspectPlanResForm> returnList = new ArrayList<InspectPlanResForm>();
			if (tempList.isEmpty() || tempList.size() == 0) {
				MobileCommonUtils.responseWrite(response, "", "UTF-8");
				return;
			}

			TawSystemAreaDaoHibernate areaMgr = (TawSystemAreaDaoHibernate) ApplicationContextHolder
					.getInstance().getBean("tawSystemAreaDao");
			PartnerDeptDaoHibernate deptMgr = (PartnerDeptDaoHibernate) ApplicationContextHolder
					.getInstance().getBean("partnerDeptDao");
			for (int i = 0; i < tempList.size(); i++) {
				try {
					resForm = new InspectPlanResForm();
					BeanUtils.copyProperties(resForm, tempList.get(i));
					if (null == tempList.get(i).getInspectTime()
							|| "".equals(tempList.get(i).getInspectTime())) {
						resForm.setInspectTime("");
					} else {
						resForm.setInspectTime((tempList.get(i)
								.getInspectTime() + "").substring(0, 19));
					}
					String specialtyName = dao.id2Name(tempList.get(i)
							.getSpecialty());
					String resTypeName = dao.id2Name(tempList.get(i)
							.getResType());
					String resLevelName = dao.id2Name(tempList.get(i)
							.getResLevel());
					String cityName = areaMgr
							.id2Name(tempList.get(i).getCity());
					String countryName = areaMgr.id2Name(tempList.get(i)
							.getCountry());
					String executeDeptName = deptMgr.id2Name(tempList.get(i)
							.getExecuteObject());
					String inspectStateStr = "";
					switch (tempList.get(i).getInspectState()) {
					case 0:
						inspectStateStr = "待完成";
						break;
					case 1:
						inspectStateStr = "已完成";
						break;
					case 2:
						inspectStateStr = "超时未关联未完成";
						break;
					case 3:
						inspectStateStr = "超时已关联未完成";
						break;
					}
					resForm
							.setSignTime(null == tempList.get(i).getSignTime() ? "无"
									: tempList.get(i).getSignTime() + "");
					resForm.setInspectState(inspectStateStr);
					resForm
							.setBurstFlag(1 == tempList.get(i).getBurstFlag() ? "突发"
									: "常规");
					resForm.setSpecialtyName(specialtyName);
					resForm.setResTypeName(resTypeName);
					resForm.setResLevelName(resLevelName);
					resForm.setCityName(cityName);
					resForm.setCountryName(countryName);
					resForm.setExecuteDeptName(executeDeptName);

					returnList.add(resForm);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("returnList", returnList);
			if (flag == 1) {

				returnMap.put("count", map.get("total"));
			} else if (flag == 3) {
				returnMap.put("count", map3.get("total"));

			} else {
				returnMap.put("count", map2.get("total"));

			}
			JSONObject returnJsonObject = JSONObject.fromObject(returnMap);
			// System.out.println(returnJsonObject.toString());
			MobileCommonUtils.responseWrite(response, returnJsonObject
					.toString(), "UTF-8");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * 资源查询.apk
	 */

	public void listInspectBarRes(HttpServletRequest request,
			HttpServletResponse response, String userId, String deptId)
			throws ServletException, IOException {
		try {
			IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) ApplicationContextHolder
					.getInstance().getBean("inspectPlanResMgr");
			TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			String radiusQuery = StaticMethod.nullObject2String(request
					.getParameter("radiusQuery")); // 半径
			String res_longitude = StaticMethod.nullObject2String(request
					.getParameter("res_longitude"));
			String res_latitude = StaticMethod.nullObject2String(request
					.getParameter("res_latitude"));
			String resNameQuery = StaticMethod.nullObject2String(request
					.getParameter("resNameQuery")); // 资源
			String completeQuery = StaticMethod.nullObject2String(request
					.getParameter("completeQuery")); // 全部
			String resBarQuery = StaticMethod.nullObject2String(request
					.getParameter("resBarQuery")); // 条码

			String pageIndex = request.getParameter("pageIndex");

			// 当资源没有计划时 --start

			Map map2 = null;

			String whereStr2 = " where planId is null";

			PartnerUserMgr partnerUserMgr = (PartnerUserMgr) ApplicationContextHolder
					.getInstance().getBean("partnerUserMgr");
			PartnerUser user2 = partnerUserMgr.getPartnerUserByUserId(userId);
			if (null != user2) {// 不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept
								// like
				if ((sessionForm.getDeptid().startsWith("201") || sessionForm
						.getDeptid().startsWith("101"))
						&& sessionForm.getDeptid().length() <= 7) {

					ITawSystemDeptManager mgr = (ITawSystemDeptManager) ApplicationContextHolder
							.getInstance().getBean("ItawSystemDeptManager");

					TawSystemDept dept = mgr.getDeptinfobydeptid(sessionForm
							.getDeptid(), "0");
					String areaid = dept.getAreaid();
					if (areaid != null && !areaid.trim().equals("")) {
						whereStr2 += "  and country like '" + areaid + "%'";
					} else {// 如果移动人员所在部门没有地域，则不允许查看
						MobileCommonUtils.responseWrite(response, "", "UTF-8");

						return;
					}

				} else {

					whereStr2 += "  and executeDept like '"
							+ sessionForm.getDeptid() + "%'";
				}

			}

			if (!"".equals(radiusQuery) && !"".equals(res_longitude)
					&& !"".equals(res_latitude)) {
				double longitude = Double.parseDouble(res_longitude);
				double latitude = Double.parseDouble(res_latitude);
				double radius = Double.parseDouble(radiusQuery);

				whereStr2 += " and resLongitude <= " + (longitude + radius)
						+ " and resLongitude>=" + (longitude - radius)
						+ " and resLatitude>=" + (latitude - radius)
						+ " and resLatitude<=" + (latitude + radius) + "  ";
			}

			if (!"".equals(resBarQuery)) {
				whereStr2 += "and resBar = '" + resBarQuery + "'";
			}

			if (!"".equals(resNameQuery)) {
				whereStr2 += "  and resName like '%" + resNameQuery + "%' ";
			}

			whereStr2 += " and cycleEndTime > "
					+ CommonSqlHelper.getCurrentDateTimeFunc() + " ";

			// System.out.println("whereStr " + whereStr);

			map2 = (Map) inspectPlanResMgr.findInspectPlanResList(Integer
					.parseInt(pageIndex) - 1, CommonConstants.PAGE_SIZE10,
					whereStr2);
			List<InspectPlanRes> tempList = (List) map2.get("result");

			// 当资源没有计划时 --end

			ITawSystemDictTypeManager dao = (ITawSystemDictTypeManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemDictTypeManager");
			InspectPlanResForm resForm;
			List<InspectPlanResForm> returnList = new ArrayList<InspectPlanResForm>();
			if (tempList.isEmpty() || tempList.size() == 0) {
				MobileCommonUtils.responseWrite(response, "", "UTF-8");
				return;
			}

			TawSystemAreaDaoHibernate areaMgr = (TawSystemAreaDaoHibernate) ApplicationContextHolder
					.getInstance().getBean("tawSystemAreaDao");
			PartnerDeptDaoHibernate deptMgr = (PartnerDeptDaoHibernate) ApplicationContextHolder
					.getInstance().getBean("partnerDeptDao");
			for (int i = 0; i < tempList.size(); i++) {
				try {
					resForm = new InspectPlanResForm();
					BeanUtils.copyProperties(resForm, tempList.get(i));
					if (null == tempList.get(i).getInspectTime()
							|| "".equals(tempList.get(i).getInspectTime())) {
						resForm.setInspectTime("");
					} else {
						resForm.setInspectTime((tempList.get(i)
								.getInspectTime() + "").substring(0, 19));
					}
					String specialtyName = dao.id2Name(tempList.get(i)
							.getSpecialty());
					String resTypeName = dao.id2Name(tempList.get(i)
							.getResType());
					String resLevelName = dao.id2Name(tempList.get(i)
							.getResLevel());
					String cityName = areaMgr
							.id2Name(tempList.get(i).getCity());
					String countryName = areaMgr.id2Name(tempList.get(i)
							.getCountry());
					String executeDeptName = deptMgr.id2Name(tempList.get(i)
							.getExecuteObject());
					String inspectStateStr = "";
					switch (tempList.get(i).getInspectState()) {
					case 0:
						inspectStateStr = "待完成";
						break;
					case 1:
						inspectStateStr = "已完成";
						break;
					case 2:
						inspectStateStr = "超时未关联未完成";
						break;
					case 3:
						inspectStateStr = "超时已关联未完成";
						break;
					}
					resForm
							.setSignTime(null == tempList.get(i).getSignTime() ? "无"
									: tempList.get(i).getSignTime() + "");
					resForm.setInspectState(inspectStateStr);
					resForm
							.setBurstFlag(1 == tempList.get(i).getBurstFlag() ? "突发"
									: "常规");
					resForm.setSpecialtyName(specialtyName);
					resForm.setResTypeName(resTypeName);
					resForm.setResLevelName(resLevelName);
					resForm.setCityName(cityName);
					resForm.setCountryName(countryName);
					resForm.setExecuteDeptName(executeDeptName);

					returnList.add(resForm);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("returnList", returnList);

			returnMap.put("count", map2.get("total"));

			JSONObject returnJsonObject = JSONObject.fromObject(returnMap);
			// System.out.println(returnJsonObject.toString());
			MobileCommonUtils.responseWrite(response, returnJsonObject
					.toString(), "UTF-8");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * 资源查询 config表 2013-08-30
	 */
	public void listResource(HttpServletRequest request,
			HttpServletResponse response, String userId, String deptId)
			throws ServletException, IOException {

		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) ApplicationContextHolder
				.getInstance().getBean("inspectPlanResMgr");
		String radiusQuery = StaticMethod.nullObject2String(request
				.getParameter("radiusQuery")); // 半径
		String res_longitude = StaticMethod.nullObject2String(request
				.getParameter("res_longitude")); // 经度
		String res_latitude = StaticMethod.nullObject2String(request
				.getParameter("res_latitude")); // 纬度
		String resNameQuery = StaticMethod.nullObject2String(request
				.getParameter("resNameQuery")); // 资源名称
		String resBarQuery = StaticMethod.nullObject2String(request
				.getParameter("resBarQuery")); // 条码
		String pageIndex = request.getParameter("pageIndex");
		List<PnrResConfig> tempList = new ArrayList<PnrResConfig>();
		String whereStr = "where 1=1 ";
		Map map = null;
		if (!"".equals(radiusQuery) && !"".equals(res_longitude) && !"".equals(res_latitude)) {
			double longitude = Double.parseDouble(res_longitude);
			double latitude = Double.parseDouble(res_latitude);
			double radius = Double.parseDouble(radiusQuery);
		whereStr += " and resLongitude <= " + (longitude + radius/(111*Math.cos(latitude)))
			+ " and resLongitude >= " + (longitude - radius/(111*Math.cos(latitude)))
			+ " and resLatitude >= " + (latitude - radius/111)
			+ " and resLatitude <= " + (latitude + radius/111);
		}

		if (!"".equals(resBarQuery)) {
				whereStr += "and resBar = '" + resBarQuery + "'";
		}

		if (!"".equals(resNameQuery)) {
				whereStr += "and resName like '%" + resNameQuery + "%' ";
		}
		if(!"".equals(res_longitude)){
			whereStr += " order by abs(resLongitude - " + res_longitude + ")*100000 + abs(resLatitude - " + res_latitude + ")*111320";
		}
		map = (Map) inspectPlanResMgr.findResourceList(Integer.parseInt(pageIndex) - 1,	CommonConstants.PAGE_SIZE10, whereStr);
		tempList = (List) map.get("result");

		ITawSystemDictTypeManager dao = (ITawSystemDictTypeManager) ApplicationContextHolder.getInstance().getBean("ItawSystemDictTypeManager");
//		InspectPlanResForm resForm;
		PnrResConfig resForm;
		List<PnrResConfig> returnList = new ArrayList<PnrResConfig>();
		if (tempList.isEmpty() || tempList.size() == 0) {
			MobileCommonUtils.responseWrite(response, "", "UTF-8");
			return;
		}

		TawSystemAreaDaoHibernate areaMgr = (TawSystemAreaDaoHibernate) ApplicationContextHolder
				.getInstance().getBean("tawSystemAreaDao");
		PartnerDeptDaoHibernate deptMgr = (PartnerDeptDaoHibernate) ApplicationContextHolder
				.getInstance().getBean("partnerDeptDao");
		for (int i = 0; i < tempList.size(); i++) {
			try {
//				resForm = new InspectPlanResForm();
				resForm = new PnrResConfig();
				resForm.setResName(tempList.get(i).getResName());
				resForm.setId(tempList.get(i).getId());
				resForm.setResLongitude(tempList.get(i).getResLongitude());
				resForm.setResLatitude(tempList.get(i).getResLatitude());
				returnList.add(resForm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("returnList", returnList);
		returnMap.put("count", map.get("total"));
		returnMap.put("type", map.get("listRes"));
		JSONObject returnJsonObject = JSONObject.fromObject(returnMap);
		System.out.print("inspectPlanMgrImpl-752rows" + returnJsonObject);
		MobileCommonUtils.responseWrite(response, returnJsonObject.toString(),
				"UTF-8");
	}

	/**
	 * 查询巡检项
	 */
	public void listInspectPlanItem(HttpServletRequest request,
			HttpServletResponse response, String userId)
			throws ServletException, IOException {

		IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr) ApplicationContextHolder
				.getInstance().getBean("inspectPlanItemMgrImpl");
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) ApplicationContextHolder
				.getInstance().getBean("inspectPlanResMgr");
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemDictTypeManager");
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) ApplicationContextHolder
				.getInstance().getBean("partnerUserMgr");

		String plan_res_id = request.getParameter("planResId");
		Search search = new Search();
		search.addFilterEqual("planResId", plan_res_id);
		search.addSortAsc("saveTime");

		// 如果是进行离线下载任务-------------开始
		String downloadtype = StaticMethod.nullObject2String(request
				.getParameter("downloadtype"));
		String ids = StaticMethod
				.nullObject2String(request.getParameter("ids"));
		ArrayList<InspectPlanRes> resList = new ArrayList<InspectPlanRes>();
		if (!"".equals(downloadtype) && "selectRes".equals(downloadtype)
				&& !"".equals(ids)) {
			search = new Search();
			String[] idArray = ids.split("\\|");
			if (idArray.length < 1) {
				MobileCommonUtils.responseWrite(response, "".toString(),
						"UTF-8");
				return;
			}
			search.addFilterIn("planResId", idArray);
			InspectPlanRes resTemp;
			for (int i = 0; i < idArray.length; i++) {
				resTemp = inspectPlanResMgr.get(Long.parseLong(idArray[i]));
				resList.add(resTemp);// 取出所选的资源,主要是取出资源的过期时间
			}
		}
		// -------------------------结束

		SearchResult<InspectPlanItem> returnSearch = inspectPlanItemMgr
				.searchAndCount(search);

		List<InspectPlanItem> inspectPlanItemList = returnSearch.getResult();
		List<InspectPlanItemForm> returnList = new ArrayList<InspectPlanItemForm>();
		if (!inspectPlanItemList.isEmpty()) {// 如果所查巡检项不为空
			InspectPlanRes res = null;
			// 如果不是下载离线数据,则从request中取plan_res_id;
			if ("".equals(downloadtype) || !"selectRes".equals(downloadtype)
					|| "".equals(ids)) {
				res = inspectPlanResMgr.get(Long.parseLong(plan_res_id));
			}
			InspectPlanItemForm inspectPlanItemForm;
			for (int i = 0; i < inspectPlanItemList.size(); i++) {
				inspectPlanItemForm = new InspectPlanItemForm();
				try {
					BeanUtils.copyProperties(inspectPlanItemForm,
							inspectPlanItemList.get(i));
					if (!"".equals(downloadtype)
							&& "selectRes".equals(downloadtype)
							&& !"".equals(ids)) {// 如果是离线下载,则设置过期时间
						for (int j = 0; j < resList.size(); j++) {
							if (inspectPlanItemForm.getPlanResId().equals(
									resList.get(j).getId() + "")) {
								inspectPlanItemForm.setEndTime(resList.get(j)
										.getPlanEndTime()
										+ "");
								break;
							}
						}
					} else {// 否则直接设置资源时间
						inspectPlanItemForm.setEndTime(res.getPlanEndTime()
								+ "");
					}

					inspectPlanItemForm.setIsOverDue("false");
					if ("radio".equals(inspectPlanItemForm.getInputType())
							|| "multiple".equals(inspectPlanItemForm
									.getInputType())) {// 下载对应的字典值

						List dictTypeList = mgr
								.getDictSonsByDictid(inspectPlanItemForm
										.getDictId());
						String dictValue = "";
						for (int d_j = 0; d_j < dictTypeList.size(); d_j++) {
							TawSystemDictType dictType = (TawSystemDictType) dictTypeList
									.get(d_j);
							dictValue += dictType.getDictId() + "|"
									+ dictType.getDictName();
							if (d_j != dictTypeList.size() - 1) {
								dictValue += ";";
							}
						}
						// System.out.println("dictValue "+dictValue);
						inspectPlanItemForm.setDictValue(dictValue);
						if ("radio".equals(inspectPlanItemForm.getInputType())) {// 对单选的结果进行MD5
							if (null == inspectPlanItemList.get(i)
									.getItemResult()
									|| "".equals(inspectPlanItemList.get(i)
											.getItemResult())) {// 如果没有结果,则默认的结果为默认值
								inspectPlanItemForm
										.setMd5Result(MD5
												.digest(StaticMethod
														.nullObject2String(null == inspectPlanItemList
																.get(i)
																.getDefaultValue() ? ""
																: inspectPlanItemList
																		.get(i)
																		.getDefaultValue())));
							} else {// 否则直接对巡检结果进行MD5
								inspectPlanItemForm
										.setMd5Result(MD5
												.digest(StaticMethod
														.nullObject2String(null == inspectPlanItemList
																.get(i)
																.getItemResult() ? ""
																: inspectPlanItemList
																		.get(i)
																		.getItemResult())));
							}
						} else {// 对多选进行MD5
							inspectPlanItemForm
									.setMd5Result(MD5
											.digest(StaticMethod
													.nullObject2String(null == inspectPlanItemList
															.get(i)
															.getItemResult() ? ""
															: inspectPlanItemList
																	.get(i)
																	.getItemResult())));
						}
					} else {
						inspectPlanItemForm
								.setMd5Result(MD5
										.digest(StaticMethod
												.nullObject2String(null == inspectPlanItemList
														.get(i).getItemResult() ? ""
														: inspectPlanItemList
																.get(i)
																.getItemResult())));
					}
					inspectPlanItemForm.setMd5Result(MD5.digest(StaticMethod
							.nullObject2String(null == inspectPlanItemList.get(
									i).getItemResult() ? ""
									: inspectPlanItemList.get(i)
											.getItemResult())));
					inspectPlanItemForm.setHasPicture(inspectPlanItemList
							.get(i).getHasPicture());
					inspectPlanItemForm
							.setMd5ExceptionContent(MD5
									.digest(StaticMethod
											.nullObject2String(null == inspectPlanItemList
													.get(i)
													.getExceptionContent() ? ""
													: inspectPlanItemList
															.get(i)
															.getExceptionContent())));
					inspectPlanItemForm
							.setMd5ExceptionFlag(MD5
									.digest(StaticMethod
											.nullObject2String(null == inspectPlanItemList
													.get(i).getExceptionFlag() ? ""
													: inspectPlanItemList
															.get(i)
															.getExceptionFlag())));
					returnList.add(inspectPlanItemForm);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);
			if (null != user) {
				String partnerid = user.getPartnerid();
				if (null != res
						&& res.getExecuteDept().equals(
								StaticMethod.null2String(partnerid))) {
					request.setAttribute("isCheckUser", true);
				} else {
					request.setAttribute("isCheckUser", false);
				}
			} else {
				request.setAttribute("isCheckUser", false);
			}
			request.setAttribute("inspectPlanItemList", returnList);
			request.setAttribute("resultSize", returnSearch.getTotalCount());
		} else {
			MobileCommonUtils.responseWrite(response, "".toString(), "UTF-8");
			return;
		}
		Map<String, List> bigItemMap = new HashMap<String, List>();
		if (null != plan_res_id) {
			bigItemMap = inspectPlanItemMgr.queryBigItem(plan_res_id);
		}
		HashMap<String, List> returnMap = new HashMap<String, List>();
		returnMap.put("bigItem", bigItemMap.get("bigItem"));
		returnMap.put("data", returnList);

		JSONArray returnArray = JSONArray.fromObject(returnMap);
		// System.out.println(returnArray.toString());
		String resultStr = returnArray.toString().replace("\t", " ").replace(
				"\r", " ").replace("\n", " ").replace("\f", " ").replace("\b",
				" ").replace("\\", "").replace("/", "\\/").replace("\r\n", " ")
				.replace("\t", " ").replace(" ", " ").replace("rn", " ");
		// String resultStr = returnArray.toString().replace("\t",
		// " ").replace("\r"," ").replace("\n", " ").replace("\f", " ")
		// .replace("\b", " ").replace("\\", "").replace("/",
		// "\\/").replace("\r\n", "<br/>&nbsp;&nbsp;").replace("\t",
		// "&nbsp;&nbsp;&nbsp;&nbsp;").replace(" ", "&nbsp;").replace("rn",
		// "&nbsp;");
		// System.out.println(resultStr);
		MobileCommonUtils
				.responseWrite(response, resultStr.toString(), "UTF-8");
	}

	/**
	 * 保存全部巡检结果
	 */
	public String saveAllCheckResult(HttpServletRequest request, String userId) {
		try {

			IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr) ApplicationContextHolder
					.getInstance().getBean("inspectPlanItemMgrImpl");
			IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) ApplicationContextHolder
					.getInstance().getBean("inspectPlanResMgr");
			IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr) ApplicationContextHolder
					.getInstance().getBean("inspectPlanMainMgr");
			PnrResConfigMgr pnrResConfigMgr = (PnrResConfigMgr) ApplicationContextHolder
					.getInstance().getBean("PnrResConfigMgr");

			IPnrInspectTrackMgr inspectTrackMgr = (IPnrInspectTrackMgr) ApplicationContextHolder
					.getInstance().getBean("pnrInspectTrackMgrImpl");
			String planResId = StaticMethod.nullObject2String(request
					.getParameter("plan_res_id"));
			String inspectState = StaticMethod.nullObject2String(request
					.getParameter("inspectState"));
			String data = StaticMethod.nullObject2String(request
					.getParameter("data"));
			// System.out.println(data);
			data = MobileCommonUtils.replaceStr2(data);
			// System.out.println(data);
			String latitude = StaticMethod.nullObject2String(request
					.getParameter("latitude"));//纬度
			String longitude = StaticMethod.nullObject2String(request
					.getParameter("longitude"));//经度
			String status = StaticMethod.nullObject2String(request
					.getParameter("status"));
			String startCheckTime = StaticMethod.nullObject2String(request
					.getParameter("startCheckTime"));
			double currentLatitude = Double
					.parseDouble("".equals(latitude) ? "0" : latitude);
			double currentLongitude = Double
					.parseDouble("".equals(longitude) ? "0" : longitude);
			double distance = Double.parseDouble(StaticMethod
					.null2String(request.getParameter("distance")));// 误差距离,单位公里
			// 新增强制提交标识   2013-09-26
			String  forcedSubmit = StaticMethod.nullObject2String(request
					.getParameter("forcedSubmit"));
			
			//资源清查标识
			String res_flag = StaticMethod.nullObject2String(request.getParameter("res_flag"));
			//资源中文名
			String resName = StaticMethod.nullObject2String(request.getParameter("resName"));
			//
			String inspectUser = StaticMethod.nullObject2String(request.getParameter("inspectUser"));
			
			String endTime = StaticMethod.nullObject2String(request.getParameter("endTime"));
			
			
			

			Search search;
			search = new Search();

			search.addFilterEqual("planResId", planResId);

			InspectPlanItem item;

			InspectPlanRes res = inspectPlanResMgr.get(Long
					.parseLong(planResId));
			try {
				SimpleDateFormat myFormatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date endDate = myFormatter.parse(res.getCycleEndTime() + "");
				Date currentDate = new Date();
				// 如果超时则不进行保存
				// System.out.println("endDate "+endDate.toString());
				// System.out.println("currentDate "+currentDate.toString());
				if (null == res.getCycleEndTime() || currentDate.after(endDate)) {
					String returnStr = "[{\"success\":\"time-out\"}]";
					return returnStr;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

			res.setItemDoneNum(res.getItemNum());// 如果用户点击了全部提交,则将资源的已巡检数量直接更新为巡检项总数
			res.setInspectTime(new Date());
			res.setInspectUser(userId);
			
			
			res.setForcedSubmit(forcedSubmit); //增加是否强制提交标识  2013-09-26

			// 更新计划,
			search = new Search();
			search.addFilterEqual("id", res.getPlanId());
			InspectPlanMain inspectPlanMain = inspectPlanMainMgr.search(search)
					.get(0);

			JSONArray array = new JSONArray("[" + data + "]");
			JSONObject obj = null;
			if (array.length() > 0) {
				obj = array.getJSONObject(0);
			}
			if (null == obj) {
				return MobileConstants.failureStr;
			}
			JSONArray dataArray = obj.getJSONArray("data");
//			System.out.println("dataArray " + dataArray.toString());
			boolean isExceptionRes = false;
			for (int i = 0; i < dataArray.length(); i++) {
				obj = dataArray.getJSONObject(i);

				String inputId = obj.getString("inputId");
				String value = null == obj.getString("value") ? "" : obj
						.getString("value");
				String exceptionContent = null == obj
						.getString("exceptionContent") ? "" : obj
						.getString("exceptionContent");
				String exceptionFlag = null == obj.getString("exceptionFlag") ? ""
						: obj.getString("exceptionFlag");

				String md5Result = obj.getString("md5Result");
				String md5ExceptionContent = obj
						.getString("md5ExceptionContent");
				String md5ExceptionFlag = obj.getString("md5ExceptionFlag");

				// 如果数据有变动,则进行保存
				boolean isChange = md5Result.equals(MD5.digest(StaticMethod
						.nullObject2String(value)))
						&& md5ExceptionContent.equals(MD5.digest(StaticMethod
								.nullObject2String(exceptionContent)))
						&& md5ExceptionFlag.equals(MD5.digest(StaticMethod
								.nullObject2String(exceptionFlag)));
				search = new Search();
				search.addFilterEqual("id", inputId);
				item = inspectPlanItemMgr.searchUnique(search);
				item.setSaveTime(new Date());
				item.setItemResult(value);
				item.setExceptionContent(exceptionContent);
				if ("-1".equals(exceptionFlag)) {
					item.setExceptionFlag(-1);
				} else {
					if (StringUtils.isEmpty(exceptionContent)) {// 如果异常为空,则该巡检项为正常
						item.setExceptionFlag(1);
					} else {
						item.setExceptionFlag(0);
						isExceptionRes = true;
					}
				}
				item.setSaveTime(new Date());
				if (item.getInputType() == "number") {
					String[] dictIdArray = item.getNormalRange().split("\\|");
					int min = Integer.parseInt(dictIdArray[0]);
					int max = Integer.parseInt(dictIdArray[1]);
					if (Integer.parseInt(value) < min
							|| Integer.parseInt(value) > max) {
						item.setExceptionFlag(1);
					}
				}
				if (item.getInputType() == "radio"
						&& !value.equals(item.getDefaultValue())) {
					item.setExceptionFlag(1);
				}
				inspectPlanItemMgr.save(item);
			}
			// 更新异常
			if (isExceptionRes) {
				res.setExceptionFlag(0);
			} else {
				res.setExceptionFlag(1);
			}
			res.setAuditState(0);// 修改审核状态为0侍审批
			// System.out.println("res.getItemDoneNum()
			// -----end------------------ "+res.getItemDoneNum());

			// 更新资源配置
			search = new Search();
			search.addFilterEqual("id", res.getResCfgId());
			PnrResConfig config = pnrResConfigMgr.searchUnique(search);
			if (null != config) {
				config.setLastInspectTime(new Date());
				if("resourceInventory".equals(res_flag)){
					config.setResLongitude("".equals(longitude) ? "0" : longitude);
					config.setResLatitude("".equals(latitude) ? "0" : latitude);	
				}
				pnrResConfigMgr.save(config);
			}
			// 更新资源配置 结束
			
			//--2014 -09-04 无此设备时，而需要照片上传，无法更改pictureUploadFlag状态，所以去掉判断！
			/*search = new Search(); 
			search.addFilterEqual("planResId", res.getId());
			search.addFilterEqual("pictureUploadFlag", "0");
			search.addFilterEqual("pictureFlag", "1");
			int notComplete = inspectPlanItemMgr.searchAndCount(search)
					.getTotalCount();// 判断照片是否已经全部上传成功,上
			if (0 != notComplete) {
				res.setInspectState(0);
			} else {*/
				if (StringUtils.isNotEmpty(inspectState)
						&& inspectState.equals("1")) {
					res.setInspectState(1);
				} else {
					res.setInspectState(0);
				}
			//}
			res.setErrorDistance(distance);

			int resExceptionNum = inspectPlanResMgr.getExceptionResCount(res
					.getPlanId());
			inspectPlanMain.setResExceptionNum(resExceptionNum);

			PnrInspectTrack inspectTrack = new PnrInspectTrack();

			if ("0".equals(status) && !"".equals(startCheckTime)) {// 如果签退,则触发此条件,更新在站时长
				SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
				res.setSignTime(myFormatter.parse(startCheckTime));
//				res.setSignStatus(signState);
				String timeOnSite = InspectUtils.getTimeDifference(
						startCheckTime, StaticMethod.getCurrentDateTime())
						+ "";
				float totalTimeOnSite = (null == res.getTimeOnSite() || "null"
						.equals(res.getTimeOnSite() + "")) ? Float
						.parseFloat(timeOnSite) : (res.getTimeOnSite() + Float
						.parseFloat(timeOnSite));
				res.setTimeOnSite(totalTimeOnSite);// 时间累计
				res.setEndSignTimes(StaticMethod.nullObject2int(res
						.getEndSignTimes()) + 1);// 自增签退次数
				inspectTrack.setEndSignTimes(null == res.getEndSignTimes() ? 1
						: res.getEndSignTimes() + 1);
				search.addFilterEqual("id", res.getPlanId());
				inspectPlanMain.setTimeOnSite((null == inspectPlanMain
						.getTimeOnSite() || "null".equals(inspectPlanMain
						.getTimeOnSite())) ? Float.parseFloat(timeOnSite)
						: (inspectPlanMain.getTimeOnSite() + Float
								.parseFloat(timeOnSite)));
			}

			inspectPlanResMgr.save(res);// 更新巡检资源
			inspectPlanMain.setResDoneNum(inspectPlanResMgr.queryHasDoneNum(
					res.getPlanId()).get("hasCheckDoneNum"));
			inspectPlanMainMgr.save(inspectPlanMain);// 更新巡检计划的已完成数

			// 保存签退坐标

			if (!"".equals(latitude) || !"".equals(longitude)) {
				inspectTrack.setSignLatitude(currentLatitude + "");
				inspectTrack.setSignLongitude(currentLongitude + "");
				inspectTrack.setSignState("1");
			} else {
				inspectTrack.setSignState("0");
			}
			inspectTrack.setPlanResId(planResId);
			inspectTrack.setSignTime(StaticMethod
					.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
			inspectTrack.setStatus(status);
			inspectTrackMgr.save(inspectTrack);
			
		if(inspectState.equals("1")&&!"resourceInventory".equals(res_flag)){
				if(isExceptionRes){
	//			IPnrTaskTicketService pnrService = (IPnrTaskTicketService) ApplicationContextHolder.getInstance().getBean("pnrTaskTicketService");
				IPnrTroubleTicketService pnrService = (IPnrTroubleTicketService) ApplicationContextHolder.getInstance().getBean("pnrTroubleTicketService");
				Search searchd = new Search();
				searchd.addFilterEqual("theme", "巡检隐患处理工单-"+res.getResName());
				int  bn =1;
				SearchResult<PnrTroubleTicket> list =pnrService.searchAndCount(searchd);
				List<PnrTroubleTicket> listpnr = list.getResult();
				
					for(int i=0;i<listpnr.size();i++){
						int state = listpnr.get(i).getState();
						if(state!=5||state!=4){
							bn++;
						}
					}
				
					if(bn==1){
						
						TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
						.getSession().getAttribute("sessionform");
						
						//automaticallyGenerateTask(planResId,userId,sessionForm.getDeptid());
						Thread thread = new Thread(new Inner(planResId,userId,sessionForm.getDeptid()));
						       thread.start();
					}
				}
		}
		
			//如果是资源清查，则往jk_room_fill_gps存数据
			if("resourceInventory".equals(res_flag)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				IJkRoomFillGpsService jkRoomFillGpsService = (IJkRoomFillGpsService) ApplicationContextHolder.getInstance().getBean("jkRoomFillGpsService");
				JkRoomFillGps jkRoomFillGps=new JkRoomFillGps();
				if(config!=null){
					jkRoomFillGps.setRoomId(config.getSubResId());
				}
				jkRoomFillGps.setRoomName(resName);
				jkRoomFillGps.setGpsX("".equals(longitude) ? "0" : longitude);
				jkRoomFillGps.setGpsY("".equals(latitude) ? "0" : latitude);
				jkRoomFillGps.setInsertMan(inspectUser);
				jkRoomFillGps.setInsertDate(sdf.parse(endTime));
				jkRoomFillGpsService.save(jkRoomFillGps);
			}
		
		
				
			return MobileConstants.successStr;
		} catch (Exception e) {
			e.printStackTrace();
			return MobileConstants.failureStr;
		}
	}
	/**
	 * 内部类为完成隐患工单
	 * @author Administrator
	 *
	 */
public class Inner implements Runnable{ 
	       private String id;
	       private String userId;
	       private String deptId;       
	       

		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}

		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getDeptId() {
			return deptId;
		}
		public void setDeptId(String deptId) {
			this.deptId = deptId;
		}
		
		public Inner(String id, String userId, String deptId) {
			this.id = id;
			this.userId = userId;
			this.deptId = deptId;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			automaticallyGenerateTask(id,userId,deptId);
		} 
} 
	/**
	 * 自动生成故障工单
	 */
	public Boolean automaticallyGenerateTask(String id,String userId,String deptId){
		Boolean bn = false;
		
		
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) ApplicationContextHolder.getInstance().getBean("inspectPlanResMgr");
		PartnerUserAndDeptMgr partnerDeptMgr = (PartnerUserAndDeptMgr) ApplicationContextHolder.getInstance().getBean("partnerUserAndDeptMgr");
		
		Long resId = Long.parseLong(id);
		InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(resId);
		String msg = "";
		if (inspectPlanRes.getExceptionFlag() != null && inspectPlanRes.getExceptionFlag() == 0) {
			IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr) ApplicationContextHolder.getInstance().getBean("inspectPlanItemMgrImpl");
			Search search = new Search();
			search.addFilterEqual("planResId", resId);
			search.addFilterEqual("exceptionFlag", 0);
			
			List<InspectPlanItem> list = inspectPlanItemMgr.search(search);
			for(int i=0;i<list.size();i++){
				
				msg+=(i+1)+"、"+list.get(i).getContent()+"异常信息:"+list.get(i).getExceptionContent()+"。\r\n";
				
			}
			
		}
		
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		//主题
		String title = "巡检隐患处理工单-"+inspectPlanRes.getResName();
		//派单人
		String initiator = userId;
		// 派单时间
		Date createTime = new Date();
		// 处理时限
		String sheetCompleteLimit = "24";
//		故障站点
		String mainResName = inspectPlanRes.getResName();
//		故障站点ID
		String mainResId =inspectPlanRes.getResCfgId();
//		故障来源 --其他
		String faultSource ="101011005";
//		故障发生时间-默认当前时间
		String mainFaultOccurTime =sFormat.format(createTime);
		// 涉及专业
		String[] specialty = {"101010806"};
		String mainSpecialty = "";
		for (int i = 0; i < specialty.length; i++) {
			if (i == (specialty.length - 1)) {
				mainSpecialty += specialty[i];
			} else {
				mainSpecialty += specialty[i] + ",";
			}
		}
// 		是否集团客户--默认否
		String mainIsGroupBusi ="1030102";
// 		工单子类型 故障处理
		String mainFaultNet = "101220101";
//      故障联系人
		String mainpeople ="";		
// 		故障描述
		String mainRemark =msg ;
		// 接收人 ---需要改
		String taskAssignee = initiator;
		PartnerUserAndDept p =partnerDeptMgr.getPartnerUserAndDeptByDeptId(deptId);
		if(p.getUserId()!=null){
			taskAssignee = p.getUserId();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 24);
		String dueDate =sFormat.format(calendar.getTime());
		// 接收人字符串
		String taskAssigneeJSON = "[{id:'"+taskAssignee+"',nodeType:'user',categoryId:'taskAssignee'}]";
		String themeId ="";		
		// 流程开始
		ProcessEngine processEngine = (ProcessEngine) ApplicationContextHolder.getInstance().getBean("processEngine");
		FormService formService = (FormService) ApplicationContextHolder.getInstance().getBean("formService");
		IdentityService identityService = (IdentityService) ApplicationContextHolder.getInstance().getBean("identityService");

		// 启动流程实例  
        ProcessInstance instance = processEngine  
                 .getRuntimeService().startProcessInstanceByKey("troubleTicketProcess");  
        String processInstanceId = instance.getId();  
        System.out.println("procId:"+ processInstanceId);  
          
        // 获得第一个任务  
        TaskService taskService = processEngine.getTaskService();  
//        List<Task> tasks = taskService.createTaskQuery().processDefinitionId(processInstanceId).taskDefinitionKey("newDistribution").list();  
		identityService.setAuthenticatedUserId(initiator);
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(
				processInstanceId).active().list();
		String taskId = taskList.get(0).getId();
		System.out.println("taskAssignee:"+taskAssignee+";initiator:"+initiator+";deptId"+deptId);

          //taskService.claim(taskId1, "s_admin");
          taskService.setAssignee(taskId,taskAssignee);
          //taskService.setOwner(taskId1, "s_admin");
          //taskService.setDueDate(taskId1,calendar.getTime());
//          taskService.setDueDate(taskId1,calendar.getTime());
          
		// 流程结束
		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) ApplicationContextHolder.getInstance().getBean("pnrTroubleTicketService");

		PnrTroubleTicket entity = new PnrTroubleTicket();
		if (themeId != null || !"".equals(themeId)) {
			entity.setId(themeId);
		}
		entity.setTheme(title);
		try {
			entity.setCreateTime(sFormat.parse(mainFaultOccurTime));
			entity.setSendTime(sFormat.parse(sFormat.format(createTime)));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		entity.setInitiator(initiator);
		// 接收人
		entity.setTaskAssignee(taskAssignee);
		// 处理人
		// entity.setDoPerson(dealPerformer2);
		entity.setConnectPerson(mainpeople);
		entity.setProcessLimit(Double.parseDouble(sheetCompleteLimit));
		entity.setFailedSite(mainResName);
		entity.setMainResId(mainResId);
		entity.setFaultSource(faultSource);
		entity.setFaultDescription(mainRemark);
		entity.setIsCustomers(Integer.parseInt(mainIsGroupBusi));
		entity.setSubType(mainFaultNet);
		entity.setSpecialty(mainSpecialty);
//		巡检发现人
		entity.setFirstInitiator(initiator);
		try {
			entity.setFirstCreateTime(sFormat.parse(sFormat.format(createTime)));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
//		工单与专业关系 --start
		pnrTroubleTicketService.saveOrUpateSpecialty(processInstanceId, specialty);
//		工单与专业关系 --end
		entity.setTaskAssigneeJSON(taskAssigneeJSON);
		// entity.setDoPersonJSON(doPersonJSON);
		entity.setProcessInstanceId(processInstanceId);
		entity.setState(0);
		try {
			entity.setEndTime(sFormat.parse(dueDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		PnrResConfigMgr pnrResConfigMgr = (PnrResConfigMgr) ApplicationContextHolder.getInstance().getBean("PnrResConfigMgr");
		PnrResConfig pnrResConfig = pnrResConfigMgr.find(mainResId);
		entity.setCity(pnrResConfig.getCity());
		entity.setCountry(pnrResConfig.getCountry());
		pnrTroubleTicketService.save(entity);
//		发短信
		String msg2 = "故障工单号:"+processInstanceId+";主题:"+title+";站点:"+mainResName+";处理时间截止到:"+dueDate;
		CommonUtils.sendMsg(msg2, taskAssignee);
		return bn;
	}
	/**
	 * 自动生成审批的任务工单
	 */
   /* public Boolean automaticallyGenerateTask(String id,String userId,String deptId){
    	Boolean bn = false;
    	
    	 
         IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) ApplicationContextHolder.getInstance().getBean("inspectPlanResMgr");
        
         Long resId = Long.parseLong(id);
         InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(resId);
         String msg = "";
         if (inspectPlanRes.getExceptionFlag() != null && inspectPlanRes.getExceptionFlag() == 0) {
         	IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr) ApplicationContextHolder.getInstance().getBean("inspectPlanItemMgrImpl");
              Search search = new Search();
              search.addFilterEqual("planResId", resId);
              search.addFilterEqual("exceptionFlag", 0);
              
              List<InspectPlanItem> list = inspectPlanItemMgr.search(search);
              for(int i=0;i<list.size();i++){
             	 
             		 msg+=(i+1)+"、"+list.get(i).getContent()+"异常信息:"+list.get(i).getExceptionContent()+"。\r\n";
             		 
              }

         }

    	
        DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        //工单主题
        String title = "巡检隐患处理工单-"+inspectPlanRes.getResName();
        //派单人
        String initiator = userId;
        // 工单生成时间
        Date createTime = new Date();
//		工单子类型 巡检隐患处理
        String mainFaultNet ="101110105";
//		站点
        String mainResName = inspectPlanRes.getResName();
//		站点ID
        String mainResId =inspectPlanRes.getResCfgId();
//		工单内容
        String mainRemark = msg;

//		计划开始时间
        String sheetAcceptLimit = sFormat.format(createTime);
        
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 1);
        
//		计划结束时间
        String sheetCompleteLimit = sFormat.format(calendar.getTime());;
        //其他专业
        String[] specialtyStrings = {"101010806"};
        String mainSpecialty="";
        for(int i=0 ; i<specialtyStrings.length; i++)
        {	if(i==(specialtyStrings.length-1))
        	{
        		mainSpecialty+=specialtyStrings[i];
        	}else
        	{
        		mainSpecialty+=specialtyStrings[i]+",";
        	}
        }
   
//		接收组
        String candidateGroup = deptId;
        //流程开始
        FormService formService = (FormService) ApplicationContextHolder.getInstance().getBean("formService");
        IdentityService identityService = (IdentityService) ApplicationContextHolder.getInstance().getBean("identityService");
        identityService.setAuthenticatedUserId(initiator);
        RuntimeService runtimeService = (RuntimeService) ApplicationContextHolder.getInstance().getBean("runtimeService");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("taskWorkOrder");
        String processInstanceId = processInstance.getId();
        TaskService taskService = (TaskService) ApplicationContextHolder.getInstance().getBean("taskService");
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).active().list();
        String taskId = taskList.get(0).getId();
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        Map<String, String> map = new HashMap<String, String>();
        for (FormProperty formProperty : taskFormData.getFormProperties()) {
            if (formProperty.isWritable()) {
                String name = formProperty.getId();
                map.put(name, deptId);
            }
        }
        formService.submitTaskFormData(taskId, map);
        IPnrTaskTicketService pnrService = (IPnrTaskTicketService) ApplicationContextHolder.getInstance().getBean("pnrTaskTicketService");
        PnrTaskTicket entity = new PnrTaskTicket();
        entity.setTheme(title);
        entity.setSubType(mainFaultNet);
        entity.setSite(mainResName);
        entity.setMainResId(mainResId);
        entity.setContent(mainRemark);
        entity.setCreateTime(createTime);
        try {
        	entity.setStartTime(sFormat.parse(sheetAcceptLimit));
			entity.setEndTime(sFormat.parse(sheetCompleteLimit));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
        entity.setFailedSite(faultResName);
        entity.setFaultResId(faultResId);
        //保存故障字符串
       entity.setSpecialty(mainSpecialty);
       //保存专业与工单的关系--start
        pnrService.saveOrUpateSpecialty(processInstanceId, specialtyStrings);
      //保存专业与工单的关系--end
        entity.setInitiator(initiator);
        entity.setProcessInstanceId(processInstanceId);
        entity.setCandidateGroup(candidateGroup);
        PnrResConfigMgr pnrResConfigMgr = (PnrResConfigMgr) ApplicationContextHolder.getInstance().getBean("PnrResConfigMgr");
        String[] mainResIds = mainResId.split(",");
        PnrResConfig pnrResConfig = pnrResConfigMgr.find(mainResIds[0]);
        entity.setCity(pnrResConfig.getCity());
        entity.setCountry(pnrResConfig.getCountry());
        entity.setState(1);
        try {
            pnrService.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	return bn;
    }*/
	public void saveLocation(HttpServletRequest request,
			HttpServletResponse response, String userId) {
		String latitude = StaticMethod.nullObject2String(request
				.getParameter("latitude"));
		String longitude = StaticMethod.nullObject2String(request
				.getParameter("longitude"));
		String plan_res_id = StaticMethod.nullObject2String(request
				.getParameter("plan_res_id"));
		String status = StaticMethod.nullObject2String(request
				.getParameter("status"));// status ==0签退 1 签到
		String startCheckTime = StaticMethod.nullObject2String(request
				.getParameter("startCheckTime"));
		int signState = StaticMethod.nullObject2int(request
				.getParameter("signState"));

		if ("".equals(plan_res_id)) {
			MobileCommonUtils.responseWrite(response,
					MobileConstants.failureStr, "UTF-8");
			return;
		}

		try {
			double currentLatitude = Double
					.parseDouble("".equals(latitude) ? "0" : latitude);
			double currentLongitude = Double
					.parseDouble("".equals(longitude) ? "0" : longitude);
			IPnrInspectTrackMgr inspectTrackMgr = (IPnrInspectTrackMgr) ApplicationContextHolder
					.getInstance().getBean("pnrInspectTrackMgrImpl");
			PnrInspectTrack inspectTrack = new PnrInspectTrack();
			if (!"".equals(latitude) || !"".equals(longitude)) {
				inspectTrack.setSignLatitude(currentLatitude + "");
				inspectTrack.setSignLongitude(currentLongitude + "");
				inspectTrack.setSignState("1");
			} else {
				inspectTrack.setSignState("0");
			}
			inspectTrack.setPlanResId(plan_res_id);
			inspectTrack.setSignTime(StaticMethod
					.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
			inspectTrack.setStatus(status);

			IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) ApplicationContextHolder
					.getInstance().getBean("inspectPlanResMgr");
			InspectPlanRes res = inspectPlanResMgr.get(Long
					.parseLong(plan_res_id));

			double resLatitude = res.getResLatitude();
			double resLongitude = res.getResLongitude();
			double distance = InspectUtils.getDistance(resLatitude,
					resLongitude, currentLatitude, currentLongitude);// 误差距离,单位公里

			IErrorDistanceMgr errorDistanceMgr = (IErrorDistanceMgr) ApplicationContextHolder
					.getInstance().getBean("errorDistanceMgr");
			Search errorSearch = new Search();
			errorSearch.addFilterEqual("resource", res.getResType());
			ErrorDistance errorDistance = errorDistanceMgr
					.searchUnique(errorSearch);

			inspectTrack.setErrorRange(distance * 1000);
			res.setErrorDistance(distance * 1000);
			PartnerUserMgr partnerUserMgr = (PartnerUserMgr) ApplicationContextHolder
					.getInstance().getBean("partnerUserMgr");
			partnerUserMgr.updatePnrUserLocation(userId, currentLatitude + "",
					currentLongitude + ""); // 签退更新人员坐标

			if ("1".equals(status)) {// 签到时更新资源的第一次保存时间
				// &&"0".equals(signState)
				if (null == res.getSignTime()
						|| StringUtils.isEmpty(res.getSignTime() + "")) {
					res.setSignTime(new Date());// 签到时保存定位时间
					res.setStartSignTimes(res.getStartSignTimes() + 1);// 资源定位次数
					inspectTrack.setStartSignTimes(res.getStartSignTimes() + 1);// 轨迹定位次数
					res.setSignStatus(signState);
					res.setSignLatitude(currentLatitude);// 签到时定位坐标
					res.setSignLongitude(currentLongitude);
				}else{
					
					res.setSignTime(new Date());// 签到时保存定位时间-再次更新签到时间
				}

			}

			if ("0".equals(status)) {// 如果签退,则触发此条件,更新在站时长
				String timeOnSite = InspectUtils.getTimeDifference(
						startCheckTime, StaticMethod.getCurrentDateTime())
						+ "";
				float totalTimeOnSite = (null == res.getTimeOnSite() || "null"
						.equals(res.getTimeOnSite() + "")) ? Float
						.parseFloat(timeOnSite) : (res.getTimeOnSite() + Float
						.parseFloat(timeOnSite));
				res.setTimeOnSite(totalTimeOnSite);// 时间累计
				res.setEndSignTimes(StaticMethod.nullObject2int(res
						.getEndSignTimes()) + 1);// 自增签退次数
				res.setInspectTime(new Date());
				inspectTrack.setEndSignTimes(null == res.getEndSignTimes() ? 1
						: res.getEndSignTimes() + 1);
				IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr) ApplicationContextHolder
						.getInstance().getBean("inspectPlanMainMgr");
				Search search = new Search();
				search.addFilterEqual("id", res.getPlanId());
				List<InspectPlanMain> list = inspectPlanMainMgr.search(search);
				if (null != list && !list.isEmpty()) {// 更新计划的在站时长
					list
							.get(0)
							.setTimeOnSite(
									(null == list.get(0).getTimeOnSite() || "null"
											.equals(list.get(0).getTimeOnSite())) ? Float
											.parseFloat(timeOnSite)
											: (list.get(0).getTimeOnSite() + Float
													.parseFloat(timeOnSite)));
					inspectPlanMainMgr.save(list.get(0));
				}
			}

			inspectPlanResMgr.save(res);// 保存资源
			inspectTrackMgr.save(inspectTrack);
			String successStr = "[{\"success\":\"true\",\"sysTime\":'"
					+ StaticMethod.getCurrentDateTime() + "'}]";
			MobileCommonUtils.responseWrite(response, successStr, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			MobileCommonUtils.responseWrite(response,
					MobileConstants.failureStr, "UTF-8");
		}
	}

	/**
	 * 保存线路轨迹
	 */
	public void saveLineLocation(HttpServletRequest request,
			HttpServletResponse response, String userId) {
		String latitude = StaticMethod.nullObject2String(request
				.getParameter("latitude"));
		String longitude = StaticMethod.nullObject2String(request
				.getParameter("longitude"));
		String plan_res_id = StaticMethod.nullObject2String(request
				.getParameter("plan_res_id"));
		String segId = StaticMethod.nullObject2String(request
				.getParameter("segId"));
		String tlp_seg = StaticMethod.nullObject2String(request
				.getParameter("tlp_seg"));
		String status = StaticMethod.nullObject2String(request
				.getParameter("status"));// status ==0签退 1 签到
		int errorDistance = 3000;// 单位公里
		int errorDistance2 = 13;// 单位公里
		if ("".equals(plan_res_id)) {
			MobileCommonUtils.responseWrite(response,
					MobileConstants.failureStr, "UTF-8");
			return;
		}

		try {
			double currentLatitude = Double
					.parseDouble("".equals(latitude) ? "0" : latitude);
			double currentLongitude = Double
					.parseDouble("".equals(longitude) ? "0" : longitude);
			System.out.println("currentLatitude   " + currentLatitude
					+ "   currentLongitude   " + currentLongitude);
			IPnrInspectTrackMgr inspectTrackMgr = (IPnrInspectTrackMgr) ApplicationContextHolder
					.getInstance().getBean("pnrInspectTrackMgrImpl");
			// 当用户将坐标上传过来后,查询该线路段中符合该点的数据 误差为 errorDistance,只查询前10个在理想情况下应该为1个
			// SQL 误差距离待测试
			String sql = "select first 10 "
					+
					// " point.tlp_pa_la as latitude, point.tlp_pa_lo as
					// longitude, point.id as id,point.tlp_pa_name as
					// name,root((SQRT(ABS(point.tlp_pa_lo-"+currentLongitude+"))+SQRT(ABS(point.tlp_pa_la-"+currentLatitude+"))),2)*1000
					// AS errordistance "+
					" point.id as id,point.tlp_pa_name as PNAME , point.tlp_pa_la as latitude, point.tlp_pa_lo as longitude,root((SQRT(ABS(point.tlp_pa_lo-"
					+ currentLongitude + "))+SQRT(ABS(point.tlp_pa_la-"
					+ currentLatitude + "))),2)*1000 AS errordistance "
					+ " from pnr_inspect_task_link point " + " where tlp_seg='"
					+ tlp_seg + "' and  root((SQRT(ABS(point.tlp_pa_lo-"
					+ currentLongitude + "))+SQRT(ABS(point.tlp_pa_la-"
					+ currentLatitude + "))),2)*1000<" + errorDistance
					+ " order by errordistance asc";
			System.out.println(sql);
			List returnList = inspectTrackMgr.getLineByLocation(sql);
			Object[] obj;
			String returnJson = "[{";
			StringBuilder builder = new StringBuilder();
			builder.append("[{\"success\":\"true\",\"datas\":[");
			for (int i = 0; i < returnList.size(); i++) {
				obj = (Object[]) returnList.get(i);
				String id = obj[0] + "";
				String name = obj[1] + "";
				double srcLatitude = Double.parseDouble("".equals(obj[2]) ? "0"
						: obj[2] + "");
				double srcLongitude = Double
						.parseDouble("".equals(obj[3]) ? "0" : obj[3] + "");
				double distance = MobileCommonUtils.getDistance(
						currentLatitude, currentLongitude, srcLatitude,
						srcLongitude);
				builder.append("{\"id\":").append("\"" + id + "\",");
				builder.append("\"name\":").append("\"" + name + "\",");
				builder.append("\"distance\":").append("\"" + distance + "\"}");
				if (i != returnList.size() - 1) {
					builder.append(",");
				}

				// --------------更新link表
				if (distance < errorDistance2) {// 如果误差小于errorDistance2公里,
					PnrInspectTaskLinkService pnrInspectTaskLinkService = (PnrInspectTaskLinkService) ApplicationContextHolder
							.getInstance().getBean("pnrInspectTaskLinkService");
					Search search = new Search();
					search.addFilterEqual("netResId", id);
					PnrInspectTaskLink link = pnrInspectTaskLinkService
							.searchUnique(search);
					if (null != link) {// 如果link中有该条数据,则进行更新
						link.setArrivedLo(longitude);
						link.setArrivedLa(latitude);
						link.setArrivedTime(StaticMethod.getCurrentDateTime());
						pnrInspectTaskLinkService.save(link);
					}
				}

			}
			builder.append("]}]");
			// System.out.println("builder "+builder);
			String result = MobileCommonUtils.toJson(returnList);
			// System.out.println("result "+result);
			String successStr = builder.toString();
			// if(!returnList.isEmpty()){
			// obj = (Object[]) returnList.get(0);
			PnrInspectTrack inspectTrack = new PnrInspectTrack();
			inspectTrack.setSignLatitude(currentLatitude + "");
			inspectTrack.setSignLongitude(currentLongitude + "");
			inspectTrack.setSignState("1");
			inspectTrack.setPlanResId(plan_res_id);
			inspectTrack.setSignTime(StaticMethod
					.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
			inspectTrack.setStatus(status);
			inspectTrackMgr.save(inspectTrack);// 不管该点是否已经保存,都进行插入操作
			// }
			// 对InspectLineTrack表进行操作,如果用户上传的点,经过匹配后,对该线路段而言,有临近的点,则向InspectLineTrack插入或更新
			if (!returnList.isEmpty()) {// 如果经过匹配,不为空
				IInspectLineTrackMgr distanceMgr = (IInspectLineTrackMgr) ApplicationContextHolder
						.getInstance().getBean("lineTrackMgr");
				obj = (Object[]) returnList.get(0);// 只选择最近的点
				String id = obj[0] + "";
				Search search = new Search();
				search.addFilterEqual("lineId", id);
				SearchResult<InspectLineTrack> searchResult = distanceMgr
						.searchAndCount(search);// 查询该点是否已经保存过

				InspectLineTrack track = new InspectLineTrack();
				track.setLatitude(latitude);
				track.setLongitude(longitude);
				track.setSegId(segId);
				track.setSignTime(new Date());

				if (searchResult.getTotalCount() > 0) {// 如果该点已经保存过,则进行更新
					track.setId(id);
				}
				distanceMgr.save(track);
			}

			MobileCommonUtils.responseWrite(response, successStr, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			MobileCommonUtils.responseWrite(response,
					MobileConstants.failureStr, "UTF-8");
		}
	}

	/**
	 * 巡检线路光缆段查询
	 * 
	 * @author pointatyou
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void getTransLineList(HttpServletRequest request,
			HttpServletResponse response) {
		String mainId = StaticMethod.nullObject2String(request
				.getParameter("mainId"));
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) ApplicationContextHolder
				.getInstance().getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		String whereStr = " where tlInspectFlag = 1 ";
		List city = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", city);
		request.setAttribute("city1", city);

		PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) ApplicationContextHolder
				.getInstance().getBean("PnrResConfigMgr");
		String pageIndexString = StaticMethod.nullObject2int(request
				.getParameter("pageIndex"))
				+ "";
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;

		PnrResConfig pnrResConfigForm = new PnrResConfig();
		String resName = StaticMethod.null2String(request
				.getParameter("resName"));
		String region = StaticMethod.null2String(request.getParameter("city"));
		String country = StaticMethod.null2String(request
				.getParameter("country"));
		pnrResConfigForm.setCity(region);
		pnrResConfigForm.setCountry(country);
		pnrResConfigForm.setResName(resName);

		if (!StringUtils.isEmpty(pnrResConfigForm.getResName())) {
			whereStr = whereStr + " and resName like '%"
					+ pnrResConfigForm.getResName() + "%'";
		}
		if (!StringUtils.isEmpty(pnrResConfigForm.getCity())) {
			whereStr = whereStr + " and city='" + pnrResConfigForm.getCity()
					+ "'";
		}
		if (!StringUtils.isEmpty(request.getParameter("region"))) {
			whereStr = whereStr + " and city='"
					+ request.getParameter("region") + "'";
		}

		Map map = pnrResConfiMgr.getResources(firstResult
				* CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE,
				whereStr);

		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemDeptManager");

		HashMap<String, String> usermap = null;
		try {
			usermap = (HashMap<String, String>) PartnerPrivUtils
					.userIsPersonnel(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String flag = "";
		if (null != usermap) {
			flag = usermap.get("isPersonnel");
		}
		if (flag.equals("y")) { // 是代维人员
			request.setAttribute("isyd", "no");
		} else {// 此时是移动人员
			TawSystemDept dept = deptMgr.getTawSystemDept(sessionForm
					.getDeptpriid());
			request.setAttribute("isyd", "yes");
			request.setAttribute("dept", dept.getAreaid());
		}

		request.setAttribute("list", map.get("result"));
		request.setAttribute("pnrResConfigForm", pnrResConfigForm);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", map.get("total"));
		/**
		 * add by lee
		 */
		TransLineHandler.handRequestLineSeg(request, response);
	}

	/**
	 * 巡检线路光缆段中点查询
	 * 
	 * @author pointatyou
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void getTransLinePointList(HttpServletRequest request,
			HttpServletResponse response) {

		String id = StaticMethod.null2String(request.getParameter("id"));
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		ID2NameService service = (ID2NameService) ApplicationContextHolder
				.getInstance().getBean("ID2NameGetServiceCatch");
		String sql = "select dd.tl_pa_lo as dd_tl_pa_lo,dd.tl_pa_la as dd_tl_pa_la,dd.tl_pz_lo as dd_tl_pz_lo,"
				+ " dd.tl_pz_la as dd_tl_pz_la,dd.tl_pa_name as dd_tl_pa_name,dd.tl_pz_name as dd_tl_pz_name,"
				+

				"point.id as id,point.tlp_pz_la as point_tlp_pz_la,point.tlp_pz_lo as point_tlp_pz_lo,point.tlp_pz_name as point_tlp_pz_name,"
				+

				"point.tlp_pa_lo as point_tlp_pa_lo,point.tlp_pa_la  as point_tlp_pa_la,point.tlp_pa_name as point_tlp_pa_name,dd.res_name as dd_res_name"
				+ " from pnr_trans_line_point point,pnr_res_config  dd  "
				+ " where  point.tlp_wire=dd.tl_wire and point.tlp_seg=dd.tl_seg and dd.tl_inspect_flag = 1 and point.tlp_source='0' and dd.id='"
				+ id + "'";
		List<ListOrderedMap> list = jdbcService.queryForList(sql);
		try {
			response.setCharacterEncoding("utf-8");
			List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
			Gson gson = new GsonBuilder().serializeNulls().create();

			HashMap<String, String> jsonMap;
			StringBuffer grid_buffer = new StringBuffer();
			if (!list.isEmpty()) {
				Map jsonMap1 = new HashMap<String, String>();
				ListOrderedMap map1 = list.get(0);
				String ddTlPaLo = StaticMethod.nullObject2String(map1
						.get("dd_tl_pa_lo"));
				String ddTlPaLa = StaticMethod.nullObject2String(map1
						.get("dd_tl_pa_la"));
				String ddTlPzLo = StaticMethod.nullObject2String(map1
						.get("dd_tl_pz_lo"));
				String ddTlPzLa = StaticMethod.nullObject2String(map1
						.get("dd_tl_pz_la"));
				String ddTlPaName = StaticMethod.nullObject2String(map1
						.get("dd_tl_pa_name"));
				String ddTlPzName = StaticMethod.nullObject2String(map1
						.get("dd_tl_pz_name"));
				String dd_res_name = StaticMethod.nullObject2String(map1
						.get("dd_res_name"));
				String resAType;
				String resBType;
				if (ddTlPaName.contains("接头盒") && ddTlPaName != "") {
					resAType = "接头盒";
				} else {
					resAType = "站点";
				}
				if (ddTlPzName.contains("接头盒") && ddTlPzName != "") {
					resBType = "接头盒";
				} else {
					resBType = "站点";
				}
				jsonMap1.put("ddTlPaLo", ddTlPaLo);
				jsonMap1.put("ddTlPaLa", ddTlPaLa);
				jsonMap1.put("ddTlPzLo", ddTlPzLo);
				jsonMap1.put("ddTlPzLa", ddTlPzLa);
				jsonMap1.put("ddTlPaName", ddTlPaName);
				jsonMap1.put("ddTlPzName", ddTlPzName);
				jsonMap1.put("dd_res_name", dd_res_name);
				jsonMap1.put("resAType", resAType);
				jsonMap1.put("resBType", resBType);
				if (ddTlPaLo != "" && ddTlPaLa != "" && ddTlPzLo != ""
						&& ddTlPzLa != "") {
					jsonList.add(jsonMap1);
				}
				for (int i = 0; i < list.size(); i++) {
					jsonMap = new HashMap<String, String>();
					ListOrderedMap map = list.get(i);
					String tlpPaLo = StaticMethod.nullObject2String(map
							.get("point_tlp_pa_lo"));
					String tlpPaLa = StaticMethod.nullObject2String(map
							.get("point_tlp_pa_la"));
					String point_tlp_pa_name = StaticMethod
							.nullObject2String(map.get("point_tlp_pa_name"));
					String tlp_pz_la = StaticMethod.nullObject2String(map
							.get("point_tlp_pz_la"));
					String tlp_pz_lo = StaticMethod.nullObject2String(map
							.get("point_tlp_pz_lo"));
					String tlp_pz_name = StaticMethod.nullObject2String(map
							.get("point_tlp_pz_name"));
					String tempId = StaticMethod.nullObject2String(map
							.get("id"));
					String type;
					if (point_tlp_pa_name.contains("标石")
							&& point_tlp_pa_name != "") {
						type = "标石";
					} else if (point_tlp_pa_name.contains("井")
							&& point_tlp_pa_name != "") {
						type = "井";
					} else {
						type = "标石";
					}

					jsonMap.put("tlpPaLo", tlpPaLo);
					jsonMap.put("tlpPaLa", tlpPaLa);
					jsonMap.put("point_tlp_pa_name", point_tlp_pa_name);
					jsonMap.put("type", type);
					jsonMap.put("id", tempId);
					jsonMap.put("tlp_pz_la", tlp_pz_la);
					jsonMap.put("tlp_pz_lo", tlp_pz_lo);
					jsonMap.put("tlp_pz_name", tlp_pz_name);
					if (tlpPaLo != "" && tlpPaLa != "") {
						jsonList.add(jsonMap);
					}
				}
			}

			HashMap<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("resultSize", jsonList.size());
			returnMap.put("list", jsonList);
			String returnStr = MobileCommonUtils.toJson(returnMap);
			MobileCommonUtils.responseWrite(response, returnStr.toString(),
					"UTF-8");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@SuppressWarnings("unchecked")
	private boolean isRes2Person(String areaId){
		IPnrResConfigDaoJdbc pnrResConfigDaoJdbc = (PnrResConfigDaoJdbc) ApplicationContextHolder.getInstance().getBean("pnrResConfigDaoJdbc");
		ArrayList<Map<String,String>> list = (ArrayList<Map<String,String>>)pnrResConfigDaoJdbc.getResPersonFlag();
		Map trpf;
		for(int i=0;i<list.size();i++){
			trpf = list.get(i);
			if(areaId == null){
				return false;
			}else{
				if(areaId.contains((String)trpf.get("city_id"))){
					return true;
				}
			}
		}
		return false;
	}
	/*
	 * 根据用户登录名和部门Id查询出用户负责的资源配置Id
	 * 用于关联查询元任务(res_cfg_id关联字段)
	 */
	private String buildConfigsStr(String userId,String deptId){
		IPnrResConfigDaoJdbc pnrResConfigDaoJdbc = (PnrResConfigDaoJdbc) ApplicationContextHolder.getInstance().getBean("pnrResConfigDaoJdbc");
		ArrayList<Map<String,String>> list = (ArrayList<Map<String,String>>)pnrResConfigDaoJdbc.getPanResByUser(userId, deptId);
		String str = "";
		for(int i=0;i<list.size();i++){
			str += "'"+list.get(i).get("id") + "',";
		}
		String a = str.substring(0, str.length()-1);
		if(str.length()>0){
			a = str.substring(0, str.length()-1);
		}
		return "("+a+")";
	}
}
