package com.boco.eoms.partner.baseinfo.dao;

import com.boco.eoms.base.dao.Dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.baseinfo.model.Resume;

/**
 * <p>
 * Title:工作简历
 * </p>
 * <p>
 * Description:工作简历
 * </p>
 * <p>
 * Fri Dec 18 16:50:43 CST 2009
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public interface IResumeDao extends Dao {

    /**
    *
    *取工作简历列表
    * @return 返回工作简历列表
    */
    public List getResumes();
    
    /**
    * 根据主键查询工作简历
    * @param id 主键
    * @return 返回某id的工作简历
    */
    public Resume getResume(final String id);
    
    /**
    *
    * 保存工作简历    
    * @param resume 工作简历
    * 
    */
    public void saveResume(Resume resume);
    
    /**
    * 根据id删除工作简历
    * @param id 主键
    * 
    */
    public void removeResume(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getResumes(final Integer curPage, final Integer pageSize,
			final String whereStr);
    /**
     * 分页取列表
     * @param curPage 当前页
     * @param pageSize 每页显示条数
     * @param whereStr where条件
     * @param ccds 查询入职的起始时间
     * @param ccde 查询入职的结束时间
     * @param dds 查询离职的起始时间
     * @param dde 查询离职的结束时间
     * @return map中total为条数,result(list) curPage页的记录
     */   
	public Map getResumes(final Integer curPage, final Integer pageSize,
			final String whereStr, final Date ccds ,final Date ccde ,final Date dds, final Date dde) ;
    /**
    *
    *按条件取工作简历列表条件
    * @param where where
    * @return 返回工作简历列表
    */
	public List getResumes(final String where);
}