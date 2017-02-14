package com.boco.eoms.netresource.asset.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.netresource.asset.model.Asset;
import com.boco.eoms.netresource.asset.mgr.IAssetMgr;
import com.boco.eoms.netresource.asset.dao.IAssetDao;

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
public class AssetMgrImpl implements IAssetMgr {
 
    private IAssetDao  assetDao;

    public IAssetDao getAssetDao() {
        return this.assetDao;
    }

    public void setAssetDao(IAssetDao assetDao) {
        this.assetDao = assetDao;
    }

    /**
     * 
     * @see com.boco.eoms.netresource.asset.IAssetMgr#getAssets()
     *      
     */
    public List getAssets() {
        return assetDao.getAssets();
    }

    /**
     * 
     * @see com.boco.eoms.netresource.asset.IAssetMgr#getAsset(java.lang.String)
     *      
     */
    public Asset getAsset(final String id) {
        return assetDao.getAsset(id);
    }

    /**
     * 
     * @see com.boco.eoms.netresource.asset.IAssetMgr#saveAsset(com.boco.eoms.netresource.asset.Asset)
     *      
     */
    public void saveAsset(Asset asset) {
        assetDao.saveAsset(asset);
    }

    /**
     * 
     * @see com.boco.eoms.netresource.asset.IAssetMgr#removeAsset(java.lang.String)
     *      
     */
    public void removeAsset(final String id) {
        assetDao.removeAsset(id);
    }

    /**
     * 
     * @see com.boco.eoms.netresource.asset.IAssetMgr#removeAsset(java.lang.String[])
     *      
     */
    public void removeAsset(final String[] ids) {
        if (null != ids) {
            for (int i = 0; i < ids.length; i++) {
                this.removeAsset(ids[i]);
            }
        }
    }

    /**
     * 
     * @see com.boco.eoms.netresource.asset.IAssetMgr#getAssets(java.lang.Integer,java.lang.Integer,java.lang.String)
     *      
     */
    public Map getAssets(final Integer curPage, final Integer pageSize,
            final String whereStr) {
        return assetDao.getAssets(curPage, pageSize, whereStr);
    }
    
    /**
     * get asset by whereSre
     */
	public List getAssetByWhereStr(String whereStr) {
		return assetDao.getAssetByWhereStr(whereStr);
	}

	/**
     * get asset by barCode
     */
	public Asset getAssetByBarCode(String assetBarCode) {
		return assetDao.getAssetByBarCode(assetBarCode);
	}
	
}
