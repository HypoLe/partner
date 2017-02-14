package com.boco.eoms.materials.webapp.action;

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
import com.boco.eoms.materials.model.Store;
import com.boco.eoms.materials.service.StoreInventoryManager;
import com.boco.eoms.materials.service.StoreManager;
import com.boco.eoms.sheet.base.util.SheetAttributes;

public class StoreInventoryAction extends BaseAction{
	/**
	 * 展示查询界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public  ActionForward view(ActionMapping mapping, ActionForm form,  
            HttpServletRequest request, HttpServletResponse response){
		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		return mapping.findForward("view");
	}
	
	/**
	 * 根据条件查询库存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	@SuppressWarnings("unchecked")
	public ActionForward select(ActionMapping mapping, ActionForm form,  
            HttpServletRequest request, HttpServletResponse response){
		StoreInventoryManager mgr = (StoreInventoryManager) getBean("storeInventoryManager");
		String storeSid = request.getParameter("storeSid");
		String materialName = request.getParameter("materialName");
		String materialEncode = request.getParameter("materialEncode");
		
		
		
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
		
		if(!"".equals(storeSid)){
			sb.append("  and storeSid = '").append(storeSid).append("'");
		}
		if(!"".equals(materialName)){
			sb.append("  and materialName = '").append(materialName).append("'");
		}
		if(!"".equals(materialEncode)){
			sb.append("  and materialEncode = '").append(materialEncode).append("'");
		}
		// 动态读取仓库
		StoreManager storeMgr = (StoreManager) getBean("storeManager");
		List<Store> storeList = storeMgr.getStore();
		request.setAttribute("storeList", storeList);
		String whereStr = sb.toString();
		Map map = mgr.getStoreInventory(pageIndex, pageSize, whereStr);
		int total = StaticMethod.nullObject2int(map.get("total"));
		List list = (List) map.get("result");
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);
		
		
		
		
		request.setAttribute("list", list);
		return mapping.findForward("view");
	}
}
