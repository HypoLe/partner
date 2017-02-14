package com.boco.eoms.partner.siteEva.dao;

import java.util.List;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.siteEva.model.SiteEvaTemplate;

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
public interface ISiteEvaTemplateDao extends Dao {

	/**
	 * 根据主键id查询模板
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public SiteEvaTemplate getTemplate(String id);

	/**
	 * 保存模板
	 * 
	 * @param template
	 *            模板
	 */
	public void saveTemplate(SiteEvaTemplate template);

	/**
	 * 删除模板
	 * 
	 * @param template
	 *            模板
	 */
	public void removeTemplate(SiteEvaTemplate template);

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
