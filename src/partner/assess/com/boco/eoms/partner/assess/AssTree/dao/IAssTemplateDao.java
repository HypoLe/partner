/**
 * 
 */
package com.boco.eoms.partner.assess.AssTree.dao;

import java.util.List;

import com.boco.eoms.partner.assess.AssTree.model.AssTemplate;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Nov 26, 2010 9:09:22 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public interface IAssTemplateDao {

	/**
	 * 根据主键id查询模板
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public AssTemplate getTemplate(String id);

	/**
	 * 保存模板
	 * 
	 * @param template
	 *            模板
	 */
	public void saveTemplate(AssTemplate template);

	/**
	 * 删除模板
	 * 
	 * @param template
	 *            模板
	 */
	public void removeTemplate(AssTemplate template);

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

}
