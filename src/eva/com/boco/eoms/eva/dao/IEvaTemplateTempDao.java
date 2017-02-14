package com.boco.eoms.eva.dao;

import java.util.List;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.eva.model.EvaTemplateTemp;

/**
 * 
 * <p>
 * Title:考核模板(临时)Dao接口
 * </p>
 * <p>
 * Description:考核模板(临时)Dao接口
 * </p>
 * <p>
 * Date:2010-7-11
 * </p>
 * 
 * @author 贲伟玮
 * @version 1.0
 * 
 */
public interface IEvaTemplateTempDao extends Dao {

	/**
	 * 根据主键id查询模板
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public EvaTemplateTemp getTemplate(String id);

	/**
	 * 保存模板
	 * 
	 * @param template
	 *            模板
	 */
	public void saveEvaTemplateTemp(EvaTemplateTemp template);

	/**
	 * 删除模板
	 * 
	 * @param template
	 *            模板
	 */
	public void removeEvaTemplateTemp(EvaTemplateTemp template);

	/**
	 * 根据模板Id返回模板名称
	 * 
	 * @param id
	 *            模板Id
	 * @return
	 */
	public String id2Name(final String id);
	
	/**
	 * 根据模板Id返回模板
	 * 
	 * @param id
	 *            模板Id
	 * @return
	 */	
	public EvaTemplateTemp getEvaTemplateTemp(final String id);	
}
