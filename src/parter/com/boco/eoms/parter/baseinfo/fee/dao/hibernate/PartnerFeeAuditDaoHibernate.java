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
import com.boco.eoms.parter.baseinfo.fee.dao.PartnerFeeAuditDao;
import com.boco.eoms.parter.baseinfo.fee.dao.PartnerFeePlanDao;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeAudit;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeInfo;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeePlan;
/**
 * <p>
 * Title:费用管理审核
 * </p>
 * <p>
 * Description:费用管理审核
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
public class PartnerFeeAuditDaoHibernate extends BaseDaoHibernate implements PartnerFeeAuditDao,
		ID2NameDAO {
	

	public List getPartnerFeeAudits(final String feeId,final String type) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerFeeAudit partnerFeeAudit where partnerFeeAudit.feeId=:feeId and partnerFeeAudit.type=:type order by partnerFeeAudit.createTime";
				Query query = session.createQuery(queryStr);
				query.setString("feeId", feeId);
				query.setString("type", type);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	public Map getPartnerFeeUnAudits(final Integer curPage,final Integer pageSize,final String userId,final String deptId,final String type) {
		
	
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerFeeAudit partnerFeeAudit where partnerFeeAudit.type ='"+type+"' and partnerFeeAudit.state ='0' and ((partnerFeeAudit.toOrgId = '"+userId+"' and partnerFeeAudit.toOrgType = 'user') or (partnerFeeAudit.toOrgId = '"+deptId+"' and partnerFeeAudit.toOrgType = 'dept')) ";
				String queryCountStr = "select count(*) " + queryStr;
				String queryOrder = " order by partnerFeeAudit.createTime";
				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				Query query = session.createQuery(queryStr+queryOrder);
				query.setFirstResult(pageSize.intValue()
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
	
	
	
	public PartnerFeeAudit getPartnerFeeAuditByState(final String feeId,final String type,final String state) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerFeeAudit partnerFeeAudit where partnerFeeAudit.feeId=:feeId and partnerFeeAudit.type=:type and partnerFeeAudit.state=:state order by partnerFeeAudit.createTime desc";
				Query query = session.createQuery(queryStr);
				query.setString("feeId", feeId);
				query.setString("type", type);
				query.setString("state", state);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PartnerFeeAudit) result.iterator().next();
				} else {
					return new PartnerFeeAudit();
				}
			}
		};
		return (PartnerFeeAudit) getHibernateTemplate().execute(callback);
	}
	
	
	

	public PartnerFeeAudit getPartnerFeeAudit(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerFeeAudit partnerFeeAudit where partnerFeeAudit.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PartnerFeeAudit) result.iterator().next();
				} else {
					return new PartnerFeeAudit();
				}
			}
		};
		return (PartnerFeeAudit) getHibernateTemplate().execute(callback);
	}
	

	public void savePartnerFeeAudit(final PartnerFeeAudit partnerFeeAudit) {
		if ((partnerFeeAudit.getId() == null) || (partnerFeeAudit.getId().equals("")))
			getHibernateTemplate().save(partnerFeeAudit);
		else
			getHibernateTemplate().saveOrUpdate(partnerFeeAudit);
	}
	
	public String id2Name(String id) throws DictDAOException {
		//TODO 请修改代码
		return null;
	}
}