package com.boco.eoms.partner.baseinfo.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.partner.baseinfo.dao.PartnerUserDao;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;

/**
 * <p>
 * Title:��f��Ϣ dao��hibernateʵ��
 * </p>
 * <p>
 * Description:��f��Ϣ
 * </p>
 * <p>
 * Tue Feb 10 17:33:14 CST 2009
 * </p>
 * 
 * @author liujinlong
 * @version 3.5
 * 
 */
public class PartnerUserDaoHibernate extends CommonGenericDaoImpl<PartnerUser, String> implements PartnerUserDao,ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerUserDao#getPartnerUsers()
	 *      
	 */
	public List getPartnerUsers() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerUser partnerUser where partnerUser.deleted <> '1'";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
    /**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerUserDao#getPartnerUsers(final String where)
	 *      
	 */
	public List getPartnerUsers(final String where) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerUser partnerUser where partnerUser.deleted <> '1' ";
				if (where != null && where.length() > 0)
					queryStr += where  ;
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
	
	/**
	 * 根据今日经纬度记录表查询人员
	 * add by chenruoke
	 * @param where
	 * @return
	 */
	public Map getPartnerUsersForGis(final Integer curPage, final Integer pageSize, final String where ,final String toDay) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerUser partnerUser where partnerUser.deleted <> '1' ";
				if (where != null && where.length() > 0){
					queryStr += where  + " and deleted <> '1'" ;
					queryStr += " and partnerUser.mobilePhone in " +
							" (select distinct (h.phoneNum) from Trajectory h " +
							" where trunc(h.insertTime) = to_date('"+toDay+"','yyyy-mm-dd'))";
				}else{
					queryStr += " and partnerUser.mobilePhone in " +
					" (select distinct (h.phoneNum) from Trajectory h " +
					" where trunc(h.insertTime) = to_date('"+toDay+"','yyyy-mm-dd'))";
					queryStr += " and deleted <> '1'" ;
				}
				String queryCountStr = "select count(partnerUser.id) " + queryStr;
				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				//System.out.println("queryStr==="+queryStr);
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
	/**
	 *
	 * @see com.boco.eoms.partner.baseinfo.PartnerUserDao#getPartnerUser(java.lang.String)
	 *
	 */
	public PartnerUser getPartnerUser(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerUser partnerUser where partnerUser.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PartnerUser) result.iterator().next();
				} else {
					return new PartnerUser();
				}
			}
		};
		return (PartnerUser) getHibernateTemplate().execute(callback);
	}
	
	public PartnerUser getPartnerUserByUserId(String userId){
		String hql = "from PartnerUser where userId='"+userId+"' and deleted='0' ";
		List list = findByHql(hql);
		if(list.size()>0){
			return (PartnerUser)list.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerUserDao#savePartnerUsers(com.boco.eoms.partner.baseinfo.StatDetailVOSPartnerUser)
	 *      
	 */
	public void savePartnerUser(final PartnerUser partnerUser) {
		if ((partnerUser.getId() == null) || (partnerUser.getId().equals("")))
			getHibernateTemplate().save(partnerUser);
		else
			getHibernateTemplate().merge(partnerUser);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerUserDao#removePartnerUsers(java.lang.String)
	 *      
	 */
    public void removePartnerUser(final String id) {
		getHibernateTemplate().delete(getPartnerUser(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 */
	public String id2Name(String id) throws DictDAOException {
		PartnerUser partnerUser = this.getPartnerUser(id);
		if(partnerUser==null){
			return "";
		}
		return partnerUser.getName();
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.baseinfo.PartnerUserDao#getPartnerUsers(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getPartnerUsers(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerUser partnerUser ";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr + " and deleted <> '1'" ;
				else queryStr += " where deleted <> '1'" ;

				String queryCountStr = "select count(partnerUser.id) " + queryStr;

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
	 * 判断人力信息用户ID是否唯一
	 * 
	 *      
	 */
	public Boolean isunique(final String userId) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PartnerUser partnerUser where partnerUser.userId=:userId";
				Query query = session.createQuery(queryStr);
				query.setString("userId", userId);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return Boolean.valueOf(false);
				} else {
					return Boolean.valueOf(true);
				}
			}
		};
		return (Boolean) getHibernateTemplate().execute(callback);
	}
	
	//批量删除某地市某厂商下所有的人力信息，参数是treeNodeId
	public void removePartnerUserByTreeNodeId(final String treeNodeId){
		/*id:EOMS-liujinlong-20090921101314
		开发人邮箱：liujinlong@boco.com.cn
		时间：2009-09-21
		修改目的：将删除改为修改，设置deleted字段为1
		*/
    	final String hql = "update PartnerUser partnerUser set deleted = '1' where partnerUser.treeNodeId = '"
			+ treeNodeId + "'";
    	/*
    	修改结束：EOMS-liujinlong-20090921101314
    	*/
	HibernateCallback callback = new HibernateCallback() {
		public Object doInHibernate(Session session)
				throws HibernateException {
			Query query = session.createQuery(hql);
			query.executeUpdate();
			return null;
		}
	};
	getHibernateTemplate().execute(callback);
	}
	/**
	 * 得到公司的所有USER
	 * 
	 * @param deptid
	 * @return
	 */
	public List getUserByCompidNoSelf(String deptid, String userid) {
		String hql = " from PartnerUser partnerUser where partnerUser.partnerid='"
			+ deptid
			+ "' and partnerUser.userId !='admin' and partnerUser.userId !='"
			+ userid + "' and partnerUser.deleted='0' order by username";
		List list = new ArrayList();
		list = (ArrayList) getHibernateTemplate().find(hql);
		
		return list;
	}
	
	/**
	 * 得到部门的所有USER
	 * 
	 * @param deptid
	 * @return
	 */
	public List getUserByCompids(String deptid) {
		String hql ;
		if("-1".equals(deptid)){
			deptid="402849be32e137740132e151aa8b0009";
			hql= " from PartnerUser partnerUser where partnerUser.partnerid='"
				+ deptid+"'";
//				+ "' and partnerUser.userid !='admin' and deleted='0' order by username";
		}
		else{
//			deptid="402849be32e137740132e151aa8b0009";
			hql= " from PartnerUser partnerUser where partnerUser.partnerid='"
				+ deptid+"'";
//			hql = " from PartnerUser partnerUser where partnerUser.partnerid='"
//					+ deptid
//					+ "' and partnerUser.userid !='admin' and deleted='0' order by username";
		}
		List list = new ArrayList();
		list = (ArrayList) getHibernateTemplate().find(hql);
		return list;
	}
		
}