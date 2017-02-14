package com.boco.eoms.partner.deviceAssess.dao;

import com.boco.eoms.base.dao.Dao;
import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.deviceAssess.model.Softupinfo;

/**
 * <p>
 * Title:软件升级事件信息
 * </p>
 * <p>
 * Description:软件升级事件信息
 * </p>
 * <p>
 * Mon Sep 27 11:46:52 CST 2010
 * </p>
 *  
 * @author zhangxuesong
 * @version 1.0
 * 
 */
public interface SoftupinfoDao extends Dao {

    /**
    *
    *取软件升级事件信息列表
    * @return 返回软件升级事件信息列表
    */
    public List getSoftupinfos();
    
    /**
    * 根据主键查询软件升级事件信息
    * @param id 主键
    * @return 返回某id的软件升级事件信息
    */
    public Softupinfo getSoftupinfo(final String id);
    
    /**
    *
    * 保存软件升级事件信息    
    * @param softupinfo 软件升级事件信息
    * 
    */
    public void saveSoftupinfo(Softupinfo softupinfo);
    
    /**
    * 根据id删除软件升级事件信息
    * @param id 主键
    * 
    */
    public void removeSoftupinfo(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getSoftupinfos(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}