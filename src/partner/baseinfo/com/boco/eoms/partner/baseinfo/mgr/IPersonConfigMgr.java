package com.boco.eoms.partner.baseinfo.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.baseinfo.model.PersonConfig;

/**
 * <p>
 * Title:人员配置
 * </p>
 * <p>
 * Description:人员配置
 * </p>
 * <p>
 * Tue Dec 08 15:14:23 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
 public interface IPersonConfigMgr {
 
	/**
	 *
	 * 取人员配置 列表
	 * @return 返回人员配置列表
	 */
	public List getPersonConfigs();
    
	/**
	 * 根据主键查询人员配置
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public PersonConfig getPersonConfig(final String id);
    
	/**
	 * 保存人员配置
	 * @param personConfig 人员配置
	 */
	public void savePersonConfig(PersonConfig personConfig);
    
	/**
	 * 根据主键删除人员配置
	 * @param id 主键
	 */
	public void removePersonConfig(final String id);
    
	/**
	 * 根据条件分页查询人员配置
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回人员配置的分页列表
	 */
	public Map getPersonConfigs(final Integer curPage, final Integer pageSize,
			final String whereStr);
    /**
     *根据条件取人员配置列表
     * @param where where条件
     * @return 返回人员配置列表
     */
	public List getPersonConfigs(final String where) ;		
}