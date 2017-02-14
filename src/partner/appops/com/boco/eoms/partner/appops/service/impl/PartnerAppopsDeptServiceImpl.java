package com.boco.eoms.partner.appops.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import utils.PartnerPrivUtils;
import base.dao.Nop3GenericDaoImpl;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dept.service.bo.TawSystemDeptBo;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.partner.appops.dao.PartnerAppopsDeptDao;
import com.boco.eoms.partner.appops.model.IPnrPartnerAppOpsDept;
import com.boco.eoms.partner.appops.model.IPnrPartnerAppOpsUser;
import com.boco.eoms.partner.appops.service.PartnerAppopsDeptService;
import com.boco.eoms.partner.appops.service.PartnerAppopsUserService;
import com.boco.eoms.partner.baseinfo.mgr.IPnrQualificationMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.baseinfo.model.PnrQualification;
import com.boco.eoms.partner.baseinfo.util.QualificationUtils;
import com.boco.eoms.partner.baseinfo.util.RoleIdList;
import com.boco.eoms.partner.interfaces.services.partnerservice.PartnerService;
import com.boco.eoms.partner.personnel.util.ExcelUtil;
import com.boco.eoms.partner.personnel.util.MyUtil;
import com.boco.eoms.partner.process.service.PnrProcessService;
import com.boco.eoms.partner.process.util.XLSModel;

/**
 * <p>
 * Title:����
 * </p>
 * <p>
 * Description:����
 * </p>
 * <p>
 * Mon Feb 09 10:57:12 CST 2009
 * </p>
 * 
 * @author liujinlong
 * @version 3.5
 * 
 * @author lihaolin
 * @version 3.6 2012-9-11
 */
public class PartnerAppopsDeptServiceImpl implements PartnerAppopsDeptService, PnrProcessService {
	private PartnerAppopsDeptDao partnerAppopsDeptDao;

	public PartnerAppopsDeptDao getPartnerAppopsDeptDao() {
		return partnerAppopsDeptDao;
	}

	public void setPartnerAppopsDeptDao(PartnerAppopsDeptDao partnerAppopsDeptDao) {
		this.partnerAppopsDeptDao = partnerAppopsDeptDao;
	}

	public List getPartnerDepts() {
		return partnerAppopsDeptDao.getPartnerDepts();
	}

	public IPnrPartnerAppOpsDept getPartnerDept(final String id) {
		return partnerAppopsDeptDao.getPartnerDept(id);
	}

	public Boolean getPartnerDeptByName(final String name) {

		return partnerAppopsDeptDao.getPartnerDeptByName(name);
	}

	public synchronized void savePartnerDept(IPnrPartnerAppOpsDept partnerDept) {

		String deptId = partnerDept.getId();
		partnerAppopsDeptDao.savePartnerDept(partnerDept);
		/*
		 * PartnerService partnerService = new PartnerService(); //
		 * 存在partnerId则为更新 if (deptId == null || "".equals(deptId)) {
		 * partnerService.addPartner(partnerDept);
		 * 
		 * } else { // 不存在partnerId则为添加
		 * partnerService.updatePartner(partnerDept); }
		 */
	}

	public void removePartnerDept(final String id) {
		partnerAppopsDeptDao.removePartnerDept(id);

		new PartnerService().deletePartner(id);

	}

	public Map getPartnerDepts(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return partnerAppopsDeptDao.getPartnerDepts(curPage, pageSize, whereStr);
	}

	/**
	 * 由字段treeId得到partnerDept
	 */
	public IPnrPartnerAppOpsDept getPartnerDeptByTreeId(final String treeId) {
		return partnerAppopsDeptDao.getPartnerDeptByTreeId(treeId);
	}

	/**
	 * 由字段treeNodeId得到partnerDept
	 */
	public IPnrPartnerAppOpsDept getPartnerDeptByTreeNodeId(final String treeNodeId) {
		return partnerAppopsDeptDao.getPartnerDeptByTreeNodeId(treeNodeId);
	}

	/**
	 * id:EOMS-liujinlong-20090924143614 开发人邮箱：liujinlong@boco.com.cn
	 * 时间：2009-09-24 开发目的：将删除改为设置删除字段置deleted为1
	 * 参数parentNodeId：合作伙伴父节点nodeID，也可能是合作伙伴的nodeId
	 */
	public void removePartnerDeptByNodeId(final String parentNodeId) {
		partnerAppopsDeptDao.removePartnerDeptByNodeId(parentNodeId);
	}

	public List getPartnerDepts(final String where) {
		return partnerAppopsDeptDao.getPartnerDepts(where);
	}

	public List<IPnrPartnerAppOpsDept> getCompanyByProvience(String Id,
			String firstdeptsimble, String user) {
		// TODO Auto-generated method stub
		return partnerAppopsDeptDao.getCompanyByProvience(Id, firstdeptsimble, user);
	}

	public void remove(String id) {
		partnerAppopsDeptDao.removeObject(IPnrPartnerAppOpsDept.class, id);
	}

	/**
	 * 
	 * @Title: getPartnerDeptsByAreaidOrDeptid
	 * @Description: 根据代维管理人员的deptid或者移动管理人员的areaid来查找所管辖的代维公司；
	 * @param
	 * @Time:Dec 13, 2012-7:12:22 PM
	 * @Author:fengguangping
	 * @return Map 返回类型
	 * @throws
	 */
	@Override
	public Map getPartnerDeptsByAreaidOrDeptid(final Integer curPage,
			final Integer pageSize, String areaid, String deptid,
			IPnrPartnerAppOpsDept dept, String deptLevel, String interfaceHeadId,
			int isPartner) {
		String whereStr = " where 1=1";
		if (isPartner != -1) {
			whereStr += " and isPartner="+isPartner;
		}
		if (!"".equals(areaid)) {// 移动人员
			whereStr += " and areaId  like '" + areaid + "%'";
		}
		if (!"".equals(deptid)) {// 代维人员
			whereStr += " and deptMagId like '" + deptid + "%'";
		}
		if (!"".equals(deptLevel)) {
			whereStr += " and deptLevel = " + deptLevel;
		}
		if (dept.getManager() != null && !dept.getManager().equals("")) {
			whereStr += " and manager like '%" + dept.getManager() + "%'";
		}
		if (dept.getContactor() != null && !dept.getContactor().equals("")) {
			whereStr += " and contactor like '%" + dept.getContactor() + "%'";
		}
		if (dept.getPhone() != null && !dept.getPhone().equals("")) {
			whereStr += " and phone like '%" + dept.getPhone() + "%'";
		}
		if (dept.getName() != null && !dept.getName().equals("")) {
			whereStr += " and name like '%" + dept.getName() + "%'";
		}
		if (dept.getOrganizationNo() != null
				&& !dept.getOrganizationNo().equals("")) {
			whereStr += " and organizationNo like '%"
					+ dept.getOrganizationNo() + "%'";
		}
		if (interfaceHeadId != null && !"".equals(interfaceHeadId)) {// 移动人员
			whereStr += "and interfaceHeadId='" + interfaceHeadId + "'";
		}
//		System.out.println("werwerwer"+whereStr);
		return partnerAppopsDeptDao.getPartnerDeptsByAreaidOrDeptis(curPage,
				pageSize, whereStr);
	}

