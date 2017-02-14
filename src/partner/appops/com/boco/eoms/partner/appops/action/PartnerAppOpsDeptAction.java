package com.boco.eoms.partner.appops.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import utils.Nop3Utils;
import utils.PartnerPrivUtils;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.area.dao.TawSystemAreaDao;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dept.service.bo.TawSystemDeptBo;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.eva.mgr.IEvaTreeMgr;
import com.boco.eoms.eva.model.EvaTree;
import com.boco.eoms.eva.util.EvaConstants;
import com.boco.eoms.partner.appops.model.IPnrPartnerAppOpsDept;
import com.boco.eoms.partner.appops.service.PartnerAppopsDeptService;
import com.boco.eoms.partner.appops.webapp.form.IPnrPartnerAppOpsDeptForm;
import com.boco.eoms.partner.baseinfo.mgr.AreaDeptTreeMgr;
import com.boco.eoms.partner.baseinfo.mgr.IPnrQualificationMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PnrQualification;
import com.boco.eoms.partner.baseinfo.util.AreaDeptTreeConstants;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PartnerDeptConstants;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.baseinfo.util.PnrDeptAndUserConfigSetList;
import com.boco.eoms.partner.baseinfo.util.PnrDeptUtil;
import com.boco.eoms.partner.baseinfo.util.QualificationUtils;
import com.boco.eoms.partner.baseinfo.util.RoleIdList;
import com.boco.eoms.partner.baseinfo.webapp.form.PartnerDeptForm;
import com.boco.eoms.partner.process.util.PnrProcessCach;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**

 * 
 */
public final class PartnerAppOpsDeptAction extends BaseAction {
	TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
	public static String defaultParentNodeId = "";// 当save 或 remove 方法，调用

	// search方法时，利用这个变量，定位到相应人力信息的树节点下

	/**
	 * δָ������ʱĬ�ϵ��õķ���
	 * 
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
	 * 
	 * @Title: goToShowOrganization
	 * @Description:显示组织架构图
	 * @param
	 * @Time:Jan 4, 2013-1:56:56 PM
	 * @Author:fengguangping
	 * @return ActionForward 返回类型
	 * @throws
	 */
	public ActionForward goToShowOrganization(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		String hql1 = " deptLevel='1' and deleted='0' ";
		List<PartnerDept> list1 = partnerDeptMgr.getPartnerDeptsByHql(hql1);
		List<List> resultList = new ArrayList<List>();
		List list;
		JsonArray jsonArray = new JsonArray();
		JsonArray jsonArray1 = new JsonArray();
		JsonArray jsonArray2 = new JsonArray();
		JsonArray jsonArray3 = new JsonArray();
		JsonArray jsonArray4 = new JsonArray();
		for (int i = 0; i < list1.size(); i++) {
			PartnerDept dept1 = list1.get(i);
			JsonObject jsonObject1 = new JsonObject();
			String hql2 = " deleted='0' and  id <> '" + dept1.getId() + "'"
					+ " and interfaceHeadId='" + dept1.getId() + "'";
			List<PartnerDept> list2 = partnerDeptMgr.getPartnerDeptsByHql(hql2);// 获取第2级部门
			if (list2.size() == 0) {
				list = new ArrayList();
				list.add(list1.get(i).getName() + "(" + list2.size() + ")");
				list.add("--" + "|" + CommonSqlHelper.generateUUID());
				list.add("--" + "|" + CommonSqlHelper.generateUUID());
				list.add("--" + "|" + CommonSqlHelper.generateUUID());
				resultList.add(list);
			}
			jsonArray2 = new JsonArray();
			for (int j = 0; j < list2.size(); j++) {
				PartnerDept dept2 = list2.get(j);
				JsonObject jsonObject2 = new JsonObject();
				String hql3 = "  deleted='0' and  id <> '" + dept2.getId()
						+ "'" + " and interfaceHeadId='" + dept2.getId() + "'";
				List<PartnerDept> list3 = partnerDeptMgr
						.getPartnerDeptsByHql(hql3);// 获取第3级部门
				jsonArray3 = new JsonArray();
				if (list3.size() == 0) {
					list = new ArrayList();
					list.add(list1.get(i).getName() + "(" + list2.size() + ")");
					list.add(list2.get(j).getName() + "(" + list3.size() + ")");
					list.add("--" + "|" + CommonSqlHelper.generateUUID());
					list.add("--" + "|" + CommonSqlHelper.generateUUID());
					resultList.add(list);
				}
				for (int k = 0; k < list3.size(); k++) {
					PartnerDept dept3 = list3.get(k);
					JsonObject jsonObject3 = new JsonObject();
					String hql4 = " deleted='0' and id <> '" + dept3.getId()
							+ "'" + " and interfaceHeadId='" + dept3.getId()
							+ "'";
					List<PartnerDept> list4 = partnerDeptMgr
							.getPartnerDeptsByHql(hql4);// 获取第4级部门
					if (list4.size() == 0) {
						list = new ArrayList();
						list.add(list1.get(i).getName() + "(" + list2.size()
								+ ")");
						list.add(list2.get(j).getName() + "(" + list3.size()
								+ ")");
						list.add(list3.get(k).getName() + "(" + list4.size()
								+ ")");
						list.add("--" + "|" + CommonSqlHelper.generateUUID());
						resultList.add(list);
					}
					jsonArray4 = new JsonArray();
					for (int l = 0; l < list4.size(); l++) {
						list = new ArrayList();
						list.add(list1.get(i).getName() + "(" + list2.size()
								+ ")");
						list.add(list2.get(j).getName() + "(" + list3.size()
								+ ")");
						list.add(list3.get(k).getName() + "(" + list4.size()
								+ ")");
						list.add(list4.get(l).getName());
						resultList.add(list);
						PartnerDept dept4 = list4.get(l);
						JsonObject jsonObject4 = partnerDeptToJsonObject(dept4);
						jsonArray4.add(jsonObject4);
					}
					jsonObject3.addProperty("id",
							StaticMethod.null2String(dept3.getDeptMagId()));
					jsonObject3.addProperty("name",
							StaticMethod.null2String(dept3.getName()));
					jsonObject3.addProperty("data", "");
					jsonObject3.add("children", jsonArray4);
					jsonArray3.add(jsonObject3);
				}
				jsonObject2.addProperty("id",
						StaticMethod.null2String(dept2.getDeptMagId()));
				jsonObject2.addProperty("name",
						StaticMethod.null2String(dept2.getName()));
				jsonObject2.addProperty("data", "");
				jsonObject2.add("children", jsonArray3);
				jsonArray2.add(jsonObject2);
			}
			jsonObject1.addProperty("id",
					StaticMethod.null2String(dept1.getDeptMagId()));
			jsonObject1.addProperty("name",
					StaticMethod.null2String(dept1.getName()));
			jsonObject1.addProperty("data", "");
			jsonObject1.add("children", jsonArray2);
			jsonArray1.add(jsonObject1);
		}
		JsonObject jsonObject0 = new JsonObject();
		jsonArray.add(jsonObject0);
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		jsonObject0.addProperty("id", sessionForm.getRootAreaId());
		jsonObject0.addProperty("name", sessionForm.getRootAreaName()
				+ "代维公司组织架构");
		jsonObject0.addProperty("data", "");
		jsonObject0.add("children", jsonArray1);
		// if ("showType".equals("1")) {
		// List<List<TdObjModel>> reList=HomeHelper.verticalGrowp(4, 0, 0,
		// resultList);
		// for (List<TdObjModel> list4 : reList) {
		// for (int m = 0; m< list4.size(); m++) {
		// TdObjModel td=list4.get(m);
		// String tdName=StaticMethod.null2String(td.getName());
		// if (tdName.contains("|")) {
		// tdName=tdName.substring(0, 2);
		// td.setName(tdName);
		// }
		// }
		// }
		// request.setAttribute("tableList", reList);
		// }
		request.setAttribute("json", jsonObject0.toString());
		return mapping.findForward("goToShowOrganization");
	}

