package com.boco.eoms.commons.system.user.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.commons.system.user.service.ITawSystemUserMappDept;

public class TawSystemUser4AInterFace {

	/**
	 * 根据从帐号id查询从账号的信息接口
	 * 
	 * @param accId
	 *            从账号id
	 * @return String 从帐号信息xml字符串
	 */
	public String getSingleSubAccountInfo(String params) {
		BocoLog.info("", params);
		ITawSystemUserManager mgr = (ITawSystemUserManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemUserManagerFlush");
		System.out.println("4A's params is =====" + params);
		List userIdList = User4AUtil.getUserIds(params);
		String userId = "";
		TawSystemUser tawSystemUser = null;
		String returnString = "";
		if (userIdList != null && userIdList.size() > 0) {
			userId = (String) userIdList.get(0);
			ITawSystemUserMappDept iTawSystemUserMappDept = (ITawSystemUserMappDept) ApplicationContextHolder
					.getInstance().getBean("iTawSystemUserMappDept");
			tawSystemUser = mgr.getSingleSubAccountInfo(userId);
			if (tawSystemUser.getId() != null
					&& !tawSystemUser.getId().equals("")) {
				List userList1 = new ArrayList();
				userList1.add(tawSystemUser);
				localTo4ADeptId(userList1);
			}
		}
		List list = new ArrayList();
		list.add(tawSystemUser);
		returnString = User4AUtil.xmlString(list);
		System.out.println("returnString========" + returnString);
		return returnString;
	}

	/**
	 * 得到所有从账号的数量接口
	 * 
	 * @param params
	 * 
	 * @return String 从账号的数量
	 */
	public String getAllSubAccount(String params) {
		System.out.println("4A's params is =====" + params);
		String returnString = "";
		ITawSystemUserManager mgr = (ITawSystemUserManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemUserManagerFlush");
		returnString = mgr.getAllSubAccount(params);
		System.out.println("returnString========" + returnString);
		return returnString;
	}

	/**
	 * 全部从帐号信息查询接口
	 * 
	 * @param params
	 * @return 从帐号信息列表 xml字符串
	 */
	public String getAllSubAccountInfo(String params) {
		ITawSystemUserManager mgr = (ITawSystemUserManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemUserManagerFlush");
		String returnString = "";

		List list = mgr.getAllSubAccountInfo(params);
		localTo4ADeptId(list);
		System.out.println("all countsSize is =======" + list.size());
		returnString = User4AUtil.xmlString(list);
		System.out.println("returnString========" + returnString);
		return returnString;
	}

	/**
	 * 
	 * 将用户本地部门ID转换为4A部门ID
	 * 
	 * @param list
	 */
	public void localTo4ADeptId(List list) {
		// 部门id转换服务
		ITawSystemUserMappDept iTawSystemUserMappDept = (ITawSystemUserMappDept) ApplicationContextHolder
				.getInstance().getBean("iTawSystemUserMappDept");
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TawSystemUser user = (TawSystemUser) iterator.next();
			if (user.getId() != null && !"".equals(user.getId())) {
				String localDeptId = user.getDeptid();
				String deptId = "";
				if (localDeptId != null && !localDeptId.equals("")) {
					deptId = iTawSystemUserMappDept.getDeptId(localDeptId);
					user.setDeptid(deptId);
				}
			}
		}
	}

	/**
	 * 全部从帐号信息查询接口(有分页)
	 * 
	 * @param params
	 *            参数包括分页大小和页数
	 * 
	 * @return 从帐号信息列表 xml字符串
	 */
	public String getAllSubAccountInfoPages(String params) {
		ITawSystemUserManager mgr = (ITawSystemUserManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemUserManagerFlush");
		Map map = User4AUtil.getPageParameters(params);
		Integer pageSize = null;
		Integer pageNum = null;
		List list = new ArrayList();
		String returnString = "";
		if (map != null) {
			pageSize = Integer.valueOf((String) map.get("pageSize"));
			pageNum = Integer.valueOf((String) map.get("pageNum"));
			list = mgr.getAllSubAccountInfoPages(pageSize, pageNum);
			localTo4ADeptId(list);
			returnString = User4AUtil.xmlString(list);
		}
		System.out.println("returnString========" + returnString);
		return returnString;
	}

	/**
	 * 从帐号添加接口(支持批量)
	 * 
	 * @param params
	 *            xml字符串，从帐号信息列表
	 * 
	 * @return 返回一个xml格式字符串
	 */
	public String saveSubAccount(String params) {
		ITawSystemUserManager mgr = (ITawSystemUserManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemUserManagerFlush");
		String returnString = "";
		String successString = "";
		String failString = "";
		String failCodeString = "<result returncode=\"1301\">";
		String endElementsString = "</result>";
		String xmlStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><results><result returncode=\"1300\">";
		String xmlEnd = "</results>";
		ITawSystemUserMappDept iTawSystemUserMappDept = (ITawSystemUserMappDept) ApplicationContextHolder
				.getInstance().getBean("iTawSystemUserMappDept");
		List list = new ArrayList();
		params = User4AUtil.getString(params);
		list = User4AUtil.getXmlString(params);
		TawSystemUser tawSystemUser = null;
		TawSystemUser tawSystemUserTemp = null;
		// 缓存
		/**
		 * 作业计划中的缓存清理，合作伙伴中去掉
		 * modify by 陈元蜀 2012-09-04 begin	 
		TawWorkplanCacheBean tawWorkplanCacheBean = (TawWorkplanCacheBean) ApplicationContextHolder
				.getInstance().getBean("TawWorkplanCacheBean");
		Map workplanMap = tawWorkplanCacheBean.getWorkplanUser();
		Map userMap = (Map) workplanMap.get("userMap");
		*modify by 陈元蜀 2012-09-04 end	
		*/
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				tawSystemUser = (TawSystemUser) list.get(i);
				String deptId = iTawSystemUserMappDept
						.getLocalDeptId(tawSystemUser.getDeptid());
				tawSystemUser.setDeptid(deptId);
				tawSystemUser.setDeleted("0");
				tawSystemUser.setSavetime(new Date());
				tawSystemUser.setEnabled(true);
				try {
					String userId = tawSystemUser.getUserid();
					tawSystemUserTemp = mgr.getTawSystemUserByuserid(userId);
					if (tawSystemUserTemp.getId() != null) {
						returnString = "1";
					} else {
						mgr.saveTawSystemUser(tawSystemUser);
						returnString = "0";
					}
				} catch (Exception e) {
					returnString = "1";
				}
				if (returnString.equals("0")) {
					successString += "<accId>" + tawSystemUser.getUserid()
							+ "</accId>";
//					userMap.put(tawSystemUser.getUserid(), tawSystemUser
//							.getUsername());modify by 陈元蜀 2012-09-04
//					System.out.println("abcdefghijklmnopqrstuvwxyz=="
//							+ userMap.containsKey(tawSystemUser.getUserid()));modify by 陈元蜀 2012-09-04
				} else if (returnString.equals("1")) {
					failString += "<accId>" + tawSystemUser.getUserid()
							+ "</accId>";
				}
			}
		}
		String temp = xmlStart + successString + endElementsString
				+ failCodeString + failString + endElementsString + xmlEnd;
		System.out.println("returnString==========" + temp);
		return temp;
	}

	/**
	 * 从帐号修改接口(支持批量)
	 * 
	 * @param params
	 *            xml字符串，从帐号信息列表
	 * 
	 * @return 返回一个xml格式字符串
	 */
	public String updateSubAccount(String params) {
		String returnString = "";
		String successString = "";
		String failString = "";
		String failCodeString = "<result returncode=\"1303\">";
		String endElementsString = "</result>";
		String xmlStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><results><result returncode=\"1302\">";
		String xmlEnd = "</results>";
		params = User4AUtil.getString(params);
		List list = User4AUtil.getXmlString(params);
		TawSystemUser tawSystemUserFrom = null;
		TawSystemUser tawSystemUserTo = null;
		ITawSystemUserManager iTawSystemUserManager = (ITawSystemUserManager) ApplicationContextHolder
				.getInstance().getBean("itawSystemUserManager");
		ITawSystemUserMappDept iTawSystemUserMappDept = (ITawSystemUserMappDept) ApplicationContextHolder
				.getInstance().getBean("iTawSystemUserMappDept");
		ITawSystemUserManager mgr = (ITawSystemUserManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemUserManagerFlush");
		/*
		 * 合作伙伴中没有作业计划，所有屏蔽
		 * modify by 陈元蜀 2012-09-04 begin
		TawWorkplanCacheBean tawWorkplanCacheBean = (TawWorkplanCacheBean) ApplicationContextHolder
				.getInstance().getBean("TawWorkplanCacheBean");
		Map workplanMap = tawWorkplanCacheBean.getWorkplanUser();
		Map userMap = (Map) workplanMap.get("userMap");
		* modify by 陈元蜀 2012-09-04 begin
		 */
		TawSystemUser tawSystemUserTemp = null;
		String deptid = "";
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				tawSystemUserFrom = ((TawSystemUser) list.get(i));
				String userId = tawSystemUserFrom.getUserid();
				tawSystemUserTo = iTawSystemUserManager
						.getTawSystemUserByuserid(userId);
				tawSystemUserTo.setSex(tawSystemUserFrom.getSex());
				deptid = iTawSystemUserMappDept
						.getLocalDeptId(tawSystemUserFrom.getDeptid());
				tawSystemUserTo.setDeptid(deptid);
				tawSystemUserTo.setEmail(tawSystemUserFrom.getEmail());
				tawSystemUserTo.setFax(tawSystemUserFrom.getFax());
				tawSystemUserTo.setMobile(tawSystemUserFrom.getMobile());
				tawSystemUserTo.setPhone(tawSystemUserFrom.getPhone());
				tawSystemUserTo.setEmployeeNumber(tawSystemUserFrom
						.getEmployeeNumber());
				tawSystemUserTo.setPassword(tawSystemUserFrom.getPassword());
				tawSystemUserTo.setUpdatetime(StaticMethod.getLocalString());
				tawSystemUserTo.setUsername(tawSystemUserFrom.getUsername());
				try {
					mgr.saveTawSystemUser(tawSystemUserTo);
					returnString = "0";
				} catch (Exception e) {
					e.printStackTrace();
					returnString = "1";
				}
				if (returnString.equals("0")) {
//					userMap.remove(tawSystemUserTo.getUserid());modify by 陈元蜀 2012-09-04
//					userMap.put(tawSystemUserTo.getUserid(), tawSystemUserTo
//							.getUsername());modify by 陈元蜀 2012-09-04
					successString += "<accId>" + tawSystemUserTo.getUserid()
							+ "</accId>";
				} else if (returnString.equals("1")) {
					failString += "<accId>" + tawSystemUserTo.getUserid()
							+ "</accId>";
				}
			}
		}
		String temp = xmlStart + successString + endElementsString
				+ failCodeString + failString + endElementsString + xmlEnd;
		System.out.println("returnString==========" + temp);
		return temp;
	}

	/**
	 * 从帐号删除接口(支持批量)
	 * 
	 * @param accIds
	 * @return
	 */
	public String romoveSubaccount(String params) {
		params = User4AUtil.getString(params);
		List usersList = User4AUtil.getUserIds(params);
		ITawSystemUserManager mgr = (ITawSystemUserManager) ApplicationContextHolder
		.getInstance().getBean("ItawSystemUserManagerFlush");
		/**
		 * 合作伙伴中没有作业计划，所以屏蔽
		 * modify by 陈元蜀 2012-09-04 begin	
		TawWorkplanCacheBean tawWorkplanCacheBean = (TawWorkplanCacheBean) ApplicationContextHolder
				.getInstance().getBean("TawWorkplanCacheBean");
		Map workplanMap = tawWorkplanCacheBean.getWorkplanUser();
		Map userMap = (Map) workplanMap.get("userMap");
		 * modify by 陈元蜀 2012-09-04 end
		 */
		String returnString = "";
		String successString = "";
		String failString = "";
		String failCodeString = "<result returncode=\"1305\">";
		String endElementsString = "</result>";
		String xmlStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><delresults><result returncode=\"1304\">";
		String xmlEnd = "</delresults>";
		String result = "";
		String userId = "";
		if (usersList != null) {
			for (int i = 0; i < usersList.size(); i++) {
				userId = (String) usersList.get(i);
				try {
					mgr
							.removeTawSystemUser(mgr.getUserByuserid(userId)
									.getId());
					result = "0";
				} catch (Exception e) {
					e.printStackTrace();
					result = "1";
				}
				if (result.equals("0")) {
//					userMap.remove(userId); modify by 陈元蜀 2012-09-04
					successString += "<accId>" + userId + "</accId>";
				} else if (result.equals("1")) {
					failString += "<accId>" + userId + "</accId>";
				}
			}
		}
		String temp = xmlStart + successString + endElementsString
				+ failCodeString + failString + endElementsString + xmlEnd;
		System.out.println("returnString==========" + temp);
		return temp;
	}
}
