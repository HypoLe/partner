package com.boco.eoms.eva.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.eva.model.EvaConfirm;

/**
 * 
 * <p>
 * Title:模板确认信息
 * </p>
 * <p>
 * Description:模板确认信息Dao接口
 * </p>
 * <p>
 * Date:2010-7-11
 * </p>
 * 
 * @author 贲伟玮
 * @version 1.0
 * 
 */
public interface IEvaConfirmMgr {

	/**
	 * 删除确认信息
	 * 
	 */
	public void removeEvaConfirm(EvaConfirm evaConfirm);
	/**
	 * 保存确认信息
	 * 
	 */
	public void saveEvaConfirm(EvaConfirm evaConfirm) ;	
	/**
	 * 得到待确认列表
	 * 
	 */	
	public Map getTemplateUnConfirms(final Integer curPage,final Integer pageSize,final String userId,final String deptId) ;
	/**
	 * 得到对应模板的所有确认信息
	 * 
	 */		
	public List getTemplateConfirms(final String evaTemplateId) ;
	/**
	 * 得到对应id的确认信息
	 * 
	 */	
	public EvaConfirm getTemplateConfirmById(final String id) ;
}
