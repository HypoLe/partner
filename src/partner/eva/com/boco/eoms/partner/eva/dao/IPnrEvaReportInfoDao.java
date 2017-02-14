package com.boco.eoms.partner.eva.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.eva.model.PnrEvaReportInfo;

public interface IPnrEvaReportInfoDao extends Dao{
	
	public PnrEvaReportInfo getPnrEvaReportInfo(String id);
	
	public void savePnrEvaReportInfo(PnrEvaReportInfo evaReportInfo);
	
	public void removePnrEvaReportInfo(PnrEvaReportInfo evaReportInfo);
	// 根据条件获取模板列表
	public List getReportInfoByCondition(String where);
	/**
	 * 查询某任务某时间所属周期的模板汇总记录

	 * @param  taskId
	 * 		任务id
	 * @param timeType 
	 * 		时间类型
	 * @param  time
	 * 		时间
	 * @param  partnerId
	 * 		合作伙伴ID
	 * 
	 * @return 模板汇总记录
	 */
	public List getReportInfoByTimeAndPartner(String templateId,String area,
			String timeType,String time, String partnerId,String publicFlag);

	
	/**
	 * 查询某任务时间段的总分记录实例
	 * 贾智会	09-11-12
	 * @param taskId
	 * @param timeType
	 * @param startTime
	 * @param endTime
	 * @param partnerId
	 * @return
	 */
	public Map listReportInfoForPage(final Integer curPage,final Integer pageSize,
			final String whereStr);

	
	/**
	 * 查询某任务某时间所属周期所有合作伙伴的模板汇总记录


	 * @param  taskId
	 * 		任务id
	 * @param timeType 
	 * 		时间类型
	 * @param  time
	 * 		时间
	 * @param  publicFlag
	 * 		发布标识
	 * 
	 * @return 模板汇总记录
	 */
	public List getReportInfosByTimeAndAllPartner(String templateId,String area,
			String timeType,String time, String publicFlag);

	/**
	 * 年统计报表
	 * @param belongNode
	 * @param startTime
	 * @param endTime
	 * @param publicFlag
	 * @return
	 */
	public List getReportYearStaticsByTime(String belongNode,String startTime,String endTime,String publicFlag);
	/**
	 * 月统计报表
	 * @param belongNode
	 * @param area
	 * @param time
	 * @param publicFlag
	 * @return
	 */
	public List getReportMouthStaticsByTime(String belongNode,String area,String time,String publicFlag);
	
	/**
	 * 得到某节点的汇总积分
	 * @param belongNode
	 * @param area
	 * @param time
	 * @param publicFlag
	 * @return
	 */
	public List getReportsByTime(String belongNode,String area,String time,String publicFlag);
}
