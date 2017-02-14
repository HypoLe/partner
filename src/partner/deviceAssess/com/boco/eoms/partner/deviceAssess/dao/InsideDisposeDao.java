package com.boco.eoms.partner.deviceAssess.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.deviceAssess.model.InsideDispose;

/**
 * <p>
 * Title:移动内部处理的设备故障事件信息
 * </p>
 * <p>
 * Description:移动内部处理的设备故障事件信息
 * </p>
 * <p>
 * Sun Sep 26 09:32:29 CST 2010
 * </p>
 * 
 * @author benweiwei 
 * @version 1.0
 * 
 */
public interface InsideDisposeDao extends Dao {

    /**
    *
    *取移动内部处理的设备故障事件信息列表
    * @return 返回移动内部处理的设备故障事件信息列表
    */
    public List getInsideDisposes();
    
    /**
    * 根据主键查询移动内部处理的设备故障事件信息
    * @param id 主键
    * @return 返回某id的移动内部处理的设备故障事件信息
    */
    public InsideDispose getInsideDispose(final String id);
    
    /**
    *
    * 保存移动内部处理的设备故障事件信息    
    * @param insideDispose 移动内部处理的设备故障事件信息
    * 
    */
    public void saveInsideDispose(InsideDispose insideDispose);
    
    /**
    * 根据id删除移动内部处理的设备故障事件信息
    * @param id 主键
    * 
    */
    public void removeInsideDispose(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getInsideDisposes(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}