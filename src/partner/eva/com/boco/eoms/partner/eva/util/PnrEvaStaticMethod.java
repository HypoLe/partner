package com.boco.eoms.partner.eva.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.boco.eoms.base.api.EOMSMgr;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.role.service.ITawSystemSubRoleManager;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.commons.system.user.service.ITawSystemUserRefRoleManager;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.eva.model.EvaOrg;

/**
 * 
 * <p>
 * Title:考核静态方法类
 * </p>
 * <p>
 * Description:考核静态方法类
 * </p>
 * <p>
 * Date:2008-12-9 下午07:10:44
 * </p>
 * 
 * @author 李秋野
 * @version 3.5.1
 * 
 */
public class PnrEvaStaticMethod {

	/**
	 * 根据周期获得周期内的第一天
	 * 
	 * @param cycle
	 *            周期
	 * @param currentDateTime
	 *            当前时间，格式为yyyy-MM-dd
	 * @return
	 */
	public static String getStartTimeByCycle(String cycle,
			String currentDateTime) {
		String startTime = currentDateTime;
		if (PnrEvaConstants.CYCLE_YEAR.equals(cycle)) {
			int year = Integer.parseInt(currentDateTime.substring(0, 4));
			Date date = PnrEvaDateUtil.getFirstDayOfYear(year);
			startTime = PnrEvaDateUtil.dateToString(date);
		} else if (PnrEvaConstants.CYCLE_QUARTER.equals(cycle)) {
			int year = Integer.parseInt(currentDateTime.substring(0, 4));
			int month = Integer.parseInt(currentDateTime.substring(5, 7));
			int quarter = 0;
			if (month <= 3) {
				quarter = 1;
			} else if (month <= 6) {
				quarter = 2;
			} else if (month <= 9) {
				quarter = 3;
			} else if (month <= 12) {
				quarter = 4;
			}
			Date date = PnrEvaDateUtil.getFirstDayOfQuarter(year, quarter);
			startTime = PnrEvaDateUtil.dateToString(date);
		} else if (PnrEvaConstants.CYCLE_MONTH.equals(cycle)) {
			int year = Integer.parseInt(currentDateTime.substring(0, 4));
			int month = Integer.parseInt(currentDateTime.substring(5, 7));
			Date date = PnrEvaDateUtil.getFirstDayOfMonth(year, month);
			startTime = PnrEvaDateUtil.dateToString(date);
		} else if (PnrEvaConstants.CYCLE_WEEK.equals(cycle)) {
			Date currentDate = PnrEvaDateUtil.stringToDate(currentDateTime);
			Date date = PnrEvaDateUtil.getFirstDayOfWeek(currentDate);
			startTime = PnrEvaDateUtil.dateToString(date);
		}
		return startTime;
	}

	/**
	 * 根据周期获得周期内的最后一天
	 * 
	 * @param cycle
	 * @return
	 */
	public static String getEndTimeByCycle(String cycle, String currentDateTime) {
		String endTime = currentDateTime;
		if (PnrEvaConstants.CYCLE_YEAR.equals(cycle)) {
			int year = Integer.parseInt(currentDateTime.substring(0, 4));
			Date date = PnrEvaDateUtil.getLastDayOfYear(year);
			endTime = PnrEvaDateUtil.dateToString(date);
		} else if (PnrEvaConstants.CYCLE_QUARTER.equals(cycle)) {
			int year = Integer.parseInt(currentDateTime.substring(0, 4));
			int month = Integer.parseInt(currentDateTime.substring(5, 7));
			int quarter = 0;
			if (month <= 3) {
				quarter = 1;
			} else if (month <= 6) {
				quarter = 2;
			} else if (month <= 9) {
				quarter = 3;
			} else if (month <= 12) {
				quarter = 4;
			}
			Date date = PnrEvaDateUtil.getLastDayOfQuarter(year, quarter);
			endTime = PnrEvaDateUtil.dateToString(date);
		} else if (PnrEvaConstants.CYCLE_MONTH.equals(cycle)) {
			int year = Integer.parseInt(currentDateTime.substring(0, 4));
			int month = Integer.parseInt(currentDateTime.substring(5, 7));
			Date date = PnrEvaDateUtil.getLastDayOfMonth(year, month);
			endTime = PnrEvaDateUtil.dateToString(date);
		} else if (PnrEvaConstants.CYCLE_WEEK.equals(cycle)) {
			Date currentDate = PnrEvaDateUtil.stringToDate(currentDateTime);
			Date date = PnrEvaDateUtil.getLastDayOfWeek(currentDate);
			endTime = PnrEvaDateUtil.dateToString(date);
		}
		return endTime;
	}

