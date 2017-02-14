package com.boco.eoms.partner.baseinfo.dao;

import com.boco.eoms.base.dao.Dao;
import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.baseinfo.model.OilEngineConfigure;

/**
 * <p>
 * Title:油机配置
 * </p>
 * <p>
 * Description:合作伙伴资源配置管理 油机配置
 * </p>
 * <p>
 * Sun Dec 13 21:42:25 CST 2009
 * </p>
 * 
 * @author wangjunfeng
 * @version 1.0
 * 
 */
public interface IOilEngineConfigureDao extends Dao {

    /**
    *
    *取油机配置列表
    * @return 返回油机配置列表
    */
    public List getOilEngineConfigures();
    
	/**
	 * 
	 * 判断是否有重复(联合主键)
	 * @return 返回重复条件的列表 
	 */
	public List isUnique(final String whereStr);

    
    /**
    * 根据主键查询油机配置
    * @param id 主键
    * @return 返回某id的油机配置
    */
    public OilEngineConfigure getOilEngineConfigure(final String id);
    
    /**
    *
    * 保存油机配置    
    * @param oilEngineConfigure 油机配置
    * 
    */
    public void saveOilEngineConfigure(OilEngineConfigure oilEngineConfigure);
    
    /**
    * 根据id删除油机配置
    * @param id 主键
    * 
    */
    public void removeOilEngineConfigure(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getOilEngineConfigures(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}