	public JsonObject partnerDeptToJsonObject(PartnerDept dept) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("id", dept.getDeptMagId());
		jsonObject.addProperty("name", dept.getName());
		jsonObject.addProperty("data", "");
		jsonObject.addProperty("children", "");
		return jsonObject;
	}

	/**
	 * ������
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
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));// nodeId
		// 是地市的nodeId
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		AreaDeptTree areaDeptTree = areaDeptTreeMgr
				.getAreaDeptTreeByNodeId(nodeId);// 此对象是地市
		String areaId = areaDeptTree.getAreaId();
		String areaName = areaDeptTree.getNodeName();

		request.setAttribute("parentNodeId", nodeId);
		request.setAttribute("areaId", areaId);

		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");

		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String factoryDict = pnrBaseAreaIdList.getPartnerRootId();
		// 增加 大合作伙伴 和 小合作伙伴 的关联关系
		if (!nodeId.equals(String.valueOf(factoryDict))) {
			List partnerList = areaDeptTreeMgr.getNextLevelAreaDeptTrees(String
					.valueOf(factoryDict));
			request.setAttribute("partnerList1", partnerList);
			request.setAttribute("partnerList", partnerList);
		}
		// 定义取出所有厂家的集合，使用公共字典取得数据
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		ArrayList tawSystemDictTypeList = mgr.getDictSonsByDictid(String
				.valueOf(roleIdList.getBigType()));

		request.setAttribute("tawSystemDictType", tawSystemDictTypeList);

		PartnerDeptForm partnerDeptForm = (PartnerDeptForm) form;
		partnerDeptForm.setAreaName(areaName);
		request.setAttribute("isEdit", "add");
		updateFormBean(mapping, request, partnerDeptForm);

		return mapping.findForward("edit");
	}

	/**
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
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		String id = StaticMethod.null2String(request.getParameter("proId"));
		String id1 = StaticMethod.null2String(request.getParameter("proId1"));
		if (!id1.equals("")) {
			id = id1;
		}
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));
		PartnerDeptForm partnerDeptForm = (PartnerDeptForm) form;
		String name = StaticMethod.null2String(partnerDeptForm.getName());
		PartnerDept partnerDept = null;
		AreaDeptTree areaDeptTree = null;
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String factoryDict = pnrBaseAreaIdList.getPartnerRootId();
		if (!id.equals("")) {
			partnerDept = partnerDeptMgr.getPartnerDept(id);
			areaDeptTree = areaDeptTreeMgr.getAreaDeptTreeByNodeId(partnerDept
					.getTreeNodeId());// 此对象是地市
			request.setAttribute("parentNodeId", areaDeptTree.getParentNodeId());
			if (!areaDeptTree.getParentNodeId().equals(factoryDict)) {
				List partnerList = areaDeptTreeMgr
						.getNextLevelAreaDeptTrees(factoryDict);
				request.setAttribute("partnerList1", partnerList);
				request.setAttribute("partnerList", partnerList);
			}
		} else if (!name.equals("")) {
			List list = partnerDeptMgr.getPartnerDepts(" and name = '" + name
					+ "'");
			if (list.size() > 0) {
				partnerDept = (PartnerDept) list.get(0);
			}
		} else if (!nodeId.equals("")) {
			partnerDept = partnerDeptMgr.getPartnerDeptByTreeNodeId(nodeId);
			areaDeptTree = areaDeptTreeMgr.getAreaDeptTreeByNodeId(nodeId);// 此对象是地市
			// 增加 大合作伙伴 和 小合作伙伴 的关联关系
			if (!nodeId.equals(String.valueOf(factoryDict))) {
				List partnerList = areaDeptTreeMgr
						.getNextLevelAreaDeptTrees(String.valueOf(factoryDict));
				request.setAttribute("partnerList1", partnerList);
				request.setAttribute("partnerList", partnerList);
			}
			request.setAttribute("parentNodeId", areaDeptTree.getParentNodeId());
			if (nodeId.length() >= 7) {
				request.setAttribute("proId", partnerDept.getId());
			}
		}
		if (partnerDept != null) {
			partnerDeptForm = (PartnerDeptForm) convert(partnerDept);
		}
		if (areaDeptTree != null) {
			String areaId = areaDeptTree.getId();
			String areaName = areaDeptTree.getNodeName();
			request.setAttribute("parentNodeId", nodeId);
			request.setAttribute("areaId", areaId);
			if (StaticMethod.null2String(partnerDeptForm.getAreaName()).equals(
					"")) {
				partnerDeptForm.setAreaName(areaName);
			}
		}
		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
		// 定义取出所有厂家的集合，使用公共字典取得数据
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		ArrayList tawSystemDictTypeList = mgr.getDictSonsByDictid(String
				.valueOf(roleIdList.getBigType()));
		ArrayList bigTypeArrayList = new ArrayList();
		if (partnerDeptForm.getBigType() != null
				&& !"".equals(partnerDeptForm.getBigType())) {
			String[] bigType = partnerDeptForm.getBigType().split(",");
			for (int i = 0; i < bigType.length; i++) {
				bigTypeArrayList.add(bigType[i]);
			}
			for (int i = 0; i < tawSystemDictTypeList.size(); i++) {
				TawSystemDictType tawSystemDictType = (TawSystemDictType) tawSystemDictTypeList
						.get(i);
				if (bigTypeArrayList.contains(tawSystemDictType.getDictId())) {
					tawSystemDictType.setDictRemark("isTrue");
				}
			}
		}
		request.setAttribute("isEdit", "editMethod");
		request.setAttribute("tawSystemDictType", tawSystemDictTypeList);
		updateFormBean(mapping, request, partnerDeptForm);
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) getBean("pnrDeptAndUserConfigSetList");
		request.setAttribute("qualifyConfig", setList.getQualificationConfig()); // 是否需要添加资质模块
		return mapping.findForward("edit");
	}

	/**
	 * �޸ĳ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		PartnerAppopsDeptService partnerDeptMgr = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		String id = StaticMethod.null2String(request.getParameter("proId"));
		String id1 = StaticMethod.null2String(request.getParameter("proId1"));
		String searchInto = StaticMethod.null2String(request
				.getParameter("searchInto"));
		if (!id1.equals("")) {
			id = id1;
		}
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));
		IPnrPartnerAppOpsDeptForm partnerDeptForm = (IPnrPartnerAppOpsDeptForm) form;
		String name = StaticMethod.null2String(partnerDeptForm.getName());
		IPnrPartnerAppOpsDept partnerDept = null;
		AreaDeptTree areaDeptTree = null;
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String factoryDict = pnrBaseAreaIdList.getPartnerRootId();
		if (!id.equals("")) {
			partnerDept = partnerDeptMgr.getPartnerDept(id);
			areaDeptTree = areaDeptTreeMgr.getAreaDeptTreeByNodeId(partnerDept
					.getTreeNodeId());// 此对象是地市
			request.setAttribute("parentNodeId", areaDeptTree.getParentNodeId());
			if (!partnerDept.getId().equals(partnerDept.getInterfaceHeadId())) {
				List partnerList = areaDeptTreeMgr
						.getNextLevelAreaDeptTrees(factoryDict);
				request.setAttribute("partnerList1", partnerList);
				request.setAttribute("partnerList", partnerList);
			}
		} else if (!name.equals("")) {
			List list = partnerDeptMgr.getPartnerDepts(" and name = '" + name
					+ "'");
			if (list.size() > 0) {
				partnerDept = (IPnrPartnerAppOpsDept) list.get(0);
			}
		} else if (!nodeId.equals("")) {
			partnerDept = partnerDeptMgr.getPartnerDeptByTreeNodeId(nodeId);
			areaDeptTree = areaDeptTreeMgr.getAreaDeptTreeByNodeId(nodeId);// 此对象是地市
			// 增加 大合作伙伴 和 小合作伙伴 的关联关系
			if (!nodeId.equals(String.valueOf(factoryDict))) {
				List partnerList = areaDeptTreeMgr
						.getNextLevelAreaDeptTrees(String.valueOf(factoryDict));
				request.setAttribute("partnerList1", partnerList);
				request.setAttribute("partnerList", partnerList);
			}
			request.setAttribute("parentNodeId", areaDeptTree.getParentNodeId());
			if (nodeId.length() >= 7) {
				request.setAttribute("proId", partnerDept.getId());
			}
		}
		if (partnerDept != null) {
			partnerDeptForm = (IPnrPartnerAppOpsDeptForm) convert(partnerDept);
		}
		if (areaDeptTree != null) {
			String areaId = areaDeptTree.getId();
			String areaName = areaDeptTree.getNodeName();
			request.setAttribute("parentNodeId", nodeId);
			request.setAttribute("areaId", areaId);
			if (StaticMethod.null2String(partnerDeptForm.getAreaName()).equals(
					"")) {
				partnerDeptForm.setAreaName(areaName);
			}
		}
		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
		// 定义取出所有厂家的集合，使用公共字典取得数据
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		// ArrayList tawSystemDictTypeList = mgr.getDictSonsByDictid(String
		// .valueOf(roleIdList.getBigType()));
		// ArrayList bigTypeArrayList = new ArrayList();
		// if (partnerDeptForm.getBigType() != null
		// && !"".equals(partnerDeptForm.getBigType())) {
		// String[] bigType = partnerDeptForm.getBigType().split(",");
		// for (int i = 0; i < bigType.length; i++) {
		// bigTypeArrayList.add(bigType[i]);
		// }
		// for (int i = 0; i < tawSystemDictTypeList.size(); i++) {
		// TawSystemDictType tawSystemDictType = (TawSystemDictType)
		// tawSystemDictTypeList
		// .get(i);
		// if (bigTypeArrayList.contains(tawSystemDictType.getDictId())) {
		// tawSystemDictType.setDictRemark("isTrue");
		// }
		// }
		// }

		/**
		 * 定义取出专业集合，使用公共字典取得数据
		 */
		ArrayList specialtyList = mgr.getDictSonsByDictid("11225");
		ArrayList bigTypeArrayList = new ArrayList();
		if (partnerDeptForm.getBigType() != null
				&& !"".equals(partnerDeptForm.getBigType())) {
			String[] bigType = partnerDeptForm.getBigType().split(",");
			for (int i = 0; i < bigType.length; i++) {
				bigTypeArrayList.add(bigType[i]);
			}
			for (int i = 0; i < specialtyList.size(); i++) {
				TawSystemDictType tawSystemDictType = (TawSystemDictType) specialtyList
						.get(i);
				if (bigTypeArrayList.contains(tawSystemDictType.getDictId())) {
					tawSystemDictType.setDictRemark("isTrue");
				}
			}
		}
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) getBean("pnrDeptAndUserConfigSetList");
		String qualifyConfig = StaticMethod.null2String(setList
				.getQualificationConfig());
		if ("1".equals(qualifyConfig)) {
			/* 取出相关联的资质信息 */
			IPnrQualificationMgr pnrQualificationMgr = (IPnrQualificationMgr) getBean("pnrQualificationMgr");
			List<PnrQualification> list = pnrQualificationMgr
					.findPnrQualificationsByDeptUUid(id);
			request.setAttribute("list", list);
		}
		request.setAttribute("qualifyConfig", qualifyConfig); // 是否需要添加资质模块
		request.setAttribute("specialtyList", specialtyList);
		request.setAttribute("isEdit", "editMethod");
		request.setAttribute("isPartner", isPartner);
		request.setAttribute("searchInto", searchInto);// 是否是数据钻取标志
		// request.setAttribute("tawSystemDictType", tawSystemDictTypeList);
		updateFormBean(mapping, request, partnerDeptForm);
		request.setAttribute("hasRightForAdd", StaticMethod.null2String(request
				.getParameter("hasRightForAdd")));
		return mapping.findForward("detail");
	}

	/**
	 * ���泧��
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
		PartnerAppopsDeptService partnerDeptMgr = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		// bww 关联部门
		ITawSystemDeptManager mgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		String[] bigType = request.getParameterValues("bigType");
		StringBuffer bigTypeString = new StringBuffer();
		for (int i = 0; i < bigType.length; i++) {
			bigTypeString.append(bigType[i]);
			bigTypeString.append(",");
		}
		IPnrPartnerAppOpsDeptForm partnerDeptForm = (IPnrPartnerAppOpsDeptForm) form;
		partnerDeptForm.setBigType(bigTypeString.toString());
		boolean isNew = (null == partnerDeptForm.getId() || ""
				.equals(partnerDeptForm.getId()));
		IPnrPartnerAppOpsDept partnerDept = (IPnrPartnerAppOpsDept) convert(partnerDeptForm);
		partnerDept.setDeleted("0");
		String parentNodeId = request.getParameter("parentNodeId");
		String treeNodeId = partnerDept.getTreeNodeId();
		String id = null;
		try {
			id = UUIDHexGenerator.getInstance().getID();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 在树表里存厂商节点 保存合作伙伴单表的同时保存树表里合作伙伴节点
		if (isNew && parentNodeId != null && !parentNodeId.equals("")) {// 新增

			// 给地市节点的factoryLists字段赋值，为了在合作伙伴统计报表中使用。
			AreaDeptTree area = areaDeptTreeMgr
					.getAreaDeptTreeByNodeId(parentNodeId);
			// 在树表里存厂商节点 保存合作伙伴单表的同时保存树表里合作伙伴节点
			AreaDeptTree factory = new AreaDeptTree();
			factory.setParentNodeId(parentNodeId);
			factory.setNodeName(partnerDeptForm.getName());
			factory.setNodeType(AreaDeptTreeConstants.NODE_TYPE_FACTORY);

			// 设置AreaDeptTree表中合作伙伴的isShow字段，为0正常显示
			factory.setIsShow("0");

			/**
			 * 新增功能：保存合作伙伴的所属地域ID存入数据库 add by wangjunfegn 2010-01-21
			 */
			String areaId = area.getAreaId();
			factory.setAreaId(areaId);
			areaDeptTreeMgr.saveAreaDeptTree(factory);
			partnerDept.setAreaId(areaId);
			partnerDept.setTreeId(factory.getId());
			partnerDept.setTreeNodeId(factory.getNodeId());
			partnerDept.setCreateTime(StaticMethod.getLocalTime());// 增加新增时间

		}
		// if (treeNodeId != null && !treeNodeId.equals("")) {
		// AreaDeptTree factory = areaDeptTreeMgr
		// .getAreaDeptTreeByNodeId(treeNodeId);
		// factory.setNodeName(partnerDept.getName());
		// areaDeptTreeMgr.saveAreaDeptTree(factory);
		// }
		if (treeNodeId != null && !treeNodeId.equals("")) {// treeNodeId存在，表示为修改
			// 修改地市节点的factoryLists字段，为了在合作伙伴统计报表中使用。
			AreaDeptTree factory = areaDeptTreeMgr
					.getAreaDeptTreeByNodeId(treeNodeId);
			if (!factory.getNodeName().equals(partnerDept.getName())) {
				AreaDeptTree area = areaDeptTreeMgr
						.getAreaDeptTreeByNodeId(parentNodeId);
				areaDeptTreeMgr.saveAreaDeptTree(area);
			}

			factory.setNodeName(partnerDept.getName());
			areaDeptTreeMgr.saveAreaDeptTree(factory);
		}
		if (isNew) {
			// bww 关联部门
			TawSystemDept sysdept = new TawSystemDept();
			TawSystemDept tawSystemDept = new TawSystemDept();
			String newlinkId = "";
			String time = StaticMethod.getLocalString();
			// 将该部门在缓存中新增
			// TawWorkplanCacheBean tawWorkplanCacheBean =
			// (TawWorkplanCacheBean) ApplicationContextHolder
			// .getInstance().getBean("TawWorkplanCacheBean");modify by 陈元蜀
			// 2012-09-04

			ITawSystemDeptManager flushmgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManagerFlush");

			if ("".equals(partnerDept.getInterfaceHeadId())
					|| partnerDept.getInterfaceHeadId() == null) {
				RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
				sysdept = deptbo.getDeptinfobydeptid(
						String.valueOf(roleIdList.getParDeptId()), "0");
				tawSystemDept.setParentDeptid(String.valueOf(roleIdList
						.getParDeptId()));
			} else {
				IPnrPartnerAppOpsDept partnerDept1 = partnerDeptMgr
						.getPartnerDept(partnerDept.getInterfaceHeadId());
				sysdept = deptbo.getDeptinfobydeptid(
						partnerDept1.getDeptMagId(), "0");
				// newlinkId = mgr.getNewLinkid(sysdept.getDeptId(), "0");
				tawSystemDept.setParentDeptid(sysdept.getLinkid());
			}
			// 根据父部门linkId生成新的linkId
			newlinkId = mgr.getNewLinkid(sysdept.getLinkid(), "0");
			tawSystemDept.setDeptId(newlinkId);
			// 该linkid作为nodeId维持树形结构
			tawSystemDept.setLinkid(newlinkId);
			tawSystemDept.setParentLinkid(sysdept.getLinkid());
			tawSystemDept.setLeaf("1");
			flushmgr.saveTawSystemDept(sysdept);
			int oerdercode = sysdept.getOrdercode().intValue();
			sysdept.setLeaf("0");
//			flushmgr.saveTawSystemDept(sysdept);
			tawSystemDept.setOrdercode(Integer.valueOf(oerdercode + 1));
			tawSystemDept.setOpertime(time);
			tawSystemDept.setDeleted("0");
			tawSystemDept.setOperremoteip(request.getRemoteAddr());
			tawSystemDept.setIsDaiweiRoot("0");// 0表示该部门不是代维根节点,1表示是代维根节点
			tawSystemDept.setTmpdeptid(tawSystemDept.getDeptId());
			tawSystemDept.setIsPartners("0");
			tawSystemDept.setDeptName(partnerDept.getName());

			/**
			 * 合作伙伴中没有作业计划，屏蔽之 modify by 陈元蜀 2012-09-04 begin Map workplanMap =
			 * tawWorkplanCacheBean.getWorkplanUser(); Map deptMap = (Map)
			 * workplanMap.get("deptMap"); deptMap.put(newlinkId,
			 * tawSystemDept.getDeptName()); modify by 陈元蜀 2012-09-04 end
			 */
//			flushmgr.saveTawSystemDept(tawSystemDept);

			IEvaTreeMgr evaTreeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
			EvaTree evaTree = new EvaTree();
			String newNodeId = evaTreeMgr.getMaxNodeId("1");
			evaTree.setNodeId(newNodeId);
			evaTree.setNodeType(EvaConstants.NODETYPE_TEMPLATETYPE);
			evaTree.setLeaf(EvaConstants.NODE_LEAF);
			evaTree.setParentNodeId("1");
			evaTree.setNodeRemark(newlinkId);
			evaTree.setNodeName(partnerDept.getName());
			evaTreeMgr.saveTreeNode(evaTree);
			partnerDept.setDeptMagId(newlinkId);
			/**
			 * 新增功能：保存所属地域ID存入数据库(人力信息、仪器仪表、车辆、油机) add by wangjunfegn 2010-01-21
			 */
			// 给地市节点的factoryLists字段赋值，为了在合作伙伴统计报表中使用。
			AreaDeptTree area = areaDeptTreeMgr
					.getAreaDeptTreeByNodeId(parentNodeId);
			String areaId = area.getAreaId();

			partnerDeptMgr.savePartnerDept(partnerDept);
			partnerDept.setInterfaceHeadId(partnerDept.getId());
			partnerDeptMgr.savePartnerDept(partnerDept);

			// if("".equals(partnerDept.getInterfaceHeadId())||partnerDept.getInterfaceHeadId()==null){
			// partnerDept.setInterfaceHeadId(partnerDept.getId());
			// partnerDeptMgr.savePartnerDept(partnerDept);
			// }

			if (partnerDept.getTreeNodeId() != null) {
				AreaDeptTree factory = areaDeptTreeMgr
						.getAreaDeptTree(partnerDept.getTreeId());

				// 在树表里存人力信息节点
				AreaDeptTree user = new AreaDeptTree();
				user.setParentNodeId(factory.getNodeId());
				user.setNodeName("人力信息");
				user.setNodeType(AreaDeptTreeConstants.NODE_TYPE_USER);

				user.setAreaId(areaId);

				user.setInterfaceHeadId(partnerDept.getInterfaceHeadId());

				user.setPartnerid(partnerDept.getId());
				// 显示人力信息
				user.setIsShow("0");

				areaDeptTreeMgr.saveAreaDeptTree(user);

				// 在树表里存仪器仪表节点
				AreaDeptTree instrument = new AreaDeptTree();
				instrument.setParentNodeId(factory.getNodeId());
				instrument.setNodeName("仪器仪表");
				instrument
						.setNodeType(AreaDeptTreeConstants.NODE_TYPE_INSTRUMENT);

				instrument.setAreaId(areaId);
				instrument.setInterfaceHeadId(partnerDept.getInterfaceHeadId());
				instrument.setPartnerid(partnerDept.getId());
				// 隐藏仪器仪表管理，当新增仪器仪表时isShow设置为0，可正常显示
				instrument.setIsShow("1");

				areaDeptTreeMgr.saveAreaDeptTree(instrument);

				// 在树表里存车辆管理节点
				AreaDeptTree car = new AreaDeptTree();
				car.setParentNodeId(factory.getNodeId());
				car.setNodeName("车辆管理");
				car.setNodeType(AreaDeptTreeConstants.NODE_TYPE_CAR);

				car.setAreaId(areaId);
				car.setInterfaceHeadId(partnerDept.getInterfaceHeadId());
				car.setPartnerid(partnerDept.getId());
				// 隐藏车辆管理，当新增车辆时isShow设置为0，可正常显示
				car.setIsShow("1");

				areaDeptTreeMgr.saveAreaDeptTree(car);

				// 在树表里存油机管理节点
				AreaDeptTree oil = new AreaDeptTree();
				oil.setParentNodeId(factory.getNodeId());
				oil.setNodeName("油机管理");
				oil.setNodeType(AreaDeptTreeConstants.NODE_TYPE_OIL);

				oil.setAreaId(areaId);
				oil.setInterfaceHeadId(partnerDept.getInterfaceHeadId());
				oil.setPartnerid(partnerDept.getId());
				// 隐藏油机管理，当新增油机时isShow设置为0，可正常显示
				oil.setIsShow("1");

				areaDeptTreeMgr.saveAreaDeptTree(oil);

				factory.setInterfaceHeadId(partnerDept.getInterfaceHeadId());
				factory.setPartnerid(partnerDept.getId());
				areaDeptTreeMgr.saveAreaDeptTree(factory);
			}

		} else {
			IEvaTreeMgr evaTreeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
			partnerDeptMgr.savePartnerDept(partnerDept);
			EvaTree evaTree = evaTreeMgr.getNodeByRemark(partnerDept
					.getDeptMagId());
			evaTree.setNodeName(partnerDept.getName());
			evaTreeMgr.saveTreeNode(evaTree);
			ITawSystemDeptManager flushmgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManagerFlush");
			TawSystemDept tawSystemDept = flushmgr.getDeptinfobylinkid(
					partnerDept.getDeptMagId(), "0");
			/**
			 * 合作伙伴中没有作业计划，屏蔽之 modify by 陈元蜀 2012-09-04 begin Map workplanMap =
			 * tawWorkplanCacheBean.getWorkplanUser(); Map deptMap = (Map)
			 * workplanMap.get("deptMap");
			 * tawSystemDept.setDeptName(partnerDept.getName());
			 * deptMap.put(tawSystemDept.getLinkid(), partnerDept.getName());
			 * modify by 陈元蜀 2012-09-04 end
			 */
			flushmgr.saveTawSystemDept(tawSystemDept);
		}
		this.defaultParentNodeId = parentNodeId;
		request.setAttribute("refreshTree", "1");
		request.setAttribute("operType", "save");
		if (isNew) {
			String treeNodeIdFresh = partnerDept.getTreeNodeId();
			treeNodeIdFresh = treeNodeIdFresh.substring(0, 5);
			request.setAttribute("treeNodeId", treeNodeIdFresh);
			request.setAttribute("actionDo", "partnerDepts");
			return mapping.findForward("refreshSelf");
		} else {
			String treeNodeIdFresh = partnerDept.getTreeNodeId();
			treeNodeIdFresh = treeNodeIdFresh.substring(0, 5);
			request.setAttribute("treeNodeId", treeNodeIdFresh);
			request.setAttribute("actionDo", "partnerDepts");
			return mapping.findForward("refreshParent");
		}
	}

	/**
	 * 
	 * @Description ajax判断组织名称是否重复;
	 * @date May 7, 2013 3:58:34 PM
	 * @author Fengguangping fengguangping@boco.com.cn
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkDeptName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String deptName = request.getParameter("deptName");
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		boolean repeatFlag = partnerDeptMgr.getPartnerDeptByName(deptName);
		out.print(repeatFlag);
		return null;
	}

	/**
	 * 
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
		PartnerAppopsDeptService partnerDeptMgr = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
		// String[] ids = request.getParameterValues("checkbox11");
		String ids0 = request.getParameter("id");
		String[] ids = ids0.split(";");
		response.setCharacterEncoding("utf-8");
		Writer writer = response.getWriter();
		if (ids.length != 0) {
			// 先检查是否存在人员
			String hasUser = "";
			List hasUserDeptNames = partnerDeptMgr
					.checkIsHasUserBeforeDelDept(ids);// 返回拥有人员的部门名单
			if (hasUserDeptNames.size() > 0) {
				hasUser = hasUserDeptNames.toString();
				if (!"".equals(hasUser) && hasUser.length() < 50) {
					hasUser += "组织还有员工,需删除员工后才可以删除组织!";
				} else {
					hasUser = "您所选择的要删除的组织或者下属组织中还有员工,需删除员工后才可以删除组织!";
				}
				writer.write(new Gson()
						.toJson(new ImmutableMap.Builder<String, String>()
								.put("success", "true").put("info", hasUser)
								.build()));
				return null;
			} else {
				partnerDeptMgr.remove(ids);
				/*for (Object deptName : hasUserDeptNames) {
					PnrProcessCach.deptCompanyCach.remove(deptName);// 更新缓存
				}*/
				writer.write(new Gson()
						.toJson(new ImmutableMap.Builder<String, String>()
								.put("success", "true").put("info", "删除成功!")
								.build()));
				return null;
			}
		}
		return mapping.findForward("success");
	}

	/**
	 * ��ҳ��ʾ�����б�
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
				PartnerDeptConstants.PARTNERDEPT_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(
				GenericValidator.isBlankOrNull(request
						.getParameter(pageIndexName)) ? 0 : (Integer
						.parseInt(request.getParameter(pageIndexName)) - 1));
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");

		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));// 地市的nodeid
		String interfaceHeadId = StaticMethod.null2String(request
				.getParameter("interfaceHeadId"));// 厂商总公司id
		String notShowAll = StaticMethod.null2String(request
				.getParameter("notShowAll"));// 不查询总公司
		// 加载当前用户所属地市
		String userid = this.getUser(request).getUserid();
		List city = PartnerCityByUser.getCityByUser(userid);
		request.setAttribute("city", city);
		// 查询条件：地市
		String region = request.getParameter("city");
		// 查询条件：县区
		String county = request.getParameter("county");
		// 查询条件：合作伙伴
		String nameSearch = request.getParameter("nameSearch");

		String whereStr = " where 1=1";
		if (!nodeId.equals("")) {
			whereStr += " and treeNodeId in (select nodeId from AreaDeptTree tree where tree.parentNodeId = "
					+ nodeId + ")";
			request.setAttribute("parentNodeId", nodeId);
		} else if (this.defaultParentNodeId != null
				&& !this.defaultParentNodeId.equals("")) {
			whereStr += " and treeNodeId in (select nodeId from AreaDeptTree tree where tree.parentNodeId = "
					+ this.defaultParentNodeId + ")";
			request.setAttribute("parentNodeId", this.defaultParentNodeId);
			this.defaultParentNodeId = "";
		}
		// 组装查询条件
		PartnerDeptForm partnerDeptForm = (PartnerDeptForm) form;
		// if(request.getParameter("nameSearch")!=null&&!request.getParameter("nameSearch").equals("")){
		// whereStr += " and name like
		// '%"+request.getParameter("nameSearch")+"%'";
		// }
		if (request.getParameter("managerSearch") != null
				&& !request.getParameter("managerSearch").equals("")) {
			whereStr += " and manager like '%"
					+ request.getParameter("managerSearch") + "%'";
		}
		if (request.getParameter("phoneSearch") != null
				&& !request.getParameter("phoneSearch").equals("")) {
			whereStr += " and phone like '%"
					+ request.getParameter("phoneSearch") + "%'";
		}
		if (region != null && !region.equals("")) {
			whereStr += " and area_id = '" + region + "'";
		}

		if (county != null && !county.equals("")) {
			whereStr += " and city = '" + county + "'";
		}
		if (nameSearch != null && !nameSearch.equals("")) {
			whereStr += " and name = '" + nameSearch + "'";
		}

		if (interfaceHeadId != null && !interfaceHeadId.equals("")) {
			whereStr += " and interfaceHeadId = '" + interfaceHeadId + "'";
		}
		if ("1".equals(notShowAll)) {
			whereStr += " and id <> '" + interfaceHeadId + "'";
		}

		Map map = (Map) partnerDeptMgr.getPartnerDepts(pageIndex, pageSize,
				whereStr);
		List list = (List) map.get("result");
		request.setAttribute(PartnerDeptConstants.PARTNERDEPT_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);

		return mapping.findForward("list");
	}

	/*
	 * 新增合作伙伴
	 * 
	 * @param mapping @param form @param request @param response @return @throws
	 * Exception
	 */
	public ActionForward newExpert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("methodName", "detail");
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));// nodeId
		// 是地市的nodeId
		String id = StaticMethod.null2String(request.getParameter("id"));
		request.setAttribute("nodeId", nodeId);
		if (nodeId.length() >= 7) {
			PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
			PartnerDept partnerDept = partnerDeptMgr
					.getPartnerDeptByTreeNodeId(nodeId);
			request.setAttribute("proId1", partnerDept.getId());
		}
		if (!id.equals("")) {
			request.setAttribute("proId1", id);
		}
		return mapping.findForward("newExpert");
	}

	/**
	 * 跳转到省下属代维公司基本信息新增页面
	 */
	public ActionForward toCompanyBaseInfoForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));// nodeId
		// 是地市的nodeId
		if (!StringUtils.isEmpty(nodeId)) {
			AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
			AreaDeptTree areaDeptTree = areaDeptTreeMgr
					.getAreaDeptTreeByNodeId(nodeId);// 此对象是地市
			String areaId = areaDeptTree.getAreaId();
			String areaName = areaDeptTree.getNodeName();

			request.setAttribute("parentNodeId", nodeId);
			request.setAttribute("areaId", areaId);
		}

		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
		// 定义取出所有厂家的集合，使用公共字典取得数据
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		// ArrayList tawSystemDictTypeList = mgr.getDictSonsByDictid(String
		// .valueOf(roleIdList.getBigType()));
		//
		// request.setAttribute("tawSystemDictType", tawSystemDictTypeList);
		/**
		 * 定义取出专业集合，使用公共字典取得数据
		 */
		ArrayList specialtyList = mgr.getDictSonsByDictid("11225");
		request.setAttribute("specialtyList", specialtyList);
		IPnrPartnerAppOpsDeptForm partnerDeptForm = (IPnrPartnerAppOpsDeptForm) form;
		request.setAttribute("isEdit", "add");
		updateFormBean(mapping, request, partnerDeptForm);
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		request.setAttribute("province", pnrBaseAreaIdList.getRootAreaId()); // 省份：黑龙江
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) getBean("pnrDeptAndUserConfigSetList");

		request.setAttribute("isPartner", isPartner);
		return mapping.findForward("companyBaseInfoForm");
	}

	/**
	 * 跳转到市公司基本信息新增页面
	 */
	public ActionForward toCompanyBaseInfoSubForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		PartnerAppopsDeptService partnerDeptMgr = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		List<TawSystemArea> areaList = partnerDeptMgr
				.getAreas(pnrBaseAreaIdList.getRootAreaId());
		String id = request.getParameter("id"); // 省下属代维公司ID
		IPnrPartnerAppOpsDept parentPnrDept = partnerDeptMgr.getPartnerDept(id);// 获取父合作伙伴
		request.setAttribute("parentPnrDept", parentPnrDept);
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));// nodeId
		// 是地市的nodeId
		if (!StringUtils.isEmpty(nodeId)) {
			AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
			AreaDeptTree areaDeptTree = areaDeptTreeMgr
					.getAreaDeptTreeByNodeId(nodeId);// 此对象是地市
			String areaId = areaDeptTree.getAreaId();
			String areaName = areaDeptTree.getNodeName();

			request.setAttribute("parentNodeId", nodeId);
			request.setAttribute("areaId", areaId);
		}
		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
		// 定义取出所有厂家的集合，使用公共字典取得数据
		// ArrayList tawSystemDictTypeList =
		// mgr.getDictSonsByDictid(String.valueOf(roleIdList.getBigType()));
		// //单位类型继承自父级省下属公司
		// ArrayList bigTypeArrayList = new ArrayList();
		// if (parentPnrDept.getBigType() != null
		// && !"".equals(parentPnrDept.getBigType())) {
		// String[] bigType = parentPnrDept.getBigType().split(",");
		// for (int i = 0; i < bigType.length; i++) {
		// bigTypeArrayList.add(bigType[i]);
		// }
		// for (int i = 0; i < tawSystemDictTypeList.size(); i++) {
		// TawSystemDictType tawSystemDictType = (TawSystemDictType)
		// tawSystemDictTypeList
		// .get(i);
		// if (bigTypeArrayList.contains(tawSystemDictType.getDictId())) {
		// tawSystemDictType.setDictRemark("isTrue");
		// }
		// }
		// }
		// request.setAttribute("tawSystemDictType", tawSystemDictTypeList);
		/**
		 * 定义取出专业集合，使用公共字典取得数据
		 */
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		ArrayList specialtyList = mgr.getDictSonsByDictid("11225");
		ArrayList bigTypeArrayList = new ArrayList();
		if (parentPnrDept.getBigType() != null
				&& !"".equals(parentPnrDept.getBigType())) {
			String[] bigType = parentPnrDept.getBigType().split(",");
			for (int i = 0; i < bigType.length; i++) {
				bigTypeArrayList.add(bigType[i]);
			}
			for (int i = 0; i < specialtyList.size(); i++) {
				TawSystemDictType tawSystemDictType = (TawSystemDictType) specialtyList
						.get(i);
				if (bigTypeArrayList.contains(tawSystemDictType.getDictId())) {
					tawSystemDictType.setDictRemark("isTrue");
				}
			}
		}
		request.setAttribute("specialtyList", specialtyList);
		IPnrPartnerAppOpsDeptForm partnerDeptForm = (IPnrPartnerAppOpsDeptForm) form;

		String parentDeptname = StaticMethod.nullObject2String(request.getParameter("parentDeptname"));
		parentDeptname = new String(parentDeptname.getBytes("ISO-8859-1"), "UTF-8");

		// 2代表子公司,即省下属下面的的公司的子公司
		request.setAttribute("deptLevel", "2");
		request.setAttribute("areaList", areaList);
		request.setAttribute("isEdit", "add");
		request.setAttribute("isPartner", isPartner);
		request.setAttribute("parentDeptname", parentDeptname);
		request.setAttribute("id", id);
		updateFormBean(mapping, request, partnerDeptForm);
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) getBean("pnrDeptAndUserConfigSetList");
		request.setAttribute("province", pnrBaseAreaIdList.getRootAreaId()); // 省份：黑龙江
		return mapping.findForward("companyBaseInfoFormSub");
	}

	/**
	 * 跳转到县级公司基本信息新增页面
	 */
	public ActionForward toCompanyBaseInfoGrandsonForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		PartnerAppopsDeptService partnerDeptMgr = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
		String id = request.getParameter("id"); // 所属市级合作伙伴ID
		IPnrPartnerAppOpsDept parentPnrDept = partnerDeptMgr.getPartnerDept(id);// 获取市级合作伙伴
		request.setAttribute("parentPnrDept", parentPnrDept);
		String parent_id = request.getParameter("parent_id");// 所属省下属合作伙伴ID
		String countryAreaId = request.getParameter("areaid");// 上级部门的区域id;
		List<TawSystemArea> areaList = partnerDeptMgr.getAreas(countryAreaId);
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));// nodeId
																// 是地市的nodeId
		if (!StringUtils.isEmpty(nodeId)) {
			AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
			AreaDeptTree areaDeptTree = areaDeptTreeMgr
					.getAreaDeptTreeByNodeId(nodeId);// 此对象是地市
			String areaId = areaDeptTree.getAreaId();
			request.setAttribute("parentNodeId", nodeId);
			request.setAttribute("areaId", areaId);
		}
		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
		// 定义取出所有厂家的集合，使用公共字典取得数据
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		// ArrayList tawSystemDictTypeList =
		// mgr.getDictSonsByDictid(String.valueOf(roleIdList.getBigType()));
		// //单位类型继承自父级省下属公司
		// ArrayList bigTypeArrayList = new ArrayList();
		// if (parentPnrDept.getBigType() != null
		// && !"".equals(parentPnrDept.getBigType())) {
		// String[] bigType = parentPnrDept.getBigType().split(",");
		// for (int i = 0; i < bigType.length; i++) {
		// bigTypeArrayList.add(bigType[i]);
		// }
		// for (int i = 0; i < tawSystemDictTypeList.size(); i++) {
		// TawSystemDictType tawSystemDictType = (TawSystemDictType)
		// tawSystemDictTypeList
		// .get(i);
		// if (bigTypeArrayList.contains(tawSystemDictType.getDictId())) {
		// tawSystemDictType.setDictRemark("isTrue");
		// }
		// }
		// }
		// request.setAttribute("tawSystemDictType", tawSystemDictTypeList);
		/**
		 * 定义取出专业集合，使用公共字典取得数据
		 */
		ArrayList specialtyList = mgr.getDictSonsByDictid("11225");
		ArrayList bigTypeArrayList = new ArrayList();
		if (parentPnrDept.getBigType() != null
				&& !"".equals(parentPnrDept.getBigType())) {
			String[] bigType = parentPnrDept.getBigType().split(",");
			for (int i = 0; i < bigType.length; i++) {
				bigTypeArrayList.add(bigType[i]);
			}
			for (int i = 0; i < specialtyList.size(); i++) {
				TawSystemDictType tawSystemDictType = (TawSystemDictType) specialtyList
						.get(i);
				if (bigTypeArrayList.contains(tawSystemDictType.getDictId())) {
					tawSystemDictType.setDictRemark("isTrue");
				}
			}
		}
		request.setAttribute("specialtyList", specialtyList);
		IPnrPartnerAppOpsDeptForm partnerDeptForm = (IPnrPartnerAppOpsDeptForm) form;

		String parentDeptname = request.getParameter("parentDeptname");
		parentDeptname = new String(parentDeptname.getBytes("ISO-8859-1"), "UTF-8");

		// 2代表子公司,即省下属下面的的公司的子公司
		request.setAttribute("deptLevel", "3");
		request.setAttribute("isEdit", "add");
		request.setAttribute("parentDeptname", parentDeptname);
		request.setAttribute("id", id);
		request.setAttribute("parent_id", parent_id);
		updateFormBean(mapping, request, partnerDeptForm);
		
		request.setAttribute("province", parentPnrDept.getAreaId());
		String provinceName = parentPnrDept.getAreaName();
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) getBean("pnrDeptAndUserConfigSetList");
		request.setAttribute("provinceName", provinceName);
		request.setAttribute("areaList", areaList);
		request.setAttribute("isPartner", isPartner);
		return mapping.findForward("partnerDeptGrandsonForm");
	}

	/**
	 * 跳转到代维小组基本信息新增页面
	 */
	public ActionForward toCompanyBaseInfoGrandsonGroupForm(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		PartnerAppopsDeptService partnerDeptMgr = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
		String id = request.getParameter("id"); // 所属市级合作伙伴ID
		IPnrPartnerAppOpsDept parentPnrDept = partnerDeptMgr.getPartnerDept(id);// 获取市级合作伙伴
		request.setAttribute("parentPnrDept", parentPnrDept);
		String parentAreaId = parentPnrDept.getAreaId();
		String parentAreaName = parentPnrDept.getAreaName();
		request.setAttribute("parentAreaName", parentAreaName);
		request.setAttribute("parentAreaId", parentAreaId);
		String parent_id = request.getParameter("parent_id");// 所属省下属合作伙伴ID
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));// nodeId
																// 是地市的nodeId
		if (!StringUtils.isEmpty(nodeId)) {
			AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
			AreaDeptTree areaDeptTree = areaDeptTreeMgr
					.getAreaDeptTreeByNodeId(nodeId);// 此对象是地市
			String areaId = areaDeptTree.getAreaId();
			request.setAttribute("parentNodeId", nodeId);
			request.setAttribute("areaId", areaId);
		}
		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
		// 定义取出所有厂家的集合，使用公共字典取得数据
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		// ArrayList tawSystemDictTypeList =
		// mgr.getDictSonsByDictid(String.valueOf(roleIdList.getBigType()));
		// //单位类型继承自父级省下属公司
		// ArrayList bigTypeArrayList = new ArrayList();
		// if (parentPnrDept.getBigType() != null
		// && !"".equals(parentPnrDept.getBigType())) {
		// String[] bigType = parentPnrDept.getBigType().split(",");
		// for (int i = 0; i < bigType.length; i++) {
		// bigTypeArrayList.add(bigType[i]);
		// }
		// for (int i = 0; i < tawSystemDictTypeList.size(); i++) {
		// TawSystemDictType tawSystemDictType = (TawSystemDictType)
		// tawSystemDictTypeList
		// .get(i);
		// if (bigTypeArrayList.contains(tawSystemDictType.getDictId())) {
		// tawSystemDictType.setDictRemark("isTrue");
		// }
		// }
		// }
		// request.setAttribute("tawSystemDictType", tawSystemDictTypeList);
		/**
		 * 定义取出专业集合，使用公共字典取得数据
		 */
		ArrayList specialtyList = mgr.getDictSonsByDictid("11225");
		ArrayList bigTypeArrayList = new ArrayList();
		if (parentPnrDept.getBigType() != null
				&& !"".equals(parentPnrDept.getBigType())) {
			String[] bigType = parentPnrDept.getBigType().split(",");
			for (int i = 0; i < bigType.length; i++) {
				bigTypeArrayList.add(bigType[i]);
			}
			for (int i = 0; i < specialtyList.size(); i++) {
				TawSystemDictType tawSystemDictType = (TawSystemDictType) specialtyList
						.get(i);
				if (bigTypeArrayList.contains(tawSystemDictType.getDictId())) {
					tawSystemDictType.setDictRemark("isTrue");
				}
			}
		}
		request.setAttribute("specialtyList", specialtyList);
		IPnrPartnerAppOpsDeptForm partnerDeptForm = (IPnrPartnerAppOpsDeptForm) form;

		String parentDeptname = StaticMethod.null2String(request.getParameter("parentDeptname"));
		parentDeptname =  new String(parentDeptname.getBytes("ISO-8859-1"), "UTF-8");
	

		// 2代表子公司,即省下属下面的的公司的子公司
		request.setAttribute("deptLevel", "4");
		request.setAttribute("isEdit", "add");
		request.setAttribute("parentDeptname", parentDeptname);
		request.setAttribute("id", id);
		request.setAttribute("parent_id", parent_id);
		request.setAttribute("isPartner", isPartner);
		updateFormBean(mapping, request, partnerDeptForm);
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		request.setAttribute("province", pnrBaseAreaIdList.getRootAreaId()); // 省份：黑龙江
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) getBean("pnrDeptAndUserConfigSetList");
		request.setAttribute("qualifyConfig", setList.getQualificationConfig()); // 是否需要添加资质模块
		return mapping.findForward("partnerDeptGrandsonGroupForm");
	}

	/**
	 * 保存考核树
	 */
	private void saveEvaTree(String newlinkId, PartnerDept partnerDept) {
		IEvaTreeMgr evaTreeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		EvaTree evaTree = new EvaTree();
		String newNodeId = evaTreeMgr.getMaxNodeId("1");
		evaTree.setNodeId(newNodeId);
		evaTree.setNodeType(EvaConstants.NODETYPE_TEMPLATETYPE);
		evaTree.setLeaf(EvaConstants.NODE_LEAF);
		evaTree.setParentNodeId("1");
		evaTree.setNodeRemark(newlinkId);
		evaTree.setNodeName(partnerDept.getName());
		evaTreeMgr.saveTreeNode(evaTree);
	}

	/**
	 * 保存系统部门信息 flag：是否是总代维公司
	 */
	private String saveTawSystemDept(HttpServletRequest request, boolean flag,
			IPnrPartnerAppOpsDept partnerDept, String dept_name) throws Exception {
		ITawSystemDeptManager mgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		TawSystemDept sysdept = new TawSystemDept();
		TawSystemDept tawSystemDept = new TawSystemDept();
		String newlinkId = "";
		String time = StaticMethod.getLocalString();
		// 将该部门在缓存中新增
		// TawWorkplanCacheBean tawWorkplanCacheBean = (TawWorkplanCacheBean)
		// ApplicationContextHolder
		// .getInstance().getBean("TawWorkplanCacheBean");modify by 陈元蜀
		// 2012-09-04

		ITawSystemDeptManager flushmgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManagerFlush");
		Boolean Exist = flushmgr.getDeptnameIsExist(dept_name, "0");
		if (Exist) {
			TawSystemDept tawSystemDept1 = flushmgr.getDeptinfoBydeptname(
					dept_name, "0");
			tawSystemDept1.setDeptName(partnerDept.getName());
			flushmgr.saveTawSystemDept(tawSystemDept1);
			return newlinkId;
		}
		if (flag) {// 总代维公司
			RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
			sysdept = deptbo.getDeptinfobydeptid(
					String.valueOf(roleIdList.getParDeptId()), "0");
			tawSystemDept.setParentDeptid(String.valueOf(roleIdList
					.getParDeptId()));
		} else {// 子代维公司
			// partnerDeptMgr 成当前部门编码
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
		/**
		 * 合作伙伴中没有作业计划，屏蔽之 modify by 陈元蜀 2012-09-04 begin Map workplanMap =
		 * tawWorkplanCacheBean.getWorkplanUser(); Map deptMap = (Map)
		 * workplanMap.get("deptMap"); deptMap.put(newlinkId,
		 * tawSystemDept.getDeptName()); modify by 陈元蜀 2012-09-04 end
		 */
		System.out.println("获取的deptid为" + newlinkId);
//		flushmgr.saveTawSystemDept(tawSystemDept);
		return newlinkId;
	}

	/**
	 * 保存代维公司基本信息
	 */
	public ActionForward saveCompanyBaseInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		try {
			PartnerAppopsDeptService partnerDeptMgr = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");

			IPnrPartnerAppOpsDeptForm partnerDeptForm = (IPnrPartnerAppOpsDeptForm) form;
			IPnrPartnerAppOpsDept partnerDept = (IPnrPartnerAppOpsDept) convert(partnerDeptForm);
			partnerDept.setDeleted("0");
			// 合作伙伴所属地市（地域）
			boolean flag = true; // true代表总代维公司
			partnerDept.setId(null);
			IPnrPartnerAppOpsDept pd = new IPnrPartnerAppOpsDept();
			BeanUtils.copyProperties(pd, partnerDept);
			pd.setIsPartner(isPartner);
			TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			String provincesId = sessionForm.getRootAreaId();
			String provinceName = sessionForm.getRootAreaName();
			pd.setAreaId(provincesId);// 省区域id
			pd.setAreaName(provinceName);// 省名称
			// 保存对应的系统部门信息
			String newlinkId ="";// this.saveTawSystemDept(request, flag, pd,
//					pd.getName());

			pd.setDeptMagId(newlinkId);
			pd.setCreateTime(StaticMethod.getLocalTime());// 增加新增时间
			// 保存总代维公司的ID
			/**
			 * 
			 */
			CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
					.getInstance().getBean("commonSpringJdbcService");
			// String sql1 =
			// "SELECT max(cast(substr(organizationno,4,(length(organizationno)-1)) AS DECIMAL)) as countNo from pnr_dept where  deptlevel='1'";
			// sql1删除最大值编码的部门，那么下个编码会和这个编码重复,综合考虑情况下选择下个方式 fengguangping
			// 2013-04-25
			String sql = "SELECT count(id) as countNo from pnr_act_appops_dept where  deptlevel='1' ";// 物理删除会导致编码出问题
			List<ListOrderedMap> countList = jdbcService.queryForList(sql);
			Map map = new HashMap();
			int organization = 0;
			if (countList != null) {
				map = countList.get(0);
				String maxNoString = StaticMethod.nullObject2String(map
						.get("countNo"));
				if ("".equals(maxNoString)) {
					maxNoString = "0";
				}
				organization = Integer.parseInt(maxNoString);
			}
			String organizationNo = "";
			String no = String.valueOf(organization + 1);
			if (no.length() == 1) {
				organizationNo = "DW-00" + no;
			} else if (no.length() == 2) {
				organizationNo = "DW-0" + no;
			} else if (no.length() == 3) {
				organizationNo = "DW-" + no;
			}
			pd.setOrganizationNo(organizationNo);
			pd = partnerDeptMgr.updateStatisticInfo(pd);
//			PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) getBean("pnrDeptAndUserConfigSetList");
			String qualifyConfig ="";// StaticMethod.null2String(setList
//					.getQualificationConfig());
			if ("1".equals(qualifyConfig)) {
				QualificationUtils qualificationUtils = new QualificationUtils();
				List<PnrQualification> qualifyList = qualificationUtils
						.newQualificationObject(request);
				partnerDeptMgr.saveDeptAndRelatedQualification(pd, qualifyList,
						"1");
			} else {
				partnerDeptMgr.savePartnerDept(pd);
				pd.setInterfaceHeadId(pd.getId());
				partnerDeptMgr.savePartnerDept(pd);
			}
//			PnrProcessCach.deptCompanyCach.put(pd.getName(), pd);// 更新缓存
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			return mapping.findForward("fail");
		}
		return mapping.findForward("success");
	}

	/**
	 * 保存子代维公司基本信息
	 */
	public ActionForward saveCompanyBaseInfoSub(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String isPartnerTemp = request.getParameter("isPartner");
			int isPartner = -1;
			try {
				isPartner = Integer.parseInt(isPartnerTemp);
			} catch (NumberFormatException e) {
			}
			// 当前部门组织编码
			PartnerAppopsDeptService partnerDeptMgr = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
			String parentid = request.getParameter("parentid");
			// String deptLevel = request.getParameter("deptLevel");
			Map<String, String> cityNameMap = new HashMap<String, String>();
			String sel_city = request.getParameter("sel_city");
			String[] selcity = sel_city.split("\\|");
			for (int i = 0; i < selcity.length; i++) {
				String[] city = selcity[i].split("#");
				cityNameMap.put(city[0], city[1]);
			}

			Set keys = cityNameMap.keySet();
			if (keys != null) {
				Iterator it = (Iterator) keys.iterator();
				while (((Iterator) it).hasNext()) {
					String key = (String) it.next();
					String value = cityNameMap.get(key);
	
					// 父类表单对象
					IPnrPartnerAppOpsDeptForm partnerDeptForm = (IPnrPartnerAppOpsDeptForm) form;
					// 把表单转化成对像
					IPnrPartnerAppOpsDept partnerDept = (IPnrPartnerAppOpsDept) convert(partnerDeptForm);
					partnerDept.setDeleted("0");
					boolean flag = false; // true代表总代维公司
					partnerDept.setId(null);
					IPnrPartnerAppOpsDept pd = new IPnrPartnerAppOpsDept();
					// copy 对像
					BeanUtils.copyProperties(pd, partnerDept);
					pd.setIsPartner(isPartner);
					pd.setName(partnerDeptForm.getName() + "-" + key);
					pd.setManager(partnerDeptForm.getManager());
					pd.setInterfaceHeadId(parentid);
					// 后加的级别 cb004
					// pd.setDeptLevel(deptLevel);
					pd.setAreaId(value);
					pd.setAreaName(key);
				
					String newlinkId =""; //this.saveTawSystemDept(request, flag,
//							pd, pd.getName());
				
					pd.setDeptMagId(newlinkId);
					pd.setCreateTime(StaticMethod.getLocalTime());// 增加新增时间
					/**
					 * 
					 */
					CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
							.getInstance().getBean("commonSpringJdbcService");
					String sql = "select b.organizationno as organizationno,a.countid as countNo from "
							+ " (SELECT count(id) as countid from pnr_act_appops_dept "
							+ " where id!=interface_head_id "
							+ " and interface_head_id='"
							+ parentid
							+ "'"
							+ " and Length(organizationno)=10 )a,"
							+ " (select organizationno from  pnr_act_appops_dept where id='"
							+ parentid + "')b";
					List<ListOrderedMap> countList = jdbcService
							.queryForList(sql);
					Map map = new HashMap();
					map = countList.get(0);
					String organizationNo = "";
					int organization = Integer.parseInt(map.get("countNo")
							.toString());
					String parentNo = map.get("organizationno").toString();
					String no = String.valueOf(organization + 1);
					if (no.length() == 1) {
						organizationNo = parentNo + "-00" + no;
					} else if (no.length() == 2) {
						organizationNo = parentNo + "-0" + no;
					} else if (no.length() == 3) {
						organizationNo = parentNo + "-" + no;
					}
					pd.setOrganizationNo(organizationNo);
					pd = partnerDeptMgr.updateStatisticInfo(pd);
					PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) getBean("pnrDeptAndUserConfigSetList");
					String qualifyConfig = StaticMethod.null2String(setList
							.getQualificationConfig());
					if ("1".equals(qualifyConfig)) {
						QualificationUtils qualificationUtils = new QualificationUtils();
						List<PnrQualification> qualifyList = qualificationUtils
								.newQualificationObject(request);
						partnerDeptMgr.saveDeptAndRelatedQualification(pd,
								qualifyList, "2");
					} else {
						partnerDeptMgr.savePartnerDept(pd);
					}
//					PnrProcessCach.deptCompanyCach.put(pd.getName(), pd);// 更新缓存
				}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			return mapping.findForward("fail");
		}
		return mapping.findForward("success");
	}

	/**
	 * 保存子子代维公司基本信息
	 */
	public ActionForward saveCompanyBaseInfoGrandson(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String isPartnerTemp = request.getParameter("isPartner");
			int isPartner = -1;
			try {
				isPartner = Integer.parseInt(isPartnerTemp);
			} catch (NumberFormatException e) {
			}
			PartnerAppopsDeptService partnerDeptMgr = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
			String parentid = request.getParameter("parentid");
			String parentDeptname = new String(request.getParameter(
					"parentDeptname").getBytes("ISO-8859-1"), "UTF-8");
			
			IPnrPartnerAppOpsDeptForm partnerDeptForm = (IPnrPartnerAppOpsDeptForm) form;

			IPnrPartnerAppOpsDept partnerDept = (IPnrPartnerAppOpsDept) convert(partnerDeptForm);
			partnerDept.setDeleted("0");

			String sel_city = request.getParameter("sel_city");
			String[] selcity = sel_city.split("\\|");
			String[] areaNameArray = new String[selcity.length];
			String[] areaIdArray = new String[selcity.length];
			for (int i = 0; i < selcity.length; i++) {
				areaNameArray[i] = selcity[i].split("#")[0];
				areaIdArray[i] = selcity[i].split("#")[1];
			}

			boolean flag = false; // true代表总代维公司
			// // 生成规则为：生成1个总代维公司(第一次循环) + 与选择地域个数数量相同的子代维公司(剩余循环)
			for (int i = 0; i < areaNameArray.length; i++) {
				partnerDept.setId(null);
				IPnrPartnerAppOpsDept pd = new IPnrPartnerAppOpsDept();
				BeanUtils.copyProperties(pd, partnerDept);
				pd.setIsPartner(isPartner);
				pd.setAreaId(areaIdArray[i]);
				pd.setName(pd.getName() + "-" + areaNameArray[i]);
				pd.setManager(partnerDeptForm.getManager());
				pd.setAreaName(areaNameArray[i]);
				pd.setInterfaceHeadId(parentid);
				String newlinkId ="";// this.saveTawSystemDept(request, flag, pd,
//						pd.getName());
				// this.saveEvaTree(newlinkId, pd);
				pd.setDeptMagId(newlinkId);
				pd.setCreateTime(StaticMethod.getLocalTime());// 增加新增时间
				/**
				 * 
				 */
				CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
						.getInstance().getBean("commonSpringJdbcService");
				String sql = "select b.organizationno as organizationno,a.countid as countNo from "
						+ " (SELECT count(id) as countid from pnr_act_appops_dept "
						+ " where   id!=interface_head_id "
						+ " and interface_head_id='"
						+ parentid
						+ "'"
						+ " and Length(organizationno)=14 )a,"
						+ " (select organizationno from  pnr_act_appops_dept where id='"
						+ parentid + "')b";
				List<ListOrderedMap> countList = jdbcService.queryForList(sql);
				Map map = new HashMap();
				map = countList.get(0);
				String organizationNo = "";
				int organization = Integer.parseInt(map.get("countNo")
						.toString());
				String parentNo = map.get("organizationno").toString();
				String no = String.valueOf(organization + 1);
				if (no.length() == 1) {
					organizationNo = parentNo + "-00" + no;
				} else if (no.length() == 2) {
					organizationNo = parentNo + "-0" + no;
				} else if (no.length() == 3) {
					organizationNo = parentNo + "-" + no;
				}
				pd.setOrganizationNo(organizationNo);
				pd = partnerDeptMgr.updateStatisticInfo(pd);
				PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) getBean("pnrDeptAndUserConfigSetList");
				String qualifyConfig = StaticMethod.null2String(setList
						.getQualificationConfig());
				if ("1".equals(qualifyConfig)) {
					QualificationUtils qualificationUtils = new QualificationUtils();
					List<PnrQualification> qualifyList = qualificationUtils
							.newQualificationObject(request);
					partnerDeptMgr.saveDeptAndRelatedQualification(pd,
							qualifyList, "3");
				} else {
					partnerDeptMgr.savePartnerDept(pd);
				}
//				PnrProcessCach.deptCompanyCach.put(pd.getName(), pd);// 更新缓存
				// 循环一次后变为子代维公司
				flag = false;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			return mapping.findForward("fail");
		}
		return mapping.findForward("success");
	}

	/**
	 * 保存代维小组基本信息
	 */
	public ActionForward saveCompanyBaseInfoGrandsonGroup(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String isPartnerTemp = request.getParameter("isPartner");
			int isPartner = -1;
			try {
				isPartner = Integer.parseInt(isPartnerTemp);
			} catch (NumberFormatException e) {
			}
			PartnerAppopsDeptService partnerDeptMgr = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
			String parentid = request.getParameter("parentid");
			String parentAreaId = request.getParameter("parentAreaId");
			String parentAreaName = request.getParameter("parentAreaName");
			String parentDeptname = new String(request.getParameter(
					"parentDeptname").getBytes("ISO-8859-1"), "UTF-8");
			
			IPnrPartnerAppOpsDeptForm partnerDeptForm = (IPnrPartnerAppOpsDeptForm) form;

			IPnrPartnerAppOpsDept partnerDept = (IPnrPartnerAppOpsDept) convert(partnerDeptForm);
			partnerDept.setDeleted("0");
			partnerDept.setIfCompany("no");
			partnerDept.setDeptLevel("4");

			CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
					.getInstance().getBean("commonSpringJdbcService");
			boolean flag = false; // true代表总代维公司
			partnerDept.setId(null);
			IPnrPartnerAppOpsDept pd = new IPnrPartnerAppOpsDept();
			BeanUtils.copyProperties(pd, partnerDept);
			pd.setAreaId(parentAreaId);
			pd.setName(pd.getName() + "-" + parentAreaName);
			pd.setManager(partnerDeptForm.getManager());
			pd.setAreaName(parentAreaName);
			pd.setInterfaceHeadId(parentid);
			pd.setIsPartner(isPartner);
			String newlinkId = "";//this.saveTawSystemDept(request, flag, pd,
//					pd.getName());
			pd.setDeptMagId(newlinkId);
			pd.setCreateTime(StaticMethod.getLocalTime());// 增加新增时间
			/**
			 * 
			 */
			String sql = "select b.organizationno as organizationno,a.countid as countNo from "
					+ " (SELECT count(id) as countid from pnr_act_appops_dept "
					+ " where  id!=interface_head_id "
					+ " and interface_head_id='"
					+ parentid
					+ "'"
					+ " and Length(organizationno)=18 )a,"
					+ " (select organizationno from  pnr_act_appops_dept where id='"
					+ parentid + "')b";
			List<ListOrderedMap> countList = jdbcService.queryForList(sql);
			Map map = new HashMap();
			map = countList.get(0);
			String organizationNo = "";
			int organization = Integer.parseInt(map.get("countNo").toString());
			String parentNo = map.get("organizationno").toString();
			String no = String.valueOf(organization + 1);
			if (no.length() == 1) {
				organizationNo = parentNo + "-00" + no;
			} else if (no.length() == 2) {
				organizationNo = parentNo + "-0" + no;
			} else if (no.length() == 3) {
				organizationNo = parentNo + "-" + no;
			}
			pd.setOrganizationNo(organizationNo);
			pd = partnerDeptMgr.updateStatisticInfo(pd);
			PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) getBean("pnrDeptAndUserConfigSetList");
			String qualifyConfig = StaticMethod.null2String(setList
					.getQualificationConfig());
			if ("1".equals(qualifyConfig)) {
				QualificationUtils qualificationUtils = new QualificationUtils();
				List<PnrQualification> qualifyList = qualificationUtils
						.newQualificationObject(request);
				partnerDeptMgr.saveDeptAndRelatedQualification(pd, qualifyList,
						"4");
			} else {
				partnerDeptMgr.savePartnerDept(pd);
			}
