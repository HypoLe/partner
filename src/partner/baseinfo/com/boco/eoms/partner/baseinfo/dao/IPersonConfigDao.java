package com.boco.eoms.partner.baseinfo.dao;

import com.boco.eoms.base.dao.Dao;
import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.baseinfo.model.PersonConfig;

/**
 * <p>
 * Title:人员配置
 * </p>
 * <p>
 * Description:人员配置
 * </p>
 * <p>
 * Tue Dec 08 15:14:23 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public interface IPersonConfigDao extends Dao {

    /**
    *
    *取人员配置列表
    * @return 返回人员配置列表
    */
    public List getPersonConfigs();
    
    /**
    * 根据主键查询人员配置
    * @param id 主键
    * @return 返回某id的人员配置
    */
    public PersonConfig getPersonConfig(final String id);
    
    /**
    *
    * 保存人员配置    
    * @param personConfig 人员配置
    * 
    */
    public void savePersonConfig(PersonConfig personConfig);
    
    /**
    * 根据id删除人员配置
    * @param id 主键
    * 
    */
    public void removePersonConfig(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getPersonConfigs(final Integer curPage, final Integer pageSize,
			final String whereStr);
    /**
    *根据条件取人员配置列表
    * @param where where条件
    * @return 返回人员配置列表
    */
	public List getPersonConfigs(final String where) ;
}