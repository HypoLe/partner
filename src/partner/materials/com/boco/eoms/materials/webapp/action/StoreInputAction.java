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
import com.boco.eoms.materials.model.StoreInput;
import com.boco.eoms.materials.model.StoreInventory;
import com.boco.eoms.materials.service.BillMaterialManager;
import com.boco.eoms.materials.service.StoreInputManager;
import com.boco.eoms.materials.service.StoreInventoryManager;
import com.boco.eoms.materials.service.StoreManager;

/**
 * 入库单处理Action
 * 
 * @author wanghongliang
 * 
 */
public class StoreInputAction extends BaseAction {
	/**
	 * 跳转至入库界面
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
		String fh = request.getParameter("fh");
		String billId = request.getParameter("billId");
		StoreInputManager mgr = (StoreInputManager) getBean("storeInputManager");
		BillMaterialManager mgr2 = (BillMaterialManager) getBean("billMaterialManager");
		if("1".equals(fh)){
			//新增入库单如果有材料但是点击返回则清空材料列表
			mgr2.removeBillMaterialall(billId);
		}
		String mateper = "";
		TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userid = tawSystemSessionForm.getUserid();
		String username = tawSystemSessionForm.getUsername();
		mateper = mgr.getMatePer(userid);
		Integer pageSize = 10;
		String pageIndexName = new ParamEncoder("list")
				.encodeParameterName("p");
		Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request
				.getParameter(pageIndexName)) ? 0 : Integer.parseInt

		(request.getParameter(pageIndexName)) - 1);
		String exportType = StaticMethod.null2String(request.getParameter(new

		org.displaytag.util.ParamEncoder("list").encodeParameterName

		(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}

		String whereStr = " where 1=1 ";

		Map map = mgr.getStoreInput(pageIndex, pageSize, whereStr);
		int total = StaticMethod.nullObject2int(map.get("total"));
		List<StoreInput> list = (List) map.get("result");
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("mateper", mateper);
		request.setAttribute("username", username);

		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		request.setAttribute("list", list);
		return mapping.findForward("storeInput");
	}

	public ActionForward addgoodsview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("addgoodsview");
	}
	
	

	/**
	 * 跳转添加入库单界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward showAddStoreInputView(ActionMapping mapping,
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
		return mapping.findForward("showAddStoreInputView");
	}

	/**
	 * 保存 入库单
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
		StoreInputManager mgr = (StoreInputManager) getBean("storeInputManager");
		// 先更新详细数据数量 价格
		BillMaterialManager bmgr = (BillMaterialManager) getBean

		("billMaterialManager");
		String mateper = "";
		TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userid = tawSystemSessionForm.getUserid();
		mateper = mgr.getMatePer(userid);
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
		StoreInput storeInput = new StoreInput();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date storeMakeBillDate = null;
		Date storeBillingDate = null;
		try {
			// 制单 开单 日期
			storeMakeBillDate = sdf.parse(new SimpleDateFormat("yyyy-MM-dd")
					.format(new	Date()));
			storeBillingDate = sdf.parse(request
					.getParameter("storeBillingDate"));
			storeInput.setStoreMakeBillDate(storeMakeBillDate);
			storeInput.setStoreBillingDate(storeBillingDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 往来公司
		String storeCompany = request.getParameter("storeCompany");
		storeInput.setStoreCompany(storeCompany);
		// 部门
		String storeDepartment = request.getParameter("storeDepartment");
		storeInput.setStoreDepartment(storeDepartment);
		// 仓库id、名字
		String storeSid = request.getParameter("storeSid");
		StoreManager sm = (StoreManager) getBean("storeManager");
		String storeSname = "";
		if (!"".equals(storeSid)) {
			storeSname = sm.getStore(storeSid).getName();
		}
		storeInput.setStoreSid(storeSid);
		storeInput.setStoreSname(storeSname);
		// 经办人
		String storeRequisitioner = request.getParameter("storeRequisitioner");
		storeInput.setStoreRequisitioner(storeRequisitioner);
		// 入库 未审核
		String storeExamineStatus = "未审核";
		String storeBillType = "入库";
		storeInput.setStoreBillType(storeBillType);
		storeInput.setStoreExamineStatus(storeExamineStatus);
		// 备注
		String storeRemark = request.getParameter("remark");
		storeInput.setStoreRemark(storeRemark);
		// 制单人
		String storeMakeBillPeople = request.getParameter("username");
		storeInput.setStoreMakeBillPeople(storeMakeBillPeople);
		// 单号
		String storeNum = request.getParameter("billId");
		storeInput.setStoreNum(storeNum);
		// 原始单号
		String storeOriginalNum = request.getParameter("storeOriginalNum");
		storeInput.setStoreOriginalNum(storeOriginalNum);
		// delete标识 默认为1
		int deleteFlag = 1;
		storeInput.setDeleteFlag(deleteFlag);
		mgr.saveStoreInput(storeInput);
		// 分页
		Integer pageSize = 10;
		String pageIndexName = new ParamEncoder("list")
				.encodeParameterName("p");
		Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request
				.getParameter(pageIndexName)) ? 0 : Integer.parseInt

		(request.getParameter(pageIndexName)) - 1);
		String exportType = StaticMethod.null2String(request.getParameter(new

		org.displaytag.util.ParamEncoder("list").encodeParameterName

		(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}

		String whereStr = " where 1=1 ";

		Map map = mgr.getStoreInput(pageIndex, pageSize, whereStr);
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
		return mapping.findForward("storeInput");
	}

	/**
	 * 删除入库单
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
		StoreInputManager mgr = (StoreInputManager) getBean("storeInputManager");
		String mateper = "";
		TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userid = tawSystemSessionForm.getUserid();
		mateper = mgr.getMatePer(userid);
		String id = request.getParameter("id");
		StoreInput storeInput = mgr.getStoreInput(id);
		// 逻辑删除
		storeInput.setDeleteFlag(0);
		mgr.updateStoreInput(storeInput);
		Integer pageSize = 10;
		String pageIndexName = new ParamEncoder("list")
				.encodeParameterName("p");
		Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request
				.getParameter(pageIndexName)) ? 0 : Integer.parseInt

		(request.getParameter(pageIndexName)) - 1);
		String exportType = StaticMethod.null2String(request.getParameter(new

		org.displaytag.util.ParamEncoder("list").encodeParameterName

		(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}

		String whereStr = " where 1=1 ";

		Map map = mgr.getStoreInput(pageIndex, pageSize, whereStr);
		int total = StaticMethod.nullObject2int(map.get("total"));
		List list = (List) map.get("result");
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("mateper", mateper);
		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		request.setAttribute("id", request.getParameter("storeInputId"));
		request.setAttribute("list", list);
		return mapping.findForward("storeInput");
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
		StoreInputManager mgr = (StoreInputManager) getBean("storeInputManager");
		String id = request.getParameter("id");
		StoreInput storeInput = mgr.getStoreInput(id);
		// 获取单号
		String billId = storeInput.getStoreNum();
		BillMaterialManager bill = (BillMaterialManager) getBean

		("billMaterialManager");
		List<BillMaterial> list = bill.getBillMateralByBillId(billId);
		request.setAttribute("list", list);
		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		request.setAttribute("storeInput", storeInput);
		return mapping.findForward("showDetail");
	}

	/**
	 * 跳转至修改入库单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward showAlterView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		StoreInputManager mgr = (StoreInputManager) getBean("storeInputManager");
		String storeInputId = request.getParameter("id");
		// 根据id获取入库单
		StoreInput storeInput = mgr.getStoreInput(storeInputId);
		String storeNum = storeInput.getStoreNum();
		request.setAttribute("storeNum",storeNum);
		request.setAttribute("storeMakeBillPeople",storeInput.getStoreMakeBillPeople());
		request.setAttribute("storeMakeBillDate",storeInput.getStoreMakeBillDate());
		request.setAttribute("storeBillingDate",storeInput.getStoreBillingDate());
		request.setAttribute("storeCompany",storeInput.getStoreCompany());
		request.setAttribute("storeOriginalNum",storeInput.getStoreOriginalNum());
		request.setAttribute("remark",storeInput.getStoreRemark());
		request.setAttribute("storeSid",storeInput.getStoreSid());
		request.setAttribute("storeRequisitioner",storeInput.getStoreRequisitioner());
		request.setAttribute("storeInputId",storeInputId);
		request.setAttribute("storeName",storeInput.getStoreSname());
		// 显示视图下方详细清单
		BillMaterialManager bill = (BillMaterialManager) getBean

		("billMaterialManager");
		List<BillMaterial> list = bill.getBillMateralByBillId(storeNum);
		request.setAttribute("list", list);
		//request.setAttribute("storeInput", storeInput);
		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		return mapping.findForward("showAlterView");
	}

	/**
	 * 修改入库单
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
		StoreInputManager mgr = (StoreInputManager) getBean("storeInputManager");
		
		// 先更新详细数据数量 价格
		BillMaterialManager bmgr = (BillMaterialManager) getBean

		("billMaterialManager");
		String mateper = "";
		TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userid = tawSystemSessionForm.getUserid();
		mateper = mgr.getMatePer(userid);
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
		
		
		request.setAttribute("mateper", mateper);
		String storeInputId = request.getParameter("storeInputId");
		StoreInput storeInput = mgr.getStoreInput(storeInputId);
		// 原始单号
		String storeOriginalNum = request.getParameter("storeOriginalNum");
		storeInput.setStoreOriginalNum(storeOriginalNum);
		// 仓库id 名字
		String storeSid = request.getParameter("storeSid");
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		String storeSname = storeMgr.getStore(storeSid).getName();
		storeInput.setStoreSid(storeSid);
		storeInput.setStoreSname(storeSname);
		// 经办人
		String storeRequisitioner = request.getParameter("storeRequisitioner");
		storeInput.setStoreRequisitioner(storeRequisitioner);
		// 往来单位
		String storeCompany = request.getParameter("storeCompany");
		storeInput.setStoreCompany(storeCompany);
		// 备注
		String storeRemark = request.getParameter("remark");
		storeInput.setStoreRemark(storeRemark);
		// 最后修改人
		storeInput.setStoreLastModifyPeople("");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 开单日期
			String storeBillingDate = request.getParameter("storeBillingDate");
			storeInput.setStoreBillingDate(sdf.parse(storeBillingDate));
			// 最后修改日期
			storeInput.setStoreLastModifyDate(sdf.parse(sdf.format(new Date

			())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mgr.alterStoreInput(storeInput);
		// 分页
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
		Map map = mgr.getStoreInput(pageIndex, pageSize, whereStr);
		int total = StaticMethod.nullObject2int(map.get("total"));
		List list = (List) map.get("result");
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);

		request.setAttribute("list", list);
		request.setAttribute("message", "修改成功");
		// 动态读取仓库
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		return mapping.findForward("storeInput");
	}

	/**
	 * 根据条件查询入库单
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
		StoreInputManager mgr = (StoreInputManager) getBean("storeInputManager");
		String mateper = "";
		TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userid = tawSystemSessionForm.getUserid();
		mateper = mgr.getMatePer(userid);
		String start = StaticMethod.nullObject2String(request.getParameter

		("start"));
		String end = StaticMethod
				.nullObject2String(request.getParameter("end"));
		String storeSid = StaticMethod.nullObject2String(request.getParameter

		("storeSid"));
		String storeNum = StaticMethod.nullObject2String(request.getParameter

		("storeNum"));
		String storeOriginalNum = StaticMethod.nullObject2String

		(request.getParameter("storeOriginalNum"));
		String storeRequisitioner = StaticMethod.nullObject2String

		(request.getParameter("storeRequisitioner"));
		String storeMakeBillPeople = StaticMethod.nullObject2String

		(request.getParameter("storeMakeBillPeople"));
		String storeCompany = StaticMethod.nullObject2String(request
				.getParameter

				("storeCompany"));
		String storeRemark = StaticMethod.nullObject2String(request
				.getParameter

				("storeRemark"));
		// List<StoreInput> list = mgr.getStoreInputByTime(start, end);
		Integer pageSize = 10;
		String pageIndexName = new ParamEncoder("list")
				.encodeParameterName("p");
		Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request
				.getParameter(pageIndexName)) ? 0 : Integer.parseInt

		(request.getParameter(pageIndexName)) - 1);
		String exportType = StaticMethod.null2String(request.getParameter(new

		org.displaytag.util.ParamEncoder("list").encodeParameterName

		(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
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
			sb.append("  and storeOriginalNum = '").append

			(storeOriginalNum).append("'");
		}
		if (!"".equals(storeRequisitioner)) {
			sb.append("  and storeRequisitioner = '").append

			(storeRequisitioner).append("'");
		}
		if (!"".equals(storeMakeBillPeople)) {
			sb.append("  and storeMakeBillPeople = '").append

			(storeMakeBillPeople).append("'");
		}
		if (!"".equals(storeCompany)) {
			sb.append("  and storeCompany = '").append(storeCompany).append

			("'");
		}
		if (!"".equals(storeSid)) {
			sb.append("  and storeSid = '").append(storeSid).append("'");
		}
		if (!"".equals(start)) {
			sb.append("  and storeBillingDate >= to_date('").append

			(start).append("','yyyy-mm-dd')");
		}
		if (!"".equals(end)) {
			sb.append("  and storeBillingDate <= to_date('").append

			(end).append("','yyyy-mm-dd')");
		}
		String whereStr = sb.toString();
		Map map = mgr.getStoreInput(pageIndex, pageSize, whereStr);
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
		return mapping.findForward("storeInput");
	}

	/**
	 * 审核入库单
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
		StoreInputManager mgr = (StoreInputManager) getBean("storeInputManager");
		BillMaterialManager bmgr = (BillMaterialManager) getBean

		("billMaterialManager");
		StoreInventoryManager smgr = (StoreInventoryManager)getBean("storeInventoryManager");
		String mateper ="";
		TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userid = tawSystemSessionForm.getUserid();
		String username = tawSystemSessionForm.getUsername();
		mateper = mgr.getMatePer(userid);
		// 获取入库单ID
		String id = request.getParameter("id");
		StoreInput storeInput = mgr.getStoreInput(id);
		// 审核人
		String storeExaminePeople = request.getParameter("shenpiren");
		storeInput.setStoreExaminePeople(storeExaminePeople);
		// 审核意见
		String auditSuggestion = request.getParameter("auditSuggestion");
		storeInput.setAuditSuggestion(auditSuggestion);

		if ("未审核".equals(storeInput.getStoreExamineStatus())) {
			storeInput.setStoreExamineStatus("已审核");
			// 获取仓库主键
			String storeSid = storeInput.getStoreSid();
			String storeNum = storeInput.getStoreNum();
			List<BillMaterial> list = bmgr.getBillMateralByBillId(storeNum);
			for (int i = 0; i < list.size(); i++) {
				// 获取 详情单中材料id
				String materialId = list.get(i).getMaterialId();
				int amount = list.get(i).getMaterialAmount();
				// 根据 仓库名称 和 材料ID 获取 库存对象
				StoreInventory inventory = smgr.getInventoryByMaterialId(
						materialId, storeSid);
				// 取出库存数量 加上 入库数量
				inventory.setOnhand(inventory.getOnhand() + amount);
				// 更新库存
				smgr.update(inventory);
			}
			// 更新入库单审核状态
			mgr.updateStoreInput(storeInput);
			request.setAttribute("message", "审核成功");
		} else {
			request.setAttribute("message", "不允许重复审核！！");
		}

		Integer pageSize = 10;
		String pageIndexName = new ParamEncoder("list")
				.encodeParameterName("p");
		Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request
				.getParameter(pageIndexName)) ? 0 : Integer.parseInt

		(request.getParameter(pageIndexName)) - 1);
		String exportType = StaticMethod.null2String(request.getParameter(new

		org.displaytag.util.ParamEncoder("list").encodeParameterName

		(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}

		String whereStr = " where 1=1 ";

		Map map = mgr.getStoreInput(pageIndex, pageSize, whereStr);
		int total = StaticMethod.nullObject2int(map.get("total"));
		List returnlist = (List) map.get("result");
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);

		request.setAttribute("list", returnlist);
		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		request.setAttribute("mateper", mateper);
		request.setAttribute("username", username);
		return mapping.findForward("storeInput");
	}

	/**
	 * 反向审核入库单
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
		StoreInputManager mgr = (StoreInputManager) getBean("storeInputManager");
		BillMaterialManager bmgr = (BillMaterialManager) getBean

		("billMaterialManager");
		StoreInventoryManager smgr = (StoreInventoryManager) getBean

		("storeInventoryManager");
		// 获取入库单ID
		String id = request.getParameter("id");
		String storeExaminePeople = request.getParameter("storeExaminePeople");
		StoreInput storeInput = mgr.getStoreInput(id);

		if ("已审核".equals(storeInput.getStoreExamineStatus())) {
			// 获取仓库主键
			String storeSid = storeInput.getStoreSid();
			storeInput.setStoreExamineStatus("反审");
			storeInput.setStoreExaminePeople(storeExaminePeople);
			String storeNum = storeInput.getStoreNum();
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
					StoreInventory inventory =

					smgr.getInventoryByMaterialId(materialId, storeSid);
					// 取出库存数量 加上 入库数量
					inventory.setOnhand(inventory.getOnhand() -

					amount);
					// 更新库存
					smgr.update(inventory);
				}
				// 更新入库单审核状态
				mgr.updateStoreInput(storeInput);
				message = "反向审核成功";
			} else {
				message = "取出的材料大于库存数，审核失败！";
			}
		} else {
			message = "不允许重复反向审核！！";
		}
		Integer pageSize = 10;
		String pageIndexName = new ParamEncoder("list")
				.encodeParameterName("p");
		Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request
				.getParameter(pageIndexName)) ? 0 : Integer.parseInt

		(request.getParameter(pageIndexName)) - 1);
		String exportType = StaticMethod.null2String(request.getParameter(new

		org.displaytag.util.ParamEncoder("list").encodeParameterName

		(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}

		String whereStr = " where 1=1 ";

		Map map = mgr.getStoreInput(pageIndex, pageSize, whereStr);
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
		return mapping.findForward("storeInput");
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
		StoreInputManager mgr = (StoreInputManager) getBean("storeInputManager");
		TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String username = tawSystemSessionForm.getUsername();
		String id = request.getParameter("id");
		StoreInput storeInput = mgr.getStoreInput(id);
		String storeNum = storeInput.getStoreNum();
		request.setAttribute("storeInput", storeInput);
		request.setAttribute("username", username);
		BillMaterialManager bmgr = (BillMaterialManager) getBean

		("billMaterialManager");
		List<BillMaterial> returnlist = bmgr.getBillMateralByBillId(storeNum);
		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		request.setAttribute("list", returnlist);
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
		StoreInputManager mgr = (StoreInputManager) getBean("storeInputManager");
		String id = request.getParameter("id");
		StoreInput storeInput = mgr.getStoreInput(id);
		String storeNum = storeInput.getStoreNum();
		request.setAttribute("storeInput", storeInput);
		BillMaterialManager bmgr = (BillMaterialManager) getBean

		("billMaterialManager");
		List<BillMaterial> returnlist = bmgr.getBillMateralByBillId(storeNum);
		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		request.setAttribute("list", returnlist);
		return mapping.findForward("goDeAuditPage");
	}
}
