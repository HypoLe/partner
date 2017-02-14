package com.boco.eoms.commons.log.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.log.dao.TawCommonLogOperatorDao;
import com.boco.eoms.commons.log.model.TawCommonLogOperator;
import com.boco.eoms.commons.log.model.TawCommonStatBean;
import com.boco.eoms.commons.loging.BocoLog;

public class TawCommonLogOperatorDaoHibernate extends BaseDaoHibernate
		implements TawCommonLogOperatorDao {

	/**
	 * @see com.boco.eoms.commons.log.dao.TawCommonLogOperatorDao#getTawCommonLogOperators(com.boco.eoms.commons.log.model.TawCommonLogOperator)
	 */
	public List getTawCommonLogOperators(
			final TawCommonLogOperator tawCommonLogOperator) {
		return getHibernateTemplate().find("from TawCommonLogOperator");

		/*
		 * Remove the line above and uncomment this code block if you want to
		 * use Hibernate's Query by Example API. if (tawCommonLogOperator ==
		 * null) { return getHibernateTemplate().find("from
		 * TawCommonLogOperator"); } else { // filter on properties set in the
		 * tawCommonLogOperator HibernateCallback callback = new
		 * HibernateCallback() { public Object doInHibernate(Session session)
		 * throws HibernateException { Example ex =
		 * Example.create(tawCommonLogOperator).ignoreCase().enableLike(MatchMode.ANYWHERE);
		 * return
		 * session.createCriteria(TawCommonLogOperator.class).add(ex).list(); } };
		 * return (List) getHibernateTemplate().execute(callback); }
		 */
	}

	/**
	 * @see com.boco.eoms.commons.log.dao.TawCommonLogOperatorDao#getTawCommonLogOperator(String
	 *      id)
	 */
	public TawCommonLogOperator getTawCommonLogOperator(final String id) {
		TawCommonLogOperator tawCommonLogOperator = (TawCommonLogOperator) getHibernateTemplate()
				.get(TawCommonLogOperator.class, id);
		if (tawCommonLogOperator == null) {
			BocoLog.warn(this,"uh oh, tawCommonLogOperator with id '" + id
					+ "' not found...");
			throw new ObjectRetrievalFailureException(
					TawCommonLogOperator.class, id);
		}

		return tawCommonLogOperator;
	}

	/**
	 * @see com.boco.eoms.commons.log.dao.TawCommonLogOperatorDao#saveTawCommonLogOperator(TawCommonLogOperator
	 *      tawCommonLogOperator)
	 */
	public void saveTawCommonLogOperator(
			final TawCommonLogOperator tawCommonLogOperator) {
		if ((tawCommonLogOperator.getId() == null)
				|| (tawCommonLogOperator.getId().equals("")))
			getHibernateTemplate().save(tawCommonLogOperator);
		else
			getHibernateTemplate().saveOrUpdate(tawCommonLogOperator);
	}

	/**
	 * @see com.boco.eoms.commons.log.dao.TawCommonLogOperatorDao#removeTawCommonLogOperator(String
	 *      id)
	 */
	public void removeTawCommonLogOperator(final String id) {
		getHibernateTemplate().delete(getTawCommonLogOperator(id));
	}

	/**
	 * 查询某个用户的操作日志
	 */
	public List getAllByUserIDs(String userid, String issucess) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		String str = "error";
		String hql = "from TawCommonLogOperator bocolog where bocolog.userid='"
				+ userid + "'";
		if (issucess.equalsIgnoreCase("error")) {
			hql += (" and issucess='" + str + "'");
		} else if (issucess.equalsIgnoreCase("sucess")) {
			hql += (" and issucess !='" + str + "' ");
		}
		list = (ArrayList) getHibernateTemplate().find(hql);
		return list;
	}

	/**
	 * 查询某个模块的日志信息
	 */
	public List getAllBymodelids(String modelid, String issucess) {
		// TODO Auto-generated method stub
		List list = new ArrayList();

		String hql = "from TawCommonLogOperator bocolog where bocolog.modelid='"
				+ modelid + "'";
		String str = "error";
		if (issucess.equalsIgnoreCase("error")) {
			hql += (" and issucess='" + str + "'");
		} else if (issucess.equalsIgnoreCase("sucess")) {
			hql += (" and issucess !='" + str + "' ");
		}
		list = (ArrayList) getHibernateTemplate().find(hql);

		return list;
	}

	/**
	 * 通过userid 和 modelid 查询日志信息
	 */
	public List getAllByuseridandModelids(String userid, String modelid,
			String issucess) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		StringBuffer hql = new StringBuffer();
		hql.append("from TawCommonLogOperator bocolog where bocolog.userid='"
				+ userid + "'");
		hql.append(" and bocolog.modelid='" + modelid + "'");

		String str = "error";
		if (issucess.equalsIgnoreCase("error")) {
			hql.append(" and issucess='" + str + "'");
		} else if (issucess.equalsIgnoreCase("sucess")) {
			hql.append(" and issucess !='" + str + "' ");
		}

		String hqls = hql.toString();
		list = (ArrayList) getHibernateTemplate().find(hqls);

		return list;
	}

	/**
	 * 通过modelid 和 操作的id 查询日志
	 */
	public List getAllbyModelidAndOperids(String modelid, String operid,
			String issucess) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		StringBuffer hql = new StringBuffer();
		hql.append("from TawCommonLogOperator bocolog where bocolog.modelid='"
				+ modelid + "'");
		hql.append(" and bocolog.operid='" + operid + "'");

		String str = "error";
		if (issucess.equalsIgnoreCase("error")) {
			hql.append(" and issucess='" + str + "'");
		} else if (issucess.equalsIgnoreCase("sucess")) {
			hql.append(" and issucess !='" + str + "' ");
		}

		String hqls = hql.toString();
		list = (ArrayList) getHibernateTemplate().find(hqls);

		return list;
	}

	/**
	 * 通过用户ID 模块ID 操作ID 进行日志查询
	 */
	public Map getAllbyUseridModelidOperids(final Integer curPage, final Integer pageSize,final Map<String,String> map) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {

		StringBuffer hql = new StringBuffer();
		StringBuffer hq2 = new StringBuffer();
		hql.append("from TawCommonLogOperator bocolog where 1=1 ");
		hq2.append("select count(distinct bocolog.id) from TawCommonLogOperator bocolog where 1=1 ");
		
		String userid = map.get("userid");
		String modelid = map.get("modeid");
		String operid = map.get("operid");
		String startTime = map.get("starttime");
		String endTime = map.get("endtime");
		String issucess = map.get("issucess");
		String remoteip = map.get("remoteip");
		String code = map.get("code");
		String notemessage = map.get("notemessage");
		String opreatetime = map.get("opreatetime");
		
		if(userid!=null&&!userid.equals("")){
			hql.append(" and bocolog.userid='" + userid.trim() + "'");
			hq2.append(" and bocolog.userid='" + userid.trim() + "'");
		}
		if(modelid!=null&&!modelid.equals("") ){
			hql.append(" and bocolog.modelid='" + modelid.trim() + "'");
			hq2.append(" and bocolog.modelid='" + modelid.trim() + "'");
		}
		if(operid!=null&&!operid.equals("")){
			hql.append(" and bocolog.operid='" + operid.trim() + "'");
			hq2.append(" and bocolog.operid='" + operid.trim() + "'");
		}
		if(startTime!=null&&!startTime.equals("")){
			hql.append(" and bocolog.beginnotetime>='" + startTime.trim() + "'");
			hq2.append(" and bocolog.beginnotetime>='" + startTime.trim() + "'");
		}
		if(endTime!=null&&!endTime.equals("")){
			hql.append(" and bocolog.beginnotetime<='" + endTime.trim() + "'");
			hq2.append(" and bocolog.beginnotetime<='" + endTime.trim() + "'");
		}
		
		if(!StringUtils.isEmpty(remoteip)){
			hql.append(" and bocolog.remoteip='" + remoteip.trim() + "'");
			hq2.append(" and bocolog.remoteip='" + remoteip.trim() + "'");
		}
		
		if (issucess!=null&&!issucess.equals("")&& !issucess.equals("-1")) {
			hql.append(" and issucess='" + issucess.trim() + "'");
			hq2.append(" and issucess='" + issucess.trim() + "'");
		}
		
		if(code!=null&&!code.equals("")){
			hql.append(" and bocolog.operid='" + code.trim() + "'");
			hq2.append(" and bocolog.operid='" + code.trim() + "'");
		}
		
		if(notemessage!=null&&!notemessage.equals("")){
			hql.append(" and bocolog.notemessage='" + notemessage.trim() + "'");
			hq2.append(" and bocolog.notemessage='" + notemessage.trim() + "'");
		}
		
		if(opreatetime!=null&&!opreatetime.equals("")){
			hql.append(" and bocolog.operatetime='" + opreatetime.trim() + "'");
			hq2.append(" and bocolog.operatetime='" + opreatetime.trim() + "'");
		}
//		} else if (issucess.equalsIgnoreCase("sucess")) {
//			hql.append(" and issucess !='" + str + "' ");
//			hq2.append(" and issucess !='" + str + "' ");
//		}
		String hqls = hql.toString();
		String hqls2 = hq2.toString();
		Query countQuery = session.createQuery(hqls2);
	

		Integer total = (Integer) countQuery.iterate().next();
		Query query = session.createQuery(hqls);
	
		if(pageSize != -1){
			query.setFirstResult(pageSize.intValue()
					* (curPage.intValue()));
			query.setMaxResults(pageSize.intValue());
		}
		
		
		List result = query.list();
		HashMap map = new HashMap();
		map.put("total", total);
		map.put("result", result);
		return map;
			}
		};
		//list = (ArrayList) getHibernateTemplate().find(hqls);
		return (Map) getHibernateTemplate().execute(callback);
	}
	

	/**
	 * 查询执行成功的信息
	 */
	public List getAllbyisSucess(String issucess) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		StringBuffer hql = new StringBuffer();
		hql.append("from TawCommonLogOperator bocolog ");
		
		String str = "error";
		if (issucess.equalsIgnoreCase("error")) {
			hql.append(" where issucess='" + str + "'");
		} else if (issucess.equalsIgnoreCase("sucess")) {
			hql.append(" where issucess !='" + str + "' ");
		}
		String hqls = hql.toString();
		list = (ArrayList) getHibernateTemplate().find(hqls);
		return list;
		
	}
	
	public String getAllwelcomebyone(String dept) {
		
		List list = new ArrayList();
		StringBuffer hql = new StringBuffer();
		hql.append("select welcomedept.deptid from TawWelcomeDept welcomedept ");
		
		
		
			hql.append(" where welcomedept.depttype='" + dept + "'");
		
		String hqls = hql.toString();
		list = (ArrayList) getHibernateTemplate().find(hqls);
		
		String depts="";
		for(int i=0;i<list.size();i++){
			depts=depts+list.get(i).toString()+",";	
		}
		if(depts.length() == 0) {
			return "";
		} else {
			depts=depts.substring(0,depts.length()-1);			
		}
		return depts;
		
	}
	/**
	 * 登录日志统计部门数
	 * @param dept
	 * @return
	 */
