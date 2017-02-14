package com.boco.eoms.sheet.base.qo;

import java.util.Map;

/**
 * 工单查询对象
 */
public interface IWorkSheetQO {

	/**
	 * 根据QO的条件拼装查询语句
	 * @return 可执行的sql
	 */
	@SuppressWarnings("unchecked")
	public abstract String getClauseSql(Map map,Map condition);
	/**
	 * 适用于陕西用户要求的新查询，查询结果包括工单详情、处理详情和归档情况
	 * @param properties
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public abstract Map getClauseSqlNew(Map map,Map condition);
	
	/**
	 * 根据QO的条件拼装查询语句，是有权限的查询
	 * @return 可执行的sql
	 */
	@SuppressWarnings("unchecked")
	public abstract String getAclClauseSql(Map map, Map condition);	
	/**
	 * Main表PO名称
	 * main poName setter getter
	 * @param PoName
	 */
	public abstract void setPoMain(String PoName);
	
	public abstract String getPoMain();
	
	/**
	 * Link表PO名称
	 * link poName setter getter
	 * @param PoName
	 */
	public abstract void setPoLink(String PoName);
	
	public abstract String getPoLink();
	
	
	/**
	 * Main表的别名名称
	 * main poName setter getter
	 * @param PoName
	 */
	public abstract void setAliasMain(String aliasMain);
	
	public abstract String getAliasMain();
	
	/**
	 * Link表别名名称
	 * link poName setter getter
	 * @param PoName
	 */
	public abstract void setAliasLink(String aliasLink);	
	
	public abstract String getAliasLink();
	
	public String getAliasTask();
	
	public void setAliasTask(String aliasTask);
	
	public String getPoTask();
	
	public void setPoTask(String poTask);
	public abstract String getSheetModelConfig();
	public abstract void setSheetModelConfig(String sheetModelConfig);
	
}
