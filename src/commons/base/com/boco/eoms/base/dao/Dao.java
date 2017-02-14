package com.boco.eoms.base.dao;

import java.io.Serializable;
import java.util.List;

import com.boco.eoms.base.model.PageModel;

/**
 * Data Access Object (Dao) interface. This is an interface used to tag our Dao
 * classes and to provide common methods to all Daos.
 * 
 * <p>
 * <a href="Dao.java.html"><i>View Source</i></a>
 * </p>
 *   
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface Dao {
          
	/**
	 * Generic method used to get all objects of a particular type. This is the
	 * same as lookup up all rows in a table.
	 * 
	 * @param clazz
	 *            the type of objects (a.k.a. while table) to get data from
	 * @return List of populated objects
	 */
	public List getObjects(Class clazz);

	/**
	 * Generic method to get an object based on class and identifier. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param clazz
	 *            model class to lookup
	 * @param id
	 *            the identifier (primary key) of the class
	 * @return a populated object
	 * @see org.springframework.orm.ObjectRetrievalFailureException
	 */
	public Object getObject(Class clazz, Serializable id);

	/**
	 * Generic method to save an object - handles both update and insert.
	 * 
	 * @param o
	 *            the object to save
	 */
	public void saveObject(Object o);

	/**
	 * Generic method to delete an object based on class and id
	 * 
	 * @param clazz
	 *            model class to lookup
	 * @param id
	 *            the identifier (primary key) of the class
	 */
	public void removeObject(Class clazz, Serializable id);
	
	
	  /**
     * 使用HSQL语句检索数据
     */
    public List find(String queryString);

    /**
     * 使用带参数的HSQL语句检索数据
     */
    public List find(String queryString, Object[] values);
    
    /**
     * 使用带参数的HSQL语句检索数据
     */
    public List find(String queryString, List values);
    
    /**
     * 使用带可变参数的HSQL语句检索数据
     */
    public List findByVarParams(String queryString, Object... values);
    
	public int bulkUpdate(String queryString, Object[] values) ;

	public int bulkUpdate(String queryString) ;

    
    
    /**
     * 分页
     * @param hql       hql查询语句
     * @param params    参数
     * @param offset    分页偏移量
     * @param pagesize  每页条数
     * @return
     */
    public PageModel searchPaginated(final String hql, final Object[] params, 
  		  final int offset,final int pagesize);
}
