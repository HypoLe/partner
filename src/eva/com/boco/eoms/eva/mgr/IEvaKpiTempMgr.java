package com.boco.eoms.eva.mgr;

import java.util.List;

import com.boco.eoms.eva.model.EvaKpiTemp;


/**
 * 
 * <p>
 * Title:考核指标(临时)Dao接口
 * </p>
 * <p>
 * Description:考核指标（临时）Dao接口
 * </p>
 * <p>
 * Date:2010-7-11
 * </p>
 * 
 * @author 贲伟玮
 * @version 1.0
 */
public interface IEvaKpiTempMgr {
	

	/**
	 * 保存指标类型
	 * 
	 * @param EvaKpi
	 *            指标
	 */
	public void saveKpiTemp(EvaKpiTemp kpi);
	
	/**
	 * 删除指标
	 * 
	 * @param kpi
	 *            指标
	 */
	public void removeKpiTemp(EvaKpiTemp kpi);
	
	/**
	 * 根据模板id返回所有属于该模板的指标
	 * 
	 * @param id
	 *            模板Id
	 * @return
	 */
	public List getEvaKpiTemps(final String evaTemplateId);	
}
