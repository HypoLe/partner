package com.boco.eoms.partner.assess.AssFlow.mgr;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.boco.eoms.partner.assess.AssFlow.model.AssOperateStep;
import com.boco.eoms.partner.assess.AssReport.model.AssReportInfo;


/**
 * <p>
 * Title:后评估步骤表信息
 * </p>
 * <p>
 * Description:后评估步骤表信息
 * </p>
 * <p>
 * Fri Sep 10 13:54:56 CST 2010
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
 public interface IAssOperateStepMgr {
 
	/**
	 *
	 * 取后评估步骤表信息 列表
	 * @return 返回后评估步骤表信息列表
	 */
	public List getAssOperateSteps();
	/**
	 *
	 * 取后评估步骤表信息 列表
	 * @return 返回后评估步骤表信息列表
	 */
	public List getAssOperateSteps(String id);
    
	/**
	 * 根据主键查询后评估步骤表信息
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public AssOperateStep getAssOperateStep(final String id);
    
	/**
	 * 保存后评估步骤表信息
	 * @param assOperateStep 后评估步骤表信息
	 */
	public void saveAssOperateStep(AssOperateStep assOperateStep);
    
	/**
	 * 保存后评估步骤表信息
	 * @param assOperateStep 后评估步骤表信息
	 */
	public void saveAssOperateStep(HttpServletRequest request,String mainId,String nextState);
    
	/**
	 * 根据条件分页查询后评估步骤表信息
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回后评估步骤表信息的分页列表
	 */
	public Map getAssOperateSteps(final Integer curPage, final Integer pageSize,
			final String whereStr);
    
	/**
	 * 得到待处理列表
	 * @param userId 人员id
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @return 返回后评估步骤表信息的分页列表
	 */
	public Map listUndoAss(final String userId, final Integer curPage, final Integer pageSize);

	/**
	 * 将查询结构中Report和Step的信息拷贝到AssOperateStepForm中
	 * @param reportAndStepList 人员id
	 * @return 返回AssOperateStepForm的集合
	 */
	public List putReportAndStepListToForm(final List reportAndStepList);
	public List getKpiInstance(HttpServletRequest request,String reportId);
}