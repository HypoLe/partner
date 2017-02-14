package com.boco.eoms.partner.serviceArea.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.serviceArea.model.SitePapers;

/**
 * <p>
 * Title:基站证件信息
 * </p>
 * <p>
 * Description:基站证件信息
 * </p>
 * <p>
 * Wed Nov 18 11:29:29 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public interface ISitePapersDao extends Dao {

    /**
    *
    *取基站证件信息列表
    * @return 返回基站证件信息列表
    */
    public List getSitePaperss();
    
    /**
    * 根据主键查询基站证件信息
    * @param id 主键
    * @return 返回某id的基站证件信息
    */
    public SitePapers getSitePapers(final String id);
    
    /**
    *
    * 保存基站证件信息    
    * @param sitePapers 基站证件信息
    * 
    */
    public void saveSitePapers(SitePapers sitePapers);
    
    /**
    * 根据id删除基站证件信息
    * @param id 主键
    * 
    */
    public void removeSitePapers(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getSitePaperss(final Integer curPage, final Integer pageSize,
			final String whereStr);
    /**
     * 根据PaperNo查基站证件信息
     * @param PaperNo 基站证件号
     * @return list中为符合条件的参数
     */     
    public List getSitePapersByPaperNo( final String papersNo );
    /**
     * 根据siteNo查基站证件信息
     * @param siteNo 基站号
     * @return list中为符合条件的参数
     */      
	public List getSitePapersBySiteNo( final Integer siteNo );
}