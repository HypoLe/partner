package com.boco.eoms.materials.webapp.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.util.ParamEncoder;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.materials.model.BillMaterial;
import com.boco.eoms.materials.model.Material;
import com.boco.eoms.materials.model.Store;
import com.boco.eoms.materials.service.BillMaterialManager;
import com.boco.eoms.materials.service.MaterialManager;
import com.boco.eoms.materials.service.StoreAllotManager;
import com.boco.eoms.materials.service.StoreInputManager;
import com.boco.eoms.materials.service.StoreManager;
import com.boco.eoms.materials.service.StoreOutManager;
import com.boco.eoms.sheet.base.util.SheetAttributes;

public class BillMaterialAction extends BaseAction {
	/**
	 * 展示 入库材料 列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialManager mgr = (MaterialManager) getBean("materialManager");
		String flag = request.getParameter("flag");
		List<Material> list = mgr.getMaterial();
		// 保存参数
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		String storeSid = request.getParameter("storeSid");
		String outStoreSid = request.getParameter("outStoreSid");
		String inputStoreSid = request.getParameter("inputStoreSid");
		String storeNum = request.getParameter("storeNum");
		request.setAttribute("storeNum", storeNum);
		
		String storeCompany = request.getParameter("storeCompany");
		String storeBillingDate = request.getParameter("storeBillingDate");
		String storeRequisitioner = request.getParameter("storeRequisitioner");
		String storeOriginalNum = request.getParameter("storeOriginalNum");
		String remark = request.getParameter("remark");
		String storeDepartment = request.getParameter("storeDepartment");
		String storeMakeBillDate =request.getParameter("storeMakeBillDate");
		String storeMakeBillPeople =request.getParameter("storeMakeBillPeople");
		
		try {
			if(storeMakeBillPeople !=null){
				storeMakeBillPeople = URLDecoder.decode(storeMakeBillPeople, "utf-8");
			}
			if(storeMakeBillDate !=null){
				storeMakeBillDate = URLDecoder.decode(storeMakeBillDate, "utf-8");
			}
			if(storeCompany !=null){
				storeCompany = URLDecoder.decode(storeCompany, "utf-8");
			}
			if(storeBillingDate !=null){
				storeBillingDate = URLDecoder.decode(storeBillingDate, "utf-8");
			}
			if(storeRequisitioner !=null){
				storeRequisitioner = URLDecoder.decode(storeRequisitioner, "utf-8");
			}
			if(storeOriginalNum !=null){
				storeOriginalNum = URLDecoder.decode(storeOriginalNum, "utf-8");
			}
			if(remark != null){
				remark = URLDecoder.decode(remark, "utf-8");
			}
			if(storeDepartment !=null){
				storeDepartment = URLDecoder.decode(storeDepartment, "utf-8");
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!"".equals(storeSid)) {
			if (storeMgr.getStore(storeSid) != null) {
				request.setAttribute("storeName", storeMgr.getStore(storeSid)
						.getName());
			}
		}
		if (!"".equals(outStoreSid)) {
			if (storeMgr.getStore(outStoreSid) != null) {
				request.setAttribute("outStoreSname", storeMgr.getStore(
						outStoreSid).getName());
			}
		}
		if (!"".equals(inputStoreSid)) {
			if (storeMgr.getStore(inputStoreSid) != null) {
				request.setAttribute("inputStoreSname", storeMgr.getStore(
						inputStoreSid).getName());
			}
		}
		request.setAttribute("storeMakeBillPeople", storeMakeBillPeople);
		request.setAttribute("storeBillingDate", storeBillingDate);
		request.setAttribute("storeDepartment", storeDepartment);
		request.setAttribute("storeCompany", storeCompany);
		request.setAttribute("storeSid", storeSid);
		request.setAttribute("outStoreSid", outStoreSid);
		request.setAttribute("inputStoreSid", inputStoreSid);
		request.setAttribute("storeRequisitioner", storeRequisitioner);
		request.setAttribute("storeOriginalNum", storeOriginalNum);
		request.setAttribute("remark", remark);
		request.setAttribute("storeMakeBillDate", storeMakeBillDate);
		
		request.setAttribute("storeInputId", request.getParameter("storeInputId"));

		request.setAttribute("list", list);
		request.setAttribute("flag", flag);
		if (flag.length() > 2) {
			if ("1_alter".equals(flag)) {
				return mapping.findForward("inputView");
			} else if ("-1_alter".equals(flag)) {
				return mapping.findForward("outView");
			} else {
				return mapping.findForward("allotView");
			}
		} else {
			if ("1".equals(flag)) {
				return mapping.findForward("inputView");
			} else if ("-1".equals(flag)) {
				return mapping.findForward("outView");
			} else {
				return mapping.findForward("allotView");
			}
		}
	}

	public ActionForward selectBillView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("selectBill");
	}

	/**
	 * 根据条件查询出库 入库 调拨单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward select(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		StoreInputManager inputMgr = (StoreInputManager) getBean("storeInputManager");
		StoreOutManager outMgr = (StoreOutManager) getBean("storeOutManager");
		StoreAllotManager allotMgr = (StoreAllotManager) getBean("storeAllotManager");

		String storeSid = request.getParameter("storeSid");
		// StoreManager storeMgr = (StoreManager) getBean("storeManager");
		// Store store = storeMgr.getStore(storeSid);
		// String storeSname = store.getName();
		String start = StaticMethod.nullObject2String(request
				.getParameter("start"));
		String end = StaticMethod
				.nullObject2String(request.getParameter("end"));
		String storeNum = request.getParameter("storeNum");
		String storeBillType = request.getParameter("storeBillType");
		String storeMakeBillPeople = request
				.getParameter("storeMakeBillPeople");
		String storeCompany = request.getParameter("storeCompany");
		String storeOriginalNum = request.getParameter("storeOriginalNum");

		Integer pageSize = ((SheetAttributes) ApplicationContextHolder
				.getInstance().getBean("SheetAttributes")).getPageSize();
		String pageIndexName = new ParamEncoder("list")
				.encodeParameterName("p");
		Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request
				.getParameter(pageIndexName)) ? 0 : Integer.parseInt(request
				.getParameter(pageIndexName)) - 1);
		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"list")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}

		StringBuffer sb = new StringBuffer();

		sb.append(" where 1=1 ");
		if (!"".equals(storeOriginalNum)) {
			sb.append("  and storeOriginalNum = '").append(storeOriginalNum)
					.append("'");
		}
		if (!"".equals(storeCompany)) {
			sb.append("  and storeCompany = '").append(storeCompany)
					.append("'");
		}
		if (!"".equals(storeMakeBillPeople)) {
			sb.append("  and storeMakeBillPeople = '").append(
					storeMakeBillPeople).append("'");
		}
		if (!"".equals(storeNum)) {
			sb.append("  and storeNum = '").append(storeNum).append("'");
		}
		if (!"".equals(storeSid)) {
			sb.append("  and storeSid = '").append(storeSid).append("'");
		}
		// to_date('2006-04-08 00:00:01','yyyy-mm-dd hh24:mi:ss')
		if (!"".equals(start)) {
			sb.append("  and storeBillingDate >= to_date('").append(start)
					.append("','yyyy-mm-dd hh24:mi:ss')");
		}
		if (!"".equals(end)) {
			sb.append("  and storeBillingDate <= to_date('").append(end)
					.append("','yyyy-mm-dd hh24:mi:ss')");
		}
		Map map = null;
		String whereStr = sb.toString();
		if ("出库".equals(storeBillType)) {
			map = outMgr.getStoreOut(pageIndex, pageSize, whereStr);
		} else if ("入库".equals(storeBillType)) {
			map = inputMgr.getStoreInput(pageIndex, pageSize, whereStr);
		} else {
			map = allotMgr.getStoreAllot(pageIndex, pageSize, whereStr);
		}
		int total = StaticMethod.nullObject2int(map.get("total"));
		List list = (List) map.get("result");
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);

		request.setAttribute("list", list);
		return mapping.findForward("selectBill");
	}

	/**
	 * 工单号生成
	 * 
	 * @return String
	 */
	public String getStoreNum(String flag) {
		StoreInputManager inputMgr = (StoreInputManager) getBean("storeInputManager");
		StoreOutManager outMgr = (StoreOutManager) getBean("storeOutManager");
		StoreAllotManager allotMgr = (StoreAllotManager) getBean("storeAllotManager");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		StringBuffer sb = new StringBuffer();
		String end = "";
		if ("1".equals(flag)) {
			sb.append("IS-");
			end = inputMgr.getCountByTime().get("total")+1+"";
		} else if ("-1".equals(flag)) {
			sb.append("OS-");
			end = outMgr.getCountByTime().get("total")+1+"";
		} else {
			sb.append("DS-");
			end = allotMgr.getCountByTime().get("total")+1+"";
		}
		end = addZero(end);
		sb.append("01-").append(sdf.format(new Date())).append("-01-").append(
				end);
		return sb.toString();

	}