public List getAlldeptcount(String dept,String begintime,String endtime) {
		
		List list = new ArrayList();
		StringBuffer hql = new StringBuffer();
		hql.append("select count(distinct systemuser.deptid),systemuser.deptid from TawCommonLogOperator operator,TawSystemUser systemuser ");
		hql.append(" where operator.userid=systemuser.userid");
		//hql.append(" and systemuser.deptid in(" + dept.trim() + ")");
		hql.append(" and operator.beginnotetime>='"+begintime.trim()+"'");
		hql.append(" and operator.beginnotetime<='"+endtime.trim()+"'");
		hql.append(" and operator.operid='0001' group by systemuser.deptid");
		String hqls = hql.toString();
		list = (ArrayList) getHibernateTemplate().find(hqls);
		
		String deptcount="";
		
		deptcount=list.get(0).toString();	
		
		
		return list;
		
	}

public List getAllusercount(String dept,String begintime,String endtime) {
	
	List list = new ArrayList();
	StringBuffer hql = new StringBuffer();
	hql.append("select count(distinct operator.userid),systemuser.deptid from TawCommonLogOperator operator,TawSystemUser systemuser ");
	hql.append(" where operator.userid=systemuser.userid");
	//hql.append(" and systemuser.deptid in(" + dept.trim() + ")");
	hql.append(" and operator.beginnotetime>='"+begintime.trim()+"'");
	hql.append(" and operator.beginnotetime<='"+endtime.trim()+"'");
	hql.append(" and operator.operid='0001' group by systemuser.deptid");
	String hqls = hql.toString();
	list = (ArrayList) getHibernateTemplate().find(hqls);
	
	String deptcount="";
	
	deptcount=list.get(0).toString();	
	
	
	return list;
	
}

