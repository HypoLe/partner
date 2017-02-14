package com.boco.eoms.role_tease.dao.hibernate;

import java.util.ArrayList;
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
import com.boco.eoms.role_tease.dao.TawSystemRoleteaseDao;
import com.boco.eoms.role_tease.model.TawSystemRoletease;
import com.boco.eoms.roleWorkflow.model.TawSystemRoleWorkflow;

/**
 * <p>
 * Title:角色梳理 dao的hibernate实现
 * </p>
 * <p>
 * Description:角色梳理
 * </p>
 * <p>
 * Tue Aug 04 11:38:53 CST 2009
 * </p>
 * 
 * @author fengshaohong
 * @version 3.5
 * 
 */
public class TawSystemRoleteaseDaoHibernate extends BaseDaoHibernate implements TawSystemRoleteaseDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.role_tease.TawSystemRoleteaseDao#getTawSystemRoleteases()
	 *      
	 */
	public List getTawSystemRoleteases() {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawSystemRoletease tawSystemRoletease";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	/**
	 *
	 * @see com.boco.eoms.role_tease.TawSystemRoleteaseDao#getTawSystemRoletease(java.lang.String)
	 *
	 */
	public TawSystemRoletease getTawSystemRoletease(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawSystemRoletease tawSystemRoletease where tawSystemRoletease.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (TawSystemRoletease) result.iterator().next();
				} else {
					return new TawSystemRoletease();
				}
			}
		};
		return (TawSystemRoletease) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.role_tease.TawSystemRoleteaseDao#saveTawSystemRoleteases(com.boco.eoms.role_tease.TawSystemRoletease)
	 *      
	 */
	public void saveTawSystemRoletease(final TawSystemRoletease tawSystemRoletease) {
		if ((tawSystemRoletease.getId() == null) || (tawSystemRoletease.getId().equals("")))
			getHibernateTemplate().save(tawSystemRoletease);
		else
			getHibernateTemplate().saveOrUpdate(tawSystemRoletease);
	}

	/**
	 * 
	 * @see com.boco.eoms.role_tease.TawSystemRoleteaseDao#removeTawSystemRoleteases(java.lang.String)
	 *      
	 */
    public void removeTawSystemRoletease(final String id) {
		getHibernateTemplate().delete(getTawSystemRoletease(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		TawSystemRoletease tawSystemRoletease = this.getTawSystemRoletease(id);
		if(tawSystemRoletease==null){
			return "";
		}
		//TODO 请修改代码
		return "";
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.role_tease.TawSystemRoleteaseDao#getTawSystemRoleteases(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getTawSystemRoleteases(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawSystemRoletease tawSystemRoletease";
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
	 * 得到某用户的所有流程子角色
	 * 
	 * @param userid
	 * @return
	 */
	public List getRoleidByuserid(String userid) {

		List roleidlist = new ArrayList();

		String hql = " select distinct refrole.subRoleid from TawSystemUserRefRole refrole where refrole.userid='"
				+ userid + "' and refrole.roleType=1" ;
		roleidlist = getHibernateTemplate().find(hql);
		return roleidlist;
	}
		
		/**
		 * 通过子角色id们得到大角色id们
		 * 
		 * @param 
		 * @return
		 */
	public List getRoleidsBysubRoleIds(List subRoleIds) {

		List roleidlist = new ArrayList();
        String strSubroles = "";
        if(subRoleIds.size()>0){
        for(int i=0;i<subRoleIds.size();i++){
        	strSubroles +="'"+(String)subRoleIds.get(i)+"',";
        }
        strSubroles = strSubroles.substring(0, strSubroles.length()-1);
		String hql = " select distinct subrole.roleId from TawSystemSubRole subrole," +
				"TawSystemRole role where subrole.id in("
				+ strSubroles + ") and subrole.roleId=role.roleId and role.workflowFlag!=0" ;
		roleidlist = getHibernateTemplate().find(hql);
        }
		return roleidlist;
	}
	/**
     * 根据flowid查询流程信息
     * @param flowid 流程名称
     * @return
     * @throws Exception
     */
    public TawSystemRoleWorkflow getTawSystemWorkflowByFlowId(final String flowId) throws Exception {
    	HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawSystemRoleWorkflow workflow where "
						+ "workflow.flowId=:flowId ";
				Query query = session.createQuery(queryStr);
				query.setString("flowId", flowId);
				if(!query.list().isEmpty()){
				   return query.list().get(0);
				}
				else {
					return null;
				}
			}
		};
		return (TawSystemRoleWorkflow) getHibernateTemplate().execute(callback);
    }
}