	public Map getPartnerDeptsByDeptLevel(final Integer curPage,
			final Integer pageSize, IPnrPartnerAppOpsDept dept, String deptid,
			String hasRight, String deptLevel) {
		String whereStr = " where 1=1";
		if (dept.getManager() != null && !dept.getManager().equals("")) {
			whereStr += " and manager like '%" + dept.getManager() + "%'";
		}
		if (dept.getContactor() != null && !dept.getContactor().equals("")) {
			whereStr += " and contactor like '%" + dept.getContactor() + "%'";
		}
		if (dept.getDeptLevel() != null && !dept.getDeptLevel().equals("")) {
			whereStr += " and deptLevel = " + deptLevel;
		}
		if (dept.getPhone() != null && !dept.getPhone().equals("")) {
			whereStr += " and phone like '%" + dept.getPhone() + "%'";
		}
		if (dept.getName() != null && !dept.getName().equals("")) {
			whereStr += " and name like '%" + dept.getName() + "%'";
		}
		if (dept.getOrganizationNo() != null
				&& !dept.getOrganizationNo().equals("")) {
			whereStr += " and organizationNo like '%"
					+ dept.getOrganizationNo() + "%'";
		}
		if (hasRight.equals("0")) {
			whereStr += " and deptMagId like '" + deptid + "%'";
		} else {
			whereStr += " and id=interfaceHeadId "; // 只查询父代维公司
		}
		return partnerAppopsDeptDao.getPartnerDeptsByDeptLevel(curPage, pageSize,
				whereStr);
	}

	public Map getPartnerDepts(final Integer curPage, final Integer pageSize,
			IPnrPartnerAppOpsDept dept, String deptid, String hasRight) {
		String whereStr = " where 1=1";

		if (dept.getManager() != null && !dept.getManager().equals("")) {
			whereStr += " and manager like '%" + dept.getManager() + "%'";
		}
		if (dept.getContactor()!= null && !dept.getContactor().equals("")) {
			whereStr += " and contactor like '%" + dept.getContactor() + "%'";
		}
		if (dept.getPhone() != null && !dept.getPhone().equals("")) {
			whereStr += " and phone like '%" + dept.getPhone() + "%'";
		}
		if (dept.getName() != null && !dept.getName().equals("")) {
			whereStr += " and name like '%" + dept.getName() + "%'";
		}
		if (hasRight.equals("0")) {
			whereStr += " and deptMagId like '" + deptid + "%'";
		} else {
			whereStr += " and id=interfaceHeadId "; // 只查询父代维公司
		}
		return this.getPartnerDepts(curPage, pageSize, whereStr);
	}

	/**
	 * @author Steve Retrive all records according to hql, no paging.
	 */
	public List<IPnrPartnerAppOpsDept> getPartnerDeptsByHql(String hql) {
		return partnerAppopsDeptDao.getPartnerDeptsByHql(hql);
	}

	public List<TawSystemArea> getAreas(final String parentareaid) {
		return partnerAppopsDeptDao.getAreas(parentareaid);
	}

	/**
	 * 得到下一级子部门的部门信息
	 * 
	 * @param pardeptid
	 * @param delid
	 * @return
	 */
	public List getNextLevecDepts(String pardeptid, String delid,
			String userdeptid, int isPartner) {
		List list = new ArrayList();
		list = partnerAppopsDeptDao.getNextLevecDepts(pardeptid, delid, userdeptid,
				isPartner);
		return list;
	}

	public List listProviderByRegionOrByCity(final String region,
			final String city) {
		return partnerAppopsDeptDao.listProviderByRegionOrByCity(region, city);
	}

