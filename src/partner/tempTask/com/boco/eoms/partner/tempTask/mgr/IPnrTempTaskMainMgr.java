package com.boco.eoms.partner.tempTask.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.tempTask.model.PnrTempTaskMain;

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
 public interface IPnrTempTaskMainMgr {
 
	/**
	 *
	 * 取合作伙伴临时任务管理 列表
	 * @return 返回合作伙伴临时任务管理列表
	 */
	public List getPnrTempTaskMains();
    
	/**
	 * 根据主键查询合作伙伴临时任务管理
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public PnrTempTaskMain getPnrTempTaskMain(final String id);
    
	/**
	 * 保存合作伙伴临时任务管理
	 * @param pnrTempTaskMain 合作伙伴临时任务管理
	 */
	public void savePnrTempTaskMain(PnrTempTaskMain pnrTempTaskMain);
    
	/**
	 * 根据主键删除合作伙伴临时任务管理
	 * @param id 主键
	 */
	public void removePnrTempTaskMain(final String id);
    
	/**
	 * 根据条件分页查询合作伙伴临时任务管理
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回合作伙伴临时任务管理的分页列表
	 */
	public Map getPnrTempTaskMains(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
	/** 
     * 按规则生成编号
     * type 对应模块
     * args 为其他参数
     */	
	public String creatNumber(String type,String maxNo)	;
}