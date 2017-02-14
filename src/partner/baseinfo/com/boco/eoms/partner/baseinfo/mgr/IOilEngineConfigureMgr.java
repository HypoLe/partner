package com.boco.eoms.partner.baseinfo.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.baseinfo.model.OilEngineConfigure;

/**
 * <p>
 * Title:油机配置
 * </p>
 * <p>
 * Description:合作伙伴资源配置管理 油机配置
 * </p>
 * <p>
 * Sun Dec 13 21:42:25 CST 2009
 * </p>
 * 
 * @author wangjunfeng
 * @version 1.0
 * 
 */
 public interface IOilEngineConfigureMgr {
 
	/**
	 *
	 * 取油机配置 列表
	 * @return 返回油机配置列表
	 */
	public List getOilEngineConfigures();
	
	/**
	 * 
	 * 判断是否有重复(联合主键)
	 * @return 返回重复条件的列表 
	 */
	public List isUnique(final String whereStr);
	
    
	/**
	 * 根据主键查询油机配置
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public OilEngineConfigure getOilEngineConfigure(final String id);
    
	/**
	 * 保存油机配置
	 * @param oilEngineConfigure 油机配置
	 */
	public void saveOilEngineConfigure(OilEngineConfigure oilEngineConfigure);
    
	/**
	 * 根据主键删除油机配置
	 * @param id 主键
	 */
	public void removeOilEngineConfigure(final String id);
    
	/**
	 * 根据条件分页查询油机配置
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回油机配置的分页列表
	 */
	public Map getOilEngineConfigures(final Integer curPage, final Integer pageSize,
			final String whereStr);
			
}