	public boolean doDeleteXLSFileValidate(HSSFRow row) throws Exception {
		int colNum = 0;
		String[] dept = new String[3];
		try {
			// 组织编码
			dept = this.checkDetpCode((ExcelUtil.getString(row, colNum)));
		} catch (Exception e) {
			throw new RuntimeException("第" + (row.getRowNum() + 1) + "行,"
					+ (colNum + 1) + "列验证出错: " + e.getMessage());
		}
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) ApplicationContextHolder
				.getInstance().getBean("partnerUserMgr");
		String whereStr = " and partnerUser.deleted='0' and partnerUser.deptId like '"
				+ dept[2] + "%'";
		List<PartnerUser> partnerUserList = partnerUserMgr
				.getPartnerUsers(whereStr);
		String hasUserDeptNames = "";
		if (partnerUserList.size() > 0) {// 表示存在员工
		// for (int j = 0; j < dept.length; j++) {
		// if(!"".equals(hasUserDeptNames)){
		// hasUserDeptNames+=",";
		// }
		// hasUserDeptNames+=StaticMethod.null2String(partnerUserList.get(j).getDeptName());;
		// }
			throw new RuntimeException("第" + (row.getRowNum() + 1) + "行,"
					+ (colNum + 1) + "列验证出错:该部门或者子部门还存在代维人员不能删除,请先删除代维人员后再申请!");
		}
		return true;
	}

	public boolean doSaveXLSFileValidate(HSSFRow row) throws Exception {
		int colNum = 0;
		try {
			// 上级组织
			String upDept[] = this.checkDetpCode((ExcelUtil.getString(row,
					colNum++)));
			if (!upDept[1].equals(ExcelUtil.getString(row, colNum++).trim()))
				throw new RuntimeException("上级部门名错误");
			ExcelUtil
					.checkNotNullLength(ExcelUtil.getString(row, colNum++), 25);
			// 地市，县区
			this.checkArea(ExcelUtil.getString(row, colNum++),
					ExcelUtil.getString(row, colNum++), upDept[0]);
			// 是否为公司
			String isCompany = this.checkIsCompany(ExcelUtil.getString(row,
					colNum++));
			if ("yes".equals(isCompany)) {
				// 企业性质，法人，入围级别
				ExcelUtil.checkNotNullDIct(ExcelUtil.getString(row, colNum++));
				ExcelUtil.checkLength(ExcelUtil.getString(row, colNum++), 20);
				ExcelUtil.checkNotNullDIct(ExcelUtil.getString(row, colNum++));
				// 注册日期，货币类型，注册资金
				ExcelUtil.checkDate(ExcelUtil.getString(row, colNum++));
				ExcelUtil.checkDIct(ExcelUtil.getString(row, colNum++));
				ExcelUtil
						.checkNotNullNumber(ExcelUtil.getString(row, colNum++));
			} else
				colNum = colNum + 6;
			// 联系人，联系电话，传真，邮箱

			ExcelUtil
					.checkNotNullLength(ExcelUtil.getString(row, colNum++), 20);
			ExcelUtil.checkNotNullNumber(ExcelUtil.getString(row, colNum++));
			ExcelUtil.checkNumber(ExcelUtil.getString(row, colNum++));
			String email = ExcelUtil.getString(row, colNum++);
			if (!email
					.matches("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$"))
				throw new RuntimeException("邮箱格式错误");
			// 邮编，专业，地址，简介，备注
			ExcelUtil.checkNotNullNumber(ExcelUtil.getString(row, colNum++));
			String pro = ExcelUtil.checkNotNullLength(
					ExcelUtil.getString(row, colNum++), 500);
			String pros[] = pro.split(",");
			for (int i = 0; i < pros.length; i++) {
				ExcelUtil.checkDIct(pros[i]);
			}
			ExcelUtil.checkNotNullLength(ExcelUtil.getString(row, colNum++),
					200);
			ExcelUtil.checkNotNullLength(ExcelUtil.getString(row, colNum++),
					200);
			ExcelUtil.checkLength(ExcelUtil.getString(row, colNum++), 200);
		} catch (Exception e) {
			throw new RuntimeException("第" + (row.getRowNum() + 1) + "行,"
					+ (colNum++) + "列验证出错  : " + e.getMessage());
		}
		return true;
	}

	public boolean doUpdateXLSFileValidate(HSSFRow row) throws Exception {
		int colNum = 0;
		try {
			// 当前组织
			this.checkDetpCode(ExcelUtil.getString(row, colNum++));
			// 上级组织
			String upDept[] = this.checkDetpCode((ExcelUtil.getString(row,
					colNum++)));
			if (!upDept[1].equals(ExcelUtil.getString(row, colNum++).trim()))
				throw new RuntimeException("上级部门名错误");
			// ExcelUtil.checkNotNullLength(ExcelUtil.getString(row, colNum++),
			// 25);
			ExcelUtil.getString(row, colNum++);
			// 地市，县区
			String upArea = ExcelUtil.getString(row, colNum++);
			String area = ExcelUtil.getString(row, colNum++);
			if (!MyUtil.isEmpty(upArea) && !MyUtil.isEmpty(area))
				this.checkArea(upArea, area, upDept[0]);
			// 是否为公司
			String isC = ExcelUtil.getString(row, colNum++);
			String isCompany = "no";
			if (!MyUtil.isEmpty(isC))
				isCompany = this.checkIsCompany(isC);
			if ("yes".equals(isCompany)) {
				// 企业性质，法人，入围级别
				ExcelUtil.checkDIct(ExcelUtil.getString(row, colNum++));
				ExcelUtil.checkLength(ExcelUtil.getString(row, colNum++), 20);
				ExcelUtil.checkDIct(ExcelUtil.getString(row, colNum++));
				// 注册日期，货币类型，注册资金
				ExcelUtil.checkDate(ExcelUtil.getString(row, colNum++));
				ExcelUtil.checkDIct(ExcelUtil.getString(row, colNum++));
				ExcelUtil
						.checkNotNullNumber(ExcelUtil.getString(row, colNum++));
			} else
				colNum = colNum + 6;
			// 联系人，联系电话，传真，邮箱
			ExcelUtil.checkLength(ExcelUtil.getString(row, colNum++), 20);
			ExcelUtil.checkNumber(ExcelUtil.getString(row, colNum++));
			ExcelUtil.checkNumber(ExcelUtil.getString(row, colNum++));
			String email = ExcelUtil.getString(row, colNum++);
			if (!"".equals(email)
					&& !email
							.matches("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$"))
				throw new RuntimeException("邮箱格式错误");
			// 邮编，专业，地址，简介，备注
			ExcelUtil.checkNumber(ExcelUtil.getString(row, colNum++));
			String pro = ExcelUtil.checkLength(
					ExcelUtil.getString(row, colNum++), 200);
			if (!"".equals(pro)) {
				String pros[] = pro.split(",");
				for (int i = 0; i < pros.length; i++) {
					ExcelUtil.checkDIct(pros[i]);
				}
			}
			ExcelUtil.checkLength(ExcelUtil.getString(row, colNum++), 200);
			ExcelUtil.checkLength(ExcelUtil.getString(row, colNum++), 200);
			ExcelUtil.checkLength(ExcelUtil.getString(row, colNum++), 200);
			ExcelUtil.checkLength(ExcelUtil.getString(row, colNum++), 200);
		} catch (Exception e) {
			throw new RuntimeException("第" + (row.getRowNum() + 1) + "行,"
					+ (colNum++) + "列验证出错  : " + e.getMessage());
		}
		return true;
	}

	public boolean doSaveXLSFileData(HSSFRow row, HttpServletRequest request)
			throws Exception {
		IPnrPartnerAppOpsDept dept = new IPnrPartnerAppOpsDept();
		int colNum = 2;
		// 组织名称
		dept.setName(ExcelUtil.getString(row, colNum++));
		String[] areas = this
				.checkArea(ExcelUtil.getString(row, colNum++),
						ExcelUtil.getString(row, colNum++),
						ExcelUtil.getString(row, 0));
		// 地市,县区
		dept.setAreaId(areas[0]);
		dept.setAreaName(areas[1]);
		// 是否为公司
		String isCompany = this.checkIsCompany(ExcelUtil.getString(row,
				colNum++));
		dept.setIfCompany(isCompany);
		if ("yes".equals(isCompany)) {
			// 企业性质，法人，入围级别
			dept.setType(ExcelUtil.getString(row, colNum++).split("_")[1]);
			dept.setManagercop(ExcelUtil.getString(row, colNum++));
			dept.setSelectedLevel(ExcelUtil.getString(row, colNum++).split("_")[1]);
			// 注册日期，货币类型，注册资金
			dept.setRegisterTime(ExcelUtil.getString(row, colNum++));
			dept.setKindCurrencies(ExcelUtil.getString(row, colNum++)
					.split("_")[1]);
			dept.setFund(ExcelUtil.getString(row, colNum++));
		} else
			colNum = colNum + 6;
		// 联系人，联系电话，传真，邮箱
		dept.setContactor(ExcelUtil.getString(row, colNum++));
		dept.setPhone(ExcelUtil.getString(row, colNum++));
		dept.setFax(ExcelUtil.getString(row, colNum++));
		dept.setEmail(ExcelUtil.getString(row, colNum++));
		// 邮编，专业，地址，简介，备注
		dept.setZip(ExcelUtil.getString(row, colNum++));
		// 代维专业 --start
		String pro = ExcelUtil.checkNotNullLength(
				ExcelUtil.getString(row, colNum++), 500);
		String pros[] = pro.split(",");
		String dbProsStr = "";
		for (int i = 0; i < pros.length; i++) {
			dbProsStr += ExcelUtil.checkDIct(pros[i]) + ",";
		}
		dbProsStr = dbProsStr.substring(0, dbProsStr.length() - 1);
		dept.setBigType(dbProsStr);
		// 代维专业 -- end
		dept.setAddress(ExcelUtil.getString(row, colNum++));
		dept.setCompanySynopses(ExcelUtil.getString(row, colNum++));
		dept.setRemark(ExcelUtil.getString(row, colNum++));

		// 创建时间，公司级别，组织编码
		String upOrgCode = ExcelUtil.getString(row, 0);
		dept.setCreateTime(new Date());
		dept.setDeptLevel(this.getDeptLevelFromOgrCode(upOrgCode));
		dept.setOrganizationNo(this.getOrgCode(upOrgCode));
		String interfaceHeadId = this.getParentId(upOrgCode);
		if (interfaceHeadId == null) {
			this.savePartnerDept(dept);
			interfaceHeadId = dept.getId();
		}
		dept.setInterfaceHeadId(interfaceHeadId);
		dept.setDeleted("0");
		boolean flag = false;// 是否是省代维公司标志;
		dept.setDeptMagId(this.saveTawSystemDept(request, flag, dept,
				dept.getName()));
		dept = updateStatisticInfo(dept);
		this.savePartnerDept(dept);
		return true;

	}

	public boolean doUpdateXLSFileData(HSSFRow row, HttpServletRequest request)
			throws Exception {
		String hql = "deleted='0' and organizationNo = '"
				+ ExcelUtil.getString(row, 0) + "'";
		IPnrPartnerAppOpsDept dept = this.partnerAppopsDeptDao.getPartnerDeptsByHql(hql).get(0);
		ExcelUtil.checkLength(ExcelUtil.getString(row, 3), 25);
		if (!MyUtil.isEmpty(ExcelUtil.getString(row, 4))
				&& !MyUtil.isEmpty(ExcelUtil.getString(row, 5))) {
			String[] areas = this.checkArea(ExcelUtil.getString(row, 4),
					ExcelUtil.getString(row, 5), ExcelUtil.getString(row, 1));
			// 地市,县区
			dept.setAreaId(areas[0]);
			dept.setAreaName(areas[1]);
		}
		// 是否为公司
		String isC = ExcelUtil.getString(row, 6);
		String isCompany = "no";
		if (!MyUtil.isEmpty(isC))
			isCompany = this.checkIsCompany(isC);
		dept.setIfCompany(isCompany);
		if ("yes".equals(isCompany)) {
			// 企业性质，法人，入围级别
			if (!MyUtil.isEmpty(ExcelUtil.getString(row, 7)))
				dept.setType(ExcelUtil.getString(row, 7).split("_")[1]);
			if (!MyUtil.isEmpty(ExcelUtil.getString(row, 8)))
				dept.setManagercop(ExcelUtil.getString(row, 8));
			if (!MyUtil.isEmpty(ExcelUtil.getString(row, 9)))
				dept.setSelectedLevel(ExcelUtil.getString(row, 9).split("_")[1]);
			// 注册日期，货币类型，注册资金
			if (!MyUtil.isEmpty(ExcelUtil.getString(row, 10)))
				dept.setRegisterTime(ExcelUtil.getString(row, 10));
			if (!MyUtil.isEmpty(ExcelUtil.getString(row, 11)))
				dept.setKindCurrencies(ExcelUtil.getString(row, 11).split("_")[1]);
			if (!MyUtil.isEmpty(ExcelUtil.getString(row, 12)))
				dept.setFund(ExcelUtil.getString(row, 12));
		}
		// 联系人，联系电话，传真，邮箱
		if (!MyUtil.isEmpty(ExcelUtil.getString(row, 13)))
			dept.setContactor(ExcelUtil.getString(row, 13));
		if (!MyUtil.isEmpty(ExcelUtil.getString(row, 14)))
			dept.setPhone(ExcelUtil.getString(row, 14));
		if (!MyUtil.isEmpty(ExcelUtil.getString(row, 15)))
			dept.setFax(ExcelUtil.getString(row, 15));
		if (!MyUtil.isEmpty(ExcelUtil.getString(row, 16)))
			dept.setEmail(ExcelUtil.getString(row, 16));
		// 邮编，专业，地址，简介，备注
		if (!MyUtil.isEmpty(ExcelUtil.getString(row, 17)))
			dept.setZip(ExcelUtil.getString(row, 17));
		// 代维专业 --start
		if (!MyUtil.isEmpty(ExcelUtil.getString(row, 18))) {
			String pro = ExcelUtil.checkNotNullLength(
					ExcelUtil.getString(row, 18), 500);
			String pros[] = pro.split(",");
			String dbProsStr = "";
			for (int i = 0; i < pros.length; i++) {
				dbProsStr += ExcelUtil.checkDIct(pros[i]) + ",";
			}
			dbProsStr = dbProsStr.substring(0, dbProsStr.length() - 1);
			dept.setBigType(dbProsStr);
		}
		// 代维专业 -- end
		if (!MyUtil.isEmpty(ExcelUtil.getString(row, 19)))
			dept.setAddress(ExcelUtil.getString(row, 19));
		if (!MyUtil.isEmpty(ExcelUtil.getString(row, 20)))
			dept.setCompanySynopses(ExcelUtil.getString(row, 20));
		if (!MyUtil.isEmpty(ExcelUtil.getString(row, 21)))
			dept.setRemark(ExcelUtil.getString(row, 21));

		// 创建时间，公司级别，组织编码
		if (!MyUtil.isEmpty(ExcelUtil.getString(row, 1))) {
			String upOrgCode = ExcelUtil.getString(row, 1);
			dept.setDeptLevel(this.getDeptLevelFromOgrCode(upOrgCode));
			dept.setOrganizationNo(this.getOrgCode(upOrgCode));
		}
		dept.setInterfaceHeadId(this.getParentId(ExcelUtil.getString(row, 1)));
		boolean flag = false;
		dept.setDeptMagId(this.saveTawSystemDept(request, flag, dept,
				dept.getName()));
		dept = updateStatisticInfo(dept);
		this.savePartnerDept(dept);
		return true;
	}
