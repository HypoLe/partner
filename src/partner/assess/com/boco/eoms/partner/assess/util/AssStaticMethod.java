package com.boco.eoms.partner.assess.util;

import java.sql.Date;
import java.text.DecimalFormat;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.commons.system.role.service.ITawSystemSubRoleManager;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;

/**
 * 
 * <p>
 * Title:考核静态方法类
 * </p>
 * <p>
 * Description:考核静态方法类
 * </p>
 * <p>
 * Date:Nov 29, 2010 9:46:47 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class AssStaticMethod {

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
		if (AssConstants.CYCLE_YEAR.equals(cycle)) {
			int year = Integer.parseInt(currentDateTime.substring(0, 4));
			Date date = DateTimeUtil.getFirstDayOfYear(year);
			startTime = DateTimeUtil.dateToString(date);
		} else if (AssConstants.CYCLE_QUARTER.equals(cycle)) {
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
			Date date = DateTimeUtil.getFirstDayOfQuarter(year, quarter);
			startTime = DateTimeUtil.dateToString(date);
		} else if (AssConstants.CYCLE_MONTH.equals(cycle)) {
			int year = Integer.parseInt(currentDateTime.substring(0, 4));
			int month = Integer.parseInt(currentDateTime.substring(5, 7));
			Date date = DateTimeUtil.getFirstDayOfMonth(year, month);
			startTime = DateTimeUtil.dateToString(date);
		} else if (AssConstants.CYCLE_WEEK.equals(cycle)) {
			Date currentDate = DateTimeUtil.stringToDate(currentDateTime);
			Date date = DateTimeUtil.getFirstDayOfWeek(currentDate);
			startTime = DateTimeUtil.dateToString(date);
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
		if (AssConstants.CYCLE_YEAR.equals(cycle)) {
			int year = Integer.parseInt(currentDateTime.substring(0, 4));
			Date date = DateTimeUtil.getLastDayOfYear(year);
			endTime = DateTimeUtil.dateToString(date);
		} else if (AssConstants.CYCLE_QUARTER.equals(cycle)) {
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
			Date date = DateTimeUtil.getLastDayOfQuarter(year, quarter);
			endTime = DateTimeUtil.dateToString(date);
		} else if (AssConstants.CYCLE_MONTH.equals(cycle)) {
			int year = Integer.parseInt(currentDateTime.substring(0, 4));
			int month = Integer.parseInt(currentDateTime.substring(5, 7));
			Date date = DateTimeUtil.getLastDayOfMonth(year, month);
			endTime = DateTimeUtil.dateToString(date);
		} else if (AssConstants.CYCLE_WEEK.equals(cycle)) {
			Date currentDate = DateTimeUtil.stringToDate(currentDateTime);
			Date date = DateTimeUtil.getLastDayOfWeek(currentDate);
			endTime = DateTimeUtil.dateToString(date);
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
		if (AssConstants.ORG_SUBROLE.equals(orgType)) {
			ITawSystemSubRoleManager subRoleMgr = (ITawSystemSubRoleManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemSubRoleManager");
			return subRoleMgr.getTawSystemSubRole(orgId).getSubRoleName();

		}
		// 若为用户则显示用户名称
		else if (AssConstants.ORG_USER.equals(orgType)) {
			ITawSystemUserManager userMgr = (ITawSystemUserManager) ApplicationContextHolder
					.getInstance().getBean("itawSystemUserManager");
			return userMgr.id2Name(orgId);
		}
		// 若为部门则显示部门名称
		else if (AssConstants.ORG_DEPT.equals(orgType)) {
			ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemDeptManager");
			return deptMgr.id2Name(orgId);
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
	 * 根据值域范围和当前值判断是否在范围内
	 * 
	 * @param value
	 * @param boundStr
	 * @param border
	 * @return
	 */
	public static boolean isInBound(String value, String boundStr,String border) {
		String[] bounds = boundStr.split(",");
		if(("10".equals(border)&&
				Float.parseFloat(value)>=Float.parseFloat(bounds[0])
				&&Float.parseFloat(value)<Float.parseFloat(bounds[1]))
				||("01".equals(border)&&
						Float.parseFloat(value)>Float.parseFloat(bounds[0])
						&&Float.parseFloat(value)<=Float.parseFloat(bounds[1]))){
			return true;
		}else{
			return false;
		}
		
	}

	/**
	 * 保留两位小数
	 * 
	 * @param value
	 * @return
	 */
	public static String floatFormat(float value) {	
		DecimalFormat myFormat = new DecimalFormat( "0.00 "); 
		String strFloat = myFormat.format(value);  
		return strFloat;
	}
	/**
	 * 保留两位小数
	 * 
	 * @param value
	 * @return
	 */
	public static String changeCharater(String text) {
		if (text.indexOf("&amp") < 0)
			text =text.replaceAll("&", "&amp;");
		text = text.replaceAll("\"", "&quot;");
		text = text.replaceAll("\"", "&quot;");
		text = text.replaceAll("'", "&apos;");
		text = text.replaceAll("<", "&lt;");
		text = text.replaceAll(">", "&gt;");

		return text;
	}
	
	public static void main(String[] args) {
		System.out.println("本年第一天："
				+ getStartTimeByCycle(AssConstants.CYCLE_YEAR, DateTimeUtil
						.getCurrentDateTime(AssConstants.DATE_FORMAT)));
		System.out.println("本季度第一天："
				+ getStartTimeByCycle(AssConstants.CYCLE_QUARTER, DateTimeUtil
						.getCurrentDateTime(AssConstants.DATE_FORMAT)));
		System.out.println("本月第一天："
				+ getStartTimeByCycle(AssConstants.CYCLE_MONTH, DateTimeUtil
						.getCurrentDateTime(AssConstants.DATE_FORMAT)));
		System.out.println("本周第一天："
				+ getStartTimeByCycle(AssConstants.CYCLE_WEEK, DateTimeUtil
						.getCurrentDateTime(AssConstants.DATE_FORMAT)));
		System.out.println("本年最后一天："
				+ getEndTimeByCycle(AssConstants.CYCLE_YEAR, DateTimeUtil
						.getCurrentDateTime(AssConstants.DATE_FORMAT)));
		System.out.println("本季度最后一天："
				+ getEndTimeByCycle(AssConstants.CYCLE_QUARTER, DateTimeUtil
						.getCurrentDateTime(AssConstants.DATE_FORMAT)));
		System.out.println("本月最后一天："
				+ getEndTimeByCycle(AssConstants.CYCLE_MONTH, DateTimeUtil
						.getCurrentDateTime(AssConstants.DATE_FORMAT)));
		System.out.println("本周最后一天："
				+ getEndTimeByCycle(AssConstants.CYCLE_WEEK, DateTimeUtil
						.getCurrentDateTime(AssConstants.DATE_FORMAT)));
		System.out.println(DateTimeUtil.getStartTimeByCycleAfter("month"));
		System.out.println(DateTimeUtil.getEndTimeByCycleAfter("month"));
	}
}
