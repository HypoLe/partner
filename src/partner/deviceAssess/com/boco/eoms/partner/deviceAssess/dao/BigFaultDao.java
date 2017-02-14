package com.boco.eoms.partner.deviceAssess.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.deviceAssess.model.BigFault;

/**
 * <p>
 * Title:厂家设备重大故障事件信息
 * </p>
 * <p>
 * Description:厂家设备重大故障事件信息
 * </p>
 * <p>
 * Mon Sep 27 13:45:23 CST 2010 
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public interface BigFaultDao extends Dao {

    /**
    *
    *取厂家设备重大故障事件信息列表
    * @return 返回厂家设备重大故障事件信息列表
    */
    public List getBigFaults();
    
    /**
    * 根据主键查询厂家设备重大故障事件信息
    * @param id 主键
    * @return 返回某id的厂家设备重大故障事件信息
    */
    public BigFault getBigFault(final String id);
    
    /**
    *
    * 保存厂家设备重大故障事件信息    
    * @param bigFault 厂家设备重大故障事件信息
    * 
    */
    public void saveBigFault(BigFault bigFault);
    
    /**
    * 根据id删除厂家设备重大故障事件信息
    * @param id 主键
    * 
    */
    public void removeBigFault(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getBigFaults(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}