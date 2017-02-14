package com.boco.eoms.partner.inspect.mgr;

import java.util.Map;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.inspect.model.InspectTemplateBigItem;

public interface IInspectTemplateBigItemMgr extends CommonGenericService<InspectTemplateBigItem> {

	/**
	 * 根据当前的id数组删除模板
	 * @param ids
	 */
	public void deleteTemplateItems(String ids[],String templateId);
	
	/**
	 * 删除模板大项
	 * @param map
	 */
	public Map<String, String> deleteTemplateBigItems(Map<String, Integer> map,String templateId);
	
	public Map<String, String> findTemplateBigItems(Map<String, Integer> map,String templateId);
}
