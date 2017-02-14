package com.boco.eoms.partner.baseinfo.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.axis2.databinding.utils.BeanUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.jfree.chart.ChartUtilities;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.baseinfo.dao.TawParCarService;
import com.boco.eoms.partner.baseinfo.mgr.AreaDeptTreeMgr;
import com.boco.eoms.partner.baseinfo.mgr.TawPartnerCarMgr;
import com.boco.eoms.partner.baseinfo.mgr.impl.PartnerDeptAreaMgrImpl;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.baseinfo.model.TawPartnerCar;
import com.boco.eoms.partner.baseinfo.model.TdObjModel;

import com.boco.eoms.partner.baseinfo.util.RoleIdList;
import com.boco.eoms.partner.baseinfo.util.TableHelper;

import com.boco.eoms.partner.baseinfo.util.TawPartnerCarConstants;
import com.boco.eoms.partner.baseinfo.webapp.form.TawApparatusForm;
import com.boco.eoms.partner.baseinfo.webapp.form.TawPartnerCarForm;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.dict.service.impl.ID2NameServiceImpl;

import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.duty.bo.TawModelAssignXLSBO;
import com.boco.eoms.duty.bo.TawRmModelSetBo;
import com.boco.eoms.duty.model.TawModelCopy;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import com.mchange.v2.beans.BeansUtils;

/**
 * <p>
 * Title:车辆管理
 * </p>
 * <p>
 * Description:车辆管理
 * </p>
 * <p>
 * Thu Feb 05 13:54:40 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() fengshaohong
 * @moudle.getVersion() 3.5
 * 
 */
public final class TawPartnerCarAction extends BaseAction {
	Integer size = null;
	Integer index = null;
	String backsql = "";
	String allNode = "";

