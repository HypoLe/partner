package com.boco.eoms.parter.baseinfo.fee.dao.hibernate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.parter.baseinfo.fee.dao.PartnerFeeInfoDao;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeInfo;

/**
 * <p>
 * Title:合作伙伴付费信息 dao的hibernate实现
 * </p>
 * <p>
 * Description:合作伙伴付费信息
 * </p>
 * <p>
 * Wed Sep 09 11:22:35 CST 2009
 * </p>
 * 
 * @author lvweihua
 * @version 3.5
 * 
 */
public class PartnerFeeInfoDaoHibernate extends BaseDaoHibernate implements
		PartnerFeeInfoDao, ID2NameDAO {

	/**
	 * 
	 * @see com.boco.eoms.parter.baseinfo.fee.PartnerFeeInfoDao#getPartnerFeeInfos()
	 * 
	 */
	public List getPartnerFeeInfos() {

		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerFeeInfo partnerFeeInfo";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	/**
	 * 
	 * @see com.boco.eoms.parter.baseinfo.fee.PartnerFeeInfoDao#getPartnerFeeInfo(java.lang.String)
	 * 
	 */
	public PartnerFeeInfo getPartnerFeeInfo(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerFeeInfo partnerFeeInfo where partnerFeeInfo.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PartnerFeeInfo) result.iterator().next();
				} else {
					return new PartnerFeeInfo();
				}
			}
		};
		return (PartnerFeeInfo) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 通过付款计划id查询 付费信息
	 * @param id
	 * @return
	 */
	public List getPartnerFeeInfoByPlanId(final String planId) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerFeeInfo partnerFeeInfo where partnerFeeInfo.planId=:planId";
				Query query = session.createQuery(queryStr);
				query.setString("planId", planId);
				List list = query.list();
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.parter.baseinfo.fee.PartnerFeeInfoDao#savePartnerFeeInfos(com.boco.eoms.parter.baseinfo.fee.PartnerFeeInfo)
	 * 
	 */
	public void savePartnerFeeInfo(final PartnerFeeInfo partnerFeeInfo) {
		if ((partnerFeeInfo.getId() == null)
				|| (partnerFeeInfo.getId().equals("")))
			getHibernateTemplate().save(partnerFeeInfo);
		else
			getHibernateTemplate().saveOrUpdate(partnerFeeInfo);
	}

	/**
	 * 
	 * @see com.boco.eoms.parter.baseinfo.fee.PartnerFeeInfoDao#removePartnerFeeInfos(java.lang.String)
	 * 
	 */
	public void removePartnerFeeInfo(final String id) {
		getHibernateTemplate().delete(getPartnerFeeInfo(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 * 
	 */
	public String id2Name(String id) throws DictDAOException {
		PartnerFeeInfo partnerFeeInfo = this.getPartnerFeeInfo(id);
		if (partnerFeeInfo == null) {
			return "";
		}
		// TODO 请修改代码
		return partnerFeeInfo.getName();
	}

	/**
	 * 
	 * @see com.boco.eoms.parter.baseinfo.fee.PartnerFeeInfoDao#getPartnerFeeInfos(java.lang.Integer,java.lang.Integer,java.lang.String)
	 * 
	 */
	public Map getPartnerFeeInfos(final Integer curPage,
			final Integer pageSize, final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerFeeInfo partnerFeeInfo";
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

	/**
	 * 根据收款单位查询 付费信息列表
	 * 
	 * @param collectUnit
	 * @return
	 */
	public List getPartnerFeeInfosByCollectUnit(final String collectUnit,
			final String startDate, final String endDate) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String sql = "select * from pnr_fee_info t where t.pay_date between to_date(?, 'yyyy-MM-dd HH24:mi:ss') AND to_date(?, 'yyyy-MM-dd HH24:mi:ss')"
						+ " and t.collect_unit =?";
				// SqlQuery 查询
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				List result = sqlQuery.addEntity(PartnerFeeInfo.class)
						.setString(0, startDate).setString(1, endDate)
						.setString(2, collectUnit).list();

				return result;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	/**
	 * 根据付款单位查询 付费信息 oracle
	 * 
	 * @param payUnit
	 * @return
	 */
	public List getPartnerFeeInfosByPayUnit(final String payUnit,
			final String startDate, final String endDate) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String sql = "select * from pnr_fee_info t where t.pay_date between to_date(?, 'yyyy-MM-dd HH24:mi:ss') AND to_date(?, 'yyyy-MM-dd HH24:mi:ss')"
						+ " and t.pay_unit =?";
				// SqlQuery 查询
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				List result = sqlQuery.addEntity(PartnerFeeInfo.class)
						.setString(0, startDate).setString(1, endDate)
						.setString(2, payUnit).list();

				return result;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	/**
	 * 根据合同编号查询 付费信息 oracle
	 * 
	 * @param compactNo
	 * @return
	 */
	public List getPartnerFeeInfosByCompactNo(final String compactNo,
			final String startDate, final String endDate) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String sql = "select * from pnr_fee_info t ";
				//Informix数据库
				if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
					sql = sql + " where t.pay_date between ? AND ? and t.compact_no =?";
				}
				//Oracle数据库		
				else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
					sql = sql + " where t.pay_date between to_date(?, 'yyyy-MM-dd HH24:mi:ss') AND to_date(?, 'yyyy-MM-dd HH24:mi:ss')"
						+ " and t.compact_no =?";
				}
				// SqlQuery 查询
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				List result = sqlQuery.addEntity(PartnerFeeInfo.class)
						.setString(0, startDate).setString(1, endDate)
						.setString(2, compactNo).list();

				return result;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	/**
	 * 收款单位收款金额统计(oracle)
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map getFeeCollectStatistics(final String collectUnit,
			final String startDate, final String endDate) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				List result = new ArrayList();
				StringBuffer queryStr = new StringBuffer(
						"select t.collect_unit as collectUnit, ");
				queryStr.append("sum(t.pay_fee) as countFee  ");
				queryStr.append("from pnr_fee_info t ");
				queryStr
						.append("where t.pay_date BETWEEN to_date(?, 'yyyy-MM-dd HH24:mi:ss') AND to_date(?, 'yyyy-MM-dd HH24:mi:ss') ");
				queryStr.append("and t.collect_unit is not null ");
				if (!"".equals(collectUnit)) {
					queryStr.append(" and t.collect_unit=? ");
				}
				queryStr.append("group by t.collect_unit");

				SQLQuery query = session.createSQLQuery(queryStr.toString());
				query.addScalar("collectUnit", org.hibernate.Hibernate.STRING);
				query.addScalar("countFee", org.hibernate.Hibernate.INTEGER);
				query.setString(0, startDate);
				query.setString(1, endDate);
				if (!"".equals(collectUnit)) {
					query.setString(2, collectUnit);
				}
				result = query.list();
				Integer total = new Integer(result.size());
				HashMap map = new HashMap();
				map.put("total", total);
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}

	/**
	 * 按付款单位付款金额统计 oracle
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map getFeePayStatistics(final String payUnit,
			final String startDate, final String endDate) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				List result = new ArrayList();
				StringBuffer queryStr = new StringBuffer(
						"select t.pay_unit as payUnit, "); // 收款单位
				queryStr.append("sum(t.pay_fee) as countFee  ");
				queryStr.append("from pnr_fee_info t ");
				queryStr
						.append("where t.pay_date BETWEEN to_date(?, 'yyyy-MM-dd HH24:mi:ss') AND to_date(?, 'yyyy-MM-dd HH24:mi:ss') ");
				queryStr.append("and t.pay_unit is not null ");
				if (!"".equals(payUnit)) {
					queryStr.append(" and t.pay_unit=? ");
				}

				queryStr.append("group by t.pay_unit");

				SQLQuery query = session.createSQLQuery(queryStr.toString());
				query.addScalar("payUnit", org.hibernate.Hibernate.STRING);
				query.addScalar("countFee", org.hibernate.Hibernate.INTEGER);
				query.setString(0, startDate);
				query.setString(1, endDate);
				if (!"".equals(payUnit)) {
					query.setString(2, payUnit);
				}
				result = query.list();
				Integer total = new Integer(result.size());
				HashMap map = new HashMap();
				map.put("total", total);
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}

	/**
	 * 按合同统计 付款金额 oracle
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map getFeeCompactStatistics(final String compactNo,
			final String startDate, final String endDate) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				List result = new ArrayList();
				StringBuffer queryStr = new StringBuffer(
						"select t.compact_no as compactNO, "); // 收款单位
				queryStr.append("sum(t.pay_fee) as countFee  "); //
				queryStr.append("from pnr_fee_info t ");
				queryStr
						.append("where t.pay_date BETWEEN to_date(?, 'yyyy-MM-dd HH24:mi:ss') AND to_date(?, 'yyyy-MM-dd HH24:mi:ss') ");
				queryStr.append("and t.compact_no is not null  ");
				if (!"".equals(compactNo)) {
					queryStr.append(" and t.compact_no=? ");
				}

				queryStr.append("group by t.compact_no");

				SQLQuery query = session.createSQLQuery(queryStr.toString());
				query.addScalar("compactNo", org.hibernate.Hibernate.STRING);
				query.addScalar("countFee", org.hibernate.Hibernate.INTEGER);
				query.setString(0, startDate);
				query.setString(1, endDate);
				if (!"".equals(compactNo)) {
					query.setString(2, compactNo);
				}
				result = query.list();
				Integer total = new Integer(result.size());
				HashMap map = new HashMap();
				map.put("total", total);
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}

	// ----------------------------------------informax-----------------------------------------------------------------------------------
	/**
	 * 根据收款单位查询 付费信息 informix
	 * 
	 * @param collectUnit
	 * @return
	 */
	public List getPartnerFeeInfosByCollectUnit(final String collectUnit,
			final Date startDate, final Date endDate) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String sql = "select * from pnr_fee_info t where t.pay_date between ? AND ? "
						+ " and t.collect_unit =?";
				// SqlQuery 查询
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				List result = sqlQuery.addEntity(PartnerFeeInfo.class).setTimestamp(
						0, startDate).setTimestamp(1, endDate).setString(2,
						collectUnit).list();

				return result;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	/**
	 * 根据付款单位查询 付费信息informix
	 * 
	 * @param payUnit
	 * @return
	 */
	public List getPartnerFeeInfosByPayUnit(final String payUnit,
			final Date startDate, final Date endDate) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String sql = "select * from pnr_fee_info t where t.pay_date between ? AND ? "
						+ " and t.pay_unit =?";
				// SqlQuery 查询
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				List result = sqlQuery.addEntity(PartnerFeeInfo.class).setTimestamp(
						0, startDate).setTimestamp(1, endDate).setString(2, payUnit)
						.list();

				return result;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	/**
	 * 根据合同编号查询 付费信息informix
	 * 
	 * @param compactNo
	 * @return
	 */
	public List getPartnerFeeInfosByCompactNo(final String compactNo,
			final Date startDate, final Date endDate) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String sql = "select * from pnr_fee_info t where t.pay_date between ? AND ? "
						+ " and t.compact_no =?";
				// SqlQuery 查询
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				List result = sqlQuery.addEntity(PartnerFeeInfo.class).setTimestamp(
						0, startDate).setTimestamp(1, endDate).setString(2,
						compactNo).list();

				return result;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	/**
	 * 收款单位收款金额统计(informix)
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map getFeeCollectStatistics(final String collectUnit,final Date startDate, final Date endDate) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				List result = new ArrayList();
				StringBuffer queryStr = new StringBuffer(
						"select t.collect_unit as collectUnit, ");
				queryStr.append("sum(t.pay_fee) as countFee  ");
				queryStr.append("from pnr_fee_info t ");
				queryStr
						.append("where t.pay_date BETWEEN ? AND ? ");
				queryStr.append("and t.collect_unit is not null ");
				if (!"".equals(collectUnit)) {
					queryStr.append(" and t.collect_unit=? ");
				}
				queryStr.append("group by t.collect_unit");

				SQLQuery query = session.createSQLQuery(queryStr.toString());
				query.addScalar("collectUnit", org.hibernate.Hibernate.STRING);
				query.addScalar("countFee", org.hibernate.Hibernate.INTEGER);
				query.setTimestamp(0, startDate);
				query.setTimestamp(1, endDate);
				if (!"".equals(collectUnit)) {
					query.setString(2, collectUnit);
				}
				result = query.list();
				Integer total = new Integer(result.size());
				HashMap map = new HashMap();
				map.put("total", total);
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}

	/**
	 * 按付款单位付款金额统计informix
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map getFeePayStatistics(final String payUnit, final Date startDate,
			final Date endDate) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				List result = new ArrayList();
				StringBuffer queryStr = new StringBuffer(
						"select t.pay_unit as payUnit, "); // 收款单位
				queryStr.append("sum(t.pay_fee) as countFee  ");
				queryStr.append("from pnr_fee_info t ");
				queryStr
						.append("where t.pay_date BETWEEN ? AND ? ");
				queryStr.append("and t.pay_unit is not null ");
				if (!"".equals(payUnit)) {
					queryStr.append(" and t.pay_unit=? ");
				}
				queryStr.append("group by t.pay_unit");

				SQLQuery query = session.createSQLQuery(queryStr.toString());
				query.addScalar("payUnit", org.hibernate.Hibernate.STRING);
				query.addScalar("countFee", org.hibernate.Hibernate.INTEGER);
				query.setTimestamp(0, startDate);
				query.setTimestamp(1, endDate);
				if (!"".equals(payUnit)) {
					query.setString(2, payUnit);
				}
				result = query.list();
				Integer total = new Integer(result.size());
				HashMap map = new HashMap();
				map.put("total", total);
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}

	/**
	 * 按合同统计 付款金额informix
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Map getFeeCompactStatistics(final String compactNo,
			final Date startDate, final Date endDate) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				List result = new ArrayList();
				StringBuffer queryStr = new StringBuffer(
						"select t.compact_no as compactNO, "); // 收款单位
				queryStr.append("sum(t.pay_fee) as countFee  "); //
				queryStr.append("from pnr_fee_info t ");
				queryStr
						.append("where t.pay_date BETWEEN ? AND ? ");
				queryStr.append("and t.compact_no is not null  ");
				if (!"".equals(compactNo)) {
					queryStr.append(" and t.compact_no=? ");
				}

				queryStr.append("group by t.compact_no");

				SQLQuery query = session.createSQLQuery(queryStr.toString());
				query.addScalar("compactNo", org.hibernate.Hibernate.STRING);
				query.addScalar("countFee", org.hibernate.Hibernate.INTEGER);
				query.setTimestamp(0, startDate);
				query.setTimestamp(1, endDate);
				if (!"".equals(compactNo)) {
					query.setString(2, compactNo);
				}
				result = query.list();
				Integer total = new Integer(result.size());
				HashMap map = new HashMap();
				map.put("total", total);
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}
}