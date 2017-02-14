package com.boco.eoms.partner.assiEva.mgr;

import java.util.ArrayList;

import com.boco.eoms.partner.assiEva.model.AssiEvaKpi;
import com.boco.eoms.partner.assiEva.model.AssiEvaKpiVariable;

/**
 * <p>
 * Title:指标变量业务方法类
 * </p>
 * <p>
 * Description:指标变量业务方法类
 * </p>
 * <p>
 * Date:2008-11-26 下午09:49:45
 * </p>
 * 
 * @author 李秋野
 * @version 3.5.1
 * 
 */
public interface IAssiEvaKpiVariableMgr {

	/**
	 * 根据主键id查询指标变量
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public AssiEvaKpi getKpiVariable(String id);

	/**
	 * 保存指标指标变量
	 * 
	 * @param AssiEvaKpi
	 *            指标
	 */
	public void saveKpiVariable(AssiEvaKpiVariable kpiVariable);

	/**
	 * 根据指标变量主键id删除指标变量
	 * 
	 * @param id
	 *            主键
	 */
	public void removeKpiVariable(String id);

	/**
	 * 查询某模板订制的所有指标变量
	 * 
	 * @param customId
	 *            模板订制id
	 * @return
	 */
	public ArrayList listVariableOfCustom(String customId);

	/**
	 * 删除某模板订制的所有指标变量
	 * 
	 * @param customId
	 *            模板订制id
	 * @return
	 */
	public void removeVariableOfCustom(String customId);
}