//			PnrProcessCach.deptCompanyCach.put(pd.getName(), pd);// 更新缓存
			// 循环一次后变为子代维公司
			flag = false;
		} catch (RuntimeException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			return mapping.findForward("fail");
		}
		return mapping.findForward("success");
	}

	/**
	 * 代维组织查询入口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchCompanyInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		PartnerAppopsDeptService partnerAppopsDeptService = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		request.setAttribute("isPartner", isPartner);
		String deptid = sessionForm.getDeptid();
		String userid = this.getUserId(request);
		String areaid = "";
		
		List<IPnrPartnerAppOpsDept> list0 = new ArrayList<IPnrPartnerAppOpsDept>();
		
		if (!"admin".equals(sessionForm.getUserid())) {
			StringBuffer sqlBuffer  = new StringBuffer();
			sqlBuffer.append(" and deptMagId='").append(deptid).append("'");
			sqlBuffer.append(" and substr(deptMagId,1,3)='");
			sqlBuffer.append(com.boco.eoms.partner.process.util.CommonUtils.startDeptMagId).append("'");
			if (isPartner != -1) {
				sqlBuffer.append(" and isPartner=").append(isPartner);
			} 
			list0 = partnerAppopsDeptService.getPartnerDepts(sqlBuffer.toString());
			if (list0 != null && list0.size() > 0) {// 表示人员是代维管理人员
				if (deptid.length() == PartnerPrivUtils.getProvinceDeptLength()) {// 进入省公司list页面
					return this.findCompanyBaseInfo(mapping, form, request,
							response);
				} else if (deptid.length() == PartnerPrivUtils
						.getCityDeptLength()) {// 进入地市公司list页面
					return this.findSubCompanyBaseInfo(mapping, form, request,
							response);
				} else if (deptid.length() == PartnerPrivUtils
						.getCountyDeptLength()) {// 进入县份公司list页面
					return this.findGrandsonCompanyBaseInfo(mapping, form,
							request, response);
				} else if (deptid.length() == PartnerPrivUtils
						.getGroupDeptLength()) {// 进入代维小组list页面
					return this.findGroupGrandsonCompanyBaseInfo(mapping, form,
							request, response);
				}
			} else {// 移动管理人员
				ITawSystemDeptManager deptManager = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
				TawSystemDept dept1 = deptManager.getDeptinfobydeptid(deptid,
						"0");
				if (dept1 != null) {
					areaid = StaticMethod.null2String(dept1.getAreaid());
					if (areaid.length() == PartnerPrivUtils.AreaId_length_Province) {// 进入省公司list页面
						return this.findCompanyBaseInfo(mapping, form, request,
								response);
					} else if (areaid.length() == PartnerPrivUtils.AreaId_length_City) {// 进入地市公司list页面
						return this.findSubCompanyBaseInfo(mapping, form,
								request, response);
					} else if (areaid.length() == PartnerPrivUtils.AreaId_length_County) {// 进入县份公司list页面
						return this.findGrandsonCompanyBaseInfo(mapping, form,
								request, response);
					}
				}
			}
		} else {
			return this.findCompanyBaseInfo(mapping, form, request, response);
		}
		return mapping.findForward("fail");
	}

	/**
	 * 查询省子代维公司基本信息
	 */
	public ActionForward findCompanyBaseInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		PartnerAppopsDeptService partnerAppopsDeptService = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
		IPnrPartnerAppOpsDept dept = new IPnrPartnerAppOpsDept();
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PartnerDeptConstants.PARTNERDEPT_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(
				GenericValidator.isBlankOrNull(request
						.getParameter(pageIndexName)) ? 0 : (Integer
						.parseInt(request.getParameter(pageIndexName)) - 1));
		// 电话
		String phoneSearch = request.getParameter("phoneSearch");
		dept.setPhone(phoneSearch);
		// 联系人
		String contactorSearch = request.getParameter("contactorSearch");
		dept.setContactor(contactorSearch);
		// 合作伙伴名称
		String nameSearch = request.getParameter("nameSearch");
		dept.setName(nameSearch);
		// 组织编码
		String organizationNoSearch = request
				.getParameter("organizationNoSearch");
		dept.setOrganizationNo(organizationNoSearch);
		// Map map = (Map) partnerDeptMgr.getPartnerDepts(pageIndex, pageSize,
		// dept,deptid,hasRight);
		String deptLevel = "1";
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String deptid = sessionForm.getDeptid();
		String hasRight = "0";
		// 移动的用户才能有添加代维公司的权限
		String userid = this.getUserId(request);
		List<IPnrPartnerAppOpsDept> list0 = new ArrayList<IPnrPartnerAppOpsDept>();
		Map map1 = new HashMap();
		if (!"admin".equals(sessionForm.getUserid())) {
			StringBuffer sqlBuffer  = new StringBuffer();
			sqlBuffer.append(" and deptMagId='").append(deptid).append("'");
			sqlBuffer.append(" and substr(deptMagId,1,3)='");
			sqlBuffer.append(com.boco.eoms.partner.process.util.CommonUtils.startDeptMagId).append("'");
			if (isPartner != -1) {
				sqlBuffer.append(" and isPartner=").append(isPartner);
			} 
			
			list0 = partnerAppopsDeptService.getPartnerDepts(sqlBuffer.toString());
			
			if (list0 != null && list0.size() > 0) {// 表示人员是代维管理人员
				map1 = (Map) partnerAppopsDeptService.getPartnerDeptsByAreaidOrDeptid(
						pageIndex, pageSize, "", deptid, dept, deptLevel, null,
						isPartner);
			} else {// 移动管理人员
				ITawSystemDeptManager deptManager = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
				TawSystemDept dept1 = deptManager.getDeptinfobydeptid(deptid,
						"0");
				if (dept1 != null) {
					if(deptid.length()>5){
						hasRight = "0"; // 移动用户
					}else{
						hasRight = "1"; // 移动用户
					}
					request.setAttribute("hasRightForAdd", hasRight);
					String areaid = StaticMethod.null2String(dept1.getAreaid());
					map1 = (Map) partnerAppopsDeptService
							.getPartnerDeptsByAreaidOrDeptid(pageIndex,
									pageSize, areaid, "", dept, deptLevel,
									null, isPartner);
				}
			}
		} else {
			hasRight = "1"; // 有权限
			request.setAttribute("hasRightForAdd", hasRight);
			request.setAttribute("hasRightGoBack", "0");// 用于判断该用户是否可以返回到上一级，当deptLevel大于页面所在的级别时可以返回，小于时则没有权限返回
			map1 = (Map) partnerAppopsDeptService.getPartnerDeptsByAreaidOrDeptid(
					pageIndex, pageSize, "", "", dept, deptLevel, null,
					isPartner);
		}
		// Map map1 = (Map)
		// partnerDeptMgr.getPartnerDeptsByDeptLevel(pageIndex,pageSize, dept,
		// deptid, hasRight, deptLevel);
		// partnerDeptMgr.getPartnerDeptsByDeptLevel(curPage,
		// pageSize,whereStr);
		List list = (List) map1.get("result");
		request.setAttribute(PartnerDeptConstants.PARTNERDEPT_LIST, list);
		request.setAttribute("resultSize", map1.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("phoneSearch", phoneSearch);
		request.setAttribute("contactorSearch", contactorSearch);
		request.setAttribute("organizationNoSearch", organizationNoSearch);
		request.setAttribute("nameSearch", nameSearch);
		request.setAttribute("isPartner", isPartner);
		return mapping.findForward("companyBaseInfoList");
	}

	/**
	 * 查询地市子代维公司基本信息
	 */
	public ActionForward findSubCompanyBaseInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PartnerDeptConstants.PARTNERDEPT_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(
				GenericValidator.isBlankOrNull(request
						.getParameter(pageIndexName)) ? 0 : (Integer
						.parseInt(request.getParameter(pageIndexName)) - 1));
		PartnerAppopsDeptService partnerAppopsDeptService = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
		String id = StaticMethod.null2String(request.getParameter("id"));
		String parent_id = StaticMethod.null2String(request
				.getParameter("parent_id"));
		String modifyBug = StaticMethod.null2String(request
				.getParameter("modifyBug"));
		String parentname = StaticMethod.null2String(request
				.getParameter("parentname"));
		// 中文乱码问题cb004
		parentname = new String(parentname.getBytes("ISO-8859-1"), "UTF-8");
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String deptid = sessionForm.getDeptid();
		String hasRight = "0";
		String deptLevel = "2";
		IPnrPartnerAppOpsDept dept = new IPnrPartnerAppOpsDept();
		// 移动的用户才能有添加代维公司的权限
		String userid = this.getUserId(request);
		List<IPnrPartnerAppOpsDept> list0 = new ArrayList<IPnrPartnerAppOpsDept>();
		Map map1 = new HashMap();
		if (!"admin".equals(sessionForm.getUserid())) {
			StringBuffer sqlBuffer  = new StringBuffer();
			sqlBuffer.append(" and deptMagId='").append(deptid).append("'");
			sqlBuffer.append(" and substr(deptMagId,1,3)='");
			sqlBuffer.append(com.boco.eoms.partner.process.util.CommonUtils.startDeptMagId).append("'");
			if (isPartner != -1) {
				sqlBuffer.append(" and isPartner=").append(isPartner);
			}
			list0 = partnerAppopsDeptService.getPartnerDepts(sqlBuffer.toString());
			if (list0 != null && list0.size() > 0) {// 表示人员是代维管理人员
				map1 = (Map) partnerAppopsDeptService.getPartnerDeptsByAreaidOrDeptid(
						pageIndex, pageSize, "", deptid, dept, deptLevel, id,
						isPartner);
				String hasRightGoBack = PnrDeptUtil.getRightForGoBack(deptid,
						"deptId");
				request.setAttribute("interfaceHeadId", id);
				request.setAttribute("hasRightGoBack", hasRightGoBack);// 用于判断该用户是否可以返回到上一级，当deptLevel大于页面所在的级别时可以返回，小于时则没有权限返回
			} else {// 移动管理人员
				ITawSystemDeptManager deptManager = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
				TawSystemDept dept1 = deptManager.getDeptinfobydeptid(deptid,
						"0");
				if (dept1 != null) {
					if(deptid.length()>5){
						hasRight = "0"; // 移动用户
					}else{
						hasRight = "1"; // 移动用户
					}
					request.setAttribute("hasRightForAdd", hasRight);
					String areaid = StaticMethod.null2String(dept1.getAreaid());
					String hasRightGoBack = PnrDeptUtil.getRightForGoBack(
							areaid, "areaId");
					request.setAttribute("interfaceHeadId", id);
					request.setAttribute("hasRightGoBack", hasRightGoBack);// 用于判断该用户是否可以返回到上一级
					map1 = (Map) partnerAppopsDeptService
							.getPartnerDeptsByAreaidOrDeptid(pageIndex,
									pageSize, areaid, "", dept, deptLevel, id,
									isPartner);
				}
			}
		} else {
			hasRight = "1"; // 有权限
			request.setAttribute("hasRightForAdd", hasRight);
			request.setAttribute("interfaceHeadId", id);
			request.setAttribute("hasRightGoBack", "0");// 用于判断该用户是否可以返回到上一级，当deptLevel大于页面所在的级别时可以返回，小于时则没有权限返回
			map1 = (Map) partnerAppopsDeptService
					.getPartnerDeptsByAreaidOrDeptid(pageIndex, pageSize, "",
							"", dept, deptLevel, id, isPartner);
		}
		// String where="";
		// if(!"".equals(modifyBug)&&null!=modifyBug){
		// CommonSpringJdbcServiceImpl jdbcService =
		// (CommonSpringJdbcServiceImpl) ApplicationContextHolder
		// .getInstance().getBean("commonSpringJdbcService");
		// String sql="select interface_head_id from pnr_dept  where Id='"+id
		// +"'";
		// List<ListOrderedMap> countList = jdbcService.queryForList(sql);
		// Map map = new HashMap();
		// map = countList.get(0);
		// id=map.get("interface_head_id").toString();
		// where = " where interfaceHeadId='" + id
		// + "' and id != interfaceHeadId  " + "and deptLevel = 2";
		// }
		// else{
		// where = " where interfaceHeadId='" + id
		// + "' and id != interfaceHeadId  " + "and deptLevel = 2";
		// where=" where areaId like '2804%' and deptLevel='2' ";
		// }
		// Map map = (Map) partnerDeptMgr.getPartnerDepts(pageIndex,
		// pageSize,where);
		List list = (List) map1.get("result");
		request.setAttribute(PartnerDeptConstants.PARTNERDEPT_LIST, list);
		request.setAttribute("resultSize", map1.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("parentName", parentname);
		request.setAttribute("parent_id", id);
		request.setAttribute("isPartner", isPartner);
		return mapping.findForward("companyBaseInfoListSub");
	}

	/**
	 * 查询区县子代维公司基本信息
	 */
	public ActionForward findGrandsonCompanyBaseInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PartnerDeptConstants.PARTNERDEPT_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(
				GenericValidator.isBlankOrNull(request
						.getParameter(pageIndexName)) ? 0 : (Integer
						.parseInt(request.getParameter(pageIndexName)) - 1));
		PartnerAppopsDeptService partnerAppopsDeptService = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
		String id = request.getParameter("id");
		String parent_id = request.getParameter("parent_id");
		// 新增一个判断,否则会有bug，在第四级返回第3级，第3级返回到第2级时会发生会将第2级的所有的组织信息返回回来，因为此时发生多级返回已经找不到interfaceHeadId
		// modify 2013-05-06 by fengguangping
		if (parent_id == null&&id!=null) {
			parent_id = partnerAppopsDeptService.getPartnerDept(id).getInterfaceHeadId();
		}
		// String where = " where interfaceHeadId='" + id
		// + "' and id != interfaceHeadId " + " and deptLevel = 3";
		// Map map = (Map) partnerDeptMgr.getPartnerDepts(pageIndex, pageSize,
		// where);
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String deptid = sessionForm.getDeptid();
		String hasRight = "0";
		String deptLevel = "3";
		IPnrPartnerAppOpsDept dept = new IPnrPartnerAppOpsDept();
		// 移动的用户才能有添加代维公司的权限
		String userid = this.getUserId(request);
		List<IPnrPartnerAppOpsDept> list0 = new ArrayList<IPnrPartnerAppOpsDept>();
		Map map1 = new HashMap();
		if (!"admin".equals(sessionForm.getUserid())) {
			StringBuffer sqlBuffer  = new StringBuffer();
			sqlBuffer.append(" and deptMagId='").append(deptid).append("'");
			sqlBuffer.append(" and substr(deptMagId,1,3)='");
			sqlBuffer.append(com.boco.eoms.partner.process.util.CommonUtils.startDeptMagId).append("'");
			if (isPartner != -1) {
				sqlBuffer.append(" and isPartner=").append(isPartner);
			} 
			list0 = partnerAppopsDeptService.getPartnerDepts(sqlBuffer.toString());
			
			if (list0 != null && list0.size() > 0) {// 表示人员是代维管理人员
				map1 = (Map) partnerAppopsDeptService.getPartnerDeptsByAreaidOrDeptid(
						pageIndex, pageSize, "", deptid, dept, deptLevel, id,
						isPartner);
				String hasRightGoBack = PnrDeptUtil.getRightForGoBack(deptid,
						"deptId");
				request.setAttribute("interfaceHeadId", parent_id);
				request.setAttribute("hasRightGoBack", hasRightGoBack);// 用于判断该用户是否可以返回到上一级，当deptLevel大于页面所在的级别时可以返回，小于时则没有权限返回
			} else {// 移动管理人员
				ITawSystemDeptManager deptManager = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
				TawSystemDept dept1 = deptManager.getDeptinfobydeptid(deptid,
						"0");
				if (dept1 != null) {
					if(deptid.length()>5){
						hasRight = "0"; // 移动用户
					}else{
						hasRight = "1"; // 移动用户
					}
					request.setAttribute("hasRightForAdd", hasRight);
					String areaid = StaticMethod.null2String(dept1.getAreaid());
					String hasRightGoBack = PnrDeptUtil.getRightForGoBack(
							areaid, "areaId");
					request.setAttribute("interfaceHeadId", parent_id);
					request.setAttribute("hasRightGoBack", hasRightGoBack);// 用于判断该用户是否可以返回到上一级，当deptLevel大于页面所在的级别时可以返回，小于时则没有权限返回
					map1 = (Map) partnerAppopsDeptService
							.getPartnerDeptsByAreaidOrDeptid(pageIndex,
									pageSize, areaid, "", dept, deptLevel, id,
									isPartner);
				}
			}
		} else {
			hasRight = "1"; // 有权限
			request.setAttribute("hasRightForAdd", hasRight);
			request.setAttribute("interfaceHeadId", parent_id);
			request.setAttribute("hasRightGoBack", "0");// 用于判断该用户是否可以返回到上一级，当deptLevel大于页面所在的级别时可以返回，小于时则没有权限返回
			map1 = (Map) partnerAppopsDeptService
					.getPartnerDeptsByAreaidOrDeptid(pageIndex, pageSize, "",
							"", dept, deptLevel, id, isPartner);
		}
		List list = (List) map1.get("result");
		request.setAttribute(PartnerDeptConstants.PARTNERDEPT_LIST, list);
		request.setAttribute("resultSize", map1.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("parent_id", id);
		request.setAttribute("isPartner", isPartner);
		return mapping.findForward("companyBaseInfoListGrandson");
	}

	/**
	 * 查询代维小组基本信息
	 */
	public ActionForward findGroupGrandsonCompanyBaseInfo(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PartnerDeptConstants.PARTNERDEPT_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(
				GenericValidator.isBlankOrNull(request
						.getParameter(pageIndexName)) ? 0 : (Integer
						.parseInt(request.getParameter(pageIndexName)) - 1));
		PartnerAppopsDeptService partnerDeptMgr = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
		String id = request.getParameter("id");
		String parent_id = request.getParameter("parent_id");
		// String where = " where interfaceHeadId='" + id
		// + "' and id != interfaceHeadId " + " and deptLevel = '4'";
		// Map map = (Map) partnerDeptMgr.getPartnerDepts(pageIndex, pageSize,
		// where);
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String deptid = sessionForm.getDeptid();
		String hasRight = "0";
		String deptLevel = "4";
		IPnrPartnerAppOpsDept dept = new IPnrPartnerAppOpsDept();
		// 移动的用户才能有添加代维公司的权限
		String userid = this.getUserId(request);
		List<IPnrPartnerAppOpsDept> list0 = new ArrayList<IPnrPartnerAppOpsDept>();
		Map map1 = new HashMap();
		if (!"admin".equals(sessionForm.getUserid())) {
			StringBuffer sqlBuffer  = new StringBuffer();
			sqlBuffer.append(" and deptMagId='").append(deptid).append("'");
			sqlBuffer.append(" and substr(deptMagId,1,3)='");
			sqlBuffer.append(com.boco.eoms.partner.process.util.CommonUtils.startDeptMagId).append("'");
			if (isPartner != -1) {
				sqlBuffer.append(" and isPartner=").append(isPartner);
			} 
			list0 = partnerDeptMgr.getPartnerDepts(sqlBuffer.toString());
			
			if (list0 != null && list0.size() > 0) {// 表示人员是代维管理人员
				String hasRightGoBack = PnrDeptUtil.getRightForGoBack(deptid,
						"deptId");
				request.setAttribute("interfaceHeadId", parent_id);
				request.setAttribute("hasRightGoBack", hasRightGoBack);// 用于判断该用户是否可以返回到上一级，当deptLevel大于页面所在的级别时可以返回，小于时则没有权限返回
				map1 = (Map) partnerDeptMgr.getPartnerDeptsByAreaidOrDeptid(
						pageIndex, pageSize, "", deptid, dept, deptLevel, id,
						isPartner);
			} else {// 移动管理人员
				ITawSystemDeptManager deptManager = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
				TawSystemDept dept1 = deptManager.getDeptinfobydeptid(deptid,
						"0");
				if (dept1 != null) {
					if(deptid.length()>5){
						hasRight = "0"; // 移动用户
					}else{
						hasRight = "1"; // 移动用户
					}
					request.setAttribute("hasRightForAdd", hasRight);
					String areaid = StaticMethod.null2String(dept1.getAreaid());
					String hasRightGoBack = PnrDeptUtil.getRightForGoBack(
							areaid, "areaId");
					request.setAttribute("interfaceHeadId", parent_id);
					request.setAttribute("hasRightGoBack", hasRightGoBack);// 用于判断该用户是否可以返回到上一级，当deptLevel大于页面所在的级别时可以返回，小于时则没有权限返回
					map1 = (Map) partnerDeptMgr
							.getPartnerDeptsByAreaidOrDeptid(pageIndex,
									pageSize, areaid, "", dept, deptLevel, id,
									isPartner);
				}
			}
		} else {
			hasRight = "1"; // 有权限
			request.setAttribute("hasRightForAdd", hasRight);
			request.setAttribute("interfaceHeadId", parent_id);
			request.setAttribute("hasRightGoBack", "0");// 用于判断该用户是否可以返回到上一级，当deptLevel大于页面所在的级别时可以返回，小于时则没有权限返回
			map1 = (Map) partnerDeptMgr
					.getPartnerDeptsByAreaidOrDeptid(pageIndex, pageSize, "",
							"", dept, deptLevel, id, isPartner);
		}
		List list = (List) map1.get("result");
		request.setAttribute(PartnerDeptConstants.PARTNERDEPT_LIST, list);
		request.setAttribute("resultSize", map1.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("parent_id", parent_id);
		request.setAttribute("isPartner", isPartner);
		return mapping.findForward("companyBaseInfoListGrandsonGroup");
	}

	/**
	 * 跳转到修改代维公司基本信息页面
	 */
	public ActionForward modifyCompanyBaseInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		request.setAttribute("isPartner", isPartner);
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		request.setAttribute("province", pnrBaseAreaIdList.getRootAreaId()); // 省份：黑龙江
		PartnerAppopsDeptService partnerDeptMgr = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
		String id = StaticMethod.null2String(request.getParameter("proId"));
		IPnrPartnerAppOpsDeptForm partnerDeptForm = (IPnrPartnerAppOpsDeptForm) form;
		String name = StaticMethod.null2String(partnerDeptForm.getName());
		IPnrPartnerAppOpsDept partnerDept = null;
		if (!id.equals("")) {
			partnerDept = partnerDeptMgr.getPartnerDept(id);
		} else if (!name.equals("")) {
			List list = partnerDeptMgr.getPartnerDepts(" and name = '" + name
					+ "'");
			if (list.size() > 0) {
				partnerDept = (IPnrPartnerAppOpsDept) list.get(0);
			}
		}
		String areaNames = "";
		if (partnerDept != null) {
			partnerDeptForm = (IPnrPartnerAppOpsDeptForm) convert(partnerDept);
			List<IPnrPartnerAppOpsDept> list = partnerDeptMgr
					.getPartnerDepts(" and interfaceHeadId ='"
							+ partnerDept.getId() + "' and interfaceHeadId<>id");
			for (IPnrPartnerAppOpsDept p : list) {
				areaNames += p.getAreaName() + ",";
			}
		}
		partnerDeptForm.setAreaNames(areaNames);

		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
		// 定义取出所有厂家的集合，使用公共字典取得数据
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		
		/**
		 * 定义取出专业集合，使用公共字典取得数据
		 */
		ArrayList specialtyList = mgr.getDictSonsByDictid("11225");
		ArrayList bigTypeArrayList = new ArrayList();
		if (partnerDeptForm.getBigType() != null
				&& !"".equals(partnerDeptForm.getBigType())) {
			String[] bigType = partnerDeptForm.getBigType().split(",");
			for (int i = 0; i < bigType.length; i++) {
				bigTypeArrayList.add(bigType[i]);
			}
			for (int i = 0; i < specialtyList.size(); i++) {
				TawSystemDictType tawSystemDictType = (TawSystemDictType) specialtyList
						.get(i);
				if (bigTypeArrayList.contains(tawSystemDictType.getDictId())) {
					tawSystemDictType.setDictRemark("isTrue");
				}
			}
		}
		request.setAttribute("specialtyList", specialtyList);
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) getBean("pnrDeptAndUserConfigSetList");
		String qualifyConfig = StaticMethod.null2String(setList
				.getQualificationConfig());
		if ("1".equals(qualifyConfig)) {
			/* 取出相关联的资质信息 */
			IPnrQualificationMgr pnrQualificationMgr = (IPnrQualificationMgr) getBean("pnrQualificationMgr");
			List<PnrQualification> list = pnrQualificationMgr
					.findPnrQualificationsByDeptUUid(id);
			request.setAttribute("list", list);
		}
		updateFormBean(mapping, request, partnerDeptForm);
		if ("2".equals(partnerDept.getDeptLevel())) {
			return mapping.findForward("companyBaseInfoSubEdit");
		}
		if ("3".equals(partnerDept.getDeptLevel())) {
			return mapping.findForward("companyBaseInfoGrandsonEdit");
		}
		if ("4".equals(partnerDept.getDeptLevel())) {
			return mapping.findForward("companyBaseInfoGrandsonGroupEdit");
		}
		return mapping.findForward("companyBaseInfoEdit");
	}

	/**
	 * 修改代维公司基本信息
	 */
	public ActionForward editCompanyBaseInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		PartnerAppopsDeptService partnerDeptMgr = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
		String dept_name = request.getParameter("dept_name");// 修改前部门名称;
	
		IPnrPartnerAppOpsDeptForm partnerDeptForm = (IPnrPartnerAppOpsDeptForm) form;
		IPnrPartnerAppOpsDept partnerDept = (IPnrPartnerAppOpsDept) convert(partnerDeptForm);
		partnerDept.setDeleted("0");
		partnerDept.setDeptLevel("1");
		partnerDept.setCreateTime(StaticMethod.getLocalTime());// 增加新增时间
		partnerDept.setIsPartner(isPartner);
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) getBean("pnrDeptAndUserConfigSetList");
		String qualifyConfig = StaticMethod.null2String(setList
				.getQualificationConfig());
		List list = null;
		if ("1".equals(qualifyConfig)) {
			QualificationUtils utils = new QualificationUtils();
			list = utils.newQualificationObject(request);
		}
		partnerDeptMgr.updateDeptAndUser(dept_name, request, partnerDept, list,
				"1");
		return mapping.findForward("success");
	}

	/**
	 * 修改市级代维公司基本信息
	 */
	public ActionForward editCompanyBaseInfoSub(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}

		PartnerAppopsDeptService partnerDeptMgr = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
		String deadline = StaticMethod.nullObject2String(request
				.getParameter("deadlinetime"));
		String dept_name = request.getParameter("dept_name");
		IPnrPartnerAppOpsDeptForm partnerDeptForm = (IPnrPartnerAppOpsDeptForm) form;
		IPnrPartnerAppOpsDept partnerDept = (IPnrPartnerAppOpsDept) convert(partnerDeptForm);
		partnerDept.setDeleted("0");
		partnerDept.setCreateTime(null);
		partnerDept.setDeadline(deadline);
		partnerDept.setIsPartner(isPartner);
		partnerDept.setCreateTime(StaticMethod.getLocalTime());// 增加新增时间
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) getBean("pnrDeptAndUserConfigSetList");
		String qualifyConfig = StaticMethod.null2String(setList
				.getQualificationConfig());
		List qualifyList = null;
		if ("1".equals(qualifyConfig)) {
			QualificationUtils qualificationUtils = new QualificationUtils();
			qualifyList = qualificationUtils.newQualificationObject(request);
		}
		partnerDeptMgr.updateDeptAndUser(dept_name, request, partnerDept,
				qualifyList, "2");
		return mapping.findForward("success");
	}

	/**
	 * 修改县级代维公司基本信息
	 */
	public ActionForward editCompanyBaseInfoGrandson(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}

		PartnerAppopsDeptService partnerDeptMgr = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");
		// 设置单位类型
		String dept_name = request.getParameter("dept_name");
		
		IPnrPartnerAppOpsDeptForm partnerDeptForm = (IPnrPartnerAppOpsDeptForm) form;
		IPnrPartnerAppOpsDept partnerDept = (IPnrPartnerAppOpsDept) convert(partnerDeptForm);
		partnerDept.setDeleted("0");
		partnerDept.setDeptLevel("3");
		partnerDept.setIsPartner(isPartner);
		partnerDept.setCreateTime(StaticMethod.getLocalTime());
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) getBean("pnrDeptAndUserConfigSetList");
		String qualifyConfig = StaticMethod.null2String(setList
				.getQualificationConfig());
		List list = null;
		if ("1".equals(qualifyConfig)) {
			QualificationUtils utils = new QualificationUtils();
			list = utils.newQualificationObject(request);
		}
		partnerDeptMgr.updateDeptAndUser(dept_name, request, partnerDept, list,
				"3");
		return mapping.findForward("success");
	}

	/**
	 * 修改代维小组基本信息
	 */
	public ActionForward editCompanyBaseInfoGrandsonGroup(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		PartnerAppopsDeptService partnerDeptMgr = (PartnerAppopsDeptService) getBean("pnrAppopsDeptService");

	
		String dept_name = request.getParameter("dept_name");

		IPnrPartnerAppOpsDeptForm partnerDeptForm = (IPnrPartnerAppOpsDeptForm) form;
		IPnrPartnerAppOpsDept partnerDept = (IPnrPartnerAppOpsDept) convert(partnerDeptForm);
		partnerDept.setDeleted("0");
		partnerDept.setIfCompany("no");
		partnerDept.setDeptLevel("4");
		partnerDept.setIsPartner(isPartner);
		partnerDept.setCreateTime(StaticMethod.getLocalTime());
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) getBean("pnrDeptAndUserConfigSetList");
		String qualifyConfig = StaticMethod.null2String(setList
				.getQualificationConfig());
		List list = null;
		if ("1".equals(qualifyConfig)) {
			QualificationUtils utils = new QualificationUtils();
			list = utils.newQualificationObject(request);
		}
		partnerDeptMgr.updateDeptAndUser(dept_name, request, partnerDept, list,
				"4");
		return mapping.findForward("success");
	}

	/**
	 * 删除代维公司基本信息
	 */
	public ActionForward removeCompanyBaseInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));

		if (!id.equals("")) {
			partnerDeptMgr.removePartnerDept(id);
		}

		String[] ids = request.getParameterValues("checkbox11");
		if (ids != null) {
			for (int i = 0; i < ids.length; i++) {
				partnerDeptMgr.remove(ids[i]);
			}
		}
		return mapping.findForward("success");
	}

	/**
	 * 校验公司是否重复
	 */
	public ActionForward isExist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String deptname = request.getParameter("deptname");
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		Boolean exist = partnerDeptMgr.getPartnerDeptByName(deptname);
		PrintWriter writer = response.getWriter();
		if (exist == true) {
			writer.write(new Gson()
					.toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "true").put("msg", "该名未被使用")
							.build()));
		} else
			writer.write(new Gson()
					.toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "false").put("msg", "该名已被使用")
							.build()));

		return null;
	}

	/**
	 * 
	 * @Title: updateStatisticsInfo
	 * @Description: 用于同步更新统计数据用,只有管理员才有此权限;
	 * @param
	 * @Time:Dec 6, 2012-1:48:22 PM
	 * @Author:fengguangping
	 * @return ActionForward 返回类型
	 * @throws
	 */
	public ActionForward updateStatisticsInfo(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionForm = getUser(request);
		String userid = StaticMethod.null2String(sessionForm.getUserid());
		String deptid = StaticMethod.null2String(sessionForm.getDeptid());
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) ApplicationContextHolder
				.getInstance().getBean("partnerDeptMgr");
		if ("admin".equals(userid)) {// 执行全部数据的更新
			List<PartnerDept> list = partnerDeptMgr.getPartnerDepts();
			for (PartnerDept partnerDept : list) {
				partnerDept = partnerDeptMgr.updateStatisticInfo(partnerDept);
				partnerDeptMgr.savePartnerDept(partnerDept);
			}
		}
		if (!"".equals(deptid) && deptid.startsWith("1")) {
		}
		return mapping.findForward("success");
	}

	public ActionForward area(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String str = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		// String[] nodes=str.split("\\|");
		// String node=nodes[0];
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-area-xtree");
		ArrayList<TawSystemArea> list = new ArrayList<TawSystemArea>();

		try {
			// ITawSystemDeptManager mgr = (ITawSystemDeptManager)
			// ApplicationContextHolder
			// .getInstance().getBean("ItawSystemDeptManager");
			ITawSystemAreaManager mgr = (ITawSystemAreaManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemAreaManager");
			list = (ArrayList) mgr.getSonAreaByAreaId(node);
			// list = (ArrayList) mgr.getALLSondept(node,"0");
			// list = (ArrayList) mgr.getALLSondeptIspartner(node, "0", node);

		} catch (Exception ex) {
			BocoLog.error(this, "生成地域树图时报错：" + ex);
		}
		// 1014为省下属
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getAreaid().equals("1014")) {
				list.remove(i);
			}
		}
		request.setAttribute("list", list);
		return mapping.findForward("xtree");
	}

	/**
	 * CCC means: City(市),Country（县）,Monitor Company（代维公司）
	 */
	public void retriveCCC(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String flag = Strings.nullToEmpty(request.getParameter("flag"));

		TawSystemAreaDao tawSystemAreaDao = Nop3Utils
				.getService(TawSystemAreaDao.class);
		PartnerDeptMgr partnerDeptMgr = Nop3Utils
				.getService(PartnerDeptMgr.class);

		if (flag.equals("")) {
			Nop3Utils.printJsonFailureMsg(response);
		}

		String parentAreaId = Strings.nullToEmpty(request
				.getParameter("parentAreaId"));

		if (parentAreaId.equals("")) {
			Nop3Utils.printJsonFailureMsg(response);
		}

		// 返回city数据
		if (flag.equals("city")) {
			List<TawSystemArea> cityList = tawSystemAreaDao
					.getSonAreaByAreaId(parentAreaId);
			String cityOptionString = "";
			// 加入默认的选择项，防止在页面初始加载的时候载入过多数据
			cityOptionString += "<option value=''>请选择市</option>";
			for (TawSystemArea tawSystemArea : Nop3Utils.nullToEmpty(cityList)) {
				cityOptionString += "<option value='"
						+ tawSystemArea.getAreaid() + "'>"
						+ tawSystemArea.getAreaname() + "</option>";
			}
			Nop3Utils.printJsonObject(response, "city", cityOptionString);
			return;
		} else if (flag.equals("country")) {
			List<TawSystemArea> countryList = tawSystemAreaDao
					.getSonAreaByAreaId(parentAreaId);
			String country = "";
			country += "<option value=''>请选择县公司</option>";
			for (TawSystemArea tawSystemArea : Nop3Utils
					.nullToEmpty(countryList)) {
				country += "<option value='" + tawSystemArea.getAreaid() + "'>"
						+ tawSystemArea.getAreaname() + "</option>";
			}

			List<PartnerDept> monitorCompanyList = partnerDeptMgr
					.getPartnerDeptsByHql(" partnerDept.areaId like '"
							+ parentAreaId + "%' ");
			String monitorCompany = "";
			monitorCompany += "<option value=''>请选择代维公司</option>";
			for (PartnerDept partnerDept : Nop3Utils
					.nullToEmpty(monitorCompanyList)) {
				monitorCompany += "<option value='" + partnerDept.getId()
						+ "'>" + partnerDept.getName() + "</option>";
			}
			Map<String, Object> myResultMap = Maps.newHashMap();
			myResultMap.put("success", "true");
			myResultMap.put("msg", "ok");
			myResultMap.put("country", country);
			myResultMap.put("monitorCompany", monitorCompany);
			response.setCharacterEncoding(Charsets.UTF_8.toString());
			response.getWriter().write(new Gson().toJson(myResultMap));
			return;
		} else {
		}

	}

	/**
	 * ajax删除资质信息
	 */
	public void removePnrQualification(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Writer writer = response.getWriter();
		IPnrQualificationMgr pnrQualificationMgr = (IPnrQualificationMgr) getBean("pnrQualificationMgr");
		if (!"".equals(id)) {
			PnrQualification p = pnrQualificationMgr.find(id);
			if (p != null) {
				p.setIsDeleted("1");
				pnrQualificationMgr.save(p);
				writer.write(new Gson()
						.toJson(new ImmutableMap.Builder<String, String>()
								.put("success", "true").put("info", "删除成功!")
								.build()));
			} else {
				writer.write(new Gson()
						.toJson(new ImmutableMap.Builder<String, String>()
								.put("success", "true").put("info", "删除失败!")
								.build()));
			}
		}
	}
}