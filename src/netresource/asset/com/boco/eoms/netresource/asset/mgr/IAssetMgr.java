package com.boco.eoms.netresource.asset.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.netresource.asset.model.Asset;

/**
 * <p>
 * Title:资产信息管理
 * </p>
 * <p>
 * Description:资产信息管理
 * </p>
 * <p>
 * Thu Mar 08 09:48:46 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
 public interface IAssetMgr {
 
    /**
     *
     * 取资产信息管理 列表
     * @return 返回资产信息管理列表
     */
    public List getAssets();

    /**
     * 根据主键查询资产信息管理
     * @param id 主键
     * @return 返回某id的对象
     */
    public Asset getAsset(final String id);

    /**
     * 保存资产信息管理
     * @param asset 资产信息管理
     */
    public void saveAsset(Asset asset);

    /**
     * 根据主键删除资产信息管理
     * @param id 主键
     */
    public void removeAsset(final String id);
    
    /**
     * 根据主键批量删除资产信息管理
     * @param ids 主键
     */
    public void removeAsset(final String[] ids);

    /**
     * 根据条件分页查询资产信息管理
     * @param curPage 当前页
     * @param pageSize 每页包含记录条数
     * @param whereStr 查询条件
     * @return 返回资产信息管理的分页列表
     */
    public Map getAssets(final Integer curPage, final Integer pageSize,
            final String whereStr);

    /**
     * 通过指定条件查询资产
     * @param whereStr 条件
     * @return 资产list
     */
    public List getAssetByWhereStr(String whereStr);
    
    /**
     * 根据条形码查询资产信息
     * @param assetBarCode 条形码
     * @return 返回某assetBarCode的对象
     */
    public Asset getAssetByBarCode(final String assetBarCode);
    
}