public List getAllcount(String dept,String begintime,String endtime) {
	
	List list = new ArrayList();
	StringBuffer hql = new StringBuffer();
	hql.append("select count(*),systemuser.deptid from TawCommonLogOperator operator,TawSystemUser systemuser ");
	hql.append(" where operator.userid=systemuser.userid");
	//hql.append(" and systemuser.deptid in(" + dept.trim() + ")");
	hql.append(" and operator.beginnotetime>='"+begintime.trim()+"'");
	hql.append(" and operator.beginnotetime<='"+endtime.trim()+"'");
	hql.append(" and operator.operid='0001' group by systemuser.deptid");
	String hqls = hql.toString();
	list = (ArrayList) getHibernateTemplate().find(hqls);
	
	String deptcount="";
	
	deptcount=list.get(0).toString();	
	
	
	return list;
	
}

public List getListdeptStat(String dept,String begintime,String endtime) {
	
	List list = new ArrayList();
	StringBuffer hql = new StringBuffer();
	hql.append("select systemuser.deptname,count(distinct operator.userid),count(*),systemuser.deptid from TawCommonLogOperator operator,TawSystemUser systemuser ");
	hql.append(" where operator.userid=systemuser.userid");
	hql.append(" and systemuser.deptid in(" + dept.trim() + ")");
	hql.append(" and operator.beginnotetime>='"+begintime.trim()+"'");
	hql.append(" and operator.beginnotetime<='"+endtime.trim()+"'");
	hql.append(" and operator.operid='0001' group by systemuser.deptname, operator.userid,systemuser.deptid order by systemuser.deptid");
	String hqls = hql.toString();
	list = (ArrayList) getHibernateTemplate().find(hqls);
	
		
	
	
	return list;
	
}

