package com.boco.eoms.materials.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.materials.dao.ContactCompanyDao;
import com.boco.eoms.materials.model.ContactCompany;

/**
 * 往来单位（ContactCompany）Dao实现类
 * 
 * @author wanghongliang
 */
public class ContactCompanyDaoHibernate extends BaseDaoHibernate implements ContactCompanyDao {
	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.ContactCompanyDao#getContactCompany()
	 */
	@SuppressWarnings("unchecked")
	public List<ContactCompany> getContactCompany() {
		return getHibernateTemplate().find("from ContactCompany");
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.ContactCompanyDao#getContactCompany(java.lang.String)
	 */
	public ContactCompany getContactCompany(String id) {
		ContactCompany contactCompany = (ContactCompany) getHibernateTemplate()
				.get(ContactCompany.class, id);
		if (contactCompany == null) {
			throw new ObjectRetrievalFailureException(ContactCompany.class, id);
		}
		return contactCompany;
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.ContactCompanyDao#getContactCompany(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, Integer> getContactCompany(final Integer curPage,
			final Integer pageSize, final String hql) {
		// TODO 自动生成方法存根
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session)
                throws HibernateException
            {
                Map map = new HashMap();
                String whereStr = " from ContactCompany contactCompany ";
                String countStr = " select count(*) from ContactCompany ";
                if(hql != null && !"".equals(hql))
                {
                    whereStr = whereStr + hql;
                    countStr = countStr + hql;
                }
                 
                Query countQuery = session.createQuery(countStr);
                List countList = countQuery.list();
                Number totalCount = 0;
                if(countList != null && countList.size() > 0)
                {
                    totalCount = (Number)countList.get(0);
                }
                Query query = session.createQuery(whereStr);
				//分页查询条
				if(pageSize.intValue()!=-1){
					query.setFirstResult(pageSize.intValue() * (curPage.intValue()));
					query.setMaxResults(pageSize.intValue());
				}
                
                List result = query.list();
                map.put("total", totalCount.intValue());
                map.put("result", result);
                return map;
            }

        };
        return (HashMap)getHibernateTemplate().execute(callback);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.ContactCompanyDao#removeContactCompany(java.lang.String)
	 */
	public void removeContactCompany(String id) {
		getHibernateTemplate().delete(getContactCompany(id));

	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.ContactCompanyDao#saveContactCompany(com.boco.eoms.materials.model.ContactCompany)
	 */
	public void saveContactCompany(ContactCompany contactCompany) {
		if ((contactCompany.getId() == null)
				|| (contactCompany.getId().equals("")))
			getHibernateTemplate().save(contactCompany);
		else
			getHibernateTemplate().saveOrUpdate(contactCompany);
	}

}
