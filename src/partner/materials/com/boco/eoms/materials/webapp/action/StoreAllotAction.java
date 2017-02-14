package com.boco.eoms.materials.webapp.action;

import java.text.ParseException;
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

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.materials.model.BillMaterial;
import com.boco.eoms.materials.model.Store;
import com.boco.eoms.materials.model.StoreAllot;
import com.boco.eoms.materials.model.StoreInventory;
import com.boco.eoms.materials.service.BillMaterialManager;
import com.boco.eoms.materials.service.StoreAllotManager;
import com.boco.eoms.materials.service.StoreInputManager;
import com.boco.eoms.materials.service.StoreInventoryManager;
import com.boco.eoms.materials.service.StoreManager;

/**
 * 调拨单处理Action
 * 
 * @author wanghongliang
 * 
 */
public class StoreAllotAction extends BaseAction {
	/**
	 * 跳转调拨单查询界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		StoreAllotManager mgr = (StoreAllotManager) getBean("storeAllotManager");
		StoreInputManager mgr2 = (StoreInputManager) getBean("storeInputManager");
		BillMaterialManager mgr3 = (BillMaterialManager) getBean("billMaterialManager");
		//获取登陆人
		String mateper = "";
		TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userid = tawSystemSessionForm.getUserid();
		String username = tawSystemSessionForm.getUsername();
		mateper = mgr2.getMatePer(userid);
		//返回时清空下挂货品
		String fh = request.getParameter("fh");
		String billId = request.getParameter("billId");
		if("1".equals(fh)){
			//新增入库单如果有材料但是点击返回则清空材料列表
			mgr3.removeBillMaterialall(billId);
		}
		
		Integer pageSize = 10;
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

		String whereStr = " where 1=1 ";

		Map map = mgr.getStoreAllot(pageIndex, pageSize, whereStr);
		int total = StaticMethod.nullObject2int(map.get("total"));
		List list = (List) map.get("result");
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("mateper", mateper);
		request.setAttribute("username", username);

		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		request.setAttribute("list", list);
		return mapping.findForward("storeAllot");
	}

	/**
	 * 跳转添加调拨单界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward showAddStoreAllotView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String username = tawSystemSessionForm.getUsername();
		request.setAttribute("username", username);
		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		return mapping.findForward("showAddStoreAllotView");
	}

	/**
	 * 保存 调拨单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		StoreInputManager mgr2 = (StoreInputManager) getBean("storeInputManager");
		String mateper = "";
		TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userid = tawSystemSessionForm.getUserid();
		mateper = mgr2.getMatePer(userid);
		// 先更新详细数据数量 价格
		BillMaterialManager bmgr = (BillMaterialManager) getBean("billMaterialManager");
		String sum = request.getParameter("sum");
		for (int i = 0; i < Integer.parseInt(sum); i++) {
			String id = request.getParameter("id_" + i);
			BillMaterial billMaterial = bmgr.getBillMaterial(id);
			String materialAmount = request.getParameter("materialAmount_" +

			i);
			String materialPrice = request.getParameter("materialPrice_" + i);
			double totalAmount = Integer.parseInt(materialAmount)
					* Double.parseDouble(materialPrice);
			
			String billremark = request.getParameter("remark" + i);
			
			billMaterial.setMaterialAmount(Integer.parseInt(materialAmount));
			billMaterial.setTotalAmount(totalAmount);
			billMaterial.setMaterialPrice(Double.parseDouble(materialPrice));
			billMaterial.setRemark(billremark);
			bmgr.updateBillMaterial(billMaterial);
		}
		
		
		
		StoreAllotManager mgr = (StoreAllotManager) getBean("storeAllotManager");
		StoreAllot storeAllot = new StoreAllot();
		StoreManager sm = (StoreManager) getBean("storeManager");
		// 调出仓库
		String outStoreSid = request.getParameter("outStoreSid");
		String outStoreSname = sm.getStore(outStoreSid).getName();
		storeAllot.setStoreSname(outStoreSname);
		storeAllot.setStoreSid(outStoreSid);
		// 调入仓库
		String inputStoreSid = request.getParameter("inputStoreSid");
		String inputStoreSname = sm.getStore(inputStoreSid).getName();
		storeAllot.setInputStoreId(inputStoreSid);
		storeAllot.setInputStoreName(inputStoreSname);
		// 往来单位
		String storeCompany = request.getParameter("storeCompany");
		storeAllot.setStoreCompany(storeCompany);
		// 经办人
		String storeRequisitioner = request.getParameter("storeRequisitioner");
		storeAllot.setStoreRequisitioner(storeRequisitioner);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date storeMakeBillDate = null;
		Date storeBillingDate = null;
		try {
			// 制单 开单 日期
			storeMakeBillDate = sdf.parse(new SimpleDateFormat("yyyy-MM-dd")
					.format(new Date()));
			storeBillingDate = sdf.parse(request
					.getParameter("storeBillingDate"));
			storeAllot.setStoreMakeBillDate(storeMakeBillDate);
			storeAllot.setStoreBillingDate(storeBillingDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 调拨 未审核
		String storeExamineStatus = "未审核";
		String storeBillType = "调拨";
		storeAllot.setStoreBillType(storeBillType);
		storeAllot.setStoreExamineStatus(storeExamineStatus);
		// 备注
		String storeRemark = request.getParameter("remark");
		storeAllot.setStoreRemark(storeRemark);
		// 制单人
		String storeMakeBillPeople = request.getParameter("username");
		storeAllot.setStoreMakeBillPeople(storeMakeBillPeople);
		// 单号
		String storeNum = request.getParameter("billId");
		storeAllot.setStoreNum(storeNum);
		// 原始单号
		String storeOriginalNum = request.getParameter("storeOriginalNum");
		storeAllot.setStoreOriginalNum(storeOriginalNum);
		// delete标识 默认为1
		int deleteFlag = 1;
		storeAllot.setDeleteFlag(deleteFlag);
		mgr.saveStoreAllot(storeAllot);
		Integer pageSize = 10;
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

		String whereStr = " where 1=1 ";

		Map map = mgr.getStoreAllot(pageIndex, pageSize, whereStr);
		int total = StaticMethod.nullObject2int(map.get("total"));
		List list = (List) map.get("result");
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("mateper", mateper);
		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		request.setAttribute("list", list);
		return mapping.findForward("storeAllot");
	}

	/**
	 * 删除调拨单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		StoreAllotManager mgr = (StoreAllotManager) getBean("storeAllotManager");
		StoreInputManager mgr2 = (StoreInputManager) getBean("storeInputManager");
		//获取人员权限值
		String mateper = "";
		TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userid = tawSystemSessionForm.getUserid();
		mateper = mgr2.getMatePer(userid);
		//获取人员权限值end
		
		String id = request.getParameter("id");
		StoreAllot storeAllot = mgr.getStoreAllot(id);
		// 逻辑删除
		storeAllot.setDeleteFlag(0);
		mgr.updateStoreAllot(storeAllot);
		Integer pageSize = 10;
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

		String whereStr = " where 1=1 ";

		Map map = mgr.getStoreAllot(pageIndex, pageSize, whereStr);
		int total = StaticMethod.nullObject2int(map.get("total"));
		List list = (List) map.get("result");
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);
		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		request.setAttribute("list", list);
		request.setAttribute("mateper", mateper);
		return mapping.findForward("storeAllot");
	}

	/**
	 * 查看详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		StoreAllotManager mgr = (StoreAllotManager) getBean("storeAllotManager");
		String id = request.getParameter("id");
		StoreAllot storeAllot = mgr.getStoreAllot(id);
		// 获取单号
		String billId = storeAllot.getStoreNum();
		BillMaterialManager bill = (BillMaterialManager) getBean("billMaterialManager");
		List<BillMaterial> list = bill.getBillMateralByBillId(billId);
		request.setAttribute("list", list);
		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		request.setAttribute("storeAllot", storeAllot);
		return mapping.findForward("showDetail");
	}

	/**
	 * 跳转至修改调拨单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward showAlterView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		StoreAllotManager mgr = (StoreAllotManager) getBean("storeAllotManager");
		String storeInputId = request.getParameter("id");
		// 根据id获取调拨单
		StoreAllot storeAllot = mgr.getStoreAllot(storeInputId);
		String storeNum = storeAllot.getStoreNum();
		request.setAttribute("storeNum",storeNum);
		request.setAttribute("storeMakeBillPeople",storeAllot.getStoreMakeBillPeople());
		request.setAttribute("storeMakeBillDate",storeAllot.getStoreMakeBillDate());
		request.setAttribute("storeBillingDate",storeAllot.getStoreBillingDate());
		request.setAttribute("storeCompany",storeAllot.getStoreCompany());
		request.setAttribute("storeOriginalNum",storeAllot.getStoreOriginalNum());
		request.setAttribute("remark",storeAllot.getStoreRemark());
		request.setAttribute("outStoreSid",storeAllot.getStoreSid());
		request.setAttribute("storeRequisitioner",storeAllot.getStoreRequisitioner());
		//出库单号
		request.setAttribute("storeInputId",storeInputId);
		request.setAttribute("outStoreSname",storeAllot.getStoreSname());
		request.setAttribute("inputStoreId",storeAllot.getInputStoreId());
		request.setAttribute("inputStoreName",storeAllot.getInputStoreName());
		
		// 显示视图下方详细清单
		BillMaterialManager bill = (BillMaterialManager) getBean("billMaterialManager");
		List<BillMaterial> list = bill.getBillMateralByBillId(storeNum);
		request.setAttribute("list", list);
		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		return mapping.findForward("showAlterView");
	}

	/**
	 * 修改调拨单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward alter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		StoreAllotManager mgr = (StoreAllotManager) getBean("storeAllotManager");
		
		// 先更新详细数据数量 价格
		BillMaterialManager bmgr = (BillMaterialManager) getBean

		("billMaterialManager");
		
		String sum = request.getParameter("sum");
		for (int i = 0; i < Integer.parseInt(sum); i++) {
			String id = request.getParameter("id_" + i);
			BillMaterial billMaterial = bmgr.getBillMaterial(id);
			String materialAmount = request.getParameter("materialAmount_" +

			i);
			String materialPrice = request.getParameter("materialPrice_" + i);
			double totalAmount = Integer.parseInt(materialAmount)
					* Double.parseDouble(materialPrice);
			
			String billremark = request.getParameter("remark" + i);
			
			billMaterial.setMaterialAmount(Integer.parseInt(materialAmount));
			billMaterial.setTotalAmount(totalAmount);
			billMaterial.setMaterialPrice(Double.parseDouble(materialPrice));
			billMaterial.setRemark(billremark);
			bmgr.updateBillMaterial(billMaterial);
		}
		
		
		
		String id = request.getParameter("storeInputId");
		StoreAllot storeAllot = mgr.getStoreAllot(id);
		StoreInputManager mgr2 = (StoreInputManager) getBean("storeInputManager");
		String mateper = "";
		TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userid = tawSystemSessionForm.getUserid();
		mateper = mgr2.getMatePer(userid);
		//原始单号
		String storeOriginalNum = request.getParameter("storeOriginalNum");
		storeAllot.setStoreOriginalNum(storeOriginalNum);
		StoreManager storeMgr = (StoreManager)getBean("storeManager");
		// 调出仓库
		String outStoreSid = request.getParameter("outStoreSid");
		String outStoreSname = storeMgr.getStore(outStoreSid).getName();
		storeAllot.setStoreSname(outStoreSname);
		storeAllot.setStoreSid(outStoreSid);
		// 调入仓库
		String inputStoreSid = request.getParameter("inputStoreSid");
		String inputStoreSname = storeMgr.getStore(inputStoreSid).getName();
		storeAllot.setInputStoreId(inputStoreSid);
		storeAllot.setInputStoreName(inputStoreSname);
		//经办人
		String storeRequisitioner = request.getParameter("storeRequisitioner");
		storeAllot.setStoreRequisitioner(storeRequisitioner);
		//往来单位
		String storeCompany = request.getParameter("storeCompany");
		storeAllot.setStoreCompany(storeCompany);
		//备注
		String storeRemark = request.getParameter("remark");
		storeAllot.setStoreRemark(storeRemark);
		//最后修改人
		storeAllot.setStoreLastModifyPeople("");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//开单日期
			String storeBillingDate = request.getParameter("storeBillingDate");
			storeAllot.setStoreBillingDate(sdf.parse(storeBillingDate));
			//最后修改日期
			storeAllot.setStoreLastModifyDate(sdf.parse(sdf.format(new Date

())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mgr.alterStoreAllot(storeAllot);

		Integer pageSize = 10;
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

		String whereStr = " where 1=1 ";

		Map map = mgr.getStoreAllot(pageIndex, pageSize, whereStr);
		int total = StaticMethod.nullObject2int(map.get("total"));
		List list = (List) map.get("result");
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);

		request.setAttribute("list", list);
		request.setAttribute("message", "修改成功");
		// 动态读取仓库
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		request.setAttribute("mateper", mateper);
		return mapping.findForward("storeAllot");
	}

	/**
	 * 根据条件查询出库单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward selectBy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		StoreAllotManager mgr = (StoreAllotManager) getBean("storeAllotManager");
		StoreInputManager mgr2 = (StoreInputManager) getBean("storeInputManager");
		String mateper = "";
		TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userid = tawSystemSessionForm.getUserid();
		mateper = mgr2.getMatePer(userid);
		String start = StaticMethod.nullObject2String(request
				.getParameter("start"));
		String end = StaticMethod
				.nullObject2String(request.getParameter("end"));
		String storeSid = StaticMethod.nullObject2String(request
				.getParameter("storeSname"));
		String inputStoreName = StaticMethod.nullObject2String(request
				.getParameter("inputStoreName"));
		String storeNum = StaticMethod.nullObject2String(request
				.getParameter("storeNum"));
		String storeOriginalNum = StaticMethod.nullObject2String(request
				.getParameter("storeOriginalNum"));
		String storeRequisitioner = StaticMethod.nullObject2String(request
				.getParameter("storeRequisitioner"));
		String storeMakeBillPeople = StaticMethod.nullObject2String(request
				.getParameter("storeMakeBillPeople"));
		String storeCompany = StaticMethod.nullObject2String(request
				.getParameter("storeCompany"));
		String storeRemark = StaticMethod.nullObject2String(request
				.getParameter("storeRemark"));
		// List<StoreAllot> list = mgr.getStoreAllotByTime(start, end);
		Integer pageSize = 10;
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

		// to_date('2006-04-08 00:00:01','yyyy-mm-dd hh24:mi:ss')

		if (!"".equals(storeRemark)) {
			sb.append("  and storeRemark = '").append(storeRemark).append("'");
		}
		if (!"".equals(storeNum)) {
			sb.append("  and storeNum = '").append(storeNum).append("'");
		}
		if (!"".equals(storeOriginalNum)) {
			sb.append("  and storeOriginalNum = '").append(storeOriginalNum)
					.append("'");
		}
		if (!"".equals(storeRequisitioner)) {
			sb.append("  and storeRequisitioner = '")
					.append(storeRequisitioner).append("'");
		}
		if (!"".equals(storeMakeBillPeople)) {
			sb.append("  and storeMakeBillPeople = '").append(
					storeMakeBillPeople).append("'");
		}
		if (!"".equals(storeCompany)) {
			sb.append("  and storeCompany = '").append(storeCompany)
					.append("'");
		}
		if (!"".equals(storeSid)) {
			sb.append("  and storeSname = '").append(storeSid).append("'");
		}
		if(!"".equals(inputStoreName)){
			sb.append("  and inputStoreName = '").append(inputStoreName).append("'");
		}
		if (!"".equals(start)) {
			sb.append("  and storeBillingDate >= to_date('").append(start)
					.append("','yyyy-mm-dd')");
		}
		if (!"".equals(end)) {
			sb.append("  and storeBillingDate <= to_date('").append(end)
					.append("','yyyy-mm-dd')");
		}
		String whereStr = sb.toString();
		Map map = mgr.getStoreAllot(pageIndex, pageSize, whereStr);
		int total = StaticMethod.nullObject2int(map.get("total"));
		List list = (List) map.get("result");
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);
		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		request.setAttribute("mateper", mateper);

		request.setAttribute("list", list);
		return mapping.findForward("storeAllot");
	}

	/**
	 * 审核调拨单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward audit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		StoreAllotManager mgr = (StoreAllotManager) getBean("storeAllotManager");
		BillMaterialManager bmgr = (BillMaterialManager) getBean("billMaterialManager");
		StoreInventoryManager smgr = (StoreInventoryManager) getBean("storeInventoryManager");
		StoreInputManager mgr2 = (StoreInputManager) getBean("storeInputManager");
		boolean isUpdate = true;
		String message = "";
		
		String mateper ="";
		TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userid = tawSystemSessionForm.getUserid();
		String username = tawSystemSessionForm.getUsername();
		mateper = mgr2.getMatePer(userid);
		// 获取入库单ID
		String id = request.getParameter("id");
		StoreAllot storeAllot = mgr.getStoreAllot(id);
		// 审核人
		String storeExaminePeople = request.getParameter("shenpiren");
		storeAllot.setStoreExaminePeople(storeExaminePeople);
		// 审核意见
		String auditSuggestion = request.getParameter("remark");
		storeAllot.setAuditSuggestion(auditSuggestion);

		if ("未审核".equals(storeAllot.getStoreExamineStatus())) {
			storeAllot.setStoreExamineStatus("已审核");
			// 获取调出仓库主键
			String storeSid = storeAllot.getStoreSid();
			// 获取调入仓库主键
			String storeInSid = storeAllot.getInputStoreId();
			String storeNum = storeAllot.getStoreNum();
			List<BillMaterial> list = bmgr.getBillMateralByBillId(storeNum);

			// 循环判断 取出的这批货物中 是否存在取出数量大于库存数量的材料
			for (int i = 0; i < list.size(); i++) {
				String materialId = list.get(i).getMaterialId();
				int amount = list.get(i).getMaterialAmount();
				StoreInventory inventory = smgr.getInventoryByMaterialId(
						materialId, storeSid);
				if (amount > inventory.getOnhand()) {
					// 不允许出库
					isUpdate = false;
					break;
				}

			}
			if (isUpdate) {
				for (int i = 0; i < list.size(); i++) {
					// 获取 详情单中材料id
					String materialId = list.get(i).getMaterialId();
					int amount = list.get(i).getMaterialAmount();
					// 根据 仓库名称 和 材料ID 获取 库存对象
					StoreInventory oInventory = smgr.getInventoryByMaterialId(
							materialId, storeSid);
					StoreInventory inInventory = smgr.getInventoryByMaterialId(
							materialId, storeInSid);
					// 调出仓库 取出库存数量 减去 出库数量
					oInventory.setOnhand(oInventory.getOnhand() - amount);
					//调入仓库 加上
					inInventory.setOnhand(inInventory.getOnhand() + amount);
					// 更新库存
					smgr.update(oInventory);
					smgr.update(inInventory);
				}
				// 更新出库单 审核状态等
				mgr.updateStoreAllot(storeAllot);
				message = "审核调拨单成功";
			} else {
				message = "取出的材料大于库存数，审核失败！";
			}
		} else {
			message =  "不允许重复审核！！";
		}

		Integer pageSize = 10;
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

		String whereStr = " where 1=1 ";

		Map map = mgr.getStoreAllot(pageIndex, pageSize, whereStr);
		int total = StaticMethod.nullObject2int(map.get("total"));
		List returnlist = (List) map.get("result");
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("message", message);
		//动态读取仓库
		StoreManager storeMgr = (StoreManager)getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		request.setAttribute("list", returnlist);
		request.setAttribute("mateper", mateper);
		request.setAttribute("username", username);
		return mapping.findForward("storeAllot");
	}

	/**
	 * 反向审核调拨单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward deAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// 审核反馈信息
		String message = "";
		boolean isUpdate = true;
		StoreAllotManager mgr = (StoreAllotManager) getBean("storeAllotManager");
		BillMaterialManager bmgr = (BillMaterialManager) getBean("billMaterialManager");
		StoreInventoryManager smgr = (StoreInventoryManager) getBean("storeInventoryManager");
		// 获取调拨单ID
		String id = request.getParameter("id");
		String storeExaminePeople = request.getParameter("storeExaminePeople");
		StoreAllot storeAllot = mgr.getStoreAllot(id);

		if ("已审核".equals(storeAllot.getStoreExamineStatus())) {
			// 获取仓库主键
			String storeSid = storeAllot.getStoreSid();
			storeAllot.setStoreExamineStatus("反审");
			storeAllot.setStoreExaminePeople(storeExaminePeople);
			String storeNum = storeAllot.getStoreNum();
			List<BillMaterial> list = bmgr.getBillMateralByBillId(storeNum);

			for (int i = 0; i < list.size(); i++) {
				String materialId = list.get(i).getMaterialId();
				int amount = list.get(i).getMaterialAmount();
				StoreInventory inventory = smgr.getInventoryByMaterialId(
						materialId, storeSid);
				if (amount > inventory.getOnhand()) {
					isUpdate = false;
					break;
				}
			}
			// 如果取出量 不大于 库存数 执行更新库存
			if (isUpdate) {
				for (int i = 0; i < list.size(); i++) {
					// 获取 详情单中材料id
					String materialId = list.get(i).getMaterialId();
					int amount = list.get(i).getMaterialAmount();
					// 根据 仓库名称 和 材料ID 获取 库存对象
					StoreInventory inventory = smgr.getInventoryByMaterialId(
							materialId, storeSid);
					// 取调拨存数量 加上 调拨数量
					inventory.setOnhand(inventory.getOnhand() - amount);
					// 更新库存
					smgr.update(inventory);
				}
				// 更新调拨单审核状态
				mgr.updateStoreAllot(storeAllot);
				message = "反向审核成功";
			} else {
				message = "取出的材料大于库存数，反向审核失败！";
			}
		} else {
			message = "不允许重复反向审核！！";
		}
		Integer pageSize = 10;
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

		String whereStr = " where 1=1 ";

		Map map = mgr.getStoreAllot(pageIndex, pageSize, whereStr);
		int total = StaticMethod.nullObject2int(map.get("total"));
		List returnlist = (List) map.get("result");
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);
		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		request.setAttribute("list", returnlist);
		request.setAttribute("message", message);
		return mapping.findForward("storeAllot");
	}

	/**
	 * 跳转至审核页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward goAuditPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		StoreAllotManager mgr = (StoreAllotManager) getBean("storeAllotManager");
		TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String username = tawSystemSessionForm.getUsername();
		String id = request.getParameter("id");
		StoreAllot storeAllot = mgr.getStoreAllot(id);
		String storeNum = storeAllot.getStoreNum();
		request.setAttribute("storeAllot", storeAllot);
		BillMaterialManager bmgr = (BillMaterialManager) getBean("billMaterialManager");
		List<BillMaterial> returnlist = bmgr.getBillMateralByBillId(storeNum);
		request.setAttribute("list", returnlist);
		request.setAttribute("username", username);
		return mapping.findForward("goAuditPage");
	}

	/**
	 * 跳转至反审核页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward goDeAuditPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		StoreAllotManager mgr = (StoreAllotManager) getBean("storeAllotManager");
		String id = request.getParameter("id");
		StoreAllot storeAllot = mgr.getStoreAllot(id);
		String storeNum = storeAllot.getStoreNum();
		request.setAttribute("storeAllot", storeAllot);
		BillMaterialManager bmgr = (BillMaterialManager) getBean("billMaterialManager");
		List<BillMaterial> returnlist = bmgr.getBillMateralByBillId(storeNum);
		request.setAttribute("list", returnlist);
		return mapping.findForward("goDeAuditPage");
	}
}