public List getListuserStat(String dept,String begintime,String endtime) {
	
	List list = new ArrayList();
	StringBuffer hql = new StringBuffer();
	hql.append("select systemuser.deptname,systemuser.username,count(systemuser.username) from TawCommonLogOperator operator,TawSystemUser systemuser ");
	hql.append(" where operator.userid=systemuser.userid");
	hql.append(" and systemuser.deptid in(" + dept.trim() + ")");
	hql.append(" and operator.beginnotetime>='"+begintime.trim()+"'");
	hql.append(" and operator.beginnotetime<='"+endtime.trim()+"'");
	hql.append(" and operator.operid='0001' group by systemuser.username, systemuser.deptname order by systemuser.deptname");
	String hqls = hql.toString();
	list = (ArrayList) getHibernateTemplate().find(hqls);
	
		
	
	
	return list;
	
}

public Map getAllStat(final Integer curPage, final Integer pageSize,
		final String depts,final String startTime,final String endTime) {
	HibernateCallback callback = new HibernateCallback() {
		public Object doInHibernate(Session session)
				throws HibernateException {

	StringBuffer hql = new StringBuffer();
	StringBuffer hq2 = new StringBuffer();
	hql.append("select systemuser.deptname, systemuser.username,operator.beginnotetime,operator.remoteip from TawCommonLogOperator operator,TawSystemUser systemuser ");
	hql.append(" where operator.userid=systemuser.userid");
	hql.append(" and systemuser.deptid in(" + depts.trim() + ")");
	hql.append(" and operator.beginnotetime>='"+startTime.trim()+"'");
	hql.append(" and operator.beginnotetime<='"+endTime.trim()+"'");
	hql.append(" and operator.operid='0001' order by systemuser.deptname,systemuser.username");
	
	hq2.append("select count(*) from TawCommonLogOperator operator,TawSystemUser systemuser ");
	hq2.append(" where operator.userid=systemuser.userid");
	hq2.append(" and systemuser.deptid in(" + depts.trim() + ")");
	hq2.append(" and operator.beginnotetime>='"+startTime.trim()+"'");
	hq2.append(" and operator.beginnotetime<='"+endTime.trim()+"'");
	hq2.append(" and operator.operid='0001'");
	

	String hqls = hql.toString();
	String hqls2 = hq2.toString();
	Query countQuery = session.createQuery(hqls2);


	Integer total = (Integer) countQuery.iterate().next();
	Query query = session.createQuery(hqls);

	if(-1 != pageSize){
		query.setFirstResult(pageSize.intValue()
				* (curPage.intValue()));
		query.setMaxResults(pageSize.intValue());
	}
	
	List list = query.list();
	List result=new ArrayList();
	for(int i=0;i<list.size();i++){
		Object [] obj =(Object [])list.get(i);
		TawCommonStatBean bean =new TawCommonStatBean();
		bean.setUsername((String)obj[1]);
		bean.setDeptname((String)obj[0]);
		bean.setBeginnotetime((String)obj[2]);
		bean.setRemoteip((String)obj[3]);
		result.add(bean);
	}
	
	HashMap map = new HashMap();
	map.put("total", total);
	map.put("result", result);
	return map;
		}
	};
	//list = (ArrayList) getHibernateTemplate().find(hqls);
	return (Map) getHibernateTemplate().execute(callback);
}

public List getAlldepts(){
//	String hql = "select dept.deptId,dept.deptName from TawSystemDept dept ";
//	getSession().createQuery("from TawSystemDept dept").list();
	return getSession().createQuery("from TawSystemDept dept where dept.deptId is not null").list();
}
}