/*
	public boolean doDeleteXLSFileData(HSSFRow row, HttpServletRequest request)
			throws Exception {
		String cellValue = XLSCellValidateUtil.getCellStringValue(row, 0);
		String hql = "deleted='0' and  organizationNo = '" + cellValue + "'";
		List list = partnerAppopsDeptDao.getPartnerDeptsByHql(hql);
		if (list.size() != 1) {
			throw new RuntimeException("第" + (row.getRowNum() + 1)
					+ "行归档失败,该条记录已经不存在或者存在多条记录!");
		}
		IPnrPartnerAppOpsDept dept = (IPnrPartnerAppOpsDept) list.get(0);
		ITawSystemDeptManager flushmgr = (ITawSystemDeptManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemDeptManagerFlush");
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) ApplicationContextHolder
				.getInstance().getBean("partnerDeptMgr");
		List<IPnrPartnerAppOpsDept> list2 = partnerDeptMgr
				.getPartnerDeptsByHql(" partnerDept.deptMagId like '"
						+ dept.getDeptMagId()
						+ "%' and partnerDept.deleted='0' ");
		for (int i = 0; i < list2.size(); i++) {// 删除该部门及其子部门
			IPnrPartnerAppOpsDept dept2 = list2.get(i);
			String deptMagId = list2.get(i).getDeptMagId();
			TawSystemDept tawSystemDept = flushmgr.getDeptinfobydeptid(
					deptMagId, "0");
			if (tawSystemDept != null) {
				tawSystemDept.setDeleted("1");
				flushmgr.saveTawSystemDept(tawSystemDept);
			}
			dept2.setDeleted("1");
			this.savePartnerDept(dept2);
		}
		return true;
	}
*/
	public XLSModel getXLSModel() {
		return new XLSModel(6, 0, 20, 6, 0, 2, 6, 0, 21);
	}

	/**
	 * 根据上级组织编码 取得上级部门id
	 * 
	 * @return
	 */
	private String getParentId(String orgCode) {
		@SuppressWarnings("unchecked")
		List<PartnerDept> listDept = partnerAppopsDeptDao
				.find("from PartnerDept dp where dp.organizationNo = '"
						+ orgCode + "'");
		if (listDept.size() != 1)
			return null;
		else
			return listDept.get(0).getId();
	}

	/**
	 * 检验 部门编码是否规范和正确
	 * 
	 * @param code
	 * @return string[2] 组织编码，组织名称
	 */
	private String[] checkDetpCode(String code) {
		String strs[] = new String[3];
		if (!code.startsWith("DW-"))
			throw new RuntimeException("组织编码格式不正确，必须以DW开头");
		@SuppressWarnings("unchecked")
		List<PartnerDept> listDept = partnerAppopsDeptDao
				.find("from PartnerDept dp where dp.organizationNo = '" + code
						+ "' and dp.deleted='0' ");
		if (listDept.size() != 1)
			throw new RuntimeException("组织编码(" + code + ")不存在或存在多条记录");
		strs[0] = code;
		strs[1] = "".equals(StaticMethod.nullObject2String(listDept.get(0)
				.getName())) ? "" : listDept.get(0).getName().trim();// modify
																		// by
																		// lee
																		// 2013
																		// 5.8
		strs[2] = listDept.get(0).getDeptMagId();
		return strs;
	}

	/**
	 * 
	 * @param upAreaName
	 *            所属地市
	 * @param areaName
	 *            所属区县
	 * @param upOrgCode
	 *            上级组织编码
	 * @return string[2] 区域id，区域名称
	 */
	private String[] checkArea(String upAreaName, String areaName,
			String upOrgCode) {
		String strs[] = new String[2];

		// 不 含有上级组织
		if ("省下属".equals(upAreaName)) {
			throw new RuntimeException("所属地市不能为 省下属 ");
		}

		// 含有上级组织
		@SuppressWarnings("rawtypes")
		Nop3GenericDaoImpl nop3Dao = (Nop3GenericDaoImpl) ApplicationContextHolder
				.getInstance().getBean("nop3GenericDao");
		Session session = nop3Dao.getSessionFactory().openSession();

		// 取得上级区域 ID
		String upAreaSql = "select areaid from taw_system_area where areaname = '"
				+ upAreaName + "'";
		SQLQuery query = session.createSQLQuery(upAreaSql);
		String upAreaId;
		try {
			upAreaId = query.list().get(0).toString();
		} catch (Exception e) {
			throw new RuntimeException("区域不存在");
		}

		if ("".equals(areaName) && upOrgCode.split("-").length == 2) {
			strs[0] = upAreaId;
			strs[1] = upAreaName;
			return strs;
		}

		// 添加子公司
		// 检验上级区域是否有 代维组织 ,及对应组织编码是否正确
		String upCompSql = "select organizationno from pnr_dept where area_id = '"
				+ upAreaId + "' ";
		query = session.createSQLQuery(upCompSql);
		@SuppressWarnings("unchecked")
		List<String> list = (ArrayList<String>) query.list();
		if (list.size() == 0)
			throw new RuntimeException("上级区域不存在代维组织，请检查区域填写是否正确");
		boolean isRighteOrgCode = false;
		if (upOrgCode.split("-").length == 4) { // 添加代维小组
			for (String code : list) {
				if (upOrgCode.substring(0, 10).equals(code)) {
					isRighteOrgCode = true;
					break;
				}
			}
		} else {
			for (String code : list) {
				if (upOrgCode.equals(code)) {
					isRighteOrgCode = true;
					break;
				}
			}
		}
		if (!isRighteOrgCode)
			throw new RuntimeException("上级组织编码有误， " + upAreaName + " 不存在组织编码为 "
					+ upOrgCode + " 的组织");
		// 取得当前区域 ID
		String sql = " select areaid from taw_system_area  where areaname = '"
				+ areaName + "'  and  parentareaid = '" + upAreaId + "'";
		query = session.createSQLQuery(sql);
		@SuppressWarnings("rawtypes")
		List list_dept = query.list();
		if (list_dept.size() != 1)
			throw new RuntimeException("区域不存在");
		strs[0] = list_dept.get(0).toString();
		strs[1] = areaName;
		return strs;
	}

	/**
	 * 是否为公司，值转换
	 * 
	 * @param companyName
	 * @return
	 */
	private String checkIsCompany(String companyName) {
		if ("是".equals(companyName))
			return "yes";
		else if ("否".equals(companyName))
			return "no";
		else
			throw new RuntimeException("当前单元格的值 只能为 是或否");
	}

	/**
	 * 根据上级组织编码 取得 当前组织等级
	 * 
	 * @param code
	 * @return
	 */
	private String getDeptLevelFromOgrCode(String code) {
		return String.valueOf(code.split("-").length);
	};

	/**
	 * 根据上级部门编码 生成当前部门编码 ,没有上级(省下属) 则传入 null或者""
	 * 
	 * @param upOrgCode
	 * @return 当前部门组织编码
	 */
	public String getOrgCode(String upOrgCode) {
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		DecimalFormat codeFormat = new DecimalFormat("000");
		if (MyUtil.isEmpty(upOrgCode)) {
			// 省下属
			String sql = "select count (*) from pnr_dept where organizationno like 'DW-___'";
			int counts = jdbcService.queryForInt(sql);
			if (counts == 999)
				throw new RuntimeException("下级部门数量达到上限值 999");
			if (counts == 0)
				return "DW-001";
			else
				return "DW-" + codeFormat.format(counts + 1);
		} else {
			String sql = "select count (*) from pnr_dept where organizationno like '"
					+ upOrgCode + "-___'";
			int counts = jdbcService.queryForInt(sql);
			if (counts == 999)
				throw new RuntimeException("下级部门数量达到上限值 999");
			if (counts == 0)
				return upOrgCode + "-001";
			else
				return upOrgCode + "-" + codeFormat.format(counts + 1);
		}
	}

	public PartnerDept getDeptByOrgCode(String orgCode) {
		List<PartnerDept> listDept = partnerAppopsDeptDao
				.find("from PartnerDept dp where dp.deleted='0' and  dp.organizationNo = '"
						+ orgCode + "'");
		if (listDept.size() != 1)
			throw new RuntimeException("组织编码(" + orgCode + ")不存在或存在多条记录");
		return listDept.get(0);
	}

	public boolean remove(String[] ids) {
		boolean flag = false;
		CommonSpringJdbcServiceImpl csjsi = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		// 循环去除所有删除的数据id
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < ids.length; i++) {
			String sql = "select id,deptlevel from pnr_act_appops_dept where id='"
					+ ids[i] + "'";
			List<ListOrderedMap> resultList = csjsi.queryForList(sql);
			if (resultList.size() == 1) {
				ListOrderedMap map = resultList.get(0);
				String id = map.get("id").toString();
				// String name=map.get("name").toString();
				String deptlevel = map.get("deptlevel").toString();
				// String
				// interface_head_id=map.get("interface_head_id").toString();
				list.add(id);
				String sql2 = "";
				if ("1".equals(deptlevel)) {
					sql2 = "select id from pnr_act_appops_dept where interface_head_id='"
							+ id + "' and interface_head_id!=id";
				} else {
					sql2 = "select id from pnr_act_appops_dept where interface_head_id='"
							+ id + "'";
				}
				List<ListOrderedMap> resultList2 = csjsi.queryForList(sql2);
				if (!resultList2.isEmpty()) {
					for (int ii = 0; ii < resultList2.size(); ii++) {
						ListOrderedMap map2 = resultList2.get(ii);
						String id2 = map2.get("id").toString();
						list.add(id2);
						String sql3 = "select id from pnr_act_appops_dept where interface_head_id='"
								+ id2 + "'";
						List<ListOrderedMap> resultList3 = csjsi
								.queryForList(sql3);
						if (!resultList3.isEmpty()) {
							for (int iii = 0; iii < resultList3.size(); iii++) {
								ListOrderedMap map3 = resultList3.get(iii);
								String id3 = map3.get("id").toString();
								list.add(id3);
								String sql4 = "select id from pnr_act_appops_dept where interface_head_id='"
										+ id3 + "'";
								List<ListOrderedMap> resultList4 = csjsi
										.queryForList(sql4);
								if (!resultList4.isEmpty()) {
									for (int iiii = 0; iiii < resultList4
											.size(); iiii++) {
										ListOrderedMap map4 = resultList4
												.get(iiii);
										String id4 = map4.get("id").toString();
										list.add(id4);
									}
								}
							}
						}
					}
				}
			}
		}

		// 去除重复数据
		HashSet<String> hashSet = new HashSet<String>(list);
		list.clear();
		list.addAll(hashSet);
		// ITawSystemDeptManager mgr = (ITawSystemDeptManager)
		// ApplicationContextHolder
		// .getInstance().getBean("ItawSystemDeptManager");
		// 删除数据
		if (!list.isEmpty()) {
			for (int j = 0; j < list.size(); j++) {
				IPnrPartnerAppOpsDept partnerDept = this.getPartnerDept(list.get(j));
				partnerDept.setDeleted("1");
				String name = partnerDept.getName();
				this.savePartnerDept(partnerDept);
				/*String sql2 = "update taw_system_dept set deleted='1' where deptname='"
						+ name + "'";
				flag = csjsi.executeProcedure(sql2);*/
			}
		}
		return flag;
	}

	public boolean doRestoreUpdateXLSFileData(HSSFRow row,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean doRestoreSaveXLSFileData(HSSFRow row,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean doRestoreDeleteXLSFileData(HSSFRow row,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 
	 * @Title: updateProfessional
	 * @Description: 将专业类型映射到响应的字段便于统计；
	 * @param
	 * @Time:Dec 6, 2012-12:10:33 PM
	 * @Author:fengguangping
	 * @return PartnerDept 返回类型
	 * @throws
	 */
	public PartnerDept updateProfessional(PartnerDept partnerDept) {
		if (partnerDept != null) {
			String pnrProfessional = StaticMethod.null2String(partnerDept
					.getSpecialty());
			partnerDept.setPro1(0);
			partnerDept.setPro2(0);
			partnerDept.setPro3(0);
			partnerDept.setPro4(0);
			partnerDept.setPro5(0);
			if (pnrProfessional.contains("1122501")) {
				partnerDept.setPro1(1);
			}
			if (pnrProfessional.contains("1122502")) {
				partnerDept.setPro2(1);
			}
			if (pnrProfessional.contains("1122503")) {
				partnerDept.setPro3(1);
			}
			if (pnrProfessional.contains("1122504")) {
				partnerDept.setPro4(1);
			}
			if (pnrProfessional.contains("1122505")) {
				partnerDept.setPro5(1);
			}
		} else {
			partnerDept = new PartnerDept();
		}
		return partnerDept;
	}

	public PartnerDept updateCompanyType(PartnerDept partnerDept) {
		if (partnerDept != null) {
			String pnrCompanyType = StaticMethod.null2String(partnerDept
					.getType());
			partnerDept.setCompanyType1(0);
			partnerDept.setCompanyType2(0);
			partnerDept.setCompanyType3(0);
			partnerDept.setCompanyType4(0);
			partnerDept.setCompanyType5(0);
			partnerDept.setCompanyType6(0);
			partnerDept.setCompanyType7(0);
			partnerDept.setCompanyType8(0);
			if ("112010101".equals(pnrCompanyType)) {
				partnerDept.setCompanyType1(1);
			} else if ("112010102".equals(pnrCompanyType)) {
				partnerDept.setCompanyType2(1);
			} else if ("112010103".equals(pnrCompanyType)) {
				partnerDept.setCompanyType3(1);
			} else if ("112010104".equals(pnrCompanyType)) {
				partnerDept.setCompanyType4(1);
			} else if ("112010105".equals(pnrCompanyType)) {
				partnerDept.setCompanyType5(1);
			} else if ("112010106".equals(pnrCompanyType)) {
				partnerDept.setCompanyType6(1);
			} else if ("112010107".equals(pnrCompanyType)) {
				partnerDept.setCompanyType7(1);
			} else if ("112010108".equals(pnrCompanyType)) {
				partnerDept.setCompanyType8(1);
			}
		} else {
			partnerDept = new PartnerDept();
		}
		return partnerDept;
	}

	public PartnerDept updateFund(PartnerDept partnerDept) {
		if (partnerDept != null) {
			String fund = StaticMethod.null2String(partnerDept.getFund());
			double fundValue = Double.parseDouble(fund);
			partnerDept.setFund1(0);
			partnerDept.setFund2(0);
			partnerDept.setFund3(0);
			partnerDept.setFund4(0);
			if (fundValue < 100) {
				partnerDept.setFund1(1);
			} else if (fundValue >= 100 && fundValue < 500) {
				partnerDept.setFund2(1);
			} else if (fundValue >= 500 && fundValue < 1000) {
				partnerDept.setFund3(1);
			} else if (fundValue >= 1000) {
				partnerDept.setFund4(1);
			}
		}
		return partnerDept;
	}

	/**
	 * 
	 * @Title: updateStatisticInfo
	 * @Description: 更新统计信息
	 * @param
	 * @Time:Dec 6, 2012-12:56:28 PM
	 * @Author:fengguangping
	 * @return PartnerDept 返回类型
	 * @throws
	 */
	public IPnrPartnerAppOpsDept updateStatisticInfo(IPnrPartnerAppOpsDept partnerDept) {
		if (partnerDept != null) {
			String areaId = StaticMethod.null2String(partnerDept.getAreaId());
			String bigType = StaticMethod.null2String(partnerDept.getBigType());
			String pnrCompanyType = StaticMethod.null2String(partnerDept
					.getType());
			String pnrSelectLevel = StaticMethod.null2String(partnerDept
					.getSelectedLevel());
			String fund = StaticMethod.null2String(partnerDept.getFund());
			if ("".equals(fund)) {
				fund = "0";
			}
			double fundValue = Double.parseDouble(fund);
			partnerDept.setFund1(0);
			partnerDept.setFund2(0);
			partnerDept.setFund3(0);
			partnerDept.setFund4(0);
			partnerDept.setCompanyType1(0);
			partnerDept.setCompanyType2(0);
			partnerDept.setCompanyType3(0);
			partnerDept.setCompanyType4(0);
			partnerDept.setCompanyType5(0);
			partnerDept.setCompanyType6(0);
			partnerDept.setCompanyType7(0);
			partnerDept.setCompanyType8(0);
			partnerDept.setPro1(0);
			partnerDept.setPro2(0);
			partnerDept.setPro3(0);
			partnerDept.setPro4(0);
			partnerDept.setPro5(0);
			partnerDept.setPro6(0);
			partnerDept.setPro7(0);
			partnerDept.setPro8(0);
			partnerDept.setPro9(0);
			partnerDept.setSellevel1(0);
			partnerDept.setSellevel2(0);
			partnerDept.setSellevel3(0);
			if (bigType.contains("1122501")) {
				partnerDept.setPro1(1);
			}
			if (bigType.contains("1122502")) {
				partnerDept.setPro2(1);
			}
			if (bigType.contains("1122503")) {
				partnerDept.setPro3(1);
			}
			if (bigType.contains("1122504")) {
				partnerDept.setPro4(1);
			}
			if (bigType.contains("1122505")) {
				partnerDept.setPro5(1);
			}
			if (bigType.contains("1122506")) {
				partnerDept.setPro6(1);
			}
			if (bigType.contains("1122507")) {
				partnerDept.setPro7(1);
			}
			if (bigType.contains("1122508")) {
				partnerDept.setPro8(1);
			}
			if (bigType.contains("1122509")) {
				partnerDept.setPro9(1);
			}
			if ("112010101".equals(pnrCompanyType)) {
				partnerDept.setCompanyType1(1);
			} else if ("112010102".equals(pnrCompanyType)) {
				partnerDept.setCompanyType2(1);
			} else if ("112010103".equals(pnrCompanyType)) {
				partnerDept.setCompanyType3(1);
			} else if ("112010104".equals(pnrCompanyType)) {
				partnerDept.setCompanyType4(1);
			} else if ("112010105".equals(pnrCompanyType)) {
				partnerDept.setCompanyType5(1);
			} else if ("112010106".equals(pnrCompanyType)) {
				partnerDept.setCompanyType6(1);
			} else if ("112010107".equals(pnrCompanyType)) {
				partnerDept.setCompanyType7(1);
			} else if ("112010108".equals(pnrCompanyType)) {
				partnerDept.setCompanyType8(1);
			}
			if (fundValue < 100) {
				partnerDept.setFund1(1);
			} else if (fundValue >= 100 && fundValue < 500) {
				partnerDept.setFund2(1);
			} else if (fundValue >= 500 && fundValue < 1000) {
				partnerDept.setFund3(1);
			} else if (fundValue >= 1000) {
				partnerDept.setFund4(1);
			}
			if (areaId.length() == PartnerPrivUtils.AreaId_length_Province) {
				partnerDept.setProvinceId(areaId);
				partnerDept.setCityId(areaId);
				partnerDept.setCountyId(areaId);
			} else if (areaId.length() == PartnerPrivUtils.AreaId_length_City) {
				partnerDept.setProvinceId(areaId.substring(0,
						PartnerPrivUtils.AreaId_length_Province));
				partnerDept.setCityId(areaId);
				partnerDept.setCountyId(areaId);
			} else if (areaId.length() == PartnerPrivUtils.AreaId_length_County) {
				partnerDept.setProvinceId(areaId.substring(0,
						PartnerPrivUtils.AreaId_length_Province));
				partnerDept.setCityId(areaId.substring(0,
						PartnerPrivUtils.AreaId_length_City));
				partnerDept.setCountyId(areaId);
			}
			if ("1240301".equals(pnrSelectLevel)) {
				partnerDept.setSellevel1(1);
			} else if ("1240302".equals(pnrSelectLevel)) {
				partnerDept.setSellevel2(1);
			} else if ("1240303".equals(pnrSelectLevel)) {
				partnerDept.setSellevel3(1);
			}
		} else {
			partnerDept = new IPnrPartnerAppOpsDept();
		}
		return partnerDept;
	}

	/**
	 * 
	 * @Title: checkIsHasUserBeforeDelDept
	 * @Description: 删除部门前先检查该部门以及下属部门是否含有员工
	 * @param
	 * @Time:Jan 10, 2013-11:27:15 AM
	 * @Author:fengguangping
	 * @return List 返回包含员工的部门名称
	 * @throws
	 */
	public List checkIsHasUserBeforeDelDept(String[] ids) throws Exception {
		List hasUserDeptNames = new ArrayList<String>();
		for (int i = 0; i < ids.length; i++) {
			String aa = ids[i];
			IPnrPartnerAppOpsDept partnerDept = this.getPartnerDept(ids[i]);
			String deptMagid = StaticMethod.null2String(partnerDept
					.getDeptMagId());
			if ("".equals(deptMagid)) {
				continue;
			}
			PartnerAppopsUserService partnerUserMgr = (PartnerAppopsUserService) ApplicationContextHolder
					.getInstance().getBean("pnrAppopsUserService");
			
			String whereStr = " and partnerUser.deptId like '" + deptMagid
					+ "%'";
			List<IPnrPartnerAppOpsUser> partnerUserList = partnerUserMgr
					.getPartnerUsers(whereStr);
			if (partnerUserList.size() > 0) {// 表示存在员工
				String deptname = StaticMethod.null2String(partnerUserList.get(
						0).getDept());
				hasUserDeptNames.add(deptname);
			}
		}
		return hasUserDeptNames;
	}

	/**
	 * 通过登录用户的权限获取组织部门信息列表
	 */
	public List<IPnrPartnerAppOpsDept> getPartnerDeptsByUserRight(
			HttpServletRequest request) throws Exception {
		List<IPnrPartnerAppOpsDept> list = new ArrayList<IPnrPartnerAppOpsDept>();
		Map<String, String> map = PartnerPrivUtils.userIsPersonnel(request);
		String flag = map.get("isPersonnel");
		String hql = "";
		if (flag.equals("y")) {
			hql = "partnerDept.deptMagId like '"
					+ map.get("deptMagId")
					+ "%' and  partnerDept.deleted='0' order by partnerDept.name asc";
		} else if (flag.equals("n")) {
			hql = "partnerDept.areaId like '"
					+ map.get("areaId")
					+ "%' and partnerDept.deleted='0' order by partnerDept. name asc";
		} else if (flag.equals("admin")) {
			hql = "partnerDept.deleted='0'  order by partnerDept.name asc";
		}
		list = partnerAppopsDeptDao.getPartnerDeptsByHql(hql);
		return list;
	}

	/**
	 * 在保存部门的时间将代维资质添加进去，可以将事务控制在同一个方法中.
	 */
	public boolean saveDeptAndRelatedQualification(IPnrPartnerAppOpsDept dept,
			List<PnrQualification> qualifyList, String level) throws Exception {
		IPnrQualificationMgr pnrQualificationMgr = (IPnrQualificationMgr) ApplicationContextHolder
				.getInstance().getBean("pnrQualificationMgr");
		partnerAppopsDeptDao.savePartnerDept(dept);
		String uuid = StaticMethod.null2String(dept.getId());
		if ("1".equals(level)) {// 是省代维公司时要保存interfaceHeadId
			dept.setInterfaceHeadId(uuid);
			partnerAppopsDeptDao.savePartnerDept(dept);
		}
		QualificationUtils qualificationUtils = new QualificationUtils();
		for (int i = 0; i < qualifyList.size(); i++) {
			PnrQualification q = qualifyList.get(i);
			q.setRelatedDeptId(uuid);
			pnrQualificationMgr.save(q);
		}
		return false;
	}

	public boolean doLoadStaticSource() {
		// XXX Auto-generated method stub
		return false;
	}

	/**
	 * 保存系统部门信息 flag：是否是总代维公司
	 */
	private String saveTawSystemDept(HttpServletRequest request, boolean flag,
			IPnrPartnerAppOpsDept partnerDept, String dept_name) throws Exception {
		TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
		ITawSystemDeptManager mgr = (ITawSystemDeptManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemDeptManager");
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) ApplicationContextHolder
				.getInstance().getBean("partnerDeptMgr");
		TawSystemDept sysdept = new TawSystemDept();
		TawSystemDept tawSystemDept = new TawSystemDept();
		String newlinkId = "";
		String time = StaticMethod.getLocalString();
		// 将该部门在缓存中新增
		// TawWorkplanCacheBean tawWorkplanCacheBean = (TawWorkplanCacheBean)
		// ApplicationContextHolder
		// .getInstance().getBean("TawWorkplanCacheBean");modify by 陈元蜀
		// 2012-09-04

		ITawSystemDeptManager flushmgr = (ITawSystemDeptManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemDeptManagerFlush");
		Boolean Exist = flushmgr.getDeptnameIsExist(dept_name, "0");
		if (Exist) {
			TawSystemDept tawSystemDept1 = flushmgr.getDeptinfoBydeptname(
					dept_name, "0");
			tawSystemDept1.setDeptName(partnerDept.getName());
			flushmgr.saveTawSystemDept(tawSystemDept1);
			return StaticMethod.null2String(tawSystemDept1.getDeptId());
		}
		if (flag) {// 总代维公司
			RoleIdList roleIdList = (RoleIdList) ApplicationContextHolder
					.getInstance().getBean("roleIdList");
			sysdept = deptbo.getDeptinfobydeptid(
					String.valueOf(roleIdList.getParDeptId()), "0");
			tawSystemDept.setParentDeptid(String.valueOf(roleIdList
					.getParDeptId()));
		} else {// 子代维公司
			PartnerDept partnerDept1 = partnerDeptMgr
					.getPartnerDept(partnerDept.getInterfaceHeadId());
			sysdept = deptbo.getDeptinfobydeptid(partnerDept1.getDeptMagId(),
					"0");
			tawSystemDept.setParentDeptid(sysdept.getLinkid());
		}
		// 根据父部门linkId生成新的linkId
		if (partnerDept.getDeptMagId() == null
				|| "".equals(partnerDept.getDeptMagId())) {
			newlinkId = mgr.getNewLinkid(sysdept.getLinkid(), "0");
			tawSystemDept.setDeptId(newlinkId);
			// 该linkid作为nodeId维持树形结构
			tawSystemDept.setLinkid(newlinkId);
			tawSystemDept.setParentLinkid(sysdept.getLinkid());
			tawSystemDept.setLeaf("1");
			flushmgr.saveTawSystemDept(sysdept);
			int oerdercode = sysdept.getOrdercode().intValue();
			sysdept.setLeaf("0");
			flushmgr.saveTawSystemDept(sysdept);
			tawSystemDept.setOrdercode(Integer.valueOf(oerdercode + 1));
			tawSystemDept.setOpertime(time);
			tawSystemDept.setDeleted("0");
			tawSystemDept.setAreaid(partnerDept.getAreaId());
			tawSystemDept.setOperremoteip(request.getRemoteAddr());
			tawSystemDept.setIsDaiweiRoot("0");// 0表示该部门不是代维根节点,1表示是代维根节点
			tawSystemDept.setTmpdeptid(tawSystemDept.getDeptId());
			tawSystemDept.setIsPartners("0");
			tawSystemDept.setDeptName(partnerDept.getName());
		} else {
			newlinkId = partnerDept.getDeptMagId();
			tawSystemDept = mgr.getDeptinfobydeptid(newlinkId,
					partnerDept.getDeleted());
			tawSystemDept.setAreaid(partnerDept.getAreaId());
			tawSystemDept.setOperremoteip(request.getRemoteAddr());
			tawSystemDept.setDeptName(partnerDept.getName());
			tawSystemDept.setOpertime(time);
		}
		flushmgr.saveTawSystemDept(tawSystemDept);
		return newlinkId;
	}

	/**
	 * 
	 * @Title: getNextLevecCompByAreaid
	 * @Description: 根据areaid和树当前节点来获取部门,
	 * @param parentdeptid
	 *            ：当前树节点的deptUUid
	 * @param delid
	 *            ：是否删除标志,"0"-未删除,"1"-删除
	 * @param areaId
	 *            ：区域id
	 * @return ArrayList
	 * @author fengguangping 2013.04.19
	 * @throws
	 */
	public List getNextLevecCompByAreaid(String parentdeptid, String delid,
			String areaId, int isPartner) throws Exception {
		List list = new ArrayList();
		list = partnerAppopsDeptDao.getNextLevecCompByAreaid(parentdeptid, delid,
				areaId, isPartner);
		return list;
	}

	public IPnrPartnerAppOpsDept getPartnerDeptByDeptMagId(String node) {
		IPnrPartnerAppOpsDept dept = new IPnrPartnerAppOpsDept();
		List list = partnerAppopsDeptDao.getPartnerDepts(" and deptMagId='" + node
				+ "'");
		if (list != null && list.size() == 1) {
			dept = (IPnrPartnerAppOpsDept) list.get(0);
		}
		return dept;
	}

	/**
	 * 
	 * @Description 更新pnr_user的deptName
	 * @date May 9, 2013 9:53:13 AM
	 * @author Fengguangping fengguangping@boco.com.cn
	 * @param partnerDept
	 */
	public void updatePnrUserDeptname(IPnrPartnerAppOpsDept partnerDept) throws Exception {
		String deptName = partnerDept.getName();
		String deptMagid = partnerDept.getDeptMagId();
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		String sql = "update pnr_act_appops_user set dept_name='" + deptName
				+ "'  where dept_id='" + deptMagid + "'";
	/*	String sql1 = "update taw_system_user set deptname='" + deptName
				+ "'  where deptid='" + deptMagid + "'";*/
		jdbcService.executeProcedure(sql);
//		jdbcService.executeProcedure(sql1);
	}

	/**
	 * 
	 * @Description:将更新pnr_user,pnr_dept,taw_system_user，taw_system_dept以及资质信息封装在同一个事务中;
	 * @date May 9, 2013 10:25:38 AM
	 * @author Fengguangping fengguangping@boco.com.cn
	 * @param oldDeptName
	 *            :修改前部门名称
	 * @param request
	 * @param partnerDept
	 *            ：部门信息
	 * @param qualifyList
	 *            ：资质信息
	 * @param deptLevel
	 *            ：部门级别
	 * @throws Exception
	 */
	public void updateDeptAndUser(String oldDeptName,
			HttpServletRequest request, IPnrPartnerAppOpsDept partnerDept,
			List qualifyList, String deptLevel) throws Exception {
		if (!"".equals(oldDeptName)) {// 修改pnr_user中的deptName;
			if (!oldDeptName.equals(partnerDept.getName())) {// 发生代维组织名称修改时，修改pnr_user和taw_system_user里面的部门名称
//				updatePnrUserDeptname(partnerDept);
			}
//			saveTawSystemDept(request, false, partnerDept, oldDeptName);// 修改taw_system_dept
		}
		updateStatisticInfo(partnerDept);// 更新统计信息;
		if (qualifyList != null) {
			saveDeptAndRelatedQualification(partnerDept, qualifyList, deptLevel);// 保存部门信息和相应的资质信息;
		} else {
			savePartnerDept(partnerDept);// 保存部门信息;
		}
	}

	@Override
	public boolean doDeleteXLSFileData(HSSFRow row, HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
}