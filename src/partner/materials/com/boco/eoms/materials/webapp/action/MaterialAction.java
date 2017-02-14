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
import com.boco.eoms.materials.model.Material;
import com.boco.eoms.materials.service.MaterialManager;
import com.boco.eoms.sheet.base.util.SheetAttributes;

/**
 * 
 * 材料表 对应action 处理类
 * 
 * @author wanghongliang
 *
 */
public class MaterialAction extends BaseAction{
	/**
	 * 查询所有材料返回List<Material>
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public  ActionForward view(ActionMapping mapping, ActionForm form,  
            HttpServletRequest request, HttpServletResponse response){
		MaterialManager mgr = (MaterialManager) getBean("materialManager");
		List<Material> list = mgr.getMaterial();
		request.setAttribute("list", list);
		return mapping.findForward("view");
	}
	
	/**
	 * 跳转至查询材料页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 
	public  ActionForward view(ActionMapping mapping, ActionForm form,  
            HttpServletRequest request, HttpServletResponse response){
		
		return mapping.findForward("view");
	}
	*/
	
	
	@SuppressWarnings("unchecked")
	public  ActionForward selectByItem(ActionMapping mapping, ActionForm form,  
            HttpServletRequest request, HttpServletResponse response){
		String encode = request.getParameter("encode");
		String categoryNum = request.getParameter("categoryNum");
		String name = request.getParameter("name");
		MaterialManager mgr = (MaterialManager)getBean("materialManager");
		
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

		
		StringBuffer sb =  new StringBuffer();
		
		sb.append(" where 1=1 ");

		//to_date('2006-04-08 00:00:01','yyyy-mm-dd hh24:mi:ss')
		
		
		if(!"".equals(encode)){
			sb.append("  and encode = '").append(encode).append("'");
		}
		if(!"".equals(categoryNum)){
			sb.append("  and storeNum = '").append(categoryNum).append("'");
		}
		if(!"".equals(name)){
			sb.append("  and name = '").append(name).append("'");
		}
		
		String whereStr = sb.toString();
		Map map = mgr.getMaterial(pageIndex, pageSize, whereStr);
		int total = StaticMethod.nullObject2int(map.get("total"));
		List list = (List) map.get("result");
		request.setAttribute("list", list);
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);
		String flag = request.getParameter("flag");
		if("1".equals(flag)){
			return mapping.findForward("inputView");
		}else if("-1".equals(flag)){
			return mapping.findForward("outView");
		}else{
			return mapping.findForward("allotView");
		}
	}
	
	/**
	 * 跳转至新增材料页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public  ActionForward addMaterial(ActionMapping mapping, ActionForm form,  
            HttpServletRequest request, HttpServletResponse response){
		
		return mapping.findForward("addMaterial");
	}
	
	/**
	 * 跳转至新增材料页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public  ActionForward save(ActionMapping mapping, ActionForm form,  
            HttpServletRequest request, HttpServletResponse response){
		//接收表单数据
		String name = request.getParameter("name");
		String encode = request.getParameter("encode");
		String categoryNum = request.getParameter("categoryNum");
		String specification = request.getParameter("specification");
		String manufacturer = request.getParameter("manufacturer");
		double inputPrice = Double.parseDouble(request.getParameter("inputPrice"));
		String remark = request.getParameter("remark");
		String unit = request.getParameter("unit");
		Integer inventoryMax = Integer.parseInt(request.getParameter("inventoryMax"));
		Integer inventoryMin = Integer.parseInt(request.getParameter("inventoryMin"));
		//将数据存入对象
		Material material = new Material();
		material.setName(name);
		material.setEncode(encode);
		material.setCategoryNum(categoryNum);
		material.setSpecification(specification);
		material.setManufacturer(manufacturer);
		material.setInputPrice(inputPrice);
		material.setRemark(remark);
		material.setUnit(unit);
		material.setInventoryMax(inventoryMax);
		material.setInventoryMin(inventoryMin);
		//保存对象
		MaterialManager mgr = (MaterialManager) getBean("materialManager");
		mgr.saveMaterial(material);
		
		
		/*StoreInventoryManager smgr = (StoreInventoryManager) getBean("storeInventoryManager");
		StoreInventory storeInventory = new StoreInventory();
		//storeInventory.setMaterialCategoryDetail(materialCategoryDetail);
		storeInventory.setMaterialCategoryNum(categoryNum);
		storeInventory.setMaterialEncode(encode);
		storeInventory.setMaterialId(materialId);*/
		
		request.setAttribute("message", "新增材料成功");
		return mapping.findForward("view");
	}
}
