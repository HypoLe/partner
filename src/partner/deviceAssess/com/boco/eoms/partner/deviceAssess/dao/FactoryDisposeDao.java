package com.boco.eoms.partner.deviceAssess.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.deviceAssess.model.FactoryDispose;

/**
 * <p>
 * Title:厂家处理设备故障事件信息
 * </p>
 * <p>
 * Description:厂家处理设备故障事件信息
 * </p>
 * <p> 
 * Sun Sep 26 15:01:06 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public interface FactoryDisposeDao extends Dao {

    /**
    *
    *取厂家处理设备故障事件信息列表
    * @return 返回厂家处理设备故障事件信息列表
    */
    public List getFactoryDisposes();
    
    /**
    * 根据主键查询厂家处理设备故障事件信息
    * @param id 主键
    * @return 返回某id的厂家处理设备故障事件信息
    */
    public FactoryDispose getFactoryDispose(final String id);
    
    /**
    *
    * 保存厂家处理设备故障事件信息    
    * @param factoryDispose 厂家处理设备故障事件信息
    * 
    */
    public void saveFactoryDispose(FactoryDispose factoryDispose);
    
    /**
    * 根据id删除厂家处理设备故障事件信息
    * @param id 主键
    * 
    */
    public void removeFactoryDispose(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getFactoryDisposes(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}