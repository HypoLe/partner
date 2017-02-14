package com.boco.eoms.partner.baseinfo.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.baseinfo.model.PnrBaseSite;

/**
 * <p>
 * Title:合作伙伴站址信息管理
 * </p>
 * <p>
 * Description:站址信息管理
 * </p>
 * <p>
 * Wed Mar 24 14:01:56 CST 2010
 * </p>
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
 public interface IPnrBaseSiteMgr {
 
	/**
	 *
	 * 取合作伙伴站址信息管理 列表
	 * @return 返回合作伙伴站址信息管理列表
	 */
	public List getPnrBaseSites();
    
	/**
	 * 根据主键查询合作伙伴站址信息管理
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public PnrBaseSite getPnrBaseSite(final String id);
    
	/**
	 * 保存合作伙伴站址信息管理
	 * @param pnrBaseSite 合作伙伴站址信息管理
	 */
	public void savePnrBaseSite(PnrBaseSite pnrBaseSite);
    
	/**
	 * 根据主键删除合作伙伴站址信息管理
	 * @param id 主键
	 */
	public void removePnrBaseSite(final String id);
    
	/**
	 * 根据主键逻辑删除合作伙伴站址信息管理
	 * @param id 主键
	 */
	public void removePnrBaseSite(final String id,String user);
	/**
	 * 根据条件分页查询合作伙伴站址信息管理
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回合作伙伴站址信息管理的分页列表
	 */
	public Map getPnrBaseSites(final Integer curPage, final Integer pageSize,
			final String whereStr);
			
	/**
	 *
	 * 二级联动菜单 加载合作伙伴 列表 (列出所在地市的合作伙伴)
	 * @return 
	 */
	public List listProviderOfCity(final String cityId);
}