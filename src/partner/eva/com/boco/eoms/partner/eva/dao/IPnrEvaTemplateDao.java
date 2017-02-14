package com.boco.eoms.partner.eva.dao;

import java.util.List;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.eva.model.PnrEvaTemplate;

/**
 * 
 * <p>
 * Title:考核模板Dao接口
 * </p>
 * <p>
 * Description:考核模板Dao接口
 * </p>
 * <p>
 * Date:2008-12-8 上午09:50:27
 * </p>
 * 
 * @author 李秋野
 * @version 3.5.1
 * 
 */
public interface IPnrEvaTemplateDao extends Dao {

	/**
	 * 根据主键id查询模板
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public PnrEvaTemplate getTemplate(String id);

	/**
	 * 保存模板
	 * 
	 * @param template
	 *            模板
	 */
	public void saveTemplate(PnrEvaTemplate template);

	/**
	 * 删除模板
	 * 
	 * @param template
	 *            模板
	 */
	public void removeTemplate(PnrEvaTemplate template);

	/**
	 * 根据模板Id返回模板名称
	 * 
	 * @param id
	 *            模板Id
	 * @return
	 */
	public String id2Name(final String id);
	
	//根据模版中存储的belongNode，找到属于同一节点的所有模版2009-8-7
	public List getTemplateByblnode(String node);
	/**
	 * 根据条件查询出模板的集合
	 * 贾智会 2009-11-18
	 * @param where
	 * @return
	 */
	public List getTemplateByCondition(String where);
	/**
	 * 根据考核层面得到已激活的模板
	 * @return
	 */
	public List getActiveTemplateByExecuteType(String templateType,String executeType);
	/**
	 * 根据考核层面得到下一级已激活的模板
	 * @return
	 */
	public List getNextTemplateByExecuteType(String parentNodeId,String executeType);
}
