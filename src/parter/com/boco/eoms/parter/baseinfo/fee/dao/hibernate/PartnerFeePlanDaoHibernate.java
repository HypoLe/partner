package com.boco.eoms.parter.baseinfo.fee.dao.hibernate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.parter.baseinfo.fee.dao.PartnerFeePlanDao;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeInfo;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeePlan;

/**
 * <p>
 * Title:合作伙伴付款计划 dao的hibernate实现
 * </p>
 * <p>
 * Description:合作伙伴付款计划
 * </p>
 * <p>
 * Tue Sep 08 09:38:34 CST 2009
 * </p>
 * 
 * @author lvweihua
 * @version 3.5
 * 
 */
public class PartnerFeePlanDaoHibernate extends BaseDaoHibernate implements PartnerFeePlanDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.fee.PartnerFeePlanDao#getPartnerFeePlans()
	 *      
	 */
	public List getPartnerFeePlans() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerFeePlan partnerFeePlan where partnerFeePlan.payStatus=? and t.payState = '2' ";
				Query query = session.createQuery(queryStr);
				query.setString(0, "0");
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 * 查询所有的付款计划
	 * @return
	 */
	public List getPartnerFeePlans1() {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerFeePlan partnerFeePlan";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.partner.baseinfo.fee.PartnerFeePlanDao#getPartnerFeePlan(java.lang.String)
	 *
	 */
	public PartnerFeePlan getPartnerFeePlan(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerFeePlan partnerFeePlan where partnerFeePlan.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PartnerFeePlan) result.iterator().next();
				} else {
					return new PartnerFeePlan();
				}
			}
		};
		return (PartnerFeePlan) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.fee.PartnerFeePlanDao#savePartnerFeePlans(com.boco.eoms.partner.baseinfo.fee.PartnerFeePlan)
	 *      
	 */
	public void savePartnerFeePlan(final PartnerFeePlan partnerFeePlan) {
		if ((partnerFeePlan.getId() == null) || (partnerFeePlan.getId().equals("")))
			getHibernateTemplate().save(partnerFeePlan);
		else
			getHibernateTemplate().saveOrUpdate(partnerFeePlan);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.fee.PartnerFeePlanDao#removePartnerFeePlans(java.lang.String)
	 *      
	 */
    public void removePartnerFeePlan(final String id) {
		getHibernateTemplate().delete(getPartnerFeePlan(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		PartnerFeePlan partnerFeePlan = this.getPartnerFeePlan(id);
		if(partnerFeePlan==null){
			return "";
		}
		//TODO 请修改代码
		return partnerFeePlan.getName();
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.fee.PartnerFeePlanDao#getPartnerFeePlans(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getPartnerFeePlans(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerFeePlan partnerFeePlan ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr ;
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
	
	/**
	 *  统计付款计划数
	 * @param plan 付费计划
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List getFeePlantStatistics(final String plan,
			final String startDate, final String endDate) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				List result = null;
				if ("".equals(plan)) {
					String sql = "select * from pnr_fee_plan t where t.create_Date between to_date(?, 'yyyy-MM-dd HH24:mi:ss') AND to_date(?, 'yyyy-MM-dd HH24:mi:ss')  and t.pay_state = '2' ";
					SQLQuery sqlQuery = session.createSQLQuery(sql);
					result = sqlQuery.addEntity(PartnerFeePlan.class)
							.setString(0, startDate).setString(1, endDate)
							.list();
				} else if (!"".equals(plan)) {
					String sql = "select * from pnr_fee_plan t where t.create_date between to_date(?, 'yyyy-MM-dd HH24:mi:ss') AND to_date(?, 'yyyy-MM-dd HH24:mi:ss')  and t.pay_state = '2' "
							+ " and t.id =?";
					SQLQuery sqlQuery = session.createSQLQuery(sql);
					result = sqlQuery.addEntity(PartnerFeePlan.class)
							.setString(0, startDate).setString(1, endDate)
							.setString(2, plan).list();
				}

				return result;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}
	
	/**
	 *  统计付款计划数  informix
	 * @param plan 付费计划
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List getFeePlantStatistics(final String plan,
			final Date startDate, final Date endDate) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				List result = null;
				if ("".equals(plan)) {
					String sql = "select * from pnr_fee_plan t where t.create_Date between ? AND  ?  and t.pay_state = '2' ";
					SQLQuery sqlQuery = session.createSQLQuery(sql);
					result = sqlQuery.addEntity(PartnerFeePlan.class)
							.setDate(0, startDate).setDate(1, endDate)
							.list();
				} else if (!"".equals(plan)) {
					String sql = "select * from pnr_fee_plan t where t.create_date between ? AND ? and t.pay_state = '2' "
							+ " and t.id =?";
					SQLQuery sqlQuery = session.createSQLQuery(sql);
					result = sqlQuery.addEntity(PartnerFeePlan.class)
							.setTimestamp(0, startDate).setTimestamp(1, endDate)
							.setString(2, plan).list();
				}

				return result;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

    /**
	 * 根据付款单位查询付款计划oracle
	 * @param payUnit 付费单位
	 * @param startDate
	 * @param endDate
	 * @param payFlag 付费标识
	 * @return
	 */
	public List getFeePlantsByPayUnit(final String payUnit,final Timestamp time,final String payStatus){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("from PartnerFeePlan partnerFeePlan where 1=1 ");
				if(payUnit!=null&&!payUnit.equals("")){
					queryStr.append(" and partnerFeePlan.payUnit = :payUnit ");
				}
				if(time!=null){
					queryStr.append(" and partnerFeePlan.planPayDate <= :time ");
				}
				if(payStatus!=null&&!payStatus.equals("")){
					queryStr.append(" and partnerFeePlan.payStatus = :payStatus ");
				}
					queryStr.append(" and partnerFeePlan.payState = '2' ");
					
				Query query = session.createQuery(queryStr.toString());
				if(payUnit!=null&&!payUnit.equals("")){
					query.setString("payUnit", payUnit);
				}
				if(time!=null){
					query.setTimestamp("time", time);
				}
				if(payStatus!=null&&!payStatus.equals("")){
					query.setString("payStatus", payStatus);
				}

				List result = query.list();
				return result;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

}