	/**
	 * 根据orgId返回orgName
	 * 
	 * @param orgId
	 *            组织Id
	 * @param orgType
	 *            组织类型
	 * @return
	 */
	public static String orgId2Name(String orgId, String orgType)
			throws DictDAOException {
		String orgName = "";
		// 若为角色则显示角色名称
		if (PnrEvaConstants.ORG_SUBROLE.equals(orgType)) {
			ITawSystemSubRoleManager subRoleMgr = (ITawSystemSubRoleManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemSubRoleManager");
			return subRoleMgr.getTawSystemSubRole(orgId).getSubRoleName();

		}
		// 若为用户则显示用户名称
		else if (PnrEvaConstants.ORG_USER.equals(orgType)) {
			ITawSystemUserManager userMgr = (ITawSystemUserManager) ApplicationContextHolder
					.getInstance().getBean("itawSystemUserManager");
			return userMgr.id2Name(orgId);
		}
		// 若为部门则显示部门名称
		else if (PnrEvaConstants.ORG_DEPT.equals(orgType)) {
			ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemDeptManager");
			return deptMgr.id2Name(orgId);
		}
		// 若为地域则显示地域名称
		else if (PnrEvaConstants.ORG_AREA.equals(orgType)) {
			
			ITawSystemAreaManager areaMgr = (ITawSystemAreaManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemAreaManager");
			TawSystemArea tawSystemArea = areaMgr.getAreaByAreaId(orgId);
			return tawSystemArea.getAreaname();
		}
		return orgName;
	}

	/**
	 * 组织名称（暂时停用）
	 * 
	 * @return 组织名称
	 */
	public static String getOrgName(String orgId, String orgType) {
		String orgName = "";
		// 若为角色则显示角色名称
		if (StaticVariable.PRIV_ASSIGNTYPE_ROLE.equals(orgType)) {
			ITawSystemSubRoleManager roleMgr = (ITawSystemSubRoleManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemSubRoleManager");
			return roleMgr.getTawSystemSubRole(orgId).getSubRoleName();

		}
		// 若为用户则显示用户名称
		else if (StaticVariable.PRIV_ASSIGNTYPE_USER.equals(orgType)) {
			ITawSystemUserManager userMgr = (ITawSystemUserManager) ApplicationContextHolder
					.getInstance().getBean("itawSystemUserManager");
			return userMgr.getUserByuserid(orgId).getUsername();
		}
		// 若为部门则显示部门名称
		else if (StaticVariable.PRIV_ASSIGNTYPE_DEPT.equals(orgType)) {
			ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemDeptManager");
			return deptMgr.getDeptinfobydeptid(orgId, "0").getDeptName();
		}
		return orgName;
	}

	/**
	 * 从json中取发布组织列表
	 * 
	 * @param orgs
	 *            json串
	 * @return 部门列表
	 */
	public static List jsonOrg2Orgs(String orgs) {
		JSONArray jsonOrgs = JSONArray.fromString(orgs);
		List orgList = new ArrayList();
		for (Iterator it = jsonOrgs.iterator(); it.hasNext();) {
			JSONObject org = (JSONObject) it.next();
			// 发布组织id
			String id = org.getString(UIConstants.JSON_ID);
			// 节点类型
			String nodeType = org.getString(UIConstants.JSON_NODETYPE);
			// 限制发布范围
			if (PnrEvaConstants.ORG_DEPT.equals(nodeType)
					|| StaticVariable.PRIV_ASSIGNTYPE_DEPT.equals(nodeType)) {
				// 获取所有子部门（包括本部门信息）
				List depts = EOMSMgr.getOrgMgrs().getDeptMgr().listAllSubDept(
						id);
				for (int i = 0; i < depts.size(); i++) {
					TawSystemDept dept = (TawSystemDept) depts.get(i);
					orgList.add(new EvaOrg(dept.getDeptId(),
							StaticVariable.PRIV_ASSIGNTYPE_DEPT));
				}
			} else if (PnrEvaConstants.ORG_SUBROLE.equals(nodeType)
					|| StaticVariable.PRIV_ASSIGNTYPE_ROLE.equals(nodeType)) {
				orgList
						.add(new EvaOrg(id, StaticVariable.PRIV_ASSIGNTYPE_ROLE));
			} else if (PnrEvaConstants.ORG_USER.equals(nodeType)
					|| StaticVariable.PRIV_ASSIGNTYPE_USER.equals(nodeType)) {
				orgList
						.add(new EvaOrg(id, StaticVariable.PRIV_ASSIGNTYPE_USER));
			}
		}
		return orgList;
	}

	/**
	 * 将取出的组织列表转换成json的字符串
	 * 
	 * @param threadPermimissionOrgs
	 *            信息权限列表
	 * @return 信息权限转换后的字符串
	 */
	public static String getOrgList(List orgs) {
		String jsonOrgs = "[]";
		if (null != orgs && !orgs.isEmpty()) {
			JSONArray jsonRoot = new JSONArray();
			for (Iterator it = orgs.iterator(); it.hasNext();) {
				EvaOrg org = (EvaOrg) it.next();
				int orgtype = StaticMethod.null2int(org.getOrgType());
				JSONObject item = new JSONObject();
				// 构造json对象
				item.put(UIConstants.JSON_ID, org.getOrgId());
				item.put(UIConstants.JSON_NAME, PnrEvaStaticMethod.getOrgName(org
						.getOrgId(), Integer.toString(orgtype)));
				// 判断发布对象类型
				String orgtypestr = "";
				switch (orgtype) {
				case 1:
					orgtypestr = "dept";
					break;
				case 2:
					orgtypestr = "user";
					break;
				}
				item.put(UIConstants.JSON_NODETYPE, orgtypestr);
				jsonRoot.put(item);
			}
			jsonOrgs = jsonRoot.toString();
		}
		return jsonOrgs;
	}
	
	

	/**
	 * 根据用户名和操作类型得到对应考核模块子角色,地域信息(作为该用户的操作地域信息)
	 * 
	 * @param userId
	 *            用户名id
	 * @param operateType
	 *            操作类型
	 *            
	 * @return 包括地域id,子角色id,错误信息的Map
	 */
	public static Map getRoleAreaByUserId(String userId,String operateType) {
		Map returnMap = new HashMap();
		String areaId = "";
		String roleId = "";
		String subRoleId = ""; 
		String errMessage = ""; 
		//当用户是admin时不做地域筛选,地域默认为1,子角色id为""
		if("admin".equals(userId)){
			areaId = "1";
		}else{
			ITawSystemSubRoleManager subRoleMgr = (ITawSystemSubRoleManager)ApplicationContextHolder
				.getInstance().getBean("ItawSystemSubRoleManager");
			PnrEvaRoleIdList roleIdList = (PnrEvaRoleIdList)ApplicationContextHolder
			.getInstance().getBean("pnrEvaRoleIdList");
			if(operateType.equals(PnrEvaConstants.OPERATE_TREE_CONFIG)){
				roleId = roleIdList.getTreeConfigRoleId();
			}else if(operateType.equals(PnrEvaConstants.OPERATE_TEMPLATE_AUDIT)){
				roleId = roleIdList.getTemplateAuditRoleId();
			}else if(operateType.equals(PnrEvaConstants.OPERATE_REPORT_AUDIT)){
				roleId = roleIdList.getReportAuditRoleId();
			}else if(operateType.equals(PnrEvaConstants.OPERATE_REPORT_EXECUTE)){
				roleId = roleIdList.getReportExecuteRoleId();
			}else if(operateType.equals(PnrEvaConstants.OPERATE_REPORT_SHOW)){
				roleId = roleIdList.getReportShowRoleId();
			}
			List subRoleList = subRoleMgr.getSubRoles(userId,roleId);
			
			for (Iterator iter = subRoleList.iterator(); iter.hasNext();) {
				TawSystemSubRole subRole = (TawSystemSubRole) iter.next();
					//当该用户有多个地域信息的时候,提示用户错误信息
					if(!areaId.equals("")&&!areaId.equals(subRole.getDeptId())){
						errMessage = "您的同一个操作角色,配置了不同的地域信息.";
					}else{
						areaId = subRole.getDeptId();
						subRoleId = subRole.getId();
					}
				}
			}
			//当该用户没有地域信息的时候,提示用户错误信息
			if("".equals(areaId)){
				errMessage = "您没有配置考核模块该操作角色.";
			}
			System.out.println("errMessage="+errMessage);
			returnMap.put("areaId", areaId);
			returnMap.put("subRoleId", subRoleId);
			returnMap.put("errMessage", errMessage);
		return returnMap;
	}


	/**
	 * 根据地域和操作类型得到对应考核模块子角色id,人员列表
	 * 
	 * @param areaId
	 *            地域id
	 * @param operateType
	 *            操作类型
	 *            
	 * @return 包括人员id集合,子角色id,错误信息的Map
	 */
	public static Map getSubroleByAreaId(String areaId,String operateType) {
		Map returnMap = new HashMap();
		List user = new ArrayList();
		String roleId = "";
		String subRoleId = ""; 
		String errMessage = ""; 
			ITawSystemSubRoleManager subRoleMgr = (ITawSystemSubRoleManager)ApplicationContextHolder
				.getInstance().getBean("ItawSystemSubRoleManager");
			ITawSystemUserRefRoleManager userRefRoleMgr = (ITawSystemUserRefRoleManager)ApplicationContextHolder
				.getInstance().getBean("itawSystemUserRefRoleManager");
			PnrEvaRoleIdList roleIdList = (PnrEvaRoleIdList)ApplicationContextHolder
			.getInstance().getBean("pnrEvaRoleIdList");
			if(operateType.equals(PnrEvaConstants.OPERATE_TREE_CONFIG)){
				roleId = roleIdList.getTreeConfigRoleId();
			}else if(operateType.equals(PnrEvaConstants.OPERATE_TEMPLATE_AUDIT)){
				roleId = roleIdList.getTemplateAuditRoleId();
			}else if(operateType.equals(PnrEvaConstants.OPERATE_REPORT_AUDIT)){
				roleId = roleIdList.getReportAuditRoleId();
			}else if(operateType.equals(PnrEvaConstants.OPERATE_REPORT_EXECUTE)){
				roleId = roleIdList.getReportExecuteRoleId();
			}
			List subRoleList = subRoleMgr.getTawSystemSubRoles(Long.parseLong(roleId));
			
			for (Iterator iter = subRoleList.iterator(); iter.hasNext();) {
				TawSystemSubRole subRole = (TawSystemSubRole) iter.next();
					if(subRole.getDeptId().equals(areaId)){
						subRoleId = subRole.getId();
					}
				}
			
			//当该用户没有地域信息的时候,提示用户错误信息
			if("".equals(subRoleId)){
				errMessage = "该地域没有配置考核模块该操作角色.";
			}else{
				user = userRefRoleMgr.getUserbyroleid(subRoleId);
			}
			returnMap.put("user", user);
			returnMap.put("subRoleId", subRoleId);
			returnMap.put("errMessage", errMessage);
		return returnMap;
	}
	
	public static void main(String[] args) {
		System.out.println("本年第一天："
				+ getStartTimeByCycle(PnrEvaConstants.CYCLE_YEAR, PnrEvaDateUtil
						.getCurrentDateTime(PnrEvaConstants.DATE_FORMAT)));
		System.out.println("本季度第一天："
				+ getStartTimeByCycle(PnrEvaConstants.CYCLE_QUARTER, PnrEvaDateUtil
						.getCurrentDateTime(PnrEvaConstants.DATE_FORMAT)));
		System.out.println("本月第一天："
				+ getStartTimeByCycle(PnrEvaConstants.CYCLE_MONTH, PnrEvaDateUtil
						.getCurrentDateTime(PnrEvaConstants.DATE_FORMAT)));
		System.out.println("本周第一天："
				+ getStartTimeByCycle(PnrEvaConstants.CYCLE_WEEK, PnrEvaDateUtil
						.getCurrentDateTime(PnrEvaConstants.DATE_FORMAT)));
		System.out.println("本年最后一天："
				+ getEndTimeByCycle(PnrEvaConstants.CYCLE_YEAR, PnrEvaDateUtil
						.getCurrentDateTime(PnrEvaConstants.DATE_FORMAT)));
		System.out.println("本季度最后一天："
				+ getEndTimeByCycle(PnrEvaConstants.CYCLE_QUARTER, PnrEvaDateUtil
						.getCurrentDateTime(PnrEvaConstants.DATE_FORMAT)));
		System.out.println("本月最后一天："
				+ getEndTimeByCycle(PnrEvaConstants.CYCLE_MONTH, PnrEvaDateUtil
						.getCurrentDateTime(PnrEvaConstants.DATE_FORMAT)));
		System.out.println("本周最后一天："
				+ getEndTimeByCycle(PnrEvaConstants.CYCLE_WEEK, PnrEvaDateUtil
						.getCurrentDateTime(PnrEvaConstants.DATE_FORMAT)));
	}
	
	/**
	 * 根据考核层面得到地域id集合
	 * 
	 * @param executeType
	 *            考核层面
	 *            
	 * @return 地域id集合
	 */
	public static String[] getAreasByexecuteType(String executeType) {
		List areaList = new ArrayList();
		String[] areas = null;
		TawSystemArea tawSystemArea = new TawSystemArea();
		PnrEvaRoleIdList roleIdList = (PnrEvaRoleIdList)ApplicationContextHolder
		.getInstance().getBean("pnrEvaRoleIdList");
		String rootAreaId = roleIdList.getRootAreaId();
		if(executeType.equals(PnrEvaConstants.EXECUTE_TYPE_PROVINCE)){
			areaList.add(rootAreaId);
		}else if(executeType.equals(PnrEvaConstants.EXECUTE_TYPE_CITY)){
			ITawSystemAreaManager areaMgr = (ITawSystemAreaManager) ApplicationContextHolder
			.getInstance().getBean("ItawSystemAreaManager");
			List tawCityList = areaMgr.getSonAreaByAreaId(rootAreaId);
			for(int i=0;i<tawCityList.size();i++){
				tawSystemArea = (TawSystemArea)tawCityList.get(i);
				areaList.add(tawSystemArea.getAreaid());
			}
		}else if(executeType.equals(PnrEvaConstants.EXECUTE_TYPE_TOWN)){
			ITawSystemAreaManager areaMgr = (ITawSystemAreaManager) ApplicationContextHolder
			.getInstance().getBean("ItawSystemAreaManager");
			List tawCityList = areaMgr.getSonAreaByAreaId(rootAreaId);
			for(int i=0;i<tawCityList.size();i++){
				tawSystemArea = (TawSystemArea)tawCityList.get(i);
				List tawTownList = areaMgr.getSonAreaByAreaId(tawSystemArea.getAreaid());
				for(int j=0;j<tawTownList.size();j++){
					tawSystemArea = (TawSystemArea)tawTownList.get(j);
					areaList.add(tawSystemArea.getAreaid());
				}
			}
		}
		areas = (String[])areaList.toArray(new String[areaList.size()]);
		return areas;
	}
}
