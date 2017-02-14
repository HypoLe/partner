package com.boco.eoms.partner.deviceAssess.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.deviceAssess.model.SoftApplyRecord;
import com.boco.eoms.partner.deviceAssess.model.SoftApplyRecord;

/**
 * <p>
 * Title:软件申请问题记录
 * </p>
 * <p>
 * Description:软件申请问题记录
 * </p>
 * <p>
 * Sun Sep 26 09:32:29 CST 2011
 * </p>
 * 
 * @author zhangkeqi 
 * @version 1.0
 * 
 */
public interface SoftApplyRecordDao extends Dao {

    /**
    *
    *取软件申请问题记录列表
    * @return 返回软件申请问题记录列表
    */
    public List getSoftApplyRecords();
    
    /**
    * 根据主键查询软件申请问题记录
    * @param id 主键
    * @return 返回某id的软件申请问题记录
    */
    public SoftApplyRecord getSoftApplyRecord(final String id);
    
    /**
    *
    * 保存软件申请问题记录   
    * @param SoftApplyRecord 软件申请问题记录
    * 
    */
    public void saveOrUpdateSoftApplyRecord(SoftApplyRecord softApplyRecord);
    
    /**
    * 根据id删除软件申请问题记录信息
    * @param id 主键
    * 
    */
    public void removeSoftApplyRecord(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getSoftApplyRecords(final Integer curPage, final Integer pageSize,
			final String whereStr);

	public Map getSoftApplyRecordsWithHQL(Integer curPage, Integer pageSize,
			String hql);
	
}