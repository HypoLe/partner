package com.boco.eoms.partner.serviceArea.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.serviceArea.model.Site;

/**
 * <p>
 * Title:站点信息管理
 * </p>
 * <p>
 * Description:站点信息管理
 * </p>
 * <p>
 * Tue Nov 17 17:51:41 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
 public interface ISiteMgr {
 
	/**
	 *
	 * 取站点信息管理 列表
	 * @return 返回站点信息管理列表
	 */
	public List getSites();
	
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
	 * 根据主键查询站点信息管理
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public Site getSite(final String id);
    
	/**
	 * 保存站点信息管理
	 * @param site 站点信息管理
	 */
	public void saveSite(Site site);
    
	/**
	 * 根据主键删除站点信息管理
	 * @param id 主键
	 */
	public void removeSite(final String id);
    
	/**
	 * 根据条件分页查询站点信息管理
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回站点信息管理的分页列表
	 */
	public Map getSites(final Integer curPage, final Integer pageSize,
			final String whereStr);
    /**
     * 根据GridNo查站点
     * @param gridNo 站点名称
     * @return list中为符合条件的参数
     */ 
    public List getSitesBySiteNo( final String siteNo );
    /**
     * 根据SiteNo查询站点信息管理
     * @param SiteNo 站点站号
     * @return 返回某基站站号的基站信息
     */    
    public Site getSiteBySiteNo(final String id);
    /**
     * 根据条件查询站点总数
     * @param whereStr 条件语句
     * @return 返回符合条件的站点总数
     */    
    public Integer getSitesNo(final String whereStr) ;		
}