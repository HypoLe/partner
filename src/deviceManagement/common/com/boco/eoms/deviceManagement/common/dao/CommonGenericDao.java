package com.boco.eoms.deviceManagement.common.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.deviceManagement.common.pojo.CommonSearch;
import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.googlecode.genericdao.search.SearchResult;

/**
 * <p>
 * We have this base class for our GenericDAOs so that we don't have to override
 * and autowire the sessionFactory property for each one. That is the only
 * reason for having this class.
 * 
 * <p>
 * The
 * 
 * @Autowired annotation tells Spring to inject the sessionFactory bean into
 *            this setter method.
 * 
 * @author Steve
 */
public interface CommonGenericDao<T, ID extends Serializable> extends
		GenericDAO<T, String> {
	public <RT> SearchResult<RT> searchAndCount(CommonSearch commonSearch);
	
	
	//------------------------- HQL ------------------------------------------ begin
	/**
	 * 使用HQL语句增、删、改实体对象
	 */
	public int bulkUpdateByHql(String hql);
	
	/**
	 * 使用带参数的HQL语句增、删、改实体对象
	 */
	public int bulkUpdateByHql(String hql, Object[] values);
	
	/**
     * 使用HQL语句检索数据
     */
	public List findByHql(String hql);

	/**
     * 使用带参数的HQL语句检索数据
     */
    public List findByHql(String hql, Object[] values);
    
    /**
     * 使用带参数的HQL语句检索数据
     */
    public List findByHql(String hql, List values);
    
    /**
     * 使用带可变参数的HQL语句检索数据
     */
    public List findByHqlVarParams(String hql, Object... values);

    /**
     * 使用带命名参数的HQL语句检索数据
     */
    public List findByNamedParam(String hql, String[] paramNames,Object[] values);
    
    /**
     * 使用HQL分页公用方法
     * @param hql       hql查询语句
     * @param params    参数
     * @param offset    分页偏移量
     * @param pagesize  每页条数
     * @return
     */
    public PageModel searchPaginated(final String hql, final Object[] params, 
    		final int offset,final int pagesize);
    
    /**
     * 将dao的事务对象开放（这样可供Service使用）
     */
    public SessionFactory getSessionFactory();
	public Session getSession();
	public HibernateTemplate getHibernateTemplate();
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate);
	//------------------------- HQL ------------------------------------------ end
	
	//------------------------- JDBC Template ------------------------------------------ begin
	public JdbcTemplate getJdbcTemplate();
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	//------------------------- JDBC Template ------------------------------------------ end
    
	//------------------------- JDBC Connection ------------------------------------------ begin
	public Connection getCon() throws Exception;
	public int saveOrUpdate(String sql,Connection conn) throws Exception;
	/**
	 * 批量执行
	 * @param conn
	 * @param ps
	 * @throws Exception
	 */
	public void batchSaveData(Connection conn,PreparedStatement ps) throws Exception;
	public void closeConn(Connection conn);
	/**
	 * 查询一列数据集合
	 * @param conn
	 * @param sql
	 * @return
	 */
	public List<Object> findOneColumnList(Connection conn,String sql);
	public Map<String,Object> getDataSynchResultList(String sql,String countSql);
	//------------------------- JDBC Connection ------------------------------------------ end
	
	
	public  Map<String,Object> getDataList(final Class<T> entryClass,final String alias,final String entrySql,final String countSql,final int pageIndex,final int pageSize);
}
