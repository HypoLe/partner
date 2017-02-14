package com.boco.eoms.netresource.point.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.netresource.point.dao.IPointsDao;
import com.boco.eoms.netresource.point.model.Points;

/**
 * <p>
 * Title:标点信息管理 dao的hibernate实现
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
public class PointsDaoHibernate extends BaseDaoHibernate implements IPointsDao , ID2NameDAO{
	
    /**
     * 
     * @see com.boco.eoms.netresource.point.IPointsDao#getPointss()
     *      
     */
    public List getPointss() {
	
        HibernateCallback callback = new HibernateCallback() {
                public Object doInHibernate(Session session)
                    throws HibernateException {
                String queryStr = "from Points points where 1=1 and isdeleted = '0' ";
                queryStr += " order by createTime desc ";
                Query query = session.createQuery(queryStr);
                return query.list();
            }
        };
        return (List) getHibernateTemplate().execute(callback);		
    }
	
    /**
     *
     * @see com.boco.eoms.netresource.point.IPointsDao#getPoints(java.lang.String)
     *
     */
    public Points getPoints(final String id) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                String queryStr = "from Points points where points.id=:id";
                Query query = session.createQuery(queryStr);
                query.setString("id", id);
                query.setFirstResult(0);
                query.setMaxResults(1);
                List result = query.list();
                if (result != null && !result.isEmpty()) {
                    return (Points) result.iterator().next();
                } else {
                    return new Points();
                }
            }
        };
        return (Points) getHibernateTemplate().execute(callback);
    }
	
    /**
     * 
     * @see com.boco.eoms.netresource.point.IPointsDao#savePointss(com.boco.eoms.netresource.point.Points)
     *      
     */
    public void savePoints(final Points points) {
        if ((points.getId() == null) || (points.getId().equals("")))
            getHibernateTemplate().save(points);
        else
            getHibernateTemplate().saveOrUpdate(points);
    }

    /**
     * 
     * @see com.boco.eoms.netresource.point.IPointsDao#removePointss(java.lang.String)
     *      
     */
    public void removePoints(final String id) {
    	if (id != null && !"".equals(id)) {
	    	Points points = getPoints(id);
	    	if(null != points){
	    		points.setIsdeleted("1");//假删除
	        	savePoints(points);
	    	}
    	}
    }
 
    /**
     * 
     * @see com.boco.eoms.netresource.point.IPointsDao#getPointss(java.lang.Integer,java.lang.Integer,java.lang.String)
     *      
     */ 
    public Map getPointss(final Integer curPage, final Integer pageSize,
             final String whereStr) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                
            	String queryStr = " from Points points where 1=1 and isdeleted = '0' ";
            	String queryCountStr = "select count(*) from Points points where 1=1 and isdeleted = '0' " ;
            	
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
     * 根据条件查询
     */
	public List getPointsByProperty(final String whereStr) {
        String queryStr = "from Points points where 1=1 and isdeleted = '0' ";
        
    	if(null != whereStr && whereStr.length()>0){
    		queryStr += whereStr;
    		queryStr += " order by createTime desc ";
    	}else{
    		queryStr += " order by createTime desc ";
    	}
    	
    	return (List)getHibernateTemplate().find(queryStr);
	}

	/**
	 * 通过Id查找名称  Id2Name
	 */
	public String id2Name(String id) throws DictDAOException {
		Points points = this.getPoints(id);
		if( null == points || "".equals(points) ){
			return "";
		}
		return points.getPointName();
	}
	
}