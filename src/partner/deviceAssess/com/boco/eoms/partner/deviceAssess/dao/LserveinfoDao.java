package com.boco.eoms.partner.deviceAssess.dao;

import com.boco.eoms.base.dao.Dao;
import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.deviceAssess.model.Lserveinfo;

/**
 * <p>
 * Title:现场服务事件信息
 * </p>
 * <p>
 * Description:现场服务事件信息
 * </p>
 * <p>
 * Mon Sep 27 11:46:52 CST 2010
 * </p> 
 * 
 * @author zhangxuesong
 * @version 1.0
 * 
 */
public interface LserveinfoDao extends Dao {

    /**
    *
    *取现场服务事件信息列表
    * @return 返回现场服务事件信息列表
    */
    public List getLserveinfos();
    
    /**
    * 根据主键查询现场服务事件信息
    * @param id 主键
    * @return 返回某id的现场服务事件信息
    */
    public Lserveinfo getLserveinfo(final String id);
    
    /**
    *
    * 保存现场服务事件信息    
    * @param lserveinfo 现场服务事件信息
    * 
    */
    public void saveLserveinfo(Lserveinfo lserveinfo);
    
    /**
    * 根据id删除现场服务事件信息
    * @param id 主键
    * 
    */
    public void removeLserveinfo(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getLserveinfos(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
}