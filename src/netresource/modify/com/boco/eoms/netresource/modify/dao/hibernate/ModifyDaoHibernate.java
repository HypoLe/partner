package com.boco.eoms.netresource.modify.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.netresource.modify.dao.IModifyDao;
import com.boco.eoms.netresource.modify.model.Modify;

/**
 * <p>
 * Title:资源变更管理 dao的hibernate实现
 * </p>
 * <p>
 * Description:资源变更管理
 * </p>
 * <p>
 * Tue Feb 21 11:40:03 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
public class ModifyDaoHibernate extends BaseDaoHibernate implements IModifyDao {
	
    /**
     * 
     * @see com.boco.eoms.netresource.modify.IModifyDao#getModifys()
     *      
     */
    public List getModifys() {
	
        HibernateCallback callback = new HibernateCallback() {
                public Object doInHibernate(Session session)
                    throws HibernateException {
                String queryStr = "from Modify modify where 1=1 AND isDeleted = 0 ";
                queryStr += " order by applyTime desc ";
                Query query = session.createQuery(queryStr);
                return query.list();
            }
        };
        return (List) getHibernateTemplate().execute(callback);		
    }
	
    /**
     *
     * @see com.boco.eoms.netresource.modify.IModifyDao#getModify(java.lang.String)
     *
     */
    public Modify getModify(final String id) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                String queryStr = "from Modify modify where modify.id=:id";
                Query query = session.createQuery(queryStr);
                query.setString("id", id);
                query.setFirstResult(0);
                query.setMaxResults(1);
                List result = query.list();
                if (result != null && !result.isEmpty()) {
                    return (Modify) result.iterator().next();
                } else {
                    return new Modify();
                }
            }
        };
        return (Modify) getHibernateTemplate().execute(callback);
    }
	
    /**
     * 
     * @see com.boco.eoms.netresource.modify.IModifyDao#saveModifys(com.boco.eoms.netresource.modify.Modify)
     *      
     */
    public void saveModify(final Modify modify) {
        if ((modify.getId() == null) || (modify.getId().equals("")))
            getHibernateTemplate().save(modify);
        else
            getHibernateTemplate().saveOrUpdate(modify);
    }

    /**
     * 
     * @see com.boco.eoms.netresource.modify.IModifyDao#removeModifys(java.lang.String)
     *      
     */
    public void removeModify(final String id) {
    	if (id != null && !"".equals(id)) {
	        Modify modify = getModify(id);
	        if(null != modify){
	        	modify.setIsDeleted(1);
	        	saveModify(modify);
	        }
    	}
    }
 
    /**
     * 
     * @see com.boco.eoms.netresource.modify.IModifyDao#getModifys(java.lang.Integer,java.lang.Integer,java.lang.String)
     *      
     */ 
    public Map getModifys(final Integer curPage, final Integer pageSize,
             final String whereStr) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                String queryStr = "FROM Modify modify WHERE 1=1 AND isDeleted = 0 ";
                if (whereStr != null && whereStr.length() > 0)
                    queryStr += whereStr;
                queryStr += " order by applyTime desc ";
                
                String queryCountStr = "select count(*) FROM Modify modify WHERE isDeleted = 0 ";
                if (whereStr != null && whereStr.length() > 0)
                	queryCountStr += whereStr;

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


}