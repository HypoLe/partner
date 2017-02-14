package com.boco.eoms.partner.deviceAssess.dao.hibernate;

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
import com.boco.eoms.partner.deviceAssess.dao.SoftApplyRecordDao;
import com.boco.eoms.partner.deviceAssess.model.SoftApplyRecord;

/**
 * <p>
 * Title软件申请问题记录 dao的hibernate实现
 * </p>
 * <p>
 * Description:软件申请问题记录
 * </p>
 * <p>
 * Sun Sep 26 09:32:29 CST 2011
 * </p>
 * 
 * @author zhangkeqi 
 * @version 1.0
 * 
 */
public class SoftApplyRecordDaoHibernate extends BaseDaoHibernate implements SoftApplyRecordDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.deviceassessSoftApplyRecordDao#getSoftApplyRecords()
	 *      
	 */
	public List getSoftApplyRecords() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from SoftApplyRecord sar";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.deviceassess.SoftApplyRecordDao#getSoftApplyRecord(java.lang.String)
	 *
	 */
	public SoftApplyRecord getSoftApplyRecord(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from SoftApplyRecord sar where sar.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (SoftApplyRecord) result.iterator().next();
				} else {
					return null;
				}
			}
		};
		return (SoftApplyRecord) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.SoftApplyRecordDao#saveSoftApplyRecords(com.boco.eoms.partner.deviceassess.SoftApplyRecord)
	 *      
	 */
	public void saveOrUpdateSoftApplyRecord(final SoftApplyRecord SoftApplyRecord) {
		if ((SoftApplyRecord.getId() == null) || (SoftApplyRecord.getId().equals("")))
			getHibernateTemplate().save(SoftApplyRecord);
		else
			getHibernateTemplate().saveOrUpdate(SoftApplyRecord);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.SoftApplyRecordDao#removeSoftApplyRecords(java.lang.String)
	 *      
	 */
    public void removeSoftApplyRecord(final String id) {
		getHibernateTemplate().delete(getSoftApplyRecord(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		SoftApplyRecord SoftApplyRecord = this.getSoftApplyRecord(id);
		if(SoftApplyRecord==null){
			return "";
		} else {
			return SoftApplyRecord.getAffairName();
		}
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.deviceassess.SoftApplyRecordDao#getSoftApplyRecords(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getSoftApplyRecords(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from SoftApplyRecord ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				String queryCountStr = "select count(*) " + queryStr;

				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				Query query = session.createQuery(queryStr);
				query
						.setFirstResult(pageSize.intValue()
								* (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				List result = query.list();
				HashMap map = new HashMap();
				map.put("total", Integer.valueOf(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}

	public Map getSoftApplyRecordsWithHQL(final Integer curPage, final Integer pageSize,
			final String hql) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String from = hql.substring(hql.indexOf("from"),hql.length());
				if(from.contains("order")) {
					from = from.substring(0,from.indexOf("order"));
				}
				if(from.contains("group")) {
					from = from.substring(0,from.indexOf("group"));
				}
				String queryCountStr = "select count(*) " + from;

				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				Query query = session.createQuery(hql);
				query
						.setFirstResult(pageSize.intValue()
								* (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				List result = query.list();
				HashMap map = new HashMap();
				map.put("total", Integer.valueOf(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
	
}