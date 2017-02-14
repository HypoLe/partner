package com.boco.eoms.partner.deviceAssess.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.partner.deviceAssess.dao.ArithmeticDao;

/**
 * <p> 
 * Title:算法 dao的hibernate实现
 * </p>
 * <p>
 * Description:算法
 * </p>
 * <p>
 * Mon Sep 27 13:45:23 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class ArithmeticDaoHibernate extends BaseDaoHibernate implements ArithmeticDao,
		ID2NameDAO {

	public String id2Name(String id) throws DictDAOException {
		return null;
	}
	/**
	 * (内部处理)故障事件信息
	 */	
	public List getInsideDisposesList( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from InsideDispose insideDispose ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
	/**
	 * (内部处理)故障事件信息
	 */
	public List getInsideDisposes(final Date timeEnd,final Date timeStart,final String factory,final String speciality,final String type) {
		
				
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				
				queryStr.append(" select count(amount) from pnr_deviceAssess_insideDispose t ");
				queryStr.append("where t.pigeonhole_time>to_date('"); 
				queryStr.append(timeEnd);
				queryStr.append("','yyyy-MM-dd HH24:mi:ss') and t.pigeonhole_time<to_date('" );
				queryStr.append(timeStart);
				queryStr.append("','yyyy-MM-dd HH24:mi:ss') ");
				if(!"".equals(factory)){
					queryStr.append(" and factory = '");
					queryStr.append(factory);
					queryStr.append("' ");
				}
				if(!"".equals(speciality)){
					queryStr.append(" and speciality = '");
					queryStr.append(speciality);
					queryStr.append("' ");
				}
				if(!"".equals(type)){
					queryStr.append(" and equipment_type = '");
					queryStr.append(type);
					queryStr.append("' ");
				}

				SQLQuery countQuery = session.createSQLQuery(queryStr.toString());
				return countQuery.list();
			} 
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
	/**
	 * (厂家处理)故障事件信息
	 */	
	public List getFactoryDisposesList( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from FactoryDispose factoryDispose ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	/**
	 * 按条件得到设备量
	 */	
	public List getFacilityNumsList( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from FacilityNum facilityNum ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
	
	/**
	 * 按条件得到板件返修设备信息
	 */	
	public List getRepairinfosList( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Repairinfo repairinfo ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
	
	
	/**
	 * 按条件得到厂家设备重大故障事件信息列表
	 */	
	public List getBigFaultsList( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from BigFault bigFault ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
	
	/**
	 * 按条件得到设备问题信息
	 */	
	public List getFacilityinfosList( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Facilityinfo facilityinfo ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
	
	
	/**
	 * 按条件得到软件升级信息
	 */	
	public List getSoftupinfosList( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Softupinfo softupinfo ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
	
	
	/**
	 * 按条件得到咨询服务事件信息
	 */	
	public List getCounselsList( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Counsel counsel ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
	
	
	/**
	 * 按条件得到现场服务事件信息
	 */	
	public List getLserveinfosList( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Lserveinfo lserveinfo ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
	
	
	/**
	 * 按条件得到工程服务事件信息
	 */	
	public List getFtraininfosList( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Ftraininfo ftraininfo ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
	
	
	/**
	 * 按条件得到培训服务事件信息
	 */	
	public List getPeventinfosList( final String whereStr ) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Peventinfo peventinfo ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
}