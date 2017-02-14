package com.boco.eoms.partner.baseinfo.dao;

import com.boco.eoms.base.dao.Dao;
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
public interface IPnrBaseSiteDao extends Dao {

    /**
    *
    *取合作伙伴站址信息管理列表
    * @return 返回合作伙伴站址信息管理列表
    */
    public List getPnrBaseSites();
    
    /**
    * 根据主键查询合作伙伴站址信息管理
    * @param id 主键
    * @return 返回某id的合作伙伴站址信息管理
    */
    public PnrBaseSite getPnrBaseSite(final String id);
    
    /**
    *
    * 保存合作伙伴站址信息管理    
    * @param pnrBaseSite 合作伙伴站址信息管理
    * 
    */
    public void savePnrBaseSite(PnrBaseSite pnrBaseSite);
    
    /**
    * 根据id删除合作伙伴站址信息管理
    * @param id 主键
    * 
    */
    public void removePnrBaseSite(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
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