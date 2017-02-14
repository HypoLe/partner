package com.boco.eoms.partner.eva.mgr.impl;

import java.util.List;
import com.boco.eoms.partner.eva.dao.IPnrEvaKpiInstanceDao;
import com.boco.eoms.partner.eva.mgr.IPnrEvaKpiInstanceMgr;
import com.boco.eoms.partner.eva.model.PnrEvaKpiInstance;

public class PnrEvaKpiInstanceMgrImpl implements IPnrEvaKpiInstanceMgr {
	

	

	private IPnrEvaKpiInstanceDao pnrEvaKpiInstanceDao;

	public IPnrEvaKpiInstanceDao getPnrEvaKpiInstanceDao() {
		return pnrEvaKpiInstanceDao;
	}

	public void setPnrEvaKpiInstanceDao(IPnrEvaKpiInstanceDao pnrEvaKpiInstanceDao) {
		this.pnrEvaKpiInstanceDao = pnrEvaKpiInstanceDao;
	}

	public PnrEvaKpiInstance getKpiInstance(String id) {
		return pnrEvaKpiInstanceDao.getKpiInstance(id);
	}

	public void removeKpiInstance(PnrEvaKpiInstance kpiInstance) {
		pnrEvaKpiInstanceDao.removeKpiInstance(kpiInstance);
	}

	public void saveKpiInstance(PnrEvaKpiInstance kpiInstance) {
		pnrEvaKpiInstanceDao.saveKpiInstance(kpiInstance);
	}
	
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 王思轩 2009-1-20
	 * @param  任务id，时间类型，时间，合作伙伴ID
	 * @return 指标考核实例
	 */
	public PnrEvaKpiInstance getKpiInstanceByTimeAndPartner(final String taskDetailId,
			final String timeType,final String time, final String partnerId){
		return pnrEvaKpiInstanceDao.getKpiInstanceByTimeAndPartner(taskDetailId, timeType, time, partnerId);
	}
	
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * 王思轩 2009-1-22
	 * @param  任务id，合作伙伴ID，时间类型，时间范围，
	 * @return 指标考核实例
	 */
	public List getKpiInstanceListByTimeAndPartner(final String taskDetailId, 
			final String partnerId, final String timeType,
			final String startTime, final String endTime, final String isPublish){
		return pnrEvaKpiInstanceDao.getKpiInstanceListByTimeAndPartner(taskDetailId, partnerId, timeType, startTime, endTime, isPublish);
	}
	
	/**
	 * 详细任务ID转换为模板名称
	 * 王思轩 09-1-21
	 * @return
	 */
	public String id2Name(String id){
		return pnrEvaKpiInstanceDao.id2Name(id);
	}
	
	/**
	 * 根据任务详情信息Id查询指标考核实例
	 * 贾智会 09-11-05
	 * @return
	 */
	public PnrEvaKpiInstance getKpiInstanceByTaskDetailId(String taskDetailId) {
		// TODO Auto-generated method stub
		return pnrEvaKpiInstanceDao.getKpiInstanceByTaskDetailId(taskDetailId);
	}


	public List getDraftKpiInstanceList(String areaId){
		return pnrEvaKpiInstanceDao.getDraftKpiInstanceList(areaId);
	}
	
	
	/**
	 * 查询某任务某时间所属周期的所有指标考核实例
	 * @param  任务id，时间类型，时间，合作伙伴ID,发布标识
	 * @return 指标考核实例
	 */
	public List getKpiInstanceAndDetail(final String taskId,
			final String timeType,final String time, final String partnerId,final String isPublish){
		return pnrEvaKpiInstanceDao.getKpiInstanceAndDetail(taskId, timeType, time, partnerId,isPublish);
	}
}
