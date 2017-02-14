package com.boco.eoms.partner.assess.AssExecute.mgr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.assess.AssExecute.dao.IAssKpiInstanceDao;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssKpiInstanceMgr;
import com.boco.eoms.partner.assess.AssExecute.model.AssKpiInstance;
import com.boco.eoms.partner.assess.AssTree.model.AssKpi;

public abstract class AssKpiInstanceMgrImpl implements IAssKpiInstanceMgr {

	private IAssKpiInstanceDao assKpiInstanceDao;

	public IAssKpiInstanceDao getAssKpiInstanceDao() {
		return assKpiInstanceDao;
	}

	public void setAssKpiInstanceDao(IAssKpiInstanceDao assKpiInstanceDao) {
		this.assKpiInstanceDao = assKpiInstanceDao;
	}

	public AssKpiInstance getKpiInstance(String id) {
		return assKpiInstanceDao.getKpiInstance(id);
	}



	public void removeKpiInstance(AssKpiInstance kpiInstance) {
		assKpiInstanceDao.removeKpiInstance(kpiInstance);
	}

	public void saveKpiInstance(AssKpiInstance kpiInstance) {
		assKpiInstanceDao.saveKpiInstance(kpiInstance);
	}


	public boolean isInstanceExistsInTime(String orgId, String date) {
		List instanceList = listKpiInstanceOfTemplate(orgId, date);
		boolean flag = false;
		if (null != instanceList && 0 < instanceList.size()) {
			flag = true;
		}
		return flag;
	}

	
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 王思轩 2009-1-20
	 * @param  任务id，时间类型，时间，合作伙伴ID
	 * @return 指标考核实例
	 */
	public AssKpiInstance getKpiInstanceByTimeAndPartner(final String taskDetailId,
			final String timeType,final String time, final String partnerId){
		return assKpiInstanceDao.getKpiInstanceByTimeAndPartner(taskDetailId, timeType, time, partnerId);
	}


	public Float getTotalScoreOfTemplate(String orgId, String date) {
		float totalScore = 0;
		List kpiList = listKpiOfTemplateWithScore(orgId, date);
		for (Iterator kpiIter = kpiList.iterator(); kpiIter.hasNext();) {
			AssKpi kpi = (AssKpi) kpiIter.next();
			if (null != kpi.getAssScore()) {
				totalScore = totalScore + kpi.getAssScore().floatValue();
			}
		}
		return Float.valueOf(totalScore);
	}
	
	/**
	 * 详细任务ID转换为模板名称
	 * 王思轩 09-1-21
	 * @return
	 */
	public String id2Name(String id){
		return assKpiInstanceDao.id2Name(id);
	}
	
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 王思轩 2009-1-20
	 * @param  任务id，时间类型，时间，合作伙伴ID
	 * @return 指标考核实例
	 */
	public AssKpiInstance getKpiInstanceByTimeAndPartner(final String taskDetailId,
			final String timeType,final String time, final String partnerId,final  String city){
		return assKpiInstanceDao.getKpiInstanceByTimeAndPartner(taskDetailId, timeType, time, partnerId,city);
	}
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 王思轩 2009-1-20
	 * @param  任务id，时间类型，时间，合作伙伴ID
	 * @return 指标考核实例
	 */
	public AssKpiInstance getKpiInstanceByReport(final String taskDetailId,final String reportId){
		return assKpiInstanceDao.getKpiInstanceByReport(taskDetailId, reportId);
	}

	public double listKpiInstance(final String parentNodeId,final String partnerId, final String timeStr, final String city) {
		List kpiInstanceList = assKpiInstanceDao.listKpiInstance(parentNodeId, partnerId, timeStr, city);
		AssKpiInstance  assKpiInstance = null;
		double money = 0.0;
		for(int i = 0 ;i<kpiInstanceList.size();i++){
			assKpiInstance = (AssKpiInstance)kpiInstanceList.get(i);
			if(assKpiInstance.getMoney()!=null&&!"".equals(assKpiInstance.getMoney())){
				money = money + Double.parseDouble(assKpiInstance.getMoney()) ;
			}
		}
		return money;
	}

	/**
	 * 根据reportId得到评估指标分项得分
	 * 
	 * @param  reportId
	 * @return List
	 */	
	public List getKpiInstancesByReport(final String reportId){
		return assKpiInstanceDao.getKpiInstancesByReport(reportId);
	}
	/**
	 * 得到评估指标分项得分
	 * 
	 * @param  nodeId指标节点Id,city 对应地市,time 执行时间
	 * @return Float
	 */	
	public Float  getKpiInstanceRealScore(final String nodeId,
			final String city, final String time,final String partnerId) {
		List list = (List)assKpiInstanceDao.getKpiInstanceRealScore(nodeId, city, time,partnerId);
		Float realScoreFloat = 0.0f;
		for(int i = 0;i<list.size();i++){
			realScoreFloat = realScoreFloat + Float.parseFloat(String.valueOf(list.get(i)));
		}
		return realScoreFloat;
	}
	
	public List getReportInfoBySpecialty(String reportId, String specialty) {
		return assKpiInstanceDao.getReportInfoBySpecialty(reportId, specialty);
	}
	public List getAssKpiInstanceByQuote(String reportId, String quote) {
		return assKpiInstanceDao.getAssKpiInstanceByQuote(reportId, quote);
	}
}
