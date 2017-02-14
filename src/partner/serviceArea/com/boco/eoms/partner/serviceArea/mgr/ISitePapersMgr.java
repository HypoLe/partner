package com.boco.eoms.partner.serviceArea.mgr;

import java.util.List;
import java.util.Map;

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
 public interface ISitePapersMgr {
 
	/**
	 *
	 * 取基站证件信息 列表
	 * @return 返回基站证件信息列表
	 */
	public List getSitePaperss();
    
	/**
	 * 根据主键查询基站证件信息
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public SitePapers getSitePapers(final String id);
    
	/**
	 * 保存基站证件信息
	 * @param sitePapers 基站证件信息
	 */
	public void saveSitePapers(SitePapers sitePapers);
    
	/**
	 * 根据主键删除基站证件信息
	 * @param id 主键
	 */
	public void removeSitePapers(final String id);
    
	/**
	 * 根据条件分页查询基站证件信息
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回基站证件信息的分页列表
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