package com.boco.eoms.partner.tempTask.dao;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskExe;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title:合作伙伴临时任务管理
 * </p>
 * <p>
 * Description:合作伙伴临时任务管理主表信息
 * </p>
 * <p>
 * Mon Apr 26 14:09:01 CST 2010
 * </p>
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
public interface IPnrTempTaskExeDao extends Dao {

    /**
    *
    *取合作伙伴临时任务管理列表
    * @return 返回合作伙伴临时任务管理列表
    */
    public List getPnrTempTaskExes();
	 
	/**
	 *
	 * 根据临时任务id取合作伙伴工作内容列表
	 * @return 返回合作伙伴工作内容列表
	 */
	public List getPnrTempTaskExes(final String agreeId);
    /**
    * 根据主键查询合作伙伴临时任务管理
    * @param id 主键
    * @return 返回某id的合作伙伴临时任务管理
    */
    public PnrTempTaskExe getPnrTempTaskExe(final String id);
    
    /**
    *
    * 保存合作伙伴临时任务管理    
    * @param pnrTempTaskExe 合作伙伴临时任务管理
    * 
    */
    public void savePnrTempTaskExe(PnrTempTaskExe pnrTempTaskExe);
    
    /**
    * 根据id删除合作伙伴临时任务管理
    * @param id 主键
    * 
    */
    public void removePnrTempTaskExe(final String id);
    
    /**
    * 分页取列表
    * @param curPage 当前页
    * @param pageSize 每页显示条数
    * @param whereStr where条件
    * @return map中total为条数,result(list) curPage页的记录
    */
    public Map getPnrTempTaskExes(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
	/**
	 * 根据条件分页查询合作伙伴临时任务未执行的任务
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param userId 用户id
	 * @param deptId 部门id 
	 * @return 返回合作伙伴临时任务管理的分页列表
	 */
	public Map getPnrTempTaskUndo(final Integer curPage, final Integer pageSize,
			final String userId,final String deptId,final String date);
	
	/**
	 * 根据条件分页查询合作伙伴临时任务待执行的任务
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param userId 用户id
	 * @param deptId 部门id 
	 * @return 返回合作伙伴临时任务管理的分页列表
	 */
	public Map getPnrTempTaskForExecute(final Integer curPage, final Integer pageSize,
			final String userId,final String deptId,final String date);
}