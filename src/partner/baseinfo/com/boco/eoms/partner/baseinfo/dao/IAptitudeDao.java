package com.boco.eoms.partner.baseinfo.dao;

import com.boco.eoms.base.dao.Dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.baseinfo.model.Aptitude;

/**
 * <p>
 * Title:合作伙伴资质
 * </p>
 * <p>
 * Description:合作伙伴资质
 * </p>
 * <p>
 * Fri Dec 18 14:11:52 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public interface IAptitudeDao extends Dao {

    /**
    *
    *取合作伙伴资质列表
    * @return 返回合作伙伴资质列表
    */
    public List getAptitudes();
    
    /**
    * 根据主键查询合作伙伴资质
    * @param id 主键
    * @return 返回某id的合作伙伴资质
    */
    public Aptitude getAptitude(final String id);
    
    /**
    *
    * 保存合作伙伴资质    
    * @param aptitude 合作伙伴资质
    * 
    */
    public void saveAptitude(Aptitude aptitude);
    
    /**
    * 根据id删除合作伙伴资质
    * @param id 主键
    * 
    */
    public void removeAptitude(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getAptitudes(final Integer curPage, final Integer pageSize,
			final String whereStr);
    /**
     * 分页取列表
     * @param curPage 当前页
     * @param pageSize 每页显示条数
     * @param whereStr where条件
     * @param ccds 查询资质生效的起始时间
     * @param ccde 查询资质生效时间的结束时间
     * @param dds 查询资质失效的起始时间
     * @param dde 查询资质失效的结束时间
     * @return map中total为条数,result(list) curPage页的记录
     */ 
	public Map getAptitudes(final Integer curPage, final Integer pageSize,
			final String whereStr, final Date asts ,final Date aste ,final Date aets, final Date aete) ;
    /**
     * 按条件取合作伙伴资质列表
     * @param where where条件
     * @return 返回合作伙伴资质列表
     */
	public List getAptitudes(final String where);
}