/**
 * 
 */
package com.boco.eoms.partner.assess.AssReport.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.partner.assess.AssReport.dao.IAssConfirmDao;
import com.boco.eoms.partner.assess.AssReport.model.AssConfirm;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Dec 2, 2010 6:27:43 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class AssConfirmDaoHibernate extends BaseDaoHibernate implements IAssConfirmDao {
	
	public void saveAssConfirm(AssConfirm assConfirm) {
		if (null == assConfirm.getId() || "".equals(assConfirm.getId())) {
			getHibernateTemplate().save(assConfirm);
		} else {
			getHibernateTemplate().saveOrUpdate(assConfirm);
		}
	}
	
	public List getAssConfirmList( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from AssConfirm assConfirm ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	

}