	/**
	 * 加零操作
	 * 
	 * @param str
	 * @return
	 */
	public String addZero(String str) {
		int max = 5;
		StringBuffer addZero = new StringBuffer();
		for (int i = 0; i < max - str.length(); i++) {
			addZero.append("0");
		}
		return addZero.append(str).toString();
	}

	/**
	 * 批量 添加 入库详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward addBillMaterial(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		BillMaterialManager mgr = (BillMaterialManager) getBean("billMaterialManager");
		MaterialManager material = (MaterialManager) getBean("materialManager");
		TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String username = tawSystemSessionForm.getUsername();
		String[] ids = request.getParameterValues("checkbox");
		// 出入库 标识 1入库 -1出库 0 调拨
		String flag = request.getParameter("flag");
		// 单号

		String billId = request.getParameter("storeNum");
		if (billId == null || "".equals(billId)) {
			billId = getStoreNum(flag);
		}
			for (int i = 0; i < ids.length; i++) {
				BillMaterial billMaterial = new BillMaterial();
				Material m = material.getMaterial(ids[i]);
				billMaterial.setTotalAmount(0);
				billMaterial.setMaterialId(ids[i]);
				billMaterial.setEncode(m.getEncode());
				billMaterial.setMaterialName(m.getName());
				billMaterial.setSpecification(m.getSpecification());
				billMaterial.setUnit(m.getUnit());
				billMaterial.setStoreBillId(billId);
				billMaterial.setMaterialAmount(0);
				mgr.saveBillMaterial(billMaterial);
			}
		
		// 链接表
		// StoreInventoryManager mger = (StoreInventoryManager)
		// getBean("storeInventoryManager");
		List<BillMaterial> list = mgr.getBillMateralByBillId(billId);
		request.setAttribute("list", list);
		request.setAttribute("listsize", list.size());
		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);

		// 保存参数
		String storeSid = request.getParameter("storeSid");
		String outStoreSid = request.getParameter("outStoreSid");
		String inputStoreSid = request.getParameter("inputStoreSid");
		if (!"".equals(storeSid)) {
			if (storeMgr.getStore(storeSid) != null) {
				request.setAttribute("storeName", storeMgr.getStore(storeSid)
						.getName());
			}
		}
		if (!"".equals(outStoreSid)) {
			if (storeMgr.getStore(outStoreSid) != null) {
				request.setAttribute("outStoreSname", storeMgr.getStore(
						outStoreSid).getName());
			}
		}
		if (!"".equals(inputStoreSid)) {
			if (storeMgr.getStore(inputStoreSid) != null) {
				request.setAttribute("inputStoreSname", storeMgr.getStore(
						inputStoreSid).getName());
			}
		}
		request.setAttribute("storeMakeBillPeople", request
				.getParameter("storeMakeBillPeople"));
		request.setAttribute("storeBillingDate", request
				.getParameter("storeBillingDate"));
		request.setAttribute("storeCompany", request
				.getParameter("storeCompany"));
		request.setAttribute("storeDepartment", request
				.getParameter("storeDepartment"));
		request.setAttribute("storeSid", request.getParameter("storeSid"));
		request.setAttribute("outStoreSid", outStoreSid);
		request.setAttribute("inputStoreSid", inputStoreSid);
		request.setAttribute("storeRequisitioner", request
				.getParameter("storeRequisitioner"));
		request.setAttribute("storeOriginalNum", request
				.getParameter("storeOriginalNum"));
		request.setAttribute("remark", request.getParameter("remark"));
		request.setAttribute("storeNum", billId);
		request.setAttribute("billId", billId);
		request.setAttribute("username", username);
		request.setAttribute("storeInputId", request.getParameter("storeInputId"));
		request.setAttribute("storeMakeBillDate", request.getParameter("storeMakeBillDate"));
		if (flag.length() > 2) {
			if ("1_alter".equals(flag)) {
				return mapping.findForward("showInputAlterView");
			} else if ("-1_alter".equals(flag)) {
				return mapping.findForward("showOutAlterView");
			} else {
				return mapping.findForward("showAllotAlterView");
			}
		} else {
			if ("1".equals(flag)) {
				return mapping.findForward("showAddStoreInputView");
			} else if ("-1".equals(flag)) {
				return mapping.findForward("showAddStoreOutView");
			} else {
				return mapping.findForward("showAddStoreAllotView");
			}
		}
	}

	/**
	 * 测试（暂时无用）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getParam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		@SuppressWarnings("unused")
		String id = request.getParameter("id");
		// String materialAmount = request.getParameter("materialAmount");
		return mapping.findForward("");
	}

	/**
	 * 根据id删除 入库详情单 数据 返回同一billId的list
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		BillMaterialManager mgr = (BillMaterialManager) getBean("billMaterialManager");
		TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String username = tawSystemSessionForm.getUsername();
		String flag = request.getParameter("flag");
		String id = request.getParameter("id");
		String billId = request.getParameter("billId");
		mgr.removeBillMaterial(id);
		List<BillMaterial> list = mgr.getBillMateralByBillId(billId);

		request.setAttribute("list", list);
		request.setAttribute("listsize", list.size());
		request.setAttribute("billId", billId);
		request.setAttribute("storeNum", billId);
		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		request.setAttribute("username", username);

		String storeSid = request.getParameter("storeSid");
		
		String outStoreSid = request.getParameter("outStoreSid");
		String inputStoreSid = request.getParameter("inputStoreSid");
		
		String storeCompany = request.getParameter("storeCompany");
		String storeBillingDate = request.getParameter("storeBillingDate");
		String storeRequisitioner = request.getParameter("storeRequisitioner");
		String storeOriginalNum = request.getParameter("storeOriginalNum");
		String remark = request.getParameter("remark");
		String storeDepartment = request.getParameter("storeDepartment");
		String storeMakeBillDate =request.getParameter("storeMakeBillDate");
		String storeMakeBillPeople =request.getParameter("storeMakeBillPeople");
		try {
			
				if(storeMakeBillPeople !=null){
					storeMakeBillPeople = URLDecoder.decode(storeMakeBillPeople, "utf-8");
				}
				if(storeMakeBillDate !=null){
					storeMakeBillDate = URLDecoder.decode(storeMakeBillDate, "utf-8");
				}
				if(storeCompany !=null){
					storeCompany = URLDecoder.decode(storeCompany, "utf-8");
				}
				if(storeBillingDate !=null){
					storeBillingDate = URLDecoder.decode(storeBillingDate, "utf-8");
				}
				if(storeRequisitioner !=null){
					storeRequisitioner = URLDecoder.decode(storeRequisitioner, "utf-8");
				}
				if(storeOriginalNum !=null){
					storeOriginalNum = URLDecoder.decode(storeOriginalNum, "utf-8");
				}
				if(remark != null){
					remark = URLDecoder.decode(remark, "utf-8");
				}
				if(storeDepartment !=null){
					storeDepartment = URLDecoder.decode(storeDepartment, "utf-8");
				}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!"".equals(storeSid)) {
			if (storeMgr.getStore(storeSid) != null) {
				request.setAttribute("storeName", storeMgr.getStore(storeSid)
						.getName());
			}
		}
		if (!"".equals(outStoreSid)) {
			if (storeMgr.getStore(outStoreSid) != null) {
				request.setAttribute("outStoreSname", storeMgr.getStore(
						outStoreSid).getName());
			}
		}
		if (!"".equals(inputStoreSid)) {
			if (storeMgr.getStore(inputStoreSid) != null) {
				request.setAttribute("inputStoreSname", storeMgr.getStore(
						inputStoreSid).getName());
			}
		}
		request.setAttribute("storeMakeBillPeople", storeMakeBillPeople);
		request.setAttribute("storeBillingDate", storeBillingDate);
		request.setAttribute("storeDepartment", storeDepartment);
		request.setAttribute("storeCompany", storeCompany);
		request.setAttribute("storeSid", storeSid);
		request.setAttribute("outStoreSid", outStoreSid);
		request.setAttribute("inputStoreSid", inputStoreSid);
		request.setAttribute("storeRequisitioner", storeRequisitioner);
		request.setAttribute("storeOriginalNum", storeOriginalNum);
		request.setAttribute("remark", remark);
		request.setAttribute("storeInputId", request.getParameter("storeInputId"));
		request.setAttribute("storeMakeBillDate", storeMakeBillDate);
		if (flag.length() > 2) {
			if ("1_alter".equals(flag)) {
				return mapping.findForward("showInputAlterView");
			} else if ("-1_alter".equals(flag)) {
				return mapping.findForward("showOutAlterView");
			} else {
				return mapping.findForward("showAllotAlterView");
			}
		} else {
			if ("1".equals(flag)) {
				return mapping.findForward("showAddStoreInputView");
			} else if ("-1".equals(flag)) {
				return mapping.findForward("showAddStoreOutView");
			} else {
				return mapping.findForward("showAddStoreAllotView");
			}
		}
	}
}
