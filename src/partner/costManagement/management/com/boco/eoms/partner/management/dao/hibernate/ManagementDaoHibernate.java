package com.boco.eoms.partner.management.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.management.dao.IManagementDao;
import com.boco.eoms.partner.management.model.Management;

/**
 * <p>
 * Title:管理成本 dao的hibernate实现
 * </p>
 * <p>
 * Description:管理成本
 * </p>
 * <p>
 * Wed Mar 28 09:29:15 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
public class ManagementDaoHibernate extends BaseDaoHibernate implements IManagementDao {
	
    /**
     * 
     * @see com.boco.eoms.partner.management.IManagementDao#getManagements()
     *      
     */
    public List getManagements() {
	
        HibernateCallback callback = new HibernateCallback() {
                public Object doInHibernate(Session session)
                    throws HibernateException {
                String queryStr = "from Management management where 1=1 and isDeleted = 0 ";
                queryStr += " order by createTime desc ";
                Query query = session.createQuery(queryStr);
                return query.list();
            }
        };
        return (List) getHibernateTemplate().execute(callback);		
    }
	
    /**
     *
     * @see com.boco.eoms.partner.management.IManagementDao#getManagement(java.lang.String)
     *
     */
    public Management getManagement(final String id) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                String queryStr = "from Management management where management.id=:id";
                Query query = session.createQuery(queryStr);
                query.setString("id", id);
                query.setFirstResult(0);
                query.setMaxResults(1);
                List result = query.list();
                if (result != null && !result.isEmpty()) {
                    return (Management) result.iterator().next();
                } else {
                    return new Management();
                }
            }
        };
        return (Management) getHibernateTemplate().execute(callback);
    }
	
    /**
     * 
     * @see com.boco.eoms.partner.management.IManagementDao#saveManagements(com.boco.eoms.partner.management.Management)
     *      
     */
    public void saveManagement(final Management management) {
        if ((management.getId() == null) || (management.getId().equals("")))
            getHibernateTemplate().save(management);
        else
            getHibernateTemplate().saveOrUpdate(management);
    }

    /**
     * 
     * @see com.boco.eoms.partner.management.IManagementDao#removeManagements(java.lang.String)
     *      
     */
    public void removeManagement(final String id) {
        if(null != id && !"".equals(id)){
        	Management management = getManagement(id);
        	if(null != management){
        		management.setIsDeleted(1);
        		saveManagement(management);
        	}
        }
    	//getHibernateTemplate().delete(getManagement(id));
    }
 
    /**
     * 
     * @see com.boco.eoms.partner.management.IManagementDao#getManagements(java.lang.Integer,java.lang.Integer,java.lang.String)
     *      
     */ 
    public Map getManagements(final Integer curPage, final Integer pageSize,
             final String whereStr) {
        HibernateCallback callback = new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
            	
                String queryStr = "from Management management where 1=1 and isDeleted = 0 ";
                String queryCountStr = "select count(*) from Management management where 1=1 and isDeleted = 0 ";
                
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
	
}