package com.boco.eoms.netresource.point.mgr;

import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.netresource.line.util.LinesImportResult;
import com.boco.eoms.netresource.point.model.Points;

/**
 * <p>
 * Title:标点信息管理
 * </p>
 * <p>
 * Description:标点信息管理
 * </p>
 * <p>
 * Thu Feb 16 18:22:16 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
 public interface IPointsMgr {
 
    /**
     *
     * 取标点信息管理 列表
     * @return 返回标点信息管理列表
     */
    public List getPointss();

    /**
     * 根据条件查询
     */
    public List getPointsByProperty(String whereStr);
    
    /**
     * 根据主键查询标点信息管理
     * @param id 主键
     * @return 返回某id的对象
     */
    public Points getPoints(final String id);

    /**
     * 保存标点信息管理
     * @param points 标点信息管理
     */
    public void savePoints(Points points);

    /**
     * 根据主键删除标点信息管理
     * @param id 主键
     */
    public void removePoints(final String id);
    
    /**
     * 根据主键批量删除标点信息管理
     * @param ids 主键
     */
    public void removePoints(final String[] ids);

    /**
     * 根据条件分页查询标点信息管理
     * @param curPage 当前页
     * @param pageSize 每页包含记录条数
     * @param whereStr 查询条件
     * @return 返回标点信息管理的分页列表
     */
    public Map getPointss(final Integer curPage, final Integer pageSize,
            final String whereStr);
    
    /**
	 * 标点信息通过Excel导入
	 * @param formFile
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public LinesImportResult importFromFile(FormFile formFile, Map params) throws Exception;

}