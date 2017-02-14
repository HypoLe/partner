package com.boco.eoms.partner.serviceArea.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.serviceArea.model.ResidentSite;

/**
 * <p>
 * Title:驻点信息管理
 * </p>
 * <p>
 * Description:驻点信息管理
 * </p>
 * <p>
 * Tue Nov 17 17:51:41 CST 2009
 * </p>
 * 
 * @version 1.0
 * 
 */
 public interface IResidentSiteMgr {
 
	/**
	 *
	 * 取驻点信息管理 列表
	 * @return 返回驻点信息管理列表
	 */
	public List getResidentSites();
	
	/**
	 *
	 * 二级联动菜单 加载地市 列表
	 * @return 返回抽查记录列表
	 */
	public List listCityOfArea(final String areaId,final String len);

	/**
	 *
	 * 二级联动菜单 加载县区 列表
	 * @return 返回抽查记录列表
	 */
	public List listCountryOfCity(final String cityId ,final String len);
	
    
	/**
	 * 根据主键查询驻点信息管理
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public ResidentSite getResidentSite(final String id);
    
	/**
	 * 保存驻点信息管理
	 * @param residentSite 驻点信息管理
	 */
	public void saveResidentSite(ResidentSite residentSite);
    
	/**
	 * 根据主键删除驻点信息管理
	 * @param id 主键
	 */
	public void removeResidentSite(final String id);
    
	/**
	 * 根据条件分页查询驻点信息管理
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回驻点信息管理的分页列表
	 */
	public Map getResidentSites(final Integer curPage, final Integer pageSize,
			final String whereStr);
    /**
     * 根据GridNo查驻点
     * @param gridNo 驻点名称
     * @return list中为符合条件的参数
     */ 
    public List getResidentSitesByResidentSiteNo( final String residentSiteNo );

    /**
     * 根据条件查询驻点总数
     * @param whereStr 条件语句
     * @return 返回符合条件的驻点总数
     */    
    public Integer getResidentSitesNo(final String whereStr) ;		
}