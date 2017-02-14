package com.boco.eoms.netresource.asset.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.netresource.asset.dao.IAssetDao;
import com.boco.eoms.netresource.asset.model.Asset;

/**
 * <p>
 * Title:资产信息管理 dao的hibernate实现
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
public class AssetDaoHibernate extends BaseDaoHibernate implements IAssetDao {
	
    /**
     * 
     * @see com.boco.eoms.netresource.asset.IAssetDao#getAssets()
     *      
     */
    public List getAssets() {
	
        HibernateCallback callback = new HibernateCallback() {
                public Object doInHibernate(Session session)
                    throws HibernateException {
                String queryStr = "from Asset asset where 1 = 1 and isDeleted = 0 ";
                queryStr += " order by createTime desc ";
                Query query = session.createQuery(queryStr);
                return query.list();
            }
        };
        return (List) getHibernateTemplate().execute(callback);		
    }
	
    /**
     *
     * @see com.boco.eoms.netresource.asset.IAssetDao#getAsset(java.lang.String)
     *
     */
    public Asset getAsset(final String id) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                String queryStr = "from Asset asset where asset.id=:id";
                Query query = session.createQuery(queryStr);
                query.setString("id", id);
                query.setFirstResult(0);
                query.setMaxResults(1);
                List result = query.list();
                if (result != null && !result.isEmpty()) {
                    return (Asset) result.iterator().next();
                } else {
                    return new Asset();
                }
            }
        };
        return (Asset) getHibernateTemplate().execute(callback);
    }
	
    /**
     * 
     * @see com.boco.eoms.netresource.asset.IAssetDao#saveAssets(com.boco.eoms.netresource.asset.Asset)
     *      
     */
    public void saveAsset(final Asset asset) {
        if ((asset.getId() == null) || (asset.getId().equals("")))
            getHibernateTemplate().save(asset);
        else
            getHibernateTemplate().saveOrUpdate(asset);
    }

    /**
     * 
     * @see com.boco.eoms.netresource.asset.IAssetDao#removeAssets(java.lang.String)
     *      
     */
    public void removeAsset(final String id) {
    	if(null != id && !"".equals(id)){
    		Asset asset = this.getAsset(id);
    		if(null != asset){
    			asset.setIsDeleted(1); //假删除 0：未删除  1：删除
    			this.saveAsset(asset);
    		}
    	}
        //getHibernateTemplate().delete(getAsset(id));
    }
 
    /**
     * 
     * @see com.boco.eoms.netresource.asset.IAssetDao#getAssets(java.lang.Integer,java.lang.Integer,java.lang.String)
     *      
     */ 
    public Map getAssets(final Integer curPage, final Integer pageSize,
             final String whereStr) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                
            	String queryStr = "from Asset asset where 1 = 1 and isDeleted = 0 ";
            	String queryCountStr = "select count(*) from Asset asset where 1 = 1 and isDeleted = 0 ";
            	
                if (whereStr != null && whereStr.length() > 0){
                	queryStr += whereStr;
                	queryCountStr += whereStr;
                }
                queryStr += " order by createTime desc ";
                
                int total = ((Integer) session.createQuery(queryCountStr)
                        .iterate().next()).intValue();
                Query query = session.createQuery(queryStr);
                query.setFirstResult(pageSize.intValue() * (curPage.intValue()));
                query.setMaxResults(pageSize.intValue());
                List result = query.list();
                HashMap map = new HashMap();
                map.put("total", new Integer(total));
                map.put("result", result);
                return map;
            }
        };
        return (Map) getHibernateTemplate().execute(callback);
    }

    /**
     * 通过指定条件查询资产
     * @param whereStr 条件
     * @return 资产list
     */
	public List getAssetByWhereStr(String whereStr) {
		if(null != whereStr && !"".equals(whereStr)){
			String queryString = "from Asset asset where 1 = 1 and isDeleted = 0 " + whereStr;
			return getHibernateTemplate().find(queryString);
		}
		return null;
	}
	
	/**
	 * 根据条形码获取资产信息对象
	 */
	public Asset getAssetByBarCode(String assetBarCode) {
		if(null != assetBarCode && !"".equals(assetBarCode)){
			String queryStr = "from Asset asset where asset.assetBarCode = '"+assetBarCode.trim()+"' ";
			List list = getHibernateTemplate().find(queryStr);
			
			if(null != list && list.size()>0){
				return (Asset)list.get(0);
			}
		}
		return new Asset();
	}
	
}