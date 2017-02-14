package com.boco.eoms.parter.baseinfo.fee.dao.hibernate;

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
import com.boco.eoms.parter.baseinfo.fee.dao.PartnerFeeUnitDao;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeUnit;

/**
 * <p>
 * Title:合作伙伴费用单位 dao的hibernate实现
 * </p>
 * <p>
 * Description:合作伙伴费用单位
 * </p>
 * <p>
 * Tue Sep 08 09:38:34 CST 2009
 * </p>
 * 
 * @author lvweihua
 * @version 3.5
 * 
 */
public class PartnerFeeUnitDaoHibernate extends BaseDaoHibernate implements PartnerFeeUnitDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.fee.PartnerFeeUnitDao#getPartnerFeeUnits()
	 *      
	 */
	public List getPartnerFeeUnits() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerFeeUnit partnerFeeUnit";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.baseinfo.fee.PartnerFeeUnitDao#getPartnerFeeUnit(java.lang.String)
	 *
	 */
	public PartnerFeeUnit getPartnerFeeUnit(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerFeeUnit partnerFeeUnit where partnerFeeUnit.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PartnerFeeUnit) result.iterator().next();
				} else {
					return new PartnerFeeUnit();
				}
			}
		};
		return (PartnerFeeUnit) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.fee.PartnerFeeUnitDao#savePartnerFeeUnits(com.boco.eoms.partner.baseinfo.fee.PartnerFeeUnit)
	 *      
	 */
	public void savePartnerFeeUnit(final PartnerFeeUnit partnerFeeUnit) {
		if ((partnerFeeUnit.getId() == null) || (partnerFeeUnit.getId().equals("")))
			getHibernateTemplate().save(partnerFeeUnit);
		else
			getHibernateTemplate().saveOrUpdate(partnerFeeUnit);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.fee.PartnerFeeUnitDao#removePartnerFeeUnits(java.lang.String)
	 *      
	 */
    public void removePartnerFeeUnit(final String id) {
		getHibernateTemplate().delete(getPartnerFeeUnit(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		PartnerFeeUnit partnerFeeUnit = this.getPartnerFeeUnit(id);
		if(partnerFeeUnit==null){
			return "";
		}
		//TODO 请修改代码
		return partnerFeeUnit.getName();
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.fee.PartnerFeeUnitDao#getPartnerFeeUnits(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getPartnerFeeUnits(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerFeeUnit partnerFeeUnit";
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
				map.put("total", new Integer(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
	
}