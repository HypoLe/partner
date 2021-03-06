/**
 * 
 */
package com.boco.eoms.partner.assess.AssTree.mgr;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.boco.eoms.partner.assess.AssTree.model.AssTemplate;

/**
 * <p>
 * Title:模板业务方法类
 * </p>
 * <p>
 * Description:模板业务方法类
 * </p>
 * <p>
 * Date:Nov 24, 2010 3:37:38 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public interface IAssTemplateMgr {

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
	 * 删除模板（逻辑删除，仅从树图中删除）
	 * 
	 * @param tplNodeId
	 *            模板对应树节点Id
	 */
	public void removeTplLogical(String tplNodeId);

	/**
	 * 删除模板（物理删除） *
	 * 
	 * @param template
	 *            模板
	 * @param tplNodeId
	 *            模板对应树节点Id
	 */
	public void removeTplPhysical(String tplNodeId);

	/**
	 * 保存模板，树节点和任务
	 * 
	 * @param template
	 *            模板
	 * @param parentNodeId
	 *            父节点Id
	 * @param orgIds
	 *            组织Id
	 */
	public void saveTemplateWithNodeAndTask(AssTemplate template,
			String parentNodeId, String[] orgIds);

	//2009-8-5 已经激活的模版，当指标有修改时，需要保存一个新模版，生效时间就是激活的时间（START_TIME 考核起始时间），旧模版截止时间是新模版的激活时间（END_TIME）。
	public void newTemplateWithTask(AssTemplate template,HttpServletRequest request,String parentNodeId);
	
	/**
	 * 修改模板
	 * 
	 * @param template
	 *            模板
	 * @param parentNodeId
	 *            父节点Id
	 * @param orgIds
	 *            组织Id
	 */
	public void updateTemplate(AssTemplate template, String parentNodeId,HttpServletRequest request,
			String[] orgIds);

	/**
	 * 根据模板Id返回模板名称
	 * 
	 * @param id
	 *            模板Id
	 * @return
	 */
	public String id2Name(String id);

	/**
	 * 激活模板（生成任务详细信息）
	 * 
	 * @param templateId
	 *            模板Id
	 * @param tplNodeId
	 *            模板对应树节点Id
	 */
	public void activeTemplate(String templateId, String tplNodeId);

	/**
	 * 计算模板下属指标权重总和（仅计算叶子节点）
	 * 
	 * @param tplNodeId
	 *            模板对应节点Id
	 */
	public Float getTotalWtOfTemplate(String tplNodeId);
	
	//根据模版中存储的belongNode，找到属于同一节点的所有模版2009-8-7
	public List getTemplateByblnode(String node);
}
