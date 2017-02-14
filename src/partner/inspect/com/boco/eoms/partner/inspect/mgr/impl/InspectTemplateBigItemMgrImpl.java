package com.boco.eoms.partner.inspect.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.inspect.dao.IInspectTemplateBigItemDao;
import com.boco.eoms.partner.inspect.dao.IInspectTemplateBigItemDaoJdbc;
import com.boco.eoms.partner.inspect.mgr.IInspectTemplateBigItemMgr;
import com.boco.eoms.partner.inspect.model.InspectTemplateBigItem;

public class InspectTemplateBigItemMgrImpl extends CommonGenericServiceImpl<InspectTemplateBigItem> implements IInspectTemplateBigItemMgr  {

	private IInspectTemplateBigItemDao inspectTemplateBigItemDao;

	private IInspectTemplateBigItemDaoJdbc inspectTemplateBigItemDaoJdbc;
	
	/**
	 * 根据当前的id数组删除模板
	 * @param ids
	 */
	public void deleteTemplateItems(String ids[],String templateId){
		inspectTemplateBigItemDaoJdbc.deleteTemplateItems(ids,templateId);
	};
	
	/**
	 * 删除模板大项
	 * @param map
	 */
	public Map<String, String> deleteTemplateBigItems(Map<String, Integer> map,String templateId) {
		// TODO Auto-generated method stub
		return inspectTemplateBigItemDaoJdbc.deleteTemplateBigItems(map,templateId);
	}
	
	public Map<String, String> findTemplateBigItems(Map<String, Integer> map,String templateId) {
		// TODO Auto-generated method stub
		return inspectTemplateBigItemDaoJdbc.findTemplateBigItems(map,templateId);
	}
	
	
	public IInspectTemplateBigItemDao getInspectTemplateBigItemDao() {
		return inspectTemplateBigItemDao;
	}

	public void setInspectTemplateBigItemDao(
			IInspectTemplateBigItemDao inspectTemplateBigItemDao) {
		this.setCommonGenericDao(inspectTemplateBigItemDao);
		this.inspectTemplateBigItemDao = inspectTemplateBigItemDao;
	}

	public IInspectTemplateBigItemDaoJdbc getInspectTemplateBigItemDaoJdbc() {
		return inspectTemplateBigItemDaoJdbc;
	}

	public void setInspectTemplateBigItemDaoJdbc(
			IInspectTemplateBigItemDaoJdbc inspectTemplateBigItemDaoJdbc) {
		this.inspectTemplateBigItemDaoJdbc = inspectTemplateBigItemDaoJdbc;
	}





	
	
	
}