	/**
	 * 未指定方法时默认调用的方法
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
	 * 新增车辆管理
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
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String deptId = sessionform.getDeptid();
		request.setAttribute("deptId", deptId);
		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
		Integer firstdeptsimble = roleIdList.getParDeptId();
		request.setAttribute("firstdeptsimble", firstdeptsimble);

		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));
		// nodeId = nodeId.substring(0, 7);
		TawPartnerCarMgr mgr = (TawPartnerCarMgr) getBean("tawPartnerCarMgr");
		TawPartnerCarForm tawPartnerCarForm = new TawPartnerCarForm();
		tawPartnerCarForm.setDept_id(deptId);
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		List listId = areaDeptTreeMgr.getAreaDeptTreesByType("area");
		List listName = new ArrayList();
		List list_id = new ArrayList();
		for (int i = 0; i < listId.size(); i++) {
			String tempId = ((AreaDeptTree) (listId.get(i))).getNodeId();
			listName.add(areaDeptTreeMgr.id2Name(tempId));
			list_id.add(tempId);
		}

		String[] all = mgr.getDictIdByParentId("11210");
		tawPartnerCarForm.setArrayPieceAll(all);
		request.setAttribute("tawPartnerCarForm", tawPartnerCarForm);
		request.setAttribute("listName", listName);
		request.setAttribute("listId", list_id);
		return mapping.findForward("edit");
	}

	/**
	 * 修改车辆管理
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
		TawPartnerCarMgr tawPartnerCarMgr = (TawPartnerCarMgr) getBean("tawPartnerCarMgr");
		TawPartnerCarMgr mgr = (TawPartnerCarMgr) getBean("tawPartnerCarMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		TawPartnerCar tawPartnerCar = tawPartnerCarMgr.getTawPartnerCar(id);
		TawPartnerCarForm tawPartnerCarForm = (TawPartnerCarForm) convert(tawPartnerCar);
//		String[] all = mgr.getDictIdByParentId("11210");
//		tawPartnerCarForm.setArrayPieceAll(all);
//		tawPartnerCarForm.setArrayPiece((tawPartnerCarForm.getPiece())
//				.split("'"));
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			tawPartnerCarForm.setAnnualCheckData(sdf.format(sdf.parse(tawPartnerCarForm.getAnnualCheckData())));
			tawPartnerCarForm.setEndTime(sdf.format(sdf.parse(tawPartnerCarForm.getEndTime())));
			tawPartnerCarForm.setStart_time(sdf.format(sdf.parse(tawPartnerCarForm.getStart_time())));
		}catch(ParseException e){
			tawPartnerCarForm.setAnnualCheckData("");
			tawPartnerCarForm.setEndTime("");
			tawPartnerCarForm.setStart_time("");
			e.printStackTrace();
		}
//		tawPartnerCarForm
		updateFormBean(mapping, request, tawPartnerCarForm);
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		List listId = areaDeptTreeMgr.getAreaDeptTreesByType("area");
		List listName = new ArrayList();
		List list_id = new ArrayList();
		for (int i = 0; i < listId.size(); i++) {
			String tempId = ((AreaDeptTree) (listId.get(i))).getNodeId();
			listName.add(areaDeptTreeMgr.id2Name(tempId));
			list_id.add(tempId);
		}
		request.setAttribute("listName", listName);
		request.setAttribute("listId", list_id);
		return mapping.findForward("edit");
	}

	/**
	 * 查看车辆管理
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
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String deptId = sessionform.getDeptid();
		request.setAttribute("deptId", deptId);
		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
		Integer firstdeptsimble = roleIdList.getParDeptId();
		request.setAttribute("firstdeptsimble", firstdeptsimble);
		TawPartnerCarMgr tawPartnerCarMgr = (TawPartnerCarMgr) getBean("tawPartnerCarMgr");
		TawPartnerCarMgr mgr = (TawPartnerCarMgr) getBean("tawPartnerCarMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		TawPartnerCar tawPartnerCar = tawPartnerCarMgr.getTawPartnerCar(id);
		TawPartnerCarForm tawPartnerCarForm = (TawPartnerCarForm) convert(tawPartnerCar);
		String[] all = mgr.getDictIdByParentId("11210");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		tawPartnerCarForm.setStart_time(sdf.format(sdf.parse(tawPartnerCarForm
				.getStart_time())));
		tawPartnerCarForm.setEndTime(sdf.format(sdf.parse(tawPartnerCarForm
				.getEndTime())));
		tawPartnerCarForm.setAnnualCheckData(sdf.format(sdf
				.parse(tawPartnerCarForm.getAnnualCheckData())));
		tawPartnerCarForm.setCarOutDate(tawPartnerCarForm.getCarOutDate());
//		tawPartnerCarForm.setCarOutDate(sdf.format(sdf.parse(tawPartnerCarForm
//				.getCarOutDate()))+"");
		tawPartnerCarForm.setArrayPieceAll(all);
//		tawPartnerCarForm.setArrayPiece((tawPartnerCarForm.getPiece())
//				.split("'"));
		updateFormBean(mapping, request, tawPartnerCarForm);
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		List listId = areaDeptTreeMgr.getAreaDeptTreesByType("area");
		List listName = new ArrayList();
		List list_id = new ArrayList();
		for (int i = 0; i < listId.size(); i++) {
			String tempId = ((AreaDeptTree) (listId.get(i))).getNodeId();
			listName.add(areaDeptTreeMgr.id2Name(tempId));
			list_id.add(tempId);
		}
		request.setAttribute("listName", listName);
		request.setAttribute("listId", list_id);
		return mapping.findForward("detail");
	}

	/**
	 * 保存车辆管理
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
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String provvalue = request.getParameter("provvalue");
		TawPartnerCarMgr tawPartnerCarMgr = (TawPartnerCarMgr) getBean("tawPartnerCarMgr");
		TawPartnerCarForm tawPartnerCarForm = (TawPartnerCarForm) form;
		String interfaceHeadId = request.getParameter("interfaceHeadId");
		tawPartnerCarForm.setBigpartnerid(StaticMethod
				.null2String(interfaceHeadId));
		tawPartnerCarForm.setPartnerid(provvalue);
		String deptid=tawPartnerCarMgr.getDictIdByPartnerId(provvalue);
		// tawPartnerCarForm.setDept_id(did);
		// tawPartnerCarForm.setAddMan(addname);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String purchaseTime = StaticMethod.nullObject2String(request
				.getParameter("purchaseTime"));
		String start_time = StaticMethod.nullObject2String(request
				.getParameter("start_time"));
		String endTime = StaticMethod.nullObject2String(request
				.getParameter("endTime"));
		String carOutDate = StaticMethod.nullObject2String(request
				.getParameter("carOutDate"));
		String annualCheckData = StaticMethod.nullObject2String(request
				.getParameter("annualCheckData"));
		tawPartnerCarForm.setPurchaseTime(null);
		tawPartnerCarForm.setStart_time(null);
		tawPartnerCarForm.setEndTime(null);
		tawPartnerCarForm.setCarOutDate(null);
		tawPartnerCarForm.setAnnualCheckData(null);
		String id = tawPartnerCarForm.getCar_number();
		String[] piece = request.getParameterValues("arrayPiece");
		String pieceTemp = "";
		if (piece != null) {
			for (int i = 0; i < piece.length; i++) {
				pieceTemp += piece[i] + "'";
			}
			pieceTemp = pieceTemp.substring(0, pieceTemp.length() - 1);
		}
		boolean isNew = (null == tawPartnerCarForm.getId() || ""
				.equals(tawPartnerCarForm.getId()));
		TawPartnerCar tawPartnerCar = (TawPartnerCar) convert(tawPartnerCarForm);
		tawPartnerCar.setPurchaseTime(purchaseTime);
		tawPartnerCar.setStart_time(sdf.parse(start_time));
		tawPartnerCar.setEndTime(sdf.parse(endTime));
		String d=sdf.format(sdf.parse(carOutDate));
		tawPartnerCar.setCarOutDate(d);
		tawPartnerCar.setAnnualCheckData(sdf.parse(annualCheckData));
		TawSystemSessionForm sessionForm = this.getUser(request);
		String addMan = sessionForm.getUserid();
		String addDept = sessionForm.getDeptid();
		Date addTime = new Date();
		String connectType = sessionForm.getContactMobile();
		if (isNew) {
			Boolean isU = tawPartnerCarMgr.isunique(id);
			if (!isU.booleanValue()) {
				AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
				List listId = areaDeptTreeMgr.getAreaDeptTreesByType("area");
				List listName = new ArrayList();
				List list_id = new ArrayList();
				for (int i = 0; i < listId.size(); i++) {
					String[] all = tawPartnerCarMgr
							.getDictIdByParentId("11210");
					tawPartnerCarForm.setArrayPieceAll(all);
					String tempId = ((AreaDeptTree) (listId.get(i)))
							.getNodeId();
					listName.add(areaDeptTreeMgr.id2Name(tempId));
					list_id.add(tempId);
				}

				updateFormBean(mapping, request, tawPartnerCarForm);

				request.setAttribute("listName", listName);
				request.setAttribute("listId", list_id);
				request.setAttribute("fallure", " 车辆已经存在");
				return mapping.findForward("edit");
			}
			String deptId = tawPartnerCar.getDept_id();
			if (deptId != null && !deptId.equals("")) {
				AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
				AreaDeptTree areaDeptTree = areaDeptTreeMgr
						.getAreaDeptTreeByNodeId(deptId);

				// 隐藏车辆管理，当新增车辆时isShow设置为0，可正常显示
				String carId = deptId + "03";
				AreaDeptTree carTree = areaDeptTreeMgr
						.getAreaDeptTreeByNodeId(carId);
				// 隐藏油机管理，当新增油机时isShow设置为0，可正常显示
				carTree.setIsShow("0");
				// areaDeptTreeMgr.saveAreaDeptTree(carTree);

				tawPartnerCar.setPartnerid(areaDeptTree.getPartnerid());
				tawPartnerCar
						.setBigpartnerid(areaDeptTree.getInterfaceHeadId());
				tawPartnerCar.setAreaidtrue(areaDeptTree.getAreaId());
			}
			tawPartnerCar.setAddMan(addMan);
			tawPartnerCar.setAddDept(addDept);
			tawPartnerCar.setAddTime(addTime);
			tawPartnerCar.setConnectType(connectType);
			tawPartnerCar.setPiece(pieceTemp);
			tawPartnerCar.setDeleted("0");
			String areaid = tawPartnerCarMgr.deptIdToId(provvalue);
			tawPartnerCar.setArea_id(areaid);
			tawPartnerCar.setDept_id(deptid);
//			String dept_id = null;
			tawPartnerCarMgr.saveTawPartnerCar(tawPartnerCar);
		} else {
			String tempId2 = tawPartnerCarForm.getId();
			TawPartnerCar old = tawPartnerCarMgr.getTawPartnerCar(tempId2);
			String carnum_old = old.getCar_number();
			if (!id.equals(carnum_old)) {
				Boolean isU = tawPartnerCarMgr.isunique(id);
				if (!isU.booleanValue()) {
					AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
					List listId = areaDeptTreeMgr
							.getAreaDeptTreesByType("area");
					List listName = new ArrayList();
					List list_id = new ArrayList();
					for (int i = 0; i < listId.size(); i++) {
						String[] all = tawPartnerCarMgr
								.getDictIdByParentId("11210");
						tawPartnerCarForm.setArrayPieceAll(all);
						String tempId = ((AreaDeptTree) (listId.get(i)))
								.getNodeId();
						listName.add(areaDeptTreeMgr.id2Name(tempId));
						list_id.add(tempId);
					}

					updateFormBean(mapping, request, tawPartnerCarForm);

					request.setAttribute("listName", listName);
					request.setAttribute("listId", list_id);
					request.setAttribute("fallure", " 车辆编号已经存在");
					return mapping.findForward("edit");
				}
			}

			tawPartnerCar.setAddMan(old.getAddMan());
			tawPartnerCar.setAddDept(addDept);
			tawPartnerCar.setAddTime(old.getAddTime());
			tawPartnerCar.setConnectType(old.getConnectType());
			tawPartnerCar.setPiece(pieceTemp);
			tawPartnerCar.setDeleted("0");
			tawPartnerCar.setDept_id(deptid);
			tawPartnerCarMgr.saveTawPartnerCar(tawPartnerCar);
		}
		request.setAttribute("treeNodeId", tawPartnerCar.getDept_id() + "03");
		request.setAttribute("actionDo", "tawPartnerCars");
		return mapping.findForward("refreshSelf");
	}

	/**
	 * 删除车辆管理
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
		TawPartnerCarMgr tawPartnerCarMgr = (TawPartnerCarMgr) getBean("tawPartnerCarMgr");
		String selcar = StaticMethod.null2String(request
				.getParameter("seldelcar"));
		String id = StaticMethod.null2String(request.getParameter("id"));
		try {
			if (!"".equals(selcar)) {
				String[] sel = selcar.split("\\|");
				for (int i = 0; i < sel.length; i++) {
					tawPartnerCarMgr.removeTawPartnerCar(sel[i]);
				}
				return mapping.findForward("success");
			} else if (!"".equals(id)) {
				tawPartnerCarMgr.removeTawPartnerCar(id);
				return mapping.findForward("success");
			}
		} catch (Exception e) {
			return mapping.findForward("fail");
		}
		return mapping.findForward("fail");
	}

	/**
	 * 分页显示车辆管理列表
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
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String deptId = sessionform.getDeptid();
		request.setAttribute("deptId", deptId);
		String isCity = "no";
		String nodeId = StaticMethod.null2String(sessionform.getDeptid());
		TawSystemSessionForm sessionForm = this.getUser(request);
		// 2009-3-6 解决页面刷新后，链接参数为空的情况
		// String in =
		// StaticMethod.null2String(request.getParameter("in"));//此条件表示点省、地市、厂商节点的查询，调用search方法。
		String in = "factory";

		// vvvvvvvvvvvv
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				TawPartnerCarConstants.TAWPARTNERCAR_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		size = pageSize;
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String exprotType = StaticMethod
				.nullObject2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								TawPartnerCarConstants.TAWPARTNERCAR_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!"".equals(exprotType)) {
			pageSize = new Integer(-1);
		}
		index = pageIndex;
		String whereStr = "";
		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
		String parDeptid = "";
		try {
			parDeptid = String.valueOf(roleIdList.getParDeptId());
		} catch (Exception e) {// 防止类型转换出现异常，所以默认为空

		}
		if (!parDeptid.equals("") && !deptId.equals("")
				&& deptId.startsWith(parDeptid)) {
			whereStr = " and dept_id like '" + deptId + "%'";
		}
		TawPartnerCarMgr tawPartnerCarMgr = (TawPartnerCarMgr) getBean("tawPartnerCarMgr");
		Map map = (Map) tawPartnerCarMgr.getTawPartnerCars(pageIndex, pageSize,
				whereStr);
		List list = (List) map.get("result");

		request.setAttribute(TawPartnerCarConstants.TAWPARTNERCAR_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("nodeId", nodeId);
		request.setAttribute("isCity", isCity);
		request.setAttribute("listName", list);

		return mapping.findForward("list");
	}

	/**
	 * 根据条件查询车辆信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	public ActionForward searchCar(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		String isCity = "no";
//		String nodeId = StaticMethod
//				.null2String(request.getParameter("nodeId"));
//
//		// 2009-3-6 解决页面刷新后，链接参数为空的情况
//		String in = StaticMethod.null2String(request.getParameter("in"));// 此条件表示点省、地市、厂商节点的查询，调用search方法。
//		if (!in.equals("")) {
//			request.setAttribute("inNodeId", nodeId);
//			request.setAttribute("in", in);
//		}
//
//		if (!"null".equals(nodeId) && nodeId != null && !"".equals(nodeId)) {
//			;
//		} else {
//			nodeId = (String) request.getAttribute("nodeId");
//		}
//		allNode = nodeId;
//		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
//		AreaDeptTree adt = areaDeptTreeMgr.getAreaDeptTreeByNodeId(nodeId);
//		TawPartnerCarForm tawPartnerCarForm = new TawPartnerCarForm();
//		String type = adt.getNodeType();
//		List listName = new ArrayList();
//		List list_id = new ArrayList();
//		if (type.equals("factory")) {
//			tawPartnerCarForm.setDept_id(nodeId);
//			isCity = "yes";
//		} else if (type.equals("area")) {
//			tawPartnerCarForm.setDept_id("");
//
//		} else if (type.equals("province")) {
//			// listName.add("--请选择-- ");
//			// list_id.add(" ");
//		} else {
//			isCity = "yes";
//			AreaDeptTree adt2 = areaDeptTreeMgr.getAreaDeptTreeByNodeId(nodeId
//					.substring(0, 7));
//			tawPartnerCarForm.setDept_id(nodeId.substring(0, 7));
//			nodeId = nodeId.substring(0, 7);
//		}
//		// vvvvvvvvvvvvvvvv
//		List listId = areaDeptTreeMgr.getAreaDeptTreesByType("area");
//		ID2NameService mgr = (ID2NameService) getBean("id2nameService");
//		for (int i = 0; i < listId.size(); i++) {
//			String tempId = ((AreaDeptTree) (listId.get(i))).getNodeId();
//			listName.add(mgr.id2Name(tempId, "areaDeptTreeDao"));
//			list_id.add(tempId);
//		}
//		updateFormBean(mapping, request, tawPartnerCarForm);
//		request.setAttribute("listName", listName);
//		request.setAttribute("listId", list_id);
//
//		String areaId = StaticMethod
//				.null2String(request.getParameter("areaId"));
//		String bigDeptId = StaticMethod.null2String(request
//				.getParameter("bigDeptId"));
//		if (!"".equals(bigDeptId)) {
//			StringBuffer whereStr = new StringBuffer();
//			TawPartnerCarMgr tawPartnerCarMgr = (TawPartnerCarMgr) getBean("tawPartnerCarMgr");
//			if (!"".equals(areaId)) {
//				whereStr.append(" and area_id = '" + areaId + "'");
//			}
//			whereStr.append(" and bigpartnerid = '" + bigDeptId + "'");
//			Map map = (Map) tawPartnerCarMgr.getTawPartnerCars(0, 100, whereStr
//					.toString());
//			List list = (List) map.get("result");
//			request.setAttribute(TawPartnerCarConstants.TAWPARTNERCAR_LIST,
//					list);
//			request.setAttribute("resultSize", map.get("total"));
//			request.setAttribute("pageSize", new Integer(100));
//			request.setAttribute("nodeId", areaId);
//			request.setAttribute("isCity", "yes");
//			return mapping.findForward("list");
//		}
//		// vvvvvvvvvvvv
//		String pageIndexName = new org.displaytag.util.ParamEncoder(
//				TawPartnerCarConstants.TAWPARTNERCAR_LIST)
//				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
//		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
//				.getPageSize();
//		size = pageSize;
//		final Integer pageIndex = new Integer(GenericValidator
//				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
//				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
//		index = pageIndex;
//		String whereStr = " and dept_id like '" + nodeId + "%'";
//		TawPartnerCarMgr tawPartnerCarMgr = (TawPartnerCarMgr) getBean("tawPartnerCarMgr");
//		Map map = (Map) tawPartnerCarMgr.getTawPartnerCars(pageIndex, pageSize,
//				whereStr);
//		List list = (List) map.get("result");
//		request.setAttribute(TawPartnerCarConstants.TAWPARTNERCAR_LIST, list);
//		request.setAttribute("resultSize", map.get("total"));
//		request.setAttribute("pageSize", pageSize);
//		request.setAttribute("nodeId", nodeId);
//		request.setAttribute("isCity", isCity);
//		return mapping.findForward("list");
//	}

	/**
	 * 条件查询 分页显示车辆管理列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward xquery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawPartnerCarMgr tawPartnerCarMgr = (TawPartnerCarMgr) getBean("tawPartnerCarMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String dept_id=sessionform.getDeptid();
		String provvalue=StaticMethod.null2String(request.getParameter("provvalue"));
		String company=StaticMethod.null2String(request.getParameter("company"));
		String car_number=StaticMethod.null2String(request.getParameter("car_number"));
		StringBuffer sql = new StringBuffer();
		if (!"".equals(car_number) && car_number != null) {
			sql.append(" and car_number like '%" + car_number + "%'");
		}
		if (!"".equals(provvalue) && provvalue != null) {
			sql.append(" and partnerid like '%" + provvalue + "%'");
		}
//		if (!"".equals(dept_id) && dept_id != null) {
//			sql.append(" and addDept= '" + dept_id + "'");
//		}
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				TawPartnerCarConstants.TAWPARTNERCAR_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		size = pageSize;
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		index = pageIndex;
		Map map = (Map) tawPartnerCarMgr.getTawPartnerCars(pageIndex, pageSize,
				sql.toString());
		List list = (List) map.get("result");
//		// vvvvvvvvvvvvvvvv
//		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
//		List listId = areaDeptTreeMgr.getAreaDeptTreesByType("area");
//		List listName = new ArrayList();
//		List list_id = new ArrayList();
//		for (int i = 0; i < listId.size(); i++) {
//			String tempId = ((AreaDeptTree) (listId.get(i))).getNodeId();
//			listName.add(areaDeptTreeMgr.id2Name(tempId));
//			list_id.add(tempId);
//		}
//		updateFormBean(mapping, request, tawPartnerCarForm);
//		request.setAttribute("listName", listName);
//		request.setAttribute("listId", list_id);
		// vvvvvvvvvvvv
		request.setAttribute(TawPartnerCarConstants.TAWPARTNERCAR_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}

	public ActionForward backToSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawPartnerCarMgr tawPartnerCarMgr = (TawPartnerCarMgr) getBean("tawPartnerCarMgr");
		Map map = (Map) tawPartnerCarMgr
				.getTawPartnerCars(index, size, backsql);
		List list = (List) map.get("result");
		request.setAttribute(TawPartnerCarConstants.TAWPARTNERCAR_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", size);
		return mapping.findForward("list");
	}

	public ActionForward xlsSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String carFormDictId="1121701";
		String carStateId="1121702";
		String isPrepareid="1121703";
		String ownershipTypeId="1121307";
		String carUseId="1121306";
		String carDriveTypeId="11215";
		String carCategoryId="1121311";
		String serviceProfessional="1121201";
		
		TawPartnerCarMgr mgr = (TawPartnerCarMgr) getBean("tawPartnerCarMgr");
		ITawSystemDictTypeManager dictMgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		
		
		ArrayList<TawSystemDictType> carFormList = dictMgr.getDictSonsByDictid(carFormDictId);
		Map<Object,Object> carFormMap=new HashMap<Object, Object>();
		for (TawSystemDictType tawSystemDictType : carFormList) {
			carFormMap.put(tawSystemDictType.getDictName(),tawSystemDictType.getDictId());
		}
		ArrayList<TawSystemDictType> serviceProfessionalList = dictMgr.getDictSonsByDictid(serviceProfessional);
		Map<Object,Object> serviceProfessionalMap=new HashMap<Object, Object>();
		for (TawSystemDictType tawSystemDictType : serviceProfessionalList) {
			serviceProfessionalMap.put(tawSystemDictType.getDictName(),tawSystemDictType.getDictId());
		}
		ArrayList<TawSystemDictType> carStateList = dictMgr.getDictSonsByDictid(carStateId);
		Map<Object,Object> carStateMap=new HashMap<Object, Object>();
		for (TawSystemDictType tawSystemDictType : carStateList) {
			carStateMap.put(tawSystemDictType.getDictName(),tawSystemDictType.getDictId());
		}
		ArrayList<TawSystemDictType> isPrepareList=dictMgr.getDictSonsByDictid(isPrepareid);
		Map<Object,Object> isPrepareMap=new HashMap<Object, Object>();
		for (TawSystemDictType tawSystemDictType : isPrepareList) {
			isPrepareMap.put(tawSystemDictType.getDictName(),tawSystemDictType.getDictId());
		}
		ArrayList<TawSystemDictType> ownershipTypeList=dictMgr.getDictSonsByDictid(ownershipTypeId);
		Map<Object,Object> ownershipTypeMap=new HashMap<Object, Object>();
		for (TawSystemDictType tawSystemDictType : ownershipTypeList) {
			ownershipTypeMap.put(tawSystemDictType.getDictName(),tawSystemDictType.getDictId());
		}
		ArrayList<TawSystemDictType> carUseList=dictMgr.getDictSonsByDictid(carUseId);
		Map<Object,Object> carUseMap=new HashMap<Object, Object>();
		for (TawSystemDictType tawSystemDictType : carUseList) {
			carUseMap.put(tawSystemDictType.getDictName(),tawSystemDictType.getDictId());
		}
		ArrayList<TawSystemDictType> carDriveTypeList=dictMgr.getDictSonsByDictid(carDriveTypeId);
		Map<Object,Object> carDriveTypeMap=new HashMap<Object, Object>();
		for (TawSystemDictType tawSystemDictType : carDriveTypeList) {
			carDriveTypeMap.put(tawSystemDictType.getDictName(),tawSystemDictType.getDictId());
		}
		ArrayList<TawSystemDictType> carCategoryList=dictMgr.getDictSonsByDictid(carCategoryId);
		Map<Object,Object> carCategoryMap=new HashMap<Object, Object>();
		for (TawSystemDictType tawSystemDictType : carCategoryList) {
			carCategoryMap.put(tawSystemDictType.getDictName(),tawSystemDictType.getDictId());
		}
		
		// 首先将文件从客户端上传到服务器
		String timeTag = StaticMethod.getCurrentDateTime("yyyy_MM_dd_HHmmss");
		String sysTemPath = request.getRealPath("/");
		TawPartnerCarForm accessoryForm = (TawPartnerCarForm) form;
		String bigpartnerid=accessoryForm.getFormInterfaceHeadIdXls();
		//accessoryForm.getFormInterfaceHeadIdXls();
		String partnerid=StaticMethod.null2String(accessoryForm.getPartneridXls());
		String areaid=mgr.deptIdToId(partnerid);
		String deptid=mgr.getDictIdByPartnerId(partnerid);
		
		PartnerDeptAreaMgrImpl service = (PartnerDeptAreaMgrImpl) ApplicationContextHolder
		.getInstance().getBean("PartnerDeptAreaMgrImplFlush");
		String areaname = service.deptIdToName(partnerid,"partnerDeptDao");
		String uploadPath = "/WEB-INF/pages/partner/baseinfo/tawPartnerCar/upfiles";
		String filePath = sysTemPath + uploadPath + "/" + timeTag + ".xls";
		File tempFile = new File(sysTemPath + uploadPath);
		if (!tempFile.exists()) {
			tempFile.mkdir();
		}
		FormFile file = accessoryForm.getAccessoryName();
		try {
			InputStream stream = file.getInputStream(); // 把文件读入
			OutputStream outputStream = new FileOutputStream(filePath); // 建立一个上传文件的输出流

			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.close();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("fail");
		}
		// 然后把文件的每一条数据读入到form中
		Workbook workbook = null;
		ArrayList formList = new ArrayList();
		ArrayList numberList = new ArrayList();
		InputStream ins = new FileInputStream(filePath);
		try {
			// 构建Workbook对象, 只读Workbook对象
			// 直接从本地文件创建Workbook, 从输入流创建Workbook

			workbook = Workbook.getWorkbook(ins);
			Sheet dataSheet = workbook.getSheet(0);

			// 读取数据
			for (int i = 1; i < dataSheet.getRows(); i++) {
				TawPartnerCar temp = new TawPartnerCar();
				temp.setDeleted("0");
				if (dataSheet.getCell(0, i).getContents() != null
						&& !"".equals(dataSheet.getCell(0, i).getContents())) {
					if (mgr.isunique(dataSheet.getCell(0, i).getContents())
							.booleanValue()) {
						temp.setCar_number(dataSheet.getCell(0, i)
								.getContents().trim());
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(1));
						continue;
					}
				} else {
					break;
					// numberList.add(new Integer(i+1));
					// numberList.add(new Integer(1));
					// continue;
				}
				if (dataSheet.getCell(1, i).getContents() != null
						&& !"".equals(dataSheet.getCell(1, i).getContents())) {
					if (mgr.isunique(dataSheet.getCell(1, i).getContents())
							.booleanValue()){
						String carform="";
						String cform=dataSheet.getCell(1, i).getContents().trim();
						Set set=carFormMap.entrySet();
						Iterator iterator = set.iterator();   
						while(iterator.hasNext()){
							Map.Entry mapentry = (Map.Entry)iterator.next();
							String tempcar=(String) mapentry.getKey();
							if(tempcar.equals(cform)){
								carform=(String) mapentry.getValue();
							}
						}
						temp.setCarForm(carform);
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(2));
						continue;
					}
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(2));
					continue;
				}
				if (dataSheet.getCell(2, i).getContents() != null
						&& !"".equals(dataSheet.getCell(2, i).getContents())) {
					if (mgr.isunique(dataSheet.getCell(2, i).getContents())
							.booleanValue()) {
//						DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
//						Date date = df2.parse(dataSheet.getCell(2, i)
//								.getContents().trim());
//						temp.setPurchaseTime(date+"");
						
						DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String timeexcel = dataSheet.getCell(2, i).getContents()
								.trim();
						String dateexcel = timeexcel.substring(0, 10);
						String dateuse = "";
						String timeuse = "";
						String[] dd = dateexcel.split("/");
						for (int tt = 0; tt < dd.length; tt++) {
							dateuse += dd[tt] + "-";
						}
						dateuse = dateuse.substring(0, dateuse.length() - 1);
						timeuse += dateuse + " "
								+ timeexcel.substring(11, timeexcel.length());
						String date = df2.format(df2.parse(timeuse));
						
						
						
						
						temp.setPurchaseTime(date+"");
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(3));
						continue;
					}
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(3));
					continue;
				}

				if (dataSheet.getCell(3, i).getContents() != null
						&& !"".equals(dataSheet.getCell(3, i).getContents())) {
					
					DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					String timeexcel = dataSheet.getCell(3, i).getContents()
							.trim();
					String dateexcel = timeexcel.substring(0, 10);
					String dateuse = "";
					String timeuse = "";
					String[] dd = dateexcel.split("/");
					for (int tt = 0; tt < dd.length; tt++) {
						dateuse += dd[tt] + "-";
					}
					dateuse = dateuse.substring(0, dateuse.length() - 1);
					timeuse += dateuse + " "
							+ timeexcel.substring(11, timeexcel.length());
					Date date = df2.parse(timeuse);
					temp.setStart_time(date);
					// temp.setStart_time(StaticMethod.String2Cal(dataSheet.getCell(3,
					// i).getContents().trim()).getGregorianChange());
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(4));
					continue;
				}
				if (dataSheet.getCell(4, i).getContents() != null
						&& !"".equals(dataSheet.getCell(4, i).getContents())) {
					
					DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					String timeexcel = dataSheet.getCell(4, i).getContents()
					.trim();
					String dateexcel = timeexcel.substring(0, 10);
					String dateuse = "";
					String timeuse = "";
					String[] dd = dateexcel.split("/");
					for (int tt = 0; tt < dd.length; tt++) {
						dateuse += dd[tt] + "-";
					}
					dateuse = dateuse.substring(0, dateuse.length() - 1);
					timeuse += dateuse + " "
					+ timeexcel.substring(11, timeexcel.length());
					Date date = df2.parse(timeuse);
					temp.setEndTime(date);
//					temp.setStart_time(date);
					// temp.setStart_time(StaticMethod.String2Cal(dataSheet.getCell(3,
					// i).getContents().trim()).getGregorianChange());
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(5));
					continue;
				}
				if (dataSheet.getCell(5, i).getContents() != null
						&& !"".equals(dataSheet.getCell(5, i).getContents())) {
					
					DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					String timeexcel = dataSheet.getCell(5, i).getContents()
					.trim();
					String dateexcel = timeexcel.substring(0, 10);
					String dateuse = "";
					String timeuse = "";
					String[] dd = dateexcel.split("/");
					for (int tt = 0; tt < dd.length; tt++) {
						dateuse += dd[tt] + "-";
					}
					dateuse = dateuse.substring(0, dateuse.length() - 1);
					timeuse += dateuse + " "
					+ timeexcel.substring(11, timeexcel.length());
					System.out.println(timeuse);
					Date date = df2.parse(timeuse);
					temp.setAnnualCheckData(date);
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(6));
					continue;
				}
				if (dataSheet.getCell(6, i).getContents() != null
						&& !"".equals(dataSheet.getCell(6, i).getContents())) {
					
					DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					String timeexcel = dataSheet.getCell(6, i).getContents()
					.trim();
					String dateexcel = timeexcel.substring(0, 10);
					String dateuse = "";
					String timeuse = "";
					String[] dd = dateexcel.split("/");
					for (int tt = 0; tt < dd.length; tt++) {
						dateuse += dd[tt] + "-";
					}
					dateuse = dateuse.substring(0, dateuse.length() - 1);
					timeuse += dateuse + " "
					+ timeexcel.substring(11, timeexcel.length());
					System.out.println(timeuse);
					Date date = df2.parse(timeuse);
					temp.setCarOutDate(df2.format(date)+"");
//					temp.setStart_time(date);
					// temp.setStart_time(StaticMethod.String2Cal(dataSheet.getCell(3,
					// i).getContents().trim()).getGregorianChange());
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(7));
					continue;
				}
				if (dataSheet.getCell(7, i).getContents() != null
						&& !"".equals(dataSheet.getCell(7, i).getContents())) {
					if (mgr.isunique(dataSheet.getCell(7, i).getContents())
							.booleanValue()){
						temp.setBrand(dataSheet.getCell(7, i)
								.getContents().trim());
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(8));
						continue;
					}
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(8));
					continue;
				}
				if (dataSheet.getCell(8, i).getContents() != null
						&& !"".equals(dataSheet.getCell(8, i).getContents())) {
					if (mgr.isunique(dataSheet.getCell(8, i).getContents())
							.booleanValue()){
						temp.setDrivingLicense(dataSheet.getCell(8, i)
								.getContents().trim());
//						temp.setBrand(dataSheet.getCell(8, i)
//								.getContents().trim());
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(9));
						continue;
					}
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(9));
					continue;
				}
				if (dataSheet.getCell(9, i).getContents() != null
						&& !"".equals(dataSheet.getCell(9, i).getContents())) {
					if (mgr.isunique(dataSheet.getCell(9, i).getContents())
							.booleanValue()){
						temp.setFactory(dataSheet.getCell(9, i)
								.getContents().trim());
//						temp.setBrand(dataSheet.getCell(8, i)
//								.getContents().trim());
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(10));
						continue;
					}
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(10));
					continue;
				}
				if (dataSheet.getCell(10, i).getContents() != null
						&& !"".equals(dataSheet.getCell(10, i).getContents())) {
					if (mgr.isunique(dataSheet.getCell(10, i).getContents())
							.booleanValue()){
						temp.setEngineNo(dataSheet.getCell(10, i)
								.getContents().trim());
//						temp.setBrand(dataSheet.getCell(8, i)
//								.getContents().trim());
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(11));
						continue;
					}
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(11));
					continue;
				}
				if (dataSheet.getCell(11, i).getContents() != null
						&& !"".equals(dataSheet.getCell(11, i).getContents())) {
					if (mgr.isunique(dataSheet.getCell(11, i).getContents())
							.booleanValue()){
						temp.setAirDisplacement(dataSheet.getCell(11, i)
								.getContents().trim());
//						temp.setBrand(dataSheet.getCell(8, i)
//								.getContents().trim());
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(12));
						continue;
					}
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(12));
					continue;
				}
				if (dataSheet.getCell(12, i).getContents() != null
						&& !"".equals(dataSheet.getCell(12, i).getContents())) {
					if (mgr.isunique(dataSheet.getCell(12, i).getContents())
							.booleanValue()){
						temp.setStartUseMilemeter(dataSheet.getCell(12, i)
								.getContents().trim());
//						temp.setBrand(dataSheet.getCell(8, i)
//								.getContents().trim());
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(13));
						continue;
					}
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(13));
					continue;
				}
				if (dataSheet.getCell(13, i).getContents() != null
						&& !"".equals(dataSheet.getCell(13, i).getContents())) {
					if (mgr.isunique(dataSheet.getCell(13, i).getContents())
							.booleanValue()){
						String carInfo="";
						String cardate=dataSheet.getCell(13, i).getContents().trim();
						Set set=carStateMap.entrySet();
						Iterator iterator = set.iterator();   
						while(iterator.hasNext()){
							Map.Entry mapentry = (Map.Entry)iterator.next();
							String tempcar=(String) mapentry.getKey();
							if(tempcar.equals(cardate)){
								carInfo=(String) mapentry.getValue();
							}
						}
						
						temp.setCarState(carInfo);
//						temp.setBrand(dataSheet.getCell(8, i)
//								.getContents().trim());
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(14));
						continue;
					}
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(14));
					continue;
				}
				if (dataSheet.getCell(14, i).getContents() != null
						&& !"".equals(dataSheet.getCell(14, i).getContents())) {
					if (mgr.isunique(dataSheet.getCell(14, i).getContents())
							.booleanValue()){
						String carInfo="";
						String cardate=dataSheet.getCell(14, i).getContents().trim();
						Set set=isPrepareMap.entrySet();
						Iterator iterator = set.iterator();   
						while(iterator.hasNext()){
							Map.Entry mapentry = (Map.Entry)iterator.next();
							String tempcar=(String) mapentry.getKey();
							if(tempcar.equals(cardate)){
								carInfo=(String) mapentry.getValue();
							}
						}
						temp.setIsPrepare(carInfo);
//						temp.setBrand(dataSheet.getCell(8, i)
//								.getContents().trim());
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(15));
						continue;
					}
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(15));
					continue;
				}
				if (dataSheet.getCell(15, i).getContents() != null
						&& !"".equals(dataSheet.getCell(15, i).getContents())) {
					if (mgr.isunique(dataSheet.getCell(15, i).getContents())
							.booleanValue()){
						String carInfo="";
						String cardate=dataSheet.getCell(15, i).getContents().trim();
						Set set=ownershipTypeMap.entrySet();
						Iterator iterator = set.iterator();   
						while(iterator.hasNext()){
							Map.Entry mapentry = (Map.Entry)iterator.next();
							String tempcar=(String) mapentry.getKey();
							if(tempcar.equals(cardate)){
								carInfo=(String) mapentry.getValue();
							}
						}
						temp.setOwnershipType(carInfo);
//						temp.setBrand(dataSheet.getCell(8, i)
//								.getContents().trim());
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(16));
						continue;
					}
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(16));
					continue;
				}
				if (dataSheet.getCell(16, i).getContents() != null
						&& !"".equals(dataSheet.getCell(16, i).getContents())) {
					if (mgr.isunique(dataSheet.getCell(16, i).getContents())
							.booleanValue()){
						
						String carInfo="";
						String cardate=dataSheet.getCell(16, i).getContents().trim();
						Set set=carUseMap.entrySet();
						Iterator iterator = set.iterator();   
						while(iterator.hasNext()){
							Map.Entry mapentry = (Map.Entry)iterator.next();
							String tempcar=(String) mapentry.getKey();
							if(tempcar.equals(cardate)){
								carInfo=(String) mapentry.getValue();
							}
						}
						temp.setUse(carInfo);
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(17));
						continue;
					}
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(17));
					continue;
				}
				if (dataSheet.getCell(17, i).getContents() != null
						&& !"".equals(dataSheet.getCell(17, i).getContents())) {
					if (mgr.isunique(dataSheet.getCell(17, i).getContents())
							.booleanValue()){
						
						String carInfo="";
						String cardate=dataSheet.getCell(17, i).getContents().trim();
						Set set=carDriveTypeMap.entrySet();
						Iterator iterator = set.iterator();   
						while(iterator.hasNext()){
							Map.Entry mapentry = (Map.Entry)iterator.next();
							String tempcar=(String) mapentry.getKey();
							if(tempcar.equals(cardate)){
								carInfo=(String) mapentry.getValue();
							}
						}
						temp.setDriveType(carInfo);
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(18));
						continue;
					}
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(18));
					continue;
				}
				if (dataSheet.getCell(18, i).getContents() != null
						&& !"".equals(dataSheet.getCell(18, i).getContents())) {
					if (mgr.isunique(dataSheet.getCell(18, i).getContents())
							.booleanValue()){
						
						String carInfo="";
						String cardate=dataSheet.getCell(18, i).getContents().trim();
						Set set=serviceProfessionalMap.entrySet();
						Iterator iterator = set.iterator();   
						while(iterator.hasNext()){
							Map.Entry mapentry = (Map.Entry)iterator.next();
							String tempcar=(String) mapentry.getKey();
							if(tempcar.equals(cardate)){
								carInfo=(String) mapentry.getValue();
							}
						}
						temp.setServiceProfessional(carInfo);
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(19));
						continue;
					}
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(19));
					continue;
				}
				if (dataSheet.getCell(19, i).getContents() != null
						&& !"".equals(dataSheet.getCell(19, i).getContents())) {
					if (mgr.isunique(dataSheet.getCell(19, i).getContents())
							.booleanValue()){
						
						String carInfo="";
						String cardate=dataSheet.getCell(19, i).getContents().trim();
						Set set=carCategoryMap.entrySet();
						Iterator iterator = set.iterator();   
						while(iterator.hasNext()){
							Map.Entry mapentry = (Map.Entry)iterator.next();
							String tempcar=(String) mapentry.getKey();
							if(tempcar.equals(cardate)){
								carInfo=(String) mapentry.getValue();
							}
						}
						temp.setCategory(carInfo);
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(20));
						continue;
					}
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(20));
					continue;
				}
				if (dataSheet.getCell(20, i).getContents() != null
						&& !"".equals(dataSheet.getCell(20, i).getContents())) {
					if (mgr.isunique(dataSheet.getCell(20, i).getContents())
							.booleanValue()){
						String cardate=dataSheet.getCell(20, i).getContents().trim();
						temp.setRemark(cardate);
					} else {
						numberList.add(new Integer(i + 1));
						numberList.add(new Integer(21));
						continue;
					}
				} else {
					numberList.add(new Integer(i + 1));
					numberList.add(new Integer(21));
					continue;
				}
				TawSystemSessionForm sessionForm = this.getUser(request);
				String addMan = sessionForm.getUserid();
				String addDept1=sessionForm.getDeptid();
				Date addTime = new Date();
				String connectType = sessionForm.getContactMobile();
				temp.setAddMan(addMan);
				temp.setAddTime(addTime);
				temp.setConnectType(connectType);
				temp.setDeleted("0");
				temp.setBigpartnerid(bigpartnerid);
				temp.setPartnerid(partnerid);
				temp.setAddDept(addDept1);
				temp.setArea_id(areaid);
				temp.setDept_id(deptid);
				formList.add(temp);
			}
			for (int i = 0; i < formList.size(); i++) {
				mgr.saveTawPartnerCar((TawPartnerCar) formList.get(i));
			}
			String problemRow = "";
			for (int i = 0; i < numberList.size(); i++) {
				problemRow += "," + numberList.get(i);
			}
			String str = "";
			if (!"".equals(problemRow)) {
				String sub = problemRow.substring(1, problemRow.length());
				String[] array = sub.split(",");
				str = "未成功录入！以下为不合法的未录入的信息：" + "<br>";
				for (int i = 0; i < array.length; i++) {

					str = str + "第" + array[i] + "行" + "第" + array[i + 1]
							+ "列;" + "<br>";
					i++;
				}
			} else {
				str = "成功录入所有信息";
			}
			request.setAttribute("problemRow", str);
		} catch (Exception e) {
			workbook.close();

			File fileDel = new File(filePath);
			if (fileDel.exists())
				fileDel.delete();
			e.printStackTrace();
			return mapping.findForward("fail");
		} finally {
			workbook.close();
			ins.close();
			File fileDel = new File(filePath);
			if (fileDel.exists())
				fileDel.delete();
		}

		return mapping.findForward("xlsInput");

		// return search(mapping, accessoryForm, request, response);

	}

	public ActionForward toXls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("problemRow", "");
		return mapping.findForward("xlsInput");
	}

	public ActionForward changeDep(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		List deptListId = areaDeptTreeMgr.getNextLevelAreaDeptTrees(id);
		List deptListName = new ArrayList();
		for (int i = 0; i < deptListId.size(); i++) {
			String tempId = ((AreaDeptTree) (deptListId.get(i))).getNodeId();
			deptListName.add(areaDeptTreeMgr.id2Name(tempId));
		}
		StringBuffer d_list = new StringBuffer();
		for (int i = 0; i < deptListId.size(); i++) {
			deptListName.add(areaDeptTreeMgr
					.id2Name(((AreaDeptTree) (deptListId.get(i))).getNodeId()));
			d_list.append(","
					+ ((AreaDeptTree) (deptListId.get(i))).getNodeId());
			d_list.append("," + deptListName.get(i));

		}

		String aaa = d_list.toString();
		// aaa=aaa.substring(1, aaa.length());
		response.setContentType("text/html; charset=GBK");
		// response.getWriter().print(aaa);
		response.getWriter().flush();
		return null;
	}

	public ActionForward changeDep2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		List deptListId = areaDeptTreeMgr.getNextLevelAreaDeptTrees(id);
		List deptListName = new ArrayList();
		// for(int i=0;i<deptListId.size();i++){
		// String tempId = ((AreaDeptTree)(deptListId.get(i))).getNodeId();
		// deptListName.add(areaDeptTreeMgr.id2Name(tempId));
		// }
		StringBuffer d_list = new StringBuffer();
		d_list.append(",--请选择--");
		for (int i = 0; i < deptListId.size(); i++) {
			deptListName.add(areaDeptTreeMgr
					.id2Name(((AreaDeptTree) (deptListId.get(i))).getNodeId()));
			d_list.append(","
					+ ((AreaDeptTree) (deptListId.get(i))).getNodeId());
			d_list.append("," + deptListName.get(i));

		}

		String aaa = d_list.toString();
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(aaa);
		response.getWriter().flush();
		return null;
	}

	public ActionForward outPutModel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TawSystemSessionForm saveSessionBeanForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		if (saveSessionBeanForm == null) {
			return mapping.findForward("timeout");
		}

		try {
			String sysTemPath = request.getRealPath("/");
			String url = sysTemPath + "/partner/model/partnercar.xls";
			File file = new File(url);
			// 读到流中
			InputStream inStream = new FileInputStream(file);// 文件的存放路径
			// 设置输出的格式
			response.reset();
			response.setContentType("application/x-msdownload;charset=GBK");
			response.setCharacterEncoding("UTF-8");
			String fileName = URLEncoder.encode(file.getName(), "UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(fileName.getBytes("UTF-8"), "GBK"));

			// 循环取出流中的数据
			byte[] b = new byte[128];
			int len;
			while ((len = inStream.read(b)) > 0)
				response.getOutputStream().write(b, 0, len);
			inStream.close();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			return mapping.findForward("fail");
		}

		return null;
	}
	/**
	 * 分页显示车辆管理列表，支持Atom方式接入Portal
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * public ActionForward search4Atom(ActionMapping mapping, ActionForm form,
	 * HttpServletRequest request, HttpServletResponse response) throws
	 * Exception { try { // --------------用于分页，得到当前页号------------- final Integer
	 * pageIndex = new Integer(request .getParameter("pageIndex")); final
	 * Integer pageSize = new Integer(request .getParameter("pageSize"));
	 * TawPartnerCarMgr tawPartnerCarMgr = (TawPartnerCarMgr)
	 * getBean("tawPartnerCarMgr"); Map map = (Map)
	 * tawPartnerCarMgr.getTawPartnerCars(pageIndex, pageSize, ""); List list =
	 * (List) map.get("result"); TawPartnerCar tawPartnerCar = new
	 * TawPartnerCar();
	 * 
	 * //创建ATOM源 Factory factory = Abdera.getNewFactory(); Feed feed =
	 * factory.newFeed();
	 *  // 分页 for (int i = 0; i < list.size(); i++) { tawPartnerCar =
	 * (TawPartnerCar) list.get(i);
	 *  // TODO 请按照下面的实例给entry赋值 Entry entry = feed.insertEntry();
	 * entry.setTitle("<a href='" + request.getScheme() + "://" +
	 * request.getServerName() + ":" + request.getServerPort() +
	 * request.getContextPath() +
	 * "/tawPartnerCar/tawPartnerCars.do?method=edit&id=" +
	 * tawPartnerCar.getId() + "' target='_blank'>" + display name for list + "</a>");
	 * entry.setSummary(summary); entry.setContent(content);
	 * entry.setLanguage(language); entry.setText(text);
	 * entry.setRights(tights);
	 *  // 以下三项需要传入Date类型或String类型（yyyy-MM-dd）的参数 entry.setUpdated(new
	 * java.util.Date()); entry.setPublished(new java.util.Date());
	 * entry.setEdited(new java.util.Date());
	 *  // 为person的name属性赋值，entry.addAuthor可以随意赋值 Person person =
	 * entry.addAuthor(userId); person.setName(userName); }
	 *  // 每页显示条数 feed.setText(map.get("total").toString()); OutputStream os =
	 * response.getOutputStream(); PrintStream ps = new PrintStream(os);
	 * feed.getDocument().writeTo(ps); } catch (Exception e) {
	 * e.printStackTrace(); } return null; }
	 */
	
	
	/*
	 * 统计
	 */
	public ActionForward goToShowPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToShowPage");
	}

	/*
	 * 统计
	 */
	public ActionForward staffContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String rows[] = StaticMethod.nullObject2String(
				request.getParameter("deleteIdss"), "").split(";");
		String checkString = StaticMethod.nullObject2String(request
				.getParameter("checks"), "");
		String search = "";
		String group = "";
		// get the search column .change the '0' to '.'
		for (int i = 0; i < rows.length; i++) {
			String row = "";
			if (rows[i].contains("TypeLikedict")) {
				row = rows[i].substring(0, rows[i].length()
						- "TypeLikedict".length());
				row = row.replace("0", ".");
			} else {
				row = rows[i].replace("0", ".");
			}

			if (i == rows.length - 1) {
				search += row + " as " + rows[i];
				group += row;
			} else {
				search += row + " as " + rows[i] + ",";
				group += row + ",";
			}

		}
		// get the text to where condition.
		String whereCondition = " ";
		String de0areaname = request.getParameter("de0area_nameT");
		String ct0brand = request.getParameter("ct0brandT");
		String ct0carOutDate = request.getParameter("ct0carOutDateT");
		String ct0carState = request.getParameter("ct0carStateT");
		String whereString="where ct.id is not null";
		String andString="and";
		
		if (!"".equals(de0areaname)&&null!=de0areaname) {
			whereCondition += " "+andString+" "+"de.area_name like '%" + de0areaname + "%'";
			
		}
		if (!"".equals(ct0brand)&&null!=ct0brand) {
			whereCondition += " "+andString+" "+" ct.brand like '%" + ct0brand + "%'";
			
		}
		if (!"".equals(ct0carOutDate)&&null!=ct0carOutDate) {
			whereCondition += " "+andString+" "+"ct.carOutDate like '%"
					+ ct0carOutDate + "%'";
		
		}
		
        if (!"".equals(ct0carState)&&null!=ct0carState) {
			whereCondition += " "+andString+" "+"ct.carState ="
					+"'"+ ct0carState+"'" ;
			
		}


		String searchSql = "select " + search + 
				 "  ,count(ct.id) from pnr_dept de, pnr_partner_car ct  ,(select name,id,deleted from pnr_dept where id = interface_head_id) as dt where de.id =ct.partnerid and  de.deleted='0' and dt.deleted='0' and  ct.bigpartnerid=dt.id  " 
			+ whereCondition+ " group by  "+ group +  "  order by  " + group;

		List<String> headList = new ArrayList<String>();
		for (int i = 0; i < rows.length; i++) {
			if ("de0area_name".equals(rows[i])) {
				headList.add("地域");
			} else if ("ct0carOutDate".equals(rows[i])) {
				headList.add("出厂日期");
			} else if ("ct0brand".equals(rows[i])) {
				headList.add("品牌");
			} else if ("ct0carStateTypeLikedict".equals(rows[i])) {
				headList.add("车辆状态");
			}

		}

		headList.add("总数");

		List<List<TdObjModel>> tempList = TableHelper.verticalGrowp(rows,
				searchSql,
				"/partner/baseinfo/tawPartnerCars.do?method=searchInto");
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkList", checkString);

		return mapping.findForward("goToShowPage");
	}

	/*
	 * 统计数据钻取
	 */
	public ActionForward searchInto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
		.getInstance().getBean("commonSpringJdbcService");
		TawParCarService tawParCarService = (TawParCarService) ApplicationContextHolder
				.getInstance().getBean("tawParCarService");
		
		Map paramMap = request.getParameterMap();
		String search ="";
		if(paramMap.containsKey("de0area_name")){
			search+= " and de.area_name= '"+new String(((String[])paramMap.get("de0area_name"))[0].toString().getBytes("ISO-8859-1"),"utf-8")+"'";
		}
		if(paramMap.containsKey("ct0carOutDate")){
			search+= " and ct.carOutDate='"+new String(((String[])paramMap.get("ct0carOutDate"))[0].toString().getBytes("ISO-8859-1"),"utf-8")+"'";
		}
		if(paramMap.containsKey("ct0brand")){
			search+= " and ct.brand='"+new String(((String[])paramMap.get("ct0brand"))[0].toString().getBytes("ISO-8859-1"),"utf-8")+"'";
		}
		if(paramMap.containsKey("ct0carStateTypeLikedict")){
			search+= " and ct.carState='"+new String(((String[])paramMap.get("ct0carStateTypeLikedict"))[0].toString().getBytes("ISO-8859-1"),"utf-8")+"'";
			
		}
		
		String searchSql="select ct.* from  pnr_dept as de, pnr_partner_car ct  , (select name,id,deleted from pnr_dept where id = interface_head_id) as dt "+ 
		"where de.id =ct.partnerid and  de.deleted='0' and dt.deleted='0'and  ct.bigpartnerid=dt.id" +
				search;
		
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		List  list = new ArrayList();
		for (ListOrderedMap listOrderedMap : resultList) {
			TawPartnerCar  PartnerCar= new TawPartnerCar();
			PartnerCar.setId((String)listOrderedMap.get("id"));
			PartnerCar.setCar_number((String)listOrderedMap.get("car_number"));
			PartnerCar.setFactory((String)listOrderedMap.get("factory"));
			PartnerCar.setStart_time((Date)listOrderedMap.get("start_time"));
			PartnerCar.setDept_id((String)listOrderedMap.get("dept_id"));
			PartnerCar.setArea_id((String)listOrderedMap.get("area_id"));
			PartnerCar.setPartnerid((String)listOrderedMap.get("partnerid"));
			list.add(PartnerCar);
		}
		request.setAttribute("tawPartnerCarList", list);
		request.setAttribute("resultSize", list.size());
		return mapping.findForward("partnerCarList");
		
		
	}
}