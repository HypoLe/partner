package com.boco.eoms.eva.mgr;

import com.boco.eoms.eva.model.EvaTemplateTemp;

/**
 * <p>
 * Title:考核模板业务方法类
 * </p>
 * <p>
 * Description:考核模板业务方法类
 * </p>
 * <p>
 * Date:2008-11-20 上午11:06:40
 * </p>
 * 
 * @author 李秋野
 * @version 3.5.1
 * 
 */
public interface IEvaTemplateTempMgr {

	/**
	 * 根据主键id查询模板
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
//	public EvaTemplateTemp getTemplate(String id);

	/**
	 * 保存模板
	 * 
	 * @param template
	 *            模板
	 */
	public void saveEvaTemplateTemp(EvaTemplateTemp template);

	/**
	 * 删除模板（临时）
	 * 
	 * @param template
	 *            模板
	 */
	public void removeEvaTemplateTemp(EvaTemplateTemp template);
	
	/**
	 * 根据模板Id返回模板
	 * 
	 * @param id
	 *            模板Id
	 * @return
	 */	
	public EvaTemplateTemp getEvaTemplateTemp(final String id);	

}
