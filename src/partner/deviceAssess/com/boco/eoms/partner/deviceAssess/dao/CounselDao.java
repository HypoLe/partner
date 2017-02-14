package com.boco.eoms.partner.deviceAssess.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.deviceAssess.model.Counsel;

/**
 * <p>
 * Title:咨询服务事件信息表
 * </p>
 * <p>
 * Description:咨询服务事件信息表
 * </p>
 * <p>
 * Mon Sep 27 15:01:30 CST 2010 
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public interface CounselDao extends Dao {

    /**
    *
    *取咨询服务事件信息表列表
    * @return 返回咨询服务事件信息表列表
    */
    public List getCounsels();
    
    /**
    * 根据主键查询咨询服务事件信息表
    * @param id 主键
    * @return 返回某id的咨询服务事件信息表
    */
    public Counsel getCounsel(final String id);
    
    /**
    *
    * 保存咨询服务事件信息表    
    * @param counsel 咨询服务事件信息表
    * 
    */
    public void saveCounsel(Counsel counsel);
    
    /**
    * 根据id删除咨询服务事件信息表
    * @param id 主键
    * 
    */
    public void removeCounsel(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getCounsels(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}