package com.boco.eoms.deviceManagement.common.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.deviceManagement.common.pojo.CommonSearch;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.google.common.base.Preconditions;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.SearchResult;

public class CommonGenericDaoImpl<T, ID extends Serializable> extends
		GenericDAOImpl<T, String> implements CommonGenericDao<T, String> {
	private HibernateTemplate hibernateTemplate;
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public <RT> SearchResult<RT> searchAndCount(CommonSearch commonSearch) {
		Preconditions.checkNotNull(commonSearch);
		SearchResult<RT> result = new SearchResult<RT>();

		Integer total = (Integer) getSession().createQuery(
				commonSearch.getCountHql()).iterate().next();

		Query query = getSession().createQuery(commonSearch.getQueryHql());
		query.setFirstResult(commonSearch.getFirstResult());
		query.setMaxResults(commonSearch.getMaxResults());

		result.setTotalCount(total.intValue());
		result.setResult(query.list());
		return result;
	}
	
	//------------------------- HQL ------------------------------------------
	/**
	 * 使用HQL语句增、删、改实体对象
	 */
	public int bulkUpdateByHql(String hql){
		return getHibernateTemplate().bulkUpdate(hql);
	}
	
	/**
	 * 使用带参数的HQL语句增、删、改实体对象
	 */
	public int bulkUpdateByHql(String hql, Object[] values){
		return getHibernateTemplate().bulkUpdate(hql, values);
	}
	
	/**
     * 使用HQL语句检索数据
     */
	public List findByHql(String hql){
		return getHibernateTemplate().find(hql);
	}

	/**
     * 使用带参数的HQL语句检索数据
     */
    public List findByHql(String hql, Object[] values){
    	return getHibernateTemplate().find(hql, values);
    }
    
    /**
     * 使用带参数的HQL语句检索数据
     */
    public List findByHql(String hql, List values){
    	return findByHql(hql, values.toArray());
    }
    
    /**
     * 使用带可变参数的HQL语句检索数据
     */
    public List findByHqlVarParams(String hql, Object... values){
    	return findByHql(hql, values);
    }

    /**
     * 使用带命名参数的HQL语句检索数据
     */
    public List findByNamedParam(String hql, String[] paramNames,Object[] values){
    	return getHibernateTemplate().findByNamedParam(hql, paramNames,values);
    }
    
    /**
     * 使用HQL分页公用方法
     * @param hql       hql查询语句
     * @param params    参数
     * @param offset    分页偏移量
     * @param pagesize  每页条数
     * @return
     */
    public PageModel searchPaginated(final String hql, final Object[] params, 
  		  final int offset,final int pagesize){
	    	HibernateCallback callback = new HibernateCallback() {
	  			public Object doInHibernate(Session session) throws HibernateException {
	  				//获取记录总数
	  				String countHql = getCountQuery(hql);
	  				Query countquery = session.createQuery(countHql);
	  				if (params != null && params.length > 0) {
	  					for (int i = 0; i < params.length; i++) {
	  						countquery.setParameter(i, params[i]);
	  					}
	  				}
	  				Object totalObj = countquery.uniqueResult();
	  				int total = (Integer)totalObj;
	  				// 获取当前页的结果集
	  				Query query  = session.createQuery(hql);
	  				if (params != null && params.length > 0) {
	  					for (int i = 0; i < params.length; i++) {
	  						query.setParameter(i, params[i]);
	  					}
	  				}
	  				query.setFirstResult(offset);
	  				query.setMaxResults(pagesize);
	  				PageModel pm = new PageModel();
	  				pm.setTotal(total);
	  				pm.setDatas(query.list());
	  				return pm;
	  			}
	  		};
	  		return (PageModel) getHibernateTemplate().execute(callback);
    }
	
	private String getCountQuery(String hql) {
		String countHql = "";
		int fromIndex = hql.indexOf("from");
		int orderbyIndex = hql.indexOf("order by");
		
		if(orderbyIndex > 0){
			hql = hql.substring(0,orderbyIndex);
		}
		int groupbyIndex = hql.indexOf("group by");
		if(groupbyIndex < 0){
			countHql = "select count(*) " + hql.substring(fromIndex);
		}else{
			countHql = "select count(*) from (" + hql + ")";
		}
		return countHql;
	}
	
	//------------------------- JDBC Connection ------------------------------------------
	/*以下为利用jdbcTemplate来获取Connection的纯jcbc操作的相关方法 begin by zhangkeqi*/
	public Connection getCon() throws Exception{
		return this.getJdbcTemplate().getDataSource().getConnection();
	}
	
	public int saveOrUpdate(String sql,Connection conn) throws Exception{
		Statement stmt = null;
		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
		}finally{
			try{
				if(stmt != null){
					stmt.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return count;
	}
	
	/**
	 * 批量执行
	 * @param conn
	 * @param ps
	 * @throws Exception
	 */
	public void batchSaveData(Connection conn,PreparedStatement ps) throws Exception{
		try{
			ps.executeBatch();
		}  finally {
			try {
				if(ps != null){
					ps.clearBatch();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void closeConn(Connection conn){
		try {
			if(conn != null){
				if(conn.isClosed() == false){
					conn.close();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 查询一列数据集合
	 * @param conn
	 * @param sql
	 * @return
	 */
	public List<Object> findOneColumnList(Connection conn,String sql){
		List<Object> resultList = new ArrayList<Object>();
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet set = ps.executeQuery();
			while(set.next()){
				resultList.add(set.getObject(1));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultList;
	}
	
	public Map<String,Object> getDataSynchResultList(String sql,String countSql) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		Connection conn = null;
		Statement stmt = null;
		List<Map<String,Object>> resultList = null;
		try {
			conn = this.getCon();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			resultList = CommonUtils.resultSet2ListMap(rs);
			
			map.put("result", resultList);
			
			if (!"".equals(countSql) && null == countSql) {
				ResultSet crs = stmt.executeQuery(countSql);
				int count = 0;
				while (crs.next()) {
					count = crs.getInt(1);
					break;
				}
				map.put("count", count);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(stmt != null){
					stmt.close();
				}
				if(conn != null) {
					if(!conn.isClosed()) {
						conn.close();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public  Map<String,Object> getDataList(final Class<T> entryClass,final String alias,final String entrySql,final String countSql,final int pageIndex,final int pageSize){
		HibernateCallback callback = new HibernateCallback() {
  			public Object doInHibernate(Session session) throws HibernateException {
  			//取得总记录数
  				SQLQuery queryCount = session.createSQLQuery(countSql);
  				queryCount.addScalar("count",Hibernate.INTEGER);
  				final   int  totalCount = Integer.parseInt(queryCount.list().get(0).toString());
  				Map map = new HashMap();
  				//查询结果
  				   SQLQuery queryEntry = session.createSQLQuery(entrySql);
  				 	queryEntry.addEntity(alias,entryClass);
  					if (totalCount < 1) {
  						map.put("totalCount", 0);
  						return map;
  					}
  					
  					int tmppageSize = pageSize <= 0?15:pageSize;
  					int tmppageIndex = pageIndex <= 0?1:pageIndex+1;//第x页
  					/*if (pageIndex <= 0) {
  						tmppageIndex = 1;
  					} else {
  						tmppageIndex = pageIndex+1;//modify by fenguangping：index应该增加1，否则无法完成第2页的分页，后面的分页也完全不准
  					}	
  					//吕中潜注释掉：因为第x页的第一条记录的行idx: (x-1)*pageSize> totalCount-1（最后一条记录的行idx）时,查询的结果就是应该为空集
  					if ((tmppageIndex - 1) * tmppageSize >= totalCount) {
  						tmppageSize = 1;
  					}*/
  					
  					int firstResult = (tmppageIndex - 1) * tmppageSize;
  					queryEntry.setFirstResult(firstResult);
  					queryEntry.setMaxResults(tmppageSize);
  					
  					List<T>  list=queryEntry.list();
  					map.put("totalCount", totalCount);
  					map.put("list", list);
  					return map;
  			}
  		};
  		return (Map) getHibernateTemplate().execute(callback);
		
	}
	/*以下为利用jdbcTemplate来获取Connection的纯jcbc操作的相关方法 end   by zhangkeqi*/
	
	public SessionFactory getSessionFactory() {
		return super.getSessionFactory();
	}
	
	public Session getSession() {
		return super.getSession();
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	
}
